<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除维修记录</title>
    
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
	<script type="text/javascript" src="resources/js/maintain/removemaintain.js"></script>
	

  </head>
  
  <body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="删除维修记录" style="background: white; height: 335px;">
		<div style="text-align: center "><br/>
			<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">删除维修记录</div>
			<div class="fitem">
				<lable>固定资产编号</lable>
				<input type="hidden" id="insfid" value="${insfid }"/>
				<input class="easyui-textbox" id="wid" readonly="readonly" value="${m.id }"/>
				<input class="easyui-textbox" id="equipmentNo" readonly="readonly" value="${m.welding.equipmentNo }"/>
			</div>
			<div class="fitem">
				<lable>维修类型</lable>
				<input class="easyui-textbox" id="tname" readonly="readonly" value="${tname }"/>
			</div>
			<div class="fitem">
				<lable>维修人员</lable>
				<input class="easyui-textbox" id="viceman" readonly="readonly" value="${m.maintenance.viceman }"/>
			</div>
				<div class="fitem">
					<lable>维修价格</lable>
					<input class="easyui-textbox" id="money" readonly="readonly" value="${m.maintenance.money }"/>
				</div>
			<div class="fitem">
				<lable>起始时间</lable>
				<input class="easyui-textbox" id="starttime" readonly="readonly" value="${m.maintenance.startTime }"/>
			</div>
			<div class="fitem">
				<lable>结束时间</lable>
				<input class="easyui-textbox" id="endtime" readonly="readonly" value="${m.maintenance.endTime }"/>
			</div>
			<div class="fitem">
				<lable>维修说明</lable>
				<textarea name="desc" id="desc" style="height:60px;width:150px" readonly="readonly">${m.maintenance.desc }</textarea>
			</div>
			<div class="weldbutton">
				<a href="javascript:removeMaintain();" class="easyui-linkbutton"	iconCls="icon-remove">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="maintain/goMaintain" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			</div>
		</div>
    </div>
  </body>
</html>
