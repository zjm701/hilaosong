
nowpage = 'packinfo';

var guodilist;

var xiaoliaolist;

var guodijson = {};

var xiaoliaojson = {};

var ntmp;

//确认添加到菜蓝
function addcartpack(n) {
	//校验规则
	if($('#guodi').text()=='无'){
		alert(Packinfo.message.guodiSel);
		return;
	}

	if($('#xiaoliao').text()=='无'){
		alert(Packinfo.message.xiaoliaoSel);
		return;
	}
	if(Packinfo.checkGoudiCount("==")){
		return;
	}

	if(Packinfo.checkXiaoliaoCount("==")){
		return;
	}

	/*getuser();

	var tmp = $.cookie("userid");

	if (typeof (tmp) == 'undefined' || tmp == 'null') {*/
		
	if(getuser()){
		return ;
	} else {
		var name = $(n).attr('data-name');

		var price = $(n).attr('data-price');

		var id = $(n).attr('data-id');

		var num = $('#packnum').val();

		var cartpack = eval('(' + $.cookie("cartpack") + ')'); // $.cookie("cartpack");
		cartpack = cartpack==null?{}:cartpack;
		var index = 0;
		for ( var key in cartpack) {
			var cin = cartpack[key].index;
			if(index<=cin){
				index = cin+1;
			}
		}
		price = (Math.round(price * 100) / 100);
		var idk = id+"_"+index;
		cartpack[idk] = {};

		cartpack[idk]['id'] = id;

		cartpack[idk]['index'] = index;

		cartpack[idk]['num'] = num;

		cartpack[idk]['name'] = name;

		cartpack[idk]['price'] = price;

		cartpack[idk]['guodi'] = Packinfo.guodijson;

		cartpack[idk]['xiaoliao'] = Packinfo.xiaoliaojson;

		$.cookie("cartpack", JSON.stringify(cartpack), {
			expires : 30
		})
		//buildSetmealBasket(cartpack[idk]);
		getcartfee();
		//TotleUtils.all(0.1);
		// $('#'+id+'').remove();
		//$('#cartpackbox').html(_html);
		 goLocationWithCity("pack.html");
	}

}

function cartpackd() {

	var num = $('#packnum').val();

	if (num > 2) {

		num -= 1;

	} else {

		num = 1;

	}

	$('#packnum').val(num);

}

function cartpackp() {

	var num = $('#packnum').val();

	num = parseInt(num);

	num += 1;

	if (num < 2) {

		num = 2;

	}

	$('#packnum').val(num);

}

