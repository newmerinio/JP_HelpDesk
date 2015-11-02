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

<script type="text/javascript" src="<s:url value="/js/communication/CommunicationInstantMessage.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/WFPM/CRM/mailreports.js"/>"></script>


<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event,data) {
	row = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	});
function mail_body(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:openmailcontaintView("+row.id+")'>" + cellvalue + "</a>";
}
function openmailcontaintView(id)
{
	$("#mail_bodyss").dialog('open');
	$("#mail_bodyss").load("<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/mail/fetchmailbody.action?id="+id);
	
}


function instantmailsender()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CommunicationOver2Cloud/mail/beforeInstantmailsend.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });

	}
function searchRow()
{
	$("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	//alert("Hello");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/report/DSR/beforinstantcrmmailreport.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}

function addNewGroup()
{
	
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url : "/view/Over2Cloud/WFPM/Communication/beforeGroupAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}
</script>


<sj:dialog
				id="mail_bodyss" 
				autoOpen="false" 
				closeOnEscape="true" 
				modal="true"
				title="Mail Content " 
				width="600"
				height="240" 
				showEffect="slide" 
				hideEffect="explode"
				>
				</sj:dialog>
</head>
<body>
<div class="clear"></div>

<div class="middle-content">
<div class="list-icon">
	<div class="head">Mail Report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>

</div>
<div style=" float:left; padding:10px 1%; width:98%;">
<div class="clear"></div>

<br><div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
 <%-- <%if(userRights.contains("emailreport_MODIFY")){ %>
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
				 	<%} %>
				 <%if(userRights.contains("emailreport_DELETE")){ %>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					 	<%} %>
 --%>
 					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<!-- <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="excelDownload();" />
 <sj:a  button="true"  cssClass="button" cssStyle="height:25px; width:32px"  title="Upload" buttonIcon="ui-icon-arrowstop-1-n" onclick="excelUpload1();" /> 
				          
			               
			              
				           
				           
<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="downloadGridData();" >Download</sj:a>	
--><!--<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="excelUpload();">Import</sj:a>

 <%if(userRights.contains("emailreport_ADD")){ %>

<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="instantmailsender();">Add</sj:a>
 	<%} %>-->
</td> 
</tr></tbody></table></div></div>

	<div id="maildatapart">
		<!-- Mail report grid will be put here dynamically -->		
	</div>




</div>
</div>
</div>

</body>
<center>
<script type="text/javascript">
searchshowMailreportdata();
</script>
</center>
</html>