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
	<style type="text/css">
	
		.textField
		{
			height:18px;
		}
		span.needed
		{
			height:22px;	
		}
		.select
		{
			height:22px;	
		}
		.newColumn
		{
			width:33%;	
		}
		.leftColumn1
		{
			width:43%;
			padding:10px;
		}
		.rightColumn1
		{
			width:50%;	
			padding: 3px;
		}
		
	
	</style>
	<script type="text/javascript">
	$.subscribe('level1', function(event,data)
	        {
	          setTimeout(function(){ $("#patientaddition").fadeIn(); }, 10);
	          setTimeout(function(){ $("#patientaddition").fadeOut(); }, 4000);
	          
	});
	
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
	 		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	 		});
	 	},
	 	   error: function() {
	             alert("error");
	         }
	 	 });
	 }
	 
	 function changeDivView(value,div1,div2)
	 {
			if(value=='2')
			{
				$('#'+div2).show();
				$('#'+div1).hide();
			}	
			else
			{
				$('#'+div1).show();
				$('#'+div2).hide();
			}	
	}
	 
	 function fetchSubOfferingData(val,id1,id2)
	 {
	 	$.ajax({
	 	    type : "post",
	 	    url : "view/Over2Cloud/WFPM/Patient/fetchSubOfferingData.action?id="+val+"&status="+$("#patient_type").val()+"&offering="+$("#stage").val(),
	 	    success : function(data) {
	 		    console.log(data);
	 		$('#'+id1+ 'option').remove();
	 		$('#'+id2+ 'option').remove();
	     	$(data).each(function(index)
	 		{
	 		  $('#'+id1).append($("<option></option>").attr("value",this.ID).text(this.name));
	 		  $('#'+id2).append($("<option></option>").attr("value",this.ID).text(this.name));
	 		});
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
	<div class="head">Patient Lead</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Configure For <s:property value="%{first_name}"/>, <s:property value="%{mobile}"/>, <s:property value="%{crm_id}"/></div>
	</div>
	<div class="clear"></div>
	 <div class="border">
	<div class="container_block">
	<div style=" float:left;width:98%;">
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       	 </div>	
        	<br>
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Patient" action="addFirstActivity" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    	<%-- <div style="font-weight: bold; font-size: large;margin-left: -87px;" align="center"><s:property value="%{first_name}"/> </div> --%>
		<s:hidden id="status" name="status" value="%{status}"></s:hidden>
		<s:hidden id="stage" name="stage" value="%{stage}"></s:hidden>
		<s:hidden id="patient_type"  value="%{patient_type}"></s:hidden>
   	<s:iterator value="clientDropDown" status="counter">
	    <s:if test="%{mandatory}">
	       <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	    </s:if> 	
	</s:iterator> 
     
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
		                              theme="simple"
		                              >
		                  </s:select>
			</div>
			</div>
			</s:if>
			<s:if test="OLevel2">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">offeringname#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1">Speciality:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="offeringname"
		                              name="offeringname" 
		                              list="#{'-1':'Select Speciality'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="fetchLevelData(this,'subofferingname','2');getDrList(this.value,'dr');getDrList(this.value,'dr1');"
		                              theme="simple"
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
		                              list="#{'-1':'Select Service'}"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="fetchSubOfferingData(this.value,'current_activity','next_activity');"
		                            	theme="simple"
		                              >
		                  	</s:select>
			</div>
			</div>
			</s:if>
			
			
    <!--<s:hidden name="rowid" value="rowid"></s:hidden>
--><!-- Text box -->
	
	<div class="newColumn">
			<div class="leftColumn1">Owner&nbsp;Type:</div>
			<div class="rightColumn1">
					<s:radio label="ownerTypeCurr" name="ownerTypeCurr" cssClass="radio" list="#{'1':'Doctor','2':'Buddy'}" value="1" theme="simple"  onchange="changeDivView(this.value,'docDiv1','buddyDiv1')"/>
			</div>
		</div>
		<div id="docDiv1">
			<div class="newColumn">
			<div class="leftColumn1">Current&nbsp;Activity&nbsp;Owner:</div>
			<div class="rightColumn1">
					<s:select 
		                  name="dr1" 
		                  id="dr1" 
		                  list="{'No Data'}"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Doctor Name"
		                  theme="simple"
		        >
				</s:select>
			</div>
			</div>
		</div>
		<div id="buddyDiv1" style="display:none;">
			<div class="newColumn">
			<div class="leftColumn1">Current&nbsp;Activity&nbsp;Owner:</div>
			<div class="rightColumn1">
					<s:select 
		                  name="relationship_mgr1" 
		                  id="relationship_mgr1" 
		                  list="relManagerMap"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Buddy Name"
		                  theme="simple"
		        >
				</s:select>
			</div>
		</div>
		</div>
		
	 	<div class="newColumn">
			<div class="leftColumn1">&nbsp;</div>
			<div class="rightColumn1">
					
			</div>
		</div>
		
			<div class="newColumn">
				<div class="leftColumn1">Current Activity:</div>
				<div class="rightColumn1">
								<s:select 
			                             name="current_activity" id="current_activity" 
			                              list="#{'-1':'Select Current Activity '}"
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              theme="simple"
			                              >
			                  	</s:select>
			     </div>
			 </div>                 	
			<div class="newColumn">
				<div class="leftColumn1">Schedule Date:</div>
				<div class="rightColumn1">
						<sj:datepicker name="curr_schedule_date" id="curr_schedule_date" readonly="true" changeMonth="true" changeYear="true" maxDate="-0y" 
							showOn="focus" displayFormat="dd-mm-yy" value="today" cssClass="textField" placeholder="Select Date" timepicker="true" />
			
				</div>
			</div>	
			<div class="newColumn">
				<div class="leftColumn1">Remarks:</div>
				<div class="rightColumn1">
						<s:textfield name="comment1"  id="comment1" theme="simple" cssClass="textField" placeholder="Enter Current Activity Remarks" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>			
	
	<div class="newColumn">
			<div class="leftColumn1">Owner&nbsp;Type:</div>
			<div class="rightColumn1">
					<s:radio label="ownerType" name="ownerType" cssClass="radio" list="#{'1':'Doctor','2':'Buddy'}" value="1" theme="simple"  onchange="changeDivView(this.value,'docDiv','buddyDiv')"/>
			</div>
		</div>
		<div id="docDiv">
			<div class="newColumn">
			<div class="leftColumn1">Next&nbsp;Activity&nbsp;Owner:</div>
			<div class="rightColumn1">
					<s:select 
		                  name="dr" 
		                  id="dr" 
		                  list="{'No Data'}"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Doctor Name"
		                  theme="simple"
		        >
				</s:select>
			</div>
			</div>
		</div>
		<div id="buddyDiv" style="display:none;">
			<div class="newColumn">
			<div class="leftColumn1">Next&nbsp;Activity&nbsp;Owner:</div>
			<div class="rightColumn1">
					<s:select 
		                  name="relationship_mgr" 
		                  id="relationship_mgr" 
		                  list="relManagerMap"
		                  cssClass="select"
		                  cssStyle="width:82%"
		                  headerKey="-1"
		                  headerValue="Select Buddy Name"
		                  theme="simple"
		        >
				</s:select>
			</div>
		</div>
		</div>
		
		
	 	<div class="newColumn">
			<div class="leftColumn1">&nbsp;</div>
			<div class="rightColumn1">
					
			</div>
		</div>
		
	<s:iterator value="patientTextBox" status="counter">
	<s:if test="%{key == 'patient_name'}">
		<s:hidden id="%{key}" name="%{key}" value="%{rowid}"></s:hidden>
	</s:if>
	<s:if test="%{key == 'next_activity'}">
		<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
					<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span class="needed"></span>
				</s:if>
							<s:select 
		                             name="%{key}" id="%{key}" 
		                              list="#{'-1':'Select Next Activity '}"
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              theme="simple"
		                              >
		                  	</s:select>
		     </div>
		 </div>                 	
	</s:if>
	
	<s:elseif test="%{key == 'maxDateTime'}">
		<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
					<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span class="needed"></span>
				</s:if>
					
					<sj:datepicker id="%{key}" name="%{key}" readonly="true"  placeholder="Enter Data" showOn="focus" timepicker="true"  cssClass="textField"
						changeMonth="true" changeYear="true"maxDate="+10y" minDate="0" displayFormat="dd-mm-yy" value="today"/>
		
			</div>
		</div>	
	</s:elseif>
	
	<s:elseif test="%{key == 'comment2'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
					<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span class="needed"></span>
				</s:if>
					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" theme="simple" placeholder="Enter Next Activity Remarks" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
		</div>			
	</s:elseif>
	</s:iterator>
	<s:iterator value="patientTextBox" status="counter">
	<s:if test="%{key == 'sent_questionair'}">
			<div class="newColumn">
			<div class="leftColumn1">Communication:</div>
			<div class="rightColumn1">
					<s:checkbox name="%{key}"  id="%{key}" theme="simple">Email </s:checkbox>
					<s:checkbox name="sendSMS"  id="sendSMS" theme="simple">SMS </s:checkbox>
					<s:checkbox name="sendQuestion"  id="sendQuestion" theme="simple">Questionnaire </s:checkbox>
			</div>
		</div>			
	</s:if>
	
	<s:elseif test="%{key == 'attachment'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			<div class="rightColumn1">
				<s:if test="%{mandatory}">
					<span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span class="needed"></span>
				</s:if>
		                <s:file id="%{key}" value="%{key}" theme="simple" ></s:file>
			</div>
		</div>			
	</s:elseif>
	</s:iterator>
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
					cssStyle="margin-left: 200px;margin-top: -43px;"
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
					onclick="backPatientBasic()"
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
</body>
</html><!--

<sj:datepicker name="date" id="date" readonly="true" changeMonth="true" changeYear="true" 
						yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" 
						placeholder="Select Date" timepicker="true" onchange="validateTime(%{id}, this.value);"/>-->