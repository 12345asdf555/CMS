$(function(){
	dgDatagrid();
});

function dgDatagrid(){
	$("#dg").datagrid( {
		fitColumns : true,
		height : $("#body").height()-40,
		width : $("#body").width()-30,
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "weldedjunction/getLiveJunctionList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 30,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'weldedJunctionno',
			title : '编号',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'itemname',
			title : '所属项目',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'material',
			title : '上游材质',
			width : 80,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'wallThickness',
			title : '上游壁厚',
			width : 60,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'externalDiameter',
			title : '上游外径',
			width : 60,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'next_material',
			title : '下游材质',
			width : 80,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'nextwall_thickness',
			title : '下游璧厚',
			width : 60,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'nextexternaldiameter',
			title : '下游外径',
			width : 60,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'maxElectricity',
			title : '电流上限',
			width : 50,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'minElectricity',
			title : '电流下限',
			width : 50,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'maxValtage',
			title : '电压上限',
			width : 50,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'minValtage',
			title : '电压下限',
			width : 50,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'starttime',
			title : '开始时间',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'endtime',
			title : '结束时间',
			width : 150,
			halign : "center",
			align : "center"
		}, {
			field : 'itemid',
			title : '组织机构id',
			width : 50,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'edit',
			title : '编辑',
			width : 100,
			halign : "center",
			align : "center",
			formatter: function(value,row,index){
				var str = '<a id="look" class="easyui-linkbutton" href="android/goJunctioncurve?no='+row.weldedJunctionno+'&itemid='+row.itemid+'"/>';
				return str;
			}
		}] ],
		toolbar : '#dg_btn',
		pagination : true,
		onLoadSuccess: function(data){
			$("a[id='look']").linkbutton({text:'实时曲线',plain:true,iconCls:'icon-search'});
		}
	});
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

