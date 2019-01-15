$(function(){
	flagnum=1;
	parentCombobox();
	dgDatagrid();
})

$(document).ready(function(){
	showChart();
})

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
var charts,option;
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
			data:['焊接时长(h)','工作时长(h)'],
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
		    	//加载图表
		    	array1.length = 0;
		    	array2.length = 0;
		    	array3.length = 0;
	        	avgworktime = data.avgWorktime;
	        	avgtime = data.avgtime;
	        	if(data.rows.length!=0){
	        		for(var i=0;i<data.rows.length;i++){
		        		array1.push(data.rows[i].welderno);
		        		array2.push(data.rows[i].worktime);
		        		array3.push(data.rows[i].time);
	        		}
	        	}
		    	showChart();
		    }
	 })
}

//导出到Excel
function exporWelderWorkTime(){
	$.messager.confirm("提示", "文件默认保存在浏览器的默认路径，<br/>如需更改路径请设置浏览器的<br/>“下载前询问每个文件的保存位置“属性！", function(result) {
		if (result) {
			var page = $('#dg').datagrid('getPager').data("pagination").options.pageNumber;
			var rows = $('#dg').datagrid('getPager').data("pagination").options.pageSize;
			var url = "export/exporWelderWorkTime?parent="+$("#parent").combobox('getValue')+"&time1="+$("#dtoTime1").datetimebox('getValue')+
			"&time2="+$("#dtoTime2").datetimebox('getValue')+"&page="+page+"&rows="+rows+"&status="+$("input[name='importDg']:checked").val();;
			var img = new Image();
			img.src = url; // 设置相对路径给Image, 此时会发送出请求
			url = img.src; // 此时相对路径已经变成绝对路径
			img.src = null; // 取消请求
			window.location.href = url;
		}
	});
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格，图表高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#dgdiv").height()-50,
		width : $("#dgdiv").width()
	});
	echarts.init(document.getElementById('charts')).resize();
}