$(function() {
	$("#wid").next().hide();

})
var url = "";
function removeWeldingMachine() {
	$('#rfm').form('clear');
	var row = $('#weldingmachineTable').datagrid('getSelected');
	if (row) {
		$('#rdlg').window({
			title : "删除设备",
			modal : true
		});
		$('#rdlg').window('open');
		$('#rfm').form('load', row);
		weldingMachineDatagrid();
		url = "weldingMachine/removeWeldingMachine?wid=" + row.id + "&insfid=" + row.insframeworkId;
	}
}
function remove() {
	$.messager.confirm('提示', '此操作不可撤销并同时删除其维修记录，是否确认删除?', function(flag) {
		if (flag) {
			$.ajax({
				type : "post",
				async : false,
				url : url,
				data : {},
				dataType : "json", //返回数据形式为json  
				success : function(result) {
					if (result) {
						if (!result.success) {
							$.messager.show({
								title : 'Error',
								msg : result.errorMsg
							});
						} else {
							$.messager.alert("提示", "删除成功！");
							if (result.msg != null) {
								$.messager.show({
									title : '提示',
									msg : result.msg
								});
							}
							$('#rdlg').dialog('close');
							$('#weldingmachineTable').datagrid('reload');
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