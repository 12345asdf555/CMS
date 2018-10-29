$(function(){
	parentCombobox();
	dgDatagrid();
	$("#rank1").numberbox({
		"onChange" : function() {
			if($("#rank1").val()==null || $("#rank1").val()==""){
				$("#rank1").numberbox('setValue',1);
			}
		}
	});
	$("#rank2").numberbox({
		"onChange" : function() {
			if($("#rank2").val()==null || $("#rank2").val()==""){
				$("#rank2").numberbox('setValue',20);
			}
		}
	});
})

var chartStr = "";
$(document).ready(function(){
	showChart();
})
var dtoTime1,dtoTime2;
function setParam(){
	var parent = $("#parent").combobox('getValue');
	var rank1 = $("#rank1").val();
	var rank2 = $("#rank2").val();
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "?parent="+parent+"&rank1="+rank1+"&rank2="+rank2+"&time1="+dtoTime1+"&time2="+dtoTime2;
}

var array1 = new Array();
var array2 = new Array();
var avg = 0;
function showChart(){
	setParam();
	 $.ajax({  
        type : "post",  
        async : false,
        url : "blocChart/gerBlocRunTime"+chartStr,
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {  
            if (result) {
            	for(var i=0;i<result.rows.length;i++){
            		array1.push(result.rows[i].machineno);
            		array2.push(result.rows[i].time);
            	}
            	avg = result.avgnum;
            }  
        },  
       error : function(errorMsg) {  
            alert("请求数据失败啦,请联系系统管理员!");  
        }  
   }); 
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
			data:['运行时长(h)']
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'120',
			bottom:'50',
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
			name: '运行时长',
			axisLabel : {
				rotate: 40, //x轴文字倾斜
			    interval:0 //允许x轴文字全部显示并重叠
			}
		},
		yAxis:{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name: '运行时长(h)'
		},
		series:[{
			name:'运行时长(h)',
			type:'bar',
            barMaxWidth:20,//最大宽度
            markLine: {
                data: [
                    {yAxis: avg, name: '平均时长'}
                ],
        		label: {
                    normal: {
                        position: 'middle',
                        color:'#000099',//字体颜色
                        formatter: '{b}: {c}h' //标志线说明
                    }
                },
		        itemStyle : { 
		            normal: { 
		                lineStyle: { 
		                    color:'#000099', //标志线颜色
		                }
		            } 
		        }
            } ,
			data:array2,
			label : {
				normal : {
					position : 'top',
					show : true //显示每个折点的值
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
			height : $("#bodydiv").height() - $("#charts").height()-$("#search_btn").height()-15,
			width : $("#bodydiv").width(),
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50],
			url : "blocChart/gerBlocRunTime"+chartStr,
			singleSelect : true,
			rownumbers : true,
			showPageList : false,
			pagination : true,
			columns :[[{
				field : "itemname",
				title : "所属部门",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "machineno",
				title : "设备编号",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "time",
				title : "运行时长(h)",
				width : 100,
				halign : "center",
				align : "center"
			}]]
	 })
}

function serach(){
	$("#chartLoading").show();
	array1 = new Array();
	array2 = new Array();
	chartStr = "";
	setTimeout(function(){
		dgDatagrid();
		showChart();
	},500);
}

function parentCombobox(){
	$.ajax({
		type : 'post',
		async : false,
		dataType : 'json',
		url : 'blocChart/getInsframework',
		success : function(result){
			var str = "";
			for(var i=0;i<result.ary.length;i++){
				str += "<option value='"+result.ary[i].id+"'>"+result.ary[i].name+"</option>";
			}
			$("#parent").html(str);
		},
		error : function(errorMsg){
	          alert("数据请求失败，请联系系统管理员!");			
		}
	})
	$("#parent").combobox({
		onChange: function (n,o) {
			$("#parent").combobox('setText',$("#parent").combobox('getText').trim());
		}
	});
	var data = $("#parent").combobox('getData');
	$("#parent").combobox('select',data[0].value);
}


//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格，图表高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#bodydiv").height() - $("#charts").height()-$("#search_btn").height()-15,
		width : $("#bodydiv").width()
	});
	echarts.init(document.getElementById('charts')).resize();
}
