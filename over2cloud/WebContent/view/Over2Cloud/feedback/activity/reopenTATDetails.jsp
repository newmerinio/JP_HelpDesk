<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<STYLE type="text/css">

	td.tdAlign {
	padding: 5px;
	padding-left: 20px;
}
tr.color
{
	background-color: #E6E6E6;
}
</STYLE>
</head>
<body>
<br>
<s:iterator value="dataMapTAT" status="status">
		
		<s:iterator value="%{value}">
				<s:if test="key=='ticket_no'">
					<table align="center">
						<tr>
							<td width="25%" align="center"><b>TAT For <s:property value="%{value}"/></b></td>
						</tr>
					</table>
				</s:if>
		</s:iterator>
				<table border="1" width="100%" style="border-collapse: collapse;">
					
					<tr bgcolor="#D8D8D8" style="height: 25px">
						<s:iterator value="%{value}" status="status">
							<s:if test="key=='Status' || key=='Level'">
					 			<td width="25%"><b><s:property value="%{key}"/>:</b></td>
								<td width="25%"><s:property value="%{value}"/></td>
						 	</s:if>
					 	</s:iterator>
					</tr>
				</table>
				<table border="1" width="100%" style="border-collapse: collapse;">	 	
					<tr style="height: 25px" >
						<s:iterator value="%{value}" status="status">
							<s:if test="key=='Open Date & Time'">
						 		<td width="25%"  ><b><s:property value="%{key}"/>:</b></td>
								<td width="25%" colspan="3"><s:property value="%{value}"/></td>
						 	</s:if>
					  	</s:iterator>
					</tr>
				</table>	 	
					 
				<table border="1" width="100%" style="border-collapse: collapse;">	 
					<tr bgcolor="#D8D8D8" style="height: 25px">
						<s:iterator value="%{value}" status="status">
							<s:if test="key=='Addressing Date & Time' || key=='Resolution Date & Time'">
						 		<td width="25%"><b><s:property value="%{key}"/>:</b></td>
								<td width="25%"><s:property value="%{value}"/></td>
						 	</s:if>
					  </s:iterator>
					</tr>
				</table>	 	
					 	
				<table border="1" width="100%" style="border-collapse: collapse;">	 	
					 <tr style="height: 25px">
						<s:iterator value="%{value}" status="status">
							<s:if test="key=='L-2 Escalation Date & Time' ||key=='L-2 Escaltion To'">
						 		<td width="25%"><b><s:property value="%{key}"/>:</b></td>
								<td width="25%"><s:property value="%{value}"/></td>
						 	</s:if>
					  </s:iterator>
					</tr>
				</table>
				
				<table border="1" width="100%" style="border-collapse: collapse;">	 	
					<tr bgcolor="#D8D8D8" style="height: 25px">
						<s:iterator value="%{value}" status="status">
							<s:if test="key=='L-3 Escalation Date & Time' || key=='L-3 Escaltion To'">
						 		<td width="25%"><b><s:property value="%{key}"/>:</b></td>
								<td width="25%"><s:property value="%{value}"/></td>
							</s:if>
					 
						</s:iterator>
					</tr>
				</table>	
</s:iterator> 
</body>
</html>