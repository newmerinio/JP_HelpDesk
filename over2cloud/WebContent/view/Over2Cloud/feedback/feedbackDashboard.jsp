<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript"src="<s:url value="/js/dashboard/feedbackDash.js"/>"></script>
<script type="text/javascript"src="<s:url value="/js/dashboard/feedbackDashoardbar.js"/>"></script>
<link rel="stylesheet" href="amcharts/plugins/export/export.css" type="text/css" />
<link rel="stylesheet" href="css/tabs.css" type="text/css" />

   <script src="amcharts/amcharts.js"></script>
	<script src="amcharts/serial.js"></script>
	<script src="amcharts/pie.js"></script>
	<script src="amcharts/themes/light.js"></script>
	<script  src="amcharts/plugins/export/export.js"></script>
	
<s:url var="chartDataUrl" action="json-chart-data" />

<script type="text/javascript">
	$(document).ready(function() {
		

		//document.getElementById('sdate').value = $("#hfromDate").val();
		//document.getElementById('edate').value = $("#htoDate").val();
		  $(".tabs-menu a").click(function(event) {
		        event.preventDefault();
		        $(this).parent().addClass("current");
		        $(this).parent().siblings().removeClass("current");
		        var tab = $(this).attr("href");
		        $(".tab-content").not(tab).css("display", "none");
		        $(tab).fadeIn();
		    });
	});
	
	
	function getDataForAllBoard() 
	{
		showMore2('jxchartneg');
		choosePie('choosePie');
		showCounterDataTicketing('jqxChart');
		showCounterDataAnalysis('3rdBlock'); 
	}
	
	function maximizeChart(divId) 
	{
		$('#maximizeDataPlr').width(1250);
		$('#maximizeDataPlr').height(450);
		//var data=$("#"+divId).html();
		//choosePie('maximizeDataPlr');
		
		$("#maxmizeViewPolar").dialog({title : 'Maximize View',width : 1300,height : 500});
		$("#maxmizeViewPolar").dialog('open');
		//$("#maximizeDataPlr").html(data);
		showMore2('maximizeDataPlr');
	}
	
	showMore2('jxchartneg');
	choosePie('choosePie');
	showCounterDataTicketing('jqxChart');
	showCounterDataAnalysis('3rdBlock'); 
	 
</script>
<style type="text/css">
.StyleScheme1{
background: #fbf9f9 url(template/theme/mytheme/images/ui-bg_glass_55_fbf9ee_1x400.png) 50% 50% repeat-x;


}
.contentdiv-small1 table td:HOVER {
	background: #e6e6e6
		url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50%
		50% repeat-x;
		
}

