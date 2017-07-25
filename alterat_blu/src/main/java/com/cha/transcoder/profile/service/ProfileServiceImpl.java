package com.cha.transcoder.profile.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cha.transcoder.profile.dao.ProfileDAO;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {
	Logger log = Logger.getLogger(ProfileServiceImpl.class);

	@Resource(name = "profileDAO")
	private ProfileDAO profileDAO;

	@Override
	public List<Map<String, Object>> selectProfile(Map<String, Object> map) throws Exception {
		return profileDAO.selectProfile(map);
	}
	
	@Override
	public List<Map<String, Object>> profileList() throws Exception {
		return profileDAO.profileList();
	}
	
	@Override
	public List<Map<String, Object>> profileChannel() throws Exception {
		return profileDAO.profileChannel();
	}
	
	@Override
	public Map<String, Object> selectProfileDetail(Map<String, Object> map) throws Exception {
		return profileDAO.selectProfileDetail(map);
	}
	
	@Override
	public int selectProfileCountAll(Map<String, Object> map) throws Exception {
		return profileDAO.selectProfileCountAll(map);
	}

	@Override
	public void insertProfile(Map<String, Object> map) throws Exception {
		profileDAO.insertProfile(map);
	}
 
	@Override
	public void updateProfile(Map<String, Object> map) throws Exception {
		profileDAO.updateProfile(map);
	}
 
	@Override
	public void deleteProfile(Map<String, Object> map) throws Exception {
		profileDAO.deleteProfile(map);
	}

}
