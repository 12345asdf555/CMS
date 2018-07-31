$(function(){
	ItemEfficiencyDatagrid();
})
var chartStr = "";
$(document).ready(function(){
	showItemEfficiencyChart();
})


var min="",max ="";
function setParam(){
	chartStr = "";
	var nextparent = $("#nextparent").val();
	var dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	var dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "?nextparent="+nextparent+"&dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2+"&min="+min+"&max="+max;
}

function showItemEfficiencyChart(){
	setParam();
	var array1 = new Array();
	var array2 = new Array();
	var Series = [];
	 $.ajax({  
         type : "post",  
         async : false,
         url : "itemChart/getItemEfficiencyChart"+chartStr,
         data : {},  
         dataType : "json", //返回数据形式为json  
         success : function(result) {  
             if (result) {
            	 if(result.ary.length>0){
            		 for(var i=0;i<result.ary.length;i++){
                       	array1 = result.ary[i].num1;
                       	Series.push({
                       		name : '工效(1:1)',
                       		type :'line',//折线图
                            barMaxWidth:20,//柱状图最大宽度
                       		smooth: true,//是否平滑曲线显示
                       		data : result.ary[i].num2,
                       		itemStyle : {
                       			normal: {
                       				label : {
                       					show: true,//显示每个折点的值
                     					formatter: '{c}%'  
                       				}
                       			}
                       		}
                       	});
                      }
                  }else{
                	  Series.push({
                     		name : '工效(1:1)',
                     		type :'line',//折线图
                     		data : ''
                      });
                  }
            }
         },
        error : function(errorMsg) {  
             alert("图表请求数据失败啦!");  
         }  
    }); 
   	//初始化echart实例
	charts = echarts.init(document.getElementById("itemEfficiencyChart"));
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
			data:['工效(1:1)']
		},
		grid:{
			left:'40',//组件距离容器左边的距离
			right:'4%',
			bottom:'20',
			containLaber:true//区域是否包含坐标轴刻度标签
		},
		toolbox:{
			feature:{
				dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}//保存为图片
			},
			right:'2%'
		},
		xAxis:{
			type:'category',
			data: array1
		},
		yAxis:{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			axisLabel: {  
                  show: true,  
                  interval: 'auto',  
                  formatter: '{value}%'  
            },  
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
	//echarts 点击事件
	charts.on('click', function (param) {
		var str = new Array();
		str = param.name.split("-");
		min = str[0],max=str[1];
		ItemEfficiencyDatagrid();
	});
	$("#chartLoading").hide();
}

function ItemEfficiencyDatagrid(){
	setParam();
	$("#itemEfficiencyTable").datagrid( {
		fitColumns : true,
		height : $("#body").height() - $("#itemEfficiencyChart").height()-$("#itemEfficiency_btn").height()-45,
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "itemChart/getItemEfficiency"+chartStr,
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'iname',
			title : '项目部',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'wname',
			title : '焊工姓名',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'wid',
			title : '焊工编号',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'weldtime',
			title : '焊接时长(h)',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'num',
			title : '完成焊口数',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'dyne',
			title : '总达因值',
			width : 150,
			halign : "center",
			align : "left",
			hidden : true
		}] ],
		pagination : true
	});
}

function serachEfficiencyItem(){
	min="",max="";
	$("#chartLoading").show();
	var time1 = $("#time1").val("");
	var time2 = $("#time2").val("");
	setTimeout(function() {
		showItemEfficiencyChart();
		ItemEfficiencyDatagrid();
	}, 500)
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#itemEfficiencyTable").datagrid('resize', {
		height : $("#body").height() - $("#itemEfficiencyChart").height()-$("#itemEfficiency_btn").height()-45,
		width : $("#body").width()
	});
	echarts.init(document.getElementById('itemEfficiencyChart')).resize();
}
