package com.jszy.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class ZipCompressor {
	static final int BUFFER = 8192;

	private File zipFile;

	public ZipCompressor(String pathName) {
		zipFile = new File(pathName);
	}

	public void compress(String... pathName) {
		ZipOutputStream out = null;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			out = new ZipOutputStream(cos);
			String basedir = "";
			for (int i = 0; i < pathName.length; i++) {
				compress(new File(pathName[i]), out, basedir);
			}
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void compress(String srcPathName) {
		File file = new File(srcPathName);
		if (!file.exists())
			throw new RuntimeException(srcPathName + "不存在！");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			compress(file, out, basedir);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			// System.out.println("压缩：" + basedir + file.getName());
			this.compressDirectory(file, out, basedir);
		} else {
			// System.out.println("压缩：" + basedir + file.getName());
			this.compressFile(file, out, basedir);
		}
	}

	/** 压缩一个目录 */
	private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/** 压缩一个文件 */
	private void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// public static void main(String[] args) {
	// ZipCompressor zc = new ZipCompressor("E:/resource/resource.zip");
	// zc.compress("E:/resource/js", "E:/resource/css", "E:/resource/images");
	// }

	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	// 删除文件夹
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static void compressAllFile(String targetZipRealPath, String wordFilePath) throws Exception {
		List<String> targetFilePathList = traverseFolder(wordFilePath);
		reZipCsvFiles(targetZipRealPath, targetFilePathList);

	}

	public static void reZipCsvFiles(String targetZipRealPath, List<String> targetFilePathList) throws Exception {
		File targetZipFile = new File(targetZipRealPath);
		InputStream in = null;
		FileOutputStream fos = null;
		ZipOutputStream zipOutputStream = null;
		try {
			fos = new FileOutputStream(targetZipFile);
			zipOutputStream = new ZipOutputStream(fos);
			for (String csvFilePath : targetFilePathList) {
				in = new FileInputStream(csvFilePath);
				String csvFileName = csvFilePath.substring(csvFilePath.lastIndexOf(File.separator) + 1);
				zipOutputStream.putNextEntry(new ZipEntry(csvFileName));
				IOUtils.copy(in, zipOutputStream);
				zipOutputStream.closeEntry();
				in.close();
			}
		} finally {
			if (zipOutputStream != null) {
				zipOutputStream.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}
}