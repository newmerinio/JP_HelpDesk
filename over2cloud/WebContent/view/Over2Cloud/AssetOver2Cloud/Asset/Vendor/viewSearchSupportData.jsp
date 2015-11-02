<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<div style="overflow: scroll; height: 400px;">
<s:hidden id="moduleName" name="moduleName" value="%{moduleName}"/>
<s:url id="viewSupport" action="viewSupportData" escapeAmp="false">
<s:param name="moduleName"  value="%{moduleName}" />
<s:param name="frequency"  value="%{frequency}" />
<s:param name="dueDate"  value="%{dueDate}" />
<s:param name="searchField" value="%{searchField}"></s:param>
<s:param name="searchString" value="%{searchString}"></s:param>
<s:param name="searchOper" value="%{searchOper}"></s:param>
</s:url>
<sjg:grid 
		 
          id="gridedittable"
          href="%{viewSupport}"
          gridModel="masterViewList"
          groupField="['actionStatus']"
          groupColumnShow="[true]"
          groupCollapse="true"
          groupText="['<b>{0}-{1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          editinline="false"
          rowList="15,50,100"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="false"   
          multiselect="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          >
		<s:iterator value="masterViewProp"  status="test"> 
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="165"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		</s:iterator> 
</sjg:grid> 
</div>