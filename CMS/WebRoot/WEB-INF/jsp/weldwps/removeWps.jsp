<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	response.setHeader("Cache-Control", "no-store");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

	<title>&nbsp;</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="resources/css/base.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="resources/themes/default/easyui.css" />
	<script type="text/javascript" src="resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/js/weldwps/showWps.js"></script>
	
</head>

<body id="wpsbody">
   	<!--startprint-->
	<div>
		<p align="center">
			<span style='font-size:18.0pt;'>考试用焊接工艺规程</span>
		</p>
		<table align="center" style='border-collapse:collapse;border:none;'>
 			<thead> 
				<tr>
					<td width=417 colspan=16 class="border_bottom">
						&nbsp;
					</td>
					<td width=152 colspan=9 class="border_bottom">
						<span>编号：</span></span class="fontstyle">${wps.fwpsnum }</span>
						<span>
							<input type="hidden" value="${wps.fwpsnum }" id="wpsnum"/>
							<input type="hidden" value="${wps.fid }" id="fid"/>
						</span>
					</td>
					<td width=103 colspan=2 class="border_bottom">
						<span>版本：</span></span class="fontstyle">${wps.fversions }</span>
					</td>
				</tr>
			</thead>
			<tr>
				<td width=171 colspan=7 class="border_left">
					<span>焊工考试项目代号</span>
				</td>
				<td width=500 colspan=20 class="border_right">
					<span class="fontstyle">${wps.fproject_code }</span>
				</td>
			</tr>
			<tr>
				<td width=67 rowspan=3 class="border_left">
					<span>依据工艺评定</span>
				</td>
				<td width=104 colspan=6 class="border_right">
					<span>报告编号</span>
				</td>
				<td width=284 colspan=12 class="border_right">
					<span class="fontstyle">${wps.freport_number }</span>
				</td>
				<td width=104 colspan=5 class="border_right">
					<span>自动化程度</span>
				</td>
				<td width=113 colspan=3 class="border_right">
					<span class="fontstyle">${wps.fdegree }</span>
				</td>
			</tr>
			<tr>
				<td width=124 colspan=6 class="border_left">
					<span>评定标准</span>
				</td>
				<td width=284 colspan=12 class="border_right">
					<span class="fontstyle">${wps.fevaluation_standard }</span>
				</td>
				<td width=104 colspan=5 class="border_right">
					<span>稳压系统</span>
				</td>
				<td width=113 colspan=3 class="border_right">
					<span class="fontstyle">${wps.fstabilivolt_system }</span>
				</td>
			</tr>
			<tr style='height:16.15pt'>
				<td width=124 colspan=6 class="border_left">
					<span>有效期</span>
				</td>
				<td width=284 colspan=12 class="border_right">
					<span class="fontstyle">${wps.fvalidity }</span>
				</td>
				<td width=104 colspan=5 class="border_right">
					<span>自动跟踪系统</span>
				</td>
				<td width=113 colspan=3 class="border_right">
					<span class="fontstyle">${wps.fautomatic }</span>
				</td>
			</tr>
			<tr style='height:16.55pt'>
				<td width=313 colspan=13 class="border_left">
					<span>焊接接头</span><span>（焊接1副试件）</span>
				</td>
				<td width=358 colspan=14 valign=top style="border-right:1px solid #000;">
					<span>焊接接头简图</span><br/>
					<span class="fontstyle">(${wps.fimages_desc })</span>
				</td>
			</tr>
			<tr style='height:6.95pt'>
				<td width=162 colspan=6 class="border_left">
					<span>坡口形式</span>
				</td>
				<td width=151 colspan=7 class="border_right">
					<span class="fontstyle">${wps.fgroove_type }</span>
				</td>
				<td width=358 colspan=14 rowspan=5 class="border_right">
					<input type="hidden" id="imgurl" value="${wps.fimages_url }"/>
					<span><img width=307 height=153 id="image" src="${wps.fimages_url }"></span>
				</td>
			</tr>
			<tr>
				<td width=162 colspan=6 class="border_left">
					<span>衬垫（材料）</span>
				</td>
				<td width=151 colspan=7 class="border_right">
					<span class="fontstyle">${wps.fmaterials }</span>
				</td>
			</tr>
			<tr>
				<td width=162 colspan=6 class="border_left">
					<span>焊缝金属厚度（mm）</span>
				</td>
				<td width=151 colspan=7 class="border_right">
					<span class="fontstyle">HWS:${wps.fthickness1 };HD:${wps.fthickness2 }</span>
				</td>
			</tr>
			<tr>
				<td width=162 colspan=6 class="border_left">
					<span>管子直径（mm）</span>
				</td>
				<td width=151 colspan=7 class="border_right">
					<span class="fontstyle">${wps.fdiameter }</span>
				</td>
			</tr>
			<tr>
				<td width=162 colspan=6 class="border_left">
					<span>其他</span>
				</td>
				<td width=151 colspan=7 class="border_right">
					<span class="fontstyle">${wps.feles1 }</span>
				</td>
			</tr>
			<tr>
				<td width=313 colspan=13 class="border_left">
					<span>母材</span>
				</td>
				<td width=368 colspan=14 class="border_right">
					<span>填充金属</span>
				</td>
			</tr>
			<tr style='height:16.45pt'>
				<td width=105 colspan=3 class="border_left">
					<span>类别号</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span class="fontstyle">${wps.fcategory}</span>
				</td>
				<td width=161 colspan=8 class="border_right">
					<span>焊材类型</span><br/>
					<span>（焊条、焊丝、焊带等）</span>
				</td>
				<td width=198 colspan=6 class="border_right">
					<span class="fontstyle">${wps.fmaterials_type }</span>
				</td>
			</tr>
			<tr>
				<td width=105 colspan=3 class="border_left">
					<span>牌号</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span>${wps.fshop_sign }</span>
				</td>
				<td width=161 colspan=8 class="border_right">
  					<span>焊材型（牌）号/规格</span>
				</td>
				<td width=198 colspan=6 class="border_right">
					<span class="fontstyle">${wps.fmaterials_number1 }${wps.fmaterials_specification1 }</span>
					<span class="fontstyle">${wps.fmaterials_number2 }${wps.fmaterials_specification2 }</span>
				</td>
			</tr>
			<tr>
				<td width=105 colspan=3 class="border_left">
					<span>规格</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span class="fontstyle">${wps.fspecification }</span>
				</td>
				<td width=161 colspan=8 class="border_right">
					<span>焊剂型（牌）号</span>
				</td>
				<td width=198 colspan=6 class="border_right">
					<span class="fontstyle">${wps.fsoldering_number }</span>
				</td>
			</tr>
			<tr>
				<td width=313 colspan=13 class="border_left">
					<span>焊接位置</span>
				</td>
				<td width=358 colspan=14 class="border_right">
					<span>保护气体类型/混合比/流量</span>
				</td>
			</tr>
			<tr>
				<td width=105 colspan=3 class="border_left">
					<span>焊接位置</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span class="fontstyle">${wps.fposition }</span>
				</td>
				<td width=94 colspan=2 class="border_right">
					<span>正面</span>
				</td>
				<td width=264 colspan=12 class="border_right">
					<span class="fontstyle">${wps.ffront1 }&nbsp;&nbsp;&nbsp;&nbsp;${wps.ffront2 }</span>
				</td>
			</tr>
			<tr>
				<td width=105 colspan=3 class="border_left">
					<span>焊接方向</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span class="fontstyle">${wps.fdirection }</span>
				</td>
				<td width=94 colspan=2 class="border_right">
					<span>背面</span>
				</td>
				<td width=264 colspan=12 class="border_right">
					<span class="fontstyle">${wps.freverse1 }&nbsp;</span><span class="fontstyle">${wps.freverse2 }&nbsp;</span><span class="fontstyle">${wps.freverse3 }</span>
				</td>
			</tr>
			<tr style='height:26.25pt' class="border_left">
				<td width=105 colspan=3 class="border_right">
					<span>其他</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span class="fontstyle">${wps.felse2 }</span>
				</td>
				<td width=94 colspan=2 class="border_right">
					<span>尾部</span>
				</td>
				<td width=264 colspan=12 class="border_right">
					<span class="fontstyle">${wps.ftail }</span>
				</td>
			</tr>
			<tr>
				<td width=303 colspan=13 class="border_left">
					<span>预热和层间温度</span>
				</td>
				<td width=358 colspan=14 class="border_right">
					<span>焊后热处理</span>
				</td>
			</tr>
			<tr>
				<td width=105 colspan=3 class="border_left">
					<span>预热温度</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span class="fontstyle">${wps.fpreheating_temperature }</span>
				</td>
				<td width=94 colspan=2 class="border_right">
					<span>温度范围</span>
				</td>
				<td width=264 colspan=12 class="border_right">
					<span class="fontstyle">${wps.ftemperature_range }</span>
				</td>
			</tr>
			<tr>
				<td width=105 colspan=3 class="border_left">
					<span>层间温度</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span class="fontstyle">${wps.ftemperature }</span>
				</td>
				<td width=94 colspan=2 class="border_right">
					<span>保温时间</span>
				</td>
				<td width=264 colspan=12 class="border_right">
					<span class="fontstyle">${wps.fsoaking_time }</span>
				</td>
			</tr>
			<tr>
				<td width=105 colspan=3 class="border_left">
					<span>预热方式</span>
				</td>
				<td width=208 colspan=10 class="border_right">
					<span class="fontstyle">${wps.fPreheat_way }</span>
				</td>
				<td width=94 colspan=2 class="border_right">
					<span>其他</span>
				</td>
				<td width=264 colspan=12 class="border_right">
					<span class="fontstyle">${wps.feles3 }</span>
				</td>
			</tr>
			<tr>
				<td width=671 colspan=27 class="border_left">
					<span>焊接技术</span>
				</td>
			</tr>
			<tr style='height:16.45pt'>
				<td width=131 colspan=5 class="border_left">
					<span>线能量范围</span>
				</td>
				<td width=540 colspan=22 class="border_right">
					<span class="fontstyle">${wps.fscope }</span>
				</td>
			</tr>
			<tr>
				<td width=131 colspan=5 class="border_left">
					<span>喷嘴尺寸</span>
				</td>
				<td width=177 colspan=7 class="border_right">
					<span class="fontstyle">${wps.fnozzle }</span>
				</td>
				<td width=131 colspan=6 class="border_right">
					<span>导电嘴与工件距离</span>
				</td>
				<td width=233 colspan=9 class="border_right">
					<span class="fontstyle">${wps.fdistance }</span>
				</td>
			</tr>
			<tr>
				<td width=131 colspan=5 class="border_left">
					<span>清根方法</span>
				</td>
				<td width=177 colspan=7 class="border_right">
					<span class="fontstyle">${wps.fback_chipping }</span>
				</td>
				<td width=131 colspan=6 class="border_right">
					<span>焊缝层数范围</span>
				</td>
				<td width=233 colspan=9 class="border_right">
					<span class="fontstyle">HWS:${wps.flayer_scope1 }层;</span>
					<span class="fontstyle">HD:${wps.flayer_scope2 }层</span>
				</td>
			</tr>
			<tr>
				<td width=131 colspan=5 class="border_left">
					<span>钨极类型/尺寸</span>
				</td>
				<td width=177 colspan=7 class="border_right">
					<span class="fontstyle">${wps.ftungsten_electrode }</span>
				</td>
				<td width=131 colspan=6 class="border_right">
					<span>熔滴过渡方式</span>
				</td>
				<td width=233 colspan=9 class="border_right">
					<span class="fontstyle">${wps.ftransient_mode }</span>
				</td>
			</tr>
			<tr>
				<td width=237 colspan=9 class="border_left">
					<span>直向焊、摆动焊及摆动方法</span>
				</td>
				<td width=434 colspan=18 class="border_right">
					<span class="fontstyle">${wps.fmethod1 }</span>
				</td>
			</tr>
			<tr>
				<td width=237 colspan=9 class="border_left">
					<span>背面、打底及中间焊道清理方法</span>
				</td>
				<td width=434 colspan=18 class="border_right">
					<span class="fontstyle">${wps.fmethod2 }</span>
				</td>
			</tr>
			<tr>
				<td width=671 colspan=27 class="border_left">
					<span>焊接参数</span>
				</td>
			</tr>
			<tr style='height:12.0pt'>
				<td width=115 colspan=4 rowspan=2 class="border_left">
					<span>焊层</span>
				</td>
				<td width=85 colspan=4 rowspan=2 class="border_right">
					<span>焊接方法</span>
				</td>
				<td width=170 colspan=6 class="border_right">
					<span>焊材</span>
				</td>
				<td width=142 colspan=8 class="border_right">
					<span>焊接电流</span>
				</td>
				<td width=76 colspan=4 rowspan=2 class="border_right">
					<span>电压范围</span>
					<span>（V）</span>
				</td>
				<td width=84 rowspan=2 class="border_right">
					<span>焊接速度（mm/min）</span>
				</td>
			</tr>
			<tr style='height:12.95pt'>
				<td width=105 colspan=2 class="border_left">
					<span>型（牌）号</span>
				</td>
				<td width=85 colspan=4 class="border_right">
					<span>规格（mm）</span>
				</td>
				<td width=57 colspan=3 class="border_right">
					<span>极性</span>
				</td>
				<td width=85 colspan=5 class="border_right">
					<span>范围（>A）
				</td>
			</tr>
			<!-- 循环显示焊接参数 -->
			<c:forEach items="${list}" var="w">
				<tr>
					<td width=115 colspan=4 class="border_left">
						<c:if test="${w.fweld_prechannel==1}">
							<span class="fontstyle" style='line-height:150%'>打底</span>
						</c:if>
						<c:if test="${w.fweld_prechannel==2}">
							<span class="fontstyle" style='line-height:150%'>填充</span>
						</c:if>
						<c:if test="${w.fweld_prechannel==3}">
							<span class="fontstyle" style='line-height:150%'>盖面</span>
						</c:if>
						<c:if test="${w.fweld_prechannel==4}">
							<span class="fontstyle" style='line-height:150%'>背面封底</span>
						</c:if>
						<c:if test="${w.fweld_prechannel==5}">
							<span class="fontstyle" style='line-height:150%'>第一层</span>
						</c:if>
					</td>
					<td width=85 colspan=4 class="border_right">
						<span class="fontstyle">${w.fwelding_method }</span>
					</td>
					<td width=85 colspan=2 class="border_right">
						<span class="fontstyle">${w.ftype }</span>
					</td>
					<td width=85 colspan=4 class="border_right">
						<span class="fontstyle">${w.fchildren_specification }</span>
					</td>
					<td width=57 colspan=3 class="border_right">
						<span class="fontstyle">${w.fpolarity }</span>
					</td>
					<td width=85 colspan=5 class="border_right">
						<span class="fontstyle">${w.fweld_i_min }~${w.fweld_i_max }</span>
					</td>
					<td width=76 colspan=4 class="border_right">
						<span class="fontstyle">${w.fweld_v_min }~${w.fweld_v_max }</span>
					</td>
					<td width=84 class="border_right">
						<span class="fontstyle">${w.fwelding_speed }</span>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td width=115 colspan=4 class="border_left">
					<span>&nbsp;</span>
				</td>
				<td width=85 colspan=4 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=85 colspan=2 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=85 colspan=4 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=57 colspan=3 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=85 colspan=5 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=76 colspan=4 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=84 class="border_right">
					<span>&nbsp;</span>
				</td>
			</tr>
			<tr>
				<td width=115 colspan=4 class="border_left">
					<span>&nbsp;</span>
				</td>
				<td width=85 colspan=4 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=85 colspan=2 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=85 colspan=4 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=57 colspan=3 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=85 colspan=5 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=76 colspan=4 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=84 class="border_right">
					<span>&nbsp;</span>
				</td>
			</tr>
			<tr>
				<td width=671 colspan=27 class="border_left">
					<span>施焊操作要领</span>
				</td>
			</tr>
			<tr>
				<td width=671 colspan=27 class="border_left" style="text-align:left;font-size:11.0pt;">
					<span>1. 考试用试件的坡口表面和坡口两侧各范围内应当清理干净，去除铁屑、氧 化皮、油、锈和污垢等杂物；</span><br/>
					<span>2. 焊条应按规定要求放入保温桶，逐根随取随用；</span><br/>
					<span>3. 严禁在坡口外引弧、熄弧；</span><br/>
					<span>4. 焊工所有的考试试件，第一层焊缝中至少应当有一个停弧再焊接头；</span><br/>
					<span>5. 在坡口内点固，点固焊后，应仔细检查各焊点的质量；</span><br/>
					<span>6. 焊完后应清理焊缝药皮、焊渣及焊缝周围的飞溅，保留焊缝原始表面，严禁打磨；</span><br/>
					<span>7. 环境温度若低于-10℃，则不允许施焊，被焊试件温度应至少保持在+5℃以上，且焊缝在焊后须缓慢冷却，以避免因内应力引起裂纹。</span>
				</td>
			</tr>
			<tr>
				<td width=77 colspan=2 class="border_left">
					<span>编制</span>
				</td>
				<td width=161 colspan=7 class="border_right">
					<span>${username }</span>
				</td>
				<td width=66 colspan=2 class="border_right">
					<span>审核</span>
					
				</td>
				<td width=161 colspan=9 class="border_right">
					<span>&nbsp;</span>
					
				</td>
				<td width=66 colspan=3 class="border_right">
					<span>批准</span>
					
				</td>
				<td width=141 colspan=4 class="border_right">
					<span>&nbsp;</span>
				</td>
			</tr>
			<tr>
				<td width=77 colspan=2 class="border_left">
					<span>日期</span>
				</td>
				<td width=161 colspan=7 class="border_right">
					<span>&nbsp;</span>
				</td>
				<td width=66 colspan=2 class="border_right">
					<span>日期</span>
				</td>
				<td width=161 colspan=9 class="border_right">
					<span>&nbsp;</span>
					
				</td>
				<td width=86 colspan=3 class="border_right">
					<span>日期</span>
				</td>
				<td width=141 colspan=4 class="border_right">
					<span>&nbsp;</span>
				</td>
			</tr>
			<tr>
				<td width=237 colspan=9 class="border_left">
					<span>编制单位名称</span>
				</td>
				<td width=434 colspan=18 class="border_right">
					<b><span>${wps.fitemname }<input type="hidden" id="itemid" value="${wps.fitemid }"></span></b>
				</td>
			</tr>
			<tr height=0>
				<td width=67 style='border:none'></td>
				<td width=9 style='border:none'></td>
				<td width=28 style='border:none'></td>
				<td width=9 style='border:none'></td>
				<td width=17 style='border:none'></td>
				<td width=30 style='border:none'></td>
				<td width=9 style='border:none'></td>
				<td width=28 style='border:none'></td>
				<td width=38 style='border:none'></td>
				<td width=47 style='border:none'></td>
				<td width=19 style='border:none'></td>
				<td width=5 style='border:none'></td>
				<td width=5 style='border:none'></td>
				<td width=57 style='border:none'></td>
				<td width=38 style='border:none'></td>
				<td width=9 style='border:none'></td>
				<td width=9 style='border:none'></td>
				<td width=12 style='border:none'></td>
				<td width=16 style='border:none'></td>
				<td width=9 style='border:none'></td>
				<td width=9 style='border:none'></td>
				<td width=38 style='border:none'></td>
				<td width=19 style='border:none'></td>
				<td width=28 style='border:none'></td>
				<td width=10 style='border:none'></td>
				<td width=18 style='border:none'></td>
				<td width=84 style='border:none'></td>
			</tr>
		</table>
		<div id="dlg-buttons" style="text-align:center;margin-bottom: 10px;margin-top: -20px;">
			<a href="javascript:removeWps()" class="easyui-linkbutton" iconCls="icon-ok">删除</a>
			<a href="wps/goWps" style="margin-left:100px;" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
	</div>
	<!--endprint-->
</body>
</html>
