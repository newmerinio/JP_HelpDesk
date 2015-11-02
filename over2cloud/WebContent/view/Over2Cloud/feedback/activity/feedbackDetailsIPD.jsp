<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@page import="com.Over2Cloud.CommonClasses.DateUtil"%>
<%
String userType = session.getAttribute("userTpe") == null ? "" : session.getAttribute("userTpe").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
        <s:url var="chartDataUrl" action="json-chart-data"/>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.color
{
	background-color: #E6E6E6;
}
#ratingEditDiv11 select{
height: 104%;
border: 1px solid rgb(162, 159, 159);
}
</style>
<script type="text/javascript">

	  $.subscribe('makeEffect', function(event,data)
      {
          setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
      });
        
	function editShowView1111()
	{
		document.getElementById("ratingEditDiv11").style.display="block";
		document.getElementById("saveId").style.display="block";
    	document.getElementById("ratingDiv11").style.display="none";
    	document.getElementById("editId").style.display="none";
	}

</script>

</head>
<body> 
		<sj:div id="orglevel1Div"  effect="fold">
           <sj:div id="level1" cssStyle="align:center;"/>
       </sj:div>
				<div class="clear"></div>
                <div class="middle-content"></div>
<s:hidden name = "clientId" id="clientId" value="%{clientId}" />
<s:hidden name = "id" id="id" value="%{#parameters.id}" />
<div align="right" style="margin-top: 10px;">
<%-- <s:a cssClass="button" theme="simple" onclick="editShowView1111()" href="#" id="editId">Edit</s:a> --%>
</div>
<div class="clear"></div>
       <div class="middle-content">
             <div class="clear"></div>
             <div class="clear"></div>
                  <div class="container_block">
                        <div style=" float:left; padding:20px 1%; width:98%;">
                             <div class="clear"></div> 
                             <div class="clear"></div>
                            <table  align="center" border="0" width="90%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr class="color" >
	                                       <td align="left" ><b>MRD&nbsp;No</b></td><td align="center" ><s:property value="%{pojo.getClientId()}" /></td>
											<td align="left" ><b>Patient&nbsp;Name</b></td><td align="center" ><s:property value="%{pojo.getClientName()}" /></td>
											<td align="left" ><b>Mobile&nbsp;No</b></td><td align="center" ><s:property value="%{pojo.getMobNo()}" /></td>
											<td align="left" ><b>Email&nbsp;ID</b></td><td align="center" ><s:property value="%{pojo.getEmailId()}" /></td>
                                    </tr>
                                    <tr>
                                    	<td align="left" ><b>Room&nbsp;No</b></td><td align="center" ><s:property value="%{pojo.getCenterCode()}" /></td>
											<td align="left" ><b>Doctor&nbsp;Name</b></td><td align="center" ><s:property value="%{pojo.getDocName()}" /></td>
											<td align="left" ><b>Patient&nbsp;Type</b></td><td align="center" ><s:property value="%{pojo.getCompType()}" /></td>
											<td align="left" ><b>Visit&nbsp;Date</b></td><td align="center" ><s:property value="%{pojo.getAddmissionDate()}" /></td>
                                    </tr>
                                    <tr class="color">
                                    	<td align="left" ><b>Handled By</b></td><td align="center" ><s:property value="%{pojo.getHandledBy()}" /></td>
										<td align="left" ><b>Speciality</b></td><td align="center" ><s:property value="%{pojo.getCentreName()}" /></td>
										<td align="left" ><b>Company Type</b></td><td align="center" ><s:property value="%{pojo.getCompanytype()}" /></td>
										<td align="left" ><b>Discharged&nbsp;On</b></td><td align="center" ><s:property value="%{pojo.getDischargeDateTime()}" /></td>
                                    </tr>
                                    <tr>
                                    	<td align="left" ><b>Choose&nbsp;Hospital</b></td><td align="center" ><s:property value="%{pojo.getChoseHospital()}" /></td>
										<td align="left" ><b>Recommendation</b></td><td align="center" ><s:property value="%{pojo.getRecommend()}" /></td>
										<s:if test="%{pojo.getOverAll()=='No'}">
											<td align="left" ><b>Remarks</b></td><td align="center" >Negative</td>
										</s:if>
										<s:else>
											<td align="left" ><b>Remarks</b></td><td align="center" >Positive</td>
										</s:else>
										<%-- <td align="left" ><b>Overall&nbsp;Satisfied</b></td><td align="center" ><s:property value="%{pojo.getOverAll()}" /></td> --%>
										<td align="left" ><b>Feedback&nbsp;Date</b></td><td align="center" ><s:property value="%{pojo.getDateTime()}" /></td>
										<td align="center" ></td><td align="center" ></td>
                                    </tr>
                               </tbody>
                            </table>
                            <br><br>
