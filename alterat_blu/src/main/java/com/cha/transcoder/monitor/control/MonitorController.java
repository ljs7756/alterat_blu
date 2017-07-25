package com.cha.transcoder.monitor.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cha.transcoder.common.AlteratConfig;
import com.cha.transcoder.common.AlteratConfigLoader;
import com.cha.transcoder.common.CommandMap;
import com.cha.transcoder.demon.GeneralProcessor;
import com.cha.transcoder.demon.ScheduledJobRunner;
import com.cha.transcoder.demon.TcJobMaker;
import com.cha.transcoder.demon.TransferProcessor;
import com.cha.transcoder.monitor.dao.MonitorDAO;
import com.cha.transcoder.monitor.service.MonitorService;
import com.cha.transcoder.nps.MediaFileFilter;
import com.cha.transcoder.nps.service.RegisterService;
import com.cha.transcoder.profile.dao.ProfileDAO;
import com.cha.transcoder.watchfolder.dao.WatchfolderDAO;

@Controller
public class MonitorController extends GeneralProcessor {

	private Logger log = Logger.getLogger(MonitorController.class);
	private final static int PAGE_LIMIT = 10;
	
	private static MonitorController instance;

	public MonitorController() {
		instance = this;
	}

	public static MonitorController getInstance() {
		return instance;
	}

	@Resource(name = "monitorService")
	private MonitorService monitorService;

	@Resource(name = "registerService")
	private RegisterService registerService;

