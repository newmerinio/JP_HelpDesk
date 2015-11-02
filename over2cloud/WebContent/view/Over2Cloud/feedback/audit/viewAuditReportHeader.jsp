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
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

function actionForAudit()
{
	if (row == null || row==0)
	{
		alert("Please Select a Row !!!");
	}
	else
	{
	 var patientId  = jQuery("#auditGrid").jqGrid('getCell',row,'patientId');
	 var clientId  = jQuery("#auditGrid").jqGrid('getCell',row,'clientId');
	 var feedbackDataId = jQuery("#auditGrid").jqGrid('getCell',row, 'feedDataId');
	 var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	        type : "post",
	        url :  conP+"/view/Over2Cloud/feedback/feedback_Activity/beforeActionForAudit.action?id="+row+"&clientId="+clientId+"&patientId="+patientId+"&feedDataId="+feedbackDataId,
	        success : function(subdeptdata) {
	        	$("#data_part").html(subdeptdata);
		    },
		       error: function() {
		            alert("error");
		        }
		     });  
	}    
}

function viewAuditReportData()
{
	 var fromDate  = $("#fromDate").val();
	 var toDate  = $("#toDate").val();
	$("#auditData").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/feedback_Activity/beforeAuditData.action?fromDate="+fromDate+"&toDate="+toDate,
	    success : function(data) {
       		$("#auditData").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

viewAuditReportData();
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Audit</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<div id="alphabeticalLinks"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					     				From: <sj:datepicker cssStyle="height: 16px; width: 60px;"
													cssClass="button" id="fromDate" name="fromDate" size="20"
													maxDate="0" value="%{fromDate}" readonly="true"
													yearRange="2013:2050" showOn="focus"
													displayFormat="dd-mm-yy"  onchange="viewAuditReportData()"/>
										To: <sj:datepicker
													cssStyle="height: 16px; width: 60px;" cssClass="button"
													id="toDate" name="toDate" 
													onchange="viewAuditReportData();" size="20" value="%{toDate}"
													readonly="true" yearRange="2013:2050" showOn="focus"
													displayFormat="dd-mm-yy"  /> 
		
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <%if(userRights.contains("audt_ADD")){%>
					  <sj:a id="addButton"  button="true" cssClass="button" onclick="actionForAudit()">Action</sj:a>
				  <%} %>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div id="auditData"></div>
</div>
</div>

</body>

</html>