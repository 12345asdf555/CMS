$(function() {
	classifyDatagrid();
})

var chartStr = "";

var charts;
var array1 = new Array();
var array2 = new Array();
function showblocHourChart() {
	var dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	var dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	$.ajax({
		type : "post",
		async : false, //同步执行  
		url : encodeURI("blocChart/getBlocHour?dtoTime1=" + dtoTime1 + "&dtoTime2=" + dtoTime2 + chartStr),
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				for (var i = 0; i < result.rows.length; i++) {
					array1.push(result.rows[i].name);
					if (result.rows[i].jidgather == 0) {
						array2.push(0);
					} else {
						var num = (result.rows[i].manhour / result.rows[i].jidgather).toFixed(2);
						array2.push(num);
					}
				}
			}
		},
		error : function(errorMsg) {
			alert("图表请求数据失败啦!");
		}
	});
	//初始化echart实例
	charts = echarts.init(document.getElementById("blocHourChart"));
	//显示加载动画效果
	charts.showLoading({
		text : '稍等片刻,精彩马上呈现...',
		effect : 'whirling'
	});
	option = {
		tooltip : {
			trigger : 'axis' //坐标轴触发，即是否跟随鼠标集中显示数据
		},
		legend : {
			data : [ '工时(h)' ],
			x: 'left',
			left: '60'
		},
		grid : {
			left : '60', //组件距离容器左边的距离
			right : '13%',
			bottom : '20',
			containLaber : true //区域是否包含坐标轴刻度标签
		},
		toolbox : {
			feature : {
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				} //保存为图片
			},
			right : '2%',
			top:'30'
		},
		xAxis : {
			type : 'category',
			data : array1,
			name : '单位'
		},
		yAxis : {
			type : 'value', //value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name : '焊接平均时长(h)'
		},
		series : [
			{
				name : '工时(h)',
				type : 'bar',
				barMaxWidth : 20, //最大宽度
				data : array2,
				label : {
					normal : {
						position : 'top',
						show : true //显示每个折点的值
					}
				}
			}
		]
	}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();
	//重定义图表宽度
	$("#blocHourChart").width("100%");
	if(array1.length>3){
		var maxlength = array1[0];
		for(var i=0; i<array1.length; i++){
			if(array1[i].length>maxlength.length){
				maxlength = array1[i];
			}
		}
		var width = array1.length * maxlength.length * 18;//最长组织机构名字每个字节算18px
		if($("#blocHourChart").width()<width){
			$("#blocHourChart").width(width);
		}
	}
	echarts.init(document.getElementById('blocHourChart')).resize();
}

function BlocHourDatagrid() {
	var dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	var dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	$("#blocHourTable").datagrid({
		fitColumns : true,
		height : $("#bodydiv").height() - $("#blocHourChart").height() - $("#blocHour_btn").height() - 15,
		width : $("#bodydiv").width(),
		idField : 'id',
		url : "blocChart/getBlocHour?dtoTime1=" + dtoTime1 + "&dtoTime2=" + dtoTime2 + chartStr,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'name',
			title : '公司',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value, row, index) {
				return '<a href="companyChart/goCompanyHour?parent=' + row.companyid + "&parentime1=" + dtoTime1 + "&parentime2=" + dtoTime2 + '">' + value + '</a>';
			}
		}, {
			field : 'jidgather',
			title : '焊口数量',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'manhour',
			title : '焊接平均工时(h)',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value, row, index) {
				if (row.jidgather == 0) {
					return 0;
				}
				return (value / row.jidgather).toFixed(2);
			}
		}, {
			field : 'dyne',
			title : '达因',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'companyid',
			title : '公司id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		} ] ]
	});
}

function classifyDatagrid() {
	$("#classify").datagrid({
		fitColumns : true,
		height : $("#classifydiv").height(),
		width : $("#bodydiv").width() / 2,
		idField : 'fid',
		url : "blocChart/getBlocHousClassify",
		singleSelect : true,
		pageSize : 5,
		pageList : [ 5, 10, 15, 20, 25 ],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'fid',
			hidden : true
		}, {
			field : 'material',
			title : '上游材质',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'nextmaterial',
			title : '下游材质',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'wall_thickness',
			title : '上游璧厚',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'nextwall_thickness',
			title : '下游璧厚',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'external_diameter',
			title : '上游外径',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'nextExternal_diameter',
			title : '下游外径',
			width : 100,
			halign : "center",
			align : "center"
		} ] ],
		toolbar : '#classify_btn',
		onLoadSuccess : function() {
			$("#classify").datagrid("clearChecked");
			$("#classify").datagrid("selectRow", 0);
			array1 = new Array();
			array2 = new Array();
			BlocHourDatagrid();
			showblocHourChart();
		}
	});
}

function commitChecked() {
	chartStr = "";
	search = "";
	array1 = new Array();
	array2 = new Array();
	$("#chartLoading").show();
	var rows = $("#classify").datagrid("getSelected");
	search += " (fmaterial='" + rows.material + "' and fexternal_diameter='" + rows.external_diameter + "' and fwall_thickness='" + rows.wall_thickness + "' and fnextExternal_diameter='" + rows.nextExternal_diameter +
		"' and fnextwall_thickness ='" + rows.nextwall_thickness + "' and fnext_material ='" + rows.nextmaterial + "')";
	chartStr += "&search=" + search;
	setTimeout(function() {
		BlocHourDatagrid();
		showblocHourChart();
	}, 500);
}

function serachblocHour() {
	commitChecked();
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#blocHourTable").datagrid('resize', {
		height : $("#bodydiv").height() - $("#blocHourChart").height() - $("#blocHour_btn").height() - 15,
		width : $("#bodydiv").width()
	});
	$("#classify").datagrid('resize', {
		height : $("#classifydiv").height(),
		width : $("#bodydiv").width() / 2
	});
	echarts.init(document.getElementById('blocHourChart')).resize();
}