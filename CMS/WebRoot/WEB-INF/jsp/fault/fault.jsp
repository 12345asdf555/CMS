<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>故障代码管理</title>
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
  <script type="text/javascript" src="resources/js/fault/fault.js"></script>
  <script type="text/javascript" src="resources/js/fault/addeditfault.js"></script>
  <script type="text/javascript" src="resources/js/fault/removefault.js"></script>
  <script type="text/javascript" src="resources/js/search/search.js"></script>
  
  </head>
  
  <body  class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="故障代码管理" style="background: witch; height: 335px;">
      
      <div id="dg_btn" style="margin-bottom: 5px;">
        <a href="javascript:addFault();" class="easyui-linkbutton" iconCls="icon-add" >新增</a>
        <a href="javascript:insertSearchFault();" class="easyui-linkbutton" iconCls="icon-search" >查找</a>
      </div>
    </div>
    
      <table id="dg" style="table-layout: fixed; width:100%;"></table>
      
      <!-- 自定义多条件查询 -->
      <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
        <div id="div0">
          <select class="fields" id="fields" data-options="editable:false"></select>
          <select class="condition" id="condition" data-options="editable:false"></select>
          <input class="content" id="content"/>
          <select class="joint" id="joint" data-options="editable:false"></select>
          <a href="javascript:newSearchFault();" class="easyui-linkbutton" iconCls="icon-add"></a>
          <a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
        </div>
      </div>
      <div id="searchButton">
      <a href="javascript:searchFault();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
      <a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
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
		<a href="javascript:$('#fdlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
	</div>
	
    <!-- 新增修改 -->
    <div id="dlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
      <form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
        <div class="fitem">
          <lable><span class="required">*</span>固定资产编号</lable>
          <input type="hidden" name="machineid" id="machineid"/>
          <input class="easyui-textbox" name="machineno" id="machineno" data-options="required:true" readonly="readonly"/>
		  <a href="javascript:selectMachine();" class="easyui-linkbutton" id="selectMachine">选择</a>
        </div>
        <div class="fitem  ">
          <lable><span class="required">*</span>故障代码</lable>
          <input type="hidden" name="itemid" id="itemid"/>
          <select class="easyui-combobox" name="codeid" id="codeid" data-options="required:true,editable:false"></select>
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>故障类别</lable>
          <select class="easyui-combobox" name="typeid" id="typeid" data-options="required:true,editable:false"></select>
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>录入时间</lable>
          <input class="easyui-datetimebox" name="time" id="time" data-options="required:true"/>
        </div>
    </form>
    </div>
    <div id="dlg-buttons">
      <a href="javascript:saveFault();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
      <a href="javascript:closeIU();" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
    </div>
    <!-- 删除 -->
      <div id="rdlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#remove-buttons">
	      <form id="rfm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
	      	<div class="fitem">
          <lable><span class="required">*</span>固定资产编号</lable>
          <input class="easyui-textbox" name="machineno" id="machineno"readonly="readonly"/>
        </div>
        <div class="fitem  ">
          <lable><span class="required">*</span>故障代码</lable>
          <select class="easyui-textbox" name="code" id="code" readonly="readonly"></select>
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>故障类别</lable>
          <select class="easyui-textbox" name="type" id="type" readonly="readonly"></select>
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>录入时间</lable>
          <input class="easyui-textbox" name="time" id="time" readonly="readonly"/>
        </div>
	      </form>
     </div>
     <div id="remove-buttons">
         <a href="javascript:remove();" class="easyui-linkbutton"  iconCls="icon-remove">删除</a>
         <a href="javascript:closeD()" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
     </div>
  </div>
  </body>
</html>