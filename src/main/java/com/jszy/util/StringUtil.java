package com.jszy.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtil {

	private static Logger log = Logger.getLogger(StringUtil.class);

	public static final String lpad(String s, char c, int len) {
		String sRet = s;
		while (sRet.length() < len) {
			sRet = c + sRet;
		}
		return sRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] split(String str, String pattern) {
		if (str == null) {
			str = "";
		}
		Vector strset = new Vector();
		int s = 0;
		int e = 0;
		while ((e = str.indexOf(pattern, s)) >= 0) {
			strset.addElement(str.substring(s, e));
			s = e + pattern.length();
		}
		if (s != str.length()) {
			strset.addElement(str.substring(s, str.length()));
		} else if (s == 0) {
			strset.addElement("");
		} else {
			strset.addElement("");
		}
		int len = strset.size();
		String[] result = new String[len];
		for (int i = 0; i < len; i++) {
			result[i] = ((String) strset.elementAt(i));
		}
		return result;
	}

	public static final String replace(String str, String sPattern,
			String sReplaceBy) {
		if (str == null) {
			return "";
		}
		int s = 0;
		int e = 0;
		StringBuffer bufRet = new StringBuffer();
		while ((e = str.indexOf(sPattern, s)) >= 0) {
			bufRet.append(str.substring(s, e));
			bufRet.append(sReplaceBy);
			s = e + sPattern.length();
		}
		bufRet.append(str.substring(s));
		return bufRet.toString();
	}

	/**
	 * 取得字符串string按照规则regex匹配的Matcher对象
	 * 
	 * @return Matcher
	 */
	public static Matcher getMatcher(String regex, String string) {
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(string);
	}

	/**
	 * 检测字符串string是否全为字母或数字
	 * 
	 * @param string
	 * @return true:全为字母或数字
	 */
	public static boolean isCharacters(String string) {
		String regex = "^\\w+$";
		Matcher matcher = getMatcher(regex, string);
		while (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化字符串, str为null或者"undefined"时返回 "", 不检查零长度字符串
	 * (当页面异步请求而没有传后台所取的参数,后台取得的值为"undefined")
	 * 
	 * @param str
	 *            : 格式化的字符串
	 * @return String : 格式化后的字符串,为空时返回零长度字符串""
	 */
	public static String nullValue(String str) {
		return nullValue(str, "", false);
	}

	/**
	 * 格式化字符串, str为null或者"undefined"时返回 "", checkZeroLength为true时检查字符串是否零长度
	 * (当页面异步请求而没有传后台所取的参数,后台取得的值为"undefined")
	 * 
	 * @param str
	 *            : 格式化的字符串
	 * @return String : 格式化后的字符串,为空时返回零长度字符串""
	 */
	public static String nullValue(String str, boolean checkZeroLength) {
		return nullValue(str, "", checkZeroLength);
	}

	/**
	 * 格式化字符串, 为null或者"undefined"时返回指定值nullValue, 不检查零长度字符串
	 * (当页面异步请求而没有传后台所取的参数,后台取得的值为"undefined")
	 * 
	 * @param str
	 *            : 格式化的字符串
	 * @param nullValue
	 *            : value为null或者"undefined"时返回值
	 * @return String : 格式化后的字符串
	 */
	public static String nullValue(String str, String nullValue) {
		if (str != null && !"undefined".equals(str) && !"null".equals(str)) {
			return str.trim();
		}
		return nullValue;
	}

	/**
	 * checkZeroLength为true时检查字符串是否零长度
	 * 
	 * @param str
	 * @param nullValue
	 * @param checkZeroLength
	 * @return
	 */
	public static String nullValue(String str, String nullValue,
			boolean checkZeroLength) {
		if (str != null && !"undefined".equals(str) && !"null".equals(str)
				&& (!checkZeroLength || !"".equals(str.trim()))) {
			return str.trim();
		}
		return nullValue;
	}

	/**
	 * 格式化字符串, 不为null或者"undefined"时返回指定值notNullValue，否则返回nullValue。
	 * (当页面异步请求而没有传后台所取的参数,后台取得的值为"undefined")
	 * 
	 * @param str
	 *            : 格式化的字符串
	 * @param notNullValue
	 *            : value非空时返回值
	 * @param nullValue
	 *            : value为空时返回值
	 * @return String : 格式化后的字符串
	 */
	public static String nullValue(String str, String notNullValue,
			String nullValue) {
		if (str != null && !"undefined".equals(str)) {
			return notNullValue;
		}
		return nullValue;
	}

	/**
	 * 格式化字符串为整数, 非整数时返回0
	 * 
	 * @param value
	 *            : 格式化的字符串 return int : 格式化后的整数
	 */
	public static long parseInt(String value) {
		return parseInt(value, 0);
	}

	/**
	 * 格式化字符串为整数, 非整数时返回指定值sDefault
	 * 
	 * @param value
	 *            : 格式化的字符串
	 * @param nDefault
	 *            : 非整数时返回值 return int : 格式化后的整数
	 */
	public static long parseInt(String value, int nDefault) {
		if (value != null && value.trim().length() > 0) {
			String svalue = value.trim();
			svalue = (svalue.charAt(0) == '-' ? svalue.substring(1) : svalue);
			String regex = "^\\d+$";
			Matcher matcher = getMatcher(regex, svalue);
			while (matcher.find()) {
				return Integer.parseInt((value.charAt(0) == '-' ? "-" : "")
						+ matcher.group());
			}
		}
		return nDefault;
	}

	/**
	 * 判断value值是否为数字
	 * 
	 * @param value
	 *            : 格式化的字符串
	 * @param nDefault
	 *            : 非整数时返回值 return int : 格式化后的整数
	 */
	public static boolean isNumber(String value) {
		if (value != null && value.trim().length() > 0) {
			value = value.trim();
			value = (value.charAt(0) == '-' ? value.substring(1) : value);
			String regex = "^\\d+$";
			Matcher matcher = getMatcher(regex, value);
			while (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 格式化字符串为小数, 非小数时返回0
	 * 
	 * @param value
	 *            : 格式化的字符串 return float : 格式化后的小数
	 */
	public static float parseFloat(String value) {
		return parseFloat(value, 0);
	}

	/**
	 * 格式化字符串为小数, 非小数时返回指定值fDefault
	 * 
	 * @param value
	 *            : 格式化的字符串
	 * @param fDefault
	 *            : 非小数时返回值 return float : 格式化后的小数
	 */
	public static float parseFloat(String value, float fDefault) {
		if (value != null && value.trim().length() > 0) {
			String svalue = value.trim();
			svalue = (svalue.charAt(0) == '-' ? svalue.substring(1) : svalue);
			String regex = "^\\d+([.]\\d+)?$";
			Matcher matcher = getMatcher(regex, svalue);
			while (matcher.find()) {
				return Float.parseFloat((value.charAt(0) == '-' ? "-" : "")
						+ matcher.group());
			}
		}
		return fDefault;
	}

	/**
	 * 截断字符串string到3000字节
	 * 
	 * @param string
	 *            : 需截断的字符串
	 * @return String : 截断后的字符串
	 */
	public static String truncate(String string) {
		return truncate(string, 3000);
	}

	/**
	 * 截断字符串string到指定长度length(字节)
	 * 
	 * @param string
	 *            : 截断的字符串
	 * @param length
	 *            : 截断的字节数
	 * @return String : 截断后的字符串
	 */
	public static String truncate(String string, int length) {
		return truncate(string, length, "");
	}

	/**
	 * 截断字符串string到指定长度length(字节),后面可以补...
	 * 
	 * @param string
	 * @param length
	 * @param sTail
	 * @return
	 */
	public static String truncate(String string, int length, String sTail) {
		try {
			if (string == null || string.trim().length() == 0) {
				return "";
			}
			byte[] bytes = string.getBytes("Unicode");
			int n = 0; // 表示当前的字节数
			int i = 2; // 要截取的字节数，从第3个字节开始

			for (; i < bytes.length && n < length; i++) {
				// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
				if (i % 2 == 1) {
					n++; // 在UCS2第二个字节时n加1
				} else {
					// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
					if (bytes[i] != 0) {
						n++;
					}
				}

			}
			// 如果i为奇数时，处理成偶数
			if (i % 2 == 1) {
				// 该UCS2字符是汉字时，去掉这个截一半的汉字
				if (bytes[i - 1] != 0) {
					i = i - 1;
				} else {
					// 该UCS2字符是字母或数字，则保留该字符
					i = i + 1;
				}
			}

			return new String(bytes, 0, i, "Unicode")
					+ (n >= length && bytes.length - 2 > n ? sTail : "");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * 截断字符串string到指定长度length(字节),后面可以补...
	 * 
	 * @param string
	 * @param length
	 * @param sTail
	 * @return
	 */
	public static String truncateTextArea(String string, int length) {
		try {
			byte[] bytes = string.getBytes("Unicode");
			int size = bytes.length / length;
			if (bytes.length % length > 0) {
				size++;
			}
			String leftStr = "";
			String newStr = "";
			for (int k = 0; k < size; k++) {
				int n = 0; // 表示当前的字节数
				int i = 2; // 要截取的字节数，从第3个字节开始

				for (; i < bytes.length && n < length; i++) {
					// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
					if (i % 2 == 1) {
						n++; // 在UCS2第二个字节时n加1
					} else {
						// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
						if (bytes[i] != 0) {
							n++;
						}
					}

				}
				// 如果i为奇数时，处理成偶数
				if (i % 2 == 1) {
					// 该UCS2字符是汉字时，去掉这个截一半的汉字
					if (bytes[i - 1] != 0) {
						i = i - 1;
					} else {
						// 该UCS2字符是字母或数字，则保留该字符
						i = i + 1;
					}
				}

				try {
					String str2 = new String(bytes, 0, i, "Unicode");
					int back = str2.indexOf("\r");
					if (back != -1) {
						back = str2.substring(0, back).getBytes("Unicode").length + 1;
						newStr += new String(bytes, 0, back, "Unicode");
						leftStr = new String(bytes, back + 1, bytes.length
								- (back + 1), "Unicode");
					} else if (bytes[i - 1] == '\\' && bytes[i] == 'r') {
						newStr += new String(bytes, 0, i, "Unicode");
						leftStr = new String(bytes, i + 1, bytes.length
								- (i + 1), "Unicode");
					} else if (bytes[i - 1] == 'r' && bytes[i - 2] == '\\') {
						newStr += new String(bytes, 0, i - 3, "Unicode");
						leftStr = new String(bytes, i, bytes.length - i,
								"Unicode");
					} else if (bytes[i] == '\\' && bytes[i + 1] == 'r') {
						newStr += new String(bytes, 0, i, "Unicode") + "\r";
						leftStr = new String(bytes, i + 1, bytes.length
								- (i + 1), "Unicode");
					} else {
						newStr += new String(bytes, 0, i, "Unicode") + "\r";
						leftStr = new String(bytes, i, bytes.length - i,
								"Unicode");
					}
				} catch (Exception e) {
					newStr += new String(bytes, 0, i, "Unicode") + "\r";
					leftStr = new String(bytes, i, bytes.length - i, "Unicode");
				}

				bytes = leftStr.getBytes("Unicode");
			}
			return newStr;
		} catch (UnsupportedEncodingException e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * 当传入的字符串string长度小于指定值length，在string前面和后面用相同长度的星号补齐至长度length
	 * 
	 * @param nString
	 *            : 字符串结果长度
	 * @param string
	 *            : 处理的字符串
	 */
	public static String prependZero(int length, int num) {
		String string = num + "";
		String newString = "";
		int cutLength = length - string.length();
		for (int i = 0; i < cutLength; i++) {
			newString += "0";
		}
		newString += string;
		return newString;
	}

	/**
	 * 格式化HTML里面的标签元素,去掉标签(
	 * <p>
	 * </p>
	 * )和空格(&nbsp;)等页面标签和文本符号,获得纯文本字符串
	 * 
	 * @param html
	 *            : 格式化的HTML文本
	 * @return String : 格式化后的纯文本字符串
	 */
	public static String formatHtmlToPureText(String html) {

		String regex1 = "<\\w+[^<>]*>|</\\w+>|<\\w+[^<>]*/>";
		Matcher matcher1 = getMatcher(regex1, html);
		while (matcher1.find()) {
			String result = matcher1.group();
			html = html.replace(result, "");
		}
		html = html.replace("&nbsp;", " ");
		html = html.replace("\r\n", "");
		html = html.replace("\r", "");
		html = html.replace("\n", "");
		return html;
	}

	/**
	 * 格式化TextArea文本为HTML文本.将换行从TextArea的\r\n转换为HTML的<br/>
	 * , 空格由" "替换为&nbsp;
	 * 
	 * @param text
	 *            : 格式化的TextArea文本
	 * @return String : 格式化后的HTML文本字符串
	 */
	public static String formatTextAreaToHtml(String text) {
		String newContent = "";
		if (text != null) {
			newContent = text;
			newContent = newContent.replace("\r\n", "<br/>");
			newContent = newContent.replace("\r", "<br/>");
			newContent = newContent.replace("\n", "<br/>");
			newContent = newContent.replace(" ", "&nbsp;");
		}
		return newContent;
	}

	/**
	 * 格式化HTML标签为TextArea文本
	 * 
	 * @param text
	 *            : 格式化的Html文本
	 * @return String : 格式化后的TextArea文本字符串
	 */
	public static String formatHtmlToTextArea(String text) {
		String newContent = "";
		if (text != null) {
			newContent = text;
			newContent = newContent.replace("&NBSP;", " ");
			newContent = newContent.replace("&nbsp;", " ");
			newContent = newContent.replace(" />", "/>");
			newContent = newContent.replace("<br/>", "\n");
			newContent = newContent.replace("<br>", "\n");
			newContent = newContent.replace("<BR>", "\n");
		}
		return newContent;
	}

	/**
	 * 格式化TextArea文本为HTML文本.将换行从TextArea的\r\n转换为HTML的<br/>
	 * , 空格由" "替换为&nbsp;
	 * 
	 * @param text
	 *            : 格式化的TextArea文本
	 * @return String : 格式化后的HTML文本字符串
	 */
	public static String formatTextAreaToHtml(String text, int length) {
		String newContent = "";
		// String str = truncate(text, length);
		if (text != null) {
			newContent = text;
			newContent = newContent.replace("\r\n", "<br/>");
			newContent = newContent.replace("\r", "<br/>");
			newContent = newContent.replace("\n", "<br/>");
			newContent = newContent.replace(" ", "&nbsp;");
		}
		return newContent;
	}

	/**
	 * 格式化字符串。将大写转换为小写，并在前加下划线，如果第一个字母是大写，则前面不加下划线。 ExamPlan --> exam_plan
	 * 
	 * @param str
	 * @return
	 */
	public static String formatUpperCase(String str) {
		String newStr = "";
		if (!str.equals(str.toLowerCase())) {
			char[] aChar = str.toCharArray();
			for (char ch : aChar) {
				if (ch >= 'A' && ch <= 'Z') {
					newStr += (newStr.equals("") ? ("" + ch).toLowerCase()
							: ("_" + ch).toLowerCase());
				} else {
					newStr += ch;
				}
			}
		} else {
			newStr = str;
		}
		return newStr;
	}

	/**
	 * 编码字符串，空格做特殊处理
	 * 
	 * @param str
	 * @param enc
	 * @return
	 */
	public static String encode(String str, String enc) {
		try {
			str = str.replace(" ", "ESPACE");
			str = str.replace(" ", "CSPACE");
			return URLEncoder.encode(str, enc);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * 解码字符串，空格做特殊处理。默认编码为UTF-8
	 * 
	 * @param str
	 * @param enc
	 * @return
	 */
	public static String decode(String str) {
		return decode(str, "UTF-8");
	}

	/**
	 * 解码字符串，空格做特殊处理
	 * 
	 * @param str
	 * @param enc
	 * @return
	 */
	public static String decode(String str, String enc) {
		try {
			if (str != null) {
				String string = new String(str.getBytes("ISO-8859-1"), enc);
				string = string.replace("ESPACE", " ");
				return string.replace("CSPACE", " ");
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * 若字符串str长度不到length，则在最前面以指定的字符sTail填充至长度length
	 * 
	 * @param str
	 * @param length
	 * @param sTail
	 * @return
	 */
	public static String fillFirstToLength(String str, int length, String sTail) {
		int size = str.length();
		if (size >= length) {
			return str;
		}
		length = length - size;
		String newStr = "";
		for (int i = 0; i < length; i++) {
			newStr += sTail;
		}
		newStr += str;
		return newStr;
	}

	/**
	 * 若字符串str长度不到length，则在最后面以指定的字符sTail填充至长度length
	 * 
	 * @param str
	 * @param length
	 * @param sTail
	 * @return
	 */
	public static String fillLastToLength(String str, int length, String sTail) {
		int size = str.length();
		if (size >= length) {
			return str;
		}
		length = length - size;
		String newStr = str;
		for (int i = 0; i < length; i++) {
			newStr += sTail;
		}
		return newStr;
	}

	/**
	 * 获得两个数字相除的结果，保留两位小数
	 * 
	 * @param roofInt
	 * @param floorInt
	 * @return
	 */
	public static String getDivisionResult(Object roofInt, Object floorInt) {
		return getDivisionResult(roofInt, floorInt, 2);
	}

	/**
	 * 获得两个数字相除的结果，保留小数位数:decimalNumber
	 * 
	 * @param roofInt
	 *            -> 被除数
	 * @param floorInt
	 *            -> 除数
	 * @param decimalNumber
	 *            -> 小数位数
	 * @return
	 */
	public static String getDivisionResult(Object roofInt, Object floorInt,
			int decimalNumber) {
		if (decimalNumber < 0) {
			throw new IllegalArgumentException("小数位数必须大于0！");
		}

		BigDecimal b1 = new BigDecimal(roofInt + "");
		BigDecimal b2 = new BigDecimal(floorInt + "");
		return Double.toString(b1.divide(b2, decimalNumber,
				BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	/**
	 * 获得两个数字相除的结果的百分数,保留两位小数
	 * 
	 * @param roofInt
	 *            -> 被除数
	 * @param floorInt
	 *            -> 除数
	 * @param decimalNumber
	 *            -> 小数位数
	 * @return
	 */
	public static String getDivisionPercent(Object roofInt, Object floorInt) {
		return getDivisionPercent(roofInt, floorInt, 2);
	}

	/**
	 * 获得两个数字相除的结果的百分数
	 * 
	 * @param roofInt
	 *            -> 被除数
	 * @param floorInt
	 *            -> 除数
	 * @param decimalNumber
	 *            -> 小数位数
	 * @return
	 */
	public static String getDivisionPercent(Object roofInt, Object floorInt,
			int decimalNumber) {
		String result = getDivisionResult(roofInt, floorInt, decimalNumber);
		String per = Double.valueOf(result) * 100 + "";
		return per.substring(0, per.indexOf("."));
	}

	/**
	 * 截取小数pDouble至小数点后num位
	 * 
	 * @param pDouble
	 * @param num
	 * @return
	 */
	public static double truncateDemical(double pDouble, int num) {
		BigDecimal bd = new BigDecimal(pDouble);
		BigDecimal bd1 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		pDouble = bd1.doubleValue();

		return pDouble;
	}

	/**
	 * 将阿拉伯数字转换为汉字
	 * 
	 * @param int num
	 * @param String
	 * @return
	 */
	public static String getChinese(int num) {
		switch (num) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "七";
		case 8:
			return "八";
		case 9:
			return "九";
		case 10:
			return "十";

		default:
			return "";
		}
	}

	/**
	 * ""
	 * 
	 * @param format
	 *            格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getTime(String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 获取时间长整形
	 * 
	 * @return
	 */
	public static Long getLongTime() {
		return new Date().getTime();
	}

	/**
	 * 替换字符串中的所有空格 换行等
	 * 
	 * @param content
	 * @return
	 */
	public static String formatNoSTRN(String content) {
		if (content != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(content);
			return m.replaceAll("");
		} else {
			return "";
		}
	}
	
	public static String repalceInfo(String str) {
		String[] regs = { "！", "，", "。", "；", "（", "）", "!", ",", ".", ";",
				"(", ")" };
		for (int i = 0; i < regs.length / 2; i++) {
			str = str.replaceAll(regs[i], regs[i + regs.length / 2]);
		}
		str.replaceAll(" ", "");
		return str;
	}
	
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < strLength) {
			sb = new StringBuffer();
			sb.append("0").append(str);// 左补0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}
}