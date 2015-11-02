<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
<style type="text/css">

</style>
<SCRIPT type="text/javascript">
offeringChartDefiner('Today');
verticalChartDefiner('Today');
getNextActData('Today');
	function offeringChartDefiner(duration){
	
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getDashboardOfferingsChart.action?duration="+duration,
		    type : "json",
		    success : function(data) {
		    if (data.length == '0')
			{
				$('#' + "chartdiv").html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
			} else
			{
				//console.log(data);
				var chart = AmCharts.makeChart('chartdiv', 
						{
					"type": "pie",
					"theme": "light",
					"marginTop": -15,
					"labelText": " [[percents]]%",
					"titleField": "Offering",
					"valueField": "Counter",
					 "labelRadius": 5,
					  "radius": "35%",
					  "innerRadius": "60%",
					  "labelText": "[[Offering]]",
					"dataProvider":data
				});
				chart.write('chartdiv');
			}},
		   error: function() {
		        alert("error from jsonArray data ");
		    }
		 });
		
	}
	function verticalChartDefiner(duration){
		

		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getVerticalDetails.action?duration="+duration,
		    type : "json",
		    success : function(data) {
		    if (data.length == '0')
			{
				$('#' + 'chartVertical').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
			} else
			{
				var chart = AmCharts.makeChart( 'chartVertical', {
			  		  "type": "serial",
			  		  "theme": "light",
			  		"legend": {
			  			"align": "center",
			  		    "useGraphSettings": true,
			  		    "equalWidths": false,
			  			"markerLabelGap": 4,
						"markerSize": 9,
						"spacing": -53,
						"autoMargins": false
			  		  },
			  		  "dataProvider": data,
			  		  "valueAxes": [ {
			  			    "stackType":"none",
			  			    "gridColor": "#FFFFFF",
			  			    "gridAlpha": 0,
			  			    "dashLength": 0,
			  			    "totalText":"[[total]]",
			  			    "totalTextOffset":10  			    
			  			  } ],
			  		  "gridAboveGraphs": true,
			  		  "startDuration": 1,
			  		  "graphs": [ {
			  		    "balloonText": "[[title]]: <b>[[value]]</b>",
			  		    "fillAlphas": 1,
			  		    "lineAlpha": 0,
			  		    "type": "column",
			  		    "valueField": "Corp",
			  		  
			  		    "title":"Corporation",
			  		    "fixedColumnWidth":30
			  		    	
			  		  },
			  		{
			    		    "balloonText": "[[title]]: <b>[[value]]</b>",
			    		    "fillAlphas": 1,
			    		    "lineAlpha": 0,
			    		    "type": "column",
			    		    "valueField": "Ref",
			    		  
			    		    "title":"Referral",
			    		    "fixedColumnWidth":30
			    		    	
			    		  },{
			    	  		    "balloonText": "[[title]]: <b>[[value]]</b>",
			    	  		    "fillAlphas": 1,
			    	  		    "lineAlpha": 0,
			    	  		    "type": "column",
			    	  		    "valueField": "Walk",
			    	  		  
			    	  		    "title":"Walk In",
			    	  		    "fixedColumnWidth":30
			    	  		    	
			    	  		  }
			  		  
			  		  ],
			  		"depth3D": 20,
			  		"angle": 30,
			  		  
			  		 "chartCursorSettings": {
					  		"valueBalloonsEnabled": false,
					          "fullWidth":true,
					          "cursorAlpha":0.1
					  	},
			  		  "categoryField": "Vertical",
			  		  "categoryAxis": {
			  		    "gridPosition": "start",
			  		    "gridAlpha": 0,
			  		    "tickPosition": "start",
			  		    "tickLength": 10,
			  		  "autoWrap":true
			  		  },
			  		"export": {
			    		    "enabled": true
			    		  }
			  		
			  		} );
			  	chart.write('chartVertical');
			  	
			  	
			}},
		   error: function() {
		        alert("error from jsonArray data ");
		    }
		 });
		
		
		}
	function getNextActData(duration)
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getNextActivityDetails.action?duration="+duration,
		    type : "json",
		    success : function(data) {
		    $("#CounterNextAct").html(data);
			},
		   error: function() {
		        alert("error from jsonArray data ");
		    }
		 });
	}
		
		
</SCRIPT>
</head>
<body>

<table width="100%" cellpadding="0" cellspacing="3" >
	<tbody>
		<tr>
			<td width="22%" valign="top" style="border-right: 1px solid #e1e1e1;" class="cart">
						<table width="100%" cellpadding="0" cellspacing="0" >
							<tbody>
								<tr>
									<td colspan="2" class="fbtxt f13 mB10" align="center">Top 10 Offerings</td>
									</tr>
									<s:iterator value="offeringDetail" status="count">
										<tr>
											<td width="50"> 
												<div class="bullet"> 	
													<s:property value="%{value}"/>
												</div>
											</td>
											<td class="pL25"> 
												<div class="fbtxt"> 	
													<s:property value="%{key}"/>
												</div>
											</td>
										</tr>
									</s:iterator>
												
								</tbody>
							</table>
			</td>
			<td width="25%"  valign="top" >
					<table width="100%" cellpadding="0" cellspacing="0" >
						<tbody>
								<tr>
								<td colspan="2" class="fbtxt f13 mB10" align="center">Top 10 Offerings</td>
								</tr><tr>
								<td ><div id="chartdiv" style="width: 100%;height: 150px;"></div></td>
								</tr>
												
						</tbody>
					</table>
			</td>
			<td width="48%" >
					<table width="100%" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<td align="center">
									<div class="fbtxt f13 mB10">Vertical Wise Patient Source Details</div>
								</td>
							</tr>
							<tr>
								<td >
								<div id="chartVertical" style="width: 100%;height: 250px;"></div>
								</td>
							</tr>
												
						</tbody>
					</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" width="65%" class="cart">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tbody>
							<tr><td align=""><div class="fbtxt f13 mB10"> 	
							Next Scheduled Activity
						</div></td></tr>
							<tr>
							<td ><div id="CounterNextAct" style="width: 100%;height:200px;overflow: auto;"></div></td>
							</tr>
												
						</tbody>
					</table>
			</td>
		</tr>
												
	</tbody>
</table>
	
	
	
	
	
</body>
</html>