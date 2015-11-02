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
<script src="<s:url value="js/lead/LeadCommon.js"/>"></script>
<script src="<s:url value="js/client/viewClient.js"/>"></script>
</head>

<body>
	<div class="clear"></div>
	<div class="list-icon">
		<s:if test="%{fromDashboard == null}">
			<%-- <div class="head"><s:property value="%{mainHeaderName}"/></div> --%>
			<div class="list-icon">
		<div class="head">
		<div id="Cid" class="head">View Opportunity</div><div class="head">
		<img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div>
		</div>
		<div class="head">Total: </div>
	    <div id="totalCount" class="head"><s:property value="%{totalRecord}"/></div>
	    <div id="totalCount1" class="head"></div>
	    
		</div>
		</s:if>
		<s:else>
			<%-- <div class="head"><s:property value="%{fromDashboard}"/></div> --%>
			<div class="list-icon">
		<div class="head">
		 <s:property value="%{fromDashboard}"/></div><img alt="" src="images/forward.png" style="width: 2%; float: left;"><div class="head">View</div> 
	    </div>

		</s:else>
	</div>



<div style=" float:left; padding:10px 1%; width:98%;">
		
		<div class="clear"></div>	 	
		<div id="alphabeticalLinks"></div>
	 	<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" 
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
						Opportunity Name: <s:textfield name="clientName"  id="clientNameId" theme="simple" onkeyup="searchResult('','','','');count1('','','')" cssClass="textField" placeholder="Enter Data" style="margin: 0px 0px 9px; width: 7%;"/>
				<!-- 	Client Name: <s:textfield name="opportunity_name"  id="opportunitynameId" theme="simple" onchange="searchResult('','','','');" cssClass="textField" placeholder="Enter Data" style="margin: 0px 0px 9px; width: 7%;"/>   -->
						Closure Date:<sj:datepicker id="fromDateSearch" name="fromDateSearch" cssClass="button" size="10"   readonly="true"    yearRange="2013:2050" cssStyle="height: 17px; width: 6%;" showOn="focus" displayFormat="dd-mm-yy" onchange="searchResult('','','','');count1('','','')"  Placeholder="Select Date"/>
						<sj:a  button="true" 
		           cssClass="button" 
		           cssStyle="height:25px; width:32px"
		           title="Download"
		           buttonIcon="ui-icon-arrowstop-1-s" 
		           onclick="getCurrentColumn('downloadExcel','clientDetail','downloadColumnAllModDialog','downloadAllModColumnDiv');" />
		          
			      <sj:a  button="true" 
		           cssClass="button" 
	               cssStyle="height:25px; width:32px"
	               title="Upload"
		           buttonIcon="ui-icon-arrowstop-1-n" 
		           onclick="excelUpload();" />
					
						</td></tr>
						<tr><td> <table class="floatL" border="0" cellpadding="0" 
									cellspacing="0"><tbody>
						
						<tr>
						<td class="pL10"> 
					
		<%-- <div id="editButtonDiv" style="float: left;"><sj:a id="editButton" buttonIcon="ui-icon-pencil" 
			cssClass="button" button="true" onclick="editRow()"> Edit </sj:a></div> --%>
			
		
					
					
		
		<s:select
			id="industryID" 
			name="industry" 
			list="industryList" 
			headerKey="-1"
			headerValue="Industry" 
            theme="simple"
            cssStyle="height: 26px; width: 9%;"
            onchange="fillName(this.value,'subIndustryID'); count1('','','')"
			cssClass="button"
            />
		<s:select
			id="subIndustryID" 
			name="subIndustry" 
			list="#{'-1':'Sub-Industry'}" 
           cssStyle="height: 26px; width: 9%;"
            theme="simple" 
            onchange="searchResult('','','',''); count1('','','')"
			cssClass="button"
            />
         <s:select 
			id="starRating" 
			name="starRating"
			list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
			headerKey="-1"
			headerValue="Rating"
			cssStyle="height: 26px; width: 9%;"
            theme="simple" 
            onchange="searchResult('','','','');count1('','','')"
			cssClass="button"
			/>
		 <s:select
			id="sourceName" 
			name="sourceName" 
			list="sourceList" 
			headerKey="-1"
			headerValue="Source" 
			cssStyle="height: 26px; width: 9%;"
			theme="simple"
			onchange="searchResult('','','','');count1('','','')"
			cssClass="button"
			/>
		<s:select
			id="locationID" 
			name="location" 
			list="locationList"
			headerKey="-1"
			headerValue="Location" 
			cssStyle="height: 26px; width: 9%;"
			theme="simple"
			onchange="searchResult('','','','');count1('','','')"
			cssClass="button"
			/>
			<s:select
			id="clientStatusId" 
			name="status" 
			list="clientstatuslist"
			headerKey="-1"
			headerValue="Status" 
			cssStyle="height: 26px; width: 7%;"
			theme="simple"
			onchange="searchResult('','','','');count1('','','')"
			cssClass="button"
			/>
			<s:if test="#session.userRights.contains('prcl_MODIFY')">
			<s:select 
			            id="acManagerId" 
						name="acManager"
			            list="employee_basicMap"
			            headerKey="-1"
			            headerValue="A/C Manager" 
		                onchange="searchResult('','','','');count1('','','')"
						cssClass="button"
		                theme="simple"
	                    cssStyle="height: 26px; width: 9%;"
			            >
				</s:select>
				</s:if>
				
			
			<s:select
			id="foreCastCategaryID" 
			name="foreCastCategary" 
			list="forecastcategoryMap"
		    headerKey="-1"
			headerValue="Forecast Categary" 
			cssStyle="height: 26px; width: 9%;"
			theme="simple"
			onchange="searchResult('','','','');count1('','','')"
			cssClass="button"
			/>
			<s:select 
						id="salesstages"
						name="sales_stages" 
						list="salesStageMap"
						headerKey="-1"
						headerValue="Sales Stages" 
						onchange="searchResult('','','','');count1('','','')"
						cssClass="button"
		                theme="simple"
	                    cssStyle="height: 26px; width: 9%;"
						>
					</s:select>
					 
					<s:select 
							id="selectstatus" 
							list="#{'-1':'Not Authorised'}"  
							cssClass="button" 
							onchange="searchshowclientdata(this.value);"
						 	cssStyle="height: 26px; width: 9%;"
							theme="simple"
							/>
				</td></tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
			<s:if test="'true' || #session.userRights.contains('prcl_ADD')"> 
				<!--<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="getCurrentColumn('downloadAllData','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" >Download</sj:a>	
			    --><!--
			    <sj:a  button="true" 
		           cssClass="button" 
		           cssStyle="height:25px; width:32px"
		           title="Download"
		           buttonIcon="ui-icon-arrowstop-1-s" 
		           onclick="getCurrentColumn('downloadExcel','clientDetail','downloadColumnAllModDialog','downloadAllModColumnDiv');" />
		           
			     <sj:a  button="true" 
		           cssClass="button" 
	               cssStyle="height:25px; width:32px"
	               title="Upload"
		           buttonIcon="ui-icon-arrowstop-1-n" 
		           onclick="excelUpload();" />
			    
			    --><%-- <sj:a  button="true" cssClass="button" 
			    buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="excelUpload();">Import</sj:a> --%>
				<div style="margin-top: -17%;">
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;"
	                title="Add"
		            buttonIcon="ui-icon-plus"
					onclick="addProspectiveClient();"
					>Add</sj:a> 
					</div>  
					<div style="margin-top: -78%;">
					<sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;"
	                title="Action"
		            buttonIcon="ui-icon-plus"
					onclick="mapOppValueToClient();"
					>Action</sj:a>
					</div>
           </s:if>
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>

	<s:if test="'true' || #session.userRights.contains('prcl_VIEW') || #session.userRights.contains('prcl_MODIFY') 
			|| #session.userRights.contains('prcl_DELETE')
			|| #session.userRights.contains('excl_VIEW') || #session.userRights.contains('excl_MODIFY') 
			|| #session.userRights.contains('excl_DELETE')
			|| #session.userRights.contains('locl_VIEW') || #session.userRights.contains('locl_MODIFY') 
			|| #session.userRights.contains('locl_DELETE')
			">
 	<div id="datapart">
		<!-- Client grid will be put here dynamically -->		
	</div>
	</s:if>

	</div>
	<br><br>
	<center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div id="divFullHistory" style="float:left; width:100%;"></div>
	
	
