<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<title>热点地图</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href='http://fonts.useso.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
<link href='http://fonts.useso.com/css?family=Dosis' rel='stylesheet' type='text/css'>
</head>

<%
	//String hotmap = (String)request.getAttribute("hotmapoption");
 %>

<body>
<div class="header-top">
  <div class="wrap">
    <div class="logo"> <a href="IndexServlet"><img src="images/logo.gif" alt=""/></a> </div>
    <div class="cssmenu">
      <nav id="nav" role="navigation"> <a href="#nav" title="Show navigation">Show navigation</a> <a href="#" title="Hide navigation">Hide navigation</a>
        <ul class="clearfix">
          <li><a href="IndexServlet"><span>首页</span></a></li>
          <li class="active"><a href="hotmap.jsp"><span>热点分布</span></a></li>
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

<div class="content-box">
	<div class="wrap">
	  	<ul class="events" id="showlabel">
	      <li class="active"><a>热点事件地理位置分布</a></li>
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
     	 <div id="hoteventsmap" style="height:400px;"></div>
      </div>
      <div class="bsidebar span_1_of_blog">
      	<h2 class="head4">选择数据源类别</h2>
        <ul class="list1" id="categorylist">
        </ul>
      </div>
      <div class="clear"></div>
      </div>    
  </div>
</div>
<script type="text/javascript" src="./js/esl.js"></script>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		GetCategoryList(10);
	});
// 路径配置
require.config({
     paths: {
         'echarts' : '.js/echarts',
         'echarts/chart/map' : './js/echarts-map',
     }
});
var myChart;        
  // 使用
  require(
      [
          'echarts',
          'echarts/chart/map', // 使用柱状图就加载bar模块，按需加
          
      ],
	 function (ec){
		// 基于准备好的dom，初始化echarts图表
		myChart = ec.init(document.getElementById('hoteventsmap'));
		drawMap('');
	}
);
	function drawMap(skey){
		$('#showlabel').html('<p class="f-text1"> 数据加载中...</p>');
		var option;
		$.ajax({
				type:"post",
				url:"HotMapServlet",
				data:{skey:skey},
				dataType:"json",
				success:function(data){
					option = data;//绑定鼠标点击事件
					var eConfig = require('echarts/config');
					myChart.on(eConfig.EVENT.MAP_SELECTED, function(param){
						var selected = param.selected;
						var mapSeries = option.series[0];
						var name, location='null';
						for(var i=0,l=mapSeries.data.length; i<l; i++){
							name = mapSeries.data[i].name;
							mapSeries.data[i].selected = selected[name];
							if(selected[name]){
								if(option.series[0].mapType == 'china')
									option.series[0].mapType = name;
								else
									location = name;
							}
						}
						if(location != 'null')
							window.location.href = 'eventlist.jsp?tg=3&skey='+location;
						myChart.setOption(option, true);
					});
					// 为echarts对象加载数据 
					myChart.setOption(option);
					if(skey=='')
						skey = '全部类别';
					$('#showlabel').html('<p class="f-text1">'+skey+'</p>');
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
			html += '<li><a href="javascript:drawMap(\'\')"';
			html += '><B>0. 全部类别 </B></a></li>';
			for(var i=0; i<num; i++){
				var skeys = data[i].split(',');
				var skey = skeys[0];
				if(skeys.length>1)
					skey = skeys[1];
				html += '<li><a href="javascript:drawMap(\'' + skey +'\')"';
				html += '>' + (i+1) +'. '+ skey + '</a></li>';
			}
			$('#categorylist').html(html);
		}
	});
}
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