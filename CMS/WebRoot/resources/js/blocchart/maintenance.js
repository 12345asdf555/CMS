$(function(){
	flagnum=1;
	parentCombobox();
})

$(document).ready(function(){
//	showChart();
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

function dealwith(num,url){
	$("#charts").show();
//	$("#itemcharts1").hide();
	$("#itemcharts2").hide();
	position = num;
	activeurl = url;
	setTimeout(function() {
		dgDatagrid();
		showChart();
	}, 500);
}

var activeurl = "";
function serach(){
	if(flagnum!=1){
		$("#chartLoading").show();
	}
	flagnum = 0;
	if(type==20){
		dealwith(0,"blocChart/getMaintenanceratio?flag=0");
		$("#explain").html("<span>设备维修率</span><hr><ul><li>展现某一时间段内，各部门的设备维修次数总占比</li></ul>");
	}else if(type==21){
		dealwith(0,"blocChart/getMaintenanceratio?flag=1");
		$("#explain").html("<span>设备维修率</span><hr><ul><li>展现某一时间段内，各部门的设备维修次数总占比</li></ul>");
	}else if(type==22){
		dealwith(1,"blocChart/getMaintenanceratio?flag=2");
		$("#explain").html("<span>设备维修率</span><hr><ul><li>展现某一时间段内，各部门的设备维修次数总占比</li></ul>");
	}else if(type==23){
		$("#charts").hide();
		$("#explain").html("<span>设备维修率</span><hr><ul><li>展现某一时间段内，该部门的不同厂商不同设备类型的焊机总费用及设备维修费用</li></ul>");
//		$("#itemcharts1").show();
		$("#itemcharts2").show();
		position = 0;
		activeurl = "itemChart/getItemTypeMaintain?flag=3";
		setTimeout(function() {
			itemDgDatagrid();
			showItemChart();
		}, 500);
	}
	array1 = new Array();
	array2 = new Array();
	array3 = new Array();
	array4 = new Array();
	array5 = new Array();
	array6 = new Array();
	array7 = new Array();
	array8 = new Array();
}

var chartStr = "",dtoTime1,dtoTime2;

function setParam(){
	parentid = $("#parent").combobox('getValue');
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "&parent="+parentid+"&time1="+dtoTime1+"&time2="+dtoTime2;
}

var array1 = new Array();
var array2 = new Array();
var array3 = new Array();
var array4 = new Array();
var array5 = new Array();
var array6 = new Array();
var array7 = new Array();
var array8 = new Array();
var avg = 0;
var sumnum = 0,faultnum = 0,summoney = 0,sumrmoney = 0;
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
            		if(result.rows[i].proportion!=0){
    	        		array2.push({
    	        			value : result.rows[i].proportion*100,
    	        			name : result.rows[i].name
    	        		});
            		}
            		
            	}
            	for(var i=0;i<result.ary.length;i++){
            		sumnum = result.ary[i].sumnum;
            		faultnum = result.ary[i].faultnum;
            		sumfaultmaintenance = result.ary[i].sumfaultmaintenance;
            		sumrmoney = result.ary[i].sumrmoney;
            		summoney = result.ary[i].summoney;
            		
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
			name:'设备维修率',
			type:'pie',
            radius : '80%',
            center : ['40%', '50%'],
//            label: {
//                normal: {
//                    position: 'inner'//文字显示在里面
//                }
//            },
			data:array2,
      		itemStyle : {
      			normal: {
      				label : {
      					formatter: function(param){
      						return param.name+"\n"+param.value+"%";
      					}
      				}
      			}
      		}
		}]
	}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();
	domresize();
}

function showItemChart(){
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
	           		array3.push(result.ary[i].manufacturername);
	           		array7.push(result.ary[i].maintainmoney);
	           		array8.push(result.ary[i].machinemoney);
	           	}
	           	for(var i=0;i<result.arys.length;i++){
	           		array4.push(result.arys[i].manufacturername);
	           		array5.push(result.arys[i].maintainnum);
	           		array6.push(result.arys[i].faultnum);
	           	}
           }  
       },  
       error : function(errorMsg) {  
           alert("请求数据失败啦,请联系系统管理员!");  
       }  
	}); 
