<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboard.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboardbarForT2M.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboardBeforeChartForT2M.js"/>"></script>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
<s:url var="chartDataUrl" action="json-chart-data"/>

<title>T2M Dashboard</title>

<script type="text/javascript" >

$(document).ready(function() {
		document.getElementById('sdate').value = $("#hfromDate").val();
		document.getElementById('edate').value = $("#htoDate").val();
		showPieCateg('sdate','edate');
		
	});
	
function getDataForAllBoard(todate122,fromdate122) {
	showExecutiveKeywordByDate(todate122,fromdate122);
	/* showCounterDataTicketing('block2');
	showCounterDataFeedbackAction('2ndBlock');
	showCounterDataAnalysis('3rdBlock');
	showNegativeFeedDataCounter('jxchartneg'); */
}
/* function showExecutiveKeywordByDate(todate122,fromdate122)
{
alert("hii");
 var todate122=$("#"+todate122).val();
var fromdate122=$("#"+fromdate122).val();

alert(todate122+"      "+fromdate122);

	 $("#dialogOpen").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/Text2Mail/jsonChartData4Excecutivekeyword.action?fromDate122="+fromdate122+"&toDate122="+todate122,
			success : function(exedata) {
			$("#dialogOpen").html(exedata);
			},
			error : function() {
				alert("error");
			}
		});
	 
} */
</script>

<style type="text/css">
.headdingtest{
font-weight: bold;
line-height: 30px;
margin-left: 30px; 
}
</style>
</head>
<body>
<sj:dialog 
	    	id="ticketDialog" 
	    	autoOpen="false" 
	    	modal="true" 
	    	showEffect="slide" 
	    	hideEffect="explode" 
	    	position="['center','top']"
    >
<center><div id="ticketsInfo"></div></center>
</sj:dialog>
    
<center>
<sj:dialog 
            id="confirmEscalationDialog" 
            autoOpen="false"  
            closeOnEscape="true" 
            modal="true" 
            title="Action On Tickets" 
            width="900" 
            height="350" 
            showEffect="slide" 
            hideEffect="explode" >
            <div id="confirmingEscalation"></div>
</sj:dialog>
</center>

<sj:dialog
          id="maxmizeViewDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          closeTopics="closeEffectDialog"
          >
          <div id="maximizeDataDiv"></div>
</sj:dialog>

<sj:dialog
          id="maxmizeCatgDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          width="900"
          height="400"
          closeTopics="closeEffectDialog"
          >
          <div id="maximizeCatgDiv"></div>
</sj:dialog>
 <div class="middle-content">
 <s:hidden id="hfromDate" value="%{fromDate122}"></s:hidden>
		<s:hidden id="htoDate" value="%{toDate122}"></s:hidden>
		<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;width:550px;height: 300px;" id="counter_dash">
		<center>
			<div class="myDiv">
				<div style="  line-height: inherit; padding: 2px;">
					Data From
					<sj:datepicker id="sdate" cssClass="button"
						cssStyle="width: 73px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;"
						readonly="true" size="20" changeMonth="true" changeYear="true"
						yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"
						Placeholder="Select From Date" onchange="getDataForAllBoard('edate','sdate')"
						value=" " />
					To
					<sj:datepicker id="edate" cssClass="button"
						cssStyle="width: 73px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;"
						readonly="true" size="20" changeMonth="true" changeYear="true"
						yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"
						Placeholder="Select TO Date" onchange="('edate','sdate')"
						value=" " />


				</div>
			</div>
		</center>
<div style="overflow: hidden;width:550px" id="counter_dash">
<div id="catg_hod_graph">
<div class="headdingtest"> </div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
  <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieCateg('sdate','edate')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('CategChart','sdate','edate')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<div id='CategChart' style="width: 100%; height: 200px" align="center"></div>
</div>

</div>
 </div>
</div>
</body>
</html>