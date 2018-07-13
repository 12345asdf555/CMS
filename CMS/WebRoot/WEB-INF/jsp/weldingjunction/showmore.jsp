<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊口信息</title>
    
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
  </head>
  
  <body class="easyui-layout">
    <div id="body" region="north" hide="true"  split="true" title="焊口信息" style="background: witch; height: 100%;">
    	<div style="text-align:center">
			<div class="fitem">
				<lable>编号</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.weldedJunctionno }"/>
				<lable>序列号</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.serialNo}"/>
			</div>
			<div class="fitem">
				<lable>机组</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.unit }"/>
				<lable>区域</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.area }"/>
			</div>
			<div class="fitem">
				<lable>系统</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.systems }"/>
				<lable>子项</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.children }"/>
			</div>
			<div class="fitem">
				<lable>达因</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.dyne }"/>
				<lable>规格</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.specification }"/>
			</div>
			<div class="fitem">
				<lable>管线号</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.pipelineNo }"/>
				<lable>房间号</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.roomNo }"/>
			</div>
			<div class="fitem">
				<lable>上游外径</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.externalDiameter }"/>
				<lable>下游外径</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.nextexternaldiameter }"/>
			</div>
			<div class="fitem">
				<lable>上游璧厚</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.wallThickness }"/>
				<lable>下游璧厚</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.nextwall_thickness }"/>
			</div>
			<div class="fitem">
				<lable>上游材质</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.material }"/>
				<lable>下游材质</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.next_material }"/>
			</div>
			<div class="fitem">
				<lable>电流上限</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.maxElectricity }"/>
				<lable>电流下限</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.minElectricity }"/>
			</div>
			<div class="fitem">
				<lable>电压上限</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.maxValtage }"/>
				<lable>电压下限</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.minValtage }"/>
			</div>
			<div class="fitem">
				<lable>电流单位</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.electricity_unit }"/>
				<lable>电压单位</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.valtage_unit }"/>
			</div>
			<div class="fitem">
				<lable>所属项目</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.itemid.name }"/>
				<lable>开始时间</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.startTime }"/>
			</div>
			<div class="fitem">
				<lable>完成时间</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.endTime }"/>
				<lable>创建时间</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.creatTime }"/>
			</div>
			<div class="fitem">
				<lable>修改时间</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.updateTime }"/>
				<lable>修改次数</lable>
				<input class="easyui-textbox" readonly="readonly" value="${wj.updatecount }"/>
			</div>
			<div style="margin-left:50px">
				<lable>
					<a href="weldedjunction/goWeldedJunction" class="easyui-linkbutton" iconCls="icon-cancel">返回</a>
				</lable>
			</div>
		</div>
	</div>
  </body>
</html>
