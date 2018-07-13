$(function(){
	$('#accordiondiv').accordion('add', {
		title: '<i class="iconfont icon-ren"></i>    管理员',
		content: $("#admin").html()
	});
	getUserInsframework();
	addTab("欢迎使用","welcome.jsp");
	tabsIncident();
})
var type;
function hierarchyLoding(){
	$.ajax({  
	      type : "post",  
	      async : false,
	      url : "hierarchy/goIndex",  
	      data : {},  
	      dataType : "json", //返回数据形式为json  
	      success : function(result) {
	          if (result) {
	              if(result.hierarchy==1){//集团层
	            	  $('#accordiondiv').accordion('add', {
	            			title: '<i class="iconfont icon-ren"></i>    集团',
	            			content: $("#bloc").html(),
	            			selected: false
	            		});
	              }else if(result.hierarchy==2 || result.hierarchy==3){//公司层/事业部层
	            	  if(type==21){
		            	  $('#accordiondiv').accordion('add', {
		            			title: '<i class="iconfont icon-ren"></i>    公司',
		            			content: $("#company").html(),
		            			selected: false
		            		});
	            	  }else if(type==22){
		            	  $('#accordiondiv').accordion('add', {
		            			title: '<i class="iconfont icon-ren"></i>    事业部',
		            			content: $("#caust").html(),
		            			selected: false
		            		});
	            	  }
	              }else if(result.hierarchy==4){//项目部层
	            	  $('#accordiondiv').accordion('add', {
	            			title: '<i class="iconfont icon-ren"></i>    项目部',
	            			content: $("#item").html(),
	            			selected: false
	            		});
	              }
	          }  
	      },
	      error : function(errorMsg) {  
	          alert("数据请求失败，请联系系统管理员!");  
	      }  
	 }); 
}

function getUserInsframework(){
	$.ajax({
		type : "post",
		async : true,
		url : "hierarchy/getUserInsframework",
		data : {},
		dataType : "json",
		success : function(result){
			type = result.type;
			var str = "<span>"+result.insframework+": </span><span>"+result.uname+"</span>";
			$("#uname").val(result.uname);
			$("#uid").val(result.id);
			$("#userInsframework").append(str);
//			hierarchyLoding();
			if(type==20){
          	  $('#accordiondiv').accordion('add', {
          			title: '<i class="iconfont icon-ren"></i>    集团',
          			content: $("#bloc").html(),
          			selected: false
          		});
			}else if(type==21){
          	  $('#accordiondiv').accordion('add', {
          			title: '<i class="iconfont icon-ren"></i>    公司',
          			content: $("#company").html(),
          			selected: false
          		});
      	  }else if(type==22){
          	  $('#accordiondiv').accordion('add', {
          			title: '<i class="iconfont icon-ren"></i>    事业部',
          			content: $("#caust").html(),
          			selected: false
          		});
      	  }else if(type==23){
        	  $('#accordiondiv').accordion('add', {
        			title: '<i class="iconfont icon-ren"></i>    项目部',
        			content: $("#item").html(),
        			selected: false
        		});
      	  }
		},
		error : function(errorMsg){
			alert("数据请求失败，请联系系统管理员!");
		}
	})
}

function openMaintenance(name){
	addTab(name+"设备维修率","blocChart/goMaintenanceratio");
}

function openUseratio(name){
	addTab(name+"设备利用率","blocChart/goUseratio");
}

function openRunTime(name){
	addTab(name+"设备运行时长","blocChart/goBlocRunTime");
}

function openOverproofRecall(){
	addTab("焊接工艺超标回溯","companyChart/goSelectWelderJunction");
}

function openUser(){
	addTab("用户管理","user/AllUser");
}

function openRole(){
	addTab("角色管理","role/AllRole");
}

function openAuthority(){
	addTab("权限管理","authority/AllAuthority");
}

function openResource(){
	addTab("资源管理","resource/AllResource");
}
function openData(){
	addTab("实时数据","data/AllData");
}

function openTd(){
	addTab("公司实时监测","td/AllTdp");
}

function openWeldingMachine(){
	addTab("焊机设备管理","weldingMachine/goWeldingMachine");
}

