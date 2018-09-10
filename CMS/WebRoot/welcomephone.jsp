<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>欢迎使用</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/easyui.css" />
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/echarts.js"></script>
	<script type="text/javascript" src="resources/js/android/welcome.js"></script>
  </head>
  <body style="background:#24375b;color:#fff;">
  	<div id="person" style="float:left;height:65%;width:48%;margin-top:10px;"></div>
	<div id="welder" style="float:right;height:65%;width:48%;margin-top:10px;"></div>
	<div style="width:96%;float:left;border:2px solid #5d99dd;border-radius: 30px;margin-left:20px;margin-right:20px;">
		<div class="wcchart" style="width:10%"></div>
		<div class="wcchart">
			<a href="javascript:livedata(0);"><div id="div1" style="margin-top:10px;margin-bottom:10px;"><img src="resources/images/welder_03.png" width="28%"></div></a>
			工作：<span id="work">0</span>
		</div>
		<div class="wcchart" >
			<a href="javascript:livedata(1)"><div style="margin-top:10px;margin-bottom:10px;"><img src="resources/images/welder_02.png" width="28%"></div></a>
			待机：<span id="standby">0</span>
		</div>
		<div class="wcchart">
			<a href="javascript:livedata(2)"><div style="margin-top:10px;margin-bottom:10px;"><img src="resources/images/welder_01.png" width="28%"></div></a>
			报警：<span id="warn">0</span>
		</div>
		<div class="wcchart" style="width:10%"></div>
	</div>
  </body>
</html>
