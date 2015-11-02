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
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript">
function setDownloadType(type){

  var conP = "<%=request.getContextPath()%>";
  var downloadactionurl="downloadfeedbackreportPos";
$("#downloaddailcontactdetails").load(conP+"/view/Over2Cloud/feedback/feedbackprogress.action?downloadType="+type+"&downloadactionurl="+downloadactionurl+"&tableName=feedbackdata&level=Yes");
$("#downloaddailcontactdetails").dialog('open');

}

function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}

function reload(rowid, result) {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	type : "post",
	url : "view/Over2Cloud/feedback/beforeCustomerPostiveView.action?flage=positive",
	success : function(subdeptdata) {
	$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
	   alert("error");
	}
	});
	}

function searchData()
{
	$("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
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
	
//	alert(">>>>>>>>>>>>>>>>."+actionStatus);
	
	$.ajax({
 		type :"post",
 		url : "view/Over2Cloud/feedback/PosFeedView.action?flage=positive&fromDate="+fromDate+"&toDate="+toDate+"&mode="+mode.split(" ").join("%20")+"&patType="+patType+"&docName="+docName.split(" ").join("%20")+"&spec="+spec.split(" ").join("%20")+"&actionStatus="+actionStatus.split(" ").join("%20"),
 		success : function(data) 
	    {
			$("#posFeedback").html(data);
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
 		url : "view/Over2Cloud/feedback/beforeCustomerPostiveView.action?flage=positive",
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
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Positive Feedback</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">Total: <s:property value="%{totalPos}"/><sj:a id="refButtonPage1" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px;margin-left: 742px;" cssClass="button" button="true" onclick="reloadPage();"></sj:a></div> 
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
					        <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchData()"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>  
					        <sj:datepicker onchange="getSearchData();" cssStyle="height: 16px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
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
<div id="posFeedback">

</div>
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
</body>
</html>