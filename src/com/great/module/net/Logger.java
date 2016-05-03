package com.great.module.net;

import android.util.Log;

public final class Logger {

	private static boolean IS_DEBUG_PATTERN = true;

	/**
	 * 日志的标记
	 */
	private static final String TAG = "module.net";

	/**
	 * 错误日志
	 * @param info 打印信息
	 */
	public static void e(String info) {
		
		if (IS_DEBUG_PATTERN) {
			
			Log.e(TAG, info);
		}
	}

	
	/**
	 * 警告信息
	 * @param info
	 */
	public static void w(String info) {

		if (IS_DEBUG_PATTERN) {
			
			Log.w(TAG, info);
		}
	}

	/**
	 * DEBUG信息
	 * @param info
	 */
	public static void d(String info) {

		if (IS_DEBUG_PATTERN) {
			
			Log.d(TAG, info);
		}
	}

	
	/**
	 * 普通信息
	 * @param info
	 */
	public static void i(String info) {
		
		if (IS_DEBUG_PATTERN) {
			
			Log.i(TAG, info);
		}
	}

}
