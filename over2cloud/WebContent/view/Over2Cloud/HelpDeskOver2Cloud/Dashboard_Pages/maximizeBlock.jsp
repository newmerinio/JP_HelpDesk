<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
var  sampleData =null;
$.ajax({
    type : "post",
    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
    type : "json",
    success : function(data) {
    	sampleData = data;
    	var settings = {
    	    	
    			title: '',
                description: 'For All Department',
                enableAnimations: true,
                showLegend: true,
                padding: { left: 5, top: 5, right: 5, bottom: 5 },
                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'dept',
                        showTickMarks: true,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        showGridLines: false,
                        gridLinesInterval: 1,
                        gridLinesColor: '#888888',
                        axisSize: 'auto'
                    },
                colorScheme: 'scheme01',
                seriesGroups:
                    [
                        {
                            type: 'stackedcolumn',
                            columnsGapPercent: 100,
                            seriesGapPercent: 5,
                            valueAxis:
                            {
                                minValue: 0,
                                displayValueAxis: true,
                                description: 'Ticket Counter',
                                axisSize: 'auto',
                                tickMarksColor: '#888888',
                                gridLinesColor: '#777777'
                            },
                            series: [
                                    { dataField: 'Pending', displayText: 'Pending', color:'#FF0033' },
	                                    { dataField: 'HighPriority', displayText: 'High Priority', color:'#003399'  },
	                                    { dataField: 'Snooze', displayText: 'Snooze', color:'#A0A0A0'  },
	                                    { dataField: 'Resolved', displayText: 'Resolved', color:'#009933'  }
                                ]
                        }
                    ]
            };
    	    
    	// setup the chart
        $('#jqxChart1').jqxChart(settings);
        $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
        $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
        var refreshChart = function () {
            $('#jqxChart1').jqxChart({ enableAnimations: false });
            $('#jqxChart1').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis").on('change', function (event) {
            var groups = $('#jqxChart1').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis").on('change', function (event) {
            var categoryAxis = $('#jqxChart1').jqxChart('categoryAxis');
            categoryAxis.flip = event.args.checked;
            refreshChart();
        });
    	
},
   error: function() {
        alert("error");
    }
 });

$.ajax({
    type : "post",
    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action",
    type : "json",
    success : function(data) {
    	sampleData = data;
    	var settings = {
    	    	
    			title: ' ',
                description: ' ',
                enableAnimations: true,
                showLegend: true,
                padding: { left: 5, top: 5, right: 5, bottom: 5 },
                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'Level',
                        showTickMarks: true,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        showGridLines: false,
                        gridLinesInterval: 1,
                        gridLinesColor: '#888888',
                        axisSize: 'auto'
                    },
                colorScheme: 'scheme01',
                seriesGroups:
                    [
                        {
                            type: 'stackedcolumn',
                            columnsGapPercent: 100,
                            seriesGapPercent: 5,
                            valueAxis:
                            {
                                minValue: 0,
                                displayValueAxis: true,
                                description: 'Ticket Counter',
                                axisSize: 'auto',
                                tickMarksColor: '#888888',
                                gridLinesColor: '#777777'
                            },
                            series: [
										{ dataField: 'Pending', displayText: 'Pending', color:'#FF0033' },
										{ dataField: 'HighPriority', displayText: 'High Priority', color:'#003399'  },
										{ dataField: 'Snooze', displayText: 'Snooze', color:'#A0A0A0'  },
										{ dataField: 'Resolved', displayText: 'Resolved', color:'#009933'  }
                                ]
                        }
                    ]
            };
    	    
    	// setup the chart
        $('#levelChart1').jqxChart(settings);
        $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
        $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
        var refreshChart = function () {
            $('#levelChart1').jqxChart({ enableAnimations: false });
            $('#levelChart1').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis").on('change', function (event) {
            var groups = $('#levelChart1').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis").on('change', function (event) {
            var categoryAxis = $('#levelChart1').jqxChart('categoryAxis');
            categoryAxis.flip = event.args.checked;
            refreshChart();
        });
    	
},
   error: function() {
        alert("error");
    }
 });
 
 //Edited By Kamlesh 4-10-2014
 //Two Axes Column Chart
