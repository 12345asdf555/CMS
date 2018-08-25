var insfid;
var charts;
var websocketURL, dic, starows, redata, symbol=0, machine, welderName;
var worknum=0, standbynum=0, overproofnum=0, offnum=0, overtimenum=0, flag = 0;

$(function() {
	loadtree();
	websocketUrl();
	getMsg();
	websocket();
	//状态发生改变
	$("#status").combobox({
		onChange : function(newValue, oldValue){
			statusClick(newValue);
			/*for(var i=0;i<machine.length;i++){
				var img;
				var status = $("#status"+machine[i].fid).val();
				if(status == 0 || status == 3){
					img = "resources/images/welder_03.png";
				}else if(status == 1 || status == 4){
					img = "resources/images/welder_02.png";
				}else if(status == 2){
					img = "resources/images/welder_04.png";
				}else if(status == 3){
					img = "resources/images/welder_01.png";
				}
				for(var j=0;j<starows.length;j++){
					if(machine[i].fid==parseInt(starows[j].fname)){
						if(parseInt(starows[j].ftime)>(parseInt(dic[0].name)*36)){
//							overtimenum+=1;
							$("#status"+starows[j].fname).val(4);
							$("#img"+starows[j].fname).attr("src","resources/images/welder_05.png");
						}
					}
				}
				if(status == newValue){
					$("#machine"+machine[i].fid).show();
					if(status!=4){
						$("#img"+machine[i].fid).attr("src",img);
					}
					
				}else{
					$("#machine"+machine[i].fid).hide();
					if(status!=4){
						$("#img"+machine[i].fid).attr("src",img);
					}
				}
			}*/
		}
	});
})

function loadtree() {
	$("#myTree").tree({
		url : 'insframework/getConmpany', //请求路径
		onLoadSuccess : function(node, data) {
			var tree = $(this);
			if (data) {
				$(data).each(function(index, d) {
					if (this.state == 'closed') {
						tree.tree('expandAll');
					}
				});
			}
			if (data.length > 0) {
				//找到第一个元素
				var nownodes = $('#myTree').tree('find', data[0].id);
				//判断是否拥有子节点
				if (nownodes.children != null) {
					var nextnodes1 = nownodes.children[0];
					if (nextnodes1.children != null) {
						var nextnodes2 = nextnodes1.children[0];
						if (nextnodes2.children != null) {
							var nextnodes3 = nextnodes2.children[0];
							insfid = nextnodes3.id;
						} else {
							insfid = nextnodes2.id;
						}
					} else {
						insfid = nextnodes1.id;
					}
				} else {
					insfid = nownodes.id;
				}
				//默认选中第一个项目部
				var defaultnode = $('#myTree').tree('find', insfid);
				$('#myTree').tree('select', defaultnode.target);
				$('#itemname').html(defaultnode.text);
				getMachine(insfid);
			}

		},
		//树形菜单点击事件,获取项目部id，默认选择当前组织机构下的第一个
		onClick : function(node) {
			document.getElementById("load").style.display="block";
			var sh = '<div id="show" style="align="center""><img src="resources/images/load.gif"/>正在加载，请稍等...</div>';
			$("#body").append(sh);
			document.getElementById("show").style.display="block";
			var dtoTime1 = getNowFormatDate(new Date().getTime() - 7200 * 1000);
			var dtoTime2 = getNowFormatDate(new Date().getTime());
			$.ajax({
				type : "post",
				async : false,
				url : "td/standbytimeout?dtoTime1=" + dtoTime1 + "&dtoTime2=" + dtoTime2 + "&dictionry=" + dic[0].name,
				data : {},
				dataType : "json", //返回数据形式为json  
				success : function(result) {
					if (result) {
						starows = eval(result.rows);
					}
				},
				error : function(errorMsg) {
					alert("数据请求失败，请联系系统管理员!");
				}
			});
			var nownodes = $('#myTree').tree('find', node.id);
			//判断是否拥有子节点
			if (nownodes.children != null) {
				var nextnodes1 = nownodes.children[0];
				if (nextnodes1.children != null) {
					var nextnodes2 = nextnodes1.children[0];

					if (nextnodes2.children != null) {
						var nextnodes3 = nextnodes2.children[0];
						insfid = nextnodes3.id;
					} else {
						insfid = nextnodes2.id;
					}
				} else {
					insfid = nextnodes1.id;
				}
			} else {
				insfid = nownodes.id;
			}
			$("#curve").html("");
			$("#standby").html(0);
			$("#work").html(0);
			$("#off").html(0);
			$("#overproof").html(0);
			$("#overtime").html(0);
			getMachine(insfid);
			var defaultnode = $('#myTree').tree('find', insfid);
			$('#itemname').html(defaultnode.text);
			flag = 0;
			setTimeout(function() {
				if (symbol != 1) {
					alert("未接收到数据!!!");
		    		document.getElementById("load").style.display ='none';
		    		document.getElementById("show").style.display ='none';
					
				}
			}, 10000);
//			getOvertime();
		}
	});
}


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

