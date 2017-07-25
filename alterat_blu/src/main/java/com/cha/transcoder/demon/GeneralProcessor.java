package com.cha.transcoder.demon;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.cha.transcoder.common.AlteratConfigLoader;
import com.cha.transcoder.common.AlteratConfig;

public class GeneralProcessor {

	private Logger log = Logger.getLogger(GeneralProcessor.class);

	protected boolean isPermissionDenied = false;
	protected boolean isSlomo = false;
	protected boolean isAudioProblem = false;

	// 디렉토리를 만든다.
	protected void makeDirectory(String path) {
		Stack stack = new Stack();

		String temp_path = path;

		AlteratConfig altConfig = AlteratConfigLoader.getInstance().getConfig();
		if (altConfig.isWindow()) {
			temp_path = temp_path.replace("/", "\\");
		} else {
			temp_path = temp_path.replace("\\", "/");
		}

		int idx = 0;
		stack.push(temp_path);
		while (idx != -1) {
			idx = temp_path.lastIndexOf(File.separator);
			if (idx != -1) {
				temp_path = temp_path.substring(0, idx);
				stack.push(temp_path);
			}
		}

		while (!stack.isEmpty()) {
			String str_path = (String) stack.pop();
			// log.debug("str_path=" + str_path);

			File entry_file = new File(str_path);
			if (!entry_file.exists()) {
				boolean result = entry_file.mkdir();
				log.debug("entry_file=" + str_path + ", result=" + result);
			}
		}
	}

	// 영상의 길이를 가져온다.
	protected MediaFileInfo getMetadata(File f) {
		MediaFileInfo info = new MediaFileInfo();
		try {
			AlteratConfig altConfig = AlteratConfigLoader.getInstance().getConfig();
			String ffprobe_path = altConfig.getFfprobe();
			log.debug("==============================");
			log.debug(ffprobe_path + " " + f.getPath());
			log.debug("==============================");

			// FFprobe 처리부분, trim()을 하지 않으면 실행안됨
			List<String> cmd = new ArrayList<String>();
			cmd.add(ffprobe_path.trim());
			cmd.add(f.getPath().trim());

			ProcessBuilder builder = new ProcessBuilder(cmd);
			builder.directory(new File(altConfig.getFfmpeg_home()));
			Process process = builder.start();

			// 프로세스 실행후 실행결과를 받기 위한 스트림, 령령어 실행결과임
			// OutputStream stdin = process.getOutputStream();
			InputStream stderr = process.getErrorStream();
			// InputStream stdout = process.getInputStream();

			String line = "";

			BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stderr));

			String duration = null;
			StringBuffer metatdata = new StringBuffer();

			while ((line = brCleanUp.readLine()) != null) {
				log.debug("[Stdout]" + line);

				int offsetX = line.indexOf("Duration:");
				int offsetY = line.indexOf("start:");
				if (offsetX != -1 && offsetX != -1) {
					duration = line.substring(offsetX + 9, offsetY);
					duration = duration.replace(",", "");
					duration = duration.trim();

					//log.debug("duration=" + duration);
				}

				if (line.indexOf("Permission denied") != -1) {
					isPermissionDenied = true;
				}

				// iPhone 6의 슬로모 영상
				if (line.indexOf("239.84 fps") != -1) {
					//log.debug("isSlomo=true, 239.84 fps");
					isSlomo = true;
				}

				// iPhone 6의 슬로모 영상
				if (line.indexOf("sample size") != -1 && line.indexOf("!= block align") != -1) {
					//log.debug("Audio Problem");
					isAudioProblem = true;
				}

				// 지원하지 않는 코덱일 경우
				if (line.indexOf("Unsupported codec") != -1) {
					// Input stream 2가 데이터 스트림일때 Unsupported codec 이 발생되나 머지는 됨.
					// 따라서, 'input stream 2' 메시지 발생이 아닐경우만 처리함.
					if (line.indexOf("for input stream 2") != -1) {
						info.setUnSupportedCodec(false);
					}
				}

				int offsetVCodec = line.indexOf("Video:");
				if (offsetVCodec != -1) {
					int offsetVCodecY = line.indexOf(" ", offsetVCodec + 7);
					String vCodec = line.substring(offsetVCodec + 7, offsetVCodecY);
					info.setVideo_codec(vCodec);
					//log.debug("video=" + vCodec);
				}

				int offsetACodec = line.indexOf("Audio:");
				if (offsetACodec != -1) {
					int offsetACodecY = line.indexOf(" ", offsetACodec + 7);
					String aCodec = line.substring(offsetACodec + 7, offsetACodecY);
					aCodec = aCodec.replace(',', ' ');
					aCodec = aCodec.trim();

					info.setAudio_codec(aCodec);
					//log.debug("Check, audio=" + aCodec);
				}

				int offsetEncoder = line.indexOf("encoder");
				if (offsetEncoder != -1) {
					String encoder = line.substring(line.indexOf(":") + 1);
					info.setEncoder(encoder.trim());
					//log.debug("encoder=" + encoder);
				}

				metatdata.append(line).append("\n");
			}
			brCleanUp.close();

			//log.debug("isPermissionDenied=" + isPermissionDenied);

			// 메타데이터를 저장한다.
			info.setDuration(duration);
			String temp = metatdata.toString();

			int idx = temp.indexOf("Input");
			if (idx != -1) {
				temp = temp.substring(idx);
			}
			info.setMetatdata(temp);

			log.debug(info.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}
}