$.ajax({
    type : "post",
    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
    type : "json",
    success : function(data) {
    	sampleData = data;
    	var settings = {
    	    	
    			title: '',
                description: 'For All Departments',
                enableAnimations: true,
                showLegend: true,
                padding: { left: 5, top: 5, right: 5, bottom: 5 },
                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
                source: sampleData,
                categoryAxis:
                    {
                        text: 'Category Axis',
                        textRotationAngle: 0,
                        dataField: 'dept',
                        showTickMarks: true,
                        tickMarksInterval: 1,
                        tickMarksColor: '#888888',
                        unitInterval: 1,
                        showGridLines: false,
                        gridLinesInterval: 1,
                        gridLinesColor: '#888888',
                        axisSize: 'auto'
                    },
                colorScheme: 'scheme01',
                seriesGroups:
                    [
                        {
                            type: 'column',
                            columnsGapPercent: 100,
                            seriesGapPercent: 5,
                            valueAxis:
                            {
                                minValue: 0,
                                displayValueAxis: true,
                                description: 'Ticket Counter',
                                axisSize: 'auto',
                                tickMarksColor: '#888888',
                                gridLinesColor: '#777777'
                            },
                            series: [
								{ dataField: 'Pending', displayText: 'Pending', color:'#FF0033' },
								{ dataField: 'HighPriority', displayText: 'High Priority', color:'#003399'  },
								{ dataField: 'Snooze', displayText: 'Snooze', color:'#A0A0A0'  },
								{ dataField: 'Resolved', displayText: 'Resolved', color:'#009933'  }
                                ]
                        }
                    ]
            };
    	    
    	// setup the chart
        $('#jqxcolumnStatus1').jqxChart(settings);
        $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
        $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
        var refreshChart = function () {
            $('#jqxChart').jqxChart({ enableAnimations: false });
            $('#jqxChart').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis").on('change', function (event) {
            var groups = $('#jqxChart').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis").on('change', function (event) {
            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
            categoryAxis.flip = event.args.checked;
            refreshChart();
        });
    	
},
   error: function() {
        alert("error");
    }
 });
 
 //Line Chart
