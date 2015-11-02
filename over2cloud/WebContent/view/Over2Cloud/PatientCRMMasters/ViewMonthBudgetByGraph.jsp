<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <script src="amcharts/amcharts.js"></script>
	<script src="amcharts/serial.js"></script>
	<script src="amcharts/themes/light.js"></script>
	<script src="amcharts/amstock.js"></script>
	<script src="amcharts/pie.js"></script>
    <script  src="amcharts/plugins/export/export.js"></script>
    
    
    <script type="text/javascript">
    
    function montlyAllotedBudget(){
    	
var yr=$("#searchField").val();
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/patientCRMMaster/fetchMontlyAllotedBudget.action?searchField="+yr,
    	    type : "json",
    	    success : function(data) {
    	 
    	 	$("#totalYearBalance").html(data.totalBalance);
    	 	$("#month_reduce_amount").val(data.remainBalance);
    	 	
    	    if (data.gridModel.length == '0')
    		{
    			$('#' + 'monthBudgetGraph').html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
    		} else
    		{
    			var chart = AmCharts.makeChart("monthBudgetGraph", {
    				  "type": "serial",
    				  "addClassNames": true,
    				  "theme": "light",
    				  "autoMargins": true,
    				  "marginLeft": 0,
    				  "marginRight": 0,
    				  "marginTop": 10,
    				  "marginBottom": 0,
    				  "balloon": {
    				    "adjustBorderColor": false,
    				    "horizontalPadding": 10,
    				    "verticalPadding": 8,
    				    "color": "#ffffff"
    				  },

    				  "dataProvider": data.gridModel,
    				  "startDuration": 1,
    				  "graphs": [{
    				    "alphaField": "alpha",
    				    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
    				    "fillAlphas": 1,
    				    "title": "Amount",
    				    "type": "column",
    				    "valueField": "values",
    				    "dashLengthField": "dashLengthColumn",
    				    	"fixedColumnWidth":40
    				  }],
    				  "categoryField": "months",
    				  "categoryAxis": {
    			  		    "gridPosition": "start",
    			  		    "gridAlpha": 0,
    			  		    "tickPosition": "start",
    			  		    "tickLength": 50,
    			  		  "autoWrap":true
    			  		  },
    			  		"valueAxes": [
    			  		    		{
    			  		    			"id": "ValueAxis-1",
    			  		    			"title": "Financial Year ("+data.yearName+")"
    			  		    		}
    			  		    	],
    				  "export": {
    				    "enabled": true
    				  }
    				});
						  	chart.write('monthBudgetGraph');
    		}
				//console.log(data.monthList.length);
    	  //  console.log(">>>>>>>> "+data.monthList);
    	    $('#month_for option').remove();
    		$('#month_for').append($("<option></option>").attr("value",-1).text('Select Month'));
    		$(data.monthList).each(function(index)
    		{	console.log(this.key+this.value)
    		   $('#month_for').append($("<option></option>").text("value",this.id).text(this.value));
    		});

    		},
    	   error: function() {
    	        alert("error from jsonArray data ");
    	    }
    	 });
    	
    	}
    montlyAllotedBudget("");
     
    </script>
    
    
</head>
<body>

<s:hidden id="searchField" value="%{searchField}"  ></s:hidden>
			<div id='monthBudgetGraph' style="height: 500px; width:96.99%; overflow: hidden;"></div>
</body>
</html>