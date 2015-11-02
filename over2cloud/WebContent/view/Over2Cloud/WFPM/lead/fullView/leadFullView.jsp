<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="container_block">
<div style="float: left; padding: 20px 1%; width: 98%;">
<div class="form_inner" id="form_reg" style="margin-top: 10px;">
	<s:if test="%{leadBasicFullViewMap.size > 0}">
	<table width="100%">
	<tr style="background-color: #E0E0E0"><td colspan="4" align="left"><STRONG>Basic Details</STRONG> </tr>
	<s:iterator value="leadBasicFullViewMap" status="status">
		<s:if test="#status.odd">
			<tr  style="background-color: #F8F8F8">
		</s:if>
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		<s:if test="#status.even">
		</s:if>
	</s:iterator>
	</table>
	<br>
	<s:if test="%{leadContactFullViewMap.size > 0}">
	<table width="100%">
	<tr style="background-color: #E0E0E0"><td colspan="4" align="left"><STRONG>Contact Details</STRONG> </tr>
	<s:iterator value="leadContactFullViewMap" status="status">
		<s:if test="#status.odd">
			<tr  style="background-color: #F8F8F8">
		</s:if>
			<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
			<td align="left" width="25%"><s:property value="%{value}"/></td>
		<s:if test="#status.even">
		</s:if>
	</s:iterator>
	</table>	
	</s:if>
	</s:if>	
	<s:else>
		No Data
	</s:else>
</div>
</div>
</div>
</body>
</html>