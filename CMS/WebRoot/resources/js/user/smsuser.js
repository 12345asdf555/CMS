$(function() {
	dgDatagrid();
	$('#dlg').dialog( {
		onClose : function() {
			$("#fm").form("disableValidation");
		}
	})
});

function dgDatagrid() {
	$("#dg").datagrid({
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "user/getSMSUser",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'fid',
			title : '序号',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'fuserid',
			title : '用户id',
			width : 150,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'fname',
			title : '姓名',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'femail',
			title : '邮箱',
			width : 250,
			halign : "center",
			align : "center"
		}, {
			field : 'fphone',
			title : '手机',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'fitemname',
			title : '所属部门',
			width : 200,
			halign : "center",
			align : "center"
		}, {
			field : 'fitemid',
			title : '项目id',
			width : 150,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'edit',
			title : '编辑',
			width : 180,
			halign : "center",
			align : "center",
			formatter : function(value, row, index) {
				var str = '<a id="edit" class="easyui-linkbutton" href="javascript:editSMSUser()"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeSMSUser()"/>';
				return str;
			}
		} ] ],
		toolbar : '#welderTable_btn',
		pagination : true,
		fitColumns : true,
		onLoadSuccess : function(data) {
			$("a[id='edit']").linkbutton({
				text : '修改',
				plain : true,
				iconCls : 'icon-edit'
			});
			$("a[id='remove']").linkbutton({
				text : '删除',
				plain : true,
				iconCls : 'icon-remove'
			});
		}
	});
}

var url = "";
var flag = 1;
function saveSMSUser() {
	flag = 1;
	$('#dlg').window({
		title : "新增短信用户",
		modal : true
	});
	$('#dlg').window('open');
	$('#fm').form('clear');
	url = "user/addSmsUser";
}

function editSMSUser() {
	flag = 2;
	$('#fm').form('clear');
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').window({
			title : "修改短信用户",
			modal : true
		});
		$('#dlg').window('open');
		$('#fm').form('load', row);
		$("#fitemid").val(row.fitemid);
		$("#fitemname").val(row.fitemname);
		$("#folduserid").val(row.fuserid);
		url = "user/editSmsUser";
	}
}
//提交
function save() {
	var url2 = "";
	if (flag == 1) {
		messager = "新增成功！";
		url2 = url;
	} else if (flag == 2) {
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
var url = "";
function removeSMSUser() {
	$('#rfm').form('clear');
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#rdlg').window({
			title : "删除短信用户",
			modal : true
		});
		$('#rdlg').window('open');
		$('#rfm').form('load', row);
		url = "user/removeSmsUser?id=" + row.fid;
	}
}

function remove() {
	$.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
		if (flag) {
			$('#rfm').form('submit', {
				url : url,
				onSubmit : function() {
					return $(this).form('enableValidation').form('validate');
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (!result.success) {
						$.messager.show({
							title : 'Error',
							msg : result.errorMsg
						});
					} else {
						$.messager.alert("提示", "删除成功！");
						$('#rdlg').dialog('close');
						$('#dg').datagrid('reload');
					}
				}
			})
		}
	});
}

function dlgSearchUser(){
	$('#userdg').datagrid('load',{
		"name" : '%'+$("#searchname").val()+'%'
	});
}

function selectUser(){
	$('#userdlg').window({
		title : "选择用户",
		modal : true
	});
	$('#userdlg').window('open');
	$("#userdg").datagrid({
		fitColumns : true,
		height : $("#userdlg").height()-20,
		width : $("#userdlg").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "user/getSelectUser",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		showPageList : false,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : '用户id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'insid',
			title : '组织机构id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'name',
			title : '用户名',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'phone',
			title : '电话',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'email',
			title : '邮箱',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'position',
			title : '岗位',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'insname',
			title : '部门',
			width : 100,
			halign : "center",
			align : "center"
		} ] ]
	});
}

function select(){
	var row = $('#userdg').datagrid('getSelected');
	$("#fphone").textbox("setValue",row.phone);
	$("#femail").textbox("setValue",row.email);
	$("#fitemid").val(row.insid);
	$("#fuserid").val(row.id);
	$("#fitemname").textbox("setValue",row.insname);
	$("#fname").textbox("setValue",row.name);
	$('#userdlg').dialog('close');
}

function closeUser(){
	$('#userdlg').dialog('close');
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}