<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>测试 DIY锅底</title>

<script type="text/javascript" src="<%=path%>/static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" >
$(function() {
	$("input[name='save']").click(function() {
		var content = {
				guodiName: $.trim($("input[name='guodiName']").val()),
				dishId: $.trim($("input[name='dishId']").val()),
				dishId2: $.trim($("input[name='dishId2']").val())
			};
		$.ajax({
			url: "<%=path%>/rest/creatediyguodi",//要访问的后台地址
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
               		<h1>Hi捞送DIY锅底（测试）</h1>
            	</div>
	            <div class="result-content">
					<div id="top">
						<table class="insert-tab" width="100%">
							<tr>
								<th width="120">用户ID:</th>
								<td>${sessionScope.loginId}</td>
							</tr>
							<tr>
								<th width="120">锅底名称:</th>
								<td><input type="text" name="guodiName" style="width: 440px" value="大宝的0号锅底"></td>
							</tr>
							<tr>
								<th width="120">门店Id:</th>
								<td><input type="text" name="dishId" style="width: 440px" value="10011_020119">10011_020119,10012_020119</td>
							</tr>
							<tr>
								<th width="120">联系人姓名:</th>
								<td><input type="text" name="dishId2" style="width: 440px" value="10016_020115">10016_020115,10229_020115,10039_020115</td>
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
