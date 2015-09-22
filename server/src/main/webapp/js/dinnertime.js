// JavaScript Document
var lastNumberInt = null;
var oHour="";
var oMin="";
var goRow=false;
var timearr = timeLimit.busytimelist;
var timearrnew=[];
var redels=/:/g;
var timeArrBefore=[];
var timeArrAfter=[];
var caldays=[];
var calday=[];
var oDaytime=""; 
var redel=/-/g;
var hourStart=[];
var hourEnd=[];
var minuteStart=[];
var minuteEnd=[];
var calShopides="";
var timedelay = "";
var days = "";
var delayMin = "";
var iHour = '';
var iMinute = '';
var oDate = new Date();
var oYear=oDate.getFullYear();
var oMonth=oDate.getMonth();
var kai="";

if(oMonth<10){
	oMonth="0"+oMonth;
}else{
	oMonth=""+oMonth;
}
var days = oDate.getDate();
if(days<10){
	days="0"+days;
}else{
	days=""+days;
}
var iHour=oDate.getHours();
var iMinute=oDate.getMinutes();

var fullTime="";
var timeArr = [];
$(function() {
	//默认文字的显示隐藏
	var oLastSelTime="";
	var redel=/-/g;
	
	
	
	var iHour=oDate.getHours();
	var iMinute=oDate.getMinutes();
	fullTime=oYear+"-"+(parseInt(oMonth)+1)+"-"+days;
	var now = fullTime.replace(redel,"").replace(redels,"");
	var oLow=0;
	var oBefore=0;
	var oLast=0;
	var oUp=0;

	$(".message_repasttime").attr("data-min",oBefore);
	$(".message_repasttime").attr("data-max",oLast);
	$(".message_repasttime").focus(function(){
		if(timeLimit.lowDate == ""){               //============>当返回的lowdate为空时
			oBefore=0;
		}else{
			oBefore=timeLimit.lowDate;
		}
		if(timeLimit.upDate == "" || timeLimit.upDate == 0){
			oLast=60;
		}else{
			oLast=timeLimit.upDate;
		}
		WdatePicker({ 
			errDealMode:2,
			skin:'whyGreen',
			minDate:'%y-%M-{%d+'+oBefore+'}',
			maxDate:'%y-%M-{%d+'+oLast+'}',
			onpicking:function(dp){dateBoxChange();showDate(dp.cal.getNewDateStr())}
		});
		
		$(".message_repasttime").keydown(function(e){
		    if(e.which==8){
		    e.preventDefault();//取消回车键原有事件。
		}
		});
		
	});
	$("#selHour").append("<option value='' selected>小时</option>");
	$("#selMin").append("<option value='' selected>分钟</option>");
	

//	$("#selHour").change(function(){
//		oHour=$(this).val();
//		$("#selMin").val("");
//	});
//	$("#selMin").change(function(){
//		oMin=$(this).val();
//	});
	
	

	/******end******/
	
	
});


function abc(busytimelist,c,openT,closeT){
	openT.replace("0.00","00.00");
	if(parseInt(openT.split(":")[0])<10){
		openT = 0+openT;
	}
	var darr = [];
	var hours = [00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
	var mins = [00,15,30,45];
	for(var j=0;j<hours.length;j++){
		var h = hours[j]+"";
		var o = new Object();
		o.arr = mins;
		o.key = h;
		darr.push(o);
	}
	
		
	
	
	var oh=null,om=null,ch=null,cm=null;
	if((openT && openT!="") && (!closeT || closeT=="")){
		oh = parseInt(openT.substring(0,2).replace(/^0/,""));
		om = parseInt(openT.substring(3,5).replace(/^0/,""));
		darr = darr.slice(darr.indexOf(oh),hours.length);
	}else if((closeT && closeT!="") && (!openT || openT=="")){
		ch = parseInt(closeT.substring(0,2).replace(/^0/,""));
		cm = parseInt(closeT.substring(3,5).replace(/^0/,""));
		darr = darr.slice(0,darr.indexOf(ch)+1);
	}else if((closeT && closeT!="") && (openT && openT!="")){
		oh = parseInt(openT.substring(0,2).replace(/^0/,""));
		om = parseInt(openT.substring(3,5).replace(/^0/,""));
		ch = parseInt(closeT.substring(0,2).replace(/^0/,""));
		cm = parseInt(closeT.substring(3,5).replace(/^0/,""));
		darr = darr.slice(darr.indexOf(oh),darr.indexOf(ch)+1);
	}
	
	mins = [00,15,30,45];
	if(om!=null && oh!=null){
		var _oind = mins.indexOf2(om);
		mins = mins.slice(_oind,mins.length);
		darr[darr.indexOf(oh)].arr = mins;	
	}
	mins = [00,15,30,45];
	if(cm!=null && ch!=null){  
		var _cind = mins.indexOf2(cm);
		if(_cind>0){
			mins = mins.slice(0,_cind+1);
		}else{
			mins = [mins[_cind]];
		}
		if(mins.length > 0){
			darr[darr.indexOf(ch)].arr = mins;
		}else{
			darr.remove(ch);	
		}
	}
	
	
	for(var i=0;i<busytimelist.length;i++){
		var busyTimeEnd = busytimelist[i].busyTimeEnd;
		var busyTimeStart = busytimelist[i].busyTimeStart;
		var date = busyTimeStart.match(/.{10}/);
		var shour = parseInt(busyTimeStart.substring(11,13).replace(/^0/,""));
		var smin = parseInt(busyTimeStart.substring(14,16).replace(/^0/,""));
		var ehour = parseInt(busyTimeEnd.substring(11,13).replace(/^0/,""));
		var emin = parseInt(busyTimeEnd.substring(14,16).replace(/^0/,""));
		
			if(darr.length == 0){
				break;
			}
			
		if(date == c){
			for(var j=0;j<hours.length;j++){
				if(shour<hours[j] && hours[j]<ehour){
					darr.remove(hours[j]);
					continue;
				}
				if(smin==0 && shour==hours[j]){
					darr.remove(hours[j]);
					continue;
				}
				mins = [00,15,30,45];
				if(shour == hours[j] && ehour != hours[j]){
					var _ind = mins.indexOf2(smin);
					mins = mins.slice(0,_ind);
					if(darr.indexOf(shour) > -1){
						darr[darr.indexOf(shour)].arr = mins;
					}
				}else if(ehour == hours[j] && shour != hours[j]){
					var _ind = mins.indexOf2(emin);
					mins = mins.slice(_ind+1, 4);
					if(mins.length > 0 && darr.indexOf(ehour) > -1){
						darr[darr.indexOf(ehour)].arr = mins;
					}else{
						darr.remove(hours[j]);	
					}
				}
				
			}
		}
	}
	return darr;
	

	
	
}
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i].key == val) return i;
    }
    return -1;
};
 Array.prototype.indexOf2 = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
