<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Incentive View</title>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="js/WFPM/incentive/incentive.js"/>"></script>

</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head">Incentive</div>
		<div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
		<div class="head" id="headDynamic">KPI</div> 
		<div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
		<div class="head">View</div> 
	</div>
	
	<!-- Level 1 to 2 Data VIEW URL CALLING Part Starts HERE -->
	<s:url id="viewLevel1" action="viewLevel1" />
	
	
	<!-- Level 1 to 2 Data Modification URL CALLING Part Starts HERE -->
	<s:url id="viewModifyLevel1" action="viewModifyLevel1" />
	<!-- Level 1 to 2 Data Modification URL CALLING Part ENDS HERE -->
	
	<div class="rightHeaderButton">
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 1%; width:98%;">
	<div class="listviewBorder">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" 
						cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
				</td></tr></tbody></table>
			</td>
	 <td class="alignright printTitle">
	 </td> 
		 <td class="alignright printTitle">
		 <s:radio list="#{'0':'KPI','1':'Offering'}" id="incentiveType" name="incentiveType" 
	 		onclick="viewIncentiveGrid()" value="%{incentiveType}" theme="simple"></s:radio>
	 		&nbsp;&nbsp;&nbsp;			
	     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" 
     	   onclick="addIncentivePage();">Allot Incentive</sj:a>
	</td>   
	</tr></tbody></table></div></div>
	
	<div id="dynamicDataPart">
		<!-- incentive data will come here dynamically *******************************************************************-->
	</div>	
	
	
	
	</div>
	</div>
</body>
<SCRIPT type="text/javascript">
viewIncentiveGrid();
</SCRIPT>
</html>