package com.jszy.safetyDetection.sd_report_info.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jszy.safetyDetection.sd_area_info.entity.SdAreaInfoEntity;
import com.jszy.safetyDetection.sd_media_info.entity.SdMediaInfoEntity;
import com.jszy.safetyDetection.sd_media_info.service.SdMediaInfoServiceI;
import com.jszy.safetyDetection.sd_report_info.entity.SdReportInfoEntity;
import com.jszy.safetyDetection.sd_report_info.service.SdReportInfoServiceI;
import com.jszy.safetyDetection.sd_user_info.entity.SdUserInfoEntity;
import com.jszy.safetyDetection.syncView.ApiToken;
import com.jszy.safetyDetection.syncView.ReturnInfo;
import com.jszy.util.DateFormatInfo;
import com.jszy.util.ExportExcelUtil;
import com.jszy.util.ExportPdfUtil;
import com.jszy.util.StringUtil;
import com.jszy.util.Sync12350Util;

@Service("sdReportInfoService")
@Transactional
public class SdReportInfoServiceImpl extends CommonServiceImpl implements SdReportInfoServiceI {

	@Autowired
	private JdbcDao jdbcDao;
	@Autowired
	private SdMediaInfoServiceI sdMediaInfoService;

	public void delete(SdReportInfoEntity entity) throws Exception {
		super.delete(entity);
		// 执行删除操作增强业务
		this.doDelBus(entity);
	}

	public Serializable save(SdReportInfoEntity entity) throws Exception {
		Serializable t = super.save(entity);
		// 执行新增操作增强业务
		this.doAddBus(entity);
		return t;
	}

	public void saveOrUpdate(SdReportInfoEntity entity) throws Exception {
		super.saveOrUpdate(entity);
		// 执行更新操作增强业务
		this.doUpdateBus(entity);
	}

	/**
	 * 新增操作增强业务
	 * 
	 * @param t
	 * @return
	 */
	private void doAddBus(SdReportInfoEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		// -----------------java增强 end-----------------------------
	}

	/**
	 * 更新操作增强业务
	 * 
	 * @param t
	 * @return
	 */
	private void doUpdateBus(SdReportInfoEntity t) throws Exception {

	}

	/**
	 * 删除操作增强业务
	 * 
	 * @param id
	 * @return
	 */
	private void doDelBus(SdReportInfoEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		// -----------------java增强 end-----------------------------
	}

