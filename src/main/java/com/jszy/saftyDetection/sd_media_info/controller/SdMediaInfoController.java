package com.jszy.safetyDetection.sd_media_info.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jszy.safetyDetection.sd_media_info.entity.SdMediaInfoEntity;
import com.jszy.safetyDetection.sd_media_info.service.SdMediaInfoServiceI;

import io.swagger.annotations.Api;

/**
 * @Title: Controller
 * @Description: 反馈媒体信息
 * @author onlineGenerator
 * @date 2020-01-15 15:01:15
 * @version V1.0
 *
 */
@Api(value = "SdMediaInfo", description = "反馈媒体信息", tags = "sdMediaInfoController")
@Controller
@RequestMapping("/sdMediaInfoController")
public class SdMediaInfoController extends BaseController {

	@Autowired
	private SdMediaInfoServiceI sdMediaInfoService;
	@Autowired
	private SystemService systemService;

	/**
	 * 反馈媒体信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jszy/safetyDetection/sd_media_info/sdMediaInfoList");
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
	public void datagrid(SdMediaInfoEntity sdMediaInfo, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SdMediaInfoEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sdMediaInfo, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.sdMediaInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除反馈媒体信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(SdMediaInfoEntity sdMediaInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		sdMediaInfo = systemService.getEntity(SdMediaInfoEntity.class, sdMediaInfo.getId());
		message = "反馈媒体信息删除成功";
		try {
			sdMediaInfoService.delete(sdMediaInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "反馈媒体信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除反馈媒体信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "反馈媒体信息删除成功";
		try {
			for (String id : ids.split(",")) {
				SdMediaInfoEntity sdMediaInfo = systemService.getEntity(SdMediaInfoEntity.class, id);
				sdMediaInfoService.delete(sdMediaInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "反馈媒体信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加反馈媒体信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(SdMediaInfoEntity sdMediaInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "反馈媒体信息添加成功";
		try {
			sdMediaInfoService.save(sdMediaInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "反馈媒体信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新反馈媒体信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(SdMediaInfoEntity sdMediaInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "反馈媒体信息更新成功";
		SdMediaInfoEntity t = sdMediaInfoService.get(SdMediaInfoEntity.class, sdMediaInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(sdMediaInfo, t);
			sdMediaInfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "反馈媒体信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 反馈媒体信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(SdMediaInfoEntity sdMediaInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdMediaInfo.getId())) {
			sdMediaInfo = sdMediaInfoService.getEntity(SdMediaInfoEntity.class, sdMediaInfo.getId());
			req.setAttribute("sdMediaInfoPage", sdMediaInfo);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_media_info/sdMediaInfo-add");
	}

	/**
	 * 反馈媒体信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(SdMediaInfoEntity sdMediaInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdMediaInfo.getId())) {
			sdMediaInfo = sdMediaInfoService.getEntity(SdMediaInfoEntity.class, sdMediaInfo.getId());
			req.setAttribute("sdMediaInfoPage", sdMediaInfo);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_media_info/sdMediaInfo-update");
	}

}
