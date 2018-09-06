$(function() {
	//判断Session是否过期
	getUserInsframework();
})

var type;
function getUserInsframework() {
	$.ajax({
		type : "post",
		async : true,
		url : "hierarchy/getUserInsframework",
		data : {},
		dataType : "json",
		success : function(result) {
			var afresh = result.afreshLogin;
			if(afresh!=null && afresh!=""){
				var url = "logins.jsp";
				var img = new Image();
			    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
			    url = img.src;  // 此时相对路径已经变成绝对路径
			    img.src = null; // 取消请求
				top.location.href = url;
			}else{
				type = result.type;
				var str = "<span>" + result.insframework + ": </span><span>" + result.uname + "</span>";
				$("#uname").val(result.uname);
				$("#uid").val(result.id);
				$("#userInsframework").append(str);
				if (type == 20) {
					$('#accordiondiv').accordion('add', {
						title : '<div><img src="resources/images/manager.png"/>&nbsp;&nbsp;报表分析</div>',
						content : $("#bloc1").html()
					});
					$('#accordiondiv').accordion('add', {
						title : '<div><img src="resources/images/c-6.png"/>&nbsp;&nbsp;实时监测</div>',
						content : $("#bloc2").html(),
						selected : false
					});
				} else if (type == 21) {
					$('#accordiondiv').accordion('add', {
						title : '<div><img src="resources/images/manager.png"/>&nbsp;&nbsp;报表分析</div>',
						content : $("#company1").html()
					});
					$('#accordiondiv').accordion('add', {
						title : '<div><img src="resources/images/c-6.png"/>&nbsp;&nbsp;实时监测</div>',
						content : $("#company2").html(),
						selected : false
					});
				} else if (type == 22) {
					$('#accordiondiv').accordion('add', {
						title : '<div><img src="resources/images/manager.png"/>&nbsp;&nbsp;报表分析</div>',
						content : $("#caust1").html()
					});
					$('#accordiondiv').accordion('add', {
						title : '<div><img src="resources/images/c-6.png"/>&nbsp;&nbsp;实时监测</div>',
						content : $("#caust2").html(),
						selected : false
					});
				} else if (type == 23) {
					$('#accordiondiv').accordion('add', {
						title : '<div><img src="resources/images/manager.png"/>&nbsp;&nbsp;报表分析</div>',
						content : $("#item1").html()
					});
					$('#accordiondiv').accordion('add', {
						title : '<div><img src="resources/images/c-6.png"/>&nbsp;&nbsp;实时监测</div>',
						content : $("#item2").html(),
						selected : false
					});
				}
				addTab("欢迎使用", "welcomephone.jsp");
				tabsIncident();
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	})
}

function openUseratio(){
	addTab("设备利用率", "android/goUseratio");
}

function openMachineTop(){
	addTab("焊机最高排行", "android/goMachineMax");
}

function openMachineBottom(){
	addTab("焊机最低排行", "android/goMachineMin");
}

function openWelderTop(){
	addTab("焊工最高排行", "android/goWelderMax");
}

function openWelderBottom(){
	addTab("焊工最低排行", "android/goWelderMin");
}

function openCompanyTd() {
	addTab("焊机实时状态监测", "td/newAllTd");
}

function addTab(title, url) {
	//该面板是否已打开
	if (!$("#tabs").tabs('exists', title)) {
		$("#tabs").tabs('add', {
			title : title,
			content : createFrame(url),
			closable : true
		});
	} else {
		$("#tabs").tabs('select', title);
	}
	// 为选项卡绑定右键
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#tabMenu').menu('show', {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#tabMenu').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});

}

function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
	return s;
}


//标签页事件
function tabsIncident() {
	//刷新
	$('#refreshtab').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update', {
			tab : currTab,
			options : {
				content : createFrame(url)
			}
		})
	})
	//关闭标签页
	$("#closetab").click(function() {
		var currtab_title = $('#tabMenu').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	})
	// 全部关闭
	$('#closeAll').click(function() {
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			if (t != "欢迎使用") {
				$('#tabs').tabs('close', t);
			}
		});
	});
	// 关闭其他标签页
	$('#closeOther').click(function() {
		$("#closeRight").click();
		$('#closeLeft').click();
	});
	//关闭右侧标签页
	$('#closeRight').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			if (t != "欢迎使用") {
				$('#tabs').tabs('close', t);
			}
		});
	})
	//关闭左侧标签页
	$('#closeLeft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			if (t != "欢迎使用") {
				$('#tabs').tabs('close', t);
			}
		});
	});
}

