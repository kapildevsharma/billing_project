<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,org.apache.log4j.Logger"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%!static Logger logger = Logger.getLogger("uploadFileResult");%>
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

<h2 class="col-sm-12 text-center htext_color">Uploaded Files:</h2>

   <div class="col-sm-12">
      <h3 class="col-sm-12">Uploaded Files:</h3>
     
    	<div class="col-sm-12"> Description: ${description}</div>
     
   		<div class="col-sm-12">
		    <c:forEach items="${uploadedFiles}" var="file">
		        <strong>   - ${file} </strong> <br>
		    </c:forEach>
    	</div>
 
    
 </div>
