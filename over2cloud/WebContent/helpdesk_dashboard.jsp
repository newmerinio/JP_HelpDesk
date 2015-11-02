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
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboardbar.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboardBeforeChart.js"/>"></script>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>

	
 


<s:url var="chartDataUrl" action="json-chart-data"/>

<title>HDM Dashboard</title>
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
<s:hidden id="dataFor" value="%{loginType}"/>
<div class="middle-content">
<!-- Scrolling Ticket Status Div -->

<!-- Scrolling Ticket Status Div End For HOD OR Particular Department -->

<!--Ticket Counter By Status Dashboard Start -->
<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;" id="counter_dash">
<div id="counterChart">
<div class="headdingtest">Ticket Counter Status For <s:property value="%{headerValue}"/></div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise('ticket_graph',300,900,'Ticket Count Status','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="bubbleStatus()"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="lineStatus()"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieStatus('Pending','Pending Ticket Status','For All Departments')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="Column2AxesChartStatus()"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="StatckedChartStatus()"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('jqxChart')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>

<div class="clear"></div>
 <div id="prevPie" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev()"  title="Previous"></sj:a></div> 
<div id='jqxChart' style="width: 100%; height: 200px;"  ></div>
<div id="nextPie" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next()" title="Next"></sj:a></div> 

</div>

</div>
<!-- Ticket Counter By Status Dashboard End -->


<!-- Level wise Ticket Status For Particular department Div Start -->
<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;" id="hod_level_status">
<div id="lable_graph">
<div class="headdingtest">Levelwise Status For <s:property value="%{headerValue}"/></div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise1('level_graph',300,900,'Level wise Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="bubbleLevel()"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="lineLevel()"><img src="images/line-graph.jpg" width="15" height="15" alt="Get Data" title="Show Line Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieLevel('Pending','Pending Ticket Status','For All Levels')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showLevelColumn2Axes()"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showLeveStackedBar()"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph 2 Axes" title="Show Stacked-Column Chart" border="0" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('levelChart')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<div id="prevPie1" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev1()"  title="Previous"></sj:a></div>
<div id='levelChart' style="width: 100%; height: 200px" align="center"></div>
<div id="nextPie1" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next1()" title="Next"></sj:a></div>
</div>
</div>
<!-- Level wise Ticket Status For Particular department Div End -->


<!-- Category wise Ticket Status Div Start for particular Department-->
<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;" id="hod_catg_status">


<!-- Category Dashboard Start -->
<div id="catg_hod_graph">
<div class="headdingtest">Current Day Analysis For <s:property value="%{headerValue}"/></div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise2('catg_graph',500,900,'Category Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="DoughnutCateg()"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showPieCateg()"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('CategChart')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<div id='CategChart' style="width: 100%; height: 200px" align="center"></div>
</div>
<!-- Category wise Ticket Status Div End for particular Department-->
</div>
<div class="contentdiv-smallnew" style="overflow: hidden;">
<div class="headding">Pending Ticket Status For <s:property value="%{headerValue}"/></div>
<div class="clear"></div>
<div style="height: auto; margin-bottom: 10px;">
	<table border="1" width="100%" align="center">
	    <tr>
			<td align="center" width="7%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Ticket&nbsp;No</b></td>
			<td align="center" width="12%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Feedback&nbsp;By</b></td>
			<td align="center" width="9%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Open Date</b></td>
			<td align="center" width="13%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Category</b></td>
		    <td align="center" width="13%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Sub-Category</b></td>
		    <td align="center" width="15%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Allot&nbsp;To</b></td>
		    <td align="center" width="20%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Location</b></td>
		    <td align="center" width="10%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Status</b></td>
		</tr>
	</table>
    <marquee onclick="this.setAttribute('scrollamount',1,1);this.setAttribute('direction','down')" onmouseover="this.setAttribute('scrollamount',0,0);this.setAttribute('direction','down')" onmouseout="this.setAttribute('scrollamount',1,5);this.setAttribute('direction','up')"  scrollamount="1" scrolldelay="1" direction="up" height="310" width="100%" style="color: #000000; text-align:left; font-family:Verdana; font-size:8pt">
    <s:iterator id="ticketData"  status="status" value="%{ticketsList}" >
	<table  width="100%" align="center">
 	<tr>
 	    <td align="center" width="7%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><a style="cursor: pointer;text-decoration: none;" onclick="ShowTicketData('<s:property value="%{id}"/>','T')" href="#"><b><s:property value="%{ticket_no}"/></b></a></td>
		<td align="center" width="12%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><a style="cursor: pointer;text-decoration: none;" onclick="ShowTicketData('<s:property value="%{id}"/>','FB')" href="#"><b><s:property value="%{feed_by}"/></b></a></td>
		<td align="center" width="9%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{open_date}"/></b></td>
		<td align="center" width="13%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{feedback_catg}"/></b></td>
 		<td align="center" width="13%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{feedback_subcatg}"/></b></td>
 		<td align="center" width="15%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{allot_to_mobno}"/></b></td>
 		<td align="center" width="20%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{location}"/></b></td>
 		<td align="center" width="10%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{status}"/></b></td>
 	</tr>
 	</table>
	</s:iterator>
	</marquee>
</div>
</div>
</div>
</body>
</html>