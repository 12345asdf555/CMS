<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除焊口</title>
    
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
	<script type="text/javascript" src="resources/js/weldedjunction/removeweldedjunction.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="删除焊口" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<br/>
			<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">删除焊口</div>
			<div class="fitem">
				<lable>编号</lable>
				<input type="hidden" id="id"  value="${wj.id }"  />
				<input class="easyui-textbox" id="weldedJunctionno"   value="${wj.weldedJunctionno }" name="weldedJunctionno"  readonly="readonly"/>
				<lable>机组</lable>
				<input class="easyui-textbox" id="unit" name="unit"  value="${wj.unit }"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<lable>区域</lable>
				<input class="easyui-textbox" id="area" name="area"  value="${wj.area }"  readonly="readonly"/>
				<lable>规格</lable>
				<input class="easyui-textbox" id="specification" name="specification"   value="${wj.specification }"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<lable>系统</lable>
				<input class="easyui-textbox" id="systems" name="systems" value="${wj.systems }"   readonly="readonly"/>
				<lable>子项</lable>
				<input class="easyui-textbox" id="children" name="children"  value="${wj.children }"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<lable>达因</lable>
				<input class="easyui-textbox" id="dyne" name="dyne"  value="${wj.dyne }"  readonly="readonly"/>
				<lable>序列号</lable>
				<input class="easyui-textbox" id="serialNo" name="serialNo" value="${wj.serialNo }"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<lable>管线号</lable>
				<input class="easyui-textbox" id="pipelineNo"  name="pipelineNo"   readonly="readonly"  value="${wj.pipelineNo }"/>
				<lable>房间号</lable>
				<input class="easyui-textbox" id="roomNo" name="roomNo"  readonly="readonly" value="${wj.roomNo }" />
			</div>
			<div class="fitem">
				<lable>上游外径</lable>
				<input class="easyui-textbox" id="externalDiameter" name="externalDiameter" readonly="readonly" value="${wj.externalDiameter }"/>
				<lable>下游外径</lable>
				<input class="easyui-textbox" id="nextexternaldiameter" name="nextexternaldiameter" readonly="readonly" value="${wj.nextexternaldiameter }"/>
			</div>
			<div class="fitem">
				<lable>上游璧厚</lable>
				<input class="easyui-textbox" id="wallThickness" name="wallThickness" readonly="readonly"  value="${wj.wallThickness }" />
				<lable>下游璧厚</lable>
				<input class="easyui-textbox" id="nextwall_thickness" name="nextwall_thickness" readonly="readonly" value="${wj.nextwall_thickness }"/>
			</div>
			<div class="fitem">
				<lable>上游材质</lable>
				<input class="easyui-textbox" id="material"  name="material" readonly="readonly" value="${wj.material }" />
				<lable>下游材质</lable>
				<input class="easyui-textbox" id="next_material" name="next_material" readonly="readonly"  value="${wj.next_material }" />
			</div>
			<div class="fitem">
				<lable>电流上限</lable>
				<input class="easyui-textbox" id="maxElectricity" name="maxElectricity" readonly="readonly" value="${wj.maxElectricity }"/>
				<lable>电流下限</lable>
				<input class="easyui-textbox" id="minElectricity" name="minElectricity" readonly="readonly" value="${wj.minElectricity }" />
			</div>
			<div class="fitem">
				<lable>电压上限</lable>
				<input class="easyui-textbox" id="maxValtage" name="maxValtage" readonly="readonly" value="${wj.maxValtage }"/>
				<lable>电压下限</lable>
				<input class="easyui-textbox" id="minValtage"  name="minValtage" readonly="readonly" value="${wj.minValtage }"/>
			</div>
			<div class="fitem">
				<lable>电流单位</lable>
				<input class="easyui-textbox" id="electricity_unit"  name="electricity_unit" readonly="readonly" value="${wj.electricity_unit }" />
				<lable>电压单位</lable>
				<input class="easyui-textbox" id="valtage_unit"  name="valtage_unit" readonly="readonly" value="${wj.valtage_unit }"/>
			</div>
			<div class="fitem">
				<lable>开始时间</lable>
				<input class="easyui-textbox" id="startTime"  name="startTime"readonly="readonly" value="${wj.startTime }"/>
				<lable>完成时间</lable>
				<input class="easyui-textbox" id="endTime"  name="endTime" readonly="readonly" value="${wj.endTime }"/>
			</div>
			<div class="fitem">
				<lable>所属项目</lable>
				<input type="hidden" id="itemid"  value="${wj.itemid.id }"  />
				<input class="easyui-textbox" id="itemname"  name="itemname" readonly="readonly" value="${wj.itemid.name }" />
				<lable>&nbsp;</lable>
				<input type="text" readonly="readonly"/>
			</div>
			<div class="weldbutton">
				<a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
					<a href="weldedjunction/goWeldedJunction" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
			</div>
    </div>
  </body>
</html>
