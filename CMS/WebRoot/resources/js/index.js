$(function() {
	getUserInsframework();
	addTab("欢迎使用", "newwelcome.jsp");
	tabsIncident();
})

var type;
var resourceary = [],xmlname;
function getUserInsframework() {
	$.ajax({
		type : "post",
		async : true,
		url : "hierarchy/getUserInsframework",
		data : {},
		dataType : "json",
		success : function(result) {
			type = result.type;
			var str = "<span>" + result.insframework + ": </span><span>" + result.uname + "</span>";
			$("#uname").val(result.uname);
			$("#uid").val(result.id);
			$("#userInsframework").append(str);
			for(var r=0;r<result.ary.length;r++){
				resourceary.push(result.ary[r].resource);
			}
			if (type == 20) {
				xmlname = "blocMenu";
			} else if (type == 21) {
				xmlname = "companyMenu";
			} else if (type == 22) {
				xmlname = "caustMenu";
			} else if (type == 23) {
				xmlname = "itemMenu";
			}
			anaylsis(result.ipurl);
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	})
}
function loadxmlDoc(file) {
	try {
		//IE
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	} catch (e) {
		//Firefox, Mozilla, Opera, etc
		xmlDoc = document.implementation.createDocument("", "", null);
	}

	try {
		xmlDoc.async = false;
		xmlDoc.load(file); //chrome没有load方法
	} catch (e) {
		//针对Chrome,不过只能通过http访问,通过file协议访问会报错
		var xmlhttp = new window.XMLHttpRequest();
		xmlhttp.open("GET", file, false);
		xmlhttp.send(null);
		xmlDoc = xmlhttp.responseXML.documentElement;
	}
	return xmlDoc;
}

function isIE() {//判断是否是ie是则返回true；document.all自ie11起返回false，所以此处不可以使用
	if (!!window.ActiveXObject || "ActiveXObject" in window)
		return true;
	else
		return false;
}

var resourceary = [],menuindex = 0;
function anaylsis(ipurl){
	var object = loadxmlDoc(ipurl+"ConfigFile/"+xmlname+".xml");
	var menuinfo = object.getElementsByTagName("Menuinfo");
	for(var m = 1; m <= menuinfo.length; m++){
		for (var i = 0; i < menuinfo.length; i++) {
			var showIndex = menuinfo[i].getElementsByTagName("ShowIndex");//显示位置
			if (isIE()) {
				showIndex = showIndex[0].text;
			} else {
				showIndex = showIndex[0].textContent;
			}
			if(m == showIndex){
				var menuName = menuinfo[i].getElementsByTagName("Name");//菜单名
				var submenus = menuinfo[i].getElementsByTagName("Submenus");//二级菜单
				var imgName = menuinfo[i].getElementsByTagName("ImgName");//菜单图标
				var array = [], firstcontext = "";
				if (isIE()) {
					menuName = menuName[0].text,imgName = imgName[0].text;
				} else {
					menuName = menuName[0].textContent,imgName = imgName[0].textContent;
				}
				var context = '<ul id="ul'+showIndex+'">';
				for (var x = 0; x < submenus.length; x++) {
					var firstName = submenus[x].getElementsByTagName("FirstName");
					var firstResource = submenus[x].getElementsByTagName("FirstResource");
					var firstimgName = submenus[x].getElementsByTagName("FirstImgName");//菜单图标
					var firstsubmenus = submenus[x].getElementsByTagName("FirstSubmenus");//三级菜单
					var firstshowIndex = submenus[x].getElementsByTagName("FirstShowIndex");//显示位置
					var subnenustext,lastcontext = "";
					if (isIE()) {//IE
						firstName = firstName[0].text,firstResource = firstResource[0].text,firstimgName = firstimgName[0].text,
						subnenustext = firstsubmenus[0].text,firstshowIndex = firstshowIndex[0].text;
					} else {
						firstName = firstName[0].textContent,firstResource = firstResource[0].textContent,firstimgName = firstimgName[0].textContent,
						subnenustext = firstsubmenus[0].textContent,firstshowIndex = firstshowIndex[0].textContent;
					}
					if($.inArray(firstResource,resourceary)!=-1){
						var bottonnum = 0;
						if(x == submenus.length-1){
							bottonnum = 15;
						}
						if(firstResource == "td/newAllTd"){
							firstcontext +='<li onclick="changeColor('+menuindex+')" id="'+firstshowIndex+'"><a href="javascript:openLive()" ><div><img src="resources/images/'+firstimgName+'" />&nbsp;&nbsp;'+firstName+'<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="click'+menuindex+'"/></div></a></li>';
						}else{
							firstcontext +='<li style="margin-bottom:'+bottonnum+'px" onclick="changeColor('+menuindex+')" id="'+firstshowIndex+'"><a href="javascript:openTab(\''+firstName+'\',\''+firstResource+'\')" ><div><img src="resources/images/'+firstimgName+'" />&nbsp;&nbsp;'+firstName+'<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="click'+menuindex+'"/></div></a></li>';
						}
						menuindex++;
					}
				}
				context += firstcontext+'</ul>';
				if(firstcontext != null && firstcontext != ""){
					//动态渲染easyui的菜单样式
					$('#accordiondiv').accordion('add', {
						title : menuName,
						content : context,
						iconCls: imgName,
						selected : false
					});
					menusort("ul"+showIndex);
				}
				$("#accordiondiv").accordion({
		            selected:0
		        })
			}
		}
	}
	$.parser.parse($("#accordiondiv"));
}

//根据位置进行排序
function menusort(name/*,flag*/){
	var ul=document.getElementById(name);
    var li=ul.getElementsByTagName("li");
    var list=[];
    //将li添加到list数组
    for(var n=0;n<li.length;n++){
    	var subid = li[n].id.substring(0, 4);
    	if(subid!="last"){
        	list.push(li[n]);
    	}
    	
    }
    //对数组进行比较排序
    list.sort(function (li1,li2){
    	var n1,n2
		n1=parseInt(li1.id);
		n2=parseInt(li2.id);
        return n1-n2;
    })
    for(var x=0;x<list.length;x++){
       ul.appendChild(list[x]);
    }
}

//打开标签页
function openTab(name,resource){
	addTab(name,resource);
}

function openLive(){
	window.open("td/newAllTd");
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
function changeColor(obj_id) {
	for(var i=0;i<=menuindex;i++){
		if(!$("#click"+i).is(":hidden")){
			$("#click"+i).hide();
			break;
		}
	}
	$("#click"+obj_id).show();
}

function updatePwd() {
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
}