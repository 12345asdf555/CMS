<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增维修记录</title>
    
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
	<script type="text/javascript" src="resources/js/maintain/addeditmaintain.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="新增维修记录" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
				<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">新增维修记录</div>
				<div class="fitem" style="margin-left: -60px;">
					<lable>固定资产编号</lable>
					<input type="hidden" id="machineid" name="machineid"/>
					<input class="easyui-textbox" id="machineno" data-options="required:true" readonly="readonly"/>
<!-- 					<select class="easyui-combobox" name="equipmentNo" id="equipmentNo" data-options="required:true,editable:false"></select> -->
					<a href="javascript:selectMachine();" class="easyui-linkbutton">选择</a>
				</div>
				<div class="fitem">
					<lable>维修类型</lable>
					<select class="easyui-combobox" name="typeId" id="typeId" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem">
					<lable>维修人员</lable>
					<input class="easyui-textbox" name="viceman" id="viceman" data-options="required:true"/>
				</div>
				<div class="fitem">
					<lable>维修价格</lable>
					<input class="easyui-textbox" name="money" id="money"/>
				</div>
				<div class="fitem">
					<lable>起始时间</lable>
					<input class="easyui-datetimebox" name="startTime" id="startTime" data-options="required:true"/>
				</div>
				<div class="fitem">
					<lable>结束时间</lable>
					<input class="easyui-datetimebox" name="endTime" id="endTime"/>
				</div>
				<div class="fitem">
					<lable>维修说明</lable>
					<textarea name="desc" id="desc" style="height:60px;width:150px"></textarea>
				</div>
				<div class="weldbutton">
					<a href="javascript:addMaintain();" class="easyui-linkbutton"	iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="maintain/goMaintain" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
				</div>
			</form>
		</div>
	    <!-- 选择焊机 -->
		<div id="dlg" class="easyui-dialog" style="width: 700px; height: 530px;" title="选择焊机" closed="true" buttons="#dlg-buttons">
			<div id="dlgSearch" style="backgroud:#fff;">
				固定资产编号：<input class="easyui-textbox" id="searchname"/>
				<a href="javascript:dlgSearchMachine();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</div>
	    	<table id="weldingmachineTable" style="table-layout: fixed; width:100%;"></table>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:saveWeldingMachine();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:$('#dlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
	</div>
  </body>
</html>
