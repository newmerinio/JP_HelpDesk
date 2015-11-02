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
<script type="text/javascript" src="js/fusioncharts/fusioncharts.js"></script>
<script type="text/javascript" src="js/fusioncharts/themes/fusioncharts.theme.zune.js"></script>
<script src="<s:url value="js/WFPM/opportunity/viewOpportunityDetails.js"/>"></script>

</head>

<body>
	<div class="clear"></div>
	<div class="list-icon">
			<div class="list-icon">
		<div class="head">
		 Review Board</div><img alt="" src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">View</div> 
						<div style="margin-top: 3px;"> 
							<s:select 
						            id="opportunityTypeId" 
									name="clientName"
						            list="#{'Lost':'Lost', 'Closed':'Closed'}" 
						            headerKey="-1"
						            headerValue="Opportunity" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
					                 cssStyle="height: 30px; width: 13%;font-size: 13px;font-weight: bold;"
						            >
							</s:select>
							</div>
	    </div>
	</div>



	<div style=" float:left; padding:10px 1%; width:98%;">
		<div class="clear"></div>
		<div id="alphabeticalLinks"></div>
		<div>	 	
	 	<div class="listviewBorder" style="height: 43px;">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie" style=" margin-top: 3px;">
				<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" 
					width="100%"><tbody>
					<tr>
					 <td>
					<s:if test="#session.userRights.contains('prcl_MODIFY')">
						<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow()"></sj:a>
						</s:if>
					<%-- <div id="delButtonDiv" style="float: left;"><sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" 
						button="true"  onclick="deleteRow()">Delete</sj:a></div> --%>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px; " 
						cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" 
						cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
						Closure Date From: <sj:datepicker id="fromDate" name="fromDate" cssClass="button" size="10" readonly="true"  value=""   yearRange="2013:2050" cssStyle="height: 17px; width: 4%;" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
						
						To: <sj:datepicker id="fromDateSearchID" name="fromDateSearch" cssClass="button" size="10" readonly="true"    yearRange="2013:2050" cssStyle="height: 17px; width: 4%;" showOn="focus" displayFormat="dd-mm-yy" onchange="searchResult('','','','');"  Placeholder="Select Date"/>
						
						<s:select 
						            id="opportunityNameId" 
									name="clientName"
						            list="opportunityNameMap"
						            headerKey="-1"
						            headerValue="Client Name" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 8%;"
						            >
							</s:select>
						<s:if test="#session.userRights.contains('prcl_MODIFY')">
						<s:select 
						            id="acManagerId" 
									name="acManager"
						            list="employee_basicMap"
						            headerKey="-1"
						            headerValue="A/C Manager" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 8%;"
						            >
							</s:select>
					</s:if>
							<s:select 
						            id="offeringId" 
									name="offeringId"
						            list="offeringforOpportunityMap"
						            headerKey="-1"
						            headerValue="Offering" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 8%;"
						            >
							</s:select>
							<s:select 
						            id="expectencyId" 
									name="expectency"
						            list="#{'High':'High', 'Medium':'Medium', 'Low':'Low'}" 
						            headerKey="-1"
						            headerValue="Expectancy" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 8%;"
						            >
							</s:select>
							
							 <s:textfield 
	    	   					 id="searchParameter" 
	            				 name="searchParameter" 
	           					 onkeyup="searchResult('','','','');"
	           					 theme="simple" cssClass="button" 
	           				     cssStyle="margin-top: 5px;height: 15px;width:70px;"
	          					 Placeholder="Wild Search" 
	          	                />
							
							<!--<s:select 
						            id="salesstageNameId" 
									name="salesstageName"
						            list="salesStagesMap"
						            headerKey="-1"
						            headerValue="Current Stage" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 9%;"
						            >
							</s:select>
							
							 
						--></td></tr>
						<tr><td> <table class="floatL" border="0" cellpadding="0" 
									cellspacing="0"><tbody>
						
						<tr>
						<td class="pL10"> 
		
				</td></tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
			
			<s:if test="'true' || #session.userRights.contains('prcl_ADD')"> 
			<div style="margin-top: -34%;margin-right: -2%;"> 
			<a><img title="Download Excel" src="images/page_white_excel.png" style="height:23px; width: 37px;margin-left: -47px;" cssStyle="height:25px;" onclick="downloadExcel();"></a>
			
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height: 24px;margin-top: -10%;"
	                title="Action"
		            buttonIcon=""
					onclick="takeActionOnOpportunity();"
					>Action</sj:a>   
				
					<img title='view Report' src='images/pie_chart_icon.jpg' height="25" width="25" onclick="viewOpportunityReport();"/>
					</div>
           </s:if>
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>
		</div>
	<s:if test="'true' || #session.userRights.contains('prcl_VIEW') || #session.userRights.contains('prcl_MODIFY') 
			|| #session.userRights.contains('prcl_DELETE')
			|| #session.userRights.contains('excl_VIEW') || #session.userRights.contains('excl_MODIFY') 
			|| #session.userRights.contains('excl_DELETE')
			|| #session.userRights.contains('locl_VIEW') || #session.userRights.contains('locl_MODIFY') 
			|| #session.userRights.contains('locl_DELETE')
			">
 	<div id="opportunitydatapart">
		<!-- Opportunity details grid will be put here dynamically -->		
	</div>
	</s:if>

	</div>
	<br><br>
</body>

	<script type="text/javascript">
		searchshowOpportunityData();
	</script>
	<sj:dialog 
		id="workOneId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="700" 
	    height="250" 
	    title="View Client Detail" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
</sj:dialog>
	<sj:dialog 
		id="workTwoId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="600" 
	    height="120" 
	    title="View Offering Detail" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
		id="workThreeId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="600" 
	    height="170" 
	    title="View A\c Manager Detail" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	
	<sj:dialog 
		id="workSixId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="600" 
	    height="150" 
	    title="Expected Value Detail" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
		id="workSevenId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="450" 
	    height="150" 
	    title="Sales Stages" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
		id="workEightId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="1000" 
	    height="250" 
	    title="Activity" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
	id="downloaddaildetails"
	buttons="{'Download':function() { okdownload(); }, 'Cancel':function() { closedownload(); },}"
	showEffect="slide" 
	hideEffect="explode" 
	autoOpen="false" 
	modal="true"
	title="Select Fields" 
	openTopics="openEffectDialog"
	closeTopics="closeEffectDialog"
	width="250"
	height="400" >
	</sj:dialog>

	</html>