//选中时改变背景颜色
function changeColor(id) {
	for(var i=1;i<=74;i++){
		$("#clickid"+i).hide();
	}
	$("#clickid"+id).show();
}

/*function updatePwd() {
	$('#dlg').window({
		modal : true
	});
	$("#dlg").dialog("open");
	$("#pwd").val("");
	$("#pwds").val("");
	noticeAssign(99);
}
function pwdKeyUp(password) {
	var pwd = $(password).val();
	if (pwd) {
		if (/[a-zA-Z]+/.test(pwd) && /[0-9]+/.test(pwd) && /\W+|_+/.test(pwd)) {
			noticeAssign(2);
		} else if (/[a-zA-Z]+/.test(pwd) || /[0-9]+/.test(pwd) || /\W+|_+/.test(pwd)) {
			if (/[a-zA-Z]+/.test(pwd) && /[0-9]+/.test(pwd)) {
				noticeAssign(1);
			} else if (/[a-zA-Z]+/.test(pwd) && /\W+|_+/.test(pwd)) {
				noticeAssign(1);
			} else if (/[0-9]+/.test(pwd) && /\W+|_+/.test(pwd)) {
				noticeAssign(1);
			} else {
				noticeAssign(0);
			}
		}
	} else {
		noticeAssign(99);
	}
}
function noticeAssign(num) {
	if (num == 2) {
		$('#weak').css({
			backgroundColor : ''
		});
		$('#middle').css({
			backgroundColor : ''
		});
		$('#strength').css({
			backgroundColor : '#ffcc33'
		});
	} else if (num == 1) {
		$('#weak').css({
			backgroundColor : ''
		});
		$('#middle').css({
			backgroundColor : '#ffcc33'
		});
		$('#strength').css({
			backgroundColor : ''
		});
	} else if (num == 0) {
		$('#weak').css({
			backgroundColor : '#ffcc33'
		});
		$('#middle').css({
			backgroundColor : ''
		});
		$('#strength').css({
			backgroundColor : ''
		});
	} else {
		$('#weak').css({
			backgroundColor : ''
		});
		$('#middle').css({
			backgroundColor : ''
		});
		$('#strength').css({
			backgroundColor : ''
		});
	}
}

function updatePassword() {
	$("#pwdcheck").html("");
	if (!$("#pwd").val()) {
		$("#pwdcheck").append("请输入密码");
	} else if (!$("#pwds").val()) {
		$("#pwdcheck").append("请确认密码");
	} else if ($("#pwd").val() != $("#pwds").val()) {
		$("#pwdcheck").append("两次密码不一致");
	} else {
		$('#fm').form('submit', {
			url : "user/updatePwd?id=" + $("#uid").val() + "&pwd=" + $("#pwd").val(),
			success : function(result) {
				if (result) {
					var result = eval('(' + result + ')');
					if (!result.success) {
						$.messager.show({
							title : 'Error',
							msg : result.errorMsg
						});
					} else {
						$.messager.alert("提示", "修改成功！");
						$('#dlg').dialog('close');
						var url = "user/logout";
						var img = new Image();
						img.src = url; // 设置相对路径给Image, 此时会发送出请求
						url = img.src; // 此时相对路径已经变成绝对路径
						img.src = null; // 取消请求
						window.location.href = encodeURI(url);

					}
				}

			},
			error : function(errorMsg) {
				alert("数据请求失败，请联系系统管理员!");
			}
		});
	}
}*/