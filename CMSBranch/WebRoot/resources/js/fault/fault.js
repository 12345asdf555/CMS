$(function(){
  dgDatagrid();
});
function dgDatagrid() {
	$("#dg").datagrid({
		fitColumns : true,
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "fault/getFaultList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'machineno',
			title : '固定资产编号 ',
			width : 120,
			halign : "center",
			align : "left"
		}, {
			field : 'code',
			title : '故障代码 ',
			width : 120,
			halign : "center",
			align : "left"
		}, {
			field : 'type',
			title : '故障类别',
			width : 120,
			halign : "center",
			align : "left"
		}, {
			field : 'time',
			title : '录入时间',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'typeid',
			title : '故障类别id',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'codeid',
			title : '故障代码id',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'machineid',
			title : '焊机id',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'itemid',
			title : '组织机构id',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'edit',
			title : '编辑',
			width : 150,
			halign : "center",
			align : "left",
			formatter : function(value, row, index) {
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="javascript:editFault();"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeFault();"/>';
				return str;
			}
		} ] ],
		toolbar : '#dg_btn',
		pagination : true,
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