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

td.poor
{
	color: #FF0000;
}
td.average
{
	color: #FFCC00;
}
td.good
{
	color: #66FF66;
}

td.vgood
{
	color: #00CC00;
}

td.excellent
{
	color: #006600;
}
tr.color
{
	background-color: #E6E6E6;
}
</STYLE>
</head>
<body>
		<table border='1' bordercolor="lightgray" cellpadding="10px" rules="rows" width="80%" align="center">
			<tr class="color">
				<td class="tdAlign">
					<b>MRD&nbsp;No:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getClientId()}" />
				</td>
				<td class="tdAlign">
					<b>Patient&nbsp;Name:</b>
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
				<td class="tdAlign">
					<b>Email&nbsp;ID:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getEmailId()}" />
				</td>
			</tr>
			<tr class="color">
				<td class="tdAlign">
					<b>Room&nbsp;No:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getCenterCode()}" />
				</td>
				<td class="tdAlign">
					<b>Patient&nbsp;Type:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getCompType()}" />
				</td>
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Speciality:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getCentreName()}" />
				</td>
				<td class="tdAlign">
					<b>Treating Doctor:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getDocName()}" />
				</td>
			</tr>
			<tr  class="color">
				<td class="tdAlign">
					<b>Visit Date:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getAddmissionDate()}" />
				</td>
				<td class="tdAlign">
					<b>Discharged On:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getDischargeDateTime()}" />
				</td>
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Company Type:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{pojo.getCompanytype()}" />
				</td>
				<td class="tdAlign">
				</td>
				<td class="tdAlign">	
				</td>
			</tr>
		</table>
</body>
</html>