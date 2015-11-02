<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Contact Details</title>
</head>
<body>
		<table border="1" width="100%" style="border-collapse: collapse;">
		<s:iterator value="contactDetails" var="contact">
			<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b>Designation:</b> </td>
							<td width="10%"><s:property  value="%{designation}"/></td>
							<td width="10%"><b>Ownership:</b></td>
							<td width="10%"><s:property  value="%{ownership}"/></td>
							<td width="10%"><b>Mobile No:</b></td>
							<td width="10%"><s:property  value="%{contactNumber}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%"><b>Email ID:</b></td>
							<td width="10%"><s:property  value="%{email}"/></td>
							<td width="10%"><b>Birthday:</b></td>
							<td width="10%"><s:property  value="%{birthday}"/> </td>
							<td width="10%"><b>Anniversary:</b></td>
							<td width="10%"><s:property  value="%{anniversary}"/> </td>
				</tr>
				
		</s:iterator>
				
				
		</table>
</body>
</html>