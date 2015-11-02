<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	border: blue;
	border-width: 12px;
}
tr th{
text-align: center;
}

.tdd {
	padding: 2px;
	margin: 1px;
	border-right: 1px solid #e7e9e9;
	background-color: #E6E6E6;
	border-bottom: 1px solid #e7e9e9;
	text-align: center;
}

.phead {
	background-color: grey;
	color: white;
	font-weight: bold;
	text-align: center;
}
.MytabHead{
			background-color: rgba(1, 34, 25, 0.13);
			text-align: center;
}
</style>

</head>
<body>
<div class="border">
	<s:hidden name="id" value="%{id}"></s:hidden>
	<center>
	<div
		style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	<div id="errZone1" style="float: center; margin-left: 7px"></div>
	</div>
	</center>
	<br>
	<table width="100%" align="center">
	<tr bgcolor="#D8D8D8"  style="height: 25px;">
		<td><b>Event Type:</b></td>
		<td><s:property value="%{epp.type}" /></td>
		<td><b>Event Title:</b></td>
		<td><s:property value="%{epp.title}" /></td>
	</tr>
	<tr style="height: 25px;">
		<td><b>Event Address:</b></td>
		<td><s:property value="%{epp.address}" /></td>
		<td><b>From Buddy:</b></td>
		<td><s:property value="%{epp.from_buddy}" /></td>
	</tr>
	<tr bgcolor="#D8D8D8"  style="height: 25px;">
		<td><b>Mapped Team:</b></td>
		<td><s:property value="%{epp.team}" /></td>
		<td><b>Mapped Offering:</b></td>
		<td><s:property value="%{epp.from_offering}" /></td>
	</tr >
</table>
	<table width="100%">
		<tr class="phead">
			<th colspan="4">Parameter</th>
		</tr>
		<tr class="MytabHead">
			<th>Sr. No</th>
			<th>Name</th>
			<th>Planned Value</th>
			<th>Actual Value</th>
		</tr>

		<s:iterator status="stat" value="%{epp.eparamList}">
			<tr>
				<td class='tdd'><s:property value="#stat.count" /></td>
				<td class='tdd'><s:property value="%{name}" /></td>
				<td class='tdd'><s:property value="%{value}" /></td>
				<td class='tdd'><s:property value="%{final}" /></td>
			</tr>
		</s:iterator>
	</table>

	<div class="clear"></div>
	<br>
	<table width="100%">
		<tr>
			<th class="phead" colspan="4">ROI</th>
		</tr>
		<tr  class="MytabHead" >
			<th >Sr. No</th>
			<th >Name</th>
			<th>Planned Value</th>
			<th >Actual Value</th>
		</tr>

		<s:iterator status="statt" value="%{epp.eroiList}">
			<tr>
				<td class='tdd'><s:property value="#statt.count" /></td>
				<td class='tdd'><s:property value="%{name}" /></td>
				<td class='tdd'><s:property value="%{value}" /></td>
				<td class='tdd'><s:property value="%{final}" /></td>
			</tr>
		</s:iterator>
	</table>
</div>
</body>
</html>