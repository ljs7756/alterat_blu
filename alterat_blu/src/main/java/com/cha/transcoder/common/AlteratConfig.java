package com.cha.transcoder.common;

public class AlteratConfig {

	private String ffprobe;
	private String ffmpeg;
	private String ffmpeg_home;
	private boolean window;

	// UI에서 보여지는 IN 드라이브
	private String in_base_drive;
	private String in_base_folder;

	// 변환후 파일이 저장되는 경로
	private String dest_dir;

	public String getFfprobe() {
		return ffprobe;
	}

	public void setFfprobe(String ffprobe) {
		this.ffprobe = ffprobe;
	}

	public String getFfmpeg() {
		return ffmpeg;
	}

	public void setFfmpeg(String ffmpeg) {
		this.ffmpeg = ffmpeg;
	}

	public String getFfmpeg_home() {
		return ffmpeg_home;
	}

	public void setFfmpeg_home(String ffmpeg_home) {
		this.ffmpeg_home = ffmpeg_home;
	}

	public boolean isWindow() {
		return window;
	}

	public void setWindow(boolean window) {
		this.window = window;
	}

	public String getIn_base_drive() {
		return in_base_drive;
	}

	public void setIn_base_drive(String in_base_drive) {
		this.in_base_drive = in_base_drive;
	}

	public String getIn_base_folder() {
		return in_base_folder;
	}

	public void setIn_base_folder(String in_base_folder) {
		this.in_base_folder = in_base_folder;
	}

	public String getDest_dir() {
		return dest_dir;
	}

	public void setDest_dir(String dest_dir) {
		this.dest_dir = dest_dir;
	}

	@Override
	public String toString() {
		return "AlteratConfig [ffprobe=" + ffprobe + ", ffmpeg=" + ffmpeg + ", ffmpeg_home=" + ffmpeg_home + ", window=" + window + ", in_base_drive="
				+ in_base_drive + ", in_base_folder=" + in_base_folder + ", dest_dir=" + dest_dir + "]";
	}

}
