<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,org.apache.log4j.Logger"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%!static Logger logger = Logger.getLogger("addEmployee.jsp");%>
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
<script type="text/javascript" src="resources/js/manageEmp.js"></script>
		
<style type="text/css">
table td {
    border-top: none !important;
    color: #e9e9ea;
}
</style>
<h2 class="col-sm-12 text-center htext_color" >Employee Registration</h2>

<p class="col-sm-12 " id="userErr"></p>
<%	if (!msg.equals("")) {	%>
<p class="col-sm-12 ">
	<span style="color: red"><%=msg%></span>
<p>
<%	} %>

<div class="col-sm-12">
	<form id="empForm" class="col-sm-6 form-login" action="#" method="post">
		<table class="table">
			<tr class="form-group">
				<td>
					<label class="form-label">Employee Name<strong style="color: red">*</strong></label> 
					<input type="hidden" value="${employee.emp_id}" id="updateid" name="updateid">
				</td>
				<td>
					<input class="form-control" type="text"	value="${employee.empname}" id="name" name="name"	placeholder="Name" required />
				</td>
			</tr>
			
			<tr class="form-group">
				<td>
					<label class="form-label">Password<strong style="color: red">*</strong></label></td>
				<td>
					<input class="form-control" type="password" id="pwd" value="${employee.emp_password}" name="pwd" placeholder="Password" required />
				</td>
			</tr>
			
			<tr class="form-group">
				<td>
					<label class="form-label">Email ID<strong style="color: red">*</strong></label>
				</td>
				<td>
					<input class="form-control" type="email" value="${employee.emailid}" id="emailid" name="emailid"	placeholder="email@example.com" required />
				</td>
			</tr>
			<tr>
				<td>
					<label class="form-label">Birth Date<strong	style="color: red">*</strong></label>
				</td>
				<fmt:formatDate pattern="yyyy-MM-dd" value="${employee.dob}"
					var="dataOfBirth" />
				<td><input class="form-control" type="date"
					value="${dataOfBirth}" id="dob" required name="dob"
					placeholder="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td>
					<label class="form-label">Joining Date<strong style="color: red">*</strong></label>
				</td>
				<fmt:formatDate pattern="yyyy-MM-dd" value="${employee.doj}" var="dateOfJoining" />
				<td>
					<input class="form-control" type="date" value="${dateOfJoining}" id="doj" required name="doj" placeholder="yyyy-MM-dd" />
				</td>
			</tr>
			<tr class="form-group">
				<td>
					<label class="form-label">Contact Number</label>
				</td>
				<td>
					<input class="form-control" type="text"	value="${employee.contact_no}" id="contact" name="contact"	placeholder="Contact Number" />
				</td>
			</tr>
			<tr class="form-group">
				<td>
					<label class="form-label">Address</label>
				</td>
				<td>
					<textarea class="form-control" id="address"
						name="address" placeholder="Address"><c:out	value="${employee.address}" /></textarea>
				</td>
			</tr>
			<tr class="form-group">
				<td>
					<input class="btn btn-success"type="button" id="btn_employee" value="Submit" /> 
					<input class="btn btn-danger" type="reset" value="Cancel" name="Cancel">
				</td>
			</tr>

		</table>
	</form>
</div>
			
