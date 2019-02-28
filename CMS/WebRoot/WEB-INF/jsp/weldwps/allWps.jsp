<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工艺管理</title>
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
	<script type="text/javascript" src="resources/js/weldwps/allWps.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>
  </head>
  
  <body class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true">
   		<div id="dg_btn">
	        <div style="margin-bottom: 5px;">
	        	<a href="wps/goAddWps" class="easyui-linkbutton" iconCls="icon-add">新增</a>
	        	<a href="javascript:insertSearchWps();" class="easyui-linkbutton" iconCls="icon-search" >查找</a>
	        	<input type="radio" name="wps" value="0" checked="checked" style="width:20px;"/>工艺<input type="radio" name="wps" value="1" style="width:20px;"/>焊接参数
	          	<a href="javascript:importclick();" class="easyui-linkbutton" iconCls="icon-excel">导入</a>
			    <a href="javascript:exportWPS();" class="easyui-linkbutton" iconCls="icon-excel">导出</a>  
	    	</div>
	  	</div>
	    <div id="importdiv" class="easyui-dialog" style="width:300px; height:200px;" closed="true">
	      <form id="importfm" method="post" class="easyui-form" data-options="novalidate:true" enctype="multipart/form-data"> 
	        <div>
	          <span><input type="file" name="file" id="file"></span><br/><br/>
	          <input type="button" value="上传" onclick="importWPS()" class="upButton"/>
	        </div>
	      </form>
	    </div>
	  	<table id="dg" style="table-layout:fixed;width:100%"></table>
    	<div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
	    	<div id="div0">
		    	<select class="fields" id="fields"></select>
		    	<select class="condition" id="condition"></select>
		    	<input class="content" id="content"/>
		    	<select class="joint" id="joint"></select>
		    	<a href="javascript:newSearchWps();" class="easyui-linkbutton" iconCls="icon-add"></a>
		    	<a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
	    	</div>
	    </div>
	    <div id="searchButton">
			<a href="javascript:searchWps();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
			<a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
   	</div>
</body>
</html>
 
 
