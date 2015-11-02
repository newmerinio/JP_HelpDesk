<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript"> 
	function loadPaperData()
	{
	 var mrdNo = $("#clientId").val();
	 var id= $("#id").val();
	    $.ajax({
	        type : "post",
	        url : "view/Over2Cloud/feedback/viewpccActionOnPaperFeedback.action?clientId="+mrdNo+"&id="+id,
	        success : function(data) {
	      				if(data.paperViewObj.responseToQueries == null){$("#responseToQueries").html("");}
    	                else {$("#responseToQueries").html(data.paperViewObj.responseToQueries);}
    	                if(data.paperViewObj.counselling == null){$("#counselling").html("");}
    	                else {$("#counselling").html(data.paperViewObj.counselling);}
    	                if(data.paperViewObj.admission == null){$("#admission").html("");}
    	                else {$("#admission").html(data.paperViewObj.admission);}
    	                if(data.paperViewObj.courtesybehaviour == null){$("#courtesybehaviour").html("");}
    	                else {$("#courtesybehaviour").html(data.paperViewObj.courtesybehaviour);}
    	                if(data.paperViewObj.attentivePrCaring == null){$("#attentivePrCaring").html("");}
    	                else {$("#attentivePrCaring").html(data.paperViewObj.attentivePrCaring);}
    	                if(data.paperViewObj.responseCommDoctor == null){$("#responseCommDoctor").html("");}
    	                else {$("#responseCommDoctor").html(data.paperViewObj.responseCommDoctor);}
    	                if(data.paperViewObj.diagnosis == null){$("#diagnosis").html("");}
    	                else {$("#diagnosis").html(data.paperViewObj.diagnosis);}
    	                if(data.paperViewObj.treatment == null){$("#treatment").html("");}
    	                else {$("#treatment").html(data.paperViewObj.treatment);}
    	                if(data.paperViewObj.attentiveprompt == null){$("#attentiveprompt").html("");}
    	                else {$("#attentiveprompt").html(data.paperViewObj.attentiveprompt);}
    	                if(data.paperViewObj.responseCommunication == null){$("#responseCommunication").html("");}
    	                else {$("#responseCommunication").html(data.paperViewObj.responseCommunication);}
    	                if(data.paperViewObj.procedures == null){$("#procedures").html("");}
    	                else {$("#procedures").html(data.paperViewObj.procedures);}
    	                if(data.paperViewObj.medication == null){$("#medication").html("");}
    	                else {$("#medication").html(data.paperViewObj.medication);}
    	                if(data.paperViewObj.careAtHome == null){$("#careAtHome").html("");}
    	                else {$("#careAtHome").html(data.paperViewObj.careAtHome);}
    	                if(data.paperViewObj.qualityUpkeep == null){$("#qualityUpkeep").html("");}
    	                else {$("#qualityUpkeep").html(data.paperViewObj.qualityUpkeep);}
    	                if(data.paperViewObj.behivourResponseTime == null){$("#behivourResponseTime").html("");}
    	                else {$("#behivourResponseTime").html(data.paperViewObj.behivourResponseTime);}
    	                if(data.paperViewObj.functioningMainenance == null){$("#functioningMainenance").html("");}
    	                else {$("#functioningMainenance").html(data.paperViewObj.functioningMainenance);}
    	                if(data.paperViewObj.qualityofFoods == null){$("#qualityofFoods").html("");}
    	                else {$("#qualityofFoods").html(data.paperViewObj.qualityofFoods);}
    	                if(data.paperViewObj.timelineService == null){$("#timelineService").html("");}
    	                else {$("#timelineService").html(data.paperViewObj.timelineService);}
    	                if(data.paperViewObj.behivourResponse == null){$("#behivourResponse").html("");}
    	                else {$("#behivourResponse").html(data.paperViewObj.behivourResponse);}
    	                if(data.paperViewObj.assessmentCounseling == null){$("#assessmentCounseling").html("");}
    	                else {$("#assessmentCounseling").html(data.paperViewObj.assessmentCounseling);}
    	                if(data.paperViewObj.responsetoQuery == null){$("#responsetoQuery").html("");}
    	                else {$("#responsetoQuery").html(data.paperViewObj.responsetoQuery);}
    	                if(data.paperViewObj.efficiencyBillingdesk == null){$("#efficiencyBillingdesk").html("");}
    	                else {$("#efficiencyBillingdesk").html(data.paperViewObj.efficiencyBillingdesk);}
    	                if(data.paperViewObj.dischargetime == null){$("#dischargetime").html("");}
    	                else {$("#dischargetime").html(data.paperViewObj.dischargetime);}
    	                if(data.paperViewObj.pharmacyservices == null){$("#pharmacyservices").html("");}
    	                else {$("#pharmacyservices").html(data.paperViewObj.pharmacyservices);}
    	                if(data.paperViewObj.cafeteriaDyning == null){$("#cafeteriaDyning").html("");}
    	                else {$("#cafeteriaDyning").html(data.paperViewObj.cafeteriaDyning);}
    	                if(data.paperViewObj.overallsecurity == null){$("#overallsecurity").html("");}
    	                else {$("#overallsecurity").html(data.paperViewObj.overallsecurity);}
    	                if(data.paperViewObj.overallservices == null){$("#overallservices").html("");}
    	                else {$("#overallservices").html(data.paperViewObj.overallservices);}
    	                if(data.paperViewObj.recommend == null){$("#recommend").html("");}
    	                else {
    	                if(data.paperViewObj.recommend == '1'){
    	                	 $("#recommend").html('Yes');
    	                    }else
    	                    {
    	                     $("#recommend").html('No');
    	                    }
    	                }
    	                if(data.paperViewObj.choseHospital == null){$("#choseHospital").html("");}
    	                else {$("#choseHospital").html(data.paperViewObj.choseHospital);}
    	                 if(data.paperViewObj.avgFronOffice == null){$("#avgFronOffice").html("");}
    	                else {$("#avgFronOffice").html(data.paperViewObj.avgFronOffice);}
    	                 if(data.paperViewObj.avgDoctotrs == null){$("#avgDoctotrs").html("");}
    	                else {$("#avgDoctotrs").html(data.paperViewObj.avgDoctotrs);}
    	                 if(data.paperViewObj.avgNurses == null){$("#avgNurses").html("");}
    	                else {$("#avgNurses").html(data.paperViewObj.avgNurses);}
    	                 if(data.paperViewObj.avgHosekeeping == null){$("#avgHosekeeping").html("");}
    	                else {$("#avgHosekeeping").html(data.paperViewObj.avgHosekeeping);}
    	                 if(data.paperViewObj.avgDiatics == null){$("#avgDiatics").html("");}
    	                else {$("#avgDiatics").html(data.paperViewObj.avgDiatics);}
    	                 if(data.paperViewObj.avgDischarge == null){$("#avgDischarge").html("");}
    	                else {$("#avgDischarge").html(data.paperViewObj.avgDischarge);}
    	                 if(data.paperViewObj.avgOthers == null){$("#avgOthers").html("");}
    	                else {$("#avgOthers").html(data.paperViewObj.avgOthers);}
    	                
    	                
	    },
	       error: function() {
	            alert("error");
	        }
	     });
	}
	loadPaperData();
