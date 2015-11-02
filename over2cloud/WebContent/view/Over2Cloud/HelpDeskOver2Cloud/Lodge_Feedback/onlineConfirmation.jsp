<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib  prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
</head>
<body>
<c:set var="feedbackBy" value="${feedbackBy}"/>
<c:set var="ticketNo" value="${ticket_no}" /> 
<c:set var="resolvedBy" value="${allotto}" />
<c:set var="allot_to_mobno" value="${allot_to_mobno}" /> 
<c:set var="esc_time" value="${escTime}" />

<div id="1" align="center" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><b>Dear <c:out value="${feedbackBy}" />, your Ticket ID <c:out value="${ticketNo}" /> has been registered successfully.  <c:out value="${resolvedBy}" />, Mobile No. <c:out value="${allot_to_mobno}" /> will contact you with in <c:out value="${esc_time}" />. Thanks for giving us opportunity to serve you.</b></font></div>
</body>
</html>

