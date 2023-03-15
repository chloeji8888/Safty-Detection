package com.jszy.safetyDetection.sd_report_info.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jszy.safetyDetection.sd_media_info.entity.SdMediaInfoEntity;
import com.jszy.safetyDetection.sd_report_info.entity.SdReportInfoEntity;
import com.jszy.safetyDetection.sd_report_info.service.SdReportInfoServiceI;

import io.swagger.annotations.Api;

/**
 * @Title: Controller
 * @Description: 上报记录
 * @author onlineGenerator
 * @date 2020-01-15 15:01:09
 * @version V1.0
 *
 */
@Api(value = "SdReportInfo", description = "上报记录", tags = "sdReportInfoController")
@Controller
@RequestMapping("/sdReportInfoController")
public class SdReportInfoController extends BaseController {

	@Autowired
	private SdReportInfoServiceI sdReportInfoService;
	@Autowired
	private SystemService systemService;

	/**
	 * 上报记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jszy/safetyDetection/sd_report_info/sdReportInfoList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(SdReportInfoEntity sdReportInfo, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		dataGrid.setSort("reportTime");
		dataGrid.setOrder("desc");
		if (StringUtils.isNotEmpty(sdReportInfo.getSerialNumber())) {
			sdReportInfo.setSerialNumber("*" + sdReportInfo.getSerialNumber() + "*");
		}
		if (StringUtils.isNotEmpty(sdReportInfo.getHiddenDangerDetail())) {
			sdReportInfo.setHiddenDangerDetail("*" + sdReportInfo.getHiddenDangerDetail() + "*");
		}
		CriteriaQuery cq = new CriteriaQuery(SdReportInfoEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sdReportInfo,
				request.getParameterMap());
		try {
			// 自定义追加查询条件
			String query_reportTime_begin = request.getParameter("reportTime_begin1");
			String query_reportTime_end = request.getParameter("reportTime_end2");
			if (StringUtil.isNotEmpty(query_reportTime_begin)) {
				cq.ge("reportTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_reportTime_begin));
			}
			if (StringUtil.isNotEmpty(query_reportTime_end)) {
				cq.le("reportTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(query_reportTime_end));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.sdReportInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除上报记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(SdReportInfoEntity sdReportInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		sdReportInfo = systemService.getEntity(SdReportInfoEntity.class, sdReportInfo.getId());
		message = "上报记录删除成功";
		try {
			sdReportInfoService.delete(sdReportInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "上报记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除上报记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上报记录删除成功";
		try {
			for (String id : ids.split(",")) {
				SdReportInfoEntity sdReportInfo = systemService.getEntity(SdReportInfoEntity.class, id);
				sdReportInfoService.delete(sdReportInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "上报记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新上报记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAudit")
	@ResponseBody
	public AjaxJson doAudit(SdReportInfoEntity sdReportInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上报记录核查操作成功";
		SdReportInfoEntity t = sdReportInfoService.get(SdReportInfoEntity.class, sdReportInfo.getId());
		try {
			if (sdReportInfo.getStatus().equals("4")) {
				sdReportInfo.setBonusStatus("1");
			} else {
				sdReportInfo.setBonusStatus("0");
			}
			TSUser user = ResourceUtil.getSessionUser();
			sdReportInfo.setAuditPeople(user.getId());
			sdReportInfo.setAuditTime(new Date());
			MyBeanUtils.copyBeanNotNull2Bean(sdReportInfo, t);
			if (sdReportInfo.getBonus() == null)
				t.setBonus(null);
			sdReportInfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "上报记录核查操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新上报记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doVerification")
	@ResponseBody
	public AjaxJson doVerification(SdReportInfoEntity sdReportInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "上报记录受理操作成功";
		SdReportInfoEntity t = sdReportInfoService.get(SdReportInfoEntity.class, sdReportInfo.getId());
		try {
			sdReportInfo.setBonusStatus("0");
			TSUser user = ResourceUtil.getSessionUser();
			sdReportInfo.setAcceptPeople(user.getId());
			sdReportInfo.setAcceptTime(new Date());
			MyBeanUtils.copyBeanNotNull2Bean(sdReportInfo, t);
			if (sdReportInfo.getBonus() == null)
				t.setBonus(null);
			if (sdReportInfo.getStatus().equals("2")) {
				sdReportInfoService.syncReport(t);
			} else {
				sdReportInfoService.saveOrUpdate(t);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "上报记录受理操作失败！" + e.getMessage();
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 上报记录审核页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goDetail")
	public ModelAndView goDetail(SdReportInfoEntity sdReportInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdReportInfo.getId())) {
			sdReportInfo = sdReportInfoService.getEntity(SdReportInfoEntity.class, sdReportInfo.getId());
			req.setAttribute("report", sdReportInfo);
			List<SdMediaInfoEntity> medias = systemService.findByProperty(SdMediaInfoEntity.class, "reportId",
					sdReportInfo.getId());
			req.setAttribute("medias", medias);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_report_info/sdReportInfo-detail");
	}

	/**
	 * 上报记录审核页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAudit")
	public ModelAndView goAudit(SdReportInfoEntity sdReportInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdReportInfo.getId())) {
			sdReportInfo = sdReportInfoService.getEntity(SdReportInfoEntity.class, sdReportInfo.getId());
			req.setAttribute("report", sdReportInfo);
			List<SdMediaInfoEntity> medias = systemService.findByProperty(SdMediaInfoEntity.class, "reportId",
					sdReportInfo.getId());
			req.setAttribute("medias", medias);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_report_info/sdReportInfo-update");
	}

	/**
	 * 上报记录核查页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goVerification")
	public ModelAndView goVerification(SdReportInfoEntity sdReportInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdReportInfo.getId())) {
			sdReportInfo = sdReportInfoService.getEntity(SdReportInfoEntity.class, sdReportInfo.getId());
			req.setAttribute("report", sdReportInfo);
			List<SdMediaInfoEntity> medias = systemService.findByProperty(SdMediaInfoEntity.class, "reportId",
					sdReportInfo.getId());
			req.setAttribute("medias", medias);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_report_info/sdReportInfo-verification");
	}

	/**
	 * 导出未选择社团学生名单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "exportXls")
	public ModelAndView exportXls(SdReportInfoEntity sdReportInfo, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String query_reportTime_begin = request.getParameter("reportTime_begin1");
		String query_reportTime_end = request.getParameter("reportTime_end2");
		sdReportInfoService.exportXls(sdReportInfo, query_reportTime_begin, query_reportTime_end, request, response);
		return null;
	}

	/**
	 * 导出未选择社团学生名单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "exportPdf")
	public ModelAndView exportPdf(SdReportInfoEntity sdReportInfo, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String query_reportTime_begin = request.getParameter("reportTime_begin1");
		String query_reportTime_end = request.getParameter("reportTime_end2");
		sdReportInfoService.exportPdf(sdReportInfo, query_reportTime_begin, query_reportTime_end, request, response);
		return null;
	}

}
