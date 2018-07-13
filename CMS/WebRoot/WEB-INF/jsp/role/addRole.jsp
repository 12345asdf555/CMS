<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增角色</title>
    
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
	<!-- <script type="text/javascript" src="resources/js/role/addrole"></script> -->

  </head>
<body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="新增角色" style="background: white; height: 335px;">
   
    <div style="text-align: center ">
       <form action="" id="fm" method="post" data-options="novalidate:true" style="margin:0;padding:20px 50px">
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">新增角色</div>
            <div class="fitem">
            	<lable>角色名</lable>
                <input id="roleName" name="roleName" class="easyui-textbox" data-options="validType:'roleValidate',required:true">
            </div>
            <div class="fitem">
            	<lable>描述</lable>
                <input name="roleDesc" class="easyui-textbox" data-options="required:false">
            </div>
			<div class="fitem">
				<lable>状态</lable>&nbsp;&nbsp;
   				<span id="radios"></span>
			</div>
            <div style="margin-bottom:20px;margin-left:100px;" align="center">
                <table id="tt" title="权限列表" checkbox="true" style="table-layout:fixed"></table>
            </div>
		    <div class="buttonoption">
			    <lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <a href="javascript:saveRole();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <a href="role/AllRole" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
				</lable>
		   </div> 
    </form>
    </div> 
    <script type="text/javascript">
        $(function(){
        statusRadio();
		$("#fm").form("disableValidation");
	    $("#tt").datagrid( {
		fitColumns : true,
		height : '250px',
		width : '15%',
		idField : 'authorities_desc',
		url : "role/getAllAuthority",
		rownumbers : false,
		showPageList : false,
		checkOnSelect:true,
		selectOnCheck:true,
		columns : [ [ {
		    field:'ck',
			checkbox:true
		},{
			field : 'id',
			title : 'id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		},{
			field : 'authorities_desc',
			title : '权限描述',
			width : 100,
			halign : "center",
			align : "left"
		}]]
	});
})
$("#fm").form("disableValidation");
var flag = 1;

        function saveRole(){
        flag = 1;
       /*   var status = $('#roleStatus').combobox('getValue'); */
         var rows = $('#tt').datagrid('getSelections');
         var sid = $("input[name='statusId']:checked").val();
          var str="";
         for(var i=0; i<rows.length; i++){
			str += rows[i].id+",";
			}
         var url;
         /*  url = "role/addRole"+"?status="+status+"&aid="+str; */
          url = "role/addRole"+"?status="+sid+"&aid="+str;
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
                     return $(this).form('enableValidation').form('validate');
                },
                success: function(result){
                    var result = eval('('+result+')');
                    if (result.errorMsg){
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    } else {
              			$.messager.alert("提示", "新增成功");              					
						var url = "role/AllRole";
						var img = new Image();
					    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
					    url = img.src;  // 此时相对路径已经变成绝对路径
					    img.src = null; // 取消请求
						window.location.href = encodeURI(url);
                    }
                }
            });
        }
        function statusRadio(){
		$.ajax({  
		    type : "post",  
		    async : false,
		    url : "role/getStatusAll",  
		    data : {},  
		    dataType : "json", //返回数据形式为json  
		    success : function(result) {
		    	if (result) {
		    		var str = "";
		    		for (var i = 0; i < result.ary.length; i++) {
		    			str += "<input type='radio' class='radioStyle' name='statusId' id='sId' value=\"" + result.ary[i].id + "\" />"  
	                    + result.ary[i].name+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		    		}
		            $("#radios").html(str);
		            $("input[name='statusId']").eq(0).attr("checked",true);
		        }  
		    },  
		    error : function(errorMsg) {  
		        alert("数据请求失败，请联系系统管理员!");  
		    }  
		});
	}
    </script>
    </div>
</body>
</html>