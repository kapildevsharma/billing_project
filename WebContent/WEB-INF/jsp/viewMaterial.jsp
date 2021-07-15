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
<script type="text/javascript" src="resources/js/manageMaterial.js"></script>

<h2 class="col-sm-12 text-center htext_color">View Materials Information</h2>


<c:if test="${!empty materials}">
	<form class="col-sm-12 " id="materialForm" action="addMaterial" method="post">
		<div class="col-sm-2 col-sm-offset-10 file_padding">
			<a href="exceldownload?id=material">
				<img alt="excel sheet"	src="resources/images/excel.png" > 
			</a>
			
			<a href="pdfdownload?id=material" > 
				<img alt="pdf sheet" src="resources/images/pdf.png" >
			</a>
			
			<a href="csvdownload?id=material" > 
				<img alt="pdf sheet" src="resources/images/csv.png" >
			</a>
		</div> 
		<input type="hidden" id="totalrecord" value="${totalRecord}">
		<input type="hidden" id="cpage" value="${cpage}">
		<div class="col-sm-12">
			<table class="table table-responsive table-hover table-inverse table-bordered">
				<thead class="thead-inverse"  >
					<tr scope="row" class="active" >
						<th>S. No.</th>
						<th>Party Name</th>
						<th>Material Name</th>
						<th>Material Type</th>
						<th>Material Unit</th>
						<th>Material Price</th>
						<th>Activation</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${materials}" var="materialInfo">
						<tr class="info">
							<td><c:out value="${materialInfo.id}" /></td>
							<td><c:out value="${materialInfo.party.name}" /></td>
							<td><c:out value="${materialInfo.materialName}" /></td>
							<td><c:out value="${materialInfo.materialType}" /></td>
							<td><c:out value="${materialInfo.unit}" /></td>
							<td><c:out value="${materialInfo.price}" /></td>
							<td><c:out value="${materialInfo.isActive}" /></td>
							<td align="center">
								<input type="hidden" value="0" id="updatemid" name="updatemid">
								<button type="button" class="btn btn-success" value="Edit" onclick="submitDetails(${materialInfo.id})"><span class="glyphicon glyphicon-edit"></span></button>
								<button type="button" class="btn btn-danger" value="Delete" onclick="deleteMaterial(${materialInfo.id})"><span class="glyphicon glyphicon-remove"></span></button>
							
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

			
			
