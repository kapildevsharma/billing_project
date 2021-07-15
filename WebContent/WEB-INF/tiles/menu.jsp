<%@ page language="java" import="org.apache.log4j.Logger,model.Users"%>
<%!static Logger logger = Logger.getLogger("menu.jsp");%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	String ipAddr = request.getRemoteAddr();
	String host = request.getServerName();
	logger.info("host: " + host + " login IP: " + ipAddr);
	Users user =(Users) request.getSession().getAttribute("loginUser");
	int user_id = user.getId();
	logger.info("login user id : "+user_id);
%>

<div class="col-sm-12 top50">
	<ul>
	<% if (user_id==15) {%>
		<li><a href="addUser">New User</a></li>
		<li><a href="viewUser">View All User</a></li>
	<%	} %>
		<li><a href="addParty">New Party</a></li>
		<li><a href="viewParty">View All Party</a></li>
		<li><a href="addMaterial">New Material</a></li>
		<li><a href="viewMaterial">View All Material</a></li>
		<li><a href="addEmployee">Add Employee</a></li>
		<li><a href="viewEmployee">View All Employee</a></li>
		<li><a href="uploadMultiFile">Upload Multiple Files</a></li>
		<li><a href="uploadOneFile">Upload One File</a></li>
		<li><a href="uploadResult">View upload Result</a></li>
	</ul>
</div>
