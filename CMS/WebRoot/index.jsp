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
<!-- 						<a href="javascript:history.go(-1)" class="easyui-linkbutton" iconCls="icon-back" id="pageUp">返回</a> -->
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
