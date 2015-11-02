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
<script type="text/javascript">
function getSearchData()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/bedMapping/viewBedMapping.action",
	    success : function(data) 
	    {
			$("#"+"bedDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	
}

getSearchData();
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	Configure Bed Mapping</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					   <sj:a id="editButton111" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onClickTopics="editRow"></sj:a>
					   <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
						<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
					   
					   </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
                      <sj:a id="uploadButton"    button="true"  buttonIcon="ui-icon-arrowstop-1-n" cssClass="button" cssStyle="height:25px; width:32px" title="Upload"  onclick="uploadFeedback('dataFor');"></sj:a>
				          <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addBedEntry();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="bedDiv">
</div>
</div>
</div>
</div>
</body>
</html>