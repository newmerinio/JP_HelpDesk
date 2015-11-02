<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="js/WFPM/CRM/viewCommactivity.js"/>"></script>
</head>
<body>
<s:hidden name="groupId" value="%{groupId}"></s:hidden>
	<div class="clear"></div>
	<div class="middle-content">
	<div class="list-icon">
		<div class="list-icon"><div class="head">Group Mail</div>
		<div class="head">
		<img alt="" src="images/forward.png" style="margin-top:70%; float: left;"></div>
		<div class="head">View </div>
	   </div>
	</div>
	<div style=" float:left; padding:10px 1%; width:97%;">
		<div class="clear"></div>	 	
		<div id="alphabeticalLinks"></div>
	 	<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" 
					width="100%"><tbody>
					<tr>
					 <td><!--
						<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Inactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px; " 
						cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" 
						cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
						-->
						</td></tr>
						<tr><td> <table class="floatL" border="0" cellpadding="0" 
									cellspacing="0"><tbody>
						<tr>
						<td class="pL10">
			<div style="margin-top: 0%;margin-left: 0PX;">
		   <sj:a 
	    	cssClass="button" 
			button="true"
			cssStyle="height:25px;" 
		    title="Back"
			onclick="backToMainView();"
				>
		  	Back
			</sj:a>	 
			</div>	
						<div style="margin-top: -2%;margin-left: 924PX;">
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;"
	                title="Group Mail"
		            onclick="communicateViaMail('%{groupId}');"
					>Send Mail</sj:a>
			</div>
					</td>
					</tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
	   </td>   
				</tr></tbody></table></div>
			</div>
	 	</div>

<s:url id="viewMailGroupDetails" action="viewMailGroupDataGrid" escapeAmp="false">
	<s:param name="groupId" value="%{groupId}"></s:param>
</s:url>
<s:hidden id="records" value="%{records}"></s:hidden>
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridBasic"
          href="%{viewMailGroupDetails}"
          gridModel="groupdataViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorEdit="true"
          navigatorDelete="true"
          navigatorSearch="true"
          navigatorRefresh="true"
          navigatorSearch="true"
          rowList="15,20,250"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyClient}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false" 
          autowidth="true"    
          >
       <s:iterator value="crmgroupViewProp" id="crmgroupViewProp" >  
		<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}" 
			title="%{headingName}"
			width="%{123}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
		/>
		</s:iterator>
	</sjg:grid>
	</div>
	</div>
	</div>
</body>

<sj:dialog 
		id="groupCountDetails"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="600" 
	    height="150" 
	    title="View Group Detail" 
	    loadingText="Please Wait" 
	    overlayColor="#fff" 
	    overlayOpacity="1.0" 
	    >
</sj:dialog>
</html>