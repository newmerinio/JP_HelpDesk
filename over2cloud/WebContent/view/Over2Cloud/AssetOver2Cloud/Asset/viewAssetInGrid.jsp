<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div style="overflow: scroll; height: 400px;">
	<s:url id="modifyAsset" action="modifyAsset" />
	<s:url id="viewAsset" action="viewAssetGridData" escapeAmp="false">
	<s:param name="searchField" value="%{searchField}"></s:param>
    <s:param name="searchString" value="%{searchString}"></s:param>
    <s:param name="searchOper" value="%{searchOper}"></s:param>
    <s:param name="selectedOutId" value="%{selectedOutId}"></s:param>
    </s:url>
		<sjg:grid 
	          id="gridedittable"
	          href="%{viewAsset}"
	          gridModel="masterViewList"
	          groupField="['associateName']"
          	  groupColumnShow="[true]"
          	  groupCollapse="true"
          	  groupText="['<b>{0}, Total Asset: {1}</b>']"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="%{deleteFlag}"
	          navigatorEdit="%{modifyFlag}"
	          navigatorSearch="true"
	          editinline="false"
	          rowList="14,100,200"
	          rowNum="14"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          rownumbers="-1"
	          pagerInput="false"   
	          multiselect="false"
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          loadingText="Requesting Data..."  
	          navigatorEditOptions="{height:230,width:400}"
	          editurl="%{modifyAsset}"
	          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          onEditInlineSuccessTopics="oneditsuccess"
          	>
          	<sjg:gridColumn 
					name="barCode"
					index="barCode"
					title="Bar Code"
					width="100"
					editable="false"
					search="false"
					align="center"
					formatter="printBarCode"
				/>
			<s:iterator value="masterViewProp" id="masterViewProp" >
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="center"
					editable="%{editable}"
					formatter="%{formatter}"
					search="%{search}"
					hidden="%{hideOrShow}"
				/>
			</s:iterator>    
		</sjg:grid>
		</div>
</body>
</html>