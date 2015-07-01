<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>测试Create Order</title>

<script type="text/javascript" src="<%=path%>/static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" >
$(function() {
	$("input[name='save']").click(function() {
		var content = {
				storeId: $.trim($("input[name='storeId']").val()),
				contactName: $.trim($("input[name='contactName']").val()),
				contactPhone: $.trim($("input[name='contactPhone']").val()),
				participantNumber: $.trim($("input[name='participantNumber']").val()),
				dinningTime: $.trim($("input[name='dinningTime']").val()),
				recieptDept: $.trim($("input[name='recieptDept']").val()),
				custMemo: $.trim($("input[name='custMemo']").val()),
				payChannel: $.trim($("input[name='payChannel']").val()),
				potNumber: $.trim($("input[name='potNumber']").val()),
				orderType: $.trim($("input[name='orderType']").val()),
				address: {
					provinceId: $.trim($("input[name='provinceId']").val()),
					cityId: $.trim($("input[name='cityId']").val()),
					detailAddress: $.trim($("input[name='detailAddress']").val())
				},
				expenses: {
					waiterFee: $.trim($("input[name='waiterFee']").val()),
					deliveryFee: $.trim($("input[name='deliveryFee']").val()),
					dishPrice: $.trim($("input[name='dishPrice']").val()),
					totalPrice: $.trim($("input[name='totalPrice']").val())
				},
				packs: [
				        { packId: $.trim($("input[name='packId']").val()),
						   packCount : $.trim($("input[name='packCount']").val()),
						   dishes: [
						            {dishId: $.trim($("input[name='packDishId1']").val())},
						            {dishId: $.trim($("input[name='packDishId2']").val())},
						            {dishId: "20014_0201"},
						            {dishId: "32014_0201"},
						            {dishId: "41006_0201"},
						            {dishId: "20026_0201"}
						           ]
						 }
				       ],
				dishes: [
				         {dishId: $.trim($("input[name='dishId1']").val()), dishNumber: $.trim($("input[name='dishNumber1']").val())},
				         {dishId: $.trim($("input[name='dishId2']").val()), dishNumber: $.trim($("input[name='dishNumber2']").val())},
				         {dishId: $.trim($("input[name='dishId3']").val()), dishNumber: $.trim($("input[name='dishNumber3']").val())}
						]
			};
		$.ajax({
			url: "<%=path%>/rest/createorder",//要访问的后台地址
			data: JSON.stringify(content),//要发送的数据
			type: "post", //使用post方法访问后台
			dataType: "json", //返回json格式的数据
			async: true,
			contentType: "application/json;charset=utf-8", 
			success: function(msg){//msg为返回的数据，在这里做数据绑定
				alert("login response:"+JSON.stringify(msg));
			}
		});
	});
});
</script>