	public Map<String, Object> populationMap(SdReportInfoEntity t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", t.getId());
		map.put("area_name", t.getArea().getAreaName());
		map.put("report_time", DateFormatInfo.formatDate(t.getReportTime(), "yyyy-MM-dd HH:mm:ss"));
		map.put("hidden_danger_place", t.getHiddenDangerPlace());
		map.put("hidden_danger_detail", t.getHiddenDangerDetail());
		map.put("status", t.getStatus());
		String statusTxt = "";
		TSTypegroup statusType = findUniqueByProperty(TSTypegroup.class, "typegroupcode", "status");
		for (TSType tsType : statusType.getTSTypes()) {
			if (tsType.getTypecode().equals(t.getStatus())) {
				statusTxt = tsType.getTypename();
			}
		}
		map.put("status_txt", statusTxt);
		if (t.getAuditTime() == null) {
			map.put("audit_time", "");
		} else {
			map.put("audit_time", DateFormatInfo.formatDate(t.getAuditTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		map.put("audit_people", t.getAuditPeople());
		map.put("bonus", t.getBonus());
		map.put("bonus_status", t.getBonusStatus());
		String bonusStatusTxt = "";
		TSTypegroup bonusStatusType = findUniqueByProperty(TSTypegroup.class, "typegroupcode", "bonus_status");
		for (TSType tsType : bonusStatusType.getTSTypes()) {
			if (tsType.getTypecode().equals(t.getBonusStatus())) {
				bonusStatusTxt = tsType.getTypename();
			}
		}
		map.put("bonus_status_txt", bonusStatusTxt);
		map.put("remarks", t.getRemarks());
		map.put("accept_remarks", t.getAcceptRemarks());
		return map;
	}

	public List<Map<String, Object>> populationMaps(List<SdReportInfoEntity> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (list != null && list.size() > 0) {
			for (SdReportInfoEntity t : list) {
				result.add(populationMap(t));
			}
		}
		return result;
	}

	private List<Map<String, Object>> populationMapList(List<SdReportInfoEntity> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (list == null || list.isEmpty())
			return result;
		for (SdReportInfoEntity t : list) {
			result.add(populationMap(t));
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> queryListByUser(String openId) {
		return populationMapList(queryList(openId));
	}

	@SuppressWarnings("unchecked")
	private List<SdReportInfoEntity> queryList(String openId) {
		String query = "SELECT report FROM SdReportInfoEntity report LEFT JOIN report.userInfo user WHERE user.openId =:openId order by report.reportTime DESC";
		Query queryObject = getSession().createQuery(query);
		queryObject.setParameter("openId", openId);
		List<SdReportInfoEntity> result = queryObject.list();
		return result;
	}

	@Override
	public Map<String, Object> queryReportDetail(String reportId) {
		SdReportInfoEntity t = get(SdReportInfoEntity.class, reportId);
		Map<String, Object> info = populationMap(t);
		// 获取媒体文件
		List<SdMediaInfoEntity> medias = sdMediaInfoService.findByProperty(SdMediaInfoEntity.class, "reportId",
				reportId);
		info.put("medias", sdMediaInfoService.populationMapList(medias));
		return info;
	}

	@Override
	public synchronized void saveByWechat(SdUserInfoEntity userInfo, String areaId, String hiddenDangerPlace,
			String hiddenDangerDetail, String fileList) throws Exception {
		SdReportInfoEntity entity = new SdReportInfoEntity();
		SdAreaInfoEntity area = new SdAreaInfoEntity();
		area.setId(areaId);
		entity.setArea(area);
		entity.setUserInfo(userInfo);
		entity.setHiddenDangerPlace(hiddenDangerPlace);
		entity.setHiddenDangerDetail(hiddenDangerDetail);
		entity.setReportTime(new Date());
		entity.setStatus("1");
		entity.setBonusStatus("0");
		// 获取流水号
		entity.setSerialNumber(getserialNumber());
		// 设置未同步12350
		entity.setIsSync("0");
		save(entity);
		// 保存媒体文件
		sdMediaInfoService.saveByWechat(entity.getId(), fileList);
	}

	private String getserialNumber() {
		Long reportNum = getCountForJdbc(
				"select count(1) from sd_report_info where to_days(report_time) = to_days(now())");
		if (reportNum == null)
			reportNum = 0L;
		String serialNumber = DateFormatInfo.getToday("yyyy-MM-dd").replace("-", "")
				+ StringUtil.addZeroForNum((reportNum.intValue() + 1) + "", 4);
		return serialNumber;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exportXls(SdReportInfoEntity sdReportInfo, String query_reportTime_begin, String query_reportTime_end,
			HttpServletRequest request, HttpServletResponse response) {
		String sFolder = request.getRealPath("/") + "export";
		String filepath = sFolder + "/隐患监测报告导出.xls";
		// 获取excel标题
		Map<String, List<String>> title = formateTitle();
		// 获取excel内容
		List<Map<String, String>> result = formatResult(sdReportInfo, query_reportTime_begin, query_reportTime_end);
		ExportExcelUtil.exportExcel(response, title, result, "隐患监测报告导出", sFolder, filepath);
	}

	private List<Map<String, String>> formatResult(SdReportInfoEntity sdReportInfo, String query_reportTime_begin,
			String query_reportTime_end) {
		List<Map<String, Object>> list = findReportList(sdReportInfo, query_reportTime_begin, query_reportTime_end);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (list == null || list.isEmpty())
			return result;
		TSTypegroup statusType = findUniqueByProperty(TSTypegroup.class, "typegroupcode", "status");
		for (Map<String, Object> t : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("serial_number", t.get("serial_number") + "");
			String report_time = t.get("report_time") + "";
			map.put("report_time", DateFormatInfo
					.formatDate(DateFormatInfo.formatTime(report_time, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
			map.put("user_name", t.get("user_name") == null ? "" : t.get("user_name") + "");
			map.put("phone", t.get("phone") == null ? "" : t.get("phone") + "");
			map.put("address", t.get("address") == null ? "" : t.get("address") + "");
			map.put("area_name", t.get("area_name") + "");
			map.put("hidden_danger_place", t.get("hidden_danger_place") + "");
			map.put("hidden_danger_detail", t.get("hidden_danger_detail") + "");
			String statusTxt = "";
			for (TSType tsType : statusType.getTSTypes()) {
				if (tsType.getTypecode().equals(t.get("status") + "")) {
					statusTxt = tsType.getTypename();
				}
			}
			map.put("status_txt", statusTxt);
			result.add(map);
		}
		return result;
	}

	private List<Map<String, Object>> findReportList(SdReportInfoEntity sdReportInfo, String query_reportTime_begin,
			String query_reportTime_end) {
		String sql = " SELECT sai.area_name,sri.serial_number,sri.report_time,sui.user_name,sui.phone,sui.address,sri.hidden_danger_place,sri.hidden_danger_detail,sri.`status` "
				+ " FROM sd_report_info sri " + " LEFT JOIN sd_area_info sai ON sri.area_id = sai.id "
				+ " LEFT JOIN sd_user_info sui ON sui.id = sri.user_id " + " WHERE 1 = 1 ";
		if (StringUtils.isNotEmpty(query_reportTime_begin)) {
			sql += " AND DATE_FORMAT(sri.report_time,'%Y-%m-%d') >= '"
					+ DateFormatInfo.formatTimeToDate(query_reportTime_begin) + "' ";
		}
		if (StringUtils.isNotEmpty(query_reportTime_end)) {
			sql += " AND DATE_FORMAT(sri.report_time,'%Y-%m-%d') <= '"
					+ DateFormatInfo.formatTimeToDate(query_reportTime_end) + "' ";
		}
		if (sdReportInfo != null && StringUtils.isNotEmpty(sdReportInfo.getStatus())) {
			sql += " AND sri.`status` = '" + sdReportInfo.getStatus() + "' ";
		}
		if (sdReportInfo != null && sdReportInfo.getArea() != null
				&& StringUtils.isNotEmpty(sdReportInfo.getArea().getId())) {
			sql += " AND sri.area_id = '" + sdReportInfo.getArea().getId() + "' ";
		}
		sql += " ORDER BY sri.report_time DESC";
		List<Map<String, Object>> list = findForJdbc(sql);
		return list;
	}

	private Map<String, List<String>> formateTitle() {
		String titleStr = "流水号,用户姓名 ,用户手机 ,用户住址,上报区域,上报时间,隐患地点,隐患详情,流程状态";
		Map<String, List<String>> title = new HashMap<>();
		List<String> titleList = new ArrayList<>(Arrays.asList(titleStr.split(",")));
		title.put("head", titleList);
		String bodyStr = "serial_number;user_name;phone;address;area_name;report_time;hidden_danger_place;hidden_danger_detail;status_txt";
		List<String> bodyList = new ArrayList<>(Arrays.asList(bodyStr.split(";")));
		title.put("body", bodyList);
		return title;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exportPdf(SdReportInfoEntity sdReportInfo, String query_reportTime_begin, String query_reportTime_end,
			HttpServletRequest request, HttpServletResponse response) {
		String template_path = request.getRealPath("/") + "export/template/pdf_temple.pdf";
		// 获取PDF内容
		List<Map<String, Object>> listPDFmap = formatResult2(sdReportInfo, query_reportTime_begin, query_reportTime_end,
				template_path);
		String wordFilePath = request.getRealPath("/") + "file/word/" + UUIDGenerator.generate() + "/";
		ExportPdfUtil.exportExcel(response, listPDFmap, "1", wordFilePath);
	}

	private List<Map<String, Object>> formatResult2(SdReportInfoEntity sdReportInfo, String query_reportTime_begin,
			String query_reportTime_end, String template_path) {
		// PDFmap
		List<Map<String, Object>> listPDFmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = findReportList(sdReportInfo, query_reportTime_begin, query_reportTime_end);
		if (list == null || list.isEmpty())
			return null;
		for (Map<String, Object> t : list) {
			Map<String, String> map = new HashMap<String, String>();
			String report_time = t.get("report_time") + "";
			map.put("serial_number", t.get("serial_number") + "");
			map.put("report_time1", DateFormatInfo
					.formatDate(DateFormatInfo.formatTime(report_time, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"));
			map.put("report_time2",
					DateFormatInfo.formatDate(DateFormatInfo.formatTime(report_time, "yyyy-MM-dd HH:mm:ss"), "HH:mm"));
			map.put("report_time", DateFormatInfo
					.formatDate(DateFormatInfo.formatTime(report_time, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
			map.put("user_name", t.get("user_name") == null ? "" : t.get("user_name") + "");
			map.put("phone", t.get("phone") == null ? "" : t.get("phone") + "");
			map.put("address", t.get("address") == null ? "" : t.get("address") + "");
			map.put("area_name", t.get("area_name") + "");
			map.put("hidden_danger_place", t.get("hidden_danger_place") + "");
			map.put("hidden_danger_detail", t.get("hidden_danger_detail") + "");
			Map<String, Object> info2 = new HashMap<String, Object>();
			info2.put("template_path", template_path);
			info2.put("textMap", map);
			info2.put("imgMap", new HashMap<String, String>());
			info2.put("file_path", t.get("serial_number") + "");
			listPDFmap.add(info2);
		}
		return listPDFmap;
	}

	@Override
	public void syncReport(SdReportInfoEntity report) throws Exception {
		ApiToken apiToken = ResourceUtil.token12350;
		if (apiToken == null || StringUtils.isEmpty(apiToken.getToken())) {
			// 放入全局缓存
			apiToken = Sync12350Util.getToken();
			ResourceUtil.token12350 = apiToken;
		}
		List<SdMediaInfoEntity> medias = sdMediaInfoService.findByProperty(SdMediaInfoEntity.class, "reportId",
				report.getId());
		if (medias != null && medias.size() > 0) {
			List<String> filePath = new ArrayList<String>();
			for (SdMediaInfoEntity media : medias)
				filePath.add(media.getMediaUrl());
			report.setFileList(StringUtils.join(filePath, "|"));
		}
		ReturnInfo returnInfo = Sync12350Util.netWorkOrder(apiToken.getToken(), report);
		if (returnInfo != null && StringUtils.isNotEmpty(returnInfo.getFormId())) {
			report.setFormId(returnInfo.getFormId());
			report.setIsSync("1");
			saveOrUpdate(report);
		} else {
			throw new RuntimeException("同步至12350失败！");
		}
	}

	@Override
	public Map<String, Object> statisticsNum(String beginDate, String endDate) {
		String sql = " SELECT  " + "	COUNT(id) AS all_num," + "	COUNT(`status` = 1 OR NULL) AS undo_num,"
				+ "	COUNT(" + "		`status` = 2" + "		OR `status` = 4" + "		OR NULL" + "	) AS pass_num,"
				+ "	COUNT(" + "		`status` = 3" + "		OR `status` = 5" + "		OR NULL"
				+ "	) AS unpass_num " + "FROM" + "	sd_report_info WHERE 1 = 1 ";
		if (StringUtils.isNotEmpty(beginDate)) {
			beginDate = DateFormatInfo.formatTimeToDate(beginDate);
			sql += " AND DATE_FORMAT(report_time, '%Y-%m-%d') >= '" + beginDate + "'";
		}
		if (StringUtils.isNotEmpty(endDate)) {
			endDate = DateFormatInfo.formatTimeToDate(endDate);
			sql += " AND DATE_FORMAT(report_time, '%Y-%m-%d') <= '" + endDate + "'";
		}
		return jdbcDao.queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> statisticsAreaNum(String beginDate, String endDate) {
		String sql = "SELECT " + "ri.area_id,	ai.area_name, " + "	COUNT(ri.id) AS all_num, "
				+ "	COUNT(`status` = 1 OR NULL) AS undo_num, " + "	COUNT( " + "		`status` = 2 "
				+ "		OR `status` = 4 " + "		OR NULL " + "	) AS pass_num, " + "	COUNT( "
				+ "		`status` = 3 " + "		OR `status` = 5 " + "		OR NULL " + "	) AS unpass_num  " + "FROM "
				+ "	sd_report_info ri " + "LEFT JOIN sd_area_info ai ON ri.area_id = ai.id " + "WHERE 1 = 1";
		if (StringUtils.isNotEmpty(beginDate)) {
			beginDate = DateFormatInfo.formatTimeToDate(beginDate);
			sql += " AND DATE_FORMAT(report_time, '%Y-%m-%d') >= '" + beginDate + "'";
		}
		if (StringUtils.isNotEmpty(endDate)) {
			endDate = DateFormatInfo.formatTimeToDate(endDate);
			sql += " AND DATE_FORMAT(report_time, '%Y-%m-%d') <= '" + endDate + "'";
		}
		sql += " GROUP BY area_id";
		return jdbcDao.queryForList(sql);
	}

}
