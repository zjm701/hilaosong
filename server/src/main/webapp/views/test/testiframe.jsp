<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>测试Iframe</title>

<script type="text/javascript" src="<%=path%>/static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/md5.js"></script>
<script type="text/javascript" >
</script>

</head>

<body id="main">
     <h1>Test Iframe</h1> 
     <h3>带参数输入  例如："testiframe.jsp?loginId=552136"</h1> .
　　　　<Iframe name="tt" src="welcome2iframe.jsp?loginId=<%=request.getParameter("loginId")%>" width="250" height="200" scrolling="no" frameborder="1"></iframe> 
</body> 
</html>