function getpackinfo(n, m, l, k) {

	if (nowpage != 'packinfo') {

		$.cookie("packid", n, {
			expires : 30
		});

		$.cookie("packname", l, {
			expires : 30
		});

		$.cookie("packprice", k, {
			expires : 30
		});

		// window.location = 'packinfo.html';

		goLocationWithCity('packinfo.html');

	}

	cartpack = $.cookie("cartpack");

	$
			.ajax({

				url : apiurl + 'getpackdishes?dishId=' + n + '',

				type : 'GET',

				dataType : 'JSON',// here

				success : function(data) {
					Packinfo.data = data;
					cartpack = eval('(' + $.cookie("cartpack") + ')');

					if (typeof (data.error) == 'undefined') {

						var _html = '';

						_html += '<div class="m_nav">' + l + '</div>';

						_html += '<div class="blank10" ></div>';

						_html += '<div class="m_input left"><span style="font-weight:bold;">套餐份数</span></div>';

						_html += '<div class="left d_num"><span><img src="images/img_jian.gif" onclick="cartpackd()" /></span>';

						// alert(JSON.stringify(cartpack[n]));

						// alert(cartpack.num);

						if (typeof (cartpack) == 'undefined'
								|| cartpack == null
								|| typeof (cartpack[n]) == 'undefined'
								|| cartpack[n] == null
								|| typeof (cartpack[n].num) == 'undefined'
								|| cartpack[n].num == null) {

							_html += '<span class="f12px fB f_c6000a"><input name="packnum" id="packnum" value="1" style="width:40px;text-align:center;"></span>';

						} else {

							_html += '<span class="f12px fB f_c6000a"><input name="packnum" id="packnum" value="'
									+ cartpack[n].num
									+ '" style="width:40px;text-align:center;"></span>';

						}

						_html += '<span><img src="images/img_jia.gif" id="btn-type" onclick="cartpackp()" /></span>';

						_html += '</div>';

						_html += '<div class="clear"></div>';

						_html += '<div class="blank10" ></div>';

						_html += '<div class="btn btn-danger blcok">选择锅底<span class="selspan" onclick="packguodi(packid);">选择</span></div>';// lt:20150919

						_html += '<div class="blank10" ></div>';

						_html += '<div id="guodi">无';

						/*if (typeof (cartpack) == 'undefined'
								|| cartpack == null
								|| typeof (cartpack[n]) == 'undefined'
								|| cartpack[n] == null
								|| typeof (cartpack[n].guodi) == 'undefined'
								|| cartpack[n].guodi == null) {

							_html += '无';

						} else {

							guodijson = cartpack[n].guodi;
							// alert(JSON.stringify(data));
							$
									.each(
											data,
											function(index, obj) {

												obj.unitPrice = (Math
														.round(obj.unitPrice * 100) / 100);

												if (obj.innerId == '1') {

													// alert(JSON.stringify(cartpack[n].guodi));
													// alert(JSON.stringify(cartpack[n].guodi[obj.dishId]));
													// alert(ntmp.num);
													if (typeof (cartpack[n].guodi[obj.dishId]) != 'undefined') {
														ntmp = cartpack[n].guodi[obj.dishId];
														_html += '<dl id="guodi'
																+ obj.dishId
																+ '" onclick="guodidel(this);" data-name="'
																+ obj.dishName
																+ '" data-innernumber="'
																+ obj.innerNumber
																+ '" data-id="'
																+ obj.dishId
																+ '" data-num="'
																+ ntmp.num
																+ '" ><dd>'
																+ obj.dishName
																+ '</dd><dt><span class="f_c6000a">x'
																+ ntmp.num
																+ '</span></dt></dl>';// lt:20150919
													}

												}

											});

							// _html +='<span class="f12px fB f_c6000a"><input
							// name="packnum" id="packnum" value="1"
							// style="width:40px;text-align:center;"></span>';

						}*/

						_html += '</div>';

						_html += '<div class="blank10" ></div>';

						_html += '<div class="btn btn-danger blcok">选择小料<span class="selspan" onclick="packxiaoliao(packid);">选择</span></div>';// lt:20150919

						_html += '<div class="blank10" ></div>';

						_html += '<div id="xiaoliao">无';
/*
						if (typeof (cartpack) == 'undefined'
								|| cartpack == null
								|| typeof (cartpack[n]) == 'undefined'
								|| cartpack[n] == null
								|| typeof (cartpack[n].xiaoliao) == 'undefined'
								|| cartpack[n].xiaoliao == null) {

							_html += '无';

						} else {

							xiaoliaojson = cartpack[n].xiaoliao;

							// alert(JSON.stringify(cartpack[n].xiaoliao));
							$
									.each(
											data,
											function(index, obj) {

												obj.unitPrice = (Math
														.round(obj.unitPrice * 100) / 100);

												if (obj.innerId == '2') {

													if (typeof (cartpack[n].xiaoliao[obj.dishId]) != 'undefined') {
														ntmp = cartpack[n].xiaoliao[obj.dishId];
														_html += '<dl id="xiaoliao'
																+ obj.dishId
																+ '" onclick="xiaoliaodel(this);" data-name="'
																+ obj.dishName
																+ '" data-innernumber="'
																+ obj.innerNumber
																+ '" data-id="'
																+ obj.dishId
																+ '" data-num="'
																+ ntmp.num
																+ '" ><dd>'
																+ obj.dishName
																+ '</dd><dt><span class="f_c6000a">x'
																+ ntmp.num
																+ '</span></dt></dl>';// lt:20150919
													}

												}

											});

						}*/

						_html += '</div>';

						_html += '<div class="blank10" ></div>';

						_html += '<div class="m_nav">包含菜品</div>';

						_html += '<div class="mu_list">';

						//var _htmlguodi = '';

						//var _htmlxiaoliao = '';

						$
								.each(
										data,
										function(index, obj) {
											obj.unitPrice = (Math
													.round(obj.unitPrice * 100) / 100);

											if (obj.innerId != '1' && obj.innerId != '2' ) {
											/*	//小料最大限制
												Packinfo.guodiMaxCount = obj.innerNumber;
												//选择的数量
												var dnum = Packinfo.guodijson[obj.dishId]==null?
															0:Packinfo.guodijson[obj.dishId].num;
												_htmlguodi += '<dl  data-name="'
														+ obj.dishName
														+ '" data-innernumber="'
														+ obj.innerNumber
														+ '" data-id="'
														+ obj.dishId
														+ '" data-num="'
														+ obj.dishNumber
														+ '" ><dd><img src="'
														+ obj.mediumImageAddr
														+ '" width=100 height="100">'
														+ obj.dishName
														+ '</dd><dt><img src="images/img_jian.gif" onclick="tguodiDel(\''
														+ obj.dishId
														+ '\',this)" /> <span class="num">'
														//+ obj.dishNumber
														//+ 0
														+ dnum
														+ '</span> <img src="images/img_jia.gif" onclick="tguodiAdd(\''
														+ obj.dishId
														+ '\',this)" /> </dt></dl>';

											} else if (obj.innerId == '2') {
												//锅底最大限制
												Packinfo.xiaoliaoMaxCount = obj.innerNumber;
												//选择的数量
												var dnum = Packinfo.xiaoliaojson[obj.dishId]==null?0
														:Packinfo.xiaoliaojson[obj.dishId].num;
												_htmlxiaoliao += '<dl  data-name="'
														+ obj.dishName
														+ '" data-innernumber="'
														+ obj.innerNumber
														+ '" data-id="'
														+ obj.dishId
														+ '" data-num="'
														+ obj.dishNumber
														+ '"><dd><img src="'
														+ obj.mediumImageAddr
														+ '" width=100 height="100">'
														+ obj.dishName
														+ '</dd><dt><img src="images/img_jian.gif" onclick="xiaoliaDel(\''
														+ obj.dishId
														+ '\',this)" /> <span class="num">'
														//+ obj.dishNumber
														+ dnum
														+ '</span> <img src="images/img_jia.gif" onclick="xiaoliaoAdd(\''
														+ obj.dishId
														+ '\',this)" /> </dt></dl>';

											} else {*/

												_html += '<dl><dd>'
														+ obj.dishName
														+ '</dd><dt><span class="f_c6000a">x'
														+ obj.dishNumber
														+ '</span></dt></dl>';

											}

										});

						//guodilist = _htmlguodi;

						//xiaoliaolist = _htmlxiaoliao;

						_html += '<div class="clear"></div><div class="btn btn-danger blcok" data-id="'
								+ packid
								+ '" data-name="'
								+ packname
								+ '" data-price="'
								+ packprice
								+ '" onclick="addcartpack(this);">确定</div>';

						_html += '</div>';

						$.cookie("packid", n, {
							expires : 30
						});

						$.cookie("packname", l, {
							expires : 30
						});

						$.cookie("packprice", k, {
							expires : 30
						});

						packid = n;

						packname = l;

						packprice = k;

						$(m).html(_html);

						if (_html == '') {

							$(m).html('<div id="noticecon">暂无</div>');

						}

					}

				}

			});

}

