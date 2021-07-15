<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	String ipAddr = request.getRemoteAddr();
	String host = request.getServerName();
	String contextPath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><tiles:insertAttribute name="title" ignore="true" /></title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel='stylesheet' type='text/css' href='resources/css/bootstrap.min.css' />
		<link rel='stylesheet' type='text/css' href='resources/css/style.css' />
		<link rel='stylesheet' type='text/css' href='resources/css/jquery-ui.css' />
		<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
		<script type='text/javascript' src='<%=contextPath%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=contextPath%>/dwr/interface/MyAjax.js'></script>
		<script type='text/javascript' src='<%=contextPath%>/dwr/util.js'></script>
		<script type="text/javascript" src="resources/js/manageFormValidation.js"></script>
	</head>

	<body class="main_body">
		<tiles:insertAttribute name="header" />
	
		<div class="content">
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-2 content-left">
						<tiles:insertAttribute name="menu" />
					</div>
					<div class="col-md-10 content-right">
						<tiles:insertAttribute name="body" />
					</div>
	
				</div>
			</div>
		</div>
		<tiles:insertAttribute name="footer" />
	
	</body>

</html>
