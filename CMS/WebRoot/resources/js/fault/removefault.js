var url = "";
function removeFault(){
  $('#rfm').form('clear');
  var row = $('#dg').datagrid('getSelected');
     if (row) {
       $('#rdlg').window( {
         title : "删除故障",
         modal : true
       });
       $('#rdlg').window('open');
       $('#rfm').form('load', row);
    url = "fault/removeFault?id="+row.id+"&uid="+row.creator;
  }
}

function remove(){
  var id = $("#id").val();
  $.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
    if (flag) {
      $.ajax({  
            type : "post",  
            async : false,
            url :url ,  
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
              if(result.msg==null){
                $.messager.alert("提示", "删除成功！");
              }else{
                $.messager.show( {title : '提示',msg : result.msg});
              }
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