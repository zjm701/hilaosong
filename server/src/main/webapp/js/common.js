var tmpjson;
var nowpage;
if(typeof($.cookie("cityid")) == 'undefined' || $.cookie("cityid") == 'null' || $.cookie("cityid") == null ){
	$.cookie("cityname", "北京市", { expires: 30 }); 
	$.cookie("cityid", "110000", { expires: 30 }); 
	$.cookie("provinceid", "110000", { expires: 30 }); //lt:20150915
	$('#cityname').html($.cookie("cityname"));
}
if(typeof($.cookie("storeid")) == 'undefined' || $.cookie("storeid") == 'null' || $.cookie("storeid") == null || $.cookie("storeid") == '' ){
	$.cookie("storeid", "", { expires: 30 }); 
}
if(typeof($.cookie("storeid1")) == 'undefined' || $.cookie("storeid1") == 'null' || $.cookie("storeid1") == null || $.cookie("storeid1") == '' ){
	$.cookie("storeid1", "", { expires: 30 }); 
}
var cityid = $.cookie("cityid");
var provinceid = $.cookie("provinceid");//lt:20150915
var cityname = $.cookie("cityname");
var storeid = $.cookie("storeid");
var storeid1 = $.cookie("storeid1");
var catid;
var userid;
var username;
var orderid;
var packid;
var packname;
var packprice;
var carttotal = 0;

//alert(cityname);

$(document).ready(function(e) {
	 $.ajaxSetup ({
	   cache: false 
	 });
	//页面加载
	if(nowpage != 'index' && nowpage != 'dish' && nowpage != 'pack'){
		//getcurrentuser();
		//if(getuser()){return false;}
	}
	if(nowpage == 'index'){
		//getcurrentuser();
		var logintmp = GetQueryString('loginId');
		//alert(logintmp);
		if(typeof(logintmp) != 'undefined' && logintmp !=null && logintmp !='null'){
			$.ajax({
				url: apiurl+'getuserinfo?loginId='+logintmp,
				type: 'GET',
				dataType: 'JSON',//here
				success: function (data) {
					
					//alert(JSON.stringify(data));
					
					if(typeof(data.loginId)=='undefined'){
						$.cookie("userid", data.user.user_entity_id, { expires: 30 }); 
						$.cookie("username", data.user.nickname, { expires: 30 }); 
						$('#userinfo').load('userinfo.html?&randnum='+Math.random()+'');
					} else {
						//未登录或者未返回
					}
				}
			});
		}
	}
	$('#userinfo').load('userinfo.html?&randnum='+Math.random()+'');
});

function getcurrentuser(){
	$.ajax({
        url: apiurl+'getcurrentuser',
        type: 'GET',
        dataType: 'JSON',//here
        success: function (data) {
			if(typeof(data.error)=='undefined'){
				//var _html = '';
				if(typeof(msg.loginID) != 'undefined'){
				$.cookie("userid", msg.loginID, { expires: 30 }); 
				$.cookie("username", msg.nickname, { expires: 30 });
				
			} else {
				//$('#userinfo').html('登录失败！');
				noticeinfo('请您登录！');
			}
			
			}
        }
    });
}

function clearcate(){
	$.cookie("storeid1", null, { expires: 30 }); 
	$.cookie("userformjson", null, { expires: 30 }); 
	$.cookie('cartdish', null, { expires: 30 }); 
	$.cookie('cartpack', null, { expires: 30 });
	$.cookie('cartdiyguodi', null, { expires: 30 });
	$('#userinfo').load('userinfo.html?&randnum='+Math.random()+'');
}

Array.prototype.remove=function(dx) 
{ 
    if(isNaN(dx)||dx>this.length){
    	return false;
    }
    for(var i=0,n=0;i<this.length;i++){ 
        if(this[i]!=this[dx]){ 
            this[n++]=this[i];
        } 
    }
    this.length-=1;
}

function GetQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) return unescape(r[2]); return null;
} 

function addcookie(name,value,expireHours){
	var cookieString=name+"="+escape(value)+"; path=/";
	//判断是否设置过期时间
	if(expireHours>0){
		var date=new Date();
		date.setTime(date.getTime+expireHours*3600*1000);
		cookieString=cookieString+"; expire="+date.toGMTString();
	}
	document.cookie=cookieString;
	setstorage(name,value);
}

function getcookie(name){
	var strcookie=document.cookie;
	var arrcookie=strcookie.split("; ");
	for(var i=0;i<arrcookie.length;i++){
	var arr=arrcookie[i].split("=");
	if(arr[0]==name)return arr[1];
	}	
	var cval = getstorage(name);
	if(cval!=null){
		addcookie(name,cval);
		return cval;
	}
	return "";
}

function delCookie(name){//删除cookie
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getcookie(name);
	if(cval!=null) document.cookie= name + "="+cval+"; path=/;expires="+exp.toGMTString();
	delstorage(name);
}

function checklogin(state){
	if(state == 0){
		//location.href = WapSiteUrl+'/tmpl/member/login.html';
		//loadMainbody('&c=members&a=login');
		return false;
	}else {
		return true;
	}
}

function contains(arr, str) {
    var i = arr.length;
    while (i--) {
           if (arr[i] === str) {
           return true;
           }   
    }   
    return false;
}

function setstorage(objName,objValue,callback){//添加cookie
	var callback = callback || function(){};
	if(window.localStorage){
		window.localStorage.setItem(objName,objValue);
	}else{
		 //log('setstorage-->This browser does NOT support localStorage');
		 //alert('setstorage-->This browser does NOT support localStorage');
		 //addcookie(objName,objValue);
	}
}
function getstorage(objName){//获取指定名称的cookie的值
	var result = '';
	if(window.localStorage){
		result = window.localStorage.getItem(objName);
	}else{
		 //alert('getstorage-->This browser does NOT support localStorage');
		 //getcookie(objName);
	}
	return result;
}

function clearstorage(){
	if(window.localStorage){
		window.localStorage.clear();
	}else{
		 //alert('clearstorage-->This browser does NOT support localStorage');
		 //clearcookie();
	}
} 

function delstorage(objName){//删除cookie
	if(window.localStorage){
		window.localStorage.removeItem(objName);
	}else{
		 //alert('delstorage-->This browser does NOT support localStorage');
		 //delCookie(objName);
	}
}


