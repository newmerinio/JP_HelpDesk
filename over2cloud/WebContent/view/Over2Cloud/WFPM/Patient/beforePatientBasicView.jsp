<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="js/PatientWFPM/patientBasicView.js"/>"></script>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
</head>

<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="list-icon">
		<div class="head">
		<div id="Cid" class="head">Patient Registration</div><div class="head">
		<img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div><div class="head">View</div>
		</div>
	    
	    <div class="head">Male: </div>
	    <div id="totalCount" class="head"><s:property value="%{totalmaleCount}"/></div>
	   
	     <div class="head">Female: </div>
	    <div id="totalCount2" class="head"><s:property value="%{totalFemaleCount}"/></div>
		</div>
		<%-- <div style="float: right;"><button type="button" class="btn btn-default btn-xs"
					title="Add Order" onclick="dashboardPatientActivity();">
					<span class="glyphicon glyphicon-dashboard" aria-hidden="true"
						style="padding: 0px 0px 3px 1px;">Dashboard</span>
				</button></div> --%>
	</div>



<div style=" float:left; padding:10px 1%; width:98%;">
		
		<div class="clear"></div>
		<div id="alphabeticalLinks"></div>	 	
	 	<div class="listviewBorder" style=" height: 39px;">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie" style=" height: 33px;"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" 
					width="100%"><tbody>
					<tr>
					 <td>
					<s:if test="#session.userRights.contains('wness_MODIFY')">
						<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow();"></sj:a>
					</s:if>
					
					<s:if test="#session.userRights.contains('wness_DELETE')">
						<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
							button="true"  onclick="deleteRow()"></sj:a>
					</s:if>
							
					<!--<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px; " 
						cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					--><sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" 
						cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
							 
	          	                <s:select
									id="gender"
	                                name="gender" 
                                	list="#{'Male':'Male','Female':'Female'}"
									headerKey="-1"
									headerValue="Gender" 
									cssStyle="height: 28px; width: 10%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />
							  <s:select
								   id="type"
                                   name="type" 
                                	list="#{'Domestic':'Domestic','International':'International'}"
									headerKey="-1"
									headerValue="Patient Type" 
									cssStyle="height: 28px; width: 12%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />
							  
							  <s:select
									id="patient_category"
	                                name="patient_category" 
                                	list="#{'Standard':'Standard','VVIP':'VVIP','Priority':'Priority','Others':'Others'}"
									headerKey="-1"
									headerValue="Patient Category" 
									cssStyle="height: 28px; width: 12%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />
							<!--<s:select
								      id="status_view"
                                      name="status_view" 
                                	list="#{'0':'Not Attempted','2':'Total Attempted','1':'Partial Attempted'}"
									headerKey="-1"
									headerValue="All Status" 
									cssStyle="height: 28px; width: 16%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />
							-->
							<s:select
								   id="patient_type"
                                   name="patient_type" 
                                	list="#{'Corporate':'Corporate','Referral':'Referral','Walk-In':'Walk-In'}"
									headerKey="-1"
									headerValue="Source" 
									cssStyle="height: 28px; width: 11%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />
						
							<s:select
								   id="sent_questionair"
                                   name="sent_questionair" 
                                	list="#{'true':'Sent','false':'Unsent'}"
									headerKey="-1"
									headerValue="Questionnaire Status" 
									cssStyle="height: 28px; width: 15%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />
							   <s:select
									id="status_patient"
	                                name="status_patient" 
                                	list="#{'0':'Prospect','1':'Existing','2':'Lost'}"
									headerKey="-1"
									headerValue="Patient Status" 
									cssStyle="height: 28px; width: 12%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />
							  
							  <s:textfield 
	    	   					 id="searchParameter" 
	            				 name="searchParameter" 
	           					 onkeyup="searchResult('','','','');" 
	           					 theme="simple" cssClass="button" 
	           				     cssStyle="margin-top: -3px;height: 28px;width:12%;margin-left: -3px;"
	          					 Placeholder="Search Any Value" 
	          	                />
						</td></tr>
						<tr><td> <table class="floatL" border="0" cellpadding="0" 
									cellspacing="0"><tbody>
						
						<tr>
						<td class="pL10"> 
					
		
				</td></tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
			<div style="margin-top: 0%;margin-right: -2%;">
			<!--<img title='view Report' src='images/pie_chart_icon.jpg' height="25" width="25" style="margin-top:-25px;margin-bottom:22px;margin-right:2px;" onclick="viewPatientReport();"/>
			 --><sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: -49px;"
	                title="Resend Questionnare"
		           onclick="confirmAction('resendConfirmDialog');"
					>Resend</sj:a> 
			<%--  <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: -63px;"
	                title="Visit"
		            buttonIcon="ui-icon-plus"
					onclick="patientVisitAction();"
					>Visit</sj:a> --%> 
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: -49px;"
	                title="Registration"
		            buttonIcon="ui-icon-plus"
					onclick="addPatientBasic();"
					>Registration</sj:a> 
		</div>		
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>

 	<div id="datapart" >
		<!-- Patient grid will be put here dynamically -->		
	</div>

	</div>
	<br><br>
	<center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div id="divFullHistory" style="float:left; width:100%;"></div>
	<sj:dialog id="resendConfirmDialog"
		buttons="{'Yes':function() { resendAction(); } ,'No':function() { closeDialog('resendConfirmDialog'); } }"
		showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Confirmation" openTopics="openEffectDialog"
		closeTopics="closeEffectDialog" width="180" height="110">
		<center><b><sj:div id="dialogData">Are you sure to send?</sj:div></b></center>
	</sj:dialog>
	
</body>
<div>    </div>
	<script type="text/javascript">
		viewPatientBasicDetail('0');
		fillAlphabeticalLinks();
	</script>
<sj:dialog 
		id="patientDataId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="350" 
	    height="400" 
	    title="Patient Basic Details" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
		id="sentDetailId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="300" 
	    height="100" 
	    title="Questionnaire Status" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	</html>