
nowpage = 'orderform1';

storeid = '';

var userformjson = {};

userformjson = $.cookie('userformjson');

// var currentYear = '2015-09-15';
var currentYear = getNowFormatDate();// lt:20150915
var timeLimit = {}// lt:20150915
var storeBusyMsg = '您好，您所预定的时间为该店繁忙时间段，给您带来不便之处，请谅解。';// lt:20150915

$(document).ready(function(e) {

	// alert(cityname.innerHTML );

	// $('#test').html(cityname.html())

	getcategories(cityid, '#catul', cityname);

	// getdishes(catid,'#dishul','1');

	// $(n).parent().find('li').removeClass('li_hover');

	//

});

// lt:20150915
function getTimeLimit() {
	$.ajax({

		url : apiurl + 'getdeliverylimit?storeId=' + storeid + '',
		type : 'GET',
		dataType : 'JSON',// here
		success : function(data) {
			// alert(typeof(data));
			if (typeof (data) != 'undefined') {
				// 与原有接口的参数对比 //lt:20150919
				var busytimelist = data.busyTimes;
				var closetimelist = data.closeTimes;
				var delevierlimitmoney1 = data.deliveryLimitMoney;
				var delevierlimitmoney2 = '';
				var delevierlimitmoney3 = '';
				var firstDayLowHour = data.firstDayStartTime;
				var lowDate = data.startDay;
				var lowhour = data.startTime;
				var lowtime = '';
				var upDate = data.endDay;
				var uphour = data.endTime;
				var uptime = '';
				var msg = data.message;
				var result = [ {
					"returncode" : "0",
					"returnmsg" : "success"
				} ];
				timeLimit = {
					"busytimelist" : busytimelist,
					"closetimelist" : closetimelist,
					"delevierlimitmoney1" : delevierlimitmoney1,
					"delevierlimitmoney2" : delevierlimitmoney2,
					"delevierlimitmoney3" : delevierlimitmoney3,
					"firstDayLowHour" : firstDayLowHour,
					"lowDate" : lowDate,
					"lowhour" : lowhour,
					"lowtime" : lowtime,
					"upDate" : upDate,
					"uphour" : uphour,
					"uptime" : uptime,
					"msg" : msg,
					"result" : result
				};
				// alert(timeLimit.uphour);

			}
		}
	});
}
// lt:20150915 end

function getfee() {

	getFfeeYfee();
	var waiterFee = $('#waiterFee').val();

	var deliveryFee = $('#deliveryFee').val();
	var depositFee = $('#depositFee').val();

	var dishtotal = $('#dishPrice').val();

	// waiterFee = (Math.round(waiterFee*100)/100);servicetotle
	// 服务费
	waiterFee = parseInt($("#servicetotle").text());//
	// 外送费
	deliveryFee = parseInt($("#ousidetotle").text());//

	deliveryFee = (Math.round(deliveryFee * 100) / 100);
	depositFee = (Math.round(depositFee * 100) / 100);

	dishtotal = (Math.round(dishtotal * 100) / 100);

	var totalPrice = waiterFee + depositFee + deliveryFee + dishtotal;

	$('#waiterFee').val(waiterFee);

	$('#deliveryFee').val(deliveryFee);

	$('#dishPrice').val(dishtotal);

	$('#totalPrice').val(totalPrice);

}

function orderform1type0() {

	return;
	$.ajax({

		url : apiurl + 'testbaidu' + '',

		type : 'GET',

		dataType : 'JSON',// here

		success : function(data) {

			// alert(JSON.stringify(data));

			if (typeof (data.error) == 'undefined') {

				// alert(JSON.stringify(data));

				storeid = data[0];

				$("input[name='storeId']").val(data[0]);

				var tmp = $("input[name='storeId']").val();

				// alert(tmp);

			}

		}

	});

}

function ordertype0() {

	$('#dizhi').show();

	$('#mendian').hide();

	// $("#mendiandizhi").html('');

	$("#deliveryFee").val(0);

	var addr = $("input[name='detailAddress']").val();

	if (addr != '') {

		ordertype0addr();

	}

	getfee();

}

function ordertype0addr() {

	var addr = $("input[name='detailAddress']").val();

	var cityid = $("input[name='cityId']").val();

	if (addr && storeid) {

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

						$('#deliveryFee').val(10);

					} else {

						$('#deliveryFee').val(data.deliveryFee);

					}
					if (typeof (data.storeId) == 'undefined') {

						// $('#deliveryFee').val(10);

					} else {

						$("input[name='storeId']").val(data.storeId);
						// distance 距离
						CookieUtils.addCookie("deliveryFee", data.deliveryFee);
						alert("外送费：" + data.distance);

					}

					getfee();

				}

			}

		});

	}

}

