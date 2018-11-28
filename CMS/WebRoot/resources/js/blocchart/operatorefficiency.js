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
			if(flagnum==1){
				serach();
			}
		}
	});
	var data = $("#parent").combobox('getData');
	$("#parent").combobox('select',data[0].value);
}
var activeurl = "blocChart/getOperatorEfficiency?flag=0";
var dgname = "部门";

function serach(){
	$.ajax({
		type : "post",
		async : true,
		url : "blocChart/getInsframeworkType?id="+$("#parent").combobox("getValue"),
		dataType : "json",
		success : function(result){
			type = result.type;
			loadData();
		}
	})

}

function loadData(){
	if(flagnum!=1){
		$("#chartLoading").show();
	}
	flagnum = 0;
	$("#explain").html("<span>操作者率</span><hr><ul><li>展现某一时间段内，各部门操作者上班时长、焊接时长以及工作效率</li><li>上机率=开机时长/上班时长</li>"+
					"<li>有效焊接率=焊接时长/开机时长</li><li>工作效率=焊接时长/上班时长</li></ul>");
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
		$("#explain").html("<span>操作者率</span><hr><ul><li>展现某一时间段内，各部门操作者上班时长、焊接时长以及工作效率(取最高五位及最低五位)</li><li>上机率=开机时长/上班时长</li>"+
		"<li>有效焊接率=焊接时长/开机时长</li><li>工作效率=焊接时长/上班时长</li></ul>");
	}
	array0 = new Array();
	array1 = new Array();
	array2 = new Array();
	array3 = new Array();
	array4 = new Array();
	array5 = new Array();
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
var array4 = new Array();
var array5 = new Array();
var avg2=0,avg5=0;
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
            	if(type!=23){
                	for(var i=0;i<result.rows.length;i++){
                		array0.push(result.rows[i].name);
                		array1.push(result.rows[i].worktime);
                		array2.push(result.rows[i].weldtime);
                		array3.push(result.rows[i].workratio);
                		array4.push(result.rows[i].effectiveratio);
                		array5.push(result.rows[i].boottime);

                	}
            	}else{
            		if(result.rows.length>=5){
            			for(var i=0;i<5;i++){
                    		array0.push(result.rows[i].name);
                    		array1.push(result.rows[i].worktime);
                    		array2.push(result.rows[i].weldtime);
                    		array3.push(result.rows[i].workratio);
                    		array4.push(result.rows[i].effectiveratio);
                    		array5.push(result.rows[i].boottime);

                    	}
            		}else{
            			for(var i=0;i<result.rows.length;i++){
                    		array0.push(result.rows[i].name);
                    		array1.push(result.rows[i].worktime);
                    		array2.push(result.rows[i].weldtime);
                    		array3.push(result.rows[i].workratio);
                    		array4.push(result.rows[i].effectiveratio);
                    		array5.push(result.rows[i].boottime);

                    	}
            		}
            		if(result.rows.length>=10){
            			for(var i=result.rows.length-5;i<result.rows.length;i++){
                    		array0.push(result.rows[i].name);
                    		array1.push(result.rows[i].worktime);
                    		array2.push(result.rows[i].weldtime);
                    		array3.push(result.rows[i].workratio);
                    		array4.push(result.rows[i].effectiveratio);
                    		array5.push(result.rows[i].boottime);

                    	}
            		}else if(result.rows.length>5 && result.rows.length<10){
            			for(var i=5;i<result.rows.length;i++){
                    		array0.push(result.rows[i].name);
                    		array1.push(result.rows[i].worktime);
                    		array2.push(result.rows[i].weldtime);
                    		array3.push(result.rows[i].workratio);
                    		array4.push(result.rows[i].effectiveratio);
                    		array5.push(result.rows[i].boottime);

                    	}
            		}
            		avg2 = 0,avg5 = 0;
            		for(var i=0;i<result.rows.length;i++){
                		avg2 += result.rows[i].weldtime;
                		avg5 += result.rows[i].boottime;
                	}
                	if(result.rows.length!=0){
                    	avg2 = (avg2/result.rows.length).toFixed(2);
                    	avg5 = (avg5/result.rows.length).toFixed(2);
                	}
            		
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
			data:['焊接时长(h)','开机时长(h)','上班时长(h)','工作效率','有效焊接率'],
			x : 'left',
			left : '50'
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'120',
			bottom: '20',
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
			name : '    '+dgname/*,
			axisLabel : {
				rotate: rotatenum, //x轴文字倾斜
			    interval:interval //0:允许x轴文字全部显示并重叠
			}*/
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
			name:'焊接时长(h)',
			type:'bar',
            barMaxWidth:20,//最大宽度
            color:['#C23531'],
			data:array2,
			label : {
				normal : {
					position : 'top',
					show : true //显示每个折点的值
				}
			}
		},{
			name:'开机时长(h)',
			type:'bar',
            barMaxWidth:20,//最大宽度
            color:['#2F4554'],
            
			data:array5,
			label : {
				normal : {
					position : 'top',
					show : true //显示每个折点的值
				}
			}
		},{
			name:'制度时长(h)',
            min: 0,
            max: 100,//最大最小值
            interval: 20,//间隔
			type:'line',
            barMaxWidth:20,//最大宽度
            color:['#61A0A8'],
			data:array1,
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
            color:['#D48265'],
            barMaxWidth:20,//最大宽度
			data:array3,
			label : {
				normal : {
					position : 'top',
					show : true, //显示每个折点的值
					formatter : '{c}%'
				}
			}
		},{
			name:'有效焊接率',
            min: 0,
            max: 100,//最大最小值
            interval: 20,//间隔
			type:'line',
            yAxisIndex: 1,
            barMaxWidth:20,//最大宽度
            color:['#91C7AE'],
			data:array4,
			label : {
				normal : {
					position : 'top',
					show : true, //显示每个折点的值
					formatter : '{c}%'
				}
			}
		}]
	}
	//项目部时添加标志线
	if(type==23){
		option.series[0].markLine={data: [{yAxis: avg2, name: "焊接平均时长"}],
				label: {normal: {position: "middle",color:"#C23531",formatter: "{b}: {c}h"}},
		        itemStyle : {normal: { lineStyle: { color:"#C23531"}}}};
		option.series[1].markLine={data: [{yAxis: avg5, name: "开机平均时长"}],
				label: {normal: {position: "middle",color:"#2F4554",formatter: "{b}: {c}h"}},
		        itemStyle : {normal: { lineStyle: { color:"#2F4554"}}}};
	}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();
	//重定义图表宽度
	$("#charts").width("100%");
	if (array0.length > 3) {
		var maxlength = array0[0];
		for (var i = 0; i < array0.length; i++) {
			if (array0[i].length > maxlength.length) {
				maxlength = array0[i];
			}
		}
		var width = array0.length * maxlength.length * 18; //最长组织机构名字每个字节算18px
		if ($("#charts").width() < width) {
			$("#charts").width(width);
		}
	}
	echarts.init(document.getElementById('charts')).resize();
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
				title : dgname,
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "worktime",
				title : "制度时长(h)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "boottime",
				title : "开机时长(h)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "shutdowntime",
				title : "关机时长(h)",
				width : 100,
				halign : "center",
				align : "center",
				hidden: true
			},{
				field : "standbytime",
				title : "待机时长(h)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "weldtime",
				title : "焊接时长(h)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "sjratio",
				title : "上机率(%)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "effectiveratio",
				title : "有效焊接率(%)",
				width : 100,
				halign : "center",
				align : "center"
			},{
				field : "workratio",
				title : "工作效率(%)",
				width : 100,
				halign : "center",
				align : "center"
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
	echarts.init(document.getElementById('charts')).resize();
}
