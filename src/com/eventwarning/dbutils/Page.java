package com.eventwarning.dbutils;
public class Page {
	private int everyPage;			//每页显示记录数
	private int totalCount;			//总记录数
	private int totalPage;			//总页数
	private int currentPage;		//当前页
	private int beginIndex;			//查询起始点
	private boolean hasPrePage;		//是否有上一页
	private boolean hasNextPage;	//是否有下一页
	public Page(int everyPage, int totalCount, int totalPage, 
			int currentPage,int beginIndex, boolean hasPrePage,
			boolean hasNextPage) {	//自定义构造方法
		this.everyPage = everyPage;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.beginIndex = beginIndex;
		this.hasPrePage = hasPrePage;
		this.hasNextPage = hasNextPage;
	}
	public Page(){}					//默认构造函数
	public int getEveryPage() {		//获得每页显示记录数
		return everyPage;
	}
	public void setEveryPage(int everyPage) {//设置每页显示记录数
		this.everyPage = everyPage;
	}
	public int getTotalCount() {//获得总记录数
		return totalCount;
	}
	public void setTotalCount(int totalCount) {//设置总记录数
		this.totalCount = totalCount;
	}
	public int getTotalPage() {//获得总页数
		return totalPage;
	}
	public void setTotalPage(int totalPage) {//设置总页数
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {//获得当前页
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {//设置当前页
		this.currentPage = currentPage;
	}
	public int getBeginIndex() {//获得查询起始点
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {//设置查询起始点
		this.beginIndex = beginIndex;
	}
	public boolean isHasPrePage() {//获得是否有上一页
		return hasPrePage;
	}
	public void setHasPrePage(boolean hasPrePage) {//设置是否有上一页
		this.hasPrePage = hasPrePage;
	}
	public boolean isHasNextPage() {//获得是否有下一页
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {//设置是否有下一页
		this.hasNextPage = hasNextPage;
	}
}
