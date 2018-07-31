<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除焊机设备</title>
    
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
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/weldingMachine/removeweldingmachine.js"></script>
  </head>
  
  <body class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
    <div  id="body" region="center"  hide="true"  split="true" title="删除焊机设备" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<br/>
			<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">删除焊机设备</div>
			<div class="fitem">
				<lable>固定资产编号</lable>
				<input class="easyui-textbox" id="wid" readonly="readonly" value="${w.id }"/>
				<input class="easyui-textbox" id="equipmentNo" readonly="readonly" value="${w.equipmentNo }"/>
			</div>
			<div class="fitem">
				<lable>设备类型</lable>
				<input class="easyui-textbox" id="tId" readonly="readonly" value="${w.typename}"/>
			</div>
			<div class="fitem">
				<lable>入厂时间</lable>
				<input class="easyui-textbox" id="joinTime" readonly="readonly" value="${w.joinTime }"/>
			</div>
			<div class="fitem">
				<lable>所属项目</lable>
				<input type="hidden" id="insfid" value="${insfid }"/>
				<input class="easyui-textbox" id="iId" readonly="readonly" value="${w.insframeworkId.name }"/>
			</div>
			<div class="fitem">
				<lable>生产厂商</lable>
				<input class="easyui-textbox" id="manuno" readonly="readonly" value="${w.manufacturerId.name }"/>
			</div>
			<div class="fitem">
				<lable>采集序号</lable>
				<input class="easyui-textbox" id="gatherId" readonly="readonly" value="${w.gatherId.gatherNo }"/>
			</div>
			<div class="fitem">
				<lable>设备位置</lable>
				<input class="easyui-textbox" id="position" readonly="readonly" value="${w.position }"/>
			</div>
				<div class="fitem">
					<lable>设备价值</lable>
					<input class="easyui-numberbox" id="money" readonly="readonly" value="${w.money }"/>
				</div>
			<div class="fitem">
				<lable>是否联网</lable>
				<input class="easyui-textbox" id="isnetworking" readonly="readonly" value="${isnetworking }"/>
			</div>
			<div class="fitem">
				<lable>状态</lable>
				<input class="easyui-textbox" id="statusName" readonly="readonly" value="${w.statusname }"/>
			</div>
			<div class="weldbutton">
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:removeWeldingMachine();" class="easyui-linkbutton"	iconCls="icon-remove">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="weldingMachine/goWeldingMachine" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
				</label>
			</div>
		</div>
    </div>
  </body>
</html>
