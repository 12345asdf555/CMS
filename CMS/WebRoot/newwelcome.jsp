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
	<script type="text/javascript" src="resources/js/getTime.js"></script>
	<script type="text/javascript" src="resources/js/welcome/newwelcome.js"></script>
  </head>
  
  <body style="background:#ffffff;">
	<div id="person" style="float:left;height:70%;width:48%;margin-top:10px;"></div>
	<div id="welder" style="float:right;height:70%;width:48%;margin-top:10px;"></div>
	<div style="height:5px;width:100%;float:left;background-color:#e9e9e9"></div>
	<div style="height:27%;width:100%;float:left;background-color:#CCCCCC">
		<div class="wcchart">
			<a href="javascript:openParentMethod(0);"><div id="div1" style="margin-top:10px;margin-bottom:10px;"><img src="resources/images/wc-01.png" width="20%"></div></a>
			焊工工作量排行
		</div>
		<div class="wcchart">
			<a href="javascript:openParentMethod(1)"><div style="margin-top:10px;margin-bottom:10px;"><img src="resources/images/wc-02.png" width="20%"></div></a>
			班组设备利用率
		</div>
		<div class="wcchart">
			<a href="javascript:openParentMethod(2)"><div style="margin-top:10px;margin-bottom:10px;"><img src="resources/images/wc-03.png" width="20%"></div></a>
			焊接规范符合率
		</div>
	</div>
  </body>
</html>
