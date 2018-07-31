<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除采集模块</title>
    
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
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/gather/removegather.js"></script>
  </head>
  
  <body class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
    <div  id="body" region="center"  hide="true"  split="true" title="删除采集模块" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
				<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">删除采集模块</div>
				<div class="fitem">
					<input type="hidden" id="id" value="${g.id }"/>
					<lable>采集模块编号</lable>
					<input class="easyui-textbox" name="gatherNo" id="gatherNo"  value="${g.gatherNo }"readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>所属项目</lable>
					<input type="hidden" id="insfid" value="${itemid }"/>
					<input class="easyui-textbox" name="itemid" id="itemid" value="${g.itemname }" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>采集模块状态</lable>
					<input class="easyui-textbox" name="status" id="status" value="${g.status }" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>采集模块通讯协议</lable>
					<input class="easyui-textbox" name="protocol" id="protocol" value="${g.protocol }" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>采集模块IP地址</lable>
					<input class="easyui-textbox" name="ipurl" id="ipurl" value="${g.ipurl }"readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>采集模块MAC地址</lable>
					<input class="easyui-textbox" name="macurl" id="macurl" value="${g.macurl }"readonly="readonly"" />
				</div>
				<div class="fitem">
					<lable>采集模块出厂时间</lable>
					<input class="easyui-textbox" name="leavetime" id="leavetime" value="${g.leavetime }"readonly="readonly"/>
				</div>
				<div class="weldbutton">
					<a href="javascript:removeGather();" class="easyui-linkbutton"	iconCls="icon-remove">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="gather/goGather" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
				</div>
			</form>
		</div>
	</div>
  </body>
</html>
