/**
 * 
 */
var machine = new Array();
var time = new Array();
var ele = new Array();
var vol = new Array();
var websocketURL;
var welderName;
var symbol=0;
var symbol1=0;
var sym=0;
var timerele;
var timervol;
var tishi;
var change;
var socket;
var redata;
var maxele=0;
var minele=0;
var maxvol=0;
var minvol=0;
var rows;
var sint=0;
var xuanze;
var sy=0;
var series;
var chart;
var series1;
var chart1;
var led=["0,1,2,4,5,6","2,5","0,2,3,4,6","0,2,3,5,6","1,2,3,5","0,1,3,5,6","0,1,3,4,5,6","0,2,5","0,1,2,3,4,5,6","0,1,2,3,5,6"];
var dic;
var starows = new Array();
/*var jishiqi=0;
var tioshu;*/
$(function(){
	var width = $("#treeDiv").width();
	$(".easyui-layout").layout({
		onCollapse:function(){
			$("#dg").datagrid({
				height : $("#body").height(),
				width : $("#body").width()
			})
		},
		onExpand:function(){
			$("#dg").datagrid({
				height : $("#body").height(),
				width : $("#body").width()
			})
		}
	});
	$("#myTree").tree({  
		onClick : function(node){
			$("#dg").datagrid('load',{
				"parent" : node.id
			});
			$("#zu").textbox("setValue", node.text);
			document.getElementById("zuid").value=node.id;
    		$("#on").textbox("setValue", 0);
    		$("#wait").textbox("setValue", 0);
    		$("#off").textbox("setValue", 0)
			window.clearInterval(timerele);
			window.clearInterval(timervol);
			$('#body31').html("");
			$('#body32').html("");
			vol.length=0;
			ele.length=0;
			time.length=0;
			starows.length=0;
			symbol=0;
			$("#macname").val("");
			$("#welname").val("");
			xuanze="";
			sy=0;
		 }
	})
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
	var dtoTime1=getNowFormatDate(new Date().getTime()-7200*1000);
	var dtoTime2=getNowFormatDate(new Date().getTime());
	$.ajax({  
	      type : "post",  
	      async : false,
	      url : "td/standbytimeout?dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2,  
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
	datatable();
    websocket();
})
    function websocket() {
		if(typeof(WebSocket) == "undefined") {
			alert("您的浏览器不支持WebSocket");
			return;
		}
		webclient();
	};
	function webclient(){
		try{
			socket = new WebSocket(websocketURL);
		}catch(err){
			alert("地址请求错误，请清除缓存重新连接！！！")
		}
		tishi = setTimeout(function(){
			if(socket.readyState!=1){
				alert("与服务器连接失败,请检查网络设置!");
			}
		},5000);
		socket.onopen = function() {
			//监听加载状态改变  
			document.onreadystatechange = completeLoading();  
			   
			//加载状态为complete时移除loading效果 
			function completeLoading() {
			        var loadingMask = document.getElementById('loadingDiv');  
			        loadingMask.parentNode.removeChild(loadingMask);  
			}
			setTimeout(function(){
				if(symbol==0){
					alert("连接成功，但未接收到任何数据");
					rows = $('#dg').datagrid("getRows");
					$("#off").textbox("setValue", rows.length);
				}
			},10000);
		};
		socket.onmessage = function(msg) {
/*			if(symbol==0){
				window.setInterval(function() {
					jishiqi=jishiqi+1;
				}, 1000)
			}*/
			redata=msg.data;
			rows = $('#dg').datagrid("getRows");
/*			if(symbol==0){
				change = window.setInterval(function() {
					sint++;
					if(sint<machine.length){
						iview(machine[sint],0);
					}else{
						sint=0;
					}
				}, 10*60*1000)
			}*/
			if(sy==0){
				xuanze=redata.substring(4, 8);
				iview(xuanze);
			}else{
				iview(xuanze);
			}
/*			var fff = "0045";
			iview(fff);
			alert("时间："+106+"秒");
			alert("处理的条数："+tioshu);*/
		};
		//关闭事件
		socket.onclose = function(e) {
            if (e.code == 4001 || e.code == 4002 || e.code == 4003 || e.code == 4005 || e.code == 4006){
                //如果断开原因为4001 , 4002 , 4003 不进行重连.
                return;
            }else{
                return;
            }
            // 重试3次，每次之间间隔5秒
            if (tryTime < 3) {
                setTimeout(function () {
                    socket = null;
                    tryTime++;
                    var _PageHeight = document.documentElement.clientHeight,  
                    _PageWidth = document.documentElement.clientWidth;   
                    var _LoadingTop = _PageHeight > 61 ? (_PageHeight - 61) / 2 : 0,  
                    	_LoadingLeft = _PageWidth > 215 ? (_PageWidth - 215) / 2 : 0;  
                    var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:' + _PageHeight + 'px;top:0;background:#f3f8ff;opacity:0.8;filter:alpha(opacity=80);z-index:10000;"><div style="position: absolute; cursor1: wait; left: ' + _LoadingLeft + 'px; top:' + _LoadingTop + 'px; width: auto; height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px; background: #fff url(resources/images/load.gif) no-repeat scroll 5px 10px; border: 2px solid #95B8E7; color: #696969;">""正在尝试第"'+tryTime+'"次重连，请稍候..."</div></div>';  
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
	
	function getNowFormatDate(millsTime){
	    var day = new Date(millsTime);
	    var Year = 0;
	    var Month = 0;
	    var Day = 0;
	    var Hour = 0;
	    var Minute = 0;
	    var Second =0;
	    var CurrentDate = "";
	    Year= day.getFullYear();//支持IE和火狐浏览器.
	    Month= day.getMonth()+1;
	    Day = day.getDate();
	    Hour = day.getHours();
	    Minute = day.getMinutes();
	    Second = day.getSeconds();
	    CurrentDate += Year +'-';
	    if (Month >= 10 ){
	     CurrentDate += Month + '-';
	    }
	    else{
	     CurrentDate += "0" + Month + '-';
	    }
	    if (Day >= 10 ){
	     CurrentDate += Day + ' ';
	    }
	    else{
	     CurrentDate += "0" + Day + ' ';
	    }
	    if(Hour >= 10){
	    	CurrentDate += Hour + ':';
	    } else {
	    	CurrentDate += '0' + Hour + ':';
	    }
	    if(Minute >= 10){
	    	CurrentDate += Minute + ':';
	    } else {
	    	CurrentDate += '0' + Minute + ':';
	    }
	    if(Second >= 10){
	    	CurrentDate += Second;
	    } else {
	    	CurrentDate += '0' + Second;
	    }
	    return CurrentDate;
	 }
	
	function datatable(){
	    $("#dg").datagrid( {
			fitColumns : true,
			height : $("#body4").height(),
			width : $("#body4").width(),
			idField : 'id',
/*			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],*/
			url : 'td/getAllPosition',
			singleSelect : false,
			rownumbers : true,
			pagination : false,
			showPageList : false,
			columns : [ [ {
				field : 'fid',
				title : '焊机id',
				width : 100,
				halign : "center",
				align : "left",
				hidden : true
			}, {
				field : 'fequipment_no',
				title : '焊机编号',
				width : 100,
				halign : "center",
				align : "left"
			}, {
				field : 'fwelder_no',
				title : '焊工编号',
				width : 100,
				halign : "center",
				align : "left"
			}, {
				field : 'fname',
				title : '焊工姓名',
				width : 100,
				halign : "center",
				align : "left"
			}, {
				field : 'fposition',
				title : '设备位置',
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : 'finsid',
				title : '组织机构id',
				width : 100,
				halign : "center",
				align : "left",
				hidden : true
			},{
				field : 'finsname',
				title : '组织机构名称',
				width : 100,
				halign : "center",
				align : "left"
			},{
				field : 'fstatus_id',
				title : '设备状态',
				width : 100,
				halign : "center",
				align : "left",
				hidden : true
			},{
				field : 'percentage',
				title : '百分比',
				width : 100,
				halign : "center",
				align : "left",
				hidden : true
			},{
				field : 'view',
				title : '实时监测',
				width : 130,
				halign : "center",
				align : "left",
				formatter:function(value,row,index){
				var str = "";
				str += '<a id="view" href="javascript:openss('+row.fid+')">查看</a>';
				return str;
				}
			}]],
			toolbar : '#toolbar',
			onLoadSuccess:function(data){
				window.setInterval(function() {
					if(starows.length!=0){
				        for(var i=0;i<data.rows.length;i++){
				        	for(var j=0;j<starows.length/2;j++){
				        		if(data.rows[i].fequipment_no==starows[j].fname){
				        			data.rows[i].percentage=(parseInt(starows[j].ftime)/parseInt(starows[starows.length/2+j].ftime)).toFixed(2);
				        			sta[j].ftime=0;
				        			starows[starows.length/2+j].ftime=0;
				        		}    
				        	}
				        }
					};
				}, 3600*1000)
		        },
	        rowStyler:function(index,row){
	            if(row.percentage>(parseInt(dic[0].name)/100).toFixed(2)&&(row.fstatus_id=="00")){
	            	return 'background-color:#FFFF00;color:black;';
	            }else{
	            	if ((row.fstatus_id=="03")||(row.fstatus_id=="05")||(row.fstatus_id=="07")){
		                return 'background-color:#00FF00;color:black;';
		            }
	/*	            else if (row.fstatus_id=="07"){
		                return 'background-color:#FF0000;color:black;';
		            }*/
		            else if (row.fstatus_id=="00"){
		                return 'background-color:#1C86EE;color:black;';
		            }
		            else{
		                return 'background-color:#A9A9A9;color:black;';
		            }
	            }
	        }
		});
	};
	
	function elecurve(){
  		Highcharts.setOptions({
  		    global: {
  		        useUTC: false
  		    }
  		});

  		$('#body31').highcharts({
  		    chart: {
  		        type: 'spline',
  		        animation: false, // don't animate in old IE
  		        marginRight: 70,
  		        events: {
  		            load: function () {
  		                // set up the updating of the chart each second
  		                    series = this.series[0],
  		                    chart = this,
  		                	timerele = window.setInterval(function () {}, 1000);
  		            }
  		        }
  		    },
  		    title: {
  		        text: '电压电流实时监测'
  		    },
  		    xAxis: {
  		        type: 'datetime',
  		        tickPixelInterval: 150,
  		        lineColor:'#FFFFFF',
  		        tickWidth:0,
	  		    labels:{
	  		    	enabled:false
	  		    }
  		    },
  		    yAxis: [{
                max:500, // 定义Y轴 最大值  
                min:0, // 定义最小值  
                minPadding: 0.2,   
                maxPadding: 0.2,  
                tickInterval:100,
                color:'#A020F0',
  		        title: {
  		            text: '电流',
  	                style: {  
  	                    color: '#A020F0'  
  	                }  
  		        }
  		    }],
  		    tooltip: {
  		        formatter: function () {
  		            return '<b>' + this.series.name + '</b><br/>' +
  		                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
  		                Highcharts.numberFormat(this.y, 2);
  		        }
  		    },
  		    legend: {
  		        enabled: false
  		    },
  		    exporting: {
  		        enabled: false
  		    },
  		    credits:{
  		      enabled:false // 禁用版权信息
  		    },
  		    series: [{
  		    	color:'#A020F0',
  		        name: '电流',
  		        
  		        data: (function () {
  		            // generate an array of random data
  		            var data = [],
  		                /*time = new Date(Date.parse("0000-00-00 00:00:00")),*/
  		                i;
  		            for (i = -19; i <= 0; i += 1) {
  		                data.push({
  		                    x: time[0]-1000+i*1000,
  		                	/*x: time + i*1000,*/
  		                    y: 0
  		                });
  		            }
  		            return data;
  		        }())
  		    }]
  		}, function(c) {
  		    activeLastPointToolip(c)
  		});
  		
  		activeLastPointToolip(chart);
	}
	
	function volcurve(){
  		Highcharts.setOptions({
  		    global: {
  		        useUTC: false
  		    }
  		});
 
  		$('#body32').highcharts({
  		    chart: {
  		        type: 'spline',
  		        animation: false, // don't animate in old IE
  		        marginRight: 70,
  		        events: {
  		            load: function () {
  		                // set up the updating of the chart each second
  		                    series1 = this.series[0],
  		                    chart1 = this;
  		            }
  		        }
  		    },
  		    title: {
  		        text: false
  		    },
  		    xAxis: {
  		        type: 'datetime',
  		        tickPixelInterval: 150/*,
  		        tickWidth:0,
	  		    labels:{
	  		    	enabled:false
	  		    }*/
  		    },
  		    yAxis: [{
                max:50, // 定义Y轴 最大值  
                min:0, // 定义最小值  
                minPadding: 0.2,   
                maxPadding: 0.2,  
                tickInterval:10,
                color:'#87CEFA',
  		    	title: {
  		            text: '电压',
  	                style: {  
  	                    color: '#87CEFA'  
  	                }  
  		        },
  		    }],
  		    tooltip: {
  		        formatter: function () {
  		            return '<b>' + this.series.name + '</b><br/>' +
  		                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
  		                Highcharts.numberFormat(this.y, 2);
  		        },
  		    },
  		    legend: {
  		        enabled: false
  		    },
  		    exporting: {
  		        enabled: false
  		    },
  		    credits:{
  		      enabled:false // 禁用版权信息
  		    },
  		    series: [{
  		        name: '电压',
  		        data: (function () {
  		            // generate an array of random data
  		            var data = [],
/*			                time = new Date(Date.parse("0000-00-00 00:00:00")),*/
		                i;
		            for (i = -19; i <= 0; i += 1) {
		                data.push({
		                    x: time[0]-1000+i*1000,
		                    y: 0
		                });
		            }
  		            return data;
  		        }()),
  		      
  		    }]
  		}, function(c) {
  		    activeLastPointToolip1(c)
  		});
  		
  		activeLastPointToolip1(chart1);
	
	}
	
	function modiflyNum(id,value)
	{	
		var elm=document.getElementById('time'+id.toString()).getElementsByTagName('li');
		var str=led[value];
		var cc=str.split(',');
		for(var i=0;i<7;i++)
		{
			elm[i].getElementsByTagName('img')[0].src="resources/images/back.png";
		}
		for(var i=0;i<cc.length;i++)
		{
			elm[parseInt(cc[i])].getElementsByTagName('img')[0].src="resources/images/purple.png";
		}
	}
	function modiflyNum1(id,value)
	{	
		var elm=document.getElementById('time'+id.toString()).getElementsByTagName('li');
		var str=led[value];
		var cc=str.split(',');
		for(var i=0;i<7;i++)
		{
			elm[i].getElementsByTagName('img')[0].src="resources/images/back.png";
		}
		for(var i=0;i<cc.length;i++)
		{
			elm[parseInt(cc[i])].getElementsByTagName('img')[0].src="resources/images/blue.png";
		}
	}
	
	function openss(avalue){
		for(var i=0;i<rows.length;i++){
			if(rows[i].fid==avalue){
				window.clearInterval(timerele);
				window.clearInterval(timervol);
				$('#body31').html("");
				$('#body32').html("");
				vol.length=0;
				ele.length=0;
				time.length=0;
				symbol=0;
				$("#macname").val("");
				$("#welname").val("");
				xuanze=rows[i].fequipment_no;
				iview(rows[i].fequipment_no);
			}
		}
	}
	
	function iview(value){
		var z=0;
		time.length=0;	
		vol.length=0;
		ele.length=0;
		var rowdex=0;
		rows=$('#dg').datagrid("getRows");
		for(var i = 0;i < redata.length;i+=69){
			var zu = document.getElementById("zuid").value;
			if(redata.substring(8+i, 12+i)!="0000"){
				for(var dex=0;dex<rows.length;dex++){					
					if(rows[dex].finsid==redata.substring(2+i,4+i)){
						if(machine.length==0){
							machine.push(redata.substring(4+i, 8+i));
						}else{
							for(var n=0;n<rows.length;n++){
							for(var j=0;j<machine.length;j++){
								if(machine[j]!=redata.substring(4+i, 8+i)&&redata.substring(4+i, 8+i)==rows[n].fequipment_no){
									if(j==machine.length-1){
										machine.push(redata.substring(4+i, 8+i));
									}
								}else{
									break;
								}
								}
							}
						}
					}
					
				    if((redata.substring(8+i, 12+i)!="0000")&&(redata.substring(4+i, 8+i)==rows[dex].fequipment_no)){
						rows[dex].fwelder_no=redata.substring(8+i, 12+i);
						rows[dex].fstatus_id=redata.substring(0+i, 2+i);
/*						var wwk = parseInt(redata.substring(53+i, 55+i))*3600+parseInt(redata.substring(56+i, 58+i))*60+parseInt(redata.substring(59+i, 61+i));
						var wwz = parseInt(redata.substring(61+i, 63+i))*3600+parseInt(redata.substring(64+i, 66+i))*60+parseInt(redata.substring(67+i, 69+i));
						rows[dex].percentage=parseFloat(((wwz-wwk)/wwz).toFixed(2));*/
						for(var k=0;k<welderName.length;k++){
							if(welderName[k].fwelder_no==redata.substring(8+i, 12+i)){
								rows[dex].fname=welderName[k].fname;
							}
						}
						$('#dg').datagrid('refreshRow', dex);
/*						$("a[id='view']").linkbutton({text:'查看',plain:true,iconCls:'icon-view'});*/
				    }
					if(starows.length!=0){
				        	for(var j=0;j<starows.length/2;j++){
				        		if(rows[dex].fequipment_no==starows[j].fname){
				        			rows[dex].percentage=(parseInt(starows[j].ftime)/parseInt(starows[starows.length/2+j].ftime)).toFixed(2);
				        			starows[j].ftime=0;
				        			starows[starows.length/2+j].ftime=0;
				        		}
				        	}
					};
		    		}
	            var work=0;
	            var wait=0;
	            var dglength=rows.length;
    			var rowsback = $('#dg').datagrid("getRows");
    			for(var ti=0;ti<rowsback.length;ti++){
    				if(rowsback[ti].fstatus_id=="03"||rowsback[ti].fstatus_id=="05"||rowsback[ti].fstatus_id=="07"){
    					work++;
    				}
    				if(rowsback[ti].fstatus_id=="00"){
    					wait++;
    				}
    			}
        		$("#on").textbox("setValue", work);
        		$("#wait").textbox("setValue", wait);
        		$("#off").textbox("setValue", dglength-wait-work)
					if((rowdex<machine.length)&&(rows.length!=0)){
					for(var dex1=0;dex1<rows.length;dex1++){
						if(machine[rowdex]==rows[dex1].fequipment_no){    
						        $('#dg').datagrid('insertRow', {  
						            index:0,  
						            row:rows[dex1],  
						        });     
						        $('#dg').datagrid('deleteRow', dex1+1);//删除一行
						}
					}
					rowdex++;
					}
				
				if(zu!=""&&zu!=null){
					if(redata.substring(4+i, 8+i)==value&&redata.substring(2+i, 4+i)==zu){
						ele.push(parseInt(redata.substring(12+i, 16+i)));
						vol.push(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2)));
//						vol.push(parseInt(redata.substring(16+i, 20+i)));
						time.push(Date.parse(redata.substring(20+i, 39+i)));
						maxele = parseInt(redata.substring(41+i, 44+i));
						minele = parseInt(redata.substring(44+i, 47+i));
						maxvol = parseInt(redata.substring(47+i, 50+i));
						minvol = parseInt(redata.substring(50+i, 53+i));
						if(symbol==0){
							elecurve();
							volcurve();
							symbol++;
							/*window.clearTimeout(tishi);*/
							clearInterval(tishi);
							for(var n=0;n<rows.length;n++){
								if(rows[n].finsid==parseInt(redata.substring(2+i, 4+i))){
									$("#zu").textbox("setValue", rows[n].finsname);
									document.getElementById("zuid").value=rows[n].finsid;
									break;
								}
							}
						}
						var backele=parseInt(redata.substring(12+i, 16+i)).toString();
						if(backele.length<4){
							var elelen=backele.length;
							for(var na=0;na<4-elelen;na++){
								backele="0"+backele;
							}
						}
						var backvol=parseInt(redata.substring(16+i, 20+i)).toString();
						if(backvol.length<4){
							var vollen=backvol.length;
							for(var nan=0;nan<4-vollen;nan++){
								backvol="0"+backvol;
							}
						}
						modiflyNum(1,parseInt(backele.charAt(0)));
						modiflyNum(2,parseInt(backele.charAt(1)));
						modiflyNum(3,parseInt(backele.charAt(2)));
						modiflyNum(4,parseInt(backele.charAt(3)));
						modiflyNum1(5,parseInt(backvol.charAt(0)));
						modiflyNum1(6,parseInt(backvol.charAt(1)));
						modiflyNum1(7,parseInt(backvol.charAt(2)));
						modiflyNum1(8,parseInt(backvol.charAt(3)));
//						document.getElementById("macname").value=value;
						$("#macname").val(value);
						for(var k=0;k<welderName.length;k++){
							if(welderName[k].fwelder_no==redata.substring(8+i, 12+i)){
//								document.getElementById("welname").value=welderName[k].fname;
								$("#welname").val(welderName[k].fname);
							}
						}
	                    var x = time[z],
	                    y = ele[z],
	                	v = vol[z];
			            if(z==0){
			                    series.addPoint([x, y], true, true);
				                activeLastPointToolip(chart);
			                    series1.addPoint([x, v], true, true);
				                activeLastPointToolip1(chart1);
				            
			            }else{
			            	 if(x>time[z-1]){
				                  series.addPoint([x, y], true, true);
				                  activeLastPointToolip(chart);
				                    series1.addPoint([x, v], true, true);
				                activeLastPointToolip1(chart1);
			            	 }
			            }
			            z++;
						sy++;
					}
				}else{
					if(redata.substring(4+i, 8+i)==value){
						ele.push(parseInt(redata.substring(12+i, 16+i)));
						vol.push(parseFloat((parseInt(redata.substring(16+i, 20+i))/10).toFixed(2)));
//						vol.push(parseInt(redata.substring(16+i, 20+i)));
						time.push(Date.parse(redata.substring(20+i, 39+i)));
						maxele = parseInt(redata.substring(41+i, 44+i));
						minele = parseInt(redata.substring(44+i, 47+i));
						maxvol = parseInt(redata.substring(47+i, 50+i));
						minvol = parseInt(redata.substring(50+i, 53+i));
						if(symbol==0){
							elecurve();
							volcurve();
							symbol++;
							/*window.clearTimeout(tishi);*/
							clearInterval(tishi);
							for(var n=0;n<rows.length;n++){
								if(rows[n].finsid==parseInt(redata.substring(2+i, 4+i))){
									$("#zu").textbox("setValue", rows[n].finsname);
									document.getElementById("zuid").value=rows[n].finsid;
									break;
								}
							}
						}
						var backele=parseInt(redata.substring(12+i, 16+i)).toString();
						if(backele.length<4){
							var elelen=backele.length;
							for(var na=0;na<4-elelen;na++){
								backele="0"+backele;
							}
						}
						var backvol=parseInt(redata.substring(16+i, 20+i)).toString();
						if(backvol.length<4){
							var vollen=backvol.length;
							for(var nan=0;nan<4-vollen;nan++){
								backvol="0"+backvol;
							}
						}
						modiflyNum(1,parseInt(backele.charAt(0)));
						modiflyNum(2,parseInt(backele.charAt(1)));
						modiflyNum(3,parseInt(backele.charAt(2)));
						modiflyNum(4,parseInt(backele.charAt(3)));
						modiflyNum1(5,parseInt(backvol.charAt(0)));
						modiflyNum1(6,parseInt(backvol.charAt(1)));
						modiflyNum1(7,parseInt(backvol.charAt(2)));
						modiflyNum1(8,parseInt(backvol.charAt(3)));
//						document.getElementById("macname").value=value;
						$("#macname").val(value);
						for(var k=0;k<welderName.length;k++){
							if(welderName[k].fwelder_no==redata.substring(8+i, 12+i)){
//								document.getElementById("welname").value=welderName[k].fname;
								$("#welname").val(welderName[k].fname);
							}
						}
	                    var x = time[z],
	                    y = ele[z],
	                	v = vol[z];
			            if(z==0){
			                    series.addPoint([x, y], true, true);
				                activeLastPointToolip(chart);
			                    series1.addPoint([x, v], true, true);
				                activeLastPointToolip1(chart1);
				            
			            }else{
			            	 if(x>time[z-1]){
				                  series.addPoint([x, y], true, true);
				                  activeLastPointToolip(chart);
				                    series1.addPoint([x, v], true, true);
				                activeLastPointToolip1(chart1);
			            	 }
			            }
			            z++;
					}
				}
			}
			if(starows.length==0){
				var arr  =
			     {
			         "fname" : redata.substring(4+i, 8+i),
			         "ftime" : 1
			     }
				var arr1  =
			     {
			         "fname" : redata.substring(4+i, 8+i),
			         "ftime" : 1
			     }
				starows.splice(starows.length/2, 0, arr);
				starows.push(arr1);
			}else{
				for(var sta=0;sta<starows.length/2;sta++){
				if(redata.substring(4+i, 8+i)==starows[sta].fname){
					if(redata.substring(0+i, 2+i)=="00"){
						starows[sta].ftime++;
					}
					starows[starows.length/2+sta].ftime++;
					break;
				}else{
					if(sta==starows.length/2-1){
						var arr  =
					     {
					         "fname" : redata.substring(4+i, 8+i),
					         "ftime" : 1
					     }
						var arr1  =
					     {
					         "fname" : redata.substring(4+i, 8+i),
					         "ftime" : 1
					     }
						starows.splice(starows.length/2, 0, arr);
						starows.push(arr1);
					}
				}
			}
		}
			/*tioshu=(i+69)/69;*/
		};
	}
	
    //监听窗口大小变化
    window.onresize = function() {
    	setTimeout(domresize, 500);
    }

    //改变表格高宽
    function domresize() {
    	$("#dg").datagrid('resize', {
    		height : $("#body").height(),
    		width : $("#body").width()
    	});
    }
  	
    
		function activeLastPointToolip(chart) {
  		    var points = chart.series[0].points;
  		    chart.yAxis[0].removePlotLine('plot-line-0');
  		    chart.yAxis[0].removePlotLine('plot-line-1');
  		    chart.yAxis[0].removePlotLine('plot-line-2');
/*  		    chart.tooltip.refresh(points[points.length -1]);
  		    chart.tooltip.refresh(points1[points1.length -1]);*/
  		  	chart.yAxis[0].addPlotLine({ //在y轴上增加 
  		  		value:maxele, //在值为2的地方 
  		  		width:2, //标示线的宽度为2px 
  		  		color: 'red', //标示线的颜色 
  		  	    dashStyle:'longdashdot',
  		  		id: 'plot-line-1', //标示线的id，在删除该标示线的时候需要该id标示 });
		          label:{
    		            text:'最高电流',     //标签的内容
    		            align:'center',                //标签的水平位置，水平居左,默认是水平居中center
    		            x:10                         //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
    		        }
  		  	});
  		  	chart.yAxis[0].addPlotLine({ //在y轴上增加 
  		  		value:minele, //在值为2的地方 
  		  		width:2, //标示线的宽度为2px 
  		  		color: 'red', //标示线的颜色 
  		  	    dashStyle:'longdashdot',
  		  		id: 'plot-line-2', //标示线的id，在删除该标示线的时候需要该id标示 });
		          label:{
    		            text:'最低电流',     //标签的内容
    		            align:'center',                //标签的水平位置，水平居左,默认是水平居中center
    		            x:10                     //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
    		        }
  		  	})	  	
  		  	chart.yAxis[0].addPlotLine({ //在y轴上增加 
  		  		value:(minele+maxele)/2, //在值为2的地方 
  		  		width:2, //标示线的宽度为2px 
  		  		color: 'red', //标示线的颜色 
  		  	    dashStyle:'longdashdot',
  		  		id: 'plot-line-0', //标示线的id，在删除该标示线的时候需要该id标示 });
		          label:{
    		            text:'预置电流',     //标签的内容
    		            align:'center',                //标签的水平位置，水平居左,默认是水平居中center
    		            x:10                     //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
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
  		  		value:maxvol, //在值为2的地方 
  		  		width:2, //标示线的宽度为2px 
  		  		color: 'black', //标示线的颜色 
  		  	    dashStyle:'longdashdot',
  		  		id: 'plot-line-3', //标示线的id，在删除该标示线的时候需要该id标示 });
		          label:{
    		            text:'最高电压',     //标签的内容
    		            align:'center',                //标签的水平位置，水平居左,默认是水平居中center
    		            x:10  
    		        }
  		  	})
  		  	chart.yAxis[0].addPlotLine({ //在y轴上增加 
  		  		value:minvol, //在值为2的地方 
  		  		width:2, //标示线的宽度为2px 
  		  		color: 'black', //标示线的颜色 
  		  	    dashStyle:'longdashdot',
  		  		id: 'plot-line-4', //标示线的id，在删除该标示线的时候需要该id标示 });
		          label:{
    		            text:'最低电压',     //标签的内容
    		            align:'center',                //标签的水平位置，水平居左,默认是水平居中center
    		            x:10                         //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
    		        }
  		  	})
  		  	chart.yAxis[0].addPlotLine({ //在y轴上增加 
  		  		value:(minvol+maxvol)/2, //在值为2的地方 
  		  		width:2, //标示线的宽度为2px 
  		  		color: 'black', //标示线的颜色 
  		  	    dashStyle:'longdashdot',
  		  		id: 'plot-line-5', //标示线的id，在删除该标示线的时候需要该id标示 });
		          label:{
    		            text:'预置电压',     //标签的内容
    		            align:'center',                //标签的水平位置，水平居左,默认是水平居中center
    		            x:10                         //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
    		        }
  		  	})		  	
  		}
  		
        //监听窗口大小变化
        window.onresize = function() {
        	setTimeout(domresize, 500);
        }

        //改变表格高宽
        function domresize() {
        	$("#dg").datagrid('resize', {
        		height : $("#body4").height(),
        		width : $("#body4").width()
        	});
        }