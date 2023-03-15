package com.jszy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public class HttpClient {

	public static String httpWechatApi(JSONObject json, String url, String RSAPRIVATEKEY) throws Exception {
		String s = RSAUtil.encryptByPrivateKey(json.toString(), RSAPRIVATEKEY);
		String end = post(url, s);
		return end;
	}

	public static String get(String url) {
		HttpURLConnection conn = null;
		OutputStreamWriter wr = null;
		try {
			conn = (HttpURLConnection) (new URL(url)).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
			conn.setRequestMethod("GET");
			int e = conn.getResponseCode();
			if (e != 200) {
				System.out.println("远程访问错误结果为" + e);
				return "";
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = br.readLine();
			StringBuffer back = new StringBuffer();
			while (line != null) {
				back.append(line);
				line = br.readLine();
			}
			return back.toString();
		} catch (Exception var16) {
			var16.printStackTrace();
			return "";
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param content
	 * @return
	 */
	public static String post(String url, String content) {
		HttpURLConnection conn = null;
		OutputStreamWriter wr = null;
		try {
			conn = (HttpURLConnection) (new URL(url)).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
			conn.setRequestMethod("POST");
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(content);
			wr.flush();
			int e = conn.getResponseCode();
			if (e != 200) {
				System.out.println("远程访问错误结果为" + e);
				return "";
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = br.readLine();
			StringBuffer back = new StringBuffer();
			while (line != null) {
				back.append(line);
				line = br.readLine();
			}
			return back.toString();
		} catch (Exception var16) {
			var16.printStackTrace();
			return "";
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param content
	 * @return
	 */
	public static String postAddToken(String url, String content, String token, String timestamp, String sign) {
		HttpURLConnection conn = null;
		OutputStreamWriter wr = null;
		try {
			conn = (HttpURLConnection) (new URL(url)).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
			conn.setRequestProperty("token", token);
			conn.setRequestProperty("timestamp", timestamp);
			conn.setRequestProperty("sign", sign);
			conn.setRequestMethod("POST");
			wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			wr.write(content);
			wr.flush();
			int e = conn.getResponseCode();
			if (e != 200) {
				System.out.println("远程访问错误结果为" + e);
				return "";
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = br.readLine();
			StringBuffer back = new StringBuffer();
			while (line != null) {
				back.append(line);
				line = br.readLine();
			}
			return back.toString();
		} catch (Exception var16) {
			var16.printStackTrace();
			return "";
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String getContent(HttpServletRequest req) throws IOException {
		int length = req.getContentLength();
		if (length <= 0) {
			return null;
		} else {
			ServletInputStream is = req.getInputStream();
			byte[] buffer = new byte[length];

			for (int pad = 0; pad < length; pad += is.read(buffer, pad, length)) {
				;
			}
			return new String(buffer, "UTF-8");
		}
	}

}
