package com.cha.transcoder.nps;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

// 변환가능한 포멧들만 처리 할 수 있도록 함.
public class MediaFileFilter implements FileFilter {

	private static String MEDIA_FILES_PATTERN = ".+\\.(avi|mpg|mpeg|mxf|wmv|flv|rm|mov|mp4|mp3|mkv|dat|asf|asx|wma|mts|ts|m2ts)$";
	Logger log = Logger.getLogger(MediaFileFilter.class);

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}

		String name = file.getName();

		Pattern p = Pattern.compile(MEDIA_FILES_PATTERN);
		String lowerCaseDir = name.toLowerCase();
		Matcher m = p.matcher(lowerCaseDir);

		return m.matches();
	}

	public static boolean isMediaFile(File file) {
		String name = file.getName();

		Pattern p = Pattern.compile(MEDIA_FILES_PATTERN);
		String lowerCaseDir = name.toLowerCase();
		Matcher m = p.matcher(lowerCaseDir);

		return m.matches();
	}
}
