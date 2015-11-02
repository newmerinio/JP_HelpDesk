<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
<style type="text/css">

</style>
<script   type='text/javascript' src='scripts/jquery-1.10.2.min.js'></script>
<script  src="js/jquery-ui.js"></script>

<script type="text/javascript"><!--

	var dur=$("#durationFor").val();
	setTitle();
	LeadStage(dur);
	upcomingActivities(dur);
	LeadSource(dur);
	LeadOffer(dur);
	DomesticQueries(dur);
	dueActivity(dur);
	dueOwner(dur);
	allActivity(dur);
function setTitle()
{
	if($("#stage123").val()=='1')
		{
		//alert("check");
		$(".stageType").html("Lead");
		}else if($("#stage123").val()=='2')
			{
			$(".stageType").text("Prospect");
			}else
				{
				$(".stageType").text("Closed");
				}
	 
}
	  

function upcomingActivities(duration)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/upcomingActivities.action?duration="+duration+"&stage="+$("#stage123").val(),
	    type : "json",
	    success : function(data) {
			$("#upcoming").html(data);
		},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
}
function LeadStage(duration){
	

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getJsonDataForLeadStage.action?duration="+duration+"&stage="+$("#stage123").val(),
	    type : "json",
	    success : function(data) {
	    console.log(data);
	 
	    if (data.length == '0')
		{
			$('#' + 'leadStage').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		} else
		{
			var chart = AmCharts.makeChart( 'leadStage', {
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
		  		    "valueField": "Domestic",
		  		  "labelText":"[[Domestic]]",
		  		    "title":"Domestic",
		  		    "fixedColumnWidth":30
		  		    	
		  		  },
		  		{
		    		    "balloonText": "[[title]]: <b>[[value]]</b>",
		    		    "fillAlphas": 1,
		    		    "lineAlpha": 0,
		    		    "type": "column",
		    		    "valueField": "International",
		    			"labelText":"[[International]]",
		    		    "title":"International",
		    		    "fixedColumnWidth":30
		    		    	
		    		  }
		  		  ],
		  		  
		  		 "chartCursorSettings": {
				  		"valueBalloonsEnabled": false,
				          "fullWidth":true,
				          "cursorAlpha":0.1
				  	},
		  		  "categoryField": "Type",
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
		  	chart.write('leadStage');
		  	
		}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
	
	}


function LeadSource(duration){
	

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getJsonDataForLeadSource.action?duration="+duration+"&stage="+$("#stage123").val(),
	    type : "json",
	    success : function(data) {
	    console.log(data);
	 
	    if (data.length == '0')
		{
			$('#' + 'leadStage').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		} else
		{
			var chart = AmCharts.makeChart( 'leadSource', {
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
		  		    "valueField": "Domestic",
		  		  "labelText":"[[Domestic]]",
		  		    "title":"Domestic",
		  		    "fixedColumnWidth":30
		  		    	
		  		  },
		  		{
		    		    "balloonText": "[[title]]: <b>[[value]]</b>",
		    		    "fillAlphas": 1,
		    		    "lineAlpha": 0,
		    		    "type": "column",
		    		    "valueField": "International",
		    		    "labelText":"[[International]]",
		    		    "title":"International",
		    		    "fixedColumnWidth":30
		    		    	
		    		  }
		  		  ],
		  		  
		  		 "chartCursorSettings": {
				  		"valueBalloonsEnabled": false,
				          "fullWidth":true,
				          "cursorAlpha":0.1
				  	},
		  		  "categoryField": "Source",
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
		  	chart.write('leadSource');
		  	
		}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
	
	}

function LeadOffer(duration){
	

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getJsonDataForLeadOffering.action?duration="+duration+"&stage="+$("#stage123").val(),
	    type : "json",
	    success : function(data) {
	  //  console.log(data);
	 
	    if (data.length == '0')
		{
			$('#' + 'leadOffer').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		} else
		{
			var chart = AmCharts.makeChart( 'leadOffer', {
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
		  		    "valueField": "Domestic",
		  		  "labelText":"[[Domestic]]",
		  		    "title":"Domestic",
		  		    "fixedColumnWidth":30
		  		    	
		  		  },
		  		{
		    		    "balloonText": "[[title]]: <b>[[value]]</b>",
		    		    "fillAlphas": 1,
		    		    "lineAlpha": 0,
		    		    "type": "column",
		    		    "valueField": "International",
		    		    "labelText":"[[International]]",
		    		    "title":"International",
		    		    "fixedColumnWidth":30
		    		    	
		    		  }
		  		  ],
		  	
		  		 "chartCursorSettings": {
				  		"valueBalloonsEnabled": false,
				          "fullWidth":true,
				          "cursorAlpha":0.1
				  	},
		  		  "categoryField": "offer",
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
		  	chart.write('leadOffer');
		  	
		}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
	
	}


function DomesticQueries(duration){
	

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getJsonDataForDomesticQry.action?duration="+duration+"&stage="+$("#stage123").val(),
	    type : "json",
	    success : function(data) {
	    console.log(data);
	 
	    if (data.length == '0')
		{
			$('#' + 'domQry').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		} else
		{

			var chart = AmCharts.makeChart( "chartdiv", {
			  type: "stock",
			  "theme": "light",
			  dataSets: [ {
			    color: "#b0de09",
			    fieldMappings: [ {
			      fromField: "Domestic",
			      toField: "Domestic"
			    },{
				      fromField: "International",
				      toField: "International"
				    } ],
			    dataProvider: data,
			    categoryField: "date"
			  } ],


			  panels: [ {
			    title: "Domestic",
			    percentHeight: 70,

			    stockGraphs: [ {
			    	  id: "g1",
	  			      valueField: "Domestic",
	  			    bullet : "round",
					lineThickness : 3,
					bulletSize : 7,
					bulletBorderAlpha : 1,
					bulletColor : "#FFFFFF",
					useLineColorForBulletBorder : true,
					bulletBorderThickness: 3,
					fillAlphas: 0,
					lineAlpha : 1,
					labelText:"[[Domestic]]"
	  		     
			    } ],

			    stockLegend: {
			      valueTextRegular: " ",
			      markerType: "none"
			    }
			  },
		      {
		        title: "International",
		        percentHeight: 30,
		        marginTop: 1,
		        columnWidth: 0.6,
		        showCategoryAxis: false,

		        stockGraphs: [ {
		          valueField: "International",
		          type: "column",
		          showBalloon: false,
		          fillAlphas: 1,
		          lineAlpha : 1,
		          useDataSetColors: false
		        } ],

		        stockLegend: {
				      valueTextRegular: " ",
				      markerType: "none"
				    }
		      }  

			   ],

			  chartScrollbarSettings: {
			    graph: "g1"
			  },

			  chartCursorSettings: {
			    valueBalloonsEnabled: true,
			    graphBulletSize: 1,
			    valueLineBalloonEnabled: true,
			    valueLineEnabled: true,
			    valueLineAlpha: 0.5
			  },

			  periodSelector: {
			    periods: [ {
			      period: "DD",
			      count: 10,
			      label: "10 days"
			    }, {
			      period: "MM",
			      count: 1,
			      label: "1 month"
			    }, {
			      period: "YYYY",
			      count: 1,
			      label: "1 year"
			    }, {
			      period: "YTD",
			      label: "YTD"
			    }, {
			      period: "MAX",
			      label: "MAX"
			    } ]
			  },

			  panelsSettings: {
			    usePrefixes: true
			  }
			 
		  		} );
		  	chart.write('domQry');
		  	
		}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
	
	}
	
function dueActivity(duration){
	

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getJsonDataForDueActivity.action?duration="+duration+"&stage="+$("#stage123").val(),
	    type : "json",
	    success : function(data) {
	    console.log(data);
	    console.log(data[0]);
	    console.log(data[1]);
	    if (data[1].length == '0')
		{
			$('#' + 'dueActivity').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		} else
		{
			var graph=[];
			for (var int = 0; int < data[0].length; int++) {
				console.log(data[0][int]);
				graph.push({
			    		    "fillAlphas": 1,
			    		    "lineAlpha": 0,
			    		    "type": "column",
			    		    "valueField": ""+data[0][int],
			    		    "balloonText": "[[title]]: <b>[[value]]</b>",
			    		    "labelText":"[[value]]",
			    		    "title":""+data[0][int],
			    		    "fixedColumnWidth":30
			    		    	
			    		  });
			}
			console.log("graph::::"+graph);
			var chart = AmCharts.makeChart( 'dueActivity', {
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
		  		  "dataProvider": data[1],
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
		  		  "graphs": graph,
		  	
		  		 "chartCursorSettings": {
				  		"valueBalloonsEnabled": false,
				          "fullWidth":true,
				          "cursorAlpha":0.1
				  	},
		  		  "categoryField": "offer",
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
		  	chart.write('dueActivity');
		  	
		}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
	
	}
	
function allActivity(duration){
	

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getJsonDataForDueActivity.action?duration="+duration+"&stage="+$("#stage123").val()+"&dataFor=All",
	    type : "json",
	    success : function(data) {
	    console.log(data);
	    console.log(data[0]);
	    console.log(data[1]);
	    if (data[1].length == '0')
		{
			$('#' + 'allActivity').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		} else
		{
			var graph=[];
			for (var int = 0; int < data[0].length; int++) {
				console.log(data[0][int]);
				graph.push({
			    		    "fillAlphas": 1,
			    		    "lineAlpha": 0,
			    		    "type": "column",
			    		    "valueField": ""+data[0][int],
			    		    "balloonText": "[[title]]: <b>[[value]]</b>",
			    		    "labelText":"[[value]]",
			    		    "title":""+data[0][int],
			    		    "fixedColumnWidth":30
			    		    	
			    		  });
			}
			console.log("graph::::"+graph);
			var chart = AmCharts.makeChart( 'allActivity', {
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
		  		  "dataProvider": data[1],
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
		  		  "graphs": graph,
		  	
		  		 "chartCursorSettings": {
				  		"valueBalloonsEnabled": false,
				          "fullWidth":true,
				          "cursorAlpha":0.1
				  	},
		  		  "categoryField": "offer",
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
		  	chart.write('allActivity');
		  	
		}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
	
	}
	
function dueOwner(duration){
	

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getJsonDataForDueOwner.action?duration="+duration+"&stage="+$("#stage123").val(),
	    type : "json",
	    success : function(data) {
	    console.log(data);
	    console.log(data[0]);
	    console.log(data[1]);
	    if (data[1].length == '0')
		{
			$('#' + 'dueOwner').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		} else
		{
			var graph=[];
			for (var int = 0; int < data[0].length; int++) {
				console.log(data[0][int]);
				graph.push({
			    		    "fillAlphas": 1,
			    		    "lineAlpha": 0,
			    		    "type": "column",
			    		    "valueField": ""+data[0][int],
			    		    "balloonText": "[[title]]: <b>[[value]]</b>",
			    		    "labelText":"[[value]]",
			    		    "title":""+data[0][int],
			    		    "fixedColumnWidth":30
			    		    	
			    		  });
			}
			console.log("graph::::"+graph);
			var chart = AmCharts.makeChart( 'dueOwner', {
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
		  		  "dataProvider": data[1],
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
		  		  "graphs": graph,
		  	
		  		 "chartCursorSettings": {
				  		"valueBalloonsEnabled": false,
				          "fullWidth":true,
				          "cursorAlpha":0.1
				  	},
		  		  "categoryField": "offer",
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
		  	chart.write('dueOwner');
		  	
		}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
	
	}
$(function() {
  //  $( "#result" ).sortable();
    //$( ".contentdiv-smallNewDash" ).disableSelection();
  });
</script>
</head>
<body>
<s:hidden id="durationFor" value="%{duration}"/>


<div class="contentdiv-smallNewDash" style="overflow: auto;width:49%" id="counter_dash">
<div class="headdingtest">Offering Vs <SPAN class="stageType"></SPAN> Type  <span id="headerId" ><s:property value="%{duration}"/></span></div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>

<div class="clear"></div>
<div id='leadOffer' style="width: 100%; height: 277px;overflow: auto;" ></div>
</div>

<div class="contentdiv-smallNewDash" style="overflow: auto;width:49%" id="counter_dash">
<div class="headdingtest"> <SPAN class="stageType"></SPAN> Query Status</div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>

<div class="clear"></div>
<div id='domQry' style="width: 100%; height: 277px;overflow: auto;" ></div>
</div>

<div class="contentdiv-smallNewDash" style="overflow: auto;width: 49%" id="counter_dash">
<div class="headdingtest"><SPAN class="stageType"></SPAN> Activity Stage <span id="headerId" ><s:property value="%{duration}"/></span></div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>

<div class="clear"></div>
<div id='leadStage' style="width: 100%; height: 277px;overflow: auto;" ></div>
</div>

<div class="contentdiv-smallNewDash" style="overflow: auto;width: 49%" id="counter_dash">
<div class="headdingtest">Up-Coming <SPAN class="stageType"></SPAN> Activities <span id="headerId" ><s:property value="%{duration}"/></span></div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>

<div class="clear"></div>
<div id='upcoming' style="width: 100%; height: 277px;overflow: auto;" ></div>
</div>
	
	<div class="contentdiv-smallNewDash" style="overflow: auto;width:98%" id="counter_dash">
<div class="headdingtest">Lead Source <span id="headerId" ><s:property value="%{duration}"/></span></div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>

<div class="clear"></div>
<div id='leadSource' style="width: 100%; height: 277px;overflow: auto;" ></div>
</div>
<!-- /////////////// -->
<div class="contentdiv-smallNewDash" style="overflow: auto;width: 49%" id="counter_dash">
<div class="headdingtest"><SPAN class="stageType"></SPAN> Due Activity <span id="headerId" ><s:property value="%{duration}"/></span></div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>

<div class="clear"></div>
<div id='dueActivity' style="width: 100%; height: 277px;overflow: auto;" ></div>
</div>

<div class="contentdiv-smallNewDash" style="overflow: auto;width: 49%" id="counter_dash">
<div class="headdingtest"><SPAN class="stageType"></SPAN> Owner Due <span id="headerId" ><s:property value="%{duration}"/></span></div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>

<div class="clear"></div>
<div id='dueOwner' style="width: 100%; height: 277px;overflow: auto;" ></div>
</div>	

<div class="contentdiv-smallNewDash" style="overflow: auto;width: 98%" id="counter_dash">
<div class="headdingtest"><SPAN class="stageType"></SPAN> All Activity Count <span id="headerId" ><s:property value="%{duration}"/></span></div>
	<div style="margin-left: 1%;margin-top: -3%;float: left: ;">
			<button  id="backBtnDept" style="display: none;position: absolute;top: 26px;left: 5px;"  type="button" class="btn btn-default btn-sm" title="Back">
			  <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>
	</div>

<div class="clear"></div>
<div id='allActivity' style="width: 100%; height: 277px;overflow: auto;" ></div>
</div>
	
	
</body>
</html>