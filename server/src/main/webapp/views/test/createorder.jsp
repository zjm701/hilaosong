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
<script type="text/javascript" src="<%=path%>/static/js/jquery-ui.min.js"></script>
<script type="text/javascript" >
$(function() {
	$("input[name='save']").click(function() {
		var content = {
				customerId: ${sessionScope.loginId},
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
				deliveryType: $.trim($("input[name='deliveryType']").val())
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
				location.href = "createresult.jsp";
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
								<th width="120">StoreId:</th>
								<td><input type="text" name="storeId" style="width: 440px" value="020102">已停业的020115</td>
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
								<td><input type="text" name="dinningTime" style="width: 440px" value="2012-06-09 20:30:00"></td>
							</tr>
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
								<th width="120">订单类型:</th>
								<td><input type="text" name="orderType" style="width: 440px" value="0">0-外卖;1-订桌;2-外带3-排号</td>
							</tr>
							<tr>
								<th width="120">外送种类:</th>
								<td><input type="text" name="deliveryType" style="width: 440px" value="0">0-外送 1-自助 2-豪华 3-上门自取</td>
							</tr>
							<tr>
								<th width="120">支付方式:</th>
								<td><input type="text" name="payChannel" style="width: 440px" value="0">0-网付，1-到付</td>
							</tr>
							<tr>
								<th width="120">添加菜品未完，待续。。。</th>
								<td></td>
							</tr>
							<tr>
								<th></th>
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
