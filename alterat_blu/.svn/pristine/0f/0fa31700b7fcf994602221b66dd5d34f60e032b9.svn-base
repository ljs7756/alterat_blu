package com.cha.transcoder.demon;

public class MediaFileInfo {

	// MP4의 경우 Video Codec이 2종류가 있음. 코덱에 따라 옵션이 다름.
	public static final String MP4_H264 = "h264";
	public static final String MP4_MPEG_VIDEO = "mpeg2video";

	private String video_codec;
	private String audio_codec;
	private String duration;
	private String metatdata;
	private String encoder;

	private boolean unSupportedCodec = false;

	public MediaFileInfo() {
	}

	public String getVideo_codec() {
		return video_codec;
	}

	public void setVideo_codec(String video_codec) {
		this.video_codec = video_codec;
	}

	public String getAudio_codec() {
		return audio_codec;
	}

	public void setAudio_codec(String audio_codec) {
		this.audio_codec = audio_codec;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getMetatdata() {
		return metatdata;
	}

	public void setMetatdata(String metatdata) {
		this.metatdata = metatdata;
	}

	public String getEncoder() {
		return encoder;
	}

	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}

	public boolean isUnSupportedCodec() {
		return unSupportedCodec;
	}

	public void setUnSupportedCodec(boolean unSupportedCodec) {
		this.unSupportedCodec = unSupportedCodec;
	}

	@Override
	public String toString() {
		return "MediaFileInfo [video_codec=" + video_codec + ", audio_codec=" + audio_codec + ", duration=" + duration + ", encoder=" + encoder
				+ ", unSupportedCodec=" + unSupportedCodec + "]";
	}

}
