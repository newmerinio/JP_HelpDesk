<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommonValidation.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
{
   setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
   setTimeout(function(){ $("#resultTarget").fadeOut(); }, 400);
});
</SCRIPT>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<s:if test="moduleName=='Support'">
<div class="list-icon">
	 <div class="head">
	 Support</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
</s:if>
<s:if test="moduleName=='Preventive'">
<div class="list-icon">
	 <div class="head">
	 Preventive Maintenance</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
</s:if>
<div class="clear"></div> 
<div style="overflow-x:hidden; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    <s:form id="configureSupport" name="configureSupport" namespace="/view/Over2Cloud/AssetOver2Cloud/Asset" 
    action="assetSupportAction" theme="simple"  method="post" enctype="multipart/form-data" >
	     <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	    </center>
	    
	    
	    
	    <jsp:include page="assetSearchData.jsp"/>

	           <div class="clear"></div>   
	           <div id="assetDetailsDiv" style="display:none;">  
	           <s:hidden  id="assetId" name="assetId"/>
	           <s:hidden id="outletId" value="%{outletId}"/>
	           <s:hidden  id="moduleName" name="moduleName" value="%{moduleName}"/>
	           
	 		  <s:iterator value="commonDDMap">
               <s:if test="key=='assetname'"> 
	               <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
		                         <s:textfield name="%{Key}11" id="%{Key}11" maxlength="50" readonly="true" placeholder="Enter Data"  cssClass="textField"/>
			            </div>
			 	  </div>
              </s:if>
             </s:iterator>
             
             <s:iterator value="commonDDMap">
               <s:if test="key=='serialno'"> 
	               <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
		                         <s:textfield name="%{Key}1" id="%{Key}1" maxlength="50" readonly="true"  placeholder="Enter Data"  cssClass="textField"/>
			            </div>
			 	  </div>
              </s:if>
             </s:iterator>
             
             <s:iterator value="commonDDMap">
               <s:if test="key=='assetbrand'"> 
	               <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
		                         <s:textfield name="%{Key}" id="%{Key}" maxlength="50"  readonly="true" placeholder="Enter Data"  cssClass="textField"/>
			            </div>
			 	  </div>
              </s:if>
             </s:iterator>
             
             <s:iterator value="commonDDMap">
               <s:if test="key=='assetmodel'"> 
	               <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
		                         <s:textfield name="%{Key}" id="%{Key}" maxlength="50" readonly="true"  placeholder="Enter Data"  cssClass="textField"/>
			            </div>
			 	  </div>
              </s:if>
             </s:iterator>
             </div> 
             <s:if test="moduleName=='Support'">
             <s:iterator value="commonDDMap">
               <s:if test="key=='supportfrom'"> 
	               <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
		                         <s:textfield name="%{Key}" id="%{Key}" maxlength="50" readonly="true" placeholder="Enter Data"  cssClass="textField"/>
			            </div>
			 	  </div>
              </s:if>
             </s:iterator>
             </s:if>
             
             
              <s:iterator value="commonDDMap">
               <s:if test="key=='dueDate'"> 
               	    <s:if test="%{mandatory}">
					<span id="normalSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      			</s:if>
	               <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                         <sj:datepicker id="%{key}" name="%{key}"  readonly="true" cssClass="textField" size="20" value="today" minDate="0" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
			            </div>
			 	  </div>
              </s:if>
              
               <s:if test="key=='dueTime'"> 
                <s:if test="%{mandatory}">
					<span id="normalSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      			</s:if>
	               <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                         <sj:datepicker id="%{key}" name="%{key}" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
			            </div>
			 	  </div>
              </s:if>
             </s:iterator>
             
	        <s:iterator value="assetFile" begin="0" end="0">
		  	   <s:if test="%{mandatory}">
		       </s:if>
		       <div class="newColumn">
		       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       <div class="rightColumn">
			   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		       <s:file name="%{key}" id="%{key}" onchange="getDocu('file');"  placeholder="Enter Data"  cssClass="textField"/>
		       </div>
		       </div>
	       </s:iterator> 
        
	       <div id="file" style="display:none;">
		       <s:iterator value="assetFile" begin="1">
				   <div class="newColumn">
			       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
			       <div class="rightColumn">
			       <s:file name="%{key}" id="%{key}" maxlength="50"  placeholder="Enter Data" cssClass="textField"/>
			       </div>
			       </div>
		  	   </s:iterator>  
	  	   </div>
  	   <div class="clear"></div>
	      <s:iterator value="assetDropDown"  status="status">
		   	   <s:if test="key=='frequency'">
		   	    <s:if test="%{mandatory}">
					<span id="normalSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      			</s:if>
		  	   <div class="newColumn">
		       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       <div class="rightColumn">
		       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			   <s:select  
			                        id					=		"%{key}"
			                        name				=		"%{key}"
			                        list				=		"frequencyMap"
			                        headerKey			=		"-1"
			                        headerValue			=		"Frequency" 
			                        cssClass			=		"select"
									cssStyle			=		"width:82%"
									onchange            =       "disableReminder(this.value),validateReminder('dueDate','frequency','reminder1')"
			                           >
			   </s:select>
			   </div>
			   </div>
			  </s:if>
		   </s:iterator>
		   
		    <s:hidden id="dateDiff" value="0"/>
		    <s:iterator value="assetCalender" begin="1" end="1" status="status">
			   <div class="newColumn">
			   <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
			   <div class="rightColumn">
		       		<sj:datepicker  id="%{key}" name="%{key}" onclick="getDocu('configCompliance1');" onChangeTopics="getSecondReminder"  readonly="true" cssClass="textField"size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
			   </div>
			   </div>
	  	   </s:iterator>
  	   
	  	   <div class="clear"></div>
	  	   <div id="configCompliance1" style="display: none;">
	  	   <s:iterator value="assetCalender" begin="2" end="2" status="status">
		  	   <s:if test="%{mandatory}">
		       </s:if>
			   <s:if test="#status.odd">
		       <div class="newColumn">
		       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       <div class="rightColumn">
		       		<sj:datepicker id="%{key}"   name="%{key}" minDate="-30" maxDate="30" onclick="getDocu('configCompliance2');" onChangeTopics="getThirdReminder" readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true" onchange="getTotalday(this.value)" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
			   </div>
			   </div>
			   </s:if>
		   </s:iterator>
		   </div>
      
	       <div id="configCompliance2" style="display: none;">
	  	   <s:iterator value="assetCalender" begin="3" end="3" status="status">
	  	  	 <s:if test="%{mandatory}">
	      	 </s:if>
		  	   <div class="newColumn">
		       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       <div class="rightColumn">
		       <sj:datepicker id="%{key}" name="%{key}" readonly="true" cssClass="textField"size="20"  changeMonth="true" changeYear="true" onchange="getTotalday(this.value)" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
		       </div>
		       </div>
	       </s:iterator>
	     	</div>
	     	
	     	<div class="clear"></div>
	       <s:iterator value="assetRadio">
			   <s:if test="%{key == 'remindMode'}">
			   <span id="normalSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{validationType}"/>,</span>
       		   <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       		   <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
		       <div class="newColumn">
		       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       <div class="rightColumn">
		       <s:radio list="viaFrom" name="%{key}" id="%{key}" value="%{'Both'}" onclick="findHiddenDiv(this.value);" />
		       </div>
		       </div>
			   </s:if>
		   </s:iterator>
	   
		   <s:iterator value="assetRadio">
		        <s:if test="%{mandatory}">
                    </s:if>
		       <span id="normalSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{validationType}"/>,</span>
       		   <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       		   <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
       		   
			   <s:if test="%{key == 'reminder_for'}">
		       <div class="newColumn">
		       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       <div class="rightColumn">
		       <s:radio list="remindToMap" name="%{key}" id="%{key}" value="%{'remToSelf'}" onclick="findHiddenDiv(this.value);"/>
		       </div>
		       </div>
		       
		       <div id="remToOther" style="display: none">
			   <div class="newColumn">
		       <div class="leftColumn">Employee:&nbsp;</div>
		       <div class="rightColumn">
		       <span class="needed"></span>
			   <s:select  
		                              	id			="emp_id"
		                              	name		="emp_id"
		                              	list		="emplMap"
		                              	headerKey	="-1"
		                              	headerValue ="Select Employee" 
		                              	multiple	="true"
		                              	cssClass	="select"
										cssStyle	="width:82%;height:40%"
		                          />
		  		</div>
		      	</div>
		      	</div>
		      	
		        <div id="remToBoth" style="display:none">
		      	<div class="newColumn">
		        <div class="leftColumn">Employee:&nbsp;</div>
		        <div class="rightColumn">
		        <span class="needed"></span>
				<s:select  
		                              	id			="emp_id1"
		                              	name		="emp_id"
		                              	list		="emplMap"
		                              	headerKey	="-1"
		                              	headerValue ="Select Employee" 
		                              	multiple	="true"
		                              	cssClass	="select"
										cssStyle	="width:82%;height:40%"
		                          />
		  		</div>
		        </div>
		  		</div>
			   </s:if>
	       </s:iterator>
	       
	       <div id="ownerShip" style="display:none;">
	       <s:iterator value="assetRadio">
	       	   <span id="normalSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{validationType}"/>,</span>
       		   <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       		   <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
			   <s:if test="%{key == 'action_type'}">
		       <div class="newColumn">
		       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
		       <div class="rightColumn">
		       <s:radio list="actionType" name="%{key}" id="%{key}" value="%{'individual'}"/>
		       </div>
		       </div>
			   </s:if>
		   </s:iterator>
	       </div>
       
			<s:iterator value="assetCheckBox">
		       <div class="newColumn">
			       <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
			       <div style="display: block;" id="self">
				       <div style="margin-left: -4px; margin-top: 11px;">Self:</div>
				       <div class="rightColumn" style="margin-left: 348px; margin-top: -37px;">
					       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					       Mail <s:checkbox name="selfmail" id="mail" />
					       SMS <s:checkbox name="selfsms" id="sms"/>
				       </div>
			       </div>
			       <div style="display: none;" id="other">
				       <div class="leftColumn" style="margin-left: 33px; margin-top: -12px;">Other:</div>
				       <div class="rightColumn" style="margin-left: 348px; margin-top: -37px;">
					       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					       Mail <s:checkbox name="othermail" id="mail" />
					       SMS <s:checkbox name="othersms" id="sms"/>
				       </div>
			       </div>
		       </div>
	       </s:iterator>   
       
       <s:iterator value="assetDropDown" begin="1" status="status">
        <s:if test="%{mandatory}">
       	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		</s:if>
  		<s:if test="#status.odd">
  		<s:if test="key=='escalation'">
  		<div class="newColumn">
        <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
        <div class="rightColumn">
        <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	    <s:select  
	                        id			= "escalation"
	                        name		= "escalation"
	                        list		= "escalationMap"
	                        headerKey	= "-1"
	                        headerValue = "Escalation" 
                            onchange	= "findHiddenDiv(this.value);"
                            cssClass	="select"
							cssStyle	="width:82%"
	                           >
	    </s:select>
	    </div>
	    </div>
	    </s:if>
	    </s:if>
  	    <div id="escalationDIV" style="display:none">
  	    <div class="clear"></div>
  		<div class="newColumn">
        <div class="leftColumn">L 2:&nbsp;</div>
        <div class="rightColumn">
		<s:select  
                              	id			="escalation_level_1"
                              	name		="escalation_level_1"
                              	list		="escL1Emp"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	onchange    ="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','escalation_level_2');"
                          />
          </div>
      	  </div>
      	  <div class="newColumn">
          <div class="leftColumn">L 2 TAT:&nbsp;</div>
          <div class="rightColumn">
		  <sj:datepicker id="l1EscDuration" name="l1EscDuration" readonly="true" onchange="changeEnable('l2EscDuration','escalation_level_2');getDocu('escalationDIV1');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
          
          <div id="escalationDIV1" style="display:none">
     	  <div class="clear"></div>
          <div class="newColumn">
          <div class="leftColumn">L 3:&nbsp;</div>
          <div class="rightColumn">
          <div id="l2">
						<s:select  
                              	id			="escalation_level_2"
                              	name		="escalation_level_2"
                              	list		="emplMap"
								cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled	="true"
                                onchange	="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','escalation_level_3');"
                              	
                          />
          </div>
      	  </div>
      	  </div>
          <div class="newColumn">
          <div class="leftColumn">L 3 TAT:&nbsp;</div>
          <div class="rightColumn">
          <sj:datepicker id="l2EscDuration" name="l2EscDuration" disabled="true" readonly="true" onchange="changeEnable('l3EscDuration','escalation_level_3');getDocu('escalationDIV2');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
          
          <div id="escalationDIV2" style="display:none">
     	  <div class="clear"></div>
     	  <div class="newColumn">
          <div class="leftColumn">L 4:&nbsp;</div>
          <div class="rightColumn">
          <div id="l3">
						<s:select  
                              	id			="escalation_level_3"
                              	name		="escalation_level_3"
                              	list		="emplMap"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled	="true"
                              	onchange	="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','escalation_level_4');"
                              	
                          />
          </div>
          </div>
          </div>
          <div class="newColumn">
          <div class="leftColumn">L 4 TAT:&nbsp;</div>
          <div class="rightColumn">
          <sj:datepicker id="l3EscDuration" name="l3EscDuration" disabled="true" readonly="true" onchange="changeEnable('l4EscDuration','escalation_level_4');getDocu('escalationDIV3');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
        
          <div id="escalationDIV3" style="display:none">
          <div class="clear"></div>
          <div class="newColumn">
          <div class="leftColumn">L 5:&nbsp;</div>
          <div class="rightColumn">
          <div id="l4">
						<s:select  
                              	id			="escalation_level_4"
                              	name		="escalation_level_4"
                              	list		="emplMap"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled	="true"
                              	
                          />
          </div>
          </div>
          </div>
          <div class="newColumn">
          <div class="leftColumn">L 5 TAT:&nbsp;</div>
          <div class="rightColumn">
          <sj:datepicker id="l4EscDuration" name="l4EscDuration" disabled="true" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
  	      </s:iterator>  
         
            <s:iterator value="supportColumnMap" begin="0" end="0">
	            <s:if test="%{mandatory}">
	               		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
						<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
						<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	            </s:if>
            	 <s:if test="key=='detail'"> 
	               <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{Key}" id="%{Key}" maxlength="50"  placeholder="Enter Data"  cssClass="textField"/>
			            </div>
			 	  </div>
              </s:if>
            </s:iterator>
			 <div class="clear"></div>	
			 <s:if test="moduleName=='Support'">
           <s:iterator value="supportColumnMap" begin="1" end="2" status="status">
            <s:if test="%{mandatory}">
               		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
            </s:if>
            <s:if test="#status.odd">
                 <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                name="%{key}" 
                                list="supportTypeList"
                                headerKey="-1"
                                headerValue="Select Support Type" 
                                cssClass="select"
                                cssStyle="width:82%"
                                 >
                               </s:select>
                               </div>
                  </div>
             </s:if>
             <s:else>
                  <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                                id="%{key}"
                                list="vendorList"
                                headerKey="-1"
                                headerValue="Select Vendor" 
                                cssClass="select"
                                cssStyle="width:82%"
                                onchange="commonSelectAjaxCall('createvendor_master','id','vendorname','vendorfor',this.value,'status','Active','vendorname','ASC','vendorid','Select Vendor Name');"
                                 >
                                </s:select>
                                </div>
                  </div>
           </s:else>
           </s:iterator>
           
           
            <s:iterator value="supportColumnMap" begin="3" end="3">
            <s:if test="%{mandatory}">
               		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
            </s:if>
           
            <div class="newColumn">
			<div class="leftColumn"><s:property value="%{value}"/>:</div>
			<div class="rightColumn">
                          <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                          <s:select 
                          id="%{key}"
                          name="%{key}" 
                          list="{'No Data'}"
                          headerKey="-1"
                          headerValue="Select Vendor Name" 
                          cssClass="select"
                          cssStyle="width:82%"
                           >
                          </s:select>
                          </div>
            </div>
           </s:iterator>
           </s:if>
           
				<div class="clear"></div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<center>
				<ul>
				<li class="submit" style="background: none;">
				<div class="type-button">
	        	<sj:submit 
         				targets			=	"resultTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
	                    effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                 		onBeforeTopics	=	"validateSupport"
                        indicator		=	"indicator2"
                        
     		  	  />
     		  	  <sj:a 
						button="true" href="#"
						onclick="resetSupport('configureSupport');"
						>
						Reset
				  </sj:a>
				  <s:if test="moduleName=='Support'">
     		  	  <sj:a 
						button="true" href="#"
						onclick="getViewData('Support');"
						>
						Back
					</sj:a>
					</s:if>
					<s:if test="moduleName=='Preventive'">
     		  	 	 <sj:a 
						button="true" href="#"
						onclick="getViewData('Preventive');"
						>
						Back
					</sj:a>
					</s:if>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <sj:div id="resultTarget"  effect="fold">
   	     </sj:div>
		 </div>
   </s:form>
   </div>
</div>
</body>
</html>