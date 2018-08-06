$(function(){
  typeCombobox();
  InsframeworkCombobox();
  manuCombobox();
  statusRadio();
  $("#iId").combobox({
        onChange:function(){  
          itemid = $("#iId").combobox("getValue");
          if(itemid != gatheritemid){
              $("#gatherId").val("");
              $("#gatherNo").textbox('setValue');
          }
//          gatherCombobox();
        } 
     });
  $('#dlg').dialog( {
    onClose : function() {
      $('#leveid').combobox('clear');
      $('#quali').combobox('clear');
      $('#owner').combobox('clear');
      $("#fm").form("disableValidation");
    }
  })
  editText();
  $("#fm").form("disableValidation");
})


var url = "";
var flag = 1;
function addWeldingMachine(){
  flag = 1;
  $("#iId").next().show();
  $("#insfname").next().hide();
  $('#dlg').window( {
    title : "新增设备迁移",
    modal : true
  });
  $('#dlg').window('open');
  $('#fm').form('clear');
  $("#iId").combobox('select',nodeid);
  var statusId = document.getElementsByName("statusId");
  statusId[0].checked = 'checked';
  var isnetworkingId = document.getElementsByName("isnetworkingId");
	isnetworkingId[0].checked =  'checked';
  url = "weldingMachine/addWeldingMachine";
  //saveWeldingMachine();
}

function editWeldingMachine(){
  flag = 2;
  $('#fm').form('clear');
  var wid = $("#wid").val();
  $("#insfname").next().show();
  $("#iId").next().hide();
  var row = $('#weldingmachineTable').datagrid('getSelected');
  if (row) {
    $('#dlg').window( {
      title : "修改焊机设备",
      modal : true
    });
    $('#dlg').window('open');
    $('#fm').form('load', row);
    $("#insfname").textbox('setValue',row.insframeworkName);
    //weldingMachineDatagrid();
    url = "weldingMachine/editWeldingMachine?wid="+row.id;
  }
  //saveWeldingMachine();
}

//提交
function saveWeldingMachine(){
  var tid = $('#tId').combobox('getValue');
  var manuno = $('#manufacturerId').combobox('getValue');
  var sid = $("input[name='statusId']:checked").val();
  var isnetworking = $("input[name='isnetworkingId']:checked").val();
  var messager = "";
  var url2 = "";
  if(flag==1){
    messager = "新增成功！";
    url2 = url+"?tId="+tid+"&iId="+$('#iId').combobox('getValue')+"&sId="+sid+"&isnetworking="+isnetworking+"&manuno="+manuno;
  }else{
    messager = "修改成功！";
    url2 = url+"&tId="+tid+"&iId="+$('#insframework').val()+"&sId="+sid+"&isnetworking="+isnetworking+"&manuno="+manuno+"&gatherId="+$('#gatherId').val();
  }
  $('#fm').form('submit', {
    url : url2,
    onSubmit : function() {
      return $(this).form('enableValidation').form('validate');
    },
    success : function(result) {
      if(result){
        var result = eval('(' + result + ')');
        if (!result.success) {
          $.messager.show( {
            title : 'Error',
            msg : result.errorMsg
          });
        }else{
          if(result.msg==null){
            $.messager.alert("提示", messager);
            $('#dlg').dialog('close');
            $('#weldingmachineTable').datagrid('reload');
          }else{
            $.messager.alert("提示", messager);
            $('#dlg').dialog('close');
            $('#weldingmachineTable').datagrid('reload');
        }
      }
    }
      
    },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      } 
  });
}

function editText(){  
  //隐藏文本处理传值
  var isnw = $("#isnw").val();
  var status = $("#status").val();
  var type = $("#type").val();
  var insframework = $("#insframework").val();
  var manu = $("#manu").val();
//  var gid = $("#gid").val();
  $('[name="isnetworking"]:radio').each(function() { 
    if (this.value ==isnw ) { 
      this.checked = true;
    } 
  });
  $('[name="statusId"]:radio').each(function() { 
    if (this.value ==status ) { 
      this.checked = true;
    } 
  });
  $('#tId').combobox('select',type);
  $('#iId').combobox('select',insframework);
  $('#manuno').combobox('select',manu);
  $('#gatherId').val($("#gid").val());
  $('#gatherNo').textbox('setValue',$("#gno").val());
  $("#insfname").next().hide();
  $("#iId").next().hide();
}

var itemid = "";
//采集序号
function gatherCombobox(){
  $.ajax({  
      type : "post",  
      async : false,
      url : "weldingMachine/getGatherAll?itemid="+itemid,  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {
          var optionStr = "";
          if (result) {
            if(result.ary.length<=0){
              optionStr += "<option></option>";  
            }else{
                for (var i = 0; i < result.ary.length; i++) {  
                    optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                            + result.ary[i].name + "</option>";  
                }
            }
              $("#gatherId").html(optionStr);
          }  
      }
  }); 
  $("#gatherId").combobox();
}