function getcities(m){
	var content;
	var k = '#catul';
	$.ajax({
        url: apiurl+'getcities',
        type: 'GET',
        dataType: 'JSON',//here
        success: function (data) {
			if(typeof(data.error)=='undefined'){
				var _html = '';
				$.each(data,function(index,obj){
					//lt:20150915
					_html += '<li onclick="getcategories(\''+obj.cityId+'\',\''+k+'\',\''+obj.city+'\',\''+obj.provinceId+'\');" data-val="'+obj.cityId+'">'+obj.city+'</li>';
					//lt:20150915 end
					//_html += '<li onclick="getcategories(\''+obj.cityId+'\',\''+k+'\',\''+obj.city+'\');" data-val="'+obj.cityId+'">'+obj.city+'</li>';
				});
				$(m).html(_html);
			
			}
        }
    });
}
function getareastore(n){
	var tmp = 0;
	storeid1 = $.cookie("storeid1");
	if(storeid1){
		$.cookie("storeid", storeid1, { expires: 30 });
		return false;
	}
	$.ajax({
        url: apiurl+'getareastore?cityId='+n,
        type: 'GET',
		async: false ,
        dataType: 'JSON',//here
        success: function (data) {
			if(typeof(data.error)=='undefined'){
				tmp = data.storeId;
				$.cookie("storeid", tmp, { expires: 30 });
				//alert(tmp);
				//return tmp;			
			}
        }
    });

}
function getcategories(n,m,l,q){//lt:20150915
	var content;
	var needrefresh = 0;
	var k = '#dishul';
	catid = $.cookie("catid");
	getareastore(n);
	if(n != cityid){
		clearcate();
		needrefresh = 1;
	}
	storeid = $.cookie("storeid");
		
	//if(typeof(storeid) == 'undefined' || storeid == 'null' || storeid == null || storeid == '' ){
	//	storeid = getareastore(n);
	//} else {
		//$.cookie("storeid", "0201", { expires: 30 }); 
	//}
	//alert(storeid);
	
	$.ajax({
        url: apiurl+'getcategories?storeId='+storeid,
//        url: apiurl+'getcategories?storeId=0201',
//        url: rootUrl+"json/getcategories.json",
        type: 'GET',
		async: false ,
        dataType: 'JSON',//here
        success: function (data) {
			if(typeof(data.error)=='undefined'){
				var _html = '';
				$.each(data,function(index,obj){
					if(obj.dishTypeId == '012'){
						if(catid == '012'){
							_html += '<li onclick="getpacks(\'012\',\''+k+'\',\'1\',\''+storeid+'\',\'套餐\');getcatstyle(this);" data-val="012" class = "li_hover">套餐</li>';
						} else {
							_html += '<li onclick="getpacks(\'012\',\''+k+'\',\'1\',\''+storeid+'\',\'套餐\');getcatstyle(this);" data-val="012">套餐</li>';
						}
					} else {
					  if(catid == obj.dishTypeId){
						  _html += '<li onclick="getdishes(\''+obj.dishTypeId+'\',\''+k+'\',\'1\',\''+storeid+'\',\''+obj.dishTypeName+'\');getcatstyle(this);" data-val="'+obj.dishTypeId+'" class="li_hover">'+obj.dishTypeName+'</li>';
					  } else {
					  _html += '<li onclick="getdishes(\''+obj.dishTypeId+'\',\''+k+'\',\'1\',\''+storeid+'\',\''+obj.dishTypeName+'\');getcatstyle(this);" data-val="'+obj.dishTypeId+'">'+obj.dishTypeName+'</li>';
					  }
					}
				});
				
				$(m).html(_html);
				$.cookie("cityname", l, { expires: 30 }); 
				$.cookie("cityid", n, { expires: 30 }); 
				$.cookie("provinceid", q, { expires: 30 }); //lt:20150915
				//alert(l);
				//alert($.cookie("cityid"));
				cityid = $.cookie("cityid");
				provinceid = $.cookie("provinceid");//lt:20150915
				$('#cityname').html(l);
				$.fancybox.close();
				if(needrefresh == 1){
					window.history.go(0);
				}
			
			}
        }
    });
}
function getcatstyle(n){
	$(n).parent().find('li').removeClass('li_hover');
	$(n).addClass('li_hover');
}
function getpacks(n,m,l,p,q){
	//l表示分页
	var content;
	//var k = '#dishul';
	//alert(n);
	if(nowpage != 'pack'){
		$.cookie("catid", n, { expires: 30 }); 
		$.cookie("catname", q, { expires: 30 }); 
		//window.location = 'pack.html';
		goLocationWithCity('pack.html');
		return;
	}
	
	$.ajax({
        url: apiurl+'getpacks?storeId='+p+'&catId='+n+'&pageIndex='+l,
//        url: apiurl+'getpacks?storeId=0201'+'&catId='+n+'&pageIndex='+l,
        type: 'GET',
		async: false ,
        dataType: 'JSON',//here
        success: function (data) {
			//alert(JSON.stringify(data));
			if(typeof(data.error)=='undefined'){
				var _html = '';
				var _html1 = '';
				_html1 = '<div class="catname">'+q+'</div>';
				$.each(data,function(index,obj){
					obj.unitPrice = (Math.round(obj.unitPrice*100)/100);
					_html += '<li onclick="" data-val="'+obj.dishId+'"><img src="'+obj.bigImageAddr+'" width="100" height="100"><div class="cp_titel">';
					_html += '<span class="f_c6000a f14px fB oh14">'+obj.storeDishName+'</span>  <span class="f_c6000a f14px oh14">价格：'+obj.unitPrice+'</span><div class="clear"></div>';
					_html += '<div class=" p_t_35"><div class="i_box">';
					/*if(obj.type == '1'){
					  if(typeof(obj.halfDishId) == 'undefined'){
					  } else {
						  _html += '<span onclick="addcarthalf(this);" data-id="'+obj.halfDishId+'"  data-name="'+obj.storeDishName+'半份" data-price="'+obj.halfPrice+'">半份选择</span>';
					  }
					 _html += ' <div class="liright"><span class="dishd J_minus" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" data-price="'+obj.unitPrice+'" onclick="cartdishd(this);">-</span> ';
					 //_html += '  <span class="dishnum">0</span>  ';
					 _html += ' <span class="dishp J_add" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" data-price="'+obj.unitPrice+'" onclick="cartdishp(this);">+</span></div> ';
					} else if(obj.type == '2'){
						_html += '<span class="dishpack" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" data-price="'+obj.unitPrice+'" onclick="cartpack(this);" >套餐选择</span>';
					}*/
					_html += '<span class="dishpack" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" data-price="'+obj.unitPrice+'" onclick="cartpack(\''+obj.dishId+'\',\''+obj.storeDishName+'\',\''+obj.unitPrice+'\');" >套餐选择</span>';
					_html += '</div><div class="clear"></div></div>';
					_html +='</div></li>';
				});
				$.cookie("catid", n, { expires: 30 }); 
				$.cookie("catname", q, { expires: 30 }); 
				catid = n;
				catname = q;
				$(m).html(_html1+_html);
				$(m +" li[data-val='"+n+"']").addClass('li_hover');
				if(_html == ''){
					$(m).html(_html1+'<div class="alert alert-danger" role="alert" id="noticecon">暂无</div>');
				}
				$.ajax({
				  url: apiurl+'cntpacks?storeId='+p+'&catId='+catid+'',
//				  url: apiurl+'cntpacks?storeId=0201&catId='+catid+'',
				  type: 'GET',
				  dataType: 'JSON',//here
				  success: function (data) {
						  //alert(JSON.stringify(data));
					  if(typeof(data.error)=='undefined'){
						  var pagetotal = data.totalPagesCount;
						  $("#pager").pager({ pagenumber: l, pagecount: pagetotal, buttonClickCallback: PageClick });
		  
					  
					  }
				  }
			  });
			
			}
        }
    });
}
//zs:1 选择自备锅底事件
function addOnselfListenter(n){
	$('#t_'+n).click(function(){
		var me = $(this);
		var v = me.attr('checked');
		//如果选择自备
		if(v=='checked'){
			//判断菜蓝里是否存在
			$.cookie("onselfClick"+n,"true")
			var b = false;
			$('#cartbox').children().each(function(){
				//<div id="20017_0201" class="cartli" type="001">
				var type = $(this).attr('type');
				if(type == n){
				//var name = $(this).children('.carttitle').text();
				//if(name.indexOf('锅')>0){
					b = true;
				}
			});
			if(b){
				me.attr("checked",false);
				alert(_onself[n].show);
			}
			_onself[n].isSelect = true;
		}else{
			$.cookie("onselfClick"+n,"false")
			_onself[n].isSelect = false;
		}
		//计算外送费
		//calculateOutsidetotle();
	});
}
//计算外卖费用
function calculateOutsidetotle(){
	var off = 0.1;
	var onc=$('#t_001');
	//<li data-val="10001_0201" onclick="" type='001' >
	if(onc.attr('checked')=='checked'){
		off = 0;
	}
	TotleUtils.all(off);
}
function getGuodiCount(){
	return getCountByType("001")+getCountByType("012");
}
function getCountByType(n){
	var count1 = 0;
	$('#cartbox').children().each(function(){
		var me = $(this);
		var type = me.attr('type');
		if(type == n){
			var num = me.children(".cartprice").children(".num").text();
			count1 += parseInt(num);
		}
	});
	return count1;
}
//自备锅底小料常量
var _onself = {'001':{
		isSelect:false,
		html:'<span class="oneself" id="oneself"><input class="oneself_check" type="checkbox" id="t_001"><span>自备锅底</span></span>',
		show:'选择自备锅底，请先删除锅底',
		adder:"选择了自备锅底，不可选择锅底",
		adder2:"请选择锅底，如果确认不需要请选择自备锅底"
	},
	'007':{
		isSelect:false,
		html:'<span class="oneself" id="oneself"><input class="oneself_check" type="checkbox" id="t_007"><span>自备小料</span></span>',
		show:'选择自备小料，请先删除小料',
		adder:"选择了自备小料，不可选择小料",
		adder2:"请选择小料，如果确认不需要请选择自备小料"
	}
}
//end....
function getdishes(n,m,l,p,q){
				var _html = '';
				var _html1 = '';
				_html1 = '<div class="catname">'+q+'</div>';
	//zs:2显示自备锅底和小料
	$('#oneself').remove()
	if(n=='001' ||n == '007'){
		var cx = _onself[n].html;
		_html1 = '<div class="catname">'+q+''+cx+'</div>';
	}
	//end..........
	//l表示分页
	var content;
	//var k = '#dishul';
	//alert(n);
	if(nowpage != 'dish'){
		$.cookie("catid", n, { expires: 30 }); 
		$.cookie("catname", q, { expires: 30 }); 
		//window.location = 'dish.html';
		goLocationWithCity('dish.html');
		return;
	}
	
	$.ajax({
        url: apiurl+'getdishes?storeId='+p+'&catId='+n+'&pageIndex='+l,
 //       url: apiurl+'getdishes?storeId=0201&catId='+n+'&pageIndex='+l,
 //       url:rootUrl+"json/getdishes.json",
        type: 'GET',
		async: false,
        dataType: 'JSON',//here
        success: function (data) {
				//alert(JSON.stringify(data));
			if(typeof(data.error)=='undefined'){
				$.each(data,function(index,obj){
					obj.unitPrice = (Math.round(obj.unitPrice*100)/100);
					_html += '<li onclick="" data-val="'+obj.dishId+'" type="'+n+'"><img src="'+obj.bigImageAddr+'" width="100" height="100"><div class="cp_titel">';
					_html += '<div class="cp_title1"><span class="f_c6000a f14px fB oh14">'+obj.storeDishName+'</span>  <span class="f_c6000a f14px oh14">一份价格：'+obj.unitPrice+'元';
					_html += '<div class="right num"><img src="images/img_jian.gif" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'"  type="'+n+'"data-price="'+obj.unitPrice+'" onclick="cartdishd(this);">   <img src="images/img_jia.gif" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" type="'+n+'" data-price="'+obj.unitPrice+'" onclick="cartdishp(this);"> </div></span><div class="clear"></div>';//lt:20150919 修改菜品列表界面
					if(typeof(obj.halfDishId) == 'undefined'){
					  } else {
						  obj.halfPrice = (Math.round(obj.halfPrice*100)/100);
						  _html += '<span class="f_c6000a f14px oh14">半份价格：'+obj.halfPrice+'元';
						  _html += '<div class="right num"><img src="images/img_jian.gif" data-id="'+obj.halfDishId+'" data-name="'+obj.storeDishName+'半份"  type="'+n+'"data-price="'+obj.halfPrice+'" onclick="cartdishd(this);">   <img src="images/img_jia.gif" data-id="'+obj.halfDishId+'" data-name="'+obj.storeDishName+'半份" type="'+n+'" data-price="'+obj.halfPrice+'" onclick="cartdishp(this);"> </div></span><div class="clear"></div>';//lt:20150919 修改菜品列表界面
					  }
					_html += '</div><div class=" p_t_20"><div class="i_box">';
					//if(obj.type == '1'){
						/*//lt:20150919 修改菜品列表界面
					  if(typeof(obj.halfDishId) == 'undefined'){
					  } else {
						  _html += '<span onclick="addcarthalf(this);" data-id="'+obj.halfDishId+'"  data-name="'+obj.storeDishName+'半份" type="'+n+'" data-price="'+obj.halfPrice+'">半份选择</span>';
					  }
					 _html += ' <div class="liright"><span class="dishd J_minus" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'"  type="'+n+'"data-price="'+obj.unitPrice+'" onclick="cartdishd(this);">-</span> ';
					 //_html += '  <span class="dishnum">0</span>  ';
					 _html += ' <span class="dishp J_add" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" type="'+n+'" data-price="'+obj.unitPrice+'" onclick="cartdishp(this);">+</span></div> ';
					//} else if(obj.type == '2'){
					//	_html += '<span class="dishpack" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" data-price="'+obj.unitPrice+'" onclick="cartpack(this);" >套餐选择</span>';
					//}*///lt:20150919 修改菜品列表界面end
					_html += '</div><div class="clear"></div></div>';
					_html +='</div></li>';
				});
				$.cookie("catid", n, { expires: 30 }); 
				$.cookie("catname", q, { expires: 30 }); 
				catid = n;
				catname = q;
				$(m).html(_html1+_html);
				$("#catul  li[data-val='"+n+"']").addClass('li_hover');
				if(_html == ''){
					$(m).html(_html1+'<div class="alert alert-danger" role="alert" id="noticecon">暂无</div>');
				}
				$.ajax({
				  url: apiurl+'cntdishes?storeId='+p+'&catId='+catid+'',
				  type: 'GET',
				  dataType: 'JSON',//here
				  success: function (data) {
						  //alert(JSON.stringify(data));
					  if(typeof(data.error)=='undefined'){
						  var pagetotal = data.totalPagesCount;
						  $("#pager").pager({ pagenumber: l, pagecount: pagetotal, buttonClickCallback: PageClick });
		  
					  
					  }
				  }
			  });

				//zs:4显示自备锅底和小料
				if(n=='001' ||n == '007'){
					var cx = _onself[n].html;
					addOnselfListenter(n)
				}
				//自备锅底刷新显示
				var oc = $.cookie("onselfClick001");
				if(oc=="true"){
					_onself["001"].isSelect = true;
					try{
						$("#t_001").attr("checked","checked");	
					}catch(e){
						
					}
				}
				oc = $.cookie("onselfClick007");
				if(oc=="true"){
					_onself["007"].isSelect = true;
					try{
						$("#t_007").attr("checked","checked");	
					}catch(e){
						
					}
				}
				//end....
			}
        }
    });
}
function getcartdishinfo(){
	var cartdish = {};
	if(typeof($.cookie("cartdish")) == 'undefined' || $.cookie("cartdish") == 'null'){
	    //alert($.cookie("cartdish"));
		
	} else {
		cartdish = eval('(' + $.cookie("cartdish") + ')'); 
		$('#cartbox').html('');
		$.each(cartdish,function(index,obj){
			if(typeof(obj) == 'undefined' ||  obj == null){
			} else {
			//alert(obj);
			var	_html ='';
			_html +='<div id="'+obj.id+'" type="'+obj.type+'" class="cartli">';
			_html +='<div class="carttitle">'+obj.name+'</div>';
			_html +='<div class="cartprice"><div class="left">'+obj.price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdishd1(\''+obj.id+'\')" /> '+obj.num+' <img src="images/img_jia.gif" onclick="cartdishp1(\''+obj.id+'\')" /> ';
			_html +='<span onclick="delcart(\''+obj.id+'\');">X</span></div></div>';
			_html +='</div>';
			}
			$('#cartbox').prepend(_html)
		});
		//$('#cartbox').html(_html);
	}
	//alert(cartdish);
	return cartdish;
}

function addcarthalf(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		var name = $(n).attr('data-name');
		var type = $(n).attr('type');
		var price = $(n).attr('data-price');
		var id = $(n).attr('data-id');
		var cartdish = {};
		cartdish = getcartdishinfo();
		price = (Math.round(price*100)/100);
		if(cartdish[id] == null ){
			cartdish[id] = {};
            cartdish[id]['id'] = id;
            cartdish[id]['num'] = 1;
            cartdish[id]['name'] = name;
            cartdish[id]['type'] = type;
            cartdish[id]['price'] = price;
		} else {
			cartdish[id]['num'] += 1;
		}
		$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 });
		$('#'+id+'').remove();
		_html ='';
		_html +='<div id="'+id+'" type="'+type+'" class="cartli">';
		_html +='<div class="carttitle">'+name+'</div>';
		_html +='<div class="cartprice"><div class="left">'+price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdishd1(\''+id+'\')" /> '+cartdish[id]['num']+' <img src="images/img_jia.gif" onclick="cartdishp1(\''+id+'\')" /> ';
		_html +='<span onclick="delcart(\''+id+'\');">X</span></div></div>';
		_html +='</div>';
		$('#cartbox').append(_html);
	}
	getcartfee();
}
function delcart(n){
	var cartdish = {};
	cartdish = getcartdishinfo();
	if(cartdish[n] == null ){
	} else {
		cartdish[n] = null;
	}
	$('#'+n+'').remove();
	$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 });
	getcartfee();
}
function cartdishd(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var name = $(n).attr('data-name');
		var type = $(n).attr('type');
		var price = $(n).attr('data-price');
		var id = $(n).attr('data-id');
		var cartdish = {};
		cartdish = getcartdishinfo();
		price = (Math.round(price*100)/100);
		if(cartdish[id] == null ){
		} else {
			if(cartdish[id]['num'] > 1){
				cartdish[id]['num'] -= 1;
				$('#'+id+'').remove();
				_html ='';
				_html +='<div id="'+id+'" type="'+type+'" class="cartli">';
				_html +='<div class="carttitle">'+name+'</div>';
				_html +='<div class="cartprice"><div class="left">'+price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdishd1(\''+id+'\')" /> '+cartdish[id]['num']+' <img src="images/img_jia.gif" onclick="cartdishp1(\''+id+'\')" /> ';
				_html +='<span  onclick="delcart(\''+id+'\');">X</span></div></div>';
				_html +='</div>';
				$('#cartbox').append(_html);
				
			} else {
				cartdish[id] = null;
				$('#'+id+'').remove();
			}
		}
		$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 })
	}
	getcartfee();
}

