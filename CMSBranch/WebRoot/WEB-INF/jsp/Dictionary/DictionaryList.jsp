<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>字典列表</title>
    
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
  <script type="text/javascript" src="resources/js/Dictionary/Dictionary.js"></script>
  <script type="text/javascript" src="resources/js/Dictionary/addeditdictionary.js"></script>
  </head>
  
  
  <body class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="用户管理" style="background: #eee; height: 335px;">
      <table id="dg" style="table-layout:fixed;width:100%"></table>
      <div id="toolbar" style="margin-bottom: 5px;">
          <a href="javascript:addDictionary()" class="easyui-linkbutton" iconCls="icon-add">新增</a>
          <select class="easyui-combobox" id="fields" style="margin-left:100px;" data-options="editable:false">
            <option value='fvaluename'>名称</option>
            <option value='fback'>类型</option>
          </select>
          <input class="easyui-textbox" id="content"/>
          <a href="javascript:searchDic();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
      </div>
      
    <!--  增加修改 -->
    <div id="dlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
      <form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
        <div class="fitem">
            <lable><span class="required">*</span>名称</lable>
            <input class="easyui-textbox" name="valueName" id="valueName" data-options="required:true"/>
          </div>
          <div align="center">
            <label id="wxts" style="display: none;color:red;">(注：请输入1~100之间的数字)</label>
          </div>
          <div class="fitem">
            <lable>类型</lable>
            <select class="easyui-combobox" name="typeid" id="typeid" data-options="required:true,editable:false"></select>
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
      <div class="fitem">
            <lable>名称</lable>
            <input name="id" id="id" lass="easyui-textbox" type="hidden"  >
            <input class="easyui-textbox" name="valueName" id="valueName"  readonly="readonly"/>
       </div>
       <div class="fitem">
            <lable>类型</lable>
            <input name="back" id="typeid" class="easyui-textbox" readonly="readonly"/>
       </div>
    </form>
  </div>
    <div id="remove-buttons">
      <a href="javascript:remove();" class="easyui-linkbutton"  iconCls="icon-ok">删除</a>
      <a href="javascript:$('#rdlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
    </div>
  </div>
  </body>
</html>