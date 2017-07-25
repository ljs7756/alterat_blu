package com.cha.transcoder.nps.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.cha.transcoder.common.AbstractDAO;

@Repository("registerDAO")
public class RegisterDAO extends AbstractDAO {

	private static RegisterDAO instance;

	public RegisterDAO() {
		instance = this;
	}

	public static RegisterDAO getInstance() {
		return instance;
	}

	public int insertMetaData(SqlSession session, String g_id, String j_id, Map<String, String> fileinfo, Map<String, String> metaMap,
			String full_metadata, int file_index) throws Exception {

		int result = -1;

		try {
			metaMap.put("g_id", g_id);
			metaMap.put("j_id", j_id);
			metaMap.put("file_path", fileinfo.get("fileid"));
			metaMap.put("file_name", fileinfo.get("filename"));
			metaMap.put("full_metadata", full_metadata);
			metaMap.put("file_index", Integer.toString(file_index));

			printQueryWithParam("register.insertNPSMetadata", metaMap);
			session.insert("register.insertNPSMetadata", metaMap);

			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	public Map<String, Object> selectNPSFullMetadata(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectDetail("register.selectNPSFullMetadata", map);
	}

	public Map<String, Object> selectParentID(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectDetail("register.selectParentID", map);
	}

	public Map<String, Object> selectNPSMetadata(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectDetail("register.selectNPSMetadata", map);
	}

	public List<Map<String, Object>> selectNPSMetaList(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) selectList("register.selectNPSMetadata", map);
	}

	public List<Map<String, Object>> selectNPSFile(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) selectList("register.selectNPSFile", map);
	}

	public int selectNPSGroupCount(Map<String, Object> map) throws Exception {
		return ((Integer) selectOne("register.selectNPSGroupCount", map)).intValue();
	}

	public int selectFileIndex(Map<String, Object> map) throws Exception {
		return ((Integer) selectOne("register.selectFileIndex", map)).intValue();
	}

	public void updateParentID(Map<String, Object> map) throws Exception {
		update("register.updateParentID", map);
	}

	public void updateNpsResponse(Map<String, Object> map) throws Exception {
		update("register.updateNpsResponse", map);
	}

	public String selectGID(Map<String, Object> map) throws Exception {
		return (String) selectOne("register.selectGID", map);
	}

	public int selectGountGID(Map<String, Object> map) throws Exception {
		return ((Integer) selectOne("register.selectGountGID", map)).intValue();
	}

	public void deleteAll() throws Exception {
		delete("register.deleteAll");
	}

}
