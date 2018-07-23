function removeGather(){
	var id = $("#id").val();
	var insfid = $("#insfid").val();
	$.messager.confirm('提示', '此操作不可撤销并同时解绑焊机设备，是否确认删除?', function(flag) {
		if (flag) {
			$.ajax({  
		        type : "post",  
		        async : false,
		        url : "gather/removeGather?id="+id+"&insfid="+insfid,  
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
							$.messager.alert("提示", "删除成功！");
							if(result.msg!=null){
								$.messager.show( {title : '提示',msg : result.msg});
							}
							$('#rdlg').dialog('close');
							$('#gatherTable').datagrid('reload');
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