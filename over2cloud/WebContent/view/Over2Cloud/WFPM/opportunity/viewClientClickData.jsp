<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Take Action</title>
</head>
<body>

		<table border="1" width="100%" style="border-collapse: collapse;">
				
				<tr  style="height: 25px">
							<td width="10%"><b>Address:</b> </td>
							<td width="10%"><s:property value="%{address}"/></td>
							<td width="10%"><b>Location:</b> </td>
							<td width="10%"><s:property value="%{location}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 23px">
							<td width="10%"> <b>Industry:</b> </td>
							<td width="10%"><s:property value="%{industry}"/></td>
							<td width="10%"><b>Sub Industry:</b> </td>
							<td width="10%"><s:property value="%{subIndustry}"/></td>
				</tr>
				<tr  style="height: 23px">
							<td width="10%"><b>Contact Person:</b> </td>
							<td width="10%"><s:property value="%{contactName}"/></td>
							<td width="10%"><b> Account Manager:</b> </td>
							<td width="10%"><s:property value="%{acManager}"/></td>
				</tr>
				
		</table>
</body>
</html>