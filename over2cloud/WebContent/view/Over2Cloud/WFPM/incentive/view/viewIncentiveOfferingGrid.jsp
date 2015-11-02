<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

	<s:url id="viewIncentiveForOfferingOuterGridURL" action="viewIncentiveForKpiOuterGrid" escapeAmp="false">
		<s:param name="incentiveType" value="%{incentiveType}"></s:param>
	</s:url>
	<s:url id="viewIncentiveForOfferingInnerGridURL" action="viewIncentiveForOfferingInnerGrid" escapeAmp="false">
	</s:url>
	
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridOfferingOuter"
          href="%{viewIncentiveForOfferingOuterGridURL}"
          gridModel="viewIncentiveKpiGridOuter"
          navigator="true"
          navigatorEdit="true"
          navigatorDelete="true"
          navigatorSearch="false"
          navigatorRefresh="true"
          navigatorAdd="false"
          navigatorView="true"
          rowList="15,500,1000"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          >
          
     <sjg:grid 
		  id="gridOfferingInner"
          href="%{viewIncentiveForOfferingInnerGridURL}"
          gridModel="viewIncentiveOfferingGridInner"
          groupField="['applicable_from']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>Applicable From: {0},  Total Offering: {1}</b>']"
          navigator="true"
          navigatorEdit="true"
          navigatorDelete="true"
          navigatorSearch="false"
          navigatorRefresh="true"
          navigatorAdd="false"
          navigatorView="true"
          rowList="25,500,1000,5000"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"
          loadingText="Requesting Data..."  
          rowNum="500"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          autowidth="true"
          shrinkToFit="false"
          onSelectRowTopics="rowSelect"
          >
          
         <sjg:gridColumn 
            id="iddinctv"
            name="id"
     		title="Id"
     		editable="false"
     		sortable="false"
    		align="center"
    		hidden="true"
    		width="70"
    		/>
         <sjg:gridColumn 
            id="offering"
            name="offering"
     		title="Offering"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="610"
    		/>
    		<sjg:gridColumn 
            id="slabFrom"
            name="slab_from"
     		title="Slab From"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="150"
    		/>
    		 <sjg:gridColumn 
            id="slabTo"
            name="slab_to"
     		title="Slab To"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="150"
    		/>
    		 <sjg:gridColumn 
            id="incentiveValue"
            name="incentive_value"
     		title="Value"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="150"
    		/>
          <sjg:gridColumn 
            id="incentiveinid"
            name="incentive_in"
     		title="Incentive In"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="170"
    		/>
          <sjg:gridColumn 
            id="applicable_from"
            name="applicable_from"
     		title="Applicable From"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="150"
    		/>
		 </sjg:grid>
          
         <!-- ------------------------------------------------------------------------------------------------- --> 
         <sjg:gridColumn 
            id="id"
            name="id"
     		title="Id"
     		editable="false"
     		sortable="false"
    		align="center"
    		hidden="true"
    		width="70"
    		/>
         <sjg:gridColumn 
            id="empName"
            name="empName"
     		title="Employee Name"
     		editable="false"
     		sortable="false"
    		align="left"
    		width="630"
    		/>
         <sjg:gridColumn 
            id="mobile"
            name="mobile"
     		title="Mobile No."
     		editable="false"
     		sortable="false"
    		align="center"
    		width="620	"
    		/>
		</sjg:grid>
</div>