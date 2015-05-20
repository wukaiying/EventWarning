<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>分类检索</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href='http://fonts.useso.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
<link href='http://fonts.useso.com/css?family=Dosis' rel='stylesheet' type='text/css'>
<link href="css/calendar-eightysix-default.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/calendar-eightysix-osx-dashboard.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/calendar-eightysix-vista.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/mootools-1.2.4-core.js"></script>
<script type="text/javascript" src="js/mootools-1.2.4.2-more.js"></script>
<script type="text/javascript" src="js/calendar-eightysix-v1.0.1.js"></script>
<script type="text/javascript">
		window.addEvent('domready', function() {
			new CalendarEightysix('exampleI', 	 { 'offsetY': -4 });
			new CalendarEightysix('exampleII', 	 { 'startMonday': true, 'format': '%m.%d.%Y', 'slideTransition': Fx.Transitions.Back.easeOut, 'draggable': true, 'offsetY': -4 });
	});	
</script>
</head>
<body>
<div class="header-top">
  <div class="wrap">
    <div class="logo"> <a href="IndexServlet"><img src="images/logo.gif" alt=""/></a> </div>
    <div class="cssmenu">
      <nav id="nav" role="navigation"> <a href="#nav" title="Show navigation">Show navigation</a> <a href="#" title="Hide navigation">Hide navigation</a>
        <ul class="clearfix">
          <li><a href="IndexServlet"><span>首页</span></a></li>
          <li><a href="hotmap.jsp"><span>热点分布</span></a></li>
          <li><a href="wordcloud.jsp"><span>热词分析</span></a></li>
          <li><a href="eventlist.jsp"><span>事件中心</span></a></li>
          <li class="active"><a href="aboutus.jsp"><span>关于我们</span></a></li>
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
	      <li class="active"><a>出错啦</a></li>
	    </ul>
  	<div class="content-box-right">
      <div class="textbox">
        <form>
          <input type="text" value="Act Big! Ultimate Music Festival" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'Act Big! Ultimate Music Festival';}">
        </form>
      </div>
      <div class="search_box">
        <form>
          <select id="country" name="country" onChange="change_country(this.value)" class="frm-field required">
            <option value="null">Your Location</option>
            <option value="AX">Åland Islands</option>
            <option value="AF">Afghanistan</option>
          </select>
        </form>
      </div>
      <div class="reservation">
        <form>
          <div class="form-text">
            <input id="exampleI" name="dateI" value="name" type="text" maxlength="10" style="width:80px;">
          </div>
          <div class="clear"></div>
        </form>
      </div>
    </div>
    <div class="clear"></div>
  </div>
</div>

<div class="main">
  <div class="wrap">

	  	
          <form action="eventlist.jsp" method="post">
            <input type="text" name="skey" value="Search...." onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'Search';}">
           	<select id="tg" name="tg" onChange="change_country(this.value)" class="frm-field required">
            <option value="2">关键字</option>
            <option value="3">地理位置</option>
            <option value="4">事件类别</option>
            </select>
            <input type="submit" value="查找">
          </form>


    </div>
  </div>
</div>

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