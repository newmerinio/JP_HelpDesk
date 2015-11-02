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
function loadPaperData()
{
var mrdNo = $("#clientId").val();
 var id= $("#id").val();
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/viewpccActionOnPaperFeedbackOPD.action?clientId="+mrdNo+"&id="+id,
        success : function(data) {
      				if(data.paperViewObj.ease_Contact == null){$("#ease_Contact").html("");}
	                else {$("#ease_Contact").html(data.paperViewObj.ease_Contact);}
	                if(data.paperViewObj.res_queries == null){$("#res_queries").html("");}
	                else {$("#res_queries").html(data.paperViewObj.res_queries);}
	                if(data.paperViewObj.polndCourt == null){$("#polndCourt").html("");}
	                else {$("#polndCourt").html(data.paperViewObj.polndCourt);}
	                if(data.paperViewObj.regndBill == null){$("#regndBill").html("");}
	                else {$("#regndBill").html(data.paperViewObj.regndBill);}
	                if(data.paperViewObj.wait4Consltnt == null){$("#wait4Consltnt").html("");}
	                else {$("#wait4Consltnt").html(data.paperViewObj.wait4Consltnt);}
	                if(data.paperViewObj.diagnosis == null){$("#diagnosis").html("");}
	                else {$("#diagnosis").html(data.paperViewObj.diagnosis);}
	                if(data.paperViewObj.diagnosis == null){$("#treatment").html("");}
	                else {$("#treatment").html(data.paperViewObj.treatment);}
	                if(data.paperViewObj.treatment == null){$("#medication").html("");}
	                else {$("#medication").html(data.paperViewObj.medication);}
	                if(data.paperViewObj.sampleColl == null){$("#sampleColl").html("");}
	                else {$("#sampleColl").html(data.paperViewObj.sampleColl);}
	                if(data.paperViewObj.labServices == null){$("#labServices").html("");}
	                else {$("#labServices").html(data.paperViewObj.labServices);}
	                if(data.paperViewObj.radiologyServices == null){$("#radiologyServices").html("");}
	                else {$("#radiologyServices").html(data.paperViewObj.radiologyServices);}
					if(data.paperViewObj.cardiology==null)
					{
						$("#cardiology").html("");
					}
					else
					{
						$("#cardiology").html(data.paperViewObj.cardiology);
					}

					if(data.paperViewObj.endoscopy==null)
					{
						$("#endoscopy").html("");
					}
					else
					{
						$("#endoscopy").html(data.paperViewObj.endoscopy);
					}
	                
	                if(data.paperViewObj.medication == null){$("#careAtHome").html("");}
	                else {$("#careAtHome").html(data.paperViewObj.careAtHome);}
	                if(data.paperViewObj.careAtHome == null){$("#neurology").html("");}
	                else {$("#neurology").html(data.paperViewObj.neurology);}
	                if(data.paperViewObj.urology == null){$("#urology").html("");}
	                else {$("#urology").html(data.paperViewObj.urology);}
	                if(data.paperViewObj.preHealth == null){$("#preHealth").html("");}
	                else {$("#preHealth").html(data.paperViewObj.preHealth);}
	                if(data.paperViewObj.anyOther == null){$("#anyOther").html("");}
	                else {$("#anyOther").html(data.paperViewObj.anyOther);}
	                if(data.paperViewObj.cleanWashroom == null){$("#cleanWashroom").html("");}
	                else {$("#cleanWashroom").html(data.paperViewObj.cleanWashroom);}
	                if(data.paperViewObj.unkeepFacility == null){$("#unkeepFacility").html("");}
	                else {$("#unkeepFacility").html(data.paperViewObj.unkeepFacility);}
	                if(data.paperViewObj.locndDirSignages == null){$("#locndDirSignages").html("");}
	                else {$("#locndDirSignages").html(data.paperViewObj.locndDirSignages);}
	                if(data.paperViewObj.chemistShop == null){$("#chemistShop").html("");}
	                else {$("#chemistShop").html(data.paperViewObj.chemistShop);}
	                if(data.paperViewObj.waitingAreas == null){$("#waitingAreas").html("");}
	                else {$("#waitingAreas").html(data.paperViewObj.waitingAreas);}
	                if(data.paperViewObj.cafeteria == null){$("#cafeteria").html("");}
	                else {$("#cafeteria").html(data.paperViewObj.cafeteria);}
	                if(data.paperViewObj.parking == null){$("#parking").html("");}
	                else {$("#parking").html(data.paperViewObj.parking);}
	                if(data.paperViewObj.security == null){$("#security").html("");}
	                else {$("#security").html(data.paperViewObj.security);}
	                if(data.paperViewObj.overallService == null){$("#overallService").html("");}
	                else {$("#overallService").html(data.paperViewObj.overallService);}
	                if(data.paperViewObj.targetOn == null){$("#recommend").html("");}
	                else {$("#recommend").html(data.paperViewObj.targetOn);}
	                if(data.paperViewObj.choseHospital == null){$("#choseHospital").html("");}
	                else {$("#choseHospital").html(data.paperViewObj.choseHospital);}
	                if(data.paperViewObj.recommend == null){$("#recommend").html("");}
	                else {
	                if(data.paperViewObj.recommend == '1'){
	                	 $("#recommend").html('Yes');
	                    }else
	                    {
	                     $("#recommend").html('No');
	                    }
	                }
	               if(data.paperViewObj.avgFronOffice == null)
		           {$("#avgFronOffice").html("");
		           }
	               else
		           {
			           $("#avgFronOffice").html(data.paperViewObj.avgFronOffice);
			       }

	               if(data.paperViewObj.avgDoctotrs == null){$("#avgDoctotrs").html("");}
	                else {$("#avgDoctotrs").html(data.paperViewObj.avgDoctotrs);}
	                 if(data.paperViewObj.avgDiag == null){$("#avgNurses").html("");}
	                else {$("#avgNurses").html(data.paperViewObj.avgDiag);}
	                 if(data.paperViewObj.avgHosekeeping == null){$("#avgHosekeeping").html("");}
	                else {$("#avgHosekeeping").html(data.paperViewObj.avgHosekeeping);}
	                 if(data.paperViewObj.avgOtherFacilities == null){$("#avgOtherFacilities").html("");}
		                else {$("#avgOtherFacilities").html(data.paperViewObj.avgOtherFacilities);}
	                if(data.paperViewObj.totalAverage == null){$("#totalAverage").html("");}
	                else {$("#totalAverage").html(data.paperViewObj.totalAverage);}
	                
	                
    },
       error: function() {
            alert("error");
        }
     });
}
loadPaperData();
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
	                                     <td align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Room No. is as</b></font></td>
	                                      
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
                  <div style="height:100%; margin-bottom:2px;" ><B>Front office/Call Center</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                </tr>
 
                                    <tr>
                                           <td align="right" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Ease of contacting hospital:</a></font></td>
                                           <td align="center" width="3%" id="ease_Contact"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr>  <td align="right" width="20%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Response to Queries:</a></font></td>
                                           <td align="center" width="3%" id="res_queries"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Politeness and Courteousness:</a></font></td>
                                           <td align="center" width="5%" id="polndCourt"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Registration and Billing Services:</a></font></td>
                                           <td align="center" width="5%" id="regndBill"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                    </tr>
                                    <tr></tr><tr></tr>
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
                                           <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Waiting time for consultation:</a></font></td>
                                           <td align="center" width="17%" id="wait4Consltnt"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Diagnosis:</a></font></td>
                                           <td align="center" width="17%" id="diagnosis"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Treatment:</a></font></td>
                                           <td align="center" width="13%" id="treatment"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="83%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Medication :</a></font></td>
                                           <td align="center" width="13%" id="medication"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Explanation of Test/Procedures</a></font></td>
                                           <td align="center" width="5%" id="careAtHome"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
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
<div style="height:100%; margin-bottom:2px;" id='3stBlock'><div style="height:auto; margin-bottom:2px;" ><B>Diagnostics</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
 
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Sample Collection:</a></font></td>
                                           <td align="center" width="5%" id="sampleColl"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Lab Services:</a></font></td>
                                           <td align="center" width="5%" id="labServices"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Radiology Services:</a></font></td>
                                           <td align="center" width="5%" id="radiologyServices"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cardiology Services:</a></font></td>
                                           <td align="center" width="5%" id="cardiology"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Endoscopy:</a></font></td>
                                           <td align="center" width="5%" id="endoscopy"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Neurology:</a></font></td>
                                           <td align="center" width="5%" id="neurology"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Urology:</a></font></td>
                                           <td align="center" width="5%" id="urology"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Preventive Health Check-Up:</a></font></td>
                                           <td align="center" width="5%" id="preHealth"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
                                     </tr>
                                      <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Any Other Please Specify:</a></font></td>
                                           <td align="center" width="5%" id="anyOther"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"></a></font></td>
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
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cleanliness Of Washrooms:</a></font></td>
                                           <td align="center" width="5%" id="cleanWashroom"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Unkeep of the facility:</a></font></td>
                                           <td align="center" width="5%" id="unkeepFacility"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>
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
   <div style="height:100%; margin-bottom:2px;" id='5stBlock'><div style="height:auto; margin-bottom:2px;" ><B>Facilities</B></div>
                       <table  align="center" border="0" width="100%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="#669966" bordercolor="black" >
	                                       <td align="center" width="83%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Feedback Parameter</b></font></td>
	                                       <td align="center" width="17%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rating</b></font></td>
	                                     
                                    </tr>
 
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Location/ Directional Signages:</a></font></td>
                                           <td align="center" width="5%" id="locndDirSignages"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Chemist Shop:</a></font></td>
                                           <td align="center" width="5%" id="chemistShop"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>
                                           <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Waiting Areas:</a></font></td>
                                           <td align="center" width="5%" id="waitingAreas"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Cafeteria:</a></font></td>
                                           <td align="center" width="5%" id="cafeteria"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>
                                     		<td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Parking Services:</a></font></td>
                                           <td align="center" width="5%" id="parking"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     	</tr>
                                     <tr>
                                     	<td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Security Assistance:</a></font></td>
                                           <td align="center" width="5%" id="security"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>
                                     	<td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#">Overall Level of Service:</a></font></td>
                                           <td align="center" width="5%" id="overallService"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
                                     </tr>
                                     <tr>  <td align="right" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Average:<b></a></font></td>
                                           <td align="center" width="5%" id="avgOtherFacilities"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"> </a></font></td>
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