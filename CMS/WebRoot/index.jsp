<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%response.setHeader("Cache-Control","no-store");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/common.css">
	<link rel="stylesheet" type="text/css" href="resources/css/iconfont.css">
	
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/index.js"></script>
	<style type="text/css">
		a{text-decoration:none;color:inherit;outline:none;}
	</style>
  </head>

  <body class="easyui-layout">
  	<!-- 头部 -->
  	<div region="north" split="true" style="height: 128px;" id="north">
		<div class="head-wrap">
			<a href="" class="logo"></a>
			<div class="search-wrap">
				<div class="search">
					<input type="" placeholder='公司内部' class=''>
					<button><i class="iconfont icon-search ver-a-m"></i></button>
				</div>
			</div>
			<div class="box clearfix">
				<div class="box-lef fl"></div>
				<div class="box-rig fl" id="boxrig">
					<div style="float:left"><a href="">首页</a></div>
					<div style="float:right">
						<a href="javascript:history.go(-1)" id="pageUp">返回</a>
						<a href="javascript:void(0)" id="userInsframework"></a>
						<a href="user/logout">注销</a>
						<a href="javascript:updatePwd()">修改密码</a>
					</div>
				</div>
			</div>
		</div>
  	</div>
  	<div region="west" hide="true" split="true" title= "导航菜单" style="width: 200px;background: #1d294d" id="west" data-options="iconCls:'icon-navigation'">
	  	<div class="easyui-accordion" border="false" id="accordiondiv"></div>
	</div>
	<!-- 集团 -->
	<div id="bloc1" >
    	<ul>

    		<li onclick="changeColor(this)"><a href="javascript:openBlocLoads()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;设备负荷率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocNoLoads()"><div><img src="resources/images/s-8.png" />&nbsp;&nbsp;设备空载率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openUseratio()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;设备利用率</div></a></li>
<!--     		<li onclick="changeColor(this)"><a href="javascript:openFaultRatio()"><div><i class="iconfont icon-bijiben"></i>设备故障率</div></a></li> -->
    		<li onclick="changeColor(this)"><a href="javascript:openMaintenance()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;设备维修率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocIdle()"><div><img src="resources/images/c-13.png" />&nbsp;&nbsp;设备闲置率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openRunTime()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;设备运行时长</div></a></li>
    		<li onclick="changeColor(this)" class="bootomdtyle"><a href="javascript:openBlocUse()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;单台设备运行数据统计</div></a></li>
    	</ul>
    </div>
	<div id="bloc2" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocEfficiency()"><div><img src="resources/images/s-3.png" />&nbsp;&nbsp;工效</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openOperatorEfficiency()"><div><img src="resources/images/s-15.png" />&nbsp;&nbsp;操作者效率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocHour()"><div><img src="resources/images/s-14.png" />&nbsp;&nbsp;焊口焊接工时</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocovertime()"><div><img src="resources/images/s-13.png" />&nbsp;&nbsp;超时待机统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocoverproof()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;焊接工艺超标统计</div></a></li>
    		<li onclick="changeColor(this)" class="bootomdtyle"><a href="javascript:openOverproofRecall()"><div><img src="resources/images/s-5.png" />&nbsp;&nbsp;焊接工艺超标回溯</div></a></li>
    	</ul>
    </div>
	<!-- 公司 -->
    <div id="company1" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyTd()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;焊机实时状态监测</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyLoads()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;设备负荷率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyNoLoads()"><div><img src="resources/images/s-8.png" />&nbsp;&nbsp;设备空载率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openUseratio()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;设备利用率</div></a></li>
