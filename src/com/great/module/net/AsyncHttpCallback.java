package com.great.module.net;

public interface AsyncHttpCallback {

	/**
	 * 成功处理
	 */
	public void onSuccess( byte[] reqData );
	
	/**
	 * 失败处理
	 */
	public void onError( int errorCode, String errorMsg);
	
}
