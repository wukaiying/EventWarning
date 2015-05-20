package com.eventwarning.dbImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.jstl.sql.Result;

import com.eventwarning.bean.Event;
import com.eventwarning.bean.EventDynamic;
import com.eventwarning.bean.KeyWord;
import com.eventwarning.bean.UserInfo;
import com.eventwarning.bean.WeiboComment;
import com.eventwarning.bean.WeiboData;
import com.eventwarning.dbutils.DBConnection;

public class DBOperation {
	public static Event GetEvent(int eventid){
		String sql = "select * from event where eventID = "+eventid;
		Result rs = DBConnection.getSelected(sql);
		if(rs!=null && rs.getRowCount()>0)
			return new Event(rs.getRows()[0]);
		return null;
	}
	
	public static WeiboData GetWeiboData(String weiboid){
		String sql = "select * from weibodata where weiboID = '"+weiboid+"'";
		Result rs = DBConnection.getSelected(sql);
		if(rs!=null && rs.getRowCount()>0)
			return new WeiboData(rs.getRows()[0]);
		return null;
	}
	
	public static UserInfo GetUserInfo(String userid){
		String sql = "select * from userinfo where userID = '"+userid+"'";
		Result rs = DBConnection.getSelected(sql);
		if(rs!=null && rs.getRowCount()>0)
			return new UserInfo(rs.getRows()[0]);
		return null;
	}
	
	public static WeiboComment GetWeiboComment(String commentid){
		String sql = "select * from weibocomment where commentID = '"+commentid+"'";
		Result rs = DBConnection.getSelected(sql);
		if(rs!=null && rs.getRowCount()>0)
			return new WeiboComment(rs.getRows()[0]);
		return null;
	}
	
	public static KeyWord GetKeyWord(String keywordid){
		String sql = "select * from keyword where keywordID = "+keywordid;
		Result rs = DBConnection.getSelected(sql);
		if(rs!=null && rs.getRowCount()>0)
			return new KeyWord(rs.getRows()[0]);
		return null;
	}

	public static List<WeiboComment> GetCommentList(String weiboID) {
		String sql = "select * from weibocomment where weiboID = '"+weiboID+"'";
		Result rs = DBConnection.getSelected(sql);
		List<WeiboComment> list = new ArrayList<WeiboComment>();
		if(rs!=null && rs.getRowCount()>0)
			for(int i=0; i<rs.getRowCount(); i++)
				list.add(new WeiboComment(rs.getRows()[i]));
		return list;
	}

	public static List<WeiboData> GetWeiboList(int eventID) {
		String sql = "select * from weibodata where eventID = "+eventID+"";
		Result rs = DBConnection.getSelected(sql);
		List<WeiboData> list = new ArrayList<WeiboData>();
		if(rs!=null && rs.getRowCount()>0)
			for(int i=0; i<rs.getRowCount(); i++)
				list.add(new WeiboData(rs.getRows()[i]));
		return list;
	}

	public static List<KeyWord> GetKeyWordList(int num, int eventID) {
		String sql = "select * from (select * from keyword where eventID=" + eventID;
		sql += " and length(keyWord)>3 ORDER BY count DESC) aa GROUP BY wordType ORDER BY count desc Limit " +num;
		if(eventID<=0)
			sql = "select *,sum(count) from keyword where length(keyWord)>3 group by keyword order by sum(count) desc Limit "+num;
		Result rs = DBConnection.getSelected(sql);
		List<KeyWord> list = new ArrayList<KeyWord>();
		if(rs!=null && rs.getRowCount()>0)
			for(int i=0; i<rs.getRowCount(); i++)
				list.add(new KeyWord(rs.getRows()[i]));
		return list;
	}
	
	public static List<EventDynamic> GetEventsDynamicList(int tg, int num){
		//选取最热/最新的事件
		String sql="select *,count(eventID) from (select * FROM eventdynamic ORDER BY updateTime DESC) aa group by eventID ORDER BY count(eventID) DESC";
		if(tg==1)
			sql = "select * from eventdynamic group by eventID order by updateTime desc";
		sql += " LIMIT "+num;
		return GetEventDynamicListBySQL(sql, 0, num);
	}
	
