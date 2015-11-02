<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
String userType = session.getAttribute("userTpe") == null ? "" : session.getAttribute("userTpe").toString();
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<STYLE type="text/css">

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
#opdEditView select{
height: 104%;
border: 1px solid rgb(162, 159, 159);
}
</STYLE>
<script type="text/javascript">
		$.subscribe('makeEffect', function(event,data)
	      {
	          setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	          setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	      });
function editShowView11()
{
	document.getElementById("opdEditView").style.display="block";
	document.getElementById("saveIdOPD").style.display="block";
	document.getElementById("OPDRatingView").style.display="none";
	document.getElementById("editIdOPD").style.display="none";
}

</script>
</head>
<body>
		<sj:div id="orglevel1Div"  effect="fold" >
           <sj:div id="level1" cssStyle="align:center;"/>
       </sj:div>
      <div align="right" style="margin-top: 10px;">
<%-- <s:if test="%{userType=='M'}" >
		<s:a cssClass="button" theme="simple" onclick="editShowView11()" href="#" id="editIdOPD">Edit</s:a>
</s:if> --%>
	 </div>	
	<div>
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
              <br>
     <div id="OPDRatingView">    
              <div style="width: 98%;margin-left: 10px;" align="left">            
		      <b style="">Comments: </b><s:property value="%{pojo.getComments()}" /></div>
			<br>			
		<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%;  margin-left:1px; margin-right:1px;" >
    <div class="clear"></div>
           <div style="height:auto; margin-bottom:2px;" id='1stBlock'></div>
                  <div style="height:100%; margin-bottom:2px;" align="center"><B>Front Office/Call Center</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                </tr>
 									<tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Ease of contacting the hospital:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getEase_Contact()}" /></td>
                                    </tr>
                                    <tr>
                                           <td align="right" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries:</a></font></td>
                                           <td align="center" width="3%"><s:property value="%{pojo.getResponseToQueries()}" /></td>
                                    </tr>
                                    <tr>  <td align="right" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Politeness and Courteousness:</a></font></td>
                                           <td align="center" width="3%"><s:property value="%{pojo.getPolndCourt()}" /></td>
                                    </tr>
                                    <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Registration and Billing Services:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getRegndBill()}" /></td>
                                    </tr>
                                  
                                    
                               </tbody>
                       </table>
                 </div>
             <div>
<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%; margin-left:1px; margin-right:1px;" >
  <div class="clear"></div>
     <div style="height:100%; margin-bottom:2px;"  id='2ndBlock'></div>
         <div style="height:auto; margin-bottom:2px;" align="center" ><B>Doctors</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Waiting time for consultation:</a></font></td>
                                           <td align="center" width="17%"><s:property value="%{pojo.getWait4Consltnt()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Diagnosis:</a></font></td>
                                           <td align="center" width="17%"><s:property value="%{pojo.getDiagnosis()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Treatment:</a></font></td>
                                           <td align="center" width="13%"><s:property value="%{pojo.getTreatment()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Medication:</a></font></td>
                                           <td align="center" width="13%"><s:property value="%{pojo.getMedication()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Test/Procedures:</a></font></td>
                                           <td align="center" width="13%"><s:property value="%{pojo.getCareAtHome()}" /></td>
                                     </tr>
                                      <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                           <td align="center" width="13%"></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                           <td align="center" width="13%"></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>

</div>

<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:100%; margin-bottom:2px;" id='3rdBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Housekeeping</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cleanliness Of Washrooms:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getCleanWashroom()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Unkeep of the facility:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getUnkeepFacility()}" /></td>
                                     </tr>
                                     
                               </tbody>
                       </table>
              </div>
</div>

