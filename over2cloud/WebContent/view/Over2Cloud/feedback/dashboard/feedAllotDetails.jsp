<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table width="100%" border="1">
		 <tr bgcolor="#FE9A2E">
			<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Employee Name</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Pending</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>High Priority</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Snooze</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Ignore</b></font></td>
		    <td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Re-Assign</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Resolved</b></font></td>
     	    <td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Total</b></font></td>
       </tr>
       <s:iterator  id="first" status="status" value="%{allotPojo}">
       <tr bgcolor="#F5D0A9">
			<td >
				<b><s:property value="%{empName}"/></b>
			</td>
			<td align="center" width="5%">
				<b><s:property value="%{totalPending}"/></b>
			</td>
			<td align="center" width="5%">
				<b><s:property value="%{highPriority}"/></b>
			</td>
			<td align="center" width="5%">
				<b><s:property value="%{snooze}"/></b>
			</td>
			<td align="center" width="5%">
				<b><s:property value="%{ignore}"/></b>
			</td>
			<td align="center" width="5%">
				<b><s:property value="%{reAsign}"/></b>
			</td>
			<td align="center" width="5%">
				<b><s:property value="%{resolved}"/></b>
			</td>
			<td align="center" width="5%">
				<b><s:property value="%{totalTickets}"/></b>
			</td>
       </tr>
       </s:iterator>
</body>
</html>