package com.cha.transcoder.nps;

//NPS에서 받은 응답중에 task_id, task_code를 저장한다.
//완료처리하기 위함임.
public class NpsTask {
	private String task_id;
	private String type_code;

	public String getTaskID() {
		return task_id;
	}

	public void setTaskID(String task_id) {
		this.task_id = task_id;
	}

	public String getTypeCode() {
		return type_code;
	}

	public void setTypeCode(String type_code) {
		this.type_code = type_code;
	}

	// 이 값이 Transferring JOB의 JOB_OPTION에 저장되고,
	// 전송완료시에 이 값을 NPS에 보낸다.
	@Override
	public String toString() {
		return "task_id=" + task_id + ", type_code=" + type_code;
	}
}