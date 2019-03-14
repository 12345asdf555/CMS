$(function(){
	$("#dtoTime1").datetimebox({
		onChange: function (newvalue,oldvalue) {
			if(newvalue==null || newvalue==""){
				$("#dtoTime1").datetimebox('setValue',oldtime);
			}else{
				var start = new Date(newvalue).getTime();
				var end = new Date($("#dtoTime2").datetimebox('getValue')).getTime();
				var time = 0;
				if(start < end){
					time = end-start;
				}
				time = Math.floor(time/86400000);
				if(time!=NaN && time>365){
					alert("提示：时间范围应在365天内");
					$("#dtoTime2").datetimebox('setValue',nowtime);
					$("#dtoTime1").datetimebox('setValue',oldtime);
				}
			}
		}
	})
	$("#dtoTime2").datetimebox({
		onChange: function (newvalue,oldvalue) {
			if(newvalue==null || newvalue==""){
				$("#dtoTime2").datetimebox('setValue',nowtime);
			}else{
				var start = new Date($("#dtoTime1").datetimebox('getValue')).getTime();
				var end = new Date(newvalue).getTime();
				var time = 0;
				if(start < end){
					time = end-start;
				}
				time = Math.floor(time/86400000);
				if(time!=NaN && time>365){
					alert("提示：时间范围应在365天内");
					$("#dtoTime1").datetimebox('setValue',oldtime);
					$("#dtoTime2").datetimebox('setValue',nowtime);
				}
			}
		}
	})
	var parenttime1 = $("#parentime1").val();
	var parenttime2 = $("#parentime2").val();
	if(parenttime1){
		$("#dtoTime1").datetimebox('setValue',parenttime1);
	}else{
		getOldTime();
	}
	if(parenttime2){
		$("#dtoTime2").datetimebox('setValue',parenttime2);
	}else{
		getNewTime();
	}
})
var nowtime,oldtime;
function getOldTime(){
	//获取当前时间
	var now = new Date();
    var year = now.getFullYear();//年  
    var month = now.getMonth();//月  
    var day = now.getDate();//日
    var hh = now.getHours();//时
    
    oldtime = year + "-";
      
    if(month < 10){
        oldtime += "0";
    }
    oldtime += month + "-";
      
    if(day < 10){
        oldtime += "0";
    }          
    oldtime += day + " ";
    
    if(hh < 10){
        oldtime += "0";
    }
    oldtime += hh + ":00:00"
	$("#dtoTime1").datetimebox('setValue',oldtime); 
}

function getNewTime(){
	//获取当前时间
	var now = new Date();  
    
    var year = now.getFullYear();//年  
    var month = now.getMonth() + 1;//月  
    var day = now.getDate();//日
    var hh = now.getHours();//时
    
    nowtime = year + "-";
      
    if(month < 10){
        nowtime += "0";
    }
    nowtime += month + "-";
      
    if(day < 10){
        nowtime += "0";
    }          
    nowtime += day + " ";
    
    if(hh < 10){
        nowtime += "0";
    }
    nowtime += hh + ":00:00";
	$("#dtoTime2").datetimebox('setValue',nowtime);
}