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
function setDownloadType(type){

  var conP = "<%=request.getContextPath()%>";
  var downloadactionurl="downloadfeedbackreportNeg";
$("#downloaddailcontactdetails").load(conP+"/view/Over2Cloud/feedback/feedbackprogress.action?downloadType="+type+"&downloadactionurl="+downloadactionurl+"&tableName=feedbackdata&fbtype=No");
$("#downloaddailcontactdetails").dialog('open');

}

function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">

function toggle_visibility(id1,id2) {
	if(document.getElementById(id1).style.display == 'block'){
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"inactive";
		sub1.style.display	=	"none";
	}else{
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"active";
		sub1.style.display	=	"block";
	}
}

function ticketNo2(cellvalue, options, row) {
	return "<a href='#' onClick='javascript:openDialog10("+row.id+ ","+row.feedTicketId+ ")'>" + cellvalue + "</a>";
}

function actionDetails(cellvalue, options, row) {
	return "<a href='#' onClick='javascript:getActionTakenDetails("+row.id+")'>" + cellvalue + "</a>";
}

function getActionTakenDetails(feedDataId)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/getAllFeedDetails.action?id="+feedDataId,
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}


function openDialog10(userId,feedTicketId) {
    id=userId;
    // $("#actionHistoryDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/WFPM/Lead/leadHistoryAction.action?id=" + userId+"&feedTicketId="+feedTicketId);
    // $("#actionHistoryDialog").dialog("open");
    // $("#feedbackTakeAction").load("<%=request.getContextPath()%>/view/Over2Cloud/feedback/viewAction7.action?id=" + userId+"&feedTicketId="+feedTicketId);
    // $("#feedbackTakeAction").dialog('open');


  //  var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/viewAction7.action?id="+ userId+"&feedTicketId="+feedTicketId,
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}
	</SCRIPT>
	
	<script type="text/javascript">
	
	function searchRow()
	{
		 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
	}

	function reloadGrid()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		type : "post",
		url : "view/Over2Cloud/feedback/beforeCustomerNegView.action?flage=negative",
		success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
		},
		error: function() {
		   alert("error");
		}
		});
	}


	function getSearchData()
	{
		var fromDate=$("#fromDate").val();
		var toDate=$("#toDate").val();
		var mode=$("#mode").val();
		var patType=$("#patType").val();

		var spec=$("#spec").val();
		var docName=$("#docName").val();

		var actionStatus=$("#actionStatus").val();
		
		
		$.ajax({
	 		type :"post",
	 		url : "view/Over2Cloud/feedback/beforeNegFeedView.action?flage=negative&fromDate="+fromDate+"&toDate="+toDate+"&mode="+mode.split(" ").join("%20")+"&patType="+patType+"&docName="+docName.split(" ").join("%20")+"&spec="+spec.split(" ").join("%20")+"&actionStatus="+actionStatus.split(" ").join("%20"),
	 		success : function(data) 
		    {
				$("#negFeedback").html(data);
			},
		    error: function()
		    {
		        alert("error");
		    }
	 	});
	}
	getSearchData();


	function reloadPage()
	{
		$.ajax({
	 		type :"post",
	 		url : "view/Over2Cloud/feedback/beforeCustomerNegView.action?flage=negative",
	 		success : function(data) 
		    {
				$("#data_part").html(data);
			},
		    error: function()
		    {
		        alert("error");
		    }
	 	});
	}

	
</script>

	</head>
<body>
<sj:dialog id="feedbackTakeAction"   title="Action on Negative Feedback" modal="true" effect="slide" autoOpen="false"  width="1100" height="350" hideEffect="explode" position="['center','middle']">
</sj:dialog>
<sj:dialog id="printSelect" title="Feedback Action Details" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop">
</sj:dialog>
<div class="list-icon">
	 <div class="head">Negative Feedback</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">Total: <s:property value="%{totalNeg}"/><sj:a id="refButtonPage1" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px;margin-left: 742px;" cssClass="button" button="true" onclick="reloadPage();"></sj:a></div> 
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
					        <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchRow();"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadGrid();"></sj:a>  
					        <sj:datepicker onchange="getSearchData();" cssStyle="height: 16px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" onchange="resetToDate(this.value)" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
		     				<sj:datepicker onchange="getSearchData();" cssStyle="height: 16px; width: 60px;" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}" minDate="%{minCount}"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date"/>
					        <s:select 
                      id="mode" 
			          name="mode" 
			          list="{'Paper','SMS','Mob Tab'}"
			          headerKey="-1"
			          headerValue="Mode"
			          cssClass="button"
			          cssStyle = "margin-left: 3px;margin-top:-31px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			  <s:select 
                      id="patType" 
			          name="patType" 
			          list="{'IPD','OPD'}"
			          headerKey="-1"
			          headerValue="Patient Type"
			          cssClass="button"
			          cssStyle = "margin-left: 3px;margin-top:-31px;width: 120px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			 <s:select 
                      id="spec" 
			          name="spec" 
			          list="speciality"
			          headerKey="-1"
			          headerValue="Speciality"
			          cssClass="button"
			          cssStyle = "margin-left: 3px;margin-top:-31px;width: 120px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			 <s:select 
                      id="docName" 
			          name="docName" 
			          list="treatedBy"
			          headerKey="-1"
			          headerValue="Doctor Name"
			          cssClass="button"
			          cssStyle = "margin-left: 3px;margin-top:-31px;width: 120px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
					    <s:select 
                      id="actionStatus" 
			          name="actionStatus" 
			          list="feedbackActionStatus"
			          headerKey="-1"
			          headerValue="Action Status"
			          cssClass="button"
			          cssStyle = "margin-left: 3px;margin-top:-31px;width: 120px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			      </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <sj:a id="downloadButton"  button="true"  buttonIcon="ui-icon-arrowstop-1-s" cssClass="button" title="Download" cssStyle="height:25px; width:32px" onclick="setDownloadType('downloadExcel')"  ></sj:a>
				      <%if(userRights.contains("nefn") ) 
			{
			%>
			<%
			}
			%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="negFeedback">
</div>
</div>
</div><br><br>
</div>

<div id="takeActionOnLead8" style="display: none; width:850px;">
<div class="wrapper">
	<div class="sub_wrapper">
		<div class="block_main repeating_block_head"><a href="javascript:void();" id="tab4" class="inactive" onclick="javascript:toggle_visibility('content9','tab4');"><h1><b>Take Action</b></h1></a></div><br>
		<div class="content_main" id="content9" style="border-top:3px solid #aaa9ab;">
			
		</div>
	</div>
</div>
</div>
<center>
         <sj:dialog 
      	id="downloaddailcontactdetails" 
      	 	  		buttons="{ 
    		' Download':function() {okdownload();},
    		}"  
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Select Fields"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="390"
		height="300"
    />
  </center>
</body>
</html>