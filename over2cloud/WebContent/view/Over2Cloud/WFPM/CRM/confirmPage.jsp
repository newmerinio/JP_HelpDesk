<%@taglib prefix="s" uri="/struts-tags"%>

	<s:hidden id="entityTypeNEW" name="entityType" value="%{entityType}"></s:hidden>
	<s:hidden id="employeeNEW" name="employee" value="%{employee}"></s:hidden>
	<s:hidden id="mobileNoNEW" name="mobileNo" value="%{mobileNo}"></s:hidden>
	<s:hidden id="rootNEW" name="root" value="%{root}"></s:hidden>
	<s:hidden id="templateidNEW" name="templateid" value="%{templateid}"></s:hidden>
	<s:hidden id="frequencysNEW" name="frequencys" value="%{frequencys}"></s:hidden>
	<s:hidden id="writeMessageNEW" name="writeMessage" value="%{writeMessage}"></s:hidden>
	<s:hidden id="dateNEW" name="date" value="%{date}"></s:hidden>
	<s:hidden id="hoursNEW" name="hours" value="%{hours}"></s:hidden>
	<s:hidden id="dayNEW" name="day" value="%{day}"></s:hidden>
	<s:hidden id="entityContactsNEW" name="entityContacts" value="%{entityContacts}"></s:hidden>

<table cellspacing="0" cellpadding="3" style="margin: auto; width: 100%;"> 
	<tr>
		<td bgcolor="#e7e9e9"
			style="border-bottom: 1px solid #e7e9e9; color: #000000;"
			valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%" >
			<tbody>
				<tr>
					<td width="10%" align="center"><b>Sr.</b></td>
					<td valign="middle" width="50%" align="center"><b>Mobile No.</b></td>
					<td valign="middle" width="40%" align="center"><b>Name</b></td>
				</tr>
			</tbody>
		</table>
		</td>
	</tr>
	<tr>
	<s:iterator value="correctData" status="status">  
	<tr>
			<td bgcolor="#ffffff"
				style="border-bottom: 1px solid #e7e9e9; color: #181818;"
				valign="top" class="tabular_cont">
			<table cellspacing="0" cellpadding="0" width="100%">
				<tbody>
					<tr>
						<td valign="middle" width="10%" bgcolor="#ffffff" align="center" 
							style="color: #000000; text-align: center;">
							<s:property value="#status.count" /></td>
						<td valign="middle" align="center" width="50%">
							<s:property value="%{key}" />
						</td>
						<td valign="middle" align="center" width="40%">
							<s:property value="%{value}" />
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</s:iterator>
</table>