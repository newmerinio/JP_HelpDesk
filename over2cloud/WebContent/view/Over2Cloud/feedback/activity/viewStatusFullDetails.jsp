<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
tr.color
{
	background-color: #E6E6E6;
}
td.tdAlign {
	padding: 5px;
}

</style>
<script type="text/javascript"></script>
</head>
<body>
<center>

<s:property value="%{deptIndx}"/>

 <s:iterator value="statusList" >
 
 	<br>
 	<b>Status For <s:property value="ticket_no"/></b>
 	
 	<s:if test="resolveAt!='NA'">
 		<div align="Center"><b>Resolved</b></div>
 		<table border="1" width="95%" style="border-collapse: collapse;">
 			<tr class="color">
 			<td class="tdAlign"><b>Resolved At:</b></td>
 			<td class="tdAlign"><s:property value="resolveAt"/></td>
 			</tr>
 			<tr>
 			<td class="tdAlign"><b>Resolved By:</b></td>
 			<td class="tdAlign"><s:property value="resolveBy"/></td>
 			</tr>
 			<tr class="color">
 			<td class="tdAlign"><b>CAPA:</b></td>
 			<td class="tdAlign"><s:property value="capa"/></td>
 			</tr>
 			<tr>
 			<td class="tdAlign"><b>RCA:</b></td>
 			<td class="tdAlign"><s:property value="rca"/></td>
 			</tr>
 		
 		</table>
 	</s:if>
 	
 	<s:if test="ignoreAt!='NA'">
 		<div align="Center"><b>Ignore</b></div>
 		<table border="1" width="95%" style="border-collapse: collapse;">
 			<tr class="color">
 			<td class="tdAlign"><b>Ignore At:</b></td>
 			<td class="tdAlign"><s:property value="ignoreAt"/></td>
 			</tr>
 			<tr>
 			<td class="tdAlign"><b>Ignore Reason:</b></td>
 			<td class="tdAlign"><s:property value="ignoreReason"/></td>
 			</tr>
 		
 		</table>
 	</s:if>
 
 	<s:if test="highPriorityAt!='NA'">
 		<div align="Center"><b>High Priority</b></div>
 		<table border="1" width="95%" style="border-collapse: collapse;">
 			<tr class="color">
 			<td class="tdAlign"><b>High Priority At:</b></td>
 			<td class="tdAlign"><s:property value="highPriorityAt"/></td>
 			</tr>
 			<tr>
 			<td class="tdAlign"><b>High Priority By:</b></td>
 			<td class="tdAlign"><s:property value="highPriorityBy"/></td>
 			</tr>
 			<tr class="color">
 			<td class="tdAlign"><b>High Priority Mode:</b></td>
 			<td class="tdAlign"><s:property value="highPriorityActionMode"/></td>
 			</tr>
 			<tr>
 			<td class="tdAlign"><b>High Priority Reason:</b></td>
 			<td class="tdAlign"><s:property value="highPriorityReason"/></td>
 			</tr>
 		
 		</table>
 	</s:if>
 
 	<s:if test="snoozeAt!='NA'">
 		<div align="Center"><b>Snooze</b></div>
 		<table border="1" width="95%" style="border-collapse: collapse;">
 			<tr class="color">
 			<td class="tdAlign"><b>Snooze At:</b></td>
 			<td class="tdAlign"><s:property value="snoozeAt"/></td>
 			</tr>
 			<tr>
 			<td class="tdAlign"><b>Snooze Upto:</b></td>
 			<td class="tdAlign"><s:property value="snoozeUpto"/></td>
 			</tr>
 			<tr class="color">
 			<td class="tdAlign"><b>Duration:</b></td>
 			<td class="tdAlign"><s:property value="duration"/></td>
 			</tr>
 			<tr>
 			<td class="tdAlign"><b>Snooze By:</b></td>
 			<td class="tdAlign"><s:property value="snoozeBy"/></td>
 			</tr>
 			<tr class="color">
 			<td class="tdAlign"><b>Snooze Reason:</b></td>
 			<td class="tdAlign"><s:property value="snoozeReason"/></td>
 			</tr>
 		
 		</table>
 	</s:if>
 
 	<s:if test="dept!='NA'">
 		<div align="Center"><b>Pending</b></div>
 		<table border="1" width="95%" style="border-collapse: collapse;">
 		
 			<tr class="color">
 			 <s:if test="ticket_no.length() >5">
 					<td class="tdAlign"><b>Re-Opened By:</b></td>
			 </s:if>
			 <s:else>
					<td class="tdAlign"><b>Opened By:</b></td>
			 </s:else>
 			<td class="tdAlign"><s:property value="feedRegBy"/></td>
 			</tr>
 			<tr >
 			<td class="tdAlign"><b>Opened For:</b></td>
 			<td class="tdAlign"><s:property value="dept"/></td>
 			</tr>
 			<tr class="color">
 			<td class="tdAlign"><b>Date of Entry:</b></td>
 			<td class="tdAlign"><s:property value="dateTime"/></td>
 			</tr>
 			<tr >
 			<td class="tdAlign"><b>Category:</b></td>
 			<td class="tdAlign"><s:property value="cat"/></td>
 			</tr>
 			<tr class="color">
 			<td class="tdAlign"><b>Sub Category:</b></td>
 			<td class="tdAlign"><s:property value="subCat"/></td>
 			</tr>
 			<tr>
 			<td class="tdAlign"><b>Brief:</b></td>
 			<td class="tdAlign"><s:property value="brief"/></td>
 			</tr>
 		
 		</table>
 	</s:if>
 </s:iterator> 
<br>
<br>
<br>
<br>
<br>
<s:if test="statusList.size()==0">
<center><b><i><font color="#D8D8D8" size="5">There is no data</font></i></b></center>
</s:if>
</center>
</body>
</html>