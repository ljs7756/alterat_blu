package com.cha.transcoder.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDAO {

	protected Log log = LogFactory.getLog(AbstractDAO.class);

	@Autowired
	protected SqlSessionTemplate sqlSession;

	public Object insert(String queryId, Object params) {
		printQueryWithParam(queryId, params);
		return sqlSession.insert(queryId, params);
	}

	public Object update(String queryId, Object params) {
		printQueryWithParam(queryId, params);
		return sqlSession.update(queryId, params);
	}

	public Object delete(String queryId, Object params) {
		printQueryWithParam(queryId, params);
		return sqlSession.delete(queryId, params);
	}

	public Object delete(String queryId) {
		printQueryWithParam(queryId);
		return sqlSession.delete(queryId);
	}

	public Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}

	public Object selectOne(String queryId, Object params) {
		printQueryWithParam(queryId, params);
		return sqlSession.selectOne(queryId, params);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}

	@SuppressWarnings("unchecked")
	public List selectList(String queryId, Object params) {
		printQueryWithParam(queryId, params);
		return sqlSession.selectList(queryId, params);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectDetail(String queryId, Object params) {
		printQueryWithParam(queryId, params);
		return sqlSession.selectOne(queryId, params);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectDetail(String queryId) {
		printQueryWithParam(queryId);
		return sqlSession.selectOne(queryId);
	}

	protected void printQueryId(String queryId) {
		if (log.isDebugEnabled()) {
			log.debug("QueryId=" + queryId);
		}
	}

	protected void printQueryWithParam(String queryId) {
		if (log.isDebugEnabled()) {
			log.debug("----------------------------------");
			log.debug("QueryId=" + queryId);
			log.debug("----------------------------------");
		}
	}

	public void printQueryWithParam(String queryId, Object params) {
		if (queryId != null && !queryId.equals("watchfolder.selectEnableList") && !queryId.equals("selectHoldOn")
				&& !queryId.equals("monitor.selectHoldOn") && !queryId.equals("monitor.select") && !queryId.equals("monitor.selectCountAll")) {
			log.debug("----------------------------------");
			if (log.isDebugEnabled()) {
				log.debug("QueryId=" + queryId);
			}

			Map<String, Object> mapPara = (Map) params;
			Iterator iter = mapPara.keySet().iterator();

			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = "";
				if (mapPara.get(key) instanceof String) {
					value = (String) mapPara.get(key);
				}

				log.debug(key + "=" + value);
			}
			log.debug("----------------------------------");
		}
	}
}
