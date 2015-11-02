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
<div id="1" align="center" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Dear <c:out value="${feedbackBy}" />, your Ticket Can't be opened due to some issue.Kindly Contact System Administrator.</b></font></div>
</body>
</html>

