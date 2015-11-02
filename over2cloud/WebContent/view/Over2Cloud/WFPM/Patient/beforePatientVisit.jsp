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
<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>
	<script type="text/javascript"><!--
		$.subscribe('level1', function(event,data)
		        {
		          setTimeout(function(){ $("#patientaddition").fadeIn(); }, 10);
		          setTimeout(function(){ $("#patientaddition").fadeOut(); }, 4000);
		          
		});

		
		 function resetForm(formId) 
		 {
			 $('#'+formId).trigger("reset");
		 }
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

		 function followUpActivity(val)
		 {
			 var row=$("#patient_name").val();
			if(val==='Follow Up'){
			 $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/WFPM/Patient/checkFollowupActivity.action?rowid="+row,
			    success : function(data) {
			    			if(data.jsonObject.status==='notexists'){
			    				alert("No visit before");
			    			}else{
			    				$("#dataPartVisit").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
			    				$.ajax({
								    type : "post",
								    url : "view/Over2Cloud/WFPM/Patient/followupActivity.action?rowid="+row,
								    success : function(subdeptdata) {
								       $("#"+"dataPartVisit").html(subdeptdata);
								 },
								   error: function() {
								            alert("error");
								        }
								 });     
				}
			    },
			    
			    error: function(){
			    	alert("error ");
			    	}
			 }); 
			}
		 }
		 
		 function fetchLevelData(val,selectId,Offeringlevel)
		 {
		 	$.ajax({
		 	    type : "post",
		 	    url : "view/Over2Cloud/WFPM/Patient/fetchOfferingLevelData.action?offeringId="+val.value+"&Offeringlevel="+Offeringlevel,
		 	    success : function(data) {
		 		    
		 		$('#'+selectId+' option').remove();
		 		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
		     	$(data).each(function(index)
		 		{
		     		// alert(this.ID +" "+ this.NAME);
		 		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		 		});
		 		
		 	},
		 	   error: function() {
		             alert("error");
		         }
		 	 });
		 }
	
	--></script>
	
	
	
	
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<!-- <div class="head">Add Prospective Client</div> -->
	<div class="head">Patient Activity</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
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
        	<div id="dataPartVisit">
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Patient" action="addFirstStatus" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    	<div style="font-weight: bold; font-size: large;margin-left: -87px;" align="center"><s:property value="%{first_name}"/> </div>
			<br>
			<br>
    
    <div class="menubox">
   	<s:iterator value="clientDropDown" status="counter">
	    <s:if test="%{mandatory}">
	       <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	    </s:if> 	
	</s:iterator> 
       
       <div class="newColumn">
			<div class="leftColumn1">Purpose of Visit:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="purpose_visit"
		                              name="purpose_visit" 
		                              list="#{'First Visit':'First Visit', 'Follow Up':'Follow Up'}"
		                              cssClass="Select Purpose of Visit"
		                              cssStyle="width:82%"
		                              onchange="followUpActivity(this.value);"
		                              >
		                  	</s:select>
			</div>
			</div>
       
         <!-- Drop down offering -->
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
			<s:if test="OLevel2">
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
		                              name="offeringlevel3" 
		                              list="#{'-1':'Select Sub Offering'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="commonSelectAjaxCall('patientcrm_status_data','id','status','offering_name',this.value,'','','','status','current_activity','--Select Current Activity--');commonSelectAjaxCall('patientcrm_status_data','id','status','offering_name',this.value,'','','','status','next_activity','--Select Next Activity--');getDrList(this.value,'dr');"
		                              >
		                  	</s:select>
			</div>
			</div>
			</s:if>
    <!--<s:hidden name="rowid" value="rowid"></s:hidden>