function changtime(){
	$("#selMin").empty();
	for(var i=0 ; i<timeArr.length; i++){
		var key = timeArr[i].key.length==1?"0"+timeArr[i].key:timeArr[i].key;
		if(key == $("#selHour").val()){
			var arr = timeArr[i].arr;
			$("#selMin").append("<option value=''>分钟</option>");
			for(var j=0;j<arr.length;j++){
				var min = (arr[j]+"").length==1?"0"+arr[j]:arr[j];
				$("#selMin").append("<option value='"+min+"'>"+min+"</option>");
			}
		}
	}
	
}


function mesnumsub(){
	var oMesSubCheck=0;
	if($(".ormess_tellb").attr('checked')!=undefined){
		phonetest();
	}
	function vals(phonenum){
		var myreg=/^1\d{10}$/;
		return myreg.test(phonenum);
		}
	function phonetest(){
		var oVal=$(".message_basic_namete").val();			
		if(vals(oVal)){
			$(".meserti").empty();
			}else{
				$("#phone").focus();
				if($("#phone").val() == ""){
					$(".meserti").text("请填写联系电话");
					oMesSubCheck=1;
				}else{
					$(".meserti").text("手机号码格式不对，请重新输入");
					oMesSubCheck=1;
				}
			
			}
		}
	return oMesSubCheck;
}
//手机号验证
function mesnum(){
	var oMesCheck=0;
	$(".mes_othtell").show();
	function vals(phonenum){
		var myreg=/^1\d{10}$/;
		return myreg.test(phonenum);
		}
	function phonetest(){
		//记录点击跳转的按钮
		if(analyze){
			analyze.addBds('发送验证码','20205','BUTTON');
		};
		var oVal=$(".message_basic_namete").val();			
		if(vals(oVal)){
			$(".meserti").empty();
			showValidateCode();
		}else{
			if($("#phone").val() == ""){
				oMesCheck=1;
				$("#phone").focus();
				$(".meserti").text("请填写联系电话");
			}else{
				oMesCheck=1;
				$("#phone").focus();
				$(".meserti").text("手机号码格式不对，请重新输入");
			}	
		}
	}
	$(".mes_testbtn").bind("click",phonetest);
	return oMesCheck;	
}
function showtime(){
	var oShowCheck=0;
	$(".message_repastnum").text()=="";
	$(".message_repasttime").text()=="";
	$(".messet var").text()=="";
		
	
	//用餐时间
	if($(".message_repasttime").val()==""){
		$(".mestime var").text("请选择用餐日期");
		oShowCheck=1;
	}else if($("#selHour").val() == "" || $("#selMin").val() == ""){
		$(".mestime var").text("请选择用餐时间");
		oShowCheck=1;
	}else{
		$(".mestime var").empty();
	}
	
	return oShowCheck;	
}    
function dateBoxChange(){
	$("#selHour").val("");
	$("#selMin").val("");
}
function showDate(date){
	var oTrueTime=date;
	oLastSelTime=date;
	var oDaytime=$("#d12").val();
	var redel=/-/g;
	oDaytime=oDaytime.replace(redel,"");
	oLastSelTime=oLastSelTime.replace(redel,"");
	if(oDaytime != oLastSelTime){
		$("#selHour").empty();
		$("#selHour").append("<option value='' selected>小时</option>");
		/*14.2.26添加*/
		$("#selMin").empty();
		$("#selMin").append("<option value='' selected>分钟</option>");
	}
	var a = timeLimit;
	iHour = iHour<10?"0"+iHour:iHour;
	iMinute = iMinute<10?"0"+iMinute:iMinute;
	kai=timeLimit.lowhour;
	if(oLastSelTime == currentYear.replace(/\-/g,"")){
		var oNowtime=iHour+""+iMinute;
		var oLowtime=timeLimit.lowhour.replace(":","");
		if(parseInt(oNowtime)>parseInt(oLowtime)){
			if(iHour<10){
				iHour="0"+iHour;
			}
			if(iMinute<10){
				iMinute="0"+iMinute;
			}
			if(parseInt(kai.replace(":",""))<=parseInt(oNowtime)){
				kai = oNowtime.substring(0,2)+":"+oNowtime.substring(2,4);
			}else{
				kai=iHour+":"+iMinute;
			}
			
		}
	}
	$("#selMin").empty();
	$("#selHour").empty();
	selHour=oTrueTime;	
	//timeArr = abc(a.busytimelist,selHour,kai,timeLimit.uphour);
	$("#selHour").append("<option value=''>小时</option>");
	$("#selMin").append("<option value=''>分钟</option>");
	createTimes(a.busytimelist,selHour,kai,timeLimit.uphour);
//		for(var i=0 ; i<timeArr.length; i++){
//			var key = timeArr[i].key.length==1?"0"+timeArr[i].key:timeArr[i].key;
//			$("#selHour").append("<option value='"+key+"'>"+key+"</option>");
//		}
//		var oSlecHour=$("#selHour").children();
//		if(oSlecHour.length == 1){
//			showAlert2(storeBusyMsg);
//		}
}


