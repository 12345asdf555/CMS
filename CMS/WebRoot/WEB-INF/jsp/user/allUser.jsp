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
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript" src="resources/js/user/alluser.js"></script>
	<script type="text/javascript" src="resources/js/search/search.js"></script>

  </head>
  
<body class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
   <div id="body" region="center"  hide="true"  split="true">
	  	
        <table id="dg" style="table-layout:fixed;width:100%"></table>
        
        <div id="div1" class="easyui-dialog" style="width: 400px; height: 500px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
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
    <!--新增 修改-->
		<div id="dlg" class="easyui-dialog" style="width: 750px; height: 550px; padding:10px 20px" closed="true" buttons="#dlg-buttons">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
            <div class="fitem">
            	<lable><span class="required">*</span>用户名</lable>
                <input name="userName" id="userName" class="easyui-textbox" data-options="required:true">
            	<lable><span class="required">*</span>登录名</lable>
            	<input type="hidden"  id="validName"/>
                <input name="userLoginName" class="easyui-textbox" data-options="validType:'userValidate',required:true">
            </div>
            <div class="fitem">
            	<lable><span class="required">*</span>密码</lable>
                <input name="userPassword" type="password" class="easyui-textbox" data-options="required:true">
                <lable>电话</lable>
                <input name="userPhone" class="easyui-textbox" data-options="validType:'phoneNum',required:false">
            </div>
            <div class="fitem">
            	<lable>邮箱</lable>
                <input name="userEmail" class="easyui-textbox" data-options="validType:'email',required:false">
                <lable><span class="required">*</span>岗位</lable>
                <input name="userPosition" class="easyui-textbox" data-options="required:true">
            </div>
            <div class="fitem">
				<lable><span class="required">*</span>部门</lable>
				<select class="easyui-combobox" name="insframework" id="insframework" data-options="required:true,editable:false"></select>
				<input type="hidden"  id="insid"/>
				<input class="easyui-textbox" name="userInsframework" id="userInsframework" readonly="readonly"/>
        		<lable>状态</lable>&nbsp;&nbsp;
   				<span id="radios"></span>
        	</div>
	        <div style="margin-bottom:60px;margin-left:100px" align="lift">
	        <table id="tt" name="tt" title="角色列表" checkbox="true" style="table-layout:fixed"></table>
	    	</div>
        </form>
       </div>
	    <div id="dlg-buttons">
			<lable>
			       <a href="javascript:saveUser();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			       <a href="javascript:closeIU();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		    </lable>
    </div>
    <!-- 删除 -->
		<div id="rdlg" class="easyui-dialog" style="width: 750px; height: 550px; padding:10px 20px" closed="true" buttons="#remove-buttons">
			<form id="rfm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
				<div class="fitem">
	                <input name="id" id="id" type="hidden" >
	            </div>
	            <div class="fitem">
	            	<lable>用户名</lable>
	                <input name="userName" id="userName" class="easyui-textbox" readonly="true">
	            	<lable>登录名</lable>
	                <input name="userLoginName" class="easyui-textbox" readonly="true">
	            </div>
	            <div class="fitem">
	            	<lable>密码</lable>
	                <input name="userPassword" class="easyui-textbox" type="password" readonly="true">
	            	<lable>电话</lable>
	                <input name="userPhone" class="easyui-textbox" readonly="true" >
	            </div>
	            <div class="fitem">
	            	<lable>邮箱</lable>
	                <input name="userEmail" class="easyui-textbox" readonly="true" >
	            	<lable>岗位</lable>
	                <input id="userPosition" name="userPosition" class="easyui-textbox" readonly="readonly">
	            </div>
	            <div class="fitem">
	            	<lable>部门</lable>
	            	<input class="easyui-textbox" name="userInsframework" id="users_insframework"  readonly="true" />
					<lable>状态</lable>
					<input name="status" class="easyui-textbox" readonly="true"/>
	            </div>
		        <div align="center">
			        <table id="rtt" title="角色列表" checkbox="true" readonly="true" style="table-layout:fixed;width:100%"></table>
			    </div>
			</form>
		<div id="remove-buttons">
			<a href="javascript:remove();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
			<a href="javascript:closeD()" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
		
  </div>
</body>
</html>
 
 
