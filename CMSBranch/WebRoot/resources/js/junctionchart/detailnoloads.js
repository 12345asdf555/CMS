$(function(){
	detailnoloadsDatagrid();
})

function detailnoloadsDatagrid(){
	var parent = $("#parent").val();
	var weldtime = $("#weldtime").val();
	var time1 = $("#time1").val();
	var time2 = $("#time2").val();
	var otype = $("#otype").val();
	$("#detailNoLoadsTable").datagrid( {
		fitColumns : true,
		height : $("#body").height() - $("#detailNoLoad_btn").height()-70,
		width : $("#body").width()-40,
		idField : 'id',
		url : "junctionChart/getDetailNoLoads?parent="+parent+"&weldtime="+weldtime+"&time1="+time1+"&time2="+time2+"&otype="+otype,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'name',
			title : '项目部',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'machineno',
			title : '设备编号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'loads',
			title : '空载率',
			width : 200,
			halign : "center",
			align : "center",
		}, {
			field : 'weldtime',
			title : '日期',
			width : 150,
			halign : "center",
			align : "center",
		}] ],
		toolbar : '#detailNoLoad_btn',
	});
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#detailNoLoadsTable").datagrid('resize', {
		height : $("#body").height() - $("#detailNoLoad_btn").height()-70,
		width : $("#body").width()-40
	});
}
