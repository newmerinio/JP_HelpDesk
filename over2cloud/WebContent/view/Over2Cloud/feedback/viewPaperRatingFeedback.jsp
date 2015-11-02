<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@page import="com.Over2Cloud.CommonClasses.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
        <s:url var="chartDataUrl" action="json-chart-data"/>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function loadPaper()
{
var mrdNo = $("#clientId").val();
 var id= $("#id").val();
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/viewpccActionOnPaperFeedback.action?clientId="+mrdNo+"&id="+id,
        success : function(data) {
        	if(data.paperViewObjIPD.front_ease == null){$("#front_ease").html("");}
            else {$("#front_ease").html(data.paperViewObjIPD.front_ease);}
      				if(data.paperViewObjIPD.responseToQueries == null){$("#responseToQueries").html("");}
	                else {$("#responseToQueries").html(data.paperViewObjIPD.responseToQueries);}
	                if(data.paperViewObjIPD.counselling == null){$("#counselling").html("");}
	                else {$("#counselling").html(data.paperViewObjIPD.counselling);}
	                if(data.paperViewObjIPD.admission == null){$("#admission").html("");}
	                else {$("#admission").html(data.paperViewObjIPD.admission);}
	                if(data.paperViewObjIPD.courtesybehaviour == null){$("#courtesybehaviour").html("");}
	                else {$("#courtesybehaviour").html(data.paperViewObjIPD.courtesybehaviour);}
	                if(data.paperViewObjIPD.attentivePrCaring == null){$("#attentivePrCaring").html("");}
	                else {$("#attentivePrCaring").html(data.paperViewObjIPD.attentivePrCaring);}
	                if(data.paperViewObjIPD.responseCommDoctor == null){$("#responseCommDoctor").html("");}
	                else {$("#responseCommDoctor").html(data.paperViewObjIPD.responseCommDoctor);}
	                if(data.paperViewObjIPD.diagnosis == null){$("#diagnosis").html("");}
	                else {$("#diagnosis").html(data.paperViewObjIPD.diagnosis);}
	                if(data.paperViewObjIPD.treatment == null){$("#treatment").html("");}
	                else {$("#treatment").html(data.paperViewObjIPD.treatment);}
	                if(data.paperViewObjIPD.attentiveprompt == null){$("#attentiveprompt").html("");}
	                else {$("#attentiveprompt").html(data.paperViewObjIPD.attentiveprompt);}
	                if(data.paperViewObjIPD.responseCommunication == null){$("#responseCommunication").html("");}
	                else {$("#responseCommunication").html(data.paperViewObjIPD.responseCommunication);}
	                if(data.paperViewObjIPD.procedures == null){$("#procedures").html("");}
	                else {$("#procedures").html(data.paperViewObjIPD.procedures);}
	                if(data.paperViewObjIPD.medication == null){$("#medication").html("");}
	                else {$("#medication").html(data.paperViewObjIPD.medication);}
	                if(data.paperViewObjIPD.careAtHome == null){$("#careAtHome").html("");}
	                else {$("#careAtHome").html(data.paperViewObjIPD.careAtHome);}
	                if(data.paperViewObjIPD.qualityUpkeep == null){$("#qualityUpkeep").html("");}
	                else {$("#qualityUpkeep").html(data.paperViewObjIPD.qualityUpkeep);}
	                if(data.paperViewObjIPD.behivourResponseTime == null){$("#behivourResponseTime").html("");}
	                else {$("#behivourResponseTime").html(data.paperViewObjIPD.behivourResponseTime);}
	                if(data.paperViewObjIPD.functioningMainenance == null){$("#functioningMainenance").html("");}
	                else {$("#functioningMainenance").html(data.paperViewObjIPD.functioningMainenance);}
	                if(data.paperViewObjIPD.qualityofFoods == null){$("#qualityofFoods").html("");}
	                else {$("#qualityofFoods").html(data.paperViewObjIPD.qualityofFoods);}
	                if(data.paperViewObjIPD.timelineService == null){$("#timelineService").html("");}
	                else {$("#timelineService").html(data.paperViewObjIPD.timelineService);}
	                if(data.paperViewObjIPD.behivourResponse == null){$("#behivourResponse").html("");}
	                else {$("#behivourResponse").html(data.paperViewObjIPD.behivourResponse);}
	                if(data.paperViewObjIPD.assessmentCounseling == null){$("#assessmentCounseling").html("");}
	                else {$("#assessmentCounseling").html(data.paperViewObjIPD.assessmentCounseling);}
	                if(data.paperViewObjIPD.responsetoQuery == null){$("#responsetoQuery").html("");}
	                else {$("#responsetoQuery").html(data.paperViewObjIPD.responsetoQuery);}
	                if(data.paperViewObjIPD.efficiencyBillingdesk == null){$("#efficiencyBillingdesk").html("");}
	                else {$("#efficiencyBillingdesk").html(data.paperViewObjIPD.efficiencyBillingdesk);}
	                if(data.paperViewObjIPD.dischargetime == null){$("#dischargetime").html("");}
	                else {$("#dischargetime").html(data.paperViewObjIPD.dischargetime);}
	                if(data.paperViewObjIPD.pharmacyservices == null){$("#pharmacyservices").html("");}
	                else {$("#pharmacyservices").html(data.paperViewObjIPD.pharmacyservices);}
	                if(data.paperViewObjIPD.cafeteriaDyning == null){$("#cafeteriaDyning").html("");}
	                else {$("#cafeteriaDyning").html(data.paperViewObjIPD.cafeteriaDyning);}
	                if(data.paperViewObjIPD.overallsecurity == null){$("#overallsecurity").html("");}
	                else {$("#overallsecurity").html(data.paperViewObjIPD.overallsecurity);}
	                if(data.paperViewObjIPD.overallservices == null){$("#overallservices").html("");}
	                else {$("#overallservices").html(data.paperViewObjIPD.overallservices);}
	                if(data.paperViewObjIPD.recommend == null){$("#recommend").html("");}
	                else {
	                if(data.paperViewObjIPD.recommend == '1'){
	                	 $("#recommend").html('Yes');
	                    }else
	                    {
	                     $("#recommend").html('No');
	                    }
	                }
	                if(data.paperViewObjIPD.choseHospital == null){$("#choseHospital").html("");}
	                else {$("#choseHospital").html(data.paperViewObjIPD.choseHospital);}
	                 if(data.paperViewObjIPD.avgFronOffice == null){$("#avgFronOffice").html("");}
	                else {$("#avgFronOffice").html(data.paperViewObjIPD.avgFronOffice);}
	                 if(data.paperViewObjIPD.avgDoctotrs == null){$("#avgDoctotrs").html("");}
	                else {$("#avgDoctotrs").html(data.paperViewObjIPD.avgDoctotrs);}
	                 if(data.paperViewObjIPD.avgNurses == null){$("#avgNurses").html("");}
	                else {$("#avgNurses").html(data.paperViewObjIPD.avgNurses);}
	                 if(data.paperViewObjIPD.avgHosekeeping == null){$("#avgHosekeeping").html("");}
	                else {$("#avgHosekeeping").html(data.paperViewObjIPD.avgHosekeeping);}
	                 if(data.paperViewObjIPD.avgDiatics == null){$("#avgDiatics").html("");}
	                else {$("#avgDiatics").html(data.paperViewObjIPD.avgDiatics);}
	                 if(data.paperViewObjIPD.avgDischarge == null){$("#avgDischarge").html("");}
	                else {$("#avgDischarge").html(data.paperViewObjIPD.avgDischarge);}
	                 if(data.paperViewObjIPD.avgOthers == null){$("#avgOthers").html("");}
	                else {$("#avgOthers").html(data.paperViewObjIPD.avgOthers);}
	                if(data.paperViewObjIPD.totalAverage == null){$("#totalAverage").html("");}
	                else {$("#totalAverage").html(data.paperViewObjIPD.totalAverage);}
	                
	                
    },
       error: function() {
            alert("error");
        }
     });
}
loadPaper();
</script>
</head>
<body> 
				<div class="clear"></div>
                <div class="middle-content">
