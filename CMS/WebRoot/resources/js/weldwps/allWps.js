$(function() {
	$("#dg").datagrid({
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "wps/getAllWps",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		showPageList : false,
		columns : [ [ {
			field : 'fid',
			title : 'FID',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'fwpsnum',
			title : '工艺编号',
			width : 100,
			halign : "center",
			align : "center",
			formatter : function(value,row,index){
				return "<a href='wps/goShowWps?id="+row.fid+"'>"+value+"</a>";
			}
		}, {
			field : 'fversions',
			title : '版本',
			width : 80,
			halign : "center",
			align : "center"
		}, {
			field : 'fproject_code',
			title : '焊工考试项目代号',
			width : 600,
			halign : "center",
			align : "center"
		}, {
			field : 'edit',
			title : '编辑',
			width : 130,
			halign : "center",
			align : "center",
			formatter : function(value, row, index) {
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="wps/goEditWps?id='+row.fid+'"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="wps/goRemoveWps?id='+row.fid+'"/>';
				return str;
			}
		} ] ],
		toolbar : '#dg_btn',
		onLoadSuccess : function(data) {
			$("a[id='edit']").linkbutton({
				text : '修改',
				plain : true,
				iconCls : 'icon-edit'
			});
			$("a[id='remove']").linkbutton({
				text : '删除',
				plain : true,
				iconCls : 'icon-remove'
			});
		}
	});

})
//导出到excel
function exportWPS() {
	var wpstype = $("input[name='wps']:checked").val();
	var url = "export/exporWPS";
	if(wpstype==1){
		url = "export/exporChiledrenWPS";
	}
	$.messager.confirm("提示", "文件默认保存在浏览器的默认路径，<br/>如需更改路径请设置浏览器的<br/>“下载前询问每个文件的保存位置“属性！", function(result) {
		if (result) {
			var img = new Image();
			img.src = url; // 设置相对路径给Image, 此时会发送出请求
			url = img.src; // 此时相对路径已经变成绝对路径
			img.src = null; // 取消请求
			window.location.href = encodeURI(url);
		}
	});
}

//导入
function importclick() {
	$("#importdiv").dialog("open").dialog("setTitle", "从excel导入数据");
}

function importWPS() {
	var file = $("#file").val();
	if (file == null || file == "") {
		$.messager.alert("提示", "请选择要上传的文件！");
		return false;
	} else {
		var wpstype = $("input[name='wps']:checked").val();
		var url = "import/importWps";
		if(wpstype==1){
			url = "import/importChildrenWPS";
		}
		$('#importfm').form('submit', {
			url : url,
			success : function(result) {
				if (result) {
					var result = eval('(' + result + ')');
					if (!result.success) {
						$.messager.show({
							title : 'Error',
							msg : result.msg
						});
					} else {
						$('#importdiv').dialog('close');
						$('#dg').datagrid('reload');
						$.messager.alert("提示", result.msg);
					}
				}

			},
			error : function(errorMsg) {
				alert("数据请求失败，请联系系统管理员!");
			}
		});
	}
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