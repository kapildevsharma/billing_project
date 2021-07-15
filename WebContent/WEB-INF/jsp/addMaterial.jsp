<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,org.apache.log4j.Logger"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%!static Logger  logger = Logger.getLogger("addMaterial.jsp");%>
<%
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0);
 	
  	String ipAddr = request.getRemoteAddr();
  	String host =request.getServerName();
	String msg = request.getParameter("msg")==null?"":request.getParameter("msg").trim();
	logger.info("host: "+host+" login IP: "+ipAddr+" error ::"+msg);
 %>
 
<script type="text/javascript" src="resources/js/manageMaterial.js"></script>
		
<style type="text/css">
table td {
	border-top: none !important;
	color: #e9e9ea;
}
</style>
		

<h2 class="col-sm-12 text-center htext_color">Material Registration</h2>
<p class="col-sm-12 " id="err"></p>
<%	if (!msg.equals("")) { 	%>
<p class="col-sm-12 ">	<span style="color: red"><%=msg%></span><p>
<%	} %>
<div class="col-sm-12">
	<form  class="col-sm-6 form-login" id="materialForm" action="#" method="post">
		<table class="table">
			<tr class="form-group">
				<td>
					<label class="form-label">Party Name<span style="color: red">*</span></label>
				</td>
				<td>
					<select class="form-control" id="party" name="party">
							<option value="0">Select Party</option>
						<c:forEach items="${partyList}" var="partylist">
							<c:choose>
								<c:when test="${not empty material }">
									<c:choose>
										<c:when  test="${partylist.party_id eq material.party.party_id }">
											<option value="${partylist.party_id}" selected >${partylist.name}</option>
										</c:when>
										<c:otherwise> 
										 	<option value="${partylist.party_id}" >${partylist.name}</option>	
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>  
								 	<option value="${partylist.party_id}" >${partylist.name}</option>	
							 	</c:otherwise>
						 	</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr class="form-group">
				<td>
					<label class="form-label">Material Name<span style="color: red">*</span> </label>
					<input class="form-control" type="hidden" value="${material.id}" id="updatemid" name="updatemid">
				</td>
				<td>
					<input class="form-control" type="text" value="${material.materialName}" id="mname" name="mname" required placeholder=" Name" />
				</td>
			</tr>

			<tr class="form-group">
				<td>
					<label class="form-label">Material Type<span style="color: red">*</span></label>
				</td>
				<td>
					<input class="form-control" type="text" id="mtype" value="${material.materialType}" name="mtype" required placeholder=" Type" />
				</td>
			</tr>

			<tr class="form-group">
				<td>
					<label class="form-label">Material Price<span style="color: red">*</span></label>
				</td>
				<td>
					<input class="form-control" type="text" value="${material.price}" id="mprice" name="mprice" required placeholder=" Price" />
				</td>
			</tr>
			<tr class="form-group">
				<td>
					<label class="form-label">Material Unit<span style="color: red">*</span></label>
				</td>
				<td>
					<input class="form-control" type="text" value="${material.unit}" id="munit" name="munit" required placeholder="Unit" />
				</td>
			</tr>
			<tr class="form-group">
				<td>
					<label class="form-label">Material Activation</label>
				</td>
				<td class="form-check form-check-inline">
					<c:choose>
						<c:when test="${!empty material.isActive }">
							<c:choose>
								<c:when test="${material.isActive eq 0 }">
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
					<input class="btn btn-success" type="button" id="btn_material" value="Submit" /> 
					<input class="btn btn-danger" type="reset" value="Cancel" name="reset">
				</td>
			</tr>
		</table>
	</form>
</div>

