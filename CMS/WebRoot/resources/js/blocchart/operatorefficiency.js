$(function(){
	flagnum=1;
	parentCombobox();
	dgDatagrid();
})


$(document).ready(function(){
	chart();
})

var type,flagnum,position;
function parentCombobox(){
	$.ajax({
		type : 'post',
		async : false,
		dataType : 'json',
		url : 'blocChart/getInsframework',
		success : function(result){
			var str = "";
			for(var i=0;i<result.ary.length;i++){
				str += "<option value='"+result.ary[i].id+"' class='20'>"+result.ary[i].name+"</option>";
			}
			$("#parent").html(str);
		},
		error : function(errorMsg){
	          alert("数据请求失败，请联系系统管理员!");			
		}
	})
	$("#parent").combobox({
		onChange: function (newvalue,oldvalue) {
			$("#parent").combobox('setText',$("#parent").combobox('getText').trim());
			$.ajax({
				type : "post",
				async : true,
				url : "blocChart/getInsframeworkType?id="+newvalue,
				dataType : "json",
				success : function(result){
					type = result.type;
					if(flagnum==1){
						serach();
					}
				}
			})
		}
	});
	var data = $("#parent").combobox('getData');
	$("#parent").combobox('select',data[0].value);
}
var activeurl = "blocChart/getOperatorEfficiency?flag=0";
var dgname = "部门";
function serach(){
	if(flagnum!=1){
		$("#chartLoading").show();
	}
	flagnum = 0;
	if(type==20){
		position = 0;
		dgname = "部门";
		activeurl = "blocChart/getOperatorEfficiency?flag=0";
	}else if(type==21){
		position = 0;
		dgname = "部门";
		activeurl = "blocChart/getOperatorEfficiency?flag=1";
	}else if(type==22){
		position = 1;
		dgname = "部门";
		activeurl = "blocChart/getOperatorEfficiency?flag=2";
	}else if(type==23){
		position = 1;
		dgname = "姓名";
		activeurl = "itemChart/getOperatorEfficiency?flag=3";
	}
	array0 = new Array();
	array1 = new Array();
	array2 = new Array();
	array3 = new Array();
	setTimeout(function() {
		dgDatagrid();
		showChart();
	}, 500);
}

var chartStr = "",dtoTime1,dtoTime2;

function setParam(){
	parentid = $("#parent").combobox('getValue');
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');  
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "&parent="+parentid+"&time1="+dtoTime1+"&time2="+dtoTime2;
}

var array0 = new Array();
var array1 = new Array();
var array2 = new Array();
var array3 = new Array();
function showChart(){
	setParam();
	 $.ajax({  
        type : "post",  
        async : false,
        url : activeurl+chartStr,
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {  
            if (result) {
            	for(var i=0;i<result.rows.length;i++){
            		array0.push(result.rows[i].name);
            		array1.push(result.rows[i].worktime);
            		array2.push(result.rows[i].weldtime);
            		array3.push(result.rows[i].workratio);

            	}
            }  
        },  
       error : function(errorMsg) {  
            alert("请求数据失败啦,请联系系统管理员!");  
        }  
   }); 
	 chart();
}

function chart(){
	var bootomnum,rotatenum,interval;
	if(position==0){
		bootomnum=20,rotatenum=0,interval="auto";
	}else{
		bootomnum=50,rotatenum=30,interval=0;
	}
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
			data:['上班时长(h)','焊接时长(h)','工作效率']
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'120',
			bottom: bootomnum,
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
			data: array0,
			name : '    '+dgname,
			axisLabel : {
				rotate: rotatenum, //x轴文字倾斜
			    interval:interval //0:允许x轴文字全部显示并重叠
			}
		},
		yAxis:[{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name : '时长(h)'
		},{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name : '工作效率', 
			splitLine:{show: false},//去除网格线
			axisLabel: {  
                  show: true,  
                  interval: 'auto',  
                  formatter: '{value}%'  
            }
		}],
		series:[{
			name:'上班时长(h)',
			type:'bar',
            barMaxWidth:20,//最大宽度
			data:array1,
			label : {
				normal : {
					position : 'top',
					show : true //显示每个折点的值
				}
			}
		},{
			name:'焊接时长(h)',
			type:'bar',
            barMaxWidth:20,//最大宽度
			data:array2,
			label : {
				normal : {
					position : 'top',
					show : true //显示每个折点的值
				}
			}
		},{
			name:'工作效率',
            min: 0,
            max: 100,//最大最小值
            interval: 20,//间隔
			type:'line',
            yAxisIndex: 1,
            barMaxWidth:20,//最大宽度
			data:array3,
			label : {
				normal : {
					position : 'top',
					show : true, //显示每个折点的值
					formatter : '{c}%'
				}
			}
		}]
	}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();
}

function dgDatagrid(){
	setParam();
	 $("#dg").datagrid( {
			fitColumns : true,
			height : $("#body").height() - $("#charts").height()-$("#search_btn").height()-15,
			width : $("#body").width(),
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50],
			url : activeurl+chartStr,
			singleSelect : true,
			rownumbers : true,
			showPageList : false,
			pagination : true,
			columns :[[{
				field : "name",
				title : dgname,
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "worktime",
				title : "上班时长(h)",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "boottime",
				title : "开机时长(h)",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "shutdowntime",
				title : "关机时长(h)",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "standbytime",
				title : "待机时长(h)",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "weldtime",
				title : "焊接时长(h)",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "sjratio",
				title : "上机率(%)",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "effectiveratio",
				title : "有效焊接率(%)",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "workratio",
				title : "工作效率(%)",
				width : 100,
				halign : "center",
				align : "left"
			}]]
	 })
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格，图表高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#body").height() - $("#charts").height()-$("#search_btn").height()-15,
		width : $("#body").width()
	});
	echarts.init(document.getElementById('charts')).resize();
}
