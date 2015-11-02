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
		<table border='1' bordercolor="lightgray" cellpadding="10px" rules="rows" width="100%" align="center">
			<tr class="color">
				
				<td class="tdAlign">
					<b>Name:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getClientName()}" />
				</td>
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Mobile&nbsp;No:</b>
				</td>
				<td class="tdAlign">
				
					<s:property value="%{pojo.getMobNo()}" />
				</td>
				
			</tr>
			<tr class="color">
				<td class="tdAlign">
					<b>Email&nbsp;ID:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getEmailId()}" />
				</td>
				
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Department:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getDept()}" />
				</td>
				
			
		</table>
</body>
</html>