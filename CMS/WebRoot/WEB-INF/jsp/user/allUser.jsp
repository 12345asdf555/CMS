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
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/user/alluser.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>

  </head>
  
<body class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
   <div id="body" region="center"  hide="true"  split="true" title="用户管理" style="background: #eee; height: 335px;">
	  	
        <table id="dg" style="table-layout:fixed;width:100%"></table>
        
        <div id="div1" class="easyui-dialog" style="width:15%;" closed="true" buttons="#dlg-buttons">
        <table id="ro" title="角色" style="table-layout:fixed;width:auto"></table>
        </div>

        <div id="toolbar" style="margin-bottom: 5px;">
        	<a href="javascript:addUser()" class="easyui-linkbutton" iconCls="icon-add">新增</a>
        	<a href="javascript:insertSearchUser();" class="easyui-linkbutton" iconCls="icon-search">查找</a>   
        	
<%-- 			<c:url value="j_spring_security_logout" var="logoutUrl"/>
   			<a href="${logoutUrl }">退出系统</a> --%>
   			<!-- <a href="javascript:logout();">退出系统</a> -->
        	
    	</div>
    		<!-- 自定义多条件查询 -->
	    <div id="searchdiv" class="easyui-dialog" style="width:800px; height:400px;" closed="true" buttons="#searchButton" title="自定义条件查询">
	    	<div id="div0">
		    	<select class="fields" id="fields"></select>
		    	<select class="condition" id="condition"></select>
		    	<input class="content" id="content"/>
		    	<select class="joint" id="joint"></select>
		    	<a href="javascript:newSearchUser();" class="easyui-linkbutton" iconCls="icon-add"></a>
		    	<a href="javascript:removeSerachByUser();" class="easyui-linkbutton" iconCls="icon-remove"></a>
	    	</div>
	    </div>
	    <div id="searchButton">
			<a href="javascript:searchUser();" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
			<a href="javascript:close();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
    </div>
</body>
</html>
 
 
