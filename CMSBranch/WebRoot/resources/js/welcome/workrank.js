$(function(){
	workRankDatagrid();
})

function workRankDatagrid(id){
	$("#dg").datagrid( {
		fitColumns : true,
		scrollbarSize:0,//舍去表格右侧多余留白
		height : $("#body").height(),
		width : $("#body").width(),
		url : "hierarchy/getWorkRank?time1="+$("#dtoTime1").datetimebox("getValue")+"&time2="+$("#dtoTime2").datetimebox("getValue"),
		singleSelect : true,
		columns : [ [ {
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
	    toolbar : '#dg_btn',
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