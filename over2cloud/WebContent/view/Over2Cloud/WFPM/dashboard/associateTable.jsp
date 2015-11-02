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
			<td>Prospective Associate</td><td align="center"><s:property value="pendingAssociate"/></td>
		</tr>
		<tr style="background-color:#E8FDFD;">
			<td>Existing Associate</td><td align="center"><s:property value="existingAssociate"/></td>
		</tr>
		<tr>
			<td>Lost Associate</td><td align="center"><s:property value="lostAssociate"/></td>
		</tr>
	</table>
</body>
</html>