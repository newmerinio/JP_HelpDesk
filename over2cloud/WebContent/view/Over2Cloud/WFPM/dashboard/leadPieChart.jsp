<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:url var="chartDataUrl" action="json-chart-data"/>

<sjc:chart
		    	id="chartPieLead%{legendShow}"
		    	cssStyle="width: %{width}px; height: %{height}px; margin: auto;"
		    	legendShow="%{legendShow}" 
		    	pie="true"
		    	pieLabel="true" 
						    	
		    >
		    	<s:iterator value="%{leadMap}">
			    	<sjc:chartData
			    		label="%{key}"
			    		data="%{value}" 
			    	/>
		    	</s:iterator>
	    </sjc:chart>