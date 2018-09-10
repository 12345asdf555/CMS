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
	<script type="text/javascript" src="resources/js/indexs.js"></script>
	<style type="text/css">
		a{text-decoration:none;color:inherit;outline:none;}
	</style>
  </head>

  <body class="easyui-layout">
  	<!-- 头部 -->
  	<!-- <div region="north" split="true" style="height: 128px;" id="north">
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
  	</div> -->
  	<div region="west" hide="true" split="true" title= "导航菜单" style="width: 200px;background: #1d294d" id="west" data-options="iconCls:'icon-navigation'">
	  	<div class="easyui-accordion" border="false" id="accordiondiv"></div>
	</div>
	<!-- 集团 -->
	<div id="bloc1" >
    	<ul>
    		<li onclick="changeColor(1)"><a href="javascript:openUseratio()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;设备利用率&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid1"/></div></a></li>
    		<li onclick="changeColor(2)"><a href="javascript:openMachineTop()"><div><img src="resources/images/s-11.png" />&nbsp;&nbsp;焊机最高排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid2"/></div></a></li>
    		<li onclick="changeColor(3)"><a href="javascript:openMachineBottom()"><div><img src="resources/images/s-12.png" />&nbsp;&nbsp;焊机最低排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid3"/></div></a></li>
    		<li onclick="changeColor(4)"><a href="javascript:openWelderTop()"><div><img src="resources/images/s-13.png" />&nbsp;&nbsp;焊工最高排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid4"/></div></a></li>
    		<li onclick="changeColor(5)" class="bootomdtyle"><a href="javascript:openWelderBottom()"><div><img src="resources/images/s-14.png" />&nbsp;&nbsp;焊工最低排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid5"/></div></a></li>
    	</ul>
    </div>
    <div id="bloc2" >
    	<ul>
    		<li onclick="changeColor(25)"><a href="javascript:openMachineTd()"><div><img src="resources/images/s-1.png" />&nbsp;&nbsp;焊机实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid25"/></div></a></li>
    		<li onclick="changeColor(26)"><a href="javascript:openMachineTd()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;焊口实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid26"/></div></a></li>
    		<li onclick="changeColor(27)" class="bootomdtyle"><a href="javascript:openAlarmTd()"><div><img src="resources/images/s-10.png" />&nbsp;&nbsp;焊机报警实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid27"/></div></a></li>
    	</ul>
    </div>
	<!-- 公司 -->
    <div id="company1" >
    	<ul>
    		<li class="ulli" onclick="changeColor(7)"><a href="javascript:openUseratio()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;设备利用率&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid7"/></div></a></li>
    		<li class="ulli" onclick="changeColor(8)"><a href="javascript:openMachineTop()"><div><img src="resources/images/s-11.png" />&nbsp;&nbsp;焊机最高排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid8"/></div></a></li>
    		<li class="ulli" onclick="changeColor(9)"><a href="javascript:openMachineBottom()"><div><img src="resources/images/s-12.png" />&nbsp;&nbsp;焊机最低排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid9"/></div></a></li>
    		<li class="ulli" onclick="changeColor(10)"><a href="javascript:openWelderTop()"><div><img src="resources/images/s-13.png" />&nbsp;&nbsp;焊工最高排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid10"/></div></a></li>
    		<li onclick="changeColor(11)" class="bootomdtyle"><a href="javascript:openWelderBottom()"><div><img src="resources/images/s-14.png" />&nbsp;&nbsp;焊工最低排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid11"/></div></a></li>
    	</ul>
    </div>
    <div id="company2" >
    	<ul>
    		<li class="ulli" onclick="changeColor(28)"><a href="javascript:openMachineTd()"><div><img src="resources/images/s-1.png" />&nbsp;&nbsp;焊机实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid28"/></div></a></li>
    		<li class="ulli" onclick="changeColor(29)"><a href="javascript:openMachineTd()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;焊口实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid29"/></div></a></li>
    		<li onclick="changeColor(30)" class="bootomdtyle"><a href="javascript:openAlarmTd()"><div><img src="resources/images/s-10.png" />&nbsp;&nbsp;焊机报警实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid30"/></div></a></li>
    	</ul>
    </div>
	<!-- 事业部 -->
    <div id="caust1" >
    	<ul>
    		<li onclick="changeColor(13)"><a href="javascript:openUseratio()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;设备利用率&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid13"/></div></a></li>
    		<li onclick="changeColor(14)"><a href="javascript:openMachineTop()"><div><<img src="resources/images/s-11.png" >&nbsp;&nbsp;焊机最高排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid14"/></div></a></li>
    		<li onclick="changeColor(15)"><a href="javascript:openMachineBottom()"><div><img src="resources/images/s-12.png" />&nbsp;&nbsp;焊机最低排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid15"/></div></a></li>
    		<li onclick="changeColor(16)"><a href="javascript:openWelderTop()"><div><img src="resources/images/s-13.png" />&nbsp;&nbsp;焊工最高排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid16"/></div></a></li>
    		<li onclick="changeColor(17)" class="bootomdtyle"><a href="javascript:openWelderBottom()"><div><img src="resources/images/s-14.png" />&nbsp;&nbsp;焊工最低排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid17"/></div></a></li>
    	</ul>
    </div>
    <div id="caust2" >
    	<ul>
    		<li onclick="changeColor(31)"><a href="javascript:openMachineTd()"><div><img src="resources/images/s-1.png" />&nbsp;&nbsp;焊机实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid31"/></div></a></li>
    		<li onclick="changeColor(32)"><a href="javascript:openMachineTd()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;焊口实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid32"/></div></a></li>
    		<li onclick="changeColor(33)" class="bootomdtyle"><a href="javascript:openAlarmTd()"><div><img src="resources/images/s-10.png" />&nbsp;&nbsp;焊机报警实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid33"/></div></a></li>
    	</ul>
    </div>
	<!-- 项目部 -->
    <div  id="item1" >
    	<ul>
    		<li onclick="changeColor(19)"><a href="javascript:openUseratio()"><div><img src="resources/images/s-7.png" />&nbsp;&nbsp;设备利用率&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid19"/></div></a></li>
    		<li onclick="changeColor(20)"><a href="javascript:openMachineTop()"><div><<img src="resources/images/s-11.png"/>&nbsp;&nbsp;焊机最高排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid20"/></div></a></li>
    		<li onclick="changeColor(21)"><a href="javascript:openMachineBottom()"><div><img src="resources/images/s-12.png" />&nbsp;&nbsp;焊机最低排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid21"/></div></a></li>
    		<li onclick="changeColor(22)"><a href="javascript:openWelderTop()"><div><img src="resources/images/s-13.png" />&nbsp;&nbsp;焊工最高排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid22"/></div></a></li>
    		<li onclick="changeColor(23)" class="bootomdtyle"><a href="javascript:openWelderBottom()"><div><img src="resources/images/s-14.png" />&nbsp;&nbsp;焊工最低排行&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid23"/></div></a></li>
    	</ul>
    </div>
    <div  id="item2" >
    	<ul>
    		<li onclick="changeColor(34)"><a href="javascript:openMachineTd()"><div><img src="resources/images/s-1.png" />&nbsp;&nbsp;焊机实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid34"/></div></a></li>
    		<li onclick="changeColor(35)"><a href="javascript:openMachineTd()"><div><img src="resources/images/c-7.png" />&nbsp;&nbsp;焊口实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid35"/></div></a></li>
    		<li onclick="changeColor(36)" class="bootomdtyle"><a href="javascript:openAlarmTd()"><div><img src="resources/images/s-10.png" />&nbsp;&nbsp;焊机报警实时状态监测&nbsp;<img src="resources/images/jt.png"style="display:none; width:20px;height:20px;" id="clickid36"/></div></a></li>
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
	<!-- 修改密码 
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
	</div>-->
  </body>
</html>