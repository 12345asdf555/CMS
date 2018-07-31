<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>焊工管理</title>
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
  <script type="text/javascript" src="resources/js/welder/welder.js"></script>
<!--   <script type="text/javascript" src="resources/js/welder/addeditwelder.js"></script> -->
<!--   <script type="text/javascript" src="resources/js/welder/removewelder.js"></script> -->
  <script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
  <script type="text/javascript" src="resources/js/search/search.js"></script>
  
  </head>
  
  <body  class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="焊工管理" style="background: witch; height: 335px;">
      
      <div id="welderTable_btn">
      <div style="margin-bottom: 5px;">
        <a href="javascript:saveWelder();" class="easyui-linkbutton" iconCls="icon-add">新增</a>
        <a href="javascript:insertSearchWelder();" class="easyui-linkbutton" iconCls="icon-search" >查找</a>
      </div>
    </div>
    
      <table id="welderTable" style="table-layout: fixed; width:100%;"></table>
      
    <!-- 自定义多条件查询 -->
      <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
        <div id="div0">
          <select class="fields" id="fields"></select>
          <select class="condition" id="condition"></select>
          <input class="content" id="content"/>
          <select class="joint" id="joint"></select>
          <a href="javascript:newSearchWelder();" class="easyui-linkbutton" iconCls="icon-add"></a>
          <a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
        </div>
      </div>
      <div id="searchButton">
      <a href="javascript:searchWelder();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
      <a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
    </div>
    <!--增加和修改 -->
    <div id="dlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
    <form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
        <div class="fitem">
          <lable><span class="required">*</span>姓名</lable>
          <input type="hidden" id="id" name="id"/>
          <input class="easyui-textbox" id="name"  name="name" data-options="required:true" />
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>编号</lable>
          <input type="hidden" id="oldwelder"/>
          <input class="easyui-textbox" id="welderno" name="welderno" data-options="validType:['weldernoValidate'],required:true" />
        </div>
        <div class="fitem">
          <lable><span class="required">*</span>所属项目</lable>
          <select class="easyui-combobox" name="iid" id="itemname" data-options="required:true,editable:false"></select>
		  <input class="easyui-textbox" id="itemid"  name="itemid" data-options="required:true" readonly="readonly"/>
        </div>
      </form>
  </div>
  <div id="dlg-buttons">
      <a href="javascript:save();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
      <a href="javascript:$('#dlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
  </div>
  <!-- 删除 -->
    <div id="rdlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#remove-buttons">
      <form id="rfm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
        <div style="margin-bottom:10px;display: none;">
            </div>
            <div class="fitem">
          <lable>姓名</lable>
          <input class="easyui-textbox" id="name"  name="name" readonly="readonly"/>
        </div>
        <div class="fitem">
          <lable>编号</lable>
          <input class="easyui-textbox" id="welderno" name="welderno" readonly="readonly"/>
        </div>
        <div class="fitem">
          <lable>所属项目</lable>
          <input class="easyui-textbox" name="itemname" id="iid" readonly="readonly"/>
        </div>
        </form> 
    </div>
  <div id="remove-buttons">
    <a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
    <a href="javascript:$('#rdlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
  </div>            
  </body>
</html>