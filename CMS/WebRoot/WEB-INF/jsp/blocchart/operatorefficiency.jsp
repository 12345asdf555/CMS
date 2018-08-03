<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>操作者效率</title>
    
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
	<script type="text/javascript" src="resources/js/blocchart/operatorefficiency.js"></script>
	<script type="text/javascript" src="resources/js/getTime.js"></script>

  </head>
  <body class="easyui-layout">
	<div id="chartLoading" style="width:100%;height:100%;">
		<div id="chartShow" style="width:160px;" align="center"><img src="resources/images/load1.gif"/>数据加载中，请稍候...</div>
	</div>
    <div id="body" region="center"  hide="true"  split="false" title="操作者效率" style="background: witch;">
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
		<div id="explain" style="table-layout: fixed; width:18%; float:left;margin-top: 6%;margin-left:10px;">
		按组织机构对操作者工作效率进行统计：<br/>
		统计时间段内的焊工工作情况；<br/>
		上机率=开机时长/上班时长<br/>
		有效焊接率=焊接时长/开机时长<br/>
		焊接时长=开机时长-待机时长<br/>
		工作效率=焊接时长/上班时长</div>
		<div id="charts" style="height:50%;width:65%;margin-right: 21%;margin-left: 21%;margin-bottom:10px;"></div>
	    <table id="dg" style="table-layout: fixed; width:100%;"></table>
	</div>
  </body>
</html>
