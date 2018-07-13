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
					<div style="float:left"><a href="javascript:void(0)" style="">首页</a></div>
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
  	<div region="west" hide="true" split="true" title="导航菜单" style="width: 200px;" id="west">
	  	<div class="easyui-accordion" border="false" id="accordiondiv"></div>
	</div>
	<!-- 管理员 -->
	<div id="admin">
		<ul>
			<li onclick="changeColor(this)"><a href="javascript:openUser()"><div><i class="iconfont icon-bijiben"></i>用户管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openRole()"><div><i class="iconfont icon-bijiben"></i>角色管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openAuthority()"><div><i class="iconfont icon-bijiben"></i>权限管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openResource()"><div><i class="iconfont icon-bijiben"></i>资源管理</div></a></li>
	        <li  onclick="changeColor(this)"><a href="javascript:openDictionary()"><div><i class="iconfont icon-bijiben"></i>字典管理</div></a></li>
	        <li onclick="changeColor(this)"><a href="javascript:openWedJunction()"><div><i class="iconfont icon-bijiben"></i>焊口管理</div></a></li>
	        <li onclick="changeColor(this)"><a href="javascript:openWelder()"><div><i class="iconfont icon-bijiben"></i>焊工管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openWeldingMachine()"><div><i class="iconfont icon-bijiben"></i>焊机设备管理</div></a></li>
			<li onclick="changeColor(this)"><a href="javascript:openMachineMigrate()"><div><i class="iconfont icon-bijiben"></i>焊机设备迁移</div></a></li>
		    <li onclick="changeColor(this)"><a href="javascript:openMachine()"><div><i class="iconfont icon-bijiben"></i>维修记录管理</div></a></li>
		    <li onclick="changeColor(this)"><a href="javascript:openFault()"><div><i class="iconfont icon-bijiben"></i>故障代码管理</div></a></li>
		    <li onclick="changeColor(this)"><a href="javascript:openManufacturer()"><div><i class="iconfont icon-bijiben"></i>生产厂商管理</div></a></li>
	        <li onclick="changeColor(this)"><a href="javascript:openInsframework()"><div><i class="iconfont icon-bijiben"></i>组织机构管理</div></a></li>
	        <li style="margin-bottom: 10px;" onclick="changeColor(this)"><a href="javascript:openGather()"><div><i class="iconfont icon-bijiben"></i>采集模块管理</div></a></li>
	   	</ul>
    </div>
	<!-- 集团 -->
	<div id="bloc" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocEfficiency()"><div><i class="iconfont icon-bijiben"></i>集团工效</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocLoads()"><div><i class="iconfont icon-bijiben"></i>集团设备负荷率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocNoLoads()"><div><i class="iconfont icon-bijiben"></i>集团设备空载率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openUseratio('集团')"><div><i class="iconfont icon-bijiben"></i>集团设备利用率</div></a></li>