//设备类型
function typeCombobox(){
  $.ajax({  
        type : "post",  
        async : false,
        url : "weldingMachine/getTypeAll",  
        data : {},  
        dataType : "json", //返回数据形式为json  
        success : function(result) {
            if (result) {
                var optionStr = '';  
                for (var i = 0; i < result.ary.length; i++) { 
                    optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                            + result.ary[i].name + "</option>";  
                } 
                $("#tId").append(optionStr);
            }  
        },  
        error : function(errorMsg) {  
            alert("数据请求失败，请联系系统管理员!");  
        }  
   }); 
  $("#tId").combobox();
}

//所属项目
function InsframeworkCombobox(){
  $.ajax({  
      type : "post",  
      async : false,
      url : "weldingMachine/getInsframeworkAll",  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {  
          if (result) {
              var optionStr = '';
              for (var i = 0; i < result.ary.length; i++) {  
                  optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                          + result.ary[i].name + "</option>";
              }
              $("#iId").html(optionStr);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
  }); 
  $("#iId").combobox();
}

//厂商
function manuCombobox(){
  $.ajax({  
    type : "post",  
    async : false,
    url : "weldingMachine/getManuAll",  
    data : {},  
    dataType : "json", //返回数据形式为json  
    success : function(result) {  
        if (result) {
            var optionStr = '';
            for (var i = 0; i < result.ary.length; i++) {  
                optionStr += "<option value=\"" + result.ary[i].id + "\" >"  
                        + result.ary[i].name + " - " +result.ary[i].type + "</option>";
            }
            $("#manufacturerId").html(optionStr);
        }  
    },  
    error : function(errorMsg) {  
        alert("数据请求失败，请联系系统管理员!");  
    }  
  }); 
  $("#manufacturerId").combobox();
}

//焊机状态
function statusRadio(){
  $.ajax({  
      type : "post",  
      async : false,
      url : "weldingMachine/getStatusAll",  
      data : {},  
      dataType : "json", //返回数据形式为json  
      success : function(result) {
        if (result) {
          var str = "";
          for (var i = 0; i < result.ary.length; i++) {
            str += "<input type='radio' class='radioStyle' name='statusId' id='sId' value=\"" + result.ary[i].id + "\" />"  
                    + result.ary[i].name;
          }
              $("#radios").html(str);
              $("input[name='statusId']").eq(0).attr("checked",true);
          }  
      },  
      error : function(errorMsg) {  
          alert("数据请求失败，请联系系统管理员!");  
      }  
  });
}

var searchStr="";
function GatherDatagrid() {
	var parent;
	if (flag == 2) {
		parent = $("#insframework").val();
	} else {
		parent = $("#iId").combobox('getValue');
	}
	searchStr += "g.fid not in (select g.fid from tb_gather g INNER JOIN tb_welding_machine m on m.fgather_id = g.fid) and fstatus!='迁移'";
	if($("#searchname").val()){
	    searchStr +=  " and fgather_no like '%"+$("#searchname").val()+"%'";
	}
	var url = "gather/getGatherList?parent=" + parent + "&searchStr=" + encodeURI(searchStr);
	$("#gatherTable").datagrid({
		fitColumns : true,
		height : $("#fdlg").height(),
		width : $("#fdlg").width(),
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		url : url,
		singleSelect : true,
		rownumbers : true,
		showPageList : false,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'id',
			title : '序号',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'gatherNo',
			title : '采集模块编号',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'itemid',
			title : '项目id',
			width : 100,
			halign : "center",
			align : "left",
			hidden : true
		}, {
			field : 'itemname',
			title : '所属项目',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'status',
			title : '采集模块状态',
			width : 100,
			halign : "center",
			align : "left"
		}, {
			field : 'protocol',
			title : '采集模块通讯协议',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'ipurl',
			title : '采集模块IP地址',
			width : 150,
			halign : "center",
			align : "left"
		}, {
			field : 'macurl',
			title : '采集模块MAC地址',
			width : 150,
			halign : "center",
			align : "left"
		} ] ],
		toolbar : '#dlgSearch',
		pagination : true,
		onLoadSuccess : function(data) {
			$("#gatherTable").datagrid('selectRow', 0);
		}
	});
}

function selectMachine(){
  $('#fdlg').window( {
    modal : true
  });
  $('#fdlg').window('open');
  GatherDatagrid();
}
var gatheritemid;
function saveGather(){
  var row = $("#gatherTable").datagrid('getSelected');
  gatheritemid = row.itemid;
  $("#gatherNo").textbox('setValue',row.gatherNo);
  $("#gatherId").val(row.id);
  $('#fdlg').dialog('close');
}

function dlgSearchGather(){
  GatherDatagrid();
}

function reset(){
  $('#gatherId').val($("#gid").val());
  $('#gatherNo').textbox('setValue',$("#gno").val());
}