package com.jszy.util;

public class UploadInfo {
	/***
	 * 文件上传后的路径，含有后缀
	 */
	private String filePath;
	/***
	 * 文件上传后的名称，含有后缀
	 */
	private String fileName;
	
	/***
	 * 原来文件名称，含有后缀
	 */
	private String oldFileName;
	
	/**
	 * 后缀
	 */
	private String suffix;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOldFileName() {
		return oldFileName;
	}
	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
