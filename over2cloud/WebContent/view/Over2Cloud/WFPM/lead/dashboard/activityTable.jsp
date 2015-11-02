<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<STYLE type="text/css">

th
{
background-color:#38D3D1;
color:white;
}
td
{
	padding-left: 5px;
	height: 20px;
	font-size: 16px;
}
</STYLE>
</head>
<body>
	<table align="center" style="width: 80%;font-size: 15px;padding: 0px; border:1px solid #38D3D1">
		<tr>
			<th>Status</th><th>Count</th>
		</tr>
		
		<tr>
			<td>Missed</td><td align="center"><s:property value="missedActivity"/></td>
		</tr>
		<tr style="background-color:#E8FDFD;">
			<td>Today's</td><td align="center"><s:property value="todayActivity"/></td>
		</tr>
		<tr>
			<td>Tomorrow's</td><td align="center"><s:property value="tomorrowActivity"/></td>
		</tr>
		<tr>
			<td>Next Seven Days</td><td align="center"><s:property value="nextSevenDaysActivity"/></td>
		</tr>
	</table>
</body>
</html>