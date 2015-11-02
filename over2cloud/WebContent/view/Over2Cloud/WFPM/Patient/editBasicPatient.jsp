<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
   <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
   <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
<script type="text/javascript">
function checkAddNew(val){
	 if(val==="newAdd"){
		 $("#addNewCorporateDialog").dialog('open');
		 $.ajax({
				type : "post",
			    url : "/over2cloud/view/Over2Cloud/WFPM/Patient/beforeAddCorporate.action",
				success : function(data){
					
					 $("#formCorporateDiv").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
					$('#formCorporateDiv').html(data);
				},
				error   : function(){
					alert("error");
				}
			});
	 }
}
 function showSelectedDivs(v1){
		$("#"+v1+"Div").show(200);
	}
function hideSelectedDivs(v1){
		$("#"+v1+"Div").hide(200);
	}

function showNonWalkin(val){
	if(val==="Referral"){
		showSelectedDivs(val);
		hideSelectedDivs("Corporate");
		$("#corporate").val('');
	}
	if(val==="Corporate"){
		showSelectedDivs(val);
		hideSelectedDivs("Referral");
		$("#referral").val('');
	}
	if(val==="Walk-in"){
		hideSelectedDivs("Referral");
		hideSelectedDivs("Corporate");
		$("#referral").val('');
		$("#corporate").val('');
	}
}

