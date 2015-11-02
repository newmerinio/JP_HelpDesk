<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client For View</title>
</head>
<body>
		<table border="1" width="100%" style="border-collapse: collapse;">
				
				<tr  style="height: 25px">
							<td width="10%"><b>Address:</b></td>
							<td width="10%"  colspan="2"><s:property value="%{address}"/></td>
							<td width="10%"><b>Location:</b></td>
							<td width="10%"><s:property value="%{location}"/></td>
							<td width="10%"></td>
							
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b>Target Segment:</b></td>
							<td width="10%"><s:property value="%{targetSegment}"/></td>
							<td width="10%"><b>Website:</b></td>
							<td width="10%"><s:property value="%{website}"/></td>
							<td width="10%"></td>
							<td width="10%"></td>
				</tr>
				
				<tr style="height: 23px">
							<td width="10%"><b>Contact No.:</b></td>
							<td width="10%"><s:property value="%{contactNumber}"/></td>
							<td width="10%"><b>Email ID:</b></td>
							<td width="10%"><s:property value="%{email}"/></td>
							<td width="10%"></td>
							<td width="10%"></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 23px">
							<td width="10%"><b></b>Industry:</td>
							<td width="10%"><s:property value="%{industry}"/></td>
							<td width="10%"><b>Sub Industry:</b></td>
							<td width="10%"><s:property value="%{subIndustry}"/></td>
							<td width="10%"></td>
							<td width="10%"></td>
				</tr>
				
				<s:iterator value="contactDetails" var="contact">
				
				<tr style="height: 23px">
					<td colspan="3" align="center"><b> Contact Name :</b> <s:property  value="%{contactName}"/></td>
				</tr>
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