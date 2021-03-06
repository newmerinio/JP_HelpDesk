<!--<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:url var="chartDataUrl" action="json-chart-data"/>

<sjc:chart
		    	id="chartPieClient%{legendShow}"
		    	cssStyle="width: %{width}px; height: %{height}px; margin: auto;"
		    	legendShow="%{legendShow}" 
		    	pie="true"
		    	pieLabel="true" 
		    	legendPosition="sw"
		    >
		    	<s:iterator value="%{clientMap}">
			    	<sjc:chartData
			    		label="%{key}"
			    		data="%{value}" 
			    	/>
		    	</s:iterator>
	    </sjc:chart>-->
	    
	    <!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'></title>
    
    
    <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
    

   
    <script type="text/javascript">
        $(document).ready(function () {
        	$.ajax({
        	    type : "post",
        	    url : "/over2cloud/view/Over2Cloud/wfpm/client/1stPieJsonData.action",
        	    type : "json",
        	    success : function(data) {
        	    	console.log("New pie dataaaaaa");
        	    	console.log(data);
        	    	sampleData = data;
        	    	 // prepare jqxChart settings
                        var settings = {
	                  title: "Total No. of issues - "+data[0].issue+" out of which Source %",
	                  description: "",
	                  enableAnimations: true,
	              showLegend: true,
	              padding: { left: 0, top: 0, right: 0, bottom: 0 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme06',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    {
	                                    	dataField: 'percent',
	                                    	displayText: 'source',
	                                    	labelRadius: 100,
	                                    	initialAngle: 2,
	                                    	radius: 70,
	                                    	centerOffset: 0,
											formatFunction: function (value) {
	                                        	
	                                            if (isNaN(value))
	                                                return value;
	                                            return value+'%' ;
	                                        }
	                                    	
	                                    }
	                                    
	                                    
	                                  ]
	                          }
	                      ]
	              };
                    // create the chart
                    $('#chartContainer21').jqxChart(settings);
                    // get the chart's instance
                      //var chart = $('#chartContainer2').jqxChart('getInstance');
                    // color scheme drop down
                /*     var colorsSchemesList = ["scheme01", "scheme02", "scheme03", "scheme04", "scheme05", "scheme06", "scheme07", "scheme08"];
                    $("#dropDownColors2").jqxDropDownList({ source: colorsSchemesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 100 });
                    $('#dropDownColors2').on('change', function (event) {
                        var value = event.args.item.value;
                        chart.colorScheme = value;
                        chart.update();
                    }); */
                   
                   /*  // series type drop down
                    var seriesList = ["splinearea", "spline", "column", "scatter", "stackedcolumn", "stackedsplinearea", "stackedspline"];
                    $("#dropDownSeries2").jqxDropDownList({ source: seriesList, selectedIndex: 4, width: '200', height: '25', dropDownHeight: 100 });
                    $('#dropDownSeries2').on('select', function (event) {
                        var args = event.args;
                       // alert(JSON.stringify(args));
                        if (args) {
                            var value = args.item.value;
                            var isLine = value.indexOf('line') != -1;
                            var isArea = value.indexOf('area') != -1;
                            //chart.categoryAxis.valuesOnTicks = isLine || isArea;
                            var group = chart.seriesGroups[0];
                            group.series[0].opacity = group.series[0].opacity = isArea ? 0.7 : 1;
                            group.series[0].lineWidth = group.series[0].lineWidth = isLine ? 2 : 1;
                            chart.seriesGroups[0].type = value;
                            chart.update();
                        }
                    }); */
                   
             
        	    	
        	},
        	   error: function() {
        	        alert("error");
        	    }
        	 });
        });
           
    </script>
</head>
<body class='default'>

        <div id='chartContainer21' style="width: 100%; height: 200px">
        </div>
    
   
</body>
</html>