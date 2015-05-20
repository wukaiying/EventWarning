package com.eventwarning.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eventwarning.dbImpl.DBOperation;

public class Event {
	public int eventID;
	public int getEventID(){
		return this.eventID;
	}
	public String startTime;
	public String getStartTime(){
		return this.startTime;
	}
	public String location;
	public String getLocation(){
		return this.location;
	}
	public String category;
	public String getCategory(){
		return this.category;
	}
	public WeiboData centuralWeibo; //<-- 修改
	public WeiboData getCenturalWeibo(){
		return this.centuralWeibo;
	}
	private List<WeiboData> weiboList;
	private List<KeyWord> keywordList;
//	public String centuralTF;
//	public String getCenturalTF(){
//		return this.centuralTF;
//	}
	public Event(){
		this.eventID=-1;
		this.centuralWeibo=null;
		this.weiboList = null;
		this.keywordList = null;
	}
	public Event(Map row){ //从数据库读出数据，通过数据行建立事件对象
		this.eventID = Integer.parseInt(row.get("eventID").toString());
		this.startTime = row.get("startTime").toString();
		this.location = row.get("location")==null?"missed":row.get("location").toString();
		this.category = row.get("category")==null?"missed":row.get("category").toString();
		this.centuralWeibo = DBOperation.GetWeiboData(row.get("centuralWeiboID").toString());
		this.weiboList = null;
		this.keywordList = null;
		//this.centuralTF = row.get("centuralTF").toString();
	}
	
	public List<WeiboData> getWeiboList(){
		if(this.weiboList==null)
			this.weiboList = DBOperation.GetWeiboList(eventID); //从数据库读取并创建微博列表
		return this.weiboList;
	}
	public String getKeyWord(){
		String kw = "";
		if(this.keywordList==null)
			getKeyWordList();
		if(this.keywordList.size()>0)
			kw += this.keywordList.get(0).keyword;
		if(this.keywordList.size()>1)
			kw += "." + this.keywordList.get(1).keyword;
		if(this.keywordList.size()>2)
			kw += "." + this.keywordList.get(2).keyword;
		if(this.keywordList.size()>3)
			kw += "." + this.keywordList.get(3).keyword;
		return kw;
	}
	public List<KeyWord> getKeyWordList(){
		if(this.keywordList==null)
			this.keywordList = DBOperation.GetKeyWordList(20, eventID); //从数据库读取并创建微博对象
		return this.keywordList;
	}
	public List<Double> getHotDegreeList(){
		List<Double> hlist = new ArrayList<Double>();
		
		return hlist;
	}
	public static List<EventDynamic> GetEventDynamicList(int eventid, int num){
		return DBOperation.GetEventDyanmicList(eventid, num);
	}
	public static List<DynamicPoint> GetHotPoints(List<EventDynamic> eds){
		List<DynamicPoint> points = new ArrayList<DynamicPoint>();
		EventDynamic ed = new EventDynamic();
		double hd;
		String t;
		for(int i=0; i<eds.size(); i++){
			t = eds.get(i).updateTime;
			hd = eds.get(i).getHotDegree(ed);
			ed = eds.get(i);
			points.add(new DynamicPoint(t,hd));
		}
		return points;
	}
}
