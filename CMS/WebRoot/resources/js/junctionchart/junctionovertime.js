$(function(){
	junctionOvertimeDatagrid();
})

function junctionOvertimeDatagrid(){
	var parent = $("#parent").val();
	var weldtime = $("#weldtime").val();
	var time1 = $("#time1").val();
	var time2 = $("#time2").val();
	var number = $("#number").val();
	var otype = $("#otype").val();
	$("#junctionOvertimeTable").datagrid( {
		fitColumns : true,
		height : $("#body").height() - $("#junctionOvertime_btn").height()-70,
		width : $("#body").width()-40,
		idField : 'id',
		url : "junctionChart/getjunctionovertime?parent="+parent+"&otype="+otype+"&weldtime="+weldtime+"&time1="+time1+"&time2="+time2+"&number="+number,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		remoteSort : false,
		columns : [ [ {
			field : 'weldtime',
			title : '日期',
			width : 120,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'overtime',
			title : '超时待机焊机数(台)',
			width : 150,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'worktime',
			title : '待机时长(分钟)',
			width : 150,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'machineno',
			title : '设备编号',
			width : 100,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'welderno',
			title : '焊工编号',
			width : 100,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'wname',
			title : '焊工姓名',
			width : 100,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}] ],
		toolbar : '#junctionOvertime_btn',
	});
}

function sort(){
	if($("#sort").val()=="asc"){
		$("#sort").val("desc");
	}else{
		$("#sort").val("asc");
	}
	$('#junctionOvertimeTable').datagrid('load', {
		"sort" : $("#sort").val()
	});
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#junctionOvertimeTable").datagrid('resize', {
		height : $("#body").height() - $("#junctionOvertime_btn").height()-70,
		width : $("#body").width()-40
	});
}
