package com.jszy.safetyDetection.sd_area_info.controller;
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

import com.jszy.safetyDetection.sd_area_info.entity.SdAreaInfoEntity;
import com.jszy.safetyDetection.sd_area_info.service.SdAreaInfoServiceI;

import io.swagger.annotations.Api;

/**   
 * @Title: Controller  
 * @Description: 区域管理
 * @author onlineGenerator
 * @date 2020-02-02 10:13:58
 * @version V1.0   
 *
 */
@Api(value="SdAreaInfo",description="区域管理",tags="sdAreaInfoController")
@Controller
@RequestMapping("/sdAreaInfoController")
public class SdAreaInfoController extends BaseController {

	@Autowired
	private SdAreaInfoServiceI sdAreaInfoService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 区域管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jszy/safetyDetection/sd_area_info/sdAreaInfoList");
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
	public void datagrid(SdAreaInfoEntity sdAreaInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SdAreaInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sdAreaInfo, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.sdAreaInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除区域管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(SdAreaInfoEntity sdAreaInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		sdAreaInfo = systemService.getEntity(SdAreaInfoEntity.class, sdAreaInfo.getId());
		message = "区域管理删除成功";
		try{
			sdAreaInfoService.delete(sdAreaInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "区域管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除区域管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "区域管理删除成功";
		try{
			for(String id:ids.split(",")){
				SdAreaInfoEntity sdAreaInfo = systemService.getEntity(SdAreaInfoEntity.class, 
				id
				);
				sdAreaInfoService.delete(sdAreaInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "区域管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加区域管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(SdAreaInfoEntity sdAreaInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "区域管理添加成功";
		try{
			sdAreaInfoService.save(sdAreaInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "区域管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新区域管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(SdAreaInfoEntity sdAreaInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "区域管理更新成功";
		SdAreaInfoEntity t = sdAreaInfoService.get(SdAreaInfoEntity.class, sdAreaInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(sdAreaInfo, t);
			sdAreaInfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "区域管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 区域管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(SdAreaInfoEntity sdAreaInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdAreaInfo.getId())) {
			sdAreaInfo = sdAreaInfoService.getEntity(SdAreaInfoEntity.class, sdAreaInfo.getId());
			req.setAttribute("sdAreaInfoPage", sdAreaInfo);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_area_info/sdAreaInfo-add");
	}
	/**
	 * 区域管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(SdAreaInfoEntity sdAreaInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sdAreaInfo.getId())) {
			sdAreaInfo = sdAreaInfoService.getEntity(SdAreaInfoEntity.class, sdAreaInfo.getId());
			req.setAttribute("sdAreaInfoPage", sdAreaInfo);
		}
		return new ModelAndView("com/jszy/safetyDetection/sd_area_info/sdAreaInfo-update");
	}
	
}
