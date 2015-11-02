<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table height="210px" width="100%" align="center" border="1">
	<tr>
		<th width="30%" bgcolor="#8A0886" >
			<font color="white">Action&nbsp;Name</font>
		</th>
		<th width="30%" bgcolor="#8A0886" >
			<font color="white">Counter</font>
		</th>
	</tr>
	<s:iterator value="feedDataDashboardList">
	<tr>
		<td align="center" bgcolor="#E6E6FA" class="sortable" width="30%">
			<b><s:property value="%{subDept}"/></b>
		</td>
		<td align="center" bgcolor="#E6E6FA" class="sortable" width="30%">
			<b><s:property value="%{actionCounter}"/></b>
		</td>
	</tr>
	</s:iterator>
</table>
</body>
</html>