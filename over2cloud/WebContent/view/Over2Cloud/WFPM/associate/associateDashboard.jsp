<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<s:url var="chartDataUrl" action="json-chart-data"/>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/associate/associateDashboard.js"></script>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>

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

<STYLE type="text/css">
a.label:hover,
a.label:focus {
  color: #fff;
  text-decoration: none;
  cursor: pointer;
}
.label-default {
  background-color: #777;
}
.label-default[href]:hover,
.label-default[href]:focus {
  background-color: #5e5e5e;
}
 .label {
    border: 1px solid #000;
  }
  .label {
  display: inline;
  padding: .2em .6em .3em;
  font-size: 75%;
  font-weight: bold;
  line-height: 1;
  color: #fff;
  text-align: center;
  white-space: nowrap;
  vertical-align: baseline;
  border-radius: .25em;
}
.label-success {
  background-color: #5cb85c;
}
.label-info {
  background-color: #5bc0de;
}
.label-primary {
  background-color: #428bca;
}
</STYLE>

</head>
<body>



<sj:dialog
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
         
         <div id='jxchartOut' style="width: 100%;height:370px;display: none;margin-top:10px; " align="center"></div>
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
         
         <div id='jxchartOutID' style="width: 100%;height:370px;display: none;margin-top:10px; " align="center"></div>
</sj:dialog>

<sj:dialog 
    	id="myclickdialog" 
    	autoOpen="false" 
    	modal="true" 
    	title="Data"
		width="550"
		height="550"
    />
<sj:dialog 
    	id="myclickdialogTable" 
    	autoOpen="false" 
    	modal="true" 
    	title="Data"
    	width="550"
		height="550"
    />
    
    
    
    
    
    <sj:dialog 
    	id="mySourceMaxViewDialog" 
    	autoOpen="false" 
    	modal="true" 
    	title="Data"
    	width="550"
		height="550"
    >
    	<div id="associateSourceFullviewDiv"></div>
    </sj:dialog>
    
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
    

<div class="middle-content">
<!-- 1 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style="height: 250px;">
<div id="catg_graph">
<div class="headding"><h3>Associate Status</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="refreshAssociateDiv('associateDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="statusdonutpie('associateDiv')"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="maximizeAssociateDiv('catg_graph',500,900,'Category Analysis');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showAssociateTable('associateDiv','','');" src="images/table-icon.jpg" width="16" height="16" alt="Table" title="Table" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showAssociatePie('associateDiv');" src="images/pie_chart_icon.jpg" width="16" height="16" alt="Pie" title="Pie" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 5px 0;"><a href="#" onclick="showBarChart('associateDiv')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph" title="Show Bar Graph" border="1" /></a>
</div>	
<div class="clear"></div>
	<div id="associateDiv" style="width: 100%; height: 200px" align="center">
	
	</div>
	
	</div>
</div>

<!-- 2 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style="height: 250px;">
<div id="catg_graph">
<div class="headding"><h3>Activity</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="refreshAssociateActivityDiv('associateActivityDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="activitydonutpie('associateActivityDiv')"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="maximizeAssociateDiv('catg_graph',500,900,'Category Analysis');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showAssociateActivityTable('associateActivityDiv','','');" src="images/table-icon.jpg" width="16" height="16" alt="Table" title="Table" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 5px 0;"><a href="#" onclick="showActivityBarChart('associateActivityDiv')"><img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph" title="Show Bar Graph" border="1" /></a>
</div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showAssociateActivityPie('associateActivityDiv');" src="images/pie_chart_icon.jpg" width="16" height="16" alt="Pie" title="Pie" /></a></div>
<div class="clear"></div>
	<div id="associateActivityDiv" style="width: 100%; height: 200px" align="center">
	
	</div>
	</div>
</div>

<!-- 3 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style="height: 250px;">
<div id="catg_graph">
<div class="headding"><h3>Associate At Status</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="refreshAssociateStatusDiv('associateStatusDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="associateStatusdonutpie('associateStatusDiv')"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="maximizeAssociateDiv('catg_graph',500,900,'Category Analysis');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showAssociateStatusTable('associateStatusDiv','','');" src="images/table-icon.jpg" width="16" height="16" alt="Table" title="Table" /></a></div>
 <div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showAssociateStatusPie('associateStatusDiv');" src="images/pie_chart_icon.jpg" width="16" height="16" alt="Pie" title="Pie" /></a></div>
