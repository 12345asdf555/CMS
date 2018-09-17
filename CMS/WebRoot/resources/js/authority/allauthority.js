$(function() {
	statusRadio();
	$('#dlg').dialog({
		onClose : function() {
			$("#fm").form("disableValidation");
		}
	})
	$("#fm").form("disableValidation");
})

$(function() {
	$("#dg").datagrid({
		fitColumns : true,
		height : $("#body").height() - 40,
		width : $("#body").width() - 30,
		idField : 'id',
		toolbar : "#toolbar",
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "authority/getAllAuthority",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'authorityName',
			title : '权限',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'authorityDesc',
			title : '描述',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'status',
			title : '状态',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'statusid',
			title : '状态id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'resources',
			title : 'URL',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value, row, index) {
				var str = "";
				str += '<a id="resource" class="easyui-linkbutton" href="javascript:resource(' + row.id + ')"/>';
				return str;
			}
		}, {
			field : 'edit',
			title : '编辑',
			width : 130,
			halign : "center",
			align : "center",
			formatter : function(value, row) {
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="javascript:editAuthorith()"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeAuthority()"/>';
				return str;
			}
		} ] ],
		toolbar : '#toolbar',
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
			$("a[id='resource']").linkbutton({
				text : '资源列表',
				plain : true,
				iconCls : 'icon-resource'
			});
		}
	});
})


var url = "";
var flag = 1;
function saveAuthority() {
	flag = 1;
	$("#fm").form("disableValidation");
	$('#dlg').window({
		title : "新增权限",
		modal : true
	});
	$('#dlg').window('open');
	$('#fm').form('clear');
	AutorityDatagrid();
	var statusid = document.getElementsByName("statusid");
	statusid[1].checked = 'checked';
	url = "authority/addAuthority";
}
var url = "";
function removeAuthority() {
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#rdlg').window({
			title : "删除权限",
			modal : true
		});
		$('#rdlg').window('open');
		$('#rfm').form('load', row);
		showdatagrid(row.id);
		url = "authority/delAuthority?id=" + row.id;
	}
}
function editAuthorith() {
	flag = 2;
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').window({
			title : "修改权限",
			modal : true
		});
		$('#dlg').window('open');
		AutorityDatagrid();
		$('#fm').form('load', row);
		$('#validName').val(row.authorityName);
		$("#authorityName").textbox("setValue", row.authorityName.substring(5));
		url = "authority/updateAuthority?aid=" + row.id;
	}
}
function doSearch(value) {
	$("#tt").datagrid({
		fitColumns : true,
		height : ($("#body").height()),
		width : $("#body").width(),
		idField : 'resources_name',
		url : "authority/getAllResource",
		rownumbers : false,
		showPageList : false,
		checkOnSelect : true,
		selectOnCheck : true,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'resources_name',
			title : '角色名',
			width : 100,
			halign : "center",
			align : "center"
		} ] ],
		onLoadSuccess : function(data) {
			if (data) {
				$.each(data.rows, function(index, item) {
					var rows = $("#dg").datagrid("getRows");
					for (var i = 0; i < rows.length; i++) {
						var rowID = rows[i].authorities_name;
						var id = rows[i].id;
						if (rowID == value) {
							$.ajax({
								url : 'authority/getResource?id=' + id,
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
							var c = eval(b);
							for (var j = 0; j < c.length; j++) {
								if (item.resources_name == c[j].resources_name) {
									$('#tt').datagrid('checkRow', index);
								}
							}
							$('#dlg').dialog('open').dialog('center').dialog('setTitle', '用户信息');
							$('#fm').form('load', rows[i]);
						}
					}
				})
			}
		}
	})
}

function showdatagrid(id) {
	$("#rtt").datagrid({
		fitColumns : true,
		height : '250px',
		width : '80%',
		idField : 'resources_name',
		url : "authority/getAllResource1?id=" + id,
		rownumbers : false,
		showPageList : false,
		checkOnSelect : true,
		selectOnCheck : true,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : 'id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'symbol',
			title : 'symbol',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'resources_name',
			title : '资源名',
			width : 100,
			halign : "center",
			align : "center"
		} ] ],
		onBeforeLoad : function(data) {
			$('#rtt').datagrid('clearChecked');
		},
		onLoadSuccess : function(data) {
			if (data) {
				$.each(data.rows, function(index, item) {
					if (item.symbol == 1) {
						$('#rtt').datagrid('checkRow', index);
					}
				})
			}
		}
	});
}


function remove() {
	$.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
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
								msg : result.msg
							});
						} else {
							$.messager.alert("提示", "删除成功！");
							$('#rdlg').dialog('close');
							$('#dg').datagrid('reload');
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

function resource(id) {
	$('#div').dialog('open').dialog('center').dialog('setTitle', '资源列表');
	$("#so").datagrid({
		fitColumns : true,
		height : '300px',
		width : $("#div").width(),
		idField : 'id',
		url : "authority/getResource?id=" + id,
		rownumbers : false,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'resources_name',
			title : 'URL',
			width : 100,
			halign : "center",
			align : "center"
		} ] ],
		rowStyler : function(index, row) {
			if ((index % 2) != 0) {
				//处理行代背景色后无法选中
				var color = new Object();
				color.class = "rowColor";
				return color;
			}
		}
	});
}

//提交
function save() {
	var sid = $("input[name='statusid']:checked").val();
	var aid = $('#id').val();
	var rows = $("#tt").datagrid("getSelections");
	var str = "";
	if (rows.length != 0) {
		for (var i = 0; i < rows.length; i++) {
			str += rows[i].id + ",";
		}
	}
	var url2 = "";
	if (flag == 1) {
		messager = "新增成功！";
		url2 = url + "?status=" + sid + "&rid=" + str;
	} else {
		messager = "修改成功！";
		url2 = url + "&status=" + sid + "&sid=" + str;
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
function statusRadio() {
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
					str += "<input type='radio' class='radioStyle' name='statusid' id='sId' value=\"" + result.ary[i].id + "\" />"
						+ result.ary[i].name + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				$("#radios").html(str);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
}
function AutorityDatagrid() {
	var urls = "";
	var row = $('#dg').datagrid('getSelected');
	if (flag == 1) {
		urls = "authority/getAllResource";
	} else {
		urls = "authority/getAllResource1?id=" + row.id;
	}
	$("#tt").datagrid({
		fitColumns : true,
		height : '250px',
		width : '80%',
		idField : 'roles_name',
		url : urls,
		rownumbers : false,
		showPageList : false,
		checkOnSelect : true,
		selectOnCheck : true,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : 'id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'symbol',
			title : 'symbol',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'resources_name',
			title : '资源名',
			width : 100,
			halign : "center",
			align : "center"
		} ] ],
		rowStyler : function(index, row) {
			if ((index % 2) != 0) {
				//处理行代背景色后无法选中
				var color = new Object();
				color.class = "rowColor";
				return color;
			}
		},
		onBeforeLoad : function(data) {
			$('#tt').datagrid('clearChecked');
		},
		onLoadSuccess : function(data) {
			if (data) {
				$.each(data.rows, function(index, item) {
					if (item.symbol == 1) {
						$('#tt').datagrid('checkRow', index);
					}
				})
			}
		}
	});
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