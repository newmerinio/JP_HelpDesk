<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:url id="viewClientSupport" action="viewClientSupportGrid" escapeAmp="false">
	<s:param name="fromDateSearch" value="%{fromDateSearch}"></s:param>	
	<s:param name="fromDate" value="%{fromDate}"></s:param>	
	<s:param name="searchFor" value="%{searchFor}"></s:param>	
	<s:param name="clientName" value="%{clientName}"></s:param>
	<s:param name="supportStatus" value="%{supportStatus}"></s:param>	
	<s:param name="offeringName" value="%{offeringName}"></s:param>	
	<s:param name="support_type" value="%{support_type}"></s:param>	
	<s:param name="ClientType" value="%{ClientType}"></s:param>	
	
	
</s:url>
<s:url id="viewModifyClientSupport" action="viewModifyClientSupportAction" />
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridClientSupportDetails"
          href="%{viewClientSupport}"
          gridModel="supportViewList"
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
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          editurl="%{viewModifyClientSupport}"
          >
     
		<s:iterator value="supportViewColum" id="supportViewColum"> 
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