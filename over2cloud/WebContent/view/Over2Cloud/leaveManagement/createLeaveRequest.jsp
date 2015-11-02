<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#empname").multiselect({
          show: ["bounce", 200],
           hide: ["explode", 1000]
        });
    });
    </script>
<script type="text/javascript">

$.subscribe('level2', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	       });
</script>
<SCRIPT type="text/javascript">
function displayValues(value)
{
	mydiv = document.getElementById('showhis');
	mydiv1 = document.getElementById('showhist');
	if(value=="Days")
		{
		if( mydiv.style.display = 'none'){
			 mydiv.style.display = 'block';
			 mydiv1.style.display = 'none';
		}
	}
	if(value=="Hour"){
		if( mydiv1.style.display = 'none'){
			mydiv1.style.display = 'block';
			 mydiv.style.display = 'none';
		}
}}

</script>
</head>
 <div class="list-icon">
	 <div class="head">
	 Leave Request </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head"> Leaves Availed: <s:property value="%{totalLeaveAvailed}"/> Leave Balance:  <s:property value="%{empLeaveBalance}"/></div> 
</div>
 <div style=" float:left; padding:5px 0%; width:100%;">
 <div class="border">
		<s:form id="formone"  namespace="/view/Over2Cloud/leaveManagement" action="requestAddAction" theme="simple"  method="post" enctype="multipart/form-data" >
                    <s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
                    <s:hidden name="leaveLft" id="leaveLft" value="%{leaveLeft}"></s:hidden>
                    <s:hidden name="leavestatusaa" id="leavestatusaa" value="%{status}"></s:hidden>
                       
            <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:left; margin-left: 7px"></div>        
            </div>
                     <s:iterator value="requestColumnRadio" status="counter"  >
                     <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
                      <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>                                  <!--<b>Leave Left:: <s:label name="leaveLft" value="%{leaveLeft}"></s:label> </b>
								--><div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:radio cssClass="abc" name="%{key}"  id="%{key}"  list="{'Days','Hour'}" onclick="displayValues(this.value);" ondblclick="disableValue(this.value);"></s:radio>
                                </div>
                      </div>
                      </s:iterator>
			       
			       <div class="clear"></div>
			        <div id="showhis" style="display: none;">
                 
					  <s:iterator value="requestColumnDate" status="counter"  begin="0" end="1">
					     <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
				     <s:if test="#counter.count%2 != 0">
				    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                           <sj:datepicker name="%{key}" id="%{key}" readonly="true" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select From date"/>
	                           </div>
	                 </div>
					 </s:if>
					 <s:else>
				     <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker name="%{key}" id="%{key}" readonly="true" minDate="0" changeMonth="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select To date"/>
	                            </div>
	                  </div> 
					 </s:else>
		     		 </s:iterator>	
		     		  
		     		  
		     		  </div>
		     		
		     		 <div id="showhist" style="display: none;">
		     		 <s:iterator value="requestColumnDate" begin="2">
		     		   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                      </s:if>
					 	<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                           <sj:datepicker name="%{key}" id="%{key}" minDate="0" changeMonth="true" readonly="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select To date"/>
	                            </div>
	                  </div>
		     		 </s:iterator>
                     <div class="clear"></div>
					 <s:iterator value="requestColumnTime" status="counter">
					        <s:if test="%{mandatory}">
                            <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                            </s:if>
					  <s:if test="#counter.count%2 != 0">
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker timepicker="true" timepickerOnly="true" readonly="true" name="%{key}" id="%{key}"  showOn="focus" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select From Time"/>
	                            </div>
					  </div>
					  </s:if>
					  <s:else>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if><sj:datepicker timepicker="true" readonly="true" timepickerOnly="true" name="%{key}" id="%{key}"   timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" showOn="focus"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select To Time"/></div>
                      </div>	
					  </s:else>
		     		</s:iterator>
		            </div>
	
				      <s:iterator value="requestColumnDropdown">
				            <s:if test="%{mandatory}">
                            <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                            </s:if>
					  <s:if test="%{key == 'empname'}">
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
	                              id="%{key}"
	                              name="%{key}" 
	                              list="employeelist"
	                              headerKey="-1"
	                              multiple="true"
                                  cssStyle="width:27.5%;height:40% "
	                              >
                  				</s:select>
                  				</div>
                  	</div>
                  </s:if>
                  <s:if test="%{key == 'leavestatus'}">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  			<s:select 
                              	id="%{key}"
                              	name="%{key}" 
                             	list="leaveType"
                             	headerKey="-1"
                              	headerValue="Select Type" 
                              	cssClass="select"
                                cssStyle="width:122%"
                              	>
                  				</s:select></div>
                  				</div>
                  </s:if>
				 </s:iterator>  
				   <div class="clear"></div>
				 <s:iterator value="requestColumnText">
				       <s:if test="%{mandatory}">
                       <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                       </s:if>
				 	<s:if test="%{key == 'reason'}">
				 	 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textarea id="%{Key}" name="%{Key}" cssStyle="margin:0px 0px 10px 0px; width:118%" cssClass="textField" placeholder="Enter Data"></s:textarea>
                                </div>
                                </div>
                  </s:if>
                  </s:iterator>
       <div class="clear"></div>
       <div class="clear"></div>
                <center><img id="indicator23" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                    <center>
					<div class="clear"></div>
                    <div class="type-button">
                    <sj:submit 
                            targets="orglevel1Div" 
                            clearForm="true"
                            value="  Save " 
                            effect="highlight"
                            effectOptions="{ color : '#222222'}"
                            effectDuration="5000"
                            button="true"
                            onCompleteTopics="level2"
                            cssClass="submit"
                            indicator="indicator23"
                            onBeforeTopics="validate"
                            
                            />
                            
                         
		<sj:a cssStyle="margin-left: -1px;margin-top: 0px;" button="true" href="#" onclick="resetForm('formone');resetColor('.pIds') ">Reset</sj:a>
			<sj:a cssStyle="margin-left: -1px;margin-top: 0px;" button="true" href="#" onclick="viewRequest();">Back</sj:a>
	          				
						
						    
                   </div>
             	<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </center>
               </s:form>
</div>
</div>
</html>