</script>
<script type="text/javascript">
$.subscribe('level1', function(event,data)
       {
         setTimeout(function(){ $("#pBasicaddition").fadeIn(); }, 10);
         setTimeout(function(){ $("#pBasicaddition").fadeOut(); }, 4000);
         
});
function resetForm(formId) 
{
$('#'+formId).trigger("reset");
}
</script>
<script type="text/javascript">
function BackPatientBasic()
{
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
$.ajax({
   type : "post",
   url : "view/Over2Cloud/WFPM/Patient/beforeviewPatient.action",
   
   success : function(subdeptdata) {
      $("#"+"data_part").html(subdeptdata);
},
  error: function() {
           alert("error");
       }
});
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="list-icon">
<!-- <div class="head">Add Prospective Client</div> -->
<div class="head">Patient Status</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
</div>
<div class="clear"></div>
<div class="border">
<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">
<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       	</div>	
        <br>
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Patient" action="updatePatientBasicForm" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    <div class="menubox">
   	      <s:hidden name="id" value="%{id}"></s:hidden>
       <!-- Text box -->
<s:iterator value="patientBasicTextBox" status="counter">
<s:if test="%{mandatory}">
<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
</s:if>

<s:if test="#counter.odd">
<div class="clear"></div>
<div class="newColumn" >
<div class="leftColumn1"><s:property value="%{value}"/>:</div>
<div class="rightColumn1">
<s:if test="%{mandatory}">
<span class="needed"></span>
</s:if>
<s:textfield name="%{key}"  id="%{key}"  value="%{default_value}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
</div>
</div>
</s:if>
<s:else>
<div class="newColumn" >
<div class="leftColumn1"><s:property value="%{value}"/>:</div>
<div class="rightColumn1">
<s:if test="%{mandatory}">
<span class="needed"></span>
</s:if>
<s:textfield name="%{key}"  id="%{key}"  value="%{default_value}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
</div>
</div>
</s:else>
</s:iterator> 
<div class="clear"></div>
<div class="newColumn">
<div class="leftColumn1">Gender:</div>
<div class="rightColumn1">
              <s:select 
                              id="gender"
                              name="gender" 
                              list="#{'Male':'Male','Female':'Female','Other':'Other'}"
                              headerKey="-1"
                              headerValue="Select Gender" 
                              cssClass="select"
                              cssStyle="width:82%"
       							value="%{gender}"
                              >
                   </s:select>
</div> 
</div>
<div class="newColumn">
<div class="leftColumn1">Nationality:</div>
<div class="rightColumn1">
              <s:select 
                              id="nationality"
                              name="nationality" 
                              list="countryMap"
                              headerKey="-1"
                              headerValue="Select Nationality" 
                              cssClass="select"
                              cssStyle="width:82%"
                              	value="%{nationality}"
                              >
                   </s:select>
</div> 
</div>
<div class="newColumn">
<div class="leftColumn1">City:</div>
<div class="rightColumn1">
              <s:select 
                              id="city"
                              name="city" 
                              list="locationList"
                              headerKey="-1"
                              headerValue="Select City" 
                              cssClass="select"
                              cssStyle="width:82%"
                              value="%{city}"
                              >
                   </s:select>
</div> 
</div>
         <div class="newColumn">
         <div class="leftColumn1">Blood Group:</div>
         <div class="rightColumn1">
         <s:if test="%{mandatory}">
         <span class="needed"></span>
         </s:if>
          <s:select 
                              id="blood_group"
                              name="blood_group" 
                              list="#{'A Positive':'A+','O Positive':'O+','B Positive':'B+','AB Positive':'AB+','A Negative':'A-','O Negative':'O-','B Negative':'B-','AB Negative':'AB-'}"
                              headerKey="-1"
                              headerValue="Select Group" 
                              cssClass="select"
                              cssStyle="width:82%"
                              	value="%{blood_group}"
                              >
                   </s:select>
        </div>
         </div>
          <div class="newColumn">
<div class="leftColumn1">Patient Category:</div>
<div class="rightColumn1">
              <s:select 
                              id="patient_category"
                              name="patient_category" 
                              list="#{'Standard':'Standard','VVIP':'VVIP','Priority':'Priority','Others':'Others'}"
                              headerKey="-1"
                              headerValue="Select Patient Category" 
                              cssClass="select"
                              cssStyle="width:82%"
                              value="%{patient_category}"
                              >
                   </s:select>
</div> 
</div>
<div class="newColumn">
<div class="leftColumn1">Source:</div>
<div class="rightColumn1">
              <s:select 
                              id="patient_type"
                              name="patient_type" 
                              list="#{'Corporate':'Corporate','Referral':'Referral','Walk-in':'Walk-in'}"
                              headerKey="-1"
                              headerValue="Select Source" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="showNonWalkin(this.value);"
                              value="%{patient_type}"
                              >
                   </s:select>
</div> 
</div>
<div id="CorporateDiv" style="display: block">
 <div class="newColumn">
<div class="leftColumn1">Corporate:</div>
<div class="rightColumn1">
              <s:select 
                              id="corporate"
                              name="corporate" 
                              list="corporateMap"
                              headerKey="-1"
                              headerValue="Select Corporate" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="checkAddNew(this.value);"
                              value="%{corporate}"
                              >
                   </s:select>
</div> 
</div>
</div>

<div id="ReferralDiv" style="display: block">
 <div class="newColumn">
<div class="leftColumn1">Referral:</div>
<div class="rightColumn1">
              <s:select 
                              id="referral"
                              name="referral" 
                              list="associateMap"
                              headerKey="-1"
                              headerValue="Select Referral" 
                              cssClass="select"
                              cssStyle="width:82%"
                              value="%{referral}"
                              >
                   </s:select>
</div> 
</div>
</div>

      
</div>
 <div class="newColumn">
	 <div class="leftColumn1">Questionare:</div>
	 <div class="rightColumn1">
	               <s:checkbox name="sent_questionair" id="sent_questionair" />
	       		   
	 </div> 
	 </div>

<div class="clear"></div>
<!-- Buttons -->
<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
         <sj:submit 
            targets="pBasicresult" 
            clearForm="true"
            value="Save" 
            effect="highlight"
            effectOptions="{ color : '#222222'}"
            effectDuration="5000"
            button="true"
            onCompleteTopics="level1"
            cssClass="submit"
            indicator="indicator2"
            onBeforeTopics="validate1"
            
          />
           <sj:a 
       name="Reset"  
href="#" 
cssClass="submit" 
indicator="indicator" 
button="true" 
onclick="resetForm('formone');"
cssStyle="margin-left: 193px;margin-top: -43px;"
>
 	Reset
</sj:a>
         <sj:a 
    name="Cancel"  
href="#" 
cssClass="submit" 
indicator="indicator" 
button="true" 
cssStyle="margin-left: 145px; margin-top: -25px;"
onclick="BackPatientBasic();"
cssStyle="margin-top: -43px;"
>
 	Back
</sj:a>
   </div>
   
<sj:div id="pBasicaddition"  effect="fold">
           <div id="pBasicresult"></div>
        </sj:div>
</s:form>
</div>
</div>
</div>
<sj:dialog
	id="addNewCorporateDialog"
	autoOpen="false"
	modal="true"
	showEffect="slide" 
   	hideEffect="explode" 
    openTopics="openEffectDialog"
    closeTopics="closeEffectDialog"
    width="1000"
    height="500"
>
<div id="formCorporateDiv"></div>
</sj:dialog>

</body>
</html>


  <!-- Drop down offering  --><!--
         <s:if test="OLevel1">
<span id="form2MandatoryFields" class="qIds" style="display: none; ">verticalname#<s:property value="%{OLevel1LevelName}"/>#D#,</span>
<div class="newColumn">
<div class="leftColumn1"><s:property value="%{OLevel1LevelName}"/>:</div>
<div class="rightColumn1">
<span class="needed"></span>
<s:select 
                             id="verticalname"
                             name="verticalname" 
                             list="verticalMap"
                             headerKey="-1"
                             headerValue="Select Name" 
                             cssClass="select"
                             cssStyle="width:82%" 
                             onchange="fetchLevelData(this,'offeringname','1')"
                             >
                  
                 </s:select>
</div>
</div>
</s:if>
--><!--<s:if test="OLevel2">
<span id="form2MandatoryFields" class="qIds" style="display: none; ">offeringname#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
<div class="newColumn">
<div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
<div class="rightColumn1">
<span class="needed"></span>
<s:select 
                             id="offeringname"
                             name="offeringname" 
                             list="#{'-1':'Select Offering'}"
                              cssClass="select"
                             cssStyle="width:82%"
                             onchange="fetchLevelData(this,'subofferingname','2')"
                             >
                 	</s:select>
</div>
</div>
</s:if>
<s:if test="OLevel3">
<span id="form2MandatoryFields" class="qIds" style="display: none; ">subofferingname#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
<div class="newColumn">
<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
<div class="rightColumn1">
<span class="needed"></span>
<s:select 
                             id="subofferingname"
                             name="offering_name" 
                             list="#{'-1':'Select Sub Offering'}"
                              cssClass="select"
                             cssStyle="width:82%"
                             onchange="fetchLevelData(this,'variantname','3');checkSuboffering(this.value,clientN.value);"
                             >
                 	</s:select>
</div>
</div>
</s:if>
   
-->