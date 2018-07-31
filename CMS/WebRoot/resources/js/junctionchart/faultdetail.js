$(function(){
	flag = 1;
	typeCombobox();
	dgDatagrid();
})
var flag = 0;
function dgDatagrid(){
	var typeid;
	if(flag==1){
		typeid = $("#type").val();
	}else{
		typeid = $("#typeid").combobox("getValue");
	}
	flag = 0;
	var parent = $("#parent").val();
	var time1 = $("#dtoTime1").datetimebox("getValue");
	var time2 = $("#dtoTime2").datetimebox("getValue");
	var otype = $("#otype").val();
	$("#dg").datagrid( {
		fitColumns : true,
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		url : "junctionChart/getFaultDetail?parent="+parent+"&typeid="+typeid+"&time1="+time1+"&time2="+time2,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'type',
			title : '故障类型',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'machineno',
			title : '故障机器',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'total',
			title : '故障次数',
			width : 100,
			halign : "center",
			align : "left",
		}, {
			field : 'time',
			title : '故障时间',
			width : 100,
			halign : "center",
			align : "left",
		}, {
			field : 'itemname',
			title : '所属项目部',
			width : 100,
			halign : "center",
			align : "left",
		}] ],
		toolbar : '#dg_btn',
	});
}

//故障类型
function typeCombobox() {
	$.ajax({
		type : "post",
		async : false,
		url : "fault/getTypeAll?num=7",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				var optionStr = '';
				for (var i = 0; i < result.ary.length; i++) {
					optionStr += "<option value=\"" + result.ary[i].id + "\" >"
						+ result.ary[i].name + "</option>";
				}
				$("#typeid").html(optionStr);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
	$("#typeid").combobox();
	$("#typeid").combobox("setValue",$("#type").val());
}

function serach(){
	dgDatagrid();
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#body").height() - $("#detailLoad_btn").height()-30,
		width : $("#body").width()
	});
}
