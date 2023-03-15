package com.jszy.open_api_wx.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jszy.safetyDetection.sd_area_info.entity.SdAreaInfoEntity;
import com.jszy.safetyDetection.sd_report_info.entity.SdReportInfoEntity;
import com.jszy.safetyDetection.sd_report_info.service.SdReportInfoServiceI;
import com.jszy.util.DateFormatInfo;
import com.jszy.util.Md5Utils;

/**
 * @Title: Controller
 * @Description: 黑名单
 * @author onlineGenerator
 * @date 2017-05-18 22:33:13
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/reportApi")
public class ReportApiController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReportApiController.class);
	@Autowired
	private SdReportInfoServiceI sdReportInfoService;
	private static PropertiesUtil util = new PropertiesUtil("sysConfig.properties");

	private Set<String> getSignatures() {
		String signature = util.readProperty("signKey");
		Set<String> signatures = new HashSet<String>();
		for (String key : signature.split("\\|")) {
			String sign = DateFormatInfo.getToday("yyyy-MM-dd") + key;
			sign = Md5Utils.MD5(sign);
			signatures.add(sign.toUpperCase());
		}
		return signatures;
	}

	/**
	 * 统计
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "statistics_num")
	@ResponseBody
	public ResponseMessage<?> statistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("ENTER statistics_num");
		String signature = request.getParameter("signature");
		Set<String> signatures = getSignatures();
		if (StringUtils.isEmpty(signature)) {
			return Result.error("请传入验签");
		} else if (!signatures.contains(signature.toUpperCase())) {
			return Result.error("验签校验不通过");
		}
		String beginDate = request.getParameter("begin_date");
		String endDate = request.getParameter("end_date");
		Map<String, Object> result = sdReportInfoService.statisticsNum(beginDate, endDate);
		return Result.success(result);
	}

	/**
	 * 获取用户上传记录列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "statistics_area_num")
	@ResponseBody
	public ResponseMessage<?> statisticsAreaNum(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("ENTER statistics_area_num");
		String signature = request.getParameter("signature");
		Set<String> signatures = getSignatures();
		if (StringUtils.isEmpty(signature)) {
			return Result.error("请传入验签");
		} else if (!signatures.contains(signature.toUpperCase())) {
			return Result.error("验签校验不通过");
		}
		String beginDate = request.getParameter("begin_date");
		String endDate = request.getParameter("end_date");
		List<Map<String, Object>> result = sdReportInfoService.statisticsAreaNum(beginDate, endDate);
		return Result.success(result);
	}

	/**
	 * 获取用户上传记录详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "query_report_list")
	@ResponseBody
	public ResponseMessage<?> queryReportList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("ENTER query_report_list");
		String signature = request.getParameter("signature");
		Set<String> signatures = getSignatures();
		if (StringUtils.isEmpty(signature)) {
			return Result.error("请传入验签");
		} else if (!signatures.contains(signature.toUpperCase())) {
			return Result.error("验签校验不通过");
		}
		String beginDate = request.getParameter("begin_date");
		String endDate = request.getParameter("end_date");
		String areaId = request.getParameter("area_id");
		String page = request.getParameter("page");
		if (StringUtils.isEmpty(page)) {
			page = "1";
		}
		String rows = request.getParameter("rows");
		if (StringUtils.isEmpty(rows)) {
			rows = "10";
		}
		DataGrid dataGrid = new DataGrid();
		dataGrid.setPage(Integer.parseInt(page));
		dataGrid.setRows(Integer.parseInt(rows));
		SdReportInfoEntity sdReportInfo = new SdReportInfoEntity();
		if (StringUtils.isNotEmpty(areaId)) {
			SdAreaInfoEntity area = new SdAreaInfoEntity();
			area.setId(areaId);
			sdReportInfo.setArea(area);
		}
		dataGrid.setSort("reportTime");
		dataGrid.setOrder("desc");
		CriteriaQuery cq = new CriteriaQuery(SdReportInfoEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sdReportInfo,
				request.getParameterMap());
		try {
			// 自定义追加查询条件
			if (StringUtil.isNotEmpty(beginDate)) {
				beginDate = DateFormatInfo.formatTimeToDate(beginDate);
				cq.ge("reportTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginDate + " 00:00:00"));
			}
			if (StringUtil.isNotEmpty(endDate)) {
				cq.le("reportTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate + " 23:59:59"));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.sdReportInfoService.getDataGridReturn(cq, true);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("currentPage", dataGrid.getPage());
		result.put("total", (dataGrid.getTotal() / dataGrid.getRows() == 0) ? (dataGrid.getTotal() / dataGrid.getRows())
				: (dataGrid.getTotal() / dataGrid.getRows() + 1));
		result.put("results", sdReportInfoService.populationMaps(dataGrid.getResults()));
		return Result.success(result);
	}

}
