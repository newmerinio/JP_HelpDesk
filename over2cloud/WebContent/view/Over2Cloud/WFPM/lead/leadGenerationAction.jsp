<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@page import="java.util.List"%>
<%
int temp=0;
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
	<link href="css/style3.css" rel="stylesheet" type="text/css" />
	<script src="<s:url value="js/lead/leadGenerationAction.js"/>"></script>
	<SCRIPT type="text/javascript">
		var status = '${status}';
	</SCRIPT>
<!--<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

-->
<!--<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">	
$(document).ready(function(){
$("#productselect").multiselect();
});
</script>	
	
	
	
--></head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
         <sj:div id="leadgntion"  effect="fold"><div id="leadresult"></div></sj:div>
</sj:dialog>
<s:form id="formTwo" name="formTwo" cssClass="cssn_form" namespace="/view/Over2Cloud/WFPM/Lead" 
	action="updateLeadAction" theme="simple"  method="post" enctype="multipart/form-data" >
	<s:hidden name="id" value="%{id}"></s:hidden>
		<div class="clear"></div>
	<div class="middle-content">
	<div class="list-icon">
		<div class="head"><s:property value="#parameters.mainHeaderName"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
		<div class="head">Take Action</div>
		<div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head"><s:property value="%{leadName}"/></div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div style="width: 100%; text-align: center; padding-top: 10px;">
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
    <div id="errZone" style="float:left; margin-left: 7px">
    </div> 
    </div>

