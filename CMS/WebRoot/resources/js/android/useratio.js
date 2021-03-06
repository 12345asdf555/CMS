$(function(){
	flagnum=1;
	parentCombobox();
	$.date('#androidtime1');
	$.date('#androidtime2');
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
		showChart();
	}, 500);
}

var chartStr = "",dtoTime1,dtoTime2;

function setParam(){
	parentid = $("#parent").combobox('getValue');
	dtoTime1 = $("#androidtime1").val();  
	dtoTime2 = $("#androidtime2").val();
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
			data:['设备利用率']
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'100',
			bottom:bootomnum,
			containLaber:true//区域是否包含坐标轴刻度标签
		},
		toolbox:{
			feature:{
				/*dataView : {show: true, readOnly: false},*/
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true}/*,
	            saveAsImage : {show: true}//保存为图片*/
	        },
			right:'2%'
		},
		xAxis:{
			type:'category',
			data: array1,
			name: '单位',
			axisLabel : {
				rotate: rotatenum, //x轴文字倾斜
			    interval:interval //0:允许x轴文字全部显示并重叠
			}
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
}


//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变图表高宽
function domresize() {
	echarts.init(document.getElementById('charts')).resize();
}
