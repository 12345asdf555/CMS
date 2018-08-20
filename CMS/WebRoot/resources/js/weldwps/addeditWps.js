$(function(){
	insframeworkCombobox();
	setEditDefault();
	$("#fm").form("disableValidation");
	$.messager.defaults = { ok: "是",cancel:"否",width:'250px',height:'150px',top:250};  
})

var flag;
var resultary = new Array(),editResultary = new Array();
function commitWps(){
	var url = "";
	flag = $("#flag").val();
	for(var i=0;i<ary.length;i++){
		var fweld_prechannel = $("#fweld_prechannel" + ary[i]).combobox('getValue');
		var fwelding_method = $("#fwelding_method" + ary[i]).combobox('getValue');
		var ftype = $("#ftype" + ary[i]).combobox('getValue');
		var fspecification = $("#fspecification" + ary[i]).combobox('getValue');
		var fpolarity = $("#fpolarity" + ary[i]).combobox('getValue');
		var fweld_i = $("#fweld_i" + ary[i]).combobox('getValue');
		var fweld_v = $("#fweld_v" + ary[i]).combobox('getValue');
		var fwelding_speed = $("#fwelding_speed" + ary[i]).combobox('getValue');
		resultary.push(fweld_prechannel+";"+fwelding_method+";"+ftype+";"+fspecification+";"+fpolarity+";"+fweld_i+";"+fweld_v+";"+fwelding_speed);
	}
	if(flag == 1){
		messager = "新增成功！";
		url = "wps/addWps?imageurl="+imageurl+"&weldwps="+resultary;
	}else{
		messager = "修改成功！";
		for(var i=0;i<listcountary.length;i++){
			var fid = $("#oldid"+ listcountary[i]).val();
			var fweld_prechannel = $("#oldfweld_prechannel"+ listcountary[i]).combobox('getValue');
			var fwelding_method = $("#oldfwelding_method"+ listcountary[i]).combobox('getValue');
			var ftype = $("#oldftype"+ listcountary[i]).combobox('getValue');
			var fspecification = $("#oldfspecification"+ listcountary[i]).combobox('getValue');
			var fweld_i= $("#oldfweld_i"+ listcountary[i]).combobox('getValue');
			var fweld_v= $("#oldfweld_v"+ listcountary[i]).combobox('getValue');
			var fwelding_speed  = $("#oldfwelding_speed"+ listcountary[i]).combobox('getValue');
			var fpolarity = $("#oldfpolarity"+ listcountary[i]).combobox('getValue');
			editResultary.push(fweld_prechannel+";"+fwelding_method+";"+ftype+";"+fspecification+";"+fpolarity+";"+fweld_i+";"+fweld_v+";"+fwelding_speed+";"+fid);
		}
		if(imageurl==null || imageurl==""){
			imageurl = $("#oldimgurl").val();
		}
		url = "wps/updateWps?imageurl="+imageurl+"&weldwps="+resultary+"&oldweldwps="+editResultary;
	}
	$('#fm').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(result) {
			if (result) {
				var result = eval('(' + result + ')');
				if (!result.success) {
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				} else {
					$.messager.alert("提示", messager);
					if(result.msg!=null){
						$.messager.show( {title : '提示',msg : result.msg});
					}
					var url = "wps/goWps";
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

function insframeworkCombobox(){
	$.ajax({
		type : "post",
		async : false,
		url : "wps/getInsAll",
		data : {},
		dataType : "json", //返回数据形式为json  
		success : function(result) {
			if (result) {
				var optionStr = '';
				for (var i = 0; i < result.ary.length; i++) {
					optionStr += "<option value=\"" + result.ary[i].id + "\" >"
						+ result.ary[i].name + "</option>";
				}
				$("#fitemid").html(optionStr);
			}
		},
		error : function(errorMsg) {
			alert("数据请求失败，请联系系统管理员!");
		}
	});
	$("#fitemid").combobox();
}

var imgdata = '';
function selectImage(obj){
	var f=$(obj).val();
    if(f == null || f ==undefined || f == ''){
    	document.getElementById('image').src = "";
        return false;
    }
    if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(f)){
        alert("类型必须是图片(.png|jpg|bmp|gif|PNG|JPG|BMP|GIF)");
        $(obj).val('');
        return false;
    }
    imgdata = new FormData();
    $.each($(obj)[0].files,function(i,file){
    	imgdata.append('file', file);
    });
	if(!file.files || !file.files[0]){
		return;
	}
	var reader = new FileReader();
	reader.onload = function(evt){
		document.getElementById('image').src = evt.target.result;
		uploadfile = evt.target.result;
	}
	reader.readAsDataURL(file.files[0]);
}
var imageurl = "";
function importWeldingMachine() {
	var file = $("#file").val();
	if (file == null || file == "") {
		$.messager.alert("提示", "请选择要上传的文件！");
		return false;
	} else {
		$.ajax({
			type : "post",
			async : false,
			url : "wps/uploadimage",
			data : imgdata,
			cache : false,
			contentType : false, //不可缺
			processData : false, //不可缺,设置为true的时候,ajax提交的时候不会序列化 data，而是直接使用data
			dataType : "json", //返回数据形式为json  
			success : function(result) {
				if (result) {
					if (!result.success) {
						imageurl = "";
						$.messager.show({
							title : 'Error',
							msg : result.errorMsg
						});
					} else {
						imageurl = result.imgurl;
						$.messager.alert("提示", "图片上传成功！");
					}
				}
			},
			error : function(errorMsg) {
				alert("数据请求失败，请联系系统管理员!");
			}
		});
	}

}

var ary = new Array();
var index = 0;
function addTd(){
	ary.push(index);//将所有插入时的index存入ary
	$("#addremoveflag").before('<tr><td width=104 colspan=3 class="border_left">'+
			'<span class="fontstyle">'+
				'<select class="easyui-combobox" style="width:80px;" id="fweld_prechannel'+index+'" name="fweld_prechannel'+index+'" data-options="required:true,editable:false">'+
					'<option value="1">打底</option><option value="2">填充</option>'+
					'<option value="3">盖面</option><option value="4">背面封底</option><option value="5">第一层</option>'+
				'</select>'+
			'</span></td><td width=65 colspan=4 class="border_right">'+
			'<span class="fontstyle">'+
				'<select class="easyui-combobox" style="width:60px;" id="fwelding_method'+index+'" name="fwelding_method'+index+'" data-options="required:true,editable:false">'+
					'<option value="HD">HD</option><option value="HWS">HWS</option>'+
					'<option value="HRZ">HRZ</option><option value="HRB">HRB</option><option value="HM">HM</option>'+
				'</select>'+
			'</span></td><td width=66 colspan=2 class="border_right">'+
			'<span class="fontstyle">'+
				'<select class="easyui-combobox" style="width:80px;" id="ftype'+index+'" name="ftype'+index+'" data-options="required:true,editable:false">'+
					'<option value="ER308L">ER308L</option><option value="ERCoCr-E">ERCoCr-E</option>'+
					'<option value="ECoCr-E">ECoCr-E</option><option value="ER70S-6">ER70S-6</option>'+
					'<option value="ER316L">ER316L</option><option value="E308-16">E308-16</option>'+
				'</select>'+
			'</span></td><td width=76 colspan=4 class="border_right">'+
			'<span class="fontstyle">'+
				'<select class="easyui-combobox" style="width:60px;" id="fspecification'+index+'" name="fspecification'+index+'" data-options="required:true,editable:false">'+
					'<option value="Φ1.0">Φ1.0</option><option value="Φ1.6">Φ1.6</option>'+
					'<option value="Φ2.0">Φ2.0</option><option value="Φ3.2">Φ3.2</option>'+
					'<option value="Φ4.0">Φ4.0</option><option value="Φ5.0">Φ5.0</option>'+
				'</select>'+
			'</span></td><td width=57 colspan=1 class="border_right">'+
			'<span class="fontstyle">'+
				'<select class="easyui-combobox" style="width:60px;" id="fpolarity'+index+'" name="fpolarity'+index+'" data-options="required:true,editable:false">'+
					'<option value="DCEN">DCEN</option><option value="DCEP">DCEP</option>'+
				'</select>'+
			'</span></td><td width=95 colspan=5 class="border_right">'+
			'<span class="fontstyle">'+
				'<select class="easyui-combobox" style="width:80px;" id="fweld_i'+index+'" name="fweld_i'+index+'" data-options="required:true,editable:false">'+
					'<option value="20~40">20~40</option><option value="30~60">30~60</option>'+
					'<option value="50~70">50~70</option><option value="60~90">60~90</option>'+
					'<option value="80~110">80~110</option><option value="90~130">90~130</option>'+
					'<option value="100~140">100~140</option><option value="160~220">160~220</option>'+
				'</select>'+
			'</span></td><td width=75 colspan=4 class="border_right">'+
			'<span class="fontstyle">'+
				'<select class="easyui-combobox" style="width:80px;" id="fweld_v'+index+'" name="fweld_v'+index+'" data-options="required:true,editable:false">'+
					'<option value="8~15">8~15</option><option value="10~15">10~15</option>'+
					'<option value="12~18">12~18</option><option value="22~26">22~26</option>'+
				'</select>'+
			'</span></td><td width=56 colspan=3 class="border_right">'+
			'<span class="fontstyle">'+
				'<select class="easyui-combobox" style="width:60px;" id="fwelding_speed'+index+'" name="fwelding_speed'+index+'" data-options="required:true,editable:false">'+
					'<option value="/">/</option><option value="6~12">6~12</option>'+
				'</select>'+
			'</span></td><td width=84 class="border_right" onclick="removeTd(this,'+index+')">'+
				'<a href="javascript:void(0)" id="remove" class="easyui-linkbutton" iconCls="icon-remove"></a>'+
			'</td></tr>');
	$("a[id='remove']").linkbutton({text : '删除',iconCls : 'icon-remove'});
	//初始化easyui下拉框
	$("#fweld_prechannel" + index).combobox();
	$("#fwelding_method" + index).combobox();
	$("#ftype" + index).combobox();
	$("#fspecification" + index).combobox();
	$("#fpolarity" + index).combobox();
	$("#fweld_i" + index).combobox();
	$("#fweld_v" + index).combobox();
	$("#fwelding_speed" + index).combobox();
	index++;
}

function removeTd(obj,flagindex){
	var trindex=obj.parentNode.rowIndex;
    var table = document.getElementById("tb");
    table.deleteRow(trindex);
    var rowindex = ary.indexOf(flagindex);
    //ary移除当前行的index
    ary.splice(rowindex,1);
}

function removeOldTd(obj,oldflagindex,id){
    //删除数据
    $.messager.confirm('提示', '此操作不可撤销，是否确认删除?', function(flag) {
		if (flag) {
			$.ajax({
				type : "post",
				async : false,
				url : "wps/destroyWps?fid=" + id +"&insfid=" + $("#itemidText").val(),
				data : {},
				dataType : "json", //返回数据形式为json  
				success : function(result) {
					if (result.errorMsg) {
						$.messager.show({
							title : 'Error',
							msg : result.errorMsg
						});
					} else {
						var trindex=obj.parentNode.rowIndex;
					    var table = document.getElementById("tb");
					    table.deleteRow(trindex);
					    var rowindex = ary.indexOf(oldflagindex);
					    //listcountary移除当前行的index
					    listcountary.splice(rowindex,1);
		            	$.messager.alert("提示", "删除成功！");
						if(result.msg!=null){
							$.messager.show( {title : '提示',msg : result.msg});
						}
					}
				},
				error : function(errorMsg) {
					alert("数据请求失败，请联系系统管理员!");
				}
			});
		}
	})
}

var listcountary = new Array();
//修改时给下拉框默认值
function setEditDefault(){
	if($("#flag").val()==2){
		$("#fdegree").combobox('select',$("#degreeText").val());
		$("#fstabilivolt_system").combobox('select',$("#systemText").val());
		$("#fautomatic").combobox('select',$("#autoText").val());
		$("#fgroove_type").combobox('select',$("#grooveText").val());
		$("#fmaterials").combobox('select',$("#materialsText").val());
		$("#fdiameter").combobox('select',$("#dialmeterText").val());
		$("#feles1").combobox('select',$("#else1Text").val());
		$("#fcategory").combobox('select',$("#categoryText").val());
		$("#fmaterials_type").combobox('select',$("#materialxtypeText").val());
		$("#fshop_sign").combobox('select',$("#shopsignText").val());
		$("#fmaterials_number1").combobox('select',$("#materialsnumber1Text").val());
		$("#fmaterials_specification1").combobox('select',$("#materialsspecification1Text").val());
		$("#fmaterials_number2").combobox('select',$("#materialsnumber2Text").val());
		$("#fmaterials_specification2").combobox('select',$("#materialsspecification2Text").val());
		$("#fspecification").combobox('select',$("#specificationText").val());
		$("#fsoldering_number").combobox('select',$("#solderingText").val());
		$("#fposition").combobox('select',$("#positionText").val());
		$("#ffront1").combobox('select',$("#fornt1Text").val());
		$("#ffront2").combobox('select',$("#fornt2Text").val());
		$("#fdirection").combobox('select',$("#directionText").val());
		$("#freverse1").combobox('select',$("#reverse1Text").val());
		$("#freverse2").combobox('select',$("#reverse2Text").val());
		$("#freverse3").combobox('select',$("#reverse3Text").val());
		$("#fpreheating_temperature").combobox('select',$("#pareheatingText").val());
		$("#ftemperature_range").combobox('select',$("#temperaturerangerText").val());
		$("#ftemperature").combobox('select',$("#temperatureText").val());
		$("#fsoaking_time").combobox('select',$("#soakingtimeText").val());
		$("#fPreheat_way").combobox('select',$("#preheatwayText").val());
		$("#feles3").combobox('select',$("#else3Text").val());
		$("#fscope").combobox('select',$("#scopeText").val());
		$("#fnozzle").combobox('select',$("#nazzleText").val());
		$("#fdistance").combobox('select',$("#distanceText").val());
		$("#fback_chipping").combobox('select',$("#chippingText").val());
		$("#flayer_scope1").combobox('select',$("#scope1Text").val());
		$("#flayer_scope2").combobox('select',$("#scope2Text").val());
		$("#ftungsten_electrode").combobox('select',$("#tungstenText").val());
		$("#fitemid").combobox('select',$("#itemidText").val());
		var listcount = $("#listcount").val();
		for(var i=0;i<listcount;i++){
			listcountary.push(i);//将所有插入时的index存入listcountary
			$("#oldfweld_prechannel"+i).combobox('select',$("#weldprechannelText"+i).val());
			$("#oldfwelding_method"+i).combobox('select',$("#weldingmethodText"+i).val());
			$("#oldftype"+i).combobox('select',$("#typeText"+i).val());
			$("#oldfspecification"+i).combobox('select',$("#specificationText"+i).val());
			$("#oldfweld_i"+i).combobox('select',$("#weldiText"+i).val());
			$("#oldfweld_v"+i).combobox('select',$("#weldvText"+i).val());
			$("#oldfwelding_speed"+i).combobox('select',$("#weldingspeedText"+i).val());
			$("#oldfpolarity"+i).combobox('select',$("#polarityText"+i).val());
		}
	}
}