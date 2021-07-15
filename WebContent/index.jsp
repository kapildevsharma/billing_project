<%@page	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	</head>
	<body>
	 <script type="text/javascript">
	    window.onload = function() {
	        setTimeout(function() {
	            window.location = "http://localhost:1000/billingSpring/login";
	        }, 1);
	    };
	</script> 
	
	</body>
</html>