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

<script type="text/javascript" src="js/lead/leadDashboard.js"></script>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
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
    	id="myclickdialog" 
    	autoOpen="false" 
    	modal="true" 
    	title="Data"
		width="550"
		height="550"
    >
    
    </sj:dialog>
    
    
    
    <sj:dialog 
    	id="piefactorzoom" 
    	autoOpen="false" 
    	modal="true" 
    	title="Data"
		width="550"
		height="550"
    >
    <div id="leadfactorpiediv"></div>	
    </sj:dialog>
    
    
    <sj:dialog 
    	id="zoompiechart" 
    	autoOpen="false" 
    	modal="true" 
    	title="Data"
		width="750"
		height="500"
    >
    	<div id="displaypiechart"></div>
    </sj:dialog>
    
    
<sj:dialog 
    	id="myclickdialogTable" 
    	autoOpen="false" 
    	modal="true" 
    	title="Data"
    	width="550"
		height="550"
    >
    	
    </sj:dialog>

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
        <a href="#" >  <span class="label label-default" onclick="showBarFactor('stackedcolumn');">Stacked-Column Chart</span></a>
        <a href="#">  <span class="label label-info" onclick="showBarFactor('column');">2 Axes-Column Chart</span></a>
        <a href="#">  <span class="label label-primary" onclick="showBarFactor('line');">Line Chart</span></a>
         <a href="#">  <span class="label label-success" onclick="showBarFactor('bubble');">Bubble Chart</span></a>
         
         <div id='jxchartOutID' style="width: 100%;height:370px;display: none;margin-top:10px; " align="center"></div>
</sj:dialog>


<div class="middle-content">
<!-- 1 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style="width: 545px;margin-left: 59px; height: 350px;">
<div class="headding"><h3>Lead Status</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="refreshLeadStatusDiv('leadStatusDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="maximizeLeadStatusDiv('zoompiechart','displaypiechart');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showLeadStatusTable('leadStatusDiv');" src="images/table-icon.jpg" width="16" height="16" alt="Table" title="Table" /></a></div>

<div style="float:right; width:auto; padding:0px 4px 5px 0;">
	<a href="#" onclick="showBarChart('stackedcolumn')">
	<img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph" title="Show Bar Graph" border="1" />
	</a>
	</div>
	
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showLeadStatusPie('leadStatusDiv');" src="images/pie_chart_icon.jpg" width="16" height="16" alt="Pie" title="Pie" /></a></div>
<div class="clear"></div>
	<div id="leadStatusDiv" class="leadstatuszoom">
	</div>
</div>

<!-- 2 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style="width: 545px; height: 350px;">
<div class="headding"><h3>Factors Effects Marketing</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="refreshLeadFactorDiv('leadFactorDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="maximizeLeadFactorDiv('piefactorzoom','leadfactorpiediv');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showLeadFactorTable('leadFactorDiv');" src="images/table-icon.jpg" width="16" height="16" alt="Table" title="Table" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 5px 0;">
	<a href="#" onclick="showActivityBarChart('stackedcolumn')">
	<img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph" title="Show Bar Graph" border="1" />
	</a>
</div>
	
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showLeadFactorPie('leadFactorDiv');" src="images/pie_chart_icon.jpg" width="16" height="16" alt="Pie" title="Pie" /></a></div>
<div class="clear"></div>
	<div id="leadFactorDiv">
	</div>
<!-- 	<br><br><center>No Data</center> -->
</div>

<!-- 3 -----------------------------------------------------------------------------------
<div class="contentdiv-small">
<div class="headding"><h3>Lead Conversion Status</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="refreshLeadFactorDiv('leadFactorDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="maximizeLeadFactorDiv('myclickdialog','true','450','450','<s:property value="#session.uName"/>');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showLeadFactorTable('leadFactorDiv');" src="images/table-icon.jpg" width="16" height="16" alt="Table" title="Table" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 5px 0;">
	<a href="#" onclick="showBarFactor('stackedcolumn')">
	<img src="images/chart-bar-stacked-icon.png" width="20" height="15" alt="Show Bar Graph" title="Show Bar Graph" border="1" />
	</a>
</div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showLeadFactorPie('leadFactorDiv');" src="images/pie_chart_icon.jpg" width="16" height="16" alt="Pie" title="Pie" /></a></div>
<div class="clear"></div>
	<div id="leadFactorDiv">
	</div>
 	<br><br><center>No Data</center> 
</div>

--><!-- 4 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style=" margin-bottom: 1px; margin-left: 58px; width: 545px;">

<div class="headding"><h3>Birthday</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showClientBirthdayTable('clientBirthdayDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showClientBirthdayTableMax('myclickdialogTable');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="clear"></div>
	<div id="clientBirthdayDiv">
	</div>
	<!-- <br><br><center>No Data</center> -->
</div>

<!-- 5 ------------------------------------------------------------------------------------->
<div class="contentdiv-small" style=" width: 545px;">

<div class="headding"><h3>Anniversary</h3></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showclientAnniversaryTable('clientAnniversaryDiv');" src="images/refresh-icon.jpg" width="16" height="16" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:7px 4px 0 0;"><a href="#">
	<img onclick="showclientAnniversaryTableMax('myclickdialogTable');" src="images/maximize-icon.jpg" width="16" height="16" alt="Maximize" title="Maximize" /></a></div>
<div class="clear"></div>
	<div id="clientAnniversaryDiv">

	</div>
	<!-- <br><br><center>No Data</center> -->
</div>

<!-- 6 -----------------------------------------------------------------------------------
<div class="contentdiv-small">
	<br><br><center>No Data</center>
</div>

--></div>
<SCRIPT type="text/javascript">
showLeadStatusPie('leadStatusDiv');
showLeadFactorPie('leadFactorDiv');
showClientBirthdayTable('clientBirthdayDiv');
showclientAnniversaryTable('clientAnniversaryDiv');
</SCRIPT>
</body>
</html>