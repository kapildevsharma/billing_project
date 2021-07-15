<%@ page language="java"  import="org.apache.log4j.Logger" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%!static Logger logger = Logger.getLogger("viewParty.jsp");%>
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
<script type="text/javascript" src="resources/js/manageParty.js"></script>

<h2 class="col-sm-12 text-center htext_color">View Party Information</h2>

<c:if test="${!empty parties}">
	<form class="col-sm-12 " id="partyForm" action="addParty" method="post">
		<div class="col-sm-2 col-sm-offset-10 file_padding">
			<a href="exceldownload?id=party">
				<img alt="excel sheet"	src="resources/images/excel.png" > 
			</a>
			
			<a href="pdfdownload?id=party" > 
				<img alt="pdf sheet" src="resources/images/pdf.png" >
			</a>
			
			<a href="csvdownload?id=party" > 
				<img alt="pdf sheet" src="resources/images/csv.png" >
			</a>
		</div> 
		<input type="hidden" id="totalrecord" value="${totalRecord}">
		<input type="hidden" id="cpage" value="${cpage}">
		<div class="col-sm-12">
			<table class="table table-responsive table-hover table-inverse table-bordered">
				<thead class="thead-inverse" >
					<tr scope="row" class="active" >
						<th>Party ID</th>
						<th>Party Name</th>
						<th>TIN Number</th>
						<th>Address</th>
						<th>Contact No.</th>
						<th>Activation</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${parties}" var="partyInfo">
						<tr class="info">
							<td><c:out value="${partyInfo.party_id}" /></td>
							<td><c:out value="${partyInfo.name}" /></td>
							<td><c:out value="${partyInfo.tin_number}" /></td>
							<td><c:out value="${partyInfo.address}" /></td>
							<td><c:out value="${partyInfo.contact_no}" /></td>
							<td><c:out value="${partyInfo.isActive}" /></td>
							<td align="center">	
								<input type="hidden" value="0"	id="updatePartyId" name="updatePartyId">
								<button type="button" class="btn btn-success" value="Edit" onclick="submitDetails(${partyInfo.party_id})"><span class="glyphicon glyphicon-edit"></span></button>
								<button type="button" class="btn btn-danger" value="Delete" onclick="deleteParty(${partyInfo.party_id})"><span class="glyphicon glyphicon-remove"></span></button>
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

