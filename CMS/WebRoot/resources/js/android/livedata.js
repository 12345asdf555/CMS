var insfid;
var charts;
var websocketURL, dic, starows, redata, symbol=0, welderName;
var worknum=0, standbynum=0, overproofnum=0, offnum=0, overtimenum=0, flag = 0;
var liveary = new Array(), machine = new Array(), ary = new Array();
$(function() {

	document.getElementById("load").style.display="block";
	var sh = '<div id="show" style="align="center""><img src="resources/images/load.gif"/>正在加载，请稍等...</div>';
	$("#bodydiv").append(sh);
	document.getElementById("show").style.display="block";
	getMachine();
	//初始化
	websocketUrl();
	websocket();
})

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
function getMachine() {
	$.ajax({
		type : "post",
		async : false,
		url : "td/getAllPosition",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				machine = eval(result.rows);
				$("#machinenum").html(machine.length);
				$("#off").html(machine.length);
				$("#curve").html();
				for(var i=0;i<machine.length;i++){
					var str = '<div id="machine'+machine[i].fid+'" style="width:200px;height:120px;float:left;display:none">'+
						'<div style="float:left;width:40%;height:100%;"><a href="android/goMachinecurve?value='+machine[i].fid+'&valuename='+machine[i].fequipment_no+'&status='+$("#status").val()+'"><img id="img'+machine[i].fid+'" src="resources/images/welder_04.png" style="height:100px;width:100%;padding-top:10px;"></a></div>'+
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
		if(redata==null || redata==""){
			window.setTimeout(function() {
				document.getElementById("load").style.display ='none';
				document.getElementById("show").style.display ='none';
			},5000);
		}
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
		/*if(machine!=null){
			document.getElementById("load").style.display="block";
			var sh = '<div id="show" style="align="center""><img src="resources/images/load.gif"/>正在加载，请稍等...</div>';
			$("#bodydiv").append(sh);
			document.getElementById("show").style.display="block";
		}*/
		if($("#ary").val()){
			ary = eval("("+decodeURIComponent(decodeURI($("#ary").val()))+")");
			var status = $("#status").val();
			for(var i=0;i<machine.length;i++){
				for(var j=0;j<ary.length;j++){
					if(machine[i].fid == parseInt(ary[j].fid)){
						$("#m4"+machine[i].fid).html(ary[j].liveele+"A");
						$("#m5"+machine[i].fid).html(ary[j].livevol+"V");
						if(status==0 && (ary[j].fstatus=="03" || ary[j].fstatus=="05" || ary[j].fstatus=="07")){
							$("#m6"+machine[i].fid).html("工作");
							$("#status"+machine[i].fid).val(0);
							$("#img"+machine[i].fid).attr("src","resources/images/welder_03.png");
						}else if(status==1 && ary[j].fstatus=="00"){
							$("#m6"+machine[i].fid).html("待机");
							$("#status"+machine[i].fid).val(1);
							$("#img"+machine[i].fid).attr("src","resources/images/welder_02.png");
						}else if(status==2 && ary[j].fstatus=="99"){
							$("#m6"+machine[i].fid).html("报警");
							$("#status"+machine[i].fid).val(2);
							$("#img"+machine[i].fid).attr("src","resources/images/welder_01.png");
						}
						$("#machine"+machine[i].fid).show();
					}
				}
			}
			document.getElementById("load").style.display ='none';
			document.getElementById("show").style.display ='none';
		}else{
			window.setTimeout(function() {
				tempary = liveary;
				var statusnum = $("#status").val();
				for(var j=0;j<tempary.length;j++){
					var status = $("#status"+tempary[j]).val();
					if(statusnum == 0){
						if(status == 0||status == 2){
							$("#machine"+tempary[j]).show();
						}
					}else if(status == statusnum){
						$("#machine"+tempary[j]).show();
					}
				}
				document.getElementById("load").style.display ='none';
				document.getElementById("show").style.display ='none';
			},5000);
		}
		
		flag=2;
	}
	for (var i = 0; i < redata.length; i += 97) {
//		if (redata.substring(8 + i, 12 + i) != "0000") {
		if(machine!=null && machine!=""){
			for(var f=0;f<machine.length;f++){
				var machineflag = false;
				for(var x=0;x<liveary.length;x++){
					if(liveary[x]==machine[f].fid){
						machineflag = true;
					}
				}
				if(!machineflag && $("#status"+machine[f].fid).val()!=4){
					$("#m3"+machine[f].fid).html("--");
					$("#m4"+machine[f].fid).html("--A");
					$("#m5"+machine[f].fid).html("--V");
					$("#m6"+machine[f].fid).html("关机");
					$("#status"+machine[f].fid).val(2);
					$("#img"+machine[f].fid).attr("src","resources/images/welder_04.png");
				}
				if(machine[f].fid==(parseInt(redata.substring(4+i, 8+i)))){
					for(var k=0;k<welderName.length;k++){
						if(welderName[k].fwelder_no==redata.substring(8+i, 12+i)){
							$("#m3"+machine[f].fid).html(welderName[k].fname);
						}
					}
					$("#m2"+machine[f].fid).html(redata.substring(89 + i, 97 + i));
					var liveele = parseInt(redata.substring(12+i, 16+i));
		            var livevol = parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2));
		            var maxele = parseInt(redata.substring(61+i, 64+i));
		            var minele = parseInt(redata.substring(64+i, 67+i));
		            var maxvol = parseInt(redata.substring(67+i, 70+i));
		            var minvol = parseInt(redata.substring(70+i, 73+i));
					var mstatus = redata.substring(0 + i, 2 + i);
					switch (mstatus) {
					case "00":
						
						if(liveary.length==0){
							liveary.push(machine[f].fid);
							$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
							$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
							$("#m6"+machine[f].fid).html("待机");
							$("#status"+machine[f].fid).val(1);
							$("#img"+machine[f].fid).attr("src","resources/images/welder_02.png");
						}else{
							var tempflag = false;
							for(var x=0;x<liveary.length;x++){
								if(liveary[x] == machine[f].fid){
									tempflag = true;
								}
							}
							if(!tempflag){
								liveary.push(machine[f].fid);
								$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
								$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
								$("#m6"+machine[f].fid).html("待机");
								$("#status"+machine[f].fid).val(1);
								$("#img"+machine[f].fid).attr("src","resources/images/welder_02.png");
							}
						}
						break;
					case "03":
						if(liveary.length==0){
							liveary.push(machine[f].fid);
							$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
							$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
							if(liveele>maxele || liveele<minele || livevol>maxvol || livevol<minvol){
								$("#m6"+machine[f].fid).html("报警");
								$("#status"+machine[f].fid).val(2);
								$("#img"+machine[f].fid).attr("src","resources/images/welder_01.png");
							}else{
								$("#m6"+machine[f].fid).html("工作");
								$("#status"+machine[f].fid).val(0);
								$("#img"+machine[f].fid).attr("src","resources/images/welder_03.png");
							}
						}else{
							var tempflag = false;
							for(var x=0;x<liveary.length;x++){
								if(liveary[x] == machine[f].fid){
									tempflag = true;
								}
							}
							if(!tempflag){
								if(liveary[x] != machine[f].fid){
									liveary.push(machine[f].fid);
									$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
									$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
									if(liveele>maxele || liveele<minele || livevol>maxvol || livevol<minvol){
										$("#m6"+machine[f].fid).html("报警");
										$("#status"+machine[f].fid).val(2);
										$("#img"+machine[f].fid).attr("src","resources/images/welder_01.png");
									}else{
										$("#m6"+machine[f].fid).html("工作");
										$("#status"+machine[f].fid).val(0);
										$("#img"+machine[f].fid).attr("src","resources/images/welder_03.png");
									}
								}
							}
						}
						break;
					case "05":
						if(liveary.length==0){
							liveary.push(machine[f].fid);
							$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
							$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
							$("#m6"+machine[f].fid).html("工作");
							$("#status"+machine[f].fid).val(0);
							$("#img"+machine[f].fid).attr("src","resources/images/welder_03.png");
						}else{
							var tempflag = false;
							for(var x=0;x<liveary.length;x++){
								if(liveary[x] == machine[f].fid){
									tempflag = true;
								}
							}
							if(!tempflag){
								if(liveary[x] != machine[f].fid){
									liveary.push(machine[f].fid);
									$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
									$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
									$("#m6"+machine[f].fid).html("工作");
									$("#status"+machine[f].fid).val(0);
									$("#img"+machine[f].fid).attr("src","resources/images/welder_03.png");
								}
							}
						}
						break;
					case "07":
						if(liveary.length==0){
							liveary.push(machine[f].fid);
							$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
							$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
							$("#m6"+machine[f].fid).html("工作");
							$("#status"+machine[f].fid).val(0);
							$("#img"+machine[f].fid).attr("src","resources/images/welder_03.png");
						}else{
							var tempflag = false;
							for(var x=0;x<liveary.length;x++){
								if(liveary[x] == machine[f].fid){
									tempflag = true;
								}
							}
							if(!tempflag){
								if(liveary[x] != machine[f].fid){
									liveary.push(machine[f].fid);
									$("#m4"+machine[f].fid).html(parseInt(redata.substring(12+i, 16+i))+"A");
									$("#m5"+machine[f].fid).html(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2))+"V");
									$("#m6"+machine[f].fid).html("工作");
									$("#status"+machine[f].fid).val(0);
									$("#img"+machine[f].fid).attr("src","resources/images/welder_03.png");
								}
							}
						}
						break;
					}
				}
			}
		}
//		}
		
	}
}

//每30'刷新一次
var tempary = new Array();
window.setInterval(function(){
	tempary = liveary;
	var statusnum = $("#status").val();
	for(var j=0;j<tempary.length;j++){
		var status = $("#status"+tempary[j]).val();
		if(statusnum == 0){
			if(status == 0||status == 2){
				$("#machine"+tempary[j]).show();
			}
		}else if(status == statusnum){
			$("#machine"+tempary[j]).show();
		}
	}
	//清空数组
	liveary.length = 0;
},30000);

