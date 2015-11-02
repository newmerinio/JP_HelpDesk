<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
<script type="text/javascript">
function showDataValue()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/WFPM/report/DSR/beforeOfferingAutofill.action",
		success : function(data){
			$("#data_part").html(data);
		},
		error   : function(){
			alert("error");
		}
	});
}
</script>
<script type="text/javascript">
function initializeGraph()
{
	var currentMonthValue = $("#fromDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/report/DSR/offeringAutofillGraph.action?currentMonthValue="+currentMonthValue,
	    type : "json",
	    success : function(data) {
		kpiData = data;
	    	var settings = {
	
	    			title: 'Offering Target Vs Achievement',
	                description: '',
	                enableAnimations: true,
	                showLegend: true,
	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                titlePadding: { left: 50, top: 0, right: 0, bottom: 10 },
	                source: kpiData,
	                categoryAxis:
	                    {
	                        text: 'Offering',
	                        textRotationAngle: 0,
	                        dataField: 'Offering',
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
	                            columnsGapPercent: 50,
	                            seriesGapPercent: 0,
	                            valueAxis:
	                            {
	                                minValue: 0,
	                                displayValueAxis: true,
	                                //description: 'Target V/S Acheivement',
	                                description: '',
	                                axisSize: 'auto',
	                                tickMarksColor: '#888888',
	                                gridLinesColor: '#777777'
	                            },
	                            series: [
	                                     { dataField: 'totalTarget', displayText: 'Target'},
	                                     { dataField: 'acheivement', displayText: 'Acheivement'}
	                                    
	                                 ]
	                        }
	                    ]
	            };
	    	    
	    	// setup the chart
	        $('#jqxChart').jqxChart(settings);
	        $("#FlipValueAxis2").jqxCheckBox({ width: 120,  checked: false });
	        $("#FlipCategoryAxis2").jqxCheckBox({ width: 120,  checked: false });
	        var refreshChart = function () {
	            $('#jqxChart').jqxChart({ enableAnimations: false });
	            $('#jqxChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis2").on('change', function (event) {
	            var groups = $('#jqxChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis2").on('change', function (event) {
	            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function getgraph4ParticularOffering(offering,date)
{
	var currentMonthValue = $("#"+date).val();
	var offeringId = $("#"+offering).val();
	
	if (offeringId=='-1') 
	{
		$("#graphValue").show();
		$("#graphDataKPI").hide();
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/report/DSR/offeringAutofillGraph.action?currentMonthValue="+currentMonthValue,
		    type : "json",
		    success : function(data) {
			kpiData = data;
		    	var settings = {

		    			title: 'Offering Target Vs Achievement',
		                description: '',
		                enableAnimations: true,
		                showLegend: true,
		                padding: { left: 5, top: 5, right: 5, bottom: 5 },
		                titlePadding: { left: 50, top: 0, right: 0, bottom: 10 },
		                source: kpiData,
		                categoryAxis:
	                    {
	                        text: 'Offering',
	                        textRotationAngle: 0,
	                        dataField: 'Offering',
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
	                            columnsGapPercent: 50,
	                            seriesGapPercent: 0,
	                            valueAxis:
	                            {
	                                minValue: 0,
	                                displayValueAxis: true,
	                                //description: 'Target V/S Acheivement',
	                                description: '',
	                                axisSize: 'auto',
	                                tickMarksColor: '#888888',
	                                gridLinesColor: '#777777'
	                            },
	                            series: 
                            	[
                                     { dataField: 'totalTarget', displayText: 'Target'},
                                     { dataField: 'acheivement', displayText: 'Acheivement'}
                                    
                                ]
	                        }
	                    ]
		            };
		    	    
		    	// setup the chart
		        $('#jqxChart').jqxChart(settings);
		        $("#FlipValueAxis2").jqxCheckBox({ width: 120,  checked: false });
		        $("#FlipCategoryAxis2").jqxCheckBox({ width: 120,  checked: false });
		        var refreshChart = function () {
		            $('#jqxChart').jqxChart({ enableAnimations: false });
		            $('#jqxChart').jqxChart('refresh');
		        }
		        // update greyScale values.
		        $("#FlipValueAxis2").on('change', function (event) {
		            var groups = $('#jqxChart').jqxChart('seriesGroups');
		            groups[0].valueAxis.flip = event.args.checked;
		            refreshChart();
		        });
		        $("#FlipCategoryAxis2").on('change', function (event) {
		            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
		            categoryAxis.flip = event.args.checked;
		            refreshChart();
		        });
		   },
		   error: function() {
		        alert("error");
		    }
		 });
	} 
	else 
	{
		$("#graphDataKPI").show();
		$("#graphValue").hide();
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/report/DSR/offeringAutofillGraphPerOffering.action?offeringId="+offeringId+"&currentMonthValue="+currentMonthValue,
		    type : "json",
		    success : function(data) {
			kpiData1 = data;
		          // prepare jqxChart settings
		          var settings = {
		              title: "Per Day Offering Acheivement",
		              description: "",
		              enableAnimations: true,
		              showLegend: true,
		              padding: { left: 10, top: 5, right: 10, bottom: 5 },
		              titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
		              source: kpiData1,
		              categoryAxis:
	                  {
	                      text: 'Category Axis',
	                      textRotationAngle: 0,
	                      dataField: 'day',
	                      showTickMarks: true,
	                      valuesOnTicks: false,
	                      tickMarksInterval: 1,
	                      tickMarksColor: '#888888',
	                      unitInterval: 1,
	                      gridLinesInterval: 1,
	                      gridLinesColor: '#888888',
	                      axisSize: 'auto'
	                  },
		              colorScheme: 'scheme05',
		              seriesGroups:
	                  [
	                      {
	                          type: 'line',
	                          showLabels: true,
	                          valueAxis:
	                          {
	                              minValue: 0,
	                              description: 'Acheivement',
	                              axisSize: 'auto',
	                              tickMarksColor: '#888888'
	                          },
	                          series: 
                        	  [
                                  { dataField: 'count', displayText: 'Days', symbolType: 'square'}
                              ]
	                      }
	                  ]
		          };
		          // setup the chart
		          $('#chartContainer').jqxChart(settings);
		    
		},
		error: function() {
		     alert("error");
		 }
		});
	}
}

