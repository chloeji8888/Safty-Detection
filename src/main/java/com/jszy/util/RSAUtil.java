package com.jszy.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

@SuppressWarnings("unused")
public class RSAUtil {

	public static final String KEY_ALGORTHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static final String PUBLIC_KEY = "RSAPublicKey";// 公钥
	public static final String PRIVATE_KEY = "RSAPrivateKey";// 私钥

	public Map<String, String> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator
				.getInstance(KEY_ALGORTHM);
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, String> keyMap = new HashMap<String, String>();

		keyMap.put(PUBLIC_KEY, StringUtil.formatNoSTRN(getPublicKey(publicKey)));
		keyMap.put(PRIVATE_KEY,
				StringUtil.formatNoSTRN(getPrivateKey(privateKey)));

		System.out.println("公钥" + keyMap.get(PUBLIC_KEY));
		System.out.println("私钥" + keyMap.get(PRIVATE_KEY));
		return keyMap;
	}

	/**
	 * 取得公钥，并转化为String类型
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public String getPublicKey(RSAPublicKey publicKey) throws Exception {
		Key key = (Key) publicKey;
		return Base64Util.encoderByte(key.getEncoded());
	}

	/**
	 * 取得私钥，并转化为String类型
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public String getPrivateKey(RSAPrivateKey privateKey) throws Exception {
		Key key = (Key) privateKey;
		return Base64Util.encoderByte(key.getEncoded());
	}

	/**
	 * 用私钥加密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String content, String key)
			throws Exception {
		List<String> array = new ArrayList<String>();
		int length = 29;
		while (length < content.length()) {
			array.add(content.substring(0, length));
			content = content.substring(length, content.length());
		}
		array.add(content);
		String returnstr = "";
		for (String sub : array) {
			byte[] data = sub.getBytes("UTF-8");
			// 解密密钥
			byte[] keyBytes = Base64Util.decoderByte(key);
			// 取私钥
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
					keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			byte[] returndate = cipher.doFinal(data);
			returnstr = returnstr
					+ StringUtil.formatNoSTRN(Base64Util
							.encoderByte(returndate)) + ",";
		}
		if (returnstr.endsWith(",")) {
			returnstr = returnstr.substring(0, returnstr.lastIndexOf(","));
		}
		return returnstr;
	}

	/**
	 * 用私钥解密 停用
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	private byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对私钥解密
		byte[] keyBytes = Base64Util.decoderByte(key);

		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 用公钥加密 停用
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	private byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		// 对公钥解密
		byte[] keyBytes = Base64Util.decoderByte(key);
		// 取公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 用公钥解密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String content, String key)
			throws Exception {
		// 对私钥解密
		String[] array = content.split(",");
		String returnstr = "";

		for (String sub : array) {
			byte[] data = Base64Util.decoderByte(sub);
			byte[] keyBytes = Base64Util.decoderByte(key);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
					keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
			Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			// 对数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] rtndate = cipher.doFinal(data);
			String temp_str = new String(rtndate, "UTF-8");
			returnstr = returnstr + temp_str;
		}

		return returnstr;
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            //加密数据
	 * @param privateKey
	 *            //私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(String content, String privateKey)
			throws Exception {
		byte[] data = content.getBytes("UTF-8");
		// 解密私钥
		byte[] keyBytes = Base64Util.decoderByte(privateKey);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				keyBytes);
		// 指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		// 取私钥匙对象
		PrivateKey privateKey2 = keyFactory
				.generatePrivate(pkcs8EncodedKeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateKey2);
		signature.update(data);
		String sign = StringUtil.formatNoSTRN(Base64Util.encoderByte(signature
				.sign()));
		return sign;
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String content, String publicKey, String sign)
			throws Exception {
		byte[] data = content.getBytes("UTF-8");
		// 解密公钥
		byte[] keyBytes = Base64Util.decoderByte(publicKey);
		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		// 指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		// 取公钥匙对象
		PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicKey2);
		signature.update(data);
		// 验证签名是否正常
		return signature.verify(Base64Util.decoderByte(sign));

	}

	public static void main(String[] args) throws Exception {
		RSAUtil util = new RSAUtil();
		Map<String, String> map = util.initKey();

		/*
		 * String content = "asdfasdf速度发放 是否是的发送到是否 ~！@#￥%……&*（）——阿龠 龡 龢 龣 龤 龥"
		 * + "fadsfasdfadf撒的发生的发生的发的所发生的发送到发送到发送" + "阿萨德法师打发斯蒂芬阿萨德法师打发斯蒂芬" +
		 * "asdfasdfasdfasdf " + "asdfasdfasdfasdfasdfasdfa  asdf阿斯顿发的说法的" +
		 * "到发送到非+·1234567890- 就发生" +
		 * "的纠纷氨基酸的会计法卡开始的缴费卡接口是大家分开就暗示的卡时间的反馈及爱上对方就卡死的风景"; String mm =
		 * RSAUtil.encryptByPrivateKey(content, map.get(RSAUtil.PRIVATE_KEY));
		 * System.out.println("---------"+mm); String nn =
		 * RSAUtil.decryptByPublicKey(mm, map.get(RSAUtil.PUBLIC_KEY));
		 * System.out.println("---------"+nn);
		 */
	}

}