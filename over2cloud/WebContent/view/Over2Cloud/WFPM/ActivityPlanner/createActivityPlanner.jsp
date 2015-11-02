<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="js/referral/referral.js"/>"></script>
<script type="text/javascript" src="<s:url value="js/WFPM/ActivityPlanner/activity_planner.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    $('#state').val('-1');
    $('#city').val('-1');
    $('#territory').val('-1');
    setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}
$.subscribe('level1', function(event,data)
{
    setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
    setTimeout(function(){ $("#complTarget").fadeOut();cancelButton(); }, 4000);
	$('#completionResult').dialog('open');
});
function cancelButton()
{
	 reset('formone');
	 $('#completionResult').dialog('close');
}
function viewGroup() 
{
	 $('#abc').dialog('close');
}
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
$(document).ready(function()
{
    $("#activity_for").multiselect({
	   show: ["", 200],
	   hide: ["explode", 1000]
	});
});
function setMonth(value)
{
	$("#for_monthForm").val(value);
}
function setterritory(value)
{
	$("#territoryForm").val(value);
}
function showHideSchedule(value)
{
	//alert(value);
	if(value=='Field Activity')
	{
		$("#eventDiv").hide();
		$("#actiDiv").show();
		$("#eventDiv").empty();
	}
	else
	{
		$("#actiDiv").hide();
		$("#eventDiv").show();
		var month = $("#for_month").val();
		$("#eventDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/patientCRMMaster/beforeAddEventPlan.action?date="+month,
		    success : function(subdeptdata) {
	       $("#"+"eventDiv").html(subdeptdata);
		   },
		   error: function() {
	            alert("error");
	        }
		 });
	  }
}
function changeDate(val)
{
	var month = $("#for_month").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/changeDate.action?month="+month+"&dataFor="+val,
	    success : function(subdeptdata) 
	   {
		  $("#for_month").val(subdeptdata[0].date);
		  $("#dayId").val(subdeptdata[0].day);
		  $("#for_monthForm").val(subdeptdata[0].date);
	   },
	   error: function() 
	   {
           alert("error");
      }
	 });
}
function editActivity()
{
	
}
function changeVal(val)
{
	if(val=='Yes')
	{
		$("#yesDiv").show();
	}
	else
	{
		$("#yesDiv").hide();
	}
}
</script>
</head>
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
          <div id="complTarget"></div>
</sj:dialog>
<div class="list-icon">
          <s:iterator value="dateField" status="counter">
                <s:if test="%{mandatory}">
                    <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                </s:if>
                <s:if test="#counter.count%2 !=0">
                 For:
                          <sj:datepicker id="%{key}"  name="%{key}"  onchange="setMonth(this.value);" value="%{date}" cssStyle="width: 95px;" readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Month"/>                
                          <s:textfield id="dayId"  readonly="true" cssStyle="width: 83px;height: 25px;background-color: rgb(233, 233, 233);"></s:textfield>
                </s:if>
          </s:iterator>
          <s:iterator value="dropDown" status="counter" end="3">
	                  <s:if test="%{key=='state'}">
		           			<s:if test="%{state}">
		           			  <s:property value="%{value}"/>:
			               		 <s:select 
			                         id="%{key}"
			                         list="commonMap"
			                         headerKey="-1"
			                         headerValue="Select %{value}" 
			                          cssStyle="width: 119px;background-color: white;"
			                         onchange="fetchCity(this.value,'city')"
			                         >
			                       </s:select>  
		           			
		           			</s:if>              
	                 </s:if>
	                 <s:elseif test="%{key=='city'}">
	                     <s:if test="%{city}">
			                <s:property value="%{value}"/>:
	               		 <s:select 
	                         id="%{key}"
	                         list="commonMap"
	                         headerKey="-1"
	                         headerValue="Select %{value}"
	                          cssStyle="width: 119px;background-color: white;" 
	                         onchange="fetchTerritory(this.value,'territory')"
	                         >
	                            </s:select>   
                        </s:if>             
	                 </s:elseif>
	                 <s:elseif test="%{key=='territory'}">
	                    <s:if test="%{terri}">
		                  <s:property value="%{value}"/>:
                       		 <s:select 
                                 id="%{key}"
                                 list="commonMap"
                                  cssStyle="width: 119px;background-color: white;"
                                 headerKey="-1"
                                 headerValue="Select %{value}" 
                                 onchange="setterritory(this.value);"
                                 >
                           </s:select> 
                        </s:if>                  
	                 </s:elseif>
	         </s:iterator>
	        <div style="float: right;"> 
		         <a href="#" onclick="changeDate('previous');"><img src="images/backward.png"></img></a>
		         <a href="#" onclick="changeDate('next');"><img src="images/forward.png"></img></a>
	        </div>
