package com.cha.transcoder.demon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FilePermission;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PermissionCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cha.transcoder.common.AlteratConfigLoader;
import com.cha.transcoder.common.AlteratConfig;
import com.cha.transcoder.common.TcUtil;
import com.cha.transcoder.monitor.dao.MonitorDAO;

public class MergeProcessor extends GeneralProcessor implements Runnable, TcConstant {

	private Logger log = Logger.getLogger(MergeProcessor.class);

	private String job_id;
	private String[] src_files;
	private String file_path;
	private String file_type;
	private String file_name;
	private String job_option;
	private String target_directory;

	public MergeProcessor() {
	}

	public void setPara(String j_id, String[] src_files, String file_path, String file_type, String file_name, String job_option,
			String target_directory) {
		this.job_id = j_id;
		this.src_files = src_files;
		this.file_path = file_path;
		this.file_type = file_type;
		this.file_name = file_name;
		this.job_option = job_option;
		this.target_directory = target_directory;
	}

	public void run() {
		try {

			// CMD에서 읽어온 문자열을 저장한다.
			String line = "";

			// 에러가 났을때 메시지를 저장했다가, metadat에 넣어준다.
			String errMsg = "";

			// 진행률을 계산하기위해 개별소재의 duration을 모두 합한 값.
			int t_progress = 0;
			boolean isPcm_Blueray = false;

			try {
				for (int i = 0; i < src_files.length; i++) {
					String file_path = src_files[i];

					FilePermission fp1 = new FilePermission(file_path, "read");
					PermissionCollection pc = fp1.newPermissionCollection();
					pc.add(fp1);

					FilePermission fp2 = new FilePermission(file_path, "write");
					pc.add(fp2);
					FilePermission fp3 = new FilePermission(file_path, "execute");
					pc.add(fp3);
					FilePermission fp4 = new FilePermission(file_path, "readlink");
					pc.add(fp4);

					if (pc.implies(new FilePermission(file_path, "read,write,execute,readlink"))) {
						log.debug("Permission for " + file_path + " is read and write and execute.");
					} else {
						log.debug("No read, write permission for " + file_path);
					}

					File source_file = new File(file_path);
					// 개별 파일의 Duration을 가져온다.
					MediaFileInfo fileInfo = getMetadata(source_file);
					String audioCodec = fileInfo.getAudio_codec();
					if (audioCodec != null && audioCodec.equals("pcm_bluray")) {
						isPcm_Blueray = true;
					}

					String duration = fileInfo.getDuration();
					if (duration != null && !duration.equals("")) {
						t_progress += TcUtil.toSecond(fileInfo.getDuration());
					}

					log.debug("source_file=" + file_path + ", execute=" + source_file.canExecute() + ", read=" + source_file.canRead() + ", write="
							+ source_file.canWrite());
					log.debug("duration=" + fileInfo.getDuration());

					if (!source_file.canRead()) {
						log.debug(source_file + ", can't read.");
						//return;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// To make a list file.
			file_path = file_path.substring(0, file_path.lastIndexOf(File.separatorChar));
			String list_file = file_path + File.separatorChar + "source_files.txt";
			log.debug("file_path=" + list_file);

			BufferedWriter bw = new BufferedWriter(new FileWriter(list_file));
			bw.write("# Do not delete this file.");
			bw.write('\n');

			log.debug("------------ source_files.txt -----------");
			for (int i = 0; i < src_files.length; i++) {
				String row = "file '" + src_files[i] + "'";
				bw.write(row);
				bw.write('\n');
				log.debug(row);
			}
			log.debug("-----------------------------------------");
			bw.close();

			// Duration & Metadata를 Update 한다.
			Map<String, Object> up_params = new HashMap();
			up_params.put("j_id", job_id);
			String duration = TcUtil.toTime(t_progress);
			up_params.put("duration", duration);

			// ffmpeg이 있는 경로
			AlteratConfig altConfig = AlteratConfigLoader.getInstance().getConfig();
			String ffmpeg_path = altConfig.getFfmpeg();

			String strCmd = ffmpeg_path + " -y -f concat -safe 0 -i " + list_file + " -vcodec copy -acodec copy ";
			if (isPcm_Blueray) {
				strCmd = strCmd + "-acodec ac3 ";
			}
			strCmd = strCmd + file_path + File.separatorChar + file_name;
			log.debug("==============================");
			log.debug("muxing=="+strCmd);
			log.debug("==============================");

			// FFmpeg 프로세스를 실행한다.
			List<String> cmd = new ArrayList<String>();
			cmd.add(ffmpeg_path.trim());
			cmd.add("-y");
			cmd.add("-f");
			cmd.add("concat");
			cmd.add("-safe");
			cmd.add("0");
			cmd.add("-i");
			cmd.add(list_file.trim());
			cmd.add("-vcodec");
			cmd.add("copy");
			cmd.add("-acodec");
			cmd.add("copy");
			if (isPcm_Blueray) {
				cmd.add("-acodec");
				cmd.add("ac3");
			}
			cmd.add(file_path + File.separatorChar + file_name);
			
			ProcessBuilder builder = new ProcessBuilder(cmd);

			// Pint all commands
			List list = builder.command();
			for (int i = 0; i < list.size(); i++) {
				log.debug("cmd_" + i + "=" + list.get(i));
			}

			builder.directory(new File(altConfig.getFfmpeg_home()));
			Process process = builder.start();

			// FFmpeg이 실행하는 커멘트에서 진행상태를 읽어와 진행률을 처리한다.
			// OutputStream stdin = process.getOutputStream();
			InputStream stderr = process.getErrorStream();
			// InputStream stdout = process.getInputStream();

			boolean isPrmission = true;
			boolean isError = false;

			int pre_percent = 0;

			MonitorDAO monitorDAO = MonitorDAO.getInstance();

			// clean up if any output in stderr
			BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stderr));
			while ((line = brCleanUp.readLine()) != null) {
				// System.out.println ("[Stderr] " + line);
				log.debug("[Stdout] " + line);

				/*
				 * 머지할대 incorrect timestamps 는 에러가 아님.
				 */
				if (line.indexOf("Error opening filters") != -1) {
					errMsg = "Error opening filters";
					log.error(errMsg);
					isError = true;
					break;
				} else if (line.indexOf("Invalid argument") != -1) {
					errMsg = "Invalid argument";
					log.error(errMsg);
					isError = true;
					break;
				} else if (line.indexOf("Error while decoding stream") != -1) {
					errMsg = "Error while decoding stream";
					log.error(errMsg);
					isError = true;
					break;
				}

				int offset = line.indexOf("size=");
				if (offset != -1) {
					// 몇프로인지 계산한다.
					Map<String, Object> params = new HashMap();
					params.put("j_id", job_id);
					//params.put("job_starttime", TcUtil.getCurrentTime());
					params.put("job_status", "Inprogress");

					int startOffset = line.indexOf("time=");
					int endOffset = line.indexOf("bitrate=");

					if (startOffset == -1) {
						log.info("No timestamps.");
					} else {
						String currentTime = line.substring(startOffset + 5, endOffset);
						currentTime = currentTime.trim();

						int l_progress = TcUtil.toSecond(currentTime);

						int percent = (int) (l_progress * 100 / t_progress);
						if (percent > 100) {
							percent = 100;
						}

						Thread.sleep(WAITING_TIME_FOR_INTERRUPT);

						if (pre_percent != percent) {
							params.put("job_progress", Integer.toString(percent));
							monitorDAO.update("monitor.updateJobInprogress", params);

							log.debug("job_id=" + job_id + ", percent=" + percent + "% (" + l_progress + "/" + t_progress + ", " + currentTime + "/"
									+ duration + ")");

							pre_percent = percent;
						}
					}
				}
			}

			if (isError) {
				// 머지 하다가 에러난 경우
				Map<String, Object> params = new HashMap();
				params.put("j_id", job_id);
				params.put("job_endtime", TcUtil.getCurrentTime());
				params.put("job_status", "Failed");
				params.put("metadata", errMsg);

				Thread.sleep(WAITING_TIME_FOR_INTERRUPT);

				monitorDAO.update("monitor.updateJobUncompleted", params);

				log.debug("job_id=" + job_id + " Failed");
				brCleanUp.close();
			} else {
				// 작업완료 된 경우
				Map<String, Object> params = new HashMap();
				params.put("j_id", job_id);

				File taget = new File(file_path + File.separatorChar + file_name);
				MediaFileInfo fileInfo = getMetadata(taget);
				up_params.put("duration", fileInfo.getDuration());
				up_params.put("metadata", fileInfo.getMetatdata());

				monitorDAO.update("monitor.updateMetadata", up_params);

				params.put("job_endtime", TcUtil.getCurrentTime());
				if (fileInfo.isUnSupportedCodec()) {
					params.put("job_status", "Failed");
				} else {
					params.put("job_status", "Completed");
				}
				params.put("job_progress", "100");

				Thread.sleep(WAITING_TIME_FOR_INTERRUPT);

				monitorDAO.update("monitor.updateJobCompleted", params);

				// 머지된 파일의 사이즈를 DB 에 저장한다.
				File merged_file = new File(file_path + File.separatorChar + file_name);
				long merged_file_size = merged_file.length();

				log.debug("file_path=" + file_path);
				log.debug("file_name=" + file_name);
				log.debug("merged_file_size=" + merged_file_size);

				params.put("file_size", merged_file_size);
				monitorDAO.update("monitor.updateFileSize", params);

				log.debug("job_id=" + job_id + ", file_size=" + merged_file_size + " Completed");
				brCleanUp.close();

				File file = new File(list_file);
				boolean result = file.delete();

				log.debug("list_file=" + list_file + ", deleted=" + result);
			}
		} catch (InterruptedException e) { // Inprogress중 사용자에 의한 Cancel 작업시 Thread를 
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e1) {
			log.error(e1.getMessage());
			e1.printStackTrace();
		}

		// 파일이 다시 머지 될수 있도록 목록에서 빼준다.
		ScheduledJobRunner.countDownProcessor(TYPE_MERGE);

//		ScheduledJobRunner.removeActiveThread(job_id);
	}

}