<!--     		<li onclick="changeColor(this)"><a href="javascript:openFaultRatio()"><div><i class="iconfont icon-bijiben"></i>设备故障率</div></a></li> -->
    		<li onclick="changeColor(this)"><a href="javascript:openMaintenance()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;设备维修率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyIdle()"><div><img src="resources/images/c-13.png" />&nbsp;&nbsp;设备闲置率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openRunTime()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;设备运行时长</div></a></li>
    		<li onclick="changeColor(this)" class="bootomdtyle"><a href="javascript:openCompanyUse()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;单台设备运行数据统计</div></a></li>
    	</ul>
    </div>
    <div id="company2" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanytEfficiency()"><div><img src="resources/images/s-3.png" />&nbsp;&nbsp;工效</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openOperatorEfficiency()"><div><img src="resources/images/s-15.png" />&nbsp;&nbsp;操作者效率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyHour()"><div><img src="resources/images/s-14.png" />&nbsp;&nbsp;焊口焊接工时</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyovertime()"><div><img src="resources/images/s-13.png" />&nbsp;&nbsp;超时待机统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyoverproof()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;焊接工艺超标统计</div></a></li>
    		<li onclick="changeColor(this)" class="bootomdtyle"><a href="javascript:openOverproofRecall()"><div><img src="resources/images/s-5.png" />&nbsp;&nbsp;焊接工艺超标回溯</div></a></li>
    	</ul>
    </div>
	<!-- 事业部 -->
    <div id="caust1" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyTd()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;焊机实时状态监测</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustLoads()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;设备负荷率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustNoLoads()"><div><img src="resources/images/s-8.png" />&nbsp;&nbsp;设备空载率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openUseratio()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;设备利用率</div></a></li>
<!--     		<li onclick="changeColor(this)"><a href="javascript:openFaultRatio()"><div><i class="iconfont icon-bijiben"></i>设备故障率</div></a></li> -->
    		<li onclick="changeColor(this)"><a href="javascript:openMaintenance()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;设备维修率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustIdle()"><div><img src="resources/images/c-13.png" />&nbsp;&nbsp;设备闲置率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openRunTime()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;设备运行时长</div></a></li>
    		<li onclick="changeColor(this)" class="bootomdtyle"><a href="javascript:openCaustUse()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;单台设备运行数据统计</div></a></li>
    	</ul>
    </div>
    <div id="caust2" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustEfficiency()"><div><img src="resources/images/s-3.png" />&nbsp;&nbsp;工效</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openOperatorEfficiency()"><div><img src="resources/images/s-15.png" />&nbsp;&nbsp;操作者效率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustHour()"><div><img src="resources/images/s-14.png" />&nbsp;&nbsp;焊口焊接工时</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustovertime()"><div><img src="resources/images/s-13.png" />&nbsp;&nbsp;超时待机统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustoverproof()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;焊接工艺超标统计</div></a></li>
    		<li onclick="changeColor(this)" class="bootomdtyle"><a href="javascript:openOverproofRecall()"><div><img src="resources/images/s-5.png" />&nbsp;&nbsp;焊接工艺超标回溯</div></a></li>
    	</ul>
    </div>
	<!-- 项目部 -->
    <div  id="item1" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyTd()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;焊机实时状态监测</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemLoads()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;设备负荷率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemNoLoads()"><div><img src="resources/images/s-8.png" />&nbsp;&nbsp;设备空载率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openUseratio()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;设备利用率</div></a></li>
