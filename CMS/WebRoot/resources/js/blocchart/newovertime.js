$(function(){
	BloctimeDatagrid(parent);
})
var chartStr = "";
$(document).ready(function(){
	showBlocOverptimeChart();
})
var dtoTime1,dtoTime2,parent="";
function setParam(){
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	var number = $("#number").val();
	chartStr += "&dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2+"&number="+number+"&parentflag="+parentflag;
}

var array1 = new Array();
var array2 = new Array();
var Series = [];
function showBlocOverptimeChart(){
   	//初始化echart实例
	charts = echarts.init(document.getElementById("charts"));
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
			left: '60'
		},
		grid:{
			left:'60',//组件距离容器左边的距离
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
			name: '超时次数'
		},
		series:[
		]
	}
	option.series = Series;
	charts.clear();
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();
	//重定义图表宽度
	$("#charts").width("100%");
	if(array1.length>3 || array2.length>5){//array2：柱状图数量
		var width = array1.length * array2.length * 22;
		$("#charts").width($("#charts").width()+width);
	}
	echarts.init(document.getElementById('charts')).resize();
}

function BloctimeDatagrid(parent){
	var url;
	setParam();
	var otype = $("input[name='otype']:checked").val();
	if(parent){
		url = "blocChart/getNewOvertime?parent="+parent+"&otype="+otype+chartStr;
	}else{
		url = "blocChart/getNewOvertime?otype="+otype+chartStr;
	}
	var column = new Array();
	 $.ajax({  
         type : "post",  
         async : false,
         url : url,
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
                	 if(result.arys1[m].insfstr){
                		 $("#parentMsg").html("<h2>"+result.arys1[m].insfstr+"</h2>");//显示当前位置
                	 }
                	 var iid = result.arys1[m].itemid;
                	 if(result.arys1[m].type==23){//项目部层
                		 column.push({field:"a"+m,title:result.arys1[m].name,width:width,halign : "center",align : "center",formatter : function(value,row,index){
                			 return '<a href="junctionChart/gojunctionnewovertime?parent='+iid+'&otype='+otype+'&weldtime='+row.w+chartStr+'">'+value+'</a>';
                		 }});
                	 }else{
                		 column.push({field:"a"+m,title:"<a href='javascript:changeInsframework("+iid+","+result.arys1[m].type+")'>"+result.arys1[m].name+"(次)</a>",width:width,halign : "center",align : "center"});
                	 }
                	 array2.push(result.arys1[m].name);
                  	 Series.push({
                  		name : result.arys1[m].name,
                  		type :'bar',//折线图
                        barMaxWidth:20,//柱状图最大宽度
                  		data : result.arys1[m].num,
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
	 $("#dg").datagrid( {
			fitColumns : true,
			height : $("#bodydiv").height() - $("#charts").height()-$("#dg_btn").height()-15,
			width : $("#bodydiv").width(),
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50],
			url : url,
			singleSelect : true,
			rownumbers : true,
			showPageList : false,
			pagination : true,
			columns :[column]
	 })
}

var tempparent;
function serach(){
	$("#chartLoading").show();
	Series = [];
	array1 = new Array();
	array2 = new Array();
	chartStr = "";
	setTimeout(function(){
		BloctimeDatagrid(tempparent);
		showBlocOverptimeChart();
	},500);
}

var parentflag = 0;
function changeInsframework(parent,type){
	var row = $("#dg").datagrid('getSelected');
	parentflag = 1;
	/*if(type==23){
		alert("项目部！");
		var url = 
		var img = new Image();
	    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
	    url = img.src;  // 此时相对路径已经变成绝对路径
	    img.src = null; // 取消请求
		window.location.href = encodeURI(url);
		//return "<a href='+"'>"+value+"</a>";
	}else{*/
		tempparent = parent;
		$("#chartLoading").show();
		Series = [];
		array1 = new Array();
		array2 = new Array();
		chartStr = "";
		setTimeout(function(){
			BloctimeDatagrid(parent);
			showBlocOverptimeChart();
		},500);
	/*}*/
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#bodydiv").height() - $("#charts").height()-$("#dg_btn").height()-15,
		width : $("#bodydiv").width()
	});
	echarts.init(document.getElementById('charts')).resize();
}