</script>
<STYLE type="text/css">

	td {
	padding: 5px;
	padding-left: 20px;
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
tr.avgcolor
{
	background-color: #cdd3d8;
}
</STYLE>
</head>
<body>
<s:hidden name = "clientId" id="clientId" value="%{clientId}" />
<s:hidden name = "id" id="id" value="%{#parameters.id}" />
<div align="center">
	<table border='1' bordercolor="lightgray" cellpadding="10px" rules="rows" width="80%">
	<tr bgcolor="grey" align="center"><td><b>View&nbsp;Mode </b> </td><td></td> </tr>
		<tr class="color"><td align="center"><b>Front Office/&nbsp;Admission </b> </td><td></td> </tr>
			<tr>
				<td >
					Response&nbsp;to&nbsp;Queries:
				</td>
				<td id="responseToQueries">
					
				</td>
				</tr>
			<tr>
				<td>
					Counseling&nbsp;of&nbsp;Expenses&nbsp;&&nbsp;Packages:
				</td>
				<td id="counselling">
					
				</td>
			</tr>
			<tr>
				<td>
					Time&nbsp;Taken&nbsp;for&nbsp;your&nbsp;Admission&nbsp;Process:
				</td>
				<td id="admission">
					
				</td>
			</tr>
			<tr>
				<td>
					Courtesy&nbsp;&&nbsp;Behavior:
				</td>
				<td id="courtesybehaviour">
					
				</td>
			</tr>
			<tr class = "avgcolor">
				<td>
					Average:
				</td>
				<td id="avgFronOffice">
					
				</td>
			</tr>
			<tr class="color"> <td align="center"><b>Doctors</b></td><td></td> </tr>
			<tr>
				<td >
					Attentive,&nbsp;Prompt&nbsp;and&nbsp;Caring:
				</td>
				<td id="attentivePrCaring">
					
				</td>
			</tr>
			<tr>
				<td>
					Timely&nbsp;Response&nbsp;and&nbsp;Communication:
				</td>
				<td id="responseCommDoctor">
				
				</td>
			</tr>
			<tr>
				<td>
					Explanation&nbsp;of&nbsp;Diagnosis:
				</td>
				<td id="diagnosis">
				
				</td>
				
			</tr>
			<tr>
				<td>
					Explanation&nbsp;of&nbsp;Treatment:
				</td>
				<td id="treatment">
				
				</td>
			</tr>
			<tr class = "avgcolor">
				<td>
					Average:
				</td>
				<td id="avgDoctotrs">
					
				</td>
			</tr>
			<tr class="color"> <td align="center"><b>Nurses</b></td><td></td> </tr>
			<tr>
				<td>
					Attentive,&nbsp;Prompt&nbsp;and&nbsp;Caring:
				</td>
				<td id="attentiveprompt">
				
				</td>
			</tr>
			<tr>
				<td>
					Timely&nbsp;Response&nbsp;and&nbsp;Communication:
				</td>
				<td id="responseCommunication">
				
				</td>
			</tr>
			<tr>
				<td>
					Explanation&nbsp;of&nbsp;Procedures:
				</td>
				<td id="procedures">
				
				</td>
			</tr>
			<tr>
				<td>
					Explanation&nbsp;of&nbsp;Medication:
				</td>
				<td id="medication">
				
				</td>
			</tr>
			<tr>
				<td>
					Explanation&nbsp;of&nbsp;Care&nbsp;at&nbsp;home
				</td>
				<td id="careAtHome">
				
				</td>
			</tr>
			<tr class = "avgcolor">
				<td>
					Average:
				</td>
				<td id="avgNurses">
					
				</td>
			</tr>
				<tr class="color"> <td align="center"><b>Housekeeping</b></td><td></td> </tr>
				<tr>
				<td>
					Quality&nbsp;of&nbsp;Cleanliness&nbsp;and&nbsp;Upkeep:
				</td>
				<td id="qualityUpkeep">
				
				</td>
			</tr>
			<tr>
				<td>
					Behavior&nbsp;and&nbsp;Response&nbsp;Time:
				</td>
				<td id="behivourResponseTime">
				
				</td>
			</tr>
			<tr>
				<td>
					Functioning&nbsp;and&nbsp;Maintenance&nbsp;of&nbsp;Equipments/&nbsp;Facilities:
				</td>
				<td id="functioningMainenance">
				
				</td>
			</tr>
			<tr class = "avgcolor">
				<td>
					Average:
				</td>
				<td id="avgHosekeeping">
					
				</td>
			</tr>
			<tr class="color"><td align="center"><b>F&nbsp;&&nbsp;B&nbsp;/&nbsp;Dietetics</b></td><td></td> </tr>
			<tr>
				<td>
					Quality&nbsp;of&nbsp;Food:
				</td>
				<td id="qualityofFoods">
				
				</td>
			</tr>
			<tr>
				<td>
					Timeliness&nbsp;of&nbsp;Service:
				</td>
				<td id="timelineService">
				
				</td>
			</tr>
			<tr>
				<td>
					Behavior&nbsp;and&nbsp;Response&nbsp;Time:
				</td>
				<td id="behivourResponse">
				
				</td>
			</tr>
			<tr>
				<td>
					Assessment&nbsp;/&nbsp;Counseling&nbsp;by&nbsp;Dietitian:
				</td>
				<td id="assessmentCounseling">
				
				</td>
			</tr>
			<tr class = "avgcolor">
				<td>
					Average:
				</td>
				<td id="avgDiatics">
					
				</td>
			</tr>
			<tr class="color"><td align="center"><b>Discharge</b></td><td></td> </tr>
			<tr>
				<td>
					Response&nbsp;to&nbsp;Queries&nbsp;by&nbsp;TPA/&nbsp;Billing&nbsp;Desk:
				</td>
				<td id="responsetoQuery">
				
				</td>
			</tr>
			<tr>
				<td>
					Efficiency&nbsp;of&nbsp;TPA/&nbsp;Billing&nbsp;Desk:
				</td>
				<td id="efficiencyBillingdesk">
				
				</td>
			</tr>
			<tr>
				<td>
					Discharge&nbsp;Time:
				</td>
				<td id="dischargetime">
				
				</td>
			</tr>
			<tr class = "avgcolor">
				<td>
					Average:
				</td>
				<td id="avgDischarge">
					
				</td>
			</tr>
			<tr class="color"><td align="center"><b>Other&nbsp;Facilities</b></td><td></td> </tr>
			<tr>
				<td>
					Pharmacy&nbsp;Services:
				</td>
				<td id="pharmacyservices">
				
				</td>
			</tr>
			<tr>
				<td>
					Cafeteria&nbsp;/&nbsp;Public&nbsp;Dining&nbsp;options:
				</td>
				<td id="cafeteriaDyning">
				
				</td>
			</tr>
			<tr>
				<td>
					Parking&nbsp;and&nbsp;Security&nbsp;Assistance:
				</td>
				<td id="overallsecurity">
				
				</td>
			</tr>
			<tr>
				<td>
					Overall&nbsp;Level&nbsp;of&nbsp;Services:
				</td>
				<td id="overallservices">
				
				</td>
			</tr>
			<tr class = "avgcolor">
				<td>
					Average:
				</td>
				<td id="avgOthers">
					
				</td>
			</tr>
			<tr class="color">
				<td>
					Would&nbsp;you&nbsp;recommend&nbsp;us&nbsp;to&nbsp;others:
				</td>
				<td id="recommend">
				
				</td>
			</tr>
			<tr>
				<td>
					You&nbsp;have&nbsp;chosen&nbsp;our&nbsp;Hospital&nbsp;because&nbsp;of:
				</td>
				<td id="choseHospital">
				
				</td>
			</tr>
			
		</table>
	</div>
</body>
</html>