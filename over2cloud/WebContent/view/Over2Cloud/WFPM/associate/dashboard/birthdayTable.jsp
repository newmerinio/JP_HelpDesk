<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="%{birthdayList != null}">
<table align="center" style="width: 80%;font-size: 15px;padding: 0px; border:1px solid #38D3D1">
	<tr>
		<th style="background-color:#C0C0C0;color:#000000;">Associate</th>
		<th style="background-color:#C0C0C0;color:#000000;">Cont. Name</th><th>Date</th>
	</tr>
	<s:iterator value="birthdayList" var="val" status="status">
		<s:if test="%{currentDate == #val[2]}">
			<s:if test="%{#status.odd}">
				<tr>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[0]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[1]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[2]}"/></td>
				</tr>
			</s:if>
			<s:else>
				<tr style="background-color:#E8FDFD;">
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[0]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[1]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	"><s:property value="%{#val[2]}"/></td>
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
				<tr style="background-color:#E8FDFD;">
					<td style="padding-left: 5px;	height: 20px;	font-size: 16px;"><s:property value="%{#val[0]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	font-size: 16px;"><s:property value="%{#val[1]}"/></td>
					<td style="padding-left: 5px;	height: 20px;	font-size: 16px;"><s:property value="%{#val[2]}"/></td>
				</tr>
			</s:else>
		</s:else>
	</s:iterator>
</table>
</s:if>
<s:else>
		<center>No Birthday This Week.</center> 
</s:else>
