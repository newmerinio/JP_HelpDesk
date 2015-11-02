<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="js/WFPM/ActivityPlanner/activity_planner.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}
$.subscribe('level1', function(event,data)
{
   setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
   setTimeout(function(){ $("#complTarget").fadeOut();cancelButton(); }, 12000);
   $('#completionResult').dialog('open');
});
function cancelButton()
{
	 $('#completionResult').dialog('close');
	 viewGroup() ;
}
function viewGroup() 
{
	 $('#abc').dialog('close');
}
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
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
	 <div class="head">
	 Advance</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Accounts Add</div> 
</div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addAdvanceAmount" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
	          <s:hidden id="id" name="activity_id" value="%{#parameters.id}"/>
	          <s:hidden id="ticket_no" name="ticket_no" value="%{#parameters.ticket_no}"/>
                    <table width="100%" border="1">
				    		<tr  bgcolor="lightgrey" style="height: 25px">
				    			<td align="left" ><strong>Name:</strong></td>
								<td align="left" ><s:property value="#parameters.name"/></td>
								
								<td align="left" ><strong>TIcket No:</strong></td>
								<td align="left" ><s:property value="#parameters.ticket_no"/></td>
						    </tr>
						    <tr  bgcolor="white" style="height: 25px">
						          <td align="left" ><strong>Activity For:</strong></td>
								  <td align="left" ><s:property value="#parameters.actFor"/></td>
						          <td align="left" ><strong>Activity Type:</strong></td>
								  <td align="left" ><s:property value="#parameters.actType"/></td>
						   </tr>
						   	<tr  bgcolor="lightgrey" style="height: 25px">
				    			<td align="left" ><strong>Month:</strong></td>
								<td align="left" ><s:property value="#parameters.month"/></td>
								 <td align="left" ><strong>Schedule:</strong></td>
								 <td align="left" ><s:property value="#parameters.from"/></td> 
								
						    </tr>
						    <tr  bgcolor="white" style="height: 25px">
								 <td align="left" ><strong>Comments:</strong></td>
								 <td align="left" ><s:property value="#parameters.comment"/></td>
						          <td align="left" ><strong>Advance:</strong></td>
								  <td align="left" ><s:property value="#parameters.amount"/></td>
						   </tr>
				    </table>
			                  <div class="newColumn">
			                                <div class="leftColumn1">Amount Taken:</div>
			                               <div class="rightColumn1">
			                             		<s:textfield  id="emp_amt" name="emp_amt"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>               
			                                </div>
			                  </div>
		                	  <div class="newColumn">
		                                <div class="leftColumn1">Reason:</div>
		                               <div class="rightColumn1">
		                             		<s:textfield  id="emp_reason" name="emp_reason"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>         
		                                </div>
		                      </div>
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
                             cssStyle="margin-left: 139px;margin-top: -43px;"
                             onclick="reset('formone');resetColor('.pIds');"
                        />&nbsp;&nbsp;
                        <sj:a
                            cssStyle="margin-left: 276px;margin-top: -58px;"
                        button="true" href="#" value="View" onclick="advance_main();" cssStyle="margin-left: 266px;margin-top: -74px;" 
                        >Back
                    </sj:a>
                    </div>
               </div>
             </s:form> 
             <sj:dialog
          id="expense"
          showEffect="slide" 
          autoOpen="false"
          title="Create Activity"
          modal="true"
          width="1200"
          position="center"
          height="450"
          draggable="true"
    	  resizable="true"
          >
        <center>  <div id="result_data"></div> </center>
</sj:dialog>         
</div>
</div>
</body>
</html>

