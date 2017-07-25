package com.cha.transcoder.sending.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SendingService {

	public List<Map<String, Object>> select(Map<String, Object> map) throws Exception;

	public int selectCountAll(Map<String, Object> map) throws Exception;

	public Map<String, Object> getprogress(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> ftpInfoList() throws Exception;
	
	public int ftpSending(List<Map<String, String>> fileList);
	
	public void updateCanceledState(Map<String, Object> map) throws Exception;
	
	
}
