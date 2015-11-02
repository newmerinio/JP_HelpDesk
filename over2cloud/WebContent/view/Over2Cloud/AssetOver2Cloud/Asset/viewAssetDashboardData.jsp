 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<body>
<s:url id="viewAllotment11" action="viewAllotmentDashboardData" escapeAmp="false">
<s:param name="tableName" value="%{tableName}" />
<s:param name="tableAllis" value="%{tableAllis}" />
<s:param name="value" value="%{value}" />
</s:url>
<sjg:grid 
		  id="allotmentGrid11"
          caption="%{mainHeaderName}"
          href="%{viewAllotment11}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="false"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          loadonce="true"
          >
		<s:iterator value="columnMap">
			<sjg:gridColumn 
			name="%{key}"
			index="%{key}"
			title="%{value}"
			width="%{width}"
			align="center"
			editable="false"
			search="true"
			hidden="false"
			/>
		</s:iterator>  
</sjg:grid>
</body>
</html>