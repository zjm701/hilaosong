//lt:20150923 重新设置city 
function citylistnew() {
	$.fancybox('<div> <ul id="cities"></ul><div class="clear"></div></div>', {
		'onComplete' : function() {
			citynew('#cities');
			//$.fancybox.resize();
			//$.fancybox.center();
			//lt:20150928  弹出居中					
            $('#fancybox-wrap').delay(500).queue(function(){$('#fancybox-wrap').css({"top":"113px"});$('this').clearQueue();});
		}
	});
}
function citynew(m) {
	var content;
	$.ajax({
		url : apiurl + 'getcities',
		type : 'GET',
		dataType : 'JSON',// here
		success : function(data) {
			if (typeof (data.error) == 'undefined') {
				var _html = '';
				$.each(data, function(index, obj) {
					// lt:20150915
					_html += '<li onclick="setcity(\'' + obj.cityId + '\',\''
							+ obj.city + '\',\'' + obj.provinceId + '\');" >'
							+ obj.city + '</li>';
					// lt:20150915 end
					// _html += '<li
					// onclick="getcategories(\''+obj.cityId+'\',\''+k+'\',\''+obj.city+'\');"
					// data-val="'+obj.cityId+'">'+obj.city+'</li>';
				});
				$(m).html(_html);

			}
		}
	});
}
function setcity(n, m, l) {

	var k = '#citynameinput';
	var j = '#cityidinput';
	var i = '#provinceinput';
	var tmp = $(j).val();
	var q = $("input[name='detailAddress']");

	if (tmp != n) {
		// if(confirm('修改城市需要您重新填写地址信息')){
		// $("input[name='detailAddress']").val('');
		// } else {
		// return false;
		// }
	}
	$(j).val(n);
	$(k).val(m);
	$(i).val(l);
	// ordertype0addr(q);
	orderform1type2new(n, l);
	$.fancybox.close();
}
// lt:20150923 重新设置city end

function getfee() {

	// var waiterFee = $('#waiterFee').val();

	var deliveryFee = $('#deliveryFee').text();

	// var dishtotal = $('#dishPrice').val();

	// waiterFee = (Math.round(waiterFee*100)/100);

	deliveryFee = (Math.round(deliveryFee * 100) / 100);

	$('#deliveryFee').text(deliveryFee);

	// dishtotal = (Math.round(dishtotal*100)/100);

	// var totalPrice = waiterFee + deliveryFee + dishtotal;

	// $('#totalPrice').val(totalPrice);

}

function orderform1type0() {

	$.ajax({

		url : apiurl + 'testbaidu' + '',

		type : 'GET',

		dataType : 'JSON',// here

		success : function(data) {

			// alert(JSON.stringify(data));

			if (typeof (data.error) == 'undefined') {

				// alert(JSON.stringify(data));

				storeid = data[0];
				// alert(storeid);

				$("input[name='storeId']").val(data[0]);

				var tmp = $("input[name='storeId']").val();

				// alert(tmp);

			}

		}

	});

}

// 选择外送
var _mendiandizhi;
function ordertype0() {

	$('#dizhi').show();

	$('#mendian').hide();

	_mendiandizhi = $("#mendiandizhi").html();

	// $("#mendiandizhi").html('');

	$("#deliveryFee").text(0);

	// orderform1type0();

	var addr = $("input[name='detailAddress']").val();

	if (addr != '') {

		ordertype0addr();

	}

	CookUtils.addCookie("ordertype_c", '0');
	getfee();

}

