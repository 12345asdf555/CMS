<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊机设备迁移</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/weldingMachine/machinemigrate.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>
  </head>
  
  <body  class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
  	<div id="body" region="center"  hide="true"  split="true" title="焊机设备迁移" style="background: #eee;height: 335px;">
	  	<input type="hidden" id="treeid"/>
	  	<div id="weldingmachineTable_btn">
			<div style="margin-bottom: 5px;">	
				<a href="javascript:insertSearchWeldingMachine();" class="easyui-linkbutton"iconCls="icon-search" >查找</a>
			</div>
		</div>
		<div id="importdiv" class="easyui-dialog" style="width:300px; height:200px;" closed="true">
			<form id="importfm" method="post" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data"> 
				<div>
					<span><input type="file" name="file" id="file"></span>
					<input type="button" value="上传" onclick="importWeldingMachine()" class="upButton"/>
				</div>
			</form>
		</div>
		
	    <table id="weldingmachineTable" style="table-layout: fixed; width:100%;"></table>
		
		<!-- 自定义多条件查询 -->
	    <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
	    	<div id="div0">
		    	<select class="fields" id="fields" data-options="editable:false"></select>
		    	<select class="condition" id="condition" data-options="editable:false"></select>
		    	<input class="content" id="content"/>
		    	<select class="joint" id="joint" data-options="editable:false"></select>
		    	<a href="javascript:newSearchWeldingMachine();" class="easyui-linkbutton" iconCls="icon-add"></a>
		    	<a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
	    	</div>
	    </div>
	    <div id="searchButton">
			<a href="javascript:searchWeldingmachine();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
			<a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
		
	    <!-- 添加 -->
		<div id="dlg" class="easyui-dialog" style="width: 420px; height: 530px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
				<div class="fitem">
					<lable>固定资产编号</lable>
					<input class="easyui-textbox" name="equipmentNo" id="equipmentNo" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>所属项目</lable>
					<input type="hidden"  id="insframeworkId"  name="insframeworkId">
					<input class="easyui-combobox" name="insframework" id="iId"/>
				</div>
				<div class="fitem">
					<lable>设备类型</lable>
					<input type="hidden"  id="typeId" name="typeId"/>
					<input class="easyui-textbox" name="typeName" id="typeName" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>入厂时间</lable>
					<input class="easyui-textbox" name="jointime" id="jointime" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>生产厂商</lable>
					<input type="hidden"  id="manufacturerId" name="manufacturerId"/>
					<input class="easyui-textbox" name="manufacturerName" id="manufacturerName"  readonly="readonly"/>
				</div>
				<div class="fitem" >
					<lable>采集序号</lable>
					<input type="hidden" name="gatherId" id="gatherId"/>
					<input class="easyui-textbox" name="gatherNo" id="gatherNo" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>设备位置</lable>
					<input class="easyui-textbox" name="position" id="position"  readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>设备价值</lable>
					<input class="easyui-textbox" name="money" id="money" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>设备状态</lable>
					<input type="hidden" name="statusId" id="statusId"/>
					<input class="easyui-textbox"  id="statusName" name="statusName" readonly="readonly"/>
				</div>
				<div class="fitem" >
					<lable>是否联网</lable>
					<input type="hidden" name="isnetworkingId" id="isnetworkingId"/>
					<input class="easyui-textbox"  id="isnetworking" name="isnetworking" readonly="readonly"/>
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:saveWeldingMachine();" class="easyui-linkbutton" iconCls="icon-ok">迁移</a>
			<a href="javascript:$('#dlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
	</div>
  </body>
</html>
