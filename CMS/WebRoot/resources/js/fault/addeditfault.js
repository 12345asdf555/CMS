$(function() {
	codeCombobox();
	typeCombobox();
	$('#dlg').dialog({
		onClose : function() {
			$("#typeid").combobox('clear');
			$("#codeid").combobox('clear');
			$("#fm").form("disableValidation");
		}
	})
	$("#fm").form("disableValidation");
})


var url = "";
var flag = 1;
function addFault() {
	flag = 1;
	$("#selectMachine").show();
	$('#fm').form('clear');
	$('#dlg').window({
		title : "新增故障",
		modal : true
	});
	$('#dlg').window('open');
	url = "fault/addFault";
}

function editFault() {
	flag = 2;
	$('#fm').form('clear');
	$("#selectMachine").hide();
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').window({
			title : "故障代码修改",
			modal : true
		});
		$('#dlg').window('open');
		$('#fm').form('load', row);
		//$('#validName').val(row.resourceName);
		url = "fault/editFault?id=" + row.id;
	}
}
//提交
function saveFault() {
	var url2 = "";
	if (flag == 1) {
		messager = "新增成功！";
		url2 = url;
	} else {
		messager = "修改成功！";
		url2 = url;
	}
	$('#fm').form('submit', {
		url : url2,
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(result) {
			if (result) {
				var result = eval('(' + result + ')');
				if (!result.success) {
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				} else {
					$.messager.alert("提示", messager);
					if (result.msg != null) {
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					}
					$('#dlg').dialog('close');
					$('#dg').datagrid('reload');
				}
			}

		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
}

function selectMachine() {
	$('#fdlg').window({
		title : "固定资产编号",
		modal : true
	});
	$('#fdlg').window('open');
	weldingMachineDatagrid();
}
function weldingMachineDatagrid() {
	$("#weldingmachineTable").datagrid({
		height : $("#fdlg").height(),
		width : $("#fdlg").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "weldingMachine/getWedlingMachineList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : '序号',
			width : 50,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'equipmentNo',
			title : '固定资产编号',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'typeName',
			title : '设备类型',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'insframeworkName',
			title : '所属项目',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'insframeworkId',
			title : '所属项目id',
			width : 80,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'statusName',
			title : '状态',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'manufacturerName',
			title : '厂家',
			width : 150,
			halign : "center",
			align : "center"
		}
		] ],
		toolbar : '#dlgSearch',
		pagination : true,
		fitColumns : true
	});
}

function saveWeldingMachine() {
	var row = $("#weldingmachineTable").datagrid('getSelected');
	$("#machineno").textbox('setValue', row.equipmentNo);
	$("#itemid").val(row.insframeworkId);
	$("#machineid").val(row.id);
	$('#fdlg').dialog('close');
}

//故障代码
function codeCombobox() {
	$.ajax({
		type : "post",
		async : false,
		url : "fault/getTypeAll?num=9",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				var optionStr = '';
				for (var i = 0; i < result.ary.length; i++) {
					optionStr += "<option value=\"" + result.ary[i].id + "\" >"
						+ result.ary[i].name + "</option>";
				}
				$("#codeid").html(optionStr);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
	$("#codeid").combobox();
}

//故障类型
function typeCombobox() {
	$.ajax({
		type : "post",
		async : false,
		url : "fault/getTypeAll?num=7",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				var optionStr = '';
				for (var i = 0; i < result.ary.length; i++) {
					optionStr += "<option value=\"" + result.ary[i].id + "\" >"
						+ result.ary[i].name + "</option>";
				}
				$("#typeid").html(optionStr);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
	$("#typeid").combobox();
}