package com.eventwarning.bean;

import java.util.Map;

public class WeiboComment {
	public String commentID;
	public String weiboID;
	public String userID;
	public String content;
	public String createAt;
	public String collectAt;
	public double sentiment;
	
	public WeiboComment(Map row){
		this.commentID = row.get("commentID").toString();
		this.weiboID = row.get("weiboID").toString();
		this.userID = row.get("userID").toString();
		this.content = row.get("content").toString();
		this.createAt = row.get("createAt").toString();
		this.collectAt = row.get("collectAt").toString();
		if(row.get("sentiment")!=null)
			this.sentiment = Double.parseDouble(row.get("sentiment").toString());
		else
			this.sentiment = 0;
	}

}
