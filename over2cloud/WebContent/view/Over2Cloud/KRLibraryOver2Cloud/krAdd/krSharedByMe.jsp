<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<s:url id="viewKR_URL" action="viewKrInGrid" escapeAmp="false"  >
<s:param name="shareStatus" value="%{shareStatus}"/>
<s:param name="searchTags" value="%{searchTags}"/>
<s:param name="fromDate" value="%{fromDate}"/>
<s:param name="toDate" value="%{toDate}"/>
<s:param name="searchValue" value="%{searchValue}"/>
<s:param name="searchField" value="%{searchField}"/>

</s:url>
<s:url id="modifyKR_URL" action="modifyKrInGrid" />
<sjg:grid 
		  id="gridedittable"
          href="%{viewKR_URL}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyKR_URL}"
          navigatorViewOptions="{height:450,width:450}"
          autowidth="true"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          loadonce="false"
          >
           <sjg:gridColumn 
				name="actions"
				index="actions"
				title="Action"
				width="60"
				align="center"
				editable="false"
				formatter="actionFormat"
				hidden="false"
				/>
          
		<s:iterator value="masterViewProp" id="masterViewProp" >  
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
</body>
</html>