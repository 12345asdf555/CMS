<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除资源</title>
    
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
	<!-- <script type="text/javascript" src="resources/js/user/edituser"></script> -->
	
  </head>
<body class="easyui-layout">
<div  id="body" region="center"  hide="true"  split="true" title="修改资源" style="background: white; height: 335px;">
    <div id="toolbar" style="text-align: center ">
       <form action="" id="fm" method="post" data-options="novalidate:true" style="margin:0;padding:20px 50px">
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">删除资源</div>
            <div class="fitem">
                <input name="id" id="id" type="hidden" value="${resource.id}">
            </div>
            <div class="fitem">
				<lable>资源名</lable>
                <input name="resourceName" id="userName" readonly="true" class="easyui-textbox" value="${resource.resourceName}" style="width:100%">
            </div>
            <div class="fitem">
				<lable>类型</lable>
                <input name="resourceType" class="easyui-textbox" readonly="true" value="${resource.resourceType}" style="width:100%">
            </div>
            <div class="fitem">
				<lable>资源</lable>
                <input name="resourceAddress" class="easyui-textbox" readonly="true"  value="${resource.resourceAddress}" style="width:100%">
            </div>
            <div class="fitem">
				<lable>描述</lable>
                <input name="resourceDesc" class="easyui-textbox" readonly="true"  value="${resource.resourceDesc}" style="width:100%">
            </div>
			<div class="fitem">
				<lable>状态</lable>
				<input id="status" class="easyui-textbox" readonly="true"  value="${resource.statusname}"/>
			</div>
		    <div class="buttonoption">
				<lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <a href="javascript:saveResource();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <a href="resource/AllResource" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		        </lable>
		    </div>

        </form>
    </div>
    
    <script type="text/javascript">
		 function saveResource(){
         var id = document.getElementById("id").value;
		$.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
			if (flag) {
				$.ajax({  
			        type : "post",  
			        async : false,
			        url : "resource/delResource?id="+id,  
			        data : {},  
			        dataType : "json", //返回数据形式为json  
			        success : function(result) {
			            if (result) {
			            	if (!result.success) {
								$.messager.show( {
									title : 'Error',
									msg : result.msg
								});
							} else {
								$.messager.alert("提示", "删除成功！");
								var url = "resource/AllResource";
								var img = new Image();
							    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
							    url = img.src;  // 此时相对路径已经变成绝对路径
							    img.src = null; // 取消请求
								window.location.href = encodeURI(url);
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
    </div>
</body>
</html>