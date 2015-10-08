
nowpage = 'diyguodi';

var diyguodijson = {};

userid = $.cookie('userid');//

function editdiyguodi(n) {

	var _html;

	var id = $(n).attr('data-id');

	var name = $(n).attr('data-name');

	var id1 = $(n).attr('data-id1');

	var id2 = $(n).attr('data-id2');

	diyguodijson = {};

	diyguodijson['id'] = id;

	diyguodijson['name'] = name;

	diyguodijson['id1'] = null;

	diyguodijson['id2'] = null;

	storeid = $.cookie('storeid');

	$
			.fancybox(

					'<center ></center><div> <ul id="guodilist"></ul><div class="clear"></div><div id="diyguodipager"></div><div class="clear"></div><div class="tc g_img"><div id="guodititle1" class="guotitle1"></div><div id="guodititle2" class="guotitle2"></div><div class="guodibox1"><img class="guo1" id="guo1" onclick="$(this).hide();diyguodijson[\'id1\']=null;$(\'#guodititle1\').html(\'\');" src="images/diy_guo1.png"></div><div class="guodibox2"><img id="guo2" class="guo2" onclick="$(this).hide();diyguodijson[\'id2\']=null;$(\'#guodititle2\').html(\'\');" src="images/diy_guo2.png"></div><img src="images/diy_guo.jpg"></div><center >'
							+ name
							+ '（修改）</center><center ><div class="btn btn-success" onclick="diyguodiupdate(this,diyguodijson);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选好了&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></center><div class="clear"></div></div>',

					{

						'onComplete' : function() {

							// getcities('#cities');

							// alert(_html);

							getNowDish('001', '1', storeid);

						}

					}

			);

}

function diyguodiupdate(n, j) {

	var content = {

		guodiId : $.trim(j.id),

		guodiName : $.trim(j.name),

		dishId : $.trim(j.id1),

		dishId2 : $.trim(j.id2)

	};

	// alert("login response:"+JSON.stringify(content));

	$.ajax({

		url : apiurl + 'updategd',// 要访问的后台地址

		data : JSON.stringify(content),// 要发送的数据

		type : "post", // 使用post方法访问后台

		dataType : "json", // 返回json格式的数据

		async : true,

		contentType : "application/json;charset=utf-8",

		success : function(msg) {// msg为返回的数据，在这里做数据绑定

			if (typeof (msg) == 'undefined') {

			} else {

				// alert("login response:"+JSON.stringify(msg));

				alert(msg.respMsg);

				getdiyguodi(userid, '#diyguodilist', '1');

				$.fancybox.close();

			}

		}

	});

}

function diyguodisel(n) {

	var id = $(n).attr('data-id');

	var title = $(n).attr('data-name');

	// diyguodijson = j;

	if (diyguodijson['id1'] == id || diyguodijson['id2'] == id) {

		return false;

	}

	// 处理图片效果
	var cu = getImgTypes(title);
	if ((diyguodijson['id1'] == null)
			|| (diyguodijson['id1'] != null && diyguodijson['id2'] != null)) {
		diyguodijson['id1'] = id;
		$('#guo1').show();
		$('#guo1').attr("src", "images/4803/" + cu + "_left_img@2x.png");
		$('#guodititle1').html(title);
	} else {
		diyguodijson['id2'] = id;
		$('#guo2').attr("src", "images/4803/" + cu + "_right_img@2x.png");
		$('#guo2').show();
		$('#guodititle2').html(title);
	}

}

