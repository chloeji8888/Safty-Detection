package com.jszy.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;;

public final class JsonUtil {

	/**
	 * 解析成多级
	 * 
	 * @param source
	 * @return
	 * @throws JSONException
	 */
	public static List<Map<String, Object>> json2ListMoryLayer(String source) throws JSONException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = new JSONArray(source);
		for (int i = 0, len = jsonArray.length(); i < len; i++) {
			if (JSONObject.class.isAssignableFrom(jsonArray.get(i).getClass())) {
				list.add(json2MapMoryLayer(jsonArray.getJSONObject(i).toString()));
			} else if (JSONArray.class.isAssignableFrom(jsonArray.get(i).getClass())) {
				list.addAll(json2ListMoryLayer(jsonArray.getJSONArray(i).toString()));
			}
		}
		return list;
	}

	/**
	 * 解析成多级
	 * 
	 * @param source
	 * @return
	 * @throws JSONException
	 */
	public static Map<String, Object> json2MapMoryLayer(String source) throws JSONException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		JSONObject json = new JSONObject(source);
		String[] names = JSONObject.getNames(json);
		if (names != null && names.length > 0) {
			for (String key : names) {
				if (JSONObject.class.isAssignableFrom(json.get(key).getClass())) {
					map.put(key, json2MapMoryLayer(json.getJSONObject(key).toString()));
				} else if (JSONArray.class.isAssignableFrom(json.get(key).getClass())) {
					map.put(key, json2ListMoryLayer(json.getJSONArray(key).toString()));
				} else {
					map.put(key, json.get(key).toString());
				}
			}
		}
		return map;
	}

	/**
	 * 将层级只有一级的json装成map
	 * 
	 * @param source
	 * @return
	 */
	public static Map<String, String> json2map(String source) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		JSONObject json = new JSONObject(source);
		String[] names = JSONObject.getNames(json);
		if (names != null && names.length > 0) {
			for (String key : names) {
				if (JSONObject.class.isAssignableFrom(json.get(key).getClass())) {
					// map.put(key, json2MapMoryLayer(json.getJSONObject(key).toString()));
				} else if (JSONArray.class.isAssignableFrom(json.get(key).getClass())) {
					// map.put(key, json2ListMoryLayer(json.getJSONArray(key).toString()));
				} else {
					map.put(key, json.get(key).toString());
				}
			}
		}
		return map;
	}

	public static String getValue(JSONObject json, String key) {
		if (json.has(key)) {
			String value = json.getString(key);
			return value;
		}
		return null;
	}

}
