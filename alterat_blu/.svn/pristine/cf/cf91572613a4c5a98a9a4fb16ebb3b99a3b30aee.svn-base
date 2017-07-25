package com.cha.transcoder.monitor.dao;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.cha.transcoder.common.AbstractDAO;

@Repository("monitorDAO")
public class MonitorDAO extends AbstractDAO {

	private static MonitorDAO instance;

	public MonitorDAO() {
		instance = this;
	}

	public static MonitorDAO getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public String selectPrimaryKey(SqlSession session) throws Exception {
		String maxPID = (String) session.selectOne("monitor.selectPrimaryKey");
		String newPID = null;

		Date current = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		DecimalFormat df = new DecimalFormat("00000");

		if (maxPID == null) {
			newPID = sdf.format(current) + "00001";
		} else {
			int seq = Integer.parseInt(maxPID.substring(9));
			newPID = sdf.format(current) + df.format(seq + 1);
		}
		return newPID; // No 'J', just 'date + seq'
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> select(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) selectList("monitor.select", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectDetail(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectDetail("monitor.selectDetail", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectHoldOn(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectOne("monitor.selectHoldOn", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> selectStanby() throws Exception {
		return (List<Map<String, String>>) selectList("monitor.selectStanby");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> selectFtpAuto() throws Exception {
		return (Map<String, String>) selectOne("monitor.selectFtpAuto");
	}

	@SuppressWarnings("unchecked")
	public int selectCountAll(Map<String, Object> map) throws Exception {
		return ((Integer) selectOne("monitor.selectCountAll", map)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getprogress(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) sqlSession.selectOne("monitor.getprogress", map);
	}

	// JOB을 저장한 이후에 j_id 를 리턴한다. 
	// SelSession 을 이용하지 않는다.
	public String insert(Map<String, Object> map) throws Exception {
		String maxPID = (String) selectOne("monitor.selectPrimaryKey");
		String newPID = null;

		Date current = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		DecimalFormat df = new DecimalFormat("00000");

		if (maxPID == null) {
			newPID = sdf.format(current) + "00001";
		} else {
			int seq = Integer.parseInt(maxPID.substring(9));
			newPID = sdf.format(current) + df.format(seq + 1);
		}

		String j_id = "J" + newPID;
		map.put("j_id", j_id);

		if (map.get("j_type").equals("Merge")) {
				
			String file_name="";
			String j_name ="";
			String[] fname = map.get("src_list").toString().split("[|]");

			
			//pooq전송을 위한 머지 구분(규칙 xx_xxx#1,xx_xx#2)
			if(fname[0].lastIndexOf("#1")> -1){
				
				j_name = fname[0].substring(0, fname[0].lastIndexOf("#1"));
				
				file_name = "Merge-" + j_name + "." + map.get("file_type");
				map.put("file_name", file_name);
				map.put("file_status", "Standby");
				
//				log.debug("mergename=="+file_name);
				
			}else{
				
				j_name = fname[0].substring(0, fname[0].lastIndexOf("."));
				
				file_name = "Merge-" + j_name + "." + map.get("file_type");
				map.put("file_name", file_name);
				
//				log.debug("mergename=="+file_name);
			}
			
			String file_path = ((String) map.get("file_path")) + File.separatorChar + file_name;
			map.put("file_path", file_path);
		}

		Object ret = insert("monitor.insert", map);
		int result = -1;
		if (ret instanceof Integer) {
			result = (Integer) ret;
			if (result == 1) {
				return j_id;
			}
		}

		return null;
	}

	public void update(Map<String, Object> map) throws Exception {
		if (map.get("enable_yn") == null) {
			map.put("enable_yn", "N");
		} else {
			if (map.get("enable_yn").equals("on")) {
				map.put("enable_yn", "Y");
			} else {
				map.put("enable_yn", "N");
			}
		}

		update("monitor.update", map);
	}

	public void updateResetStatus(Map<String, Object> map) throws Exception {
		update("monitor.updateResetStatus", map);
	}

	public void updateCanceledState(Map<String, Object> map) throws Exception {
		update("monitor.updateCanceledState", map);
	}

	public void deleteAll() throws Exception {
		delete("monitor.deleteAll");
	}

	// --------------- Batch process --------------- //
	private SqlSession session;

	public void beginBatch() {
		log.debug("[BATCH] Open Session for Batch");
		session = sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	}

	public SqlSession getSession() {
		return session;
	}

	public void insertBatch(String queryId, Map<String, Object> map) throws Exception {
		try {
			printQueryWithParam(queryId, map);
			session.insert(queryId, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		log.debug("[BATCH] Commit");
		session.commit();

		session.clearCache();
		session.close();
	}

	public void rollback() {
		log.debug("[BATCH] Rollback");
		session.rollback();

		session.clearCache();
		session.close();
	}
	// --------------- End of Batch process --------------- //
}
