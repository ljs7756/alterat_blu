package com.cha.transcoder.nps.packet;

public class ResultOfRegistration {

	private String success;
	private String status;
	private String message;
	private String task_id;
	private String content_id;
	private String parent_id;
	private String interface_id;
	private Task_List_Info task_list_info;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getInterface_id() {
		return interface_id;
	}

	public void setInterface_id(String interface_id) {
		this.interface_id = interface_id;
	}

	public Task_List_Info getTask_list_info() {
		return task_list_info;
	}

	public void setTask_list_info(Task_List_Info task_list_info) {
		this.task_list_info = task_list_info;
	}

	@Override
	public String toString() {
		return "ResultOfRegistration [success=" + success + ", status=" + status + ", message=" + message + ", task_id="
				+ task_id + ", content_id=" + content_id + ", parent_id=" + parent_id + ", interface_id=" + interface_id
				+ ", task_list_info=" + task_list_info + "]";
	}

}
