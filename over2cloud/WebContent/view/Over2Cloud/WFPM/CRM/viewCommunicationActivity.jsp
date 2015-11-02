<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="js/WFPM/CRM/viewCommactivity.js"/>"></script>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="list-icon"><div class="head">Saved Parameters CRM Group</div>
		<div id="Cid" class="head">Active: <s:property value="%{activeGroups}"/>, Inactive: <s:property value="%{deactiveGroups}"/></div><div class="head">
		<img alt="" src="images/forward.png" style="margin-top:70%; float: left;"></div>
		<div class="head">Total: </div>
	    <div id="records" class="head"><s:property value="%{records}"/></div>	
		</div>
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
						<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Inactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px; " 
						cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" 
						cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
						</td></tr>
						<tr><td> <table class="floatL" border="0" cellpadding="0" 
									cellspacing="0"><tbody>
						
						<tr>
						<td class="pL10"> 
						<s:select
							id="groupnameId" 
							name="name" 
							list="groupNameList"
						    headerKey="-1"
							headerValue="Communication group" 
							cssStyle="height: 29px; width: 28%; margin-top: -22%;float-right: 152%;margin-left: 25%;margin-top: -4%;"
							theme="simple"
							onchange="searchResult('','','','');"
							cssClass="button"
							/>
						
						 <s:select
									id="status" 
									name="status" 
									list="{'All Status','Active','Inactive'}"
									cssStyle="height: 28px; width: 19%;margin-left: 322px;margin-top: -28px;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							  />	
					</td>
					
					</tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
			<div style="margin-top: -4%;">
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: -17px;"
	                title="Group Mail"
		           onclick="addGroupMail();"
					>Group Mail</sj:a>
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: -17px;"
	                title="Add"
		            buttonIcon="ui-icon-plus"
					onclick="addactivityGroup();"
					>Add</sj:a>   
			</div>
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>

 	<div id="crmactivityshowgrid">
		<!-- CRM activity grid will be put here dynamically -->		
	</div>

	</div>
	
</body>
<script type="text/javascript">
	var param = 0;
	searchshowCommActivityData(param);
</script>
<sj:dialog 
		id="groupCountDetails"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="1000" 
	    height="550" 
	    title="View Group Detail" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
</sj:dialog>
</html>