<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊机焊接工艺超标回溯</title>
    
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
	<script type="text/javascript" src="resources/js/junctionchart/junctionoverproof.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="焊机焊接工艺超标回溯" style="background: witch; height: 335px;">
		<div style="margin-bottom: 5px; "align="center">
			<input  name="afresh" id="afresh" type="hidden" value="${afreshLogin }"/>
			<input  name="welderno" id="welderno" type="hidden" value="${welderno }"/>
			<input  name="machineno" id="machineno" type="hidden" value="${machineno }"/>
			<input  name="junctionno" id="junctionno" type="hidden" value="${junctionno }"/>
			<input  name="time" id="time" type="hidden" value="${time }"/>
			<input  name="itemid" id="itemid" type="hidden" value="${itemid }"/>
			<a class="easyui-linkbutton"  href="javascript:addtime()" iconCls="icon-add">加速</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" href="javascript:reducetime()" iconCls="icon-remove">减速</a>
		</div>
		<div><h2>${str }</h2></div>
		<div id="explain" style="table-layout: fixed; width:18%; float:left;margin-top: 120px;margin-left:10px;">
		按组织机构和日期对超规范焊接趋势统计：<br/>
		统计时间段内的超规范焊接回溯；<br/>
		X轴：日期<br/>
		Y轴：超标时长(s)<br/></div>
		<div id="junctionOverproofChart" style="height:500px;width:80%; margin: 21%;margin-bottom: 20px; margin-top: 20px;"></div>
	</div>
  </body>
</html>
