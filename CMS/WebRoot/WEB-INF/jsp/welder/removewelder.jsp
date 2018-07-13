<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除焊工</title>
    
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
	<script type="text/javascript" src="resources/js/welder/removewelder.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="删除焊工" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<br/>
			<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">删除焊工</div>
			<div class="fitem">
				<lable>姓名</lable>
				<input type="hidden" id="id" name="id"  value="${w.id }"  />
				<input class="easyui-textbox" id="name"   value="${w.name }" name="name"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<lable>编号</lable>
				<input class="easyui-textbox" id="welderno" name="welderno"  value="${w.welderno }"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<lable>所属项目</lable>
				<input type="hidden" id="itemid"  value="${w.iid }"  />
				<input class="easyui-textbox" id="iid"  name="iid" readonly="readonly" value="${w.iname }" />
			</div>
			<div class="weldbutton">
				<a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
					<a href="welder/goWelder" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
			</div>
    </div>
  </body>
</html>
