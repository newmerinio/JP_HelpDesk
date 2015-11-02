<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Take Action On Opportunity</title>
<script src="<s:url value="js/WFPM/opportunity/viewOpportunityDetails.js"/>"></script>

<script type="text/javascript">

		$.subscribe('actionform', function(event,data)
	        {
	          setTimeout(function(){ $("#actionsave").fadeIn(); }, 10);
	          setTimeout(function(){ $("#actionsave").fadeOut(); }, 4000);
	         
        });
</script>
	<script type="text/javascript">
		function resetForm(formtwo)
		{
			$('#'+formtwo).trigger("reset");
		}
	</script>
</head>

<body>

		<div class="clear"></div>
		<div class="list-icon">
		<!-- <div class="head">Add Prospective Client</div> -->
		<div class="head">Action For <s:property value="%{organization}"/> For <s:property value="%{offeringname}"/> </div>
		</div>
		<div class="clear"></div>
		<div class="container_block">
			<div style=" float:left; padding:10px 1%; width:98%;">
		
		<table border="1" width="95%" align="center" style="border-collapse: collapse;">
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Organization Name:</td>
							<td width="10%"><s:property value="%{organization}"/></td>
							<td width="10%">Star Rating:</td>
							<td width="10%"><s:property value="%{starRating}"/></td>
							<td width="10%">Address:</td>
							<td width="10%"><s:property value="%{address}"/></td>
				</tr>
				<tr style="height: 25px">
							<td width="10%">Location:</td>
							<td width="10%"><s:property value="%{location}"/></td>
							<td width="10%">A/C Manager:</td>
							<td width="10%"><s:property value="%{acManager}"/></td>
							<td width="10%">Industry:</td>
							<td width="10%"><s:property value="%{industry}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Sub Industry:</td>
							<td width="10%"><s:property value="%{subIndustry}"/></td>
							<td width="10%">Forecast Category:</td>
							<td width="10%"><s:property value="%{forecastCategory}"/></td>
							<td width="10%">Sales Stages:</td>
							<td width="10%"><s:property value="%{salesStages}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%">Contact Name:</td>
							<td width="10%"><s:property value="%{contactName}"/> </td>
							<td width="10%"></td>
							<td width="10%"> </td>
							<td width="10%"></td>
							<td width="10%"> </td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Activity History:</td>
							<td width="10%"><s:property value="%{lastActivity}"/></td>
							<td width="10%"></td>
							<td width="10%"> </td>
							<td width="10%"></td>
							<td width="10%"> </td>
				</tr>
				
		</table>
		<br><br><br>	
		<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/wfpm/client" action="takeactionOnOpportinty" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
		<div style="margin-left: 8%;">
				Select Action: <s:select 
				id="opportunityTypeId" 
				name="opportunityActionType"
				list="#{'Close':'Close', 'Edit':'Edit', 'Lost':'Lost', 'Next Activity':'Next Activity'}" 
				headerKey="-1"
				headerValue="Select Action" 
				onchange="actionOnOpportunity();"
				cssClass="button"
				theme="simple"
				cssStyle="height: 30px; width: 20%;"
			>
			</s:select>
		</div>
		<s:hidden name="id" value="%{id}" id="id" /> 
		<s:hidden name="clientName" value="%{clientName}" id="clientName"/> 
		<s:hidden name="offeringId" value="%{offeringId}" id="offeringId" /> 
		<s:hidden name="takeActionId" value="%{takeActionId}" id="takeActionId" /> 
		
		<br>
		<div id="actiondatadivId">
		<!-- open action jsp here-->
		</div>
		
		<div class="clear"></div>
	<!-- Buttons -->
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;margin-top: 3%;">
         
          <sj:submit 
	           		   targets="opportunityAction" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="actionform"
                       cssClass="submit"
                       indicator="indicator3"
                       onBeforeTopics="validate2"
	          />
           	<sj:a 
	     	    name="Reset"  
				href="#" 
				cssClass="submit" 
				indicator="indicator" 
				button="true" 
				onclick="resetForm('formtwo');"
				cssStyle="margin-left: 193px;margin-top: -43px;"
				>
				  	Reset
				</sj:a>
          <sj:a 
	     	name="Cancel"  
			href="#" 
			cssClass="submit" 
			indicator="indicator" 
			button="true" 
			cssStyle="margin-left: 145px; margin-top: -25px;"
			onclick="backToOpportunityBoard();"
			cssStyle="margin-top: -43px;"
			
			>
	  		Back
		</sj:a>
		
				<sj:div id="actionsave"  effect="fold">
                    	<div id="opportunityAction"></div>
               	</sj:div>
	    </div>
		
		</s:form>
		
		</div>
		</div>
</body>
</html>