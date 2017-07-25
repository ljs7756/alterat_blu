package com.cha.transcoder.watchfolder.control;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cha.transcoder.common.CommandMap;
import com.cha.transcoder.watchfolder.service.WatchfolderService;

@Controller
public class WatchFolderController {

	Logger log = Logger.getLogger(WatchFolderController.class);
	private final static int PAGE_LIMIT = 10;

	@Resource(name = "watchfolderService")
	private WatchfolderService watchfolderService;

	@RequestMapping(value = "/selectWatchfolder.do")
	public ModelAndView openWatchfolderList(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/watchfolder_list");

		Map<String, Object> map = commandMap.getMap();

		int offset = 0;
		int current_page = 0;

		if (map.get("current_page") == null) {
			offset = 0;
			current_page = 0;
		} else {
			current_page = Integer.parseInt((String) map.get("current_page"));
			offset = current_page * PAGE_LIMIT;
		}

		map.put("off_set", Integer.toString(offset));
		map.put("page_count", Integer.toString(PAGE_LIMIT));

		List<Map<String, Object>> list = watchfolderService.selectWatchfolder(commandMap.getMap());
		int count_all = watchfolderService.selectWatchfolderCountAll(commandMap.getMap());

		mv.addObject("list", list);
		mv.addObject("current_page", current_page);
		mv.addObject("tot_page", (count_all - 1) / PAGE_LIMIT);

		return mv;
	}

	@RequestMapping(value = "/watchfolder_add.do")
	public ModelAndView openWatchfolderList2(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/watchfolder_add");
		return mv;
	}

	@RequestMapping(value = "/insertWatchfolder.do")
	public ModelAndView openInsertWatchfolder(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/watchfolder_list");

		Map<String, Object> map = commandMap.getMap();

		boolean noError = true;
		String msg_error = null;

		// 소스 디렉토리
		File src_dir = new File((String) map.get("source_directory"));
		if (src_dir.exists()) {
			log.debug("src_dir.getAbsolutePath()=" + src_dir.getAbsolutePath());
			log.debug("'" + src_dir.getPath() + "' existed.");
		} else {
			try {
				if (src_dir.mkdirs()) {
					log.debug("'" + src_dir.getPath() + "' made.");
				} else {
					noError = false;
				}
			} catch (Exception e) {
				log.error(e);
				noError = false;
				msg_error = e.getMessage();
			}

		}

		// 타겟 디렉토리
		File tgt_dir = new File((String) map.get("target_directory"));
		if (tgt_dir.exists()) {
			log.debug("tgt_dir.getAbsolutePath()=" + tgt_dir.getAbsolutePath());
			log.debug("'" + tgt_dir.getPath() + "' existed.");
		} else {
			try {
				if (tgt_dir.mkdirs()) {
					log.debug("'" + tgt_dir.getPath() + "' made.");
				} else {
					noError = false;
				}
			} catch (Exception e) {
				log.error(e);
				noError = false;
				msg_error = e.getMessage();
			}

		}

		watchfolderService.insertWatchfolder(commandMap.getMap());

		map.put("off_set", "0");
		map.put("page_count", Integer.toString(PAGE_LIMIT));

		if (noError) {
			// 에러가 없으면 정상처리
			List<Map<String, Object>> list = watchfolderService.selectWatchfolder(commandMap.getMap());
			int count_all = watchfolderService.selectWatchfolderCountAll(commandMap.getMap());

			mv.addObject("list", list);
			mv.addObject("current_page", "0");
			mv.addObject("tot_page", (count_all - 1) / PAGE_LIMIT);
		} else {
			// 에러가 발생하면 에러 페이지로 보냄
			mv.setViewName("/error.do");
			mv.addObject("return_page", "/watchfolder_add.do");
			mv.addObject("msg_error", msg_error);
		}

		return mv;
	}

	@RequestMapping(value = "/selectWatchfolderDetail.do")
	public ModelAndView openSelectTheWatchfolder(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/watchfolder_modify");

		Map<String, Object> map = watchfolderService.selectWatchfolderDetail(commandMap.getMap());
		mv.addObject("map", map);

		return mv;
	}

	@RequestMapping(value = "/updateWatchfolder.do")
	public ModelAndView openUpdateWatchfolder(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/watchfolder_list");

		watchfolderService.updateWatchfolder(commandMap.getMap());

		Map<String, Object> map = commandMap.getMap();
		map.put("off_set", "0");
		map.put("page_count", Integer.toString(PAGE_LIMIT));

		List<Map<String, Object>> list = watchfolderService.selectWatchfolder(commandMap.getMap());
		int count_all = watchfolderService.selectWatchfolderCountAll(commandMap.getMap());

		mv.addObject("list", list);
		mv.addObject("current_page", "0");
		mv.addObject("tot_page", (count_all - 1) / PAGE_LIMIT);

		return mv;
	}

	@RequestMapping(value = "/deleteWatchfolder.do")
	public ModelAndView openDeleteWatchfolder(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/watchfolder_list");

		watchfolderService.deleteWatchfolder(commandMap.getMap());

		Map<String, Object> map = commandMap.getMap();
		map.put("off_set", "0");
		map.put("page_count", Integer.toString(PAGE_LIMIT));

		List<Map<String, Object>> list = watchfolderService.selectWatchfolder(commandMap.getMap());
		int count_all = watchfolderService.selectWatchfolderCountAll(commandMap.getMap());

		mv.addObject("list", list);
		mv.addObject("current_page", "0");
		mv.addObject("tot_page", (count_all - 1) / PAGE_LIMIT);

		return mv;
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

}
