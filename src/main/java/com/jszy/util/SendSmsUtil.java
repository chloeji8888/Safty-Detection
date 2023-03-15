package com.jszy.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SendSmsUtil {

	private final static String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
	private final static String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
	private final static String accessKeyId = "";// 你的accessKeyId,参考本文档步骤2
	private final static String accessKeySecret = "";// 你的accessKeySecret，参考本文档步骤2
	private final static String smsCode = "";

	public static void sendValidate(String phone, int validateCode) throws Exception {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		SendSmsRequest request = new SendSmsRequest();
		request.setMethod(MethodType.POST);
		request.setPhoneNumbers(phone);
		request.setSignName("无锡终身学习");
		request.setTemplateCode(smsCode);
		request.setTemplateParam("{\"code\": \"" + validateCode + "\"}");
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		}
	}

}