<div class="clear"></div>
	<div id="associateStatusDiv" style="width: 100%; height: 200px" align="center">
	</div>
	</div>
</div>

<!-- 4 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style="height: 250px;">

<div class="headding"><h3>Offering w.r.t Associate</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="refreshOffWrtAssoDiv('offWrtAsso');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="maximizeOffWrtAssoDiv('myclickdialog','true','450','450');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showOffWrtAssoTable('offWrtAsso');" src="images/table-icon.jpg" width="16" height="16" alt="Table" title="Table" /></a></div>
<!-- <div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showOffWrtAssoPie('offWrtAsso');" src="images/pie_chart_icon.jpg" width="16" height="16" alt="Pie" title="Pie" /></a></div>
-->
<div class="clear"></div>
	<div id="offWrtAsso">

	</div>
</div>



<!-- 5 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style="height: 250px;">

<div id="catg_graph">

<div class="headding"><h3>Associate Activity Source</h3></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="associateSourcedonutpie('associateSourceDiv')"><img src="images/donut-chart.png" width="15" height="15" alt="Get Data" title="Show Doughnut Chart" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="maximizeAssociateDiv('catg_graph',500,900,'Category Analysis');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#"><img onclick="showAssociateActivitySource('associateSourceDiv');" src="images/Pie-chart-icon.png" width="16" height="16" alt="Pie" title="Pie" /></a></div>				
<div class="clear"></div>
	<div id="associateSourceDiv" style="width: 100%; height: 200px" align="center">

	</div>
	
	</div>
</div>








<!-- 6 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style="height: 250px;">
<div>
<div id="ann12" style="display: block">
					<div class="headding"><h3>Anniversary</h3></div>
					
					<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
					
					
						<img onclick="showAssociateAnniversaryTable('associateAnniversaryDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
					<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
						<img onclick="showAssociateAnniversaryTableMax('myclickdialogTable');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
						<INPUT TYPE="BUTTON" VALUE="Birthday" ONCLICK="button1()" style="  margin-left: 145px;margin-top: 9px;">
						
					
					
					<div class="clear"></div>
						<div id="associateAnniversaryDiv">
						
						</div>
	</div>
	<div id="bir12" style="display: none">
	
						<div class="headding"><h3>Birthday</h3></div>
						<INPUT TYPE="BUTTON" VALUE="Anniversary" ONCLICK="button2()" style="   margin-left: 145px;margin-top: 9px;">
					<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
						<img onclick="showAssociateBirthdayTable('associateBirthdayDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
					<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
						<img onclick="showAssociateBirthdayTableMax('myclickdialogTable');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
					<div class="clear"></div>
						<div id="associateBirthdayDiv">
						
						</div>
	</div>
</div>

</div>

<!--  ------------------------------------------------------------------------------------->
<!-- <div class="contentdiv-small">

<div id="ann12" style="display: block">

	<div class="headding"><h3>Birthday</h3></div>
	<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
		<img onclick="showAssociateBirthdayTable('associateBirthdayDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
	<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
		<img onclick="showAssociateBirthdayTableMax('myclickdialogTable');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
		<INPUT TYPE="BUTTON" VALUE="Birthday" ONCLICK="button1()" style="  margin-left: 145px;margin-top: 9px;">
	<div class="clear"></div>
		<div id="associateBirthdayDiv">
	
		</div>
	</div>
</div>	



<div id="bir12" style="display: none">
	<div class="headding"><h3>Anniversary</h3></div>
	<INPUT TYPE="BUTTON" VALUE="Anniversary" ONCLICK="button2()" style="   margin-left: 145px;margin-top: 9px;">
	<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
		<img onclick="showAssociateAnniversaryTable('associateAnniversaryDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
	<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
		<img onclick="showAssociateAnniversaryTableMax('myclickdialogTable');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
	<div class="clear"></div>
		<div id="associateAnniversaryDiv">
	
		</div>
</div>

</div> -->
<SCRIPT type="text/javascript">
showAssociatePie('associateDiv');
showAssociateActivityPie('associateActivityDiv');
showAssociateStatusPie('associateStatusDiv');
//showAssociateStatusTable('associateStatusDiv');
//showOffWrtAssoPie('offWrtAsso');
showAssociateActivitySource('associateSourceDiv');
showOffWrtAssoTable('offWrtAsso');
showAssociateBirthdayTable('associateBirthdayDiv');
showAssociateAnniversaryTable('associateAnniversaryDiv');
</SCRIPT>
</body>
</html>
