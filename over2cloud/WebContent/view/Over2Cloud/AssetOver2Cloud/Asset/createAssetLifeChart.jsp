<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
$.subscribe('getPointInfo', function(event, data) 
{
	var item = event.originalEvent.item;
	if (item) 
	{
		document.getElementById("hrt").style.display="block";
		var key=item.series.listValue;
	      event.originalEvent.plot.highlight(item.series, item.datapoint);
	      $.ajax({
				type : "post",
				url : "/cloudapp/view/Over2Cloud/AssetOver2Cloud/Asset/getClickPointInfo.action?titleIndex="+item.dataIndex+"&millSec="+item.datapoint[0],
				success : function(data){
					$("#draggablenonvalid").html(data);
				},
				error : function(){
					alert("error");
				}
			});
	}
});
</script>
</head>
<body>
<div class="list-icon"><div class="clear"></div><div class="head">Asset Life Cycle</div></div>

 <div class="clear"></div>  
 <div class="clear"></div> 
  
<div id="hrt" style="display: none;">
	<sj:div id="draggablenonvalid" draggable="true" cssClass="noaccept ui-widget-content ui-corner-all" cssStyle="width: 100px; height: 100px; padding: 0.5em; float: left; margin: 10px 10px 10px 0;">
	</sj:div>
</div>
 <div class="clear"></div> 
  <div class="clear"></div> 
<div id="topicsClick"></div>
<h3>Chart with Date Values</h3>
<div style="width: 750px; overflow: auto; padding-left:1cm;">
<div style="display: block;
		writing-mode: tb-rl;
		-webkit-transform: rotate(-90deg);	
		-moz-transform: rotate(-90deg);
		-ms-transform: rotate(-90deg);
		-o-transform: rotate(-90deg);
		transform: rotate(-90deg);
		position: absolute;
		left: 25%;
		top: 390px;
		font-size: 24px;">Date
		</div>
    <sjc:chart
        id="chartDate"
        xaxisMode="time"
        xaxisTimeformat="%m.%y"
        xaxisColor="blue"
        yaxisColor="blue"
        xaxisTickSize="[1, 'month']"
        xaxisTickColor="#aaa"
        xaxisPosition="bottom"
        yaxisPosition="left"
        yaxisTickSize="1"
        yaxisMax="32"
        yaxisMin="1"
        cssStyle="width: 1800px; height: 450px;"
        onClickTopics="getPointInfo"
    >
        <sjc:chartData
                id="chartDateData"
                list="xyaxisMap"
                color="green"
                lines="{ show: false }"
                points="{ show: false }"
                
        />
        <sjc:chartData
                id="chartDateData"
                label="Map -Date, Integer-"
                list="lifeMap"
                color="green"
                lines="{ show: true }"
                points="{show: true}"
                clickable="true"
        />
        
        <sjc:chartData
                id="chartDateData"
                label="Map -Date, Integer-"
                list="pastMap"
                color="red"
                lines="{ show: true }"
                points="{show: true}"
                clickable="true"
        />
    </sjc:chart>
   <Center><font size="4">Months With Year</font></Center>
</div>
</body>
</html>
