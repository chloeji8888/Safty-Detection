package com.jszy.threadPag;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ResourceUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jszy.safetyDetection.sd_report_info.entity.SdReportInfoEntity;
import com.jszy.safetyDetection.syncView.ApiToken;
import com.jszy.safetyDetection.syncView.ReturnInfo;
import com.jszy.util.Sync12350Util;

public class SyncReportThread implements Runnable {

	private Logger logger = Logger.getLogger(this.getClass());
	private Thread t;
	private SdReportInfoEntity report;
	private String fileList;

	public SyncReportThread(SdReportInfoEntity report, String fileList) {
		this.report = report;
		this.fileList = fileList;
	}

	@Override
	public void run() {
		logger.info("==============同步隐患至12350==============");
		ApiToken apiToken = ResourceUtil.token12350;
		if (apiToken == null || StringUtils.isEmpty(apiToken.getToken())) {
			// 放入全局缓存
			apiToken = Sync12350Util.getToken();
			ResourceUtil.token12350 = apiToken;
		}
		List<String> filePath = new ArrayList<String>();
		if (StringUtils.isNotEmpty(fileList)) {
			JSONArray jsonA = JSONArray.parseArray(fileList);
			for (int i = 0; i < jsonA.size(); i++) {
				JSONObject json = jsonA.getJSONObject(i);
				filePath.add(json.getString("media_url"));
			}
			report.setFileList(StringUtils.join(filePath, "|"));
		}
		try {
			ReturnInfo returnInfo = Sync12350Util.netWorkOrder(apiToken.getToken(), report);
			report.setFormId(returnInfo.getFormId());
			report.setIsSync("1");
			String service = "sdReportInfoService";
			// 获取执行方法
			Object object = ApplicationContextUtil.getBean(service);
			Method method = object.getClass().getMethod("saveOrUpdate", SdReportInfoEntity.class);
			method.invoke(object, report);
		} catch (Exception e) {
			logger.error("==============同步隐患至12350失败==============" + e.getMessage());
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

}
