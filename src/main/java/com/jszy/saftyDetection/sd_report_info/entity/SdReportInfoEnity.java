package com.jszy.safetyDetection.sd_report_info.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.jszy.safetyDetection.sd_area_info.entity.SdAreaInfoEntity;
import com.jszy.safetyDetection.sd_user_info.entity.SdUserInfoEntity;

/**
 * @Title: Entity
 * @Description: 上报记录
 * @author onlineGenerator
 * @date 2020-01-19 10:06:25
 * @version V1.0
 *
 */
@Entity
@Table(name = "sd_report_info", schema = "")
@SuppressWarnings("serial")
public class SdReportInfoEntity implements java.io.Serializable {
	/** 主键 */
	private java.lang.String id;
	/** 流水号 */
	private java.lang.String serialNumber;
	/** 用户id */
	@Excel(name = "用户id", width = 15)
	// private java.lang.String userId;
	private SdUserInfoEntity userInfo;
	/** 上报区域 */
	private SdAreaInfoEntity area;
	/** 上报时间 */
	@Excel(name = "上报时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date reportTime;
	/** 隐患地点 */
	@Excel(name = "隐患地点", width = 15)
	private java.lang.String hiddenDangerPlace;
	/** 隐患详情 */
	@Excel(name = "隐患详情", width = 15)
	private java.lang.String hiddenDangerDetail;
	/** 流程状态 */
	@Excel(name = "流程状态", width = 15)
	private java.lang.String status;
	/** 受理时间 */
	@Excel(name = "受理时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date acceptTime;
	/** 受理人 */
	@Excel(name = "受理人", width = 15)
	private java.lang.String acceptPeople;
	/** 审核时间 */
	@Excel(name = "审核时间", width = 15, format = "yyyy-MM-dd")
	private java.util.Date auditTime;
	/** 审核人 */
	@Excel(name = "审核人", width = 15)
	private java.lang.String auditPeople;
	/** 奖励金额 */
	@Excel(name = "奖励金额", width = 15)
	private java.math.BigDecimal bonus;
	/** 奖金发放状态 */
	@Excel(name = "奖金发放状态", width = 15)
	private java.lang.String bonusStatus;
	/** 核查备注 */
	@Excel(name = "核查备注", width = 15)
	private java.lang.String acceptRemarks;
	/** 审核备注 */
	@Excel(name = "审核备注", width = 15)
	private java.lang.String remarks;
	/** 是否同步至12350 */
	@Excel(name = "是否同步至12350", width = 15)
	private java.lang.String isSync;
	/** 12350工单ID */
	@Excel(name = "12350工单ID", width = 50)
	private java.lang.String formId;

	private String fileList;

	@Transient
	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}

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
	 * @param: java.lang.String 主键
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	@Column(name = "SERIAL_NUMBER", nullable = false, length = 12)
	public java.lang.String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(java.lang.String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 用户id
	 */
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "USER_ID")
	public SdUserInfoEntity getUserInfo() {
		return this.userInfo;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 用户id
	 */
	public void setUserInfo(SdUserInfoEntity userInfo) {
		this.userInfo = userInfo;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "AREA_ID")
	public SdAreaInfoEntity getArea() {
		return area;
	}

	public void setArea(SdAreaInfoEntity area) {
		this.area = area;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 上报时间
	 */

	@Column(name = "REPORT_TIME", nullable = false, length = 32)
	public java.util.Date getReportTime() {
		return this.reportTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 上报时间
	 */
	public void setReportTime(java.util.Date reportTime) {
		this.reportTime = reportTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 隐患地点
	 */

	@Column(name = "HIDDEN_DANGER_PLACE", nullable = false, length = 100)
	public java.lang.String getHiddenDangerPlace() {
		return this.hiddenDangerPlace;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 隐患地点
	 */
	public void setHiddenDangerPlace(java.lang.String hiddenDangerPlace) {
		this.hiddenDangerPlace = hiddenDangerPlace;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 隐患详情
	 */

	@Column(name = "HIDDEN_DANGER_DETAIL", nullable = false, length = 2000)
	public java.lang.String getHiddenDangerDetail() {
		return this.hiddenDangerDetail;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 隐患详情
	 */
	public void setHiddenDangerDetail(java.lang.String hiddenDangerDetail) {
		this.hiddenDangerDetail = hiddenDangerDetail;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 流程状态
	 */

	@Column(name = "STATUS", nullable = false, length = 2)
	public java.lang.String getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 流程状态
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 审核时间
	 */

	@Column(name = "AUDIT_TIME", nullable = true, length = 32)
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 审核时间
	 */
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 审核人
	 */

	@Column(name = "ACCEPT_PEOPLE", nullable = true, length = 32)
	public java.lang.String getAcceptPeople() {
		return this.acceptPeople;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 审核人
	 */
	public void setAcceptPeople(java.lang.String acceptPeople) {
		this.acceptPeople = acceptPeople;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 审核时间
	 */

	@Column(name = "ACCEPT_TIME", nullable = true, length = 32)
	public java.util.Date getAcceptTime() {
		return this.acceptTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 审核时间
	 */
	public void setAcceptTime(java.util.Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 审核人
	 */

	@Column(name = "AUDIT_PEOPLE", nullable = true, length = 32)
	public java.lang.String getAuditPeople() {
		return this.auditPeople;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 审核人
	 */
	public void setAuditPeople(java.lang.String auditPeople) {
		this.auditPeople = auditPeople;
	}

	/**
	 * 方法: 取得java.math.BigDecimal
	 * 
	 * @return: java.math.BigDecimal 奖励金额
	 */

	@Column(name = "BONUS", nullable = true, scale = 2, length = 8)
	public java.math.BigDecimal getBonus() {
		return this.bonus;
	}

	/**
	 * 方法: 设置java.math.BigDecimal
	 * 
	 * @param: java.math.BigDecimal 奖励金额
	 */
	public void setBonus(java.math.BigDecimal bonus) {
		this.bonus = bonus;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 奖金发放状态
	 */

	@Column(name = "BONUS_STATUS", nullable = true, length = 1)
	public java.lang.String getBonusStatus() {
		return this.bonusStatus;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 奖金发放状态
	 */
	public void setBonusStatus(java.lang.String bonusStatus) {
		this.bonusStatus = bonusStatus;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 审核备注
	 */

	@Column(name = "REMARKS", nullable = true, length = 1000)
	public java.lang.String getRemarks() {
		return this.remarks;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 审核备注
	 */
	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 核查备注
	 */

	@Column(name = "ACCEPT_REMARKS", nullable = true, length = 1000)
	public java.lang.String getAcceptRemarks() {
		return this.acceptRemarks;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 核查备注
	 */
	public void setAcceptRemarks(java.lang.String acceptRemarks) {
		this.acceptRemarks = acceptRemarks;
	}

	@Column(name = "IS_SYNC", nullable = true, length = 1)
	public java.lang.String getIsSync() {
		return isSync;
	}

	public void setIsSync(java.lang.String isSync) {
		this.isSync = isSync;
	}

	@Column(name = "FORM_ID", nullable = true, length = 50)
	public java.lang.String getFormId() {
		return formId;
	}

	public void setFormId(java.lang.String formId) {
		this.formId = formId;
	}

}
