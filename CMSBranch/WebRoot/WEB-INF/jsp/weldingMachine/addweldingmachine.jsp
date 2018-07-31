<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增焊机设备</title>
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
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/weldingMachine/addeditweldingmachine.js"></script>
  </head>
  
  <body class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
    <div  id="body" region="center"  hide="true"  split="true" title="新增焊机设备" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
				<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">新增焊机设备</div>
				<div class="fitem">
					<lable>固定资产编号</lable>
					<input class="easyui-textbox" name="equipmentNo" id="equipmentNo"  data-options="validType:['wmEnoValidate'],required:true"/>
				</div>
				<div class="fitem">
					<lable>设备类型</lable>
					<select class="easyui-combobox" name="typeId" id="tId" data-options="required:true,editable:false" ></select>
				</div>
				<div class="fitem">
					<lable>入厂时间</lable>
					<input class="easyui-datetimebox" name="joinTime" id="joinTime"/>
				</div>
				<div class="fitem">
					<lable>所属项目</lable>
					<select class="easyui-combobox" name="iId" id="iId" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem">
					<lable>生产厂商</lable>
					<select class="easyui-combobox" name="manuno" id="manuno" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem" style="margin-left: -60px;">
					<lable>采集序号</lable>
<!-- 					<select class="easyui-combobox" name="gatherId" id="gatherId" data-options="validType:['checkNumber','wmGatheridValidate'],editable:false"></select> -->
					<input type="hidden" name="gatherId" id="gatherId" />
					<input class="easyui-textbox" name="gatherNo" id="gatherNo" data-options="validType:['wmGatheridValidate']" readonly="readonly"/>
					<a href="javascript:selectMachine();" class="easyui-linkbutton">选择</a>
				</div>
				<div class="fitem">
					<lable>设备位置</lable>
					<input class="easyui-textbox" name="position" id="position"/>
				</div>
				<div class="fitem">
					<lable>设备价值</lable>
					<input class="easyui-numberbox" name="money" id="money"/>
				</div>
				<div class="fitem" >
					<lable>是否联网</lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" class="radioStyle" name="isnetworking" value="0" checked="checked"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" class="radioStyle" name="isnetworking" value="1"/>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<div class="fitem">
					<lable>状态</lable>
	   				<span id="radios"></span>
				</div>
				<div class="weldbutton">
					<lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:addWeldingMachine();" class="easyui-linkbutton"	iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="weldingMachine/goWeldingMachine" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
					</lable>
				</div>
			</form>
		</div>
	    <!-- 选择采集模块 -->
		<div id="dlg" class="easyui-dialog" style="width: 700px; height: 530px;" title="选择焊机" closed="true" buttons="#dlg-buttons">
			<div id="dlgSearch">
				采集模块编号：<input class="easyui-textbox" id="searchname"/>
				<a href="javascript:dlgSearchGather();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</div>
	    	<table id="gatherTable" style="table-layout: fixed; width:100%;"></table>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:saveGather();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:$('#dlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
	</div>
  </body>
</html>