//	showItemNumChart();
	showItemMoneyChart();
}

//显示项目部设备费用及维修费用对比图
function showItemMoneyChart(){
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
				bottom:'70',
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
				data: array3,
				axisLabel : {
					rotate: 30, //x轴文字倾斜
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
					data:array7
				},
				{
					name:'设备总费用',
					type:'bar',
		            barMaxWidth:20,//最大宽度
					data:array8
				}
			]
		}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	$("#chartLoading").hide();
	domresize();

}

//设备维修次数-故障次数对比图
function showItemNumChart(){
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
	$("#dg1").show();
	$("#dg2").hide();
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
			field : "proportion",
			title : "维修次数占比",
			width : 100,
			halign : "center",
			align : "left"
		},{
//			field : "faultratio",
//			title : "故障次数",
//			width : 100,
//			halign : "center",
//			align : "left"
//		},{
//			field : "faultmaintenanceratio",
//			title : "故障维修率(%)",
//			width : 100,
//			halign : "center",
//			align : "left"
//		},{
			field : "rmoney",
			title : "维护费用(元)",
			width : 100,
			halign : "center",
			align : "left"
		},{
			field : "mmoney",
			title : "设备费用(元)",
			width : 100,
			halign : "center",
			align : "left"
		}]],
		onLoadSuccess : function(data){
			$('#dg').datagrid('insertRow',{
				index: 0,// 索引从0开始
				row: {
					total: '总维护次数：'+sumnum,
					proportion: '总维护占比：'+1,
//					faultratio:'总故障次数：'+faultnum,
//					faultmaintenanceratio:'总故障维修率：'+sumfaultmaintenance,
					rmoney:'维护总费用：'+sumrmoney,
					mmoney:'焊机总费用：'+summoney
				}

			});
		}
	 })
	

}

function itemDgDatagrid(){
	setParam();
	$("#dg1").hide();
	$("#dg2").show();
	$("#itemdg").datagrid( {
		fitColumns : true,
		height : $("#body").height() - $("#itemcharts2").height()-$("#search_btn").height()-15,
		width : $("#body").width(),
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		url : "itemChart/getItemTypeMaintainList?flag=3"+chartStr,
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns :[[{
			field : "manufacturername",
			title : "厂商-类型",
			width : 100,
			halign : "center",
			align : "left",
			formatter : function(value,row,index){
				return "<a href='"+encodeURI("maintain/goMaintain?str=i.fid %3D "+row.itemid+" and e.fid %3D "+row.manufacturerid+" and wm.ftype_id %3D "+row.machinetypeid)+"'>"+value+"</a>";
			}
		},{
			field : "maintainnum",
			title : "设备维修次数",
			width : 100,
			halign : "center",
			align : "left"
		},{
//			field : "faultnum",
//			title : "设备故障次数",
//			width : 100,
//			halign : "center",
//			align : "left"
//		},{
			field : "maintainmoney",
			title : "设备维修费用",
			width : 100,
			halign : "center",
			align : "left"
		},{
			field : "machinemoney",
			title : "设备费用",
			width : 100,
			halign : "center",
			align : "left"
		},{
			field : "manufacturerid",
			title : "厂商id",
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		},{
			field : "machinetypeid",
			title : "设备类型id",
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		},{
			field : "itemid",
			title : "设备类型id",
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}]]
	 })
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格，图表高宽
function domresize() {
	if(!$("#dg1").is(":hidden")){
		$("#dg").datagrid('resize', {
			height : $("#body").height() - $("#charts").height()-$("#search_btn").height()-15,
			width : $("#body").width()
		});
		echarts.init(document.getElementById('charts')).resize();
	}else{

		$("#itemdg").datagrid('resize', {
			height : $("#body").height() - $("#itemcharts2").height()-$("#search_btn").height()-15,
			width : $("#body").width()
		});
//		echarts.init(document.getElementById('itemcharts1')).resize();
		echarts.init(document.getElementById('itemcharts2')).resize();
	}
}