function packguodi(n) {

	$
			.fancybox(

					'<center ></center><div> <ul id="guodilist"></ul><div class="clear"></div><div class="btn btn-danger blcok" style=" margin:50px auto 20px auto; width:100px;" onclick="Packinfo.commitGuodi()">确定</div><div class="clear"></div></div>',

					{

						'onComplete' : function() {
							$('#guodilist').html(Packinfo.buildGoudiList(Packinfo.data));
							Packinfo.isCloseGoudi=true;
						},
						'onClosed' : function() {
							//通过关闭按钮关闭的
							if(Packinfo.isCloseGoudi){
								Packinfo.cleanGuodi();
							}
						}

					}

			);

}

function packxiaoliao(n) {

	$
			.fancybox(

					'<center ></center><div> <ul id="xiaoliaolist"></ul></div><div class="clear"></div><div class="btn btn-danger blcok" style=" margin:50px auto 20px auto; width:100px;" onclick="Packinfo.commitXiaoliao()">确定</div><div class="clear"></div></div>',

					{

						'onComplete' : function() {
							$('#xiaoliaolist').html(Packinfo.buildXiaoliaoList(Packinfo.data));
							Packinfo.isCloseXiaoliao = true;

						},
						'onClosed' : function() {
							//通过关闭按钮关闭的
							if(Packinfo.isCloseXiaoliao){
								Packinfo.cleanXiaoliao();
							}
						}

					}

			);

}

