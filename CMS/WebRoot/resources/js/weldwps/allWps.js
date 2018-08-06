$(function() {
	$("#dg").datagrid({
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "wps/getAllWps",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		showPageList : false,
		columns : [ [ {
			field : 'fid',
			title : 'FID',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'fwpsnum',
			title : '工艺编号',
			width : 60,
			halign : "center",
			align : "left"
		}, {
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
			align : "left"
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
			field : 'fweld_alter_i',
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
		}, {
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
		}, {
			field : 'fname',
			title : '工艺参数名称',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'edit',
			title : '编辑',
			width : 130,
			halign : "center",
			align : "left",
			formatter : function(value, row, index) {
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="javascript:editWps()"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeWps()"/>';
				return str;
			}
		} ] ],
		toolbar : '#dg_btn',
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

})


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