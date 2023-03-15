package com.jszy.safetyDetection.syncView;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SyncOrder {
	// 工单来源
	private String formOrigin;
	// 工单内容
	private String contentText;
	// 客户类型
	private String cusType;
	// 客户性别
	private String cusSex;
	// 市民姓名
	private String cusName;
	// 联系号码
	private String cusPhone;

	// 市民地址
	private String cusAddress;
	// 身份证号码
	private String idCardNumber;
	// 是否公开市民信息
	private String whConPerInfo;
	// 诉求地址
	private String busiAddress;
	// 附件
	private List<SyncOrderFiles> formAttachList;

	public String getFormOrigin() {
		return formOrigin;
	}

	public void setFormOrigin(String formOrigin) {
		this.formOrigin = formOrigin;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getCusSex() {
		return cusSex;
	}

	public void setCusSex(String cusSex) {
		this.cusSex = cusSex;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusPhone() {
		return cusPhone;
	}

	public void setCusPhone(String cusPhone) {
		this.cusPhone = cusPhone;
	}

	public String getCusAddress() {
		return cusAddress;
	}

	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getWhConPerInfo() {
		return whConPerInfo;
	}

	public void setWhConPerInfo(String whConPerInfo) {
		this.whConPerInfo = whConPerInfo;
	}

	public String getBusiAddress() {
		return busiAddress;
	}

	public void setBusiAddress(String busiAddress) {
		this.busiAddress = busiAddress;
	}

	public List<SyncOrderFiles> getFormAttachList() {
		return formAttachList;
	}

	public void setFormAttachList(List<SyncOrderFiles> formAttachList) {
		this.formAttachList = formAttachList;
	}

	public String toSignString() {
		List<String> str = new ArrayList<String>();
		if (StringUtils.isNotEmpty(formOrigin)) {
			str.add("formOrigin:" + formOrigin);
		}
		if (StringUtils.isNotEmpty(contentText)) {
			str.add("contentText:" + contentText);
		}
		if (StringUtils.isNotEmpty(cusType)) {
			str.add("cusType:" + cusType);
		}
		if (StringUtils.isNotEmpty(cusSex)) {
			str.add("cusSex:" + cusSex);
		}
		if (StringUtils.isNotEmpty(cusName)) {
			str.add("cusName:" + cusName);
		}
		if (StringUtils.isNotEmpty(cusPhone)) {
			str.add("cusPhone:" + cusPhone);
		}
		if (StringUtils.isNotEmpty(cusAddress)) {
			str.add("cusAddress:" + cusAddress);
		}
		if (StringUtils.isNotEmpty(idCardNumber)) {
			str.add("idCardNumber:" + idCardNumber);
		}
		if (StringUtils.isNotEmpty(whConPerInfo)) {
			str.add("whConPerInfo:" + whConPerInfo);
		}
		if (StringUtils.isNotEmpty(busiAddress)) {
			str.add("busiAddress:" + busiAddress);
		}
		if (formAttachList != null && formAttachList.size() > 0) {
			for (SyncOrderFiles orderFile : formAttachList) {
				str.add("attachName:" + orderFile.getAttachName());
				str.add("filePath:" + orderFile.getFilePath());
			}
		}
		return StringUtils.join(str, ",");
	}

}
