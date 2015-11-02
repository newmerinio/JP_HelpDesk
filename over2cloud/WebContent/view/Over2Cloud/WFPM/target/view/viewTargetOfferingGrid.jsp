<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

	<s:url id="viewTargetForOfferingOuterGridURL" action="viewTargetForKpiOuterGrid" escapeAmp="false">
		<s:param name="targetType" value="%{targetType}"></s:param>
	</s:url>
	<s:url id="viewTargetForOfferingInnerGridURL" action="viewTargetForOfferingInnerGrid" escapeAmp="false">
	</s:url>
	
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridOfferingOuter"
          href="%{viewTargetForOfferingOuterGridURL}"
          gridModel="viewTargetKpiGridOuter"
          navigator="true"
          navigatorEdit="true"
          caption=""
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
          href="%{viewTargetForOfferingInnerGridURL}"
          gridModel="viewTargetOfferingGridInner"
          groupField="['applicableFrom']"
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
            id="idd"
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
    		width="400"
    		/>
         <sjg:gridColumn 
            id="weightage"
            name="weightage"
     		title="Weightage"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="400"
    		/>
         <sjg:gridColumn 
            id="targetValue"
            name="targetValue"
     		title="Target Value"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="440"
    		/>
         <sjg:gridColumn 
            id="applicableFrom"
            name="applicableFrom"
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
    		width="610"
    		/>
         <sjg:gridColumn 
            id="mobile"
            name="mobile"
     		title="Mobile No."
     		editable="false"
     		sortable="false"
    		align="center"
    		width="640"
    		/>
		</sjg:grid>
</div>