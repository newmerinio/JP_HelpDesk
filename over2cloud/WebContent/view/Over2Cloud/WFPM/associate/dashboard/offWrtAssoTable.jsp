<%@ taglib prefix="s" uri="/struts-tags" %>
<table align="center" style="width: 80%;font-size: 15px;padding: 0px; border:1px solid #000000">
	<tr>
		<th style="background-color:#C0C0C0;color:#000000;">Status</th>
		<th style="background-color:#C0C0C0;color:#000000;">Count</th>
	</tr>
	
	<s:if test="%{offWrtAssoMap != null}">
		<s:iterator value="offWrtAssoMap" var="val" status="status">
			<s:if test="%{#status.odd}">
				<tr>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{key}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{value}"/></td>
			</s:if>
			<s:else>
				<tr style="background-color:#D0D0D;">
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{key}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{value}"/></td>
				</tr>
			</s:else>
		</s:iterator>
	</s:if>		
</table>
