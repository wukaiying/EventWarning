package com.eventwarning.bean;

import java.util.Map;

public class UserInfo {
	public String userID;
	public String userName;
	public int fensNum;
	public Boolean isV;
	public Boolean isMember;
	public Boolean isInsider;
	public int weiboNum;
	public String registerTime;
	public UserInfo(Map row){//从数据库读出数据，通过数据行建立事件对象
		this.userID = row.get("userID").toString();
		this.userName = row.get("userName").toString();
		this.fensNum = Integer.parseInt(row.get("fensNum").toString());
		this.isV = Boolean.parseBoolean(row.get("isV").toString());
		this.isMember = Boolean.parseBoolean(row.get("isMember").toString());
		this.isInsider = Boolean.parseBoolean(row.get("isInsider").toString());
		this.weiboNum = Integer.parseInt(row.get("weiboNum").toString());
		this.registerTime = row.get("registerTime").toString();
	}
	public static String GetUserInfoUrl(String userID){
		return null;
	}

}
