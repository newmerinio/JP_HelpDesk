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
<script src="<s:url value="js/PatientWFPM/beforeRelMgrView.js"/>"></script>
</head>

<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="list-icon">
		<div class="head">
		<div id="Cid" class="head">Relationship Manager</div><div class="head">
		<img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div><div class="head">View</div>
		</div>
	    
		</div>
		
	</div>



<div style=" float:left; padding:10px 1%; width:98%;">
		
		<div class="clear"></div>	 	
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
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
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
					
		
				</td></tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
			
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;margin-top: -27px;"
	                title="Add"
		            buttonIcon="ui-icon-plus"
					onclick="addRelationshipMgr();"
					>Add</sj:a> 
				
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>

 	<div id="datapart">
		<!-- Patient grid will be put here dynamically -->		
	</div>

	</div>
	<br><br>
	<center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div id="divFullHistory" style="float:left; width:100%;"></div>
	
	
</body>
	<script type="text/javascript">
		viewRelManager('0');
	</script>

	</html>