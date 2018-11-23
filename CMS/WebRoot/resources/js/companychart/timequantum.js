$(function(){
	weldDatagrid();
	insframeworkTree();
});

var chartStr = "";
function setParam(){
	var dtoTime1 = $("#dtoTime1").datetimebox('getValue');
	var dtoTime2 = $("#dtoTime2").datetimebox('getValue');
	chartStr = "?dtoTime1="+dtoTime1+"&dtoTime2="+dtoTime2+"&parent="+parent;
}

function weldDatagrid(){
	setParam();
	$("#dg").datagrid( {
		height : $("#body").height()-$("#commit_btn").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "companyChart/getTimequantum"+chartStr,
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		pagination : true,
		fitColumns : true,
		columns : [ [ {
			field : 'welderno',
			title : '焊工编号',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'weldername',
			title : '焊工姓名',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'junctionno',
			title : '焊口编号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'machineno',
			title : '焊机编号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'itemname',
			title : '所属项目',
			width : 120,
			halign : "center",
			align : "center"
		}, {
			field : 'starttime',
			title : '开始时间',
			width : 120,
			halign : "center",
			align : "center"
		}, {
			field : 'endtime',
			title : '结束时间',
			width : 120,
			halign : "center",
			align : "center"
		}, {
			field : 'maxelectricity',
			title : '最大电流',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'minelectricity',
			title : '最小电流',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'id',
			title : '超标段id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'edit',
			title : '编辑',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value,row,index){
				return  '<a id="getoverproof" class="easyui-linkbutton" href="companyChart/searchoverproof?id='+row.id+'"/>';
			}
		}] ],
		pagination : true,
		onLoadSuccess:function(data){
	        $("a[id='getoverproof']").linkbutton({text:'查看超标',plain:true,iconCls:'icon-search'});
		},
		nowrap : false
	});
}

var parent = "";
function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			parent = node.id;
			weldDatagrid();
		 }
	})
}

var parent = "";
function serach(){
	chartStr = "";
	if($("#myTree").tree('getSelected')!=null){
		parent = $("#myTree").tree('getSelected').id
	}
	weldDatagrid();
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#body").height()-$("#commit_btn").height(),
		width : $("#body").width()
	});
}

