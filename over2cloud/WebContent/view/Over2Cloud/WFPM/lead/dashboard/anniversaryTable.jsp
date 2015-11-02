<%@ taglib prefix="s" uri="/struts-tags" %>
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
	<s:if test="%{anniversaryList.size != null}">
	<table align="center" style="width: 80%;font-size: 15px;padding: 0px; border:1px solid #000000">
		<tr>
			<th>Associate</th><th>Cont. Name</th><th>Date</th>
		</tr>
		<s:iterator value="anniversaryList" var="val" status="status">
			<s:if test="%{currentDate == #val[2]}">
				<s:if test="%{#status.odd}">
					<tr>
						<td><s:property value="%{#val[0]}"/></td>
						<td><s:property value="%{#val[1]}"/></td>
						<td><s:property value="%{#val[2]}"/></td>
					</tr>
				</s:if>
				<s:else>
					<tr style="background-color:#D0D0D0;">
						<td><s:property value="%{#val[0]}"/></td>
						<td><s:property value="%{#val[1]}"/></td>
						<td><s:property value="%{#val[2]}"/></td>
					</tr>
				</s:else>
			</s:if>
			<s:else>
				<s:if test="%{#status.odd}">
					<tr>
						<td><s:property value="%{#val[0]}"/></td>
						<td><s:property value="%{#val[1]}"/></td>
						<td><s:property value="%{#val[2]}"/></td>
					</tr>
				</s:if>
				<s:else>
					<tr style="background-color:#D0D0D0;">
						<td><s:property value="%{#val[0]}"/></td>
						<td><s:property value="%{#val[1]}"/></td>
						<td><s:property value="%{#val[2]}"/></td>
					</tr>
				</s:else>
			</s:else>
		</s:iterator>
	</table>
	</s:if>
	<s:else>
			<center>No Anniversary This Week.</center> 
	</s:else>
