<%@ taglib uri="/struts-tags" prefix="s" %>
<table style="margin-left:16px; padding:0px; width:97.8%;">
<s:iterator value="missedActivityList" status="status" var="var">
<tr>
	<td style="border-bottom:1px solid #e7e9e9; padding:2px;margin:1px;text-align:center; width: 3%;"><s:property value="#status.count"/></td>
	<td style="border-bottom:1px solid #e7e9e9; padding:2px;margin:1px;text-align:center; width: 40%;"><s:property value="%{#var[1]}"/></td>
	<td style="border-bottom:1px solid #e7e9e9; padding:2px;margin:1px;text-align:center; width: 40%;"><s:property value="%{#var[2]}"/></td>
	<td style="border-bottom:1px solid #e7e9e9; padding:2px;margin:1px;text-align:center; width: 7%;"><s:property value="%{#var[3]}"/></td>
	<td style="border-bottom:1px solid #e7e9e9; padding:2px;margin:1px;text-align:center; width: 10%;"><s:property value="%{#var[4]}"/></td>
</tr>
</s:iterator>
</table>
