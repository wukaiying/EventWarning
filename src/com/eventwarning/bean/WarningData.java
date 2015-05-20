package com.eventwarning.bean;

import java.util.Map;

import com.eventwarning.dbImpl.DBOperation;
import com.eventwarning.dbutils.DBConnection;

public class WarningData {
	public double hotd;
	public double sentimentd;
	public double strongd;
	public double actived;
	
	public static double maxWeiboNum;
	public static double minWeiboNum;
	public static double maxCommentNum;
	public static double minCommentNum;
	public static double maxRepostNum;
	public static double minRepostNum;
	public static double maxUniqueUserNum;
	public static double minUniqueUserNum;
	public static double maxVUserNum;
	public static double minVuserNum;
	public static double maxActiveDegree;
	public static double minActiveDegree;
	public static double maxSentimentTendency;
	public static double minSentimentTendency;
	
	public WarningData(){
		GetWarnData();
	}
	
	public static void GetWarnData(){
		String sql = "select MIN(weiboNum),MIN(repostNum),MIN(commentNum),MIN(uniqueUserNum),MIN(vUserNum),MIN(activityDegree),MIN(sentimentTendency)"
				+",MAX(weiboNum),MAX(repostNum),MAX(commentNum),MAX(uniqueUserNum),MAX(vUserNum),MAX(activityDegree),MAX(sentimentTendency)"
				+" from eventdynamic";
		Map row = DBConnection.getSelected(sql).getRows()[0];
		maxWeiboNum = Double.parseDouble(row.get("MAX(weiboNum)").toString());
		minWeiboNum = Double.parseDouble(row.get("MIN(weiboNum)").toString());
		maxCommentNum = Double.parseDouble(row.get("MAX(commentNum)").toString());
		minCommentNum = Double.parseDouble(row.get("MIN(commentNum)").toString());
		maxRepostNum = Double.parseDouble(row.get("MAX(repostNum)").toString());
		minRepostNum = Double.parseDouble(row.get("MIN(repostNum)").toString());
		maxUniqueUserNum = Double.parseDouble(row.get("MAX(uniqueUserNum)").toString());
		minUniqueUserNum = Double.parseDouble(row.get("MIN(uniqueUserNum)").toString());
		maxVUserNum = Double.parseDouble(row.get("MAX(vUserNum)").toString());
		minVuserNum = Double.parseDouble(row.get("MIN(vuserNum)").toString());
		maxActiveDegree = Double.parseDouble(row.get("MAX(activityDegree)").toString());
		minActiveDegree = Double.parseDouble(row.get("MIN(activityDegree)").toString());
		maxSentimentTendency = Double.parseDouble(row.get("MAX(sentimentTendency)").toString());
		minSentimentTendency = Double.parseDouble(row.get("MIN(sentimentTendency)").toString());
	}
	
	public double getHotD(EventDynamic ed){
		double ans = 0;
		ans += (ed.weiboNum-minWeiboNum)/(maxWeiboNum-minWeiboNum)*0.3;
		ans += (ed.uniqueUserNum-minUniqueUserNum)/(maxUniqueUserNum-minUniqueUserNum)*0.1;
		ans += (ed.commentNum - minCommentNum)/(maxCommentNum-minCommentNum)*0.3;
		ans += (ed.repostNum-minRepostNum)/(maxRepostNum-minRepostNum)*0.3;
		return ans*100;
	}
	public double getSentimentD(EventDynamic ed){
		return (maxSentimentTendency - ed.sentimentTendency)/(maxSentimentTendency-minSentimentTendency)*30;
	}
	public double getActiveD(EventDynamic ed){
		return (ed.activeDegree-minActiveDegree + 1)/(maxActiveDegree-minActiveDegree)*100;
	}
	public double getStrongD(EventDynamic ed){
		double ans=0;
		ans += (ed.uniqueUserNum-minUniqueUserNum)/(maxUniqueUserNum-minUniqueUserNum)*0.7;
		ans += (ed.vUserNum*1.0/ed.uniqueUserNum)*0.3;
		return ans*100;
	}
	public int getWarnD(EventDynamic ed){
		double wd = getHotD(ed)*0.3+getSentimentD(ed)*0.3+getActiveD(ed)*0.1+getStrongD(ed)*0.3;
		if(wd<35)
			return 1;
		if(wd<50)
			return 2;
		if(wd<75)
			return 3;
		if(wd<85)
			return 4;
		return 5;
	}
}
