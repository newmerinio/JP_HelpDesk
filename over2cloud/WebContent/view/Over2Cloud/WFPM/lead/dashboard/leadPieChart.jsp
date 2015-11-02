<%-- <%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:url var="chartDataUrl" action="json-chart-data"/>

 <sjc:chart
		    	id="chartPieLead%{legendShow}"
		    	cssStyle="width: %{width}px; height: %{height}px; margin: auto;"
		    	legendShow="true"
		    	title="Lead pieChart"
		    	
		    	pie="true"
		    	pieLabel="true" 
		    	legendPosition="ne"
		    	xaxisLabel="true"
		    	xaxisMax="1"
		    	xaxisMin="1"
		    	
		    >
		    	<s:iterator value="%{leadMap}">
			    	<sjc:chartData
			    		label="%{key}"
			    		data="%{value}" 
			    	/>
		    	</s:iterator>
	    </sjc:chart> --%>
	    
	    
	    
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
        	    url : "view/Over2Cloud/WFPM/Lead/piechartdata.action",
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
                    $('#chartContainer123').jqxChart(settings);
                   
                   
             
        	    	
        	},
        	   error: function() {
        	        alert("error");
        	    }
        	 });
        });
           
    </script>
</head>
<body>

        <div id='chartContainer123' style="width: 100%; height: 300px">
        </div>
    
   
</body>
</html>
	    
	    