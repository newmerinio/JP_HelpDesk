<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

	<s:url id="viewTargetForKpiOuterGridURL" action="viewTargetForKpiOuterGrid" escapeAmp="false">
		<s:param name="targetType" value="%{targetType}"></s:param>
	</s:url>
	<s:url id="viewTargetForKpiInnerGridURL" action="viewTargetForKpiInnerGrid" escapeAmp="false">
	</s:url>
	
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridKpiOuter"
          href="%{viewTargetForKpiOuterGridURL}"
          gridModel="viewTargetKpiGridOuter"
          caption=""
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
		  id="gridKpiInner"
          href="%{viewTargetForKpiInnerGridURL}"
          gridModel="viewTargetKpiGridInner"
          groupField="['applicableFrom']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>Applicable From: {0},  Total KPI: {1}</b>']"
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
            id="kra"
            name="kra"
     		title="KRA"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="400"
    		/>
         <sjg:gridColumn 
            id="kpi"
            name="kpi"
     		title="KPI"
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
    		width="200"
    		/>
         <sjg:gridColumn 
            id="targetValue"
            name="targetValue"
     		title="Target Value"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="230"
    		/>
         <sjg:gridColumn 
            id="applicableFrom"
            name="applicableFrom"
     		title="Applicable From"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="170"
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