$(function(){
	otypecombobox();
	BlocIdleDatagrid();
})
var chartStr = "";
$(document).ready(function(){
	showblocIdleChart();
})

var dtoTime1,dtoTime2;
function setParam(){
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	var otype = $('#otype').combobox('getValue');
	chartStr = "?otype="+otype+"&dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2;
}

var array1 = new Array();
var array2 = new Array();
var Series = [];
function showblocIdleChart(){
   	//初始化echart实例
	charts = echarts.init(document.getElementById("blocIdleChart"));
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
			data:array2,
			x: 'left',
			left: '50'
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
			name: '闲置率'
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
	$("#blocIdleChart").width("100%");
	if(array1.length>3 || array2.length>5){//array2：柱状图数量
		var width = array1.length * array2.length * 12;
		$("#blocIdleChart").width($("#blocIdleChart").width()+width);
	}
	echarts.init(document.getElementById('blocIdleChart')).resize();
}

function BlocIdleDatagrid(){
	setParam();
	var column = new Array();
	 $.ajax({  
         type : "post",  
         async : false,
		 url : "blocChart/getBlocIdle"+chartStr,
         data : {},  
         dataType : "json", //返回数据形式为json  
         success : function(result) {  
             if (result) {
            	 var width=$("#bodydiv").width()/result.rows.length;
                 column.push({field:"w",title:"时间跨度(月/季度/半年/年)",width:width,halign : "center",align : "center"});

                 for(var x=0;x<result.arys.length;x++){
                 	array1.push(result.arys[x].weldTime);
                 }
                 for(var m=0;m<result.arys1.length;m++){
                	 column.push({field:"a"+m,title:"<a href='companyChart/goCompanyIdle?parent="+result.arys1[m].id+"&parentime1="+dtoTime1+"&parentime2="+dtoTime2+"'>"+result.arys1[m].name+"(台)</a>",width:width,halign : "center",align : "center"});
                	 array2.push(result.arys1[m].name);
                  	 Series.push({
                 		name : result.arys1[m].name,
                 		type :'bar',//折线图
                        barMaxWidth:20,//柱状图最大宽度
                 		data : result.arys1[m].bilv,
						label : {
							normal : {
								position : 'top',
								show : true, //显示每个折点的值
								formatter : '{c}%'
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
	 $("#blocIdleTable").datagrid( {
			fitColumns : true,
			height : $("#bodydiv").height() - $("#blocIdleChart").height()-$("#blocIdle_btn").height()-15,
			width : $("#bodydiv").width(),
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50],
			url : "blocChart/getBlocIdle"+chartStr,
			singleSelect : true,
			rownumbers : true,
			showPageList : false,
			pagination : true,
			columns :[column]
	 })
}

function otypecombobox(){
	var optionFields = 
	"<option value='2'>月</option>" +
	"<option value='5'>季度</option>" +
	"<option value='6'>半年</option>" +
	"<option value='1'>年</option>";
	$("#otype").html(optionFields);
	$("#otype").combobox();
	$('#otype').combobox('select',"2");
}

function serachBlocIdle(){
	$("#chartLoading").show();
	array1 = new Array();
	array2 = new Array();
	Series = [];
	chartStr = "";
	setTimeout(function() {
		BlocIdleDatagrid();
		showblocIdleChart();
	}, 500)
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#blocIdleTable").datagrid('resize', {
		height : $("#bodydiv").height() - $("#blocIdleChart").height()-$("#blocIdle_btn").height()-15,
		width : $("#bodydiv").width()
	});
	echarts.init(document.getElementById('blocIdleChart')).resize();
}
