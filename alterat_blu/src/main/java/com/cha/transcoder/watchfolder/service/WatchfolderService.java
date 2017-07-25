package com.cha.transcoder.watchfolder.service;

import java.util.List;
import java.util.Map;

public interface WatchfolderService {

	List<Map<String, Object>> selectWatchfolder(Map<String, Object> map) throws Exception;

	Map<String, Object> selectWatchfolderDetail(Map<String, Object> map) throws Exception;

	public int selectWatchfolderCountAll(Map<String, Object> map) throws Exception;

	public void insertWatchfolder(Map<String, Object> map) throws Exception;

	public void updateWatchfolder(Map<String, Object> map) throws Exception;

	public void deleteWatchfolder(Map<String, Object> map) throws Exception;

}