function ordertype2() {

	$('#dizhi').hide();

	$('#mendian').show();

	// $("#mendiandizhi").html('');

	$("#deliveryFee").val(0);

	getfee();

}

function orderform1type2(n) {

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

						_html += '4008107107';// 门店电话统一//lt:20150920

						_html += '</option>';

						// alert(obj.storeId);

						// alert(n);

						if (obj.storeId == n) {

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

function getFfeeYfee() {
	// var potNum = $("input[name='potNumber']").val();
	// var panNum = $("input[name='panNumber']").val();
	var potNum = $("#potNumber").text();
	var panNum = $("#panNumber").text();
	var ordertype = $("input[name='orderType']:checked").val();
	var dishPrice = $("input[name='dishPrice']").val();
	var tmp = 0;
	tmp = parseInt(potNum) + parseInt(panNum);
	var tmp1 = 0;

	// alert(ordertype);

	if (ordertype == '2') {

		if (tmp > 0) {
			tmp1 = potNum * 400 + panNum * 100;
			tmp1 = (Math.round(tmp1 * 100) / 100);
			$("input[name='depositFee']").val(tmp1);
		}

	} else if (ordertype == '0') {
		if (tmp > 0) {
			tmp1 = dishPrice / 10;
			tmp1 = (Math.round(tmp1 * 100) / 100);
			$("input[name='waiterFee']").val(tmp1);
		}

	}
	// zs:
	var usecup = $('#lujuC');// $('input[name="usecup"]:checked').val();
	var usecup2 = $('#guojuC');// $('input[name="usecup2"]:checked').val();
	// 都选是外卖费清零
	if (usecup.attr("checked") == 'checked'
			&& usecup2.attr("checked") == 'checked') {
		TotleUtils.all(0);
	} else {

		var st = parseInt($('#servicetotle').text());
		if (usecup == '1') {
			if (st == usecup_totle || st == usecup_totle + usecup2_totle) {
				calculateServiceTotle('del', 1);
			}
		} else if (usecup2 == '1') {
			if (st == usecup2_totle || st == usecup_totle + usecup2_totle) {
				calculateServiceTotle('del', 1);
			}
		}
		TotleUtils.all(0.1);
	}
}
function calculateServiceTotle(t, type) {
	// 总费用计算
	TotleUtils.all(0.1);
}
// end....

$(document).ready(
		function(e) {

			getTimeLimit();
			orderform1type0();

			$('#dizhi').show();

			$('#mendian').hide();

			$("input[name='dinningTime']").val(getNowFormatDate());

			$("input[name='cityId']").val(cityid);
			$("input[name='cityname']").val(cityname);

			// alert('11');

			// show_date('dinningTime',true);

			var userformjson = {};

			userformjson = eval('(' + $.cookie("userformjson") + ')');

			if (typeof (userformjson) == 'undefined' || userformjson == null
					|| userformjson == 'null' || userformjson == '') {

				alert('请填写订餐信息');
				goLocationWithCity('userform.html');
				return false;

			}

			// alert(JSON.stringify(userformjson));

			$("input[name='storeId']").val(userformjson.storeId);

			$("input[name='contactName']").val(userformjson.contactName);

			$("input[name='contactPhone']").val(userformjson.contactPhone);

			$("input[name='participantNumber']").val(
					userformjson.participantNumber);

			// $("input[name='payChannel']").val(userformjson.payChannel);

			// $("input[name='orderType']").val(userformjson.contactName);

			$("input[name='orderType'][value=" + userformjson.orderType + "]")
					.attr("checked", true);

			if (userformjson.orderType == '2') {

				// alert(userformjson.orderType);

				$('#dizhi').hide();

				$('#mendian').show();
				$('#feiyong #yfee').show();

			}
			if (userformjson.orderType == '0') {

				// alert(userformjson.orderType);

				$('#feiyong #ffee').show();

			}

			orderform1type2(userformjson.storeId);

			$("input[name='cityId']").val(userformjson.cityId);
			$("input[name='provinceId']").val(userformjson.provinceId);

			$("input[name='detailAddress']").val(userformjson.detailAddress);

			$("input[name='deliveryFee']").val(userformjson.deliveryFee);

			var cartdish = {};

			cartdish = getcartdishinfo();

			cartpack = getcartpackinfo();

			cartdiyguodi = getcartdiyguodiinfo();

			_html = '';

			var dishtotal = 0;

			$.each(cartdish, function(index, obj) {

				if (typeof (obj) == 'undefined' || obj == null) {

				} else {

					// alert(obj);

					// dishtotal += obj.price * obj.num;

					var apic = obj.num * obj.price;
					dishtotal += apic;
					_html += '<dl><dt>' + obj.name
							+ '</dt><dd><span class="f_c6000a p_r_50">'
							+ obj.price
							+ '元</span><span class="f_c6000a p_r_50">x'
							+ obj.num + '</span><span class="f_c6000a">' + apic
							+ '元</span></dd></dl>';

				}

			});

			$.each(cartpack, function(index, obj) {

				if (typeof (obj) == 'undefined' || obj == null) {

				} else {

					// alert(obj);

					// dishtotal += obj.price * obj.num;
					var apic = obj.num * obj.price;
					dishtotal += apic;

					_html += '<dl><dt>' + obj.name
							+ '</dt><dd><span class="f_c6000a p_r_50">'
							+ obj.price
							+ '元</span><span class="f_c6000a p_r_50">x'
							+ obj.num + '</span><span class="f_c6000a">' + apic
							+ '元</span></dd></dl>';

				}

			});

			$.each(cartdiyguodi, function(index, obj) {

				if (typeof (obj) == 'undefined' || obj == null) {

				} else {

					// alert(obj);

					// dishtotal += obj.price * obj.num;
					var apic = obj.num * obj.price;
					dishtotal += apic;

					_html += '<dl><dt>' + obj.name
							+ '</dt><dd><span class="f_c6000a p_r_50">x'
							+ obj.num + '</span><span class="f_c6000a">' + apic
							+ '元</span></dd></dl>';

				}

			});

			$('#dishlist').html(_html);

			$('#dishPrice').val(dishtotal);

			var waiterFee = $('#waiterFee').val();

			var depositFee = $('#depositFee').val();
			var deliveryFee = $('#deliveryFee').val();

			waiterFee = (Math.round(waiterFee * 100) / 100);
			depositFee = (Math.round(depositFee * 100) / 100);

			deliveryFee = (Math.round(deliveryFee * 100) / 100);

			var totalPrice = waiterFee + depositFee + deliveryFee + dishtotal;

			$('#totalPrice').val(totalPrice);

			// end......

			$("#storeid2").change(function() {

				var tmp = $("#storeid2").val();

				$("input[name='storeId']").val(tmp);

				// alert();

				var tmp1 = $("#storeid2  option:selected").attr('data-addr');

				$("#mendiandizhi").html(tmp1);

			});

		});

// 提交订单

function orderform1() {

	var cartdish = {};

	cartdish = getcartdishinfo();

	var cartpack = {};

	cartpack = getcartpackinfo();

	var cartdiyguodi = {};

	cartdiyguodi = getcartdiyguodiinfo();

	var payChannel = $("input[name='payChannel']:checked").val();

	// alert($("input[name='reciept']:checked").val());

	if ($("input[name='needReciept']:checked").val() == '1'
			&& $("input[name='recieptType']:checked").val() == '1'
			&& $("input[name='recieptDept']").val() == '') {

		alert('请填写发票抬头');

		return false;

	}

	var dishesjson = [];

	$.each(cartdish, function(index, obj) {

		if (typeof (obj) == 'undefined' || obj == null) {

		} else {

			// alert(obj);

			var tmp = {
				dishId : obj.id,
				dishNumber : obj.num
			};

			dishesjson.push(tmp);

		}

	});

	$.each(cartdiyguodi, function(index, obj) {

		if (typeof (obj) == 'undefined' || obj == null) {

		} else {

			// alert(obj);

			var tmp = {
				guodiId : obj.id,
				dishNumber : obj.num
			};

			dishesjson.push(tmp);

		}

	});

	var packsjson = [];

	$.each(cartpack, function(index, obj) {

		if (typeof (obj) == 'undefined' || obj == null) {

		} else {

			// alert(obj);

			var tmp1 = obj.guodi;

			var tmp2 = obj.xiaoliao;

			// alert(JSON.stringify(tmp1));

			var tmppacks = [];

			$.each(tmp1, function(index1, obj1) {

				if (typeof (obj1) == 'undefined' || obj1 == null) {

				} else {

					// alert(obj);

					var tmp = {
						dishId : obj1.id,
						dishNumber : obj1.num
					};

					tmppacks.push(tmp);

				}

			});

			$.each(tmp2, function(index2, obj2) {

				if (typeof (obj2) == 'undefined' || obj2 == null) {

				} else {

					// alert(obj);

					var tmp = {
						dishId : obj2.id,
						dishNumber : obj2.num
					};

					tmppacks.push(tmp);

				}

			});

			// alert(JSON.stringify(tmppacks));

			var tmp = {
				packId : obj.id,
				packCount : obj.num,
				dishes : tmppacks
			};

			packsjson.push(tmp);

		}

	});

	var needReciept = $.trim($("input[name='needReciept']:checked").val());

	var recieptDept = $.trim($("input[name='recieptDept']").val());

	if (needReciept == 1 && recieptDept == '') {

		// alert('请填写发票抬头');

	}

	var time1 = $.trim($("#d12").val());
	var time2 = $.trim($("#selHour option:checked").val());
	var time3 = $.trim($("#selMin option:checked").val());

	var content = {

		storeId : $.trim($("input[name='storeId']").val()),

		contactName : $.trim($("input[name='contactName']").val()),

		contactPhone : $.trim($("input[name='contactPhone']").val()),

		participantNumber : $.trim($("input[name='participantNumber']").val()),

		dinningTime : time1 + ' ' + time2 + ':' + time3 + ':00',

		needReciept : $.trim($("input[name='needReciept']:checked").val()),

		recieptType : $.trim($("input[name='recieptType']:checked").val()),

		recieptDept : $.trim($("input[name='recieptDept']").val()),

		custMemo : $.trim($("input[name='custMemo']").val()),

		payChannel : $.trim($("input[name='payChannel']:checked").val()),

		potNumber : $.trim($("#potNumber").text()),
		panNumber : $.trim($("#panNumber").text()),

		orderType : $.trim($("input[name='orderType']:checked").val()),

		address : {

			// provinceId: '123456',

			cityId : $.trim($("input[name='cityId']").val()),
			cityName : $.trim($("input[name='cityname']").val()),

			detailAddress : $.trim($("input[name='detailAddress']").val())

		},

		expenses : {

			waiterFee : $.trim($("input[name='waiterFee']").val()),

			deliveryFee : $.trim($("input[name='deliveryFee']").val()),

			depositFee : $.trim($("input[name='depositFee']").val()),

			dishPrice : $.trim($("input[name='dishPrice']").val()),

			totalPrice : $.trim($("input[name='totalPrice']").val())

		},

		packs : packsjson,

		dishes : dishesjson

	};

	// 订单内容
	// alert(JSON.stringify(content));

	$.ajax({

		url : apiurl + 'createorder',// 要访问的后台地址

		data : JSON.stringify(content),// 要发送的数据

		type : "post", // 使用post方法访问后台

		dataType : "json", // 返回json格式的数据

		async : true,

		contentType : "application/json;charset=utf-8",

		success : function(msg) {// msg为返回的数据，在这里做数据绑定

			// alert("login response:"+JSON.stringify(msg));
			if (msg == null || typeof (msg) == 'undefined') {
				alert('提交订单出错');
			}
			// 提示失败信息
			// alert(msg.respMsg);

			if (msg.respCode != '310') {

				// window.location='./index.html';
				if (msg.respCode == '101') {
					$.cookie("userid", null);
					$.cookie("username", null);
					goLocationWithCity('index.html');

				}

			} else {

				// 成功

				// alert('订单号：'+msg.orderId);
				if ("0" == msg.payChannel) {
					// 预支付
					$.ajax({
						url : rootUrl + 'pay/prePayOrder?orderId='
								+ msg.orderId + '&txAmt=' + msg.payAmt,// 要访问的后台地址
						type : "post", // 使用post方法访问后台
						dataType : "json", // 返回json格式的数据
						async : true,
						contentType : "application/json;charset=utf-8",
						success : function(preResult) {// preResult为返回的数据
							if (preResult.resultCode == 0) {
								alert(preResult.resultMsg);
							} else {

								// 请求银联支付页面
								var url = rootUrl + 'pay/unionPay?txAmt='
										+ msg.payAmt + '&orderId='
										+ msg.orderId;
								// goLocationWithCity(url);
								window.location.href = url;
							}
						}

					});
				} else {
					// 保存订单号
					$.cookie("sucuess_orderId", msg.orderId, {
						path : '/'
					});
					goLocationWithCity('success.html');
				}
				clearcate();// 清空购物篮 lt:20150919
			}

		}

	});

}

var Sel = {
		//是否开发票
		needReciept:function(val){
			//是
			if(val==1){
				$('#fapiao').show();
				if($("input[name='recieptType']:checked").val() == '1'){
					$('#fapiaott').show();
				}else{
					$('#fapiaott').hide();
				}
			}else{
				$('#fapiao').hide();
				$('#fapiaott').hide();
			}
		},
		//公司个人
		recieptType:function(val){
			//是
			if(val==1){
				$('#fapiaott').show();
				
			}else{
				$('#fapiaott').hide();
			}
		}
}