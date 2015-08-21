var tmpjson;
var nowpage;
if(typeof($.cookie("cityid")) == 'undefined' || $.cookie("cityid") == 'null' || $.cookie("cityid") == null ){
	$.cookie("cityname", "北京市", { expires: 30 }); 
	$.cookie("cityid", "110000", { expires: 30 }); 
	$('#cityname').html($.cookie("cityname"));
}
var cityid = $.cookie("cityid");
var cityname = $.cookie("cityname");
var catid;
var userid;
var username;
var orderid;
var packid;
var packname;
var packprice;

//alert(cityname);

$(document).ready(function(e) {
    //页面加载
	$('#userinfo').load('userinfo.html?&randnum='+Math.random()+'');
});

Array.prototype.remove=function(dx) 
{ 
    if(isNaN(dx)||dx>this.length){return false;} 
    for(var i=0,n=0;i<this.length;i++) 
    { 
        if(this[i]!=this[dx]) 
        { 
            this[n++]=this[i] 
        } 
    } 
    this.length-=1 
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
					_html += '<li onclick="getcategories(\''+obj.cityId+'\',\''+k+'\',\''+obj.city+'\');" data-val="'+obj.cityId+'">'+obj.city+'</li>';
				});
				$(m).html(_html);
			
			}
        }
    });
}
function getcategories(n,m,l){
	var content;
	var k = '#dishul';
	catid = $.cookie("catid");
	
	$.ajax({
        url: apiurl+'getcategories?cityId='+n,
        type: 'GET',
        dataType: 'JSON',//here
        success: function (data) {
			if(typeof(data.error)=='undefined'){
				var _html = '';
				$.each(data,function(index,obj){
					if(obj.dishTypeId == '012'){
						if(catid == '012'){
							_html += '<li onclick="getpacks(\'012\',\''+k+'\',\'1\');getcatstyle(this);" data-val="012" class = "li_hover">套餐</li>';
						} else {
							_html += '<li onclick="getpacks(\'012\',\''+k+'\',\'1\');getcatstyle(this);" data-val="012">套餐</li>';
						}
					} else {
					  if(catid == obj.dishTypeId){
						  _html += '<li onclick="getdishes(\''+obj.dishTypeId+'\',\''+k+'\',\'1\');getcatstyle(this);" data-val="'+obj.dishTypeId+'" class="li_hover">'+obj.dishTypeName+'</li>';
					  } else {
					  _html += '<li onclick="getdishes(\''+obj.dishTypeId+'\',\''+k+'\',\'1\');getcatstyle(this);" data-val="'+obj.dishTypeId+'">'+obj.dishTypeName+'</li>';
					  }
					}
				});
				
				$(m).html(_html);
				$.cookie("cityname", l, { expires: 30 }); 
				$.cookie("cityid", n, { expires: 30 }); 
				//alert(l);
				//alert($.cookie("cityid"));
				cityid = $.cookie("cityid");
				$('#cityname').html(l);
				$.fancybox.close();
			
			}
        }
    });
}
function getcatstyle(n){
	$(n).parent().find('li').removeClass('li_hover');
	$(n).addClass('li_hover');
}
function getpacks(n,m,l){
	//l表示分页
	var content;
	//var k = '#dishul';
	//alert(n);
	if(nowpage != 'pack'){
		$.cookie("catid", n, { expires: 30 }); 
		window.location = 'pack.html';
	}
	
	$.ajax({
        url: apiurl+'getpacks?catId='+n+'&pageIndex='+l,
        type: 'GET',
        dataType: 'JSON',//here
        success: function (data) {
				//alert(JSON.stringify(data));
			if(typeof(data.error)=='undefined'){
				var _html = '';
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
				catid = n;
				$(m).html(_html);
				$(m +" li[data-val='"+n+"']").addClass('li_hover');
				if(_html == ''){
					$(m).html('<div class="alert alert-danger" role="alert" id="noticecon">暂无</div>');
				}
				$.ajax({
				  url: apiurl+'cntpacks?catId='+catid+'',
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
function getdishes(n,m,l){
	//l表示分页
	var content;
	//var k = '#dishul';
	//alert(n);
	if(nowpage != 'dish'){
		$.cookie("catid", n, { expires: 30 }); 
		window.location = 'dish.html';
	}
	
	$.ajax({
        url: apiurl+'getdishes?catId='+n+'&pageIndex='+l,
        type: 'GET',
        dataType: 'JSON',//here
        success: function (data) {
				//alert(JSON.stringify(data));
			if(typeof(data.error)=='undefined'){
				var _html = '';
				$.each(data,function(index,obj){
					obj.unitPrice = (Math.round(obj.unitPrice*100)/100);
					_html += '<li onclick="" data-val="'+obj.dishId+'"><img src="'+obj.bigImageAddr+'" width="100" height="100"><div class="cp_titel">';
					_html += '<div class="cp_title1"><span class="f_c6000a f14px fB oh14">'+obj.storeDishName+'</span>  <span class="f_c6000a f14px oh14">一份价格：'+obj.unitPrice+'元</span><div class="clear"></div>';
					if(typeof(obj.halfDishId) == 'undefined'){
					  } else {
						  obj.halfPrice = (Math.round(obj.halfPrice*100)/100);
						  _html += '<span class="f_c6000a f14px oh14">半份价格：'+obj.halfPrice+'元</span><div class="clear"></div>';
					  }
					_html += '</div><div class=" p_t_20"><div class="i_box">';
					//if(obj.type == '1'){
					  if(typeof(obj.halfDishId) == 'undefined'){
					  } else {
						  _html += '<span onclick="addcarthalf(this);" data-id="'+obj.halfDishId+'"  data-name="'+obj.storeDishName+'半份" data-price="'+obj.halfPrice+'">半份选择</span>';
					  }
					 _html += ' <div class="liright"><span class="dishd J_minus" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" data-price="'+obj.unitPrice+'" onclick="cartdishd(this);">-</span> ';
					 //_html += '  <span class="dishnum">0</span>  ';
					 _html += ' <span class="dishp J_add" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" data-price="'+obj.unitPrice+'" onclick="cartdishp(this);">+</span></div> ';
					//} else if(obj.type == '2'){
					//	_html += '<span class="dishpack" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" data-price="'+obj.unitPrice+'" onclick="cartpack(this);" >套餐选择</span>';
					//}
					_html += '</div><div class="clear"></div></div>';
					_html +='</div></li>';
				});
				$.cookie("catid", n, { expires: 30 }); 
				catid = n;
				$(m).html(_html);
				$("#catul  li[data-val='"+n+"']").addClass('li_hover');
				if(_html == ''){
					$(m).html('<div class="alert alert-danger" role="alert" id="noticecon">暂无</div>');
				}
				$.ajax({
				  url: apiurl+'cntdishes?catId='+catid+'',
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
function getcartdishinfo(){
	var cartdish = {};
	if(typeof($.cookie("cartdish")) == 'undefined' || $.cookie("cartdish") == 'null'){
	    //alert($.cookie("cartdish"));
		
	} else {
		cartdish = eval('(' + $.cookie("cartdish") + ')'); 
		_html ='';
		$.each(cartdish,function(index,obj){
			if(typeof(obj) == 'undefined' ||  obj == null){
			} else {
			//alert(obj);
			_html +='<div id="'+obj.id+'" class="cartli">';
			_html +='<div class="carttitle">'+obj.name+'</div>';
			_html +='<div class="cartprice"><div class="left">'+obj.price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdishd1(\''+obj.id+'\')" /> '+obj.num+' <img src="images/img_jia.gif" onclick="cartdishp1(\''+obj.id+'\')" /></div></div>';
			_html +='<span class=" btn btn-danger" onclick="delcart(\''+obj.id+'\');">删除</span>';
			_html +='</div>';
			}
		});
		$('#cartbox').html(_html);
	}
	//alert(cartdish);
	return cartdish;
}

function addcarthalf(n){
	getuser();
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
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
            cartdish[id]['price'] = price;
		} else {
			cartdish[id]['num'] += 1;
		}
		$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 });
		$('#'+id+'').remove();
		_html ='';
		_html +='<div id="'+id+'" class="cartli">';
		_html +='<div class="carttitle">'+name+'</div>';
		_html +='<div class="cartprice"><div class="left">'+price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdishd1(\''+id+'\')" /> '+cartdish[id]['num']+' <img src="images/img_jia.gif" onclick="cartdishp1(\''+id+'\')" /></div></div>';
		_html +='<span class=" btn btn-danger" onclick="delcart(\''+id+'\');">删除</span>';
		_html +='</div>';
		$('#cartbox').append(_html);
	}
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
}
function cartdishd(n){
	getuser();
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
		var name = $(n).attr('data-name');
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
				_html +='<div id="'+id+'" class="cartli">';
				_html +='<div class="carttitle">'+name+'</div>';
				_html +='<div class="cartprice"><div class="left">'+price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdishd1(\''+id+'\')" /> '+cartdish[id]['num']+' <img src="images/img_jia.gif" onclick="cartdishp1(\''+id+'\')" /></div></div>';
				_html +='<span class=" btn btn-danger" onclick="delcart(\''+id+'\');">删除</span>';
				_html +='</div>';
				$('#cartbox').append(_html);
				
			} else {
				cartdish[id] = null;
				$('#'+id+'').remove();
			}
		}
		$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 })
	}
}

function cartdishd1(n){
	getuser();
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
}
function cartdishp1(n){
	getuser();
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
}

function cartdishp(n){
	getuser();
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
	} else {
		//$(this).attr();
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
            cartdish[id]['price'] = price;
		} else {
			cartdish[id]['num'] += 1;
		}
		$.cookie("cartdish",JSON.stringify(cartdish), { expires: 30 })
		$('#'+id+'').remove();
		_html ='';
		_html +='<div id="'+id+'" class="cartli">';
		_html +='<div class="carttitle">'+name+'</div>';
		_html +='<div class="cartprice"><div class="left">'+price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartdishd1(\''+id+'\')" /> '+cartdish[id]['num']+' <img src="images/img_jia.gif" onclick="cartdishp1(\''+id+'\')" /></div></div>';
		_html +='<span class=" btn btn-danger" onclick="delcart(\''+id+'\');">删除</span>';
		_html +='</div>';
		$('#cartbox').append(_html)
	}
}
function cartpack(n,m,l){
	if(nowpage != 'packinfo'){
		$.cookie("packid", n, { expires: 30 }); 
		$.cookie("packname", m, { expires: 30 }); 
		$.cookie("packprice", l, { expires: 30 }); 
		//alert(l);
		window.location = 'packinfo.html';
	}
}

function cartpackd1(n){
	getuser();
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
}
function cartpackp1(n){
	getuser();
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
			//alert(obj);
			_html +='<div id="'+obj.id+'" class="cartli">';
			_html +='<div class="carttitle">'+obj.name+'</div>';
			_html +='<div class="cartprice"><div class="left">'+obj.price+'元</div><div class="right num"><img src="images/img_jian.gif" onclick="cartpackd1(\''+obj.id+'\')" /> '+obj.num+' <img src="images/img_jia.gif" onclick="cartpackp1(\''+obj.id+'\')" /></div></div>';
			_html +='<span class=" btn btn-danger" onclick="delcartpack(\''+obj.id+'\');">删除</span>';
			_html +='</div>';
			}
		});
		$('#cartpackbox').html(_html);
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
}
function cartdiyguodi(n){
	getuser();
	var tmp = $.cookie("userid");
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
		_html +='<div class="cartprice"><div class="left">'+price+'元</div><div class="right num">×'+cartdiyguodi[id]['num']+'</div></div>';
		_html +='<span class=" btn btn-danger" onclick="delcart(\''+id+'\');">删除</span>';
		_html +='</div>';
		noticeinfo('您选择了'+name+''+cartdiyguodi[id]['num']+'份！');
		$('#cartdiyguodibox').append(_html);
	}
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
			_html +='<div class="cartprice"><div class="left">'+obj.price+'元</div><div class="right num">×'+obj.num+'</div></div>';
			_html +='<span class=" btn btn-danger" onclick="delcartdiyguodi(\''+obj.id+'\');">删除</span>';
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
}
function getuser(){
	var tmp = $.cookie("userid");
	if(typeof(tmp) == 'undefined' || tmp == 'null'){
		
		noticeinfo('请您先登陆！');
		var tmp1 = setTimeout(function () { window.location = './';} ,1000);
		//return false;
	}
	//return true;
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
		window.location = 'orderinfo.html';
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
function ceng()
{
document.getElementById("id_left_01").style.display="none";

}
function right_ceng()
{
document.getElementById("id_right_01").style.display="none";

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
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + strHour + seperator2 + date.getMinutes();
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





window.onload=function(){

          // document.documentElement.style.webkitTouchCallout = "none"; //禁止弹出菜单

    //document.documentElement.style.webkitUserSelect = "none";//禁止选中

};