function deldiyguodi(n, me) {

	$.ajax({

		url : apiurl + 'deletegd?id=' + n + '',

		type : 'GET',

		dataType : 'JSON',// here

		success : function(data) {

			// alert(JSON.stringify(data));

			if (typeof (data.error) == 'undefined') {

				// alert(JSON.stringify(data));

				var tmp = data.respMsg;

				noticeinfo(tmp);

				// zs:删除行
				$(me).parent().remove();

			} else {
				window.history.go(0);// lt:20150920 刷新界面
			}

		}

	});

}
// zs:锅底图片获取类型
function getImgTypes(name) {
	var ts = {
		"豆花" : "1",
		"骨" : "2",
		"全青椒" : "3",
		"全藿香鱼" : "3",
		"菌" : "3",
		"江团" : "6",
		"酸菜鱼" : "7",
		"蹄花" : "1", // 蹄花
		"无渣" : "9", // 无图
		"番" : "10", // 无图
		"海鲜" : "11", // 无图
		"双椒" : "12", // 无图
		"特色简州跳跳鱼红油火锅" : "20",
		"全清油香辣火锅" : "23",
		"精品全家红锅" : "21",
		"全家红火锅" : "22",
		"麻辣" : "5",
		"红油" : "5",
		"全清油" : "5",
		"全牛油" : "5",
		"牛油" : "5",
		"全家红" : "5",
		"红汤" : "5",
		"正宗川味" : "5",
		"清油" : "5"
	};
	for ( var t in ts) {
		// alert(t +" indexOf"+name.indexOf(t))
		if (name.indexOf(t) >= 0) {
			return ts[t];
		}
	}
	// alert(ts[name] +" indexOf"+tv)
	return "15";
}

function adddiyguodi() {

	var _html;

	diyguodijson = {};

	diyguodijson['name'] = null;

	diyguodijson['id1'] = null;

	diyguodijson['id2'] = null;

	storeid = $.cookie('storeid');

	$
			.fancybox(

					'<center ></center><div> <ul id="guodilist"></ul><div class="clear"></div><div id="diyguodipager"></div><div class="clear"></div><div class="tc g_img"><div id="guodititle1" class="guotitle1"></div><div id="guodititle2" class="guotitle2"></div><div class="guodibox1"><img class="guo1" id="guo1" onclick="$(this).hide();diyguodijson[\'id1\']=null;$(\'#guodititle1\').html(\'\');" src="images/diy_guo1.png"></div><div class="guodibox2"><img id="guo2" class="guo2" onclick="$(this).hide();diyguodijson[\'id2\']=null;$(\'#guodititle2\').html(\'\');" src="images/diy_guo2.png"></div><img src="images/diy_guo.jpg"></div><center ><div class="btn btn-success" onclick="diyguodicreat(this,diyguodijson);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选好了&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></center><div class="clear"></div></div>',

					{

						'onComplete' : function() {

							// getcities('#cities');

							// alert(_html);

							getNowDish('001', '1', storeid);

						}

					}

			);

}

function diyguodicreat(n, j) {

	var content = {

		guodiName : $.trim(j.name),

		dishId : $.trim(j.id1),

		dishId2 : $.trim(j.id2)

	};

	// alert(""+JSON.stringify(content));

	$.ajax({

		url : apiurl + 'getgdname?userId=' + userid,

		type : 'GET',

		dataType : 'JSON',// here

		success : function(data) {

			// alert(JSON.stringify(data));

			if (typeof (data.guodiName) == 'undefined') {

				// alert(JSON.stringify(data));

				alert(data.respMsg);

			} else {

				var name = data.guodiName;

				content['guodiName'] = name;

				$.ajax({

					url : apiurl + 'creategd',// 要访问的后台地址

					data : JSON.stringify(content),// 要发送的数据

					type : "post", // 使用post方法访问后台

					dataType : "json", // 返回json格式的数据

					async : true,

					contentType : "application/json;charset=utf-8",

					success : function(msg) {// msg为返回的数据，在这里做数据绑定

						if (typeof (msg) == 'undefined') {

						} else {

							// alert("login response:"+JSON.stringify(msg));

							alert(msg.respMsg);

							getdiyguodi(userid, '#diyguodilist', '1');

							$.fancybox.close();

						}

					}

				});

			}

		}

	});

}