function guodizb() {

	$.fancybox.close();

	guodijson = {};

	$("#guodi").html("无");

}

function xiaoliaozb() {

	$.fancybox.close();

	xiaoliaojson = {};

	$("#xiaoliao").html("无");

}

function guodidel(n) {

	var id = $(n).attr('data-id');

	var innernumber = $(n).attr('data-innernumber');

	var name = $(n).attr('data-name');

	var num = $(n).attr('data-num');

	// guodijson[id] = 'null';

	delete guodijson[id];

	$('#guodi' + id + '').remove();

	if ($('#guodi').html() == '') {

		$('#guodi').html('无');

	}

}

function xiaoliaodel(m) {

	var n = $(m).parent().parent();

	var id = $(n).attr('data-id');

	var innernumber = $(n).attr('data-innernumber');

	var name = $(n).attr('data-name');

	var num = $(n).attr('data-num');

	// xiaoliaojson[id] = 'null';

	delete xiaoliaojson[id];

	$('#xiaoliao' + id + '').remove();

	if ($('#xiaoliao').html() == '') {

		$('#xiaoliao').html('无');

	}

}
//delete
function guodisel(n) {}

function xiaoliaonum() {

	var num = 0;

	// alert(JSON.stringify(xiaoliaojson));

	$.each(xiaoliaojson, function(index, obj) {

		if (typeof (obj) == 'undefined' || obj == null) {

		} else {

			num += obj.num;

		}

	});

	return num;

}

function xiaoliaosel(n) {

	var id = $(n).attr('data-id');

	var innernumber = $(n).attr('data-innernumber');

	var name = $(n).attr('data-name');

	var num = $(n).attr('data-num');

	var _html = '';

	var length = 0;

	// alert(JSON.stringify(xiaoliaojson));

	if (typeof (xiaoliaojson[id]) == 'undefined' || xiaoliaojson[id] == 'null') {

		var innerNumber = $(n).attr('data-innernumber');

		// length = JSONLength(xiaoliaojson);

		length = xiaoliaonum();

		// alert(length);

		if (length >= innerNumber) {

			alert('小料只能选择' + innerNumber + '个！');

			return false;

		}

		xiaoliaojson[id] = {

			'id' : id,

			'num' : 1

		};

		_html += '<dl id="xiaoliao' + id
				+ '" onclick="guodidel(this);" data-name="' + name
				+ '" data-innernumber="' + innernumber + '" data-id="' + id
				+ '" data-num="' + num + '" ><dd>' + name
				+ '</dd><dt><span class="f_c6000a">x' + num
				+ '</span></dt></dl>';// lt:20150919

		if ($('#xiaoliao').html() == '无') {

			$('#xiaoliao').html('');

		}

		$('#xiaoliao').append(_html);

		$(n).addClass('on');

	} else {

		// xiaoliaojson[id] = 'null';

		// xiaoliaojson.remove(id);

		delete xiaoliaojson[id];

		$('#xiaoliao' + id + '').remove();

		$(n).removeClass('on');

		if ($('#xiaoliao').html() == '') {

			$('#xiaoliao').html('无');

		}

	}

	// alert(JSON.stringify(xiaoliaojson));

}

