<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.eventwarning.bean.*" %>
<%@ page isELIgnored="false" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("utf-8");
%>

<!DOCTYPE HTML>
<html>
<head>
<title>网络舆情预警系统</title>
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
          <li class="active"><a href="IndexServlet"><span>首页</span></a></li>
          <li><a href="hotmap.jsp"><span>热点分布</span></a></li>
          <li><a href="wordcloud.jsp"><span>热词分析</span></a></li>
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
<!--   <div class="map"> <img src="images/map.jpg" style="width:100%" alt="#"/> </div> -->
<div class="content-box">
  <div class="wrap">
    <ul class="events">
   <%
   			int tg = 0;
   			if(request.getAttribute("tg")!=null)
   					tg = Integer.parseInt(request.getAttribute("tg").toString()); 
   	%>
   	<c:if test="${tg==0 }">
      <li class="active"><a href="IndexServlet?tg=0">热门事件</a></li>
      <li><a href="IndexServlet?tg=1">最近更新</a></li>
      </c:if>
   <c:if test="${tg==1 }">
      <li><a href="IndexServlet?tg=0">热门事件</a></li>
      <li class="active"><a href="IndexServlet?tg=1">最近更新</a></li>
      </c:if>
    </ul>
    <div class="content-box-right">
      <div class="reservation"></div>
    </div>
    <div class="clear"></div>
  </div>
</div>

<div class="main">
  <div class="wrap">
  <c:forEach items="${requestScope.eventdynamics}" var="ed" varStatus="varStatus">
  	<c:if test="${varStatus.index%3==0}">
    	<div class="section group">
    </c:if>
  	<div class="col_1_of_3 span_1_of_3">
        <div class="desc">
        <c:url value="EventServlet?eid=${ed.event.eventID }" var="eventurl"></c:url>
        <c:url value="eventlist.jsp?tg=3&skey=${ed.event.location}" var="searchlocationurl"></c:url>
          <h3><a href="${eventurl}"><c:out value="${ed.event.keyWord}"/></a></h3>
          <h4 class="m_2"><c:out value="${ed.event.startTime}"/></h4>
          <h5 class="m_3"><a href="${searchlocationurl }">相关地点：<c:out value="${ed.event.location}"/></a></h5>
          <div> <p><c:out value="${ed.event.centuralWeibo.content}"/><p></div>
        </div>
        <div class="section group example">
          <div class="col_1_of_3 span_1_of_3">
            <ul>
              <li>
                <div class="m_desc"><span class="m_2"><c:out value="${ed.postivePercent}"/>%</span><br>
                  <span class="m_3">正面</span></div>
                <div class="clear"></div>
              </li>
            </ul>
          </div>
           <div class="col_1_of_3 span_1_of_3">
            <ul>
              <li>
                <div class="m_desc"><span class="m_2"><c:out value="${ed.neutralPercent}"/>%</span><br>
                  <span class="m_3">中立</span></div>
                <div class="clear"></div>
              </li>
            </ul>
          </div>
          <div class="col_1_of_3 span_1_of_3">
            <ul>
              <li>
                <div class="m_desc"><span class="m_2"><c:out value="${ed.negativePercent}"/>%</span><br>
                  <span class="m_3">负面</span></div>
                <div class="clear"></div>
              </li>
            </ul>
          </div>
          <div class="clear"></div>
        </div>
      </div>
  	<c:if test="${varStatus.index%3==2}">
	  		<div class="clear"></div>
	    </div>
  	</c:if>
  </c:forEach>
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