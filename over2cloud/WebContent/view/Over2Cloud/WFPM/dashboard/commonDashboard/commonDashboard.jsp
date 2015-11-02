<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	var monthCounter = '${currDate}';
</script>
<script type="text/javascript" src="js/WFPM/dashboard/commonDashboard.js"></script>
</head>
<body>
<sj:dialog
	id="contactPersonDetailsDialog"
	autoOpen="false"
	modal="true"
></sj:dialog>

<center>
<br>
<div style="width: 99%;">
	<table width="97%" style="margin:0px;padding:0px; ">
		<tr style="font-weight: bolder;">
			<td colspan="8"  style="margin:1px;text-align:center;">
			<div style="margin-left: 40%; float: left;">
				<div style="float: left;">
					<img alt="Previous" onclick="previousDayData();" style="cursor: pointer;" src="images/WFPM/commonDashboard/backward.jpg" title="Previous">
					&nbsp;&nbsp;&nbsp;
				</div>
				<div style="float: left;  color: black;">	
					Activities As On&nbsp;&nbsp; 
				</div>
				<div id="activityDate" style="color: black; float: left;"></div>
				<div style="float: left;">
					&nbsp;&nbsp;&nbsp;<img alt="Next" onclick="nextDayData();" style="cursor: pointer;" src="images/WFPM/commonDashboard/forward.jpg" title="Next">
				</div>
			</div>
			<br>
			<div style="margin-top: -2%;">
				Activities On Date:	<sj:datepicker name="date" id="date" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="button" 
						placeholder="Select Search Date" timepicker="false" onchange="searchActivity(this.value);" style="width: 10%;"/>
			</div>
			</td>
			
		</tr>
		<tr>
		</tr> 
		<tr style="background-color: grey; color: white; font-weight: bold;">
			<td style="padding:2px;margin:1px;text-align:center; width: 3%;">Sr.</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 8%;">Action</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 10%;">Contact Person</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 18%;">Organization</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 7%;">Type</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 10%;">Offering</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 10%;">Activity</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 8%;">Time</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 10%;">A/c Manager</td>
			<td style="padding:2px;margin:1px;text-align:center; width: 16%;">Compelling Reasons</td>
		</tr>
	</table>

<div id="activityDataList"  style="height:200px; overflow-y: scroll;">
</div>
</div>
<br><br>

<!-- Missed Activities -->
<table style="margin:0px;padding:0px; width:97%;">
<tr style="background-color: white; color: black; font-weight: bold;">
	<td style="padding:2px;margin:1px; text-align:center; width: 4%;" colspan="5">Missed Activities</td>
</tr>
<tr style="background-color: grey; color: white; font-weight: bold;">
	<td style="padding:2px;margin:1px;text-align:center; width: 3%;">Sr.</td>
	<td style="padding:2px;margin:1px;text-align:center; width: 30%;">Activity</td>
	<td style="padding:2px;margin:1px;text-align:center; width: 30%;">Organization</td>
	<td style="padding:2px;margin:1px;text-align:center; width: 7%;">Type</td>
	<td style="padding:2px;margin:1px;text-align:center; width: 10%;">Date</td>
	<td style="padding:2px;margin:1px;text-align:center; width: 20%;">A/c Manager</td>
</tr>
</table>
<div id="missedActivityDataList" style="height: 150px; overflow-y: scroll;">
</div>

</center>

</body>
<script type="text/javascript">
//fetch today activities
fetchActivities();

//fetch missed activities
fetchMissedActivities();
</script>
</html>