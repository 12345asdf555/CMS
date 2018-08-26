$(function(){
	detailloadsDatagrid();
})

function detailloadsDatagrid(){
	var id = $("#id").val();
	var type = $("#type").val();
	var time1 = $("#time1").val();
	var time2 = $("#time2").val();
	$("#dg").datagrid( {
		fitColumns : true,
		height : $("#body").height()-50,
		width : $("#body").width()-40,
		idField : 'id',
		url : "junctionChart/getUseDetail?id="+id+"&type="+type+"&time1="+time1+"&time2="+time2,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'name',
			title : '厂商名称',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'machineno',
			title : '设备编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'type',
			title : '类型',
			width : 100,
			halign : "center",
			align : "left",
		}, {
			field : 'time',
			title : '时长(h)',
			width : 100,
			halign : "center",
			align : "left",
		}] ]
	});
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#body").height()-50,
		width : $("#body").width()-40,
	});
}
