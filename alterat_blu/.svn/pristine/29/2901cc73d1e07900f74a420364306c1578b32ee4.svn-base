package com.cha.transcoder.monitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cha.transcoder.monitor.dao.MonitorDAO;

@Service("monitorService")
public class MonitorServiceImpl implements MonitorService {
	Logger log = Logger.getLogger(MonitorServiceImpl.class);

	@Resource(name = "monitorDAO")
	private MonitorDAO monitorDAO;

	public void MonitorServiceImpl() {
	}

	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) throws Exception {
		return monitorDAO.select(map);
	}

	@Override
	public Map<String, Object> selectDetail(Map<String, Object> map) throws Exception {
		return monitorDAO.selectDetail(map);
	}

	@Override
	public int selectCountAll(Map<String, Object> map) throws Exception {
		return monitorDAO.selectCountAll(map);
	}

	@Override
	public String insert(Map<String, Object> map) throws Exception {
		return monitorDAO.insert(map);
	}

	@Override
	public void update(Map<String, Object> map) throws Exception {
		monitorDAO.update(map);
	}

	@Override
	public void updateResetStatus(Map<String, Object> map) throws Exception {
		monitorDAO.updateResetStatus(map);
	}

	@Override
	public void updateCanceledState(Map<String, Object> map) throws Exception {
		monitorDAO.updateCanceledState(map);
	}

	@Override
	public void deleteAll() throws Exception {
		monitorDAO.deleteAll();
	}
	
	@Override
	public Map<String, Object> getprogress(Map<String, Object> map) throws Exception {
		return monitorDAO.getprogress(map);
	}
}