function ordertype0addr() {

	var addr = $("input[name='detailAddress']").val();

	var cityid = $("input[name='cityId']").val();
	var cityname = $("input[name='cityname']").val();
	var provinceid = $("input[name='provinceId']").val();

	if (addr && cityid) {
		// 不包含市
		if (addr.indexOf("市") == -1) {
			var vnival = $("#citynameinput").val();
			// 并且地址里不是以选择的市的前缀开始，将地址拼起来
			if (addr.indexOf(vnival.replace("市", "")) != 0) {
				addr = vnival + addr;
			}
		}

		$.ajax({

			url : apiurl + 'getstores0?cityId=' + cityid + '&provinceId='
					+ provinceid + '&address=' + addr + '',

			type : 'GET',

			async : false,
			dataType : 'JSON',// here

			success : function(data) {

				// alert(JSON.stringify(data));

				if (typeof (data.error) == 'undefined') {

					// alert(JSON.stringify(data));

					// $("input[name='storeId']").val(data[0]);

					// var tmp = $("input[name='storeId']").val();

					// alert(tmp);

					if (typeof (data.deliveryFee) == 'undefined') {

						$('#deliveryFee').text(10);

					} else {

						$('#deliveryFee').text(data.deliveryFee);

					}

					if (typeof (data.storeId) == 'undefined') {

						// $('#deliveryFee').text(10);

					} else {

						$("input[name='storeId']").val(data.storeId);
						// alert(data.storeId);
						// distance 距离
						CookUtils.addCookie("deliveryFee", data.deliveryFee);
						CookUtils.addCookie("distance", data.distance);
						//alert("外送费："+data.distance);

					}

					getfee();

				}

			}

		});

	}

}

// 选择自取
function ordertype2() {

	$('#dizhi').hide();

	$('#mendian').show();

	$("#mendiandizhi").html(_mendiandizhi);

	$("#deliveryFee").text(0);

	CookUtils.addCookie("ordertype_c", '2');

	getfee();

}

function orderform1type2() {

	$.ajax({

		// url: apiurl+'getstores2?cityId='+cityid+'',
		url : apiurl + 'getstores2?cityId=' + cityid + '&provinceId='
				+ provinceid + '',// lt:20150915

		type : 'GET',

		dataType : 'JSON',// here

		success : function(data) {

			// alert(JSON.stringify(data));

			if (typeof (data.error) == 'undefined') {

				$('#storeid2').empty();

				_html = '<option value="">请选择门店自取</option>';

				$.each(data, function(index, obj) {

					if (typeof (obj) == 'undefined' || obj == null) {

					} else {

						// alert(obj);

						_html += '<option value="' + obj.storeId
								+ '" data-addr="' + obj.storeAddress + '" >';

						_html += '' + obj.storeName + '，电话：';

						_html += '';

						_html += '' + obj.storeTele + '';

						_html += '</option>';

					}

				});

				$('#storeid2').html(_html);

			}

		}

	});

}

function orderform1type2new(n, m, sid) {

	if ($.isEmpty(n)) {
		n = cityid;
	}

	if ($.isEmpty(m)) {
		m = provinceid;
	}
	$.ajax({

		// url: apiurl+'getstores2?cityId='+cityid+'',
		url : apiurl + 'getstores2?cityId=' + n + '&provinceId=' + m + '',// lt:20150915

		type : 'GET',

		dataType : 'JSON',// here

		success : function(data) {

			// alert(JSON.stringify(data));

			if (typeof (data.error) == 'undefined') {

				$('#storeid2').empty();

				_html = '<option value="">请选择门店自取</option>';

				$('#mendiandizhi').html('');
				$.each(data, function(index, obj) {

					if (typeof (obj) == 'undefined' || obj == null) {

					} else {

						_html += '<option value="' + obj.storeId
								+ '" data-addr="' + obj.storeAddress

						if (obj.storeId == sid) {
							_html += '" selected="selected'
						}

						_html += '" >';

						_html += '' + obj.storeName + '，电话：';

						_html += '';

						_html += '4008107107';// 门店电话统一//lt:20150920

						_html += '</option>';

						// alert(obj.storeId);

						// alert(n);

						if (obj.storeId == sid) {

							// alert(n);

							$('#mendiandizhi').html(obj.storeAddress);

						}

					}

				});

				$('#storeid2').html(_html);

				$('#storeid2 option[value=' + n + ']').attr("selected", true);

			}

		}

	});

}

