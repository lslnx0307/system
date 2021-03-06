package com.jsjf.model.activity;

import java.sql.Timestamp;
import java.util.Date;

import com.jsjf.common.Utils;

public class JsActivityAggregationPage {

	private Integer id;//id
	private String title;//活动标题
	private String activityDate;//活动时间
	private String pcUrl;//pc活动链接
	private String pcPic;//pc图片
	private String appUrl;//app活动链接
	private String appPic;//app图片
	private Integer isTop;//是否置顶 0-不置顶，1-置顶
	private Integer status;//状态 0-进行中，1-已结束
	private Integer addUser;//添加人
	private Integer addTime;//添加时间
	private Integer updateUser;//修改人
	private Date updateTime;//修改时间
	private Integer terminalTypePC;//终端类型-PC
	private Integer terminalTypeH5;//终端类型-H5
	private Integer terminalTypeIOS;//终端类型-IOS
	private Integer terminalTypeAndroid;//终端类型-安卓
	private Date  arrStartTime;//上架时间
	private Date  arrEndTime;//下架时间
	private String arrStartTimeStr;
	private String arrEndTimeStr;
	
	
	public String getArrStartTimeStr() {
		return Utils.format(arrStartTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getArrEndTimeStr() {
		return Utils.format(arrEndTime, "yyyy-MM-dd HH:mm:ss");
	}

	public JsActivityAggregationPage(){
		
	}
	
	public JsActivityAggregationPage(Integer id, String title, String activityDate, String pcUrl, String pcPic,
			String appUrl, String appPic, Integer isTop, Integer status, Integer addUser, Integer addTime,
			Integer updateUser, Date updateTime, Integer terminalTypePC, Integer terminalTypeH5,
			Integer terminalTypeIOS, Integer terminalTypeAndroid,Date arrStartTime,Date arrEndTime) {
		this.id = id;
		this.title = title;
		this.activityDate = activityDate;
		this.pcUrl = pcUrl;
		this.pcPic = pcPic;
		this.appUrl = appUrl;
		this.appPic = appPic;
		this.isTop = isTop;
		this.status = status;
		this.addUser = addUser;
		this.addTime = addTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.terminalTypePC = terminalTypePC;
		this.terminalTypeH5 = terminalTypeH5;
		this.terminalTypeIOS = terminalTypeIOS;
		this.terminalTypeAndroid = terminalTypeAndroid;
		this.arrStartTime = arrStartTime;
		this.arrEndTime = arrEndTime;
	}



	public Date getArrStartTime() {
		return arrStartTime;
	}

	public void setArrStartTime(Date arrStartTime) {
		this.arrStartTime = arrStartTime;
	}

	public Date getArrEndTime() {
		return arrEndTime;
	}

	public void setArrEndTime(Date arrEndTime) {
		this.arrEndTime = arrEndTime;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getActivityDate() {
		return activityDate;
	}


	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}


	public String getPcUrl() {
		return pcUrl;
	}


	public void setPcUrl(String pcUrl) {
		this.pcUrl = pcUrl;
	}


	public String getPcPic() {
		return pcPic;
	}


	public void setPcPic(String pcPic) {
		this.pcPic = pcPic;
	}


	public String getAppUrl() {
		return appUrl;
	}


	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}


	public String getAppPic() {
		return appPic;
	}


	public void setAppPic(String appPic) {
		this.appPic = appPic;
	}


	public Integer getIsTop() {
		return isTop;
	}


	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getAddUser() {
		return addUser;
	}


	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}


	public Integer getAddTime() {
		return addTime;
	}


	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}


	public Integer getUpdateUser() {
		return updateUser;
	}


	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getTerminalTypePC() {
		return terminalTypePC;
	}

	public void setTerminalTypePC(Integer terminalTypePC) {
		this.terminalTypePC = terminalTypePC;
	}

	public Integer getTerminalTypeH5() {
		return terminalTypeH5;
	}

	public void setTerminalTypeH5(Integer terminalTypeH5) {
		this.terminalTypeH5 = terminalTypeH5;
	}

	public Integer getTerminalTypeIOS() {
		return terminalTypeIOS;
	}

	public void setTerminalTypeIOS(Integer terminalTypeIOS) {
		this.terminalTypeIOS = terminalTypeIOS;
	}

	public Integer getTerminalTypeAndroid() {
		return terminalTypeAndroid;
	}

	public void setTerminalTypeAndroid(Integer terminalTypeAndroid) {
		this.terminalTypeAndroid = terminalTypeAndroid;
	}

	@Override
	public String toString() {
		return "JsActivityAggregationPage [id=" + id + ", title=" + title + ", activityDate=" + activityDate
				+ ", pcUrl=" + pcUrl + ", pcPic=" + pcPic + ", appUrl=" + appUrl + ", appPic=" + appPic + ", isTop="
				+ isTop + ", status=" + status + ", addUser=" + addUser + ", addTime=" + addTime + ", updateUser="
				+ updateUser + ", updateTime=" + updateTime + ", terminalTypePC=" + terminalTypePC + ", terminalTypeH5="
				+ terminalTypeH5 + ", terminalTypeIOS=" + terminalTypeIOS + ", terminalTypeAndroid="
				+ terminalTypeAndroid + ", arrStartTime=" + arrStartTime + ", arrEndTime=" + arrEndTime + "]";
	}
	
	
}
