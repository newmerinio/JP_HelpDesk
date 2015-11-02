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
<script src="<s:url value="/js/feedback/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});


$.subscribe('getData', function(event, data) 
{
	getSearchData();
});



function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function reloadGrid()
{
	var conP = "<%=request.getContextPath()%>";
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/beforeFeedbackView.action?modifyFlag=0&deleteFlag=0",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function addNewFeedback() {

	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/beforeFeedbackAddViaPaper.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
	
}

function setDownloadType(type)
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var patType=$("#patType").val();

	var spec=$("#spec").val();
	var docName=$("#docName").val();

	var feedBack=$("#feedBack").val();
	
	var conP = "<%=request.getContextPath()%>";
 var downloadactionurl="downloadfeedbackreportPaper";
$("#downloaddailcontactdetails").load(conP+"/view/Over2Cloud/feedback/feedbackprogress.action?downloadType="+type+"&downloadactionurl="+downloadactionurl+"&tableName=feedbackdata&fromDate="+fromDate+"&toDate="+toDate+"&patType="+patType.split(" ").join("%20")+"&spec="+spec.split(" ").join("%20")+"&docName="+docName.split(" ").join("%20")+"&feedBack="+feedBack.split(" ").join("%20"));
$("#downloaddailcontactdetails").dialog('open');

}

function showGraph()
{
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/demoGraph.jsp",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function reloadPage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/feedback/beforeFeedbackView.action?modifyFlag=0&deleteFlag=0",
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
	var patType=$("#patType").val();

	var spec=$("#spec").val();
	var docName=$("#docName").val();

	var feedBack=$("#feedBack").val();
	
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/feedback/beforeSearchedPaperView.action?modifyFlag=0&deleteFlag=0&mode=Paper&fromDate="+fromDate+"&toDate="+toDate+"&patType="+patType+"&docName="+docName.split(" ").join("%20")+"&spec="+spec.split(" ").join("%20")+"&feedBack="+feedBack.split(" ").join("%20"),
 		success : function(data) 
	    {
			$("#paperFeedback").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
	
}

getSearchData();
</SCRIPT>
</head>
<body>
<sj:dialog 
           id="viewratingId" 
           showEffect="slide" 
           hideEffect="explode" 
           autoOpen="false" 
           modal="true" 
           title="Rating View"
           openTopics="openEffectDialog" 
           closeTopics="closeEffectDialog"
           modal="true" 
           width="1000" 
           height="540">
 </sj:dialog>
<sj:dialog 
           id="facilityisnotavilable" 
           buttons="{ 'OK ':function() { },'Cancel':function() { },}"  
           showEffect="slide" 
           hideEffect="explode" 
           autoOpen="false" 
           modal="true" 
           title="This facility is not available in Existing Module"
           openTopics="openEffectDialog"closeTopics="closeEffectDialog"
           modal="true" 
           width="390" 
           height="100">
 </sj:dialog>
<div class="list-icon">
	 <div class="head">Paper Mode</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Total: <s:property value="%{totalPaper}"/>, Positive: <s:property value="%{posPaper}"/>, Negative: <s:property value="%{negPaper}"/></div> 
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
							<sj:datepicker cssStyle="height: 16px; width: 60px;"  onblur="getSearchData();"  onchange="getSearchData();" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" onchange="resetToDate(this.value)" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
		     				<sj:datepicker cssStyle="height: 16px; width: 60px;" onchange="getSearchData();"onblur="getSearchData();" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}" minDate="%{minCount}"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />  
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
                      id="feedBack" 
			          name="feedBack" 
			          list="{'Positive','Negative'}"
			          headerKey="-1"
			          headerValue="Feedback"
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
			<sj:a cssClass="btn createNewBtnFeedbck" buttonIcon="ui-icon-plus" button="true" onclick="addNewFeedback();" cssClass="button" title="Add">Add</sj:a>
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
<div id ="paperFeedback">

		</div>
		</div>
		<center>
         <sj:dialog 
      	id="downloaddailcontactdetails" 
      	 	  		buttons="{ 
    		'Download':function() {okdownload();},
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
		</div></div>
</body>
</html>