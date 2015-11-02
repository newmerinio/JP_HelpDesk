<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>More Settings</title>
<script type="text/javascript">
function fromEmail()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforeEmailSettingViewHeader.action",
	    success : function(subdeptdata) {
	       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function holiday()
{
$("#moreSettingDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
$.ajax({
    type : "post",
    url : "view/Over2Cloud/leaveManagement/beforeHolidayListView.action",
    success : function(subdeptdata) {
   $("#"+"moreSettingDiv").html(subdeptdata);
},
   error: function() {
        alert("error");
    }
 });
}

function sms_mail_conf()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeSMSMailConf.action",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function calender()
{
	$("#calenderDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/referral/calendarMini.jsp",
	    success : function(subdeptdata) {
       $("#"+"calenderDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function deadLineDisplay()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/deadLineDisplay.action",
	    async:false,
	    success : function(subdeptdata) 
	    {
			var deadline= subdeptdata;
			var text = '<div style="margin-left: 10px;float:left;margin-top: -17px;">';
			text += '<font  color="red"><b>Deadline For: </b></font>';
			for(var k=0;k<deadline.length;k++)
			{
				text += ''+deadline[k].configureFor+', '+deadline[k].date;
				text += ', ';
			}	
			text += '</div>';
			$("#deadlineDiv").append(text);
	    },
	    error: function() 
	    {
	        alert("error");
	    }
	 });
}
calender();
deadLineDisplay();
showCount();
function showCount()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/showCountData.action",
	    async:false,
	    success : function(subdeptdata) 
	    {
			var deadline= subdeptdata;
			var text = '<div style="margin-left: 10px;float:left;margin-top: -17px;">';
			text += '<font  color="red"><b>Deadline For: </b></font>';
			for(var k=0;k<deadline.length;k++)
			{
				text += ''+deadline[k].configureFor+', '+deadline[k].date;
				text += ', ';
			}	
			text += '</div>';
			$("#deadlineDiv").append(text);
	    },
	    error: function() 
	    {
	        alert("error");
	    }
	 });
}
</script>
<style type="text/css">
.shd
{
box-shadow: 2px 1px 7px 1px #ccc;
border-radius: 8px;
}
.mybadge{
box-shadow: 2px 1px 7px 1px #ccc;
border-radius: 27px;
float: right;
margin: 0% 4% 4% 6%;
position: absolute;
background: rgb(249, 66, 23);
padding: 1px 7px 1px 6px;
color: white;
}
</style>
</head>
<body>
	<div class="border" style="width: 72%;height:380px;padding: 7% 7% 2% 6%;">
		<table width="100%"  border="0" align="left" cellpadding="3" cellspacing="2">
		
					
						<tr>
						<td class="shd" align="center">
						<span class="mybadge" id="corporate">2</span>
							<table >
								<tr>
									<td align="center" >
										<a href="#" onclick="corporate_add();"><img alt="Corporate" src="images/calender/corporate.jpg" width="100px" height="100px" title="Corporate"></a>
									</td>
								</tr>
								<tr>
									<td align="center">Corporate</td>
								</tr>						
							</table>
						</td>
							
						    <td align="center" >
						    	<table >
								<tr>
									<td align="center" >
										<a href="#" id="patientWFPM" onclick="fromEmail();"><img alt="Patient" src="images/calender/patient.jpg" width="100px" height="100px" title="Patient"></a>
									</td>
								</tr>
								<tr>
									<td align="center">Patient</td>
								</tr>						
							</table>
						    
						    </td>
						    <td align="center" class="shd">
						    <table >
								<tr>
									<td align="center" >
										<a href="#" onclick="referralView();"><img alt="Referral" src="images/calender/referral.png" width="100px" height="100px" title="Referral"></a>
									</td>
								</tr>
								<tr>
									<td align="center">Referral</td>
								</tr>						
							</table>
						    </td>
							<td align="center" >
							  <table >
								<tr>
									<td align="center" >
										<a href="#" onclick="schedule_planner();"><img alt="Plan Activity" src="images/calender/activity.jpg" width="100px" height="100px" title="Plan Activity"></a>
									</td>
								</tr>
								<tr>
									<td align="center">Plan Activity</td>
								</tr>						
							</table>
							</td>
						</tr>
						<tr>
							<td align="center">
							 <table >
								<tr>
									<td align="center" >
										<a href="#" onclick="schedule_planner();"><img alt="Plan Event" src="images/calender/event.jpg" width="100px" height="100px" title="Plan Event"></a>
									</td>
								</tr>
								<tr>
									<td align="center">Plan Event</td>
								</tr>						
							</table>
							</td>
							<td align="center" class="shd">
							 <table >
								<tr>
									<td align="center" >
											<a href="#" onclick="calendarView();"><img alt="DCR Activity" src="images/calender/dcrfill.jpg" width="100px" height="100px" title="DCR Activity"></a>
									</td>
								</tr>
								<tr>
									<td align="center">DCR Activity</td>
								</tr>						
							</table>
							</td>
							<td align="center">
							 <table >
								<tr>
									<td align="center" >
											<a href="#" onclick="calendarView();"><img alt="DCR Event" src="images/calender/eventdcr.png" width="100px" height="100px" title="DCR Event"></a>
									</td>
								</tr>
								<tr>
									<td align="center">DCR Event</td>
								</tr>						
							</table>
							
							</td>
							<td align="center" class="shd">
							 <table >
								<tr>
									<td align="center" >
											<a href="#" onclick="extendApproval();"><img alt="Request Status" src="images/calender/requeststatus.png" width="100px" height="100px" title="Request Status"></a>
									</td>
								</tr>
								<tr>
									<td align="center">Request Status</td>
								</tr>						
							</table>
							
							
							</td>
						</tr>
			
	    </table>
	   </div>
	   <div class="border" style="width: 72%;">
	   <div id="deadlineDiv"></div>
	   </div>
	   <div class="border" style="width: 26%;
float: right;
margin: -34% 1% 1% 1%;">  
	     <table width="25%" height="100%" border="1" align="right">
				<tr>
					<td style="  border-right: 1px solid rgb(195, 184, 184);" width="15%">
						<div id="calenderDiv" style="height: 100%;width: 101%"></div>
					</td>
				</tr>
		</table>
	  </div>
</body>
</html>