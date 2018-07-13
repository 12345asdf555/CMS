<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增焊工</title>
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
	<script type="text/javascript" src="resources/js/welder/addeditwelder.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="新增焊工" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
				<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">新增焊工</div>
				<div class="fitem">
					<lable>姓名</lable>
					<input class="easyui-textbox" id="name"  name="name" data-options="required:true" />
				</div>
				<div class="fitem">
					<lable>编号</lable>
					<input class="easyui-textbox" id="welderno" name="welderno" data-options="validType:['weldernoValidate'],required:true" />
				</div>
				<div class="fitem">
					<lable>所属项目</lable>
					<select class="easyui-combobox" id="iid"  name="iid" data-options="required:true,editable:false"></select>
				</div>
				<div class="weldbutton">
					<a href="javascript:addWelder();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="welder/goWelder" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
				</div>
			</form>
		</div>
	</div>
  </body>
</html>
