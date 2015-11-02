 var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
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
	                                description: ' ',
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
            $('#jqxChart').jqxChart(settings);
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
	                                description: ' ',
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
            $('#levelChart').jqxChart(settings);
            $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
            $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
            var refreshChart = function () {
                $('#levelChart').jqxChart({ enableAnimations: false });
                $('#levelChart').jqxChart('refresh');
            }
            // update greyScale values.
            $("#FlipValueAxis").on('change', function (event) {
                var groups = $('#levelChart').jqxChart('seriesGroups');
                groups[0].valueAxis.flip = event.args.checked;
                refreshChart();
            });
            $("#FlipCategoryAxis").on('change', function (event) {
                var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
                categoryAxis.flip = event.args.checked;
                refreshChart();
            });
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });
	
	
	//category pie
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4CategCounters.action",
	    type : "json",
	    success : function(data) {
	    	
	    	
	    	sampleData = data;
	    	var total=0;
	  	    for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Counter);
	}
	    	  var settings = {
	                  title: "",
	                  description: "",
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
	                                    	dataField: 'Counter',
	                                    	displayText: 'Category',
	                                    	labelRadius: 70,
	                                    	initialAngle: 15,
	                                    	radius: 55,
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
	    	  $('#CategChart').jqxChart({ enableAnimations: true });
	        $('#CategChart').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#CategChart').jqxChart({ enableAnimations: false });
	            $('#CategChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#CategChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#CategChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	// pie end here
	
	function StatckedChartStatus() {
	restoreBoard("prevPie","nextPie","jqxChart");
	maxDivId1='1stStackedBar';
	var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
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
	                                description: ' ',
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
	            $('#jqxChart').jqxChart(settings);
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
	
	}//end here
	
	function Column2AxesChartStatus() {
	restoreBoard("prevPie","nextPie","jqxChart");
	maxDivId1='1stColumnBar';
	var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
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
	                                description: ' ',
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
	            $('#jqxChart').jqxChart(settings);
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
	
	}//end here
	
	function showPieStatus(status,title,description){
	showDetailsPie1('prevPie','nextPie','jqxChart');
	maxDivId1='1stPendingPie';
	var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
	    type : "json",
	    success : function(data) {
	    	
	    	
	    	sampleData = data;
	    	 var total=0;
	    	 if(status=='Pending'){
	  	    
	    	 for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Pending);
	}
	  	    
	    	 }else if(status=='Snooze'){
	  	    
	    	 for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Snooze);
	}
	  	    
	    	 }else if(status=='HighPriority'){
	  	    
	    	 for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].HighPriority);
	}
	  	    
	    	 }else if(status=='Resolved'){
	  	    
	    	 for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Resolved);
	}
	  	    
	    	 }
	    	 
	    	  var settings = {
	                  title: ""+title,
	                  description: ""+description,
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
	                                    	dataField: status,
	                                    	displayText: 'dept',
	                                    	labelRadius: 100,
	                                    	initialAngle: 15,
	                                    	radius: 45,
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
	    	  $('#jqxChart').jqxChart({ enableAnimations: true });
	        $('#jqxChart').jqxChart(settings);
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
	
	}//end here
	
	
	
	function lineStatus() {
	restoreBoard("prevPie","nextPie","jqxChart");
	maxDivId1='1stLine';
	var  sampleData =null;
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
                	'<br /><b>Resolved: </b>' +data.Resolved+
                	'</DIV>';
            };

  	  	    var settings = {
  	                title: " ",
  	                description: " ",
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
  	            
  	            $('#jqxChart').jqxChart(settings);
  	  	    	
  	  	},
  	  	   error: function() {
  	              alert("error");
  	          }
  	  	 });
	}//end here
	
	function bubbleStatus(){
	restoreBoard("prevPie","nextPie","jqxChart");
	maxDivId1='1stBubble';
	var  sampleData =null;
	$.ajax({
	    type : "post",
	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4DeptCounters.action",
	    type : "json",
	    success : function(data) {
	    	console.log("Bubble status");
	    	console.log(data);
	    	sampleData = data;
	    	 var settings = {
	                 title: ' ',
	                 description: ' ',
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
	    	 $('#jqxChart').jqxChart(settings);
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
	}//end here 
	
	//for Dashboard 2
	
	function showLeveStackedBar(){
	restoreBoard("prevPie1","nextPie1","levelChart");
	maxDivId2='2ndStackedBar';
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
	        $('#levelChart').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
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
	 
	
	}
	
function showLevelColumn2Axes(){
	restoreBoard("prevPie1","nextPie1","levelChart");
	maxDivId2='2ndColumn2Axes';
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action",
	    type : "json",
	    success : function(data) {
	    	sampleData = data;
	    	console.log("Level Data");
	    	console.log(data);
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
	        $('#levelChart').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 120,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 120,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
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
	 
	
	}//end here 

//Line Chart Level
function lineLevel() {
	restoreBoard("prevPie1","nextPie1","levelChart");
	maxDivId2='LineLevel';
	var  sampleData =null;
	$.ajax({
	  	    type : "post",
	  	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action",
	  	    success : function(statusdata) {
	  	    	
	  	    	sampleData=statusdata;
	  	    	
	  	    var toolTipCustomFormatFn = function (key,value) {
          var data=statusdata[value];
            return                              '<DIV style="text-align:left"><b>Department: </b>' + data.Level+
            	'<br /><b>Pending: </b>' +data.Pending+
            	'<br /><b>High Priority: </b>' +data.HighPriority+
            	'<br /><b>Snooze: </b>' +data.Snooze+
            	'<br /><b>Resolved: </b>' +data.Resolved+
            	'</DIV>';
        };

	  	    var settings = {
	                title: " ",
	                description: " ",
	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	                source: sampleData,
	              enableAnimations: true,
	                
	                categoryAxis:
	                    {	
	                	textRotationAngle:0,
	                        dataField: 'Level',
	                       
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
	            
	            $('#levelChart').jqxChart(settings);
	  	    	
	  	},
	  	   error: function() {
	              alert("error");
	          }
	  	 });
}//end here

function bubbleLevel(){
	restoreBoard("prevPie1","nextPie1","levelChart");
	maxDivId2='BubbleLevel';
	var  sampleData =null;
	$.ajax({
	    type : "post",
	url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action",
	    type : "json",
	    success : function(data) {
	    	
	    	console.log(data);
	    	sampleData = data;
	    	 var settings = {
	                 title: ' ',
	                 description: ' ',
	                 enableAnimations: true,
	                 showLegend: true,
	                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	                 source: sampleData,
	                 xAxis:
	                     {
	                         dataField: 'Level',
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
	    	 $('#levelChart').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#levelChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
}//end here 

function showPieLevel(level,title,description) {
	showDetailsPie1('prevPie1','nextPie1','levelChart');
	maxDivId2='2ndPendingPie';
	var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4LevelCounters.action",
	    type : "json",
	    success : function(data) {
	    	 var total=0;
	    	 if(level=='Pending'){
	  	    
	    	 for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Pending);
	}
	  	    
	    	 }else if(level=='Snooze'){
	  	    
	    	 for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Snooze);
	}
	  	    
	    	 }else if(level=='HighPriority'){
	  	    
	    	 for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].HighPriority);
	}
	  	    
	    	 }else if(level=='Resolved'){
	  	    
	    	 for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Resolved);
	}
	  	    
	    	 }
	  	    
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: ""+title,
	                  description: ""+description,
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
	                                    	dataField: level,
	                                    	displayText: 'Level',
	                                    	labelRadius: 100,
	                                    	initialAngle: 15,
	                                    	radius: 45,
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
	    	  $('#levelChart').jqxChart({ enableAnimations: true });
	        $('#levelChart').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#levelChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
}//end here 


//dashboard 3rd
//category Pie
function showPieCateg(){
	var  sampleData =null;
	maxDivId3="PieCateg";
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4CategCounters.action",
	    type : "json",
	    success : function(data) {
	    	
	    	
	    	sampleData = data;
	    	 var total=0;
	  	    for (var int = 0; int < data.length; int++) {
	total=total+parseFloat(data[int].Counter);
	}
	    	  var settings = {
	                  title: "",
	                  description: "",
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
	                                    	dataField: 'Counter',
	                                    	displayText: 'Category',
	                                    	labelRadius: 70,
	                                    	initialAngle: 15,
	                                    	radius: 55,
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
	    	  $('#CategChart').jqxChart({ enableAnimations: true });
	        $('#CategChart').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#CategChart').jqxChart({ enableAnimations: false });
	            $('#CategChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#CategChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#CategChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
}

function DoughnutCateg(){
	var  sampleData =null;
	maxDivId3="DoughnutCateg";
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4CategCounters.action",
	    type : "json",
	    success : function(data) {
	    	
	    	
	    	sampleData = data;
	
	    	 var settings = {
	                 title: "",
	                 description: "",
	                 enableAnimations: true,
	                 showLegend: true,
	                 showBorderLine: true,
	                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                 titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                 source: sampleData,
	                 colorScheme: 'scheme06',
	                 seriesGroups:
	                     [
	                         {
	                             type: 'donut',
	                             showLabels: true,
	                             series:
	                                 [
	                                     {
	                                         dataField: 'Counter',
	                                         displayText: 'Category',
	                                         labelRadius: 40,
	                                         initialAngle: 15,
	                                         radius: 60,
	                                         innerRadius: 10,
	                                         centerOffset: 0
	                                         
	                                     }
	                                 ]
	                         }
	                     ]
	             };
	
	    	  $('#CategChart').jqxChart(settings);
},
error: function() {
  alert("error from jsonArray data ");
}
});
	    
	
}