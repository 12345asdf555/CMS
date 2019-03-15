<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊口管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="" />
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/weldedjunction/weldedjunction.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>
    <script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/weldedjunction/addeditweldedjunction.js"></script>
	<script type="text/javascript" src="resources/js/weldedjunction/removeweldedjunction.js"></script>
	
  </head>
  
  <body  class="easyui-layout">
    <jsp:include  page="../insframeworktree.jsp"/>
  	<div id="body" region="center"  hide="true"  split="true">
	  	<div id="disctionaryTable_btn">
			<div style="margin-bottom: 5px;">
				<a href="javascript:addWeldedjunction();" class="easyui-linkbutton" iconCls="icon-add">新增</a>
				<a href="javascript:insertsearchWJ();" class="easyui-linkbutton" iconCls="icon-search" >查找</a>
			</div>
		</div>
	    <table id="weldedJunctionTable" style="table-layout: fixed; width:100%;"></table>
	    
		<div id="wpsdiv" class="easyui-dialog" style="width:800px; height:500px;" closed="true" title="选择工艺" buttons="#savaWps">
			<div id="fdlgSearch">
		        工艺编号：<input class="easyui-textbox" id="searchname"/>
		        <a href="javascript:dlgSearchWPS();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		    </div>
		    <table id="wpsdg" style="table-layout:fixed;width:100%"></table>
	    </div>
	    <div id="savaWps">
			<a href="javascript:saveWpd();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:$('#wpsdiv').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	    <!-- 自定义多条件查询 -->
	    <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
	    	<div id="div0">
		    	<select class="fields" id="fields"></select>
		    	<select class="condition" id="condition"></select>
		    	<input class="content" id="content"/>
		    	<select class="joint" id="joint"></select>
		    	<a href="javascript:newSearchWJ();" class="easyui-linkbutton" iconCls="icon-add"></a>
		    	<a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
	    	</div>
	    </div>
	    <div id="searchButton">
			<a href="javascript:searchWJ();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
			<a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
		<!-- 添加修改 -->
		<div id="dlg" class="easyui-dialog" style="width: 780px; height: 98%; padding:10px 20px" closed="true" buttons="#dlg-buttons">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>编号</span>
					<input type="hidden" id="oldno" />
					<input class="easyui-textbox" id="weldedJunctionno"  name="weldedJunctionno" data-options="validType:['wjNoValidate','length[1,8]'],required:true" />
					<span class="textstyle">机组</span>
					<input class="easyui-textbox" id="unit" name="unit"/>
				</div>
				<div class="fitem">
					<span class="textstyle">区域</span>
					<input class="easyui-textbox" id="area" name="area"/>
					<span class="textstyle">规格</span>
					<input class="easyui-textbox" id="specification" name="specification"/>
				</div>
				<div class="fitem">
					<span class="textstyle">系统</span>
					<input class="easyui-textbox" id="systems" name="systems"/>
					<span class="textstyle">子项</span>
					<input class="easyui-textbox" id="children" name="children"/>
				</div>
				<div class="fitem">
					<span class="textstyle">管线号</span>
					<input class="easyui-textbox" id="pipelineNo"  name="pipelineNo" />
					<span class="textstyle">房间号</span>
					<input class="easyui-textbox" id="roomNo" name="roomNo"/>
				</div>
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>序列号</span>
					<input class="easyui-textbox" id="serialNo" name="serialNo" data-options="required:true"/>
					<span class="textstyle"><span class="required">*</span>所属项目</span>
					<select class="easyui-combobox" id="itemid"  name="itemid" data-options="required:true,editable:false"></select>
					<input class="easyui-textbox" id="itemname"  name="itemname" data-options="required:true" readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>上游外径</span>
					<input class="easyui-textbox" id="externalDiameter" name="externalDiameter"  data-options="required:true"/>
					<span class="textstyle"><span class="required">*</span>下游外径</span>
					<input class="easyui-textbox" id="nextexternaldiameter" name="nextexternaldiameter"  data-options="required:true"/>
				</div>
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>上游璧厚</span>
					<input class="easyui-textbox" id="wallThickness" name="wallThickness" data-options="required:true"/>
					<span class="textstyle"><span class="required">*</span>下游璧厚</span>
					<input class="easyui-textbox" id="nextwall_thickness" name="nextwall_thickness" data-options="required:true"/>
				</div>
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>上游材质</span>
					<input class="easyui-textbox" id="material"  name="material" data-options="required:true"/>
					<span class="textstyle"><span class="required">*</span>下游材质</span>
					<input class="easyui-textbox" id="next_material" name="next_material" data-options="required:true"/>
				</div>
				<div class="fitem" style="padding-right: 30px;">
					<span class="textstyle"><span class="required">*</span>电流上限</span>
					<input class="easyui-numberbox"  min="0.001" precision="3"  id="maxElectricity" name="maxElectricity" data-options="required:true"/>
					<span class="textstyle"><span class="required">*</span>电流下限</span>
					<input class="easyui-numberbox"  min="0.001" precision="3"  id="minElectricity" name="minElectricity" data-options="required:true"/>
					<a href="javascript:wpdDatagrid();" class="easyui-linkbutton">选择</a>
				</div>
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>电压上限</span>
					<input class="easyui-numberbox"  min="0.001" precision="3"  id="maxValtage" name="maxValtage" data-options="required:true"/>
					<span class="textstyle"><span class="required">*</span>电压下限</span>
					<input class="easyui-numberbox"  min="0.001" precision="3"  id="minValtage"  name="minValtage" data-options="required:true"/>
				</div>
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>电流单位</span>
					<input class="easyui-textbox" id="electricity_unit"  name="electricity_unit" data-options="required:true"/>
					<span class="textstyle"><span class="required">*</span>电压单位</span>
					<input class="easyui-textbox" id="valtage_unit"  name="valtage_unit" data-options="required:true"/>
				</div>
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>开始时间</span>
					<input class="easyui-datetimebox" id="startTime"  name="startTime" data-options="required:true"/>
					<span class="textstyle">完成时间</span>
					<input class="easyui-datetimebox" id="endTime"  name="endTime"/>
				</div>
				<div class="fitem">
					<span class="textstyle"><span class="required">*</span>达因</span>
					<input class="easyui-numberbox" id="dyne" name="dyne" data-options="required:true"/>
					<span class="textstyle">&nbsp;</span>
					<input type="text" border="0" readonly="readonly"/>
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:save();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:closeIU();" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
		<!-- 删除 -->
		<div id="rdlg" class="easyui-dialog" style="width: 750px; height: 98%; padding:10px 20px" closed="true" buttons="#remove-buttons">
			<form id="rfm" class="easyui-form" method="post" data-options="novalidate:true">
				<div class="fitem">
					<span class="textstyle">编号</span>
					<input class="easyui-textbox" id="weldedJunctionno"  name="weldedJunctionno"  readonly="readonly"/>
					<span class="textstyle">机组</span>
					<input class="easyui-textbox" id="unit" name="unit"  readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">区域</span>
					<input class="easyui-textbox" id="area" name="area"  readonly="readonly"/>
					<span class="textstyle">规格</span>
					<input class="easyui-textbox" id="specification" name="specification"  readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">系统</span>
					<input class="easyui-textbox" id="systems" name="systems"  readonly="readonly"/>
					<span class="textstyle">子项</span>
					<input class="easyui-textbox" id="children" name="children"  readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">管线号</span>
					<input class="easyui-textbox" id="pipelineNo"  name="pipelineNo"   readonly="readonly"/>
					<span class="textstyle">房间号</span>
					<input class="easyui-textbox" id="roomNo" name="roomNo"  readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">序列号</span>
					<input class="easyui-textbox" id="serialNo" name="serialNo"  readonly="readonly"/>
					<span class="textstyle">所属项目</span>
					<select class="easyui-textbox" id="itemname"  name="itemname" readonly="readonly"></select>
				</div>
				<div class="fitem">
					<span class="textstyle">上游外径</span>
					<input class="easyui-textbox" id="externalDiameter" name="externalDiameter" readonly="readonly"/>
					<span class="textstyle">下游外径</span>
					<input class="easyui-textbox" id="nextexternaldiameter" name="nextexternaldiameter" readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">上游璧厚</span>
					<input class="easyui-textbox" id="wallThickness" name="wallThickness" readonly="readonly"/>
					<span class="textstyle">下游璧厚</span>
					<input class="easyui-textbox" id="nextwall_thickness" name="nextwall_thickness" readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">上游材质</span>
					<input class="easyui-textbox" id="material"  name="material" readonly="readonly"/>
					<span class="textstyle">下游材质</span>
					<input class="easyui-textbox" id="next_material" name="next_material" readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">电流上限</span>
					<input class="easyui-textbox" id="maxElectricity" name="maxElectricity" readonly="readonly"/>
					<span class="textstyle">电流下限</span>
					<input class="easyui-textbox" id="minElectricity" name="minElectricity" readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">电压上限</span>
					<input class="easyui-textbox" id="maxValtage" name="maxValtage" readonly="readonly"/>
					<span class="textstyle">电压下限</span>
					<input class="easyui-textbox" id="minValtage"  name="minValtage" readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">电流单位</span>
					<input class="easyui-textbox" id="electricity_unit"  name="electricity_unit" readonly="readonly"/>
					<span class="textstyle">电压单位</span>
					<input class="easyui-textbox" id="valtage_unit"  name="valtage_unit" readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">开始时间</span>
					<input class="easyui-textbox" id="startTime"  name="startTime"readonly="readonly"/>
					<span class="textstyle">完成时间</span>
					<input class="easyui-textbox" id="endTime"  name="endTime" readonly="readonly"/>
				</div>
				<div class="fitem">
					<span class="textstyle">达因</span>
					<input class="easyui-numberbox" id="dyne" name="dyne" data-options="required:true"/>
					<span class="textstyle">&nbsp;</span>
					<input type="text" border="0" readonly="readonly"/>
				</div>
			</form>
		</div>
		<div id="remove-buttons">
			<a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
			<a href="javascript:closeD();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
		<!-- 查看更多 -->
		<div id="moredlg" class="easyui-dialog" style="width: 750px; height: 98%; padding:10px 20px" closed="true" buttons="#show-buttons">
			<form id="showfm" class="easyui-form" method="post" data-options="novalidate:true">
			<div class="fitem">
				<span class="textstyle">编号</span>
				<input class="easyui-textbox" id="weldedJunctionno"  name="weldedJunctionno"  readonly="readonly"/>
				<span class="textstyle">机组</span>
				<input class="easyui-textbox" id="unit" name="unit"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">区域</span>
				<input class="easyui-textbox" id="area" name="area"  readonly="readonly"/>
				<span class="textstyle">规格</span>
				<input class="easyui-textbox" id="specification" name="specification"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">系统</span>
				<input class="easyui-textbox" id="systems" name="systems"  readonly="readonly"/>
				<span class="textstyle">子项</span>
				<input class="easyui-textbox" id="children" name="children"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">管线号</span>
				<input class="easyui-textbox" id="pipelineNo"  name="pipelineNo"   readonly="readonly"/>
				<span class="textstyle">房间号</span>
				<input class="easyui-textbox" id="roomNo" name="roomNo"  readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">序列号</span>
				<input class="easyui-textbox" id="serialNo" name="serialNo"  readonly="readonly"/>
				<span class="textstyle">所属项目</span>
				<select class="easyui-textbox" id="itemname"  name="itemname" readonly="readonly"></select>
			</div>
			<div class="fitem">
				<span class="textstyle">上游外径</span>
				<input class="easyui-textbox" id="externalDiameter" name="externalDiameter" readonly="readonly"/>
				<span class="textstyle">下游外径</span>
				<input class="easyui-textbox" id="nextexternaldiameter" name="nextexternaldiameter" readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">上游璧厚</span>
				<input class="easyui-textbox" id="wallThickness" name="wallThickness" readonly="readonly"/>
				<span class="textstyle">下游璧厚</span>
				<input class="easyui-textbox" id="nextwall_thickness" name="nextwall_thickness" readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">上游材质</span>
				<input class="easyui-textbox" id="material"  name="material" readonly="readonly"/>
				<span class="textstyle">下游材质</span>
				<input class="easyui-textbox" id="next_material" name="next_material" readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">电流上限</span>
				<input class="easyui-textbox" id="maxElectricity" name="maxElectricity" readonly="readonly"/>
				<span class="textstyle">电流下限</span>
				<input class="easyui-textbox" id="minElectricity" name="minElectricity" readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">电压上限</span>
				<input class="easyui-textbox" id="maxValtage" name="maxValtage" readonly="readonly"/>
				<span class="textstyle">电压下限</span>
				<input class="easyui-textbox" id="minValtage"  name="minValtage" readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">电流单位</span>
				<input class="easyui-textbox" id="electricity_unit"  name="electricity_unit" readonly="readonly"/>
				<span class="textstyle">电压单位</span>
				<input class="easyui-textbox" id="valtage_unit"  name="valtage_unit" readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">开始时间</span>
				<input class="easyui-textbox" id="startTime"  name="startTime"readonly="readonly"/>
				<span class="textstyle">完成时间</span>
				<input class="easyui-textbox" id="endTime"  name="endTime" readonly="readonly"/>
			</div>
			<div class="fitem">
				<span class="textstyle">达因</span>
				<input class="easyui-numberbox" id="dyne" name="dyne" readonly="readonly"/>
				<span class="textstyle">创建时间</span>
				<input class="easyui-textbox" readonly="readonly" name="creatTime"/>
			</div>
			<div class="fitem">
				<span class="textstyle">修改时间</span>
				<input class="easyui-textbox" readonly="readonly" name="updateTime"/>
				<span class="textstyle">修改次数</span>
				<input class="easyui-textbox" readonly="readonly" name="updatecount"/>
			</div>
			</form>
		</div>
		
		<div id="show-buttons">
			<a href="javascript:closeshowmore();" class="easyui-linkbutton" iconCls="icon-ok">关闭</a>
		</div>
	</div>
  </body>
</html>
