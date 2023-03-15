package com.jszy.safetyDetection.syncView;

/**
 * @author wls
 */
public class ReturnInfoResponse {

	private InterSystem system;
	private ReturnInfo returnInfo;

	public InterSystem getSystem() {
		return system;
	}

	public void setSystem(InterSystem system) {
		this.system = system;
	}

	public ReturnInfo getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(ReturnInfo returnInfo) {
		this.returnInfo = returnInfo;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{").append("\"system\":").append(null == system ? "" : system.toString()).append(",")
				.append("\"busiForm\": ").append(null == returnInfo ? "" : returnInfo.toString()).append(" }");
		return s.toString();
	}

}
