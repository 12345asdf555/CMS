$(function(){
	$('#dlg').dialog( {
		onClose : function() {
			$('#typeid').combobox('clear');
			$('#parent').combobox('clear');
			$("#fm").form("disableValidation");
		}
	})
	var flagstatus = $("#flag").val();
	if(flagstatus==0){
		insfcombobox($("#type").val(),id);
	}else{
		insfcombobox(0,0);
	}
	insframeworkTree();
	updatetext();
	
	$("#fm").form("disableValidation");
})

var url = "";
var flag = 1;
function addInsframework(){
	flag = 1;
	insfcombobox(0,0);
	$("#parent").next().show();
	$("#inparent").next().hide();
	$('#fdlg').window( {
		title : "新增组织机构",
		modal : true
	});
	$('#fdlg').window('open');
	$('#fm').form('clear');
	url = "insframework/addInsframework";
	//saveInsframework();
}

function editInsframework(){
	flag = 2;
	$("#inparent").next().show();
	$("#parent").next().hide();
	var row = $('#insframeworkTable').datagrid('getSelected');
	insfcombobox(row.typeid,row.id);
	if (row) {
		$('#fdlg').window( {
			title : "组织机构管理",
			modal : true
		});
		$('#fdlg').window('open');
		$('#fm').form('load', row);
		$("#inparent").textbox('setValue',row.parent);
		var id = $("#id").val();
		$('#validname').val(row.name);
	url = "insframework/editInsframework?id="+row.id;
	}//saveInsframework();
}
//提交
function saveInsframework(){
	var type = $("#typeid").combobox('getValue');
	var url2 = "";
	if(flag==1){
		messager = "新增成功！";
		url2 = url+"?parent="+$("#parent").combobox('getValue')+"&type="+type;
	}else{
		messager = "修改成功！";
		url2 = url+"&parent="+$("#parentid").val()+"&type="+type;
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
					$('#fdlg').dialog('close');
					$('#insframeworkTable').datagrid('reload');
				}
			}
			
		},  
	    error : function(errorMsg) {  
	        alert("数据请求失败，请联系系统管理员!");  
	    } 
	});
}

function updatetext(){
	var type = $("#type").val();
//	var parent = $("#parentid").val();
	$("#typeid").combobox('select',type);
//	$("#inparent").combobox('select',parent);
	
}

//上级项目/类型
function insfcombobox(type,id){
	$.ajax({
      type : "post",  
      async : false,
      url : "insframework/getParent?type="+type+"&id="+id,  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {
          if (result) {
              var optionStr1 = '',optionStr2 = '';  
              for (var i = 0; i < result.ary.length; i++) {  
                  optionStr1 += "<option value=\"" + result.ary[i].id + "\" >"  
                          + result.ary[i].name + "</option>";  
              }  
              $("#parent").html(optionStr1);
              for (var i = 0; i < result.arys.length; i++) {  
                  optionStr2 += "<option value=\"" + result.arys[i].id + "\" >"  
                          + result.arys[i].name + "</option>";  
              }  
              $("#typeid").html(optionStr2);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
 }); 
	$("#parent").combobox();
	$("#typeid").combobox();
	$("#fm").form("disableValidation");
}

//树形菜单点击事件
function insframeworkTree(){
	$("#myTree").tree({
		onClick : function(node){
			$("#parent").combobox('select',node.id);
			if($("#parent").combobox('getText')==$("#parent").combobox('getValue')){
				alert("请选择当前用户所属组织机构或下级组织机构(项目部除外)！");
				$("#parent").combobox('clear');
			}
		 }
	})
}
