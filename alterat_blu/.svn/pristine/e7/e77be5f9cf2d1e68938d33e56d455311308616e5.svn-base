package com.cha.transcoder.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cha.transcoder.common.AlteratConfig;
import com.cha.transcoder.common.CommandMap;
import com.cha.transcoder.common.TcUtil;
import com.cha.transcoder.monitor.dao.MonitorDAO;
import com.cha.transcoder.nps.SoapManager;

@Controller
public class TestController {

	Logger log = Logger.getLogger(TestController.class);

	@RequestMapping(value = "/testDatepicker.do")
	public ModelAndView openMonitorList(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/test/testDatepicker");

		return mv;
	}

	@RequestMapping(value = "/testDatepicker2.do")
	public ModelAndView openMonitorList2(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/test/testDatepicker2");

		return mv;
	}

	// ---------------------------------------------------------------------------------------------
	// 많은 데이터를 Insert 할때를 위해 테스트 함
	@RequestMapping(value = "/testInsertBatch.do")
	public void testInsertBatch(HttpServletResponse response) throws Exception {
		MonitorDAO dao = MonitorDAO.getInstance();

		dao.beginBatch();

		for (int i = 0; i < 100000; i++) {
			Map<String, Object> params = new HashMap();
			params.put("j_id", i);
			params.put("j_type", "Transcoding");

			dao.insertBatch("monitor.testInsertBatch", params);

			log.debug("insert=" + i);
		}

		dao.commit();

		try {
			response.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------------------------------
	@RequestMapping(value = "/testAjax.do")
	public ModelAndView openAjaxPage(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/test/ajax");

		return mv;
	}

	@RequestMapping(value = "/testServerResponse.do")
	public void requestTestHtml(HttpServletResponse response) throws Exception {
		try {
			response.getWriter().print("Server Response");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------------------------------
	@RequestMapping(value = "/testMerge.do")
	public ModelAndView testMerge(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("test/testMerge");

		return mv;
	}

	@RequestMapping(value = "/testMp4Merge.do")
	public ModelAndView testMp4Merge(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("test/testMp4Merge");

		return mv;
	}

	@RequestMapping(value = "/testMergeOthers.do")
	public ModelAndView testMergeOthers(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("test/testMergeOthers");

		return mv;
	}
	// ---------------------------------------------------------------------------------------------

	@RequestMapping(value = "/testToTime.do")
	public void testToTime(HttpServletResponse response) throws Exception {
		try {
			String strTime = "02:10:40";
			log.debug("strTime=" + strTime);
			int sec = TcUtil.toSecond(strTime);
			log.debug("sec=" + sec);
			String time = TcUtil.toTime(sec);
			log.debug("time=" + time);

			log.debug("------------------------------------");

			strTime = "00:22:33";
			log.debug("strTime=" + strTime);
			sec = TcUtil.toSecond(strTime);
			log.debug("sec=" + sec);
			time = TcUtil.toTime(sec);
			log.debug("time=" + time);

			response.getWriter().print("Server Response");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------------------------------

	@RequestMapping(value = "/testJson.do")
	public void testJson(HttpServletResponse response) throws Exception {
		//Gson gson = new Gson();

		String json = "{\"success\":\"true\",\"status\":0,\"message\":\"OK\",\"task_id\":\"554535\",\"content_id\":\"145133\",\"parent_id\":\"145133\",\"interface_id\":\"130612\",\"task_list_info\":{\"554535\":{\"task_id\":\"554535\",\"type\":\"69\",\"source\":\"MOVI0000.avi\",\"target\":\"PG2120146D\\/NPS_INGEST\\/clip\\/P2015120908933\\/P2015120908933_1.avi\",\"channel\":\"ingest_file_group_GH\",\"content_id\":\"145134\",\"src_media_id\":\"\",\"tgt_media_id\":\"405681\",\"src_storage_info\":{\"type\":\"SAN\",\"path\":\"Y:\\/CMS\",\"login_id\":null,\"login_pw\":null},\"tgt_storage_info\":{\"type\":\"SAN\",\"path\":\"Y:\\/CMS\",\"login_id\":null,\"login_pw\":null}}}}";

		log.debug("json=" + json);

		int begin_index = json.indexOf("target");
		int end_index = json.indexOf("channel");

		String dest_file = json.substring(begin_index, end_index);

		dest_file = json.substring(begin_index + 9, end_index - 3);
		dest_file = dest_file.replace('\\', '/');
		dest_file = dest_file.replaceAll("//", "/");
		dest_file = dest_file.replace('/', File.separatorChar);

		log.debug("dest_file=" + dest_file);

	}

	// ---------------------------------------------------------------------------------------------
	// 등록테스트
	@RequestMapping(value = "/testRegister.do")
	public ModelAndView testRegister(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("test/testRegister");

		return mv;
	}

	@RequestMapping(value = "/testRegisterComplete.do")
	public void testRegisterComplete(CommandMap commandMap, HttpServletResponse response) throws Exception {
		Map<String, Object> map = commandMap.getMap();

		String txtTaskID = (String) map.get("txtTaskID");
		String txtTypeCode = (String) map.get("txtTypeCode");

		log.debug("txtTaskID=" + txtTaskID);
		log.debug("txtTypeCode=" + txtTypeCode);

		SoapManager sopMgr = SoapManager.getInstance();

		String msg = sopMgr.requestTaskComplete(txtTaskID, txtTypeCode);

		try {
			response.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------------------------------
	// 등록테스트
	@RequestMapping(value = "/testReplaceFirst.do")
	public ModelAndView test(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("test/testFinished");
		/*
		 * String fpath = "D:/temp3/in/123/456/789";
		 * 
		 * String source_path = "D:/temp3/in"; String target_path =
		 * "D:/temp3/out";
		 * 
		 * String target_directory = fpath.replaceFirst(source_path,
		 * target_path);
		 * 
		 * log.debug("target_directory=" + target_directory);
		 */

		String json_metadata = "\"4778265\":\"100\"";

		json_metadata = json_metadata.replaceFirst("\"4778265\":\"default\\)\\[001\",", "\"4778265\":\"010\",");
		json_metadata = json_metadata.replaceFirst("\"4778265\":\"\\p{Digit}{3}?\"", "\"4778265\":\"010\"");

		log.debug("json_metadata(1)=" + json_metadata);

		json_metadata = "\"4778265\":\"default)[001\",\"123\":\"123\",";
		json_metadata = json_metadata.replaceFirst("\"4778265\":\"default\\)\\[001\",", "\"4778265\":\"010\",");
		json_metadata = json_metadata.replaceFirst("\"4778265\":\"\\p{Digit}{3}?\"", "\"4778265\":\"010\"");

		log.debug("json_metadata(2)=" + json_metadata);

		return mv;
	}

	// ---------------------------------------------------------------------------------------------
	// 설정 테스트
	@RequestMapping(value = "/alteratConfig.do")
	public ModelAndView testConfig(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("test/testFinished");

		ApplicationContext context = new ClassPathXmlApplicationContext("/config/spring/context-alterat.xml");

		AlteratConfig obj = (AlteratConfig) context.getBean("alteratConfig");
		log.debug("----------------------------");
		log.debug(obj);

		return mv;
	}

	// ---------------------------------------------------------------------------------------------
	// 드라이브의 용량 읽어오기
	@RequestMapping(value = "/testFreeSpace.do")
	public ModelAndView testFreeSpace(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("test/testFinished");

		File file = new File("c:");
		long totalSpace = file.getTotalSpace(); //total disk space in bytes.
		long usableSpace = file.getUsableSpace(); ///unallocated / free disk space in bytes.
		long freeSpace = file.getFreeSpace(); //unallocated / free disk space in bytes.

		log.debug(" === Partition Detail ===");

		log.debug(" === bytes ===");
		log.debug("Total size : " + totalSpace + " bytes");
		log.debug("Space free : " + usableSpace + " bytes");
		log.debug("Space free : " + freeSpace + " bytes");

		log.debug(" === mega bytes ===");
		log.debug("Total size : " + totalSpace / 1024 / 1024 + " mb");
		log.debug("Space free : " + usableSpace / 1024 / 1024 + " mb");
		log.debug("Space free : " + freeSpace / 1024 / 1024 + " mb");

		return mv;
	}

	// ---------------------------------------------------------------------------------------------
	// jqxProgressBar 테스트
	@RequestMapping(value = "/testJqxProgressBar.do")
	public ModelAndView testJqxProgressBar(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("test/testJqxProgressBar");
		return mv;
	}
}
