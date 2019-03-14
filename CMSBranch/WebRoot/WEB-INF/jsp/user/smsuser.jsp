<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title>
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
	<script type="text/javascript" src="resources/js/user/smsuser.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>

  </head>
  
<body class="easyui-layout">
   	<div id="body" region="center"  hide="true"  split="true">
    	<div id="welderTable_btn">
      		<div style="margin-bottom: 5px;">
		        <a href="javascript:saveSMSUser();" class="easyui-linkbutton" iconCls="icon-add">新增</a>
		        <a href="javascript:insertSearchSMSUser();" class="easyui-linkbutton" iconCls="icon-search" >查找</a>
      		</div>
    	</div>
    
	    <table id="dg" style="table-layout: fixed; width:100%;"></table>
	      
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
		          <input type="hidden" id="fid" name="fid"/>
		          <input type="hidden" id="fuserid" name="fuserid"/>
		          <input type="hidden" id="folduserid" name="folduserid"/>
		          <input class="easyui-textbox" id="fname"  name="fname" data-options="validType:'smsuserValidate',required:true" readonly="readonly"/>
		          <a href="javascript:selectUser();" class="easyui-linkbutton">选择</a>
		        </div>
	        	<div class="fitem">
	          		<lable><span class="required">*</span>电话</lable>
	          		<input class="easyui-textbox" id="fphone" name="fphone" data-options="validType:'phoneNum',required:true" />
	       	 	</div>
	        	<div class="fitem">
	          		<lable>邮箱</lable>
	          		<input class="easyui-textbox" id="femail" name="femail" data-options="validType:'email'">
	       	 	</div>
	        	<div class="fitem">
			         <lable><span class="required">*</span>所属项目</lable>
	          		 <input type="hidden" id="fitemid" name="fitemid" />
			         <input class="easyui-textbox" name="fitemname" id="fitemname" data-options="required:true" readonly="readonly"/>
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
        		<div style="margin-bottom:10px;display: none;"></div>
            	<div class="fitem">
          			<lable>姓名</lable>
          			<input class="easyui-textbox" name="fname" readonly="readonly"/>
        		</div>
        		<div class="fitem">
          			<lable>电话</lable>
          			<input class="easyui-textbox" name="fphone" readonly="readonly"/>
        		</div>
        		<div class="fitem">
          			<lable>邮箱</lable>
          			<input class="easyui-textbox" name="femail" readonly="readonly"/>
        		</div>
        		<div class="fitem">
		          	<lable>所属项目</lable>
		          	<input class="easyui-textbox" name="fitemname" readonly="readonly"/>
		        </div>
        	</form> 
    	</div>
		<div id="remove-buttons">
		  <a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
		  <a href="javascript:$('#rdlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>  
	</div>
	<div id="userdlg" class="easyui-dialog" style="width: 600px; height: 500px; padding:10px 20px" closed="true" buttons="#select-buttons">
      	<div id="fdlgSearch">
        	用户名：<input class="easyui-textbox" id="searchname"/>
        	<a href="javascript:dlgSearchUser();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      	</div>
      	<table id="userdg" style="table-layout: fixed; width:100%;"></table>	
		<div id="select-buttons">
		  <a href="javascript:select();" class="easyui-linkbutton" iconCls="icon-ok">选择</a>
		  <a href="javascript:$('#userdlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>  
	</div>
</body>
</html>
 
 
