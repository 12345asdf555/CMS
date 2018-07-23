$(function(){
  typeCombobox();
  $('#dlg').dialog( {
    onClose : function() {
      $("#type").combobox('clear');
      $("#fm").form("disableValidation");
    }
  })
  $("#fm").form("disableValidation");
})


var url = "";
var flag = 1;
function addFault(){
  flag = 1;
  $('#fm').form('clear');
  $('#dlg').window( {
    title : "新增故障",
    modal : true
  });
  $('#dlg').window('open');
  url = "fault/addFault";
}

function editFault(){
  flag = 2;
  $('#fm').form('clear');
  var row = $('#dg').datagrid('getSelected');
  if (row) {
    $('#dlg').window( {
      title : "故障代码修改",
      modal : true
    });
    $('#dlg').window('open');
    $('#fm').form('load', row);
    //$('#validName').val(row.resourceName);
    url = "fault/editFault?id="+row.id;
  }
}
//提交
function saveFault(){
  var url2 = "";
  if(flag==1){
    messager = "新增成功！";
    url2 = url;
  }else{
    messager = "修改成功！";
    url2 = url;
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
          if(result.msg!=null){
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

//所属项目
function typeCombobox(){
  $.ajax({  
      type : "post",  
      async : false,
      url : "fault/getTypeAll",  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {  
          if (result) {
              var optionStr = '';
              for (var i = 0; i < result.ary.length; i++) {  
                  optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                          + result.ary[i].name + "</option>";
              }
              $("#type").html(optionStr);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
  });   
  $("#type").combobox();
  var typeid = $("#typeid").val();
  $("#type").combobox('select',typeid);
}