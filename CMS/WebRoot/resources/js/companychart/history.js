$(document).ready(function() {
	showOverproof();
})
var chartStr = "";
function setParam() {
	var dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	var dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "&dtoTime1=" + dtoTime1 + "&dtoTime2=" + dtoTime2;
}
var charts,
	flagnum = 0;
function showOverproof() {
	setParam();
	var array1 = new Array();
	var array2 = new Array();
	var array3 = new Array();
	var Series = [];
	$.ajax({
		type : "post",
		async : false, //同步执行  
		url : "companyChart/gethistory?id=" + $("#id").val() + chartStr,
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				for (var i = 0; i < result.rows.length; i++) {
					array1.push(result.rows[i].weldtime1 + "\n" + result.rows[i].weldtime2);
					array2.push(result.rows[i].electricity);
					array3.push(result.rows[i].fvoltage);
				}
			}
		},
		error : function(errorMsg) {
			alert("图表请求数据失败啦!");
		}
	});
	if (flagnum == 0) {
		flagnum = 1;
		//初始化echart实例
		charts = echarts.init(document.getElementById("overproof"));
	}
	var option = {
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '焊机电流', '焊机电压' ],
			x : 'left',
			left : '6%'
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : false
				},
				dataView : {
					show : false,
					readOnly : false
				},
				restore : {
					show : false
				}
			}
		},
		dataZoom : [//表格数据量太大处理方式,框选缩放
			{
				type : 'slider',
				show : true,
				xAxisIndex : [ 0 ]
			},
			{
				type : 'inside',
				xAxisIndex : [ 0 ]
			}
		],
		grid : {
			left : '8%', //组件距离容器左边的距离
			right : '5%',
			top : "5%",
			bottom : 60
		},
		xAxis : [ {
			type : 'category',
			data : array1
		} ],
		yAxis : [ {
			type : 'value',
			max : 500,
			min : 0
		} ],
		series : [ {
			name : '焊机电流',
			type : 'line', //折线图
			data : array2
		}, {
			name : '焊机电压',
			type : 'line', //折线图
			data : array3
		} ]
	};
	//为echarts对象加载数据
	charts.setOption(option);
}

function serach() {
	showOverproof();
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变图表高宽
function domresize() {
	charts.resize();
}