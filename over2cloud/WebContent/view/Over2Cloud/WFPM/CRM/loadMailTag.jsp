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

		<table border="1" width="100%" style="border-collapse: collapse;">
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Name:</td>
							<td width="10%"><s:property value="%{name}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%">Organization Name:</td>
							<td width="10%"><s:property value="%{organization_name}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Communication Name:</td>
							<td width="10%"><s:property value="%{com_name}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%">Mobile:</td>
							<td width="10%"><s:property value="%{mobileNo}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%">Address:</td>
							<td width="10%"><s:property value="%{address}"/></td>
				</tr>
				
		</table>
</body>
</html>