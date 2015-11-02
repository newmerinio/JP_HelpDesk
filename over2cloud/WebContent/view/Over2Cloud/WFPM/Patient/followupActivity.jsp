<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>
</head>
<body>
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Patient" action="addFirstStatus" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    <div class="menubox">
  <s:if test="OLevel3">
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">subofferingname#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
			<s:select 
		                              id="subofferingname"
		                              name="offeringlevel3" 
		                              list="offeringMap"
		                               cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="commonSelectAjaxCall('patientcrm_status_data','id','status','offering_name',this.value,'','','','status','current_activity','--Select Current Activity--');commonSelectAjaxCall('patientcrm_status_data','id','status','offering_name',this.value,'','','','status','next_activity','--Select Next Activity--');getDrList(this.value,'dr');"
		                              headerKey="-1"
		                              headerValue="--Select Offering--"
		                              value="%{offeringLevel}"
		                              >
		                  	</s:select>
			</div>
			</div>
			</s:if>
			<span id="form2MandatoryFields" class="qIds" style="display: none; ">subofferingname#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
			<div class="newColumn">
			<div class="leftColumn1">Last Status:</div>
			<div class="rightColumn1">
			<span class="needed"></span>
				<s:textfield name="offeringname"  id="offeringname"  cssClass="textField"  value="%{laststatus}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>
			
			
			
    
<!-- Text box -->
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


<!--
	<s:iterator value="patientTextBox" status="counter">
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
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
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
	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	</s:else>
	
	</s:iterator> 
	
         
	--></div>
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
					cssStyle="margin-left: 160px;margin-top: -27px;"
					>
					  	Reset
					</sj:a>
					<div id="back"></div>
				          <sj:a 
					     	name="Cancel"  
					href="#" 
					cssClass="submit" 
					indicator="indicator" 
					button="true" 
					cssStyle="margin-top: -27px;margin-left: 316px;"
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
</body>
</html>

