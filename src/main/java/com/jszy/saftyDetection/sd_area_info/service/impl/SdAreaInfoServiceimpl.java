package com.jszy.safetyDetection.sd_area_info.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jszy.safetyDetection.sd_area_info.entity.SdAreaInfoEntity;
import com.jszy.safetyDetection.sd_area_info.service.SdAreaInfoServiceI;

@Service("sdAreaInfoService")
@Transactional
public class SdAreaInfoServiceImpl extends CommonServiceImpl implements SdAreaInfoServiceI {

	public void delete(SdAreaInfoEntity entity) throws Exception {
		super.delete(entity);
		// 执行删除操作增强业务
		this.doDelBus(entity);
	}

	public Serializable save(SdAreaInfoEntity entity) throws Exception {
		Serializable t = super.save(entity);
		// 执行新增操作增强业务
		this.doAddBus(entity);
		return t;
	}

	public void saveOrUpdate(SdAreaInfoEntity entity) throws Exception {
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
	private void doAddBus(SdAreaInfoEntity t) throws Exception {
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
	private void doUpdateBus(SdAreaInfoEntity t) throws Exception {
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
	private void doDelBus(SdAreaInfoEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		// -----------------java增强 end-----------------------------
	}

	public Map<String, Object> populationMap(SdAreaInfoEntity t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", t.getId());
		// map.put("create_name", t.getCreateName());
		// map.put("create_by", t.getCreateBy());
		// map.put("create_date", t.getCreateDate());
		// map.put("update_name", t.getUpdateName());
		// map.put("update_by", t.getUpdateBy());
		// map.put("update_date", t.getUpdateDate());
		map.put("area_code", t.getAreaCode());
		map.put("area_name", t.getAreaName());
		return map;
	}

	@Override
	public List<Map<String, Object>> queryList() {
		List<SdAreaInfoEntity> list = getList(SdAreaInfoEntity.class);
		return populationMaps(list);
	}

	private List<Map<String, Object>> populationMaps(List<SdAreaInfoEntity> list) {
		if (list == null || list.isEmpty())
			return new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (SdAreaInfoEntity t : list)
			result.add(populationMap(t));
		return result;
	}

}
