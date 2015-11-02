<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
    <title id='Description'></title>
   
    <script type="text/javascript">
    	drawPieChart();
       function drawPieChart() {
        	
        	$.ajax({
        	    type : "post",
        	    url : "view/Over2Cloud/feedback/NegativeFeedPieJsonData.action?block="+$("#typePieList").val()+"&status="+$("#typePieModeList").val()+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
        	    type : "json",
        	    success : function(data)  {
           	    	//console.log(data);
           	    	var chart1 = AmCharts.makeChart( "chartContainer21", {
           	    		  "type": "pie",
           	    		"legend": {
           	    		    "markerType": "circle",
           	    		    "position": "right",
           	    		    "marginRight": 50,
           	    		    "autoMargins": false
           	    		  },
           	    		  "theme": "light",
           	    		  "dataProvider": data,
           	    		  "valueField": "counter",
           	    		  "titleField": "mode",
           	    		  "outlineAlpha": 0.4,
           	    		  "depth3D": 15,
           	    		  "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
           	    		  "angle": 30,
           	    		  "export": {
           	    		    "enabled": true
           	    		  }
           	    		} );
           	    	chart1.write("chartContainer21");
           	    	},
        	   error: function() {
        	        alert("error");
        	    }
        	 });
        }
           
    </script>
</head>
<body class='default'>

 <table>
        <tr>
            <td style="padding-left: 105px;">
                <p style="font-family: Verdana; font-size: 12px;width: 40px;">Type : 
                </p>
                 </td><td style="width: 90px;">
                 		<s:select cssClass="button" list="#{'Stakeholder':'Stakeholder','Rating':'Rating','Remarks':'Remarks'}" id="typePieList" theme="simple" onchange="drawPieChart()"></s:select>
                 </td>
            <td style="padding-left: 105px;">
                <p style="font-family: Verdana; font-size: 12px;width: 40px">Mode :
            </p></td><td style="width: 60px;">
               <s:select cssClass="button" list="{'Paper-IPD','Paper-OPD','SMS-IPD','SMS-OPD','Admin Round','Ward Rounds','By Hand','Email','Verbal'}" id="typePieModeList" theme="simple" onchange="drawPieChart()"></s:select>
            </td>
        </tr>
    </table>
      
        <div id='chartContainer21' style="width: 99%; height: 300px">
        </div>
    
   
</body>
</html>