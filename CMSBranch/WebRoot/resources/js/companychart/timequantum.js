$(function(){
	weldDatagrid();
	page();
});

function weldDatagrid(){
	$("#timequantumTable").datagrid( {
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
//		url : "companyChart/getTimequantum?time="+$("#weldtime").val()+"&welder="+$("#welder").val()+"&junction="+$("#junction").val(),
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		pagination : true,
		fitColumns : true,
		columns : [ [ {
			field : 'welder',
			title : '焊工编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'junction',
			title : '焊口编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'weldtime',
			title : '时间段',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'electricity',
			title : '焊接电流',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'maxelectricity',
			title : '最大电流',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'minelectricity',
			title : '最小电流',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'weldtimes',
			title : '时间数组',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'electricitys',
			title : '电流数组',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'edit',
			title : '编辑',
			width : 100,
			halign : "center",
			align : "left",
			formatter : function(value,row,index){
				return  '<a id="getoverproof" class="easyui-linkbutton" href="companyChart/searchoverproof?welder='+row.welder+'&junction='+row.junction+'&maxelectricity='+row.maxelectricity+'&minelectricity='+row.minelectricity+'&weldtime='+row.weldtimes+'&electricitys='+row.electricitys+'"/>';
			}
		}] ],
		pagination : true,
		onLoadSuccess:function(data){
	        $("a[id='getoverproof']").linkbutton({text:'查看超标',plain:true,iconCls:'icon-search'});
		},
		nowrap : false
	});
}

function formatterDate(){
	var now = new Date();
    var year = now.getFullYear();//年  
    var month = now.getMonth() + 1;//月  
    var day = now.getDate();//日
    return year+"-"+month+"-"+day;
}

//假分页
function page(){
	$('#timequantumTable').datagrid({ loadFilter: pagerFilter }).datagrid({  
        url:"companyChart/getTimequantum?time="+$("#weldtime").val()+"&welder="+$("#welder").val()+"&junction="+$("#junction").val()    //加载数据  
    });  

//分页数据的操作  
function pagerFilter(data) {  
    if (typeof data.length == 'number' && typeof data.splice == 'function') {   // is array  
        data = {  
            total: data.length,  
            rows: data  
        }  
    }  
    var dg = $(this);  
    var opts = dg.datagrid('options');  
    var pager = dg.datagrid('getPager');  
    pager.pagination({  
        onSelectPage: function (pageNum, pageSize) {  
            opts.pageNumber = pageNum;  
            opts.pageSize = pageSize;  
            pager.pagination('refresh', {  
                pageNumber: pageNum,  
                pageSize: pageSize  
            });  
            dg.datagrid('loadData', data);  
        }  
    });  
    if (!data.originalRows) {  
        data.originalRows = (data.rows);  
    }  
    var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);  
    var end = start + parseInt(opts.pageSize);  
    data.rows = (data.originalRows.slice(start, end));  
    return data;  
}
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#timequantumTable").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}

