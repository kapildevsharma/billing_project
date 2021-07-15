<%@ page language="java" import="java.util.*"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="org.apache.log4j.Logger"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%!static Logger logger = Logger.getLogger("addParty.jsp");%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);

	String ipAddr = request.getRemoteAddr();
	String host = request.getServerName();
	String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg").trim();
	logger.info("host: " + host + " login IP: " + ipAddr + " error ::" + msg);
%>
<script type="text/javascript" src="resources/js/manageParty.js"></script>
		
<style type="text/css">
table td {
	border-top: none !important;
	color: #e9e9ea;
}
</style>

<h2 class="col-sm-12 text-center htext_color" >Party Registration</h2>

<p class="col-sm-12 " id="partyErr"></p>
<%	if (!msg.equals("")) { 	%>
<p class="col-sm-12 ">	<span style="color: red"><%=msg%></span><p>
<%	} %>
<div class="col-sm-12">
	<form id="partyForm" class="col-sm-6 form-login" action="#" method="post">
		<table class="table" >
			<tr class="form-group">
				<td>
					<label class="form-label">Party's Name<span style="color: red">*</span></label>
					<input type="hidden" value="${party.party_id}" id="updateid" name="updateid">
				</td>
				<td>
					<input class="form-control" type="text" value="${party.name}" id="name" name="name" placeholder="Party'sName" />
				</td>
			</tr>

			<tr class="form-group">
				<td>
					<label class="form-label">TIN Number<span style="color: red">*</span></label>
				</td>
				<td>
					<input class="form-control" type="text" id="tin_no" value="${party.tin_number}" name="tin_no" placeholder="TIN Number" />
				</td>
			</tr>
			<tr class="form-group">
				<td> 
					<label class="form-label">Party's Active</label> 
				</td>
				<td class="form-check form-check-inline"><c:choose>
						<c:when test="${!empty party.isActive }">
							<c:choose>
								<c:when test="${party.isActive eq 0 }" >
									<label class="form-check-label">
										<input class="form-check-input" type="radio" name="isActive" value="0" checked> No 
			  							<input class="form-check-input" type="radio" name="isActive" value="1">Yes
									</label>
								</c:when>
								<c:otherwise>
									<label class="form-check-label">
										<input class="form-check-input" type="radio" name="isActive" value="0"> No 
			  							<input class="form-check-input" type="radio" name="isActive" value="1" checked>Yes
									</label>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<label class="form-check-label">
								<input class="form-check-input" type="radio" name="isActive" value="0" checked> No 
	  							<input class="form-check-input" type="radio" name="isActive" value="1">Yes
							</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr class="form-group">
				<td>
					<label class="form-label">Contact Number<span style="color: red">*</span></label>
				</td>
				<td>
					<input class="form-control"  type="text" value="${party.contact_no}" id="contact_no" name="contact_no" placeholder="Contact Number" />
				</td>
			</tr>
			<tr class="form-group">
				<td><label class="form-label">Address</label></td>
				<td>
					<textarea class="form-control" id="address" 
					name="address" placeholder="Address"><c:out value="${party.address}" /></textarea>
				</td> 
			</tr>
			<tr class="form-group">
				<td>
					<input class="btn btn-success" type="button" id="btn_party" value="Submit" /> 
					<input class="btn btn-danger" type="reset" value="Cancel" name="reset">
				</td>
			</tr>

		</table>
	</form>
</div>

