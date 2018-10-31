$(document).ready(function(){
	showOverproof();
})

function showOverproof(){
	var array1 = new Array();
	var array2 = new Array();
	var Series = [];
	$.ajax({  
        type : "post",  
        async : false, //同步执行  
        url : "companyChart/getExcessiveBackDetail?id="+$("#id").val(),
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {  
            if (result) {  
                for(var i=0;i<result.rows.length;i++){
                 	array1.push(result.rows[i].weldtime);
                	array2.push(result.rows[i].electricity);
                }
                Series.push({
             		name : '超标回溯',
             		type :'line',//折线图
             		data : array2,
                    markLine: {
                        data: [
                            {yAxis: result.rows[0].maxelectricity, name: '最大电流'},
                            {yAxis: result.rows[0].minelectricity, name: '最小电流'}
                        ]
                    }
             	});
            }  
        },  
       error : function(errorMsg) {  
            alert("图表请求数据失败啦!");  
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
			data: ['超标回溯'],
			x : 'left',
			left:'6%'
		},
		grid:{
			left:'6%',//组件距离容器左边的距离
			right:'4%',
			bottom:'20',
			containLaber:true//区域是否包含坐标轴刻度标签
		},
		toolbox:{
			feature:{
				saveAsImage:{}//保存为图片
			},
			right:'2%',
			top:'30'
		},
		xAxis:{
			type:'category',
			data: array1
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
	//重定义图表宽度
	$("#overproof").width("100%");
	if(array1.length>5){
		var width = (array1.length-5) * 200;
		$("#overproof").width($("#overproof").width()+width);
	}
	echarts.init(document.getElementById('overproof')).resize();
}
//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变图表高宽
function domresize() {
	echarts.init(document.getElementById('overproof')).resize();
}
