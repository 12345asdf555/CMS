<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊机设备管理</title>
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
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
  <script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
  <script type="text/javascript" src="resources/js/weldingMachine/weldingMachine.js"></script>
  <script type="text/javascript" src="resources/js/weldingMachine/addeditweldingmachine.js"></script>
  <script type="text/javascript" src="resources/js/weldingMachine/removeweldingmachine.js"></script>
  <script type="text/javascript" src="resources/js/search/search.js"></script>
  </head>
  
  <body class="easyui-layout">
    <jsp:include  page="../insframeworktree.jsp"/>
    <div id="body" region="center"  hide="true"  split="true">
      <input type="hidden" id="treeid"/>
      <div id="weldingmachineTable_btn">
      <div style="margin-bottom: 5px;">
        <a href="javascript:addWeldingMachine();" class="easyui-linkbutton" iconCls="icon-add">新增</a>
        <a href="javascript:importclick();" class="easyui-linkbutton" iconCls="icon-excel">导入</a>
        <a href="javascript:exportWeldingMachine();" class="easyui-linkbutton" iconCls="icon-excel">导出</a>        
        <a href="javascript:insertSearchWeldingMachine();" class="easyui-linkbutton"iconCls="icon-search" >查找</a>
      </div>
    </div>
    <div id="importdiv" class="easyui-dialog" style="width:300px; height:200px;" closed="true">
      <form id="importfm" method="post" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data"> 
        <div>
          <span><input type="file" name="file" id="file"></span>
          <input type="button" value="上传" onclick="importWeldingMachine()" class="upButton"/>
        </div>
      </form>
    </div>
    
      <table id="weldingmachineTable" style="table-layout: fixed; width:100%;"></table>

    <!-- 自定义多条件查询 -->
      <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
        <div id="div0">
          <select class="fields" id="fields" data-options="editable:false"></select>
          <select class="condition" id="condition" data-options="editable:false"></select>
          <input class="content" id="content"/>
          <select class="joint" id="joint" data-options="editable:false"></select>
          <a href="javascript:newSearchWeldingMachine();" class="easyui-linkbutton" iconCls="icon-add"></a>
          <a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
        </div>
      </div>
      <div id="searchButton">
      <a href="javascript:searchWeldingmachine();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
      <a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
    </div>
    <!-- 添加 -->
    <div id="dlg" class="easyui-dialog" style="width: 420px; height: 530px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
      <form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
        <div class="fitem">
          <lable><span class="required">*</span>固定资产编号</lable>
          <input type="hidden" id="valideno"/>
          <input type="hidden" id="validgid"/>
          <input class="easyui-textbox" name="equipmentNo" id="equipmentNo"  data-options="validType:['wmEnoValidate'],required:true"/>
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>设备类型</lable>
          <select class="easyui-combobox" name="typeId" id="tId" data-options="required:true,editable:false" ></select>
        </div>
        <div class="fitem">
          <lable>入厂时间</lable>
          <input class="easyui-datetimebox" name="joinTime" id="joinTime" />
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>所属项目</lable>
          <input type="hidden" id="insframework" name="insframework"/>
          <input class="easyui-textbox" id="insfname" readonly="readonly"/>
          <select class="easyui-combobox" name="insframeworkId" id="iId" data-options="required:true,editable:false"></select>
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>生产厂商</lable>
          <select class="easyui-combobox" name="manufacturerId" id="manufacturerId" data-options="required:true,editable:false"></select>
        </div>
        <div class="fitem" style="margin-left: -100px;">
          <lable>采集序号</lable>
