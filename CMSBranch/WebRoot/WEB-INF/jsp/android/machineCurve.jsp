<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>实时界面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="resources/css/main.css">
<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="resources/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
<link rel="stylesheet" type="text/css" href="resources/css/iconfont.css">

<script type="text/javascript" src="resources/js/loading.js"></script>
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
<script type="text/javascript" src="resources/js/highcharts.js"></script>
<script type="text/javascript" src="resources/js/exporting.js"></script>
<script type="text/javascript" src="resources/js/android/machineCurve.js"></script>
</head>

<body class="easyui-layout">
	<div id="bodys" region="center" hide="true" split="true">
		<input id="status" type="hidden" value="${status}">
		<div style="width:15%;height:150px;float:left;">
			点击示意图返回<br/><a id="historyurl" href="android/goLivedata?status=${status}"><img id="mrjpg" src="resources/images/welder_04.png" width="80%" height="90%" style="margin-left:20px"></a>
		</div>
		<div style="float:right;width:83%;height:150px;">
			基本参数
			<div class="divtb">
				<label id="la2">设备编号</label>
				<input id="in2" value="${value}" readonly="readonly" type="text" disabled="true" style="display:none;text-align:center;color:#000000;width:200px;height:32px;font-size:18px;background-color:#EEEEEE;border:0px;">
				<input id="inn2" value="${valuename}" readonly="readonly" type="text" disabled="true">
				<label id="la5">预置电流</label>
				<input id="in5" value="" type="text" readonly="readonly" disabled="true">
				<label id="la6">预置电压</label>
				<input id="in6" value="" type="text" readonly="readonly" disabled="true">
				<label>振动频率</label>
				<input id="new5" type="text" readonly="readonly" disabled="true">
			</div>
			<div class="divtb">
				<label id="la7">焊接电流</label>
				<input id="in7" value="" type="text" readonly="readonly" disabled="true">
				<label id="la8">焊接电压</label>
				<input id="in8" value="" type="text" readonly="readonly" disabled="true">
				<label id="la4">设备状态</label>
				<input id="in4" value="关机" type="text" readonly="readonly" disabled="true">
				<label>焊接热输入</label>
				<input id="new3" type="text" readonly="readonly" disabled="true">
			</div>
			<div class="divtb">
				<label>送丝速度</label>
				<input id="new1" type="text" readonly="readonly" disabled="true">
				<label>焊接速度</label>
				<input id="new2" type="text" readonly="readonly" disabled="true">
				<label>热丝电流</label>
				<input id="new4" type="text" readonly="readonly" disabled="true">
				<label>超标判定</label>
				<input id="new6" value="关机中" type="text" readonly="readonly" disabled="true">
			</div>
		</div>
		<div style="float:left;width:98%;height:70%;border:2px solid #eee">
			实时曲线
			<div id="body31" style="width:100%;height:50%;"></div>
			<div style="float:left; width:100%;height:5px;background-color: #C4C4C4;"></div>
			<div id="body32" style="width:100%;height:48%;"></div>
		</div>
	</div>
</body>
</html>

