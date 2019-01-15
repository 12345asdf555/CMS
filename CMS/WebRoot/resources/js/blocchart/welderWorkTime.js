$(function(){
	flagnum=1;
	parentCombobox();
	dgDatagrid();
})


/*$(document).ready(function(){
	showChart();
})*/

//获取组织机构下拉框
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


function serach(){
	chartStr = "";
	dgDatagrid();
	array1.length = 0;
	array2.length = 0;
	array3.length = 0;
}

var array1 = new Array();
var array2 = new Array();
var array3 = new Array();
var avgworktime=0,avgtime=0;
/*function showChart(){
	 $.ajax({  
        type : "post",  
        async : false,
        url : "blocChart/getWelderWorkTime",
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {  
            if (result) {
            	for(var i=0;i<result.rows.length;i++){
            		array1.push(result.rows[i].welderno);
            		array2.push(result.rows[i].worktime);
            		array3.push(result.rows[i].time);
            	}
            	avgworktime = result.avgWorktime();
            	avgtime = result.avgTime();
            }  
        },  
       error : function(errorMsg) {  
            alert("请求数据失败啦,请联系系统管理员!");  
        }  
   }); 
	 chart();
}*/
function showChart(){
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
			data:['焊接时长(h)','开机时长(h)','制度时长(h)','工作效率','有效焊接率'],
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
			data: array1,
			name : '    焊工'
		},
		yAxis:[{
			type: 'value',//value:数值轴，category:类目轴，time:时间轴，log:对数轴
			name : '时长(h)'
		}],
		series:[{
			name:'焊接时长(h)',
			type:'bar',
            barMaxWidth:20,//最大宽度
            color:['#C23531'],
			data:array2,
            markLine: {
                data: [
                    {yAxis: avgworktime, name: '平均焊接时长'}
                ],
        		label: {
                    normal: {
                        position: 'end',
                        color:'#000099',//字体颜色
                        formatter: '{b}: {c}h' //标志线说明
                    }
                },
		        itemStyle : { 
		            normal: { 
		                lineStyle: { 
		                    color:'#000099', //标志线颜色
		                }
		            } 
		        }
            } ,
			label : {
				normal : {
					position : 'top',
					show : true //显示每个折点的值
				}
			}
		},{
			name:'工作时长(h)',
			type:'bar',
            barMaxWidth:20,//最大宽度
            color:['#2F4554'],
			data:array3,
            markLine: {
                data: [
                    {yAxis: avgtime, name: '平均工作时长'}
                ],
        		label: {
                    normal: {
                        position: 'end',
                        color:'#000099',//字体颜色
                        formatter: '{b}: {c}h' //标志线说明
                    }
                },
		        itemStyle : { 
		            normal: { 
		                lineStyle: { 
		                    color:'#000099', //标志线颜色
		                }
		            } 
		        }
            } ,
			label : {
				normal : {
					position : 'top',
					show : true //显示每个折点的值
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
	echarts.init(document.getElementById('charts')).resize();
}

function dgDatagrid(){
	 $("#dg").datagrid( {
			fitColumns : true,
			height : $("#dgdiv").height()-50,
			width : $("#dgdiv").width(),
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50],
			url : "blocChart/getWelderWorkTime?parent="+$("#parent").combobox('getValue')+"&time1="+$("#dtoTime1").datetimebox('getValue')+"&time2="+$("#dtoTime2").datetimebox('getValue'),
			singleSelect : true,
			rownumbers : true,
			showPageList : false,
			pagination : true,
			columns :[[{
				field : "name",
				title : "姓名",
				width : 10,
				halign : "center",
				align : "center"
			},{
				field : "welderno",
				title : "编号",
				width : 10,
				halign : "center",
				align : "center"
			},{
				field : "worktime",
				title : "焊接时长(h)",
				width : 10,
				halign : "center",
				align : "center"
			},{
				field : "time",
				title : "工作时长(h)",
				width : 10,
				halign : "center",
				align : "center"
			}]],
		    toolbar : '#dgTable_btn',
		    onLoadSuccess: function(data){
		    	/*array1.push(result.rows[i].welderno);
        		array2.push(result.rows[i].worktime);
        		array3.push(result.rows[i].time);*/
        	
	        	avgworktime = data.avgWorktime;
	        	avgtime = data.avgTime;
	        	if(data.rows.length!=0){
	        		for(var i=0;i<data.rows.length;i++)
	        		array1.push(data.rows[i].welderno);
	        		array2.push(data.rows[i].worktime);
	        		array3.push(data.rows[i].time);
	        	}
		    	
//		    	showChart();
		    }
	 })
}



//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格，图表高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#dgdiv").height(),
		width : $("#dgdiv").width()
	});
	echarts.init(document.getElementById('charts')).resize();
}


/*function loadData(){
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
}*/

