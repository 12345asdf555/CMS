$(function(){
	refresh();
})
$(document).ready(function(){
	getAjax();
})
var array1 = new Array();
var array2 = new Array();
var array3 = new Array();
function showjunctionOverproofChart(){
	var Series = [];
	Series.push({
  		name : array2,
  		type :'line',//折线图
   		smooth: true,//是否平滑曲线显示
  		data : data2,
  		itemStyle : {
  			normal: {
  				label : {
  					show: true//显示每个折点的值
  				}
  			}
  		}
  	});
  	//初始化echart实例
	charts = echarts.init(document.getElementById("junctionOverproofChart"));
	//显示加载动画效果
	charts.showLoading({
		text: '稍等片刻,精彩马上呈现...',
		effect:'whirling'
	});
	option = {
		title:{
			text: ""//焊接工艺超标统计
		},
		tooltip:{
			trigger: 'axis'//坐标轴触发，即是否跟随鼠标集中显示数据
		},
		legend:{
			data:array2
		},
		grid:{
			left:'8%',//组件距离容器左边的距离
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
			boundaryGap:false,//坐标轴两边是否留白
			data: data1,
			axisLabel: {  
				  interval:0,  
				  rotate:60  
			}
		},
		yAxis:{
			type: 'value'//value:数值轴，category:类目轴，time:时间轴，log:对数轴
		},
		series:[
		]
	}
	option.series = Series;
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
};
var setTime = 1000;
var data1 = new Array();
var data2 = new Array();

var timer;
function refresh(){
	timer=window.setInterval(function () {
		data1.splice(0,data1.length);//清空数组 
		data2.splice(0,data2.length);
		if(array1.length==0){
			window.clearInterval(timer);
		}else{
			for(var i=0;i<10;i++){
				data1.push(array1[i]);
				data2.push(array3[i]);
			};
			array1 = array1.slice(9, array1.length-1);
			array3 = array3.slice(9, array3.length-1);
			showjunctionOverproofChart();
		}
		
	}, setTime);
}
//加速
function addtime(){
	setTime -= 500;
	if(setTime<=100){
		setTime = 100;
	}
	window.clearInterval(timer);
	refresh();
}

//减速
function reducetime(){
	setTime += 500;
	if(setTime>=10000){
		setTime = 10000;
	}
	window.clearInterval(timer);
	refresh();
}

function getAjax(){
	var welderno = $("#welderno").val();
	var machineno = $("#machineno").val();
	var junctionno = $("#junctionno").val();
	var time = $("#time").val();
	var itemid = $("#itemid").val();
	$.ajax({  
        type : "post",  
        async : false,
        url : "junctionChart/getjunctionoverproof?welderno="+welderno+"&machineno="+machineno+"&junctionno="+junctionno+"&time="+time+"&itemid="+itemid,
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {  
            if (result) {
            	 for(var i=0;i<result.ary1.length;i++){
              	   array1.push(result.ary1[i].weldtime);
             	 }
            	 for(var i=0;i<result.ary.length;i++){
            	   array2.push(result.ary[i].junctionno);
            	   array3 = result.ary[i].overflage;
                }
               
            }  
        },  
       error : function(errorMsg) {  
            alert("图表请求数据失败啦!");  
        }  
   }); 
}