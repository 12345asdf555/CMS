$(function(){
	detailDatagrid();
})
var flag = 0;
function detailDatagrid(){
	var parent = $("#parent").val();
	var otype = $("#otype").val();
	var time1, time2,weldtime='';
	if(flag==0){
		weldtime = $("#weldtime").val();
		time1 = $("#time1").val();
		time2 = $("#time2").val();
		$("#dtoTime1").datetimebox('setValue',$("#time1").val());
		$("#dtoTime2").datetimebox('setValue',$("#time2").val());
	}else{
		time1 = $("#dtoTime1").datetimebox('getValue');
		time2 = $("#dtoTime2").datetimebox('getValue');
	}
	flag = 1;
	$("#dg").datagrid( {
		fitColumns : true,
		height : $("#body").height() - $("#dg_btn").height()-20,
		width : $("#body").width(),
		idField : 'id',
		url : "junctionChart/getOverproofDetail?parent="+parent+"&weldtime="+weldtime+"&time1="+time1+"&time2="+time2+"&otype="+otype,
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
			title : '项目部',
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
			title : '焊工',
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
			field : 'overproof',
			title : '超标时长(min)',
			width : 100,
			halign : "center",
			align : "center",
			formatter:function(value,row,index){
				return row.overproof;
			}
		}] ]
	});
}

function serach(){
	detailDatagrid();
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $().height() - $("#dg_btn").height(),
		width : $().width()
	});
}
