<%@ page language="java" contentType="text/html; charset=ISO-8859-1"pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
$.subscribe('getChatInfo', function(event, data) 
{
	var item = event.originalEvent.item;
	if (item) 
	{
		var key=item.series.label;
		if(key=='Remaining Spare')
		{
			$("#spareInfoDialog").dialog({title: 'Total Remaining Spare',width: 350});
		}
		else
		{
			$("#spareInfoDialog").dialog({title: key,width: 800});
		}
		 $.ajax({
	    	    type : "post",
	    	    url : "/cloudapp/view/Over2Cloud/AssetOver2Cloud/Spare/getSpareInfo.action?deptName="+key+"&modifyFlag=0&deleteFlag=0",
	    	    success : function(data){
	    		$("#spareInfoDiv").html(data);
	        	$("#spareInfoDialog").dialog('open');
	    	},
	    	   error: function() {
	    	        alert("error");
	    	    }
	    	 });
	}
});

$.subscribe('hover', function(event, data)
{
    $("#hover1").text(event.originalEvent.pos.x.toFixed(2)+','+event.originalEvent.pos.y.toFixed(2));
});
</script>
</head>
<body>
<div class="middle-content">
	<div class="list-icon">
	<div class="head">Spare Chat Graph</div>
</div>
<div class="clear"></div>
<div class="border">
<div id="hover1"></div>
    <sjc:chart
        id="chartPie"
        cssStyle="width: 600px; height: 400px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        onHoverTopics="hover"
    >
    <s:iterator value="%{consumeNonConsumeSpareMap}">
	    <s:if test="key=='Remaining Spare'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="green"
		    		/>	
		    		
		 </s:if>
		 <s:else>
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#1112121"/>
		 </s:else>
    	</s:iterator>
    </sjc:chart>
<sj:dialog id="spareInfoDialog" modal="true" effect="slide" autoOpen="false" width="800" title="Spare Info" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['center','top']" >
	<div id="spareInfoDiv"></div>
</sj:dialog>
</div>
</div>
</body>
</html>