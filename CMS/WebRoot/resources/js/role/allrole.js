/**
 * 
 */
        $(function(){ 
	    $("#tt").datagrid( {
		fitColumns : true,
		height : ($("#body").height()),
		width : $("#body").width(),
		idField : 'authorities_desc',
		url : "role/getAllAuthority",
		rownumbers : false,
		showPageList : false,
		checkOnSelect:true,
		selectOnCheck:true,
		columns : [ [ {
		    field:'ck',
			checkbox:true
		},{
			field : 'authorities_desc',
			title : '权限描述',
			width : 100,
			halign : "center",
			align : "left"
		}]]
		
	});
})   
       
       $(function(){
	    $("#dg").datagrid( {
		fitColumns : true,
		height : ($("#body").height()),
		width : $("#body").width(),
		idField : 'id',
		toolbar : "#toolbar",
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],  
		url : "role/getAllRole",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'roles_name',
			title : '角色名',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'roles_desc',
			title : '描述',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'status',
			title : '状态',
			width : 100,
			halign : "center",
			align : "left"
        }, {
			field : 'authority',
			title : '权限列表',
			width : 100,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
			var str = "";
			str += '<a id="authority" class="easyui-linkbutton" href="javascript:authority('+row.id+')"/>';
			return str; 
			}
        }, {
			field : 'user',
			title : '分配用户',
			width : 100,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
			var str = "";
			str += '<a id="user" class="easyui-linkbutton" href="role/todtbUser?id='+row.id+'"/>';
			return str; 
			}
		}, {
			field : 'edit',
			title : '编辑',
			width : 130,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
			var str = "";
			str += '<a id="edit" class="easyui-linkbutton" href="role/getRole?id='+row.id+'"/>';
			str += '<a id="remove" class="easyui-linkbutton" href="role/desRole?id='+row.id+'"/>';
			return str;
			}
		}]],
		toolbar : '#toolbar',
		onLoadSuccess:function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
	        $("a[id='authority']").linkbutton({text:'权限列表',plain:true,iconCls:'icon-authority'});
	        $("a[id='user']").linkbutton({text:'分配用户',plain:true,iconCls:'icon-user'});
	        }
	});
})

       function removeRole(id){
		$.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
			if (flag) {
				$.ajax({  
			        type : "post",  
			        async : false,
			        url : "role/delRole?id="+id,  
			        data : {},  
			        dataType : "json", //返回数据形式为json  
			        success : function(result) {
			            if (result) {
			            	if (!result.success) {
								$.messager.show( {
									title : 'Error',
									msg : result.msg
								});
							} else {
								$.messager.alert("提示", "删除成功！");
								var url = "role/AllRole";
								var img = new Image();
							    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
							    url = img.src;  // 此时相对路径已经变成绝对路径
							    img.src = null; // 取消请求
								window.location.href = encodeURI(url);
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

       function newRole(){
            url = "role/AllRole";
        }
        
            function doSearch(value){
 			$("#tt").datagrid( {
				fitColumns : true,
				height : ($("#body").height()),
				width : $("#body").width(),
				idField : 'authorities_name',
				url : "role/getAllAuthority",
				rownumbers : false,
				showPageList : false,
				checkOnSelect:true,
				selectOnCheck:true,
				columns : [ [ {
				    field:'ck',
					checkbox:true
				},{
					field : 'authorities_name',
					title : '权限名',
					width : 100,
					halign : "center",
					align : "left"
				}]],      
			onLoadSuccess:function(data){  
			if(data){
			$.each(data.rows, function(index, item){
        	var rows = $("#dg").datagrid("getRows");
        	for(var i=0;i<rows.length;i++){
                var rowID = rows[i].roles_name;
                var id = rows[i].id; 
                if(rowID==value){
						    $.ajax( {
							url : 'role/getAuthority?id='+id,
							data : {
							},
							type : 'post',
							async : false,
							dataType : 'json',
							success : function(result) {
							b = result.rows;
							},
							error : function() {
								alert("获取数据失败，请联系系统管理员！");
							}
						});
				    var c = eval(b);
					for(var j=0;j<c.length;j++)
					{
			        if(item.authorities_name==c[j].authorities_name){
			        $('#tt').datagrid('checkRow', index);
			        }
			        }
                $('#dlg').dialog('open').dialog('center').dialog('setTitle','角色信息');
                $('#fm').form('load',rows[i]);
            }
        }
        })
        }
        }
        })
        }
        
        
        function authority(id){
        $('#div').dialog('open').dialog('center').dialog('setTitle','权限列表');
        $("#ao").datagrid( {
		fitColumns : true,
		height : '300px',
		width : $("#div").width(),
		idField : 'id',
		url : "role/getAuthority?id="+id,
		rownumbers : false,
		showPageList : false,
		singleSelect : true,
		columns : [ [ {
			field : 'authorities_desc',
			title : '权限描述',
			width : 100,
			halign : "center",
			align : "left"
		}]]
		
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
        