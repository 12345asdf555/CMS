$(function(){
	itemjunctionDatagrid();
})

function setParam(){
	var item = $("#item").val();
	var material = $("#material").val();
	var externalDiameter = $("#externalDiameter").val();
	var wallThickness = $("#wallThickness").val();
	var nextexternaldiameter = $("#nextexternaldiameter").val();
	var nextmaterial = $("#nextmaterial").val();
	var nextwallthickness = $("#nextwallthickness").val();
	var dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	var dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	var time1 = $("#parentime1").val();
	var time2 = $("#parentime2").val();
	chartStr = "?item="+item+"&material="+encodeURI(material)+"&externalDiameter="+encodeURI(externalDiameter)+"&wallThickness="+encodeURI(wallThickness)+"&nextexternaldiameter="+encodeURI(nextexternaldiameter)+"&nextmaterial="+encodeURI(nextmaterial)+"&nextwallthickness="+encodeURI(nextwallthickness)+"&dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2+"&time1="+time1+"&time2="+time2;
}

function itemjunctionDatagrid(){
	setParam();
	var item = $("#item").val();
	$("#junctionHourTable").datagrid( {
		fitColumns : true,
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		url : "junctionChart/getJunctionHour"+chartStr,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'iname',
			title : '项目部',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'name',
			title : '焊口',
			width : 100,
			halign : "center",
			align : "left",
			formatter: function(value,row,index){
				return '<a href="junctionChart/goJunctionDetail?id='+row.itemid+'&item='+item+'">'+value+'</a>';
			}
		}, {
			field : 'manhour',
			title : '焊接工时(s)',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'dyne',
			title : '达因',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'starttime',
			title : '开始时间',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'endtime',
			title : '完成时间',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'welder',
			title : '焊工',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'itemid',
			title : '焊口id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}] ],
		toolbar : '#junctionHour_btn',
	});
}

function serachjunctionHour(){
	chartStr = "";
	$("#parentime1").val("");
	$("#parentime2").val("");
	itemjunctionDatagrid();
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#junctionHourTable").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}