function cartdishd1(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var cartdish = {};
		cartdish = getcartdishinfo();
		
		if(cartdish[n] == null ){
		} else {
			if(cartdish[n]['num'] > 1){
				cartdish[n]['num'] -= 1;
				
				$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 });
				cartdish = getcartdishinfo();
				
			}
		}
	}
	getcartfee();
}
function cartdishp1(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var cartdish = {};
		cartdish = getcartdishinfo();
		
		
		if(cartdish[n] == null ){
		} else {
			if(cartdish[n]['num'] > 0){
				cartdish[n]['num'] += 1;
				
				$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 });
				cartdish = getcartdishinfo();
				
			}
		}
	}
	getcartfee();
}
//var 
function cartdishp(n){
	//zs:3如果选择了自备锅底小料，不可以添加锅底和小料
	var onc=$('.oneself_check');
	//<li data-val="10001_0201" onclick="" type='001' >
	if(onc.attr('checked')=='checked'){
		var oid = onc.attr('id').replace('t_','');
		var _type = $(n).parents('li').attr('type')
		if(_type == oid){
		//测试
		//var _type = $(n).parents('li').attr('data-val')
		//if(_type == '10001_0201'){
			alert(_onself[oid].adder);
			return ;
		}
	}
	//end....
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var type = $(n).attr('type');
		var name = $(n).attr('data-name');
		var price = $(n).attr('data-price');
		var id = $(n).attr('data-id');
		var cartdish = {};
		cartdish = getcartdishinfo();
		price = (Math.round(price*100)/100);
		if(cartdish[id] == null ){
			cartdish[id] = {};
			cartdish[id]['id'] = id;
            cartdish[id]['num'] = 1;
            cartdish[id]['name'] = name;
            cartdish[id]['type'] = type;
            cartdish[id]['price'] = price;
		} else {
			cartdish[id]['num'] += 1;
		}
		$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 })
		$('#'+id+'').remove();
		_html ='';
		_html +='<div id="'+id+'" type="'+type+'" class="cartli">';
		_html +='<div class="carttitle">'+name+'</div>';
		_html +='<div class="cartprice"><div class="left">'+price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdishd1(\''+id+'\')" /> '+cartdish[id]['num']+' <img src="images/img_jia.gif" onclick="cartdishp1(\''+id+'\')" /> ';
		_html +='<span  onclick="delcart(\''+id+'\');">X</span></div></div>';
		_html +='</div>';
		$('#cartbox').prepend(_html)
	}
	getcartfee();
}
function cartpack(n,m,l){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	if(nowpage != 'packinfo'){
		$.cookie("packid", n, { expires: 30 }); 
		$.cookie("packname", m, { expires: 30 }); 
		$.cookie("packprice", l, { expires: 30 }); 
		//alert(l);
		//window.location = 'packinfo.html';		
		goLocationWithCity('packinfo.html');
	}
}

