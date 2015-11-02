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

function setDownloadType(type){
	var conP = "<%=request.getContextPath()%>";
 var downloadactionurl="downloadfeedbackreportPaper";
$("#downloaddailcontactdetails").load(conP+"/view/Over2Cloud/feedback/feedbackprogress.action?downloadType="+type+"&downloadactionurl="+downloadactionurl+"&tableName=feedbackdata");
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


function getSearchData()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var patType=$("#patType").val();
	var mode=$("#mode").val();
	
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/feedback/beforeSearchDataView.action?startDate="+fromDate+"&endDate="+toDate+"&type="+patType+"&mode="+mode.split(" ").join("%20"),
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
<div class="list-icon">
	 <div class="head">Statistics</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div align="right"><sj:a  buttonIcon="ui-icon-person" button="true" onclick="viewFeedback();" title="PCC Action Page" cssClass="button" cssStyle = "width: 140px;margin-right: 3px;margin-top:3px;height:25px">Activity Board</sj:a></div> 
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
							<sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
		     				<sj:datepicker onchange="getSearchData();" cssStyle="height: 16px; width: 60px;" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}" minDate="%{minCount}"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date"/>  
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
                      id="mode" 
			          name="mode" 
			          list="{'Paper','SMS','Mob App'}"
			          headerKey="-1"
			          headerValue="Mode"
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
		
		</div></div>
</body>
</html>