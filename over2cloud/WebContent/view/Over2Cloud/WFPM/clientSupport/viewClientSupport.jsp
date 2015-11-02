<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="js/client/viewClientsupport.js"/>"></script>
<script src="<s:url value="js/patientActivity/patientActivity.js"/>"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
			<div class="list-icon">
		<div class="head">
		 Client Support</div><img alt="" src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">View</div> 
						<div style="margin-top: 3px;"> 
							<s:select 
						            id="ClientTypeId" 
									name="clientCategary"
						            list="#{'Closed Clients':'Closed Clients'}" 
						            headerKey="Existing Clients"
						            headerValue="Existing Clients" 
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
	 	<div class="listviewBorder" style="height: 43px;">
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
						From: <sj:datepicker id="fromDate" name="fromDate" cssClass="button" size="10" readonly="true"  value=""   yearRange="2013:2050" cssStyle="height: 17px; width: 6%;" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
						
						To: <sj:datepicker id="fromDateSearchID" name="fromDateSearch" cssClass="button" size="10" readonly="true"    yearRange="2013:2050" cssStyle="height: 17px; width: 6%;" showOn="focus" displayFormat="dd-mm-yy" onchange="searchResult('','','','');"  Placeholder="Select Date"/>
							For :<s:select 
						            id="searchForId" 
									name="searchFor"
						            list="#{'Support':'Support', 'Delivery':'Delivery', 'Conversion':'Conversion'}" 
						            headerKey="-1"
						            headerValue="Search For" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 11%;"
						            >
							</s:select>
							<s:select 
						            id="clientNameId" 
									name="clientName"
						            list="clientNameMAP"
						            headerKey="-1"
						            headerValue="Client Name" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 11%;"
						            >
							</s:select>
							<s:select 
						            id="offeringNameId" 
									name="offeringName"
						            list="offeringNameMAP"
						            headerKey="-1"
						            headerValue="Offering Name" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 11%;"
						            >
							</s:select>
							<!--<s:select 
						            id="supportstatusId" 
									name="supportStatus"
						            list="#{'All Support':'All Support', 'Active':'Active', 'Expired':'Expired', 'Hold':'Hold'}" 
						            headerKey="-1"
						            headerValue="Support Status" 
					                onchange="searchResult('','','','');"
									cssClass="button"
					                theme="simple"
				                    cssStyle="height: 30px; width: 11%;"
						            >
							</s:select>
							--><s:select 
								id="clientSupportTypeId"
								name="supportType" 
								list="supportTypeMAP"
								headerKey="-1"
								headerValue="Support Type" 
								cssClass="button"
								theme="simple"
					            cssStyle="height: 30px; width: 11%;"
					             onchange="searchResult('','','','');"
								>
							</s:select>
							
							</td></tr>
						<tr><td> <table class="floatL" border="0" cellpadding="0" 
									cellspacing="0"><tbody>
						
						<tr>
						<td class="pL10"> 
		
				</td></tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
			
			<s:if test="'true' || #session.userRights.contains('prcl_ADD')"> 
				<div style="margin-top: -15%;">
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: 28px;"
			        title="Action"
		            buttonIcon=""
					onclick="takeActionForSMS();"
					>SMS</sj:a>   
			
					
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: 27px;margin-left: -3px;width: 47px;"
	                title="Action"
		            buttonIcon=""
					onclick="takeActionForEmail();"
					>Mail</sj:a>   
			
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: 29px;margin-right: 101px;margin-left: -238px;"
	                title="Action"
		            buttonIcon=""
					onclick="takeActionForClientSupport();"
					>Support Action</sj:a>   
				</div>
				
				<sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: -27px;margin-right: 227px;width: 139px;"
	                title="Action"
		            buttonIcon=""
					onclick="takeActionForPateintPreventive();"
					>Preventive Action</sj:a>   
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
 	<div id="clientsupportdatapart">
		<!-- Client support details grid will be put here dynamically -->		
	</div>
	</s:if>

	</div>
	<br><br>
	
</body>
	<script type="text/javascript">
	searchShowClientSupportData();
	</script>
	<sj:dialog 
		id="clientshowId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
		id="offeringshowId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="700" 
	    height="120" 
	    title="View Offering Details" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
		id="showContactId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="700" 
	    height="200" 
	    title="Details For Contact Person" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
		id="conversionDetailId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="1100" 
	    height="220" 
	    title="Conversion Details Of Offering" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	<sj:dialog 
		id="supportStatusId"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="800" 
	    height="260" 
	    title="View Support Status" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
		<sj:dialog 
		id="convertedById"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="800" 
	    height="160" 
	    title="View Converted By Data" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
	</sj:dialog>
	
	
</html>