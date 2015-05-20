package com.eventwarning.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.eventwarning.dbImpl.DBOperation;

public class GetCategoryServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetCategoryServlet() {
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
	  	int num = 0;
	  	if(request.getParameter("num")!=null)
	  		num = Integer.parseInt(request.getParameter("num"));
	  	List<String> categorylist;
	  	JSONArray json = null;
	  	try{
	  		categorylist = DBOperation.GetCategoryStrList(num);
		  	json = JSONArray.fromObject(categorylist);
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
