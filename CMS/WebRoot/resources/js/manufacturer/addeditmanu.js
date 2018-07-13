$(function(){
	typeCombobox();
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
	url = "manufacturer/addManufacturer";
	saveManufacturer();
}

function editManufacturer(){
	flag = 2;
	url = "manufacturer/editManufacturer";
	saveManufacturer();
}
//提交
function saveManufacturer(){
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
					var time = 500;
					if(result.msg==null){
						$.messager.alert("提示", messager);
					}else{
						time = 2500;
						$.messager.show( {title : '提示',msg : result.msg});
					}
					window.setTimeout(function() {
						var url = "manufacturer/goManufacturer";
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

//所属项目
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