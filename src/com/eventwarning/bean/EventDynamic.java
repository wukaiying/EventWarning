package com.eventwarning.bean;

import java.text.DecimalFormat;
import java.util.Map;

import com.eventwarning.dbImpl.DBOperation;

public class EventDynamic {
	public static DecimalFormat   DF   =new   java.text.DecimalFormat("#.00");
	public int dynamicID;
	public int getDynamicID(){
		return this.dynamicID;
	}
	public Event event; // <-- 修改
	public Event getEvent(){
		return this.event;
	}
	public String updateTime;
	public String getUpdateTime(){
		return this.updateTime;
	}
	public int weiboNum;
	public int getWeiboNum(){
		return this.weiboNum;
	}
	public int repostNum;
	public int getRepostNum(){
		return this.repostNum;
	}
	public int commentNum;
	public int getCommentNum(){
		return this.commentNum;
	}
	public int uniqueUserNum;
	public int getUniqueUserNum(){
		return this.uniqueUserNum;
	}
	public int vUserNum;
	public int getVUserNum(){
		return this.vUserNum;
	}
	public int postiveNum; //积极
	public int getPostiveNum(){
		return this.postiveNum;
	}
	public int neutralNum; //中立
	public int getNeutraNum(){
		return this.neutralNum;
	}
	public int negativeNum; //消极
	public int getNegativeNum(){
		return this.negativeNum;
	}
	
	public int getPostivePercent(){
		if(this.postiveNum+this.negativeNum+this.neutralNum>0)
			return (int)(this.postiveNum*100.0/(this.postiveNum+this.negativeNum+this.neutralNum));
		return 0;
	}
	public int getNegativePercent(){
		if(this.postiveNum+this.negativeNum+this.neutralNum>0)
			return (int)(this.negativeNum*100.0/(this.postiveNum+this.negativeNum+this.neutralNum));
		return 0;
	}
	public int getNeutralPercent(){
		if(this.postiveNum+this.negativeNum+this.neutralNum>0)
			return (int)(this.neutralNum*100.0/(this.postiveNum+this.negativeNum+this.neutralNum));
		return 0;
	}
	public double sentimentTendency;
	public double getSentimentTendency(){
		return this.sentimentTendency;
	}
	public double activeDegree;
	public double getActiveDegree(){
		return this.activeDegree;
	}
	public double getHotDegree(EventDynamic ed){
		double ans = 0;
		ans += (this.weiboNum - ed.weiboNum)*0.4;
		ans += (this.commentNum - ed.commentNum)*0.25;
		ans += (this.repostNum - ed.repostNum)*0.25;
		ans += (this.uniqueUserNum - ed.uniqueUserNum)*0.1;
		return ans;
	}
	public EventDynamic(Map row){
		this.dynamicID = Integer.parseInt(row.get("dynamicID").toString());
		this.event = DBOperation.GetEvent(Integer.parseInt(row.get("eventID").toString()));// <-- 修改
		this.updateTime = row.get("updateTime").toString();
		this.weiboNum = Integer.parseInt(row.get("weiboNum").toString());
		this.repostNum = Integer.parseInt(row.get("repostNum").toString());
		this.commentNum = Integer.parseInt(row.get("commentNum").toString());
		this.uniqueUserNum = Integer.parseInt(row.get("uniqueUserNum").toString());
		this.vUserNum = Integer.parseInt(row.get("vUserNum").toString());
		String []sentimentNum = row.get("sentimentNum").toString().split(",");
		if(sentimentNum.length>=1){
			this.postiveNum = Integer.parseInt(sentimentNum[0]);
			this.neutralNum = Integer.parseInt(sentimentNum[1]);
			this.negativeNum = Integer.parseInt(sentimentNum[2]);
		}
		else
			this.postiveNum=this.negativeNum=this.neutralNum=0;
	}

	public EventDynamic() {
		// TODO Auto-generated constructor stub
		this.weiboNum=this.commentNum=this.repostNum=this.uniqueUserNum=this.vUserNum=0;
		this.dynamicID=this.postiveNum=this.negativeNum=this.neutralNum=0;
		this.event=null; this.activeDegree=this.sentimentTendency=0;this.updateTime="";
	}
}