<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;">
<div class="clear"></div>
<div style="height:100%; margin-bottom:2px;" id='4thBlock'><div style="height:auto; margin-bottom:2px;" align="center" ><B>Diagnostics</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Sample Collection:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getSampleColl()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Lab Services:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getLabServices()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Radiology Services:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getRadiologyServices()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cardiology Services:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getCardiology()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Endoscopy:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getEndoscopy()}" /></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Neurology:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getNeurology()}"/></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Urology:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getUrology()}" /></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Preventive Health Check-Up:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getPreHealth()}" /></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Any Other Please Specify:</a></font></td>
                                           <td align="center" width="5%" ><s:property value="%{pojo.getAnyOther()}" /></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>

<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='5thBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Facilities</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Location/ Directional Signages:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getLocndDirSignages()}" /></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Chemist Shop:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getChemistShop()}" /></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Waiting Areas:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getWaitingAreas()}" /></td>
                                     </tr>
                                      <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cafeteria:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getCafeteria()}" /></td>
                                     </tr>
                                      <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Parking Services:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getParking()}" /></td>
                                     </tr>
                                      <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Security Assistance:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getSecurity()}" /></td>
                                     </tr>
                                    <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Overall Level of Service:</a></font></td>
                                           <td align="center" width="5%"><s:property value="%{pojo.getOverallService()}" /></td>
                                     </tr> 
                               </tbody>
                       </table>
              </div>
	</div>
</div>
</div>     

	<%-- <div id="opdEditView" style="display: none">
	<s:form id="editForm" name="editForm" action="editFeedbackDataViaPaper" namespace="/view/Over2Cloud/feedback"  theme="css_xhtml"  method="post">
		<s:hidden name = "feedDataId" id="feedDataId" value="%{pojo.getFeedDataId()}" />
	
		<div align="left" ><b>Comments:</b> <s:textarea id="comments" name="comments" cols="50" rows="5" theme="simple" value="%{pojo.getComments()}"></s:textarea></div>
		<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%;  margin-left:1px; margin-right:1px;" >
    <div class="clear"></div>
           <div style="height:auto; margin-bottom:2px;" id='1stBlock'></div>
                  <div style="height:100%; margin-bottom:2px;" align="center"><B>Front Office/Call Center</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                </tr>
 									<tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Ease of contacting the hospital:</a></font></td>
                                           <td align="center" width="5%">
                                           	<s:select
                                           		id="ease_Contact"
                            					name="ease_Contact" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getEase_Contact()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                    <tr>
                                           <td align="right" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries:</a></font></td>
                                           <td align="center" width="3%">
                                           <s:select
                                           		id="res_queries"
                            					name="res_queries" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getResponseToQueries()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                    <tr>  <td align="right" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Politeness and Courteousness:</a></font></td>
                                           <td align="center" width="3%">
                                           <s:select
                                           		id="polndCourt"
                            					name="polndCourt" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getPolndCourt()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                    <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Registration and Billing Services:</a></font></td>
                                           <td align="center" width="5%" >
                                           <s:select
                                           		id="regndBill"
                            					name="regndBill" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getRegndBill()}'
                        					>
                        				 </s:select></td>
                                    </tr>
                                  
                                    
                               </tbody>
                       </table>
                 </div>
             <div>
<div class="contentdiv-small" style="overflow: hidden;  height:40%; width:22%; margin-left:1px; margin-right:1px;" >
  <div class="clear"></div>
     <div style="height:100%; margin-bottom:2px;"  id='2ndBlock'></div>
         <div style="height:auto; margin-bottom:2px;" align="center" ><B>Doctors</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Waiting time for consultation:</a></font></td>
                                           <td align="center" width="17%">
                                           <s:select
                                           		id="wait4Consltnt"
                            					name="wait4Consltnt" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getWait4Consltnt()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Diagnosis:</a></font></td>
                                           <td align="center" width="17%">
                                           <s:select
                                           		id="diagnosis"
                            					name="diagnosis" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getDiagnosis()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Treatment:</a></font></td>
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
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Medication:</a></font></td>
                                           <td align="center" width="13%">
                                           <s:select
                                           		id="medication"
                            					name="medication" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getMedication()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Test/Procedures:</a></font></td>
                                           <td align="center" width="13%">
                                           <s:select
                                           		id="careAtHome"
                            					name="careAtHome" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getCareAtHome()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                           <td align="center" width="13%"></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                           <td align="center" width="13%"></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>

