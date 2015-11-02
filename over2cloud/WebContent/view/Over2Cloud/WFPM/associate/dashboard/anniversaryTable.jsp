<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="%{anniversaryList.size != null}">
<table align="center" style="width: 80%;font-size: 15px;padding: 0px; border:1px solid #000000">
	<tr>
		<th style="background-color:#C0C0C0;color:#000000;">Associate</th>
		<th style="background-color:#C0C0C0;color:#000000;">Cont. Name</th>
		<th style="background-color:#C0C0C0;color:#000000;">Date</th>
	</tr>
	<s:iterator value="anniversaryList" var="val" status="status">
		<s:if test="%{currentDate == #val[2]}">
			<s:if test="%{#status.odd}">
				<tr>
					<td style="padding-left: 5px;	height: 20px;	"><B><s:property value="%{#val[0]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><B><s:property value="%{#val[1]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><B><s:property value="%{#val[2]}"/></td>
				</tr>
			</s:if>
			<s:else>
				<tr style="background-color:#D0D0D0">
					<td style="padding-left: 5px;	height: 20px;	"><B><s:property value="%{#val[0]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><B><s:property value="%{#val[1]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><B><s:property value="%{#val[2]}"/></td>
				</tr>
			</s:else>
		</s:if>
		<s:else>
			<s:if test="%{#status.odd}">
				<tr>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[0]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[1]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[2]}"/></td>
				</tr>
			</s:if>
			<s:else>
				<tr style="background-color:#D0D0D0">
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[0]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[1]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[2]}"/></td>
				</tr>
			</s:else>
		</s:else>
	</s:iterator>
</table>
</s:if>
<s:else>
		<center>No Anniversary This Week.</center> 
</s:else>
