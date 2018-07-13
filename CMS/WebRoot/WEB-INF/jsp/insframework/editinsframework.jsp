<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改组织机构</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/insframework/addeditinsframework.js"></script>
	<script type="text/javascript" src="resources/js/insframework/insframeworktree.js"></script>
	<script type="text/javascript">
	</script>
  </head>
  
  <body class="easyui-layout">
  	<jsp:include  page="../insframeworktree.jsp"/>
    <div  id="body" region="center"  hide="true"  split="true" title="修改组织机构" style="background: white; height: 335px;">
		<div style="text-align: center ">
			<form id="fm" class="easyui-form" method="post" data-options="novalidate:true"><br/>
				<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">组织机构编辑<br/><lable style="color:red">(谨慎操作!)</lable></div>
				<div class="fitem">
					<lable>名称</lable>
					<input type="hidden" id="flag" value="0"/>
					<input type="hidden" id="id" value="${insf.id }"/>
					<input type="hidden" id="validname" value="${insf.name }"/>
					<input class="easyui-textbox" name="name" id="name" value="${insf.name }" data-options="validType:'insfnameValidate',required:true"/>
				</div>
				<div class="fitem">
					<lable>名称简写</lable>
					<input class="easyui-textbox" name="logogram" value="${insf.logogram }" id="logogram"/>
				</div>
				<div class="fitem">
					<lable>项目编码</lable>
					<input class="easyui-textbox" name="code" value="${insf.code }" id="code"/>
				</div>
				<div class="fitem">
					<lable>上级项目</lable>
					<input type="hidden" id="parentid" value="${insf.parent }"/>
<%-- 					<select class="easyui-combobox" name="parent" value="${insf.parent }" id="parent" data-options="required:true,editable:false"></select> --%>
					<input class="easyui-textbox" id="parentname" name="parentname" value="${parent }" readonly="readonly"/>
				</div>
				<div class="fitem">
					<lable>项目类型</lable>
					<input type="hidden" id="type" value="${insf.type }"/>
					<select class="easyui-combobox" name="typeid" id="typeid" value="${insf.type }" data-options="required:true,editable:false"></select>
				</div>
				<div class="weldbutton">
					<a href="javascript:editInsframework();" class="easyui-linkbutton"	iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="insframework/goInsframework" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
				</div>
			</form>
		</div>
	</div>
  </body>
</html>
