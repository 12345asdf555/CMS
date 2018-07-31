<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改故障代码</title>
    
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
	<script type="text/javascript" src="resources/js/fault/addeditfault.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="修改故障代码" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
				<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">故障代码编辑</div>
				<div class="fitem">
					<lable>故障代码</lable>
					<input type="hidden" id="id"  name="id" value="${f.id }">
					<input type="hidden" id="creator"  name="creator" value="${f.creator }">
					<input class="easyui-textbox" name="code" id="code"  value="${f.code }" data-options="required:true"/>
				</div>
				<div class="fitem">
					<lable>故障类别</lable>
					<input type="hidden" id="typeid" value="${f.type }">
					<select class="easyui-combobox" name="type" id="type" data-options="required:true,editable:false"></select>
				</div>
				<div class="fitem">
					<lable>故障描述</lable>
					<textarea name="desc" id="desc" style="height:60px;width:150px">${f.desc }</textarea>
				</div>
				<div class="weldbutton">
					<a href="javascript:editFault();" class="easyui-linkbutton"	iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="fault/goFault" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
				</div>
			</form>
		</div>
	</div>
  </body>
</html>