<!--         <select class="easyui-combobox" name="gatherId" id="gatherId" data-options="validType:['checkNumber','wmGatheridValidate'],editable:false"></select> -->
          <input type="hidden" name="gatherId" id="gatherId" />
          <input class="easyui-textbox" name="gatherNo" id="gatherNo" data-options="validType:['wmGatheridValidate']" readonly="readonly"/>
          <a href="javascript:selectMachine();" class="easyui-linkbutton">选择</a>
          <a href="javascript:reset();" class="easyui-linkbutton">还原</a>
        </div>
		<div class="fitem">
          <lable>设备位置</lable>
          <input class="easyui-textbox" name="position" id="position"/>
        </div>
        <div class="fitem">
          <lable>设备价值</lable>
          <input class="easyui-numberbox" name="money" id="money"/>
        </div>
        <div class="fitem" >
          <lable>是否联网</lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" class="radioStyle" name="isnetworkingId" value="0" checked="checked"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" class="radioStyle" name="isnetworkingId" value="1"/>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
        <div class="fitem">
          <lable>状态</lable>
             <span id="radios"></span>
        </div>
      </form>
    </div>
    <div id="dlg-buttons">
      <a href="javascript:saveWeldingMachine();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
      <a href="javascript:$('#dlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
    </div>
   <!-- 选择采集模块 -->
    <div id="fdlg" class="easyui-dialog" style="width: 700px; height: 530px;" title="选择焊机" closed="true" buttons="#fdlg-buttons">
      <div id="fdlgSearch">
        采集模块编号：<input class="easyui-textbox" id="searchname"/>
        <a href="javascript:dlgSearchGather();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      </div>
      <table id="gatherTable" style="table-layout: fixed; width:100%;"></table>
    </div>
    <div id="fdlg-buttons">
      <a href="javascript:saveGather();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
      <a href="javascript:closeFdlog()" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
    </div>
  <!--   删除 -->
  <div id="rdlg" class="easyui-dialog" style="width: 420px; height: 530px; padding:10px 20px" closed="true" buttons="#remove-buttons">
    <form id="rfm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
    <div class="fitem">
        <lable>固定资产编号</lable>
        <input class="easyui-textbox" id="wid" readonly="readonly"/>
        <input class="easyui-textbox" id="equipmentNo" name="equipmentNo" readonly="readonly" />
      </div>
      <div class="fitem">
        <lable>设备类型</lable>
<!--       <input class="easyui-textbox" name="tId" id="tId" readonly="readonly"/> -->
        <input class="easyui-textbox" name="typeName" id="typeName" readonly="readonly"/>
      </div>
      <div class="fitem">
        <lable>入厂时间</lable>
        <input class="easyui-textbox" name="joinTime" id="joinTime" readonly="readonly"/>
      </div>
      <div class="fitem">
        <lable>所属项目</lable>
        <input type="hidden" id="insfid" value="${insfid }"/>
<!--       <input class="easyui-textbox"  name="iId" id="iId" readonly="readonly" /> -->
        <input class="easyui-textbox"  name="insframeworkName" id="insframeworkName" readonly="readonly" />
      </div>
      <div class="fitem">
        <lable>生产厂商</lable>
        <input class="easyui-textbox" name="manufacturerName" id="manufacturerName" readonly="readonly" />
      </div>
      <div class="fitem">
        <lable>采集序号</lable>
        <input class="easyui-textbox" id="gatherNo"  name="gatherNo" readonly="readonly" />
      </div>
      <div class="fitem">
        <lable>设备位置</lable>
        <input class="easyui-textbox" name="position" id="position" readonly="readonly"/>
      </div>
        <div class="fitem">
          <lable>设备价值</lable>
          <input class="easyui-numberbox" name="money" id="money" readonly="readonly" />
        </div>
      <div class="fitem">
        <lable>是否联网</lable>
        <input class="easyui-textbox" name="isnetworking" id="isnetworking" readonly="readonly" />
      </div>
      <div class="fitem">
        <lable>状态</lable>
        <input class="easyui-textbox" name="statusName" id="statusName" readonly="readonly"/>
      </div>
    </form>
    
    <div id="remove-buttons">
      <label>
        <a href="javascript:remove();" class="easyui-linkbutton"  iconCls="icon-remove">删除</a>
        <a href="javascript:$('#rdlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
      </label>
    </div>
  </div>
  </body>
</html>