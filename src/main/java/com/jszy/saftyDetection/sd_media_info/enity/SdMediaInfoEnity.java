package com.jszy.safetyDetection.sd_media_info.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 反馈媒体信息
 * @author onlineGenerator
 * @date 2020-01-15 15:01:15
 * @version V1.0
 *
 */
@Entity
@Table(name = "sd_media_info", schema = "")
@SuppressWarnings("serial")
public class SdMediaInfoEntity implements java.io.Serializable {
	/** 主键 */
	private java.lang.String id;
	/** 上报记录 */
	@Excel(name = "上报记录", width = 15)
	private java.lang.String reportId;
	/** 媒体类型 */
	@Excel(name = "媒体类型", width = 15)
	private java.lang.String mediaType;
	/** 媒体文件地址 */
	@Excel(name = "媒体文件地址", width = 15)
	private java.lang.String mediaUrl;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name = "ID", nullable = false, length = 36)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             主键
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 上报记录
	 */

	@Column(name = "REPORT_ID", nullable = false, length = 36)
	public java.lang.String getReportId() {
		return this.reportId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             上报记录
	 */
	public void setReportId(java.lang.String reportId) {
		this.reportId = reportId;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 媒体类型
	 */

	@Column(name = "MEDIA_TYPE", nullable = false, length = 32)
	public java.lang.String getMediaType() {
		return this.mediaType;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             媒体类型
	 */
	public void setMediaType(java.lang.String mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 媒体文件地址
	 */

	@Column(name = "MEDIA_URL", nullable = false, length = 500)
	public java.lang.String getMediaUrl() {
		return this.mediaUrl;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             媒体文件地址
	 */
	public void setMediaUrl(java.lang.String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
}
