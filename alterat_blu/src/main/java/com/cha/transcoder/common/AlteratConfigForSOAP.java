package com.cha.transcoder.common;

public class AlteratConfigForSOAP {

	private String soap_server_url;
	private String soap_server_function_insertMetadata;
	private String soap_server_function_getContentList;
	private String soap_server_function_login;
	private String soap_server_user_id;

	public String getSoap_server_url() {
		return soap_server_url;
	}

	public void setSoap_server_url(String soap_server_url) {
		this.soap_server_url = soap_server_url;
	}

	public String getSoap_server_function_insertMetadata() {
		return soap_server_function_insertMetadata;
	}

	public void setSoap_server_function_insertMetadata(String soap_server_function_insertMetadata) {
		this.soap_server_function_insertMetadata = soap_server_function_insertMetadata;
	}

	public String getSoap_server_function_getContentList() {
		return soap_server_function_getContentList;
	}

	public void setSoap_server_function_getContentList(String soap_server_function_getContentList) {
		this.soap_server_function_getContentList = soap_server_function_getContentList;
	}

	public String getSoap_server_function_login() {
		return soap_server_function_login;
	}

	public void setSoap_server_function_login(String soap_server_function_login) {
		this.soap_server_function_login = soap_server_function_login;
	}

	public String getSoap_server_user_id() {
		return soap_server_user_id;
	}

	public void setSoap_server_user_id(String soap_server_user_id) {
		this.soap_server_user_id = soap_server_user_id;
	}

	@Override
	public String toString() {
		return "AlteratConfigForSOAP [soap_server_url=" + soap_server_url + ", soap_server_function_insertMetadata="
				+ soap_server_function_insertMetadata + ", soap_server_function_getContentList=" + soap_server_function_getContentList
				+ ", soap_server_function_login=" + soap_server_function_login + ", soap_server_user_id=" + soap_server_user_id + "]";
	}

}
