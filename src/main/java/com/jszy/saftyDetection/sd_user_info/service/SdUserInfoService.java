package com.jszy.safetyDetection.sd_user_info.service;

import com.jszy.safetyDetection.sd_user_info.entity.SdUserInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SdUserInfoServiceI extends CommonService {

	public void delete(SdUserInfoEntity entity) throws Exception;

	public Serializable save(SdUserInfoEntity entity) throws Exception;

	public void saveOrUpdate(SdUserInfoEntity entity) throws Exception;

	public Map<String, Object> populationMap(SdUserInfoEntity entity);

	public void export(SdUserInfoEntity entity, HttpServletRequest request, HttpServletResponse response);
}
