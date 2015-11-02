<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
    <script type="text/javascript">
    var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/report/DSR/offeringJsonChartData.action",
	    type : "json",
	    success : function(data) {
	    	sampleData = data;
	    	var settings = {
	    	        title: "Offering Target vs. Achievement",
	    	        description: "Offering Target vs. Achievement of Employee",
	    			enableAnimations: true,
	    	        showLegend: true,
	    	        padding: { left: 5, top: 5, right: 5, bottom: 5 },
	    	        titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	    	        source: sampleData,
	    	        categoryAxis:
	    	            {
	    	                dataField: 'name',
	    	                showGridLines: true
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
	    	                        description: 'Target',
	    	                        axisSize: 'auto',
	    	                        tickMarksColor: '#888888'
	    	                    },
	    	                    series: [
	    	                            { dataField: 'target', displayText: 'Target'},
	    	                            { dataField: 'achivement', displayText: 'Achivement'}
	    	                            
	    	                        ]
	    	                }
	    	            ]
	    	    };
	    	    
	    	    // setup the chart
	    	    $('#jqxChart').jqxChart(settings);
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });
	
 // prepare jqxChart settings
    

       
</script>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head">Offering Target vs. Achivement</div>
	</div>
	<div style=" float:left;  width:98%;">
			<div style='height: 600px; width:100%;'>
		    <div id='host' style="margin: 0 auto; width:100%; height:400px;">
				<div id='jqxChart' style="width:100%; height:500px; position: relative; left: 0px; top: 0px;">
				</div>
			</div>
		    </div>
	</div>
</body>
</html>