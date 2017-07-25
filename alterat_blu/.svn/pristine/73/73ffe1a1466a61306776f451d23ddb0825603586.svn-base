package com.cha.transcoder.nps.controller;

import com.cha.transcoder.common.CommandMap;
import com.cha.transcoder.nps.FileUtils;
import com.cha.transcoder.nps.service.RegisterService;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cha.transcoder.common.AlteratConfig;
import com.cha.transcoder.common.AlteratConfigLoader;

@Controller
public class RegisterController {

	Logger log = Logger.getLogger(RegisterController.class);

	/** sampleService */
	@Resource(name = "registerService")
	private RegisterService registerService;

	@Autowired
	FileUtils fileutil;

	/**
	 * 트랜스코더 메인화면 컨트롤러
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/nps/main.do")
	public ModelAndView main(@RequestParam HashMap<String, String> map) throws Exception {
		ModelAndView mv = new ModelAndView("nps/maintiles.tiles");

		return mv;
	}

	/**
	 * 디렉토리 목록을 JQX JSON으로 리턴
	 * 
	 * @param requestMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/nps/directory.do")
	public ModelAndView directory(@RequestParam HashMap<String, String> requestMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		String folder = requestMap.get("folder");
		log.debug("folder=" + folder);

		if (folder == null) {
			AlteratConfig altConfig = AlteratConfigLoader.getInstance().getConfig();
			folder = altConfig.getIn_base_drive();
		}
		log.debug("folder=" + folder);

		HashMap<String, Object> resultMap = fileutil.getDirectory(folder, 1);

		log.debug("resultMap=" + resultMap);
		mv.addObject("result", resultMap);

		return mv;
	}

	/**
	 * 2차 디렉토리 목록을 JQX JSON으로 리턴
	 * 
	 * @param requestMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/nps/subDirectory.do")
	public ModelAndView subDirectory(@RequestParam HashMap<String, String> requestMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		String folder = requestMap.get("folder");
		log.debug("folder=" + folder);

		if (folder == null) {
			AlteratConfig altConfig = AlteratConfigLoader.getInstance().getConfig();
			folder = altConfig.getIn_base_drive();
		}
		log.debug("folder=" + folder);

		List<HashMap> resultMap = fileutil.getSubDirectory(folder, 2);

		log.debug("resultMap=" + resultMap);
		mv.addObject("result", resultMap);

		return mv;
	}

	/**
	 * 파일 목록을 JQX JSON으로 리턴
	 * 
	 * @param requestMap
	 *            folder명
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/nps/file.do")
	public ModelAndView file(@RequestParam HashMap<String, String> requestMap) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		String folder = requestMap.get("folder");

		if (folder == null) {
			AlteratConfig altConfig = AlteratConfigLoader.getInstance().getConfig();
			folder = altConfig.getIn_base_drive();
		}

		List<HashMap> resultMap = fileutil.getFile(folder);

		log.debug("resultMap=" + resultMap);
		mv.addObject("result", resultMap);

		return mv;
	}

	@RequestMapping(value = "/nps/registerNps.do")
	public ModelAndView registerNps(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("nps/register_popup");

		Map<String, Object> map = commandMap.getMap();

		return mv;
	}

	/**
	 * 트랜스코딩 작업을 등록한다.
	 * 
	 * @param requestMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/nps/register.do")
	public ModelAndView register(@RequestParam HashMap<String, String> requestMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		int affected = -1;
		try {
			String json_filedata = requestMap.get("json_filedata").toString();

			log.debug("json_filedata=" + json_filedata);

			JsonFactory jsonfactory = new JsonFactory();
			JsonParser json_filedataParser = jsonfactory.createJsonParser(json_filedata);

			List<Map<String, String>> fileList = new ArrayList<Map<String, String>>(); // 파일 리스트.
			Map<String, String> fileMap = null; // 파일 정보.

			if (log.isDebugEnabled()) {
				log.debug("\n\n\n ************************ json parsing start************************************************");
			}

			// 파일 메터데이터 파싱.
			while (json_filedataParser.nextToken() != JsonToken.END_OBJECT) {
				String token = json_filedataParser.getCurrentName();
				if (token != null) {
					if (token.equals("file")) {
						while (json_filedataParser.nextToken() != JsonToken.END_ARRAY) {
							String sub_token = json_filedataParser.getCurrentName();
							if (sub_token != null) {
								if (sub_token == "fileid") {                                    //원본파일 경로
									fileMap = new HashMap<String, String>();
									json_filedataParser.nextToken();
									fileMap.put(sub_token, json_filedataParser.getText());
								} else if (sub_token == "filename") {                            //원본파일 이름
									json_filedataParser.nextToken();
									fileMap.put(sub_token, json_filedataParser.getText());
								} else if (sub_token == "worker") {                               //변환할 확장자
									json_filedataParser.nextToken();
									fileMap.put(sub_token, json_filedataParser.getText());
								} else if (sub_token == "bitrate") {                               //변환할 확장자
									json_filedataParser.nextToken();
									fileMap.put(sub_token, json_filedataParser.getText());
								} else if (sub_token == "p_id") {                                //선택한 프로파일 아이디
									json_filedataParser.nextToken();
									fileMap.put(sub_token, json_filedataParser.getText());
									fileList.add(fileMap);
								}
								log.debug("metaMap[" + fileMap + "]");
							}
						}
					}
				}
			}

			if (log.isDebugEnabled()) {
				log.debug("fileList=" + fileList);
				log.debug("\n************************ json parsing end************************************************\n\n");
			}

			json_filedataParser.close();

			// 트랜스코딩 작업을 실제 등록한다.
			affected = registerService.registerTranscoding(fileList);

			log.debug("affected=" + affected);

		} catch (JsonGenerationException jge) {
			log.error(jge.getMessage());
			jge.printStackTrace();
		} catch (JsonMappingException je) {
			log.error(je.getMessage());
			je.printStackTrace();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		mv.addObject("result", affected);

		return mv;
	}

	/**
	 * 머지 작업을 등록한다.
	 * 
	 * @param requestMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/nps/muxingRegist.do")
	public ModelAndView muxingRegist(@RequestParam HashMap<String, String> requestMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		int regist_result = -1;
		try {
			String json_filedata = requestMap.get("json_filedata").toString();
			String ftpYn = requestMap.get("ftpYn").toString();
			log.debug("json_filedata'" + json_filedata + "'");
			log.debug("ftpYn===" + ftpYn );

			JsonFactory jsonfactory = new JsonFactory();
			JsonParser json_filedataParser = jsonfactory.createJsonParser(json_filedata);

			List<Map<String, String>> fileList = new ArrayList<Map<String, String>>(); // NPS 등록 파일 리스트.
			Map<String, String> fileMap = null; // NPS 등록 파일 정보.

			if (log.isDebugEnabled()) {
				log.debug("\n\n\n ************************ json parsing start************************************************");
			}

			// 파일 메터데이터 파싱.
			while (json_filedataParser.nextToken() != JsonToken.END_ARRAY) {
				String sub_token = json_filedataParser.getCurrentName();
				log.debug("sub_token[" + sub_token + "]");
				if (sub_token != null) {
					if (sub_token == "ordernum") {
						fileMap = new HashMap<String, String>();
						json_filedataParser.nextToken();
						fileMap.put(sub_token, json_filedataParser.getText());
					} else if (sub_token == "fileid") {
						json_filedataParser.nextToken();
						fileMap.put(sub_token, json_filedataParser.getText());
						fileList.add(fileMap);
					}
//					log.debug("metaMap[" + fileMap + "]");
				}
			}
			log.debug("fileList[" + fileList + "]");

			// 트랜스코딩 작업을 실제 등록한다.
			regist_result = registerService.registerMuxing(fileList,ftpYn);
			regist_result = 1;
		} catch (JsonGenerationException jge) {
			log.error(jge.getMessage());
			jge.printStackTrace();
		} catch (JsonMappingException je) {
			log.error(je.getMessage());
			je.printStackTrace();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		mv.addObject("result", regist_result);

		return mv;
	}
}
