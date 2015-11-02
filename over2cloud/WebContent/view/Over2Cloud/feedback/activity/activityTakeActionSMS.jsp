<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<SCRIPT type="text/javascript">
function viewActivityPage()
{
	//alert("Hello");
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/feedback_Activity/viewActivityHeader.action",
        success : function(subdeptdata) {
      $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function getActionToDeptDetails(actionTaken,feedDataId)
{
	var feedId=$("#"+feedDataId).val();
	var statusType=$("#"+actionTaken).val();
	if(statusType=='1')
	{
			// url : conP+"/view/Over2Cloud/feedback/beforeFeedViaOnlineTicketPcc.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=online&fbDataId="+feedId+"&feedTicketId="+feedTicketId+"&compType="+compType.split(" ").join("%20")+"&clientId="+clientId.split(" ").join("%20"),
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/feedback/feedback_Activity/beforeFeedViaOnlineTicketActivity.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedDataId="+feedId,
		    success : function(subdeptdata) {
	       $("#"+"smsActionDiv").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else
	{
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/feedback/activity/activityNoAction.jsp",
		    success : function(subdeptdata) {
	       $("#"+"mybuttondialog123").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

$.subscribe('validateSMSAction', function(event,data)
		{
			$("#confirmEscalationDialogSMS").dialog('open');
			$("#confirmEscalationDialogSMS").dialog({title:'Activity Action Status'});
		   
		});

function resetForm(formId)
{
	$('#'+formId).trigger("reset");
	 $("#formDiv").html("");
}

function getActionDiv(actionTaken)
{
	var statusType=$("#"+actionTaken).val();
	if(statusType=='3')
	{
		 document.getElementById("snoozeDiv").style.display = 'block';
	}
}

</SCRIPT>

<STYLE type="text/css">

	td.tdAlign {
	padding: 5px;
}

td.poor
{
	color: #FF0000;
}
td.average
{
	color: #FFCC00;
}
td.good
{
	color: #66FF66;
}

td.vgood
{
	color: #00CC00;
}

td.excellent
{
	color: #006600;
}
tr.color
{
	background-color: #E6E6E6;
}
</STYLE>

</head>
<body>
<div align="center">
<s:form action="takeActionActivity" id="activityActionForm" name="activityActionForm" >
<s:hidden name="feedId" id="feedId" value="%{pojo.getFeedDataId()}"></s:hidden>
<s:hidden name="clientId" id="clientId" value="%{pojo.getClientId()}" />
<s:hidden name="mode" id="mode" value="%{pojo.getMode()}" />
<s:hidden name="feedStatId" id="feedStatId" value="%{feedStatId}" />
<s:hidden name="activityFlag" id="activityFlag" value="%{activityFlag}"/>
<s:hidden name="ticket_no" id="ticket_no" value="%{ticket_no}"/>
<s:hidden name="patientId" id="patientId" value="%{patientId}"/>
<s:hidden name="feedbackDataId" id="feedbackDataId" value="%{feedbackDataId}"/>
<s:hidden name="visitType" id="visitType" value="%{visitType}"/>

		<table border='1' bordercolor="lightgray" cellpadding="10px" rules="rows" width="80%" align="center">
			<tr class="color">
				<td class="tdAlign">
					<b>MRD&nbsp;No:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getClientId()}" /></b>
				</td>
				<td class="tdAlign">
					<b>Patient&nbsp;Name:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getClientName()}" /></b>
				</td>
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Mobile&nbsp;No:</b>
				</td>
				<td class="tdAlign">
				
					<b><s:property value="%{pojo.getMobNo()}" /></b>
				</td>
				<td class="tdAlign">
					<b>Email&nbsp;ID:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getEmailId()}" /></b>
				</td>
			</tr>
			<tr class="color">
				<td class="tdAlign">
					<b>Opened At:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getDateTime()}" /></b>
				</td>
				<td class="tdAlign">
					<b>Mode:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getMode()}" /></b>
				</td>
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Discharged On:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getDischargeDateTime()}" /></b>
				</td>
				<td class="tdAlign">
				</td >
				<td class="tdAlign">
				</td>
			</tr>
			<tr class="color">
				<td class="tdAlign">
					<b>To Department:</b>
				</td>
				<td class="tdAlign">
					<s:select 
                      id="feedToDept" 
			          name="feedToDept" 
			          list="serviceSubDeptList"
			          headerKey="-1"
			          headerValue="Select Department"
			          cssClass="select"
			          theme="simple"
			          cssStyle="width:82%"
			          onchange="commonSelectAjaxCall2('feedback_type','id','fb_type','dept_subdept',this.value,'module_name','FM','fb_type','ASC','feedType','Select Feedback Type');"
         			 />
				</td>
				<td class="tdAlign">
					<b>Feedback Type:</b>
				</td>
				<td class="tdAlign">
					<s:select  
	                  			  id="feedType"
	                              name="feedbackType"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Feedback Type" 
	                              cssClass="select"
	                              theme="simple"
	                              cssStyle="width:82%"
		                          onchange="commonSelectAjaxCall('feedback_category','id','category_name','fb_type',this.value,'','','category_name','ASC','category','Select Category');"
	                              >
	                  </s:select>
				</td>
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Category:</b>
				</td>
				<td class="tdAlign">
					<s:select 
	                              id="category"
	                              name="category" 
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Category" 
	                              cssClass="select"
	                              theme="simple"
	                              cssStyle="width:82%"
	                              onchange="commonSelectAjaxCall('feedback_subcategory','id','sub_category_name','category_name',this.value,'','','sub_category_name','ASC','subCategory3','Select Sub-Category');">
	                  </s:select>
				</td>
				<td class="tdAlign">
					<b>Sub Category:</b>
				</td>
				<td class="tdAlign">
					<s:select 
                              id="subCategory3"
                              name="subCatId"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub Category" 
                              cssClass="select"
                              theme="simple"
                              onchange="getFeedBreifViaAjax(this.value);"
                              cssStyle="width:82%"
                              >
                 </s:select>
				</td>
			</tr>
			<tr class="color">
				<td class="tdAlign"><b>Patient&nbsp;Interaction:</b></td>
				<td class="tdAlign"><s:textfield name="feed_brief" theme="simple" id="feed_brief" placeholder="Enter Brief" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				</td>
				<td colspan="2" class="tdAlign">
						SMS:&nbsp;&nbsp;&nbsp;<s:checkbox name="recvSMS" id="recvSMS" theme="simple"></s:checkbox>
		                Email:&nbsp;&nbsp;&nbsp;<s:checkbox name="recvEmail" id="recvEmail" theme="simple"></s:checkbox>
				</td>
			</tr>
			<tr>
				<td class="tdAlign">
					<b>Take Action:</b>
				</td>
				<td class="tdAlign">
					<s:select 
                        id="action"
                        name="action"
                        list="#{'1':'Open Ticket', '2':'No Further Action Required','3':'Snooze','4':'Ignore'}"
                        headerKey="-1"
                        headerValue="--Select Action Name--" 
                        cssClass="select"
					    cssStyle="width:82%"
					    theme="simple"
					    onchange="getActionDiv('action');"
                        >
				</s:select>
				</td>
				<td class="tdAlign">
					<b>Comments:</b>
				</td>
				<td class="tdAlign">
					<s:textarea cssClass="textcell" id="comments" name="comments" cssStyle="width:180px;" theme="simple"></s:textarea>
				</td>
			</tr>
			<tr class="color">
				<td colspan="2">
					<div id="snoozeDiv" style="display:none">
						<table width="100%">
						<tr>
							<td class="tdAlign" ><b>Snooze Date</b></td>
							<td class="tdAlign">
								<sj:datepicker id="snoozeDate" name="snoozeDate" readonly="true" placeholder="Select Snooze Date"  showOn="focus"  displayFormat="dd-mm-yy"  minDate="0" cssClass="textField" />
							</td>
							<td class="tdAlign"><b>Snooze Time</b></td>
							<td class="tdAlign">
								<sj:datepicker id="snoozeTime" name="snoozeTime" readonly="true" placeholder="Select Snooze Time"  showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField"/>
							</td>
						</tr>
						<tr>
							<td class="tdAlign"><b>Snooze Comment</b></td>
							<td class="tdAlign">
								<s:textfield name="snoozecomment" id="snoozecomment" placeholder="Enter Snooze Comment"  cssClass="textField" theme="simple"/>
							</td>
						</tr>
					</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<table width="100%">
						<tr>
							<td width="100%" align="center">
								<center>	
             <div class="type-button">
                        <sj:submit 
	                        targets="confirmingEscalationSMS" 
	                        clearForm="true"
	                        value="  Register  " 
	                        button="true"
	                        cssClass="submit"
	                        onBeforeTopics="validateSMSAction"
	                        cssStyle="margin-left: 280px;margin-top: -18px;"
	                        />
                        &nbsp;&nbsp;
                        <sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 380px;margin-top: -28px;"
		                     onclick="resetForm('takeActionActivity')"
			            />
              </div> 
           </center>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<center>
			         <sj:dialog loadingText="Please wait..."  id="confirmEscalationDialogSMS" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Online" width="450" cssStyle="overflow:hidden;" height="200" showEffect="slide" hideEffect="explode" >
                           <sj:div id="foldeffectExcel"  effect="fold"><div id="saveExcel"></div></sj:div><div id="confirmingEscalationSMS"></div>
                     </sj:dialog>
                </center>
		</s:form>
		</div>
		<br />
		<div>
<b>NOTE:- <br><br></b>
		&nbsp;&nbsp;&nbsp;<b>Ignore:</b><i>Call repeatedly not picked up.</i><br>
		&nbsp;&nbsp;&nbsp;<b>Snooze:</b><i> Due to Reasons Beyond Control of User.</i><br>
		&nbsp;&nbsp;&nbsp;<b>NFA:</b><i> No Further Action Required.</i><br>
		&nbsp;&nbsp;&nbsp;<b>Allotment:</b><i>Tickets will be alloted as per the roaster and holidays of the concerned department.</i>
</div>
</body>
</html>