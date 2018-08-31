/**
 * 
 */

       $(function(){
    	   statusRadio();
    		$('#dlg').dialog( {
    			onClose : function() {
    				$("#fm").form("disableValidation");
    			}
    		})
    	$("#fm").form("disableValidation");
	    $("#dg").datagrid( {
		fitColumns : true,
		height : ($("#body").height())-40,
		width : $("#body").width()-30,
		idField : 'id',
		toolbar : "#toolbar",
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],  
		url : "resource/getAllResource",
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		showPageList : false,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			halign : "center",
			align : "center",
			hidden: true
		}, {
			field : 'resourceName',
			title : '资源名',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'resourceType',
			title : '类型',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'resourceAddress',
			title : '地址',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'resourceDesc',
			title : '描述',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'status',
			title : '状态',
			width : 100,
			halign : "center",
			align : "center"
		}, {
			field : 'statusid',
			title : '状态id',
			width : 100,
			halign : "center",
			align : "center",
			hidden : true
		}, {
			field : 'edit',
			title : '编辑',
			width : 130,
			halign : "center",
			align : "center",
			formatter:function(value,row,index){
			var str = "";
			str += '<a id="edit" class="easyui-linkbutton" href="javascript:editResource()"/>';
			str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeResource()"/>';
			return str;
			}
		}]],
		toolbar : '#toolbar',
		onLoadSuccess:function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
	        }
	});
})

       var url = "";
       var flag = 1;
       function newResource(){
    	   flag = 1;
    		$('#fm').form('clear');
    		$('#dlg').window( {
    			title : "新增资源",
    			modal : true
    		});
    		$('#dlg').window('open');
    		var statusid = document.getElementsByName("statusid");
    		statusid[1].checked = 'checked';
    		url = "resource/addResource";
            
        }
       function editResource(){
    		flag = 2;
    		$('#fm').form('clear');
    		var row = $('#dg').datagrid('getSelected');
    		if (row) {
    			$('#dlg').window( {
    				title : "修改资源",
    				modal : true
    			});
    			$('#dlg').window('open');
    			$('#fm').form('load', row);
    			$('#validName').val(row.resourceName);
    			url = "resource/updateResource?id="+row.id;
    		}
    	}
       var url = "";
       function removeResource(){
   		$('#rfm').form('clear');
       	var row = $('#dg').datagrid('getSelected');
       	if (row) {
       		$('#rdlg').window( {
       			title : "删除资源",
       			modal : true
       		});
       		$('#rdlg').window('open');
       		$('#rfm').form('load', row);
       		url = "resource/delResource?id="+row.id;
       	}
       }

       function remove(){
       		$.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
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
       									msg : result.msg
       								});
       							} else {
       								$.messager.alert("提示", "删除成功！");
       								$('#rdlg').dialog('close');
       								$('#dg').datagrid('reload');
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
    	//提交
    	function save(){
    		var url2 = "";
    	    var sid = $("input[name='statusid']:checked").val();
    		if(flag==1){
    			messager = "新增成功！";
    			url2 = url+"?status="+sid;
    		}else{
    			messager = "修改成功！";
    			url2 = url+"&status="+sid;
    		}
    		$('#fm').form('submit', {
    			url : url2,
    			onSubmit : function() {
    				return $(this).form('enableValidation').form('validate');
    			},
    			success : function(result) {
    				if(result){
    					var result = eval('(' + result + ')');
    					if (!result.success) {
    						$.messager.show( {
    							title : 'Error',
    							msg : result.errorMsg
    						});
    					} else {
    						$.messager.alert("提示", messager);
    						$('#dlg').dialog('close');
    						$('#dg').datagrid('reload');
    					}
    				}
    				
    			},  
    		    error : function(errorMsg) {  
    		        alert("数据请求失败，请联系系统管理员!");  
    		    } 
    		});
    	}

    	function statusRadio(){
    		$.ajax({  
    		    type : "post",  
    		    async : false,
    		    url : "user/getStatusAll",  
    		    data : {},  
    		    dataType : "json", //返回数据形式为json  
    		    success : function(result) {
    		    	if (result) {
    		    		var str = "";
    		    		for (var i = 0; i < result.ary.length; i++) {
    		    			str += "<input type='radio' class='radioStyle' name='statusid' id='sId' value=\"" + result.ary[i].id + "\" />"  
    	                    + result.ary[i].name+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    		    		}
    		            $("#radios").html(str);
    		        }  
    		    },  
    		    error : function(errorMsg) {  
    		        alert("数据请求失败，请联系系统管理员!");  
    		    }  
    		});
    	}
        function doSearch(value){
 			$("#tt").datagrid( {
				fitColumns : true,
				height : ($("#body").height()),
				width : $("#body").width(),
				idField : 'roles_name',
				url : "user/getAllRole",
				rownumbers : false,
				showPageList : false,
				checkOnSelect:true,
				selectOnCheck:true,
				columns : [ [ {
				    field:'ck',
					checkbox:true
				},{
					field : 'roles_name',
					title : '角色名',
					width : 100,
					halign : "center",
					align : "center"
				}]],      
			onLoadSuccess:function(data){  
			if(data){
			$.each(data.rows, function(index, item){
        	var rows = $("#dg").datagrid("getRows");
        	for(var i=0;i<rows.length;i++){
                var rowID = rows[i].users_name;
                var id = rows[i].id; 
                if(rowID==value){
						    $.ajax( {
							url : 'user/getRole?id='+id,
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
			        if(item.roles_name==c[j].roles_name){
			        $('#tt').datagrid('checkRow', index);
			        }
			        }
                $('#dlg').dialog('open').dialog('center').dialog('setTitle','用户信息');
                $('#fm').form('load',rows[i]);
            }
        }
        })
        }
        }
        })
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