<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
	<s:url id="viewLeadGrid" action="viewLeadGrid" escapeAmp="false">
		<s:param name="status" value="%{status}"></s:param>
		<s:param name="id" value="%{id}"></s:param>
		<s:param name="industry" value="%{industry}"></s:param>
		<s:param name="subIndustry" value="%{subIndustry}"></s:param>
		<s:param name="starRating" value="%{starRating}"></s:param>
		<s:param name="sourceName" value="%{sourceName}"></s:param>
		<s:param name="location" value="%{location}"></s:param>
		<s:param name="searchField" value="%{searchField}"></s:param>
		<s:param name="searchString" value="%{searchString}"></s:param>
		<s:param name="searchOper" value="%{searchOper}"></s:param>
		<s:param name="lead_name_wild" value="%{lead_name_wild}"></s:param>
	</s:url>
	<s:url id="deleteLeadGridOper" action="deleteLeadGridOper" />
	
	<s:url id="viewLeadContact" action="viewLeadContact">
		<s:param name="status" value="%{status}"></s:param>
	</s:url>
	
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridedittable"
          href="%{viewLeadGrid}"
          gridModel="masterViewList"
          navigator="true"
          navigatorEdit="true"
          navigatorDelete="true"
          navigatorSearch="false"
          navigatorRefresh="true"
          navigatorAdd="false"
          navigatorView="true"
          rowList="15,20,25"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewLeadGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          autowidth="true"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
          
        <s:if test="%{showAction != 1}">
         <sjg:gridColumn 
            name="imagename"
            name="imagename"
     		title="Actions"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="70"
    		formatter="formatImage"
    		/>
   		</s:if>
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="%{width}"
			align="%{align}"
			editable="%{editable}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		</s:iterator>  
		</sjg:grid>
</div>