<!--     		<li onclick="changeColor(this)"><a href="javascript:openMaintenance('集团')"><div><i class="iconfont icon-bijiben"></i>集团设备维修率</div></a></li> -->
    		<li onclick="changeColor(this)"><a href="javascript:openBlocIdle()"><div><i class="iconfont icon-bijiben"></i>集团设备闲置率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocHour()"><div><i class="iconfont icon-bijiben"></i>集团焊口焊接工时</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocovertime()"><div><i class="iconfont icon-bijiben"></i>集团超时待机统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openRunTime('集团')"><div><i class="iconfont icon-bijiben"></i>集团设备运行时长</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocoverproof()"><div><i class="iconfont icon-bijiben"></i>集团焊接工艺超标统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openOverproofRecall()"><div><i class="iconfont icon-bijiben"></i>集团焊接工艺超标回溯</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openBlocUse()"><div><i class="iconfont icon-bijiben"></i>集团单台设备运行数据统计</div></a></li>
    	</ul>
    </div>
	<!-- 公司 -->
    <div id="company" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanytEfficiency()"><div><i class="iconfont icon-bijiben"></i>公司工效</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyTd()"><div><i class="iconfont icon-bijiben"></i>公司实时监测</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyLoads()"><div><i class="iconfont icon-bijiben"></i>公司设备负荷率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyNoLoads()"><div><i class="iconfont icon-bijiben"></i>公司设备空载率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openUseratio('公司')"><div><i class="iconfont icon-bijiben"></i>公司设备利用率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyIdle()"><div><i class="iconfont icon-bijiben"></i>公司设备闲置率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyHour()"><div><i class="iconfont icon-bijiben"></i>公司焊口焊接工时</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyovertime()"><div><i class="iconfont icon-bijiben"></i>公司超时待机统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openRunTime('公司')"><div><i class="iconfont icon-bijiben"></i>公司设备运行时长</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyoverproof()"><div><i class="iconfont icon-bijiben"></i>公司焊接工艺超标统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openOverproofRecall()"><div><i class="iconfont icon-bijiben"></i>公司焊接工艺超标回溯</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCompanyUse()"><div><i class="iconfont icon-bijiben"></i>公司单台设备运行数据统计</div></a></li>
    	</ul>
    </div>
	<!-- 事业部 -->
    <div id="caust" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustEfficiency()"><div><i class="iconfont icon-bijiben"></i>事业部工效</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustTd()"><div><i class="iconfont icon-bijiben"></i>事业部实时监测</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustLoads()"><div><i class="iconfont icon-bijiben"></i>事业部设备负荷率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openUseratio('事业部')"><div><i class="iconfont icon-bijiben"></i>事业部设备利用率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustNoLoads()"><div><i class="iconfont icon-bijiben"></i>事业部设备空载率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustIdle()"><div><i class="iconfont icon-bijiben"></i>事业部设备闲置率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustHour()"><div><i class="iconfont icon-bijiben"></i>事业部焊口焊接工时</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustovertime()"><div><i class="iconfont icon-bijiben"></i>事业部超时待机统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openRunTime('事业部')"><div><i class="iconfont icon-bijiben"></i>事业部设备运行时长</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustoverproof()"><div><i class="iconfont icon-bijiben"></i>事业部焊接工艺超标统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openOverproofRecall()"><div><i class="iconfont icon-bijiben"></i>事业部焊接工艺超标回溯</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openCaustUse()"><div><i class="iconfont icon-bijiben"></i>事业部单台设备运行数据统计</div></a></li>
    	</ul>
    </div>
	<!-- 项目部 -->
    <div  id="item" >
    	<ul>
    		<li onclick="changeColor(this)"><a href="javascript:openItemEfficiency()"><div><i class="iconfont icon-bijiben"></i>项目部工效</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemTd()"><div><i class="iconfont icon-bijiben"></i>项目部实时监测</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemLoads()"><div><i class="iconfont icon-bijiben"></i>项目部设备负荷率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemNoLoads()"><div><i class="iconfont icon-bijiben"></i>项目部设备空载率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openUseratio('项目部')"><div><i class="iconfont icon-bijiben"></i>项目部设备利用率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemIdle()"><div><i class="iconfont icon-bijiben"></i>项目部设备闲置率</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemHour()"><div><i class="iconfont icon-bijiben"></i>项目部焊口焊接工时</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemovertime()"><div><i class="iconfont icon-bijiben"></i>项目部超时待机统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openRunTime('项目部')"><div><i class="iconfont icon-bijiben"></i>项目部设备运行时长</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemoverproofs()"><div><i class="iconfont icon-bijiben"></i>项目部焊接工艺超标统计</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openOverproofRecall()"><div><i class="iconfont icon-bijiben"></i>项目部焊接工艺超标回溯</div></a></li>
    		<li onclick="changeColor(this)"><a href="javascript:openItemUse()"><div><i class="iconfont icon-bijiben"></i>项目部单台设备运行数据统计</div></a></li>
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
