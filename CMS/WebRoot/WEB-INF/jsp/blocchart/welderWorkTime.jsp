<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊工焊接工作时间</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
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
	<script type="text/javascript" src="resources/js/getTime.js"></script>
	<script type="text/javascript" src="resources/js/session-overdue.js"></script>
	<script type="text/javascript" src="resources/js/blocchart/welderWorkTime.js"></script>

  </head>
  <body class="easyui-layout">
	<div id="chartLoading" style="width:100%;height:100%;">
		<div id="chartShow" style="width:160px;" align="center"><img src="resources/images/load1.gif"/>数据加载中，请稍候...</div>
	</div>
    <div id="bodydiv" region="center"  hide="true"  split="false">
	  	<div id="search_btn">
			<div style="margin-bottom: 5px;">
				<input  name="afresh" id="afresh" type="hidden" value="${afreshLogin }"/>
				时间：
				<input class="easyui-datetimebox" name="dtoTime1" id="dtoTime1">--
				<input class="easyui-datetimebox" name="dtoTime2" id="dtoTime2">
				组织机构：
				<select class="easyui-combobox" id="parent" name="parent" data-options="editable:false"></select>
				<a href="javascript:serach();" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
			</div>
		</div>
		<div id="welderDivParent">
			<div id="charts" style="height:96%;width:100%;"></div>
		</div>
		<div id="dgdiv" style="float:right;width:48%;height:95%;">
			<div id="dgTable_btn">
		      <div style="margin-bottom: 5px;margin-left:10px;">
		      	<input type="radio" name="importDg" value="0" style="width:25px;"/>当前
		      	<input type="radio" name="importDg" value="1" style="width:25px;" checked="checked"/>全部
		        <a href="javascript:exporWelderWorkTime();" class="easyui-linkbutton" iconCls="icon-excel" >导出</a>
		      </div>
		    </div>&nbsp;&nbsp;
	    	<table id="dg" style="table-layout: fixed; width:100%;"></table>
		</div>
	</div>
  </body>
</html>
