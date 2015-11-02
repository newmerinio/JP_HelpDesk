<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table border="1" width="100%" align="center">
    <tr>
    <s:if test="hodFlag">
		<td align="center" width="38%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Sub Dept</b></td>
	</s:if>
	<s:elseif test="mgmtFlag">
	<td align="center" width="38%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Department</b></td>
	</s:elseif>
	<s:elseif test="normalFlag">
	<td align="center" width="38%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Sub Dept</b></td>
	</s:elseif>
		<td align="center" width="14%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;PN</b></td>
		<td align="center" width="15%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;HP</b></td>
		<td align="center" width="14%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;SN</b></td>
		<td align="center" width="19%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;RS</b></td>
	</tr>
</table>
<div style="overflow:auto; height:200px;">
<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
	<table border="1" width="100%" align="center">
	 	<tr>
	 	    <td align="left"   width="38%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="#rsCompl.deptName"/></b></td>
	 	    <td align="center" width="14%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Pending','T','dataFor','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pc"/></b></a></td>
			<td align="center" width="15%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','High Priority','T','dataFor','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.hpc"/></b></a></td>
			<td align="center" width="14%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Snooze','T','dataFor','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.sc"/></b></a></td>
			<td align="center" width="19%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Resolved','T','dataFor','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.rc"/></b></a></td>
	 	</tr>
 	</table>
</s:iterator>
</div>
</body>
