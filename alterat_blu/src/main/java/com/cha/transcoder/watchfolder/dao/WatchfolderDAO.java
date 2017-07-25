package com.cha.transcoder.watchfolder.dao;

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

@Repository("watchfolderDAO")
public class WatchfolderDAO extends AbstractDAO {

	protected Log log = LogFactory.getLog(WatchfolderDAO.class);
	private static WatchfolderDAO instance;

	public WatchfolderDAO() {
		instance = this;
	}

	public static WatchfolderDAO getInstance() {
		if (instance == null) {
			instance = new WatchfolderDAO();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectWatchfolder(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) selectList("watchfolder.selectWatchfolder", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectWatchfolderOnlyManual() throws Exception {
		return (Map<String, Object>) selectDetail("watchfolder.selectWatchfolderOnlyManual");
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectWatchfolderDetail(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectDetail("watchfolder.selectWatchfolderDetail", map);
	}

	@SuppressWarnings("unchecked")
	public int selectWatchfolderCountAll(Map<String, Object> map) throws Exception {
		return ((Integer) selectOne("watchfolder.selectWatchfolderCountAll", map)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTargetDirectory(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) selectList("watchfolder.selectTargetDirectory", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectEnableList(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) selectList("watchfolder.selectEnableList", map);
	}

	public void insertWatchfolder(Map<String, Object> map) throws Exception {
		if (map.get("enable_yn") == null) {
			map.put("enable_yn", "N");
		} else {
			if (map.get("enable_yn").equals("on")) {
				map.put("enable_yn", "Y");
			} else {
				map.put("enable_yn", "N");
			}
		}
		
		if (map.get("manual_yn") == null) {
			map.put("manual_yn", "N");
		} else {
			if (map.get("manual_yn").equals("on")) {
				map.put("manual_yn", "Y");
			} else {
				map.put("manual_yn", "N");
			}
		}

		String newPID = null;
		String maxPID = (String) selectOne("watchfolder.selectPID");
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

		map.put("w_id", "W" + newPID);

		insert("watchfolder.insertWatchfolder", map);
	}

	public void updateWatchfolder(Map<String, Object> map) throws Exception {
		if (map.get("enable_yn") == null) {
			map.put("enable_yn", "N");
		} else {
			if (map.get("enable_yn").equals("on")) {
				map.put("enable_yn", "Y");
			} else {
				map.put("enable_yn", "N");
			}
		}
		
		if (map.get("manual_yn") == null) {
			map.put("manual_yn", "N");
		} else {
			if (map.get("manual_yn").equals("on")) {
				map.put("manual_yn", "Y");
			} else {
				map.put("manual_yn", "N");
			}
		}

		update("watchfolder.updateWatchfolder", map);
	}

	public void deleteWatchfolder(Map<String, Object> map) throws Exception {
		insert("watchfolder.deleteWatchfolder", map);
	}

}
