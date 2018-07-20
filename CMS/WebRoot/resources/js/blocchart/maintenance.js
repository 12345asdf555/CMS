$(function(){
	flagnum=1;
	parentCombobox();
	dgDatagrid();
})

$(document).ready(function(){
	showChart();
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

var activeurl = "blocChart/getMaintenanceratio?flag=999";
function serach(){
	if(flagnum!=1){
		$("#chartLoading").show();
	}
	flagnum = 0;
	if(type==20){
		position = 0;
		activeurl = "blocChart/getMaintenanceratio?flag=0";
		setTimeout(function() {
			dgDatagrid();
			showChart();
		}, 500);
	}else if(type==21){
		position = 0;
		activeurl = "blocChart/getMaintenanceratio?flag=1";
		setTimeout(function() {
			dgDatagrid();
			showChart();
		}, 500);
	}else if(type==22){
		position = 1;
		activeurl = "blocChart/getMaintenanceratio?flag=2";
		setTimeout(function() {
			dgDatagrid();
			showChart();
		}, 500);
	}else if(type==23){
		$("#charts").hide();
		$("#explain").hide();
		$("#itemcharts1").show();
		$("#itemcharts2").show();
		position = 0;
		activeurl = "itemChart/getItemTypeMaintain?flag=3";
		showItemNumChart();
		showItemMoneyChart();
	}
	array1 = new Array();
	array2 = new Array();
	array3 = new Array();
	array4 = new Array();
	array5 = new Array();
	array6 = new Array();
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
var array3 = new Array();
var array4 = new Array();
var array5 = new Array();
var array6 = new Array();
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
            		array2.push({
            			value : result.rows[i].total/result.row[i].sumnum,
            			name : result.rows[i].name
            		});
            		
            	}
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
			trigger: 'item'
		},
		legend:{
			type : 'scroll',
			orient : 'vertical',
			right : '5%',
			top : 20,
			bottom : 20,
			data : array1
		},
		toolbox:{
			feature:{
				saveAsImage:{}//保存为图片
			},
			right:'2%'
		},
		series:[{
			name:'设备利用率',
			type:'pie',
            radius : '80%',
            center : ['40%', '50%'],
			data:array2
		}]
	}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();
}

//显示项目部设备费用及维修费用对比图
function showItemMoneyChart(){
	setParam();
	 $.ajax({  
       type : "post",  
       async : false,
       url : activeurl+chartStr,
       data : {},  
       dataType : "json", //返回数据形式为json  
       success : function(result) {  
           if (result) {
           	for(var i=0;i<result.ary.length;i++){
           		array1.push(result.ary[i].manufacturername);
           		array2.push(result.ary[i].maintainmoney);
           		array3.push(result.ary[i].machinemoney);
           	}
           }  
       },  
      error : function(errorMsg) {  
           alert("请求数据失败啦,请联系系统管理员!");  
       }  
  }); 
   	//初始化echart实例
	charts = echarts.init(document.getElementById("itemcharts2"));
	//显示加载动画效果
	charts.showLoading({
		text: '稍等片刻,精彩马上呈现...',
		effect:'whirling'
	});
	option = {
			tooltip:{
				trigger: 'axis',//坐标轴触发，即是否跟随鼠标集中显示数据
			},
			legend:{
				data:['维修费用','设备总费用']
			},
			grid:{
				left:'60',//组件距离容器左边的距离
				right:'4%',
				bottom:'90',
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
				data: array1,
				axisLabel : {
					rotate: 50, //x轴文字倾斜
				    interval:0 //允许x轴文字全部显示并重叠
				}
			},
			yAxis:{
				type: 'value'//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			},
			series:[
				{
					name:'维修费用',
					type:'bar',
		            barMaxWidth:20,//最大宽度
					data:array2
				},
				{
					name:'设备总费用',
					type:'bar',
		            barMaxWidth:20,//最大宽度
					data:array3
				}
			]
		}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();

}

//设备维修次数-故障次数对比图
function showItemNumChart(){
	setParam();
	 $.ajax({  
       type : "post",  
       async : false,
       url : activeurl+chartStr,
       data : {},  
       dataType : "json", //返回数据形式为json  
       success : function(result) {  
           if (result) {
           	for(var i=0;i<result.ary.length;i++){
           		array4.push(result.ary[i].manufacturername);
           		array5.push(result.ary[i].maintainmoney);
           		array6.push(result.ary[i].machinemoney);
           	}
           }  
       },  
      error : function(errorMsg) {  
           alert("请求数据失败啦,请联系系统管理员!");  
       }  
  }); 
   	//初始化echart实例
	charts = echarts.init(document.getElementById("itemcharts1"));
	//显示加载动画效果
	charts.showLoading({
		text: '稍等片刻,精彩马上呈现...',
		effect:'whirling'
	});
	option = {
			tooltip:{
				trigger: 'axis',//坐标轴触发，即是否跟随鼠标集中显示数据
			},
			legend:{
				data:['设备维修次数','故障次数']
			},
			grid:{
				left:'60',//组件距离容器左边的距离
				right:'4%',
				bottom:'90',
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
				data: array4,
				axisLabel : {
					rotate: 50, //x轴文字倾斜
				    interval:0 //允许x轴文字全部显示并重叠
				}
			},
			yAxis:{
				type: 'value'//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			},
			series:[
				{
					name:'设备维修次数',
					type:'bar',
		            barMaxWidth:20,//最大宽度
					data:array5
				},
				{
					name:'故障次数',
					type:'bar',
		            barMaxWidth:20,//最大宽度
					data:array6
				}
			]
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
				title : "部门",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "total",
				title : "维修次数",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "time",
				title : "维修次数占比",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "num",
				title : "故障率",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "useratio",
				title : "故障维修率",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "rmoney",
				title : "维护费用",
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : "mmoney",
				title : "设备费用",
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
	echarts.init(document.getElementById('itemcharts1')).resize();
	echarts.init(document.getElementById('itemcharts2')).resize();
}
