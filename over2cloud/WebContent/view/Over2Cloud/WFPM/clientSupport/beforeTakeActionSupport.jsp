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
<script src="<s:url value="js/client/viewClientsupport.js"/>"></script>

<script type="text/javascript">

		$.subscribe('actionform', function(event,data)
	        {
	          setTimeout(function(){ $("#actionsave").fadeIn(); }, 10);
	          setTimeout(function(){ $("#actionsave").fadeOut(); }, 4000);
	          // to go back on view page
	          $.ajax({
	  		    type : "post",
	  		    url : "view/Over2Cloud/wfpm/clientsupport/beforeclientDetails.action",
	  		    
	  		    success : function(subdeptdata) {
	  		       $("#"+"data_part").html(subdeptdata);
	  		},
	  		   error: function() {
	  		            alert("error");
	  		        }
	  		 });
	         
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
<%
String downloadPO=request.getAttribute("down").toString();
%>
	<div class="clear"></div>
		<div class="list-icon">
		<!-- <div class="head">Add Prospective Client</div> -->
		<div class="head">Action For <s:property value="%{clientname}"/> For <s:property value="%{offeringName}"/> </div>
		</div>
		<div class="clear"></div>
		<div class="border">
		<div class="container_block">
			<div style=" float:left; padding:10px 1%; width:98%;">
		
		<table border="1" width="95%" align="center" style="border-collapse: collapse;">
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b>Organization Name:</b> </td>
							<td width="10%"><s:property value="%{organization}"/></td>
							<td width="10%"><b>Star Rating:</b></td>
							<td width="10%"><s:property value="%{starRating}"/></td>
							<td width="10%"><b>Location:</b></td>
							<td width="10%"><s:property value="%{location}"/></td>
				</tr>
				<tr style="height: 25px">
							<td width="10%"><b>Target Segment:</b></td>
							<td width="10%"><s:property value="%{targetSegment}"/></td>
							<td width="10%"><b>Website:</b></td>
							<td width="10%"><s:property value="%{website}"/></td>
							<td width="10%">Contact No.</td>
							<td width="10%"><s:property value="%{clientContact}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="20%"><b>Vertical:</b><b><s:property  value="%{verticalname}"/></b>  </td>
							<td width="10%"><b>Offering:</b><b> <s:property  value="%{offeringname}"/></b></td>
							<td width="10%" colspan="4"><b>Sub Offering:</b><b> <s:property  value="%{subofferingname}"/> </b></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%"><b>Name:</b></td>
							<td width="10%"><s:property value="%{contactName}"/> </td>
							<td width="10%"><b>Degree Of Influence:</b></td>
							<td width="10%"><s:property value="%{degreeOfInfluence}"/> </td>
							<td width="10%"><b>Designation</b></td>
							<td width="10%"> <s:property value="%{designation}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b>Mobile No.</b></td>
							<td width="10%"> <s:property value="%{contactNumber}"/></td>
							<td width="10%"><b>Email ID:</b></td>
							<td width="10%"><s:property value="%{email}"/></td>
							<td width="10%"></td>
							<td width="10%"></td>
				</tr>
					<tr style="height: 25px">
							<td width="10%"><b>Converted On:</b></td>
							<td width="10%"> <s:property value="%{convertedOn}"/></td>
							<td width="10%"><b>Converted By:</b></td>
							<td width="10%"> <s:property value="%{acManager}"/></td>
							<td width="10%"><b>Comments:</b></td>
							<td width="10%"> <s:property value="%{comments}"/></td>
					</tr>
					<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b>Download P.O.:</b></td>
							<td width="10%">
							<% 
							if(downloadPO.equalsIgnoreCase("NA"))
							{
							%>
							<s:a href="#" onclick="docCheck();" cssStyle="color:#4876FF;">NA</s:a>
							<%
							}else{ 
							%>
							<s:a href="view/Over2Cloud/wfpm/clientsupport/downloadPo.action?offeringId='%{offeringId}'&clientName='%{clientName}'&docType=poattach1" cssStyle="color:#4876FF;">
							<img title="Attachment" src='images/docDownlaod.jpg'/>
							</s:a> 
							<%}%>
							</td>
							<td width="10%"></td>
							<td width="10%"> </td>
							<td width="10%"></td>
							<td width="10%"> </td>
					</tr>
				
		</table>
		<br><br><br>	
		<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/wfpm/clientsupport" action="takeactionOnsupport" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
		<s:if test="%{close_flag=='true'}">
			<div style="margin-left: 8%;">
		Take Action: <s:select 
				id="actionTypeId" 
				name="supportActionType"
				list="#{'Configure Support':'Configure Support', 'Extented Support':'Extented Support', 'Hold Support':'Hold Support'}" 
				headerKey="-1"
				headerValue="Select Action" 
				onchange="actionOnClientSupport();"
				cssClass="button"
				theme="simple"
				cssStyle="height: 30px; width: 20%;"
			>
			</s:select>
		</div>
		</s:if>
		<s:else>
			<div style="margin-left: 8%;">
	Take Action: <s:select 
				id="actionTypeId" 
				name="supportActionType"
				list="#{'Configure Delivery':'Configure Delivery', 'Configure Support':'Configure Support', 'Extented Support':'Extented Support', 'Hold Support':'Hold Support'}" 
				headerKey="-1"
				headerValue="Select Action" 
				onchange="actionOnClientSupport();"
				cssClass="button"
				theme="simple"
				cssStyle="height: 30px; width: 20%;"
			>
			</s:select>
		</div>
		</s:else>
		
		<s:hidden name="id" value="%{id}" id="id" /> 
		<s:hidden name="clientName" value="%{clientName}" id="clientName"/> 
		<s:hidden name="offeringId" value="%{offeringId}" id="offeringId" /> 
		<s:hidden name="contactName" value="%{contactName}" id="contactnameID" />
		
		<div id="actionSupportdatadivId">
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
			onclick="backToClientSupport();"
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
		</div>
</body>
</html>