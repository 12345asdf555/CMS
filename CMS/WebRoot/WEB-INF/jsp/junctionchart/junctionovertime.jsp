<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊口超时待机</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	
	<script type="text/javascript" src="resources/js/load.js"></script>
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/echarts.js"></script>
	<script type="text/javascript" src="resources/js/session-overdue.js"></script>
	<script type="text/javascript" src="resources/js/junctionchart/junctionovertime.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="焊口超时待机" style="background: witch; height: 335px;">
	  	<div id="junctionOvertime_btn">
			<div style="margin-bottom: 5px;">
				<input  name="afresh" id="afresh" type="hidden" value="${afreshLogin }"/>
				<input  name="parent" id="parent" type="hidden" value="${parent }"/>
				<input  name="weldtime" id="weldtime" type="hidden" value="${weldtime }"/>
				<input  name="time1" id="time1" type="hidden" value="${time1 }"/>
				<input  name="time2" id="time2" type="hidden" value="${time2 }"/>
				<input  name="number" id="number" type="hidden" value="${number }"/>
			</div>
		</div>
		<div><h2>${str }</h2></div>
	    <table id="junctionOvertimeTable" style="table-layout: fixed; width:100%;"></table>
	</div>
  </body>
</html>
