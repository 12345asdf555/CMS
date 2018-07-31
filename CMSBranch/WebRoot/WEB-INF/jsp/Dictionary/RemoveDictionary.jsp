<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'RemoveDictionary.jsp' starting page</title>
    
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

  </head>
  
  <body class="easyui-layout">
    <div id="body" region="center"  hide="true"  split="true" title="删除字典" style="background: white; height: 335px;">
    	<div style="text-align: center ">
    		<div><br/>
				<div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">删除字典<lable style="color:red">(谨慎操作!)</lable></div>
    			<div class="fitem">
    				<lable>名称</lable>
    				<input name="id" id="id" lass="easyui-textbox" type="hidden" value="${Dictionary.id} ">
			    	<input class="easyui-textbox" name="valueName" id="valueName" value="${Dictionary.valueName}" readonly="true"/>
			    </div>
			    <div class="fitem">
			    	<lable>类型</lable>
			    	<input name="typeid" id="typeid" class="easyui-textbox" value="${Dictionary.back}" readonly="true"/>
			    </div>
			    <div class="">
			    	<lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:removeDictionary();" class="easyui-linkbutton"	iconCls="icon-ok">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="Dictionary/goDictionary" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
					</lable>
			    </div>
    	</div>
    </div>
  </body>
  <script type="text/javascript">
  	function removeDictionary(){
  	var id=$("#id").val();
	$.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
		if (flag) {
			$.ajax({  
		        type : "post",  
		        async : false,
		        url : "Dictionary/deleteDictionary?id="+id,  
		        data : {},  
		        dataType : "json", //返回数据形式为json  
		        success : function(result) {
		            if (result) {
		            	if (!result.success) {
							$.messager.show( {
								title : 'Error',
								msg : result.errorMsg
							});
						} else {
							var time = 500;
							if(result.msg==null){
							$.messager.alert("提示", "删除成功！");
							}else{
								time = 2500;
								$.messager.show( {title : '提示',msg : result.msg});
							}
							window.setTimeout(function() {
								var url = "Dictionary/goDictionary";
								var img = new Image();
							    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
							    url = img.src;  // 此时相对路径已经变成绝对路径
							    img.src = null; // 取消请求
								window.location.href = encodeURI(url);
							}, time);
						}
		            }  
		        },  
		        error : function(errorMsg) {  
		            alert("数据请求失败，请联系系统管理员!");  
		        }  
		   }); 
		}
	});
} 
  </script>
</html>
