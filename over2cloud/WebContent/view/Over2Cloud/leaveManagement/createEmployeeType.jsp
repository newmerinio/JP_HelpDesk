<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	       });
function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}
</script>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
</head>
<div class="list-icon">
	 <div class="head">
	 Employee Type </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
 <div style=" float:left; padding:5px 0%; width:100%;">
 <div class="border">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/leaveManagement" action="employeeTypeAdd" theme="simple"  method="post" enctype="multipart/form-data" >
		     	
		    <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:left; margin-left: 7px"></div>        
            </div>
		  
                  <div class="clear"></div>
                    <s:iterator value="employeeColumnText"  status="counter">
	                     <s:if test="%{mandatory}">
	                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                         </s:if>
	                      <s:if test="#counter.count%2 != 0">
		                  <div class="newColumn">
									<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1">
	                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                  <s:textfield id="%{Key}" name="%{Key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
	                   </div>
	                   </div>
	                    </s:if>
	                    <s:else>
	                     <div class="newColumn">
									<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1">
	                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                  <s:textfield id="%{Key}" name="%{Key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
	                   </div>
	                   </div>
	                  </s:else>
                  </s:iterator>
             	
				<center>
					<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<center>
				<div class="clear"></div>
					<div class="type-button">
	                <sj:submit 
                        targets="orglevel1" 
                        clearForm="true"
                        value="Save" 
                        effect="highlight"
                        effectOptions="{ color : '#222222'}"
                        effectDuration="5000"
                        button="true"
                        onCompleteTopics="level1"
                        cssClass="submit"
                        indicator="indicator2"
                        onBeforeTopics="validate"
                        cssStyle="margin-left: -40px;"
	                 />
	                
			<sj:a cssStyle="margin-left: -1px;margin-top: 0px;" button="true" href="#" onclick="resetForm('formone');resetColor('.pIds');">Reset</sj:a>
			<sj:a cssStyle="margin-left: -1px;margin-top: 0px;" button="true" href="#" onclick="viewEmployeeType();">Back</sj:a>
	           	  
	               </div>
				</center>
					<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
	</s:form>	
</div>
</div>
</html>