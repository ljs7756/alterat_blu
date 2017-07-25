package com.cha.transcoder.profile.dao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.cha.transcoder.common.AbstractDAO;

@Repository("profileDAO")
public class ProfileDAO extends AbstractDAO {

	protected Log log = LogFactory.getLog(ProfileDAO.class);
	private static ProfileDAO instance;

	public ProfileDAO() {
		instance = this;
	}

	public static ProfileDAO getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectProfile(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) selectList("profile.selectProfile", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> profileList() throws Exception {
		return (List<Map<String, Object>>) selectList("profile.profileList");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> profileChannel() throws Exception {
		return (List<Map<String, Object>>) selectList("profile.profileChannel");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> selectProfilePooq() throws Exception {
		return (List<Map<String, String>>) selectList("profile.selectProfilePooq");
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectProfileDetail(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectDetail("profile.selectProfileDetail", map);
	}

	@SuppressWarnings("unchecked")
	public int selectProfileCountAll(Map<String, Object> map) throws Exception {
		return ((Integer) selectOne("profile.selectProfileCountAll", map)).intValue();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectProfileByWorker(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectOne("profile.selectProfileByWorker", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectProfileByWorker_2(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectOne("profile.selectProfileByWorker_2", map);
	}

	public void insertProfile(Map<String, Object> map) throws Exception {
		if (map.get("enable_yn") == null) {
			map.put("enable_yn", "N");
		} else {
			if (map.get("enable_yn").equals("on")) {
				map.put("enable_yn", "Y");
			} else {
				map.put("enable_yn", "N");
			}
		}

		if (map.get("video_enable").equals("on")) {
			map.put("video_enable", "Y");
		} else {
			map.put("video_enable", "N");
		}

		if (map.get("audio_enable").equals("on")) {
			map.put("audio_enable", "Y");
		} else {
			map.put("audio_enable", "N");
		}

		String newPID = null;
		String maxPID = (String) selectOne("profile.selectPID");
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

		map.put("p_id", "P" + newPID); 

		insert("profile.insertProfile", map);
	}

	public void updateProfile(Map<String, Object> map) throws Exception {
		if (map.get("enable_yn") == null) {
			map.put("enable_yn", "N");
		} else {
			if (map.get("enable_yn").equals("on")) {
				map.put("enable_yn", "Y");
			} else {
				map.put("enable_yn", "N");
			}
		}

		update("profile.updateProfile", map);
	}

	public void deleteProfile(Map<String, Object> map) throws Exception {
		insert("profile.deleteProfile", map);
	}

}
