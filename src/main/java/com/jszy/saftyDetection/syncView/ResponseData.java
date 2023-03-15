package com.jszy.safetyDetection.syncView;

import java.io.Serializable;

/**
 * @Description: 对外接口返回参数实体类
 * @Author: wls
 * @CreateDate: 2019/12/5 15:11
 */
@SuppressWarnings("serial")
public class ResponseData<T> implements Serializable {

	T resData;

	public T getResData() {
		return resData;
	}

	public void setResData(T resData) {
		this.resData = resData;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (null != resData) {
			s.append("{").append("\"resData\":").append(resData.toString()).append(" }");
		} else {
			s.append("{").append("\"resData\":").append(" }");
		}
		return s.toString();
	}
}
