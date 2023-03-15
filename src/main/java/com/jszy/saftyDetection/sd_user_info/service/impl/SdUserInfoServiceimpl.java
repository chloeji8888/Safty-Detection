package com.jszy.safetyDetection.sd_user_info.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jszy.safetyDetection.sd_user_info.entity.SdUserInfoEntity;
import com.jszy.safetyDetection.sd_user_info.service.SdUserInfoServiceI;
import com.jszy.util.ExportExcelUtil;

@Service("sdUserInfoService")
@Transactional
public class SdUserInfoServiceImpl extends CommonServiceImpl implements SdUserInfoServiceI {

	public void delete(SdUserInfoEntity entity) throws Exception {
		super.delete(entity);
		// 执行删除操作增强业务
		this.doDelBus(entity);
	}

	public Serializable save(SdUserInfoEntity entity) throws Exception {
		Serializable t = super.save(entity);
		// 执行新增操作增强业务
		this.doAddBus(entity);
		return t;
	}

	public void saveOrUpdate(SdUserInfoEntity entity) throws Exception {
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
	private void doAddBus(SdUserInfoEntity t) throws Exception {
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
	private void doUpdateBus(SdUserInfoEntity t) throws Exception {
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
	private void doDelBus(SdUserInfoEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		// -----------------java增强 end-----------------------------
	}

	public Map<String, Object> populationMap(SdUserInfoEntity t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", t.getId());
		map.put("open_id", t.getOpenId());
		map.put("user_name", t.getUserName());
		map.put("phone", t.getPhone());
		map.put("address", t.getAddress());
		map.put("email", t.getEmail());
		map.put("qq_number", t.getQqNumber());
		map.put("bank_name", t.getBankName());
		map.put("bank_no", t.getBankNo());
		return map;
	}

	private List<Map<String, String>> populationMaps(List<SdUserInfoEntity> list) {
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		if (list != null && list.size() > 0) {
			for (SdUserInfoEntity t : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", t.getId());
				map.put("open_id", t.getOpenId());
				map.put("user_name", StringUtils.isEmpty(t.getUserName()) ? "" : t.getUserName());
				map.put("phone", StringUtils.isEmpty(t.getPhone()) ? "" : t.getPhone());
				map.put("address", StringUtils.isEmpty(t.getAddress()) ? "" : t.getAddress());
				map.put("email", StringUtils.isEmpty(t.getEmail()) ? "" : t.getEmail());
				map.put("qq_number", StringUtils.isEmpty(t.getQqNumber()) ? "" : t.getQqNumber());
				map.put("bank_name", StringUtils.isEmpty(t.getBankName()) ? "" : t.getBankName());
				map.put("bank_no", StringUtils.isEmpty(t.getBankNo()) ? "" : t.getBankNo());
				maps.add(map);
			}
		}
		return maps;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void export(SdUserInfoEntity entity, HttpServletRequest request, HttpServletResponse response) {
		List<SdUserInfoEntity> list = getList(SdUserInfoEntity.class);
		String sFolder = request.getRealPath("/") + "export";
		String filepath = sFolder + "/用户信息导出.xls";
		// 获取excel标题
		Map<String, List<String>> title = formateTitle();
		// 获取excel内容
		List<Map<String, String>> result = populationMaps(list);
		ExportExcelUtil.exportExcel(response, title, result, "用户信息导出", sFolder, filepath);
	}

	private Map<String, List<String>> formateTitle() {
		String titleStr = "微信id,用户姓名 ,手机号,住址,邮箱,QQ号码,开户行,银行卡号";
		Map<String, List<String>> title = new HashMap<>();
		List<String> titleList = new ArrayList<>(Arrays.asList(titleStr.split(",")));
		title.put("head", titleList);
		String bodyStr = "open_id;user_name;phone;address;email;qq_number;bank_name;bank_no";
		List<String> bodyList = new ArrayList<>(Arrays.asList(bodyStr.split(";")));
		title.put("body", bodyList);
		return title;
	}

}
