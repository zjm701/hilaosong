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
<title>登陆成功</title>

<script type="text/javascript" src="<%=path%>/static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" >
function getuserinfo(){
	$.ajax({
			url: "<%=path%>/rest/getuserinfo",//要访问的后台地址
			data: "userId=" + ${sessionScope.loginId},//要发送的数据
			type: "get", //使用get方法访问后台
			dataType: "json", //返回json格式的数据
			async: true,
			success: function(msg){//msg为返回的数据，在这里做数据绑定
				alert("getuserinfo response:"+JSON.stringify(msg));
				location.reload();
			}
	});
}
</script>
</head>
<body>

<%

if (session.getAttribute("loginId") == null){
%>
您登录失败！
<hr>
<a href="#" onclick="history.go(-1);">重新登录</a>
<%
}else{
%>
您已成功登录！userId： ${sessionScope.loginId}
<hr>
<a href="#" onclick="getuserinfo()">获取用户信息</a>
<%} 
if(session.getAttribute("user") != null){
%>
<hr>
用户昵称： ${sessionScope.user.nickname}
<hr>
<a href="createguodi.jsp">创建DIY锅底</a>
<a href="updateguodi.jsp">更新DIY锅底</a>
<a href="createorder.jsp">创建定单</a>
<%}
%>

</body>
</html>