<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊口焊接工时</title>
    
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
	<script type="text/javascript" src="resources/js/getTime.js"></script>
	<script type="text/javascript" src="resources/js/junctionchart/junctionhour.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="焊口焊接工时" style="background: witch; height: 335px;">
	  	<div id="junctionHour_btn">
			<div style="margin-bottom: 5px;">
				<input  name="afresh" id="afresh" type="hidden" value="${afreshLogin }"/>
				<input  name="item" id="item" type="hidden" value="${item }"/>
				<input  name="material" id="material" type="hidden" value="${material }"/>
				<input  name="externalDiameter" id="externalDiameter" type="hidden" value="${externalDiameter }"/>
				<input  name="wallThickness" id="wallThickness" type="hidden" value="${wallThickness }"/>
				<input  name="nextexternaldiameter" id="nextexternaldiameter" type="hidden" value="${nextexternaldiameter }"/>
				<input  name="nextmaterial" id="nextmaterial" type="hidden" value="${nextmaterial }"/>
				<input  name="nextwallthickness" id="nextwallthickness" type="hidden" value="${nextwall_thickness }"/>
				<input  name="parentime1" id="parentime1" type="hidden" value="${parentime1 }"/>
				<input  name="parentime2" id="parentime2" type="hidden" value="${parentime2 }"/>
				时间：
				<input class="easyui-datetimebox" name="dtoTime1" id="dtoTime1">--
				<input class="easyui-datetimebox" name="dtoTime2" id="dtoTime2">
				<a href="javascript:serachjunctionHour();" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
		</div>
	    <table id="junctionHourTable" style="table-layout: fixed; width:100%;"></table>
	</div>
  </body>
</html>
