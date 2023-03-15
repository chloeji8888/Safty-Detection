package com.jszy.safetyDetection.syncView;

/**
 * @author wls
 */
public class ReturnInfo {

	public static final String SUCCESS = "1";
	public static final String ERROR = "0";

	/** 工单ID */
	private String formId;

	/** 是否操作成功 */
	private String operCode;

	/** 工操作异常信息 */
	private String operMsg;
	private String originFormId;

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperMsg() {
		return operMsg;
	}

	public void setOperMsg(String operMsg) {
		this.operMsg = operMsg;
	}

	public String getOriginFormId() {
		return originFormId;
	}

	public void setOriginFormId(String originFormId) {
		this.originFormId = originFormId;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{").append("\"formId\":").append(formId).append(",").append("\"operCode\": ").append(operCode)
				.append(",").append("\"operMsg\": ").append(operMsg).append(" }");
		return s.toString();
	}
}
