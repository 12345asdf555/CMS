<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>删除角色</title>
    
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
	
  </head>
<body class="easyui-layout">
	<div  id="body" region="center"  hide="true"  split="true" title="修改角色" style="background: white; height: 335px;">
    <div id="toolbar" style="text-align: center ">
       <form action="" id="fm" method="post" data-options="novalidate:true" style="margin:0;padding:20px 50px">
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">删除角色</div>
            <div style="margin-bottom:10px;display: none;">
                <input name="id" id="id" class="easyui-textbox" type="hidden" value="${role.id}">
            </div>
            <div class="fitem">
            	<lable>角色名</lable>
                <input name="roleName" class="easyui-textbox" readonly="true" value="${role.roleName}" style="width:100%">
            </div>
            <div class="fitem">
            	<lable>描述</lable>
                <input name="roleDesc" class="easyui-textbox" readonly="true" value="${role.roleDesc}" style="width:100%">
            </div>
			<div class="fitem">
				<lable>状态</lable>
				<input id="status" class="easyui-textbox" readonly="true"  value="${role.statusname}"/>
			</div>
            <div style="margin-bottom:20px;margin-left:100px;" align="center">
                <table id="tt" title="权限列表" checkbox="true" readonly="true" style="table-layout:fixed;width:100%"></table>
            </div>
		    <div class="buttonoption">
			    <lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <a href="javascript:saveRole();" class="easyui-linkbutton" iconCls="icon-ok">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <a href="role/AllRole" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
				</lable>
		   </div> 
    </form>
    </div>
    <script type="text/javascript">
            $(function(){
		    showdatagrid();
		})
		
		function showdatagrid(){
	    $("#tt").datagrid( {
		fitColumns : true,
		height : '250px',
		width : '15%',
		idField : 'authorities_desc',
		url : "role/getAllAuthority1?id="+$("#id").val(),
		rownumbers : false,
		showPageList : false,
		checkOnSelect:true,
		selectOnCheck:true,
		columns : [ [ {
		    field:'ck',
			checkbox:true
		},{
			field : 'symbol',
			title : 'symbol',
			width : 100,
			halign : "center",
			align : "left",
			hidden:true
		},{
			field : 'authorities_desc',
			title : '权限描述',
			width : 100,
			halign : "center",
			align : "left"
		}]],
				onLoadSuccess:function(data){       
			 if(data){
             $.each(data.rows, function(index, item){
             if(item.symbol==1){
	         $('#tt').datagrid('checkRow', index);
	         }
             })
             }           
/* 			        if(data){
			        $.each(data.rows, function(index, item){
			        	    var a = $("#id").val();
			        	    var b;
			        	    var c
						    $.ajax( {
							url : 'role/getAuthority?id='+a,
							data : {
							},
							type : 'post',
							async : false,
							dataType : 'json',
							success : function(result) {
							b = result.rows;
							},
							error : function() {
								alert("获取数据失败，请联系系统管理员！");
							}
						});
						c = eval(b);
					for(var i=0;i<c.length;i++)
					{
			        if(item.authorities_desc==c[i].authorities_desc){
			        $('#tt').datagrid('checkRow', index);
			        }
			        }
			        });
			        } */
			        }                   
	});
}

        function saveRole(){
		var id = document.getElementById("id").value;
		$.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
			if (flag) {
				$.ajax({  
			        type : "post",  
			        async : false,
			        url : "role/delRole?id="+id,  
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
								var url = "role/AllRole";
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