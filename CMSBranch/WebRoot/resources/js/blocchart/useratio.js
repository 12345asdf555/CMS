$(function(){
	flagnum=1;
	parentCombobox();
	dgDatagrid();
})


$(document).ready(function(){
	chart(0);
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
			$("#parent").combobox('setText',$.trim($("#parent").combobox('getText')));
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
var activeurl;
function serach(){
	if(flagnum!=1){
		$("#chartLoading").show();
	}
	flagnum = 0;
	if(type==20){
		position = 0;
		activeurl = "blocChart/getUseratio?flag=0";
	}else if(type==21){
		position = 0;
		activeurl = "blocChart/getUseratio?flag=1";
	}else if(type==22){
		position = 1;
		activeurl = "blocChart/getUseratio?flag=2";
	}else if(type==23){
		position = 0;
		activeurl = "blocChart/getUseratio?flag=3";
	}
	array1 = new Array();
	array2 = new Array();
	setTimeout(function() {
		dgDatagrid();
		showChart();
	}, 500);
}

var chartStr = "",dtoTime1,dtoTime2;

function setParam(){
	parentid = $("#parent").combobox('getValue');
	dtoTime1 = $("#dtoTime1").datebox('getValue');  
	dtoTime2 = $("#dtoTime2").datebox('getValue');
	chartStr = "&parent="+parentid+"&time1="+dtoTime1+"&time2="+dtoTime2;
}

var array1 = new Array();
var array2 = new Array();
var avg = 0;
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
            		array1.push(result.rows[i].name);
            		array2.push(result.rows[i].useratio);
            	}
            }  
        },  
       error : function(errorMsg) {  
            alert("请求数据失败啦,请联系系统管理员!");  
        }  
   }); 
	 chart(1);
}

var charts;
function chart(num){
	//处理ie重复实例化显示异常
	if(num==0){
	   	//初始化echart实例
		charts = echarts.init(document.getElementById("charts"));
	}
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
			data:['设备利用率']
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'100',
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
			name: '单位'
		},
		yAxis:{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name: '利用率',
			axisLabel: {  
                  show: true,  
                  interval: 'auto',  
                  formatter: '{value}%'  
            }
		},
		series:[{
			name:'设备利用率',
			type:'bar',
            barMaxWidth:20,//最大宽度
			data:array2,
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
	//重定义图表宽度
	$("#charts").width("100%");
	if (array1.length > 3) {
		var maxlength = array1[0];
		for (var i = 0; i < array1.length; i++) {
			if (array1[i].length > maxlength.length) {
				maxlength = array1[i];
			}
		}
		var width = array1.length * maxlength.length * 18; //最长组织机构名字每个字节算18px
		if ($("#charts").width() < width) {
			$("#charts").width(width);
		}
	}
	charts.resize();
}

function dgDatagrid(){
	setParam();
	 $("#dg").datagrid( {
			fitColumns : true,
			height : $("#bodydiv").height() - $("#charts").height()-$("#search_btn").height()-15,
			width : $("#bodydiv").width(),
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50],
			url : activeurl+chartStr,
			singleSelect : true,
			rownumbers : true,
			showPageList : false,
			pagination : true,
			columns :[[{
				field : "name",
				title : "部门",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "day",
				title : "天数",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "time",
				title : "设备运行时长(h)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "maxnum",
				title : "设备最大使用数(台)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "num",
				title : "设备总数(台)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "useratio",
				title : "设备利用率",
				width : 100,
				halign : "center",
				align : "center",
				formatter : function(value,row,index){
					return value + "%";
				}
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
		height : $("#bodydiv").height() - $("#charts").height()-$("#search_btn").height()-15,
		width : $("#bodydiv").width()
	});
	charts.resize();
}
