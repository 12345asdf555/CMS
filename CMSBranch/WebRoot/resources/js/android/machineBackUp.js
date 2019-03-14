var insfid;
var charts;
var websocketURL, dic, starows, redata, symbol=0, welderName;
var worknum=0, standbynum=0, overproofnum=0, offnum=0, overtimenum=0, flag = 0;
var liveary = new Array(), machine = new Array(),miday = new Array();
$(function() {
	getMachine(insfid);
	//初始化
	showChart();
	websocketUrl();
	websocket();
	//状态发生改变
	$("#status").combobox({
		onChange : function(newValue, oldValue){
			statusClick(newValue);
		}
	});
})

//获取超时待机基数，超时待机信息
function websocketUrl() {
	$.ajax({
		type : "post",
		async : false,
		url : "td/AllTdbf",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				websocketURL = eval(result.web_socket);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
}

//获取焊机及焊工信息
function getMachine(insfid) {
	$.ajax({
		type : "post",
		async : false,
		url : "td/getAllPosition",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				miday.length = 0;
				machine = eval(result.rows);
				$("#machinenum").html(machine.length);
				$("#off").html(machine.length);
//				showChart();
				$("#curve").html();
				for(var i=0;i<machine.length;i++){
					miday.push(machine[i].fid);
					var str = '<div id="machine'+machine[i].fid+'" style="width:200px;height:120px;float:left;display:none">'+
						'<div style="float:left;width:40%;height:100%;"><a href="android/goMachinecurve?value='+machine[i].fid+'&valuename='+machine[i].fequipment_no+'"><img id="img'+machine[i].fid+'" src="resources/images/welder_04.png" style="height:100px;width:100%;padding-top:10px;"></a></div>'+
						'<div style="float:left;width:60%;height:100%;">'+
						'<ul><li style="width:100%;height:19px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">设备编号：<span id="m1'+machine[i].fid+'">'+machine[i].fequipment_no+'</span></li>'+
						'<li style="width:100%;height:19px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">焊缝编号：<span id="m2'+machine[i].fid+'">--</span></li>'+
						'<li style="width:100%;height:19px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">操作人员：<span id="m3'+machine[i].fid+'">--</span></li>'+
						'<li style="width:100%;height:19px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">焊接电流：<span id="m4'+machine[i].fid+'">--A</span></li>'+
						'<li style="width:100%;height:19px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">焊接电压：<span id="m5'+machine[i].fid+'">--V</span></li>'+
						'<li style="width:100%;height:19px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis">焊机状态：<span id="m6'+machine[i].fid+'">关机</span></li></ul><input id="status'+machine[i].fid+'" type="hidden" value="2"></div></div>';
					$("#curve").append(str);
				}
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
	
	//获取焊工信息
	$.ajax({  
	      type : "post",  
	      async : false,
	      url : "td/getLiveWelder",  
	      data : {},  
	      dataType : "json", //返回数据形式为json  
	      success : function(result) {
	          if (result) {
	        	  welderName=eval(result.rows);
	        	  $('#itemname').html(welderName[0].fitemname);
	          }  
	      },
	      error : function(errorMsg) {  
	          alert("数据请求失败，请联系系统管理员!");  
	      }  
	 });
}

//执行websocket
function websocket() {
	if (typeof (WebSocket) == "undefined") {
		alert("您的浏览器不支持WebSocket");
		return;
	}
	webclient();
}

function webclient() {
	try {
		socket = new WebSocket(websocketURL);
	} catch (err) {
		alert("地址请求错误，请清除缓存重新连接！！！")
	}
	setTimeout(function() {
		if (socket.readyState != 1) {
			alert("与服务器连接失败,请检查网络设置!");
		}
	}, 10000);
	socket.onopen = function() {
		//监听加载状态改变  
		document.onreadystatechange = completeLoading();

		//加载状态为complete时移除loading效果 
		function completeLoading() {
			var loadingMask = document.getElementById('loadingDiv');
			loadingMask.parentNode.removeChild(loadingMask);
		}
	};
	socket.onmessage = function(msg) {
		redata = msg.data;
		iview();
		symbol=1;
	};
	//关闭事件
	socket.onclose = function(e) {
		if (e.code == 4001 || e.code == 4002 || e.code == 4003 || e.code == 4005 || e.code == 4006) {
			//如果断开原因为4001 , 4002 , 4003 不进行重连.
			return;
		} else {
			return;
		}
		// 重试3次，每次之间间隔5秒
		if (tryTime < 3) {
			setTimeout(function() {
				socket = null;
				tryTime++;
				var _PageHeight = document.documentElement.clientHeight,
					_PageWidth = document.documentElement.clientWidth;
				var _LoadingTop = _PageHeight > 61 ? (_PageHeight - 61) / 2 : 0,
					_LoadingLeft = _PageWidth > 215 ? (_PageWidth - 215) / 2 : 0;
				var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:' + _PageHeight + 'px;top:0;background:#f3f8ff;opacity:0.8;filter:alpha(opacity=80);z-index:10000;"><div style="position: absolute; cursor1: wait; left: ' + _LoadingLeft + 'px; top:' + _LoadingTop + 'px; width: auto; height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px; background: #fff url(resources/images/load.gif) no-repeat scroll 5px 10px; border: 2px solid #95B8E7; color: #696969;">""正在尝试第"' + tryTime + '"次重连，请稍候..."</div></div>';
				document.write(_LoadingHtml);
				ws();
			}, 5000);
		} else {
			tryTime = 0;
		}
	};
	//发生了错误事件
	socket.onerror = function() {
		alert("发生异常，正在尝试重新连接服务器！！！");
	}
}

//获取实时数据
function iview() {
	if(flag==0){
		if(machine!=null){
			document.getElementById("load").style.display="block";
			var sh = '<div id="show" style="align="center""><img src="resources/images/load.gif"/>正在加载，请稍等...</div>';
			$("#bodydiv").append(sh);
			document.getElementById("show").style.display="block";
		}
		window.setTimeout(function() {
			tempary = liveary;
			worknum=0, standbynum=0, overproofnum=0, offnum=machine.length-tempary.length, overtimenum=0;
			if(tempary.length!=0){
				for(var i=0;i<machine.length;i++){
					$("#machine"+machine[i].fid).hide();
				}
			}
			for(var j=0;j<tempary.length;j++){
				$("#m4"+tempary[j].fid).html(tempary[j].liveele);
				$("#m5"+tempary[j].fid).html(tempary[j].livevol);
				$("#m6"+tempary[j].fid).html(tempary[j].livestatus);
				$("#status"+tempary[j].fid).val(tempary[j].livestatusid);
				$("#img"+tempary[j].fid).attr("src",tempary[j].liveimg);
				var status = $("#status"+tempary[j].fid).val();
				if(status == 0||status == 3){
					worknum += 1;
				}else if(status == 1||status == 4){
					standbynum += 1;
				}
				if(status == 3){
					overproofnum += 1;
				}
				if(status == 4){
					overtimenum += 1;
				}
				var statusnum = $("#status").combobox('getValue');
				if(statusnum == 0){
					if(status == 0||status == 3){
						$("#machine"+tempary[j].fid).show();
					}
				}else if(statusnum == 1){
					if(status == 1||status == 4){
						$("#machine"+tempary[j].fid).show();
					}
				}else if(status == statusnum){
					$("#machine"+tempary[j].fid).show();
				}
			}
			$("#standby").html(standbynum);
			$("#work").html(worknum);
			$("#off").html(offnum);
			$("#overproof").html(overproofnum);
			$("#overtime").html(overtimenum);
			showChart();
    		document.getElementById("load").style.display ='none';
    		document.getElementById("show").style.display ='none';
		},5000);
		flag=2;
	}
	if(redata.length==291){
		for (var i = 0; i < redata.length; i += 97) {
	//		if (redata.substring(8 + i, 12 + i) != "0000") {
				if(miday.indexOf(parseInt(redata.substring(4 + i, 8+ i)))!=-1){
				if(machine!=null && machine!=""){
					$("#m3"+parseInt(redata.substring(4 + i, 8+ i))).html(redata.substring(8+i, 12+i));
					$("#m2"+parseInt(redata.substring(4 + i, 8+ i))).html(redata.substring(89 + i, 97 + i));
					var liveele = parseInt(redata.substring(12+i, 16+i));
		            var livevol = parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2));
		            var maxele = parseInt(redata.substring(61+i, 64+i));
		            var minele = parseInt(redata.substring(64+i, 67+i));
		            var maxvol = parseInt(redata.substring(67+i, 70+i));
		            var minvol = parseInt(redata.substring(70+i, 73+i));
					var mstatus = redata.substring(0 + i, 2 + i);
					$("#m4"+parseInt(redata.substring(4 + i, 8+ i))).html(parseInt(redata.substring(12+i, 16+i))+"A");
					$("#m5"+parseInt(redata.substring(4 + i, 8+ i))).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
					var livestatus,livestatusid,liveimg;
					switch (mstatus) {
					case "00":
						livestatus = "待机";
						livestatusid = 1;
						liveimg = "resources/images/welder_02.png";
						break;
					case "03":
						if(liveele>maxele || liveele<minele || livevol>maxvol || livevol<minvol){
							livestatus = "超标";
							livestatusid = 3;
							liveimg = "resources/images/welder_01.png";
						}else{
							livestatus = "工作";
							livestatusid = 0;
							liveimg = "resources/images/welder_03.png";
						}
						break;
					case "05":
						livestatus = "工作";
						livestatusid = 0;
						liveimg = "resources/images/welder_03.png";
						break;
					case "07":
						livestatus = "工作";
						livestatusid = 0;
						liveimg = "resources/images/welder_03.png";
						break;
					case "09":
						livestatus = "超时";
						livestatusid = 4;
						liveimg = "resources/images/welder_05.png";
						break;
					}
					if(liveary.length==0){
						liveary.push(
								{"fid":parseInt(redata.substring(4 + i, 8+ i)),
								"liveele":liveele+"A",
								"livevol":livevol+"V",
								"livestatus":livestatus,
								"livestatusid":livestatusid,
								"liveimg":liveimg})
					}else{
						var tempflag = false;
						for(var x=0;x<liveary.length;x++){
							if(liveary[x].fid == parseInt(redata.substring(4 + i, 8+ i))){
								tempflag = true;
								break;
							}
						}
						if(!tempflag){
							liveary.push(
									{"fid":parseInt(redata.substring(4 + i, 8+ i)),
									"liveele":liveele+"A",
									"livevol":livevol+"V",
									"livestatus":livestatus,
									"livestatusid":livestatusid,
									"liveimg":liveimg})
						}
					}
				}
			}
		}
	}
}

//饼图统计
function showChart(){
   	//初始化echart实例
	var charts = echarts.init(document.getElementById("piecharts"));
	//显示加载动画效果
	charts.showLoading({
		text: '稍等片刻,精彩马上呈现...',
		effect:'whirling'
	});
	option = {
		tooltip:{
			trigger: 'item',
			formatter: function(param){
				if(param.name=="其它"){
					return "";
				}else{
					return '<div>实时统计<div>'+'<div style="float:left;margin-top:5px;border-radius:50px;border:solid rgb(100,100,100) 1px;width:10px;height:10px;background-color:'+param.color+'"></div><div style="float:left;">&nbsp;'+param.name+'：'+param.value+'%<div>';
				}
			}
		},
		toolbox:{
			feature:{
				saveAsImage:{}//保存为图片
			},
			right:'2%'
		},
		series:[{
			name:'实时统计',
			type:'pie',
			radius : ['55%', '70%'],
            color:['#FFB90F','#7cbc16','#818181'],
			data:[
                {value:($("#standby").html()/$("#machinenum").html()*100).toFixed(2), name:'待机', id :1},
                {value:($("#work").html()/$("#machinenum").html()*100).toFixed(2), name:'工作', id :0},
                {value:($("#off").html()/$("#machinenum").html()*100).toFixed(2), name:'关机', id :2}
            ],
      		itemStyle : {
      			normal: {
      				label : {
      					formatter: function(param){
      						return param.name+"："+param.value+"%";
      					}
      				}
      			}
      		}
		},{
			name:'超标统计',
			type:'pie',
			radius : ['40%', '50%'],
            color:['#c4370c','#ffffff'],
			data:[
                {value:($("#overproof").html()/$("#machinenum").html()*100).toFixed(2), name:'超标', id :3},
                {value:(($("#machinenum").html()-$("#overproof").html())/$("#machinenum").html()*100).toFixed(2), name:'其它'}
            ],
            hoverAnimation:false,//鼠标悬停区域不放大
            label: {
                normal: {
                    show: false
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
      		itemStyle : {
      			normal: {
      				label : {
      					formatter: function(param){
      						return param.name+"："+param.value+"%";
      					}
      				}
      			}
      		}
		},{
			name:'超时待机统计',
			type:'pie',
			radius : ['25%', '35%'],
            color:['#55a7f3','#ffffff'],
			data:[
                {value:($("#overtime").html()/$("#machinenum").html()*100).toFixed(2), name:'超时待机', id :4},
                {value:(($("#machinenum").html()-$("#overtime").html())/$("#machinenum").html()*100).toFixed(2), name:'其它'}
            ],
            hoverAnimation:false,//鼠标悬停区域不放大
            label: {
                normal: {
                    show: false
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
      		itemStyle : {
      			normal: {
      				label : {
      					formatter: function(param){
      						return param.name+"："+param.value+"%";
      					}
      				}
      			}
      		}
		}]
	}
	// 1、清除画布
	charts.clear();
	// 2、为echarts对象加载数据
	charts.setOption(option);
    ///3、在渲染点击事件之前先清除点击事件
    charts.off('click');
	//隐藏动画加载效果
	charts.hideLoading();
	// 4、echarts 点击事件
	charts.on('click', function (param) {
		statusClick(param.data.id);
	});
	$("#chartLoading").hide();
}

//每30'刷新一次
var tempary = new Array();
window.setInterval(function(){
	tempary = liveary;
	var statusnum = $("#status").combobox('getValue')
	worknum=0, standbynum=0, overproofnum=0, offnum=machine.length-tempary.length, overtimenum=0;
	for(var i=0;i<machine.length;i++){
		$("#machine"+machine[i].fid).hide();
		if(statusnum==2){
			var offflag = true;
			for(var j=0;j<tempary.length;j++){
				if(machine[i].fid==tempary[j].fid){
					offflag = false;
				}
			}
			if(offflag){
				$("#m3"+machine[i].fid).html("--");
				$("#m4"+machine[i].fid).html("--A");
				$("#m5"+machine[i].fid).html("--V");
				$("#status"+machine[i].fid).val(2);
				$("#m6"+machine[i].fid).html("关机");
				$("#img"+machine[i].fid).attr("src","resources/images/welder_04.png");
				$("#machine"+machine[i].fid).show();
			}
		}
	}
	for(var j=0;j<tempary.length;j++){
		$("#m4"+tempary[j].fid).html(tempary[j].liveele);
		$("#m5"+tempary[j].fid).html(tempary[j].livevol);
		$("#m6"+tempary[j].fid).html(tempary[j].livestatus);
		$("#status"+tempary[j].fid).val(tempary[j].livestatusid);
		$("#img"+tempary[j].fid).attr("src",tempary[j].liveimg);
		var status = $("#status"+tempary[j].fid).val();
		if(status == 0||status == 3){
			worknum += 1;
		}else if(status == 1||status == 4){
			standbynum += 1;
		}
		if(status == 3){
			overproofnum += 1;
		}
		if(status == 4){
			overtimenum += 1;
		}
		if(statusnum == 0){
			if(status == 0||status == 3){
				$("#machine"+tempary[j].fid).show();
			}
		}else if(statusnum == 1){
			if(status == 1||status == 4){
				$("#machine"+tempary[j].fid).show();
			}
		}else if(status == statusnum){
			$("#machine"+tempary[j].fid).show();
		}
	}
	$("#standby").html(standbynum);
	$("#work").html(worknum);
	$("#off").html(offnum);
	$("#overproof").html(overproofnum);
	$("#overtime").html(overtimenum);
	showChart();
	//清空数组
	liveary.length = 0;
},30000);

//状态按钮点击事件
function statusClick(statusnum){
	$("#status").combobox('setValue',statusnum);
	for(var i=0;i<machine.length;i++){
		$("#machine"+machine[i].fid).hide();
		if(statusnum==2){
			var offflag = true;
			for(var j=0;j<tempary.length;j++){
				if(machine[i].fid==tempary[j].fid){
					offflag = false;
				}
			}
			if(offflag){
				$("#m3"+machine[i].fid).html("--");
				$("#m4"+machine[i].fid).html("--A");
				$("#m5"+machine[i].fid).html("--V");
				$("#status"+machine[i].fid).val(2);
				$("#m6"+machine[i].fid).html("关机");
				$("#img"+machine[i].fid).attr("src","resources/images/welder_04.png");
				$("#machine"+machine[i].fid).show();
			}
		}
	}
	
	for(var j=0;j<tempary.length;j++){
		var status = $("#status"+tempary[j].fid).val();
		if(statusnum == 0){
			if(status == 0||status == 3){
				$("#machine"+tempary[j].fid).show();
			}
		}else if(statusnum == 1){
			if(status == 1||status == 4){
				$("#machine"+tempary[j].fid).show();
			}
		}else if(status == statusnum){
			$("#machine"+tempary[j].fid).show();
		}
	}
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变图表高宽
function domresize() {
	echarts.init(document.getElementById('piecharts')).resize();
}
