package com.cha.transcoder.monitor.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cha.transcoder.monitor.service.MonitorService;

@Controller
public class MergeController {

	Logger log = Logger.getLogger(MergeController.class);
	private final static int PAGE_LIMIT = 10;

	@Resource(name = "monitorService")
	private MonitorService monitorService;

	@RequestMapping(value = "/insertMergeJob.do")
	public void manualJobs(@RequestParam HashMap<String, String> requestMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			ArrayList arr = new ArrayList();

			log.debug("src_01=" + requestMap.get("src_01"));
			log.debug("src_02=" + requestMap.get("src_02"));
			log.debug("src_03=" + requestMap.get("src_03"));

			arr.add(requestMap.get("src_01"));
			arr.add(requestMap.get("src_02"));
			arr.add(requestMap.get("src_03"));

			String[] source = (String[]) arr.toArray(new String[0]);
			String destDir = requestMap.get("dst_01");

			insertJob(source, destDir);

			try {
				response.getWriter().print("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			response.getWriter().print("failure");
		}
	}

	private void insertJob(String[] source_files, String destDir) {

		try {
			Map<String, Object> params = new HashMap();

			String file_type = null;
			String src_path = null;
			
			StringBuffer bufSrcList = new StringBuffer();
			for (int i = 0; i < source_files.length; i++) {
				if (i == 0) {
					int idx = source_files[i].lastIndexOf("\\");
					if (idx == -1) {
						idx = source_files[i].lastIndexOf("/");
					}
					file_type = source_files[i].substring(source_files[i].lastIndexOf(".") + 1);
					
					String path_name = source_files[i];
					//log.debug("Check, 00 path_name =" + path_name );					
					src_path = path_name.substring(0, path_name.lastIndexOf(File.separatorChar));
					
					//log.debug("Check, 00 src_path=" + src_path);
				} else {
					bufSrcList.append("|"); // 구분자
				}
				bufSrcList.append(source_files[i].substring(source_files[i].indexOf(File.separatorChar)));
				
				//log.debug("Check, bufSrcList=" + bufSrcList.toString());
			}

			params.put("j_type", "Merge");
			params.put("src_path", src_path);
			params.put("src_list", bufSrcList.toString());
			params.put("file_path", destDir); // UI 에서 입력받은 Destination 폴더
												// (Watch 폴더의 In)
			params.put("file_type", file_type.toLowerCase());
			// params.put("file_name", file_name); // file_name은 MonitorDAO 에서

			// source_files.txt 파일은 src_list 에 의해 ffmpeg 실행시 만드는 파일임
			params.put("job_option", "-f concat -i source_files.txt -vcodec copy -acodec copy");
			params.put("job_status", "Hold On");

			String j_id = monitorService.insert(params);

			log.debug("j_id=" + j_id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
