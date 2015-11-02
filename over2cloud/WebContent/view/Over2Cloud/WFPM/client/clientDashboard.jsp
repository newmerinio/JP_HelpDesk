<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% 
String userTpe=(String)session.getAttribute("userTpe");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/client/clientDashboard.js"></script>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdraw.js"></script>


<s:url var="chartDataUrl" action="json-chart-data"/>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/graphDownload.js"></script>
<script type="text/javascript" src="js/canvas2image.js"></script>

<link rel="stylesheet" type="text/css" href="css/chartdownload.css" />
	
 <script type="text/javascript">

 function button1(){
		document.getElementById('ann12').style.display = "none";
		document.getElementById('bir12').style.display = "block";
		
	}

	function button2(){
		
		
		document.getElementById('ann12').style.display = "block";
		
		document.getElementById('bir12').style.display = "none";
	}

</script>
 
 
<script type="text/javascript">


showClientStatusPie('clientStatusDiv');
showBarChart('CategChart');
DoughnutCateg2('jqxChart');
showData('offWrtClient','');
show2ndblockpie('clientSourceDiv');

</script>

<s:url var="chartDataUrl" action="json-chart-data"/>

<title>HDM Dashboard</title>
<style type="text/css">
.colorme{
color:black;
}
.myDiv{

width: 327px;
height: 32px;
margin-top: 4px;
font-family: sans-serif;
font-weight: bold;
color: black;

}
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
        
          <div id="maximizeDataDiv" style="width: 100%; height: 100%;">
          </div>
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
<!--<sj:dialog
          id="chartsdialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
           width="1000"
          height="450"
          closeTopics="closeEffectDialog"
          >
        <a href="#" >  <span class="label label-default" onclick="showBarCalled('stackedcolumn');">Stacked-Column Chart</span></a>
        <a href="#">  <span class="label label-info" onclick="showBarCalled('column');">2 Axes-Column Chart</span></a>
        <a href="#">  <span class="label label-primary" onclick="showBarCalled('line');">Line Chart</span></a>
         <a href="#">  <span class="label label-success" onclick="showBarCalled('bubble');">Bubble Chart</span></a>
         
         <div id='jxchartOutlet' style="width: 100%;height:370px;display: none;margin-top:10px; " align="center"></div>
</sj:dialog>
<sj:dialog
          id="activitychartsdialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
           width="1000"
          height="450"
          closeTopics="closeEffectDialog"
          >
        <a href="#" >  <span class="label label-default" onclick="showBarClientActivity('stackedcolumn');">Stacked-Column Chart</span></a>
        <a href="#">  <span class="label label-info" onclick="showBarClientActivity('column');">2 Axes-Column Chart</span></a>
        <a href="#">  <span class="label label-primary" onclick="showBarClientActivity('line');">Line Chart</span></a>
         <a href="#">  <span class="label label-success" onclick="showBarClientActivity('bubble');">Bubble Chart</span></a>
         
         <div id='jxchartOutletID' style="width: 100%;height:370px;display: none;margin-top:10px; " align="center"></div>
</sj:dialog>



