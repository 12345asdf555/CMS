$(function(){
	DictionaryDataGrid();
});
function DictionaryDataGrid(){
	$("#dg").datagrid({
		fitColumns:true,
		height:$("#body").height(),
		width:$("#body").width(),
		idField:'id',
		pageSize:10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url:"Dictionary/getDictionaryAll",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns:[[{
			field:'id',
			title:'序号',
			width:100,
			halign : "center",
			align : "left",
			hidden:true
		},
		{
			field:'valueName',
			title:'名称',
			width:350,
			halign:"center",
			align:"left"
		}
		,{
			field:'back',
			title:'类型',
			width:350,
			halign:"center",
			align:"left"
		},
		{
			field:'edit',
			title:'编辑',
			width:250,
			halign:"center",
			align:"left",
			formatter:function(value,row,index){
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="Dictionary/goEditDictionary?id='+row.id+'"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="Dictionary/goRemoveDictionary?id='+row.id+'"/>';
				return str;
			}
		}
		]],
		toolbar:'#toolbar',
		pagination : true,
		onLoadSuccess:function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
		}
	});
}
function searchDic(){
	var cols=$("#fields").combobox("getValue");
	var content=$("#content").val();
	var searchStr=cols+" like '%"+content+"%'";
	$('#dg').datagrid('load', {
		"searchStr" : searchStr
	});
}