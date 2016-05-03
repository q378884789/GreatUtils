package com.great.module.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程管理
 * @author zhanghao_c
 *
 */
public class AsyncHttpSessionManager {

	// 默认最大的线程数是3
	protected int mMaxTread = 3;

	ExecutorService mThreadPool = null;
	
	private static AsyncHttpSessionManager manager  = null;
	
	/**
	 * 创建最大数量的线程池
	 * @param max 最大数量
	 * @return 返回线程池manager
	 */
	public synchronized static AsyncHttpSessionManager getInstance(int max)
	{
		if ( manager == null ) {
			manager = new AsyncHttpSessionManager(max);
		}
		return manager;
	}
	
	
	private AsyncHttpSessionManager(int maxTread) {
		mMaxTread = maxTread;
		mThreadPool = Executors.newFixedThreadPool(mMaxTread);
	}

	/**
	 * 提交线程到线程池
	 * @param thread
	 */
	public void sublime( AsyncHttpSession session){
		mThreadPool.submit(session);
	}
	
}
