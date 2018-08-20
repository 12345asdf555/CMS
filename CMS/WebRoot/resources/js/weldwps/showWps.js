$(function(){
	$.messager.defaults = { ok: "是",cancel:"否",width:'250px',height:'150px',top:250};  
})

function printWps(){
	var bodyHtml = $("body").html();
	var start = "<!--startprint-->"; //设置打印开始区域
	var end = "<!--endprint-->";//设置打印结束区域
	var prnhtml = bodyHtml.substr(bodyHtml.indexOf(start)+17);//从开始代码向后取html,17表示<!--startprint-->长度
	prnhtml = prnhtml.substring(0,prnhtml.indexOf(end));//从结束代码向前取html
	$("body").html(prnhtml);
	window.print();
	$("body").html(bodyHtml);//还原页面
}

function removeWps(){
	//删除数据
    $.messager.confirm('提示', '此操作不可撤销，并同时删除该工艺下的所有焊接参数，是否确认删除?', function(flag) {
		if (flag) {
			$.ajax({
				type : "post",
				async : false,
				url : "wps/destroyWpsAll?id="+$("#fid").val()+"&wpsnum=" + $("#wpsnum").val() +"&insfid=" + $("#itemid").val(),
				data : {},
				dataType : "json", //返回数据形式为json  
				success : function(result) {
					if (result.errorMsg) {
						$.messager.show({
							title : 'Error',
							msg : result.errorMsg
						});
					} else {
		            	$.messager.alert("提示", "删除成功！");
						if(result.msg!=null){
							$.messager.show( {title : '提示',msg : result.msg});
						}
						var url = "wps/goWps";
						var img = new Image();
					    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
					    url = img.src;  // 此时相对路径已经变成绝对路径
					    img.src = null; // 取消请求
						window.location.href = encodeURI(url);
					}
				},
				error : function(errorMsg) {
					alert("数据请求失败，请联系系统管理员!");
				}
			});
		}
	})
}