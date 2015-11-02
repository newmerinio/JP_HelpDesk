<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
			<table border="1" width="100%" style="border-collapse: collapse;">
				<s:iterator value="referancedatalist" var="contact">
				<%-- <s:property value="%{fullDetailShow}"/> --%>
			 	 <s:if test="%{fullDetailShow=='yes'}">
					<tr bgcolor="#D8D8D8" style="height: 25px">
									<td width="10%"><b>Client Name :</b></td>
									<td width="10%"><s:property  value="%{clientname}"/></td>
									<td width="10%"><b>Source Name :</b> </td>
									<td width="10%"><s:property  value="%{sourceMaster}"/></td>
						</tr>
						<tr style="height: 23px">
									<td width="10%"><b>Refered By :</b></td>
									<td width="10%"><s:property  value="%{referedBy}"/></td>
									<td width="10%"><b>Reference Name :</b></td>
									<td width="10%"><s:property  value="%{mapName}"/></td>
						</tr>
						
						<tr bgcolor="#D8D8D8" style="height: 25px">
									<td width="10%"><b> Reference Mobile No:</b></td>
									<td width="10%"><s:property  value="%{mobileNo}"/></td>
						 		    <td width="10%"><b>Reference Email Id:</b></td>
									<td width="10%"><s:property  value="%{emailId}"/></td>
						 </tr>
						</s:if>
						<s:else>
						<tr bgcolor="#D8D8D8" style="height: 25px">
									<td width="10%"><b>Client Name :</b></td>
									<td width="10%"><s:property  value="%{clientname}"/></td>
									<td width="10%"><b>Source Name :</b> </td>
									<td width="10%"><s:property  value="%{sourceMaster}"/></td>
						</tr>
						<tr style="height: 23px">
									<td width="10%"><b>Refered By :</b></td>
									<td width="10%"><s:property  value="%{referedBy}"/></td>
									<td width="10%"><b>Reference Name :</b></td>
									<td width="10%"><s:property  value="%{refName}"/></td>
						</tr>
					 </s:else>
						 
				</s:iterator>
				</table>
</body>
</html>