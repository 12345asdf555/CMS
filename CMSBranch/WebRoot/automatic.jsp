<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="utf-8"%>
﻿<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html>
	<head>
<%-- 		<base href="<%=basePath%>"> --%>
		<title>云智能焊接管控系统</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		
	 	<link rel="stylesheet" type="text/css" href="resources/css/login.css">
	 	
		<script type="text/javascript" src="resources/js/jquery.min.js"></script>
		<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="resources/js/loading.js"></script>
	</head>

	<body onLoad="document.f.j_username.focus();">
	  	<div id="bodydiv">
	    <div id="logindiv">
	    	<div id="formdiv">
	    		<form name="f" id="f" action="<c:url value='j_spring_security_check'/>" method="POST">
	    			<table width="85%" align="center">
		                <tr>
		                    <td>用户名</td>
				            <td align="center">
				                <input type='text' name='j_username'  id="uname" value='<c:if test="${not empty param.login_error}">
				                <c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
				            </td>
		                </tr>
		                <tr>
				            <td>密码</td>
				            <td align="center"><input type='password' name='j_password' id='j_password'></td>
		                </tr>
<!-- 		                <tr><td><input type="checkbox" name="_spring_security_remember_me"></td><td> 两周内自动登录</td></tr> -->
				        <tr>
				        	<td></td>
				            <td align="center">
				                <!-- <input name="submit" type="submit" value="登录" id="loginbutton"> -->
				            </td>
				        </tr>
				        <tr>
				        	<td colspan="2" align="center" style="text-size:12px">
							    <c:if test="${not empty param.login_error}">
							       <font color="red">
							           用户名或密码不正确，请重新输入。
							       </font>
							    </c:if>
	    					</td>
				        </tr>
	    			</table>
	    		</form>
			</div>
	    </div>
	    <div id="tenghanbottom">Copyright 1998-2017上海腾悍智能科技有限公司</div>
	  	</div>
	    <div align="center" class="connect" style="height: 220px;">
			<span style="color: red;">${error}</span>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			$("#bodydiv").attr("style","display:none;");
			var base = new Base64();
			var uname=base.decode("<%=request.getParameter("uname")%>");
			var pwd=base.decode("<%=request.getParameter("pwd")%>");
			document.getElementById("uname").value=uname;
			document.getElementById("j_password").value=pwd;
/* 			document.getElementById("loginbutton").onclick; */
		    $("#f").submit();  
		})
		
		/** 
		* 
		* Base64 encode / decode 
		* 
		* @author haitao.tu 
		* @date 2010-04-26 
		* @email tuhaitao@foxmail.com 
		* 
		*/
		function Base64() { 
		  
		 // private property 
		 _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="; 
		  
		 // public method for encoding 
		 this.encode = function (input) { 
		  var output = ""; 
		  var chr1, chr2, chr3, enc1, enc2, enc3, enc4; 
		  var i = 0; 
		  input = _utf8_encode(input); 
		  while (i < input.length) { 
		   chr1 = input.charCodeAt(i++); 
		   chr2 = input.charCodeAt(i++); 
		   chr3 = input.charCodeAt(i++); 
		   enc1 = chr1 >> 2; 
		   enc2 = ((chr1 & 3) << 4) | (chr2 >> 4); 
		   enc3 = ((chr2 & 15) << 2) | (chr3 >> 6); 
		   enc4 = chr3 & 63; 
		   if (isNaN(chr2)) { 
		    enc3 = enc4 = 64; 
		   } else if (isNaN(chr3)) { 
		    enc4 = 64; 
		   } 
		   output = output + 
		   _keyStr.charAt(enc1) + _keyStr.charAt(enc2) + 
		   _keyStr.charAt(enc3) + _keyStr.charAt(enc4); 
		  } 
		  return output; 
		 } 
		  
		 // public method for decoding 
		 this.decode = function (input) { 
		  var output = ""; 
		  var chr1, chr2, chr3; 
		  var enc1, enc2, enc3, enc4; 
		  var i = 0; 
		  input = input.replace(/[^A-Za-z0-9\+\/\=]/g, ""); 
		  while (i < input.length) { 
		   enc1 = _keyStr.indexOf(input.charAt(i++)); 
		   enc2 = _keyStr.indexOf(input.charAt(i++)); 
		   enc3 = _keyStr.indexOf(input.charAt(i++)); 
		   enc4 = _keyStr.indexOf(input.charAt(i++)); 
		   chr1 = (enc1 << 2) | (enc2 >> 4); 
		   chr2 = ((enc2 & 15) << 4) | (enc3 >> 2); 
		   chr3 = ((enc3 & 3) << 6) | enc4; 
		   output = output + String.fromCharCode(chr1); 
		   if (enc3 != 64) { 
		    output = output + String.fromCharCode(chr2); 
		   } 
		   if (enc4 != 64) { 
		    output = output + String.fromCharCode(chr3); 
		   } 
		  } 
		  output = _utf8_decode(output); 
		  return output; 
		 } 
		  
		 // private method for UTF-8 encoding 
		 _utf8_encode = function (string) { 
		  string = string.replace(/\r\n/g,"\n"); 
		  var utftext = ""; 
		  for (var n = 0; n < string.length; n++) { 
		   var c = string.charCodeAt(n); 
		   if (c < 128) { 
		    utftext += String.fromCharCode(c); 
		   } else if((c > 127) && (c < 2048)) { 
		    utftext += String.fromCharCode((c >> 6) | 192); 
		    utftext += String.fromCharCode((c & 63) | 128); 
		   } else { 
		    utftext += String.fromCharCode((c >> 12) | 224); 
		    utftext += String.fromCharCode(((c >> 6) & 63) | 128); 
		    utftext += String.fromCharCode((c & 63) | 128); 
		   } 
		  
		  } 
		  return utftext; 
		 } 
		  
		 // private method for UTF-8 decoding 
		 _utf8_decode = function (utftext) { 
		  var string = ""; 
		  var i = 0; 
		  var c = c1 = c2 = 0; 
		  while ( i < utftext.length ) { 
		   c = utftext.charCodeAt(i); 
		   if (c < 128) { 
		    string += String.fromCharCode(c); 
		    i++; 
		   } else if((c > 191) && (c < 224)) { 
		    c2 = utftext.charCodeAt(i+1); 
		    string += String.fromCharCode(((c & 31) << 6) | (c2 & 63)); 
		    i += 2; 
		   } else { 
		    c2 = utftext.charCodeAt(i+1); 
		    c3 = utftext.charCodeAt(i+2); 
		    string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63)); 
		    i += 3; 
		   } 
		  } 
		  return string; 
		 } 
		} 
	</script>
</html>