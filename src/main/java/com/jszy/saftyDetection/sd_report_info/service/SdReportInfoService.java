package com.jszy.safetyDetection.sd_report_info.service;

import com.jszy.safetyDetection.sd_report_info.entity.SdReportInfoEntity;
import com.jszy.safetyDetection.sd_user_info.entity.SdUserInfoEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SdReportInfoServiceI extends CommonService {

	public void delete(SdReportInfoEntity entity) throws Exception;

	public Serializable save(SdReportInfoEntity entity) throws Exception;

	public void saveOrUpdate(SdReportInfoEntity entity) throws Exception;

	public Map<String, Object> populationMap(SdReportInfoEntity entity);

	public List<Map<String, Object>> populationMaps(List<SdReportInfoEntity> list);

	public List<Map<String, Object>> queryListByUser(String openId);

	public Map<String, Object> queryReportDetail(String reportId);

	public void saveByWechat(SdUserInfoEntity userInfo, String areaId, String hiddenDangerPlace,
			String hiddenDangerDetail, String fileList) throws Exception;

	public void exportPdf(SdReportInfoEntity sdReportInfo, String query_reportTime_begin, String query_reportTime_end,
			HttpServletRequest request, HttpServletResponse response);

	public void syncReport(SdReportInfoEntity report) throws Exception;

	public Map<String, Object> statisticsNum(String beginDate, String endDate);

	public List<Map<String, Object>> statisticsAreaNum(String beginDate, String endDate);

	public void exportXls(SdReportInfoEntity sdReportInfo, String query_reportTime_begin, String query_reportTime_end,
			HttpServletRequest request, HttpServletResponse response);

}
