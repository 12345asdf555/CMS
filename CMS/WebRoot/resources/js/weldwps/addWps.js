/**
 * 
 */
/**
 * 
 */
$(function() {
	inscombobox();
	$('#dlg').dialog({
		onClose : function() {
			$('#insid').combobox('clear');
			$("#fm").form("disableValidation");
		}
	})
	$("#fm").form("disableValidation");
})

var url;
var flag = 1;
function addWps() {
	flag = 1;
	$("#iid").next().hide();
	$("#insid").next().show();
	$('#dlg').window({
		title : "新增工艺",
		modal : true
	});
	$('#dlg').window('open');
	$('#fm').form('clear');
	$("#iid").textbox('setValue','text');
	url = "wps/addWps";
}

function editWps() {
	flag = 2;
	$("#iid").next().show();
	$("#insid").next().hide();
	$('#fm').form('clear');
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').window({
			title : "修改工艺",
			modal : true
		});
		$('#dlg').window('open');
		$('#fm').form('load', row);
		$("#validName").val(row.fwpsnum);
		$("#iid").textbox('setValue',row.insname);
		url = "wps/updateWps?fid="+row.fid;
	}
}
//提交
function save() {
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
					if(result.msg!=null){
						$.messager.show( {title : '提示',msg : result.msg});
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


function inscombobox() {
	$.ajax({
		type : "post",
		async : false,
		url : "weldingMachine/getInsframeworkAll",
		dataType : "json",
		data : {},
		success : function(result) {
			if (result) {
				var optionStr = '';
				for (var i = 0; i < result.ary.length; i++) {
					optionStr += "<option value=\"" + result.ary[i].id + "\" >"
						+ result.ary[i].name + "</option>";
				}
				$("#insid").html(optionStr);
			} else {
				alert('部门加载失败');
			}
			$("#insid").combobox();
		},
		error : function() {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
}