<div id="ratingDiv11"> 
<div align="left" ><b>Comments: </b><s:property value="%{pojo.getComments()}" /></div>
<br>                           
<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%;  margin-left:1px; margin-right:1px;" >
    <div class="clear"></div>
           <div style="height:auto; margin-bottom:2px;" id='1stBlock'></div>
                  <div style="height:100%; margin-bottom:2px;" align="center"><B>Front Office/Admission</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                </tr>
 									<tr>  <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Ease of contacting the hospital:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getFront_ease()}" /></td>
                                    </tr>
                                    <tr>
                                           <td align="left" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries:</a></font></td>
                                           <td align="center" width="3%"><s:property value="%{pojo.getResponseToQueries()}" /></td>
                                    </tr>
                                    <tr>  <td align="left" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Counseling of Expenses & Packages:</a></font></td>
                                           <td align="center" width="3%"><s:property value="%{pojo.getCounselling()}" /></td>
                                    </tr>
                                    <tr>
                                           <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Time Taken for your Admission Process:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getAdmission()}" /></td>
                                    </tr>
                                    <tr>  <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Courtesy & Behavior:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getCourtesybehaviour()}" /></td>
                                    </tr>
                                    
                               </tbody>
                       </table>
                 </div>
             <div>
<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%; margin-left:1px; margin-right:1px;" >
  <div class="clear"></div>
     <div style="height:100%; margin-bottom:2px;"  id='2stBlock'></div>
         <div style="height:auto; margin-bottom:2px;" align="center" ><B>Doctors</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Attentive, Prompt and Caring:</a></font></td>
                                           <td align="center" width="17%"><s:property value="%{pojo.getAttentivePrCaring()}" /></td>
                                     </tr>
                                     <tr>  <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timely Response and Communication:</a></font></td>
                                           <td align="center" width="17%"><s:property value="%{pojo.getResponseCommDoctor()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Diagnosis:</a></font></td>
                                           <td align="center" width="13%"><s:property value="%{pojo.getDiagnosis()}" /></td>
                                     </tr>
                                     <tr>  <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Treatment:</a></font></td>
                                           <td align="center" width="13%"><s:property value="%{pojo.getTreatment()}" /></td>
                                     </tr>
                                      <tr>  <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                           <td align="center" width="13%"></td>
                                     </tr>
                                     <tr>  <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                           <td align="center" width="13%"></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>

</div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;">
<div class="clear"></div>
<div style="height:100%; margin-bottom:2px;" id='3stBlock'><div style="height:auto; margin-bottom:2px;" align="center" ><B>Nurses</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Attentive, Prompt and Caring:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getAttentiveprompt()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timely Response and Communication:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getResponseCommunication()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Procedures:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getProcedures()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Medication:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getMedication()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Care at home:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getCareAtHome()}" /></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:100%; margin-bottom:2px;" id='4stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Housekeeping</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Quality of Cleanliness and Upkeep:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getQualityUpkeep()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Behavior and Response Time:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getBehivourResponseTime()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Functioning and Maintenance of Equipments/ Facilities:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getFunctioningMainenance()}" /></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
<div class="clear"></div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:100%; margin-bottom:2px;" id='5stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>F & B/ Dietetics</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Quality of Food:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getQualityofFoods()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timeliness of Service:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getTimelineService()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Behavior and Response Time:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getBehivourResponse()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Assessment / Counseling by Dietitian:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getAssessmentCounseling()}" /></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>




<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='6stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Discharge</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries by TPA/ Billing Desk:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getResponsetoQuery()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Efficiency of TPA/ Billing Desk:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getEfficiencyBillingdesk()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Discharge Time:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getDischargetime()}" /></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='7stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Other Facilities</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Pharmacy Services:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getPharmacyservices()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cafeteria / Public Dining options:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getCafeteriaDyning()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Parking and Security Assistance:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getOverallsecurity()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Overall Level of Services:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getOverallservices()}" /></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
<div class="contentdiv-small" style="overflow: hidden; height:50%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='8stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Other Feedback</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Would you recommend us to others:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getRecommend()}" /></td>
                                     </tr>
                                       <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">You have chosen our Hospital because of:</a></font></td>
                                           <td align="center" width="5%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><s:property value="%{pojo.getChoseHospital()}" /></font></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">&nbsp&nbsp&nbsp&nbsp&nbsp</a></font></td>
                                           <td align="center" width="5%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>&nbsp&nbsp&nbsp</b></a></font></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>

