<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增生产厂商</title>
    
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
	<script type="text/javascript" src="resources/js/manufacturer/addeditmanu.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="新增生产厂商" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
				<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">新增生产厂商</div>
				<div class="fitem">
					<lable>生产厂商</lable>
					<input class="easyui-textbox" name="name" id="name" data-options="validType:['manuValidate'],required:true"/>
				</div>
				<div class="fitem">
					<lable>厂商类型</lable>
					<select class="easyui-combobox" name="type" id="type" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem">
					<lable>厂商类型值</lable>
					<input class="easyui-textbox" name="typeValue" id="typeValue" />
				</div>
				<div class="weldbutton">
					<lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:addManufacturer();" class="easyui-linkbutton"	iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="manufacturer/goManufacturer" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
					</lable>
				</div>
			</form>
		</div>
	</div>
  </body>
</html>
