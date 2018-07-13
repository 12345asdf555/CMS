$(function(){
	typeidCombobox();
	$("#fm").form("disableValidation");
});

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
	$("#typeid").combobox({
		onChange: function () {
			if($("#typeid").combobox('getValue')==8){
				$('#valueName').validatebox({required:true,validType:['intNum']});
//				$('#valueName').attr("data-options", "required:true,validType:['intNum']");
				document.getElementById("wxts").style.display="block";
			}else{
				document.getElementById("wxts").style.display="none";
			}
		}
	});
	$("#typeid").combobox();
}

function saveDictionary(value){
	var url;
	var back=$("#typeid").combobox('getText');
	if(value==1){
		url="Dictionary/addDictionary?back="+back;
		messager = "新增成功！";
	}else if(value==2){
		url="Dictionary/editDictionary?back="+back;
		messager = "修改成功！";
	}
	$("#fm").form('submit',{
		url:url,
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success:function(result){
			if(result){
				var result=eval('(' + result + ')');
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
						var url = "Dictionary/goDictionary";
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
	})
}
function searchDic(){
	var cols=$("#fields").combobox("getValue");
	var content=$("#content").val();
	var searchStr=cols+" like '%"+content+"%'";
	$('#dg').datagrid('load', {
		"searchStr" : searchStr
	});
}