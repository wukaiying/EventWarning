package com.eventwarning.bean;

import java.util.List;
import java.util.Map;

import com.eventwarning.dbImpl.DBOperation;

public class WeiboData {
	public String weiboID;
	public String getWeiboID(){
		return this.weiboID;
	}
	public String userID;
	public String getUserID(){
		return this.userID;
	}
	public int eventID;
	public int getEventID(){
		return this.eventID;
	}
	public String content;
	public String getContent(){
		return this.content;
	}
	public int commentNum;
	public int getCommentNum(){
		return this.commentNum;
	}
	public int repostNum;
	public int getRepostNum(){
		return this.repostNum;
	}
	public String createAt;
	public String getCreateAt(){
		return this.createAt;
	}
	public String collectAt;
	public String getCollectAt(){
		return this.collectAt;
	}
	private List<WeiboComment> commentList;
	public double sentiment;
	public double getSentiment(){
		return this.sentiment;
	}
	
	public WeiboData(Map row){
		this.weiboID = row.get("weiboID").toString();
		this.userID = row.get("userID").toString();
		this.eventID = row.get("eventID")==null?-1: Integer.parseInt(row.get("eventID").toString());
		this.content = row.get("content").toString();
		this.commentNum = Integer.parseInt(row.get("commentNum").toString());
		this.repostNum = Integer.parseInt(row.get("repostNum").toString());
		this.createAt = row.get("createAt").toString();
		this.collectAt = row.get("collectAt").toString();
		this.commentList = null;
		this.sentiment = row.get("sentiment")==null?0:Double.parseDouble(row.get("sentiment").toString());
	}
	public List<WeiboComment> getCommentList(){
		if(commentList==null)
			commentList = DBOperation.GetCommentList(weiboID);// 从数据库中获取评论列表
		return commentList;
	}
	public static String GetWeiboUrl(String weiboID){
		//通过 ID 获取微博的链接地址
		return null;
	}

}