$.ajax({
	    type : "post",
	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
	    success : function(statusdata) {
	    	
	    	sampleData=statusdata;
	    	
	    var toolTipCustomFormatFn = function (key,value) {
      var data=statusdata[value];
        return                              '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
        									'<br /><b>Pending: </b>' +data.Pending+
        									'<br /><b>High Priority: </b>' +data.HighPriority+
        									'<br /><b>Snooze: </b>' +data.Snooze+
        									'<br /><b>Done: </b>' +data.Resolved+
        									'</DIV>';
    };

	    var settings = {
              title: " ",
              description: "For All Departments",
              padding: { left: 5, top: 5, right: 5, bottom: 5 },
              titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
              source: sampleData,
            enableAnimations: true,
              
              categoryAxis:
                  {	
              		textRotationAngle:0,
                      dataField: 'dept',
                     
                      showGridLines: false,
                  },
              colorScheme: 'scheme01',
              seriesGroups:
                  [                    
                      {
                          type: 'line',
                        showLabels: true,
                         /*  formatSettings:
                          {
                              prefix: 'Time ',
                              decimalPlaces: 0,
                              sufix: ' min'
                          } */
                        toolTipFormatFunction: toolTipCustomFormatFn,

                          valueAxis:
                          {
                          	minValue: 0,
                            displayValueAxis: true,
                            description: 'Ticket Counter',
                            axisSize: 'auto',
                          },
                          series: [
                                 
									{ dataField: 'Pending', displayText: 'Pending' },
									{ dataField: 'HighPriority', displayText: 'High Priority' },
									{ dataField: 'Snooze', displayText: 'Snooze' },
									{ dataField: 'Resolved', displayText: 'Resolved' }
									  	                        
                              ]
                      }
                  ]
          };
          
          $('#jqxLineStatus1').jqxChart(settings);
	    	
	},
	   error: function() {
            alert("error");
        }
	 });

 //Bubble Chart
 $.ajax({
		    type : "post",
			url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
		    type : "json",
		    success : function(data) {
		    	
		    	console.log(data);
		    	sampleData = data;
		    	 var settings = {
		                 title: ' ',
		                 description: 'For All Departments',
		                 enableAnimations: true,
		                 showLegend: true,
		                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
		                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
		                 source: sampleData,
		                 xAxis:
		                     {
		                         dataField: 'dept',
		                         valuesOnTicks: false
		                     },
		                 colorScheme: 'scheme01',
		                 seriesGroups:
		                     [
		                         {
		                             type: 'bubble',
		                             valueAxis:
		                             {
		                            	 	minValue: 0,
			                                displayValueAxis: true,
			                                axisSize: 'auto',
		                                 description: 'Ticket Counter'
		                                 
		                             },
		                             series: [
		                                     { dataField: 'Pending', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Pending',color:'#FF0033' },
		                                     { dataField: 'HighPriority', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'High Prority',color:'#003399' },
		                                     { dataField: 'Snooze', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Snooze',color:'#A0A0A0' },
		                                     { dataField: 'Resolved', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Resolved',color:'#009933' }
		                                     
		                                     ]
						           }              
		                     ]
		             };
		    	    
		    	// setup the chart
		    	 $('#bubbleStatus1').jqxChart(settings);
		        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
		        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
		        var refreshChart = function () {
		            $('#jqxChart').jqxChart({ enableAnimations: false });
		            $('#jqxChart').jqxChart('refresh');
		        }
		        // update greyScale values.
		        $("#FlipValueAxis").on('change', function (event) {
		            var groups = $('#jqxChart').jqxChart('seriesGroups');
		            groups[0].valueAxis.flip = event.args.checked;
		            refreshChart();
		        });
		        $("#FlipCategoryAxis").on('change', function (event) {
		            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
		            categoryAxis.flip = event.args.checked;
		            refreshChart();
		        });
		    	
		},
		   error: function() {
		        alert("error from jsonArray data ");
		    }
		 });
 
 //Pending Pie
 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
		    type : "json",
		    success : function(data) {
		    	
		    	
		    	sampleData = data;
		    	var total=0;
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Pending);
				}
	    		
		    	  var settings = {
		                  title: "Pending Ticket Status",
		                  description: "All Departments ",
		                  enableAnimations: true,
			              showLegend: true,
			              padding: { left: 5, top: 5, right: 5, bottom: 5 },
		                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
		                  source: sampleData,
		                  colorScheme: 'scheme02',
		                  seriesGroups:
		                      [
		                          {
		                              type: 'pie',
		                              showLabels: true,
		                              series:
		                                  [ 
		                                    { 
		                                    	dataField: 'Pending',
		                                    	displayText: 'dept',
		                                    	labelRadius: 200,
		                                    	initialAngle: 15,
		                                    	radius: 75,
		                                    	centerOffset: 0,
		                                    	formatFunction: function (value) {
		                                         	
		                                             if (isNaN(value))
		                                                 return value;
		                                             return parseFloat((value/total)*100).toFixed(1)+'%' ;
		                                         }
		                                    }
		                                    
		                                    
		                                  ]
		                          }
		                      ]
		              };
		    	// setup the chart
		    	  $('#1stPendingPie').jqxChart({ enableAnimations: true });
		        $('#1stPendingPie').jqxChart(settings);
		        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
		        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
		        var refreshChart = function () {
		            $('#jqxChart').jqxChart({ enableAnimations: false });
		            $('#jqxChart').jqxChart('refresh');
		        }
		        // update greyScale values.
		        $("#FlipValueAxis").on('change', function (event) {
		            var groups = $('#jqxChart').jqxChart('seriesGroups');
		            groups[0].valueAxis.flip = event.args.checked;
		            refreshChart();
		        });
		        $("#FlipCategoryAxis").on('change', function (event) {
		            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
		            categoryAxis.flip = event.args.checked;
		            refreshChart();
		        });
		    	
		},
		   error: function() {
		        alert("error from jsonArray data ");
		    }
		 });
		
</script>
<title>Insert title here</title>
</head>
<body>
<center>
<s:if test="maximizeDivBlock=='catg_graph'">
<sjc:chart
	        id="chartPie21111"
	        cssStyle="width: 400px; height: 400px; align: center;"
	        legendShow="false"
	        pie="true"
	        pieLabel="true"
	        cssClass="chartDataCss"
        >
<s:iterator value="%{graphCatgMap}">
<sjc:chartData
	        label="%{key}"
	        data="%{value}"
