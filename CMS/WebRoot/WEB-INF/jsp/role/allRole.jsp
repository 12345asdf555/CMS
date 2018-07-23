<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>角色管理</title>
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
  <script type="text/javascript" src="resources/js/role/allrole.js"></script>
  
  <script type="text/javascript" src="resources/js/search/search.js"></script>

  </head>
<body class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="角色管理" style="background: #eee; height: 335px;">
    
        <div data-options="region:'center',title:'信息',iconCls:'icon-ok'">
           <table id="dg" style="table-layout:fixed;width:100%"></table>
        </div>
        <div id="div" class="easyui-dialog" style="width:15%;" closed="true" buttons="#dlg-buttons">
          <table id="ao" title="权限" style="table-layout:fixed;width:auto"></table>
        </div>
        <div id="toolbar">
          <a href="javascript:addRole()" class="easyui-linkbutton" iconCls="icon-add" >新增</a>
          <a href="javascript:insertSearchRole();" class="easyui-linkbutton" iconCls="icon-search">查找</a> 
       </div>
       <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
        <div id="div0">
          <select class="fields" id="fields"></select>
          <select class="condition" id="condition"></select>
          <input class="content" id="content"/>
          <select class="joint" id="joint"></select>
          <a href="javascript:newSearchRole();" class="easyui-linkbutton" iconCls="icon-add"></a>
          <a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
        </div>
      </div>
      <div id="searchButton">
      <a href="javascript:searchRole();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
      <a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
    </div>
    <!-- 添加修改 -->
    <div id="dlg" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
      <form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
        <div class="fitem">
              <lable><span class="required">*</span>角色名</lable>
              	<input type="hidden"  id="validName"/>
                <input id="roleName" name="roleName" class="easyui-textbox" data-options="validType:'roleValidate',required:true">
            </div>
            <div class="fitem">
              <lable>描述</lable>
                <input name="roleDesc" class="easyui-textbox" data-options="required:false">
            </div>
      <div class="fitem">
        <lable>状态</lable>&nbsp;&nbsp;
           <span id="radios"></span>
      </div>
            <div style="margin-bottom:20px;margin-left:100px;" align="center">
                <table id="tt" title="权限列表" checkbox="true" style="table-layout:fixed"></table>
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
             <input name="id" id="id" class="easyui-textbox" type="hidden" value="${role.id}">
            </div>
            <div class="fitem">
              <lable>角色名</lable>
                <input name="roleName" id="roleName" class="easyui-textbox" readonly="readonly"/>
            </div>
            
            <div class="fitem">
              <lable>描述</lable>
                <input name="roleDesc" id="roleDesc" class="easyui-textbox" readonly="readonly"/>
            </div>
      <div class="fitem">
        <lable>状态</lable>
<!--       <span id="radio"></span> -->
        <input id="roleStatus" name="roleStatus" class="easyui-textbox" readonly="readonly"/>
      </div>
            <div style="margin-bottom:20px;margin-left:100px;" align="center">
                <table id="tt" title="权限列表" checkbox="true" readonly="true" style="table-layout:fixed;width:100%"></table>
            </div>
      </form>
  </div>
    <div id="remove-buttons">
      <a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
      <a href="javascript:$('#rdlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
    </div>
     </div>
</body>
</html>
 