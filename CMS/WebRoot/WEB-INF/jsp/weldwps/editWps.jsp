<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<meta http-equiv="Content-Type"content="multipart/form-data; charset=utf-8" />
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
	<script type="text/javascript" src="resources/js/easyui-extend-check.js"></script>
	<script type="text/javascript" src="resources/js/weldwps/addeditWps.js"></script>
</head>

<body id="wpsbody">
	<div>
		<p align="center">
			<span style='font-size:18.0pt;'>考试用焊接工艺规程</span>
			<input type="hidden" value="2" id="flag"/>
		</p>
		<form id="fm" class="easyui-form" method="post" data-options="novalidate:true">
			<table id="tb" align="center" style='border-collapse:collapse;border:none;'>
	 			<thead> 
					<tr>
						<td width=231 colspan=10 class="border_bottom">
							<input type="hidden" value="${wps.fid }" id="fid" name="fid"/>
						</td>
						<td width=220 colspan=11 class="border_bottom">
							<span>编号：</span>
							</span class="fontstyle"><input class="easyui-textbox" value="${wps.fwpsnum }" id="fwpsnum" name="fwpsnum" readonly="readonly"></span>
						</td>
						<td width=220 colspan=6 class="border_bottom">
							<span>版本：</span>
							</span class="fontstyle"><input class="easyui-numberbox" value="${wps.fversions }" id="fversions" name="fversions" data-options="required:true"></span>
						</td>
					</tr>
				</thead>
				<tr>
					<td width=171 colspan=7 class="border_left">
						<span>焊工考试项目代号</span>
					</td>
					<td width=500 colspan=20 class="border_right">
						<span class="fontstyle">
							<textarea rows="2" cols="70" id="fproject_code" name="fproject_code" class="easyui-validatebox" data-options="required:true">${wps.fproject_code }</textarea>
						</span>
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
						<span class="fontstyle"><input value="${wps.freport_number }" class="easyui-textbox" id="freport_number" name="freport_number" data-options="required:true"></span>
					</td>
					<td width=104 colspan=5 class="border_right">
						<span>自动化程度</span>
					</td>
					<td width=113 colspan=3 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="degreeText" value="${wps.fdegree }"/>
							<select class="easyui-combobox" id="fdegree" name="fdegree" style="width:100px;" data-options="required:true,editable:false">
								<option value="手工">手工</option>
								<option value="自动">自动</option>
								<option value="半自动">半自动</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=124 colspan=6 class="border_left">
						<span>评定标准</span>
					</td>
					<td width=284 colspan=12 class="border_right">
						<span class="fontstyle"><input value="${wps.fevaluation_standard }" class="easyui-textbox" id="fevaluation_standard" name="fevaluation_standard" data-options="required:true"></span>
					</td>
					<td width=104 colspan=5 class="border_right">
						<span>稳压系统</span>
					</td>
					<td width=113 colspan=3 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="systemText" value="${wps.fstabilivolt_system }"/>
							<select class="easyui-combobox" id="fstabilivolt_system" name="fstabilivolt_system" style="width:100px;" data-options="required:true,editable:false">
								<option value="有">有</option>
								<option value="无">无</option>
							</select>	
						</span>
					</td>
				</tr>
				<tr style='height:16.15pt'>
					<td width=124 colspan=6 class="border_left">
						<span>有效期</span>
					</td>
					<td width=284 colspan=12 class="border_right">
						<span class="fontstyle"><input class="easyui-textbox" value="${wps.fvalidity }" id="fvalidity" name="fvalidity" data-options="required:true"></span>
					</td>
					<td width=104 colspan=5 class="border_right">
						<span>自动跟踪系统</span>
					</td>
					<td width=113 colspan=3 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="autoText" value="${wps.fautomatic }"/>
							<select class="easyui-combobox" value="${wps.fautomatic }" id="fautomatic" name="fautomatic" style="width:100px;" data-options="required:true,editable:false">
								<option value="有">有</option>
								<option value="无">无</option>
							</select>	
						</span>
					</td>
				</tr>
				<tr style='height:16.55pt'>
					<td width=313 colspan=13 class="border_left">
						<span>焊接接头</span><span>（焊接1副试件）</span>
					</td>
					<td width=358 colspan=14 valign=top style="border-right:1px solid #000;">
						<span>焊接接头简图</span><br/>
						<span class="fontstyle">(<input class="easyui-textbox" value="${wps.fimages_desc }" id="fimages_desc" name="fimages_desc">)</span>
					</td>
				</tr>
				<tr style='height:6.95pt'>
					<td width=162 colspan=6 class="border_left">
						<span>坡口形式</span>
					</td>
					<td width=151 colspan=7 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="grooveText" value="${wps.fgroove_type }"/>
							<select class="easyui-combobox" id="fgroove_type" name="fgroove_type" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="V">V</option>
								<option value="X">X</option>
								<option value="I">I</option>
							</select>	
						</span>
					</td>
					<td width=358 colspan=14 rowspan=5 class="border_right">
						<input type="hidden" id="oldimgurl" value="${wps.fimages_url }">
						<img id="image" src="${wps.fimages_url }" style="width:358px; height:130px;"/>
				        <span><input type="file" name="file" id="file" onchange="selectImage(this);"></span>
				        <input type="button" value="上传" onclick="importWeldingMachine()" class="upButton"/>
					</td>
				</tr>
				<tr>
					<td width=162 colspan=6 class="border_left">
						<span>衬垫（材料）</span>
					</td>
					<td width=151 colspan=7 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="materialsText" value="${wps.fmaterials }"/>
							<select class="easyui-combobox" id="fmaterials" name="fmaterials" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="无（双面焊）">无（双面焊）</option>
								<option value="HWS:无,HD:焊缝金属">HWS:无,HD:焊缝金属</option>
								<option value="Q345B">Q345B</option>
								<option value="0Cr18Ni9">0Cr18Ni9</option>
								<option value="焊缝金属">焊缝金属</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=162 colspan=6 class="border_left">
						<span>焊缝金属厚度（mm）</span>
					</td>
					<td width=151 colspan=7 class="border_right">
						<span class="fontstyle">
							HWS:<input class="easyui-numberbox" value="${wps.fthickness1 }" min="0.001" value="1" precision="3" id="fthickness1" name="fthickness1" data-options="iconCls:'icon-search',iconWidth:50,required:true">mm
							HD:<input class="easyui-numberbox" value="${wps.fthickness2 }" min="0.001" value="1" precision="3" id="fthickness2" name="fthickness2" data-options="iconCls:'icon-search',iconWidth:50,required:true">mm
						</span>
					</td>
				</tr>
				<tr>
					<td width=162 colspan=6 class="border_left">
						<span>管子直径（mm）</span>
					</td>
					<td width=151 colspan=7 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="dialmeterText" value="${wps.fdiameter }"/>
							<select class="easyui-combobox" id="fdiameter" name="fdiameter" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="6">6</option>
								<option value="6.35">6.35</option>
								<option value="9/53">9.53</option>
								<option value="13.7">13.7</option>
								<option value="14">14</option>
								<option value="21.3">21.3</option>
								<option value="48">48</option>
								<option value="57">57</option>
								<option value="60/34">60/34</option>
								<option value="88.9/48.3">88.9/48.3</option>
								<option value="88.9/73">88.9/73</option
								<option value="140">140</option>
								<option value="159">159</option>
								<option value="219">219</option>>
								<option value="219.1">219.1</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=162 colspan=6 class="border_left">
						<span>其他</span>
					</td>
					<td width=151 colspan=7 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="else1Text" value="${wps.feles1 }"/>
							<select class="easyui-combobox" id="feles1" name="feles1" data-options="required:true,editable:false">
								<option value="/">/</option>
								<option value="挡板高h30">挡板高h30</option>
							</select>
						</span>
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
						<span class="fontstyle">
							<input type="hidden" id="categoryText" value="${wps.fcategory }"/>
							<select class="easyui-combobox" id="fcategory" name="fcategory" data-options="required:true,editable:false">
								<option value="I">I</option>
								<option value="II">II</option>
								<option value="III">III</option>
								<option value="IV">IV</option>
								<option value="V">V</option>
								<option value="VI">VI</option>
								<option value="VII">VII</option>
								<option value="SX1">SX1</option>
							</select>
						</span>
					</td>
					<td width=161 colspan=8 class="border_right">
						<span>焊材类型</span><br/>
						<span>（焊条、焊丝、焊带等）</span>
					</td>
					<td width=198 colspan=6 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="materialxtypeText" value="${wps.fmaterials_type }"/>
							<select class="easyui-combobox" id="fmaterials_type" name="fmaterials_type" data-options="required:true,editable:false">
								<option value="焊条">焊条</option>
								<option value="焊丝">焊丝</option>
								<option value="焊带">焊带</option>
								<option value="焊丝、焊条">焊丝、焊条</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=105 colspan=3 class="border_left">
						<span>牌号</span>
					</td>
					<td width=208 colspan=10 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="shopsignText" value="${wps.fshop_sign }"/>
							<select class="easyui-combobox" id="fshop_sign" name="fshop_sign" data-options="required:true,editable:false">
								<option value="Q345B">Q345B</option>
								<option value="0Cr18Ni9">0Cr18Ni9</option>
								<option value="304L">304L</option>
								<option value="20">20</option>
								<option value="Q235B">Q235B</option>
								<option value="Q390B">Q390B</option>
								<option value="304">304</option>
								<option value="06Cr19Ni10">06Cr19Ni10</option>
								<option value="304/0Cr18Ni9">304/0Cr18Ni9</option>
								<option value="TP304L">TP304L</option>
								<option value="TP304L/F304">TP304L/F304</option>
							</select>
						</span>
					</td>
					<td width=161 colspan=8 class="border_right">
	  					<span>焊材型（牌）号/规格</span>
					</td>
					<td width=198 colspan=6 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="materialsnumber1Text" value="${wps.fmaterials_number1 }"/>
							<select class="easyui-combobox" id="fmaterials_number1" name="fmaterials_number1" data-options="required:true,editable:false">
								<option value="E7015(AWS A5.1)">E7015(AWS A5.1)</option>
								<option value="E308-16(AWS A5.4)">E308-16(AWS A5.4)</option>
								<option value="ER70S-6(AWS A5.18)">ER70S-6(AWS A5.18)</option>
								<option value="ER308L(AWS A5.9)">ER308L(AWS A5.9)</option>
								<option value="H08MnA">H08MnA</option>
								<option value="E71T-1C(AWS A5.20)">E71T-1C(AWS A5.20)</option>
								<option value="E308LT1-1(AWS A5.22)">E308LT1-1(AWS A5.22)</option>
								<option value="ERCoCr-E(AWS A5.21)">ERCoCr-E(AWS A5.21)</option>
								<option value="ECoCr-E(AWS A5.13)">ECoCr-E(AWS A5.13)</option>
								<option value="ER316L(AWS A5.9)">ER316L(AWS A5.9)</option>
							</select>
						</span>
						<span class="fontstyle">
							<input type="hidden" id="materialsspecification1Text" value="${wps.fmaterials_specification1 }"/>
							<select class="easyui-combobox" id="fmaterials_specification1" name="fmaterials_specification1" style="width:50px;" data-options="required:true,editable:false">
								<option value="Φ1.2">Φ1.2</option>
								<option value="Φ1.6">Φ1.6</option>
								<option value="Φ2.0">Φ2.0</option>
								<option value="Φ2.5">Φ2.5</option>
								<option value="Φ3.2">Φ3.2</option>
								<option value="Φ4.0">Φ4.0</option>
								<option value="Φ5.0">Φ5.0</option>
								<option value="Φ1.0">Φ1.0</option>
							</select>
						</span>
						<span class="fontstyle">
							<input type="hidden" id="materialsnumber2Text" value="${wps.fmaterials_number2 }"/>
							<select class="easyui-combobox" id="fmaterials_number2" name="fmaterials_number2" data-options="required:true,editable:false">
								<option value="E7015(AWS A5.1)">E7015(AWS A5.1)</option>
								<option value="E308-16(AWS A5.4)">E308-16(AWS A5.4)</option>
								<option value="ER70S-6(AWS A5.18)">ER70S-6(AWS A5.18)</option>
								<option value="ER308L(AWS A5.9)">ER308L(AWS A5.9)</option>
								<option value="H08MnA">H08MnA</option>
								<option value="E71T-1C(AWS A5.20)">E71T-1C(AWS A5.20)</option>
								<option value="E308LT1-1(AWS A5.22)">E308LT1-1(AWS A5.22)</option>
								<option value="ERCoCr-E(AWS A5.21)">ERCoCr-E(AWS A5.21)</option>
								<option value="ECoCr-E(AWS A5.13)">ECoCr-E(AWS A5.13)</option>
								<option value="ER316L(AWS A5.9)">ER316L(AWS A5.9)</option>
							</select>
						</span>
						<span class="fontstyle">
							<input type="hidden" id="materialsspecification2Text" value="${wps.fmaterials_specification2 }"/>
							<select class="easyui-combobox" id="fmaterials_specification2" style="width:50px;" name="fmaterials_specification2" data-options="required:true,editable:false">
								<option value="Φ1.2">Φ1.2</option>
								<option value="Φ1.6">Φ1.6</option>
								<option value="Φ2.0">Φ2.0</option>
								<option value="Φ2.5">Φ2.5</option>
								<option value="Φ3.2">Φ3.2</option>
								<option value="Φ4.0">Φ4.0</option>
								<option value="Φ5.0">Φ5.0</option>
								<option value="Φ1.0">Φ1.0</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=105 colspan=3 class="border_left">
						<span>规格</span>
					</td>
					<td width=208 colspan=10 class="border_right">
						<input type="hidden" id="specificationText" value="${wps.fspecification }"/>
						<select class="easyui-combobox" id="fspecification" name="fspecification" data-options="required:true,editable:false">
							<option value="δ22">δ22</option>
							<option value="Φ48×8">Φ48×8</option>
							<option value="δ10">δ10</option>
							<option value="δ26">δ26</option>
							<option value="δ6">δ6</option>
							<option value="δ0.8">δ0.8</option>
							<option value="δ1.5">δ1.5</option>
							<option value="Φ6×1/S12">Φ6×1/S12</option>
							<option value="Φ14×2/S27">Φ14×2/S27</option>
							<option value="Φ13.7×2.65">Φ13.7×2.65</option>
							<option value="Φ6.35×1.65">Φ6.35×1.65</option>
							<option value="Φ57×6">Φ57×6</option>
							<option value="Φ21.3×7.47">Φ21.3×7.47</option>
							<option value="Φ88.9×7.5/Φ48.3×8">Φ88.9×7.5/Φ48.3×8</option>
							<option value="Φ88.9×6.7/Φ73×10">Φ88.9×6.7/Φ73×10</option>
							<option value="Φ60×3/Φ34×3">Φ60×3/Φ34×3</option>
							<option value="δ12">δ12</option>
							<option value="δ14">δ14</option>
							<option value="Φ219.1×22.23">Φ219.1×22.23</option>
						</select>
					</td>
					<td width=161 colspan=8 class="border_right">
						<span>焊剂型（牌）号</span>
					</td>
					<td width=198 colspan=6 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="solderingText" value="${wps.fsoldering_number }"/>
							<select class="easyui-combobox" id="fsoldering_number" name="fsoldering_number" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="HJ431">HJ431</option>
							</select>
						</span>
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
						<span class="fontstyle">
							<input type="hidden" id="positionText" value="${wps.fposition }"/>
							<select class="easyui-combobox" id="fposition" name="fposition" data-options="required:true,editable:false">
								<option value="PA">PA</option>
								<option value="PB">PB</option>
								<option value="PC">PC</option>
								<option value="PD">PD</option>
								<option value="PE">PE</option>
								<option value="PF">PF</option>
								<option value="PG">PG</option>
								<option value="H-L045">H-L045</option>
								<option value="J-L045">J-L045</option>
								<option value="H-L045和J-L045">H-L045和J-L045</option>
							</select>
						</span>
					</td>
					<td width=94 colspan=2 class="border_right">
						<span>正面</span>
					</td>
					<td width=264 colspan=12 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="fornt1Text" value="${wps.ffront1 }"/>
							<select class="easyui-combobox" id="ffront1" name="ffront1" style="width:120px;" data-options="required:true,editable:false">
								<option value="Ar(99.99%)">Ar(99.99%)</option>
								<option value="95%Ar+5%CO2">95%Ar+5%CO2</option>
								<option value="CO2(99.8%)">CO2(99.8%)</option>
							</select>
						</span>
						<span class="fontstyle">
							<input type="hidden" id="fornt2Text" value="${wps.ffront2 }"/>
							<select class="easyui-combobox" id="ffront2" name="ffront2" style="width:120px;" data-options="required:true,editable:false">
								<option value="15-20L/min">15-20L/min</option>
								<option value="12-15L/min">12-15L/min</option>
								<option value="10-20L/min">10-20L/min</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=105 colspan=3 class="border_left">
						<span>焊接方向</span>
					</td>
					<td width=208 colspan=10 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="directionText" value="${wps.fdirection }"/>
							<select class="easyui-combobox" id="fdirection" name="fdirection" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="立向上">立向上</option>
								<option value="立向下">立向下</option>
							</select>
						</span>
					</td>
					<td width=94 colspan=2 class="border_right">
						<span>背面</span>
					</td>
					<td width=264 colspan=12 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="reverse1Text" value="${wps.freverse1 }"/>
							<select class="easyui-combobox" id="freverse1" name="freverse1" style="width:120px;" data-options="required:true,editable:false">
								<option value="Ar(99.99%)">Ar(99.99%)</option>
								<option value="95%Ar+5%CO2">95%Ar+5%CO2</option>
								<option value="CO2(99.8%)">CO2(99.8%)</option>
							</select>
						</span>
						<span class="fontstyle">
							<input type="hidden" id="reverse2Text" value="${wps.freverse2 }"/>
							<select class="easyui-combobox" id="freverse2" name="freverse2" style="width:120px;" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="至少前两层">至少前两层</option>
							</select>
						</span>
						<span class="fontstyle">
							<input type="hidden" id="reverse3Text" value="${wps.freverse3 }"/>
							<select class="easyui-combobox" id="freverse3" name="freverse3" style="width:120px;" data-options="required:true,editable:false">
								<option value="15-20L/min">15-20L/min</option>
								<option value="12-15L/min">12-15L/min</option>
								<option value="10-20L/min">10-20L/min</option>
							</select>
						</span>
						<span class="fontstyle">
							<input type="text" value="&nbsp;" style="width:120px;">
						</span>
					</td>
				</tr>
				<tr style='height:26.25pt' class="border_left">
					<td width=105 colspan=3 class="border_right">
						<span>其他</span>
					</td>
					<td width=208 colspan=10 class="border_right">
						<span class="fontstyle">
							<select class="easyui-combobox" value="${wps.felse2 }" id="felse2" name="felse2" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
							</select>
						</span>
					</td>
					<td width=94 colspan=2 class="border_right">
						<span>尾部</span>
					</td>
					<td width=264 colspan=12 class="border_right">
						<span class="fontstyle">
							<select class="easyui-combobox" value="${wps.ftail }" id="ftail" name="ftail" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
							</select>
						</span>
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
						<span class="fontstyle">
							<input type="hidden" id="pareheatingText" value="${wps.fpreheating_temperature }"/>
							<select class="easyui-combobox" id="fpreheating_temperature" name="fpreheating_temperature" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="/">/</option>
								<option value="≥5℃">≥5℃</option>
							</select>
						</span>
					</td>
					<td width=94 colspan=2 class="border_right">
						<span>温度范围</span>
					</td>
					<td width=264 colspan=12 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="temperaturerangerText" value="${wps.ftemperature_range }"/>
							<select class="easyui-combobox" id="ftemperature_range" name="ftemperature_range" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="/">/</option>
								<option value="630±15℃">630±15℃</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=105 colspan=3 class="border_left">
						<span>层间温度</span>
					</td>
					<td width=208 colspan=10 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="temperatureText" value="${wps.ftemperature }"/>
							<select class="easyui-combobox" id="ftemperature" name="ftemperature" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="/">/</option>
								<option value="≤315℃">≤315℃</option>
								<option value="≤150℃">≤150℃</option>
								<option value="≤300℃">≤300℃</option>
							</select>
						</span>
					</td>
					<td width=94 colspan=2 class="border_right">
						<span>保温时间</span>
					</td>
					<td width=264 colspan=12 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="soakingtimeText" value="${wps.fsoaking_time }"/>
							<select class="easyui-combobox" id="fsoaking_time" name="fsoaking_time" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="4h">4h</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=105 colspan=3 class="border_left">
						<span>预热方式</span>
					</td>
					<td width=208 colspan=10 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="preheatwayText" value="${wps.fPreheat_way }"/>
							<select class="easyui-combobox" id="" name="fPreheat_way" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="4h">4h</option>
							</select>
						</span>
					</td>
					<td width=94 colspan=2 class="border_right">
						<span>其他</span>
					</td>
					<td width=264 colspan=12 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="else3Text" value="${wps.feles3 }"/>
							<select class="easyui-combobox" id="feles3" name="feles3" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="硬度要求：22~30HRC">硬度要求：22~30HRC</option>
							</select>
						</span>
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
						<span class="fontstyle">
							<input type="hidden" id="scopeText" value="${wps.fscope }"/>
							<select class="easyui-combobox" id="fscope" name="fscope" data-options="required:true,editable:false">
								<option value="/">/</option>
								<option value="≤36KJ/cm">≤36KJ/cm</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=131 colspan=5 class="border_left">
						<span>喷嘴尺寸</span>
					</td>
					<td width=177 colspan=7 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="nazzleText" value="${wps.fnozzle }"/>
							<select class="easyui-combobox" id="fnozzle" name="fnozzle" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="Φ8-10">Φ8-10</option>
							</select>
						</span>
					</td>
					<td width=131 colspan=6 class="border_right">
						<span>导电嘴与工件距离</span>
					</td>
					<td width=233 colspan=9 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="distanceText" value="${wps.fdistance }"/>
							<select class="easyui-combobox" id="fdistance" name="fdistance" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="/">/</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=131 colspan=5 class="border_left">
						<span>清根方法</span>
					</td>
					<td width=177 colspan=7 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="chippingText" value="${wps.fback_chipping }"/>
							<select class="easyui-combobox" id="fback_chipping" name="fback_chipping" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="机械打磨">机械打磨</option>
							</select>
						</span>
					</td>
					<td width=131 colspan=6 class="border_right">
						<span>焊缝层数范围</span>
					</td>
					<td width=233 colspan=9 class="border_right">
						<span class="fontstyle">HWS:
							<input type="hidden" id="scope1Text" value="${wps.flayer_scope1 }"/>
							<select class="easyui-combobox" id="flayer_scope1" name="flayer_scope1" style="width:60px;" data-options="required:true,editable:false">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="1~2">1~2</option>
								<option value="2~3">2~3</option>
								<option value="3~4">3~4</option>
								<option value="3~5">3~5</option>
								<option value="4~6">4~6</option>
								<option value="6~8">6~8</option>
								<option value="7~10">7~10</option>
							</select>层;
						</span>
						<span class="fontstyle">HD:
							<input type="hidden" id="scope2Text" value="${wps.flayer_scope2 }"/>
							<select class="easyui-combobox" id="flayer_scope2" name="flayer_scope2" style="width:60px;" data-options="required:true,editable:false">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="1~2">1~2</option>
								<option value="2~3">2~3</option>
								<option value="3~4">3~4</option>
								<option value="3~5">3~5</option>
								<option value="4~6">4~6</option>
								<option value="6~8">6~8</option>
								<option value="7~10">7~10</option>
							</select>层
						</span>
					</td>
				</tr>
				<tr>
					<td width=131 colspan=5 class="border_left">
						<span>钨极类型/尺寸</span>
					</td>
					<td width=177 colspan=7 class="border_right">
						<span class="fontstyle">
							<input type="hidden" id="tungstenText" value="${wps.ftungsten_electrode }"/>
							<select class="easyui-combobox" id="ftungsten_electrode" name="ftungsten_electrode" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
								<option value="WCe Φ2.4">WCe Φ2.4</option>
							</select>
						</span>
					</td>
					<td width=131 colspan=6 class="border_right">
						<span>熔滴过渡方式</span>
					</td>
					<td width=233 colspan=9 class="border_right">
						<span class="fontstyle">
							<select class="easyui-combobox" value="${wps.ftransient_mode }" id="ftransient_mode" name="ftransient_mode" data-options="required:true,editable:false">
								<option value="N/A">N/A</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=237 colspan=9 class="border_left">
						<span>直向焊、摆动焊及摆动方法</span>
					</td>
					<td width=434 colspan=18 class="border_right">
						<span class="fontstyle">
							<select class="easyui-combobox" value="${wps.fmethod1 }" id="fmethod1" name="fmethod1" data-options="required:true,editable:false">
								<option value="直线或摆动">直线或摆动</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=237 colspan=9 class="border_left">
						<span>背面、打底及中间焊道清理方法</span>
					</td>
					<td width=434 colspan=18 class="border_right">
						<span class="fontstyle">
							<select class="easyui-combobox" value="${wps.fmethod2 }" id="fmethod2" name="fmethod2" data-options="required:true,editable:false">
								<option value="刷或机械打磨">刷或机械打磨</option>
							</select>
						</span>
					</td>
				</tr>
				<tr>
					<td width=671 colspan=27 class="border_left">
						<span>焊接参数</span>
					</td>
				</tr>
				<tr>
					<td width=104 colspan=3 rowspan=2 class="border_left">焊层</td>
					<td width=65 colspan=4 rowspan=2 class="border_right">焊接方法</td>
					<td width=142 colspan=6 class="border_right">焊材</td>
					<td width=141 colspan=6 class="border_right">焊接电流</td>
					<td width=75 colspan=4 rowspan=2 class="border_right">电压范围</td>
					<td width=56 colspan=3 rowspan=2 class="border_right">焊接速度</td>
					<td width=84  rowspan=2 class="border_right">操作</td>
				</tr>
				<tr style='height:12.95pt'>
					<td width=66 colspan=2 class="border_left">
						<span>型（牌）号</span>
					</td>
					<td width=76 colspan=4 class="border_right">
						<span>规格（mm）</span>
					</td>
					<td width=57 colspan=1 class="border_right">
						<span>极性</span>
					</td>
					<td width=95 colspan=5 class="border_right">
						<span>范围（>A）
					</td>
				</tr>
				<!-- 循环显示焊接参数 -->
				<c:forEach items="${list}" var="w" varStatus="s">
					<tr>
						<td width=104 colspan=3 class="border_left">
							<span class="fontstyle">
								<input type="hidden" id="listcount" value="${listcount }"/>
								<input type="hidden" id="oldid${s.index }" value="${w.childrenid}"/>
								<input type="hidden" id="weldprechannelText${s.index }" value="${w.fweld_prechannel}"/>
								<select class="easyui-combobox" style="width:80px;" id="oldfweld_prechannel${s.index }" name="oldfweld_prechannel${s.index }" data-options="required:true,editable:false">
									<option value="1">打底</option><option value="2">填充</option>
									<option value="3">盖面</option><option value="4">背面封底</option><option value="5">第一层</option>
								</select>
							</span>
						</td>
						<td width=65 colspan=4 class="border_right">
							<span class="fontstyle">
								<input type="hidden" id="weldingmethodText${s.index }" value="${w.fwelding_method }"/>
								<select class="easyui-combobox" style="width:60px;" id="oldfwelding_method${s.index }" name="oldfwelding_method${s.index }" data-options="required:true,editable:false">
									<option value="HD">HD</option><option value="HWS">HWS</option>
									<option value="HRZ">HRZ</option><option value="HRB">HRB</option><option value="HM">HM</option>
								</select>
							</span>
						</td>
						<td width=66 colspan=2 class="border_right">
							<span class="fontstyle" value="${w.ftype }">
								<input type="hidden" id="typeText${s.index }" value="${w.ftype }"/>
								<select class="easyui-combobox" style="width:80px;" id="oldftype${s.index }" name="oldftype${s.index }" data-options="required:true,editable:false">
									<option value="ER308L">ER308L</option><option value="ERCoCr-E">ERCoCr-E</option>
									<option value="ECoCr-E">ECoCr-E</option><option value="ER70S-6">ER70S-6</option>
									<option value="ER316L">ER316L</option><option value="E308-16">E308-16</option>
								</select>
							</span>
						</td>
						<td width=76 colspan=4 class="border_right">
							<span class="fontstyle">
								<input type="hidden" id="specificationText${s.index }" value="${w.fchildren_specification }"/>
								<select class="easyui-combobox" style="width:60px;" id="oldfspecification${s.index }" name="oldfspecification${s.index }" data-options="required:true,editable:false">
									<option value="Φ1.0">Φ1.0</option><option value="Φ1.6">Φ1.6</option>
									<option value="Φ2.0">Φ2.0</option><option value="Φ3.2">Φ3.2</option>
									<option value="Φ4.0">Φ4.0</option><option value="Φ5.0">Φ5.0</option>
								</select>
							</span>
						</td>
						<td width=57 colspan=1 class="border_right">
							<span class="fontstyle">
								<input type="hidden" id="polarityText${s.index }" value="${w.fpolarity }"/>
								<select class="easyui-combobox" style="width:60px;" id="oldfpolarity${s.index }" name="oldfpolarity${s.index }" data-options="required:true,editable:false">
									<option value="DCEN">DCEN</option><option value="DCEP">DCEP</option>
								</select>
							</span>
						</td>
						<td width=95 colspan=5 class="border_right">
							<span class="fontstyle">
								<input type="hidden" id="weldiText${s.index }" value="${w.fweld_i_min }~${w.fweld_i_max }"/>
								<select class="easyui-combobox" style="width:80px;" id="oldfweld_i${s.index }" name="oldfweld_i${s.index }" data-options="required:true,editable:false">
									<option value="20~40">20~40</option><option value="30~60">30~60</option>
									<option value="50~70">50~70</option><option value="60~90">60~90</option>
									<option value="80~110">80~110</option><option value="90~130">90~130</option>
									<option value="100~140">100~140</option><option value="160~220">160~220</option>
								</select>
							</span>
						</td>
						<td width=75 colspan=4 class="border_right">
							<span class="fontstyle"> 
								<input type="hidden" id="weldvText${s.index }" value="${w.fweld_v_min }~${w.fweld_v_max }"/>
								<select class="easyui-combobox" style="width:80px;" id="oldfweld_v${s.index }" name="oldfweld_v${s.index }" data-options="required:true,editable:false">
									<option value="8~15">8~15</option><option value="10~15">10~15</option>
									<option value="12~18">12~18</option><option value="22~26">22~26</option>
								</select>
							</span>
						</td>
						<td width=56 colspan=3 class="border_right">
							<span class="fontstyle">
								<input type="hidden" id="weldingspeedText${s.index }"  value="${w.fwelding_speed }"/>
								<select class="easyui-combobox" style="width:60px;" id="oldfwelding_speed${s.index }" name="oldfwelding_speed${s.index }" data-options="required:true,editable:false">
									<option value="/">/</option><option value="6~12">6~12</option>
								</select>
							</span>
						</td>
						<td width=84 class="border_right" onclick="removeOldTd(this,${s.index },${w.childrenid })">
							<span><a href="javascript:void(0)" id="remove" class="easyui-linkbutton" iconCls="icon-remove"></a></span>
						</td>
					</tr>
				</c:forEach>
				<tr id="addremoveflag">
					<td width=104 colspan=3 class="border_left">
						<span>&nbsp;</span>
					</td>
					<td width=65 colspan=4 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=66 colspan=2 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=76 colspan=4 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=57 colspan=1 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=95 colspan=5 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=75 colspan=4 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=56 colspan=3 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=84 class="border_right">
						<span><a href="javascript:addTd()" class="easyui-linkbutton" iconCls="icon-add">新增</a></span>
					</td>
				</tr>
				<tr>
					<td width=104 colspan=3 class="border_left">
						<span>&nbsp;</span>
					</td>
					<td width=65 colspan=4 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=66 colspan=2 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=76 colspan=4 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=57 colspan=1 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=95 colspan=5 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=75 colspan=4 class="border_right">
						<span>&nbsp;</span>
					</td>
					<td width=56 colspan=3 class="border_right">
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
						<span>&nbsp;</span>
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
						<span>
							<input type="hidden" id="itemidText" value="${wps.fitemid }"/>
							<select style="width:200px;" class="easyui-combobox" id="fitemid" name="fitemid" style="width:120px;" data-options="required:true,editable:false"></select>
						</span>
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
		</form>
		<div id="dlg-buttons" style="text-align:center;margin-bottom: 10px;margin-top: -30px;">
			<a href="javascript:commitWps()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="wps/goWps" style="margin-left:100px;" class="easyui-linkbutton" iconCls="icon-cancel" >取消</a>
		</div>
	</div>
</body>
</html>