function xiaoliaod1(id) {

	var num = xiaoliaojson[id]['num'];

	if (num > 2) {

		num -= 1;

	} else {

		num = 1;

	}

	xiaoliaojson[id]['num'] = num;

	$('#xiaoliao' + id + ' .num').html(num);

}

function xiaoliaop1(id) {

	var innerNumber = $('#xiaoliao' + id).attr('data-innernumber');

	var length = xiaoliaonum();

	// alert(length);

	if (length >= innerNumber) {

		alert('小料只能选择' + innerNumber + '个！');

		return false;

	}

	var num = xiaoliaojson[id]['num'];

	num = parseInt(num);

	num += 1;

	if (num < 2) {

		num = 2;

	}

	xiaoliaojson[id]['num'] = num;

	$('#xiaoliao' + id + ' .num').html(num);

}

$(document).ready(function(e) {

	getcategories(cityid, '#catul', cityname);

	packid = $.cookie("packid");

	packname = $.cookie("packname");

	packprice = $.cookie("packprice");

	if (typeof (packid) == 'undefined' || packid == 'null') {

		window.history.go(-1);

	}

	userid = $.cookie('userid');//

	if (typeof (userid) == 'undefined' || userid == 'null') { // 如果没有登录报错

		window.history.go(-1);

	}

	getpackinfo(packid, '#packinfo', packname, packprice);

});

