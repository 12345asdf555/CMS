$(function(){
	GatherDatagrid();
	insframeworkTree();
});

function GatherDatagrid(){
	$("#gatherTable").datagrid( {
		fitColumns : true,
		height : $("#body").height()-45,
		width : $("#body").width()-30,
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : "gather/getGatherList",
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : '序号',
			width : 100,
			halign : "center",
			align : "center",
			hidden:true
		}, {
			field : 'gatherNo',
			title : '采集模块编号',
			width : 120,
			halign : "center",
			align : "center"
		}, {
			field : 'itemid',
			title : '项目id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'itemname',
			title : '所属项目',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'status',
			title : '采集模块状态',
			width : 130,
			halign : "center",
			align : "center"
		}, {
			field : 'protocol',
			title : '采集模块通讯协议',
			width : 130,
			halign : "center",
			align : "center"
		}, {
			field : 'ipurl',
			title : '采集模块IP地址',
			width : 130,
			halign : "center",
			align : "center"
		}, {
			field : 'macurl',
			title : '采集模块MAC地址',
			width : 130,
			halign : "center",
			align : "center"
		}, {
			field : 'leavetime',
			title : '采集模块出厂时间',
			width : 130,
			halign : "center",
			align : "center"
		}, {
			field : 'edit',
			title : '编辑',
			width : 130,
			halign : "center",
			align : "center",
			formatter:function(value,row,index){
				var str = "";
				str += '<a id="edit" class="easyui-linkbutton" href="javascript:editGather1('+row.itemid+','+row.id+','+true+')"/>';
				str += '<a id="remove" class="easyui-linkbutton" href="javascript:editGather1('+row.itemid+','+row.id+','+false+')"/>';
				return str;
			}
		}] ],
		toolbar : '#gather_btn',
		pagination : true,
		nowrap : false,
		rowStyler: function(index,row){
            if ((index % 2)!=0){
            	//处理行代背景色后无法选中
            	var color=new Object();
                color.class="rowColor";
                return color;
            }
        },
		onLoadSuccess:function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
		}
	});
}

function editGather1(id,gid,flags){
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
            				editGather2();
            			}else{
            				removeGather2();
            			}
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

var url = "";
function removeGather2(){
	$('#rfm').form('clear');
	var row = $('#gatherTable').datagrid('getSelected');//获取整行的值
	if (row) {
		$('#rdlg').window( {
			title : "删除采集模块",
			modal : true
		});
		$('#rdlg').window('open');
		$('#rfm').form('load', row);
		url = "gather/removeGather?id="+row.id+"&insfid="+row.itemid;
	}
}
function removeGather1(){
	$.messager.confirm('提示', '此操作不可撤销并同时解绑焊机设备，是否确认删除?', function(flag) {
		if (flag) {
			$.ajax({  
		        type : "post",  
		        async : false,
		        url : url,  
		        data : {},  
		        dataType : "json", //返回数据形式为json  
		        success : function(result) {
		            if (result) {
		            	if (!result.success) {
							$.messager.show( {
								title : 'Error',
								msg : result.errorMsg
							});
						} else {
							$.messager.alert("提示", "删除成功！");
							if(result.msg!=null){
								$.messager.show( {title : '提示',msg : result.msg});
							}
							$('#rdlg').dialog('close');
							$('#gatherTable').datagrid('reload');
						}
		            }  
		        },  
		        error : function(errorMsg) {  
		            alert("数据请求失败，请联系系统管理员!");  
		        }  
		   }); 
		}
	});
}
//树形菜单点击事件
function insframeworkTree(){
	$("#myTree").tree({  
		onClick : function(node){
			$("#gatherTable").datagrid('load',{
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
	$("#gatherTable").datagrid('resize', {
		height : $("#body").height(),
		width : $("#body").width()
	});
}

