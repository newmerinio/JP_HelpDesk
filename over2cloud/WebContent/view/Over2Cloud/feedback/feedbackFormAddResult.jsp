<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
Feedback for Mrd No <b><u><s:property value="%{clientId}" /></u></b>, added successfully !!! ,<Br />
Total Positive Feedback added today: <b><u><s:property value="%{todayPositive}" /></u></b> <Br />
Total Negative Feedback added today: <b><u><s:property value="%{todayNegative}" /></u></b><Br />
Total Feedback added today: <b><u><s:property value="%{todayTotal}" /></u></b><Br />
 </center>
</body>
</html>