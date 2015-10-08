//设置cookie方法
function setCookie(name,value){
	var Days =30;
	var exp =new Date();
	exp.setTime(exp.getTime()+Days*24*60*60*1000);
	document.cookie = name +"="+encodeURIComponent(value)+";expires="+exp.toUTCString();
}

//获取cookie方法
function getCookie (name){
	var arr,reg =new RegExp("(^|)"+name+"=([^;]*)(;|$)");
	if(arr = document.cookie.match(reg)){
		return decodeURIComponent(arr[2]);
	}else{
		return null;
	}
}
//删除cookie方法
function delCookie(name){
	var exp =new Date();
	exp.setTime(exp.getTime()-1);
	var cval =getCookie(name);
	if(cval!=null){
		document.cookie = name+ "="+cval+";expires="+exp.toUTCString();
	}
}
var loadingTimer;
function ajaxRequestFun(url,data,callback){
	showLoading();
	$.ajax({
		type : "post",
		url : path + "/"+url,
		dataType : "json",
		data : data,
		success : function(data) {
			hideLoading();
			callback(data);
		},
		error : function(e){
			hideLoading();
			showAlert2("获取数据失败");
		}
	});
}

//显示提示框
function showAlert(title1,title2,text,okCallback,noCallback){
	var openDiv = $("<div class='ordermesopen alertBox' style='display:block;'></div>");
	var mainDiv = $("<div class='ordermesomain'></div>");
	var closeDiv = $("<em></em>");
	closeDiv.bind("click",function(){
		openDiv.remove();
	});
	if(!okCallback){
		okCallback = function(){
			openDiv.remove();
		};
	}
	var titleH3 = $("<h3>提示</h3>");
	var textP = $("<p>"+text+"</p>");
	var okInput = $("<input type='button' value='"+title1+"' class='analyze_parent' desction='"+title1+"' code='20213' />");
	okInput.bind("click",okCallback);
	var noInput = $("<input type='button' value='"+title2+"' class='ormesopendish analyze_parent' desction='"+title2+"' code='20214' />");
	noInput.bind("click",function(){
		openDiv.remove();
		noCallback();
	});
	var clearDiv = $("<div class='clear'></div>");
	mainDiv.append(closeDiv);
	mainDiv.append(titleH3);
	mainDiv.append(textP);
	mainDiv.append(okInput);
	mainDiv.append(noInput);
	mainDiv.append(clearDiv);
	openDiv.append(mainDiv);
	$("body").append(openDiv);
}
function showAlert2(text,btnText,okCallback){
	if($(".alertBox").size() > 0){
		return;
	}
	var openDiv = $("<div class='ordermesopen2 alertBox'></div>");
	var mainDiv = $("<div class='ordermesomain2'></div>");
	var closeDiv = $("<em></em>");
	closeDiv.bind("click",function(){
		openDiv.remove();
		/*使关闭按钮同样执行确定按钮的回调函数*/
		okCallback();
	});
	if(!okCallback){
		okCallback = function(){
			openDiv.remove();
		};   
	}
	if(!btnText){
		btnText = "确定";
	}
	var titleH3 = $("<h3>提示</h3>");
	var textP = $("<p>"+text+"</p>");
	var okInput = $("<input type='button' class='ormesopendish' value='"+btnText+"' />");
	okInput.bind("click",function(){
		openDiv.remove();
		okCallback();
	});
	var clearDiv = $("<div class='clear'></div>");
	mainDiv.append(closeDiv);
	mainDiv.append(titleH3);
	mainDiv.append(textP);
	mainDiv.append(okInput);
	mainDiv.append(clearDiv);
	openDiv.append(mainDiv);
	$("body").append(openDiv);
}
//显示loading条
function showLoading(){
	if($(".loadingBox").size() == 0){
		var openDiv = $("<div class='ordermesopen2 loadingBox'></div>");
		var tickerDiv = $("<div class='ticker'></div>");
		openDiv.append(tickerDiv);
		$("body").append(openDiv);
	}
	var x = 0;
	loadingTimer = setInterval(function(){
		x -= 50;
		x<=-395?x=0:"";
		$(".ticker").css("background-position", x+"px"+" 0");
	},100);
}
function hideLoading(){
	$(".loadingBox").remove();
	clearInterval(loadingTimer);
}
function goLocation(locStr) {
//	this.location.href = locStr;
	var url = serverAndPort+sns_url;
	var gotoaction = (locStr.indexOf("?")>0)?(rootUrl+locStr+"&newweb=1"):(rootUrl+locStr+"?newweb=1");
	gotoaction = encodeURI(gotoaction);
	var base64Encode = encode64(gotoaction);
	this.parent.location.href = url+base64Encode;
}

var goonUrl = "";
function goLocationWithCity22(locStr) {
//	this.location.href = locStr;
	var tprm="pid99="+$(".order_active").val();
    __ozfac2(tprm,"#getstore");
	var url = serverAndPort+sns_url;
	var gotoaction = (locStr.indexOf("?")>0)?(rootUrl+locStr+"&newweb=1"):(rootUrl+locStr+"?newweb=1");
	gotoaction = encodeURI(gotoaction);
	var base64Encode = encode64(gotoaction);
	goonUrl = url+base64Encode;
	setTimeout("goon()", 500);
}
function goon() {
	this.parent.location.href = goonUrl;
}
function goCaterIndex(){
	this.parent.location.href = serverAndPort;
}
//保留小数点后两位  [如果获取到的是数字类型  调用时 Splice(String(str))]
function Splice(str){
	if(str.indexOf('.')!=-1){
		str1 = str.split('.')[0];
		str = str.split('.')[1].split('');
		if(str.length<2){
			str.splice(2,0,0);
		}
		else if(str.length>2){
			str.splice(2,str.length-2);
		}
		return str1+'.'+str.join('');
	}
	else{
		str = str.split('');
		str.splice(str.length,0,'.',0,0);
		return str.join('');
	}
}
/*获取头部参数函数*/
var getArgs = function ()
	{
		var args = new Object( ); //声明一个空对象
		var query = window.location.search.substring(1); // 取查询字符串，如从http://www.snowpeak.org/testjs.htm?a1=v1&a2=&a3=v3#anchor 中截出 a1=v1&a2=&a3=v3。
		var pairs = query.split("&"); // 以 & 符分开成数组
		for(var i = 0; i < pairs.length; i++) {
		var pos = pairs[i].indexOf('='); // 查找 "name=value" 对
		if (pos == -1) continue; // 若不成对，则跳出循环继续下一对
		var argname = pairs[i].substring(0,pos); // 取参数名
		var value = pairs[i].substring(pos+1); // 取参数值
		value = decodeURIComponent(value); // 若需要，则解码
		args[argname] = value; // 存成对象的一个属性
	}
	return args; // 返回此对象
};
$(function(){
var userId = sessionStorage.getItem("userId");
if(userId){
	var  _ozuid=userId;
}
var _oztime = new Date().getTime();
});


new_element=document.createElement("script");
new_element.setAttribute("type","text/javascript");
new_element.setAttribute("src","/Cater/resources/js/o_code.js");
document.body.appendChild(new_element);