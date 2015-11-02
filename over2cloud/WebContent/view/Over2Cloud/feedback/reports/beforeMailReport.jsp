<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center"><b>Report Of </b> 
				<sj:datepicker id="startDate" cssClass="button"
						cssStyle="width: 80px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;"
						readonly="true" size="20" changeMonth="true" changeYear="true"
						maxDate="0"  showOn="focus" displayFormat="dd-mm-yy"
						Placeholder="Select Date" onchange="getMailReport(this.value)"
						value="%{startDate}"> </sj:datepicker>
	</div>					
	<br>
	<table  border='1' style="background: black" cellpadding='4' cellspacing='1'  width='100%' align='left' >
		<tbody>
			<tr  bgcolor='#848482'><td colspan='7' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Feedback Summary Status for All Departments by All Modes</b></td></tr>		
			<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>By&nbsp;SMS&nbsp;IPD&nbsp;Mode</b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total&nbsp;Sent</b></td><td align='center' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getSt()}"/></b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Positive</b></td><td align='center' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getSnr()}"/></b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Negative</b></td><td align='center' width='6%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getSn()}"/></b></td>
			</tr>
			<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>By&nbsp;SMS&nbsp;OPD&nbsp;Mode</b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total&nbsp;Sent</b></td><td align='center' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getSto()}"/></b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Positive</b></td><td align='center' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getSpos()}"/></b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Negative</b></td><td align='center' width='6%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getSneg()}"/></b></td>
			</tr>
			<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>By&nbsp;Paper&nbsp;Mode</b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total&nbsp;Received</b></td><td align='center' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getPt()}"/></b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Positive</b></td><td align='center' width='7%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getPp()}"/></b></td>
				<td align='left' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Negative</b></td><td align='center' width='6%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{FRP4Counter.getPn()}"/></b></td>
			</tr>
		</tbody>
	</table>
	<br>
	<br>
	<table border='1' style="background: black" cellpadding='4' cellspacing='1'  width='100%' align='left'>
	
		<tbody>
			<tr  bgcolor='#848482'><td colspan='6' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Feedback Summary Status for All Departments by All Modes</b></td></tr>
			<tr  bgcolor='#B6B6B4'><td align='center'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Department</b></td><td align='center'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Pending</b></td><td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Total</b></td><td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Pending</b></td><td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total Snooze</b></td><td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Current Day Resolved</b></td></tr>
			<s:iterator value="%{FRPList}" var="FRPObj">
				<tr  bgcolor='#e8e7e8'><td align='left'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="#FRPObj.deptName"/></b></td>
					<td align='center'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FRPObj.CFP"/></td>
					<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FRPObj.CDT"/></td>
					<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FRPObj.CDP"/></td>
					<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FRPObj.TS"/></td>
					<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FRPObj.CDR"/></td>
				</tr>
			</s:iterator>	
				<tr  bgcolor='#e8e7e8'><td align='center'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total</b></td>
					<td align='center'  width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{cfp_Total}"/></b></td>
					<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{cd_Total}"/></b></td>
					<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{cd_Pending}"/></b></td>
					<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{total_snooze}"/></b></td>
					<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b><s:property value="%{CDR_Total}"/></b></td>
				</tr>
		</tbody>
	</table>	

	<s:if test="%{currentDayResolvedData.size > 0}">
	<br>
	<br><br><br><br><br>
	<table border='1' style="background: black" cellpadding='4' cellspacing='1'  width='100%' align='left'>
		<tbody>
			<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved Patient Feedback Summary For All</b></td></tr>
	 		<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>
	 		<s:iterator value="%{currentDayResolvedData}" var="FBP" status="status">
	 		<!-- " + FBP.getOpen_time().substring(0, 5) + " -->
	 		<s:if test="#status.odd">
				<tr  bgcolor='#ffffff'>
			</s:if>
			<s:else>
				<tr  bgcolor='#e8e7e8'>
			</s:else>	
	 				<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.cr_no"/></td>
	 				<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.feed_by"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.patMobNo"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.ticket_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.via_from"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.open_date"/></td>
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.feedback_catg"/></td>
					<td align='left' width='25%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.feed_brief"/></td>
					<td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.feedback_to_dept"/></td> 
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.feedback_allot_to"/></td>  
					<td align='left' width='10%'  bgcolor='#728C00'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP.status"/></td>
				</tr>
	 		</s:iterator>
	 	</tbody>
	</table>
	</s:if>

	<s:if test="%{currentDayPendingData.size > 0}">
	<br><br><br><br><br>
	<br>
	<table border='1' style="background: black" cellpadding='4' cellspacing='1'  width='100%' align='left'>
		<tbody>
			<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Pending Tickets Patient Feedback Summary For All</b></td></tr>
			<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>
			<s:iterator value="%{currentDayPendingData}" var="FBP1" status="status">
			<s:if test="#status.odd">
				<tr  bgcolor='#ffffff'>
			</s:if>
			<s:else>
				<tr  bgcolor='#e8e7e8'>
			</s:else>	
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.cr_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_by"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.patMobNo"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.ticket_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.via_from"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.open_date"/></td>
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_catg"/></td>
					<td align='left' width='25%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_brief"/></td>
					<td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_to_dept"/></td> 
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_allot_to"/></td>
					<td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.status"/></td></tr>	
			</s:iterator>
		</tbody>
	</table>
	</s:if>

	<s:if test="%{currentDaySnoozeData.size > 0}">
	<br>
	<br><br><br><br><br>
	<table border='1' style="background: black" cellpadding='4' cellspacing='1'  width='100%' align='left'>
		<tbody>
			<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze Tickets Patient Feedback Summary For All</b></td></tr>
			<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>
			<s:iterator value="%{currentDaySnoozeData}" var="FBP1" status="status">
				<s:if test="#status.odd">
				<tr  bgcolor='#ffffff'>
			</s:if>
			<s:else>
				<tr  bgcolor='#e8e7e8'>
			</s:else>	
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.cr_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_by"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.patMobNo"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.ticket_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.via_from"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.open_date"/></td>
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_catg"/></td>
					<td align='left' width='25%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_brief"/></td>
					<td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_to_dept"/></td> 
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_allot_to"/></td>
					<td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.status"/></td></tr>	
			</s:iterator>
		</tbody>
	</table>
	</s:if>
	
	<s:if test="%{currentDayHPData.size > 0}">
	<br><br><br><br><br><br>
	<table border='1' style="background: black" cellpadding='4' cellspacing='1'  width='100%' align='left'>
		<tbody>
			<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority Tickets Patient Feedback Summary For All</b></td></tr>
			<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>
			<s:iterator value="%{currentDayHPData}" var="FBP1" status="">
			<s:if test="#status.odd">
				<tr  bgcolor='#ffffff'>
			</s:if>
			<s:else>
				<tr  bgcolor='#e8e7e8'>
			</s:else>	
					 <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.cr_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_by"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.patMobNo"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.ticket_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.via_from"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.open_date"/></td>
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_catg"/></td>
					<td align='left' width='25%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_brief"/></td>
					<td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_to_dept"/></td> 
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_allot_to"/></td>
					<td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.status"/></td></tr>	
			</s:iterator>
		</tbody>
	</table>	
	</s:if>
	
	<s:if test="%{currentDayIgData.size > 0}">
	<br><br><br><br><br><br>
	<table border='1' style="background: black" cellpadding='4' cellspacing='1'  width='100%' align='left'>
		<tbody>
			<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore Tickets Patient Feedback Summary For All</b></td></tr>
			<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>
			<s:iterator value="%{currentDayIgData}" var="FBP1" status="status">
				<s:if test="#status.odd">
				<tr  bgcolor='#ffffff'>
			</s:if>
			<s:else>
				<tr  bgcolor='#e8e7e8'>
			</s:else>	
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.cr_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_by"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.patMobNo"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.ticket_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.via_from"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.open_date"/></td>
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_catg"/></td>
					<td align='left' width='25%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_brief"/></td>
					<td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_to_dept"/></td> 
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_allot_to"/></td>
					<td align='left' width='10%' bgcolor='#E4287C' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.status"/></td></tr>	
			</s:iterator>
		</tbody>
	</table>
	</s:if>
	
	<s:if test="%{CFData.size > 0}">
	<br><br><br><br><br><br>
	<table border='1' style="background: black" cellpadding='4' cellspacing='1'  width='100%' align='left' >
		<tbody>
			<tr  bgcolor='#848482'><td colspan='11' align='left'  style=' color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Carry Forward Tickets Patient Feedback Summary For All</b></td></tr>
			<tr  bgcolor='#B6B6B4'><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>MRD&nbsp;No</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Name</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Patient&nbsp;Mob&nbsp;No.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mode</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Brief</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Dept.</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr>
			<s:iterator value="%{CFData}" var="FBP1" status="status">
			<s:if test="#status.odd">
				<tr  bgcolor='#ffffff'>
			</s:if>
			<s:else>
				<tr  bgcolor='#e8e7e8'>
			</s:else>	
					<td align='center' width='10%' style=' color:#111111; font-size:11px; border-color: black ;font-family:Verdana, Arial, Helvetica, sans-serif;border: 1px'><s:property value="#FBP1.cr_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; border-color: black ; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_by"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.patMobNo"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.ticket_no"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.via_from"/></td>
					<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.open_date"/></td>
					<td align='left' width='10%' style=' color:#111111; border-color: black ; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_catg"/></td>
					<td align='left' width='25%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feed_brief"/></td>
					<td align='left' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_to_dept"/></td> 
					<td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.feedback_allot_to"/></td>
					<td align='left' width='10%' bgcolor='#E41B17' style='  color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><s:property value="#FBP1.status"/></td></tr>	
			</s:iterator>
		</tbody>
	</table>
	</s:if>
		
</body>
</html>