<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,org.apache.log4j.Logger"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%!static Logger logger = Logger.getLogger("uploadFileMulti");%>
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
<h2 class="col-sm-12 text-center htext_color">Upload Multi File</h2>

<div class="col-sm-12">
	<!-- MyUploadForm -->
	<form:form class="col-sm-10 form-login" modelAttribute="myUploadForm"
		method="POST" action="" enctype="multipart/form-data">
		<table class="table">
			<tr class="form-group">
				<td>Description:</td>
				<td><form:input path="description" class="form-control"
						style="width:300px;" /></td>
			</tr>
			<tr class="form-group">
				<td>File to upload (1):</td>
				<td><form:input path="fileDatas" type="file" /></td>
			</tr>
			<tr class="form-group">
				<td>File to upload (2):</td>
				<td><form:input path="fileDatas" type="file" /></td>
			</tr>
			<tr class="form-group">
				<td>File to upload (3):</td>
				<td><form:input path="fileDatas" type="file" /></td>
			</tr>
			<tr class="form-group">
				<td>File to upload (4):</td>
				<td><form:input path="fileDatas" type="file" /></td>
			</tr>
			<tr class="form-group">
				<td>File to upload (5):</td>
				<td><form:input path="fileDatas" type="file" /></td>

			</tr>
			<tr class="form-group">
				<td><input class="btn btn-success" type="submit" value="Upload">
				</td>
			</tr>

		</table>
	</form:form>
</div>
