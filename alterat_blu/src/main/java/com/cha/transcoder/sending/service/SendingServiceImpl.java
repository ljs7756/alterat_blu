package com.cha.transcoder.sending.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cha.transcoder.nps.MediaFileFilter;
import com.cha.transcoder.sending.dao.SendingDAO;
import com.cha.transcoder.watchfolder.dao.WatchfolderDAO;
import com.cha.transcoder.sending.control.SendingController;

@Service("sendingService")
public class SendingServiceImpl implements SendingService {
	Logger log = Logger.getLogger(SendingServiceImpl.class);

	@Resource(name = "sendingDAO")
	private SendingDAO sendingDAO;

	public void SendingServiceImpl() {
		
	}

	@Override
	public List<Map<String, Object>> select(Map<String, Object> map) throws Exception {
		return sendingDAO.select(map);
	}

	@Override
	public int selectCountAll(Map<String, Object> map) throws Exception {
		return sendingDAO.selectCountAll(map);
	}

	@Override
	public Map<String, Object> getprogress(Map<String, Object> map) throws Exception {
		return sendingDAO.getprogress(map);
	}
	
	@Override
	public List<Map<String, Object>> ftpInfoList() throws Exception {
		return sendingDAO.ftpInfoList();
	}
	
	@Override
	public int ftpSending(List<Map<String, String>> fileList) {
		int result = -1;

		String target_path   ="";
	
		try {

			SendingController sc = new SendingController();
			
			sendingDAO.beginBatch();


			for (Map<String, String> fileinfo : fileList) {

				String file_name = fileinfo.get("filename");
				String fileid   = fileinfo.get("fileid");    // path + name
				String no   = fileinfo.get("no");    // 확장자
				String ftp_code  = fileinfo.get("channel");    // 확장자

				//pooq용 targetpath
				if(ftp_code.equals("pooq2")){
					 target_path = "/"+file_name.substring(0, file_name.indexOf("_"));
				}
				
				File source_file = new File(fileid);
				
				if (MediaFileFilter.isMediaFile(source_file)) {
					sc.insertFtp(sendingDAO.getSession(), source_file, fileid, file_name, target_path, ftp_code);
					result = 1;
				}

			}
			sendingDAO.commit();

		} catch (Exception e) {
			sendingDAO.rollback();

			result = -1;
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void updateCanceledState(Map<String, Object> map) throws Exception {
		sendingDAO.updateCanceledState(map);
		
	}

}
