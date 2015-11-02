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

	<table border="1" width="95%" style="border-collapse: collapse;">
	<s:if test="%{statusList.size<=2}">
		<tr class="color">
 			<td class="tdAlign"><b>Reason:</b></td>
 			<td class="tdAlign"><s:property value="%{pojo.getReason()}" /></td>
 		</tr>
 	</s:if>	
 		<tr >
 			<td class="tdAlign"><b>Patient Name:</b></td>
 			<td class="tdAlign"><s:property value="%{pojo.getFeed_by()}" /></td>
 		</tr>
 		<tr class="color">
 			<td class="tdAlign"><b>Mobile No:</b></td>
 			<td class="tdAlign"><s:property value="%{pojo.getFeed_by_mob()}" /></td>
 		</tr>
 			
 	</table>
 <s:iterator value="statusList" >
 	<br>
 	<s:if test="%{statusList.size>2}">
 		<table border="1" width="95%" style="border-collapse: collapse;">
			<tr class="color">
 				<td class="tdAlign"><b>Reason:</b></td>
 				<td class="tdAlign"><s:property value="reason" /></td>
 			</tr>
 		</table>
 	</s:if>
	<b>Stage- <s:property value="stage"/> For <s:property value="dept"/>, Ticket ID: <s:property value="ticket_no"/></b>
 	
 	<s:if test="dept!='NA'">
 	<br>
 		<table border="1" width="95%" style="border-collapse: collapse;">
 		<tr class="color">
 			<s:if test="ticket_no.length() >5">
	 			<td class="tdAlign"><b>Re-Opened On:</b></td>
	 		</s:if>	
	 		<s:else>
				<td class="tdAlign"><b>Opened On:</b></td>
			</s:else>
	 			<td class="tdAlign"><s:property value="dateTime"/></td>
	 		</tr>
 			<tr >
 			<td class="tdAlign"><b>Status:</b></td>
 			<td class="tdAlign"><s:property value="fstatus"/></td>
 			</tr>
 			<tr class="color">
 			<s:if test="ticket_no.length() >5">
 					<td class="tdAlign"><b>Re-Opened For:</b></td>
			</s:if>
			<s:else>
					<td class="tdAlign"><b>Opened For:</b></td>
			</s:else>
 			<td class="tdAlign"><s:property value="allot_to"/></td>
 			</tr>
 			<tr >
	 			<td class="tdAlign"><b>Mobile No:</b></td>
 				<td class="tdAlign"><s:property value="mobNo"/></td>
 			</tr>
	 		<tr class="color">
	 			<td class="tdAlign"><b>Comments:</b></td>
	 			<td class="tdAlign"><s:property value="comments"/></td>
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