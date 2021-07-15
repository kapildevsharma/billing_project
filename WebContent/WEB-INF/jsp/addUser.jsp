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
	String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg").trim();
	String contextPath = request.getContextPath();
	logger.info("host: " + host + " login IP: " + ipAddr + " error ::" + msg);
%>
<script type="text/javascript" src="resources/js/manageUser.js"></script>
<script type="text/javascript" src='resources/js/dwrajax.js'></script>
<style type="text/css">
table td {
	border-top: none !important;
	color: #e9e9ea;
}
</style>

<h2 class="col-sm-12 text-center htext_color">User Registration</h2>

<p class="col-sm-12 " id="userErr"></p>
<%	if (!msg.equals("")) {	%>
<p class="col-sm-12 ">
	<span style="color: red"><%=msg%></span>
<p>
<%	} %>

<div class="col-sm-12">
	<form id="userForm" class="col-sm-7 form-login" action="#" method="post">
		<table class="table">
			<tr class="form-group">
				<td>
					<label class="form-label">Name <span style="color: red">*</span></label> 
					<input type="hidden" value="${user.id}" id="updateid" name="updateid">
				</td>
				<td>
					<input class="form-control" type="text" value="${user.username}" id="name" name="name" placeholder="Name" />
				</td>
			</tr>

			<tr class="form-group">
				<td>
					<label class="form-label">Password<span style="color: red">*</span></label>
				</td>
				<td>
					<input class="form-control" type="password" id="pwd" value="${user.password}" name="pwd" placeholder="Password" />
				</td>
			</tr>
			<tr>
				<td>
					<label class="form-label">Birth Date<span style="color: red">*</span></label>
				</td>
				
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd" value="${user.dob}" var="birthDate" />
					<input class="form-control" type="date"	value="${birthDate}" id="dob" name="dob" placeholder="yyyy-MM-dd" />
				</td>
			</tr>
			<tr class="form-group">
				<td><label class="form-label">Contact Number</label></td>
				<td>
					<input class="form-control" type="text" value="${user.contact_no}" id="contact" name="contact" placeholder="Contact Number" />
				</td>
			</tr>
			<tr class="form-group">
				<td><label class="form-label">Address</label></td>
				<td>
					<textarea rows="3" class="form-control" id="address" 
					name="address" placeholder="Address"><c:out value="${user.address}" /></textarea>
				</td>
			</tr>
			<tr class="form-group">
				<td>
					<input class="btn btn-success" type="button" id="btn_user" value="Submit" /> 
					<input class="btn btn-danger" type="reset" value="Cancel" name="reset">
				</td>
			</tr>

		</table>
	</form>
</div>