var Packinfo={
		message:{
			count:"请选择",
			guodiCount:"份锅底",
			xiaoliaoCount:"份小料",
			guodiSel:"请选择锅底",
			xiaoliaoSel:"请选择小料"
		},
		guodiMaxCount:0,			//最大锅底数量
		xiaoliaoMaxCount:0,			//最大小料数量
		guodiSelCount:0,			//选择的锅底数量
		xiaoliaoSelCount:0,			//选择的小料小数量
		guodijson:{},				//选择的锅底
		xiaoliaojson:{},			//选择小料
		isCloseGoudi:false,			//是否点击关闭按钮，判断是否清空
		isCloseXiaoliao:false,		//是否点击关闭按钮，判断是否清空
		checkGoudiCount:function(type){
			var pn = $("#packnum").val()
			pn = parseInt(pn)*Packinfo.guodiMaxCount;
			if(type == '==' && Packinfo.guodiSelCount!=pn){
				alert(Packinfo.message.count+pn+Packinfo.message.guodiCount);
				return true;
			}else if(type == '<' && Packinfo.guodiSelCount>=pn){
				alert(Packinfo.message.count+pn+Packinfo.message.guodiCount);
				return true;
			}
			return false;
		},		//选择小料
		checkXiaoliaoCount:function(type){
			var pn = $("#packnum").val()
			pn = parseInt(pn)*Packinfo.xiaoliaoMaxCount;
			if(type == '==' && Packinfo.xiaoliaoSelCount!=pn){
				alert(Packinfo.message.count+pn+Packinfo.message.xiaoliaoCount);
				return true;
			}else if(type == '<' && Packinfo.xiaoliaoSelCount>=pn){
				alert(Packinfo.message.count+pn+Packinfo.message.xiaoliaoCount);
				return true;
			}
			return false;
		},
		//选择锅底
		commitGuodi:function(){
			//请选择N份锅底
			if(Packinfo.checkGoudiCount("==")){
				return;
			}
			Packinfo.isCloseGoudi = false;
			$.fancybox.close();
			$('#guodi').html('');
			for ( var i in Packinfo.guodijson) {
				Packinfo.buildGuodi(Packinfo.guodijson[i]);
			}
		},
		//构建选择的锅底
		buildGuodi:function(g){
			var _html = '';
			_html += '<dl id="guodi' + g.id
			+ '" onclick="guodidel(this);" data-name="' + g.name
			+ '" data-innernumber="' + g.innernumber + '" data-id="' + g.id
			+ '" data-num="' + g.num + '" ><dd>' + g.name
			+ '</dd><dt><span class="f_c6000a">x' + g.num
			+ '</span></dt></dl>';// lt:20150919
			if ($('#guodi').html() == '无') {
				$('#guodi').html('');
			}
			$('#guodi').append(_html);
			$('#guodilist dl').removeClass('on');
		},
		cleanGuodi:function(){
			Packinfo.guodijson={};
			Packinfo.guodiSelCount = 0;
			$('#guodi').html('无');
		},
		//选择小料
		commitXiaoliao:function(){
			//请选择N份锅底
			if(Packinfo.checkXiaoliaoCount("==")){
				return;
			}
			Packinfo.isCloseXiaoliao = false;
			$.fancybox.close();
			$('#xiaoliao').html('');
			for ( var i in Packinfo.xiaoliaojson) {
				Packinfo.buildXiaoliao(Packinfo.xiaoliaojson[i]);
			}
		},
		//构建选择的小料
		buildXiaoliao : function(g) {
			var _html = '';
			_html += '<dl id="xiaoliao' + g.id
					+ '" onclick="guodidel(this);" data-name="' + g.name
					+ '" data-innernumber="' + g.innernumber + '" data-id="' + g.id
					+ '" data-num="' + g.num + '" ><dd>' + g.name
					+ '</dd><dt><span class="f_c6000a">x' + g.num
					+ '</span></dt></dl>';// lt:20150919
			if ($('#xiaoliao').html() == '无') {
				$('#xiaoliao').html('');
			}
			$('#xiaoliao').append(_html);
		},
		cleanXiaoliao:function(){
			Packinfo.xiaoliaojson={};
			Packinfo.xiaoliaoSelCount = 0;
			$('#xiaoliao').html('无');
		},
		buildGoudiList:function(data){
			var _htmlguodi = "";
			$.each(data,
							function(index, obj) {
								obj.unitPrice = (Math
										.round(obj.unitPrice * 100) / 100);

								if (obj.innerId == '1') {
									//小料最大限制
									Packinfo.guodiMaxCount = obj.innerNumber;
									//选择的数量
									var dnum = Packinfo.guodijson[obj.dishId]==null?
												0:Packinfo.guodijson[obj.dishId].num;
									_htmlguodi += '<dl  data-name="'
											+ obj.dishName
											+ '" data-innernumber="'
											+ obj.innerNumber
											+ '" data-id="'
											+ obj.dishId
											+ '" data-num="'
											+ obj.dishNumber
											+ '" ><dd><img src="'
											+ obj.mediumImageAddr
											+ '" width=100 height="100">'
											+ obj.dishName
											+ '</dd><dt><img src="images/img_jian.gif" onclick="tguodiDel(\''
											+ obj.dishId
											+ '\',this)" /> <span class="num">'
											//+ obj.dishNumber
											//+ 0
											+ dnum
											+ '</span> <img src="images/img_jia.gif" onclick="tguodiAdd(\''
											+ obj.dishId
											+ '\',this)" /> </dt></dl>';

								} 

							});
			return _htmlguodi;
		},
		buildXiaoliaoList : function(data) {
			var _htmlxiaoliao = "";
			$
				.each(
						data,
						function(index, obj) {
							obj.unitPrice = (Math.round(obj.unitPrice * 100) / 100);
							 if (obj.innerId == '2') {
								// 锅底最大限制
								Packinfo.xiaoliaoMaxCount = obj.innerNumber;
								// 选择的数量
								var dnum = Packinfo.xiaoliaojson[obj.dishId] == null ? 0
										: Packinfo.xiaoliaojson[obj.dishId].num;
								_htmlxiaoliao += '<dl  data-name="'
										+ obj.dishName
										+ '" data-innernumber="'
										+ obj.innerNumber
										+ '" data-id="'
										+ obj.dishId
										+ '" data-num="'
										+ obj.dishNumber
										+ '"><dd><img src="'
										+ obj.mediumImageAddr
										+ '" width=100 height="100">'
										+ obj.dishName
										+ '</dd><dt><img src="images/img_jian.gif" onclick="xiaoliaDel(\''
										+ obj.dishId
										+ '\',this)" /> <span class="num">'
										// + obj.dishNumber
										+ dnum
										+ '</span> <img src="images/img_jia.gif" onclick="xiaoliaoAdd(\''
										+ obj.dishId
										+ '\',this)" /> </dt></dl>';

							} 

						});
			return _htmlxiaoliao;
	}
}
// 添加锅底
function tguodiAdd(id,m){
	// 请选择N份锅底
	if(Packinfo.checkGoudiCount("<")){
		return;
	}
	var n = $(m).parent().parent();
	var id = $(n).attr('data-id');
	var innernumber = $(n).attr('data-innernumber');
	var name = $(n).attr('data-name');
	var num = $(n).attr('data-num');
	if (typeof (Packinfo.guodijson[id]) == 'undefined' || Packinfo.guodijson[id] == 'null') {
		Packinfo.guodijson[id] = {
			'id' : id,
			'num' : 1,
			'innernumber' : innernumber,
			'name' : name

		};

	} else {
		Packinfo.guodijson[id].num++;
	}
	Packinfo.guodiSelCount++;
	var numd = $(m).prev()
	var num = parseInt(numd.text());
	numd.text(Packinfo.guodijson[id].num);
}
//删除锅底
function tguodiDel(id,m){
	var n = $(m).parent().parent();
	var id = $(n).attr('data-id');
	var innernumber = $(n).attr('data-innernumber');
	var name = $(n).attr('data-name');
	var num = $(n).attr('data-num');
	Packinfo.guodijson[id].num--;
	Packinfo.guodiSelCount--;
	var numd = $(m).next()
	var num = parseInt(numd.text());
	numd.text(Packinfo.guodijson[id].num);
	
	if(Packinfo.guodijson[id].num==0){
		delete Packinfo.guodijson[id];
	}
}
//添加小料
function xiaoliaoAdd(id,m){
	//请选择N份锅底
	if(Packinfo.checkXiaoliaoCount("<")){
		return;
	}
	var n = $(m).parent().parent();
	var id = $(n).attr('data-id');
	var innernumber = $(n).attr('data-innernumber');
	var name = $(n).attr('data-name');
	var num = $(n).attr('data-num');
	if (typeof (Packinfo.xiaoliaojson[id]) == 'undefined' || Packinfo.xiaoliaojson[id] == 'null') {
		Packinfo.xiaoliaojson[id] = {
			'id' : id,
			'num' : 1,
			'innernumber' : innernumber,
			'name' : name,
			'num' : 1

		};

	} else {
		Packinfo.xiaoliaojson[id].num++;
	}
	Packinfo.xiaoliaoSelCount++;
	var numd = $(m).prev()
	var num = parseInt(numd.text());
	numd.text(Packinfo.xiaoliaojson[id].num);
}
//删除小料
function xiaoliaDel(id,m){
	var n = $(m).parent().parent();
	var id = $(n).attr('data-id');
	var innernumber = $(n).attr('data-innernumber');
	var name = $(n).attr('data-name');
	var num = $(n).attr('data-num');
	Packinfo.xiaoliaojson[id].num--;
	Packinfo.xiaoliaoSelCount--;
	var numd = $(m).next()
	var num = parseInt(numd.text());
	numd.text(Packinfo.xiaoliaojson[id].num);
	if(Packinfo.xiaoliaojson[id].num==0){
		delete Packinfo.xiaoliaojson[id];
	}
}