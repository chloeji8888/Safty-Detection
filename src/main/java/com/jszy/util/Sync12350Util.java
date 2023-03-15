package com.jszy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.PropertiesUtil;

import com.alibaba.fastjson.JSONObject;
import com.jszy.safetyDetection.sd_report_info.entity.SdReportInfoEntity;
import com.jszy.safetyDetection.syncView.ApiToken;
import com.jszy.safetyDetection.syncView.ApiTokenResponse;
import com.jszy.safetyDetection.syncView.InterSystem;
import com.jszy.safetyDetection.syncView.ResponseData;
import com.jszy.safetyDetection.syncView.ReturnInfo;
import com.jszy.safetyDetection.syncView.ReturnInfoResponse;
import com.jszy.safetyDetection.syncView.SyncOrder;
import com.jszy.safetyDetection.syncView.SyncOrderFiles;

@SuppressWarnings("unchecked")
public class Sync12350Util {
	private static PropertiesUtil util = new PropertiesUtil("sysConfig.properties");

	private static String appId = util.readProperty("12350AppId");
	private static String appKey = util.readProperty("12350AppKey");
	private static String watingUrl = util.readProperty("12350WatingUrl");
// ----------测试信息
//	private static String appId = "abee61161d6349609245fb3e7cdfad34";
//	private static String appKey = "jshx12345";
//	private static String watingUrl = "http://218.90.225.42:18084/tz12345/api/";

	// token获取
	public static ApiToken getToken() {
		ApiToken apiToken = new ApiToken();
		long timestamp = System.currentTimeMillis();
		JSONObject json = new JSONObject();
		json.put("appId", appId);
		json.put("timestamp", String.valueOf(timestamp));
		json.put("sign", getTokenSign(appId, timestamp, appKey));
		String urlString = watingUrl + "token";
		try {
			String content = HttpClient.post(urlString, json.toString());
			ResponseData<ApiTokenResponse> responseData = JSONObject.parseObject(content, ResponseData.class);
			ApiTokenResponse apiTokenResponse = JSONObject
					.parseObject(JSONObject.toJSONString(responseData.getResData()), ApiTokenResponse.class);
			InterSystem system = apiTokenResponse.getSystem();
			apiToken = apiTokenResponse.getApiToken();
			if (system != null && "1".equals(system.getCode())) {
				return apiToken;
			} else {
				System.err.println("12350获取token失败：：：：：：：：：：：：：" + content);
			}
		} catch (Exception e) {
			System.err.println("12350获取token失败：：：：：：：：：：：：：" + e.getMessage());
		}
		return apiToken;
	}

	public static String getTokenSign(String appId, long timestamp, String appKey) {
		return Md5Utils.MD5(appId + timestamp + appKey);
	}

	// 上报诉求提交
	public static ReturnInfo netWorkOrder(String token, SdReportInfoEntity reportInfo) {
		SyncOrder syncOrder = formateToSyncOrder(reportInfo);
		ReturnInfo returnInfo = new ReturnInfo();
		long timestamp = System.currentTimeMillis();
		// 获取验签
		String sign = getNetWorkOrderSign(token, timestamp, syncOrder);
		// 拼接数据
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> reqData = new HashMap<String, Object>();
		reqData.put("paras", JSONObject.parseObject(JSONObject.toJSONString(syncOrder), Map.class));
		data.put("reqData", reqData);
		String urlString = watingUrl + "report/netWorkOrder";
		JSONObject data2 = new JSONObject(data);
		System.out.println("data2======" + data2.toString());
		System.out.println("sign======" + sign);
		System.out.println("token======" + token);
		String content = HttpClient.postAddToken(urlString, data2.toString(), token, String.valueOf(timestamp), sign);
		ResponseData<ReturnInfoResponse> responseData = JSONObject.parseObject(content, ResponseData.class);
		ReturnInfoResponse returnInfoResponse = JSONObject
				.parseObject(JSONObject.toJSONString(responseData.getResData()), ReturnInfoResponse.class);
		InterSystem system = returnInfoResponse.getSystem();
		returnInfo = returnInfoResponse.getReturnInfo();
		if (system != null && "1".equals(system.getCode())) {
			return returnInfo;
		} else {
			System.err.println("12350上报诉求提交失败：：：：：：：：：：：：：" + content);
		}
		return returnInfo;
	}

	public static String getNetWorkOrderSign(String token, long timestamp, SyncOrder syncOrder) {
		return Md5Utils.MD5(syncOrder.toSignString() + token + timestamp + appKey);
	}

	private static SyncOrder formateToSyncOrder(SdReportInfoEntity reportInfo) {
		SyncOrder syncOrder = new SyncOrder();
		syncOrder.setFormOrigin("WX");
		syncOrder.setContentText("12350工单：\n" + "诉求地址：" + reportInfo.getHiddenDangerPlace() + "\n诉求内容："
				+ reportInfo.getHiddenDangerDetail());
		syncOrder.setCusType("1");
		String whConPerInfo = "0";
		String cusName = "市民";
		String cusPhone = "052312350";
		if (reportInfo.getUserInfo() != null && StringUtils.isNotEmpty(reportInfo.getUserInfo().getPhone())) {
			cusPhone = reportInfo.getUserInfo().getPhone();
		}
		if (reportInfo.getUserInfo() != null && StringUtils.isNotEmpty(reportInfo.getUserInfo().getUserName())) {
			cusName = reportInfo.getUserInfo().getUserName();
		}
		syncOrder.setCusName(cusName);
		syncOrder.setCusPhone(cusPhone);
		syncOrder.setWhConPerInfo(whConPerInfo);
		if (reportInfo.getUserInfo() != null && StringUtils.isNotEmpty(reportInfo.getUserInfo().getAddress())) {
			syncOrder.setCusAddress(reportInfo.getUserInfo().getAddress());
		}
		syncOrder.setBusiAddress(reportInfo.getHiddenDangerPlace());
		// 附件
		String fileList = reportInfo.getFileList();
		if (StringUtils.isNotEmpty(fileList)) {
			List<SyncOrderFiles> formAttachList = new ArrayList<SyncOrderFiles>();
			String[] filePath = fileList.split("\\|");
			for (String s : filePath) {
				SyncOrderFiles entity = new SyncOrderFiles();
				entity.setFilePath(util.readProperty("httpUrl") + s);
				entity.setAttachName(s.substring(s.lastIndexOf("/") + 1));
				formAttachList.add(entity);
			}
			syncOrder.setFormAttachList(formAttachList);
		}
		return syncOrder;
	}

	public static void main(String[] args) {
		String syncOrder = "formOrigin:WX,contentText:12350工单：塘湾卫生院对面，电信局一电线杆根部断了裂倾斜存在安全隐患,cusType:1,cusName:匿名,cusPhone:15161062223,whConPerInfo:1,busiAddress:江苏省泰州市海陵区塘振线,attachName:402856be7921fc280179259657120008.jpg,filePath:http://8.189.62.146/upload/20210501/402856be7921fc280179259657120008.jpg,attachName:402856be7921fc280179259657ce0009.jpg,filePath:http://8.189.62.146/upload/20210501/402856be7921fc280179259657ce0009.jpg";
		String token = "292a112889c84400974cb85482b42096";
		long timestamp = 1620380169250L;
		System.err.println("str==========" + syncOrder + token + timestamp + "tz12345_12350app");
		System.out.println(Md5Utils.MD5(syncOrder + token + timestamp + "tz12345_12350app"));
	}
}