<s:hidden name = "clientId" id="clientId" value="%{clientId}" />
<s:hidden name = "id" id="id" value="%{#parameters.id}" />

<div class="clear"></div>
       <div class="middle-content">
             <div class="clear"></div>
             <div class="clear"></div>
                  <div class="container_block">
                        <div style=" float:left; padding:20px 1%; width:98%;">
                             <div class="clear"></div> 
                             <div class="clear"></div>
              
                       
                            <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="FF3333"" bordercolor="black" >
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Patient Id</b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Name</b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Mobile No:</b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Doctor Name</b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Specialty</b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Patient type</b></font></td>
	                                     <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Room No.</b></font></td>
	                                      
                                    </tr>
                                    <tr bgcolor="#FFFFFF" bordercolor="black" >
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><s:property value="%{clientId}"></s:property></b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><s:property value="%{#parameters.clientName}"></s:property></b></font></td>
	                                     <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><s:property value="%{#parameters.mobNo}"></s:property></b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><s:property value="%{#parameters.serviceBy}"></s:property></b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif"  color="#000000" size="2"><b><s:property value="%{#parameters.centreName}"></s:property></b></font></td>
	                                       <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><s:property value="%{#parameters.compType}"></s:property></b></font></td>
	                                     <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b><s:property value="%{#parameters.centerCode}"></s:property></b></font></td>
	                                      
                                    </tr>
                                     

                               </tbody>
                            </table>
                            <br><br>
     
                             
                                                  
