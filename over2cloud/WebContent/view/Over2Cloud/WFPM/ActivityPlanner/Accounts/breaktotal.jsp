<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1" width="100%" align="center">
		<tbody>
		
		 <tr bgcolor="#7D89E3">
			<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Month</b></font></td>
			<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Expenses</b></font></td>
			<td  align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Value</b></font></td>
		 </tr>
		
		<s:iterator id="totalCompl"  status="status" value="%{test}" >
		 <tr bgcolor="#BABFE0">
			<td align="center" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#totalCompl.Month" /></a></font></td>
			
			<td align="center" width="20%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.exp"/></a></font></td>
			
			<td align="center" width="20%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="1"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#totalCompl.val"/></a></font></td>
			
		 </tr>
		</s:iterator>
		</tbody>
	</table>
</body>
</html>