<%@ page session="true"%>

<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>


<h2><a href="/delivery/admin/uploadfile">Upload Example</a></h2>

<br>

<br>

<br>

<br>

<%

if (session.getAttribute("uploadFile") != null

&& !(session.getAttribute("uploadFile")).equals("")) {

%>

<h3>Uploaded File</h3>

<br>

<img

src="<%="/delivery/static/images/"

+ session.getAttribute("uploadFile")%>"

alt="Upload Image" />

<%

session.removeAttribute("uploadFile");

}

%>

