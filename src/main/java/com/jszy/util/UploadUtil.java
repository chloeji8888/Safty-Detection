package com.jszy.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/***
 * 上传公共方法
 * 
 * @author 蒋昊/chiangho
 *
 */
public class UploadUtil {

	public static UploadInfo upload(HttpServletRequest request, String fileType)
			throws IllegalStateException, IOException {
		UploadInfo uploadInfo = new UploadInfo();
		// 上传项目附件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile(fileType);
		if (multipartFile != null) {
			ServletContext application = request.getSession().getServletContext();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String uploadPath = "/upload/" + sdf.format(new Date()) + "/";
			File dir = new File(application.getRealPath(uploadPath));
			if (dir.exists() == false)
				dir.mkdirs();
			// 获取原文件名
			String originaFileName = multipartFile.getOriginalFilename();
			// 获取新的文件名
			String newFile = UUIDGenerator.generate();
			// 获取文件后缀名
			String suffix = originaFileName.substring(originaFileName.lastIndexOf("."));
			File dest = new File(dir, newFile + suffix);
			multipartFile.transferTo(dest);
			uploadInfo.setFileName(newFile + suffix);
			uploadInfo.setOldFileName(originaFileName);
			uploadInfo.setSuffix(suffix);
			String filePath =  uploadPath + newFile + suffix;
			uploadInfo.setFilePath(filePath);
			return uploadInfo;
		}
		return null;
	}
}