</div>

<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:100%; margin-bottom:2px;" id='3rdBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Housekeeping</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cleanliness Of Washrooms:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select
                                           		id="cleanWashroom"
                            					name="cleanWashroom" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getCleanWashroom()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Unkeep of the facility:</a></font></td>
                                           <td align="center" width="5%">
                                            <s:select
                                           		id="unkeepFacility"
                            					name="unkeepFacility" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getUnkeepFacility()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     
                               </tbody>
                       </table>
              </div>
</div>

<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;">
<div class="clear"></div>
<div style="height:100%; margin-bottom:2px;" id='4thBlock'><div style="height:auto; margin-bottom:2px;" align="center" ><B>Diagnostics</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Sample Collection:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="sampleColl"
                            					name="sampleColl" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getSampleColl()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Lab Services:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="labServices"
                            					name="labServices" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getLabServices()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Radiology Services:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="radiologyServices"
                            					name="radiologyServices" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getRadiologyServices()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cardiology Services:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="cardiology"
                            					name="cardiology" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getCardiology()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Endoscopy:</a></font></td>
                                           <td align="center" width="5%" >
                                           <s:select
                                           		id="endoscopy"
                            					name="endoscopy" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getEndoscopy()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Neurology:</a></font></td>
                                           <td align="center" width="5%" >
                                           <s:select
                                           		id="neurology"
                            					name="neurology" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getNeurology()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Urology:</a></font></td>
                                           <td align="center" width="5%" >
                                           <s:select
                                           		id="urology"
                            					name="urology" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getUrology()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Preventive Health Check-Up:</a></font></td>
                                           <td align="center" width="5%" >
                                           <s:select
                                           		id="preHealth"
                            					name="preHealth" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getPreHealth()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Any Other Please Specify:</a></font></td>
                                           <td align="center" width="5%" >
                                           <s:select
                                           		id="anyOther"
                            					name="anyOther" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getAnyOther()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                               </tbody>
                       </table>
              </div>
</div>

<div class="contentdiv-small" style="overflow: hidden; height:40%; width:22%; margin-left:1px; margin-right:1px;" >
<div class="clear"></div>
   <div style="height:auto; margin-bottom:2px;" id='5thBlock'><div style="height:auto; margin-bottom:2px;" align="center"><B>Facilities</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
                                    </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Location/ Directional Signages:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="locndDirSignages"
                            					name="locndDirSignages" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getLocndDirSignages()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Chemist Shop:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="chemistShop"
                            					name="chemistShop" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getChemistShop()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Waiting Areas:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="waitingAreas"
                            					name="waitingAreas" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getWaitingAreas()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cafeteria:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="cafeteria"
                            					name="cafeteria" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getCafeteria()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Parking Services:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="parking"
                            					name="parking" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getParking()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                      <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Security Assistance:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="security"
                            					name="security" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getSecurity()}'
                        					>
                        				 </s:select></td>
                                     </tr>
                                    <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Overall Level of Service:</a></font></td>
                                           <td align="center" width="5%">
                                           <s:select
                                           		id="overallService"
                            					name="overallService" 
                          					 	list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            					theme="simple"
                            					value='%{pojo.getOverallService()}'
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
		<div style="padding-bottom: 10px;" align="center">
                        <sj:submit 
                        	 id="saveIdOPD"
                             targets="level1" 
                             value="Save" 
                              effect="highlight"
                             effectOptions="{ color : '#222222'}"
                             effectDuration="5000"
                             button="true"
                             cssStyle="margin-left: -340px;margin-top: 395px;display:none;"
                             onCompleteTopics="makeEffect"
                        />
              </div>

	</s:form>

	</div>
 --%>
</body>
</html>