$(function(){
	junctionOvertimeDatagrid();
})

function junctionOvertimeDatagrid(){
	var parent = $("#parent").val();
	var weldtime = $("#weldtime").val();
	var time1 = $("#time1").val();
	var time2 = $("#time2").val();
	var number = $("#number").val();
	$("#junctionOvertimeTable").datagrid( {
		fitColumns : true,
		height : $("#body").height() - $("#junctionOvertime_btn").height()-30,
		width : $("#body").width(),
		idField : 'id',
		url : "junctionChart/getjunctionovertime?parent="+parent+"&weldtime="+weldtime+"&time1="+time1+"&time2="+time2+"&number="+number,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'weldtime',
			title : '日期',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'overtime',
			title : '超时待机焊机数(台)',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'worktime',
			title : '超时待机时长(h)',
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
			field : 'welderno',
			title : '焊工编号',
			width : 100,
			halign : "center",
			align : "left",
		}, {
			field : 'wname',
			title : '焊工姓名',
			width : 100,
			halign : "center",
			align : "left",
		}] ],
		toolbar : '#junctionOvertime_btn',
	});
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#junctionOvertimeTable").datagrid('resize', {
		height : $("#body").height() - $("#junctionOvertime_btn").height()-30,
		width : $("#body").width()
	});
}
