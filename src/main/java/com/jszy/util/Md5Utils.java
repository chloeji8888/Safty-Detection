package com.jszy.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

public class Md5Utils {
	private static final Logger log = Logger.getLogger(Md5Utils.class);

	public Md5Utils() {
	}

	public static final String MD5(String s) {
		char[] hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
				'F' };

		try {
			byte[] btInput = s.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;

			for (int i = 0; i < j; ++i) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 15];
				str[k++] = hexDigits[byte0 & 15];
			}

			return new String(str);
		} catch (Exception var10) {
			log.error(var10 + var10.getMessage(), var10);
			return null;
		}
	}
}
