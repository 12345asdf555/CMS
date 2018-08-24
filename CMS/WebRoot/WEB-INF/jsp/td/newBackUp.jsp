<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊机实时状态监测</title>
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
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/highcharts.js"></script>
	<script type="text/javascript" src="resources/js/session-overdue.js"></script>
	<script type="text/javascript" src="resources/js/echarts.js"></script>
<!-- 	<script type="text/javascript" src="resources/js/exporting.js"></script> -->
	<script type="text/javascript" src="resources/js/td/newBackUp.js"></script>
<!-- 	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script> -->
<!-- 	<script type="text/javascript" src="resources/js/rmeffect.js"></script> -->

  </head>
  
<body class="easyui-layout">
	<jsp:include  page="../insframeworktree.jsp"/>
	<div id="body" region="center"  hide="true"  split="true" title="焊机实时状态监测" style="background: #fff; width:600px;height: 335px;">
		<!-- 饼图 -->
		<div id="piecharts" style="float:left; height:250px; width:49.5%;border:1px solid #C4C4C4;"></div>
		<!-- 实时信息 -->
		<div id="listdiv" style="float:left; height:250px; width:49.5%;border:1px solid #C4C4C4;">
			<div style="width:100%;height:25px; background:#616fb4;font-size:14px">
				<input  name="afresh" id="afresh" type="hidden" value="${afreshLogin }"/>
				<div style="float:left;color:#fff;padding-left:10px;">组织机构：</div>
				<div style="float:right;text:right;color:#fff;padding-right:10px;" id="itemname"></div>
			</div>
				<div style="float:left; width:100%;height:40px;padding-top:20px;border-bottom:1px solid #C4C4C4;">
					<div style="float:left;padding-left:20px;">设备总数：<span id="machinenum">0</span></div>
					<div style="float:right;text:right;padding-right:20px;">
						状态:&nbsp;
						<select class="easyui-combobox" name="status" id="status" data-options="editable:false">
							<option value="0" selected="true">工作</option>
							<option value="1">待机</option>
							<option value="2">关机</option>
							<option value="3">超标</option>
							<option value="4">超时待机</option>
						</select>
					</div>
				</div>
				<div id="curvediv">
					<ul>
						<li>
							<a href="javascript:statusClick(0);" class="easyui-linkbutton"iconCls="icon-work" style="width:110px;text-align:left;">工作总数</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" id="work" style="width:50px;">0</a>
						</li>
						<li>
							<a href="javascript:statusClick(1);" class="easyui-linkbutton"iconCls="icon-standby" style="width:110px;text-align:left;">待机总数</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" id="standby" style="width:50px;">0</a>
						</li>
						<li>
							<a href="javascript:statusClick(2);" class="easyui-linkbutton"iconCls="icon-off" style="width:110px;text-align:left;">关机总数</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" id="off" style="width:50px;">0</a>
						</li>
						<li>
							<a href="javascript:statusClick(3);" class="easyui-linkbutton"iconCls="icon-overproof" style="width:110px;text-align:left;">超标总数</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" id="overproof" style="width:50px;">0</a>
						</li>
						<li>
							<a href="javascript:statusClick(4);" class="easyui-linkbutton"iconCls="icon-overtimestandby" style="width:110px;text-align:left;">超时待机总数</a>
							<a href="javascript:void(0);" class="easyui-linkbutton" id="overtime" style="width:50px;">0</a>
						</li>
					</ul>
				</div>
		</div>
		<!-- 实时焊机 -->
		<div id="curve" style="float:left;height:70%; width:99%;padding-left:10px;">
		</div>
		<div id="load" style="width:100%;height:100%;"></div>
	</div>
	<style type="text/css">
	    #load{ display: none; position: absolute; left:0; top:0;width: 100%; height: 40%; background-color: #555753; z-index:10001; -moz-opacity: 0.4; opacity:0.5; filter: alpha(opacity=70);}
		#show{display: none; position: absolute; top: 45%; left: 45%; width: 180px; height: 5%; padding: 8px; border: 8px solid #E8E9F7; background-color: white; z-index:10002; overflow: auto;}
	</style>
</body>
</html>
 
 