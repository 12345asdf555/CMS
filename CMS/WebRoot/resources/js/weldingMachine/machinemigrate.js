$(function(){
	insframeworkTree();
	weldingMachineDatagrid();
	InsframeworkCombobox();
	$('#dlg').dialog( {
		onClose : function() {
			$('#iId').combobox('clear');
		}
	})
});

function weldingMachineDatagrid(){
	$("#weldingmachineTable").datagrid( {
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "weldingMachine/getWedlingMachineList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false, 
        columns : [ [ {
			field : 'id',
			title : '序号',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'equipmentNo',
			title : '固定资产编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'typeName',
			title : '设备类型',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'jointime',
			title : '入厂时间',
			width : 200,
			halign : "center",
			align : "left"
		}, {
			field : 'insframeworkName',
			title : '所属项目',
			width : 100,
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
		}, {
			field : 'isnetworking',
			title : '是否在网',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'gatherNo',
			title : '采集序号',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'position',
			title : '位置',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'money',
			title : '价值',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'statusId',
			title : '状态id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'isnetworkingId',
			title : '是否联网id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'gatherId',
			title : '采集id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'manufacturerId',
			title : '厂商id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'typeId',
			title : '类型id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'insframeworkId',
			title : '项目id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'edit',
			title : '编辑',
			width : 100,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
				var str = '<a id="edit" class="easyui-linkbutton" href="javascript:openEditDialog()"/>';
				return str;
			}
		}] ],
		toolbar : '#weldingmachineTable_btn',
		pagination : true,
		fitColumns : true,
		onLoadSuccess:function(data){
	        $("a[id='edit']").linkbutton({text:'迁移',plain:true,iconCls:'icon-edit'});
		}
	});
}
var url ;
function openEditDialog(){
	$('#fm').form('clear');
	var row = $('#weldingmachineTable').datagrid('getSelected');
	if (row) {
		$('#dlg').window( {
			title : "迁移焊机设备",
			modal : true
		});
		$('#dlg').window('open');
		$("#iId").combobox('select',row.insframeworkId);
		$('#valideno').val(row.equipmentNo);
		$('#validgid').val(row.gatherId);
		$('#validinsf').val(row.iId);
		$('#fm').form('load', row);
		url = "weldingMachine/migrateWeldingMachine?wid="+row.id;
	}
}

//提交
function saveWeldingMachine(){
	$('#fm').form('submit', {
		url : url,
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
				}else{
					var time = 500;
					if(result.msg==null){
						$.messager.alert("提示", "迁移成功！");
					}else{
						time = 2500;
						$.messager.show( {title : '提示',msg : result.msg});
					}
					window.setTimeout(function() {
						$('#dlg').dialog('close');
						$('#weldingmachineTable').datagrid('reload');
						$("#valideno").val("");
						$("#validgid").val("");
						$("#valideid").val("");
					}, time);
				}
			}
		},  
	    error : function(errorMsg) {  
	        alert("数据请求失败，请联系系统管理员!");  
	    } 
	});
}


//所属项目
function InsframeworkCombobox(){
	$.ajax({  
    type : "post",  
    async : false,
    url : "weldingMachine/getInsframeworkAll",  
    data : {},  
    dataType : "json", //返回数据形式为json  
    success : function(result) {  
        if (result) {
            var optionStr = '';
            for (var i = 0; i < result.ary.length; i++) {  
                optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                        + result.ary[i].name + "</option>";
            }
            $("#iId").html(optionStr);
        }  
    },  
    error : function(errorMsg) {  
        alert("数据请求失败，请联系系统管理员!");  
    }  
	}); 
	$("#iId").combobox();
}


function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			$("#weldingmachineTable").datagrid('load',{
				"parent" : node.id
			})
			$("#treeid").val(node.id);
		 }
	})
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#weldingmachineTable").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}

