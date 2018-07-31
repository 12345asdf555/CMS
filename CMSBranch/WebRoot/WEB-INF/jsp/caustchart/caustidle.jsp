<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>事业部焊机闲置率</title>
    
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
	<script type="text/javascript" src="resources/js/caustchart/caustidle.js"></script>
  </head>
  
  <body class="easyui-layout">
	<div id="chartLoading" style="width:100%;height:100%;">
		<div id="chartShow" style="width:160px;" align="center"><img src="resources/images/load1.gif"/>数据加载中，请稍候...</div>
	</div>
    <div id="body" region="center"  hide="true"  split="true" title="事业部焊机闲置率" style="background: witch; height: 335px;">
	  	<div id="caustIdle_btn">
			<div style="margin-bottom: 5px;">
				<input  name="parent" id="parent" type="hidden" value="${parent }"/>
				<input  name="afresh" id="afresh" type="hidden" value="${afreshLogin }"/>
				<input  name="parentime1" id="parentime1" type="hidden" value="${parentime1 }"/>
				<input  name="parentime2" id="parentime2" type="hidden" value="${parentime2 }"/>
				时间：
				<input class="easyui-datetimebox" name="dtoTime1" id="dtoTime1">--
				<input class="easyui-datetimebox" name="dtoTime2" id="dtoTime2">
				闲置时长：
				<select class="easyui-combobox" name="otype" id="otype"></select>
				<a href="javascript:serachcaustIdle();" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
		</div>
		<div id="parentMsg"><h2>${str }</h2></div>
		<div id="explain" style="table-layout: fixed; width:18%; float:left;margin-top: 10%;margin-left:10px;">
		按组织机构和日期对设备闲置率趋势统计：<br/>
		统计时间段内的各部门设备闲置趋势；<br/>
		X轴：日期<br/>
		Y轴：闲置数量(台)<br/></div>
		<div id="caustIdleChart" style="height:50%;width:65%;margin-right: 21%;margin-left: 21%;margin-bottom:10px;"></div>
		
	    <table id="caustIdleTable" style="table-layout: fixed; width:100%;"></table>
	</div>
  </body>
</html>
