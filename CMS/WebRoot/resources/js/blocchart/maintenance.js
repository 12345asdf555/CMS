$(function(){
	flagnum=1;
	parentCombobox();
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
				},
				error : function(errorMsg){
					alert("数据请求失败，请联系系统管理员!");
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
		activeurl = "blocChart/getMaintenanceratio?flag=0";
	}else if(type==21){
		position = 0;
		activeurl = "blocChart/getMaintenanceratio?flag=1";
	}else if(type==22){
		position = 1;
		activeurl = "blocChart/getMaintenanceratio?flag=2";
	}else if(type==23){
		position = 0;
		activeurl = "blocChart/getMaintenanceratio?flag=3";
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
	var bootomnum,rotatenum;
	if(position==0){
		bootomnum=20,rotatenum=0;
	}else{
		bootomnum=70,rotatenum=50;
	}
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
            		array2.push(result.rows[i].total);
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
			data : ['工作','待机','关机']
		},
//		grid:{
//			left:'50',//组件距离容器左边的距离
//			right:'4%',
//			bottom:bootomnum,
//			containLaber:true//区域是否包含坐标轴刻度标签
//		},
		toolbox:{
			feature:{
				saveAsImage:{}//保存为图片
			},
			right:'2%'
		},
		series:[{
			name:'设备利用率',
			type:'pie',
            radius : '55%',
            center : ['40%', '50%'],
			data:[
                {value:4, name:'工作'},
                {value:5, name:'待机'},
                {value:45, name:'关机'}
            ]
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
}
