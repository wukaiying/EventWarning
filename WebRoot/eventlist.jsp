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
    <base href="<%=basePath%>">
    
    <title>事件列表</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
	<link href='http://fonts.useso.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
	<link href='http://fonts.useso.com/css?family=Dosis' rel='stylesheet' type='text/css'>
	<script src="js/jquery.min.js"></script>
	<script src="js/easyResponsiveTabs.js" type="text/javascript"></script>
	<script type="text/javascript">
			    $(document).ready(function () {
			        $('#horizontalTab').easyResponsiveTabs({
			            type: 'default', //Types: default, vertical, accordion           
			            width: 'auto', //auto or any width like 600px
			            fit: true   // 100% fit in a container
			        });
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
          <li class="active"><a href="eventlist.jsp"><span>事件中心</span></a></li>
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
<% 
	int tg = 0;
	if(request.getParameter("tg")!=null)
		tg = Integer.parseInt(request.getParameter("tg"));
	//String skey = request.getParameter("skey");
	int curPage = 1;
	if(request.getParameter("curPage")!=null)
		curPage = Integer.parseInt(request.getParameter("curPage"));
	String skey = "";
	if(request.getParameter("skey")!=null)
		skey = request.getParameter("skey");
	int totalPage = 10;
%>
<div class="content-box">
	<div class="wrap">
	  	<ul class="events">
	      <li class="active"><a id="showLabel">事件中心</a></li>
	      <li><a href="javascript:GetEvents(<%=tg %>, <%=curPage %>, '<%=skey %>')">刷新</a></li>
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
      <div id="eventlist"></div>
      <ul class="pagination">
      	<li><a href="javascript:GetEvents(<%=tg %>, <%=1 %>, '<%=skey %>')" class="border skin-border-color4 skin-font-color13 skin-color-hover3 skin-background-hover1">首页</a></li>
		<li><a href="javascript:GetEvents(<%=tg %>, <%=curPage-1 %>, '<%=skey %>')" class="border skin-border-color4 skin-font-color13 skin-color-hover3 skin-background-hover1">上页</a></li>
		<li><a href="javascript:GetEvents(<%=tg %>, <%=curPage+1 %>, '<%=skey %>')" class="border skin-border-color4 skin-font-color13 skin-color-hover3 skin-background-hover1">下页</a></li>
		<li><a href="javascript:GetEvents(<%=tg %>, <%=totalPage %>, '<%=skey %>')" class="border skin-border-color4 skin-font-color13 skin-color-hover3 skin-background-hover1">尾页</a></li>
      </ul>
     </div>
	<script type="text/javascript">
	$(document).ready(function(){
		GetEvents(<%=tg%>,<%=curPage%>, '<%=skey%>');
		GetKeyWordList(10);
		GetCategoryList(10);
	});
	function GetEvents(tg, curPage, skey){
		$('#eventlist').html('<p class="f-text1"> 数据加载中，请稍后...</p>');
		var texts = new Array('最热事件','最新更新',skey, skey,skey);
		$('#showLabel').html(texts[tg]);      
		var url = "<%=basePath%>" + "GetEventServlet";
		var totalPage;
		$.ajax({
			type : "post",
			url : url,
			data:{tg:tg, skey:skey, curPage:curPage},
			dataType:"json",
			success:function(data){
				var html = ''; 
                var eventlist = data[0]['eventlist'];
                if(eventlist.length != 0){
	                curPage = data[0]['curPage'];
	                totalPage = data[0]['totalPage'];
	                $.each(eventlist, function(commentIndex, comment){
	                	var event = comment['event'];
	                    html += '<div class="blog-left">';
						html += '<div class="item_published"> <span>' + event['eventID'] + '</span></div>';
						html += '<div class="item_head">';
						html += '<h5 class="item_title"> <a href="EventServlet?eid=' + event['eventID'] + '">';
						html += '[类别：'+ event['category'] +'] ' + event['location'];
						html += '</a></h5></div><div class="info"><dl class="info_dl"><dd><div class="item-head">';
						html += event['startTime']+'</a></div></dd><div class="clear"></div></dl></div>';
						html += '<p class="f-text1">'+event['centuralWeibo']['content']+'</p>';
						html += '<a href="EventServlet?eid='+event['eventID']+'" class="link">事件追踪</a></div>';
	                });
	                $('#eventlist').html(html);
	            }
	            else{
	            	$('#eventlist').html('<p class="f-text1">无相关事件数据</p>');
	            }
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(textStatus);
            },
            complete: function(XMLHttpRequest, textStatus) {
                this; // 调用本次AJAX请求时传递的options参数
            }
		});
	};
	function GetKeyWordList(num){
		$('#keywordlist').html('<p class="f-text1"> 数据加载中，请稍后...</p>');
		$.ajax({
			type:"post",
			url:"GetKeyWordServlet",
			data:{num:num},
			dataType:"json",
			success:function(data){
				var html = '';
				for(var i=0; i<num; i++){
					html += '<li><a href="javascript:GetEvents(2,1,\''+data[i]+'\')"';
					html += '>' + data[i] + '</a></li>';
				}
				$('#keywordlist').html(html);
			}
		});
	}
	
	function GetCategoryList(num){
		$('#categorylist').html('<p class="f-text1"> 数据加载中，请稍后...</p>');
		$.ajax({
			type:"post",
			url:"GetCategoryServlet",
			data:{num:num},
			dataType:"json",
			success:function(data){
				var html = '';
				for(var i=0; i<num; i++){
					html += '<li><a href="javascript:GetEvents(4,1,\''+data[i]+'\')"';
					html += '>' + (i+1) +'. '+ data[i] + '</a></li>';
				}
				$('#categorylist').html(html);
			}
		});
	}
	</script>      

      <div class="bsidebar span_1_of_blog">
      
		<h2 class="head4">热点词</h2>
        <ul class="tags" id="keywordlist">
        </ul>
        <div class="clear"></div>
		<br> <br>            
        <h2 class="head4">热门事件类别</h2>
        <ul class="list1" id="categorylist">
        </ul>

      </div>
    </div>
    <div class="clear"></div>
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
</body>
</html>