function createTimes(busyTime,selHour,opentime,closetime){
	//添加小时
	var sHour = parseInt(opentime.split(":")[0]);
	var sMin = parseInt(opentime.split(":")[1]);
	var eHour = parseInt(closetime.split(":")[0]);
	var eMin = parseInt(closetime.split(":")[1]);
	var oHour = $("#selHour").val();
	for(var i=sHour; i<=eHour; i++){
		var x=(i<10)?("0"+i):(i);
		$("#selHour").append("<option value='"+x+"'>"+x+"</option>");
	}
	$("#selHour").bind("change",function(){
		creatMin(sHour,eHour);
	});
	if(oHour != "小时"){
		creatMin(sHour,eHour);
	}
	function creatMin(sHour,eHour){
		$("#selMin").empty();
		$("#selMin").append("<option value=''>分钟</option>");
		//添加分钟
		for(var i=0; i<60; i+=15){
			if( i == 0){
				$("#selMin").append("<option value='"+i+"'>0"+i+"</option>");
			}else{
				$("#selMin").append("<option value='"+i+"'>"+i+"</option>");
			}
		}
		//分钟删除临界值
		var oHourval=parseInt($("#selHour").val());
		if(oHourval ==parseInt(sHour)){
			var oLength = $("#selMin").children().length-1;
			for(var i=0; i<oLength; i++){
				var oMin = $("#selMin").children().eq(oLength-i).val();
				if(parseInt(sMin) > parseInt(oMin)){
					$("#selMin").children().eq(oLength-i).remove();
				}
			}
		}else if(oHourval == parseInt(eHour)){
			for(var i=0; i<$("#selMin").children().length; i++){
				if(parseInt(eMin) < parseInt($("#selMin").children().eq(i).val())){
					$("#selMin").children().eq(i).remove();
				}
			}
		} 
	}
	$("#selMin").blur(function(){
		if($("#selHour").val() != "小时" || $("#selMin").val() != "分钟"){
			var newSelTimeand = $("#d12").val()+$("#selHour").val()+$("#selMin").val();
			var newSelTime = newSelTimeand.replace(/\-/g,"");
			var newDate = $("#d12").val();
			var newHour = parseInt($("#selHour").val());
			var newMin = parseInt($("#selMin").val());
			//用户选择时间去匹配繁忙时间
			for(var i=0; i<busyTime.length; i++){
				var busyTimeEnd = busyTime[i].busyTimeEnd;
				var busyTimeStart = busyTime[i].busyTimeStart;
				var date = busyTimeStart.substring(0,10);
				var shour = parseInt(busyTimeStart.substring(11,13).replace(/^0/,""));
				var smin = parseInt(busyTimeStart.substring(14,16).replace(/^0/,""));
				var ehour = parseInt(busyTimeEnd.substring(11,13).replace(/^0/,""));
				var emin = parseInt(busyTimeEnd.substring(14,16).replace(/^0/,""));	
				if (newDate == date) {
					if(newHour>shour && newHour<ehour){
						goRow=true;
					}else if(newHour == shour && newMin>=smin){
						goRow=true;
					}else if(newHour == ehour && newMin<=emin){
						goRow=true;
					}
				}
			}
		}
	});
}