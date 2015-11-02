<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/WFPM/commonDashboard/tableStyle.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<table style="margin:0px;padding:0px; width:98%;">
<s:if test="%{offeringWeightageDetailsZeroval.size() < 1}">
	<table style="margin:0px;padding:0px; width:98%;">
	<tr bgcolor="#D8D8D8">
		<td style="padding:2px;margin:1px;border:1px solid #ccc;text-align:center;" colspan="2">NA</td>
	</tr>
	</table>
</s:if>
<s:else>
<s:iterator value="offeringWeightageDetailsZeroval" var="var">
 	 <s:iterator value="offeringWeightageDetailsVal" var="var2" /> 
	
 	<tr><td style="padding:2px;margin:1px;border:1px solid #ccc;text-align:center; background-color: #D8D8D8" colspan="2"><s:property value="%{#var.key}"/></td></tr>
	<s:if test="%{#var.value.size() < 1}">
		<tr bgcolor="#D8D8D8">
			<td style="padding:2px;margin:1px;border:1px solid #ccc;text-align:center;" colspan="2">NA</td>
		</tr>
	</s:if>
	 <s:iterator value="%{#var.value}">
	 <tr>
      <td style="padding:2px;margin:1px;border:1px solid #ccc;text-align:left;"><s:property value="%{key}"/></td>
	  <td style="padding:2px;margin:1px;border:1px solid #ccc;text-align:left;"><s:property value="%{value}"/></td>

 	</tr>
 	</s:iterator>
 	<s:iterator value="%{#var2.value}">
	<tr>
        <td style="padding:2px;margin:1px;border:1px solid #ccc;text-align:left;"><s:property value="%{key}"/></td>
		<td style="padding:2px;margin:1px;border:1px solid #ccc;text-align:left;"><s:property value="%{value}"/></td>

 	</tr>
 	</s:iterator>
 	
 	
</s:iterator>

</s:else>
</table>

</body>
</html>