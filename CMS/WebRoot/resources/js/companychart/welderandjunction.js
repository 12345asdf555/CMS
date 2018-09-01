$(function(){
	weldDatagrid();
	insframeworkTree();
	$("input",$("#dtoTime1").next("span")).blur(function(){
		var newvalue = $("#dtoTime1").datebox('getValue');
		if(newvalue==null || newvalue==""){
			$("#dtoTime1").datebox('setValue',formatterDate());
		}
		$("#welderTable").datagrid('load',{
			"parent" : parent,
			"dtoTime" : newvalue
		})
	});
//	$("#dtoTime1").datebox({
//		onBlur: function (newvalue,oldvalue) {
//			if(newvalue==null || newvalue==""){
//				$("#dtoTime1").datebox('setValue',formatterDate());
//			}
//			$("#welderTable").datagrid('load',{
//				"parent" : parent,
//				"dtoTime" : $("#dtoTime1").datebox('getValue')
//			})
//		}
//	})
	$("#dtoTime1").datebox('setValue',formatterDate());
});
var chartStr = "";
function setParam(){
	var dtoTime1 = $("#dtoTime1").datebox('getValue');
	chartStr = "?dtoTime1="+dtoTime1;
}

function weldDatagrid(){
	$("#welderTable").datagrid( {
		height : $("#northbody").height(),
		width : $("#northbody").width(),
		idField : 'id',
		pageSize : 5,
		pageList : [ 5,10, 15, 20],
		url : "welder/getOverWelder",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		pagination : true,
		fitColumns : true,
		columns : [ [  {
		    field:'ck',
			checkbox:true
		},{
			field : 'id',
			title : '序号',
			width : 100,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'name',
			title : '焊工姓名',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'welderno',
			title : '编号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'iid',
			title : '项目id',
			width : 100,
			halign : "center",
			align : "center",
			hidden: true
		}, {
			field : 'itemname',
			title : '所属项目',
			width : 100,
			halign : "center",
			align : "center"
		}] ],
		onCheck : function(index,row){
    		chartStr = "";
        	setParam();
        	chartStr+="&welder="+row.welderno+"&parent="+row.iid;
    		weldedJunctionDatagrid();
        },
        onClickRow : function(index,row){
    		chartStr = "";
        	setParam();
        	chartStr+="&welder="+row.welderno+"&parent="+row.iid;
    		weldedJunctionDatagrid();
        },
        onLoadSuccess : function(index,row){
        	$("#welderTable").datagrid('selectRow',0);
        },
		nowrap : false,
		toolbar : '#commit_btn'
	});
}


function weldedJunctionDatagrid(){
	$("#weldedJunctionTable").datagrid( {
		height : $("#southbody").height(),
		width : $("#southbody").width(),
		idField : 'id',
		pageSize : 5,
		pageList : [ 5,10, 15, 20],
		url : "companyChart/getJunctionByWelder"+chartStr,
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [  {
		    field:'ck',
			checkbox:true
		},{
			field : 'weldedJunctionno',
			title : '焊口编号',
			halign : "center",
			align : "center"
		}, {
			field : 'maxElectricity',
			title : '电流上限',
			halign : "center",
			align : "center"
		}, {
			field : 'minElectricity',
			title : '电流下限',
			halign : "center",
			align : "center"
		}, {
			field : 'maxValtage',
			title : '电压上限',
			halign : "center",
			align : "center"
		}, {
			field : 'minValtage',
			title : '电压下限',
			halign : "center",
			align : "center"
		}, {
			field : 'itemname',
			title : '所属项目',
			halign : "center",
			align : "center"
		}, {
			field : 'externalDiameter',
			title : '上游外径',
			halign : "center",
			align : "center"
		}, {
			field : 'nextexternaldiameter',
			title : '下游外径',
			halign : "center",
			align : "center"
		}, {
			field : 'wallThickness',
			title : '上游壁厚',
			halign : "center",
			align : "center"
		}, {
			field : 'nextwall_thickness',
			title : '下游璧厚',
			halign : "center",
			align : "center"
		}, {
			field : 'material',
			title : '上游材质',
			halign : "center",
			align : "center"
		}, {
			field : 'next_material',
			title : '下游材质',
			halign : "center",
			align : "center"
		}, {
			field : 'edit',
			title : '编辑',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value,row,index){
				return  '<a id="getoverproof" class="easyui-linkbutton" href="javascript:searchoverproof()"/>';
			}
		}] ],
		pagination : true,
		onLoadSuccess:function(data){
	        $("a[id='getoverproof']").linkbutton({text:'查看超标',plain:true,iconCls:'icon-search'});
		}
	});
}

function serachjunction(){
	var total = $("#welderTable").datagrid("getData").total;
	if(total == 0){
		alert("该时间内无超标焊工！");
	}else{
		var row = $("#welderTable").datagrid('getSelected');
		if(row==null){
			alert("请先选择焊工！");
		}else{
			chartStr = "";
			setParam();
			chartStr += "&welder="+row.welderno+"&parent="+row.iid;;
			weldedJunctionDatagrid();
		}
	}
}

function searchoverproof(){
	var row = $("#welderTable").datagrid('getSelected');
	var rows = $("#weldedJunctionTable").datagrid('getSelected');
	if(row==null){
		alert("请先选择焊工！");
	}else{
		var url = "companyChart/goOverproofTimeQuantum?welder="+row.welderno+"&junction="+rows.weldedJunctionno+"&weldtime="+$("#dtoTime1").datebox('getValue');
		var img = new Image();
	    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
	    url = img.src;  // 此时相对路径已经变成绝对路径
	    img.src = null; // 取消请求
		window.location.href = encodeURI(url);
	}
}

function formatterDate(){
	var now = new Date();
    var year = now.getFullYear();//年  
    var month = now.getMonth() + 1;//月  
    var day = now.getDate();//日
    if(month < 10){
    	month = "0"+month;
    }
      
    if(day < 10){
    	day = "0"+day;
    } 
    return year+"-"+month+"-"+day;
}

var parent = "";
function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			parent = node.id;
			$("#welderTable").datagrid('load',{
				"parent" : parent,
				"dtoTime" : $("#dtoTime1").datebox('getValue')
			});
			$('#weldedJunctionTable').datagrid('loadData',{total:0,rows:[]})
		 }
	})
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#welderTable").datagrid('resize', {
		height : $("#northbody").height(),
		width : $("#northbody").width()
	});
	$("#weldedJunctionTable").datagrid('resize', {
		height : $("#southbody").height(),
		width : $("#southbody").width()
	});
}

