package com.jszy.open_api_wx.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.jwt.util.menu.ResponseMessageCodeEnum;
import org.jeecgframework.web.system.service.SystemService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jszy.safetyDetection.sd_user_info.entity.SdUserInfoEntity;
import com.jszy.safetyDetection.sd_user_info.service.SdUserInfoServiceI;
import com.jszy.util.HttpClient;
import com.jszy.util.JsonUtil;
import com.jszy.util.SendSmsUtil;
import com.jszy.util.TokenUtil;
import com.jszy.util.Validator;

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
@RequestMapping("/wxUserController")
public class WXUserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WXUserController.class);
	private static PropertiesUtil util = new PropertiesUtil("sysConfig.properties");
	@Autowired
	private SystemService systemService;
	@Autowired
	private SdUserInfoServiceI sdUserInfoService;

	/**
	 * 获取用户open_id
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "queryToken")
	@ResponseBody
	public ResponseMessage<?> queryToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("ENTER queryToken");
		String content = HttpClient.getContent(request);
		JSONObject json = new JSONObject(content);
		String code = JsonUtil.getValue(json, "code");
		// 验证
		if (StringUtils.isEmpty(code)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入code");
		}
		Map<String, Object> info = new HashMap<>();
		JSONObject param = new JSONObject();
		param.put("code", code);
		param.put("wechat_name", util.readProperty("wechat_name"));
		String result = HttpClient.httpWechatApi(param, util.readProperty("MINI_QUERY_OPENID"),
				util.readProperty("RSAPRIVATEKEY"));
		JSONObject res = new JSONObject(result);
		String openId = "";
		if ("000000".equals(res.get("rtnCode") + "")) {
			openId = res.get("open_id") + "";
		} else {
			return Result.error();
		}
		String token = TokenUtil.createJwtToken(openId);
		info.put("token", token);
		// 判断是否绑定
		SdUserInfoEntity userInfo = systemService.findUniqueByProperty(SdUserInfoEntity.class, "openId", openId);
		if (userInfo == null) {
			info.put("is_bind", "false");
		} else {
			info.put("is_bind", "true");
		}
		return Result.success(info);
	}

	/**
	 * 发送短信
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "sendSMS")
	@ResponseBody
	public ResponseMessage<?> sendSMS(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("ENTER sendSMS");
		String content = HttpClient.getContent(request);
		JSONObject json = new JSONObject(content);
		String phone = JsonUtil.getValue(json, "phone");
		// 验证
		if (StringUtils.isEmpty(phone)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入手机号码");
		}
		if (!Validator.isMobile(phone)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入正确的手机号码");
		}
		int validateCode = (int) ((Math.random() * 9 + 1) * 100000);
		Map<String, Object> info = new HashMap<>();
		try {
			SendSmsUtil.sendValidate(phone, validateCode);
			info.put("validateCode", validateCode);
			return Result.success();
		} catch (Exception e) {
			logger.error("推送短信失败：" + e.getMessage());
			return Result.error("推送短信失败：" + e.getMessage());
		}
	}

	/**
	 * 校验用户是否绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "checkUserIsBind")
	@ResponseBody
	public ResponseMessage<?> checkUserIsBind(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("ENTER checkUserIsBind");
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
		Map<String, Object> info = new HashMap<>();
		SdUserInfoEntity userInfo = systemService.findUniqueByProperty(SdUserInfoEntity.class, "openId", openId);
		if (userInfo == null) {
			info.put("is_bind", "false");
		} else {
			info.put("is_bind", "true");
		}
		return Result.success(info);
	}

	/**
	 * 查询用户个人信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "queryUserInfo")
	@ResponseBody
	public ResponseMessage<?> queryUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("ENTER queryUserInfo");
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
		return Result.success(sdUserInfoService.populationMap(userInfo));
	}

	/**
	 * 保存用户个人信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "doBindUser")
	@ResponseBody
	public ResponseMessage<?> doBindUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("ENTER doBindUser");
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
		// 用户姓名
		String userName = JsonUtil.getValue(json, "user_name");
		// if (StringUtils.isEmpty(userName)) {
		// return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入用户姓名");
		// }
		// 手机号
		String phone = JsonUtil.getValue(json, "phone");
		// if () {
		// return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入手机号");
		// }
		if (StringUtils.isNotEmpty(phone) && !Validator.isMobile(phone)) {
			return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "传入的手机号不正确");
		}
		// 住址
		String address = JsonUtil.getValue(json, "address");
		// if (StringUtils.isEmpty(address)) {
		// return Result.error(ResponseMessageCodeEnum.VALID_ERROR, "请传入住址");
		// }
		// 邮箱
		String email = JsonUtil.getValue(json, "email");
		// QQ号码
		String qqNumber = JsonUtil.getValue(json, "qq_number");
		// 开户行
		String bankName = JsonUtil.getValue(json, "bank_name");
		// 银行卡号
		String bankNo = JsonUtil.getValue(json, "bank_no");
		
		SdUserInfoEntity t = systemService.findUniqueByProperty(SdUserInfoEntity.class, "openId", openId);
		if (t == null)
			t = new SdUserInfoEntity();
		t.setOpenId(openId);
		t.setUserName(userName);
		t.setPhone(phone);
		t.setAddress(address);
		t.setEmail(email);
		t.setQqNumber(qqNumber);
		t.setBankName(bankName);
		t.setBankNo(bankNo);
		try {
			if (StringUtils.isEmpty(t.getId())) {
				sdUserInfoService.save(t);
			} else {
				sdUserInfoService.saveOrUpdate(t);
			}
		} catch (Exception e) {
			return Result.error(ResponseMessageCodeEnum.ERROR, "报存失败");
		}
		return Result.success();
	}

}
