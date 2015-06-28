<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下单成功</title>

<script type="text/javascript" src="<%=path%>/static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" >
function getorderinfo(){
	$.ajax({
			url: "<%=path%>/rest/getorderinfo",//要访问的后台地址
			data: "orderId=" + ${sessionScope.orderId},//要发送的数据
			type: "get", //使用get方法访问后台
			dataType: "json", //返回json格式的数据
			async: true,
			success: function(msg){//msg为返回的数据，在这里做数据绑定
				alert("getuserinfo response:"+JSON.stringify(msg));
			}
	});
}
</script>
</head>
<body>

<%

if (session.getAttribute("orderId") == null){
%>
您创建订单失败！
<%
}else{
%>
您创建订单成功！orderId： ${sessionScope.orderId}
<%} 
%>
<hr>
<a href="#" onclick="history.go(-1);">返回</a>

</body>
</html>