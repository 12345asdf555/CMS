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
<script type="text/javascript" src="resources/js/td/nextCurve.js"></script>
</head>

<body class="easyui-layout">
	<div id="bodys" region="center" hide="true" split="true" title="实时界面"
		style="background: #fff; height: 335px;">
		<div style="width:20%;height:150px;float:left;">
			点击示意图返回<br /><a href="td/newAllTd"><img id="mrjpg" src="resources/images/welder_04.png" width="35%" height="70%" style="margin-left:20px"></a>
		</div>
		<div style="width:55%;height:150px;float:left;">
			基本参数
				<div class="divtb">
<!-- 				<label id="la1">设备序号</label>
					<input id="in1" value="" type="text" readonly="readonly" disabled="true">
					<label id="la3">机&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型</label>
					<input id="in3" value="" type="text" readonly="readonly" disabled="true"> -->
					<label id="la2">设备编号</label>
					<input id="in2" value="${value}" readonly="readonly" type="text" disabled="true" style="display:none;text-align:center;color:#000000;width:200px;height:32px;font-size:18px;background-color:#EEEEEE;border:0px;">
					<input id="inn2" value="${valuename}" readonly="readonly" type="text" disabled="true">
					<label id="la5">预置电流</label>
					<input id="in5" value="" type="text" readonly="readonly" disabled="true">
					<label id="la6">预置电压</label>
					<input id="in6" value="" type="text" readonly="readonly" disabled="true">
				</div>
				<div class="divtb">
					<label id="la7">焊接电流</label>
					<input id="in7" value="" type="text" readonly="readonly" disabled="true">
					<label id="la8">焊接电压</label>
					<input id="in8" value="" type="text" readonly="readonly" disabled="true">
					<label id="la4">设备状态</label>
					<input id="in4" value="关机" type="text" readonly="readonly" disabled="true">
				</div>
				<div class="divtb">
					<label>送丝速度</label>
					<input id="new1" type="text" readonly="readonly" disabled="true">
					<label>焊接速度</label>
					<input id="new2" type="text" readonly="readonly" disabled="true">
					<label>热丝电流</label>
					<input id="new4" type="text" readonly="readonly" disabled="true">
				</div>
				<div class="divtb">
					<label>振动频率</label>
					<input id="new5" type="text" readonly="readonly" disabled="true">
					<label>焊接热输入</label>
					<input id="new3" type="text" readonly="readonly" disabled="true">
				</div>
				<!-- <table  width="100%">
				<tbody>
					<tr>
						<td align="center" width="50%">
							<label id="la1">设备序号</label>
							<input id="in1" value="" type="text" readonly="readonly" disabled="true">
						</td>
						<td align="center" width="50%">
							<label id="la2">设备编号</label>
							<input id="in2" value="${value}" readonly="readonly" type="text" disabled="true" style="display:none;text-align:center;color:#000000;width:200px;height:32px;font-size:18px;background-color:#EEEEEE;border:0px;">
							<input id="inn2" value="${valuename}" readonly="readonly" type="text" disabled="true">
						</td>
					</tr>
					<tr>
						<td align="center" width="50%">
							<label id="la3">机型</label>
							<input id="in3" value="" type="text" readonly="readonly" disabled="true">
						</td>
						<td id="td1" align="center" width="50%">
							<label id="la4">设备状态</label>
							<input id="in4" value="关机" type="text" readonly="readonly" disabled="true" style="text-align:center;color:#000000;width:200px;height:32px;font-size:10px;background-color:#EEEEEE;border:0px;">
						</td>
					</tr>
					<tr>
						<td align="center" width="50%">
							<label id="la5">预置电流</label>
							<input id="in5" value="" type="text" readonly="readonly" disabled="true">A
						</td>
						<td align="center" width="50%">
							<label id="la6">预置电压</label>
							<input id="in6" value="" type="text" readonly="readonly" disabled="true">V
						</td>
					</tr>
					<tr>
						<td align="center" width="50%">
							<label id="la7">焊接电流</label>
							<input id="in7" value="" type="text" readonly="readonly" disabled="true">A
						</td>
						<td align="center" width="50%">
							<label id="la8">焊接电压</label>
							<input id="in8" value="" type="text" readonly="readonly" disabled="true">V
						</td>
					</tr>
				</tbody>
			</table> -->
<!-- 			</div> -->
		</div>
 		<div style="width:24%;height:100%;float:right;">
			属性列表
			<div class="divtbs">
				<label id="la9">关机时间:</label>
				<input id="in9" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la10">开机时间:</label>
				<input id="in10" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la11">焊接时间:</label>
				<input id="in11" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la12">工作时间:</label>
				<input id="in12" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la13">焊&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工:</label>
				<input id="in13" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la14">焊缝编号:</label>
				<input id="in14" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la15">当前通道:</label>
				<input id="in15" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la16">通道总数:</label>
				<input id="in16" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la18">焊接控制:</label>
				<input id="in18" readonly="readonly">
			</div>
			<div class="divtbs">
			<label id="la21">初期电流:</label>
				<input id="in21" readonly="readonly">
			</div>
			<div class="divtbs">
			<label id="la22">收弧电流:</label>
				<input id="in22" readonly="readonly">
			</div>
			<div class="divtbs">
			<label id="la24">气体流量:</label>
				<input id="in24" readonly="readonly">
			</div>
			<div class="divtbs">
			<label id="la25">瞬时功率:</label>
				<input id="in25" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la33">焊接方法:</label>
				<input id="in33" readonly="readonly">
			</div>
			<div class="divtbs">
				<label id="la35">提前送气时间:</label>
				<input id="in35" readonly="readonly">
			</div>
			<div class="divtbs">
			<label id="la36">滞后停气时间:</label>
				<input id="in36" readonly="readonly">
			</div>
			<!--<table id="attrtable">
				<tbody>
					<tr>
						<td align="center" width="50%"><label id="la9">关机时间:</label>
						</td>
						<td><input id="in9" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la10">开机时间:</label>
						</td>
						<td><input id="in10" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la11">焊接时间:</label>
						</td>
						<td><input id="in11" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la12">工作时间:</label>
						</td>
						<td><input id="in12" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la13">焊工:</label></td>
						<td><input id="in13" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la14">焊缝编号:</label>
						</td>
						<td><input id="in14" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la15">当前通道:</label>
						</td>
						<td><input id="in15" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la16">通道总数:</label>
						</td>
						<td><input id="in16" readonly="readonly" value="30">
						</td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la18">焊接控制:</label>
						</td>
						<td><input id="in18" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la21">初期电流:</label>
						</td>
						<td><input id="in21" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la22">收弧电流:</label>
						</td>
						<td><input id="in22" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la24">气体流量:</label>
						</td>
						<td><input id="in24" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la25">瞬时功率:</label>
						</td>
						<td><input id="in25" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la33">焊接方法:</label>
						</td>
						<td><input id="in33" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la35">提前送气时间:</label>
						</td>
						<td><input id="in35" readonly="readonly"></td>
					</tr>
					<tr>
						<td align="center" width="50%"><label id="la36">滞后停气时间:</label>
						</td>
						<td><input id="in36" readonly="readonly"></td>
					</tr>
				</tbody>
			</table>-->
		</div>
		<div style="width:75%;height:70%;float:left;border:2px solid #eee">
			实时曲线
			<div id="body31" style="width:100%;height:50%;"></div>
			<div id="body32" style="width:100%;height:48%;"></div>
		</div>
	</div>
</body>
</html>

