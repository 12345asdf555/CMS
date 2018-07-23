$(function(){
	typeCombobox();
	//equipmentCombobox();
	updatetext();
	$('#dlg').dialog( {
		onClose : function() {
			$('#typeId').combobox('clear');
			$("#fm").form("disableValidation");
		}
	})
	$("#fm").form("disableValidation");
})

var url = "";
var maintainfalg = true;
function addMaintain(){
	maintainfalg = true;
	$("#selectMachine").show();
	$('#dlg').window( {
		title : "新增维修记录",
		modal : true
	});
	$('#dlg').window('open');
	$('#fm').form('clear');
	url = "maintain/addMaintain";
	//saveMaintain();
}

function editMaintain(){
	maintainfalg = false;
	$("#selectMachine").hide();
	$('#fm').form('clear');
	var row = $('#maintainTable').datagrid('getSelected');
	if (row) {
		$('#dlg').window( {
			title : "修改维修记录",
			modal : true
		});
		$('#dlg').window('open');
		$('#fm').form('load', row);
	}
	url = "maintain/editMaintain";
//	saveMaintain();
}
//提交
function saveMaintain(){
	var tid = $("#typeId").combobox('getValue');
	var url2 = "";
	if(maintainfalg){
		messager = "新增成功！";
		url2 = url+"?tId="+tid+"&wId="+$("#machineid").val();
	}else{
		messager = "修改成功！";
		url2 = url+"?tId="+tid+"&wId="+$("#machineid").val();
	}
	$('#fm').form('submit', {
		url : url2,
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(result) {
			if(result){
				var result = eval('(' + result + ')');
				if (!result.success) {
					$.messager.show( {
						title : 'Error',
						msg : result.errorMsg
					});
				} else {
					
						if(result.msg==null){
							$.messager.alert("提示", messager);
						}else{
							$.messager.show( {title : '提示',msg : result.msg});
						}
						$('#dlg').dialog('close');
						$('#maintainTable').datagrid('reload');
				}
			}
			
		},  
	    error : function(errorMsg) {  
	        alert("数据请求失败，请联系系统管理员!");  
	    } 
	});
}

function updatetext(){
	//隐藏文本框
	$("#type").next().hide();
	var type = $("#type").val();
	$("#typeId").combobox('select',type);
}

//维修类型
function typeCombobox(){
	$.ajax({  
        type : "post",  
        async : false,
        url : "maintain/getComboboxValue",  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {
            if (result) {
                var optionStr = '';  
                for (var i = 0; i < result.ary2.length; i++) {  
                    optionStr += "<option value=\"" + result.ary2[i].typeid + "\" >"  
                            + result.ary2[i].typename + "</option>";  
                }  
                $("#typeId").html(optionStr);
            }  
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
   }); 
	$("#typeId").combobox();
}

function selectMachine(){
	$('#fdlg').window( {
		title :"固定资产编号",
		modal : true
	});
	$('#fdlg').window('open');
	weldingMachineDatagrid();
}
function weldingMachineDatagrid(){
	$("#weldingmachineTable").datagrid( {
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
		    field:'ck',
			checkbox:true
		},{
			field : 'id',
			title : '序号',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'equipmentNo',
			title : '固定资产编号',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'typeName',
			title : '设备类型',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'insframeworkName',
			title : '所属项目',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'statusName',
			title : '状态',
			width : 80,
			halign : "center",
			align : "left"
		} , {
			field : 'manufacturerName',
			title : '厂家',
			width : 150,
			halign : "center",
			align : "left"
		}
		] ],
		toolbar:'#dlgSearch',
		pagination : true,
		fitColumns : true
	});
}

function saveWeldingMachine(){
	var row = $("#weldingmachineTable").datagrid('getSelected');
	$("#machineno").textbox('setValue',row.equipmentNo);
	$("#machineid").val(row.id);
	$('#fdlg').dialog('close');
}

function dlgSearchMachine(){
	var searchStr = "";
	if($("#searchname").val()){
		searchStr =  "fequipment_no like '%"+$("#searchname").val()+"%'";
	}
	$('#weldingmachineTable').datagrid('load', {
		"searchStr" : searchStr
	});
	
}