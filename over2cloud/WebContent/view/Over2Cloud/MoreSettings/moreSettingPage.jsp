<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>More Settings</title>
<style type="text/css">
.Head{
letter-spacing: 2px;
}
</style>
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
function logo()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/commonModules/beforeOrganizationViewTable.action",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body>
					<table width="100%" height="100%">
						<tr>
							<td style="  border-right: 1px solid rgb(195, 184, 184);" width="15%">
								<table width="100%" height="100%">
									<tr>
										<td align="center"><a href="#" onclick="fromEmail();"><img alt="From Email" src="images/moresetting/email.png" width="60px" height="60px"></a></td>
									</tr>
									<tr>
										<td align="center" class="Head">Email Setting</td>
									</tr>
									<!-- <tr>
										<td align="center" class="Head"><a href="#" onclick="sms_mail_conf();"><img alt="Notification" src="images/moresetting/images.png" width="40px" height="40px"></a></td>
									</tr>
									<tr>
										<td align="center" class="Head">Notification</td>
									</tr>
									<tr>
										<td align="center" class="Head"><a href="#"><img alt="Lodge Feedback" src="images/moresetting/feedback_icon.jpg" width="40px" height="40px"></a></td>
										
									</tr>
									<tr>
										<td align="center" class="Head">Lodge Feedback</td>
									</tr> -->
									<tr>
										<td align="center" class="Head"><a href="#" onclick="logo();"><img alt="Summary Report" src="images/moresetting/logo.png" width="60px" height="60px"></a></td>
									</tr>
									<tr>
										<td align="center" class="Head">Logo</td>
									</tr><!--
									<tr>	
										<td align="center" class="Head"><a href="#" onclick="holiday();"><img alt="Holiday Calender" src="images/moresetting/calendar-icon-new.png" width="40px" height="40px"></a></td>
									</tr>
									<tr>
										<td align="center" class="Head">Holiday Calendar</td>
									</tr>
								--></table>
							
							</td>
							<td align="center" width="100%"><div id="moreSettingDiv" style="height: 100%;width: 100%"></div></td>
							
						</tr>
					
					
				</table>
</body>
</html>