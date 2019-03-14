var chartStr = "";
$(function(){
	$.date('#androidtime1');
	$.date('#androidtime2');
})
$(document).ready(function(){
	showCompanyLoadsChart();
})

function setParam(){
	var parent = $("#parent").val();
	var status = $("#status").val();
	var dtoTime1 = $("#androidtime1").val();
	var dtoTime2 = $("#androidtime2").val();
	chartStr = "?parent="+parent+"&dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2+"&status="+status;
}

var charts;
var array1 = new Array();
var array2 = new Array();
var Series = [];
function showCompanyLoadsChart(){
	setParam();
   	//初始化echart实例
	charts = echarts.init(document.getElementById("companywmlistChart"));
	//显示加载动画效果
	charts.showLoading({
		text: '稍等片刻,精彩马上呈现...',
		effect:'whirling'
	});
	option = {
		title:{
			text: ""//标题
		},
		tooltip:{
			trigger: 'axis',//坐标轴触发，即是否跟随鼠标集中显示数据
			formatter: function (params, ticket, callback) {
			    	return params[0].seriesName+"<br/>"+params[0].marker+params[0].name+":  "+params[0].value;
			}
		},
		legend:{
			data:['焊工工时(h)']
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'100',
			bottom:'20',
			containLaber:true//区域是否包含坐标轴刻度标签
		},
		toolbox:{
			feature:{
				/*dataView : {show: true, readOnly: false},*/
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true}/*,
	            saveAsImage : {show: true}//保存为图片
*/			},
			right:'2%'
		},
		xAxis:{
			type:'category',
			data: array1,
			name: '焊工编号'
		},
		yAxis:{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name: '工时(h)'
		},
		series:[{
     		name :'焊工工时(h)',
     		type :'bar',//柱状图
            barMaxWidth:20,//最大宽度
     		data :array2
		},{
     		name :'焊工工时(h)',
     		type :'line',//折线图
     		data :array2,
            barMaxWidth:20,//最大宽度
     		itemStyle : {
     			normal: {
                    color:'#000000',  //折点颜色
     				label : {
     					show: true//显示每个折点的值
     				},
     				lineStyle:{  
                        color:'#000000'  //折线颜色
                    }  
     			}
     		}
     	}]
	}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
}


function getData(){
	setParam();
	$.ajax({
		type : "post",
		async : false,
		url : "android/getWelderList" + chartStr,
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				for (var i = 0; i < result.rows.length; i++) {
					array1.push(result.rows[i].equipment);
                  	array2.push(result.rows[i].num.toFixed(2));
				}
			}
		},
		error : function(errorMsg) {
			alert("请求数据失败啦,请联系系统管理员!");
		}
	});
	//更新数据
    var option = charts.getOption();
    option.series[0].data = array2;
    option.series[1].data = array2;
    option.xAxis[0].data = array1;
    charts.setOption(option);
	$("#chartLoading").hide();
}


function serachlist(){
	$("#chartLoading").show();
	Series = [];
	array1 = new Array();
	array2 = new Array();
	chartStr = "";
	getData();
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	echarts.init(document.getElementById('companywmlistChart')).resize();
}
