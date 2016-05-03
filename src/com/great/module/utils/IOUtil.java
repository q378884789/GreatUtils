package com.great.module.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class IOUtil {

	/**
	 * 将输入流转换成字节流
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static byte[] inputStreamToBytes(InputStream input) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	public static InputStream stringToInputStream(String ins) {
		// 字符串转inputStream
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(ins.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;

	}

	public static String inputStreamToString(InputStream is) throws IOException {
		// InputStream转字符串
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}

		is.close();
		return baos.toString();
	}

	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		long count = copyLarge(input, output);
		if (count > 2147483647L) {
			return -1;
		}
		return (int) count;
	}

	private static long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[4096];
		long count = 0L;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