<!--     		<li onclick="changeColor(this)"><a href="javascript:openFaultRatio()"><div><i class="iconfont icon-bijiben"></i>设备故障率</div></a></li> -->
    		<li onclick="changeColor(this)"><a href="javascript:openMaintenance()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;设备维修率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemIdle()"><div><img src="resources/images/c-13.png" />&nbsp;&nbsp;设备闲置率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openRunTime()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;设备运行时长</div></a></li>
    		<li onclick="changeColor(this)" class="bootomdtyle"><a href="javascript:openItemUse()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;单台设备运行数据统计</div></a></li>
    	</ul>
    </div>
    <div  id="item2" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openItemEfficiency()"><div><img src="resources/images/s-3.png" />&nbsp;&nbsp;工效</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openOperatorEfficiency()"><div><img src="resources/images/s-15.png" />&nbsp;&nbsp;操作者效率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemHour()"><div><img src="resources/images/s-14.png" />&nbsp;&nbsp;焊口焊接工时</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemovertime()"><div><img src="resources/images/s-13.png" />&nbsp;&nbsp;超时待机统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemoverproofs()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;焊接工艺超标统计</div></a></li>
    		<li onclick="changeColor(this)" class="bootomdtyle"><a href="javascript:openOverproofRecall()"><div><img src="resources/images/s-5.png" />&nbsp;&nbsp;焊接工艺超标回溯</div></a></li>
    	</ul>
    </div>
	<!-- 管理员 -->
	<div id="admin">
		<ul>
			<li onclick="changeColor(this)"><a href="javascript:openUser()"><div><img src="resources/images/c-1.png" />&nbsp;&nbsp;用户管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openRole()"><div><img src="resources/images/c-2.png" />&nbsp;&nbsp;角色管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openAuthority()"><div><img src="resources/images/c-3.png" />&nbsp;&nbsp;权限管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openResource()"><div><img src="resources/images/c-4.png" />&nbsp;&nbsp;资源管理</div></a></li>
	        <li onclick="changeColor(this)"><a href="javascript:openDictionary()"><div><img src="resources/images/c-5.png" />&nbsp;&nbsp;字典管理</div></a></li>
	        <li onclick="changeColor(this)"><a href="javascript:openWedJunction()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;焊口管理</div></a></li>
	        <li onclick="changeColor(this)"><a href="javascript:openWelder()"><div><img src="resources/images/c-6.png" />&nbsp;&nbsp;焊工管理</div></a></li>
	        <li onclick="changeColor(this)"><a href="javascript:openWps()"><div><img src="resources/images/c-9.png" />&nbsp;&nbsp;工艺管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openWeldingMachine()"><div><img src="resources/images/c-4.png" />&nbsp;&nbsp;焊机设备管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openMachineMigrate()"><div><img src="resources/images/c-12.png" />&nbsp;&nbsp;焊机设备迁移</div></a></li>
		    <li onclick="changeColor(this)"><a href="javascript:openMachine()"><div><img src="resources/images/c-10.png" />&nbsp;&nbsp;维修记录管理</div></a></li>
<!-- 		    <li onclick="changeColor(this)"><a href="javascript:openFault()"><div><i class="iconfont icon-bijiben"></i>故障代码管理</div></a></li> -->
		    <li onclick="changeColor(this)"><a href="javascript:openManufacturer()"><div><img src="resources/images/c-13.png" />&nbsp;&nbsp;生产厂商管理</div></a></li>
	        <li onclick="changeColor(this)"><a href="javascript:openInsframework()"><div><img src="resources/images/c-11.png" />&nbsp;&nbsp;组织机构管理</div></a></li>
	        <li style="margin-bottom: 10px;" onclick="changeColor(this)"><a href="javascript:openGather()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;采集模块管理</div></a></li>
	   	</ul>
    </div>
	<div id="mainPanle" region="center" style="background: white; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false"></div>
		<div id="tabMenu" class="easyui-menu" style="width:150px">
			<div id="refreshtab">刷新</div>
			<div id="closetab">关闭标签页</div>
			<div id="closeLeft">关闭左侧标签页</div>
			<div id="closeRight">关闭右侧标签页</div>
			<div id="closeOther">关闭其他标签页</div>
			<div id="closeAll">关闭全部标签页</div>
	    </div>
	</div>
	<div data-options="region:'south',split:true" style="height:20px;">
   		<div class="tenghan-bottom">
			<div style="margin-right:5px;"><a href="" class="logo"></a></div>
		</div>
	</div>
	<!-- 修改密码 -->
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 300px; padding:10px 20px" closed="true" title="修改密码" buttons="#dlg-buttons">
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
		<div class="fitem">
			<lable>用户名</lable>
			<input type="hidden" name="uid" id="uid" readonly="true"/>
			<input type="text" name="uname" id="uname" readonly="true"/>
		</div>
		<div class="fitem">
			<lable><span class="required">*</span>密码</lable>
			<input type="password" name="pwd" id="pwd" onkeyup="pwdKeyUp(this)" /><br/>
		</div>
		<div class="fitem">
			<lable>安全程度</lable>
			<span id="weak">弱</span><span id="middle">中</span><span id="strength">强</span>
		</div>
		<div class="fitem">
			<lable><span class="required">*</span>确认密码</lable>
			<input type="password"  name="pwds" id="pwds" data-options="required:true"/>
		</div>
		<div id="pwdcheck" style="color:red;width:220px;height:20px;text-align: center;margin-left:45px;"></div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:updatePassword();" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:$('#dlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
	</div>
  </body>
</html>
