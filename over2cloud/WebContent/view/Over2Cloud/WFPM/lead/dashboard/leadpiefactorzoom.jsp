
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
        	    url : "view/Over2Cloud/WFPM/Lead/piechartFactor.action",
        	    type : "json",
        	    success : function(data) {
        	    	console.log("New pie dataaaaaa");
        	    	console.log(data);
        	    	sampleData = data;
        	    	 // prepare jqxChart settings
                        var settings = {
	                  title: "Lead Status",
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
	                                    	radius: 150,
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
                    $('#chartContainerpiefactor').jqxChart(settings);
                    
                   
             
        	    	
        	},
        	   error: function() {
        	        alert("error");
        	    }
        	 });
        });
           
    </script>
    <style type="text/css">
    	.zoompaichart { transition: all .2s ease-in-out; }
		.zoompaichart:hover { transform: scale(1.5); margin-top: 70px; height:400px;  }
    </style>
</head>
<body>

        <div class="zoompaichart22223333" id='chartContainerpiefactor' style="width: 100%; height: 450px">
        </div>
    
   
</body>
</html>
	    
	    