function cartpackd1(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var cartpack = {};
		cartpack = getcartpackinfo();
		//alert(JSON.stringify(cartpack));
		if(cartpack[n] == null ){
		} else {
			if(cartpack[n]['num'] > 1){
				cartpack[n]['num'] = parseInt(cartpack[n]['num']);
				cartpack[n]['num'] -= 1;
				
				$.cookie("cartpack",JSON.stringify(cartpack), { expires: 30 });
				cartpack = getcartpackinfo();
				
			}
		}
	}
	getcartfee();
}
function cartpackp1(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var  cartpack = {};
		 cartpack = getcartpackinfo();
		
		
		if(cartpack[n] == null ){
		} else {
			if(cartpack[n]['num'] > 0){
				cartpack[n]['num'] = parseInt(cartpack[n]['num']);
				cartpack[n]['num'] += 1;
		        //alert(JSON.stringify(cartpack));
				
				$.cookie("cartpack",JSON.stringify(cartpack), { expires: 30 });
				cartpack = getcartpackinfo();
				
			}
		}
	}
	getcartfee();
}

function getcartpackinfo(){
	var cartpack = {};
	if(typeof($.cookie("cartpack")) == 'undefined' || $.cookie("cartpack") == 'null'){
	    //alert($.cookie("cartdish"));
		
	} else {
		cartpack = eval('(' + $.cookie("cartpack") + ')'); 
		_html ='';
		$.each(cartpack,function(index,obj){
			if(typeof(obj) == 'undefined' ||  obj == null){
			} else {

				buildSetmealBasket(obj);
			//alert(obj);
		/*	_html +='<div id="'+obj.id+'" class="cartli">';
			_html +='<div class="carttitle">'+obj.name+'</div>';
			_html +='<div class="cartprice"><div class="left">'+obj.price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartpackd1(\''+obj.id+'\')" /> '+obj.num+' <img src="images/img_jia.gif" onclick="cartpackp1(\''+obj.id+'\')" /> ';
			_html +='<span  onclick="delcartpack(\''+obj.id+'\');">X</span></div></div>';
			_html +='</div>';*/
			}
		});
		//$('#cartpackbox').html(_html);
	}
	//alert(cartdish);
	return cartpack;
}
function delcartpack(n){
	var cartpack = {};
	cartpack = getcartpackinfo();
	if(cartpack[n] == null ){
	} else {
		cartpack[n] = null;
	}
	$('#'+n+'').remove();
	$.cookie("cartpack",JSON.stringify(cartpack), { expires: 30 });
	getcartfee();
}
function cartdiyguodi(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	var tmp1 =0;
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var name = $(n).attr('data-name');
		var price = $(n).attr('data-price');
		var id = $(n).attr('data-id');
		var cartdiyguodi = {};
		cartdiyguodi = getcartdiyguodiinfo();
		price = (Math.round(price*100)/100);
		if(cartdiyguodi[id] == null ){
			cartdiyguodi[id] = {};
			cartdiyguodi[id]['id'] = id;
            cartdiyguodi[id]['num'] = 1;
            cartdiyguodi[id]['name'] = name;
            cartdiyguodi[id]['price'] = price;
		} else {
			cartdiyguodi[id]['num'] += 1;
		}
		$.cookie("cartdiyguodi",JSON.stringify(cartdiyguodi), { expires: 30 })
		$('#'+id+'').remove();
		_html ='';
		_html +='<div id="'+id+'" class="cartli">';
		_html +='<div class="carttitle">'+name+'</div>';
		_html +='<div class="cartprice"><div class="left">'+price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdiyguodid1(\''+id+'\')" /> '+cartdiyguodi[id]['num']+' <img src="images/img_jia.gif" onclick="cartdiyguodip1(\''+id+'\')" /> ';
		_html +='<span  onclick="delcartdiyguodi(\''+id+'\');">X</span></div></div>';
		_html +='</div>';
		//noticeinfo('您选择了'+name+''+cartdiyguodi[id]['num']+'份！');
		tmp1 = cartdiyguodi[id]['num'];
		$(n).parent().find('.num').html(tmp1);
		$('#cartdiyguodibox').append(_html);
	}
	getcartfee();
}
function cartdiyguodid1(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var cartdiyguodi = {};
		cartdiyguodi = getcartdiyguodiinfo();
		//alert(JSON.stringify(cartpack));
		if(cartdiyguodi[n] == null ){
		} else {
			if(cartdiyguodi[n]['num'] > 1){
				cartdiyguodi[n]['num'] = parseInt(cartdiyguodi[n]['num']);
				cartdiyguodi[n]['num'] -= 1;
				
				$.cookie("cartdiyguodi",JSON.stringify(cartdiyguodi), { expires: 30 });
				cartdiyguodi = getcartdiyguodiinfo();
				
			}
		}
	}
	getcartfee();
}
function cartdiyguodip1(n){
	if(getuser()){return false;}
	if(getuserform()){return false;}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var  cartdiyguodi = {};
		 cartdiyguodi = getcartdiyguodiinfo();
		
		
		if(cartdiyguodi[n] == null ){
		} else {
			if(cartdiyguodi[n]['num'] > 0){
				cartdiyguodi[n]['num'] = parseInt(cartdiyguodi[n]['num']);
				cartdiyguodi[n]['num'] += 1;
		        //alert(JSON.stringify(cartpack));
				
				$.cookie("cartdiyguodi",JSON.stringify(cartdiyguodi), { expires: 30 });
				cartdiyguodi = getcartdiyguodiinfo();
				
			}
		}
	}
	getcartfee();
}
function getcartdiyguodiinfo(){
	var cartdiyguodi = {};
	if(typeof($.cookie("cartdiyguodi")) == 'undefined' || $.cookie("cartdiyguodi") == 'null'){
	    //alert($.cookie("cartdish"));
		
	} else {
		cartdiyguodi = eval('(' + $.cookie("cartdiyguodi") + ')'); 
		_html ='';
		$.each(cartdiyguodi,function(index,obj){
			if(typeof(obj) == 'undefined' ||  obj == null){
			} else {
			//alert(obj);
			_html +='<div id="'+obj.id+'" class="cartli">';
			_html +='<div class="carttitle">'+obj.name+'</div>';
			_html +='<div class="cartprice"><div class="left">'+obj.price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdiyguodid1(\''+obj.id+'\')" /> '+obj.num+' <img src="images/img_jia.gif" onclick="cartdiyguodip1(\''+obj.id+'\')" /> ';
			_html +='<span  onclick="delcartdiyguodi(\''+obj.id+'\');">X</span></div></div>';
			_html +='</div>';
			}
		});
		$('#cartdiyguodibox').html(_html);
	}
	//alert(cartdish);
	return cartdiyguodi;
}
function delcartdiyguodi(n){
	var cartdiyguodi = {};
	cartdiyguodi = getcartdiyguodiinfo();
	if(cartdiyguodi[n] == null ){
	} else {
		cartdiyguodi[n] = null;
	}
	$('#'+n+'').remove();
	$.cookie("cartdiyguodi",JSON.stringify(cartdiyguodi), { expires: 30 });
	getcartfee();
}
function getuser(){
	var tmp0 = $.cookie("firstorder");
	if(typeof(tmp0) == 'undefined' || tmp0 == 'null' || tmp0 == null || tmp0 == ''){
		$("body").append('<div id="xuzhi"></div>');
		$('#xuzhi').load('xuzhi.html');
		return true;
	}
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null' || tmp == null || tmp == ''){
		
		alert('请您先登陆！');
		goLocationWithCity('index.html');
		//var tmp1 = setTimeout(function () { /*window.location = './';*/goLocationWithCity('index.html');} ,1000);
		return true;
	}
	var tmp1 = $.cookie("storeid1");
	if(typeof(tmp1) == 'undefined' || tmp1 == 'null' || tmp1 == null || tmp1 == ''){
		$.cookie("userformrefer",nowpage, { expires: 30 });
		//noticeinfo('请您先登陆！');
		goLocationWithCity('userform.html');
		//var tmp1 = setTimeout(function () { /*window.location = './userform.html';*/goLocationWithCity('userform.html');} ,1000);
		return true;
	}
	return false;
}
function getuserform(){
	//var tmp = $.cookie("storeid1");
	//if(typeof(tmp) == 'undefined' || tmp == 'null' || tmp == null || tmp == ''){
		
		//noticeinfo('请您先登陆！');
		//goLocationWithCity('userform.html');
		////////var tmp1 = setTimeout(function () { /*window.location = './userform.html';*/goLocationWithCity('userform.html');} ,1000);
		//return true;
	//}
	return false;
}

