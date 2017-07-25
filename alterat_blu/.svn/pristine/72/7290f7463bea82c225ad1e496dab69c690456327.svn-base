package com.cha.transcoder.sending.dao;

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

@Repository("sendingDAO")
public class SendingDAO extends AbstractDAO {

	private static SendingDAO instance;

	public SendingDAO() {
		instance = this;
	}

	public static SendingDAO getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> select(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) selectList("sending.select", map);
	}
	
	@SuppressWarnings("unchecked")
	public int selectCountAll(Map<String, Object> map) throws Exception {
		return ((Integer) selectOne("sending.selectCountAll", map)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectStandby() throws Exception {
	
		return (Map<String, Object>) selectOne("sending.selectStandby");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getprogress(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) sqlSession.selectOne("sending.getprogress", map);
	}
	
	public void updateCanceledState(Map<String, Object> map) throws Exception {
		update("sending.updateCanceledState", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> ftpInfoList() throws Exception {
		return (List<Map<String, Object>>) selectList("ftpinfo.ftpinfolist");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectFtpInfo(Map<String, Object> map) throws Exception {
	
		return  (Map<String, Object>) selectOne("ftpinfo.selectFtpinfo", map);
	}
	
	public void updateprogress(Map<String, Object> map) throws Exception {
		sqlSession.update("ftpinfo.updateProgress", map);
	}
	
	public void updatejobstatus(Map<String, Object> map) throws Exception {
		sqlSession.update("ftpinfo.updateJobStatus", map);
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