</div>
</div>

</div>



<%-- <s:form id="editForm" name="editForm" action="editFeedbackDataViaPaper" namespace="/view/Over2Cloud/feedback"  theme="css_xhtml"  method="post">
<s:hidden name = "feedDataId" id="feedDataId" value="%{pojo.getFeedDataId()}" />
<div id="ratingEditDiv11" style="display: none;">

<div align="left" ><b>Comments:</b> <s:textarea id="comments" name="comments" cols="50" rows="5" theme="simple" value="%{pojo.getComments()}"></s:textarea></div>
	<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%;  margin-left:1px; margin-right:1px;" >
    <div class="clear"></div>
           <div style="height:auto; margin-bottom:2px;" id='1stBlock'></div>
                  <div style="height:100%; margin-bottom:2px;" align="center"><B>Front Office/Admission</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                </tr>
 									<tr>  <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Ease of contacting the hospital:</a></font></td>
                                           <td align="center" width="4%" height="2%">
                                           <s:select 
                           						id="front_ease"
                            					name="front_ease" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getFront_ease()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                    <tr>
                                           <td align="left" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries:</a></font></td>
                                           <td align="center" width="4%" height="2%">
                                            <s:select 
                           						id="responseToQueries"
                            					name="responseToQueries" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getResponseToQueries()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                    <tr>  <td align="left" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Counseling of Expenses & Packages:</a></font></td>
                                           <td align="center" width="4%" height="2%">
                                            <s:select 
                           						id="counselling"
                            					name="counselling" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getCounselling()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                    <tr>
                                           <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Time Taken for your Admission Process:</a></font></td>
                                           <td align="center" width="4%" height="2%" >
                                           <s:select 
                           						id="admission"
                            					name="admission" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getAdmission()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                    <tr>  <td align="left" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Courtesy & Behavior:</a></font></td>
                                           <td align="center" width="4%" height="2%">
                                           <s:select 
                           						id="courtesybehaviour"
                            					name="courtesybehaviour" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getCourtesybehaviour()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                    
                               </tbody>
                       </table>
                 </div>
             <div>
<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%; margin-left:1px; margin-right:1px;" >
  <div class="clear"></div>
     <div style="height:100%; margin-bottom:2px;"  id='2stBlock'></div>
         <div style="height:auto; margin-bottom:2px;" align="center" ><B>Doctors</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Attentive, Prompt and Caring:</a></font></td>
                                           <td align="center" width="17%">
                                           <s:select 
                           						id="attentivePrCaring"
                            					name="attentivePrCaring" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getAttentivePrCaring()}'
                        					>
                        				 </s:select>
                                          </td>
                                     </tr>
                                     <tr>  <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timely Response and Communication:</a></font></td>
                                           <td align="center" width="17%">
                                           <s:select 
                           						id="responseCommDoctor"
                            					name="responseCommDoctor" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getResponseCommDoctor()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Diagnosis:</a></font></td>
                                           <td align="center" width="13%">
                                           <s:select 
                           						id="diagnosis"
                            					name="diagnosis" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getDiagnosis()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Treatment:</a></font></td>
                                           <td align="center" width="13%">
                                           <s:select 
                           						id="treatment"
                            					name="treatment" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getTreatment()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>  <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                           <td align="center" width="13%"></td>
                                     </tr>
                                     <tr>  <td align="left" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                           <td align="center" width="13%"></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>

