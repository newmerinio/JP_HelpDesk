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
function expenseView(val)
{
	alert("val ::: "+val);
	$('#expense').dialog('open');
	$('#expense').dialog({title: 'View Expense',height: '350',width:'1000'});
    $("#result_data").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeViewExpenseEdit.action?activityId="+val,
	    success : function(subdeptdata) 
	   {
			$("#"+"result_data").html(subdeptdata);
	   },
	   error: function() 
	   {
           alert("error");
      }
	 });
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
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addDsrApproval" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
	          <s:hidden id="activityId" name="activityId" value="%{activityId}"/>
	            <s:hidden id="dsrId" name="dsrId" value="%{dsrId}"/>
			                  <s:if test="%{dataMap.size()>0}">
			                         <table width="100%" border="1">
								    		<tr  bgcolor="lightgrey" style="height: 25px">
									    			<s:iterator value="dataMap" status="counter">
									   					 <s:if test="%{key=='For Month' || key=='Activity For' || key=='Activity Type'}">
											    			<td align="left" ><strong><s:property value="%{key}"/>:</strong></td>
															<td align="left" ><s:property value="%{value}"/></td>
									   					 </s:if>
													</s:iterator>
										    </tr>
										    <tr  bgcolor="white" style="height: 25px">
											    <s:iterator value="dataMap" status="counter">
											    <s:if test="%{key=='Schedule' || key=='DCR Status' }">
											    			<td align="left" ><strong><s:property value="%{key}"/>:</strong></td>
															<td align="left" ><s:property value="%{value}"/></td>
											    </s:if>
												</s:iterator>
												<td align="left" ><strong>Reimbursement</strong></td>
												<td align="left" ><img id="Reimbursement" alt="Reimbursement" src="images/OTM/check.jpg" height="25" width="25" onclick="expenseView('<s:property value="%{dsrId}"/>');"></td>
										    </tr>
								    </table>
			                  </s:if>
			                  <div class="newColumn">
			                                <div class="leftColumn1">Status:</div>
			                               <div class="rightColumn1">
			                             		 <s:select 
				                                      id="manager_status"
				                                      name="manager_status"
				                                      list="{'Approved','Disapproved'}"
				                                      headerKey="-1"
				                                      headerValue="Select Status" 
				                                      cssClass="textField"
				                                      >
	                                            </s:select>                
			                                </div>
			                  </div>
		                	  <div class="newColumn">
		                                <div class="leftColumn1">Reason:</div>
		                               <div class="rightColumn1">
		                             		<s:textfield  id="manager_reason" name="manager_reason"   maxlength="50" cssClass="textField" placeholder="Enter Data"/>         
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
                        button="true" href="#" value="View" onclick="viewGroup();" cssStyle="margin-left: 266px;margin-top: -74px;" 
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

