<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#resultTarget").fadeOut(); }, 400);
		  });

function viewFeedbackModeDetails()
{
	 var patientId  = $("#patient").val();
	 var clientId  = $("#clientId").val();
	 var feedbackDataId = $("#feedDataId").val();
	 var conP = "<%=request.getContextPath()%>";
		$("#patInfo").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	        type : "post",
	        url :  conP+"/view/Over2Cloud/feedback/feedback_Activity/viewPatModeHistory.action?clientId="+clientId+"&patientId="+patientId+"&feedbackDataId="+feedbackDataId,
	        success : function(subdeptdata) {
	        	$("#patInfo").html(subdeptdata);
		    },
		       error: function() {
		            alert("error");
		        }
		     });  
}

function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}

function viewAuditReport()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/feedback_Activity/beforeAuditHeader.action",
	    success : function(data) 
	    {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
</script>
</head>
<body>
<s:hidden name="clientId" value="%{clientId}"></s:hidden>
<s:hidden name="patientId" value="%{patientId}"></s:hidden>
<s:hidden name="feedDataId" value="%{feedDataId}"></s:hidden>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Audit</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">Action</div>  
	  
</div>
<div class="clear"></div>
<div style=" height:590px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div id="patInfo" style="height: 400px;padding-top: 0px;position: relative"></div>
<s:form id="formone" name="formone" action="takeActionForAudit" theme="simple"  method="post" namespace="/view/Over2Cloud/feedback/feedback_Activity" >
<s:hidden name="id" value="%{id}"></s:hidden>

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  
		<div id="divId11">
				<div class="newColumn">
        			   <div class="leftColumn" >Take Action: </div>
					        <div class="rightColumn">
				                  <s:select  
				                  		id="actionType"
				                        name="actionType"
				                        list="#{'Correct':'Correct','Wrong':'Wrong'}"
				                        headerKey="-1"
				                        headerValue="Action Type" 
				                        cssClass="select"
					                    cssStyle="margin-left: 3px;width: 143px;height: 30px;"
					                    theme="simple"
				                      >
				                  </s:select>
                           </div>
                   </div>
                   <div class="newColumn">
        			   <div class="leftColumn" >Comments: </div>
					        <div class="rightColumn">
				                 <s:textarea id="comments" name="comments" theme="simple" cssClass="textField" placeHolder="Enter Comments"></s:textarea>
                           </div>
                   </div>
        </div>           
          <div class="clear"> </div>   
		 <div class="fields" align="center">
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
                        indicator		=	"indicator2"
                        onBeforeTopics  =   "validate"
                        
     		  	  />
     		  	  <sj:a 
						button="true" href="#"
						onclick="resetForm('formone');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewAuditReport();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 <sj:div id="resultTarget"  effect="fold">
   	     </sj:div>
		 </div>
</s:form>
</div>
</div>
</body>
<script type="text/javascript">
viewFeedbackModeDetails();
</script>
</html>

