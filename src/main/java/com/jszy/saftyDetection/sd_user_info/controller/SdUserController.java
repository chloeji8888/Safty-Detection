package com.jszy.safetyDetection.sd_user_info.controller;

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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jszy.safetyDetection.sd_user_info.entity.SdUserInfoEntity;
import com.jszy.safetyDetection.sd_user_info.service.SdUserInfoServiceI;

import io.swagger.annotations.Api;

/**
 * @Title: Controller
 * @Description: 用户个人信息
 * @author onlineGenerator
 * @date 2020-01-15 15:00:51
 * @version V1.0
 *
 */
@Api(value = "SdUserInfo", description = "用户个人信息", tags = "sdUserInfoController")
@Controller
@RequestMapping("/sdUserInfoController")
public class SdUserInfoController extends BaseController {

	@Autowired
	private SdUserInfoServiceI sdUserInfoService;
	@Autowired
	private SystemService systemService;

	/**
	 * 用户个人信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jszy/safetyDetection/sd_user_info/sdUserInfoList");
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
	public void datagrid(SdUserInfoEntity sdUserInfo, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		if (StringUtils.isNotEmpty(sdUserInfo.getUserName())) {
			sdUserInfo.setUserName("*" + sdUserInfo.getUserName() + "*");
		}
		if (StringUtils.isNotEmpty(sdUserInfo.getPhone())) {
			sdUserInfo.setPhone("*" + sdUserInfo.getPhone() + "*");
		}
		CriteriaQuery cq = new CriteriaQuery(SdUserInfoEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sdUserInfo, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.sdUserInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除用户个人信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(SdUserInfoEntity sdUserInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		sdUserInfo = systemService.getEntity(SdUserInfoEntity.class, sdUserInfo.getId());
		message = "用户个人信息删除成功";
		try {
			sdUserInfoService.delete(sdUserInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户个人信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除用户个人信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户个人信息删除成功";
		try {
			for (String id : ids.split(",")) {
				SdUserInfoEntity sdUserInfo = systemService.getEntity(SdUserInfoEntity.class, id);
				sdUserInfoService.delete(sdUserInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户个人信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加用户个人信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(SdUserInfoEntity sdUserInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户个人信息添加成功";
		try {
			sdUserInfoService.save(sdUserInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户个人信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新用户个人信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(SdUserInfoEntity sdUserInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户个人信息更新成功";
		SdUserInfoEntity t = sdUserInfoService.get(SdUserInfoEntity.class, sdUserInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(sdUserInfo, t);
			sdUserInfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户个人信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 用户个人信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(SdUserInfoEntity sdUserInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdUserInfo.getId())) {
			sdUserInfo = sdUserInfoService.getEntity(SdUserInfoEntity.class, sdUserInfo.getId());
			req.setAttribute("sdUserInfoPage", sdUserInfo);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_user_info/sdUserInfo-add");
	}

	/**
	 * 用户个人信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(SdUserInfoEntity sdUserInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdUserInfo.getId())) {
			sdUserInfo = sdUserInfoService.getEntity(SdUserInfoEntity.class, sdUserInfo.getId());
			req.setAttribute("sdUserInfoPage", sdUserInfo);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_user_info/sdUserInfo-update");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "sdUserInfoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出用户汇报表详情
	 * 
	 * @return
	 */
	@RequestMapping(params = "exportXls")
	public ModelAndView export(SdUserInfoEntity sdUserInfo,HttpServletRequest request, HttpServletResponse response) {
		sdUserInfoService.export(sdUserInfo, request, response);
		return null;
	}

}