	@RequestMapping(value = "/selectMonitoring.do")
	public ModelAndView openMonitorList(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/monitoring");

		Map<String, Object> map = commandMap.getMap();

		int offset = 0;
		int current_page = 0;

		if (map.get("current_page") == null) {
			offset = 0;
			current_page = 0;
		} else {
			if (map.get("current_page") instanceof String) {
				current_page = Integer.parseInt((String) map.get("current_page"));
			} else if (map.get("current_page") instanceof String[]) {
				String[] arrCurPage = (String[]) map.get("current_page");
				current_page = Integer.parseInt(arrCurPage[0]);
			}

			offset = current_page * PAGE_LIMIT;
		}

		map.put("off_set", Integer.toString(offset));
		map.put("page_count", Integer.toString(PAGE_LIMIT));

		String chk_all = (String) map.get("chk_all");
		String chk_transcoding = (String) map.get("chk_transcoding");
		String chk_transferring = (String) map.get("chk_transferring");
		String chk_merge = (String) map.get("chk_merge");

		if (chk_all == null) {
			if (chk_transcoding == null) {
				map.put("chk_transcoding", "Empty");
			} else {
				map.put("chk_transcoding", "Transcoding");
			}

			if (chk_transferring == null) {
				map.put("chk_transferring", "Empty");
			} else {
				map.put("chk_transferring", "Transferring");
			}

			if (chk_merge == null) {
				map.put("chk_merge", "Empty");
			} else {
				map.put("chk_merge", "Merge");
			}
		} else {
			map.put("chk_transcoding", "Transcoding");
			map.put("chk_transferring", "Transferring");
			map.put("chk_merge", "Merge");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strToday = sdf.format(Calendar.getInstance().getTime());

		String begin_date = (String) map.get("begin_date");
		if (begin_date == null) {
			map.put("begin_date", strToday);
			begin_date = strToday;
		} else {
			map.put("begin_date", begin_date);
		}
		String end_date = (String) map.get("end_date");
		if (end_date == null) {
			map.put("end_date", strToday);
			end_date = strToday;
		} else {
			map.put("end_date", end_date);
		}

		String chk_status_all = (String) map.get("chk_status_all");
		String chk_holdon = (String) map.get("chk_holdon");
		String chk_progress = (String) map.get("chk_progress");
		String chk_canceled = (String) map.get("chk_canceled");
		String chk_failed = (String) map.get("chk_failed");
		String chk_completed = (String) map.get("chk_completed");

		if (chk_status_all == null) {
			if (chk_holdon == null) {
				map.put("chk_holdon", "Empty");
			} else {
				map.put("chk_holdon", "Hold On");
			}

			if (chk_progress == null) {
				map.put("chk_progress", "Empty");
			} else {
				map.put("chk_progress", "Inprogress");
			}

			if (chk_canceled == null) {
				map.put("chk_canceled", "Empty");
			} else {
				map.put("chk_canceled", "Canceled");
			}

			if (chk_failed == null) {
				map.put("chk_failed", "Empty");
			} else {
				map.put("chk_failed", "Failed");
			}

			if (chk_completed == null) {
				map.put("chk_completed", "Empty");
			} else {
				map.put("chk_completed", "Completed");
			}
		} else {
			map.put("chk_holdon", "Hold On");
			map.put("chk_progress", "Inprogress");
			map.put("chk_canceled", "Canceled");
			map.put("chk_failed", "Failed");
			map.put("chk_completed", "Completed");
		}

		// 리스트를 가져온다.
		List<Map<String, Object>> list = monitorService.select(commandMap.getMap());
		int count_all = monitorService.selectCountAll(commandMap.getMap());

		mv.addObject("list", list);
		mv.addObject("current_page", current_page);
		mv.addObject("tot_page", count_all / PAGE_LIMIT);

		// 페이지 리스트 보여주는 화면
		int start_page = 0;
		if (current_page > 0) {
			start_page = (current_page / 10) * 10;
		}

		int tot_page = count_all / PAGE_LIMIT;

		int pages = start_page + 9;
		if (pages > tot_page) {
			pages = tot_page;
		}

		String check_yn = null;
		if (map.get("check_yn") instanceof String) {
			check_yn = (String) map.get("check_yn");
		}

		if (check_yn == null) {
			mv.addObject("check_yn", "N");
		} else {
			mv.addObject("check_yn", check_yn);
		}

		// JSP 페이지로 값을 보낸다.
		mv.addObject("start_page", start_page);
		mv.addObject("current_page", current_page);
		mv.addObject("pages", pages);
		mv.addObject("chk_all", chk_all);
		mv.addObject("chk_transcoding", chk_transcoding);
		mv.addObject("chk_transferring", chk_transferring);
		mv.addObject("chk_merge", chk_merge);
		mv.addObject("begin_date", begin_date);
		mv.addObject("end_date", end_date);
		mv.addObject("chk_status_all", chk_status_all);
		mv.addObject("chk_holdon", chk_holdon);
		mv.addObject("chk_progress", chk_progress);
		mv.addObject("chk_canceled", chk_canceled);
		mv.addObject("chk_failed", chk_failed);
		mv.addObject("chk_completed", chk_completed);

		return mv;
	}

	@RequestMapping(value = "/deleteAll.do")
	public void openDeleteMonitor(HttpServletResponse response) throws Exception {

		// Job 데이터 삭제
		monitorService.deleteAll();

		// 등록관련 메타데이터 삭제
		registerService.deleteAll();

		// 자료를 초기화 한다.
		TcJobMaker.getInstance().removeAllMediaFiles();

		try {
			response.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 폴더 하위의 모든 파일을 리스트에 담아 리턴한다.
	private File[] retrevieFiles(File f) {
		ArrayList arrChild = new ArrayList();

		File[] listOfFiles = f.listFiles(new MediaFileFilter());
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isDirectory()) {
					File[] retVal = retrevieFiles(listOfFiles[i]);
					if (retVal != null) {
						for (int j = 0; j < retVal.length; j++) {
							arrChild.add(retVal[j]);
						}
					}
				} else {
					arrChild.add(listOfFiles[i]);
				}
			}
		}

		return (File[]) arrChild.toArray(new File[0]);
	}