.myDiv {
	width: 327px;
	height: 32px;
	margin-top: 4px;
	font-family: sans-serif;
	font-weight: bold;
	color: black;
}
</style>
</head>
<body>
	<sj:dialog id="maxmizeViewPolar" modal="true" effect="slide"
		autoOpen="false" width="1200" height="580"
		title="Department Wise Ticket Status" overlayColor="#fff"
		overlayOpacity="1.0" position="['center','center']">
		<div id="maximizeDataPlr"></div>
	</sj:dialog>
	<sj:dialog id="feedDialog" modal="true" effect="slide" autoOpen="false"
		overlayColor="#fff" overlayOpacity="1.0"
		position="['center','center']">
	</sj:dialog>
	<div class="middle-content">
		<%-- <s:hidden id="hfromDate" value="%{fromDate}"></s:hidden>
		<s:hidden id="htoDate" value="%{toDate}"></s:hidden> --%>
		<center>
			<div class="myDiv">
				<div style="float: left; line-height: inherit; padding: 2px;">
					Data From
					<sj:datepicker id="sdate" cssClass="button"
						cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;"
						readonly="true" size="20" changeMonth="true" changeYear="true"
						yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"
						Placeholder="Select From Date" onchange="getDataForAllBoard()"
						value="%{fromDate}" />
					To
					<sj:datepicker id="edate" cssClass="button"
						cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;"
						readonly="true" size="20" changeMonth="true" changeYear="true"
						yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy"
						Placeholder="Select TO Date" onchange="getDataForAllBoard()"
						value="%{toDate}" />


				</div>
			</div>
			<div style="float: right; width: auto; padding: 0px 25px 0px 0px;margin-top: -2%;">
				<a href="#" onclick="getMailReport('')" > <img
					src="images/mailicon.png" width="25" height="20"
					alt="Mail report" title="Mail Report" border="1"/>
				</a>
			</div>
		</center>
		
		
		<div id="tabs-container">
    <ul class="tabs-menu">
        <li class="current"><a href="#tab-1">Feedback</a></li>
        <li><a href="#tab-2">Rating & Stakeholder</a></li>
    </ul>
    <div class="tab">
        <div id="tab-1" class="tab-content">
            <div id='chart' style="width: 100%; height: 100%">
    			 	<div  style="float:left;overflow: auto;border:1px solid #e1e1e1; margin-top: 1px;width: 34%;height: 450px;">
    			 	 <!-- <div style="float: right; width: auto; padding: 0px 4px 0 0;">
								<a href="#" onclick="maximizeChart('choosePie');"> <img src="images/maximize-icon.jpg" width="20" height="20"
									alt="Show Counters" title="MaximizeChart" /></a>
							</div>  -->
							<div style="float: center; width: auto; padding: 0px 4px 0 0;">
								<center>
									<font color="#000000"><b>Choosen BLK Because Of</b></font>
								</center>
							</div>
						<div id="choosePie"></div>
					</div>
					<div>
						<div class="contentdiv-small"
							style="overflow: top; margin-top: 1px; margin-left: 1px; width: 62.5%; height: 450px">
							 <!-- <div style="float: right; width: auto; padding: 0px 4px 0 0;">
								<a href="#" onclick="maximizeChart('jxchartneg');"> <img src="images/maximize-icon.jpg" width="20" height="20"
									alt="Show Counters" title="MaximizeChart" /></a>
							</div> -->
							
							 <div style="float: right; width: auto; padding: 0px 4px 0 0;">
									<a href="#" onclick="showMore2('jxchartneg');"> <img src="images/Economics.png" width="25" height="20"
										alt="Show Bar Graph" title="Bar Graph" border="1" /></a>
							</div>
							<div style="float: right; width: auto; padding: 0px 4px 0 0;">
								<a href="#" onclick="NegativeFeedAnlysisPie('jxchartneg');"> <img src="images/Pie-chart-icon.png" width="20" height="20"
									alt="Show Bar Graph" title="Pie Chart" /></a>
							</div>
							
							<div style="float: right; width: auto; padding: 0px 4px 0 0;">
								<a href="#" onclick="showNegativeFeedDataCounter('jxchartneg');">
									<img src="images/table.jpg" width="18" height="18"
									alt="Pie Chart" title="Show Counters" />
								</a>
							</div>
							
							<div style="float: center; width: auto; padding: 0px 4px 0 0;">
								<center>
									<font color="#000000"><b>Total Feedback Status</b></font>
								</center>
							</div>
								<div id='jxchartneg' style="width: 100%; height: 220px" align="center"></div>
						</div>
					</div>
				</div>
        </div>
        <div id="tab-2" class="tab-content">
            	 <div id='StackeHolder' style="width: 100%; height: 100%">
            	<div class="contentdiv-small" style="overflow: auto; margin-top: 1px;width: 95%;height: 250px;">
					<div id="3rdBlock"></div>
				</div>
            	<div class="contentdiv-small" style="y-overflow: scroll; x-overflow: hidden; width: 95%; height: 200px;">
														
														 
														<div style="float: right; width: auto; padding: 0px 4px 0 0;">
														
														<div style="float: right; width: auto; padding: 0px 4px 0 0;">
															<a href="#" onclick="showBarChart('jqxChart')"> <img
																src="images/chart-bar-stacked-icon.png" width="20" height="15"
																alt="Show Bar Graph" title="Bar Graph" border="1" />
															</a>
														</div>
														<div style="float: right; width: auto; padding: 0px 4px 0 0;">
															<a href="#" onclick="showCounterDataTicketing('jqxChart')"> <img
																src="images/table.jpg" width="18" height="18" alt="Show Counters"
																title="Show Counters" />
															</a>
														</div>
														<div style="float: center; width: auto; padding: 0px 4px 0 0;">
											
														</div>
														<div id='jqxChart' style="width: 100%; height: 220px" align="center">
														
														<div class="counertDiv">
											<center><font color="#000000"><b><s:property value="%{graphHeader}"/></b></font></center>
											
												<br>
												</div>
														</div>
													</div>
            </div>
        </div>
        
        </div>
        
    </div>
</div>
		
		
</body>
<script type="text/javascript">

</script>
</html>