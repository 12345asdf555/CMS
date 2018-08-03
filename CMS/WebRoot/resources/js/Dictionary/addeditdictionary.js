$(function() {
	typeidCombobox();
	DictionaryDataGrid();
	$('#dlg').dialog({
		onClose : function() {
			$('#typeid').combobox('clear');
			$("#typeid").combobox({disabled:false});
			$("#fm").form("disableValidation");
		}
	})
	$("#typeid").combobox({
		onChange : function(newValue,oldValue){
			if(editflag){
				if(newValue==10 || newValue==8){
//					var row = $("#dg").datagrid('getSelected');
//					if(row){
//						if(row.typeid != newValue){
							$("#typeid").combobox("setValue",oldValue);
							alert("该类型不支持新增/修改");
//						}
//					}else{
//						$("#typeid").combobox("setValue",oldValue);
//						alert("该类型不支持新增/修改");
//					}
				}
			}
		}
	})
	$("#fm").form("disableValidation");

});

var url = "";
var flag = 1;
function addDictionary() {
	flag = 1;
	$('#dlg').window({
		title : "新增字典",
		modal : true
	});
	$('#dlg').window('open');
	$('#fm').form('clear');
	url = "Dictionary/addDictionary";
}
var editflag = true;
function editDictionary() {
	flag = 2;
	editflag = false;
	$('#fm').form('clear');
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		if(row.typeid == 8 || row.typeid == 10){
			$("#typeid").combobox({disabled:true});
		};
		$('#dlg').window({
			title : "修改字典",
			modal : true
		});
		$('#dlg').window('open');
		$('#fm').form('load', row);
		url = "Dictionary/editDictionary?id=" + row.id + "&value=" + row.typeid;
		editflag = true;
	}
}
function removeDictionary() {
	$('#rfm').form('clear');
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#rdlg').window({
			title : "删除字典",
			modal : true
		});
		$('#rdlg').window('open');
		$('#rfm').form('load', row);
		url = "Dictionary/deleteDictionary?id=" + row.id;
	}
}
//提交
function save() {
	var url2 = "";
	var back=$("#typeid").combobox('getText');
	if (flag == 1) {
		messager = "新增成功！";
		url2 = url + "?back=" + back;
	} else {
		messager = "修改成功！";
		url2 = url + "&back=" + back;
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

function typeidCombobox() {
	$.ajax({
		type : 'post',
		async : false,
		dataType : 'json',
		url : 'Dictionary/getBack',
		success : function(result) {
			var str = "";
			for (var i = 0; i < result.ary.length; i++) {
				str += "<option value=\"" + result.ary[i].typeid + "\">" + result.ary[i].back + "</option>";
			}
			$("#typeid").html(str);
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	})
	$("#typeid").combobox();
}


function remove() {
	var id = $("#id").val();
	$.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
		if (flag) {
			$.ajax({
				type : "post",
				async : false,
				url : "Dictionary/deleteDictionary?id=" + id,
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
function searchDic() {
	var cols = $("#fields").combobox("getValue");
	var content = $("#content").val();
	var searchStr = cols + " like '%" + content + "%'";
	$('#dg').datagrid('load', {
		"searchStr" : searchStr
	})
}
