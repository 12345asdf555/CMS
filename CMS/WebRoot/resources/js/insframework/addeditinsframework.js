$(function(){
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
	url = "insframework/addInsframework";
	saveInsframework();
}

function editInsframework(){
	flag = 2;
	var id = $("#id").val();
	url = "insframework/editInsframework?id="+id;
	saveInsframework();
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
					var time = 500;
					if(result.msg==null){
						$.messager.alert("提示", messager);
					}else{
						time = 2500;
						$.messager.show( {title : '提示',msg : result.msg});
					}
					window.setTimeout(function() {
						var url = "insframework/goInsframework";
						var img = new Image();
					    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
					    url = img.src;  // 此时相对路径已经变成绝对路径
					    img.src = null; // 取消请求
						window.location.href = encodeURI(url);
					}, time);
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
//	$("#parent").combobox('select',parent);
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