--><div id="ticketStatusTab">
<div class="middle-content">
<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;" id="hod_catg_status">
<!-- Client Dashboard Start 1 -->
<div id="catg_graph">
<div class="headdingtest">Client Type Status </div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise('catg_graph',500,900,'Category Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="bubbleStatus('CategChart')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="DoughnutCateg('CategChart')"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 5px 0;"><a href="#" onclick="showBarChart('CategChart')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph" title="Show Bar Graph" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showClientPie('CategChart')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('CategChart','','')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<div id='CategChart' style="width: 100%; height: 200px" align="center"></div> 
</div>
</div>
<!-- Category wise Ticket Status Div End for particular Department-->

<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;" id="counter_dash">
<div id="counterChart">
<div class="headdingtest">Client Activity </div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise1('ticket_graph',400,900,'Ticket Count Status','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="bubbleStatusActivity('jqxChart')"><img src="images/chart_bubble.png" width="20" height="15" alt="Show Bubble Chart" title="Show Bubble Chart" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="DoughnutCateg2('jqxChart')"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showClientActivityPie('jqxChart','','')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showActivityBarChart('jqxChart')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('jqxChart','','')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
 <div id="prevPie" style="display: none;float: left;margin-right: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-w" onclick="prev()"  title="Previous"></sj:a></div> 
<div id='jqxChart' style="width: 100%; height: 200px;"  ></div>
<div id="nextPie" style="display: none;float: right;margin-left: 5px;"><sj:a  cssStyle="width:10px;height:200px;" cssClass="button" button="true" buttonIcon="ui-icon-carat-1-e" onclick="next()" title="Next"></sj:a></div> 

</div>

</div>


<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;" id="hod_catg_status">


<!-- Category Dashboard Start -->
<div id="clientStatusDiv_graph">
<div class="headdingtest">Client Activity Status</div>
<!-- <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3rdBlock')"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div> -->
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise2('clientStatusDiv_graph',500,900,'Category Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<!--<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="Column2Axes('clientStatusDiv')"><img src="images/Column-Chart-2Axes.png" width="20" height="15" alt="Show Bar Graph" title="Show Column Chart 2 Axes" border="1" /></a></div>
--><div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="DoughnutCateg3('clientStatusDiv')"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showClientStatusPie('clientStatusDiv')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('clientStatusDiv','')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<div id='clientStatusDiv' style="width: 100%; height: 200px" align="center"></div>
</div>
<!-- Category wise Ticket Status Div End for particular Department-->
</div>




<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;" id="hod_catg_status">
<!-- Category Dashboard Start -->
<div id="clientStatusDiv_graph">
<div class="headdingtest">Offering w.r.t Client</div>
<!--<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refreshOffWrtClientDiv('offWrtClient');"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
--><div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise3('clientStatusDiv_graph',500,900,'Category Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showData('offWrtClient','')"><img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" /></a></div>
<div class="clear"></div>
<div id='offWrtClient' style="width: 100%; height: 200px" align="center"></div>
</div>
<!-- Category wise Ticket Status Div End for particular Department-->
</div>

<div class="contentdiv-smallnewHDMDash" style="overflow: hidden;" id="hod_catg_status">
<!-- Category Dashboard Start -->
<div id="clientSourceDiv_graph">
<div class="headdingtest">Client Activity Source</div>
<!--<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showClientBirthdayTable('clientSourceDiv');"><img src="images/refresh-icon.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
--><div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="beforeMaximise4('clientSourceDiv_graph',500,900,'Category Analysis','G')"><img src="images/maximize-icon.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="show2ndblockpie('clientSourceDiv')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div id='clientSourceDiv' style="width: 100%; height: 200px" align="center"></div>
</div>
<!-- Category wise Ticket Status Div End for particular Department-->
</div>


<div class="contentdiv-small">
<div>
<div id="ann12" style="display: block">
<div class="headding"><h3>Anniversary</h3></div>
<!--<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showclientAnniversaryTable('clientAnniversaryDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
--><div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showclientAnniversaryTableMax('myclickdialogTable');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<INPUT TYPE="BUTTON" VALUE="Birthday" ONCLICK="button1()" style="  margin-left: 145px;margin-top: 9px;">
<div class="clear"></div>
<div id="clientAnniversaryDiv">
<div id="clientBirthdayDiv">
					
</div>
</div>
</div>
<div id="bir12" style="display: none">
	
<div class="headding"><h3>Birthday</h3></div>
<INPUT TYPE="BUTTON" VALUE="Anniversary" ONCLICK="button2()" style="   margin-left: 145px;margin-top: 9px;">
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showClientBirthdayTable('clientBirthdayDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showClientBirthdayTableMax('myclickdialogTable');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="clear"></div>
<div id="clientBirthdayDiv">
					
</div>
</div>
</div>

</div>



</div>
</div>
<div id="StaffAndLocationTab"></div>
</body>
</html>