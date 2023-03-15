package com.jszy.safetyDetection.sd_area_info.service;
import com.jszy.safetyDetection.sd_area_info.entity.SdAreaInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SdAreaInfoServiceI extends CommonService{
	
 	public void delete(SdAreaInfoEntity entity) throws Exception;
 	
 	public Serializable save(SdAreaInfoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(SdAreaInfoEntity entity) throws Exception;

	public List<Map<String, Object>> queryList();
 	
}
