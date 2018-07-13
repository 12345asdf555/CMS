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
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/iconfont.css">
	
	<script type="text/javascript" src="resources/js/loading.js"></script>
	<script type="text/javascript" src="resources/js/session-overdue.js"></script>
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/highcharts.js"></script>
	<script type="text/javascript" src="resources/js/exporting.js"></script>
	<script type="text/javascript" src="resources/js/td/BackUp.js"></script>
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<!-- <script type="text/javascript" src="resources/js/rmeffect.js"></script> -->

  </head>
  
<body class="easyui-layout">
	<jsp:include  page="../insframeworktree.jsp"/>
	<div id="body" region="center"  hide="true"  split="true" title="实时界面" style="background: #eee; width:600px;height: 335px;">
		<div id="body2" style="width:15%;height:15%;float:left;position:absolute;left:5%;top:8%;">
		<div style="position:absolute;left:5%;top:30%;"><label>电流：</label></div>
		<div class="wrap">
		<div class="clock">		
			<ul>
			<li>
				<ul id="time1">
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
				</ul>
			</li>
					<li>
				<ul id="time2">
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
				</ul>
			</li>
				<li>
				<ul id="time3">
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
				</ul>
			</li>
			<li>
				<ul id="time4">
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
				</ul>
			</li>
		</ul>
		</div>
		</div>
		</div>
		<div id="body31" style="width:75%;height:20%;position:absolute;left:25%;">
		</div>
		<div id="body32" style="width:75%;height:20%;position:absolute;left:25%;top:27%;">
		</div>
		<div id="body5" style="width:15%;height:15%;position:absolute;left:5%;top:20%;">
		<div style="position:absolute;left:5%;top:35%;"><label>电压：</label></div>
		<div class="wrap">
		<div class="clock">
			<ul>
			<li>
				<ul id="time5">
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
				</ul>
			</li>
					<li>
				<ul id="time6">
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
				</ul>
			</li>
				<li>
				<ul id="time7">
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
				</ul>
			</li>
			<li class="point"><span></span></li>
			<li>
				<ul id="time8"  style="margin-left:-38px">
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
					<li><img src="resources/images/front.png" width="20%" height="20%"></li>
				</ul>
			</li>
		</ul>
		</div>
		</div>
		</div>
		<div id="body6" style="position:absolute;left:5%;top:35%;"><label>焊机编号：</label>
		<input name="macname" id="macname" readonly="true" style="text-align:center">
		</div>
		<div id="body7" style="position:absolute;left:5%;top:45%;"><label>焊工姓名：</label>
		<input name="welname" id="welname" readonly="true" style="text-align:center">
		</div>
		<div style="width:100%;height:45%;position:absolute;top:50%;">
			<div style="margin-bottom:10px;float:left;">
	            <label for="zu" style="text-align:center;display:inline-block">组织机构：</label><input name="zu" id="zu" editable="false" class="easyui-textbox">
	        	<input id="zuid" type="hidden">
	        </div>
			<div style="margin-bottom:10px;float:left;">
	            <div style=" width:17px; height:15px; background-color:#00FF00; border-radius:25px; float:left;"></div><label for="on" style="text-align:center;display:inline-block">工作总数：</label><input name="on" id="on" class="easyui-textbox" editable="false" value="0">
	        </div>
	        <div style="margin-bottom:10px;float:left;">
	            <div style=" width:17px; height:15px; background-color:#FF0000; border-radius:25px; float:left;"></div><label for="warning" style="text-align:center;display:inline-block">报警总数：</label><input name="warning" id="warning" class="easyui-textbox" editable="false" value="0">
	        </div>
	        <div style="margin-bottom:10px;float:left;">
	            <div style=" width:17px; height:15px; background-color:#0000CD; border-radius:25px; float:left;"></div><label for="wait" style="text-align:center;display:inline-block">待机总数：</label> <input name="wait" id="wait" class="easyui-textbox" editable="false" value="0">
	        </div>
	        <div style="margin-bottom:10px;float:left;">
	            <div style=" width:17px; height:15px; background-color:#A9A9A9; border-radius:25px; float:left;"></div><label for="off" style="text-align:center;display:inline-block">关机总数：</label> <input name="off" id="off" class="easyui-textbox" editable="false" value="0">
	        </div>
		</div>
		<div id="body4" style="width:100%;height:45%;position:absolute;top:55%;">
			<table id="dg" style="table-layout:fixed;width:100%"></table>
		</div>
	</div>
</body>
</html>
 
 