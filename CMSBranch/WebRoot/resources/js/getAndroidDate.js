$(function(){
	/*$("#dtoTime1").datebox({
		onChange: function (newvalue,oldvalue) {
			if(newvalue==null || newvalue==""){
				$("#dtoTime1").datebox('setValue',oldtime);
			}else{
				var start = new Date(newvalue).getTime();
				var end = new Date($("#dtoTime2").datebox('getValue')).getTime();
				var time = 0;
				if(start < end){
					time = end-start;
				}
				time = Math.floor(time/86400000);
				if(time!=NaN && time>365){
					alert("提示：时间范围应在365天内");
					$("#dtoTime2").datebox('setValue',nowtime);
					$("#dtoTime1").datebox('setValue',oldtime);
				}
			}
		}
	})
	$("#dtoTime2").datebox({
		onChange: function (newvalue,oldvalue) {
			if(newvalue==null || newvalue==""){
				$("#dtoTime2").datebox('setValue',nowtime);
			}else{
				var start = new Date($("#dtoTime1").datebox('getValue')).getTime();
				var end = new Date(newvalue).getTime();
				var time = 0;
				if(start < end){
					time = end-start;
				}
				time = Math.floor(time/86400000);
				if(time!=NaN && time>365){
					alert("提示：时间范围应在365天内");
					$("#dtoTime1").datebox('setValue',oldtime);
					$("#dtoTime2").datebox('setValue',nowtime);
				}
			}
		}
	})*/
	getOldTime();
	getNewTime();
})
var oldtime,nowtime;
function getOldTime(){
	//获取当前时间
	var now = new Date();  
	now.setDate(now.getDate()-6);//获取6天前的日期 
    var year = now.getFullYear();//年  
    var month = now.getMonth() + 1;//月  
    var day = now.getDate();//日
    
    oldtime = year + "-";
      
    if(month < 10){
        oldtime += "0";
    }
    oldtime += month + "-";
      
    if(day < 10){
        oldtime += "0";
    }          
    oldtime += day;
	$("#androidtime1").val(oldtime);
}

function getNewTime(){
	//获取当前时间
	var now = new Date();  
    
    var year = now.getFullYear();//年  
    var month = now.getMonth() + 1;//月  
    var day = now.getDate();//日
    
    nowtime = year + "-";
      
    if(month < 10){
        nowtime += "0";
    }
    nowtime += month + "-";
      
    if(day < 10){
        nowtime += "0";
    }          
    nowtime += day;
	$("#androidtime2").val(nowtime);
}