function getType0Md() {

	var addr = $("input[name='detailAddress']").val();

	if (addr == '' || addr == null) {

		alert('请填写地址');

		return false;

	}

	$
			.ajax({

				url : apiurl + 'getstores0?cityId=' + cityid + '&address='
						+ addr + '',

				type : 'GET',

				dataType : 'JSON',// here

				success : function(data) {

					// alert(JSON.stringify(data));

					if (typeof (data.error) == 'undefined') {

						// alert(JSON.stringify(data));

						var _html = '';

						$
								.each(
										data,
										function(index, obj) {

											obj.unitPrice = (Math
													.round(obj.unitPrice * 100) / 100);

											_html += '<dl onclick="$(\'input[name=\"storeId\"]\').val('
													+ obj.storeId
													+ ');" >'
													+ obj.storeName
													+ ' '
													+ obj.storeTele
													+ '</dd><dt>'
													+ obj.storeAddress
													+ '</dt></dl>';

										});

						$
								.fancybox(

										'<center ></center><div> <ul id="newstorelist"></ul><div class="clear"></div>',

										{

											'onComplete' : function() {

												// getcities('#cities');

												// alert(_html);

												$('#newstorelist').html(_html);

												$.fancybox.resize();

											}

										}

								);

					}

				}

			});

}

$(document).ready(
		function(e) {

			History.init();
			Listenter.add();

			// orderform1type0();

			$("input[name='dinningTime']").val(getNowFormatDate());

			$("input[name='cityId']").val(cityid);
			$("input[name='cityname']").val(cityname);
			$("input[name='provinceId']").val(provinceid);// lt:20150928

			// alert('11');

			show_date('dinningTime', true);

			var cartdish = {};

			cartdish = getcartdishinfo();

			cartpack = getcartpackinfo();

			cartdiyguodi = getcartdiyguodiinfo();

			$('#dizhi').show();

			$('#mendian').hide();

			_html = '';

			var dishtotal = 0;

			$.each(cartdish, function(index, obj) {

				if (typeof (obj) == 'undefined' || obj == null) {

				} else {

					// alert(obj);

					dishtotal += obj.price * obj.num;

					_html += '<dl><dt>' + obj.name
							+ '</dt><dd><span class="f_c6000a p_r_50">x'
							+ obj.num + '</span><span class="f_c6000a">'
							+ obj.price + '元</span></dd></dl>';

				}

			});

			$.each(cartpack, function(index, obj) {

				if (typeof (obj) == 'undefined' || obj == null) {

				} else {

					// alert(obj);

					dishtotal += obj.price * obj.num;

					_html += '<dl><dt>' + obj.name
							+ '</dt><dd><span class="f_c6000a p_r_50">x'
							+ obj.num + '</span><span class="f_c6000a">'
							+ obj.price + '元</span></dd></dl>';

				}

			});

			$.each(cartdiyguodi, function(index, obj) {

				if (typeof (obj) == 'undefined' || obj == null) {

				} else {

					// alert(obj);

					dishtotal += obj.price * obj.num;

					_html += '<dl><dt>' + obj.name
							+ '</dt><dd><span class="f_c6000a p_r_50">x'
							+ obj.num + '</span><span class="f_c6000a">'
							+ obj.price + '元</span></dd></dl>';

				}

			});

			$('#dishlist').html(_html);

			$('#dishPrice').val(dishtotal);

			var waiterFee = $('#waiterFee').val();

			var deliveryFee = $('#deliveryFee').text();

			waiterFee = (Math.round(waiterFee * 100) / 100);

			deliveryFee = (Math.round(deliveryFee * 100) / 100);

			var totalPrice = waiterFee + deliveryFee + dishtotal;

			$('#totalPrice').val(totalPrice);

			$('#imgjian').click(function() {

				var tmp = $('#potNumber').val();

				tmp = parseInt(tmp);

				if (tmp > 0) {

					tmp--;

					$('#potNumber').val(tmp);

				} else {

					$('#potNumber').val('0');

				}

			});

			$('#imgjia').click(function() {

				var tmp = $('#potNumber').val();

				tmp = parseInt(tmp);

				if (tmp > 0) {

					tmp++;

					$('#potNumber').val(tmp);

				} else {

					$('#potNumber').val('1');

				}

			});

			$("#storeid2").change(function() {

				var tmp = $("#storeid2").val();

				$("input[name='storeId']").val(tmp);

				// alert();

				var tmp1 = $("#storeid2  option:selected").attr('data-addr');

				$("#mendiandizhi").html(tmp1);

			});

			var userformjson = eval('(' + $.cookie("userformjson") + ')');

			if (typeof (userformjson) == 'undefined' || userformjson == null
					|| userformjson == 'null' || userformjson == '') {

				// alert('请填写订餐信息');
				// 门店列表
				orderform1type2();
				var ot = $.cookie('ordertype_c');

				$("input[name='orderType'][value=" + ot + "]").click();

			} else {

				// alert(JSON.stringify(userformjson));

				$("input[name='storeId']").val(userformjson.storeId);

				$("input[name='contactName']").val(userformjson.contactName);

				$("input[name='contactPhone']").val(userformjson.contactPhone);

				$("input[name='participantNumber']").val(
						userformjson.participantNumber);

				// $("input[name='payChannel']").val(userformjson.payChannel);

				// $("input[name='orderType']").val(userformjson.contactName);

				$(
						"input[name='orderType'][value="
								+ userformjson.orderType + "]").attr("checked",
						true);

				if (userformjson.orderType == '2') {

					// alert(userformjson.orderType);

					$('#dizhi').hide();

					$('#mendian').show();

				}

				orderform1type2new('', '', userformjson.storeId);

				$("input[name='cityId']").val(userformjson.cityId);
				$("input[name='provinceId']").val(userformjson.provinceId);

				$("input[name='detailAddress']")
						.val(userformjson.detailAddress);

				$("#deliveryFee").text(userformjson.deliveryFee);

			}

		});
