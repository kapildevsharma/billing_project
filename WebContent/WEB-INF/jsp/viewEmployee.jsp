<%@ page language="java"  import="org.apache.log4j.Logger" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="resources/js/manageEmp.js"></script>

<h2 class="col-sm-12 text-center htext_color">View Employee Information</h2>

<div class="col-sm-12 " id="loadingDiv" style="display: none;">
	<img alt="loading" src="resources/images/loading-bar.gif">
</div>

<c:if test="${!empty employees}">
	<form class="col-sm-12 " id="empForm" action="addEmployee" method="post">
		<div class="col-sm-2 col-sm-offset-10 file_padding">
			<a  href="exceldownload?id=employee">
				<img alt="excel sheet"	src="resources/images/excel.png" > 
			</a>
			
			<a  href="pdfdownload?id=employee" > 
				<img alt="pdf sheet" src="resources/images/pdf.png" >
			</a>
			
			<a href="csvdownload?id=employee" > 
				<img alt="pdf sheet" src="resources/images/csv.png" >
			</a>
		</div> 
		<input type="hidden" id="totalrecord" value="${totalRecord}">
		<input type="hidden" id="cpage" value="${cpage}">
		<div class="col-sm-12">
			<table id="tblGrid" class="table table-responsive table-hover table-inverse table-bordered">
				<thead class="thead-inverse"  >
					<tr scope="row" class="active" >
						<th>EmployeeID</th>
						<th>Name</th>
						<th>Email</th>
						<th>DOB</th>
						<th>DOJ</th>
						<th>Address</th>
						<th>Contact No.</th>
						<th>Action</th>
						
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${employees}" var="emp">
					<tr class="info">
						<td><c:out value="${emp.emp_id}" /></td>
						<td><c:out value="${emp.empname}" /></td>
						<td><c:out value="${emp.emailid}" /></td>
						<td><fmt:formatDate pattern="dd-MM-yyyy" value="${emp.dob}" /></td>
						<td><fmt:formatDate pattern="dd-MM-yyyy" value="${emp.doj}" /></td>
						<td><c:out value="${emp.address}" /></td>
						
						<td><c:out value="${emp.contact_no}" /></td>
							
						<td align="center">
							<input type="hidden" value="0" id="updateEmpID"	name="updateEmpID">
							<button type="button" class="btn btn-success"  value="Edit" onclick="submitDetails(${emp.emp_id})"><span class="glyphicon glyphicon-edit"></span></button>
							<button type="button" class="btn btn-danger" value="Delete" onclick="deleteEmployee(${emp.emp_id})"><span class="glyphicon glyphicon-remove"></span></button>
						</td>
					</tr>
				</c:forEach>
				<tbody>
			</table>
		</div>
		<div class="col-sm-12" id="pagelimit">
			
		</div>
	</form>
</c:if>

		