</body>

<script type="text/javascript">
var isExistingClient='${isExistingClient}';
function ExistLostClient() {
	if(isExistingClient != '0' || isExistingClient != '1' || isExistingClient != '2')
	{
		isExistingClient = '0';
	}
	$("#selectstatus").val(isExistingClient);
	searchshowclientdata(isExistingClient);
}
/*
fillClientTypeDD('<%= (userRights.contains("prcl_VIEW") || userRights.contains("prcl_MODIFY") || userRights.contains("prcl_DELETE"))? "1":"0" %>'
		,'<%= (userRights.contains("excl_VIEW") || userRights.contains("excl_MODIFY") || userRights.contains("excl_DELETE"))? "1":"0" %>'
		,'<%= (userRights.contains("locl_VIEW") || userRights.contains("locl_MODIFY") || userRights.contains("locl_DELETE"))? "1":"0" %>',"selectstatus");
*/

fillClientTypeDD("1","1","1","selectstatus");

ExistLostClient();
fillAlphabeticalLinks();
</script>
	<sj:dialog 
		id="downloadColumnAllModDialog"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="300" 
	    height="500" 
	    title="Client Column Names To Choose" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    position="['center']">
<div id="downloadAllModColumnDiv"></div>
</sj:dialog>
	<sj:dialog 
		id="downloadWeightageDetails"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="600" 
	    height="400" 
	    title="Offering Weightage %" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
</sj:dialog>

<sj:dialog 
		id="referanceID"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="600" 
	    height="120" 
	    title="Client Reference Detail" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
</sj:dialog>

<!--<sj:dialog id="downloadColumnAstDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Lead Generation Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
	<div id="downloadAstColumnDiv"></div>
</sj:dialog>
--></html>