function userform(){
	/**
	 * 校验难码
	 */
	var pv = $("#phoneValidate");
	if($("#phoneValidateDl").css("display") == "none"){
		userformSuccess();
	}else{
		var code = pv.val();
		if(code.trim() == ''){
			alert("请输入验证码！");
			return;
		}
		Listenter.checkVerificationCode(code,function(){
			userformSuccess();
		});
	}
}
/**
 * 提交
 * @returns {Boolean}
 */
function userformSuccess() {

	var payChannel = $("input[name='payChannel']").val();

	var cn = $("input[name='contactName']").val()
	if (cn == '') {
		alert('请填写联系人');
		return false;

	}

	if ($("input[name='orderType']:checked").val() == '0'
			&& $("input[name='detailAddress']").val() == '') {

		alert('请填写地址');

		return false;

	}

	if ($("input[name='orderType']:checked").val() == '2'
			&& $('#storeid2').val() == '') {

		alert('请选择门店');

		return false;

	}
	if(Listenter.checkedPhone(true)){
		return false;
	};
	var num = $("input[name='participantNumber']").val();
	if ( $.trim(num)== '') {

		alert('请填写用餐人数');

		return false;

	}
	if(!/^[0-9]*$/.test(num)){
		alert("人数必需数字!");
		return false;
	}
	//TODO:测试保存
	History.save();
	//return;
	// 计算下外卖费
	var ot = $("input[name='orderType']:checked").val();
	if (ot == 0) {
		ordertype0addr();
	}
	var deliveryFee = $('#deliveryFee').text();
	if (parseInt(deliveryFee) > 300) {
		if (!confirm('您的单锅外卖费是:' + deliveryFee + '元！请确认')) {
			return false;
		}
	}

	var userformjson = eval('(' + $.cookie("userformjson") + ')');

	var tmp = $.trim($("input[name='storeId']").val());

	if (typeof (userformjson) == 'undefined' || userformjson == null
			|| userformjson == 'null' || userformjson == '') {

		// alert('请填写订餐信息');

	} else {

		if (tmp != userformjson['storeId']) {// //lt:20150920
												// 根据门店id判断是否清空购物篮，不根据地址判断

			// alert(tmp);
			// alert(userformjson['storeId']);
			if (confirm('由于您更改了门店，将会清空购物篮？')) {

				$.cookie('storeid1', null, {
					expires : 30
				});

				$.cookie('cartdish', null, {
					expires : 30
				});

				$.cookie('cartpack', null, {
					expires : 30
				});

				$.cookie('cartdiyguodi', null, {
					expires : 30
				});

			} else {

				return false;

			}

		}

	}

	var content = {

		storeId : $.trim($("input[name='storeId']").val()),

		contactName : $.trim($("input[name='contactName']").val()),

		contactPhone : $.trim($("input[name='contactPhone']").val()),

		participantNumber : $.trim($("input[name='participantNumber']").val()),

		payChannel : $.trim($("input[name='payChannel']").val()),
		cityName : $.trim($("input[name='cityname']").val()),

		orderType : $.trim($("input[name='orderType']:checked").val()),

		cityId : $.trim($("input[name='cityId']").val()),
		provinceId : $.trim($("input[name='provinceId']").val()),

		detailAddress : $.trim($("input[name='detailAddress']").val()),

		deliveryFee : $.trim($("#deliveryFee").text())

	};

	tmp = $.trim($("input[name='storeId']").val());

	tmp1 = JSON.stringify(content);

	// alert(tmp);

	$.cookie("storeid1", tmp, {
		expires : 30
	});

	$.cookie("userformjson", tmp1, {
		expires : 30
	});

	$.cookie("cityname", $("input[name='cityname']").val(), {
		expires : 30
	});
	$.cookie("cityid", $("input[name='cityId']").val(), {
		expires : 30
	});
	$.cookie("provinceid", $("input[name='provinceId']").val(), {
		expires : 30
	});

	// alert(tmp1);
	var referpage = $.cookie("userformrefer");

	var indxhtml = 'index.html?loginId='+U.userdata.user.user_entity_id;
	if (typeof (referpage) == 'undefined' || referpage == null
			|| referpage == 'null' || referpage == '') {
		goLocationWithCity(indxhtml);
	} else if (referpage == 'userform') {
		goLocationWithCity(indxhtml);
	} else if (referpage == 'pack' || referpage == 'dish'
			|| referpage == 'diyguodi' || referpage == 'orderform1') {
		goLocationWithCity(referpage + '.html');
	} else {
		goLocationWithCity(indxhtml);
	}

	// window.location='./index.html';

}
var Listenter = {
		defaultTime:60,
		settime:function(val){
				if (Listenter.countdown == 0) {
					val.removeAttr("disabled");
					val.css("background","#F3F0F0");
					val.val("免费获取验证码");
					//Listenter.countdown = Listenter.defaultTime;
				} else {
					val.attr("disabled", true);
					val.css("background","#B5B5B5");
					val.val("重新发送(" + Listenter.countdown + ")");
					Listenter.countdown--;
					setTimeout(function() {
						Listenter.settime(val)
					}, 1000)
				}
		},
		/**
		 * 校验手机验证码
		 */
		checkVerificationCode:function(code,back){
			$.ajax({
				url : apiurl + 'checkVerificationCode',// ?loginId='+logintmp,
				type : 'POST',
				data:{
					code:code,
					phone:$("#contactPhone").val()
				},
				//async: false ,
				dataType : 'JSON',// here
				success : function(data) {
					if (data.respCode=='011') {
						back();	
					}else{
						return alert("手机校验码验证"+data.respMsg)
					}
				}
			});
		},
		/**
		 * 获得手机验证码
		 */
		getVerificationCode:function(back){
			$.ajax({
				url : apiurl + 'getVerificationCode',// ?loginId='+logintmp,
				type : 'POST',
				data:{
					phone:$("#contactPhone").val()
				},
				//async: false ,
				dataType : 'JSON',// here
				success : function(data) {
					if (data.respCode=='011') {
						back();	
					}else{
						alert(data.respMsg)
					}
				}
			});
		},
		add:function(){
			//校验码
			$("#phoneValidateBu").click(function(){

				if(!Listenter.checkedPhone(true)){
					//倒记时
					Listenter.getVerificationCode(function(){
						Listenter.countdown=Listenter.defaultTime,
						Listenter.settime($("#phoneValidateBu"));
					});
				};
			});
			$("#contactPhone").blur(function(){
				if(!Listenter.checkedPhone(false)){
					//判断手机号是否改变
					var cp = $("#contactPhone").val();
					var mymos = History.params.a2;
					var ishide = false
					for (var i = 0; i < mymos.length; i++) {
						var mymo = mymos[i];
						if(mymo.name == cp){
							ishide = true;
						}
					}
					//var hi_mobile =  $.cookie("hi_mobile");
					//if(hi_mobile!=null && cp == hi_mobile){
					if(ishide){
						$('#phoneValidateDl').hide();
					}else{
						$('#phoneValidateDl').show();
					}
				}
			});

			// jxb:20150929
			// 只能让用餐人数输入数字
			$("#participantNumber").blur(function() {
				var flag = checkNumber($(this).val());
				if (!flag) {
					$(this).focus();
				}
			});
		},
		
		checkedPhone:function(b){
			var cp = $("#contactPhone").val();
			if (cp == '') {
				if(b)alert('请填写手机');
				return true;
			} else if (cp.length != 11) {
				if(b)alert('请填写正确的手机号');
				return true;
			}
			if(!/^[0-9]*$/.test(cp)){
				if(b)alert("手机号必需数字!");
				return true;
			}
			return false;
		}
}
//用户历史信息
var History = {
		init:function(){ 
			History.getHistory('getHistoryUser');
		},
		params: {
			a1:[],
			a2:[],
			a3:[]
		},
		auto:{},
		getHistory:function(url){
			$.ajax({
						url : apiurl + url,// ?loginId='+logintmp,
						type : 'GET',
						dataType : 'JSON',// here
						success : function(data) {
							if (data) {
								var i1=0,i2=0,i3=0;
								function build(param,d,i){
									param[i]={};
									param[i].name = d.info;
									param[i].id = d.id;
								}
								for (var i = 0; i < data.length; i++) {
									var d = data[i];
									var t = d.types;
									if(t==1){
										build(History.params.a1,d,i1);
										i1++;
									}else if(t==2){
										build(History.params.a2,d,i2);
										i2++;
									}else if(t==3){
										build(History.params.a3,d,i3);
										i3++;
									}
								}
								History.autocomplete("contactName",History.params.a1,440);
								History.autocomplete("contactPhone",History.params.a2,440);
								History.autocomplete("detailAddress",History.params.a3,345);
							} else {
								alert(data)
							}
						}
					});
		},
		autocomplete:function(id,param,width){
			//if(History.auto[id]==null){
				History.auto[id] =   $('#'+id).autocomplete(param, {
	                max: 12,    //列表里的条目数
	                minChars: 0,    //自动完成激活之前填入的最小字符
	                width: width,     //提示的宽度，溢出隐藏
	                scrollHeight: 300,   //提示的高度，溢出显示滚动条
	                matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
	                autoFill: false,    //自动填充
	                isDel:true,
	                formatItem: function(row, i, max) {
	                    return row.name;
	                },
	                formatMatch: function(row, i, max) {
	                    return row.name;
	                },
	                formatResult: function(row) {
	                    return row.to;
	                },
	                del:function(id,nid,options){
	                	$.ajax({
							url : apiurl + 'deleteHistoryUser?id='+id,// ?loginId='+logintmp,
							type : 'POST',
							dataType : 'JSON',// here
							success : function(data) {
								alert(data.respMsg);
								if (data.respCode=='005') {
									if(!delp("a1",id,"contactName")){
										if(!delp("a2",id,"contactPhone")){
											delp("a3",id,"detailAddress")
										}
									}
								}
								//重新构建autocomplete
								function delp(aid,id,nid){
									var b = false;
									var pas2 = [],i2= 0;
									var params = History.params[aid];
									for (var i = 0; i < params.length; i++) {
										var p = params[i];
										if(p.id==id){
											b = true;
										}else{
											pas2[i2]=p;
											i2++;
										}
									}
									if(b){
										options.data= pas2;
										History.params[aid] = pas2;
										History.auto[nid].setOptions(options);
										//History.auto[nid].flushCache();
										//History.autocomplete(nid,pas2);
										return b;
									}
								}
							}
	                	});
	                }
	            });
				History.auto[id].result(function(event, row, formatted) {
	                //alert(row.id);
	            });
			//}else{
			//	var au = History.auto[id];
			//	alert(au);
			//}
		},
		//保存
		save:function(){
			$.ajax({
				url : apiurl + 'saveHistoryUser',// ?loginId='+logintmp,
				type : 'POST',
				dataType : 'JSON',// here
				data:{
					name:$("#contactName").val(),
					phone:$("#contactPhone").val(),
					address:$("#detailAddress").val()
				},
				success : function(data) {
					if (data.respCode=='004') {
						
					} else {
						
					}
				}
			});
		}
}
