<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>


<s:url id="viewmailGrid" action="instantmailreporturl" >
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{viewmailGrid}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="15,25,50"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          editurl="%{viewModifyGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          onSelectRowTopics="rowselect"
          shrinkToFit="false"
          autowidth="true"
          >
             	
		<s:iterator value="viewInstantMsgGrid" id="viewInstantMsgGrid">  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{145}"
		align="%{align}"
		editable="%{editable}"
		formatter="%{formatter}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator> 
</sjg:grid>