<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/visit/visitrequest.js"/>"></script>
<SCRIPT type="text/javascript">

loadGrid();

</SCRIPT>
</head>
<body>
<div class="list-icon">
	 <div class="head">Visiting Purpose </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="totalCount">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>   
</s:iterator>
</div> 
</div> 
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					     <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>	
						     <sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchRow();"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadGrid();"></sj:a>  
					          
         </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
        	<!-- <sj:a id="downloadButton"  button="true"  buttonIcon="ui-icon-arrowstop-1-s" cssClass="button" title="Download" cssStyle="height:25px; width:32px" onclick="setDownloadType('downloadExcel')"  ></sj:a> -->
				      <%if(userRights.contains("depa_ADD")) 
					{
			%>
			<sj:a cssClass="btn createNewBtnFeedbck" buttonIcon="ui-icon-plus" button="true" onclick="visitingPurposeAddPageOpen();" cssClass="button" title="Add">Add</sj:a>
			<%
			}
			%>
        
				  
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div id="result_partss"></div>
		</div></div>
</body>
</html>