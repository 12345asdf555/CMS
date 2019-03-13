$(function(){
	typecombobox();
	CompanyEfficiencyDatagrid();
})
var chartStr = "";
$(document).ready(function(){
	showcompanyEfficiencyChart(0);
})

var min="", max ="",dtoTime1,dtoTime2,charts;
function setParam(){
	chartStr = "";
	var parent = $('#parent').combobox('getValue');
	var nextparent = $("#nextparent").val();
	dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "?parent="+parent+"&nextparent="+nextparent+"&dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2+"&min="+min+"&max="+max;
}

function showcompanyEfficiencyChart(num){
	setParam();
	var array1 = new Array();
	var array2 = new Array();
	var Series = [];
	 $.ajax({  
         type : "post",  
         async : false,
         url : "caustChart/getCaustEfficiencyChart"+chartStr,
         data : {},  
         dataType : "json", //返回数据形式为json  
         success : function(result) {  
             if (result) {
            	 if(result.ary.length>0){
            		 for(var i=0;i<result.ary.length;i++){
                       	array1 = result.ary[i].num1;
                       	Series.push({
            				name:'工时分布(1:1)',
            				type:'bar',
            	            barMaxWidth:20,//最大宽度
            				data:result.ary[i].num2,
            		        label: {
            		            normal: {
            		                position: 'top',
            		                show: true,//显示每个折点的值
                 					formatter: '{c}%'  
            		            }
            		        }
                       	});
                      }
                  }else{
                	  Series.push({
                     		name : '工时分布(1:1)',
                     		type :'bar',
                     		data : ''
                      });
                  }
            }
         },
        error : function(errorMsg) {  
             alert("图表请求数据失败啦!");  
         }  
    });  
	if(num==0){
	   	//初始化echart实例
		charts = echarts.init(document.getElementById("companyEfficiencyChart"));
	}
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
			data:['工时分布(1:1)'],
			x : 'left',
			left : '50'
		},
		grid:{
			left:'50',//组件距离容器左边的距离
			right:'60',
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
			name : '时间(h)'
		},
		yAxis:{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name : '工时分布(%)',
			axisLabel: {  
                  show: true,  
                  interval: 'auto',  
                  formatter: '{value}%'  
            },  
            show: true  
		},
		series:[
		]
	}
	option.series = Series;
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	//echarts 点击事件
	charts.on('click', function (param) {
		var str = new Array();
		str = param.name.split("-");
		min = str[0],max=str[1];
	    CompanyEfficiencyDatagrid();
	});
	$("#chartLoading").hide();
	//重定义图表宽度
	$("#companyEfficiencyChart").width("100%");
	if(array1.length>7){
		var width = (array1.length-7) * 40;
		$("#companyEfficiencyChart").width($("#companyEfficiencyChart").width()+width);
	}
	charts.resize();
}

function typecombobox(){
	$.ajax({  
      type : "post",  
      async : false,
      url : "companyChart/getCaust?parent="+$("#nextparent").val(),  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {  
          if (result) {
              var optionStr = '';
              for (var i = 0; i < result.ary.length; i++) {  
                  optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                          + result.ary[i].name + "</option>";
              }
              $("#parent").html(optionStr);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
	}); 
	$("#parent").combobox();
	var data = $("#parent").combobox('getData');
	$("#parent").combobox('select',data[0].value);
}

function CompanyEfficiencyDatagrid(){
	setParam();
	$("#companyEfficiencyTable").datagrid( {
		fitColumns : true,
		height : $("#bodydiv").height() - $("#companyEfficiencyChart").height()-$("#companyEfficiency_btn").height()-45,
		width : $("#bodydiv").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "companyChart/getCompanyEfficiency"+chartStr,
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '事业部id',
			width : 100,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'iname',
			title : '事业部',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value,row,index){
				return "<a href='caustChart/goCaustEfficiency?nextparent="+row.id+"&parentime1="+dtoTime1+"&parentime2="+dtoTime2+"'>"+value+"</a>";
			}
		}, {
			field : 'wname',
			title : '焊工姓名',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'wid',
			title : '焊工编号',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'weldtime',
			title : '焊接时长(h)',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'num',
			title : '完成焊口数',
			width : 150,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'dyne',
			title : '总达因值',
			width : 150,
			halign : "center",
			align : "center",
			hidden : true
		}] ],
		pagination : true
	});
}

function serachEfficiencyCompany(){
	min="",max="";
	$("#nextparent").val("");
	$("#chartLoading").show();
	setTimeout(function() {
		showcompanyEfficiencyChart(1);
		CompanyEfficiencyDatagrid();
	}, 500)
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#companyEfficiencyTable").datagrid('resize', {
		height : $("#bodydiv").height() - $("#companyEfficiencyChart").height()-$("#companyEfficiency_btn").height()-45,
		width : $("#bodydiv").width()
	});
	charts.resize();
}