<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%;  margin-left:1px; margin-right:1px;" >
    <div class="clear"></div>
           <div style="height:auto; margin-bottom:2px;" id='1stBlock'></div>
                  <div style="height:100%; margin-bottom:2px;" ><B>Front office/Admission</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                </tr>
 
                                    <tr>
                                           <td align="right" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries:</a></font></td>
                                           <td align="center" width="3%" id="responseToQueries"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr>  <td align="right" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Counseling of Expenses & Packages:</a></font></td>
                                           <td align="center" width="3%" id="counselling"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Time Taken for your Admission Process:</a></font></td>
                                           <td align="center" width="5%" id="admission"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Courtesy & Behavior:</a></font></td>
                                           <td align="center" width="5%" id="courtesybehaviour"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Ease of contacting the hospital:</a></font></td>
                                           <td align="center" width="5%" id="front_ease"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr></tr>
                                    <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Average:<b></a></font></td>
                                           <td align="center" width="5%" id="avgFronOffice"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>

                               </tbody>
                       </table>
                 </div>
             <div>
      
                 
<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%; margin-left:1px; margin-right:1px;" >
  <div class="clear"></div>
     <div style="height:100%; margin-bottom:2px;"  id='2stBlock'></div>
         <div style="height:auto; margin-bottom:2px;" ><B>Doctors</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
 
                                     <tr>
                                           <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Attentive, Prompt and Caring:</a></font></td>
                                           <td align="center" width="17%" id="attentivePrCaring"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timely Response and Communication:</a></font></td>
                                           <td align="center" width="17%" id="responseCommDoctor"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Diagnosis:</a></font></td>
                                           <td align="center" width="13%" id="diagnosis"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Treatment :</a></font></td>
                                           <td align="center" width="13%" id="treatment"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">&nbsp&nbsp&nbsp&nbsp&nbsp</a></font></td>
                                           <td align="center" width="5%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>&nbsp&nbsp&nbsp</b></a></font></td>
                                     </tr>
                                     
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Average:<b></a></font></td>
                                           <td align="center" width="13%" id="avgDoctotrs"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                                
                               </tbody>
                       </table>
              </div>

</div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;">
<div class="clear"></div>
<div style="height:100%; margin-bottom:2px;" id='3stBlock'><div style="height:auto; margin-bottom:2px;" ><B>Nurses</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
 
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Attentive, Prompt and Caring:</a></font></td>
                                           <td align="center" width="5%" id="attentiveprompt"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timely Response and Communication:</a></font></td>
                                           <td align="center" width="5%" id="responseCommunication"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Procedures:</a></font></td>
                                           <td align="center" width="5%" id="procedures"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Medication:</a></font></td>
                                           <td align="center" width="5%" id="medication"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Care at home:</a></font></td>
                                           <td align="center" width="5%" id="careAtHome"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     
                                     <tr></tr><tr></tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Average<b></a></font></td>
                                           <td align="center" width="5%" id="avgNurses"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>

                               </tbody>
                       </table>
              </div>

