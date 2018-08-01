$(document).ready(function(){
	useRatio();
})

var array1 = new Array();
var array2 = new Array();
var array3 = new Array();
var array4 = new Array();
function useRatio(){
	$.ajax({
		type : 'post',
		asyn : false,
		url : 'hierarchy/getUseRatio?time1='+$("#dtoTime1").datetimebox("getValue")+"&time2="+$("#dtoTime2").datetimebox("getValue"),
		dataType : 'json',
		success : function(result){
			for(var i=0;i<result.ary.length;i++){
				array1.push(result.ary[i].itemname);
				array2.push(result.ary[i].worknum);
				array3.push(result.ary[i].machinenum);
				array4.push(result.ary[i].useratio);
			}
			usechart();
		},
		error : function(errorMsg){
		      alert("数据请求失败，请联系系统管理员!");  
		}
	});
}

function usechart(){
  	//初始化echart实例
	charts = echarts.init(document.getElementById("charts"));
	//显示加载动画效果
	charts.showLoading({
		text: '稍等片刻,精彩马上呈现...',
		effect:'whirling'
	});
	option = {
		tooltip:{
			trigger: 'axis'//坐标轴触发，即是否跟随鼠标集中显示数据
		},
		legend:{
			data:['工作设备数','设备总数','设备利用率']
		},
		grid:{
			left:'60',//组件距离容器左边的距离
			right:'100',
			bottom:'40',
			containLaber:true//区域是否包含坐标轴刻度标签
		},
		xAxis:{
			type:'category',
			name : '   所属部门',
			data: array1
		},
		yAxis:[{
			type: 'value',
			name: '数量(台)',
            min: 0,
            max: 200,
            interval: 40
		},{
			type: 'value',
			name: '利用率(%)',
            min: 0,
            max: 100,
            interval: 20
		}],
		series:[{
     		name :'工作设备数',
     		type :'bar',//柱状图
            barMaxWidth:25,//最大宽度
     		data :array2,
	        label: {
	            normal: {
	                position: 'top',
	                show: true,//显示每个折点的值
 					formatter: '{c}%'  
	            }
	        }
		},{
     		name :'设备总数',
     		type :'bar',//柱状图
            barMaxWidth:25,//最大宽度
     		data :array3,
	        label: {
	            normal: {
	                position: 'top',
	                show: true,//显示每个折点的值
 					formatter: '{c}%'  
	            }
	        }
		},{
     		name :'设备利用率',
     		type :'line',//折线图
       		symbol: 'circle',//实心折点
            yAxisIndex: 1,
     		data :array4,
	        label: {
	            normal: {
	                position: 'top',
	                show: true,//显示每个折点的值
 					formatter: '{c}%'  
	            }
	        }
     	}]
	}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
}

function search(){
	array1 = new Array();
	array2 = new Array();
	array3 = new Array();
	array4 = new Array();
	useRatio();
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	echarts.init(document.getElementById('charts')).resize();
}