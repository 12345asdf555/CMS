$(function(){
	weldDatagrid();
	insframeworkTree();
});

function weldDatagrid(){
	$("#dg").datagrid( {
		height : $("#body").height()-$("#commit_btn").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "weldingMachine/getWedlingMachineList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		pagination : true,
		fitColumns : true,
		columns : [ [{
			field : 'id',
			title : '序号',
			width : 50,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'equipmentNo',
			title : '焊机编号',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'insframeworkName',
			title : '所属项目',
			width : 150,
			halign : "center",
			align : "center"
		},{
			field : 'gatherNo',
			title : '采集序号',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'edit',
			title : '编辑',
			width : 150,
			halign : "center",
			align : "center",
			formatter : function(value,row,index){
				return  '<a id="weldhistory" class="easyui-linkbutton" href="companyChart/gohistory?id='+row.id+'"/>';
			}
		}] ],
		pagination : true,
		onLoadSuccess:function(data){
	        $("a[id='weldhistory']").linkbutton({text:'焊机运行回溯',plain:true,iconCls:'icon-search'});
		},
		nowrap : false
	});
}

var parent = "";
function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			$("#dg").datagrid('load', {//刷新状态
				"parent" : node.id
			})
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

