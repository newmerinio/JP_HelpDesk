<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/actionValidation.js"/>"></script>
<script type="text/javascript">
function getFeedbackStatus(statusId)
{
	var statusType=$("#"+statusId).val();

	 if(statusType=='High Priority')
	  {
		showHideFeedDiv('highPriorityDiv','snoozeDiv','resolvedDiv','ignoreDiv','reAssign');
	  }

	if(statusType=='Snooze')
	  {
		showHideFeedDiv('snoozeDiv','highPriorityDiv','resolvedDiv','ignoreDiv','reAssign');
	  }

	if(statusType=='Ignore')
	  {
	      showHideFeedDiv('ignoreDiv','highPriorityDiv','resolvedDiv','snoozeDiv','reAssign');
	  }	  
	
	if(statusType=='Resolved')
	  {
	    var	feedId=$("#complaintid").val();
        showHideFeedDiv('resolvedDiv','highPriorityDiv','snoozeDiv','ignoreDiv','reAssign');
        $.ajax({
	           type : "post",
	           url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getResolver.action?complaintid="+feedId,
	           success : function(feeddata) {
            $("#resolverAjaxDiv").html(feeddata);
	         },
	   error: function() {
         alert("error");
     }
	 });
	}

	if(statusType=='Re-Assign')
    {
	    var feedStatus = 'reAllot';
         showHideFeedDiv('reAssign','highPriorityDiv','resolvedDiv','snoozeDiv','ignoreDiv');
             $.ajax({
             type : "post",
             url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/reAllotTicket.action?feedStatus="+feedStatus+"&dataFor=ASTM",
             success : function(feeddata) {
             $("#reAssign").html(feeddata);
            },
            error: function()
             {
               alert("error");
             }
         });
    }
}



function getRCA()
{
    var	feedId=$("#complaintid").val();
       $.ajax({
           type : "post",
           url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getRCA.action?complaintid="+feedId,
           success : function(feeddata) {
           $("#rcaAjaxDiv").html(feeddata);
         },
   error: function() {
        alert("error");
    }
 });
}

