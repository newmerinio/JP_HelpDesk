<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>A\c Manager Click View</title>
</head>
<body>

		<table border="1" width="100%" style="border-collapse: collapse;">
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"> <b>Group Name:</b> </td>
							<td width="10%"><s:property value="%{groupname}"/></td>
							<td width="10%"><b>Department:</b> </td>
							<td width="10%"><s:property value="%{deptname}"/></td>
				</tr>
				<tr style="height: 23px">
					<td width="10%"><b>A\c Manager Name:</b> </td>
					<td width="10%"><s:property value="%{empname}"/></td>
					<td width="10%"><b>Mobile No.:</b> </td>
					<td width="10%"><s:property value="%{mobileno}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 23px">
							<td width="10%"><b>Address:</b> </td>
							<td width="10%"><s:property value="%{address}"/></td>
							<td width="10%"><b> Email:</b> </td>
							<td width="10%"><s:property value="%{email}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%"><b>DOB :</b> </td>
							<td width="10%"><s:property value="%{dob}"/></td>
							<td width="10%"><b> DOA:</b> </td>
							<td width="10%"><s:property value="%{doa}"/></td>
				</tr>
		</table>
</body>
</html>