<div class="border">	 
	<div class="form_menubox">
	 <!-- Validation -->
	 <span id="form2MandatoryFields" class="pIds" style="display: none; ">clienttypeName#Convert#D#,</span>
	 <span id="form2MandatoryFields" class="pIds" style="display: none; ">statusselect#Status#D#,</span>
	 <span id="form2MandatoryFields" class="pIds" style="display: none; ">productselect#Offering#D#,</span>
	 <span id="form2MandatoryFields" class="pIds" style="display: none; ">userselect#Employee#D#,</span>
	 <span id="form2MandatoryFields" class="pIds" style="display: none; ">time#Time#D#,</span>
	 <span id="form2MandatoryFields" class="pIds" style="display: none; ">comment#comment#T#a,</span>
			 
	<!-- Basic information -->
	<s:iterator value="leadBasicForAction" var="var">
		<s:if test="%{#var[1] == 'industry'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{#var[0]}"/>:</div>
			<div class="rightColumn1"><span class="needed"/>
			     <s:select
							id="industryID" 
							name="industry" 
							list="industryList" 
							headerKey="-1"
							headerValue="Select Industry" 
							cssClass="select"
                            cssStyle="width:82%"
                            onclick="fillName(this.value,'subIndustryID')"
                            value="%{#var[2]}"
                            >
				</s:select>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{#var[1] == 'subIndustry'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{#var[0]}"/>:</div>
			<div class="rightColumn1"><span class="needed"/>
			     <s:select
							id="subIndustryID" 
							name="subIndustry" 
							list="jsonArray"
							listKey="ID"
							listValue="NAME" 
							cssClass="select"
                            cssStyle="width:82%"
                            value="%{#var[2]}"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:else>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{#var[0]}"/>:</div>
			<div class="rightColumn1"><span class="needed"/>
			     <s:textfield name="%{#var[1]}"  id="%{#var[1]}" value="%{#var[2]}"  cssClass="textField" placeholder="Enter Data" 
			     	cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
			</div>
		</s:else>
	
	<s:if test="%{#status.even}">
		<div class="clear"></div>
	</s:if>	
	</s:iterator>		  
	<div class="clear"></div>
	
	<!-- Contact information -->
	<s:iterator value="leadContactForAction" var="var">
		  <div class="newColumn">
		  <div class="leftColumn1"><s:property value="%{#var[0]}"/>:</div>
		  <div class="rightColumn1"><span class="needed"/>
		     <s:textfield name="%{#var[1]}"  id="%{#var[1]}" value="%{#var[2]}"  cssClass="textField" placeholder="Enter Data" 
		     	cssStyle="margin:0px 0px 10px 0px;"/>
		  </div>
		  </div>
	
	<s:if test="%{#status.even}">
		  <div class="clear"></div>
	</s:if>	
   </s:iterator>		  
	<div class="clear"></div>
	
	<div class="newColumn">
	<div class="leftColumn1">Target Segment</div> 
	<div class="rightColumn1">
	      <s:select
	             id="weightage_targetsegment" 
	             name="weightage_targetsegment"
	             list="weightageMasterMap"
	             headerKey="-1"
                 headerValue="Select" 
                 cssClass="select" 
                 cssStyle="width:82%"
	             
	            >
	       </s:select>
	 </div>
	 </div>           
	
	 
	<!-- Contact information -->
			 
			<div class="newColumn">
                  <div class="leftColumn1">Convert To:</div>
                  <div class="rightColumn1"><span class="needed"></span>
	                <select name="clienttypeName" id='clienttypeName' style="width:82%"
	                	    class="select"
                            style="width:82%"
	                	    onchange="setData();checkData();">
							<option value="-1">Select Type</option>
							<option value="1">Prospective Client</option>
							<option value="2">Prospective Associate</option>
							<option value="3">Snoozed Lead</option>
							<option value="4">Lost Lead</option>
					</select>
                  </div>
			</div>
                  <div id="hideStatus">
                  <div class="newColumn">
				      <div class="leftColumn1">Status:</div>
	                  <div class="rightColumn1"><span class="needed"></span>
		                  <SELECT 
		                  		id="statusselect" 
		                  		name="statusselect"  
	                  			class="select"   
	                  			style="width:82%"
	                  			>
						  <option value="-1">Select Status</option>
						  </SELECT>
	                  </div>
                  </div>
                  </div>
             </div>
             <div class="clear"></div>
          <div id="take">
          <div class="form_menubox">
               	  <div class="newColumn">
                  <div class="leftColumn1">Offering Interested:</div>
                  <div class="rightColumn1">
                  <span class="needed"></span>
                   <s:select 
                              id="productselect"
                              name="productselect" 
                              list="offeringList2"
                              multiple="true"
                              headerKey="-1"
                              headerValue="Select Offering" 
                            
                              cssClass="select" 
                              cssStyle="width:50%;height:100px"
                              >
                  </s:select>
                  </div>
                  </div>
                  <div id="mapEmpDiv">
               	  <div class="newColumn">
                  <div class="leftColumn1">Account Manager :</div>
                  <div class="rightColumn1">
                  <span class="needed"></span>
                  <s:select 
                              id="userselect"
                              name="userselect" 
                              list="sourceList"
                              headerKey="-1"
                              headerValue="Select Employee" 
                              cssClass="select" 
                              cssStyle="width:82%"
                              >
                  </s:select>
                  </div>
                  </div>
                  </div>
             </div>
       </div>   
       <div class="clear"></div>
       <div class="form_menubox">
                <div id="nextSchDiv">
               	  <div class="newColumn">
                  <div class="leftColumn1">Next Schedule:</div>
                  <div class="rightColumn1">
                  <span class="needed"></span>
                  <sj:datepicker id="time" displayFormat="dd-mm-yy"  
                  	placeholder="Enter Data" cssClass="textField" name ="time" label="With AM/PM" timepicker="true" 
                  	timepickerAmPm="true"  showOn="focus" onchange="validateLeadTime(%{id}, this.value);"/>
                </div>
                </div>
                <div id="timeValidationLead" style="margin-left: 81%; display: none;width: 20%; color: red;" ></div>
                 	<s:textfield name="leadtextdata" id="leadtextdataid" value=""  />
                  <div id="clientTime"></div>
                  </div>
        </div>  
        
       <div class="clear"></div>
       <div class="form_menubox">
              	  <div class="newColumn">
			      <div class="leftColumn1">Location:</div>
                  <div class="rightColumn1">
                  	<s:textfield name="location" cssClass="textField"  id="location" placeholder="Enter Data" />
                  </div>
                  </div>
                  
              	  <div class="newColumn">
                  <div class="leftColumn1">Comment:</div>
                  <div class="rightColumn1">
                  <span class="needed"></span>
                  <s:textarea cssClass="textcell" id="comment" name="comment" cssClass="textField"  
                  	placeholder="Enter Data" theme="simple"></s:textarea>
                  <div id="clientComments"></div>
                  </div>
                  </div>
         </div>
       <div class="clear"></div>
       	<div class="form_menubox">
    
              	  <div class="newColumn">
				 	  <div class="leftColumn1">Email:</div>
	                  <div class="rightColumn1"><s:checkbox name="sendEmail"   cssStyle="margin:0px 0px 10px 0px" /></div>
                  </div>			       
              	  <div class="newColumn">
	                  <div class="leftColumn1">SMS:</div>
	                  <div class="rightColumn1"><s:checkbox name="sendSms"   cssStyle="margin:0px 0px 10px 0px" /></div>
                  </div>			       
 		</div>
		<div id="take1">
		</div>
           
	<center>
				<img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
				
				<div class="fields" align="center">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
							<sj:submit 
								id="takeActionBtnId"	
		                        targets="leadresult" 
		                        clearForm="true"
		                        value="Take Action" 
		                        effect="highlight"
		                        effectOptions="{ color : '#222222'}"
		                        effectDuration="5000"
		                        button="true"
		                        onCompleteTopics="level2"
		                        cssClass="submit"
		                        indicator="indicator3"
		                        onBeforeTopics="validate1"
		                        />
		                        
            	          <sj:a 
	          
							     	name="Reset"  
									href="#" 
									cssClass="submit" 
									button="true" 
									onclick="resetForm3('formTwo');"
								>
								  	Reset
								</sj:a>
								
		                    <sj:submit 
	                        value="Back" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        cssClass="submit"
	                        onclick="cancelleadview();"
	                        />
							   </div>
				</ul>
							<div class="clear"></div>
							
						</div>
				</center>
	<br><br>   
        </div>
	        
</s:form>
</body>
</html>