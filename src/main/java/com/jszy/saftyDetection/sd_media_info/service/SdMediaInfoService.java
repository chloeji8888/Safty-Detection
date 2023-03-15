package com.jszy.safetyDetection.sd_media_info.service;

import com.jszy.safetyDetection.sd_media_info.entity.SdMediaInfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SdMediaInfoServiceI extends CommonService {

	public void delete(SdMediaInfoEntity entity) throws Exception;

	public Serializable save(SdMediaInfoEntity entity) throws Exception;

	public void saveOrUpdate(SdMediaInfoEntity entity) throws Exception;

	public List<Map<String, Object>> populationMapList(List<SdMediaInfoEntity> list);

	public void saveByWechat(String reportId, String fileList) throws Exception;
}
