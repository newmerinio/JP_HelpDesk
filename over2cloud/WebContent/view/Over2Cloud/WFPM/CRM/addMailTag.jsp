<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/WFPM/CRM/viewMailTag.js"/>"></script>
<script type="text/javascript">
$.subscribe('mailtagresult', function(event,data)
	       {
	         setTimeout(function(){ $("#confirmmailTag").fadeIn(); }, 10);
	         setTimeout(function(){ $("#confirmmailTag").fadeOut(); }, 4000);
	       });
	
</script>

<script type="text/javascript">
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}

</script>
</head>


	<body>
		<div class="list-icon">
			<div class="head">Mail Tag</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
		</div>
		<div style=" float:left; padding:10px 0%; width:100%;">
<div class="border">
<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  			<div id="errZone" style="float:left; margin-left: 7px"></div>
        </div>
		<s:form id="formone" name="formone"  namespace="/view/Over2Cloud/WFPM/CRM" action="addMailTagData" theme="simple"  method="post" enctype="multipart/form-data">
					<div class="newColumn">
					<div class="leftColumn1">Name:</div>
						<div class="rightColumn">
							<s:checkbox name="checkname" value="" id="checkname"></s:checkbox>
							<div style="margin-left: 12%;margin-top: -7%;"><s:textfield name="name" id="name" cssClass="textField" placeholder="Enter name tag" cssStyle="margin: 0px 0px 10px; width: 302px; height: 30px;"></s:textfield></div>
						</div>
					</div>
					<div class="newColumn">
					<div class="leftColumn1">Organization Name:</div>
						<div class="rightColumn">
							<s:checkbox name="organizationName" value="" id="organizationName"></s:checkbox>
							<div style="margin-left: 12%;margin-top: -7%;"><s:textfield name="organization_name" id="organization_name" cssClass="textField" placeholder="Enter organization tag" cssStyle="margin: 0px 0px 10px; width: 302px; height: 30px;"></s:textfield></div>
						</div>
					</div>
					
					<div class="clear"></div>	
				
                <br>
		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			<center>
			<sj:submit 
          			   targets="mailtagId"
                       clearForm="true"
                       value="Send" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       cssClass="submit"
                       cssStyle="margin-right: 2px;margin-bottom: 29px;"
                       indicator="indicator2"
                       onCompleteTopics="mailtagresult"
         />
         
         	<sj:a 
				     	href="#" 
		               	button="true" 
		                onclick="resetForm('formone');"
						cssStyle="margin-top: -28px;"
						>
					  	Reset
		   </sj:a>
         	<sj:a 
				     	href="#" 
		               	button="true" 
		                onclick="backtoMailTagView();"
						cssStyle="margin-top: -28px;"						
						>
					  	Back
		   </sj:a>
        
		  </center> 
		  
		   <center>
				<sj:div id="mailtagId"  effect="fold">
                    <div id="confirmmailTag"></div>
               </sj:div>

     
     </center>
   </div>
			</s:form>
			</div>
			</div>
			</div>
			</div>
	</body>
</html>
							