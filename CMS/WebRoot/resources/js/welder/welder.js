$(function(){
  weldDatagrid();
  insframworkCombobox();
  $('#dlg').dialog( {
    onClose : function() {
      $('#leveid').combobox('clear');
      $('#quali').combobox('clear');
      $('#owner').combobox('clear');
      $("#fm").form("disableValidation");
    }
  })
  $("#fm").form("disableValidation");
});

function weldDatagrid(){
  $("#welderTable").datagrid( {
    height : $("#body").height(),
    width : $("#body").width(),
    idField : 'id',
    pageSize : 10,
    pageList : [ 10, 20, 30, 40, 50 ],
    url : "welder/getWelderList",
    singleSelect : true,
    rownumbers : true,
    showPageList : false,
    columns : [ [ {
      field : 'id',
      title : '序号',
      width : 100,
      halign : "center",
      align : "left",
      hidden:true
    }, {
      field : 'name',
      title : '姓名',
      width : 150,
      halign : "center",
      align : "left"
    }, {
      field : 'welderno',
      title : '编号',
      width : 150,
      halign : "center",
      align : "left"
    }, {
      field : 'itemname',
      title : '所属项目',
      width : 150,
      halign : "center",
      align : "left"
    }, {
      field : 'iid',
      title : '项目id',
      width : 150,
      halign : "center",
      align : "left",
      hidden : true
    }, {
      field : 'edit',
      title : '编辑',
      width : 150,
      halign : "center",
      align : "left",
      formatter: function(value,row,index){
        var str = '<a id="edit" class="easyui-linkbutton" href="javascript:editWelder()"/>';
        str += '<a id="remove" class="easyui-linkbutton" href="javascript:removeWelder()"/>';
        return str;
      }
    }] ],
    toolbar : '#welderTable_btn',
    pagination : true,
    fitColumns : true,
    onLoadSuccess: function(data){
          $("a[id='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
          $("a[id='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
    }
  });
}
function insframworkCombobox(){
  $.ajax({  
        type : "post",  
        async : false,
        url : "weldingMachine/getInsframeworkAll",  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {  
            if (result) {
                var optionStr = '';
                for (var i = 0; i < result.ary.length; i++) {  
                    optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                            + result.ary[i].name + "</option>";
                }
                $("#itemname").html(optionStr);
            }  
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
    }); 
    $("#itemname").combobox();
  
}
var url = "";
var flag = 1;
function saveWelder(){
  flag = 1;
  $('#dlg').window( {
    title : "新增焊工",
    modal : true
  });
  $('#dlg').window('open');
  $('#fm').form('clear');
  url = "welder/addWelder";
}

function editWelder(){
  flag = 2;
  var row = $('#welderTable').datagrid('getSelected');
  if (row) {
    $('#dlg').window( {
      title : "修改焊工",
      modal : true
    });
    $('#dlg').window('open');
    $('#fm').form('load', row);
    $('#oldwelder').val(row.welderno);
//    $("#itemname").combobox("setValue",row.iid);
    url = "welder/editWelder";
  }
}
//提交
function save(){
 var insframework = $('#itemname').combobox('getValue');
  var url2 = "";
  if(flag==1){
    messager = "新增成功！";
    url2 = url;
  }else if(flag==2){
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
          if(!result.msg==null){
            $.messager.alert("提示", messager);
          }else{

				$.messager.alert("提示", messager);
				if(result.msg!=null){
					$.messager.show( {title : '提示',msg : result.msg});
				}
				$('#dlg').dialog('close');
				$('#welderTable').datagrid('reload');
          }
        }
      }
      
    },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      } 
  });
}
var url= "";
function removeWelder(){
    var row = $('#welderTable').datagrid('getSelected');
    if (row) {
      $('#rdlg').window( {
        title : "删除焊工",
        modal : true
      });
      $('#rdlg').window('open');
      $('#rfm').form('load', row);
    //url = "welders/destroyWelder?fid="+row.id;
    url = "welder/removeWelder?id="+row.id +"&insfid="+row.iid;  
    }
  }
  
  function remove(){
//  var url2 = "";
//  url2 = "welder/removeWelder?id="+row.id +"&insfid="+row.iid; 
  $.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
    if (flag) {
        $('#rfm').form('submit',{
            url: url,
            onSubmit: function(){
                 return $(this).form('enableValidation').form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (!result.success){
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                } else {
                  if(result.msg==null){
              $.messager.alert("提示", "删除成功！");
                  }
            $('#rdlg').dialog('close');
            $('#welderTable').datagrid('reload');
//            var url = "welders/AllWelder";
//            var img = new Image();
//              img.src = url;  // 设置相对路径给Image, 此时会发送出请求
//              url = img.src;  // 此时相对路径已经变成绝对路径
//              img.src = null; // 取消请求
//            window.location.href = encodeURI(url);
                }
            }
        })
    }
  });
}
  //所属项目
  function InsframeworkCombobox(){
    $.ajax({  
        type : "post",  
        async : false,
        url : "weldingMachine/getInsframeworkAll",  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {  
            if (result) {
                var optionStr = '';
                for (var i = 0; i < result.ary.length; i++) {  
                    optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                            + result.ary[i].name + "</option>";
                }
                $("#iid").html(optionStr);
            }  
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
    }); 
    $("#iid").combobox();
  }
//监听窗口大小变化
window.onresize = function() {
  setTimeout(domresize, 500);
}

//改变表格高宽
function domresize() {
  $("#welderTable").datagrid('resize', {
    height : $("#body").height(),
    width : $("#body").width()
  });
}