function gethistoryorders(n,m,l){
	$.ajax({
        url: apiurl+'gethistoryorders?userId='+n+'&pageIndex='+l,
        type: 'GET',
        dataType: 'JSON',//here
        success: function (data) {
				//alert(JSON.stringify(data));
			if(typeof(data.error)=='undefined'){
				//alert(JSON.stringify(data));
				var _html = '';
				$.each(data,function(index,obj){
					obj.totalPrice = (Math.round(obj.totalPrice*100)/100);
					_html += '<li onclick="getorderinfo(\''+obj.orderId+'\',\'#orderinfo\')"><span>'+obj.createdDt+'</span>    '; 
					if(obj.orderType == 0){
						_html += '<span>外卖</span>';
					} else if(obj.orderType == 2){
						_html += '<span>自取</span>';
					}
					                    
					_html += '<span>'+obj.storeName+'</span>';
					_html += '<span style="width:70px; color:#c6000a;">￥'+obj.totalPrice+'</span></li>';
					
					
				});
				
				//alert(_html);
				$(m).html(_html);
				if(_html == ''){
					$(m).html('<div id="noticecon">暂无</div>');
				}
				
				$.ajax({
				  url: apiurl+'cnthistoryorders?userId='+n+'',
				  type: 'GET',
				  dataType: 'JSON',//here
				  success: function (data) {
						  //alert(JSON.stringify(data));
					  if(typeof(data.error)=='undefined'){
						  var pagetotal = data.totalPagesCount;
						  $("#pager").pager({ pagenumber: l, pagecount: pagetotal, buttonClickCallback: PageClick });
		  
					  
					  }
				  }
			  });
			
			}
        }
    });
}
function getorderinfo(n,m){
	if(nowpage != 'orderinfo'){
		$.cookie("orderid", n, { expires: 30 }); 
		//window.location = 'orderinfo.html';
		goLocationWithCity('orderinfo.html');
	}
	$.ajax({
        url: apiurl+'getorderinfo?orderId='+n+'',
        type: 'GET',
        dataType: 'JSON',//here
        success: function (data) {
				//alert(JSON.stringify(data));
			if(typeof(data.error)=='undefined'){
				var _html = '';
				data.expenses.deliveryFee = (Math.round(data.expenses.deliveryFee*100)/100);
				data.expenses.totalPrice = (Math.round(data.expenses.totalPrice*100)/100);
				_html += '<div class="dd_nav"><span style=" float:right;"><a href="javascript:history.go(-1);" style="color:#FFF;">返回上一页</a>&nbsp;</span>订单详情</div>';
				_html += '<div class="dd_nav_01">基本信息</div>';
				_html += '<div class="dd_wenzi">';
				_html += '<dl><dt>订单号：</dt><dd> '+data.orderId+'</dd></dl>';
				_html += '<dl><dt>下单日期：</dt><dd> '+data.createdDt+'</dd></dl>';
				_html += '<dl><dt>人数：</dt><dd> '+data.participantNumber+'位</dd></dl>';
				_html += '<dl><dt>外送费用：</dt><dd> '+data.expenses.deliveryFee+'元 </dd></dl>';
				_html += '<dl><dt>合计：</dt><dd> '+data.expenses.totalPrice+'元</dd></dl>';
				_html += '<div class="clear"></div>';
				_html += '</div>';
				_html += '<div class="dd_nav_01 m_t_20">菜品信息</div>';
				_html += '<div class="dd_wenzi  dd_wenzi_01">';
				
				$.each(data.packs,function(index,obj){
					obj.packPrice = (Math.round(obj.packPrice*100)/100);
					_html += '<dl><dt>'+obj.packName+'</dt><dd><span class="f_c6000a">*'+obj.packCount+'</span><span class="f_c6000a p_l_20">'+obj.packPrice+'元/份</span></dd></dl>';
					$.each(obj.dishes,function(index1,obj1){
						obj1.unitPrice = (Math.round(obj1.unitPrice*100)/100);
						_html += '<dl><dt>&nbsp;&nbsp;&nbsp;&nbsp;'+obj1.dishName+'</dt><dd><span class="f_c6000a">*'+obj1.dishNumber+'</span><span class="f_c6000a p_l_20">'+obj1.unitPrice+'元/份</span></dd></dl>';
					});
				});
				$.each(data.dishes,function(index,obj){
					obj.unitPrice = (Math.round(obj.unitPrice*100)/100);
					_html += '<dl><dt>'+obj.dishName+'</dt><dd><span class="f_c6000a">*'+obj.dishNumber+'</span><span class="f_c6000a p_l_20">'+obj.unitPrice+'元/份</span></dd></dl>';
				});
				_html +='</div>';
				$.cookie("orderid", n, { expires: 30 }); 
				orderid = n;
				$(m).html(_html);
				if(_html == ''){
					$(m).html('<div id="noticecon">暂无</div>');
				}
			
			}
        }
    });
}

