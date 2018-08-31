$(function(){
	dgDatagrid();
});

function dgDatagrid(){
	$("#dg").datagrid( {
		fitColumns : true,
		height : $("#body").height()-45,
		width : $("#body").width()-30,
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "manufacturer/getManufacturerList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 100,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'name',
			title : '生产厂商 ',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'creator',
			title : '创建者 ',
			width : 100,
			halign : "center",
			align : "center",
			hidden:	true
		},{
			field : 'manutype',
			title : '厂商类型',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'type',
			title : '厂商类型id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'typeValue',
			title : '厂商类型值',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'edit',
			title : '编辑',
			width : 130,
			halign : "center",
			align : "center",
			formatter:function(value,row,index){
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="javascript:editManufacturer();"/>';
				//manufacturer/goeditManufacturer?id='+row.id+'
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeManufacturer();"/>';
				return str;
			}
		}] ],
		toolbar : '#dg_btn',
		pagination : true,
		onLoadSuccess:function(data){
			$("a[id='ok']").linkbutton({text:'完成',plain:true,iconCls:'icon-ok'});
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
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

