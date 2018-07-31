$(function(){
  typeidCombobox();
//  contentCombobox();
  DictionaryDataGrid();
  $('#dlg').dialog( {
    onClose : function() {
      $('#typeid').combobox('clear');
      $("#fm").form("disableValidation");
    }
  })
  $("#fm").form("disableValidation");
  
});

var url = "";
var flag = 1;
function addDictionary(){
  flag = 1;
  $('#dlg').window( {
    title : "新增字典",
    modal : true
  });
  $('#dlg').window('open');
  $('#fm').form('clear');
  //$("#typeid").combobox('setValue',$("#content").combobox('getValue'));
  url = "Dictionary/addDictionary";
}

function editDictionary(){
  flag = 2;
  $('#fm').form('clear');
  var row = $('#dg').datagrid('getSelected');
  if (row) {
    $('#dlg').window( {
      title : "修改字典",
      modal : true
    });
    $('#dlg').window('open');
    $('#fm').form('load', row);
    url = "Dictionary/editDictionary?id="+ row.id+"&value="+row.value;
  }
}
function removeDictionary(){
  $('#rfm').form('clear');
  var row = $('#dg').datagrid('getSelected');
  if (row) {
    $('#rdlg').window( {
      title : "删除字典",
      modal : true
    });
    $('#rdlg').window('open');
    $('#rfm').form('load', row);
    url = "Dictionary/deleteDictionary?id="+row.id;
  }
}
//提交
function save(){
  var url2 = "";
  var back=$("#typeid").combobox('getText');
  if(flag==1){
    messager = "新增成功！";
    url2 = url+"?back="+back;
  }else{
    messager = "修改成功！";
    url2 = url+"&back="+back;
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

function typeidCombobox(){
  $.ajax({
    type : 'post',
    async : false,
    dataType : 'json',
    url : 'Dictionary/getBack',
    success : function(result){
      var str = "";
      for(var i=0;i<result.ary.length;i++){
        str += "<option value=\""+result.ary[i].typeid+"\">"+result.ary[i].back+"</option>";
      }
      $("#typeid").html(str);
    },
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
  })
  $("#typeid").combobox();
}


//设备类型
/*function contentCombobox(){
  $.ajax({  
        type : "post",  
        async : false,
        url : "Dictionary/getBack",  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {
            var optionStr = "";
            if (result) {
                for (var i = 0; i < result.ary.length; i++) {  
                    optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                            + result.ary[i].name + "</option>";  
                }
                $("#content").html(optionStr);
            }  
        }
    });
    $("#content").combobox({
      onChange: function () {
        DictionaryDataGrid();
      }
    });
    var data = $('#content').combobox('getData');
    $('#content').combobox('select',data[0].value);
}*/
function remove(){
    var id=$("#id").val();
  $.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
    if (flag) {
      $.ajax({  
            type : "post",  
            async : false,
            url : "Dictionary/deleteDictionary?id="+id,  
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
function searchDic(){
  var cols=$("#fields").combobox("getValue");
  var content=$("#content").val();
  var searchStr=cols+" like '%"+content+"%'";
  $('#dg').datagrid('load', {
    "searchStr" : searchStr
  });
}