$(function(){
	typeCombobox();
	$('#dlg').dialog( {
		onClose : function() {
			
			//$('#typeid').combobox('clear');
			$("#fm").form("disableValidation");
		}
	})
	$("#type").combobox({
        onChange:function(){
        	//处理类型发生改变时生产厂商无法进行验证问题
        	var name = $("#name").val();
        	$("#name").textbox("setValue",name);
        } 
     });
	var typeid = $("#typeid").val();
	$("#type").combobox('select',typeid);
	$("#fm").form("disableValidation");
})


var url = "";
var flag = 1;
function addManufacturer(){
	flag = 1;
	$('#dlg').window( {
		title : "新增生产商",
		modal : true
	});
	$('#dlg').window('open');
	$('#fm').form('clear');
	url = "manufacturer/addManufacturer";
	//saveManufacturer();
}

function editManufacturer(){
	flag = 2;
	$('#fm').form('clear');
	var row = $('#dg').datagrid('getSelected');
	if (row) {
		$('#dlg').window( {
			title : "修改厂商记录",
			modal : true
		});
		$('#dlg').window('open');
		$('#fm').form('load', row);
		$('#validName').val(row.id);
		url = "manufacturer/editManufacturer?id="+row.id;
	}
}
//提交
function saveManufacturer(){
	var url2 = "";
	var type = $('#type').combobox('getValue');
	var xxx = document.getElementById("type").value;
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

//类型
function typeCombobox(){
	$.ajax({  
      type : "post",  
      async : false,
      url : "manufacturer/getTypeAll",  
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
}