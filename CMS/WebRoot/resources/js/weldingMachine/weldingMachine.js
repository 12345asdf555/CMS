$(function(){
	insframeworkTree();
	weldingMachineDatagrid();
	var width = $("#treeDiv").width();
	$(".easyui-layout").layout({
		onCollapse:function(){
			$("#weldingmachineTable").datagrid( {
				height : $("#body").height(),
				width : $("#body").width()
			})
		},
		onExpand:function(){
			$("#weldingmachineTable").datagrid( {
				height : $("#body").height(),
				width : $("#body").width()
			})
		}
	});
});

function weldingMachineDatagrid(){
	$("#weldingmachineTable").datagrid( {
		height : $("#body").height(),
		width : $("#body").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "weldingMachine/getWedlingMachineList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false, 
        columns : [ [ {
			field : 'id',
			title : '序号',
			width : 50,
			halign : "center",
			align : "left",
			hidden:true
		}, {
			field : 'equipmentNo',
			title : '固定资产编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'typeName',
			title : '设备类型',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'jointime',
			title : '入厂时间',
			width : 170,
			halign : "center",
			align : "left"
		}, {
			field : 'insframeworkName',
			title : '所属项目',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'statusName',
			title : '状态',
			width : 80,
			halign : "center",
			align : "left"
		} , {
			field : 'manufacturerName',
			title : '厂家',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'isnetworking',
			title : '是否在网',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'gatherNo',
			title : '采集序号',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'position',
			title : '位置',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'money',
			title : '价值',
			width : 80,
			halign : "center",
			align : "left"
		}, {
			field : 'statusId',
			title : '状态id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'isnetworkingId',
			title : '是否联网id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'manufacturerId',
			title : '厂商id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'typeId',
			title : '类型id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'insframeworkId',
			title : '项目id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'edit',
			title : '编辑',
			width : 250,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="javascript:editMachine('+row.insframeworkId+','+row.id+','+true+')"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:editMachine('+row.insframeworkId+','+row.id+','+false+')"/>';
				str += '<a id="maintain" class="easyui-linkbutton" href="weldingMachine/goMaintain?wid='+row.id+'"/>';
				return str;
			}
		}] ],
		toolbar : '#weldingmachineTable_btn',
		pagination : true,
		fitColumns : true,
		onLoadSuccess:function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
	        $("a[id='maintain']").linkbutton({text:'维修记录',plain:true,iconCls:'icon-edit'});
		}
	});
}

function editMachine(id,wid,flags){
	$.ajax({  
        type : "post",  
        async : false,
        url : "insframework/getUserAuthority?id="+id,  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {
            if (result) {
        		if(result.afreshLogin==null || result.afreshLogin==""){
            		if(result.flag){
            			var url = "";
            			if(flags){
            				url = "weldingMachine/goEditWeldingMachine?wid="+wid;
            			}else{
            				url = "weldingMachine/goremoveWeldingMachine?wid="+wid+"&insfid="+id;
            			}
	       				var img = new Image();
	       			    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
	       			    url = img.src;  // 此时相对路径已经变成绝对路径
	       			    img.src = null; // 取消请求
	       				window.location.href = encodeURI(url);
            		}else{
            			alert("对不起，您不能对你的上级或同级部门的数据进行编辑");
            		}
            	}else{
            		$.messager.confirm("提示",result.afreshLogin,function(data){
		        		 if(data){
		        			var url = "login.jsp";
		       				var img = new Image();
		       			    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
		       			    url = img.src;  // 此时相对路径已经变成绝对路径
		       			    img.src = null; // 取消请求
		     				 top.location.href = url;
		     			 }
		     		 });
		        }
           }
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
   });
}

//导出到excel
function exportWeldingMachine(){
	$.messager.confirm("提示", "文件默认保存在浏览器的默认路径，<br/>如需更改路径请设置浏览器的<br/>“下载前询问每个文件的保存位置“属性！",function(result){
		if(result){
			var url = "export/exporWeldingMachine";
			var img = new Image();
		    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
		    url = img.src;  // 此时相对路径已经变成绝对路径
		    img.src = null; // 取消请求
			window.location.href = encodeURI(url);
		}
	});
}

//导入
function importclick(){
	$("#importdiv").dialog("open").dialog("setTitle","从excel导入数据");
}

function importWeldingMachine(){
	var file = $("#file").val();
	if(file == null || file == ""){
		$.messager.alert("提示", "请选择要上传的文件！");
		return false;
	}else{
		$('#importfm').form('submit', {
			url : "import/importWeldingMachine",
			success : function(result) {
				if(result){
					var result = eval('(' + result + ')');
					if (!result.success) {
						$.messager.show( {
							title : 'Error',
							msg : result.msg
						});
					} else {
						$('#importdiv').dialog('close');
						$('#weldingmachineTable').datagrid('reload');
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

function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			$("#weldingmachineTable").datagrid('load',{
				"parent" : node.id
			})
			$("#treeid").val(node.id);
		 }
	})
}

//监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
	$("#weldingmachineTable").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}

