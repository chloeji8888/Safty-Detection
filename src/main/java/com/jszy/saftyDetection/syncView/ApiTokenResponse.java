package com.jszy.safetyDetection.syncView;

/**
 * @author wls
 */
public class ApiTokenResponse {

	private InterSystem system;
	private ApiToken apiToken;

	public InterSystem getSystem() {
		return system;
	}

	public void setSystem(InterSystem system) {
		this.system = system;
	}

	public ApiToken getApiToken() {
		return apiToken;
	}

	public void setApiToken(ApiToken apiToken) {
		this.apiToken = apiToken;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{").append("\"system\":").append(null == system ? "" : system.toString()).append(",")
				.append("\"busiForm\": ").append(null == apiToken ? "" : apiToken.toString()).append(" }");
		return s.toString();
	}

}
