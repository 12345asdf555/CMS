$(function(){
	CausttimeDatagrid();
})
var chartStr = "";
$(document).ready(function(){
	showCaustOverptimeChart(0);
})
var dtoTime1,dtoTime2;
function setParam(){
	var otype = $("input[name='otype']:checked").val();
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	var number = $("#number").val();
	chartStr += "&dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2+"&otype="+otype+"&number="+number;
}

var array1 = new Array();
var array2 = new Array();
var charts,Series = [];
function showCaustOverptimeChart(num){
	if(num==0){
	   	//初始化echart实例
		charts = echarts.init(document.getElementById("caustOvertimeChart"));
	}
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
			data:array2,
			x: 'left',
			left: '40'
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'140',
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
			right:'2%',
			top:'30'
		},
		xAxis:{
			type:'category',
			data: array1,
			name: '日期'
		},
		yAxis:{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name: '焊机数量'
		},
		series:[
		]
	}
	option.series = Series;
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();
	//重定义图表宽度
	$("#caustOvertimeChart").width("100%");
	if(array1.length>3 || array2.length>5){//array2：柱状图数量
		var width = array1.length * 80 + array2.length * 22;
		$("#caustOvertimeChart").width($("#caustOvertimeChart").width()+width);
	}
	charts.resize();
}

function CausttimeDatagrid(){
	setParam();
	var parent = $("#parent").val();
	var column = new Array();
	 $.ajax({  
         type : "post",  
         async : false,
         url : "caustChart/getCaustOvertime?parent="+parent+chartStr,
         data : {},  
         dataType : "json", //返回数据形式为json  
         success : function(result) {  
             if (result) {
            	 var width=$("#bodydiv").width()/result.rows.length;
                 column.push({field:"w",title:"时间跨度(年/月/周/日)",width:width,halign : "center",align : "center"});

                 for(var i=0;i<result.arys.length;i++){
                  	 array1.push(result.arys[i].weldTime);
            	 }
                 for(var m=0;m<result.arys1.length;m++){
                	 column.push({field:"a"+m,title:"<a href='itemChart/goItemOvertime?parent="+result.arys1[m].itemid+"&parentime1="+dtoTime1+"&parentime2="+dtoTime2+"'>"+result.arys1[m].name+"(台)</a>",width:width,halign : "center",align : "center"});
                	 array2.push(result.arys1[m].name);
                  	 Series.push({
                  		name : result.arys1[m].name,
                  		type :'bar',//折线图
                  		data : result.arys1[m].overtime,
                        barMaxWidth:20,//柱状图最大宽度
						label : {
							normal : {
								position : 'top',
								show : true //显示每个折点的值
							}
						}
                  	});
                 }
             }  
         },  
        error : function(errorMsg) {  
             alert("请求数据失败啦,请联系系统管理员!");  
         }  
    }); 
	 $("#caustOvertimeTable").datagrid( {
			fitColumns : true,
			height : $("#bodydiv").height() - $("#caustOvertimeChart").height()-$("#caustOvertime_btn").height()-45,
			width : $("#bodydiv").width(),
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50],
			url : "caustChart/getCaustOvertime?parent="+parent+chartStr,
			singleSelect : true,
			rownumbers : true,
			showPageList : false,
			pagination : true,
			columns :[column]
	 })
}

function serachCaustOvertime(){
	$("#chartLoading").show();
	Series = [];
	array1 = new Array();
	array2 = new Array();
	chartStr = "";
	setTimeout(function(){
		CausttimeDatagrid();
		showCaustOverptimeChart(1);
	},500);
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#caustOvertimeTable").datagrid('resize', {
		height : $("#bodydiv").height() - $("#caustOvertimeChart").height()-$("#caustOvertime_btn").height()-45,
		width : $("#bodydiv").width()
	});
	charts.resize();
}
