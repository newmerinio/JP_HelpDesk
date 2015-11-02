<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expected Opportunity Value View</title>
</head>
<body>

		<table border="1" width="100%" style="border-collapse: collapse;">
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b>First Value :</b> </td>
							<td width="10%"><s:property value="%{firstExpVal}" /></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%"><b>Date :</b> </td>
							<td width="10%"><s:property value="%{firstExpDate}" /></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b> Second Value:</b> </td>
							<td width="10%">NA</td>
				</tr>
				<tr style="height: 23px">
							<td width="10%"><b>Date:</b> </td>
							<td width="10%">NA</td>
				</tr>
		</table>
</body>
</html>