</div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:100%; margin-bottom:2px;" id='4stBlock'><div style="height:auto; margin-bottom:2px;" ><B>Housekeeping</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
 
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Quality of Cleanliness and Upkeep:</a></font></td>
                                           <td align="center" width="5%" id="qualityUpkeep"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Behavior and Response Time:</a></font></td>
                                           <td align="center" width="5%" id="behivourResponseTime"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Functioning and Maintenance of Equipments/ Facilities:</a></font></td>
                                           <td align="center" width="5%" id="functioningMainenance"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     
                                     <tr></tr><tr></tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Average:<b></a></font></td>
                                           <td align="center" width="5%" id="avgHosekeeping"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>

                               </tbody>
                       </table>
              </div>
</div>


<div class="clear"></div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:100%; margin-bottom:2px;" id='5stBlock'><div style="height:auto; margin-bottom:2px;" ><B>F & B/ Dietetics</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
 
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Quality of Food:</a></font></td>
                                           <td align="center" width="5%" id="qualityofFoods"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timeliness of Service:</a></font></td>
                                           <td align="center" width="5%" id="timelineService"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Behavior and Response Time:</a></font></td>
                                           <td align="center" width="5%" id="behivourResponse"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Assessment / Counseling by Dietitian:</a></font></td>
                                           <td align="center" width="5%" id="assessmentCounseling"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr></tr><tr></tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Average:<b></a></font></td>
                                           <td align="center" width="5%" id="avgDiatics"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>

                               </tbody>
                       </table>
              </div>
</div>




<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='6stBlock'><div style="height:auto; margin-bottom:2px;" ><B>Discharge</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
 
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries by TPA/ Billing Desk:</a></font></td>
                                           <td align="center" width="5%" id="responsetoQuery"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Efficiency of TPA/ Billing Desk:</a></font></td>
                                           <td align="center" width="5%" id="efficiencyBillingdesk"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Discharge Time:</a></font></td>
                                           <td align="center" width="5%" id="dischargetime"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     
                                     <tr></tr><tr></tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Average:<b></a></font></td>
                                           <td align="center" width="5%" id="avgDischarge"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>

                               </tbody>
                       </table>
              </div>
</div>






<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='7stBlock'><div style="height:auto; margin-bottom:2px;" ><B>Other Facilities</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
 
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Pharmacy Services:</a></font></td>
                                           <td align="center" width="5%" id="pharmacyservices"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cafeteria / Public Dining options:</a></font></td>
                                           <td align="center" width="5%" id="cafeteriaDyning"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Parking and Security Assistance:</a></font></td>
                                           <td align="center" width="5%" id="overallsecurity"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Overall Level of Services:</a></font></td>
                                           <td align="center" width="5%" id="overallservices"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr></tr><tr></tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Average:<b></a></font></td>
                                           <td align="center" width="5%" id="avgOthers"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>

                               </tbody>
                       </table>
              </div>
</div>





<div class="contentdiv-small" style="overflow: hidden; height:50%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='8stBlock'><div style="height:auto; margin-bottom:2px;" ><B>Other Feedback</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">chosen our Hospital because of:</a></font></td>
                                           <td align="center" width="5%" id="choseHospital"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Physician</b></a></font></td>
                                     </tr>
                                       <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">&nbsp&nbsp&nbsp&nbsp&nbsp</a></font></td>
                                           <td align="center" width="5%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>&nbsp&nbsp&nbsp</b></a></font></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">&nbsp&nbsp&nbsp&nbsp&nbsp</a></font></td>
                                           <td align="center" width="5%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>&nbsp&nbsp&nbsp</b></a></font></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Total Average:</b></a></font></td>
                                           <td align="center" width="5%" id="totalAverage"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     
                               </tbody>
                       </table>
              </div>
</div>



</div>
<div>

</div>
</div>
</body>
</html>