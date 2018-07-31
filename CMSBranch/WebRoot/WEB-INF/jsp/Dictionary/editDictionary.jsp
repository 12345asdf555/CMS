<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改字典</title>
    
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
	<script type="text/javascript" src="resources/js/Dictionary/addeditdictionary.js"></script>

  </head>
  	
  <body class="easyui-layout">
    	<div id="body" region="center"  hide="true"  split="true" title="修改字典" style="background: white; height: 335px;">
    		<div style="text-align: center ">
    				<form action="" id="fm" method="post" data-options="novalidate:true" style="margin:0;padding:20px 50px">
    					<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">字典编辑<lable style="color:red">(谨慎操作!)</lable></div>
    					<div>
    						<div class="fitem">
    							<lable>名称</lable>
    							<input name="id" id="id" type="hidden" value="${Dictionary.id}">
			    				<input class="easyui-textbox" name="valueName" id="valueName" value="${Dictionary.valueName}" data-options="required:true"/>
			    			</div>
			    			<div align="center">
    							<label id="wxts" style="display: none;color:red;">(注：请输入1~100之间的数字)</label>
    						</div>
			    			<div class="fitem">
			    				<lable>类型</lable>
			    				<input name="type" id="type" value="${Dictionary.typeid }" type="hidden">
			    				<select class="easyui-combobox" name="typeid" id="typeid" data-options="required:true,editable:false"></select>
			    			</div>
			    			<div class="">
			    				<lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:saveDictionary(2);" class="easyui-linkbutton"	iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="Dictionary/goDictionary" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
								</lable>
			    			</div>
    					</div>
    				</form>
    		</div>
    	</div>
  </body>
  <script type="text/javascript">
	$(function(){
		$("#typeid").combobox("select",$("#type").val());
	})
</script>
</html>
