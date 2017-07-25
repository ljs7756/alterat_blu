package com.cha.transcoder.profile.control;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cha.transcoder.common.CommandMap;
import com.cha.transcoder.profile.service.ProfileService;

@Controller
public class ProfileController {

	Logger log = Logger.getLogger(ProfileController.class);
	private final static int PAGE_LIMIT = 10;

	@Resource(name = "profileService")
	private ProfileService profileService;

	@RequestMapping(value = "/selectProfile.do")
	public ModelAndView openProfileList(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/tc_profile_list");

		Map<String, Object> map = commandMap.getMap();

		int offset = 0;
		int current_page = 0;

		if (map.get("current_page") != null) {
			current_page = Integer.parseInt((String) map.get("current_page"));
			offset = current_page * PAGE_LIMIT;
		}

		String common = "pooq";//default
		if (map.get("common") != null) {
			common = (String) map.get("common");
		}

		map.put("off_set", Integer.toString(offset));
		map.put("page_count", Integer.toString(PAGE_LIMIT));
		map.put("common", common);

		// DB에서 프로파일 목록을 검색한다.
		List<Map<String, Object>> channelList = profileService.profileChannel();
		List<Map<String, Object>> list = profileService.selectProfile(commandMap.getMap());
		int count_all = profileService.selectProfileCountAll(commandMap.getMap());

		mv.addObject("channelList", channelList);
		mv.addObject("list", list);
		mv.addObject("current_page", current_page);
		mv.addObject("tot_page", (count_all - 1) / PAGE_LIMIT);
		mv.addObject("common", common);

		return mv;
	}
	
	@RequestMapping(value = "/profileList.do")
	public ModelAndView profileDetail() throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		// DB에서 프로파일 목록을 검색한다.
		List<Map<String, Object>> list = profileService.profileList();
		
		mv.addObject("list", list);

		return mv;
	}
	
	@RequestMapping(value = "/profileChannel.do")
	public ModelAndView profileChannel() throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");

		// DB에서 프로파일 목록을 검색한다.
		List<Map<String, Object>> list = profileService.profileChannel();
		
		mv.addObject("list", list);

		return mv;
	}

	@RequestMapping(value = "/tc_profile_add.do")
	public ModelAndView openProfileList2(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/tc_profile_add");

		Map<String, Object> map = commandMap.getMap();
		String common = "pooq";
		if (map.get("common") != null) {
			common = (String) map.get("common");
		}
		
		List<Map<String, Object>> channelList = profileService.profileChannel();
		
		mv.addObject("channelList", channelList);
		mv.addObject("common", common);
		
		return mv;
	}

	@RequestMapping(value = "/insertProfile.do")
	public ModelAndView openInsertProfile(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/tc_profile_list");

		profileService.insertProfile(commandMap.getMap());

		Map<String, Object> map = commandMap.getMap();
		
		String common = "pooq";
		if (map.get("common") != null) {
			common = (String) map.get("common");
		}
		
		map.put("off_set", "0");
		map.put("page_count", Integer.toString(PAGE_LIMIT));

		List<Map<String, Object>> channelList = profileService.profileChannel();
		List<Map<String, Object>> list = profileService.selectProfile(commandMap.getMap());
		int count_all = profileService.selectProfileCountAll(commandMap.getMap());

		mv.addObject("channelList", channelList);
		mv.addObject("list", list);
		mv.addObject("current_page", "0");
		mv.addObject("tot_page", (count_all - 1) / PAGE_LIMIT);
		mv.addObject("common", common);

		return mv;
	}

	@RequestMapping(value = "/selectTheProfile.do")
	public ModelAndView openSelectTheProfile(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/tc_profile_modify");

		Map<String, Object> map = commandMap.getMap();
		String common = "pooq";
		if (map.get("common") != null) {
			common = (String) map.get("common");
		}
		
		List<Map<String, Object>> channelList = profileService.profileChannel();
		Map<String, Object> theProfile = profileService.selectProfileDetail(commandMap.getMap());
		
		mv.addObject("channelList", channelList);
		mv.addObject("map", theProfile);
		mv.addObject("common", common);

		return mv;
	}

	@RequestMapping(value = "/updateProfile.do")
	public ModelAndView openUpdateProfile(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/tc_profile_list");

		profileService.updateProfile(commandMap.getMap());

		Map<String, Object> map = commandMap.getMap();
		map.put("off_set", "0");
		map.put("page_count", Integer.toString(PAGE_LIMIT));
		String common = "pooq";
		if (map.get("common") != null) {
			common = (String) map.get("common");
		}

		List<Map<String, Object>> channelList = profileService.profileChannel();
		List<Map<String, Object>> list = profileService.selectProfile(commandMap.getMap());
		int count_all = profileService.selectProfileCountAll(commandMap.getMap());

		mv.addObject("channelList", channelList);
		mv.addObject("list", list);
		mv.addObject("common", common);
		mv.addObject("current_page", "0");
		mv.addObject("tot_page", (count_all - 1) / PAGE_LIMIT);

		return mv;
	}

	@RequestMapping(value = "/deleteProfile.do")
	public ModelAndView openDeleteProfile(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/tc_profile_list");

		profileService.deleteProfile(commandMap.getMap());

		Map<String, Object> map = commandMap.getMap();
		map.put("off_set", "0");
		map.put("page_count", Integer.toString(PAGE_LIMIT));

		List<Map<String, Object>> channelList = profileService.profileChannel();
		List<Map<String, Object>> list = profileService.selectProfile(commandMap.getMap());
		int count_all = profileService.selectProfileCountAll(commandMap.getMap());

		mv.addObject("channelList", channelList);
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