</div>
<div class="clear"></div>
     <div >
       <div class="border" >
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addActivity" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
            <s:hidden name="for_month" id="for_monthForm" value="%{date}"/>
            <s:hidden name="territory" id="territoryForm" value="%{territory}"/>
             <s:hidden name="status" id="status" value="%{status}"/>
            <s:if test="%{fullDetails.size()>0}">
					<table width="100%" border="1" style="border-collapse: collapse;">
					<tr  bgcolor="#D8D8D8" style="height:25px">
						<td colspan="1" width="%">
								<strong>Activity For</strong>
						</td>	
						<td colspan="1" width="%">
								<strong>Activity Type</strong>
						</td>	
						<td colspan="1" width="%">
								<strong>Schedule</strong>
						</td>	
						<td colspan="1"  width="%">
								<strong>Comments</strong>
						</td>	
						<!--<td colspan="1"  width="%">
								<strong>Edit</strong>
						</td>	
					--></tr>
					 <s:iterator value="%{fullDetails}" id="totalCompl" status="status">
					       <s:if test="#status.odd">
					       		 <tr style="height: 25px;">
					             	<td  align="left" width="10%"><s:property value="#totalCompl.activity_for" /></td>
					                <td  align="left" width="10%"><s:property value="#totalCompl.activity_type" /></td>
					                <td  align="left" width="10%"><s:property value="#totalCompl.schedule_time" /></td>
					                <td  align="left" width="10%"><s:property value="#totalCompl.comment" /></td><!--
					                <td  align="left" width="10%"><img height="20px" width="20px" src="images/1386942655_edit.png" onclick="editActivity('<s:property value="#totalCompl.id" />')"/></td>
					            --></tr>
					       </s:if>
					       <s:else>
					       		<tr bgcolor="#D8D8D8"  style="height: 25px;">
					             	<td  align="left" width="10%"><s:property value="#totalCompl.activity_for" /></td>
					                <td  align="left" width="10%"><s:property value="#totalCompl.activity_type" /></td>
					                <td  align="left" width="10%"><s:property value="#totalCompl.schedule_time" /></td>
					                <td  align="left" width="10%"><s:property value="#totalCompl.comment" /></td><!--
					                <td  align="left" width="10%"><img height="20px" width="20px" src="images/1386942655_edit.png" onclick="editActivity('<s:property value="#totalCompl.id" />')"/></td>
					            --></tr>
					       </s:else>
					</s:iterator>
					</table>
            
            </s:if>
                <div class="clear"></div>
                <s:iterator value="dropDown" status="counter" begin="4" end="5">
	                 <s:if test="%{key=='rel_type'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="%{key}"
			                                      name="%{key}"
			                                      list="relationMap"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}" 
			                                      cssClass="textField"
			                                      onchange="fetchRelationSubType(this.value,'rel_sub_type')"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
	                 </s:if>
	                 <s:elseif test="%{key=='rel_sub_type'}">
	                 <s:hidden name="rel_sub_type" id="rel"></s:hidden>
	                 <s:hidden name="SubRel" id="SubRel"></s:hidden>
	                 
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="%{key}"
			                                      list="{'No Data'}"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}" 
			                                      cssClass="textField"
			                                      onchange="fetchActivityFor('rel_sub_type','territory','activityDiv');"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
		                     <div class="newColumn">
		                                <div class="leftColumn1">Schedule Plan For:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="scheduleplan"
			                                      name="schedule_plan"
			                                      list="{'Field Activity','Event'}"
			                                      headerKey="-1"
			                                      headerValue="Select Schedule Plan For" 
			                                      cssClass="textField"
			                                      onchange="showHideSchedule(this.value);fetchActivityTypeEvent('rel_sub_type','scheduleplan','activity_type','event_type');"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
	                 </s:elseif>
                 </s:iterator>
                  <div id="actiDiv" style="display: none;">
                   <s:iterator value="dropDown" status="counter" begin="6">
	                
	                  <s:if test="%{key=='activity_for'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <div id="activityDiv">
		                             		 	<s:select 
			                                      id="%{key}"
			                                      name="%{key}"
			                                      list="{'No Data'}"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}" 
			                                      cssClass="textField"
			                                      multiple="true"
			                                      cssStyle="width:5%"
			                                      >
                                            </s:select>  
		                             		 </div>              
		                                </div>
		                  </div>
	                   </s:if>
	                   <s:elseif test="%{key=='activity_type'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="%{key}"
			                                       name="%{key}"
			                                      list="{'No Data'}"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}" 
			                                      cssClass="textField"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
	                 </s:elseif>
	                 </s:iterator>
	                 
	                  <div class="newColumn">
		                                <div class="leftColumn1">Advance Required:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:radio 
			                                      id="advance_required"
			                                       name="advance_required"
			                                      list="{'Yes','No'}"
			                                      onchange="changeVal(this.value);"
			                                      >
                                            </s:radio>                
		                                </div>
		                  </div>
	                 <div id="yesDiv" style="display: none;">
	                  <div class="newColumn">
		                                <div class="leftColumn1">Amount:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<s:textfield name="amount" id="amount"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
	                 
	                 </div>
	                 
	                 </div>
	                 <div id="eventDiv" style="display: none;"></div>
	                 
	                 
	                 
                    <s:iterator value="timeField" status="counter">
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="#counter.count%2 !=0">
	                    <div class="clear"></div>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<sj:datepicker id="%{key}adsfdsgf"  name="%{key}"  readonly="true" cssClass="textField" size="20" timepicker="true" timepickerOnly="true"  showOn="focus"  Placeholder="Select Data"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:else>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			 <sj:datepicker id="%{key}sfdfd"  name="%{key}"  readonly="true" cssClass="textField" size="20" timepicker="true" timepickerOnly="true"  showOn="focus" Placeholder="Select Data"/>
		                  				</div>
		                  </div>
	                 </s:else>
                 </s:iterator>
                   <s:iterator value="textBox" status="counter">
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="#counter.count%2 !=0">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:else>
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
		                  				</div>
		                  </div>
	                 </s:else>
                 </s:iterator>
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 10px;margin-left:-80px" align="center">
                        <sj:submit 
                             targets="complTarget" 
                             clearForm="true"
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#FFFFFF'}"
                             effectDuration="5000"
                             button="true"
                             onBeforeTopics="validate"
                             onCompleteTopics="level1"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: 139px;margin-top: -41px;"
                             onclick="reset('formone');resetColor('.pIds');"
                        />&nbsp;&nbsp;
                        <sj:a
                            cssStyle="margin-left: 276px;margin-top: -58px;"
                        button="true" href="#" value="View" onclick="viewGroup();" cssStyle="margin-left: 266px;margin-top: -74px;" 
                        >Back
                    </sj:a>
                    </div>
               </div>
             </s:form>          
</div>
</div>
</body>
</html>

