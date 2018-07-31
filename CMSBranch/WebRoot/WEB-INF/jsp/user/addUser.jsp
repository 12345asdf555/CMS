<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增用户</title>
    
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
	<!-- <script type="text/javascript" src="resources/js/user/adduser"></script> -->

  </head>
<body class="easyui-layout">
    <div  id="body" region="center"  hide="true"  split="true" title="新增用户" style="background: white; height: 335px;">
		<div style="text-align: center ">
       	<form action="" id="fm" method="post" data-options="novalidate:true" style="margin:0;padding:20px 50px">
            
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc">新增用户</div>
            <div class="fitem">
            	<lable>用户名</lable>
                <input name="userName" id="userName" class="easyui-textbox" data-options="required:true">
            </div>
            <div class="fitem">
            	<lable>登录名</lable>
                <input name="userLoginName" class="easyui-textbox" data-options="validType:'userValidate',required:true">
            </div>
            <div class="fitem">
            	<lable>密码</lable>
                <input name="userPassword" type="password" class="easyui-textbox" data-options="required:true">
            </div>
            <div class="fitem">
            	<lable>电话</lable>
                <input name="userPhone" class="easyui-textbox" data-options="required:false">
            </div>
            <div class="fitem">
            	<lable>邮箱</lable>
                <input name="userEmail" class="easyui-textbox" data-options="required:false">
            </div>
            <div class="fitem">
            	<lable>岗位</lable>
                <input name="userPosition" class="easyui-textbox" data-options="required:true">
            </div>
            <div class="fitem">
				<lable>部门</lable>
<!-- 				<select class="easyui-combobox" name="userInsframework" id="userInsframework" data-options="required:true"></select> -->
				<input type="hidden"  id="insid" value="${insid }"/>
				<input class="easyui-textbox" name="userInsframework" id="userInsframework" value="${insfname }" readonly="readonly"/>
        	</div>

			<div class="fitem">
				<lable>状态</lable>&nbsp;&nbsp;
   				<span id="radios"></span>
			</div>
	        <div style="margin-bottom:20px;margin-left:100px" align="center">
	        <table id="tt" name="tt" title="角色列表" checkbox="true" style="table-layout:fixed"></table>
	        </div>
	    	<div class="buttonoption">
				<lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <a href="javascript:saveUser();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <a href="user/AllUser" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
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
		idField : 'roles_name',
		url : "user/getAllRole",
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
			hidden:true
		},{
			field : 'roles_name',
			title : '角色名',
			width : 100,
			halign : "center",
			align : "left"
		}]]
	});
})
        $("#fm").form("disableValidation");
 		var flag = 1; 
        function saveUser(){
        flag = 1;
         var insframework = $("#insid").val();
         var sid = $("input[name='statusId']:checked").val();
         var rows = $("#tt").datagrid("getSelections");
         var str="";
		for(var i=0; i<rows.length; i++){
			str += rows[i].id+",";
			}
         var url;
          url = "user/addUser?userInsframework="+insframework+"&status="+sid+"&rid="+str;
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
					var url = "user/AllUser";
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
		    url : "user/getStatusAll",  
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