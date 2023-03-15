/** 
 * Project Name	:	tec_project_evaluation 
 * File Name	:	FileUtil.java 
 * Package Name	:	com.jszy.util 
 * Date			:	2019年4月9日 下午3:58:53 
 * Copyright (c) 2019, www.jszysoft.com All Rights Reserved. 
 */

package com.jszy.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName : FileUtil <br/>
 * Function : TODO ADD FUNCTION. <br/>
 * Reason : TODO ADD REASON(可选). <br/>
 * date : 2019年4月9日 下午3:58:53 <br/>
 * 
 * @author 414647623@qq.com
 * @version
 * @since JDK 1.8
 */
public class FileUtil
{
	public static void delFolder(String folderPath)
	{
		try
		{
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path)
	{
		boolean flag = false;
		File file = new File(path);
		if (!file.exists())
		{
			return flag;
		}
		if (!file.isDirectory())
		{
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++)
		{
			if (path.endsWith(File.separator))
			{
				temp = new File(path + tempList[i]);
			}
			else
			{
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile())
			{
				temp.delete();
			}
			if (temp.isDirectory())
			{
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static void downLoadFile(String filepath, HttpServletResponse response) throws Exception
	{
		File file = new File(filepath);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		bis = new BufferedInputStream(new FileInputStream(file));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		while (true)
		{
			int bytesRead;
			if (-1 == (bytesRead = bis.read(buff, 0, buff.length)))
				break;
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
}
