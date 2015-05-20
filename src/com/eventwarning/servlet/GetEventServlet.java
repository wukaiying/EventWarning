package com.eventwarning.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.eventwarning.bean.EventDynamic;
import com.eventwarning.dbImpl.DBOperation;

public class GetEventServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetEventServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
	  	resp.setContentType("application/json;character=utf-8");
	  	resp.setHeader("Cache-Control", "no-cache");
	  	int tg = 0, curPage = 1;
	  	String skey = "KeyWordNeeded";
	  	if(request.getParameter("tg")!=null)
	  		tg = Integer.parseInt(request.getParameter("tg"));
	  	if(request.getParameter("curPage")!=null)
	  		curPage = Integer.parseInt(request.getParameter("curPage"));
	  	if(request.getParameter("skey")!=null)
	  		skey = request.getParameter("skey");
	  	List<EventDynamic> elist = null;
	  	int PageSize = 10, totalPage = 10;
	  	JSONArray json = null;
	  	try{
		  	switch(tg){
		  	case 0: //取最热事件
		  	case 1: //取最新更新事件
		  		elist = DBOperation.GetEventsDynamicList(tg, curPage, PageSize);
		  		break;
		  	case 2: //按关键字查找
		  	case 3: //按地区查找
		  	case 4: //按类别查找
		  		elist = DBOperation.GetEventsDynamicList(tg, skey, curPage, PageSize);
		  		break;
		  	}
		  	Map result = new HashMap();
		  	result.put("totalPage", totalPage);
		  	result.put("curPage", curPage);
		  	result.put("eventlist", elist);
		  	json = JSONArray.fromObject(result);
		  	System.out.println(json.toString());
		  	resp.getWriter().write(json.toString());
	  	}
	  	catch(Exception e){e.printStackTrace();}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
