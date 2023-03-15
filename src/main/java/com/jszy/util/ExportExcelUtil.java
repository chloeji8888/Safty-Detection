/** 
 * Project Name	:	tec_project_evaluation 
 * File Name	:	ExportExcelUtil.java 
 * Package Name	:	com.jszy.util 
 * Date			:	2019年4月9日 下午4:45:23 
 * Copyright (c) 2019, www.jszysoft.com All Rights Reserved. 
 */

package com.jszy.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * ClassName : ExportExcelUtil <br/>
 * Function : TODO ADD FUNCTION. <br/>
 * Reason : TODO ADD REASON(可选). <br/>
 * date : 2019年4月9日 下午4:45:23 <br/>
 * 
 * @author 414647623@qq.com
 * @version
 * @since JDK 1.8
 */
public class ExportExcelUtil {
	@SuppressWarnings("resource")
	public static void exportExcel(HttpServletResponse response, Map<String, List<String>> title,
			List<Map<String, String>> result, String sheetName, String sFolder, String filepath) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 设置宽度自适应
		sheet.autoSizeColumn(1);
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = null;
		// 获取title集合
		if (!title.containsKey("head"))
			return;
		if (!title.containsKey("body"))
			return;
		List<String> titleList = title.get("head");
		if (titleList == null || titleList.isEmpty())
			return;
		for (int i = 0; i < titleList.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(titleList.get(i));
			cell.setCellStyle(style);
		}
		if (result != null && result.size() > 0)
			for (int rowNum = 0; rowNum < result.size(); rowNum++) {
				row = sheet.createRow((int) rowNum + 1);
				Map<String, String> param = result.get(rowNum);
				List<String> bodyList = title.get("body");
				for (int cellNum = 0; cellNum < bodyList.size(); cellNum++) {
					cell = row.createCell(cellNum);
					cell.setCellValue(param.get(bodyList.get(cellNum)));
					cell.setCellStyle(style);
				}
			}
		try {
			File file = new File(sFolder);
			if (!file.exists()) {
				file.mkdir();
			}
			File file2 = new File(filepath);
			if (file2.exists()) {
				file2.delete();
			}
			FileOutputStream fout = new FileOutputStream(filepath);
			wb.write(fout);
			fout.close();
			FileUtil.downLoadFile(filepath, response);
			file2.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public static void exportExcel(HttpServletResponse response, List<Map<String, List<String>>> titles,
			List<List<Map<String, String>>> results, List<String> sheetNames, String sFolder, String filepath) {
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int num = 0; num < sheetNames.size(); num++) {
			String sheetName = sheetNames.get(num);
			HSSFSheet sheet = wb.createSheet(sheetName);
			Map<String, List<String>> title = titles.get(num);
			List<Map<String, String>> result = results.get(num);
			// 设置宽度自适应
			sheet.autoSizeColumn(1);
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			HSSFCell cell = null;
			// 获取title集合
			if (!title.containsKey("head"))
				return;
			if (!title.containsKey("body"))
				return;
			List<String> titleList = title.get("head");
			if (titleList == null || titleList.isEmpty())
				return;
			for (int i = 0; i < titleList.size(); i++) {
				cell = row.createCell(i);
				cell.setCellValue(titleList.get(i));
				cell.setCellStyle(style);
			}
			if (result != null && result.size() > 0)
				for (int rowNum = 0; rowNum < result.size(); rowNum++) {
					row = sheet.createRow((int) rowNum + 1);
					Map<String, String> param = result.get(rowNum);
					List<String> bodyList = title.get("body");
					for (int cellNum = 0; cellNum < bodyList.size(); cellNum++) {
						cell = row.createCell(cellNum);
						cell.setCellValue(param.get(bodyList.get(cellNum)));
						cell.setCellStyle(style);
					}
				}
		}
		try {
			File file = new File(sFolder);
			if (!file.exists()) {
				file.mkdir();
			}
			File file2 = new File(filepath);
			if (file2.exists()) {
				file2.delete();
			}
			FileOutputStream fout = new FileOutputStream(filepath);
			wb.write(fout);
			fout.close();
			FileUtil.downLoadFile(filepath, response);
			file2.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
