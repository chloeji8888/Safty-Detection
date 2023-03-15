package org.jeecgframework.core.interceptors;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 字符集拦截器
 * 
 * @author 张代浩
 * 
 */
public class EncodingInterceptor implements HandlerInterceptor {

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			if (!needCheck(name)) {
				String[] values = request.getParameterValues(name);
				for (String value : values) {
					// sql注入直接拦截
					if (judgeSQLInject(value.toLowerCase())) {
						response.setContentType("text/html;charset=UTF-8");
						response.getWriter().print("参数含有非法攻击字符,已禁止继续访问！");
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean needCheck(String name) {
		String unNeedCheckStr = "footer,sort,field,treefield,results,sqlbuilder";
		Set<String> set = new HashSet<String>(Arrays.asList(unNeedCheckStr.split(",")));
		if(set.contains(name))
			return true;
		return false;
	}

	/**
	 * 判断参数是否含有攻击串
	 * 
	 * @param value
	 * @return
	 */
	public boolean judgeSQLInject(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		String xssStr = "master|insert|select|update|delete|drop|truncate|alert|declare|create";
		String[] xssArr = xssStr.split("\\|");
		for (int i = 0; i < xssArr.length; i++) {
			if (value.indexOf(xssArr[i]) > -1) {
				return true;
			}
		}
		return false;
	}

}