function openMachineMigrate(){
	addTab("焊机设备迁移","weldingMachine/goMachineMigrate");
}

function openMachine(){
	addTab("维修记录管理","maintain/goMaintain");
}

function openFault(){
	addTab("故障代码管理","fault/goFault");
}

function openManufacturer(){
	addTab("生产厂商管理","manufacturer/goManufacturer");
}

function openWedJunction(){
	addTab("焊口列表","weldedjunction/goWeldedJunction");
}

function openWelder(){
	addTab("焊工列表","welder/goWelder");
}

function openInsframework(){
	addTab("组织机构管理","insframework/goInsframework");
}

function openGather(){
	addTab("采集模块管理","gather/goGather");
}

function openCaustEfficiency(){
	addTab("事业部工效","caustChart/goCaustEfficiency");
}

function openCaustHour(){
	addTab("事业部焊口焊接工时","caustChart/goCaustHour");
}

function openCaustoverproof(){
	addTab("事业部焊接工艺超标统计","caustChart/goCaustOverproof");
}

function openCaustovertime(){
	addTab("事业部超时待机统计","caustChart/goCaustOvertime");
}

function openCaustLoads(){
	addTab("事业部设备负荷率","caustChart/goCaustLoads");
}

function openCaustNoLoads(){
	addTab("事业部设备空载率","caustChart/goCaustNoLoads");
}

function openCaustIdle(){
	addTab("事业部设备闲置率","caustChart/goCaustIdle");
}

function openCaustUse(){
	addTab("事业部单台设备运行数据统计","caustChart/goCaustUse");
}

function openCaustTd(){
	addTab("事业部实时监测","td/AllTddi");
}

function openCompanytEfficiency(){
	addTab("公司工效","companyChart/goCompanyEfficiency");
}

function openCompanyUse(){
	addTab("公司单台设备运行数据统计","companyChart/goCompanyUse");
}

function openCompanyHour(){
	addTab("公司焊口焊接工时","companyChart/goCompanyHour");
}

function openCompanyoverproof(){
	addTab("公司焊接工艺超标统计","companyChart/goCompanyOverproof");
}

function openCompanyovertime(){
	addTab("公司超时待机统计","companyChart/goCompanyOvertime");
}

function openCompanyLoads(){
	addTab("公司设备负荷率","companyChart/goCompanyLoads");
}

function openCompanyNoLoads(){
	addTab("公司设备空载率","companyChart/goCompanyNoLoads");
}

function openCompanyIdle(){
	addTab("公司设备闲置率","companyChart/goCompanyIdle");
}

function openCompanyTd(){
	addTab("公司实时监测","td/AllTd");
}

function openItemEfficiency(){
	addTab("项目部工效","itemChart/goItemEfficiency");
}

function openItemHour(){
	addTab("项目部焊口焊接工时","itemChart/goItemHour");
}

function openItemovertime(){
	addTab("项目部超时待机统计","itemChart/goItemOvertime");
}

function openItemLoads(){
	addTab("项目部设备负荷率","itemChart/goItemLoads");
}

function openItemNoLoads(){
	addTab("项目部设备空载率","itemChart/goItemNoLoads");
}

function openItemIdle(){
	addTab("项目部设备闲置率","itemChart/goItemIdle");
}

function openItemTd(){
	addTab("项目部实时监测","td/AllTdp");
}

function openItemoverproofs(){
	addTab("项目部焊接工艺超标统计","itemChart/goItemoverproof");
}

function openItemUse(){
	addTab("项目部单台设备运行数据统计","itemChart/goItemUse");
}

function openBlocEfficiency(){
	addTab("集团工效","blocChart/goBlocEfficiency");
}

function openBlocUse(){
	addTab("集团单台设备运行数据统计","blocChart/goBlocUse");
}

function openBlocHour(){
	addTab("集团焊口焊接工时","blocChart/goBlocHour");
}

function openBlocoverproof(){
	addTab("集团焊接工艺超标统计","blocChart/goBlocOverproof");
}

function openBlocovertime(){
	addTab("集团超时待机统计","blocChart/goBlocOvertime");
}

function openBlocLoads(){
	addTab("集团设备负荷率","blocChart/goBlocLoads");
}

