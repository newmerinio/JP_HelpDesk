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
			<th>Offering</th><th>Count</th>
		</tr>
		<s:iterator value="offWrtAssoMap">
			<tr>
				<td><s:property value="%{key}"/> </td><td align="center"><s:property value="%{value}"/></td>
			</tr>
		</s:iterator>	
	</table>
</body>
</html>