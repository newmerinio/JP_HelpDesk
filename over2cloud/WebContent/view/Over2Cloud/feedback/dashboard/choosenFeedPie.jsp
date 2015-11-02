<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'></title>
    <%@ taglib prefix="s" uri="/struts-tags"%>
    <script type="text/javascript">
    drawChoosePie();
       function drawChoosePie()
       {
    	   $.ajax({
       	    type : "post",
       	    url : "view/Over2Cloud/feedback/chooseFeedPieJsonData.action?block=choosen&status="+$("#typeChooseModeList").val()+"&fromDate="+$("#sdate").val()+"&toDate="+$("#edate").val(),
       	    type : "json",
       	    success : function(data) {
       	    //	console.log(data);
       	    	var chart1 = AmCharts.makeChart( "chartContainer211", {
       	    		  "type": "pie",
       	    		  "theme": "light",
       	    		  "dataProvider": data,
       	    		  "legend": {
       	    		    "markerType": "circle",
       	    		    "position": "bottom",
       	    		    "marginBottom": 1,
       	    		    "autoMargins": false
       	    		  },
       	    		  
       	    		  "valueField": "counter",
       	    		  "titleField": "mode",
       	    		  "outlineAlpha": 0.1,
       	    		  "depth3D": 15,
       	    		 "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
       	    		  "angle": 20,
       	    		  "export": {
       	    		    "enabled": true
       	    		  }
       	    		} );
       	    	chart1.write("chartContainer211");
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
        <tr >
             <td style="padding-left: 105px;">
                <p style="font-family: Verdana; font-size: 12px;width: 40px">Mode :
            </p></td><td style="width: 60px;">
               <s:select cssClass="button" list="{'Paper-IPD','Paper-OPD'}" id="typeChooseModeList" theme="simple" onchange="drawChoosePie()"></s:select>
            </td>
        </tr>
    </table>
        <div id='chartContainer211' style="width: 100%; height: 390px">
        </div>
    
   
</body>
</html>