/** 
 * Project Name	:	tec_project_evaluation 
 * File Name	:	ExportExcelUtil.java 
 * Package Name	:	com.jszy.util 
 * Date			:	2019年4月9日 下午4:45:23 
 * Copyright (c) 2019, www.jszysoft.com All Rights Reserved. 
 */

package com.jszy.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
public class ExportPdfUtil {
	public static void exportExcel(HttpServletResponse response, List<Map<String, Object>> listPDFmap, String isSingle,
			String wordFilePath) {
		File file = new File(wordFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			// 生成pdf文件
			createPDFFiles(wordFilePath, listPDFmap, isSingle);
			// 生成zip文件
			ZipCompressor.compressAllFile(wordFilePath + "safety.zip", wordFilePath);
			FileUtil.downLoadFile(wordFilePath + "safety.zip", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 删除目前的文件下的所有文件
			FileUtil.delFolder(wordFilePath);
		}
	}

	@SuppressWarnings("unchecked")
	private static void createPDFFiles(String wordFilePath, List<Map<String, Object>> listMap, String isSingle)
			throws Exception {
		if (listMap != null && listMap.size() > 0) {
			for (Map<String, Object> map : listMap) {
				String template_path = map.get("template_path") + "";
				Map<String, Object> textMap = (Map<String, Object>) map.get("textMap");
				String filePath = wordFilePath + map.get("file_path") + "" + ".pdf";
				// 调用生成pdf方法
				PdfUtils.pdfOut(template_path, filePath, textMap, (Map<String, Object>) map.get("imgMap"));
			}
			if ("1".equals(isSingle)) {
				// 导出一个pdf
				// 调用整合pdf方法
				// 传入pdf所在文件 wordFilePath
				String newfile = wordFilePath + "allPdf.pdf";
				PdfUtils.mergePdfFiles(wordFilePath, newfile);
			}
		}
	}
}
