
var machine = new Array(),time = new Array(),ele = new Array(),vol = new Array(),
machstatus = new Array(),work = new Array(),wait = new Array(),cbpd = new Array();
var dglength,websocketURL,timerele,socket,redata,series, chart,series1,chart1,dic,starows;
var symbol = 0,maxele = 0,minele = 0,maxvol = 0,minvol = 0,flag=0,noflag=0;

$(function() {
	var width = $("#treeDiv").width();
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
	$.ajax({
		type : "post",
		async : false,
		url : "td/getAllPosition",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				machine = eval(result.rows);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
	websocket();
})

function websocket() {
	if (typeof (WebSocket) == "undefined") {
		alert("您的浏览器不支持WebSocket");
		return;
	}
	webclient();
}
;
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
		//				datatable();
		//监听加载状态改变  
		document.onreadystatechange = completeLoading();

		//加载状态为complete时移除loading效果 
		function completeLoading() {
			var loadingMask = document.getElementById('loadingDiv');
			loadingMask.parentNode.removeChild(loadingMask);
		}
	/*				setTimeout(function(){
						if(symbol==0){
							alert("连接成功，但未接收到任何数据");
						}
					},5000);*/
	};
	socket.onmessage = function(msg) {
		redata = msg.data;
		iview();
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
		aler("发生异常，正在尝试重新连接服务器！！！");
	}
}


function elecurve() {
	Highcharts.setOptions({
		global : {
			useUTC : false
		}
	});

	$('#body31').highcharts({
		chart : {
			type : 'spline',
			animation : false, // don't animate in old IE
			marginRight : 70,
			events : {
				load : function() {
					// set up the updating of the chart each second
					series = this.series[0],
					chart = this,
					timerele = window.setInterval(function() {}, 1000);
				}
			}
		},
		title : {
			text : '电压电流实时监测'
		},
		xAxis : {
			type : 'datetime',
			tickPixelInterval : 150,
			lineColor : '#FFFFFF',
			tickWidth : 0,
			labels : {
				enabled : false
			}
		},
		yAxis : [ {
			max : 500, // 定义Y轴 最大值  
			min : 0, // 定义最小值  
			minPadding : 0.2,
			maxPadding : 0.2,
			tickInterval : 100,
			color : '#A020F0',
			title : {
				text : '电流',
				style : {
					color : '#A020F0'
				}
			}
		} ],
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '</b><br/>' +
					Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
					Highcharts.numberFormat(this.y, 2);
			}
		},
		legend : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		credits : {
			enabled : false // 禁用版权信息
		},
		series : [ {
			color : '#A020F0',
			name : '电流',

			data : (function() {
				// generate an array of random data
				var data = [],
					/*time = new Date(Date.parse("0000-00-00 00:00:00")),*/
					i;
				for (i = -19; i <= 0; i += 1) {
					data.push({
						x : time[0] - 1000 + i * 1000,
						/*x: time + i*1000,*/
						y : 0
					});
				}
				return data;
			}())
		} ]
	}, function(c) {
		activeLastPointToolip(c)
	});

	activeLastPointToolip(chart);
}

