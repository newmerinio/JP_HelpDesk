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
			<th>Status</th><th>666666666666666666</th>
		</tr>
		
		<s:if test="%{statusMap != null}">
			<s:iterator value="statusMap" var="val" status="status">
				<s:if test="%{#status.odd}">
					<tr>
						<td><s:property value="%{#val[1]}"/></td>
						<td><s:property value="%{#val[2]}"/></td>
				</s:if>
				<s:else>
					<tr style="background-color:#E8FDFD;">
						<td><s:property value="%{#val[1]}"/></td>
						<td><s:property value="%{#val[2]}"/></td>
					</tr>
				</s:else>
			</s:iterator>
		</s:if>		
	</table>
</body>
</html>