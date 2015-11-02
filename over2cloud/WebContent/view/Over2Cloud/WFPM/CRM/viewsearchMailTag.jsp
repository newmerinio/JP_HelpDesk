<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:url id="viewMailTag" action="viewMailTagDataGrid" escapeAmp="false">
	
	<s:param name="searchField" value="%{searchField}"></s:param>
	<s:param name="searchString" value="%{searchString}"></s:param>
</s:url>
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="viewMailTagId"
          href="%{viewMailTag}"
          gridModel="mailTagViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          navigatorRefresh="true"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          autowidth="true"
          
          >
          
		<s:iterator value="mailTagViewProp" id="mailTagViewProp" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}" 
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		formatter="%{formatter}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator>  
	</sjg:grid>
	</div>