function volcurve() {
	Highcharts.setOptions({
		global : {
			useUTC : false
		}
	});

	$('#body32').highcharts({
		chart : {
			type : 'spline',
			animation : false, // don't animate in old IE
			marginRight : 70,
			events : {
				load : function() {
					// set up the updating of the chart each second
					series1 = this.series[0],
					chart1 = this;
				}
			}
		},
		title : {
			text : false
		},
		xAxis : {
			type : 'datetime',
			tickPixelInterval : 150 /*,
	  		        tickWidth:0,
		  		    labels:{
		  		    	enabled:false
		  		    }*/
		},
		yAxis : [ {
			max : 50, // 定义Y轴 最大值  
			min : 0, // 定义最小值  
			minPadding : 0.2,
			maxPadding : 0.2,
			tickInterval : 10,
			color : '#87CEFA',
			title : {
				text : '电压',
				style : {
					color : '#87CEFA'
				}
			},
		} ],
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '</b><br/>' +
					Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
					Highcharts.numberFormat(this.y, 2);
			},
		},
		legend : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		credits : {
			enabled : false // 禁用版权信息
		},
		series : [ {
			name : '电压',
			data : (function() {
				// generate an array of random data
				var data = [],
					/*			                time = new Date(Date.parse("0000-00-00 00:00:00")),*/
					i;
				for (i = -19; i <= 0; i += 1) {
					data.push({
						x : time[0] - 1000 + i * 1000,
						y : 0
					});
				}
				return data;
			}()),
		} ]
	}, function(c) {
		activeLastPointToolip1(c)
	});

	activeLastPointToolip1(chart1);

}
function iview() {
	var z = 0;
	time.length = 0;

	vol.length = 0;
	ele.length = 0;
	if(redata.length==291){
			for (var i = 0; i < redata.length; i += 97) {
	//		if (redata.substring(8 + i, 12 + i) != "0000") {
				if (parseInt(redata.substring(89 + i, 97 + i)) == $("#no").val() && parseInt(redata.substring(2 + i, 4 + i)) == $("#itemid").val()) {
					var liveele = parseInt(redata.substring(12+i, 16+i));
		            var livevol = parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2));
					ele.push(liveele);
					vol.push(livevol);
					var ttme = redata.substring(40+i, 59+i);
		            ttme=ttme.replace(/-/g, '/');
		            time.push(Date.parse(new Date(ttme)));
					machstatus.push(redata.substring(0 + i, 2 + i));
					maxele = parseInt(redata.substring(61 + i, 64 + i));
					minele = parseInt(redata.substring(64 + i, 67 + i));
					maxvol = parseInt(redata.substring(67 + i, 70 + i));
					minvol = parseInt(redata.substring(70 + i, 73 + i));
					if(noflag<5){
						cbpd.push(liveele);
						noflag++;
					}else{
						for(var cb=0;cb<cbpd.length;cb++){
							if(cbpd[cb]>maxele){
								if(cb==cbpd.length-1){
									document.getElementById("new6").value = "已超标";
									document.getElementById("new6").style.backgroundColor = "#c4370c";
								}
							}else{
								document.getElementById("new6").value = "正常";
								break;
							}
						}
						cbpd.length=0;
						noflag=0;
					}
					if (symbol == 0) {
						elecurve();
						volcurve();
						symbol++;
					}
					document.getElementById("in5").value = (maxele + minele) / 2 +" A";
					document.getElementById("in6").value = (maxvol + minvol) / 2 +" V";
					document.getElementById("in7").value = parseInt(redata.substring(12 + i, 16 + i)) +" A";
					document.getElementById("in8").value = parseFloat((parseInt(redata.substring(16 + i, 20 + i)) / 10).toFixed(2)) +" V";
					$("#new1").val(parseInt(redata.substring(20 + i, 24 + i)));
					$("#new2").val(parseInt(redata.substring(24 + i, 28 + i)));
					$("#new3").val(parseInt(redata.substring(28 + i, 32 + i)));
					$("#new4").val(parseInt(redata.substring(32 + i, 36 + i)));
					$("#new5").val(parseInt(redata.substring(36 + i, 40 + i)));
					for (var k = 0; k < machine.length; k++) {
						if (machine[k].fid == redata.substring(4 + i, 8 + i)) {
							$("#inn2").val(machine[k].fequipment_no);
							$("#in2").val(machine[k].fid);
						}
					}
	
					if (time.length != 0 && z < time.length) {
						var mstatus = redata.substring(0 + i, 2 + i);
						switch (mstatus) {
						case "00":
							/*if($("#in4").val() == "超时待机"){
								break;
							}*/
							document.getElementById("in4").value = "待机";
							document.getElementById("in4").style.backgroundColor = "#f9e718";
							document.getElementById("mrjpg").src = "resources/images/welder_02.png";
							break;
						case "03":
							if(liveele>maxele || liveele<minele || livevol>maxvol || livevol<minvol){
								document.getElementById("in4").value = "超标";
								document.getElementById("in4").style.backgroundColor = "#c4370c";
								document.getElementById("mrjpg").src = "resources/images/welder_01.png";
							}else{
								document.getElementById("in4").value = "焊接";
								document.getElementById("in4").style.backgroundColor = "#7cbc16";
								document.getElementById("mrjpg").src = "resources/images/welder_03.png";
							}
							break;
						case "05":
								document.getElementById("in4").value = "收弧";
								document.getElementById("in4").style.backgroundColor = "#7cbc16";
								document.getElementById("mrjpg").src = "resources/images/welder_03.png";
							break;
						case "07":
								document.getElementById("in4").value = "起弧";
								document.getElementById("in4").style.backgroundColor = "#7cbc16";
								document.getElementById("mrjpg").src = "resources/images/welder_03.png";
							break;
						case "09":
								document.getElementById("in4").value = "超时待机";
								document.getElementById("in4").style.backgroundColor = "#55a7f3";
								document.getElementById("mrjpg").src = "resources/images/welder_05.png";
							break;
						}
						var x = time[z],
							y = ele[z],
							v = vol[z];
						if (z == 0) {
							series.addPoint([ x, y ], true, true);
							activeLastPointToolip(chart);
							series1.addPoint([ x, v ], true, true);
							activeLastPointToolip1(chart1);
	
						} else {
							if (x > time[z - 1]) {
								series.addPoint([ x, y ], true, true);
								activeLastPointToolip(chart);
								series1.addPoint([ x, v ], true, true);
								activeLastPointToolip1(chart1);
							}
						}
					}
				}
	//		}
			z++;
		}
	}
	if ((time.length) % 3 == 1) {
		ele[time.length] = ele[time.length - 1];
		ele[time.length + 1] = ele[time.length - 1];
		vol[time.length] = vol[time.length - 1];
		vol[time.length + 1] = vol[time.length - 1];
		time[time.length] = time[time.length - 1] + 1000;
		time[time.length + 1] = time[time.length - 1] + 2000;
	}
	if (time.length % 3 == 2) {
		ele[time.length] = ele[time.length - 1];
		vol[time.length] = vol[time.length - 1];
		time[time.length] = time[time.length - 1] + 1000;
	}
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变highchart高宽
function domresize() {
	$('#body31').highcharts().reflow();
	$('#body32').highcharts().reflow();
}


