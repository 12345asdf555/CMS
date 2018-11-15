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
	$("#dg").datagrid( {
		fitColumns : true,
		height : $("#body").height() - $("#junctionOvertime_btn").height()-40,
		width : $("#body").width(),
		idField : 'id',
		url : "junctionChart/getNewOvertimeDetail?parent="+parent+"&otype="+otype+"&weldtime="+weldtime+"&time1="+time1+"&time2="+time2+"&number="+number,
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
			align : "center"
		}, {
			field : 'itemname',
			title : '所属部门',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'overtime',
			title : '待机时长(分钟)',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'welderno',
			title : '焊工编号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'wname',
			title : '焊工姓名',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'machineno',
			title : '焊机编号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'junctionno',
			title : '焊口编号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'starttime',
			title : '开始时间',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'endtime',
			title : '结束时间',
			width : 150,
			halign : "center",
			align : "center"
		}] ],
		toolbar : '#junctionOvertime_btn',
	});
}

function goback(){
	var img = new Image();
    img.src = "blocChart/goNewOvertime?parent="+$("#parent").val();  // 设置相对路径给Image, 此时会发送出请求
    urls = img.src;  // 此时相对路径已经变成绝对路径
    img.src = null; // 取消请求
	window.location.href = encodeURI(urls);
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#body").height() - $("#junctionOvertime_btn").height()-70,
		width : $("#body").width()-40
	});
}
