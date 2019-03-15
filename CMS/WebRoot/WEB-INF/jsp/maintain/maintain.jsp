<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>维修记录管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="" />
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/maintain/maintain.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>
	<script type="text/javascript" src="resources/js/maintain/addeditmaintain.js"></script>
	<script type="text/javascript" src="resources/js/maintain/removemaintain.js"></script>
	
  </head>
  
  <body  class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
  	<div id="body" region="center"  hide="true"  split="true">
	  	<input type="hidden" id="treeid"/>
	  	<div id="maintainTable_btn">
			<div style="margin-bottom: 5px;">
				<input type="hidden" id="str" value="${str }"/>
				<a href="javascript:addMaintain();" class="easyui-linkbutton" iconCls="icon-add">新增</a>
				<a href="javascript:importclick();" class="easyui-linkbutton" iconCls="icon-excel">导入</a>
				<a href="javascript:exporMaintain();" class="easyui-linkbutton" iconCls="icon-excel">导出</a>
				<a href="javascript:insertSearchMaintain();" class="easyui-linkbutton" iconCls="icon-search" >查找</a>
				<a href="javascript:history.go(-1)" class="easyui-linkbutton" iconCls="icon-back" id="pageUp">返回</a>
			</div>
		</div>
		
		<div id="importdiv" class="easyui-dialog" style="width:300px; height:200px;" closed="true">
			<form id="importfm" method="post" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data"> 
				<div>
					<span><input type="file" name="file" id="file"></span>
					<input type="button" value="上传" class="upButton" onclick="importWeldingMachine()"/> 
				</div>
			</form>
		</div>
		
	    <table id="maintainTable" style="table-layout: fixed; width:100%;"></table>
	    
	    <!-- 自定义多条件查询 -->
	    <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
	    	<div id="div0">
		    	<select class="fields" id="fields" data-options="editable:false"></select>
		    	<select class="condition" id="condition" data-options="editable:false"></select>
		    	<input class="content" id="content"/>
		    	<select class="joint" id="joint" data-options="editable:false"></select>
		    	<a href="javascript:newSearchMaintain();" class="easyui-linkbutton" iconCls="icon-add"></a>
		    	<a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
	    	</div>
	    </div>
	    <div id="searchButton">
			<a href="javascript:searchMaintain();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
			<a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
		<!-- 新增修改 -->
	    <div id="dlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
<!-- 			<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">新增维修记录</div> -->
				<div class="fitem" style="margin-left: -100px;">
					<lable><span class="required">*</span>固定资产编号</lable>
					<input type="hidden" id="mid" name="mid"/>
					<input type="hidden" id="machineid" name="wid"/>
					<input type="hidden" id="insfid" name="insfid"/>
					<input class="easyui-textbox" id="machineno" name="equipmentNo" data-options="required:true" readonly="readonly"/>
<!-- 	<select class="easyui-combobox" name="equipmentNo" id="equipmentNo" data-options="required:true,editable:false"></select> -->
					<a href="javascript:selectMachine();" class="easyui-linkbutton" id="selectMachine">选择</a>
				</div>
				<div class="fitem">
					<lable><span class="required">*</span>维修类型</lable>
					<select class="easyui-combobox" name="typeid" id="typeId" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem">
					<lable><span class="required">*</span>维修人员</lable>
					<input class="easyui-textbox" name="viceman" id="viceman" data-options="required:true"/>
				</div>
				<div class="fitem">
					<lable>维修价格</lable>
					<input class="easyui-textbox" name="money" id="money"/>
				</div>
				<div class="fitem">
					<lable><span class="required">*</span>起始时间</lable>
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
			</form>
			<div id="dlg-buttons">
					<a href="javascript:saveMaintain();" class="easyui-linkbutton"	iconCls="icon-ok">保存</a>
					<a href="javascript:closeIU();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			</div>
		</div>
	    <!-- 选择焊机 -->
		<div id="fdlg" class="easyui-dialog" style="width: 700px; height: 530px;" title="选择焊机" closed="true" buttons="#fdlg-buttons">
			<div id="dlgSearch" style="backgroud:#fff;">
				固定资产编号：<input class="easyui-textbox" id="searchname"/>
				<a href="javascript:dlgSearchMachine();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</div>
	    	<table id="weldingmachineTable" style="table-layout: fixed; width:100%;"></table>
		</div>
		<div id="fdlg-buttons">
			<a href="javascript:saveWeldingMachine();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:closeFdlog();" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
		<!-- 删除 -->
		<div id="rdlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#remove-buttons">
			<form id="rfm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
			<div class="fitem">
				<lable>固定资产编号</lable>
					<input type="hidden" id="machineid" name="machineid"/>
					<input class="easyui-textbox" id="equipmentNo" name="equipmentNo" data-options="required:true" readonly="readonly"/>
<!-- 	<select class="easyui-combobox" name="equipmentNo" id="equipmentNo" data-options="required:true,editable:false"></select> -->
<!-- 					<a href="javascript:selectMachine();" class="easyui-linkbutton">选择</a> -->
				</div>
				<div class="fitem">
					<lable>维修类型</lable>
					<input class="easyui-textbox" name="typename" id="typename" data-options="required:true" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>维修人员</lable>
					<input class="easyui-textbox" name="viceman" id="viceman" data-options="required:true" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>维修价格</lable>
					<input class="easyui-textbox" name="money" id="money" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>起始时间</lable>
					<input class="easyui-textbox" name="startTime" id="startTime" data-options="required:true" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>结束时间</lable>
					<input class="easyui-textbox" name="endTime" id="endTime" readonly="readonly"/>
				</div>
			<div class="fitem">
				<lable>维修说明</lable>
				<textarea name="desc" id="desc" style="height:60px;width:150px" readonly="readonly"></textarea>
			</div>
		</form>
		</div>
		<div id="remove-buttons">
			<a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
			<a href="javascript:closeD();" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
	</div>
  </body>
</html>
