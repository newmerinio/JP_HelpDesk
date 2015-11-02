<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'></title>
    
    
    <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
   <script type="text/javascript" src="jqwidgets/jqxslider.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdropdownlist.js"></script>
    

   
    <script type="text/javascript">
        $(document).ready(function () {
        	$.ajax({
        	    type : "post",
        	    url : "view/Over2Cloud/feedback/3rdPieJsonData.action?status=Negative&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
        	    type : "json",
        	    success : function(data) {
        	    	console.log("3rd Pie");
        	    	console.log(data);
        	    	var total=0;
        	  	    for (var int = 0; int < data.length; int++) {
        	total=total+parseFloat(data[int].counter);
        	}
        	    	sampleData = data;
        	    	 // prepare jqxChart settings
                        var settings = {
	                  title: "Negative Ticket Status For All Types Of Feedback",
	                  description: "Data From "+$("#sdate").val()+" To "+$("#edate").val(),
	                  enableAnimations: true,
	              showLegend: true,
	              padding: { left: 5, top: 5, right: 5, bottom: 5 },
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
	                                    	dataField: 'counter',
	                                    	displayText: 'mode',
	                                    	labelRadius: 230,
	                                    	initialAngle: 15,
	                                    	radius: 200,
	                                    	centerOffset: 0,
											formatFunction: function (value) {
	                                        	
												 if (isNaN(value))
		                                                return value;
		                                            return parseFloat((value/total)*100).toFixed(1)+'%';
	                                        }
	                                    	
	                                    }
	                                    
	                                    
	                                  ]
	                          }
	                      ]
	              };
                    // create the chart
                    $('#chartContainer2').jqxChart(settings);
                    // get the chart's instance
                      var chart = $('#chartContainer2').jqxChart('getInstance');
                    // color scheme drop down
                    var colorsSchemesList = ["Negative", "Positive"];
                    $("#dropDownColors2").jqxDropDownList({ source: colorsSchemesList, selectedIndex: 0, width: '100', height: '25', dropDownHeight: 60,dropDownWidth: 100 });
                    $('#dropDownColors2').on('change', function (event) {
                        var value = event.args.item.value;
                        //alert(value);
                        
                        $.ajax({
                    	    type : "post",
                    	    url : "view/Over2Cloud/feedback/3rdPieJsonData.action?status="+value.split(" ").join("%20")+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
                    	    type : "json",
                    	    success : function(data) {
                    	    	var total=0;
                    	  	    for (var int = 0; int < data.length; int++) {
                    	total=total+parseFloat(data[int].counter);
                    	}
                    	    	sampleData = data;
                    	    	 // prepare jqxChart settings
                                    var settings = {
            	                  title: value+" Ticket Status For All Types Of Feedback",
            	                  description: "Data From "+$("#sdate").val()+" To "+$("#edate").val(),
            	                  enableAnimations: true,
            	              showLegend: true,
            	              padding: { left: 5, top: 5, right: 5, bottom: 5 },
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
            	                                    	dataField: 'counter',
            	                                    	displayText: 'mode',
            	                                    	labelRadius: 230,
            	                                    	initialAngle: 15,
            	                                    	radius: 200,
            	                                    	centerOffset: 0,
            											formatFunction: function (value) {
            	                                        	
            												 if (isNaN(value))
            		                                                return value;
            		                                            return parseFloat((value/total)*100).toFixed(1)+'%';
            	                                        }
            	                                    	
            	                                    }
            	                                    
            	                                    
            	                                  ]
            	                          }
            	                      ]
            	              };

                                    $('#chartContainer2').jqxChart(settings);
                                    
                    	    },
                     	   error: function() {
                     	        alert("error");
                     	    }
                     	 });
                     
                        
                    }); 
                   
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
<!-- <center>
 <table style="width: 680px">
        <tr>
            <td style="padding-left: 50px;">
                <p style="font-family: Verdana; font-size: 12px;">Select Chart Type: 
                </p><div id='dropDownSeries2'>
                </div>
                
            </td>
            <td>
                <p style="font-family: Verdana; font-size: 12px;">Select Feedback :
            
                <div id='dropDownColors2'>
                </div>    </p>
            </td>
        </tr>
    </table>
    </center> -->
   <div style="width: 100%" id='dropDownColors2'>
                </div> 
        <div id='chartContainer2' style="width: 100%; height: 500px">
        </div>
    
   
</body>
</html>