$(function(){
	flagnum=1;
	parentCombobox();
//	dgDatagrid();
})

$(document).ready(function(){
//	showChart();
})

var type,flagnum;
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

var activeurl = "";
function serach(){
	if(flagnum!=1){
		$("#chartLoading").show();
	}
	flagnum = 0;
	if(type==20){
		activeurl = "blocChart/getFaultratio?flag=0";
	}else if(type==21){
		activeurl = "blocChart/getFaultratio?flag=1";
	}else if(type==22){
		activeurl = "blocChart/getFaultratio?flag=2";
	}else if(type==23){
		activeurl = "blocChart/getFaultratio?flag=3";
	}
	array1 = new Array();
	array2 = new Array();
	setTimeout(function() {
		dgDatagrid();
		showChart();
	}, 500);
}

var chartStr = "",parentid,dtoTime1,dtoTime2;

function setParam(){
	parentid = $("#parent").combobox('getValue');
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "&parent="+parentid+"&time1="+dtoTime1+"&time2="+dtoTime2;
}

var array1 = new Array();
var array2 = new Array();
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
            		array1.push(result.rows[i].type);
            		if(result.rows[i].faultratio!=0){
    	        		array2.push({
    	        			value : result.rows[i].faultratio,
    	        			name : result.rows[i].type
    	        		});
            		}
            		
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
			name:'设备故障率',
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
			field : "type",
			title : "故障类型",
			width : 100,
			halign : "center",
			align : "left",
			formatter : function(value,row,index){
				return "<a href='junctionChart/goFaultDetail?typeid="+row.typeid+chartStr+"'>"+value+"</a>";
			}
		},{
			field : "typeid",
			title : "类型id",
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		},{
			field : "faultratio",
			title : "故障率(%)",
			width : 100,
			halign : "center",
			align : "left"
		},{
			field : "faultnum",
			title : "故障次数",
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
		height : $("#bodydiv").height() - $("#charts").height()-$("#search_btn").height()-15,
		width : $("#bodydiv").width()
	});
	echarts.init(document.getElementById('charts')).resize();
}
