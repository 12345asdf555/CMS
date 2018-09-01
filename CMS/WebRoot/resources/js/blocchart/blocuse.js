$(function(){
	typecombobox();
	CaustUseDatagrid();
})
var chartStr = "";
$(document).ready(function(){
	showblocUseChart();
})
var dtoTime1,dtoTime2;
function setParam(){
	var type = $('#type').combobox('getValue');
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "?type="+type+"&dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2;
}

var charts;
var array1 = new Array();
var array2 = new Array();
function showblocUseChart(){
	setParam();
	 $.ajax({  
         type : "post",  
         async : false, //同步执行  
         url : "blocChart/getBlocUse"+chartStr,
         data : {},  
         dataType : "json", //返回数据形式为json  
         success : function(result) {  
             if (result) {  
                 for(var i=0;i<result.rows.length;i++){
                  	array1.push(result.rows[i].name +"\n"+ result.rows[i].type);
                 	array2.push(result.rows[i].time);
                 }
             }  
         },  
        error : function(errorMsg) {  
             alert("图表请求数据失败啦!");  
         }  
    }); 
   	//初始化echart实例
	charts = echarts.init(document.getElementById("blocUseChart"));
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
			data:['时长(h)']
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'140',
			bottom:'40',
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
			name: '厂家-类型'
		},
		yAxis:{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name: '焊接平均时长(h)'
		},
		series:[
			{
				name:'时长(h)',
				type:'bar',
	            barMaxWidth:20,//最大宽度
				data:array2,
				label : {
					normal : {
						position : 'top',
						show : true //显示每个折点的值
					}
				}
			}
		]
	}
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
 	$("#chartLoading").hide();
}

function CaustUseDatagrid(){
	setParam();
	$("#blocUseTable").datagrid( {
		fitColumns : true,
		height : $("#bodydiv").height() - $("#blocUseChart").height()-$("#blocUse_btn").height()-15,
		width : $("#bodydiv").width(),
		idField : 'id',
		url : "blocChart/getBlocUse"+chartStr,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'fid',
			title : 'id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'typeid',
			title : '类型id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'fname',
			title : '厂家',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value,row,index){
				return '<a href="junctionChart/goUse?manuid='+row.fid+'&manutype='+row.typeid+'&time1='+dtoTime1+'&time2='+dtoTime2+'">'+value+'</a>';
			}
		}, {
			field : 'type',
			title : '型号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'time',
			title : '平均时长(h)',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'num',
			title : '数量(台)',
			width : 100,
			halign : "center",
			align : "center"
		}] ]
	});
}

function typecombobox(){
	$.ajax({  
      type : "post",  
      async : false,
      url : "blocChart/getCaust",  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {  
          if (result) {
              var optionStr = '';
              for (var i = 0; i < result.ary.length; i++) {  
                  optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                          + result.ary[i].name + "</option>";
              }
              $("#type").html(optionStr);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
	}); 
	$("#type").combobox();
	var data = $("#type").combobox('getData');
	$("#type").combobox('select',data[0].value);
}

function serachblocUse(){
	$("#chartLoading").show();
	array1 = new Array();
	array2 = new Array();
	chartStr = "";
	setTimeout(function(){
		CaustUseDatagrid();
		showblocUseChart();
	},500);
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#blocUseTable").datagrid('resize', {
		height : $("#bodydiv").height() - $("#blocUseChart").height()-$("#blocUse_btn").height()-15,
		width : $("#bodydiv").width()
	});
	echarts.init(document.getElementById('blocUseChart')).resize();
}
