$(function(){
	detailOverproofDatagrid();
})

function detailOverproofDatagrid(){
	var parent = $("#parent").val();
	var weldtime = $("#weldtime").val();
	var time1 = $("#time1").val();
	var time2 = $("#time2").val();
	$("#DetailOverproofTable").datagrid( {
		fitColumns : true,
		height : $("#body").height() - $("#DetailOverproof_btn").height()-20,
		width : $("#body").width(),
		idField : 'id',
		url : "itemChart/getDatailOverproof?parent="+parent+"&weldtime="+weldtime+"&time1="+time1+"&time2="+time2,
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50],
		rownumbers : true,
		showPageList : false,
		pagination : true,
		columns : [ [ {
			field : 'iname',
			title : '项目部',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'fmachine_id',
			title : '设备编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'wname',
			title : '焊工',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'fwelder_id',
			title : '焊工编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'fjunction_id',
			title : '焊口编号',
			width : 100,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
				return "<a href='junctionChart/gojunctionoverproof?welderno="+row.fwelder_id+"&machineno="+row.fmachine_id+"&junctionno="+row.fjunction_id+"&time="+row.weldtime+"&itemid="+row.iid+"'>"+value+"</a>"
			}
		}, {
			field : 'weldtime',
			title : '日期',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'jidgather',
			title : '采集编号',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'fmax_electricity',
			title : '最大电流',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'fmin_electricity',
			title : '最小电流',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'overnum',
			title : '超标次数',
			width : 100,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
				return row.overproof;
			}
		}, {
			field : 'overproof',
			title : '超标率',
			width : 100,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
				var count = "1";
				$.ajax({  
			        type : "post",  
			        async : false,
			        url : "itemChart/getCountTime",  
			        data : {
			        	"welderno":row.fwelder_id,
			        	"machineno":row.fmachine_id,
			        	"junctionno":row.fjunction_id,
			        	"time":row.weldtime,
			        	"id" : row.iid
			        },  
			        dataType : "json", 
			        success : function(result) {
			        	if(result.count<=0){
			            	count =  "0%";
			        	}else if (result) {
			            	count =  ((value/result.count)*100).toFixed(2) + "%";
			            }
			        },  
			        error : function(errorMsg) {  
			            alert("数据请求失败，请联系系统管理员!");  
			        }  
			   }); 
				return count;
			}
		}, {
			field : 'jid',
			title : '焊口id',
			width : 100,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'iid',
			title : '项目id',
			width : 100,
			halign : "center",
			align : "left",
			hidden:true
		}] ]
	});
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#DetailOverproofTable").datagrid('resize', {
		height : $("#body").height() - $("#DetailOverproof_btn").height(),
		width : $("#body").width()
	});
}