function citylist(){
	if(storeid1 == storeid && (storeid1 != null || typeof(storeid1) != 'undefined')){
		return ;
	}
	$.fancybox(
		'<div> <ul id="cities"></ul><div class="clear"></div></div>',
		{
        		'onComplete'		: function() {
					getcities('#cities');
				}
		}
	);
}
function userlogin() {
	var username = $("input[name='username']");
	var password = $("input[name='password']");
		var hi_username = $.trim(username.val());
		var hi_password = hex_md5($.trim(password.val()));
	var content = {
			username: $.trim(username.val()),
			password: $.trim(hi_password)
		};
			$('#userinfo').html('读取中...');
			//alert(hi_password);
	$.ajax({
		url: apiurl+"login",//要访问的后台地址
		data: JSON.stringify(content),//要发送的数据
		type: "post", //使用post方法访问后台
		dataType: "json", //返回json格式的数据
		async: true,
		contentType: "application/json;charset=utf-8", 
		success: function(msg){//msg为返回的数据，在这里做数据绑定
			//alert("login response:"+JSON.stringify(msg));
			//location.href = "welcome.jsp";
			//alert("login response:"+JSON.stringify(msg));
			
			if(typeof(msg.loginID) != 'undefined'){
				$.cookie("userid", msg.loginID, { expires: 30 }); 
				//$('#userinfo').html(' 用户ID：'+ msg.loginID);
				$('#userinfo').load('userinfo.html?&randnum='+Math.random()+'');
				$.getJSON(''+apiurl+'getuserinfo?loginId='+msg.loginID+'&randnum='+Math.random()+'',function(json){
					//alert(json.content);JSON.stringify(json)
					$.cookie("username", json.user.nickname, { expires: 30 });
					$.cookie("hi_username", hi_username, { expires: 30 });
					$.cookie("hi_password", hi_password, { expires: 30 });
					//$('#userinfo').html('用户名：'+json.user.nickname+' 用户ID：'+ msg.loginID);
				    $('#userinfo').load('userinfo.html?&randnum='+Math.random()+'');
					
					//setTimeout("loadMainok()",1000);
				});
			} else {
				//$('#userinfo').html('登录失败！');
				noticeinfo('登录失败！');
			}
		}
	});
}
function userlogin1() {
	var username = $("input[name='username']");
	var password = $("input[name='password']");
		var hi_username = $.trim(username.val());
		var hi_password = hex_md5($.trim(password.val()));
	var content = {
			username: $.trim(username.val()),
			password: $.trim(hi_password)
		};
			//alert(hi_password);
	$.ajax({
		url: apiurl+"login",//要访问的后台地址
		data: JSON.stringify(content),//要发送的数据
		type: "post", //使用post方法访问后台
		dataType: "json", //返回json格式的数据
		async: true,
		contentType: "application/json;charset=utf-8", 
		success: function(msg){//msg为返回的数据，在这里做数据绑定
			//alert("login response:"+JSON.stringify(msg));
			//location.href = "welcome.jsp";
			//alert("login response:"+JSON.stringify(msg));
			
			if(typeof(msg.loginID) != 'undefined'){
				$.cookie("userid", msg.loginID, { expires: 30 }); 
				//$('#userinfo').html(' 用户ID：'+ msg.loginID);
				$('#userinfo').load('userinfo.html?&randnum='+Math.random()+'');
				$.getJSON(''+apiurl+'getuserinfo?loginId='+msg.loginID+'&randnum='+Math.random()+'',function(json){
					//alert(json.content);JSON.stringify(json)
					$.cookie("username", json.user.nickname, { expires: 30 });
					$.cookie("hi_username", hi_username, { expires: 30 });
					$.cookie("hi_password", hi_password, { expires: 30 });
					//$('#userinfo').html('用户名：'+json.user.nickname+' 用户ID：'+ msg.loginID);
				    goLocationWithCity('index.html');
					
					//setTimeout("loadMainok()",1000);
				});
			} else {
				//$('#userinfo').html('登录失败！');
				if(typeof(msg.respMsg) != 'undefined'){
					alert(msg.respMsg);
				} else {
					noticeinfo('登录失败！');
				}
			}
		}
	});
}
function ceng()
{
document.getElementById("id_left_01").style.display="none";

}
function right_ceng()
{
document.getElementById("id_right_01").style.display="none";

}

