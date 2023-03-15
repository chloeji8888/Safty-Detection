package com.jszy.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PdfUtils {
	// 利用模板生成pdf
	public static void pdfOut(String templatePath // pdf模版文件的位置
			, String newPDFPath // 生成的pdf文件的位置
			, Map<String, Object> textMap // 存放文本的map
			, Map<String, Object> imgMap) throws Exception { // 存放图片路径的map
		FileOutputStream out = new FileOutputStream(newPDFPath);// 输出流
		PdfReader reader = new PdfReader(templatePath);// 读取pdf模板
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PdfStamper stamper = new PdfStamper(reader, bos);
		AcroFields form = stamper.getAcroFields();
		// 处理文本域内容
		if (null != textMap && textMap.size() != 0) {
			for (String key : textMap.keySet()) {
				String value = textMap.get(key) + "";
				form.setField(key, value);
			}
		}
		// 处理图片域内容
		if (null != imgMap && imgMap.size() != 0) {
			for (String key : imgMap.keySet()) {
				String fieldName = key;
				int pageNo = form.getFieldPositions(fieldName).get(0).page;
				Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
				// 设置图片坐标
				float x = signRect.getLeft();
				float y = signRect.getBottom() + signRect.getHeight() / 4;
				// 获取图片
				Image image = Image.getInstance(imgMap.get(key) + "");
				// 获取操作的页面
				PdfContentByte under = stamper.getOverContent(pageNo);
				// 根据域的大小缩放图片
				image.scaleToFit(signRect.getWidth(), signRect.getHeight());
				// 添加图片
				image.setAbsolutePosition(x, y);
				under.addImage(image);
			}
		}

		stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
		stamper.close();
		Document doc = new Document();
		PdfCopy copy = new PdfCopy(doc, out);
		doc.open();
		PdfImportedPage importPage = null;
		/// 循环是处理成品只显示一页的问题
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), i);
			copy.addPage(importPage);
		}
		doc.close();
		reader.close();
		bos.close();
		out.close();
	}

	// 合并pdf文件的方法
	public static boolean mergePdfFiles(String path // pdf模版文件夹的位置
			, String newfile) throws Exception { // 合并的pdf文件的位置
		List<String> fileList = traverseFolder(path);
		boolean retValue = false;
		morePdfTopdf(fileList, newfile);
		retValue = true;
		return retValue;
	}

	public static void morePdfTopdf(List<String> fileList, String savepath) throws Exception {
		Document document = null;
		PdfReader reader2 = new PdfReader(fileList.get(0));
		document = new Document(reader2.getPageSize(1));
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));
		document.open();
		for (int i = 0; i < fileList.size(); i++) {
			PdfReader reader = new PdfReader(fileList.get(i));
			int n = reader.getNumberOfPages();// 获得总页码
			for (int j = 1; j <= n; j++) {
				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, j);// 从当前Pdf,获取第j页
				copy.addPage(page);
			}
			reader.close();
		}
		document.close();
		reader2.close();
	}

	// 遍历文件夹下的文件
	public static List<String> traverseFolder(String path) {
		// 存放pdf模版文件
		List<String> tempList = new ArrayList<>();
		// 获取文件夹下的所有文件
		File file = new File(path);
		try {
			if (file.exists()) {
				File[] template_files = file.listFiles();
				if (null == template_files || template_files.length == 0) {
					throw new RuntimeException("模版文件夹是空的!");
				} else {
					for (File template : template_files) {
						tempList.add(path + "\\" + template.getName());
					}
					return tempList;
				}
			} else {
				throw new RuntimeException("模版文件夹不存在!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