</head>
<body id="main">
	<div class="container clearfix">
		<div class="main-wrap">
	        <div class="result-wrap">
            	<div class="result-title">
               		<h1>Hi捞送下单（测试）</h1>
            	</div>
	            <div class="result-content">
					<div id="top">
						<table class="insert-tab" width="100%">
							<tr>
								<th width="120">用户ID:</th>
								<td>${sessionScope.loginId}</td>
							</tr>
							<tr>
								<th width="120">订单类型:</th>
								<td><input type="text" name="orderType" style="width: 440px" value="0">0-外卖;2-外带(自取)， 选0必须输入外送地址</td>
							</tr>
							<tr>
								<th width="120">门店Id:</th>
								<td><input type="text" name="storeId" style="width: 440px" value="020102">0外卖，自动选取距离近的门店，2自取，用户自行输入</td>
							</tr>
							<tr>
								<th width="120">联系人姓名:</th>
								<td><input type="text" name="contactName" style="width: 440px" value="Jimmy"></td>
							</tr>
							<tr>
								<th width="120">联系人电话:</th>
								<td><input type="text" name="contactPhone" style="width: 440px" value="13585947701"></td>
							</tr>
							<tr>
								<th width="120">用餐人数:</th>
								<td><input type="text" name="participantNumber" style="width: 440px" value="4"></td>
							</tr>
							<tr>
								<th width="120">用餐时间:</th>
								<td><input type="text" name="dinningTime" style="width: 440px" value="2015-07-02 20:30:00"></td>
							</tr>
							<tr>
								<th width="120">外送地址:</th>
								<td>
									省Id：<input type="text" name="provinceId" style="width: 50px" value="110000">
									市Id：<input type="text" name="cityId" style="width: 50px" value="110105">
									详细地址:<input type="text" name="detailAddress" style="width: 180px" value="某某路100号">110000-北京市，110105-朝阳区
								</td>
							</tr>
							<tr>
								<th width="120">金额:</th>
								<td>
									服务费：<input type="text" name="waiterFee" style="width: 50px" value="10">
									外送费：<input type="text" name="deliveryFee" style="width: 50px" value="20">
									菜品价：<input type="text" name="dishPrice" style="width: 50px" value="88">
									总价:<input type="text" name="totalPrice" style="width: 60px" value="118">总价=菜品价+外送费+服务费
								</td>							</tr>
							<tr>
								<th width="120">发票抬头:</th>
								<td><input type="text" name="recieptDept" style="width: 440px" value="某著名公司"></td>
							</tr>
							<tr>
								<th width="120">备注:</th>
								<td><input type="text" name="custMemo" style="width: 440px" value="请美女送餐"></td>
							</tr>
							<tr>
								<th width="120">炉具套数:</th>
								<td><input type="text" name="potNumber" style="width: 440px" value="1"></td>
							</tr>
							<tr>
								<th width="120">支付方式:</th>
								<td><input type="text" name="payChannel" style="width: 440px" value="0">0-网付，1-到付</td>
							</tr>
							<tr>
								<th width="120">选择套餐</th>
								<td><hr></td>
							</tr>
							<tr>
								<th width="120"></th>
								<td>套餐ID<input type="text" name="packId" style="width: 100px" value="82192_0201">套餐数量<input type="text" name="packCount" style="width: 100px" value="1">微特享套餐A</td>
							</tr>
							<tr>
								<th width="120">选择锅底</th>
								<td><input type="text" name="packDishId1" style="width: 440px" value="10001_0201">N选1： 10001_0201,10002_0201 等 (套餐详情中"innerId":"1"的都是锅底)</td>
							</tr>
							<tr>
								<th width="120">选择小料</th>
								<td><input type="text" name="packDishId2" style="width: 440px" value="61008_0201">N选1： 61008_0201,61012_0201 等 (套餐详情中"innerId":"2"的都是小料)</td>
							</tr>
							<tr>
								<th width="120">套餐中其他菜品</th>
								<td>32014_0201,20014_0201,41006_0201,20026_0201等不能改，都是1份</td>
							</tr>
							<tr>
								<th width="120">选择套餐外的菜品</th>
								<td><hr></td>
							</tr>
							<tr>
								<th width="120">菜品1</th>
								<td>菜品ID<input type="text" name="dishId1" style="width: 100px" value="20014_0201">菜品数量<input type="text" name="dishNumber1" style="width: 100px" value="1"></td>
							</tr>
							<tr>
								<th width="120">菜品2</th>
								<td>菜品ID<input type="text" name="dishId2" style="width: 100px" value="30012_0201">菜品数量<input type="text" name="dishNumber2" style="width: 100px" value="2"></td>
							</tr>
							<tr>
								<th width="120">菜品3</th>
								<td>菜品ID<input type="text" name="dishId3" style="width: 100px" value="41066_0201">菜品数量<input type="text" name="dishNumber3" style="width: 100px" value="3"></td>
							</tr>
							<tr>
								<th width="120"></th>
								<td><input type="button" name="save" value="提  交" ></td>
							</tr>
						</table>
					</div>
				</div>            
	        </div>
        </div>
    </div>
</body>
</html>
