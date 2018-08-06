<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工艺管理</title>
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
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/weldwps/allWps.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>
	<script type="text/javascript" src="resources/js/weldwps/addWps.js"></script>
	<script type="text/javascript" src="resources/js/weldwps/destroyWps.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="工艺管理" style="background: #eee;height: 335px;">
   		<div id="dg_btn">
	        <div style="margin-bottom: 5px;">
	        	<a href="javascript:addWps();" class="easyui-linkbutton" iconCls="icon-add">新增</a>
	        	<a href="javascript:insertSearchWps();" class="easyui-linkbutton" iconCls="icon-search" >查找</a>
	    	</div>
	  	</div>
	  	<table id="dg" style="table-layout:fixed;width:100%"></table>
    	<div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
	    	<div id="div0">
		    	<select class="fields" id="fields"></select>
		    	<select class="condition" id="condition"></select>
		    	<input class="content" id="content"/>
		    	<select class="joint" id="joint"></select>
		    	<a href="javascript:newSearchWps();" class="easyui-linkbutton" iconCls="icon-add"></a>
		    	<a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
	    	</div>
	    </div>
	    <div id="searchButton">
			<a href="javascript:searchWps();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
			<a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	    <!-- 添加修改 -->
		<div id="dlg" class="easyui-dialog" style="width: 800px; height: 400px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
           		<div class="fitem">
	            	<lable><span class="required">*</span>工艺编号</lable>
	                <input name="fwpsnum" id="fwpsnum" class="easyui-textbox" data-options="validType:'wpsValidate',required:true">
	                <input id="validName" type="hidden">
	            	<lable><span class="required">*</span>预置通道</lable>
	                <input name="fweld_prechannel" class="easyui-numberbox"  data-options="required:true">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>报警电流</lable>
	                <input name="fweld_alter_i" class="easyui-numberbox"  data-options="required:true">
	            	<lable><span class="required">*</span>报警电压</lable>
	                <input name="fweld_alter_v" class="easyui-numberbox"  data-options="required:true">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>标准焊接电流</lable>
	                <input name="fweld_i" class="easyui-numberbox"   data-options="required:true">
	            	<lable><span class="required">*</span>标准焊接电压</lable>
	                <input name="fweld_v" type="easyui-textbox" class="easyui-numberbox" data-options="required:true">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>最大焊接电流</lable>
	                <input name="fweld_i_max" class="easyui-numberbox"  data-options="required:true">
	            	<lable><span class="required">*</span>最小焊接电流</lable>
	                <input name="fweld_i_min" class="easyui-numberbox"  data-options="required:true">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>最大焊接电压</lable>
	                <input name="fweld_v_max" class="easyui-numberbox"   data-options="required:true">
	            	<lable><span class="required">*</span>最小焊接电压</lable>
	                <input name="fweld_v_min" class="easyui-numberbox" data-options="required:true">
	            </div>
	            <div class="fitem">
	            	<lable><span class="required">*</span>工艺参数名称</lable>
	                <input name="fname" class="easyui-textbox" data-options="required:true">
	            	<lable><span class="required">*</span>焊丝直径</lable>
	                <input name="fdiameter" class="easyui-numberbox"  min="0.001" precision="3" data-options="required:true">
	            </div>
	            <div class="fitem">
					<lable><span class="required">*</span>部门</lable>
					<select class="easyui-combobox" name="insid" id="insid" data-options="required:true,editable:false"></select>
					<input class="easyui-textbox" name="iid" id="iid" data-options="required:true" readonly="readonly"/>
	            	<lable>备注</lable>
	                <input name="fback" class="easyui-textbox">
	        	</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:save();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:$('#dlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
		<!-- 删除 -->
		<div id="rdlg" class="easyui-dialog" style="width: 800px; height: 400px; padding:10px 20px" closed="true" buttons="#remove-buttons">
			<form id="rfm" class="easyui-form" method="post" data-options="novalidate:true">
	            <div class="fitem">
	            	<lable>工艺编号</lable>
	                <input name="fwpsnum" id="fwpsnum" class="easyui-textbox" readonly="true">
	            	<lable>预置通道</lable>
	                <input name="fweld_prechannel" class="easyui-textbox" readonly="true">
	            </div>
	            <div class="fitem">
	            	<lable>报警电流</lable>
	                <input name="fweld_alter_i" class="easyui-textbox" readonly="true">
	            	<lable>报警电压</lable>
	                <input name="fweld_alter_v" class="easyui-textbox" readonly="true" >
	            </div>
	            <div class="fitem">
	            	<lable>标准焊接电流</lable>
	                <input name="fweld_i" class="easyui-textbox" readonly="true">
	            	<lable>标准焊接电压</lable>
	                <input name="fweld_v" class="easyui-textbox" readonly="true">
	            </div>
	            <div class="fitem">
	            	<lable>最大焊接电流</lable>
	                <input name="fweld_i_max" class="easyui-textbox" readonly="true">
	            	<lable>最小焊接电流</lable>
	                <input name="fweld_i_min" class="easyui-textbox" readonly="true" >
	            </div>
	            <div class="fitem">
	            	<lable>最大焊接电压</lable>
	                <input name="fweld_v_max" class="easyui-textbox"  readonly="true">
	            	<lable>最小焊接电压</lable>
	                <input name="fweld_v_min" class="easyui-textbox" readonly="true">
	            </div>
	            <div class="fitem">
	            	<lable>工艺参数名称</lable>
	                <input name="fname" class="easyui-textbox" readonly="true">
	            	<lable>焊丝直径</lable>
	                <input name="fdiameter" class="easyui-textbox" readonly="true">
	            </div>
	            <div class="fitem">
					<lable>部门</lable>
					<input name="insname" id="Fowner" class="easyui-textbox" readonly="true">
	            	<lable>备注</lable>
	                <input name="fback" class="easyui-textbox" readonly="true">
	        	</div>
			</form>
		</div>
		<div id="remove-buttons">
			<a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
			<a href="javascript:$('#rdlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
   	</div>
</body>
</html>
 
 
