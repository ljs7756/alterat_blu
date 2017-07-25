package com.cha.transcoder.nps;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.cha.transcoder.common.AlteratConfigForSOAP;
import com.cha.transcoder.common.AlteratConfigLoader;
import com.cha.transcoder.monitor.dao.MonitorDAO;
import com.cha.transcoder.nps.dao.RegisterDAO;
import com.cha.transcoder.nps.soapinterface.CommonBindingStub;

public class SoapManager {

	Logger log = Logger.getLogger(SoapManager.class);

	@Resource(name = "registerDAO")
	RegisterDAO registerDAO;

	private final WebServiceTemplate webserviceTemplate = new WebServiceTemplate();

	private String soapMessage = "";

	private static SoapManager instance;
	private static PreviousParentID previousParentID;

	// 
	private SoapManager() {
	}

	public static SoapManager getInstance() {
		if (instance == null) {
			instance = new SoapManager();
			previousParentID = new PreviousParentID();
		}
		return instance;
	}

	// 등록 버튼을 눌렀을대 넘어온다. (트랜스코딩 종료후 아님.. !!!)
	public void registerMetadata(boolean isGroup, String g_id, String j_id, String file_path, String file_size) {
		try {
			log.debug("isGroup=" + isGroup + ", g_id=" + g_id + ", j_id=" + j_id + ", file_path=" + file_path + ", file_size=" + file_size);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("g_id", g_id);
			paramMap.put("j_id", j_id);

			if (registerDAO == null) {
				registerDAO = RegisterDAO.getInstance();
			}

			previousParentID.setGID(g_id);

			// soap으로 보낼 meta json 데이터를 만들자. 
			soapMessage = createMsgForInsertMetadata(isGroup, g_id, j_id, file_path, previousParentID.getParent_id());

			log.debug("\n\n\n ******************************* Starting registerMetadata ***************************************");
			log.debug("Create a SOAP Message=" + soapMessage);

			// null이 아니면 정상적인 메세지가 만들어 졌음.
			if (soapMessage != null) {

				AlteratConfigForSOAP altConfig = AlteratConfigLoader.getInstance().getConfigSOAP();
				String server_url = altConfig.getSoap_server_url();

				URL end_url = new URL(server_url);
				CommonBindingStub stub = new CommonBindingStub(end_url, null);
				String result = stub.insertMetadata(soapMessage);

				log.debug("NPS Response=" + result);

				// 완료처리 하기 위해 NPS 응답결과를 저장한다. 
				paramMap.put("nps_response", result);
				registerDAO.updateNpsResponse(paramMap);

				String success = result.substring(12, result.indexOf(",") - 1);
				log.debug("success=" + success);

				if (success.equals("true")) {

					int idxParentID = result.indexOf("parent_id");
					if (idxParentID > -1) {

						String strParentID = result.substring(idxParentID + 12, result.indexOf("interface_id") - 3);

						previousParentID.setParent_id(strParentID);
						paramMap.put("parent_id", strParentID);
						registerDAO.updateParentID(paramMap);
					}

				} else {
					// 서버로부터 success:false를 받았을 경우에 처리
					int idxStatus = result.indexOf("status");
					String status = result.substring(idxStatus + 9, result.indexOf(",", idxStatus) - 1);

					log.error("status=" + status);

					int idxMessage = result.indexOf("message");
					String message = result.substring(idxMessage + 10, result.lastIndexOf("}") - 1);

					log.error("message=" + message);
				}

				//log.debug("soap result'[" + result + "]'");
				log.debug("\n******************************* End of registerMetadata ***************************************\n\n");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// 전송작업을 등록한다.
	public void insertTransferringJob(String new_file_name, String source_file, String file_size, String destDir, NpsTask npsTask) {

		try {
			Map<String, Object> params = new HashMap();

			String file_type = source_file.substring(source_file.lastIndexOf(".") + 1);

			params.put("j_type", "Transferring");
			params.put("file_path", source_file);
			params.put("file_type", file_type.toLowerCase());
			params.put("file_name", new_file_name);
			params.put("file_size", file_size);
			params.put("target_directory", destDir);
			params.put("job_status", "Hold On");
			params.put("job_option", npsTask.toString());

			MonitorDAO monitorDAO = MonitorDAO.getInstance();
			String j_id = monitorDAO.insert(params);

			log.debug("j_id=" + j_id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 소재등록을 위한 메타데이터를 만든다.
	public String createMsgForInsertMetadata(boolean isGroup, String g_id, String j_id, String file_path, String parent_id) {
		StringBuffer message = new StringBuffer();

		try {
			log.debug("isGroup=" + isGroup + ", g_id=" + g_id + ", j_id=" + j_id);

			if (isGroup) {
				// J_ID에 대한 파일 정보 및 메타 정보를 가져오자.
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("g_id", g_id);
				paramMap.put("j_id", j_id);
				Map<String, Object> meta_result = registerDAO.selectNPSFullMetadata(paramMap);

				//String file_path = meta_result.get("file_path").toString(); 			
				if (meta_result == null) {
					log.debug("meta_result is Null");
				}

				// 파일 경로에 "\"하나를 "\\" 둘로 바꾸어 넘겨야 함.
				file_path = file_path.replaceAll(Matcher.quoteReplacement(File.separator),
						Matcher.quoteReplacement(File.separator) + Matcher.quoteReplacement(File.separator));
				String full_metadata = meta_result.get("full_metadata").toString();
				int group_count = registerDAO.selectNPSGroupCount(paramMap);
				int file_index = registerDAO.selectFileIndex(paramMap);

				message.append("{");
				message.append("\"inserttype\" : ").append("\"").append("each").append("\"").append(",");
				message.append("\"requestmeta\" : ");
				message.append(full_metadata.substring(0, full_metadata.length() - 1)).append(",");
				message.append("    \"is_group\" : ").append("\"").append("Y").append("\"").append(",");
				message.append("    \"index\" : ").append("\"").append(file_index).append("\"").append(",");
				message.append("    \"group_count\" : ").append("\"").append(group_count).append("\"").append(",");

				if (parent_id == null) {
					message.append("    \"filename\" : ").append("\"").append(file_path).append("\"");
				} else {
					message.append("    \"filename\" : ").append("\"").append(file_path).append("\",");
					message.append("    \"parent_id\" : ").append("\"").append(parent_id).append("\"");
				}

				message.append("}");
				message.append("}");

			} else {
				// J_ID에 대한 파일 정보 및 메타 정보를 가져오자.
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("g_id", g_id);
				paramMap.put("j_id", j_id);
				Map<String, Object> meta_result = registerDAO.selectNPSFullMetadata(paramMap);

				// 파일 경로에 "\"하나를 "\\" 둘로 바꾸어 넘겨야 함.
				file_path = file_path.replaceAll(Matcher.quoteReplacement(File.separator),
						Matcher.quoteReplacement(File.separator) + Matcher.quoteReplacement(File.separator));
				String full_metadata = meta_result.get("full_metadata").toString();

				//log.debug("full_metadata=" + full_metadata + "\n\n"); 

				message.append("{");
				message.append("\"inserttype\" : 0,");
				message.append("\"requestmeta\" : ");
				message.append(full_metadata.substring(0, full_metadata.length() - 1)).append(",");
				message.append("    \"filename\" : ").append("\"").append(file_path).append("\"");

				message.append("}");
				message.append("}");

				//log.debug("message=" + message.toString() + "\n\n");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			return message.toString();
		}
	}

	// 완료처리 메시지를 만들어 보낸다.
	public String requestTaskComplete(String task_id, String type_code) {
		StringBuffer message = new StringBuffer();

		String ip = "";
		try {
			InetAddress address = InetAddress.getLocalHost();
			if (address.getLocalHost().getHostAddress() != null) {
				ip = address.getLocalHost().getHostAddress();
			}

			log.debug("ip=" + ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		try {
			message.append("{");
			message.append("\"action\":").append("\"update\"").append(",");
			message.append("\"task_id\":").append(task_id).append(",");
			message.append("\"type_code\":").append("\"").append(type_code).append("\"").append(",");
			message.append("\"progress\":100,");
			message.append("\"status\":\"complete\",");
			message.append("\"ip\":").append("\"").append(ip).append("\"").append(",");
			message.append("\"log\":").append("\"\"");
			message.append("}");

			AlteratConfigForSOAP altConfig = AlteratConfigLoader.getInstance().getConfigSOAP();
			String server_url = altConfig.getSoap_server_url();

			log.debug("server_url=" + server_url);

			URL end_url = new URL(server_url);
			CommonBindingStub stub = new CommonBindingStub(end_url, null);
			log.debug("Create a SOAP Message=" + message.toString());

			String result = stub.updateStatus(message.toString());
			log.debug("NPS Response=" + result);

			if (result.indexOf("\"success\":\"false\"") != -1) {
				Thread.sleep(500);

				result = stub.updateStatus(message.toString());
				//log.debug("Check, Retry 1, NPS Response=" + result);

				if (result.equals(result.indexOf("\"success\":\"false\"") != -1)) {
					Thread.sleep(500);

					result = stub.updateStatus(message.toString());
					//log.debug("Check, Retry 2, NPS Response=" + result);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		return message.toString();
	}
}

class PreviousParentID {
	private String g_id;
	private String parent_id;

	public String getGID() {
		return g_id;
	}

	public void setGID(String g_id) {
		if (this.g_id == null) {
			this.g_id = g_id;
		} else {
			if (!this.g_id.equals(g_id)) {
				parent_id = null; // initailize
				this.g_id = g_id;
			}
		}
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	@Override
	public String toString() {
		return "ParentID [g_id=" + g_id + ", parent_id=" + parent_id + "]";
	}
}