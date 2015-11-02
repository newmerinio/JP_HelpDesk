<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:url var="chartDataUrl" action="json-chart-data"/>

<sjc:chart
		    	id="chartPieClient%{legendShow}"
		    	cssStyle="width: %{width}px; height: %{height}px; margin: auto;"
		    	legendShow="%{legendShow}" 
		    	pie="true"
		    	pieLabel="true" 
						    	
		    >
		    	<s:iterator value="%{clientMap}">
			    	<sjc:chartData
			    		label="%{key}"
			    		data="%{value}" 
			    	/>
		    	</s:iterator>
	    </sjc:chart>