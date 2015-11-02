<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
</head>
<body>

<s:url id="viewKrUpload" action="krUploadViewData" escapeAmp="false">
<s:param name="searchField" value="%{searchField}"/>
<s:param name="searchString" value="%{searchString}"/>
<s:param name="fromDate" value="%{fromDate}"/>
<s:param name="toDate" value="%{toDate}"/>
</s:url>
<s:url id="viewModifyKrUpload" action="viewModifyKrUpload" />
<sjg:grid 
		  id="gridedittable"
          href="%{viewKrUpload}"
          gridModel="masterViewList"
          groupField="['group_name']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0}: {1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,30,45,60"
          rownumbers="-1"
          viewrecords="true"       
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {reloadPage();}}"
          navigatorViewOptions="{height:300,width:400}"
          navigatorEditOptions="{height:300,width:400,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{viewModifyKrUpload}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          pager="true"
          rownumbers="true"
          onSelectRowTopics="rowselect"
          >
          <sjg:gridColumn 
				name="actions"
				index="actions"
				title="Share"
				width="40"
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