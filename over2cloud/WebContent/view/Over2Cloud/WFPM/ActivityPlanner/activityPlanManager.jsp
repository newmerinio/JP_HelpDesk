<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
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
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addActivityPlanManagerStatus" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
	          <s:hidden id="id" name="actId" value="%{#parameters.id}"/>
                    <table width="100%" border="1">
				    		<tr  bgcolor="lightgrey" style="height: 25px">
				    			<td align="left" ><strong>Name:</strong></td>
								<td align="left" ><s:property value="#parameters.name"/></td>
								<td align="left" ><strong>Status:</strong></td>
								<td align="left" ><s:property value="#parameters.status"/></td>
						    </tr>
						    <tr  bgcolor="white" style="height: 25px">
						          <td align="left" ><strong>Month:</strong></td>
								  <td align="left" ><s:property value="#parameters.month"/></td>
						          <td align="left" ><strong>Extend By:</strong></td>
								  <td align="left" ><s:property value="#parameters.extend"/></td>
						   </tr>
						   	<tr  bgcolor="lightgrey" style="height: 25px">
				    			<td align="left" ><strong>Request On:</strong></td>
								<td align="left" ><s:property value="#parameters.date"/>, <s:property value="#parameters.time"/></td>
								<td align="left" ></td>
								<td align="left" ></td> 
						    </tr>
				    </table>
				         <div class="newColumn">
		                                <div class="leftColumn1">Action Taken:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="status"
			                                      name="status"
			                                      list="{'Approved','Disapprove'}"
			                                      headerKey="-1"
			                                      headerValue="Select Action Taken" 
			                                      cssClass="textField"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
		                   <div class="newColumn">
		                                <div class="leftColumn1">Comments:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<s:textarea name="manager_reason" id="manager_reason"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
		                   <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				                <div class="clear" >
				                       <div style="padding-bottom: 10px;margin-left:-80px" align="center">
				                        <sj:submit 
				                             targets="level123" 
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
				                             cssStyle="margin-left: 139px;margin-top: -37px;"
				                             onclick="reset('formone');resetColor('.pIds');"
				                        />&nbsp;&nbsp;
				                    </div>
				               </div>
				</s:form>
               </div>
</div>
</body>
</html>

