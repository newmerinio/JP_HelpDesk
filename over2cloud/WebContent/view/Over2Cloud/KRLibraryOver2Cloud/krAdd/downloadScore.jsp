<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table width="100%" border="1">
     	 <s:iterator value="%{fullViewMap}" status="status">
			<s:if test="#status.odd">
				<tr  bgcolor="lightgrey" style="height: 25px">
			</s:if>
				<td align="left" width="25%"><strong><s:property value="%{key}"/>:</strong></td>
				<td align="left" width="25%"><s:property value="%{value}"/></td>
			<s:if test="#status.even">
			</s:if>
		</s:iterator>
</table>
	 
 <s:url id="viewKR_URLDownload" action="viewKrInGridDownload" escapeAmp="false"  >
 <s:param name="docName" value="%{docName}"></s:param>
</s:url>
<sjg:grid 
		  id="downloadScoreGrid"
          href="%{viewKR_URLDownload}"
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
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorViewOptions="{height:450,width:450}"
          autowidth="true"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          loadonce="true"
          >
         <sjg:gridColumn 
				name="downloadBy"
				index="downloadBy"
				title="Download By"
				width="310"
				align="center"
				editable="false"
				hidden="false"
				/> 
		 <sjg:gridColumn 
				name="date"
				index="date"
				title="Date/Time"
				width="310"
				align="center"
				editable="false"
				hidden="false"
				/>
				
</sjg:grid>    
	  
	  
</body>
</html>