<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,org.apache.log4j.Logger"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%!static Logger logger = Logger.getLogger("addUser.jsp");%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	String ipAddr = request.getRemoteAddr();
	String host = request.getServerName();
	String msg = request.getParameter("msg") == null ? "" : request
			.getParameter("msg").trim();
	String contextPath = request.getContextPath();
	logger.info("host: " + host + " login IP: " + ipAddr );
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Users Login</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel='stylesheet' type='text/css' href='resources/css/bootstrap.min.css' />
<link rel='stylesheet' type='text/css'href='resources/css/jquery-ui.css' />
<link rel='stylesheet' type='text/css'href='resources/css/style.css' />

<link rel="stylesheet" href='resources/css/bootstrap.min.css'>
<script type="text/javascript" src="resources/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="resources/js/login.js"></script>



<script type='text/javascript' src='<%=contextPath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=contextPath%>/dwr/interface/MyAjax.js'></script>
<script type='text/javascript' src='<%=contextPath%>/dwr/util.js'></script>

</head>
<body background="resources/images/login-bg1.jpg">
	<div class="container">
		<div class="row">
			<div class="col-md-offset-4 col-md-5">
				<div class="col-sm-12 div-padding" >
					<form id="loginForm" class="col-sm-12 form-login login_form" action="#"
						method="post">
						<h2 class="alert alert-info text-center">Login</h2> 
						<c:if test="${param.logout != null}">
							<div id="logoutmsg" class="alert alert-success">
								<p>You have been logged out successfully.</p>
							</div>
						</c:if>
						<div id="userErr"> </div>
						<div class="form-group col-xs-12">
							<div>
								<label class="form-label">UserName <span
									style="color: red">*</span></label> <input class="form-control"
									type="text" id="name" name="name" placeholder="Name" />
							</div>
						</div>

						<div class="form-group col-xs-12">
							<div>
								<label class="form-label">Password<span
									style="color: red">*</span></label> <input class="form-control"
									type="password" id="pwd" name="pwd" placeholder="Password" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12 text-center">
								<input class="btn btn-primary" type="button" id="btn_user"
									style="width: 100%;" value="Sign in" />
							</div>
							<!-- <div class="col-sm-6 text-center">
								<a class="btn btn-danger"  href="registerUser">Sign Up</a>
							</div> -->
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>