function openBlocNoLoads(){
	addTab("集团设备空载率","blocChart/goBlocNoLoads");
}

function openBlocIdle(){
	addTab("集团设备闲置率","blocChart/goBlocIdle");
}
function openDictionary(){
	addTab("字典管理",'Dictionary/goDictionary')
}
function addTab(title,url){
	//该面板是否已打开
	if(!$("#tabs").tabs('exists',title)){
		$("#tabs").tabs('add',{    
		    title:title,    
		    content:createFrame(url),    
		    closable:true 
		});
	}else{
		$("#tabs").tabs('select',title);
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
function tabsIncident(){
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
	$("#closetab").click(function(){
		var currtab_title = $('#tabMenu').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	})
	// 全部关闭
	$('#closeAll').click(function() {
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});
	});
	// 关闭其他标签页
	$('#closeOther').click(function() {
		$("#closeRight").click();
		$('#closeLeft').click();
	});
	//关闭右侧标签页
	$('#closeRight').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
	})
	//关闭左侧标签页
	$('#closeLeft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
	});
}

//选中时改变背景颜色
function changeColor(obj){
	$("ul li").css("backgroundColor","#ffffff");
	obj.style.background = "#ffe48d";
}

function updatePwd(){
	$('#dlg').window( {
		modal : true
	});
	$("#dlg").dialog("open");
	$("#pwd").val("");
	$("#pwds").val("");
	noticeAssign(99);
}
function pwdKeyUp(password){
	var pwd = $(password).val();
	if(pwd){
		if(/[a-zA-Z]+/.test(pwd) && /[0-9]+/.test(pwd) && /\W+|_+/.test(pwd)) {
			noticeAssign(2);
		}else if(/[a-zA-Z]+/.test(pwd) || /[0-9]+/.test(pwd) || /\W+|_+/.test(pwd)) {
			if(/[a-zA-Z]+/.test(pwd) && /[0-9]+/.test(pwd)) {
				noticeAssign(1);
			}else if(/[a-zA-Z]+/.test(pwd) && /\W+|_+/.test(pwd)) {
				noticeAssign(1);
			}else if(/[0-9]+/.test(pwd) && /\W+|_+/.test(pwd)) {
				noticeAssign(1);
			}else{
				noticeAssign(0);
			}
		 }
	}else{
		noticeAssign(99);
	}
}
function noticeAssign(num) {
	 if(num == 2) {
		 $('#weak').css({backgroundColor:''});
		 $('#middle').css({backgroundColor:''});
		 $('#strength').css({backgroundColor:'#ffcc33'});
	 }else if(num == 1){
		 $('#weak').css({backgroundColor:''});
		 $('#middle').css({backgroundColor:'#ffcc33'});
		 $('#strength').css({backgroundColor:''});
	 }else if(num ==0) {
		 $('#weak').css({backgroundColor:'#ffcc33'});
		 $('#middle').css({backgroundColor:''});
		 $('#strength').css({backgroundColor:''});
	 }else{
		 $('#weak').css({backgroundColor:''});
		 $('#middle').css({backgroundColor:''});
		 $('#strength').css({backgroundColor:''});
	 }
}

function updatePassword(){
	$("#pwdcheck").html("");
	if(!$("#pwd").val()){
		$("#pwdcheck").append("请输入密码");
	}else if(!$("#pwds").val()){
		$("#pwdcheck").append("请确认密码");
	}else if($("#pwd").val() != $("#pwds").val()){
		$("#pwdcheck").append("两次密码不一致");
	}else{
		$('#fm').form('submit', {
			url : "user/updatePwd?id="+$("#uid").val()+"&pwd="+$("#pwd").val(),
			success : function(result) {
				if(result){
					var result = eval('(' + result + ')');
					if (!result.success) {
						$.messager.show( {
							title : 'Error',
							msg : result.errorMsg
						});
					} else {
						$.messager.alert("提示", "修改成功！");
						$('#dlg').dialog('close');
						var url = "user/logout";
						var img = new Image();
					    img.src = url;  // 设置相对路径给Image, 此时会发送出请求
					    url = img.src;  // 此时相对路径已经变成绝对路径
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