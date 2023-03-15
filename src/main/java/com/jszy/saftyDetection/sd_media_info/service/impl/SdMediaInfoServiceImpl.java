package com.jszy.safetyDetection.sd_media_info.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jszy.safetyDetection.sd_media_info.entity.SdMediaInfoEntity;
import com.jszy.safetyDetection.sd_media_info.service.SdMediaInfoServiceI;

@Service("sdMediaInfoService")
@Transactional
public class SdMediaInfoServiceImpl extends CommonServiceImpl implements SdMediaInfoServiceI {

	public void delete(SdMediaInfoEntity entity) throws Exception {
		super.delete(entity);
		// 执行删除操作增强业务
		this.doDelBus(entity);
	}

	public Serializable save(SdMediaInfoEntity entity) throws Exception {
		Serializable t = super.save(entity);
		// 执行新增操作增强业务
		this.doAddBus(entity);
		return t;
	}

	public void saveOrUpdate(SdMediaInfoEntity entity) throws Exception {
		super.saveOrUpdate(entity);
		// 执行更新操作增强业务
		this.doUpdateBus(entity);
	}

	/**
	 * 新增操作增强业务
	 * 
	 * @param t
	 * @return
	 */
	private void doAddBus(SdMediaInfoEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		// -----------------java增强 end-----------------------------
	}

	/**
	 * 更新操作增强业务
	 * 
	 * @param t
	 * @return
	 */
	private void doUpdateBus(SdMediaInfoEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		// -----------------java增强 end-----------------------------
	}

	/**
	 * 删除操作增强业务
	 * 
	 * @param id
	 * @return
	 */
	private void doDelBus(SdMediaInfoEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		// -----------------java增强 end-----------------------------
	}

	public List<Map<String, Object>> populationMapList(List<SdMediaInfoEntity> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (list == null || list.isEmpty())
			return result;
		for (SdMediaInfoEntity t : list) {
			result.add(populationMap(t));
		}
		return result;
	}

	public Map<String, Object> populationMap(SdMediaInfoEntity t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", t.getId());
		map.put("report_id", t.getReportId());
		map.put("media_type", t.getMediaType());
		map.put("media_url", t.getMediaUrl());
		return map;
	}

	@Override
	public void saveByWechat(String reportId, String fileList) throws Exception {
		if (StringUtils.isEmpty(fileList))
			return;
		JSONArray jsonA = JSONArray.parseArray(fileList);
		for (int i = 0; i < jsonA.size(); i++) {
			JSONObject json = jsonA.getJSONObject(i);
			SdMediaInfoEntity entity = new SdMediaInfoEntity();
			entity.setReportId(reportId);
			entity.setMediaType(json.getString("media_type"));
			entity.setMediaUrl(json.getString("media_url"));
			save(entity);
		}
	}
}