/>
</s:iterator>
</sjc:chart>
</s:if>
<s:elseif test="maximizeDivBlock=='catg_table'">
	<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="70%"   bgcolor="#F64D54" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp; Category</b></td>
		<td align="center" width="30%" bgcolor="#F64D54" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Counter</b></td>
	</tr>
</table>
<s:iterator id="rsCompl1dfr4245"  status="status" value="%{catgCountList}" >
<table border="1" width="100%" align="center">
	<tr>
	    <td align="left" width="70%"   bgcolor="#EDDBDA" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="%{feedback_catg}"/></b></td>
	<td align="center" width="30%" bgcolor="#EDDBDA" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{counter}"/></b></a></td>
	</tr>
	</table>
</s:iterator>
</s:elseif>
<s:elseif test="maximizeDivBlock=='level_graph'">
<div id='levelChart1' style="width: 100%; height: 200px" align="center"></div>
</s:elseif>
<s:elseif test="maximizeDivBlock=='level_table'">
<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="40%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp; Level</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;PN</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;HP</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;SN</b></td>
		<td align="center" width="15%" bgcolor="#25B0C4" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;RS</b></td>
	</tr>
</table>
<s:iterator id="rsCompl1dfrgcvxfzcvzdf"  status="status" value="%{leveldashObj.dashList}" >
	<table border="1" width="100%" align="center">
 	<tr>
 	    <td align="left" width="40%"   bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.level"/></b></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Pending','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.pc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','High Priority','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.hpc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Snooze','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.sc"/></b></a></td>
		<td align="center" width="15%" bgcolor="#D5EAED" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Resolved','H');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl1dfrgcvxfzcvzdf.rc"/></b></a></td>
		
 	</tr>
 	</table>
	</s:iterator>
</s:elseif>
<s:elseif test="maximizeDivBlock=='ticket_table'">
<div style="height: auto; margin-bottom: 10px;">
<table border="1" width="100%" align="center">
    <tr>
    <s:if test="hodFlag">
		<td align="center" width="38%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Sub Dept</b></td>
	</s:if>
	<s:elseif test="mgmtFlag">
	<td align="center" width="38%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Department</b></td>
	</s:elseif>
	<s:elseif test="normalFlag">
	<td align="center" width="38%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Sub Dept</b></td>
	</s:elseif>
		<td align="center" width="14%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;PN</b></td>
		<td align="center" width="15%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;HP</b></td>
		<td align="center" width="14%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;SN</b></td>
		<td align="center" width="19%" bgcolor="#138F6A" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;RS</b></td>
	</tr>
</table>
<div style="overflow:auto; height:200px;">
<s:iterator id="rsCompl"  status="status" value="%{dashObj.dashList}" >
	<table border="1" width="100%" align="center">
	 	<tr>
	 	    <td align="left"   width="38%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="#rsCompl.deptName"/></b></td>
	 	    <td align="center" width="14%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Pending','T','dataFor','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.pc"/></b></a></td>
			<td align="center" width="15%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','High Priority','T','dataFor','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.hpc"/></b></a></td>
			<td align="center" width="14%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Snooze','T','dataFor','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.sc"/></b></a></td>
			<td align="center" width="19%"   bgcolor="#C3E8D1" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" onclick="getData('<s:property value="#rsCompl.id"/>','Resolved','T','dataFor','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="#rsCompl.rc"/></b></a></td>
	 	</tr>
 	</table>
</s:iterator>
</div>
</div>
</s:elseif>
<s:elseif test="maximizeDivBlock=='ticket_graph'">
<div id='jqxChart1' style="width: 100%; height: 200px" align="center"></div>
</s:elseif>
<!-- // Edited bt Kamlesh 4-10-2014 -->
<s:elseif test="maximizeDivBlock=='ticket_columgraph'">
<div id='jqxcolumnStatus1' style="width: 100%; height: 200px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='ticket_Linegraph'">
<div id='jqxLineStatus1' style="width: 100%; height: 200px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='ticket_Bubblegraph'">
<div id='bubbleStatus1' style="width: 100%; height: 200px" align="center"></div>
</s:elseif>

<s:elseif test="maximizeDivBlock=='ticket_PendingPiegraph'">
<div id='1stPendingPie' style="width: 100%; height: 300px" align="center"></div>
</s:elseif>
</center>
</body>
</html>