<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Current Stages View</title>
</head>
<body>

		<table border="1" width="100%" style="border-collapse: collapse;">
				
							<tr bgcolor="#D8D8D8" style="height: 25px">
							 <td width="10%" align="center"><b>Current Stages</b></td>
										
							</tr>
							<s:iterator value="allsalesstages" status="counter">
							<s:if test="#counter.odd == true">
								<tr> 
									<td width="10%" align="center"><s:property value="%{value}"/> </td>
								</tr>
							</s:if>
							<s:else>
								<tr bgcolor="#D8D8D8"> 
									<td width="10%" align="center"><s:property value="%{value}"/> </td>
								</tr>
							</s:else>
								
							</s:iterator>
				
		</table>
</body>
</html>