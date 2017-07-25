package com.cha.transcoder.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class TcUtil {
	private static Logger log = Logger.getLogger(TcUtil.class);

	/**
	 * 시간형식이 00:00:00 인 스트링을 초로 변경한다.
	 * 
	 * @param time
	 *            시간
	 * @return int 시간을 초로 변환한 값.
	 */
	public static int toSecond(String time) {
		String hour = time.substring(0, 2);
		String minute = time.substring(3, 5);
		String second = time.substring(6, 8);

		int tot = Integer.parseInt(hour) * 60 * 60;
		tot = tot + Integer.parseInt(minute) * 60;
		tot = tot + Integer.parseInt(second);

		return tot;
	}

	/**
	 * 초를 시:분:초 로 바꾼다.
	 * 
	 * @param second
	 *            시분초를 초로 계산한 값
	 * @return 시:분;초
	 */
	public static String toTime(int second) {
		int hour = second / (60 * 60);
		int min = (second % (60 * 60)) / 60;
		int sec = (second % (60 * 60)) % 60;

		DecimalFormat df = new DecimalFormat("00");
		StringBuffer buffer = new StringBuffer();

		buffer.append(df.format(hour));
		buffer.append(":");
		buffer.append(df.format(min));
		buffer.append(":");
		buffer.append(df.format(sec));

		return buffer.toString();
	}

	// 현재시간을 리턴한다.
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}
}
