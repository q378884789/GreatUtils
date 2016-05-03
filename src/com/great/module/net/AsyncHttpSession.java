package com.great.module.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;

import com.great.module.utils.APNUtil;

import android.content.Context;

/**
 * Session线程
 * 
 * @author zhanghao_c
 * 
 */
public class AsyncHttpSession implements Runnable
{

	public String mUrl = "";
	private Context mContext;
	private AsyncHttpCallback mCallback;
	
	private byte[] mData = null;
	
	private AsyncHttpSessionManager manager = null;

	public AsyncHttpSession(Context context, String Url) {

		this.mContext = context;
	}

	public void doRequest(String data)
	{
		mData = data.getBytes();
		
		manager = AsyncHttpSessionManager.getInstance(HttpSessionConstant.MAX_POST_CONNECTIONS);
		manager.sublime(this);
	}

	/**
	 * 注册AsyncHttpCallback
	 * @param callback
	 */
	public void regisetAsyncHttpCallBack(AsyncHttpCallback callback){
		mCallback = callback;
	}
	
	@Override
	public void run()
	{
		
		DataOutputStream os = null;
		HttpURLConnection conn = null;
		
		int retry = 0;

			if ( APNUtil.isNetworkAvailable(mContext) ) {
				while (retry >= HttpSessionConstant.MAX_RETRY.POST_DATA) {
					try {
						URL url = new URL(mUrl);

						conn = (HttpURLConnection) url.openConnection();
						// 设置请求方式为post
						conn.setRequestMethod("post");
						// 设置连接超时时间
						conn.setConnectTimeout(HttpSessionConstant.CONNECTION_TIMEOUT);
						// 设置是否从httpUrlConnection读入
						conn.setDoInput(true);
						conn.setDoOutput(true);
						// 设置是否使用缓存
						conn.setUseCaches(false);
						// 设置代理
						conn.setInstanceFollowRedirects(true);
						// 设置content-type类型
						conn.setRequestProperty("Content-Type", "multipart/form-data");
						conn.setRequestProperty("User-Agent", HttpSessionConstant.getUserAgent());
						// 在conn.getOutputStream中隐含有conn.connet()行数；
						conn.connect();
						
						//写请求数据
						os = (DataOutputStream) conn.getOutputStream();
						os.write(mData);
						os.flush();
						
						//获取数据（输入流）
						byte[] data = readStream(conn.getInputStream());
						
						mCallback.onSuccess(data);
						//数据请求成功推出重试循环
						return;
						
					}
					catch (Exception excp) {
						if (excp instanceof InterruptedIOException) {
							if (mCallback != null)
								mCallback.onError(HttpSessionConstant.ERROR_CODE.ERR_CONNECT_TIMEOUT, excp.toString());
							return;
						}

						if (excp instanceof UnknownHostException) {
							if (mCallback != null)
								mCallback.onError(HttpSessionConstant.ERROR_CODE.ERR_UNKNOWN_HOST, excp.toString());
							return;
						}

						if (excp instanceof ConnectException) {
							if (mCallback != null)
								mCallback.onError(HttpSessionConstant.ERROR_CODE.ERR_CONNECT_REFUSE, excp.toString());
							return;
						}

						if (excp instanceof ClientProtocolException) {
							if (mCallback != null)
								mCallback.onError(HttpSessionConstant.ERROR_CODE.ERR_PROTOCOL_ERROR, excp.toString());
							return;
						}
						
						Logger.e("Exception have other exception : " );
						excp.printStackTrace();
					}
					finally{
						
						try {
							os.close();
							conn.disconnect();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				retry++;
			}
		}
			else {
				mCallback.onError(HttpSessionConstant.ERROR_CODE.ERR_NETWORK_DISABLE,
						"has net work error");
				Logger.e("network can`t connnetion");
				//当网络出现异常时，会进行重试，
			}
			
	}
	
	
	/**
	 * 将输入流转成字符串
	 * @param inputStream
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	private static byte[] readStream(InputStream inputStream) throws IOException {  
		
        ByteArrayOutputStream bout = new ByteArrayOutputStream();  
        byte[] buffer = new byte[HttpSessionConstant.SOCKET_BUFFER_SIZE];  
        int len = 0;  
        while ((len = inputStream.read(buffer)) != -1) {  
            bout.write(buffer, 0, len);  
        }  
        
        bout.close();  
        inputStream.close();  
        return bout.toByteArray();  
    }  
}
