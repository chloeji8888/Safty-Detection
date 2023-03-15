package com.jszy.util;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Base64Util {
	
	
	/**
	 * base 64加密
	 * @param str
	 * @return
	 */
	public static String encoder(String str) {  
	        byte[] b = null;  
	        String s = null;  
	        try {  
	            b = str.getBytes("utf-8");  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	        if (b != null) {  
	            s = new BASE64Encoder().encode(b);  
	        }  
	        return s;  
	    }  
	  
	    /**
	     * base64 解密
	     * @param s
	     * @return
	     */
	    public static String decoder(String s) {  
	        byte[] b = null;  
	        String result = null;  
	        if (s != null) {  
	            BASE64Decoder decoder = new BASE64Decoder();  
	            try {  
	                b = decoder.decodeBuffer(s);  
	                result = new String(b, "utf-8");  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result;  
	    }

	    public static byte[] decoderByte(String s) {  
	        byte[] b = null;  
	        if (s != null) {  
	            BASE64Decoder decoder = new BASE64Decoder();  
	            try {  
	                b = decoder.decodeBuffer(s);  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return b;  
	    }
	    /**
	     * base加码
	     * @param param_bytes
	     * @return
	     */
		public static String encoderByte(byte[] bytes) {
			String s = new BASE64Encoder().encode(bytes);  
			return s;
		}  
}
