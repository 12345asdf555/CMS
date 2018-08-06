<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>权限管理</title>
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
	<script type="text/javascript" src="resources/js/authority/allauthority.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>

  </head>

<body class="easyui-layout">
	<div id="body" region="center"  hide="true"  split="true" title="权限管理" style="background: #eee;">
        <div data-options="region:'center',title:'信息',iconCls:'icon-ok'">
        	<table id="dg" style="table-layout:fixed;width:100%"></table>
        </div>
        <div id="div" class="easyui-dialog" style="width:15%;" closed="true" buttons="#dlg-buttons">
        	<table id="so" title="资源" style="table-layout:fixed;width:100%"></table>
        </div>
	    <div id="toolbar">
	        <a href="javascript:saveAuthority();" class="easyui-linkbutton" iconCls="icon-add">新增</a>
	        <a href="javascript:insertSearchAuthority();" class="easyui-linkbutton" iconCls="icon-search">查找</a> 
	    </div>
   		<div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
	    	<div id="div0">
		    	<select class="fields" id="fields"></select>
		    	<select class="condition" id="condition"></select>
		    	<input class="content" id="content"/>
		    	<select class="joint" id="joint"></select>
		    	<a href="javascript:newSearchAuthority();" class="easyui-linkbutton" iconCls="icon-add"></a>
		    	<a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
	    	</div>
	    </div>
	    <div id="searchButton">
			<a href="javascript:searchAuthority();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
			<a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
		<!-- 增加修改 -->
		<div id="dlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
				<div class="fitem">
					<lable><span class="required">*</span>权限</lable>
	           		<input id="validName" type="hidden">
	                <input name="authorityName" id="authorityName" class="easyui-textbox" data-options="validType:'authorityValidate',required:true" style="width:100%">
            	</div>
	            <div class="fitem">
					<lable><span class="required">*</span>描述</lable>
	                <input name="authorityDesc" class="easyui-textbox" data-options="required:true" style="width:100%">
	            </div>
				<div class="fitem">
					<lable>状态</lable>&nbsp;&nbsp;
	   				<span id="radios"></span>
				</div>
		        <div style="margin-bottom:30px;margin-left:50px" align="center">
	<!-- 	        <div align="center"> -->
				<input type="hidden"  name="resources_name">
		        <table id="tt" title="资源列表" checkbox="true" style="table-layout:fixed"></table>
		        </div>
	        </form>
	     <div>
	     <div id="dlg-buttons">
			<lable>
				 <a href="javascript:save();" class="easyui-linkbutton c6" iconCls="icon-ok">保存</a>
				 <a href="javascript:closeIU();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			</lable>
		</div>
		<!--  删除 -->
		<div id="rdlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#remove-buttons">
			<form id="rfm" class="easyui-form" method="post" data-options="novalidate:true">
				<div style="margin-bottom:10px;display: none;">
		                <input name="id" id="id" class="easyui-textbox" type="hidden">
	            </div>
	            <div class="fitem">
					<lable>权限</lable>
	                <input name="authorityName" class="easyui-textbox" readonly="true" >
	            </div>
	            <div class="fitem">
					<lable>描述</lable>
	                <input name="authorityDesc" class="easyui-textbox" readonly="true">
	            </div>
				<div class="fitem">
					<lable>状态</lable>
					<input name="status" class="easyui-textbox" readonly="true" />
				</div>
	        	<div style="margin-bottom:20px;" align="center">
		        	<table id="rtt" title="资源列表" checkbox="true" style="table-layout:fixed"></table>
	       	 	</div>
        	</form>
        </div>
		<div id="remove-buttons">
			<lable>
			        <a href="javascript:remove();" class="easyui-linkbutton c6" iconCls="icon-ok">删除</a>
			        <a href="javascript:closeD();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		    </lable>
		</div>
    </div>
</body>
</html>
 
 
