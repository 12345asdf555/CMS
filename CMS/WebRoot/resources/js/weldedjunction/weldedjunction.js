$(function(){
	insframeworkTree();
	weldedJunctionDatagrid();
	$("#itemid").combobox({
		onChange : function(){
			var no = $("#weldedJunctionno").val();
			$("#weldedJunctionno").textbox('setValue',no);//组织机构发生变化时重新修改焊口编号，触发约束
		}
	})
});

function weldedJunctionDatagrid(){
	$("#weldedJunctionTable").datagrid( {
		fitColumns : true,
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "weldedjunction/getWeldedJunctionList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 30,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'weldedJunctionno',
			title : '编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'serialNo',
			title : '序列号',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'pipelineNo',
			title : '管线号',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'roomNo',
			title : '房间号',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'roomNo',
			title : '房间号',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'itemid',
			title : '组织机构id',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'area',
			title : '区域',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'systems',
			title : '系统',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'children',
			title : '子项',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'externalDiameter',
			title : '上游外径',
			width : 60,
			halign : "center",
			align : "left"
		}, {
			field : 'nextexternaldiameter',
			title : '下游外径',
			width : 60,
			halign : "center",
			align : "left"
		}, {
			field : 'wallThickness',
			title : '上游壁厚',
			width : 60,
			halign : "center",
			align : "left"
		}, {
			field : 'nextwall_thickness',
			title : '下游璧厚',
			width : 60,
			halign : "center",
			align : "left"
		}, {
			field : 'material',
			title : '上游材质',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'next_material',
			title : '下游材质',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'dyne',
			title : '达因',
			width : 50,
			halign : "center",
			align : "left"
		}, {
			field : 'specification',
			title : '规格',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'maxElectricity',
			title : '电流上限',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'minElectricity',
			title : '电流下限',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'maxValtage',
			title : '电压上限',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'minValtage',
			title : '电压下限',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'itemname',
			title : '所属项目',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'startTime',
			title : '开始时间',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'endTime',
			title : '完成时间',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'creatTime',
			title : '创建时间',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'updateTime',
			title : '修改时间',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'updatecount',
			title : '修改次数',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'valtage_unit',
			title : '电压单位',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'electricity_unit',
			title : '电流单位',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'edit',
			title : '编辑',
			width : 250,
			halign : "center",
			align : "left",
			formatter: function(value,row,index){
				var str = '<a id="edit" class="easyui-linkbutton" href="javascript:editWeldedjunction()"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeWeldedjunction()"/>';
				str += '<a id="look" class="easyui-linkbutton" href="javascript:showMore()"/>';
				return str;
			}
		}] ],
		toolbar : '#disctionaryTable_btn',
		pagination : true,
		onLoadSuccess: function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
			$("a[id='look']").linkbutton({text:'查看更多',plain:true,iconCls:'icon-add'});
		}
	});
}

var searchStr;
function wpdDatagrid(){
	$("#wpsdiv").window({
		modal : true
	});
	$("#wpsdiv").window('open');
	var url = "wps/getAllWps?parent=" + $("#itemid").combobox('getValue') + "&searchStr=" + encodeURI(searchStr);
	$("#wpsdg").datagrid({
		height : $("#wpsdiv").height(),
		width : $("#wpsdiv").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : url,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		showPageList : false,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'fid',
			title : 'FID',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'fwpsnum',
			title : '工艺编号',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'fname',
			title : '工艺参数名称',
			width : 100,
			halign : "center",
			align : "left"
		/*}, {
			field : 'fweld_i',
			title : '标准焊接电流',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'fweld_v',
			title : '标准焊接电压',
			width : 80,
			halign : "center",
			align : "left"*/
		}, {
			field : 'fweld_i_max',
			title : '最大焊接电流',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'fweld_i_min',
			title : '最小焊接电流',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'fweld_v_max',
			title : '最大焊接电压',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'fweld_v_min',
			title : '最小焊接电压',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			/*field : 'fweld_alter_i',
			title : '报警电流',
			width : 60,
			halign : "center",
			align : "left"
		}, {
			field : 'fweld_alter_v',
			title : '报警电压',
			width : 60,
			halign : "center",
			align : "left"
		}, {
			field : 'fdiameter',
			title : '焊丝直径',
			width : 60,
			halign : "center",
			align : "left"
		}, {
			field : 'fweld_prechannel',
			title : '预置通道',
			width : 60,
			halign : "center",
			align : "left"
		}, {*/
			field : 'insname',
			title : '部门',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'insid',
			title : '部门id',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'fback',
			title : '备注',
			width : 120,
			halign : "center",
			align : "left"
		} ] ],
		toolbar : '#fdlgSearch',
		onLoadSuccess : function(data){
			$("#wpsdg").datagrid('selectRow',0);
		}
	});
}

function saveWpd(){
	var row = $("#wpsdg").datagrid('getSelected');
	$("#maxElectricity").numberbox('setValue',row.fweld_i_max);
	$("#minElectricity").numberbox('setValue',row.fweld_i_min);
	$("#maxValtage").numberbox('setValue',row.fweld_v_max);
	$("#minValtage").numberbox('setValue',row.fweld_v_min);
	$('#wpsdiv').dialog('close');
}

function dlgSearchWPS() {
	if ($("#searchname").val()) {
		searchStr = " FWPSNum like '%"+$("#searchname").val()+"%'";
	}
	wpdDatagrid();
}

function showMore(){
	var row = $('#weldedJunctionTable').datagrid('getSelected');
	if (row) {
		$('#moredlg').window( {
			title : "查看更多",
			modal : true
		});
		$('#moredlg').window('open');
		$('#showfm').form('load', row);
	}
}

function closeshowmore(){
	$('#moredlg').window('close');
}

//树形菜单点击事件
function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			$("#weldedJunctionTable").datagrid('load',{
				"parent" : node.id
			})
		 }
	})
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#weldedJunctionTable").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}