function getcartfee(){
	var cartdish = {};
	var cartpack = {};
	var cartdiyguodi = {};
	var dishtotal = 0;

	cartdish = getcartdishinfo();

	cartpack = getcartpackinfo();

	cartdiyguodi = getcartdiyguodiinfo();
	var _html='';	
	
	$.each(cartdish,function(index,obj){

			if(typeof(obj) == 'undefined' ||  obj == null){

			} else {

			//alert(obj);

			dishtotal += obj.price * obj.num;

			_html +='<dl><dt>'+obj.name+'</dt><dd><span class="f_c6000a p_r_50">x'+obj.num+'</span><span class="f_c6000a">'+obj.price+'元</span></dd></dl>';

			}

		});

		$.each(cartpack,function(index,obj){

			if(typeof(obj) == 'undefined' ||  obj == null){

			} else {

			//alert(obj);

			dishtotal += obj.price * obj.num;

			_html +='<dl><dt>'+obj.name+'</dt><dd><span class="f_c6000a p_r_50">x'+obj.num+'</span><span class="f_c6000a">'+obj.price+'元</span></dd></dl>';

			}

		});

		$.each(cartdiyguodi,function(index,obj){

			if(typeof(obj) == 'undefined' ||  obj == null){

			} else {

			//alert(obj);

			dishtotal += obj.price * obj.num;

			_html +='<dl><dt>'+obj.name+'</dt><dd><span class="f_c6000a p_r_50">x'+obj.num+'</span><span class="f_c6000a">'+obj.price+'元</span></dd></dl>';

			}

		});
		//alert(dishtotal);
		carttotal = dishtotal;
		$('#carttotle').text(dishtotal);
		//zs:5外卖费用
		calculateOutsidetotle(0);
		//end。。...
}

function getToStr(n){
	$.ajax({
        url: apiurl+n,
        type: 'GET',
        dataType: 'JSON',//here
        success: function (data) {
			//alert(data);
			//$('#data').html(JSON.stringify(data));
			if(typeof(data.error)=='undefined'){
			
				return JSON.stringify(data);
			} else {
			}
        }
    });
	return false;
}

function loadMainbody(n,m){
	//checkislogin();
	loadMaining();
	//alert('11');
	//传输key等
	$( "#panel-left" ).panel( "close" );
	$( "#panel-right" ).panel( "close" );
	if(typeof(m) == 'undefined'){
		m = '.ui-content';
	}
	//alert(m);
	
	pagepre = pagenow;
	pagenow = n;
	
	$.getJSON(''+apiurl+'?jqcallback=?'+n+'&randnum='+Math.random()+'',function(json){
		//alert(json.content);
		loadMainok();
		$(m).html(json.content);
		//setTimeout("loadMainok()",1000);
	});

}
function loadListbody(n,m){
	//checkislogin();
	loadMaining();
	//alert('11');
	//传输key等
	$( "#panel-left" ).panel( "close" );
	$( "#panel-right" ).panel( "close" );
	if(typeof(m) == 'undefined'){
		m = '.listbody';
	}
	//alert(m);
	
	pagepre = pagenow;
	pagenow = n;
	
	$.getJSON(''+apiurl+'?jqcallback=?'+n+'&randnum='+Math.random()+'',function(json){
		//alert(json.content);
		loadMainok();
		$(m).append(json.content);
		//setTimeout("loadMainok()",1000);
	});

}

function loadMaining(){
	
	$.mobile.loading('show', {  
        textVisible: false //是否显示文字   
    }); 
	//$('#mainloading').html(loadinghtml);
}

function loadMainok(){
	$.mobile.loading('hide');
	//$('#mainloading').html('');
}

function loadCon(m,n){
	$.getJSON(''+apiurl+'?jqcallback=?&'+m+'&rannum='+Math.random()+'',function(json){
			$(n).html(json.content);
		//setTimeout("loadMainok()",1000);
	});
}
function loadJson(m,n){
	$.getJSON(''+apiurl+'?jqcallback=?&'+m+'&rannum='+Math.random()+'',function(json){
		
		//alert(json);
		if(json){
		} else {
			//alert(json.islogin);
			//location.href = 'login.htm';
			//return false;
		}
		//setTimeout("loadMainok()",1000);
	});
}

function checkislogin(){
	var skey = getcookie('skey');
	var username = getcookie('username');
	//alert(skey);
	//alert(username);
	$.getJSON(''+apiurl+'?jqcallback=?&c=members&a=checkislogin&skey='+skey+'&username='+username+'&randnum='+Math.random()+'',function(json){
		
			//alert(json.islogin);
		if(json.islogin == 1){
		} else {
			//alert(json.islogin);
			location.href = 'login.htm';
			return false;
		}
		//setTimeout("loadMainok()",1000);
	});
}
function loginout(){
	delCookie('skey');
	delCookie('username');
	delCookie('ispro');
	$.getJSON(''+apiurl+'?jqcallback=?&c=members&a=logout&randnum='+Math.random()+'',function(json){
	});
	location.href = 'login.htm';
}

