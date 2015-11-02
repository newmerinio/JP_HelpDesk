<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<title>Insert title here</title>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<script type="text/javascript" src="/js/common/topclient.js"></script>
<script type='text/javascript' src='js/jquery.colorbox.js'></script>

<style>
	.heading
	{
		font-size: 15px;
		font-size: 1.2em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #000000;
	}
</style>
<script type="text/javascript">
function showEditDetails()
{
	
	$("#dataShowDiv").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url :"view/Over2Cloud/WFPM/Patient/beforeEditPatientProfile.action",
        success : function(subdeptdata) {
       $("#"+"dataShowDiv").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

</script>
</head>
<body>
 <sj:dialog 
      	id="downloadpdfid" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Upload Logo"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="520"
		height="300"
    >
    <div id="testDiv"></div>
    </sj:dialog>
<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
		
		<div class="head">Patient</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Basic Details</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
	<s:if test="%{datavalue == 'true'}"> 
			<table width="100%" height="100%" align="center" border="0">
				<tr>
				<td valign="top" width="30%" align="center">
					<div class="logo1" id="PageRefresh" style="float: left;">
                            <img src="images/orgLogo.jpg" width="180px"height="65px" />
        </div>
						
					 <br />
					</td>
					
					
					
					<td valign="top" align="center" width="70%">
						<div id="dataShowDiv">
						<table align="center" width="100%" height="80%" border="0" style="width:61%">
							<tr>
								<td class="heading"><b>
									First Name:</b>
								</td>
								
								
								<td class="heading"  style="margin-left: 31px">
									<s:property value="%{first_name}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Last Name:</b>
								</td>
								<td class="heading"  style="margin-left: 34px">
									<s:property value="%{last_name}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									DOB:</b>
								</td>
								<td class="heading"  style="margin-left: 70px">
									<s:property value="%{age}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Address:</b>
								</td>
								<td class="heading"  style="margin-left: 52px">
						<s:property value="%{address}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Email:</b>
								</td>
								<td class="heading"  style="margin-left: 66px">
									<s:property value="%{email}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Mobile:</b>
								</td>
								<td class="heading"  style="margin-left: 60px">
									<s:property value="%{mobile}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Gender:</b>
								</td>
								<td class="heading"  style="margin-left: 56px">
									<s:property value="%{gender}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Nationality:</b>
								</td>
								<td class="heading"  style="margin-left: 34px">
									<s:property value="%{nationality}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Patient Type:</b>
								</td>
								<td class="heading"  style="margin-left: 25px">
									<s:property value="%{patient_type}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Patient Category:</b>
								</td>
								<td class="heading"  style="margin-left: 1px">
									<s:property value="%{patient_category}" />
								</td>
							</tr>
							<tr>
								<td class="heading"><b>
									Blood Group:</b>
								</td>
								<td class="heading"  style="margin-left: 24px">
									<s:property value="%{blood_group}" />
								</td>
							</tr>
							
							<tr>
								<td colspan="2">
								
								<br>
								 <sj:a cssStyle="margin-left: 100px;margin-top: 11px;" 
						button="true" href="#" value="Edit"  cssClass="button"
						onclick="showEditDetails();"
					>
						Edit
					</sj:a>
					
					<br><br><br>
					<!--<tr>
								
								<td class="heading" style="margin-left: -17px">
									<p><i><b>Note : </b>Please provide valid and active profile details as it is being used in various modules for user's support.</i></p> 
								</td>
							</tr>
					
			            <sj:a
	                        cssStyle="margin-left: 276px;margin-top: -58px;"
						button="true" href="#" value="View" onclick="showOrgDetails();" cssStyle="margin-left: -9px;margin-top:11px;" 
						>Back
					</sj:a>
								--></td>
							</tr>
						</table>
							</div>
					</td>
					
				</tr>
			</table>
		</s:if>
		<s:else>
			<table align="center" width="100%" height="80%" border="0" style="width:61%">
							<tr align="center">
								<td class="heading" colspan="2" align="center">
								<b>
									No Data Available</b>
								</td>
								
							</tr>
				</table>
		</s:else>
	</div>
	<br>
	<br>
	<br>
</div>
</body>

<script>
	        function uploadPic()
	        {
	        	$("#downloadpdfid").dialog('opeDr Suman');
	        	$("#testDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	        	$.ajax({
	        	    type : "post",
	        	    url : "/over2cloud/logoUpload.action",
	        	    success : function(subdeptdata) {
	               $("#"+"testDiv").html(subdeptdata);
	        	},
	        	   error: function() {
	                    alert("error");
	                }
	        	 });
	        }
</script>
</html>