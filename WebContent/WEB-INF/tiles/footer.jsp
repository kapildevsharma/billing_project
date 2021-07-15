<%@ page language="java" import="java.util.*,org.apache.log4j.Logger"%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	String ipAddr = request.getRemoteAddr();
	String host = request.getServerName();
	int year = Calendar.getInstance().get(Calendar.YEAR);
%>
<footer>

<div class="container">
<div class="row">
	<div class="col-md-12 text-center">
	<p class="footer-text">Kapil InfoTech Pvt. Ltd. <%=year %> © All Rights Reserved.<p></div>
	</div>
	</div>

</footer>