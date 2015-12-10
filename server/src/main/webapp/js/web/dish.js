
nowpage = 'dish';

$(document).ready(function(e) {

	catid = $.cookie("catid");

	if (typeof (catid) == 'undefined' || catid == 'null') {

		window.history.go(-1);

	}

	getcategories(cityid, '#catul', cityname);

});

PageClick = function(pageclickednumber) {
	catid = $.cookie("catid");
	catname = $.cookie("catname");

	U.getdishes(catid, '#dishul', pageclickednumber, storeid, catname
			,true);

}
/**
 * 加载菜品 
 */

//end....
U.getdishes = function(n,m,l,p,q,ispage){
	var _html = '';
	var _html1 = '';
	_html1 = '<div class="catname dish dish'+n+'">'+q+'</div>';
	
	$.ajax({
      url: apiurl+'getdishes?storeId='+p+'&catId='+n+'&pageIndex='+l,
//       url: apiurl+'getdishes?storeId=0201&catId='+n+'&pageIndex='+l,
//       url:rootUrl+"json/getdishes.json",
      type: 'GET',
		async: false,
      dataType: 'JSON',//here
      success: function (data) {
			//分页点击
			if(ispage){
			  	  $(".dish"+n).remove();
			}
				//alert(JSON.stringify(data));
			if(typeof(data.error)=='undefined'){
				$.each(data,function(index,obj){
					obj.unitPrice = (Math.round(obj.unitPrice*100)/100);
					//小料不显示瓶装
					if(n=='007'){
						//处理小料图片
						var xlimg = obj.dishId.split('_')[0];
						var xlids = '61001,61008,61012,61015,61022,61023,61024,61025,61017,61011,61003,61028,';
						if(xlids.indexOf(xlimg)<0){
							xlimg = '61017';
						}
						_html += '<li onclick="" class="dish dish'+n+'" data-val="'+obj.dishId+'" type="'+n+'"><img src="'+rootUrl+'images/dish/007/'+xlimg+'.jpg" width="100" height="100"/><div class="cp_titel">';
					}else{

						_html += '<li onclick="" class="dish dish'+n+'" data-val="'+obj.dishId+'" type="'+n+'"><img src="'+obj.bigImageAddr+'" width="100" height="100"/><div class="cp_titel">';
					}
					_html += '<div class="cp_title1"><span class="f_c6000a f14px fB oh14">'+obj.storeDishName+'</span>  <span class="f_c6000a f14px oh14"><div class="left  f14px ">一份价格：'+obj.unitPrice+'元</div>';
					_html += '<div class="right num"><img src="images/img_jian.gif" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'"  type="'+n+'"data-price="'+obj.unitPrice+'" onclick="cartdishd(this);">   <img src="images/img_jia.gif" data-id="'+obj.dishId+'" data-name="'+obj.storeDishName+'" type="'+n+'" data-price="'+obj.unitPrice+'" onclick="cartdishp(this);"> </div></span><div class="clear"></div>';//lt:20150919 修改菜品列表界面

					//U.debug("....3....."+_html);
		        	if(typeof(obj.halfDishId) == 'undefined'){
					} else {
						obj.halfPrice = (Math.round(obj.halfPrice*100)/100);
						_html += '<span class="f_c6000a f14px oh14"><div class="left  f14px ">半份价格：'+obj.halfPrice+'元</div>';
						_html += '<div class="right num"><img src="images/img_jian.gif" data-id="'+obj.halfDishId+'" data-name="'+obj.storeDishName+'半份"  type="'+n+'"data-price="'+obj.halfPrice+'" onclick="cartdishd(this);">   <img src="images/img_jia.gif" data-id="'+obj.halfDishId+'" data-name="'+obj.storeDishName+'半份" type="'+n+'" data-price="'+obj.halfPrice+'" onclick="cartdishp(this);"> </div></span><div class="clear"></div>';//lt:20150919 修改菜品列表界面
					}
					_html += '</div><div class=" p_t_20"><div class="i_box">';
					_html += '</div><div class="clear"></div></div>';
					_html +='</div></li>';
					//U.debug("....3....."+_html);
				});
				catid = n;
				catname = q;
				$(m).append(_html1+_html);
				$("#catul  li[data-val='"+n+"']").addClass('li_hover');
				if(_html == ''){
					$(m).append(_html1+'<div class="alert dish dish'+n+' alert-danger" role="alert" id="noticecon">暂无</div>');
				}
				//分页点击
				if(ispage){
					getdishes(n,m,l,p,q);
				}
				if(n=='006004'){
					catid = $.cookie("catid");
					catname = $.cookie("catname");
					//getcategories(cityid, '#catul', cityname);
					getdishes(catid, '#dishul', '1', storeid, catname);
				}
			}
      }
  });
}