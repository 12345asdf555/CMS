$(document).ready(function(){
	showOverproof();
})

function showOverproof(){
	var array1 = new Array();
	var array2 = new Array();
	var array3 = new Array();
	var weldtime = $("#weldtime").val();
	array1= weldtime.split(",");
	array2.push("焊工："+$("#welder").val()+",焊口："+$("#junction").val());
	array3 = $("#electricitys").val().split(",");
	var Series = [];
 	Series.push({
 		name : array2,
 		type :'line',//折线图
 		data : array3,
        markLine: {
            data: [
                {yAxis: $("#maxelectricity").val(), name: '最大电流'},
                {yAxis: $("#minelectricity").val(), name: '最小电流'}
            ]
        } ,
 		itemStyle : {
 			normal: {
 				label : {
 					show: true,//显示每个折点的值
 					formatter: function(param){
 						if(param.value==0){
 							return "正常";
 						}
 					}
 				}
 			}
 		}
 	});
   	//初始化echart实例
	charts = echarts.init(document.getElementById("overproof"));
	//显示加载动画效果
	charts.showLoading({
		text: '稍等片刻,精彩马上呈现...',
		effect:'whirling'
	});
	option = {
		title:{
			text: ""
		},
		tooltip:{
			trigger: 'axis'//坐标轴触发，即是否跟随鼠标集中显示数据
		},
		legend:{
			data: array2
		},
		grid:{
			left:'6%',//组件距离容器左边的距离
			right:'4%',
			bottom:'30%',
			containLaber:true//区域是否包含坐标轴刻度标签
		},
		toolbox:{
			feature:{
				saveAsImage:{}//保存为图片
			},
			right:'2%'
		},
		xAxis:{
			type:'category',
			data: array1,
			axisLabel: {
				interval:0,  
				rotate:60  
			}
		},
		yAxis:{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
            show: true  
		},
		series:[
		]
	}
	option.series = Series;
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
}
//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变图表高宽
function domresize() {
	echarts.init(document.getElementById('overproof')).resize();
}
