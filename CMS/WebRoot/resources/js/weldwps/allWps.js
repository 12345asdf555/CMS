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
			align : "center",
			hidden : true
		}, {
			field : 'fwpsnum',
			title : '工艺编号',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value,row,index){
				return "<a href='wps/goShowWps?id="+row.fid+"'>"+value+"</a>";
			}
		}, {
			field : 'fversions',
			title : '版本',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'fproject_code',
			title : '焊工考试项目代号',
			width : 600,
			halign : "center",
			align : "center"
		}, {
			field : 'edit',
			title : '编辑',
			width : 130,
			halign : "center",
			align : "center",
			formatter : function(value, row, index) {
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="wps/goEditWps?id='+row.fid+'"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="wps/goRemoveWps?id='+row.fid+'"/>';
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