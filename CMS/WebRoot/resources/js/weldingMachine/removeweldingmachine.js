$(function(){
	$("#wid").next().hide();
})

function removeWeldingMachine(){
	var wid = $("#wid").val();
	var insfid = $("#insfid").val();
	$.messager.confirm('提示', '此操作不可撤销并同时删除其维修记录，是否确认删除?', function(flag) {
		if (flag) {
			$.ajax({  
		        type : "post",  
		        async : false,
		        url : "weldingMachine/removeWeldingMachine?wid="+wid+"&insfid="+insfid,  
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
							var time = 500;
							if(result.msg==null){
								$.messager.alert("提示", "删除成功！");
							}else{
								time = 2500;
								$.messager.show( {title : '提示',msg : result.msg});
							}
							window.setTimeout(function() {
								var url = "weldingMachine/goWeldingMachine";
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
	});
}