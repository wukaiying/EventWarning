	package com.eventwarning.dbutils;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class DBConnection {
	private static Connection conn=null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	
	/*
	 * 静态代码块是类加载时自动执行的，方法不管是不是静态，都是需要调用的，如果你写在静态代码块里，外部怎么调用？写静态方法直接写在类中就行啦。
                         如果你要在静态代码块里写代码，不用写方法，直接写语句就行了。
	* */
	static{
		Properties properties = new Properties();
		try {
			properties.load(DBConnection.class.getClassLoader().getResourceAsStream("./db.properties"));
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			user = properties.getProperty("username");
			password = properties.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 连接数据库
	 * 定义成静态的方法，不需要new类，直接写类名调用
	 * */
	public static Connection getConnection(){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn){
		if(conn != null) {				//如果conn连接对象不为空
			try {
				conn.close();			//关闭conn连接对象对象
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(PreparedStatement conn){
		if(ps != null) {				//如果ps预处理对象不为空
			try {
				ps.close();			//关闭ps预处理对象
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet conn){
		if(rs != null) {				//如果rs结果集对象不为null
			try {
				rs.close();				//关闭rs结果集对象
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 操作数据库
	 */
	public static Result getSelected(String sql){
		//数据库查询操作
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Result r = null;
		try{
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			r = ResultSupport.toResult(rs);
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			DBConnection.close(rs);
			DBConnection.close(ps);
			DBConnection.close(conn);
		}
		return r;
	}
}
