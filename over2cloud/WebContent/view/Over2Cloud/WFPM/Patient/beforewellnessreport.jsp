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
<script src="<s:url value="js/PatientWFPM/wellnessreportView.js"/>"></script>
<script type="text/javascript">
function refreshRow(row, result) 
{
	  $("#gridBasicDetails").trigger("reloadGrid"); 
}
</script>
</head>

<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="list-icon">
		<div class="head">
		<div id="Cid" class="head">Report</div><div class="head">
		<img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div><div class="head">View</div>
		</div>
		
		<div class="head">Total Seek: </div>
	    <div id="totalCount" class="head"><s:property value="%{totseek}"/></div>
	    <div id="totalCount1" class="head"></div>
	    <div class="head">Partial Attempted: </div>
	    <div id="totalCount" class="head"><s:property value="%{pat}"/></div>
	    <div id="totalCount1" class="head"></div>
	   
	     <div id="totalCount2" class="head"></div>
	    <div class="head">Not Attempted: </div>
	    <div id="totalCount3" class="head"><s:property value="%{nat}"/></div>
	    <div id="totalCount4" class="head"></div> 
	      <div id="totalCount5" class="head"></div>
	    <div class="head">Total Attempted: </div>
	    <div id="totalCount6" class="head"><s:property value="%{tat}"/></div>
	    <div id="totalCount7" class="head"></div>
		</div>
	</div>



<div style=" float:left; padding:10px 1%; width:98%;">
		
		<div class="clear"></div>	 	
	 	<div class="listviewBorder" style="height: 39px;">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" 
					width="100%"><tbody>
					<tr>
					 <td>
					<!--<s:if test="#session.userRights.contains('wness_MODIFY')">
						<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow()"></sj:a>
					</s:if>
					
					<s:if test="#session.userRights.contains('wness_DELETE')">
						<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
							button="true"  onclick="deleteRow()"></sj:a>
					</s:if>
							
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px; " 
						cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					-->
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:26px; width:32px;margin-top: -6px;" 
						cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
						 <sj:datepicker name="from_date" id="from_date"  value="%{datebeforeday}"  showOn="focus" displayFormat="dd-mm-yy" yearRange="2014:2020" placeholder="From" onchange="searchResult('','','','');" readonly="true" title="From" cssClass="textField" style="margin: 0px 6px 10px; width: 5%;margin-left: -1px;height: 26px;"></sj:datepicker>
					     <sj:datepicker name="to_date" id="to_date"  value="today"  showOn="focus" displayFormat="dd-mm-yy" yearRange="2014:2020" readonly="true"   cssClass="textField" placeholder="From" title="To" onchange="searchResult('','','','');" style="margin: 0px 6px 10px; width: 5%;margin-left: -4px;height: 26px;"></sj:datepicker>
						<s:textfield 
	    	   					 id="searchParameter" 
	            				 name="searchParameter" 
	           					 onkeyup="searchResult('','','','');" 
	           					 theme="simple" cssClass="button" 
	           				    cssStyle="margin-top: -3px;height: 19px;width:8%;margin-left: -3px;"
	          					 Placeholder="Search Any Value" 
	          	                />
	
						<s:select
								      id="status_view"
                                      name="status_view" 
                                	list="#{'0':'Not Attempted','2':'Total Attempted','1':'Partial Attempted'}"
									headerKey="-1"
									headerValue="All Status" 
									cssStyle="height: 30px; width: 13%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />
                        
						</td></tr>
						<tr><td> <table class="floatL" border="0" cellpadding="0" 
									cellspacing="0"><tbody>
						
						<tr>
						<td class="pL10"> 
		
				</td></tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
			
			<!-- 
			 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: -27px;"
	                title="Add"
		            buttonIcon="ui-icon-plus"
					onclick="addRelationshipMgr2();"
					>Add</sj:a> 
			 -->
				
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>

 	<div id="mappingGridId">
		<!-- Doctor Offering Mapping grid will be put here dynamically -->		
	</div>

	</div>
	
	<sj:dialog
	id="patientFeedbackDataView1"
	autoOpen="false"
	modal="true"
	showEffect="slide" 
   	hideEffect="explode" 
    openTopics="openEffectDialog"
    closeTopics="closeEffectDialog"
>
<div id="activityfeedbackData2"  style="   margin-left: 4px;
  padding: 0px;
  overflow-y: auto;
  width: 99%;
  position: absolute;"></div>
</sj:dialog>
	
</body>
	<script type="text/javascript">
     viewWellnessReport('0');
	</script>

	</html>