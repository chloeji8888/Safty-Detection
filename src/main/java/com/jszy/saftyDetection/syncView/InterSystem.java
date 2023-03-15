package com.jszy.safetyDetection.syncView;

/**
 * @author wls
 */
public class InterSystem {
	public static final String SUCCESS = "1";
	public static final String ERROR_SERVER = "2";
	public static final String ERROR_SYSTEM = "3";

	/**
	 * 请求头参数不完整错误代码
	 */
	public static final String ERROR_REQUEST_HEADER = "1000";

	/**
	 * token失效错误代码
	 */
	public static final String ERROR_TOKEN = "1001";
	/**
	 * 数据签名错误代码
	 */
	public static final String ERROR_SIGN = "1002";

	/**
	 * 时间戳不正确（小于系统当前时间）
	 */
	public static final String ERROR_TIME_STAMP = "1003";

	/**
	 * 请求超时（时间戳大于系统设置的阈值）
	 */
	public static final String ERROR_TIME_OUT = "1004";

	/**
	 * 请求过于频繁错误代码
	 */
	public static final String ERROR_REQUEST_MORE = "2000";

	/**
	 * 请求参数不正确代码
	 */
	public static final String ERROR_REQUEST_PARAMETER = "3000";

	/**
	 * 数据重复提交错误代码
	 */
	public static final String ERROR_REQUEST_SAME = "4000";

	private String code;
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{").append("\"code\":").append(code).append(",").append("\"msg\": ").append(msg).append(" }");
		return s.toString();
	}
}
