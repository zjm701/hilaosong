<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<title>home</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"></link>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script> 
		<script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
		<script src="/delivery/static/js/underscore-min.js"></script>
	</head> 
	<body>
		<div class="page">
			<tiles:insertAttribute name="header" />
				<div class="container-fluid">
					<div class="row">
						<tiles:insertAttribute name="menu" />
						<tiles:insertAttribute name="body" />
					</div>
				</div>
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>