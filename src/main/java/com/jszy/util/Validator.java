package com.jszy.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验器：利用正则表达式校验邮箱、手机号等
 * 
 * @author chiangho
 * 
 */
public class Validator {
	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE =  "^1[\\d]{10}";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：验证汉字
	 */
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

	/**
	 * 正则表达式：验证URL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * 正则表达式：验证IP地址
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	/**
	 * 正则表达式：验证正数
	 */
	public static boolean isNum(String num) {
		if (num.matches("^\\d+$")) {
			return true;
		}
		if (num.matches("^\\d+(\\.\\d+)?$")) {
			return true;
		}
		return false;
	}

	/**
	 * 正则表达式：验证数字
	 */
	public static boolean isNumeric(String str) {
		Boolean strResult = str.matches("-?[0-9]+.*[0-9]*");
		return strResult;
	}

	/**
	 * 正则表达式：验证数字（正数、0）
	 */
	public static boolean isPositiveNumericAndZero(String str) {
		Boolean strResult = str.matches("^\\d+(\\.\\d+)?$");
		return strResult;
	}

	/**
	 * 正则表达式：验证数字（正数）
	 */
	public static boolean isPositiveNumeric(String str) {
		Boolean strResult = str
				.matches("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
		return strResult;
	}

	/**
	 * 判断是正整数
	 * 
	 * @param input
	 * @return true是
	 */
	public static boolean isPositiveInteger(String input) {
		Matcher mer = Pattern.compile("^\\d*[1-9]\\d*$").matcher(input);
		return mer.find();
	}

	/**
	 * 判断是整数数字（正整数、0、负整数）
	 * 
	 * @param input
	 * @return true是
	 */
	public static boolean isInteger(String input) {
		Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
		return mer.find();
	}

	/**
	 * 判断是整数数字（正整数、0）
	 * 
	 * @param input
	 * @return true是
	 */
	public static boolean isPositiveAndZeroInteger(String input) {
		Matcher mer = Pattern.compile("^[+]?[0-9]+$").matcher(input);
		return mer.find();
	}

	/**
	 * 校验用户名
	 * 
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUsername(String username) {
		return Pattern.matches(REGEX_USERNAME, username);
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {
		return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验汉字
	 * 
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChinese(String chinese) {
		return Pattern.matches(REGEX_CHINESE, chinese);
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		if (idCard.length() == 15 || idCard.length() == 18) {
			return IdcardValidator.isValidatedAllIdcard(idCard);
		}
		return false;
	}

	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String url) {
		return Pattern.matches(REGEX_URL, url);
	}

	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr(String ipAddr) {
		return Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}

	// /**
	// * 校验是否为时间格式的字符串
	// * yyyy/MM/dd HH:mm:ss
	// * yyyy-MM-dd HH:mm:ss
	// *
	// * @param timeStr
	// * @return
	// */
	// public static boolean isValidDate(String timeStr) {
	// return DateFormatInfo.isValidDate(timeStr);
	// }
	//
	/**
	 * 校验2个数 b1 >= b2
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isBigger(BigDecimal b1, BigDecimal b2) {
		int result = b1.compareTo(b2);
		if (result == -1)
			return false;
		return true;
	}

	/**
	 * 校验2个数 b1 <= b2
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isSmaller(BigDecimal b1, BigDecimal b2) {
		int result = b1.compareTo(b2);
		if (result == 1)
			return false;
		return true;
	}

	public static void main(String[] args) {
		String username = "fdsdfsdj";
		System.out.println(Validator.isUsername(username));
		System.out.println(Validator.isChinese(username));
	}
}