function showHideFeedDiv(showDiv,hideDiv1,hideDiv2,hideDiv3,hideDiv4)
{
	if($("#"+showDiv).css('display') == 'none')
		$("#"+showDiv).show('slow');
	 
	if($("#"+hideDiv1).css('display') != 'none')
		$("#"+hideDiv1).hide('slow');

	if($("#"+hideDiv2).css('display') != 'none')
	$("#"+hideDiv2).hide('slow');
	
    if($("#"+hideDiv3).css('display') != 'none')
	$("#"+hideDiv3).hide('slow');

    if($("#"+hideDiv4).css('display') != 'none')
        $("#"+hideDiv4).hide('slow');
}
</script>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head"><s:property value="%{feedStatus}"/> Feedback</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Action</div>
</div>
<div class="clear"></div>
<div style="overflow:auto; height:420px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<body>
		<s:form id="formone" name="formone" action="actionOnFeedback"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
		    <s:hidden id="complaintid" name="complaintid"  value="%{#parameters.feedId}"/>
		    <s:hidden id="old_status" name="old_status"  value="%{#parameters.status}"/>
		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
		    
		    <div class="newColumn">
		       <div class="leftColumn">Ticket No:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="ticket_no" id="ticket_no" value="%{#parameters.ticketNo}"  cssClass="textField" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn">Feedback By:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="feedback_by" id="feedback_by" value="%{#parameters.feedbackBy}"  cssClass="textField" />
		            </div>
            </div>
		    <div class="clear"></div>
		    
		    <div class="newColumn">
		       <div class="leftColumn">Mobile No:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="feedback_by_mobno" id="feedback_by_mobno" value="%{#parameters.mobileno}"  cssClass="textField" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn">Email Id:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="feedback_by_emailid" id="feedback_by_emailid" value="%{#parameters.emailId}"  cssClass="textField" />
		            </div>
            </div>
		    <div class="clear"></div>
            
            <div class="newColumn">
		       <div class="leftColumn">Sub Category:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="feedback_subcatg" id="feedback_subcatg" value="%{#parameters.subCatg}"  cssClass="textField" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn">Brief:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="feedback_brief" id="feedback_brief" value="%{#parameters.feedBreif}"  cssClass="textField" />
		            </div>
            </div>
		    <div class="clear"></div>
            
            <div class="newColumn">
		       <div class="leftColumn">Open Date:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="open_date" id="open_date" value="%{#parameters.openDate}" cssClass="textField" />
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn">Open Time:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="open_time" id="open_time" value="%{#parameters.openTime}" cssClass="textField" />
		            </div>
            </div>
		    <div class="clear"></div>
            
             <div class="newColumn">
		       <div class="leftColumn">Status:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:select 
		                             id="status"
		                             name="status" 
		                             list="statusList" 
		                             headerKey="-1"
		                             headerValue="Select Status"
		                             onchange="getFeedbackStatus('status');"
		                             value="%{#parameters.status}"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>    
		              </div>
		       </div>       
		         <div>     
		           <span id="normalSpan" class="pIds" style="display: none; ">status#status#D#,</span>
		           <span id="highPrioritySpan" class="hPpIds" style="display: none; ">status#status#D#,</span>
		           <span id="ignoreSpan" class="iGpIds" style="display: none; ">status#status#D#,</span>
		           <span id="snoozeSpan" class="sZpIds" style="display: none; ">status#status#D#,</span>
		           <span id="resolveSpan" class="reSpIds" style="display: none; ">status#status#D#,</span>         
           
	           <div id="highPriorityDiv" style="display:none">
	           <div class="newColumn">
			       <div class="leftColumn">Reason/ RCA:</div>
			            <div class="rightColumn">
			              <span id="highPriority" class="hPpIds" style="display: none; ">hpcomment#Comment#T#an,</span>
			                	<span class="needed"></span>
				                 <s:textfield name="hpcomment" id="hpcomment" placeholder="Enter High Priority Comment" cssClass="textField" />
			            </div>
	            </div>
	            </div>
           
	           <div id="ignoreDiv" style="display:none">
	           <div class="newColumn">
			       <div class="leftColumn">Reason/ RCA:</div>
			            <div class="rightColumn">
			            <span id="ignoreSpan" class="iGpIds" style="display: none; ">ignorecomment#Ignore Comment#T#an,</span>
			                	<span class="needed"></span>
				                 <s:textfield name="ignorecomment" id="ignorecomment" placeholder="Enter Ignore Comment" cssClass="textField" />
			            </div>
	            </div>
	            </div>
           
             </div>
             
             <div class="clear"></div>
             
                 <div id="snoozeDiv" style="display:none">
               <div>
               <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozeDate#Snooze Date#Date#,</span> 
               <div class="newColumn">
		       <div class="leftColumn">Snooze Date:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <sj:datepicker id="snoozeDate" name="snoozeDate" readonly="true" placeholder="Select Snooze Date"  showOn="focus"  displayFormat="dd-mm-yy"  minDate="0" cssClass="textField"/>
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Snooze Time:</div>
		            <div class="rightColumn">
		            <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozeTime#Snooze Time#Time#,</span> 
		                	 <span class="needed"></span>
			                 <sj:datepicker id="snoozeTime" name="snoozeTime" readonly="true" placeholder="Select Snooze Time"  showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField"/>
		            </div>
               </div>
               </div>
               
               <span id="snoozeSpan" class="sZpIds" style="display: none; ">snoozecomment#Snooze Comment#T#an,</span>
               <div class="newColumn">
		       <div class="leftColumn">Reason/ RCA:</div>
		            <div class="rightColumn">
		                	<span class="needed"></span>
			                 <s:textfield name="snoozecomment" id="snoozecomment" placeholder="Enter Snooze Comment"  cssClass="textField" />
		            </div>
               </div>
               </div>
               
               <div id="resolvedDiv" style="display:none">
               <div class="newColumn">
		       <div class="leftColumn">Allot To:</div>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="allotto" id="allotto" value="%{#parameters.allotto}" readonly="true"  cssClass="textField" />
		            </div>
               </div>
               
               <div class="newColumn">
               <span id="resolveSpan" class="reSpIds" style="display: none; ">resolver#Resolver#D#,</span> 
		       <div class="leftColumn">Resolve By:</div>
		            <div class="rightColumn">
		                	<span class="needed"></span>
			                 <div id="resolverAjaxDiv">
		                        <s:select 
		                                  id="resolver"
		                                  name="resolver"
		                                  list="{'No Data'}"
		                                  headerKey="-1"
		                                  headerValue="Select Resolver"
		                                  cssClass="select"
		                                  cssStyle="width:82%"
		                                  >
		                        </s:select>
                   </div>
		            </div>
               </div>
                <div class="clear"></div>
               <div class="newColumn">
		       <div class="leftColumn">RCA:</div>
		            <div class="rightColumn">
		                	 <div id="rcaAjaxDiv">
		                        <s:select 
		                                  id="rca"
		                                  name="rca"
		                                  list="{'No Data'}"
		                                  headerKey="-1"
		                                  headerValue="Select RCA" 
		                                  cssClass="select"
		                                  cssStyle="width:82%"
		                                  >
		                        </s:select>
                   </div>
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Action Taken:</div>
		        <span id="resolveSpan" class="reSpIds" style="display: none; ">remark#Remark#T#an,</span>
		            <div class="rightColumn">
		                	 <span class="needed"></span>
			                 <s:textfield name="remark" id="remark"  cssClass="textField" />
		            </div>
               </div>
               <div class="newColumn">
               <span id="resolveSpan" class="reSpIds" style="display: none; ">spareCatg#Spare Category#D#,</span> 
		       <div class="leftColumn">Spare Category:</div>
		       <div class="rightColumn">
		                	<span class="needed"></span>
		                        <s:select 
		                                  id="spareCatg"
		                                  list="spareCatgMap"
		                                  headerKey="-1"
		                                  headerValue="Select Category"
		                                  cssClass="select"
		                                  cssStyle="width:82%"
		                                  onchange="commonSelectAjaxCall('spare_detail','id','spare_name','spare_category',this.value,'','','spare_name','ASC','spareName','Select Spare');"
		                                  >
		                        </s:select>
               </div>
		       </div>
		       
		       <div class="newColumn">
               <span id="resolveSpan" class="reSpIds" style="display: none; ">spareName#Spare Name#D#,</span> 
		       <div class="leftColumn">Spare Name:</div>
		       <div class="rightColumn">
		                	<span class="needed"></span>
		                        <s:select 
		                                  id="spareName"
		                                  name="spareName"
		                                  list="{'No data'}"
		                                  headerKey="-1"
		                                  headerValue="Select Spare"
		                                  cssClass="select"
		                                  cssStyle="width:82%"
		                                  >
		                        </s:select>
               </div>
		       </div>
               <div class="newColumn">
		       <div class="leftColumn">No's Used:</div>
		            <div class="rightColumn">
			                 <s:textfield name="no_spareused" id="no_spareused"  cssClass="textField" />
		            </div>
               </div>
               <div class="newColumn">
		       <div class="leftColumn">Resource Used:</div>
		            <div class="rightColumn">
			                 <s:textfield name="spareused" id="spareused"  cssClass="textField" />
		            </div>
               </div>
               </div>
               <span id="normalSpan" class="reAssignPIds" style="display: none; ">status#status#D#,deptname3#todept#D#,subdeptname3#tosubdept#D#,fbType3#feedTypeId#D#,reAssignRemark#reAssignRemark#T#an,</span>
               <div id="reAssign" style="display:none"></div>
            
   <!-- Buttons -->
   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   <sj:submit 
	           targets="target1" 
	           clearForm="true"
	           value="  Save  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           onCompleteTopics="successfullSubmission"
	           button="true"
	           cssStyle="margin-left: -30px;"
			   />
	            <sj:a cssStyle="margin-left: 203px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="viewPendingForm();">View</sj:a>
   </div>
   </div>
   </li>
   </ul>
   </div>
   <div id="target1"></div>
</s:form>
</body>
</div>
</div>
</html>
