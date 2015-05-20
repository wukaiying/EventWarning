<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.eventwarning.bean.*" %>
<%@ page language="java" import="com.github.abel533.echarts.Option" %>
<%@ page isELIgnored="false" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>事件追踪</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	<link href='http://fonts.useso.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
	<link href='http://fonts.useso.com/css?family=Dosis' rel='stylesheet' type='text/css'>
	<script src="js/jquery.min.js"></script>

  </head>
  
  <body>
  <% 
  	Event event = null;
  	if(request.getAttribute("event")!=null)
  		event = (Event)request.getAttribute("event");
  	else
  		event = new Event();
  		
  	String optionbar = (String)request.getAttribute("keywordbar");
  	String hotline = (String)request.getAttribute("hotline");
  	String sentimentpie = (String)request.getAttribute("sentimentpie");
  	String warningbar = (String)request.getAttribute("warningbar");
  	int warningd = (Integer)request.getAttribute("warningd");
  	boolean showtag = false;
  %>
<div class="header-top">
  <div class="wrap">
    <div class="logo"> <a href="IndexServlet"><img src="images/logo.gif" alt=""/></a> </div>
    <div class="cssmenu">
      <nav id="nav" role="navigation"> <a href="#nav" title="Show navigation">Show navigation</a> <a href="#" title="Hide navigation">Hide navigation</a>
        <ul class="clearfix">
          <li><a href="IndexServlet">首页</a></li>
          <li><a href="wordcloud.jsp"><span>热门词云</span></a></li>
          <li><a href="eventlist.jsp?tg=0&curPage=1"><span>热点事件</span></a></li>
          <li><a href="warninglist.jsp">预警信息</a></li>
          <li><a href="about.jsp">关于我们</a></li>
          <div class="clear"></div>
        </ul>
      </nav>
    </div>
    <div class="buttons">
      <div class="search">
          <form action="SearchServlet">
            <input type="text" name="keyword" value="Search...." onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'Search';}">
            <input type="submit" value="">
          </form>
        </div>
      <div class="clear"></div>
    </div>
    <div class="clear"></div>
    <h2 class="head">Find the <span class="m_1">hot events </span>You'll want <span class="m_1">to know</span></h2>
  </div>
</div>

<div class="main">
  <div class="wrap">
    <div class="feature-desc">      
      <h5 class="m_1"><a>核心报道</a></h5>
      <p class="m_13"><%=event.centuralWeibo.content %></p>
      <div class="links">
          <ul>
            <li><a><img src="images/blog-icon1.png" title="date"><span>时间：<%=event.startTime.substring(0,16) %></span></a></li>
            <li><a><img src="images/blog-icon5.png" title="permalink"><span>地点：<%=event.location %></span></a></li>
            <li><a><img src="images/blog-icon2.png" title="Comments"><span>关键词：<%=event.getKeyWord() %></span></a></li>
            <li><a><img src="images/blog-icon4.png" title="Lables"><span>类别：<%=event.category %></span></a></li>
          </ul>
        </div>
      <div id="keywordbar" style="height:40%;"></div>
    </div>
    <div class="feature-desc">
      <div class="post_meta"><span class="post_author"><em> 动态追踪 </em></span> </div>
      <div id="hotline" style="height:40%"></div>
    <div class="clear"></div>
    <div class="clear"></div>
      <div id="sentimentpie" style="height:40%"></div>
    </div>
    
    
    <div class="feature-desc">
      <div class="post_meta"><span class="post_author"><em> 预警分析 </em></span> </div>
      <h5 class="m_1"><a>预警等级：</a> 
      <c:forEach begin="1" end="<%=warningd%>" step="1">
      	<image src="./images/warning_star.png" style="height:30px; width:30px;"></image>
      </c:forEach>
      </h5>
      <div id="warndetail"> </div>	
     <ul id="showornot">
      	<li><a href="javascript:ShowWarnDetail(1);">显示指标说明</a></li>
     </ul>
    </div>
  </div>
</div>

<script type="text/javascript" src="./js/esl.js"></script>
<script type="text/javascript">
   // 路径配置
     require.config({
         paths: {
             'echarts' : '.js/echarts',
             'echarts/chart/bar' : './js/echarts',
             'echarts/chart/line': './js/echarts',
             'echarts/chart/pie': './js/echarts'
         }
     });
// 使用
require(
      [
          'echarts',
          'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
          'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
          'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
          
      ],
	 function (ec){
	 	DrawKeywordBar(ec);
	 	DrawHotLine(ec);
	 	DrawSentimentPie(ec);
	 }
);
function DrawKeywordBar(ec) {
    // 基于准备好的dom，初始化echarts图表
    var myChart = ec.init(document.getElementById('keywordbar')); 
   	var option = <%=optionbar%>;
	myChart.setOption(option); 
};
function DrawHotLine(ec) {
   // 基于准备好的dom，初始化echarts图表
   var myChart = ec.init(document.getElementById('hotline')); 
   var option = <%=hotline%>;
   myChart.setOption(option);
};
function DrawSentimentPie(ec) {
	// 基于准备好的dom，初始化echarts图表
	var myChart = ec.init(document.getElementById('sentimentpie')); 
	var option = <%=sentimentpie%>;           
	// 为echarts对象加载数据 
	myChart.setOption(option); 
};
function DrawWarningBar(ec) {
	// 基于准备好的dom，初始化echarts图表
	var myChart = ec.init(document.getElementById('warningbar')); 
	var option = <%=warningbar%>;           
	// 为echarts对象加载数据 
	myChart.setOption(option); 
};
function ShowWarnDetail(tg){
	if(tg==1){
		var html = '';
		html +='<div id="warningbar" style="height:40%"></div>';
	    html +='<div class="clear"></div>';    
	    html +='<h5 class="m_1"><a>事件热度</a></h5>';
	    html +='<p class="m_13">该指标表明事件在网络中参与程度</p>';      
	    html +='<h5 class="m_1"><a>情感指数</a></h5>';
	    html +='<p class="m_13">该指标表明事件参与用户的情感态度</p>';      
	    html +='<h5 class="m_1"><a>舆情强度</a></h5>';
	    html +='<p class="m_13">该指标表明事件中用户的影响力、报道来源的可靠性</p>';      
	    html +='<h5 class="m_1"><a>讨论活度</a></h5>';
	    html +='<p class="m_13">该指标表明事件中用户参与讨论的激烈程度</p>';
      	 $('#warndetail').html(html);
      	 require(
		      [
		          'echarts',
		          'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
		          'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
		          'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
		          
		      ],
			 function (ec){
			 	DrawWarningBar(ec);
			 }
		);
		$('#showornot').html('<li><a href="javascript:ShowWarnDetail(0);">收起指标说明</a></li>');
	}
	else{
		$('#warndetail').empty();
		$('#showornot').html('<li><a href="javascript:ShowWarnDetail(1);">显示指标说明</a></li>');
	}
};
</script>

<div class="footer">
  <div class="wrap">
    <div class="footer-bottom">
      <div class="copy">
        <p>© 2015 版权所有 <a href="http://www.buct.edu.cn/" target="_blank" title="北京化工大学">北京化工大学</a> - 研究机构： <a href="http://ielab.buct.edu.cn/" title="北化智能工程研究室" target="_blank">智能工程研究室</a></p>
      </div>
      <div class="clear"></div>
    </div>
  </div>
</div>
</body>
</html>