function activeLastPointToolip(chart) {
	var points = chart.series[0].points;
	chart.yAxis[0].removePlotLine('plot-line-0');
	chart.yAxis[0].removePlotLine('plot-line-1');
	chart.yAxis[0].removePlotLine('plot-line-2');
	/*  		    chart.tooltip.refresh(points[points.length -1]);
	  		    chart.tooltip.refresh(points1[points1.length -1]);*/
	chart.yAxis[0].addPlotLine({ //在y轴上增加 
		value : maxele, //在值为2的地方 
		width : 2, //标示线的宽度为2px 
		color : 'red', //标示线的颜色 
		dashStyle : 'longdashdot',
		id : 'plot-line-1', //标示线的id，在删除该标示线的时候需要该id标示 });
		label : {
			text : '最高电流', //标签的内容
			align : 'center', //标签的水平位置，水平居左,默认是水平居中center
			x : 10 //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
		}
	});
	chart.yAxis[0].addPlotLine({ //在y轴上增加 
		value : minele, //在值为2的地方 
		width : 2, //标示线的宽度为2px 
		color : 'red', //标示线的颜色 
		dashStyle : 'longdashdot',
		id : 'plot-line-2', //标示线的id，在删除该标示线的时候需要该id标示 });
		label : {
			text : '最低电流', //标签的内容
			align : 'center', //标签的水平位置，水平居左,默认是水平居中center
			x : 10 //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
		}
	})
	chart.yAxis[0].addPlotLine({ //在y轴上增加 
		value : (minele + maxele) / 2, //在值为2的地方 
		width : 2, //标示线的宽度为2px 
		color : 'red', //标示线的颜色 
		dashStyle : 'longdashdot',
		id : 'plot-line-0', //标示线的id，在删除该标示线的时候需要该id标示 });
		label : {
			text : '预置电流', //标签的内容
			align : 'center', //标签的水平位置，水平居左,默认是水平居中center
			x : 10 //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
		}
	})
}

function activeLastPointToolip1(chart) {
	var points = chart.series[0].points;
	chart.yAxis[0].removePlotLine('plot-line-3');
	chart.yAxis[0].removePlotLine('plot-line-4');
	chart.yAxis[0].removePlotLine('plot-line-5');
	/*  		    chart.tooltip.refresh(points[points.length -1]);
	  		    chart.tooltip.refresh(points1[points1.length -1]);*/
	chart.yAxis[0].addPlotLine({ //在y轴上增加 
		value : maxvol, //在值为2的地方 
		width : 2, //标示线的宽度为2px 
		color : 'black', //标示线的颜色 
		dashStyle : 'longdashdot',
		id : 'plot-line-3', //标示线的id，在删除该标示线的时候需要该id标示 });
		label : {
			text : '最高电压', //标签的内容
			align : 'center', //标签的水平位置，水平居左,默认是水平居中center
			x : 10
		}
	})
	chart.yAxis[0].addPlotLine({ //在y轴上增加 
		value : minvol, //在值为2的地方 
		width : 2, //标示线的宽度为2px 
		color : 'black', //标示线的颜色 
		dashStyle : 'longdashdot',
		id : 'plot-line-4', //标示线的id，在删除该标示线的时候需要该id标示 });
		label : {
			text : '最低电压', //标签的内容
			align : 'center', //标签的水平位置，水平居左,默认是水平居中center
			x : 10 //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
		}
	})
	chart.yAxis[0].addPlotLine({ //在y轴上增加 
		value : (minvol + maxvol) / 2, //在值为2的地方 
		width : 2, //标示线的宽度为2px 
		color : 'black', //标示线的颜色 
		dashStyle : 'longdashdot',
		id : 'plot-line-5', //标示线的id，在删除该标示线的时候需要该id标示 });
		label : {
			text : '预置电压', //标签的内容
			align : 'center', //标签的水平位置，水平居左,默认是水平居中center
			x : 10 //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
		}
	})
}