function getMsg(){
	$.ajax({
		type : "post",
		async : false,
		url : "Dictionary/getDictionaryValueame?ivalue=81",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				dic = eval(result.ary);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
	var dtoTime1 = getNowFormatDate(new Date().getTime() - 7200 * 1000);
	var dtoTime2 = getNowFormatDate(new Date().getTime());
	$.ajax({
		type : "post",
		async : false,
		url : "td/standbytimeout?dtoTime1=" + dtoTime1 + "&dtoTime2=" + dtoTime2 + "&dictionry=" + dic[0].name,
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				starows = eval(result.rows);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
}

function getNowFormatDate(millsTime) {
	var day = new Date(millsTime);
	var Year = 0;
	var Month = 0;
	var Day = 0;
	var Hour = 0;
	var Minute = 0;
	var Second = 0;
	var CurrentDate = "";
	Year = day.getFullYear(); //支持IE和火狐浏览器.
	Month = day.getMonth() + 1;
	Day = day.getDate();
	Hour = day.getHours();
	Minute = day.getMinutes();
	Second = day.getSeconds();
	CurrentDate += Year + '-';
	if (Month >= 10) {
		CurrentDate += Month + '-';
	} else {
		CurrentDate += "0" + Month + '-';
	}
	if (Day >= 10) {
		CurrentDate += Day + ' ';
	} else {
		CurrentDate += "0" + Day + ' ';
	}
	if (Hour >= 10) {
		CurrentDate += Hour + ':';
	} else {
		CurrentDate += '0' + Hour + ':';
	}
	if (Minute >= 10) {
		CurrentDate += Minute + ':';
	} else {
		CurrentDate += '0' + Minute + ':';
	}
	if (Second >= 10) {
		CurrentDate += Second;
	} else {
		CurrentDate += '0' + Second;
	}
	return CurrentDate;
}

function getMachine(insfid) {
	if (insfid == "" || insfid == null) {
		var url = "td/getAllPosition";
	} else {
		var url = "td/getAllPosition?parent=" + insfid;
	}
	$.ajax({
		type : "post",
		async : false,
		url : url,
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				machine = eval(result.rows);
				$("#machinenum").html(machine.length);
				$("#off").html(machine.length);
				showChart();
				for(var i=0;i<machine.length;i++){
					var str = '<div id="machine'+machine[i].fid+'" style="width:200px;height:120px;float:left;display:none">'+
						'<div style="float:left;width:40%;height:100%;"><a href="td/goNextcurve?value='+machine[i].fid+'&valuename='+machine[i].fequipment_no+'"><img id="img'+machine[i].fid+'" src="resources/images/welder_04.png" style="height:100px;width:100%;padding-top:10px;"></a></div>'+
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
	      url : "td/allWeldname",  
	      data : {},  
	      dataType : "json", //返回数据形式为json  
	      success : function(result) {
	          if (result) {
	        	  welderName=eval(result.rows);
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
		if(starows.length!=0){
			if(machine!=null && machine!=""){
		        for(var i=0;i<machine.length;i++){
		        	var stalen = starows.length;
		        	for(var j=0;j<stalen;j++){
		        		if(machine[i].fid==parseInt(starows[j].fname)){
		        			if(machine[i].finsid==insfid){
//							$("#m6"+machine[i].fid).html("超时待机");
//							$("#status"+machine[i].fid).val(4);
//							$("#img"+machine[i].fid).attr("src","resources/images/welder_05.png");
//		        			starows[j].ftime=0;
//		        			starows[starows.length/2+j].ftime=0;
		        			}
		        		}    
		        	}
		        }
			}
//			starows.length=0;
		};
		window.setTimeout(function() {
			worknum=0, standbynum=0, overproofnum=0, offnum=0, overtimenum=0;
			for(var i=0;i<machine.length;i++){
				var img;
				var status = $("#status"+machine[i].fid).val();
				if(status == 0){
					worknum += 1;
					img = "resources/images/welder_03.png";
				}else if(status == 1||status == 4||status == 3){
					standbynum += 1;
					img = "resources/images/welder_02.png";
				}else if(status == 2){
					offnum += 1;
					img = "resources/images/welder_04.png";
				}else if(status == 3){
					overproofnum += 1;
					img = "resources/images/welder_01.png";
				}else if(status == 4){
			          overtimenum += 1;
			          img = "resources/images/welder_05.png";
		        }
				$("#machine"+machine[i].fid).hide();
				if(status!=4){
					$("#img"+machine[i].fid).attr("src",img);
				}
				if($("#status").combobox('getValue') == 1){
					if(status == 1||status == 4||status == 3){
						$("#machine"+machine[i].fid).show();
						$("#img"+machine[i].fid).attr("src",img);
					}
				}else if(status == $("#status").combobox('getValue')){
					$("#machine"+machine[i].fid).show();
					if(status!=4){
						$("#img"+machine[i].fid).attr("src",img);
					}else{
						$("#img"+machine[i].fid).attr("src","resources/images/welder_05.png");
					}
					
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
    		flag=2;
    		var len = starows.length;
    		starows.splice(len/2, len/2);
		},5000);
//		flag = 1;
	}
	for (var i = 0; i < redata.length; i += 89) {
//		if (redata.substring(8 + i, 12 + i) != "0000") {
		if(machine!=null && machine!=""){
			for(var f=0;f<machine.length;f++){
				if(machine[f].fid==(parseInt(redata.substring(4+i, 8+i)))){
					for(var k=0;k<welderName.length;k++){
						if(welderName[k].fwelder_no==redata.substring(8+i, 12+i)){
							$("#m3"+machine[f].fid).html(welderName[k].fname);
						}
					}
					$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
					$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
					var liveele = parseInt(redata.substring(12+i, 16+i));
		            var livevol = parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2));
		            var maxele = parseInt(redata.substring(61+i, 64+i));
		            var minele = parseInt(redata.substring(64+i, 67+i));
		            var maxvol = parseInt(redata.substring(67+i, 70+i));
		            var minvol = parseInt(redata.substring(70+i, 73+i));
					var mstatus = redata.substring(0 + i, 2 + i);
					switch (mstatus) {
					case "00":
						var status = $("#status"+machine[f].fid).val();
						if(status == 4){
							break;
						}
						$("#m6"+machine[f].fid).html("待机");
						$("#status"+machine[f].fid).val(1);
//						$("#img"+machine[f].fid).attr("src","resources/images/welder_02.png");
						break;
					case "03":
						if(liveele>maxele || liveele<minele || livevol>maxvol || livevol<minvol){
							$("#m6"+machine[f].fid).html("超标");
							$("#status"+machine[f].fid).val(3);
//							$("#img"+machine[f].fid).attr("src","resources/images/welder_01.png");
						}else{
							$("#m6"+machine[f].fid).html("工作");
							$("#status"+machine[f].fid).val(0);
//							$("#img"+machine[f].fid).attr("src","resources/images/welder_03.png");
						}
						break;
					case "05":
						$("#m6"+machine[f].fid).html("工作");
						$("#status"+machine[f].fid).val(0);
						break;
					case "07":
						$("#m6"+machine[f].fid).html("工作");
						$("#status"+machine[f].fid).val(0);
						break;
					}
				}
			}
		}
//		}
		if(flag==2){
			if(redata.substring(4+i, 8+i)==insfid){
			if(starows.length==0){
				var arr  =
			     {
			         "fname" : redata.substring(4+i, 8+i),
			         "ftime" : 1
			     }
				starows.push(arr);
			}else{
				for(var sta=0;sta<starows.length;sta++){
					if(redata.substring(4+i, 8+i)==starows[sta].fname){
						if(redata.substring(0+i, 2+i)=="00"){
							starows[sta].ftime++;
						}else{
							starows[sta].ftime=0;
						}
						break;
					}else{
						if(sta==starows.length-1){
							var arr  =
						     {
						         "fname" : redata.substring(4+i, 8+i),
						         "ftime" : 1
						     }
							starows.push(arr);
						}
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
	charts = echarts.init(document.getElementById("piecharts"));
	//显示加载动画效果
	charts.showLoading({
		text: '稍等片刻,精彩马上呈现...',
		effect:'whirling'
	});
	option = {
		tooltip:{
			trigger: 'item'
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
//            center: ['50%', '50%'],
            color:['#FFB90F','#7cbc16','#818181'],
			data:[
                {value:($("#standby").html()/$("#machinenum").html()*100).toFixed(2), name:'待机'},
                {value:($("#work").html()/$("#machinenum").html()*100).toFixed(2), name:'工作'},
                {value:($("#off").html()/$("#machinenum").html()*100).toFixed(2), name:'关机'}/*,
                {value:($("#overproof").html()/$("#machinenum").html()*100).toFixed(2), name:'超标'},
                {value:($("#overtime").html()/$("#machinenum").html()*100).toFixed(2), name:'超时'}*/
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
			radius : ['35%', '50%'],
//            center: ['50%', '50%'],
            color:['#818181','#c4370c'],
			data:[
                {value:(($("#machinenum").html()-$("#overproof").html())/$("#machinenum").html()*100).toFixed(2), name:'其它'},
                {value:($("#overproof").html()/$("#machinenum").html()*100).toFixed(2), name:'超标'}
            ],
            labelLine: {
                normal: {
                    length: 30,
                    length2: 50
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
			radius : ['15%', '30%'],
//            center: ['50%', '50%'],
            color:['#818181','#000000'],
			data:[
                {value:(($("#machinenum").html()-$("#overtime").html())/$("#machinenum").html()*100).toFixed(2), name:'其它'},
                {value:($("#overtime").html()/$("#machinenum").html()*100).toFixed(2), name:'超时待机'}
            ],
            labelLine: {
                normal: {
                    length: 30,
                    length2: 60
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
	//为echarts对象加载数据
	charts.setOption(option);
	//隐藏动画加载效果
	charts.hideLoading();
	//echarts 点击事件
	charts.on('click', function (param) {
		var status;
		if(param.name=="工作"){
			status = 0;
		}else if(param.name=="待机"){
			status = 1;
		}else if(param.name=="关机"){
			status = 2;
		}else if(param.name=="超标"){
			status = 3;
		}else if(param.name=="超时"){
			status = 4;
		}
		statusClick(status);
	});
	$("#chartLoading").hide();
}

//每分钟刷新一次
var timer = window.setInterval(fun,60000);

var fun = function() {
	getMachine(insfid);
	worknum=0, standbynum=0, overproofnum=0, offnum=0, overtimenum=0;
	for(var i=0;i<machine.length;i++){
		var status = $("#status"+machine[i].fid).val();
		if(status == 0){
			worknum += 1;
			img = "resources/images/welder_03.png";
		}else if(status == 1||status == 4||status == 3){
			standbynum += 1;
			img = "resources/images/welder_02.png";
		}else if(status == 2){
			offnum += 1;
			img = "resources/images/welder_04.png";
		}else if(status == 3){
			overproofnum += 1;
			img = "resources/images/welder_01.png";
		}
		for(var j=0;j<starows.length;j++){
			if(parseInt(starows[j].fname)==machine[i].fid){
				if(parseInt(starows[j].ftime)>(parseInt(dic[0].name)*36)){
					overtimenum+=1;
					$("#status"+starows[j].fname).val(4);
					$("#img"+starows[j].fname).attr("src","resources/images/welder_05.png");
				}
			}
		}
		$("#machine"+machine[i].fid).hide();
		if(status!=4){
			$("#img"+machine[i].fid).attr("src",img);
		}
		if($("#status").combobox('getValue') == 1){
			if(status == 1||status == 4||status == 3){
				$("#machine"+machine[i].fid).show();
				$("#img"+machine[i].fid).attr("src",img);
			}
		}else if(status == $("#status").combobox('getValue')){
			$("#machine"+machine[i].fid).show();
			if(status!=4){
				$("#img"+machine[i].fid).attr("src",img);
			}else{
				$("#img"+machine[i].fid).attr("src","resources/images/welder_05.png");
			}
		}
	}
	$("#standby").html(standbynum);
	$("#work").html(worknum);
	$("#off").html(offnum);
	$("#overproof").html(overproofnum);
	$("#overtime").html(overtimenum);
	showChart();
}

//统计超时
function getOvertime(){
	getMsg();
	if(starows.length!=0){
        for(var i=0;i<machine.length;i++){
        	for(var j=0;j<starows.length/2;j++){
        		if(machine[i].fid==parseInt(starows[j].fname)){
        			var count = (parseInt(starows[j].ftime)/parseInt(starows[starows.length/2+j].ftime)).toFixed(2);
        			if(count>(parseInt(dic[0].name)/100).toFixed(2)){
						$("#m6"+machine[i].fid).html("超时待机");
						$("#status"+machine[i].fid).val(4);
        			}
        			starows[j].ftime=0;
        			starows[starows.length/2+j].ftime=0;
        		}    
        	}
        }
	};
}

//状态按钮点击事件
function statusClick(statusnum){
	window.clearInterval(timer);
	getMachine(insfid);
	var img;
	worknum=0, standbynum=0, overproofnum=0, offnum=0, overtimenum=0;
	for(var i=0;i<machine.length;i++){
		var status = $("#status"+machine[i].fid).val();
//		if(statusnum==0){
			if(status == 0||status == 3){
				worknum += 1;
				img = "resources/images/welder_03.png";
				
//			}
		}
//			else if(statusnum==1){
			if(status == 1||status == 4){
				standbynum += 1;
				img = "resources/images/welder_02.png";
			}
//		}
		if(status == 2){
			offnum += 1;
			img = "resources/images/welder_04.png";
		}else if(status == 3){
			overproofnum += 1;
			img = "resources/images/welder_01.png";
		}
		for(var j=0;j<starows.length;j++){
			if(machine[i].fid==parseInt(starows[j].fname)){
				if(parseInt(starows[j].ftime)>(parseInt(dic[0].name)*36)){
					overtimenum+=1;
					$("#status"+starows[j].fname).val(4);
					$("#img"+starows[j].fname).attr("src","resources/images/welder_05.png");
				}
			}
		}
		$("#machine"+machine[i].fid).hide();
		if(status!=4){
			$("#img"+machine[i].fid).attr("src",img);
		}
		if(statusnum == 0){
			if(status == 0||status == 3){
				$("#machine"+machine[i].fid).show();
				$("#img"+machine[i].fid).attr("src",img);
			}
		}else if(statusnum == 1){
			if(status == 1||status == 4){
				$("#machine"+machine[i].fid).show();
				$("#img"+machine[i].fid).attr("src",img);
			}
		}else if(status == statusnum){
			$("#machine"+machine[i].fid).show();
			if(status!=4){
				$("#img"+machine[i].fid).attr("src",img);
			}else{
				$("#img"+machine[i].fid).attr("src","resources/images/welder_05.png");
			}
			
		}
	}
	$("#standby").html(standbynum);
	$("#work").html(worknum);
	$("#off").html(offnum);
	$("#overproof").html(overproofnum);
	$("#overtime").html(overtimenum);
	showChart();
	
	timer = window.setInterval(fun,60000);
}

//每小时统计超时
/*window.setInterval(function() {
	getOvertime();
}, 3600*1000)*/

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}


//改变图表高宽
function domresize() {
	echarts.init(document.getElementById('piecharts')).resize();
}
