package com.cha.transcoder.demon;

import java.util.Hashtable;

public interface TcConstant {

	public static final int TYPE_TRANSCODING = 100;
	public static final int TYPE_MERGE = 200;
	public static final int TYPE_TRANSFERRING = 300;

	// 동시작업을 고려하여 작업의 전체합은 CPU Core 수 이어야 함
	// 코어수를 넘어가면 성능이 저하됨. (6개)
	// 4/1일 머지와 트랜스코딩을 같이 하지 않는다는 조건으로 갯수를 6개로 함.

	// 트랜스코딩 프로세서수 제한
	public static int LIMIT_OF_TRANSCODING = 10;

	// 머지작업 프로세서수 제한
	public static int LIMIT_OF_MERGE = 6;

	// 전송작업 프로세서수 제한
	public static int LIMIT_OF_TRANSFERRING = 1;

	// 작업이 취소댈때 인터럽트를 위해 기다리는 시간
	public static int WAITING_TIME_FOR_INTERRUPT = 1;
	
	public static Hashtable activeThread2 = new Hashtable(LIMIT_OF_TRANSCODING + LIMIT_OF_MERGE + LIMIT_OF_TRANSFERRING);

}