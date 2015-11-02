<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:url id="viewOpportunity" action="viewOpportunityGrid" escapeAmp="false">
	<s:param name="fromDateSearch" value="%{fromDateSearch}"></s:param>	
	<s:param name="fromDate" value="%{fromDate}"></s:param>	
	<s:param name="client_Name" value="%{client_Name}"></s:param>	
	<s:param name="acManager" value="%{acManager}"></s:param>
	<s:param name="offeringOpportunity" value="%{offeringOpportunity}"></s:param>	
	<s:param name="opportunityType" value="%{opportunityType}"></s:param>	
	<s:param name="salesstageName" value="%{salesstageName}"></s:param>	
	<s:param name="expectency" value="%{expectency}"></s:param>	
	<s:param name="status" value="%{status}"></s:param>
	<s:param name="searchParameter" value="%{searchParameter}"></s:param>
	<s:param name="searchField" value="%{searchField}"></s:param>
	<s:param name="searchString" value="%{searchString}"></s:param>
	<s:param name="searchOper" value="%{searchOper}"></s:param>
</s:url>

<s:url id="viewModifyOpportunity" action="viewModifyOpportunityAction" />
<div style="overflow: scroll; height: 450px;"  cssClass="FixedHeader">

<sjg:grid 
		  id="gridOpportunityDetails"
          href="%{viewOpportunity}"
          gridModel="opportunityViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorEdit="true"
          navigatorDelete="true"
          navigatorSearch="true"
          navigatorRefresh="true"
          navigatorSearch="true"
          rowList="15,20,250"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
	      pagerButtons="true"
	      pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          footerrow = "true"
          userDataOnFooter="true"
          editurl="%{viewModifyOpportunity}"
          >
     
		<s:iterator value="opportunityViewProp" id="opportunityViewProp" > 
		<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}" 
			title="%{headingName}"
			width="%{width}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			
		/>
		</s:iterator>  
	</sjg:grid>
	</div>