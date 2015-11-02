<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:url var="chartDataUrl" action="json-chart-data"/>

<sjc:chart
		    	id="chartPieLeadMax"
		    	cssStyle="width: 450px; height: 450px; margin: auto;"
		    	legendShow="true"
		    	legendPosition="sw"
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