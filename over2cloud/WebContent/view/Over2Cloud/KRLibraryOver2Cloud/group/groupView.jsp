<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

<s:url id="viewGroup" action="viewGroupGrid" escapeAmp="false">
<s:param name="searchFields" value="%{searchFields}"/>
<s:param name="searchStr" value="%{searchStr}"/>
<s:param name="searchingData" value="%{searchingData}"/>
<s:param name="fieldName" value="%{fieldName}"/>
<s:param name="fieldvalue" value="%{fieldvalue}"/>
</s:url>

<s:url id="modifyGroup" action="modifyGroupGrid" />

<s:url id="viewSubGroup" action="viewSubGroupGrid" escapeAmp="false">
</s:url>
<s:url id="modifySubGroup" action="modifySubGroupGrid" />

<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewGroup}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="20"
          navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyGroup}"
          shrinkToFit="true"
          autowidth="true"
          onSelectRowTopics="rowselect"
          navigatorViewOptions="{height:230,width:400}"
          >
          <s:if test="groupLevel > 1">
          <sjg:grid 
		  id="gridContactDetails"
          caption="%{subGroupLevelName}"
          href="%{viewSubGroup}"
          gridModel="masterViewListForSubGroup"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="20"
          navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifySubGroup}"
          navigatorViewOptions="{height:230,width:500}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          >
		<s:iterator value="masterViewPropForSubGroup" id="masterViewPropForSubGroup" >  
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
        </s:if>  
          
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