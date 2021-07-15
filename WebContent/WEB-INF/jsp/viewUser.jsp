<%@ page language="java"  import="org.apache.log4j.Logger" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%!static Logger logger = Logger.getLogger("viewEmployee.jsp");%>
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

<h2 class="col-sm-12 text-center text_white">View User Information</h2>

<div class="col-sm-12 " id="loadingDiv" style="display: none;">
	<img alt="loading" src="resources/images/loading-bar.gif">
</div>

<c:if test="${!empty users}">
	<form class="col-sm-12" id="userForm"  action="addUser" method="post">
		<div class="col-sm-2 col-sm-offset-10 file_padding">
			<a href="exceldownload?id=user">
				<img alt="excel sheet"	src="resources/images/excel.png" > 
			</a>
			
			<a href="pdfdownload?id=user" > 
				<img alt="pdf sheet" src="resources/images/pdf.png" >
			</a>
			
			<a href="csvdownload?id=user" > 
				<img alt="pdf sheet" src="resources/images/csv.png" >
			</a>
		</div> 
		<input type="hidden" id="totalrecord" value="${totalRecord}">
		<input type="hidden" id="cpage" value="${cpage}">
		<div class="col-sm-12">
			<table class="table table-responsive table-hover table-inverse table-bordered" >
				<thead class="thead-inverse">
					<tr scope="row" class="active">
						<th>UserID</th>
						<th>User Name</th>
						<th>Address</th>
						<th>DOB</th>
						<th>Contact No.</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<tr  class="info">
							<td><c:out value="${user.id}" /></td>
							<td><c:out value="${user.username}" /></td>
							<td><c:out value="${user.address}" /></td>
							<td>
							<fmt:formatDate pattern="dd-MM-yyyy" value="${user.dob}" /> 
							</td>
							<td><c:out value="${user.contact_no}" /></td>
							<td align="center">
								<input type="hidden" value="0" id="updateid" name="updateid">
								<button type="button" class="btn btn-success" value="Edit" onclick="submitDetails(${user.id})"><span class="glyphicon glyphicon-edit"></span></button>
								<button type="button" class="btn btn-danger" value="Delete" onclick="deleteUser(${user.id})"><span class="glyphicon glyphicon-remove"></span></button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="col-sm-12" id="pagelimit">
			
		</div>
	</form>
</c:if>


