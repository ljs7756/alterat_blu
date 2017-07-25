package com.cha.transcoder.nps.packet;

public class Task_Info {
	public String task_id;
	public String type;
	public String source;
	public String target;
	public String channel;
	public String content_id;
	public String src_media_id;
	public String tgt_media_id;
	public Src_Storage_Info src_storage_info;
	public Tgt_Storage_Info tgt_storage_info;

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}

	public String getSrc_media_id() {
		return src_media_id;
	}

	public void setSrc_media_id(String src_media_id) {
		this.src_media_id = src_media_id;
	}

	public String getTgt_media_id() {
		return tgt_media_id;
	}

	public void setTgt_media_id(String tgt_media_id) {
		this.tgt_media_id = tgt_media_id;
	}

	public Src_Storage_Info getSrc_storage_info() {
		return src_storage_info;
	}

	public void setSrc_storage_info(Src_Storage_Info src_storage_info) {
		this.src_storage_info = src_storage_info;
	}

	public Tgt_Storage_Info getTgt_storage_info() {
		return tgt_storage_info;
	}

	public void setTgt_storage_info(Tgt_Storage_Info tgt_storage_info) {
		this.tgt_storage_info = tgt_storage_info;
	}

	
	@Override
	public String toString() {
		return "TaskInfo [task_id=" + task_id + ", type=" + type + ", source=" + source + ", target=" + target
				+ ", channel=" + channel + ", content_id=" + content_id + ", src_media_id=" + src_media_id
				+ ", tgt_media_id=" + tgt_media_id + "]";
	}

}
