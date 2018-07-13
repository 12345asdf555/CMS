<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>设备利用率</title>
    
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
	<script type="text/javascript" src="resources/js/blocchart/useratio.js"></script>
	<script type="text/javascript" src="resources/js/getDate.js"></script>

  </head>
  <body class="easyui-layout">
	<div id="chartLoading" style="width:100%;height:100%;">
		<div id="chartShow" style="width:160px;" align="center"><img src="resources/images/load1.gif"/>数据加载中，请稍候...</div>
	</div>
    <div id="body" region="center"  hide="true"  split="false" title="设备利用率" style="background: witch;">
	  	<div id="search_btn">
			<div style="margin-bottom: 5px;">
				时间：
				<input class="easyui-datebox" name="dtoTime1" id="dtoTime1">--
				<input class="easyui-datebox" name="dtoTime2" id="dtoTime2">
				组织机构：
				<select class="easyui-combobox" id="parent" name="parent" data-options="editable:false"></select>
				<a href="javascript:serach();" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
		</div>
		<div id="explain" style="table-layout: fixed; width:18%; float:left;margin-top: 6%;margin-left:10px;">
		按组织机构对设备利用率进行统计：<br/>
		统计时间段内的设备利用情况；<br/>
		利用率=设备运行时长/设备台数/选择的时间段天数<br/>
		X轴：组织机构<br/>
		Y轴：利用率<br/></div>
		<div id="charts" style="height:50%;width:65%;margin-right: 21%;margin-left: 21%;margin-bottom:10px;"></div>
	    <table id="dg" style="table-layout: fixed; width:100%;"></table>
	</div>
  </body>
</html>
