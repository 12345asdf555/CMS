/**
 * 
 */
        $(function(){
        	$('#dlg').dialog( {
        		onClose : function() {
        			$('#insframework').combobox('clear');
        			$("#fm").form("disableValidation");
        		}
        	})
        	$("#fm").form("disableValidation");
        	var width = $("#treeDiv").width();
    		$(".easyui-layout").layout({
    			onCollapse:function(){
    				$("#dg").datagrid({
    					height : $("#body").height(),
    					width : $("#body").width()
    				})
    			},
    			onExpand:function(){
    				$("#dg").datagrid({
    					height : $("#body").height(),
    					width : $("#body").width()
    				})
    			}
    		});
            statusRadio();
            insfCombobox();
        	insframeworkTree();
	    $("#tt").datagrid( {
		fitColumns : true,
		height : '250px',
		width : '80%',
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
		url : "user/getAllUser",
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
			field : 'insid',
			title : '组织机构id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'userName',
			title : '用户名',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'userLoginName',
			title : '登录名',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'userPhone',
			title : '电话',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'userEmail',
			title : '邮箱',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'userPosition',
			title : '岗位',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'userInsframework',
			title : '部门',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'status',
			title : '状态',
			width : 100,
			halign : "center",
			align : "left"
        },{
			field : 'statusid',
			title : '状态id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'userPassword',
			title : '密码',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		}, {
			field : 'role',
			title : '角色',
			width : 100,
			halign : "center",
			align : "left",
			formatter:function(value,row,index){
			var str = "";
			str += '<a id="role" class="easyui-linkbutton" href="javascript:role('+row.id+')"/>';
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
			str += '<a id="edit" class="easyui-linkbutton" href="javascript:deleteUser('+row.insid+','+row.id+','+true+')"/>';
			str += '<a id="remove" class="easyui-linkbutton" href="javascript:deleteUser('+row.insid+','+row.id+','+false+')"/>';
			return str;
			}
		}]],
		toolbar : '#toolbar',
		onLoadSuccess:function(data){
	        $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	        $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
	        $("a[id='role']").linkbutton({text:'角色列表',plain:true,iconCls:'icon-Role'});
	        }
	});
})
        function role(id){
        $('#div1').dialog('open').dialog('center').dialog('setTitle','角色列表');
        $("#ro").datagrid( {
		fitColumns : true,
		height : '300px',
		width : $("#div").width(),
		idField : 'id',
		url : "user/getRole?id="+id,
		rownumbers : false,
		showPageList : false,
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			halign : "center",
			align : "left",
			hidden: true
		},{
			field : 'roles_name',
			title : '角色名',
			width : 100,
			halign : "center",
			align : "left"
		}]]
		
			});

        }
        function logout(){
 			$.ajax({  
 		        type : "post",  
 		        async : false,
 		        url : "user/logout",  
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
 							var url = "/CMS/login.jsp";
 							top.location.href = url;
 						}
 		            }  
 		        },  
 		        error : function(errorMsg) {  
 		            alert("数据请求失败，请联系系统管理员!");  
 		        }  
 		   }); 
         }
        //组织机构菜单
        function insframeworkTree(){
        	$("#myTree").tree({  
        		onClick : function(node){
        			$("#dg").datagrid('load',{
        				"parent" : node.id
        			})
        		 }
        	})
        }
        var url = "";
        var flag = 1;
        function addUser(){
        	$("#insframework").next().hide();
        	$("#userInsframework").next().show();
        	var node = $('#myTree').tree('getSelected');
        	if(node==null || node==""){
        		alert("请先选择该用户所属组织机构(部门)！");
        	}else{
        	flag = 1;
        	$("#fm").form("disableValidation");
        	$('#dlg').window( {
        		title : "新增用户",
        		modal : true
        	});
        	$('#dlg').window('open');
        	$('#fm').form('clear');
        	$('#tt').datagrid('clearSelections');

        	//insframework为必填项
    		$("#insframework").combobox('setText',node.id);
        	//写入当前行列的数据
			$("#insid").val(node.id);
			$("#userInsframework").textbox('setValue',node.text);
			//状态的启动和停止
			var statusid = document.getElementsByName("statusid");
			statusid[1].checked ='checked';
        	url = "user/addUser";
        }
  }
        function editUser(){
        	flag = 2;
        	$("#insframework").next().show();
        	$("#userInsframework").next().hide();
        	$('#fm').form('clear');
        	var row = $('#dg').datagrid('getSelected');
        	$("#insid").val(row.insid);
        	if (row) {
        		$('#dlg').window( {
        			title : "修改用户",
        			modal : true
        		});
        		$('#dlg').window('open');
        		UserDatagrid();
        		$('#fm').form('load', row);
        		$('#validName').val(row.userLoginName);
        		$("#insframework").combobox('setValue',row.insid);
        		url = "user/updateUser?uid="+ row.id;
        	}
        }
        var url = "";
        function removeUser(){
        	$('#rfm').form('clear');
        	var row = $('#dg').datagrid('getSelected');
        	if (row) {
        		$('#rdlg').window( {
        			title : "删除用户",
        			modal : true
        		});
        		$('#rdlg').window('open');
        		$('#rfm').form('load', row);
        		showdatagrid(row.id);
        		url = "user/delUser?id="+row.id;
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
       function saveUser(){
           //flag = 1;
            var sid = $("input[name='statusid']:checked").val();
            var rows = $("#tt").datagrid("getSelections");
            var str="";
            var uid = $('#id').val();
   		for(var i=0; i<rows.length; i++){
   			str += rows[i].id+",";
   			}
            var url2;
             if(flag==1){
            		messager = "新增成功！";
            		url2 = "user/addUser?userInsframework="+$("#insid").val()+"&status="+sid+"&rid="+str;
            	}else{
            		messager = "修改成功！";
            		url2 = url+"&userInsframework="+$("#insframework").combobox('getValue')+"&status="+sid+"&rid="+str;
            	}
               $('#fm').form('submit',{
                   url: url2,
                   onSubmit: function(){
                       return $(this).form('enableValidation').form('validate');
                   },
                   success: function(result){
                       var result = eval('('+result+')');
                       if (result.errorMsg){
                           $.messager.show({
                               title: 'Error',
                               msg: result.errorMsg
                           });
                       } else {
                    	   if(result.msg==null){
       						$.messager.alert("提示", messager);
       					}else{
       						$.messager.show( {title : '提示',msg : result.msg});
       					}
       					$('#dlg').dialog('close');
       					$('#dg').datagrid('reload');
                       }
                   }
               });
           }
        function UserDatagrid(){
        	var urls="";
        	var row = $('#dg').datagrid('getSelected');
        	if(flag==1){
        		urls="user/getAllRole";
        	}else{
        		urls="user/getAllRole1?id="+row.id;
        	}
        	$("#tt").datagrid( {
        		fitColumns : true,
        		height : '250px',
        		width : '80%',
        		idField : 'roles_name',
        		url : urls,
        		rownumbers : false,
        		showPageList : false,
        		checkOnSelect:true,
        		selectOnCheck:true,
        		columns : [ [ {
        		    field:'ck',
        			checkbox:true
        		},{
        			field : 'id',
        			title : 'id',
        			width : 100,
        			halign : "center",
        			align : "left",
        			hidden:true
        		},{
        			field : 'roles_name',
        			title : '角色名',
        			width : 100,
        			halign : "center",
        			align : "left"
        		}]],
        		rowStyler: function(index,row){
                    if ((index % 2)!=0){
                    	//处理行代背景色后无法选中
                    	var color=new Object();
                        color.class="rowColor";
                        return color;
                    }
        		},
        		onBeforeLoad:function(data){
        			 $('#tt').datagrid('clearChecked');
        		},
        		onLoadSuccess:function(data){
        			 if(data){
        				 $.each(data.rows, function(index, item){
        					 if(item.symbol==1){
        				         $('#tt').datagrid('checkRow', index);
        					 }
        				 })
        			 }
        		}
        	});
        }
        function showdatagrid(id){
			$("#rtt").datagrid( {
				fitColumns : true,
				height : '250px',
				width : '80%',
				idField : 'roles_name',
				url : "user/getAllRole1?id="+id,
				rownumbers : false,
				showPageList : false,
				checkOnSelect:true,
				selectOnCheck:true,
				columns : [ [ {
				    field:'ck',
					checkbox:true
				},{
					field : 'symbol',
					title : 'symbol',
					width : 100,
					halign : "center",
					align : "left",
					hidden:true
				},{
					field : 'roles_name',
					title : '角色名',
					width : 100,
					halign : "center",
					align : "left"
				}]],      
				rowStyler: function(index,row){
		            if ((index % 2)!=0){
		            	//处理行代背景色后无法选中
		            	var color=new Object();
		                color.class="rowColor";
		                return color;
		            }
				}, 
				onBeforeLoad:function(data){
					 $('#rtt').datagrid('clearChecked');
				},
				onLoadSuccess:function(data){
	   			 if(data){
					 $.each(data.rows, function(index, item){
						 if(item.symbol==1){
					         $('#rtt').datagrid('checkRow', index);
						 }
					 })
				 }
			   }                   
			});
		}

        
        function deleteUser(id,uid,flags){
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
 		            				url = "user/getUser?id="+uid;
 		            				editUser();
 		            			}else{
 		            				url = "user/desUser?id="+uid;
 		            				removeUser();
 		            			}
//     		       				var img = new Image();
//     		       			    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
//     		       			    url = img.src;  // 此时相对路径已经变成绝对路径
//     		       			    img.src = null; // 取消请求
//     		       				window.location.href = encodeURI(url);
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
               
function insfCombobox(){
	$.ajax({
	   type: "post", 
	   url: "user/getIns",
	   dataType: "json",
	   data: {},
	   success: function (result) {
	      if (result) {
	    	  var optionstring = "";
	    	  //循环遍历 下拉框绑定
	    	  for(var k=0;k<result.rows.length;k++){
	    		  optionstring += "<option value=\"" + result.rows[k].insid + "\" >" + result.rows[k].insname + "</option>";
	    	  }
	    	  $("#insframework").html(optionstring);
	      	  $("#insframework").combobox();
	      }
	   },
	   error: function () {
	      alert('error');
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