package org.jeecgframework.core.interceptors;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.jeecgframework.core.util.XSSUtils;
import org.springframework.web.servlet.HandlerMapping;

public class XssFilterWrapper extends HttpServletRequestWrapper {

	public XssFilterWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 对参数处理 get 提交时候，防止站点脚本注入
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		return XSSUtils.cleanXSS(value, String.class);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		HashMap<String, String[]> newMap = new HashMap<>();
		Map<String, String[]> maps = super.getParameterMap();
		for (String key : maps.keySet()) {
			String[] values = super.getParameterValues(key);
			if (values != null) {
				int length = values.length;
				String[] escapseValues = new String[length];
				for (int i = 0; i < length; i++) {
					escapseValues[i] = XSSUtils.cleanXSS(values[i], String.class);
				}
				newMap.put(XSSUtils.cleanXSS(key, String.class), escapseValues);
			} else {
				newMap.put(XSSUtils.cleanXSS(key, String.class), maps.get(key));
			}
		}
		return Collections.unmodifiableMap(newMap);
	}

	/**
	 * 对数值进行处理 get post 提交时候 效验参数
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public String[] getParameterValues(String name) {
//		if ("content".equals(name)) {// 不想过滤的参数，此处content参数是 富文本内容
//			return super.getParameterValues(name);
//		}
		String[] values = super.getParameterValues(name);
		if (values != null) {
			int length = values.length;
			String[] escapseValues = new String[length];
			for (int i = 0; i < length; i++) {
				escapseValues[i] = XSSUtils.cleanXSS(values[i], String.class);
			}
			return escapseValues;
		}
		return super.getParameterValues(name);
	}

	// 请求头里过滤 提交时候 效验表单,防止站点脚本注入
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		return XSSUtils.cleanXSS(value, String.class);
	}

	/**
	 * 主要是针对HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
	 * 获取pathvalue的时候把原来的pathvalue经过xss过滤掉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getAttribute(String name) {
		// 获取pathvalue的值
		if (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE.equals(name)) {
			Map uriTemplateVars = (Map) super.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			if (Objects.isNull(uriTemplateVars)) {
				return uriTemplateVars;
			}
			Map newMap = new LinkedHashMap<>();
			for (Object key : uriTemplateVars.keySet()) {
				Object value = uriTemplateVars.get(key);
				if (value instanceof String) {
					newMap.put(XSSUtils.cleanXSS((String) key, String.class),
							XSSUtils.cleanXSS((String) value, String.class));
				} else {
					newMap.put(XSSUtils.cleanXSS((String) key, String.class), value);

				}
			}
			return newMap;
		} else {
			return super.getAttribute(name);
		}
	}

}