</div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;">
<div class="clear"></div>
<div style="height:100%; margin-bottom:2px;" id='3stBlock'><div style="height:auto; margin-bottom:2px;" align="center" ><B>Nurses</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Attentive, Prompt and Caring:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="attentiveprompt"
                            					name="attentiveprompt" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getAttentiveprompt()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timely Response and Communication:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="responseCommunication"
                            					name="responseCommunication" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getResponseCommunication()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Procedures:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="procedures"
                            					name="procedures" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getProcedures()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Medication:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="medication"
                            					name="medication" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getMedication()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Care at home:</a></font></td>
                                           <td align="center" width="5%" >
                                            <s:select 
                           						id="careAtHome"
                            					name="careAtHome" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getCareAtHome()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:100%; margin-bottom:2px;" id='4stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Housekeeping</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Quality of Cleanliness and Upkeep:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select 
                           						id="qualityUpkeep"
                            					name="qualityUpkeep" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getQualityUpkeep()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Behavior and Response Time:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select 
                           						id="behivourResponseTime"
                            					name="behivourResponseTime" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getBehivourResponseTime()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Functioning and Maintenance of Equipments/ Facilities:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select 
                           						id="functioningMainenance"
                            					name="functioningMainenance" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getFunctioningMainenance()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
<div class="clear"></div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:100%; margin-bottom:2px;" id='5stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>F & B/ Dietetics</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Quality of Food:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="qualityofFoods"
                            					name="qualityofFoods" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getQualityofFoods()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Timeliness of Service:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="timelineService"
                            					name="timelineService" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getTimelineService()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Behavior and Response Time:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="behivourResponse"
                            					name="behivourResponse" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getBehivourResponse()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Assessment / Counseling by Dietitian:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="assessmentCounseling"
                            					name="assessmentCounseling" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getAssessmentCounseling()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>




<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='6stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Discharge</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries by TPA/ Billing Desk:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="responsetoQuery"
                            					name="responsetoQuery" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getResponsetoQuery()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Efficiency of TPA/ Billing Desk:</a></font></td>
                                           <td align="center" width="5%"> <s:select 
                           						id="efficiencyBillingdesk"
                            					name="efficiencyBillingdesk" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getEfficiencyBillingdesk()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Discharge Time:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select 
                           						id="dischargetime"
                            					name="dischargetime" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getDischargetime()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='7stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Other Facilities</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Pharmacy Services:</a></font></td>
                                           <td align="center" width="5%" >
                                           <s:select 
                           						id="pharmacyservices"
                            					name="pharmacyservices" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getPharmacyservices()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cafeteria / Public Dining options:</a></font></td>
                                           <td align="center" width="5%" >
                                           <s:select 
                           						id="cafeteriaDyning"
                            					name="cafeteriaDyning" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getCafeteriaDyning()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Parking and Security Assistance:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select 
                           						id="overallsecurity"
                            					name="overallsecurity" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getOverallsecurity()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Overall Level of Services:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select 
                           						id="overallservices"
                            					name="overallservices" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getOverallservices()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
<div class="contentdiv-small" style="overflow: hidden; height:50%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='8stBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Other Feedback</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Recommend&nbsp;us&nbsp;to&nbsp;others:</a></font></td>
                                           <td align="center" width="5%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2">
                                            <s:select 
                           						id="targetOn"
                            					name="targetOn" 
                          					 	list="{'Yes','No'}" 
                            					theme="simple"
                            					value='%{pojo.getRecommend()}'
                        					>
                        				 </s:select></font></td>
                                     </tr>
                                      <tr>  <td align="right" width="10%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Chosen&nbsp;us&nbsp;because&nbsp;of:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select 
                           						id="choseHospital"
                            					name="choseHospital" 
                          					 	list="#{'Physician':'Physician','Friends & Relatives':'Friends & Relatives','TPA':'TPA','Corp-Tie Ups':'Corp-Tie Ups','Adv./Website':'Adv./Website','Others':'Others'}"
                            					theme="simple"
                            					value='%{pojo.getChoseHospital()}'
                            					cssStyle="width:92px;"
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Overall&nbsp;Satisfied:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select 
                           						id="overAll"
                            					name="overAll" 
                          					 	list="{'Yes','No'}" 
                            					theme="simple"
                            					value='%{pojo.getOverAll()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">&nbsp&nbsp&nbsp&nbsp&nbsp</a></font></td>
                                           <td align="center" width="5%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>&nbsp&nbsp&nbsp</b></a></font></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>
</div>
 			<div style="padding-bottom: 10px;" align="center">
                        <sj:submit 
                        	 id="saveId"
                             targets="level1" 
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#222222'}"
                             effectDuration="5000"
                             button="true"
                             cssStyle="margin-left: -890px;margin-top: 450px;display:none;"
                             onCompleteTopics="makeEffect"
                        />
              </div>
</s:form> 
</div>--%>

</body>
</html>