--><!-- Text box -->
	<s:iterator value="patientTextBox" status="counter">
	<s:if test="%{key == 'patient_name'}">
		<s:hidden id="%{key}" name="%{key}" value="%{rowid}"></s:hidden>
	</s:if>
	<s:else>
	<s:if test="%{mandatory}">
	<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>

	<s:if test="#counter.odd">
	<div class="clear"></div>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	
	<s:if test="%{key == 'maxDateTime'}">
	<sj:datepicker name="%{key}" id="%{key}" readonly="true" changeMonth="true" changeYear="true" yearRange="1890:2020" 
	showOn="focus" displayFormat="dd-mm-yy"  cssClass="textField" placeholder="Select Date" timepicker="true" />
	</s:if>
	<s:elseif test="%{key == 'curr_schedule_date'}">
		<sj:datepicker name="%{key}" id="%{key}" readonly="true" changeMonth="true" changeYear="true" yearRange="1890:2020" 
		showOn="focus" displayFormat="dd-mm-yy" value="today" cssClass="textField" placeholder="Select Date" timepicker="true" />
	</s:elseif>
	<s:elseif test="%{key == 'sent_questionair'}">
	<s:checkbox name="%{key}"  id="%{key}"></s:checkbox>
	</s:elseif>
	<s:elseif test="%{key == 'current_activity'}">
	<s:select 
		                             name="%{key}" id="%{key}" 
		                              list="#{'-1':'Select Current Activity '}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              
		                              >
		                  	</s:select>
	</s:elseif>
	<s:elseif test="%{key == 'next_activity'}">
	<s:select 
		                             name="%{key}" id="%{key}" 
		                              list="#{'-1':'Select Next Activity '}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              
		                              >
		                  	</s:select>
	</s:elseif>
	<s:elseif test="%{key == 'relationship_mgr'}">
	<s:select 
		                             name="%{key}" id="%{key}" 
		                              list="relManagerMap"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              headerKey="-1"
		                              headerValue="Select Relationship Manager"
		                              >
	</s:select>
	</s:elseif>
	<s:else>
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</s:else>
	</div>
	</div>
	</s:if>
	<s:else>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:if test="%{key == 'maxDateTime'}">
	<sj:datepicker name="%{key}" id="%{key}" readonly="true" changeMonth="true" changeYear="true" yearRange="1890:2020" 
	showOn="focus" displayFormat="dd-mm-yy"  cssClass="textField" placeholder="Select Date" timepicker="true" />
	</s:if>
	<s:elseif test="%{key == 'curr_schedule_date'}">
		<sj:datepicker name="%{key}" id="%{key}" readonly="true" changeMonth="true" changeYear="true" yearRange="1890:2020" 
		showOn="focus" displayFormat="dd-mm-yy" value="today" cssClass="textField" placeholder="Select Date" timepicker="true" />
	</s:elseif>
	<s:elseif test="%{key == 'sent_questionair'}">
		<s:checkbox name="%{key}"  id="%{key}"></s:checkbox>
	</s:elseif>
	<s:elseif test="%{key == 'current_activity'}">
	<s:select 
		                             name="%{key}" id="%{key}" 
		                              list="#{'-1':'Select Current Activity '}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              
		                              >
		                  	</s:select>
	</s:elseif>
	<s:elseif test="%{key == 'next_activity'}">
	<s:select 
		                             name="%{key}" id="%{key}" 
		                              list="#{'-1':'Select Next Activity '}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              
		                              >
		                  	</s:select>
	</s:elseif>
	<s:elseif test="%{key == 'relationship_mgr'}">
	<s:select 
		                             name="%{key}" id="%{key}" 
		                              list="#{'-1':'Select Next Activity '}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              
		                              >
		                  	</s:select>
	</s:elseif>
	<s:elseif test="%{key == 'dr'}">
	<s:select 
		                              name="%{key}" 
		                              id="%{key}" 
		                              list="{'No Data'}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              headerKey="-1"
		                              headerValue="Select Doctor Name"
		                              >
	</s:select>
	</s:elseif>
	<s:else>
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</s:else>
	</div>
	</div>
	</s:else>
	</s:else>
	</s:iterator> 
	
         
	</div>
	<div class="clear"></div>
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
         <sj:submit 
            targets="patientresult" 
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
					onclick="BackPatientBasic()"
					cssStyle="margin-top: -43px;"
					
					>
					  	Back
					</sj:a>
					    </div>
	    
	
	<sj:div id="patientaddition"  effect="fold">
           <div id="patientresult"></div>
        </sj:div>
	</s:form>
	</div>
	
	
	</div>
	</div>
	</div>
</body>
</html><!--

<sj:datepicker name="date" id="date" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" 
						placeholder="Select Date" timepicker="true" onchange="validateTime(%{id}, this.value);"/>-->