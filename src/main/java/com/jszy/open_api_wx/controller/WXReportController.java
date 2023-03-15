package com.jszy.open_api_wx.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.jwt.util.menu.ResponseMessageCodeEnum;
import org.jeecgframework.web.system.service.SystemService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jszy.safetyDetection.sd_area_info.service.SdAreaInfoServiceI;
import com.jszy.safetyDetection.sd_report_info.service.SdReportInfoServiceI;
import com.jszy.safetyDetection.sd_user_info.entity.SdUserInfoEntity;
import com.jszy.util.HttpClient;
import com.jszy.util.JsonUtil;
import com.jszy.util.TokenUtil;
import com.jszy.util.UploadInfo;
import com.jszy.util.UploadUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * @Title: Controller
 * @Description: 黑名单
 * @author onlineGenerator
 * @date 2017-05-18 22:33:13
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/wxReportController")
public class WXReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WXReportController.class);
	@Autowired
	private SdReportInfoServiceI sdReportInfoService;
	@Autowired
	private SdAreaInfoServiceI sdAreaInfoService;
	@Autowired
	private SystemService systemService;

	/**
	 * 获取区域列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "query_area_list")
	@ResponseBody
	public ResponseMessage<?> queryAreaList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("ENTER query_area_list");
		// String content = HttpClient.getContent(request);
		List<Map<String, Object>> list = sdAreaInfoService.queryList();
		return Result.success(list);
	}

	/**
	 * 获取用户上传记录列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "query_report_list")
	@ResponseBody
	public ResponseMessage<?> queryReportList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("ENTER query_report_list");
		String content = HttpClient.getContent(request);
		JSONObject json = new JSONObject(content);
		String token = JsonUtil.getValue(json, "token");
		// 验证
		if (StringUtils.isEmpty(token)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入token");
		}
		// 获取token中的用户信息
		Claims claims;
		try {
			claims = TokenUtil.parseJWT(token);
		} catch (ExpiredJwtException e) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "token已失效，请重新获取");
		}
		String openId = claims.getId();
		List<Map<String, Object>> list = sdReportInfoService.queryListByUser(openId);
		return Result.success(list);
	}

	/**
	 * 获取用户上传记录详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "query_report_detail")
	@ResponseBody
	public ResponseMessage<?> queryReportDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("ENTER query_report_detail");
		String content = HttpClient.getContent(request);
		JSONObject json = new JSONObject(content);
		String token = JsonUtil.getValue(json, "token");
		// 验证
		if (StringUtils.isEmpty(token)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入token");
		}
		// 获取token中的用户信息
		Claims claims;
		try {
			claims = TokenUtil.parseJWT(token);
		} catch (ExpiredJwtException e) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "token已失效，请重新获取");
		}
		String openId = claims.getId();
		SdUserInfoEntity userInfo = systemService.findUniqueByProperty(SdUserInfoEntity.class, "openId", openId);
		if (userInfo == null) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "用户未绑定，请先绑定");
		}
		String reportId = JsonUtil.getValue(json, "report_id");
		// 验证
		if (StringUtils.isEmpty(reportId)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入报告id");
		}
		Map<String, Object> info = sdReportInfoService.queryReportDetail(reportId);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		info.put("basePath", basePath);
		return Result.success(info);
	}

	/**
	 * 保存用户上传记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "save_wechat_file")
	@ResponseBody
	public ResponseMessage<?> saveWechatFile(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("ENTER save_wechat_file");
		UploadInfo fileInfo = UploadUtil.upload(request, "wechat_file");
		return Result.success(fileInfo.getFilePath());
	}

	/**
	 * 保存用户上传记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "save_report")
	@ResponseBody
	public ResponseMessage<?> saveReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("ENTER save_report");
		String content = HttpClient.getContent(request);
		JSONObject json = new JSONObject(content);
		String token = JsonUtil.getValue(json, "token");
		// 验证
		if (StringUtils.isEmpty(token)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入token");
		}
		// 获取token中的用户信息
		Claims claims;
		try {
			claims = TokenUtil.parseJWT(token);
		} catch (ExpiredJwtException e) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "token已失效，请重新获取");
		}
		String openId = claims.getId();
		SdUserInfoEntity userInfo = systemService.findUniqueByProperty(SdUserInfoEntity.class, "openId", openId);
		if (userInfo == null) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "用户未绑定，请先绑定");
		}
		// 隐患区域
		String areaId = JsonUtil.getValue(json, "area_id");
		if (StringUtils.isEmpty(areaId)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入隐患区域");
		}
		// 隐患地点
		String hiddenDangerPlace = JsonUtil.getValue(json, "hidden_danger_place");
		if (StringUtils.isEmpty(hiddenDangerPlace)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入隐患地点");
		}
		// 隐患详情
		String hiddenDangerDetail = JsonUtil.getValue(json, "hidden_danger_detail");
		if (StringUtils.isEmpty(hiddenDangerDetail)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入隐患详情");
		}
		// 媒体文件
		String fileList = JsonUtil.getValue(json, "file_list");
		if (StringUtils.isEmpty(fileList)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入媒体文件信息");
		}
		try {
			sdReportInfoService.saveByWechat(userInfo, areaId, hiddenDangerPlace, hiddenDangerDetail, fileList);
		} catch (Exception e) {
			return Result.error(ResponseMessageCodeEnum.ERROR, "报存失败");
		}
		return Result.success();
	}

}
