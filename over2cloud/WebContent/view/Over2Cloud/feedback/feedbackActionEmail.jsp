<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript">
$.subscribe('beforeFirstAccordian1', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
           });
</script>
<script type="text/javascript">
function getFeedbackStatus(statusId)
{
	var statusType=$("#"+statusId).val();
	if(statusType=='Resolved')
	  {
	    
        showHideFeedDiv('resolvedDiv','highPriorityDiv','snoozeDiv','ignoreDiv','reAssign');
	}
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
<body>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Pending Feedback" id="acor_item1">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" action="/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/actionOnFeedbackViaMail.action"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
		    <s:hidden name="ticketno" id="ticketno" value="%{#parameters.ticketNo}"></s:hidden>
		    <s:hidden name="feedid" id="feedid" value="%{#parameters.feedId}"></s:hidden>
		    <div class="form_menubox">
               <div class="user_form_text" style="text-indent:20px;">Ticket No:</div>
               <div class="user_form_input"><s:textfield name="ticket_no" readonly="true" id="ticket_no" value="%{#parameters.ticketNo}" cssClass="form_menu_inputtext"/></div>
               <div class="user_form_text1" style="text-indent:10px;">Feedback By:</div>
               <div class="user_form_input"><s:textfield name="feedback_by" readonly="true" id="feedback_by" value="%{#parameters.feedbackBy}" cssClass="form_menu_inputtext"/></div>
            </div>
            
            <div class="form_menubox">
               <div class="user_form_text" style="text-indent:20px;">Mobile No:</div>
               <div class="user_form_input"><s:textfield name="feedback_by_mobno" readonly="true" id="feedback_by_mobno" value="%{#parameters.mobileno}" cssClass="form_menu_inputtext"/></div>
               <div class="user_form_text1" style="text-indent:10px;">Email Id:</div>
               <div class="user_form_input"><s:textfield name="feedback_by_emailid" readonly="true" id="feedback_by_emailid" value="%{#parameters.emailId}" cssClass="form_menu_inputtext"/></div>
            </div>
            
            <div class="form_menubox">
               <div class="user_form_text" style="text-indent:20px;">Brief:</div>
               <div class="user_form_input"><s:textfield readonly="true" name="feedback_brief" id="feedback_brief" value="%{#parameters.feedBreif}" cssClass="form_menu_inputtext"/></div>
            </div>
            <div class="form_menubox">
              <div class="user_form_text" style="text-indent:20px;">Status:*</div>
              <div class="user_form_input">
                  <s:select 
                             id="status"
                             name="status" 
                             list="{'Resolved'}" 
                             headerKey="-1"
                             headerValue="Select Status"
                             onchange="getFeedbackStatus('status');"
                             value="%{#parameters.status}"
                             cssClass="form_menu_inputselect"
                              >
                  </s:select>
                  </div>
		  </div>
         <div id="resolvedDiv" style="display:none">
              <div class="form_menubox">
                   <div class="user_form_text" style="text-indent:20px;">Allot To:</div>
                   <div class="user_form_input"><s:textfield readonly="true" name="allotto" id="allotto" value="%{#parameters.allotto}"  cssClass="form_menu_inputtext"/></div>
              </div>
              <div class="form_menubox">
                   <div class="user_form_text" style="text-indent:20px;">Action Taken:*</div>
                   <div class="user_form_input"><s:textfield name="remark" id="remark"  cssClass="form_menu_inputtext"/></div>
                   <div class="user_form_text1" style="text-indent:10px;">Spare Used:*</div>
                   <div class="user_form_input"><s:textfield name="spareused" id="spareused"  cssClass="form_menu_inputtext"/></div>
              </div>
              <div class="form_menubox">
                   <div class="user_form_text" style="text-indent:20px;">RCA:*</div>
                   <div class="user_form_input"><s:textfield name="rca" id="rca"  cssClass="form_menu_inputtext"/></div>
                   <div class="user_form_text1" style="text-indent:10px;">CAPA:*</div>
                   <div class="user_form_input"><s:textfield name="capa" id="capa"  cssClass="form_menu_inputtext"/></div>
              </div>
   </div>
   <div class="fields">
   <ul><li class="submit">
   <div class="type-button">
   <sj:submit 
	           targets="target1" 
	           clearForm="true"
	           value="  Action  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onCompleteTopics="beforeFirstAccordian1"
	           cssClass="submit"/>
   </div>
   </li></ul>
   </div>
   <sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
</s:form>
</div>
</div>
</sj:accordionItem>

</sj:accordion>
</div>
</div>
</body>
</html>
