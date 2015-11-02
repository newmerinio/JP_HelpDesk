<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<STYLE type="text/css">

	td.tdAlign
	{
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
<script type="text/javascript">

$.subscribe('validate1', function(event,data)
		{
			$("#confirmEscalationDialog").dialog('open');
			$("#confirmEscalationDialog").dialog({title:'Activity Action Status'});
		   
		});
		
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
	 $("#formDiv").html("");
}
</script>
</head>
<body>
<s:form action="takeActionActivity" id="activityActionForm" name="activityActionForm" >
<s:hidden name="feedId" id="feedId" value="%{pojo.getFeedDataId()}"></s:hidden>
<s:hidden name="feedToDept" id="feedToDept" value="%{pojo.getDeptId()}" />
<s:hidden name="subCatId" id="subCatId" value="%{pojo.getSubCatId()}" />
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
					<b>Mob&nbsp;No:</b>
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
				</td >
				<td class="tdAlign">
					<b><s:property value="%{pojo.getMode()}" /></b>
				</td>
			</tr>
			<tr>
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
				<b><s:property value="%{pojo.getDept()}" /></b>
				</td>
				<td class="tdAlign">
					<b>Category:</b>
				</td >
				<td class="tdAlign"><b><s:property value="%{pojo.getCat()}" /></b>
				</td>
			</tr>
			<tr>
				<td class="tdAlign">
					<b>Sub Category:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getSubCat()}" /></b>
				</td>
				<td class="tdAlign">
					<b>Brief:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getBrief()}" /></b>
				</td>
			</tr>
			<tr class="color">
				<td class="tdAlign">
					<b>Resolution Time:</b>
				</td>
				<td class="tdAlign">
					<b><s:property value="%{pojo.getAddressingTime()}" /></b>
				</td>
				<td class="tdAlign">
					<b>SMS&nbsp;&&nbsp;Email:</b>
				</td>
				<td class="tdAlign">
						SMS:&nbsp;&nbsp;&nbsp;<s:checkbox name="recvSMS" id="recvSMS" theme="simple"></s:checkbox>
           				Email:&nbsp;&nbsp;&nbsp;<s:checkbox name="recvEmail" id="recvEmail" theme="simple"></s:checkbox>
				</td>
			</tr>
			<tr>
				<td class="tdAlign">
					<b>Action:</b>
				</td>
				<td class="tdAlign">
					<s:select 
	                      id="action" 
				          name="action" 
				          list="#{'1':'Open Ticket', '2':'No Further Action Required','3':'Ignore'}"
				          headerKey="1"
				          headerValue="Open Ticket"
				          cssClass="button"
				          theme="simple"
         				 />
				</td>
				<td class="tdAlign">
					<b>Comments:</b>
				</td>
				<td class="tdAlign">
					<s:textarea cssClass="textcell" id="comments" name="comments" cssStyle="width:180px;" theme="simple"></s:textarea>
				</td>
			</tr>
			<tr class="color">
				<td colspan="4" align="center" >
					<table width="100%">
						<tr>
							<td width="100%" align="center">
								<center>	
             <div class="type-button">
                        <sj:submit 
	                        targets="confirmingEscalation" 
	                        clearForm="true"
	                        value="  Register  " 
	                        button="true"
	                        cssClass="submit"
	                        cssStyle="margin-left: 280px;margin-top: -18px;"
	                        onBeforeTopics="validate1"
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
			         <sj:dialog loadingText="Please wait..."  id="confirmEscalationDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Online" width="450" cssStyle="overflow:hidden;" height="200" showEffect="slide" hideEffect="explode" >
                           <sj:div id="foldeffectExcel"  effect="fold"><div id="saveExcel"></div></sj:div><div id="confirmingEscalation"></div>
                     </sj:dialog>
                </center>
		</s:form>
		<br />
		<div>
<b>NOTE: <i>Tickets will be alloted as per the roaster and holidays of the concerned department.</i></b>
</div>
</body>
</html>