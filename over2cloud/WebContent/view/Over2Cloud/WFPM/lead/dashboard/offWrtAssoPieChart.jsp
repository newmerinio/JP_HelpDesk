<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:url var="chartDataUrl" action="json-chart-data"/>
<sjc:chart
    	id="chartPieoffWrtAssoMap%{legendShow}"
    	cssStyle="width: %{width}px; height: %{height}px; margin: auto;"
    	legendShow="%{legendShow}"
    	legendPosition="sw"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{offWrtAssoMap}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
</sjc:chart>