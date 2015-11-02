<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="js/WFPM/ActivityPlanner/activity_planner.js"/>"></script>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    //document.getElementById('result').style.display='none';
      setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}
$.subscribe('level1', function(event,data)
{
   setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
   //setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
   setTimeout(function(){ $("#orglevel1Div").fadeOut();cancelButton(); }, 4000);
	$('#completionResult').dialog('open');
});
function cancelButton()
{
	 $('#completionResult').dialog('close');
	 viewGroup();
}
function viewGroup() 
{
	 $('#abc').dialog('close');
}
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
function changeDate(val)
{
	var month = $("#for_month").val();
	var actId= $("#activityId").val();
	//alert("Month ::: "+month)
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/changeDateApproval.action?month="+month+"&dataFor="+val+"&actId="+actId,
	    success : function(subdeptdata) 
	   {
		  $("#for_month").val(subdeptdata[0].for_month);
		  $("#activity_for").val(subdeptdata[0].activity_for);
		  $("#activity_type").val(subdeptdata[0].activity_type);
		  $("#activityId").val(subdeptdata[0].id);
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
          ><sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
          <div id="complTarget"></div>
</sj:dialog>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addActivityApproval" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
	            <!--<div style="float: right;"> 
		         <a href="#" onclick="changeDate('previous');"><img src="images/backward.png"></img></a>
		         <a href="#" onclick="changeDate('next');"><img src="images/forward.png"></img></a>
	           </div>
	                --><s:hidden id="activityId" name="activityId" value="%{activityId}"/>
                   
					<table width="100%" align="center">
							<tr bgcolor="#D8D8D8"  style="height: 25px;">
								<td><b>Month:</b></td>
								<td><s:property value="%{date}" /></td>
								<td><b>Activity For:</b></td>
								<td><s:property value="%{activity_for}" /></td>
							</tr>
							<tr style="height: 25px;">
								<td><b>Activity Type:</b></td>
								<td><s:property value="%{activityType}" /></td>
								<td><b>Budget Amount:</b></td>
								<td><s:property value="%{amount}" /></td>
							</tr>
							<tr bgcolor="#D8D8D8"  style="height: 25px;">
								<td><b>Schedule Date:</b></td>
								<td><s:property value="%{sch_date}" /></td>
								<td><b>Comment:</b></td>
								<td><s:property value="%{comment}" /></td>
							</tr>
						</table>
	
                     <s:iterator value="dropDown" status="counter" >
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                 <s:if test="%{key=='activity_status'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                               <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		 <s:select 
			                                      id="%{key}"
			                                      name="%{key}"
			                                      list="{'Approved','Disapprove'}"
			                                      headerKey="-1"
			                                      headerValue="Select %{value}" 
			                                      cssClass="textField"
			                                      >
                                            </s:select>                
		                                </div>
		                  </div>
	                 </s:if>
	                 </s:iterator>
                   <s:iterator value="textBox" status="counter">
	                  <s:if test="%{mandatory}">
	                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                  </s:if>
	                  <s:if test="%{key=='activity_comment'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             		<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
		                                </div>
		                  </div>
	                  </s:if>
	                  <s:elseif test="%{!key='activity_type' || !key=='activity_for'}">
		                  <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1">
		                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
		                         			<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
		                  				</div>
		                  </div>
	                 </s:elseif>
                 </s:iterator>
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
</div>
</div>
</body>
</html>

