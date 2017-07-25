package com.cha.transcoder.demon;

import java.io.File;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.cha.transcoder.common.AlteratConfigLoader;
import com.cha.transcoder.common.AlteratConfig;
import com.cha.transcoder.common.TcUtil;
import com.cha.transcoder.monitor.dao.MonitorDAO;
import com.cha.transcoder.profile.dao.ProfileDAO;
import com.cha.transcoder.watchfolder.dao.WatchfolderDAO;

public class TcJobMaker extends GeneralProcessor {

	private Logger log = Logger.getLogger(TcJobMaker.class);
	private static TcJobMaker instance;

	private ArrayList mediaFiles = new ArrayList();

	private TcJobMaker() {
	}

	public static TcJobMaker getInstance() {
		if (instance == null) {
			instance = new TcJobMaker();
		}
		return instance;
	}

	public void receiveEvent(Path source) {

		synchronized (mediaFiles) {
			File sFile = source.toFile();
			String file_path = sFile.getPath();
			log.debug("source=" + file_path);

			if (!mediaFiles.contains(file_path) && sFile.isFile()) {
				log.debug("add a media file=" + file_path);
				mediaFiles.add(file_path);

				File source_file = source.toFile();
				log.debug("source=" + source_file.toPath() + ", execute=" + source_file.canExecute() + ", read=" + source_file.canRead() + ", write="
						+ source_file.canWrite() + ", isDir=" + source_file.isDirectory());

				if (source_file.canRead()) {
					try {
						MonitorDAO monitorDAO = MonitorDAO.getInstance();
						if (monitorDAO != null) {
							String newPID = null;
							String maxPID = (String) monitorDAO.selectOne("monitor.selectPID");
							if (maxPID == null) {
								Date current = Calendar.getInstance().getTime();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

								newPID = sdf.format(current) + "00001";
							} else {
								int seq = Integer.parseInt(maxPID.substring(9));
								DecimalFormat df = new DecimalFormat("00000");

								Date current = Calendar.getInstance().getTime();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

								newPID = sdf.format(current) + df.format(seq + 1);
							}

							log.debug("newPID=" + newPID);

							Map<String, Object> params = new HashMap();
							params.put("j_id", "J" + newPID);

							// 프로파일을 가져온다.
							Map<String, Object> map2 = new HashMap();
							map2.put("enable_yn", "Y");
							map2.put("worker", "mepg2video");

							// Profile에 등록된 Video / Audio의 옵션을 가져온다.
							ProfileDAO profileDAO = ProfileDAO.getInstance();
							Map<String, Object> lstMXF = profileDAO.selectProfileByWorker(map2);
							String job_option = "";
							if (lstMXF != null) {
								String video_option = (String) lstMXF.get("video_option");
								String audio_option = (String) lstMXF.get("audio_option");

								job_option = video_option + " " + audio_option;
							}
							log.debug("job_option=" + job_option);

							Map<String, Object> map3 = new HashMap();

							String fpath = source_file.getPath();
							AlteratConfig altConfig = AlteratConfigLoader.getInstance().getConfig();
							if (altConfig.isWindow()) {
								fpath = fpath.substring(0, fpath.lastIndexOf("\\")); // 윈도우일때
								fpath = fpath.replace("\\", "/");
							} else {
								fpath = fpath.substring(0, fpath.lastIndexOf("/")); // 리눅스
							}

							log.debug("fpath=" + fpath);
							// map3.put("source_directory", fpath);

							WatchfolderDAO watchfolderDAO = WatchfolderDAO.getInstance();

							// 소스폴더에 해당하는 타겟이 있는지 확인
							List<Map<String, Object>> l_target = watchfolderDAO.selectTargetDirectory(map3);

							for (int i = 0; i < l_target.size(); i++) {
								Map<String, Object> m_target = l_target.get(i);
								String src_dir = (String) m_target.get("source_directory");
								src_dir = src_dir.replace("\\", "/");

								String tgt_dir = (String) m_target.get("target_directory");
								tgt_dir = tgt_dir.replace("\\", "/");

								log.debug("src_dir=" + src_dir);
								log.debug("tgt_dir=" + tgt_dir);

								if (fpath.indexOf(src_dir) != -1) {
									//fpath = fpath.replace(src_dir, tgt_dir);
									fpath = fpath.replaceFirst(src_dir, tgt_dir);
									log.debug("fpath=" + fpath);
								}
							}

							String target_directory = null;
							if (altConfig.isWindow()) {
								target_directory = fpath.replace("/", "\\");
							} else {
								target_directory = fpath.replace("\\", "/");
							}

							log.debug("target_directory=" + target_directory);
							makeDirectory(target_directory);

							// ------------------------------------
							// 데이터베이스에 Job을 넣는 부분
							// ------------------------------------
							String file_name = source_file.getName();
							String file_type = file_name.substring(file_name.lastIndexOf(".") + 1);
							file_type = file_type.toLowerCase();
							params.put("file_path", source_file.getPath());
							params.put("file_type", file_type);
							params.put("file_name", file_name);
							params.put("file_size", Long.toString(source_file.length()));
							params.put("file_status", "Create");
							params.put("target_directory", target_directory);
							params.put("delete_yn", "N");
							params.put("job_option", job_option);
							params.put("job_starttime", null);
							params.put("job_endtime", null);

							if (file_type.indexOf("m2ts") != -1 || file_type.indexOf("wmv") != -1 || file_type.indexOf("mts") != -1
									|| file_type.indexOf("avi") != -1 || file_type.indexOf("mpeg") != -1 || file_type.indexOf("mov") != -1
									|| file_type.indexOf("mp4") != -1 || file_type.indexOf("mkv") != -1) {
								params.put("job_status", "Hold On");
							} else {
								params.put("job_starttime", TcUtil.getCurrentTime());
								params.put("job_endtime", TcUtil.getCurrentTime());
								params.put("job_status", "Failed");
								// TODO, Failed 디렉토리로 복사
							}
							params.put("job_progress", "0");

							if (source_file.isFile()) {
								// DB Table이 utf-8로 안되어있으면 인서트 안됨.
								log.debug("Insert a job. j_id=J" + newPID);
								monitorDAO.insert("monitor.insert", params);
							}
						} else {
							log.debug("watchfolderDAO is null");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				log.debug("source=" + file_path + ", This file exists in vector or a directory.");
			}
		}
	}

	public void removeMediaFile(String file_path) {
		boolean result = mediaFiles.remove(file_path);
		log.debug("Remove a media file in mediaFiles (ArrayList)" + file_path + ", result=" + result);
	}

	public void removeAllMediaFiles() {
		log.debug("Remove all media files.");
		mediaFiles.clear();
	}
}