	public static List<EventDynamic> GetEventsDynamicList(int tg, String skey, int curPage, int pageSize){
		//选取最热/最新的事件
		String sql="select *,COUNT(eventID) from eventdynamic where eventID in ";
		if(tg==2)
			sql+=
				"(select eventID from keyword where keyWord like \'%"+ skey +"%\') ";
		else if(tg==3)
			sql += "(select eventID from event where location like \'%"+ skey +"%\') ";
		else if(tg==4)
			sql += "(select eventID from event where category like \'%" + skey +"%\')";
		sql += "GROUP BY(eventID) ORDER BY count(eventID) DESC LIMIT "+curPage*pageSize;
		System.out.println(sql);
		return GetEventDynamicListBySQL(sql, (curPage-1)*pageSize, curPage*pageSize);
	}
	
	public static List<EventDynamic> GetEventsDynamicList(int tg, int curPage, int pageSize){
		//选取最热/最新的事件
		String sql="select *,count(eventID) from eventdynamic group by eventID order by count(eventID) desc";
		if(tg==1)
			sql = "select *,count(eventID) from eventdynamic group by eventID order by updateTime desc";
		sql += " LIMIT "+ curPage*pageSize;
		System.out.println(sql);
		return GetEventDynamicListBySQL(sql, (curPage-1)*pageSize, curPage*pageSize);
	}
	
	public static List<EventDynamic> GetEventDyanmicList(int eventid, int num){
		//选取指定事件的动态
		String sql="select * from eventdynamic where eventID = "+eventid +" order by updateTime asc";
		sql += " LIMIT "+num;
		return GetEventDynamicListBySQL(sql, 0, num);
	}
	public static List<EventDynamic> GetEventDynamicListBySQL(String sql, int s, int e){
		List<EventDynamic> list = new ArrayList<EventDynamic>();
		Result rs = DBConnection.getSelected(sql);
		if(rs!=null && rs.getRowCount()>0)
			for(int i=s; i<rs.getRowCount() && i<=e; i++)
				list.add(new EventDynamic(rs.getRows()[i]));
		return list;
	}
	public static List<Map> GetLocationEvent(){
		String sql = "select *,count(eventID) from event group by location ORDER BY count(eventID) desc";
		Result rs = DBConnection.getSelected(sql);
		List<Map> ans = new ArrayList();
		if(rs!=null && rs.getRowCount()>0){
			for(int i=0; i<rs.getRowCount(); i++){
				Map m = new HashMap();
				m.put("count", Integer.parseInt(rs.getRows()[i].get("count(eventID)").toString()));
				m.put("event",new Event(rs.getRows()[i]));
				ans.add(m);
			}
		}
		return ans;
	}

	public static Integer GetProvinceCount(String province, String keyword) {
		// TODO Auto-generated method stub
		String sql = "select count(eventID) from `event` where location LIKE \'%"
				+ province + "%\' and category LIKE \'%"+ keyword + "%\'";
		Result rs = DBConnection.getSelected(sql);
		return Integer.parseInt(rs.getRows()[0].get("count(eventID)").toString());
	}

	public static List<String> GetKeyWordStrList(int num) {
		// TODO Auto-generated method stub
		String sql = "select keyWord,sum(count) from keyword where length(keyWord)>3 group by keyWord ORDER BY sum(count) desc";
		sql += " LIMIT "+num;
		Result rs = DBConnection.getSelected(sql);
		List<String> ans = new ArrayList();
		for(int i=0; i<rs.getRowCount(); i++){
			ans.add(rs.getRows()[i].get("keyWord").toString());
		}
		return ans;
	}

	public static List<String> GetCategoryStrList(int num) {
		// TODO Auto-generated method stubString sql = "select *,sum(count) from keyword group by eventID ORDER BY sum(count) desc";
		String sql = "select category,sum(category) from event group by category ORDER BY sum(category) desc";
		sql += " LIMIT "+num;
		Result rs = DBConnection.getSelected(sql);
		List<String> ans = new ArrayList();
		for(int i=0; i<rs.getRowCount(); i++){
			ans.add(rs.getRows()[i].get("category").toString());
		}
		return ans;
	}
	public static List<String> GetCityList(){
		String sql = "select CityName from city";
		Result res = DBConnection.getSelected(sql);
		List<String> ans = new ArrayList();
		for(int i=0;i<res.getRowCount(); i++)
			ans.add(res.getRows()[i].get("CityName").toString());
		return ans;
		
	}
}