function fetchAchievedOfferingForMonth(date) //date=dd:mm:yyyy
{
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/WFPM/report/DSR/fetchAchievedOfferingForMonth.action",
		data: "currentMonthValue="+date,
		success : function(data){
			$('#offeringSelect option').remove();
			$('#offeringSelect').append($("<option></option>").attr("value",-1).text('All Offering'));
			$(data.jsonArrayOffering).each(function(index)
			{
			   $('#offeringSelect').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		},
		error   : function(){
			alert("error");
		}
	});
}

</script>
</head>
<body>
<div class="list-icon">
		<div class="head">Offering Report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">View</div>
	</div>
	<div class="clear"></div>
<div class="listviewBorder" style="margin: 10px; width: 98%;" align="center">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
		<sj:datepicker cssStyle="height: 16px; width: 79px;" onchange="fetchAchievedOfferingForMonth(this.value); getgraph4ParticularOffering('offeringSelect','fromDate');" cssClass="button" 
			id="fromDate" name="fromDate" size="20" maxDate="0" value="%{currentMonthValue}" readonly="true"   yearRange="2013:2050" showOn="focus" 
			displayFormat="M-yy" Placeholder="Select Date" changeMonth="true" changeYear="true" />
		
		<s:select  
                   	id					=		"offeringSelect"
                   	name				=		"offeringSelect"
                   	list				=		"mapOffering"
                   	headerKey			=		"-1"
                   	headerValue			=		"All Offering" 
					cssClass			=		"button"
               		theme				=		"simple"
               		cssStyle			=		"height: 28PX;width: 184px;"
               		onchange			=		"getgraph4ParticularOffering('offeringSelect','fromDate');"
                         	>
        </s:select>
		
		</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	  <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDataValue()"><img src="images/WFPM/table-icon.jpg" width="31px" height="24px" alt="Table" title=Table" /></a></div>		
	</td>   
		</tr></tbody></table>
    </div>
    </div>
	<div id="graphValue" > 
	      <div id='jqxChart' style="height: 450px" align="center"></div>
	</div>
	
	<div id="graphDataKPI" style="display: none;"> 
		<div id='chartContainer' style="height:450px;" align="center"></div>
	</div>	
</div>
</body>
<SCRIPT type="text/javascript">
initializeGraph();
</SCRIPT>
</html>