	@RequestMapping(value = "/manualJobs.do")
	public void manualJobs(HttpServletResponse response) throws Exception {
		MonitorDAO dao = MonitorDAO.getInstance();
		try {
			WatchfolderDAO wDAO = WatchfolderDAO.getInstance();
			Map<String, Object> manual_folder = wDAO.selectWatchfolderOnlyManual();

			String source_directory = (String) manual_folder.get("source_directory");
			String target_directory = (String) manual_folder.get("target_directory");

			log.debug("source_directory=" + source_directory);
			log.debug("target_directory=" + target_directory);

			File f = new File(source_directory);

			dao.beginBatch();

			// 해당폴더의 파일과 하위폴더의 파일들을 DB 에 Job에 넣는다.
			// MediaFileFilter 에 의해 걸러진 영상파일만 처리한다.
			File[] listOfFiles = retrevieFiles(f);
			if (listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++) {
					//log.debug(i + "=" + listOfFiles[i].getParent());
					insertTcJob(dao.getSession(), listOfFiles[i], source_directory, target_directory);
				}
			} else {
				log.debug("listOfFiles is null");
			}

			dao.commit();

			try {
				response.getWriter().print("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			dao.rollback();

			log.error(e.getMessage());
			response.getWriter().print("failure");
		}

	}

	private void printMap(Map<String, Object> map) {
		if (map != null && !map.isEmpty()) {
			Iterator<String> keys = map.keySet().iterator();

			while (keys.hasNext()) {
				String key = keys.next();
				String value = (String) map.get(key);

				log.debug("key=" + key + ", value=" + value);
			}
		} else {
			log.debug("map is null");
		}
	}

	/**
	 * 트랜스코딩 작업을 등록하고, J_ID 를 리턴한다.
	 * 
	 */
	public String insertTcJob(SqlSession session, File source_file, String source_path, String target_path) {

		log.debug("source=" + source_file.toPath() + ", execute=" + source_file.canExecute() + ", read=" + source_file.canRead() + ", write="
				+ source_file.canWrite() + ", isDir=" + source_file.isDirectory());
		log.debug("source_path=" + source_path);
		log.debug("target_path=" + target_path);

		String newPID = null;

		if (source_file.canRead()) {
			try {
				if (session != null) {
					Date current = Calendar.getInstance().getTime();
					DecimalFormat df = new DecimalFormat("00000");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

					MonitorDAO mDAO = MonitorDAO.getInstance();
					newPID = mDAO.selectPrimaryKey(session);

					log.debug("newPID=" + newPID);

					Map<String, Object> params = new HashMap();
					params.put("j_id", "J" + newPID);
					params.put("j_type", "Transcoding");

					// 프로파일을 가져온다.
					Map<String, Object> map2 = new HashMap();
					map2.put("enable_yn", "Y");
					map2.put("worker", "mp4");

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
					fpath = fpath.substring(0, fpath.lastIndexOf(File.separatorChar));

					log.debug("fpath=" + fpath);
					log.debug("source_path=" + source_path);

					target_path = fpath.replace(source_path, target_path);
					log.debug("target_path=" + target_path);

					makeDirectory(target_path);

					// -----------------------------------------------------------
					// 데이터베이스에 Job을 넣는 부분, 미디어파일만 'Hold On'으로 넣는다.
					// -----------------------------------------------------------
					String file_name = source_file.getName();
					String file_type = file_name.substring(file_name.lastIndexOf(".") + 1);
					file_type = file_type.toLowerCase();
					params.put("file_path", source_file.getPath());
					params.put("file_type", file_type);
					params.put("file_name", file_name);
					params.put("file_size", Long.toString(source_file.length()));
					params.put("file_status", "Create");
					params.put("target_directory", target_path);
					params.put("delete_yn", "N");
					params.put("job_option", job_option);
					params.put("job_starttime", null);
					params.put("job_endtime", null);
					params.put("job_status", "Hold On");
					params.put("job_progress", "0");

					if (source_file.isFile()) {
						// 파라미터 출력
						mDAO.printQueryWithParam("monitor.insert", params);

						// DB Table이 utf-8로 안되어있으면 인서트 안됨.
						session.insert("monitor.insert", params);
					}
				} else {
					log.debug("session is null");
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return "J" + newPID;
	}
	
	
	/**
	 * 트랜스코딩 작업을 등록하고, J_ID 를 리턴한다.
	 * 
	 */
	public String insertTcJob_2(SqlSession session, File source_file, String source_path, String target_path, String worker, String bitrate, String p_id) {
	
		log.debug("source=" + source_file.toPath() + ", execute=" + source_file.canExecute() + ", read=" + source_file.canRead() + ", write="
				+ source_file.canWrite() + ", isDir=" + source_file.isDirectory());

		String newPID = null;

		if (source_file.canRead()) {
			try {
				if (session != null) {
					Date current = Calendar.getInstance().getTime();
					DecimalFormat df = new DecimalFormat("00000");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

					MonitorDAO mDAO = MonitorDAO.getInstance();
					newPID = mDAO.selectPrimaryKey(session);

					log.debug("newPID=" + newPID);

					Map<String, Object> params = new HashMap();
					params.put("j_id", "J" + newPID);
					params.put("j_type", "Transcoding");

					// 프로파일을 가져온다.
					Map<String, Object> map2 = new HashMap();
					map2.put("enable_yn", "Y");
					map2.put("p_id", p_id);

					// Profile에 등록된 Video / Audio의 옵션을 가져온다.
					ProfileDAO profileDAO = ProfileDAO.getInstance();
					Map<String, Object> lstFormat = profileDAO.selectProfileByWorker_2(map2);
					String job_option = "";
					if (lstFormat != null) {
						String video_option = (String) lstFormat.get("video_option");
						String audio_option = (String) lstFormat.get("audio_option");

						job_option = video_option + " " + audio_option;
					}
					log.debug("job_option=" + job_option);

					Map<String, Object> map3 = new HashMap();

					String fpath = source_file.getPath();
					fpath = fpath.substring(0, fpath.lastIndexOf(File.separatorChar));

					log.debug("fpath=" + fpath);
					log.debug("source_path=" + source_path);

					target_path = fpath.replace(source_path, target_path);
					log.debug("target_path=" + target_path);

					
					makeDirectory(target_path);
					
					
					// -----------------------------------------------------------
					// 데이터베이스에 Job을 넣는 부분, 미디어파일만 'Hold On'으로 넣는다.
					// -----------------------------------------------------------
					String file_name = source_file.getName();
					String file_type = file_name.substring(file_name.lastIndexOf(".") + 1);
					String target_name = file_name.substring(0,file_name.lastIndexOf(".")) +"_"+bitrate+"." + worker;
					file_type = file_type.toLowerCase();
					params.put("file_path", source_file.getPath());
					params.put("file_type", file_type);
					params.put("file_name", file_name);
					params.put("file_size", Long.toString(source_file.length()));
					params.put("file_status", "Create");
					params.put("target_directory", target_path);
					params.put("target_name", target_name);
					params.put("target_type", worker);
					params.put("bitrate", bitrate);
					params.put("delete_yn", "N");
					params.put("job_option", job_option);
					params.put("job_starttime", null);
					params.put("job_endtime", null);
					params.put("job_status", "Hold On");
					params.put("job_progress", "0");

					if (source_file.isFile()) {
						// 파라미터 출력
						mDAO.printQueryWithParam("monitor.insert", params);

						// DB Table이 utf-8로 안되어있으면 인서트 안됨.
						session.insert("monitor.insert", params);
					}
				} else {
					log.debug("session is null");
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return "J"+ newPID;
	}
	
	
		/**
		 * pooq용 자동 트랜스코딩 등록
		 * 
		 */
		public String insertTcJobAuto(SqlSession session, File source_file, String source_path, String target_path, String worker, String bitrate, String p_id, String merge_id, String ftp) {
		
			log.debug("source=" + source_file.toPath() + ", execute=" + source_file.canExecute() + ", read=" + source_file.canRead() + ", write="
					+ source_file.canWrite() + ", isDir=" + source_file.isDirectory());
			log.debug("source_path=" + source_path);
			log.debug("target_path=" + target_path);
		
			String newPID = null;

			if (source_file.canRead()) {
				try {
					if (session != null) {
						Date current = Calendar.getInstance().getTime();
						DecimalFormat df = new DecimalFormat("00000");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

						MonitorDAO mDAO = MonitorDAO.getInstance();
						newPID = mDAO.selectPrimaryKey(session);

						log.debug("newPID=" + newPID);

						Map<String, Object> params = new HashMap();
						params.put("j_id", "J" + newPID);
						params.put("j_type", "Transcoding");

						// 프로파일을 가져온다.
						Map<String, Object> map2 = new HashMap();
						map2.put("enable_yn", "Y");
						map2.put("p_id", p_id);
	
						// Profile에 등록된 Video / Audio의 옵션을 가져온다.
						ProfileDAO profileDAO = ProfileDAO.getInstance();
						Map<String, Object> lstFormat = profileDAO.selectProfileByWorker_2(map2);
						String job_option = "";
						if (lstFormat != null) {
							String video_option = (String) lstFormat.get("video_option");
							String audio_option = (String) lstFormat.get("audio_option");

							job_option = video_option + " " + audio_option;
						}
//						log.debug("job_option=" + job_option);

						Map<String, Object> map3 = new HashMap();

						String fpath = source_file.getPath();
						fpath = fpath.substring(0, fpath.lastIndexOf(File.separatorChar));

//						log.debug("fpath=" + fpath);
//						log.debug("source_path=" + source_path);

						target_path = fpath.replace(source_path, target_path);
						log.debug("target_path=" + target_path);
						
			
						
						makeDirectory(target_path);

						// -----------------------------------------------------------
						// 데이터베이스에 Job을 넣는 부분, 미디어파일만 'Hold On'으로 넣는다.
						// -----------------------------------------------------------
						
						//Merge- 문자를 제거한 파일명 트랜스코딩 등록
						String file_name = source_file.getName();
						
						if(file_name.lastIndexOf("Merge-") > -1){
							file_name =file_name.substring(6);
						}
						
						//target file 이름
						String target_name = file_name.substring(0,file_name.lastIndexOf(".")) +"_"+bitrate+"." + worker;
			
						String file_type = file_name.substring(file_name.lastIndexOf(".") + 1);
						file_type = file_type.toLowerCase();
						params.put("file_path", source_file.getPath());
						params.put("file_type", file_type);
						params.put("file_name", file_name);
						params.put("file_size", Long.toString(source_file.length()));
						params.put("file_status", "Create");
						params.put("target_directory", target_path);
						params.put("target_name", target_name);
						params.put("target_type", worker);
						params.put("bitrate", bitrate);
						params.put("delete_yn", "N");
						params.put("job_option", job_option);
						params.put("job_starttime", null);
						params.put("job_endtime", null);
						params.put("job_status", "Hold On");
						params.put("job_progress", "0");
						params.put("target_path", "0");
						params.put("ftp", ftp);

						if (source_file.isFile()) {
							// 파라미터 출력
							mDAO.printQueryWithParam("monitor.insert", params);

							//Stanby --> Done
							Map<String, Object> param_mergeId = new HashMap();
							param_mergeId.put("j_id", merge_id);
							log.debug("merge_id===="+merge_id);
							session.insert("monitor.updateFileStatus", param_mergeId);
							
							// DB Table이 utf-8로 안되어있으면 인서트 안됨.
							session.insert("monitor.insert", params);
						}
					} else {
						log.debug("session is null");
					}
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
			return "J" + newPID;
	}	

	@RequestMapping(value = "/deleteInputFiles.do")
	public void deleteInput(HttpServletResponse response) throws Exception {

		WatchfolderDAO wDAO = WatchfolderDAO.getInstance();
		Map<String, Object> manual_folder = wDAO.selectWatchfolderOnlyManual();

		String source_directory = (String) manual_folder.get("source_directory");

		log.debug("source_directory=" + source_directory);
		// log.debug("target_directory=" + target_directory);

		File f = new File(source_directory);

		// 해당폴더의 파일과 드렉토리를 지운다.
		File[] listOfFiles = retrevieFiles(f);
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				boolean success = listOfFiles[i].delete();

				if (success) {
					log.debug(i + "=" + listOfFiles[i] + " -> deleted.");
				} else {
					log.debug(i + "=" + listOfFiles[i] + " -> not deleted.");
				}
			}
		}

		try {
			response.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteOutputFiles.do")
	public void deleteOutput(HttpServletResponse response) throws Exception {

		WatchfolderDAO wDAO = WatchfolderDAO.getInstance();
		Map<String, Object> manual_folder = wDAO.selectWatchfolderOnlyManual();

		// String source_directory = (String)
		// manual_folder.get("source_directory");
		String target_directory = (String) manual_folder.get("target_directory");

		// log.debug("source_directory=" + source_directory);
		log.debug("target_directory=" + target_directory);

		File f = new File(target_directory);

		// 해당폴더의 파일과 드렉토리를 지운다.
		File[] listOfFiles = retrevieFiles(f);
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				boolean success = listOfFiles[i].delete();

				if (success) {
					log.debug(i + "=" + listOfFiles[i] + " -> deleted.");
				} else {
					log.debug(i + "=" + listOfFiles[i] + " -> not deleted.");
				}
			}
		}

		try {
			response.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/requestMetadata.do")
	public void requestMetadata(CommandMap commandMap, HttpServletResponse response) throws Exception {

		Map<String, Object> map = commandMap.getMap();

		Map<String, Object> result = monitorService.selectDetail(map);
		String metadata = (String) result.get("metadata");

		if (metadata != null) {
			metadata = metadata.replace("\n", "<br>");
		}
		log.debug("metadata=" + metadata);

		try {
			if (metadata == null) {
				response.getWriter().print("No metadata.");
			} else {
				response.getWriter().print(metadata);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/restartJob.do")
	public void restartJob(CommandMap commandMap, HttpServletResponse response) throws Exception {
		Map<String, Object> map = commandMap.getMap();

		// DB 상태값을 'Hold On'으로 변경하여 작업을 새로 가져갈 수 있도록 한다.
		monitorService.updateResetStatus(map);

		// Acive되어있는 Thread 를 취소시킨다.
		String j_id = (String) map.get("j_id");
		String j_type = (String) map.get("j_type");
	
		ScheduledJobRunner.cancelActiveThread(j_id, j_type); 

		try {
			response.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/cancelJob.do")
	public void cancelJob(CommandMap commandMap, HttpServletResponse response) throws Exception {
		Map<String, Object> map = commandMap.getMap();
	
		// Acive되어있는 Thread 를 취소시킨다.
		String j_id = (String) map.get("j_id");
		String j_type = (String) map.get("j_type");
		
		Map<String, Object> result = monitorService.selectDetail(map);
		
		String job_option = (String)result.get("job_option");

		//merge 경우 filename 프로세스를 구분한다.
		if(j_type.equals("Merge")){
			job_option = (String)result.get("file_name");
		}
		
		log.debug("cancel job_option08180927=="+job_option);
		
		  try {
			  
		  //윈도우 버전 시작
//			  Process p =Runtime.getRuntime().exec("tasklist /fi \"imagename eq ffmpeg.exe\"");  //window tasklist
			  Process p =Runtime.getRuntime().exec("wmic process where caption=\"ffmpeg.exe\" get commandline,processid /format:list");  //window wmic
			  
			   InputStream stderr = p.getInputStream();
			    BufferedReader br = new BufferedReader(new InputStreamReader(stderr));
		    
			    String line = null;
			    List<String> list = new ArrayList<String>();
			    
			    while ((line = br.readLine()) != null) {
			    
			     list.add(line);
			    }

			    if(list != null){
			    	for(int i=0; i<list.size(); i++){
			    		log.debug("cmdline=="+i+"=="+list.get(i));
			    		if(list.get(i).indexOf(job_option) != -1){
			    			log.debug("its me!!!=="+list.get(i));
			    			try{
			    				String pid = list.get(i+2).replace("ProcessId=", "");

		    		             log.debug("pid=="+pid); 
			    				
		    					  String cmd_kill = "taskkill /pid "+pid+" /f";
			    		             
			    				Process p_kill =Runtime.getRuntime().exec(cmd_kill);
			    				
			    			}catch (Exception e) {
			  				  e.getStackTrace();
			  			  }
			    		}
			    	}
			    }
		//윈도우 버전 끝	    
			  
	   // 리눅스 버전 시작
//			  String sh = "/bin/sh";
//			  String cop = "-c";
//			  String cmd = "ps -ef | grep ffmpeg";
//			  
//			  String[] exe = new String[] {sh, cop, cmd};
//
//			  Process p = Runtime.getRuntime().exec(exe);
//			    InputStream stderr = p.getInputStream();
//			    BufferedReader br = new BufferedReader(new InputStreamReader(stderr));
//		    
//			    String line = null;
//			    List<String> list = new ArrayList<String>();
//			    
//			    while ((line = br.readLine()) != null) {
//	
//			     list.add(line);
//			    }
//			    
//			    if(list != null){
//			    	for(int i=0; i<list.size(); i++){
//			    		log.debug("cmdline=="+i+"=="+list.get(i));
//			    		if(list.get(i).indexOf(job_option) != -1){
//			    			log.debug("its me!!!=="+list.get(i));
//			    			try{
//			    				
//			    				StringTokenizer st = new StringTokenizer(list.get(i));
//			    	
//			    		            			 st.nextToken();  //1th user
//			    		            String pid = st.nextToken();  //2th pid
//			    		             log.debug("pid=="+pid); 
//			    				
//		    					  String cmd_kill = "kill -9 "+pid;
//		    					  String[] exe_kill = new String[] {sh, cop, cmd_kill};
//			    		             
//			    				Process p_kill =Runtime.getRuntime().exec(exe_kill);
//			    				
//			    			}catch (Exception e) {
//			  				  e.getStackTrace();
//			  			  }
//			    		}
//			    	}
//			    }
			 // 리눅스 버전 끝
			  
			  } catch (Exception e) {
				  e.getStackTrace();
			  }
		
		

		  ScheduledJobRunner.cancelActiveThread(j_id, j_type);
		
		try {
			response.getWriter().print("success");
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		// DB의 상태값을 'Canceled'로 변경한다.
		monitorService.updateCanceledState(map);
	}
	
	@RequestMapping(value = "/getprogress.do")
	public ModelAndView getProgress(CommandMap commandMap) throws Exception {
		  
		ModelAndView mv = new ModelAndView("jsonView");

		Map<String, Object> map = commandMap.getMap();
		String j_id = (String)map.get("j_id");
		
//		log.debug("j_id==="+j_id);
		
		Map<String, Object> params = new HashMap();
		
		params = monitorService.getprogress(map);
		
//		log.debug("progress==="+progress);


		mv.addObject("params", params);

		return mv;
	}
	
	
}