function getdiyguodi(n, m, l) {

	var num = 0;

	var cartdiyguodi = getcartdiyguodiinfo();

	$
			.ajax({

				url : apiurl + 'getgds?userId=' + n + '&pageIndex=' + l,

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

											num = 0;

											if (typeof (cartdiyguodi[obj.guodiId]) == 'undefined'
													|| cartdiyguodi[obj.guodiId] == null
													|| cartdiyguodi[obj.guodiId] == 'null'
													|| cartdiyguodi[obj.guodiId] == '') {

											} else {

												num = cartdiyguodi[obj.guodiId]['num'];

											}

											obj.unitPrice = (Math
													.round(obj.unitPrice * 100) / 100);

											_html += '<li><span style=" width:150px; height:36px; overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'
													+ obj.guodiName
													+ '</span>    '; // lt:20150921diy锅底显示购物车

											_html += '<span title="'
													+ obj.dishName
													+ '" style=" width:150px; height:36px; overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'
													+ obj.dishName + '</span>';// lt:20150921//lt:20150921diy锅底显示购物车

											_html += '<span style=" width:50px; height:36px; color:#c6000a;">￥'
													+ obj.unitPrice + '</span>';

											_html += '';

											_html += '<a class="btn btn-danger" data-id="'
													+ obj.guodiId
													+ '" data-name="'
													+ obj.guodiName
													+ '" data-id1="'
													+ obj.dishId
													+ '" data-id2="'
													+ obj.dishId2
													+ '" onclick="editdiyguodi(this)">修改</a> ';

											_html += '<a class="btn btn-danger" onclick="deldiyguodi(\''
													+ obj.guodiId
													+ '\',this)">删除</a> ';

											_html += '<a class="num">' + num
													+ '</a> ';

											_html += '<a class="btn btn-danger" data-id="'
													+ obj.guodiId
													+ '" data-name="'
													+ obj.guodiName
													+ '" data-price="'
													+ obj.unitPrice
													+ '"   onclick="cartdiyguodi(this)">选取</a>';

											_html += '';

											_html += '</li>';

										});

						// alert(_html);

						$(m).html(_html);

						if (_html == '') {

							$(m).html('<div id="noticecon">暂无</div>');

						}

						$.ajax({

							url : apiurl + 'cntgds?userId=' + n + '',

							type : 'GET',

							dataType : 'JSON',// here

							success : function(data) {

								// alert(JSON.stringify(data));

								if (typeof (data.error) == 'undefined') {

									var pagetotal = data.totalPagesCount;

									$("#pager").pager({
										pagenumber : l,
										pagecount : pagetotal,
										buttonClickCallback : PageClick
									});

								}

							}

						});

					}

				}

			});

}

function getNowDish(n, l, p) {

	$.ajax({

		url : apiurl + 'getdiydishes?storeId=' + p
				+ '&catId=001&pageSize=8&pageIndex=' + l,

		type : 'GET',

		dataType : 'JSON',// here

		success : function(data) {

			// alert(JSON.stringify(data));

			if (typeof (data.error) == 'undefined') {

				// alert(JSON.stringify(data));

				var _html = '';

				$.each(data, function(index, obj) {

					obj.unitPrice = (Math.round(obj.unitPrice * 100) / 100);

					_html += '<dl onclick="diyguodisel(this);" data-name="'
							+ obj.storeDishName + '" data-id="' + obj.dishId
							+ '" data-price="' + obj.unitPrice
							+ '" ><dd><img src="' + obj.bigImageAddr
							+ '" width=100 height="100">' + obj.storeDishName
							+ '</dd><dt><span class="f_c6000a">'
							+ obj.unitPrice + '元</span></dt></dl>';

				});

				$('#guodilist').html(_html);

				$.ajax({

					url : apiurl + 'cntdishes?storeId=' + p + '&catId=' + catid
							+ '&pageSize=8',

					type : 'GET',

					dataType : 'JSON',// here

					success : function(data) {

						// alert(JSON.stringify(data));

						if (typeof (data.error) == 'undefined') {

							var pagetotal = data.totalPagesCount;

							$("#diyguodipager").pager({
								pagenumber : l,
								pagecount : pagetotal,
								buttonClickCallback : newDishPageClick
							});

						}

					}

				});

				$.fancybox.resize();
				 $.fancybox.center();

			}

		}

	});

}

$(document).ready(function(e) {

	getcategories(cityid, '#catul', cityname);

	if (typeof (userid) == 'undefined' || userid == 'null' || userid == null) { // 如果没有登录报错

		window.history.go(-1);

	}

	// userid = '23675';

	getdiyguodi(userid, '#diyguodilist', '1');

});

PageClick = function(pageclickednumber) {

	getdiyguodi(userid, '#diyguodilist', pageclickednumber);

}

newDishPageClick = function(pageclickednumber) {

	getNowDish('001', pageclickednumber, storeid);

}