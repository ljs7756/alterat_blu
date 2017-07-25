package com.cha.transcoder.demon;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Scheduled;

import com.cha.transcoder.common.CommandMap;
import com.cha.transcoder.monitor.control.MonitorController;
import com.cha.transcoder.monitor.dao.MonitorDAO;
import com.cha.transcoder.monitor.service.MonitorService;
import com.cha.transcoder.nps.MediaFileFilter;
import com.cha.transcoder.watchfolder.dao.WatchfolderDAO;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * 데이터베이스에 등록된 와치 폴더를 채크한다.
 */
//@Component
@Service
public class WatchfolderManager {

	private Logger log = Logger.getLogger(WatchfolderManager.class);

	private static Hashtable htFolders = new Hashtable(10);
	
	@Resource(name = "watchfolderDAO")
	private WatchfolderDAO watchfolderDAO;
	

	public void WatchfolderManager() {
		log.debug("Constructor....");
	}
	
	

//	@Scheduled(fixedDelay = 50000000)
	@PostConstruct
	public void scheduling() {
		log.debug("====111watchfolder start111====");

		Map<String, Object> map = new HashMap();
		map.put("enable_yn", "Y");

		try {
			List<Map<String, Object>> list = watchfolderDAO.selectEnableList(map);
			if (list == null) {
				log.debug("No watch folder.");
			} else {
				 log.debug("count of watchfolder=" + list.size());
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map2 = list.get(i);

					String src_dir = (String) map2.get("source_directory");
					String trg_dir = (String) map2.get("target_directory");
					
					
							Path s_dir = Paths.get(src_dir);
							Path t_dir = Paths.get(trg_dir);
							ThreadSourceFolderWatcher trd = new ThreadSourceFolderWatcher();
							trd.setPara(s_dir,t_dir);
							Thread trdRunner = new Thread(trd);
							trdRunner.start();
					
					
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		
	}
	
	
}


class ThreadSourceFolderWatcher implements Runnable {
	private Logger log = Logger.getLogger(ThreadSourceFolderWatcher.class);

	private Path s_dir = null;
	private Path t_dir = null;
	
	
//	@Resource(name = "monitorController")
//	private MonitorController monitorController;
	
	MonitorDAO monitorDAO = MonitorDAO.getInstance();
	

	MonitorController monitorController = MonitorController.getInstance();
	
	

	public ThreadSourceFolderWatcher() {
	}
	
	public void setPara(Path s_dir, Path t_dir) {

		this.s_dir = s_dir;
		this.t_dir = t_dir;
		
	}


	public void run() {
		log.debug("Running..........");
		
		//20170717_jongseong 와치폴더 
	
		WatchService watcher;
	
		try {
			watcher = FileSystems.getDefault().newWatchService();
			s_dir.register(watcher,ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY,OVERFLOW);
			
			while (true) {
			    WatchKey key;
			    try {
			        // wait for a key to be available
			        key = watcher.take();
			    } catch (InterruptedException ex) {
			        return;
			    }
			
			    for (WatchEvent<?> event : key.pollEvents()) {
			        // get event type
			        WatchEvent.Kind<?> kind = event.kind();
			 
			        // get file name
			        @SuppressWarnings("unchecked")
			        WatchEvent<Path> ev = (WatchEvent<Path>) event;
			        Path fileName = ev.context();
			       
			        log.debug(kind.name() + ": " + fileName+" 변경 감지..");
			 
			        if (kind == OVERFLOW) {
			            continue;
			        } else if (kind == ENTRY_CREATE) {
			 
			            // process create event
			        	log.debug("Create...."+fileName.getFileName());
			        	
			        	   String fileNm = fileName.getFileName().toString();
			        
					       if(!fileNm.contains("_ing")){
					    	   try{
					    		   
					    		// 와치폴더에 저장된 타겟 경로를 가져온다.
									String source_path = s_dir+"";
									String target_path = t_dir+"";
									
									
									//확장자 분리
									int Idx = fileNm.lastIndexOf(".");
									String _fileNm = fileNm.substring(0, Idx);
									
									//디렉토리 생성을 위한 네임 분리
									String[] arr;
									arr = _fileNm.split("_");
//									log.debug("arr[0]=="+arr[0]);
//									log.debug("arr[1]=="+arr[1]);
//									log.debug("target_path=="+target_path);
									
									
									//현재시간으로 폴더 생성 및 네이밍
									Date now = new Date();
									
									SimpleDateFormat fm = new SimpleDateFormat("yyMMddHHmmss");
									String nowDate = fm.format(now);
									
									String targetDirectory = target_path+"/"+arr[0]+"/"+arr[1]+"/"+nowDate;
									
									File f = new File(targetDirectory);
									f.mkdirs(); //폴더 생성
									
									
									//파일 이동
									String originFilePath = source_path+"/"+fileNm;
									String targetFilePath = targetDirectory+"/"+fileNm;
									
									File ori_f = new File(originFilePath);
									ori_f.renameTo(new File(targetFilePath)); //파일 이동
									
						    	    String worker = "mp4";
						    	    String bitrate = "1000";
						    	    String p_id = "P2017072000001";
						    	    String fileid   = s_dir+"/"+fileNm;  //linux
						         // String fileid   = s_dir+"\\"+fileNm; //window
						    	    
						    	   
						    	    File source_file = new File(targetFilePath);
						    		

									if (MediaFileFilter.isMediaFile(source_file)) {
										
//									
										monitorDAO.beginBatch();

										String j_id = monitorController.insertTcJob_2(monitorDAO.getSession(), source_file, source_path, targetDirectory, worker, bitrate, p_id);

										
//										if (j_id != null) {
//											result = 1;
//										}
										monitorDAO.commit();
									}
									
					    		   
					    		   
					    	   }catch(Exception e){
					    		   monitorDAO.rollback();
					    		   e.printStackTrace();  
					    	   }
								
					    	   
					       }
			 
			        } else if (kind == ENTRY_DELETE) {
			 
			            // process delete event
			        	log.debug("Delete...."+fileName.getFileName());
			 
			        } else if (kind == ENTRY_MODIFY) {
			 
			            // process modify event
			        	log.debug("Modify...."+fileName.getFileName());
					    
			 
			        }
			    }
			 
			    // IMPORTANT: The key must be reset after processed
			    boolean valid = key.reset();
			    if (!valid) {
			        break;
			    }
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	}
	
	
}
