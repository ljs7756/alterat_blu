package com.cha.transcoder.watchfolder.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cha.transcoder.watchfolder.dao.WatchfolderDAO;

@Service("watchfolderService")
public class WatchfolderServiceImpl implements WatchfolderService {
	Logger log = Logger.getLogger(WatchfolderServiceImpl.class);

	@Resource(name = "watchfolderDAO")
	private WatchfolderDAO watchfolderDAO;

	@Override
	public List<Map<String, Object>> selectWatchfolder(Map<String, Object> map) throws Exception {
		return watchfolderDAO.selectWatchfolder(map);
	}

	@Override
	public Map<String, Object> selectWatchfolderDetail(Map<String, Object> map) throws Exception {
		return watchfolderDAO.selectWatchfolderDetail(map);
	}

	@Override
	public int selectWatchfolderCountAll(Map<String, Object> map) throws Exception {
		return watchfolderDAO.selectWatchfolderCountAll(map);
	}

	@Override
	public void insertWatchfolder(Map<String, Object> map) throws Exception {
		watchfolderDAO.insertWatchfolder(map);
	}

	@Override
	public void updateWatchfolder(Map<String, Object> map) throws Exception {
		watchfolderDAO.updateWatchfolder(map);
	}

	@Override
	public void deleteWatchfolder(Map<String, Object> map) throws Exception {
		watchfolderDAO.deleteWatchfolder(map);
	}

}
