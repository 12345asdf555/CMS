<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>采集模块管理</title>
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
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/gather/gather.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>
	
  </head>
  
  <body  class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
  	<div id="body" region="center"  hide="true"  split="true" title="采集模块管理" style="background: witch; height: 335px;">
	  	<input type="hidden" id="treeid"/>
	  	<div id="gather_btn">
			<div style="margin-bottom: 5px;">
				<a href="gather/goaddGather" class="easyui-linkbutton" iconCls="icon-add">新增</a>
				<a href="javascript:insertSearchGather();" class="easyui-linkbutton" iconCls="icon-search" >查找</a>
			</div>
		</div>
		
	    <table id="gatherTable" style="table-layout: fixed; width:100%;"></table>
	    
	    <!-- 自定义多条件查询 -->
	    <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
	    	<div id="div0">
		    	<select class="fields" id="fields" data-options="editable:false"></select>
		    	<select class="condition" id="condition" data-options="editable:false"></select>
		    	<input class="content" id="content"/>
		    	<select class="joint" id="joint" data-options="editable:false"></select>
		    	<a href="javascript:newSearchGather();" class="easyui-linkbutton" iconCls="icon-add"></a>
		    	<a href="javascript:removeSerach();" class="easyui-linkbutton" iconCls="icon-remove"></a>
	    	</div>
	    </div>
	    <div id="searchButton">
			<a href="javascript:searchGather();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
			<a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	    
	</div>
  </body>
</html>