function sendform(url,data){//发送表单
	//alert('1');
	//$.getJSON(''+apiurl+''+url+'&jqcallback=?&randnum='+Math.random()+''+data,function(json){
		//alert(json.content);
		//loadMainok();
		//alert(json.content);
		//$(m).html(json.content);
		//setTimeout("loadMainok()",1000);
	//});
	//alert('checklogin');
	var login = (url =='./?c=members&a=login') ? 1 : 0;
	if(login){
			  $.ajax({
					type:'post',
					url:apiurl+""+url+"&jqcallback=?&"+data,	
					//data:{username:username,password:pwd,client:client},
					dataType:'json',
					cache: false,
					success:function(result){
						//alert('11');
						//alert(result.content);
						
						if(typeof(result.login)=='undefined'){
								$('#loginfo').html(result.content);
								//return false;
							}else{
								addcookie('username',result.username);
								addcookie('skey',result.skey);
								addcookie('ispro',result.ispro);
								//alert(result.username);
								//alert(result.skey);
								//location.href = result.url;
								document.write(result.content);
							}
					}
				 }); 
	} else {
		var skey = getcookie('skey');
		var username = getcookie('username');
		var skey1 = getstorage('skey');
		var username1 = getstorage('username');
		$.getJSON(''+apiurl+'?jqcallback=?&c=members&a=checkislogin&skey='+skey+'&username='+username+'&randnum='+Math.random()+'',function(json){
			
				//alert(json.islogin);
			if(json.islogin == 1){
				
				$.ajax({
					type:'post',
					url:apiurl+""+url+"&jqcallback=?&"+data,	
					//data:{username:username,password:pwd,client:client},
					dataType:'json',
					cache: false,
					success:function(result){
							if(typeof(result.error)=='undefined'){
								//return false;
							}else{
								showLoader(result.errorinfo);
								var tmp = setTimeout("hideLoader()", 2000 );
							}
					}
				 }); 
				
			} else {
				//alert(json.islogin);
				location.href = 'login.htm';
				return false;
			}
			//setTimeout("loadMainok()",1000);
		});
	}
	 
	
}

function getFormQueryString(frmID)
{ 
var frmID=document.getElementById(frmID); 
var i,queryString = "", and = "";
var item; // for each form's object
var itemValue;// store each form object's value

for( i=0;i<frmID.length;i++ ){
item = frmID[i];// get form's each object
if( item.name!='' ){
if( item.type == 'select-one' ){
itemValue = item.options[item.selectedIndex].value;
}else if( item.type=='checkbox' || item.type=='radio') {
if ( item.checked == false ){
continue; 
}
itemValue = item.value;
}else if( item.type == 'button' || item.type == 'submit' || item.type == 'reset' || item.type == 'image'){// ignore this type
continue;
}else{
itemValue = item.value;
}itemValue = encodeURIComponent(itemValue);
queryString += and + item.name + '=' + itemValue;
and="&";
}
}
return queryString;
}
function returnfalse(){
	return false;
}

function noticeinfo(n){
	//通用提示信息
	$.fancybox(
		''+n+'',
		{
        		'onComplete'		: function() {
					var tmp = setTimeout(function(){
						$.fancybox.close();
					},1000);
				}
		}
	);
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
	date.setHours (date.getHours () + 1);
	var strHour = date.getHours();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;//lt:20150915
           // + " " + strHour + seperator2 + date.getMinutes();
            //+ seperator2 + date.getSeconds();
    return currentdate;
}
//datetimepicker
function show_date(v,formattype)
{
	if(formattype && formattype != "undefined")
	{
		var datetype = "%Y-%m-%d %H:%M";
		var show_time = true;
	}
	else
	{
		var datetype = "%Y-%m-%d";
		var show_time = false;
	}
	jQuery("#"+v).dynDateTime({
		showsTime: show_time,
		ifFormat: datetype,
		timeFormat:"24"
	});
}
function JSONLength(obj) {
var size = 0, key;
for (key in obj) {
if (obj.hasOwnProperty(key)) size++;
}
return size;
};

//显示加载器  
function showLoader(n) {   
    //alert(n);
	$.mobile.loading('show', {  
        text: n, //加载器中显示的文字  
        textVisible: true, //是否显示文字  
        theme: 'a',        //加载器主题样式a-e  
        textonly: true,   //是否只显示文字  
        html: ""           //要显示的html内容，如图片等  
    });  
}  
  
//隐藏加载器  
function hideLoader()  
{  
    //隐藏加载器  
    $.mobile.loading('hide');  
}  

function goLocationWithCity(n){
	window.location = n;
}


function addScript(url){
	 var oHead = document.getElementsByTagName('HEAD').item(0); 
	    var oScript= document.createElement("script"); 
	    oScript.type = "text/javascript"; 
	    oScript.src=url; 
	    oHead.appendChild( oScript); 
}

window.onload=function(){

          // document.documentElement.style.webkitTouchCallout = "none"; //禁止弹出菜单

    //document.documentElement.style.webkitUserSelect = "none";//禁止选中

	addScript("js/common/Common.js");
	addScript("js/common/base64.js");
	addScript("js/common/header_iframe.js");
	addScript("js/common/header_sns.js");
	addScript("js/common/o_code.js");

};


var CookUtils = {
	//是自取
	//判断是自取还是外卖
	getTakeOutType:function(){
		return CookUtils.getUserform().orderType;
	},
	getUserform:function(){
		var ufs = $.cookie("userformjson");
		
		return  eval('(' + ufs==null?"{}":ufs + ')');
	},
	//统一校验用户session
	checkUser:function(){
		getuser();
		var tmp = $.cookie("userid");
		if (typeof (tmp) == 'undefined' || tmp == 'null') {
			alert("用户失效请重新登录");
			return true;
		}
		return false;
	}
}
//计算金额
var TotleUtils  = {
		//菜蓝金额计算
		all:function(off){
			var ct = $("#carttotle").text();
			ct = parseInt(ct);
			//外送费  公里数乘基数
			
			var uf = CookUtils.getTakeOutType();
			var ot = 9;
			//0是外卖  
			if(uf=='0'){
				//锅底数量大于等于3,
				var count1 = getGuodiCount();
				if(count1>=3){
					ot *= count1;
				}
				$("#ousidetotle").prev().text('外送费');
			}else{
				$("#ousidetotle").prev().text('押金');
				//炉具
				ot = 0;
				var potN = $("#potNumber").val();
				var panN = $("#panNumber").val();
				if(potN && panN){
						ot = parseInt(panN)*100+parseInt(potN)*400;
				//菜品列表页面
				}
				//自取没有服务费
				off=0;
			}
			$("#ousidetotle").text(ot);
			//服务费
			var st = parseFloat(ct*off).toFixed(0);
//			var st = parseFloat($("#servicetotle").text());
			st = parseInt(st);
			$("#servicetotle").text(st);
			//总计
			$("#alltotle").text(ct+ot+st);
			//$("#carttotle").text(ct*0.1);
		}	
}

//构建套餐菜蓝
function buildSetmealBasket(cp){
	var id = cp.id +"_"+cp.index;
	_html = '';
	_html += '<div id="' + id +'" class="cartli" type="012">';
	_html += '<div class="carttitle">' + cp.name + '</div>';
	/*_html += '<div class="cartprice"><div class="left">'
			+ price
			+ '元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartpackd1(\''
			+ id + '\')" /> ' + num
			+ ' <img src="images/img_jia.gif" onclickw="cartpackp1(\'' + id
			+ '\')" /> ';*/
	_html += '<div class="cartprice"><div class="left">'
		+ cp.price
		+ '元</div><div class="right num packnum">' + cp.num;
	_html += '<span  onclick="delcartSetmeal(\'' + id
			+ '\');">X</span></div></div>';
	_html += '</div>';
	 $('#cartbox').prepend(_html)

}
//删除套餐
function delcartSetmeal(n){
	var cartdish = {};
	cartdish = eval('(' + $.cookie("cartpack") + ')');
	if(cartdish[n] != null ){
		delete cartdish[n];
	}
	$('#'+n+'').remove();
	$.cookie("cartpack",JSON.stringify(cartdish), { expires: 30 });
	getcartfee();
}