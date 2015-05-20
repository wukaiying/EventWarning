<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.eventwarning.bean.*" %>
<%@ page isELIgnored="false" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>热点词云</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href='http://fonts.useso.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
<link href='http://fonts.useso.com/css?family=Dosis' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="http://fonts.useso.com/css?family=Arimo:400,700,400italic">

<link rel="stylesheet" type="text/css" href="./css/jqcloud.css" />

</head>
<body>
<div class="header-top">
  <div class="wrap">
    <div class="logo"> <a href="IndexServlet"><img src="images/logo.gif" alt=""/></a> </div>
    <div class="cssmenu">
      <nav id="nav" role="navigation"> <a href="#nav" title="Show navigation">Show navigation</a> <a href="#" title="Hide navigation">Hide navigation</a>
        <ul class="clearfix">
          <li><a href="IndexServlet"><span>首页</span></a></li>
          <li><a href="HotMapServlet"><span>热点分布</span></a></li>
          <li class="active"><a href="wordcloud.jsp"><span>热词分析</span></a></li>
          <li><a href="eventlist.jsp"><span>事件中心</span></a></li>
          <li><a href="aboutus.jsp"><span>关于我们</span></a></li>
          <div class="clear"></div>
        </ul>
      </nav>
    </div>
    <div class="buttons">
        <div class="search">
          <form action="eventlist.jsp" method="post">
	        <select id="tg" name="tg">
	            <option value="2">关键字</option>
	            <option value="3">位置</option>
	            <option value="4">类别</option>
	        </select>
            <input type="text" name="skey" value="Search...." onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'Search';}">
            <input type="submit" value="">
          </form>
        </div>
      <div class="clear"></div>
    </div>
    <div class="clear"></div>
    <h2 class="head">Find the <span class="m_1">hot events </span>You'll want <span class="m_1">to know</span></h2>
  </div>
</div>

<div class="content-box">
	<div class="wrap">
	  	<ul class="events">
	      <li class="active"><a href="javascript:drawword()">热点词汇云图展示</a></li>
	    </ul>
  	<div class="content-box-right">
      <div class="reservation"></div>
    </div>
    <div class="clear"></div>
  </div>
</div>

<div class="main">
  <div class="wrap">
  
	<div class="section group">
      <div class="cont span_2_of_blog" >				
		<div class="blog-left" id="wordcloud" style="width:100%; height:60%;margin:0;">
		</div>
  		<div class="clear"></div>
  	  </div>
  	  <div class="bsidebar span_1_of_blog">        
        <h2 class="head4">相关事件</h2>
        <ul class="list1">
        <c:forEach begin="1" end="8" step="1" varStatus="i">
          <li><a href="#">${i.index}. consectetuer adipiscing elit</a></li>
        </c:forEach>
        </ul>

      </div>
  	  
  	</div>  
	<div class="clear"></div>
 </div>
</div>


<script type="text/javascript" src="./js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="./js/jqcloud-1.0.4.js"></script>
<script type="text/javascript">
    $(document).ready(drawword());
    function drawword() {
		var url = "<%=basePath%>" + "WordCloudServlet";
	    $.ajax( { //ajax异步获取后台动态数据
	        type : "POST",
	        url : url,
	        contentType : "application/json",
	        dataType : "json",//此处要设置成json 
	        success : function(result){
	        	var word_array = result;
	        	document.getElementById("wordcloud").innerHTML='';
	        	$("#wordcloud").jQCloud(word_array,{
            				shape :"rectangular", //形状样式
	        	});
	        }
	    });
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