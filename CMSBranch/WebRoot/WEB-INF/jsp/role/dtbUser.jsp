<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改角色</title>
    
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
	<div  id="body" region="center"  hide="true"  split="true" title="分配用户" style="background: white; height: 335px;">
        <div data-options="region:'center',title:'',iconCls:'icon-ok'">
    
    <div id="toolbar" style="text-align: center ">
       <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">分配用户</div>

            <div style="margin-bottom:10px;display: none;">
                <input name="id" id="id" class="easyui-textbox" type="hidden" value="${role.id}">
            </div>
            
			<div style="margin-bottom:10px;display: none;">
                <input name="roleName" class="easyui-textbox" type="hidden" value="${role.roleName}">
            </div>            
           
            <div style="margin-bottom:20px" align="center">
                <table id="tt" title="用户列表" checkbox="true" style="table-layout:fixed;width:100%"></table>
            </div>
    </form>
    </div>
   <div id="dlg-buttons" style="text-align: center ">
        <a href="role/AllRole" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveRole()" style="width:90px">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="role/AllRole" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
    </div>
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
		idField : 'users_name',
		url : "role/getAllUser?id="+$("#id").val(),
		rownumbers : false,
		showPageList : false,
		checkOnSelect:true,
		selectOnCheck:true,
		columns : [ [ {
		    field:'ck',
			checkbox:true
		},{
			field : 'users_name',
			title : '用户名',
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
	             
			       /*  if(data){
			        $.each(data.rows, function(index, item){
			        	    var a = $("#id").val();
			        	  
			        	    var b;
			        	    var c
						    $.ajax( {
							url : 'role/getUser?id='+a,
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
			        if(item.users_name==c[i].users_name){
			        $('#tt').datagrid('checkRow', index);
			        }
			        }
			        });
			        } */
			        }                   
	});
}

        function saveRole(){
         var rid = $('#id').val();
         var rows = $("#tt").datagrid("getSelections");
         var str="";
		for(var i=0; i<rows.length; i++){
			str += rows[i].id+",";
			}
         var url;
          url = "role/dtbUser"+"?&uid="+str+"&rid="+rid;
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg){
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg').dialog('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the user data
                    }
                }
            });
        }
    </script>
    </div>
</body>
</html>