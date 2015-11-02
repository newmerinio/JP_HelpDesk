<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<body>
<s:url id="viewMappedContact" action="viewMappedContact">
	<s:param name="dataFor" value="%{dataFor}"/>
</s:url>
<center>
<sjg:grid 
		  id="viewMappedContactId"
          href="%{viewMappedContact}"
          gridModel="masterViewList"
          groupField="['outlet']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0} Total Mapped: {1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="100,500,1000"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
	      onEditInlineSuccessTopics="oneditsuccess"
          >
        <s:if test="%{dataFor=='TAH'}">
		<s:iterator value="masterViewProp" id="masterViewProp" > 
			<sjg:gridColumn 
				name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="467"
				align="%{align}"
				editable="%{editable}"
				formatter="%{formatter}"
				search="%{search}"
				hidden="%{hideOrShow}"
			/>
		</s:iterator> 
		</s:if> 
		<s:else>
			<s:iterator value="masterViewProp" id="masterViewProp" > 
			<sjg:gridColumn 
				name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="309"
				align="%{align}"
				editable="%{editable}"
				formatter="%{formatter}"
				search="%{search}"
				hidden="%{hideOrShow}"
			/>
		</s:iterator> 
		</s:else>
</sjg:grid>
</center>
</body>
</html>