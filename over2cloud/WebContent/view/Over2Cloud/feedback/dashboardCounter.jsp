<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table width="100%" border="1">
		 <tr bgcolor="#BF8AEF">
			<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Department</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Pending</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 1</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 2</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 3</b></font></td>
		    <td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 4</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 5</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Snooze</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>High&nbsp;Priority</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Ignore</b></font></td>
      		<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Re-Assign</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Resolved</b></font></td>
     	    <td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Total</b></font></td>
       </tr>
	
		<s:iterator  id="first" status="status" value="%{dashPojoList}">
		<tr align="center" class="tableData" bgcolor="#EDDFFB">
			<td align="left" width="">
				<b><s:property value="%{deptName}"/></b>
			</td>
			<td>
				<sj:a onClickTopics="showCounterData"></sj:a><b><s:property value="%{pending}"/></b>
			</td>
			<td >
				<b><s:property value="%{pendingL1}"/></b>
			</td>
			<td >
				<b><s:property value="%{pendingL2}"/></b>
			</td>
			<td >
				<b><s:property value="%{pendingL3}"/></b>
			</td>
			<td >
				<b><s:property value="%{pendingL4}"/></b>
			</td>
			<td >
				<b><s:property value="%{pendingL5}"/></b>
			</td>
			<td >
				<b><s:property value="%{sz}"/></b>
			</td>
			<td >
				<b><s:property value="%{hp}"/></b>
			</td>
			<td >
				<b><s:property value="%{ig}"/></b>
			</td>
			<td >
				<b><s:property value="%{rAs}"/></b>
			</td>
			<td >
				<b><s:property value="%{resolved}"/></b>
			</td>
			<td >
				<b><s:property value="%{totalCounter}"/></b>
			</td>
		</tr>
		</s:iterator>
	<tr class="tableData" bgcolor="#EDDFFB">
	<td align="left" width="15%" height="40%"><b>Total</b> </td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalPending"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalLevel1"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalLevel2"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalLevel3"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalLevel4"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalLevel5"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalSnooze"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalHighPriority"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalIgnore"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalReAssign"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalResolve"/></b></td>
		<td align="center" width="5%" height="40%" ><b><s:property value="totalCounter"/></b></td>
 </tr>
	</table>
</body>
</html>