<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

</head>
<body>
<table width="90%" cellspacing="0" cellpadding="3">
<tr><td bgcolor="#0e0e0e" class="tabular_head" valign="middle" colspan="8"></td></tr>
	<tr><td bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="50%">View</td>
				<td valign="middle" width="50%">Modify</td>
			</tr>
			<tr>
				<td><s:property  value="%{lShare}"/></td>
				<td><s:property  value="%{lModify}"/></td>
			</tr>
		</tbody></table>
	</td>
	</tr>

</table>
</body>
</html>