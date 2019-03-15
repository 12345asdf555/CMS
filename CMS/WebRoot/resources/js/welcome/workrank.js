$(function(){
	workRankDatagrid();
})

function workRankDatagrid(id){
	$("#dg").datagrid( {
		fitColumns : true,
		remoteSort : false,
		height : $("#body").height()-40,
		width : $("#body").width()-35,
		url : "hierarchy/getWorkRank?time1="+$("#dtoTime1").datetimebox("getValue")+"&time2="+$("#dtoTime2").datetimebox("getValue"),
		singleSelect : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		pagination : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 100,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'rownum',
			title : '排名',
			width : 100,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'welderno',
			title : '工号',
			width : 100,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'name',
			title : '姓名',
			width : 100,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'item',
			title : '班组',
			width : 150,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			}
		}, {
			field : 'hour',
			title : '累计焊接工时',
			width : 100,
			halign : "center",
			align : "center",
			sortable : true,
			sorter : function(a,b){
				return (a>b?1:-1);
			},
			formatter : function(value,row,index){
				return value + "小时";
			}
		}]],
	    toolbar : '#dg_btn'
	});
}

function serach(){
	workRankDatagrid();
}

//改变表格高宽
function domresize() {
	$("#dg").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}