<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<font face="Arial, Helvetica, sans-serif" size="2"><B>Breakdown History Details for <s:property value="%{assetName}"/>, Code: <s:property value="%{assetCode}"/>, Sr. No. <s:property value="%{slNo}"/></B></font>
<table border="1" width="100%" style="border-collapse: collapse;">
			<tr bgcolor="#BDBDBD" style="height: 25px">
				<td align="center" width="10%">Ticket ID</td>
				<td align="center" width="10%">Issue</td>
				<td align="center" width="10%">Brief</td>
				<td align="center" width="10%">Open On</td>
				<td align="center" width="10%">Closed On</td>
				<td align="center" width="10%">Total Breakdown</td>
				<td align="center" width="10%">RCA</td>
				<td align="center" width="10%">Spare Used</td>
				<td align="center" width="10%">Resolved By</td>
				<td align="center" width="10%">Escalated</td>
				<td align="center" width="10%">Level</td>
			</tr>
			<s:iterator id="assetComplaint"  status="status" value="%{complainDetailList}" >
				<tr style="height: 23px">
					<td align="center"><s:property value="#assetComplaint.ticket_no" /></td>
					<td align="center"><s:property value="#assetComplaint.feedback_catg" /></td>
					<td align="center"><s:property value="#assetComplaint.comments" /></td>
					<td align="center"><s:property value="#assetComplaint.open_date" /></td>
					<td align="center"><s:property value="#assetComplaint.actionTakenDate" /></td>
					<td align="center"><s:property value="#assetComplaint.totalBreakdown" /></td>
					<td align="center"><s:property value="#assetComplaint.RCA" /></td>
					<td align="center"><s:property value="#assetComplaint.other" /></td>
					<td align="center"><s:property value="#assetComplaint.empName" /></td>
					<td align="center"><s:property value="#assetComplaint.escFlag" /></td>
					<td align="center"><s:property value="#assetComplaint.escLevel" /></td>
				</tr>
			</s:iterator>
		</table>
</center>
</body>
</html>