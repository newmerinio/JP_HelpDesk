<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<s:url id="viewActivityBoardData" action="viewActivityBoardData" escapeAmp="false">
	<s:param name="maxDateValue" value="%{maxDateValue}"></s:param>
    <s:param name="minDateValue" value="%{minDateValue}"></s:param>
    <s:param name="taskType" value="%{taskType}"></s:param>
    <s:param name="fromDepart" value="%{fromDepart}"></s:param>
    <s:param name="toDepart" value="%{toDepart}"></s:param>
    <s:param name="lodgeMode" value="%{lodgeMode}"></s:param>
    <s:param name="feedStatus" value="%{feedStatus}"></s:param>
    <s:param name="closeMode" value="%{closeMode}"></s:param>
    <s:param name="escLevel" value="%{escLevel}"></s:param>
    <s:param name="escTAT" value="%{escTAT}"></s:param>
    <s:param name="searchField" value="%{searchField}"></s:param>
    <s:param name="searchString" value="%{searchString}"></s:param>
    <s:param name="severityLevel" value="%{severityLevel}"></s:param>
    </s:url>
		<sjg:grid 
	          id="gridedittable"
	          href="%{viewActivityBoardData}"
	          gridModel="masterViewProp"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          groupField="['status']"
    	 	  groupColumnShow="[true]"
    	  	  groupCollapse="true"
    	  	  groupText="['<b>{0}: {1} Tickets</b>']"
	          navigatorDelete="false"
	          navigatorEdit="false"
	          navigatorSearch="true"
	          editinline="false"
	          rowList="500,1000,2000"
	          rowNum="500"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          rownumbers="true"
	          pagerInput="true"   
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
	          multiselect="true"
          	>
			<s:iterator value="viewPageColumns" id="viewPageColumns" >
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
</body>
</html>