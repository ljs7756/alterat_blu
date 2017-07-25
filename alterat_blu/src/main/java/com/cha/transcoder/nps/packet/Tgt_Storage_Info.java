package com.cha.transcoder.nps.packet;

public class Tgt_Storage_Info {
	private String type;
	private String path;
	private String login_id;
	private String login_pw;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getLogin_pw() {
		return login_pw;
	}

	public void setLogin_pw(String login_pw) {
		this.login_pw = login_pw;
	}

	@Override
	public String toString() {
		return "Src_Storage_Info [type=" + type + ", path=" + path + ", login_id=" + login_id + ", login_pw=" + login_pw
				+ "]";
	}

}
