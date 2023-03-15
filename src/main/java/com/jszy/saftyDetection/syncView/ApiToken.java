package com.jszy.safetyDetection.syncView;

import java.util.Date;

/**
 * @Description: 对外接口token
 * @Author: wls
 * @CreateDate: 2020/1/7 14:11
 */
public class ApiToken {
	/** token */
	private String token;

	/** 失效时间 */
	private Date expireTime;

	public ApiToken() {

	}

	public ApiToken(String token, Date expireTime) {
		this.token = token;
		this.expireTime = expireTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{").append("\"token\":").append(token).append(",").append("\"expireTime\": ").append(expireTime)
				.append(" }");
		return s.toString();
	}
}
