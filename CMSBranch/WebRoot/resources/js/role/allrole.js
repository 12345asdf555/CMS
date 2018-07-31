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
//      $("#TT").DATAGRID( {
//    FITCOLUMNS : TRUE,
//    HEIGHT : ($("#BODY").HEIGHT()),
//    WIDTH : $("#BODY").WIDTH(),
//    IDFIELD : 'AUTHORITIES_DESC',
//    URL : "ROLE/GETALLAUTHORITY",
//    ROWNUMBERS : FALSE,
//    SHOWPAGELIST : FALSE,
//    CHECKONSELECT:TRUE,
//    SELECTONCHECK:TRUE,
//    COLUMNS : [ [ {
//        FIELD:'CK',
//      CHECKBOX:TRUE
//    },{
//      FIELD : 'AUTHORITIES_DESC',
//      TITLE : '权限描述',
//      WIDTH : 100,
//      HALIGN : "CENTER",
//      ALIGN : "LEFT"
//    }]]
//    
//  });
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
      field : 'roleName',
      title : '角色名',
      width : 100,
      halign : "center",
      align : "left"
    }, {
      field : 'roleDesc',
      title : '描述',
      width : 100,
      halign : "center",
      align : "left"
    }, {
      field : 'roleStatus',
      title : '状态',
      width : 100,
      halign : "center",
      align : "left"
        }, {
      field : 'statusid',
      title : '状态id',
      width :  100,
      halign: "center",
      align : "left",
      hidden: true
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
      str += '<a id="edit" class="easyui-linkbutton" href="javascript:editRole()"/>';
      str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeRole()"/>';
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
function RoleDatagrid(){
  var urls="";
  var row = $('#dg').datagrid('getSelected');
  if(flag==1){
    urls="role/getAllAuthority";
  }else{
    urls="role/getAllAuthority1?id="+row.id;
  }
  $("#tt").datagrid( {
    fitColumns : true,
    height : '250px',
    width : '80%',
    idField : 'roleName',
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
      hidden: true
    },{
      field : 'authorities_desc',
      title : '权限描述',
      width : 100,
      halign : "center",
      align : "left"
    }]],
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
        
        function RoleRemoveDatagrid(){
        	  var urls="";
        	  var row = $('#dg').datagrid('getSelected');
        	  if(flag==1){
        	    urls="role/getAllAuthority";
        	  }else{
        	    urls="role/getAllAuthority1?id="+row.id;
        	  }
        	  $("#rtt").datagrid( {
        	    fitColumns : true,
        	    height : '250px',
        	    width : '80%',
        	    idField : 'roleName',
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
        	      hidden: true
        	    },{
        	      field : 'authorities_desc',
        	      title : '权限描述',
        	      width : 100,
        	      halign : "center",
        	      align : "left"
        	    }]],
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
        var url = "";
        var flag = 1;
        function addRole(){
          flag = 1;
          $("#fm").form("disableValidation");
          $('#dlg').window( {
            title : "新增角色",
            modal : true
            
          });
          $('#dlg').window('open');
          $('#fm').form('clear');
          RoleDatagrid();
          var statusid = document.getElementsByName("statusid");
          statusid[1].checked = 'checked';
          url = "role/addRole";
        }
function editRole(){
  flag = 2;
  var row = $('#dg').datagrid('getSelected');
  if (row) {
    $('#dlg').window( {
      title : "修改角色",
      modal : true
    });
    $('#dlg').window('open');
    RoleDatagrid();
    $('#fm').form('load', row);
    $('#validName').val(row.roleName);
    url = "role/updateRole?rid="+ row.id+"&insfid="+row.itemid;
  }
}
//提交
function save(){
  var rows = $('#tt').datagrid('getSelections');
  var sid = $("input[name='statusid']:checked").val();
  var str="";
  for(var i=0; i<rows.length; i++){
    str += rows[i].id+",";
  }
  var url2 = "";
  if(flag==1){
    messager = "新增成功！";
    url2 = url+"?status="+sid+"&aid="+str;
  }else{
    messager = "修改成功！";
    url2 = url+"&status="+sid+"&aid="+str;
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
          if(result.msg==null){
            $.messager.alert("提示", messager);
          }else{
            $.messager.show( {title : '提示',msg : result.msg});
          }
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
var url = "";
function removeRole(){
  var row = $('#dg').datagrid('getSelected');
  if (row) {
    $('#rdlg').window( {
      title : "删除角色",
      modal : true
    });
    $('#rdlg').window('open');
    $('#rfm').form('load', row);
    RoleRemoveDatagrid();
  //  $('#validName').val(row.roleName);
//    var statusid = document.getElementsByName("statusid");
//    statusid[0].checked =  'checked';
    url = "role/delRole?id="+row.id;
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
//                var url = "role/AllRole";
//                var img = new Image();
//                  img.src = url;  // 设置相对路径给Image, 此时会发送出请求
//                  url = img.src;  // 此时相对路径已经变成绝对路径
//                  img.src = null; // 取消请求
//                window.location.href = encodeURI(url);
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
            function statusRadio(){
              $.ajax({  
                  type : "post",  
                  async : false,
                  url : "role/getStatusAll",  
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
                          $("input[name='statusid']").eq(0).attr("checked",true);
                      }  
                  },  
                  error : function(errorMsg) {  
                      alert("数据请求失败，请联系系统管理员!");  
                  }  
              })
            }
            function showdatagrid(id){
                $("#rtt").datagrid( {
                fitColumns : true,
                height : '250px',
                width : '80%',
                idField : 'authorities_desc',
                url : "role/getAllAuthority1?id="+id,
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
                  field : 'authorities_desc',
                  title : '权限描述',
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