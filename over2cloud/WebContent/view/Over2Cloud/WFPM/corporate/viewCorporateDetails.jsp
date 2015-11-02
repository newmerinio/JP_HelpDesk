<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<!-- Code End for Header Strip -->
 <div class="clear"></div>
 <div style="overflow: scroll; height: 430px;">
<s:url id="viewCorporateData" action="viewCorporateData" escapeAmp="false">
	<s:param name="from_date" value="%{from_date}"></s:param>
	    <s:param name="to_date" value="%{to_date}"></s:param>
	    <s:param name="searchParameter" value="%{searchParameter}"></s:param>

 <s:param name="action_status" value="%{action_status}"></s:param>
	      <s:param name="data_status" value="%{data_status}"></s:param>
	    <s:param name="searchField" value="%{searchField}"></s:param>
		<s:param name="searchString" value="%{searchString}"></s:param>
		<s:param name="searchOper" value="%{searchOper}"></s:param>
		<s:param name="corp_type" value="%{corp_type}"></s:param>
		<s:param name="packages" value="%{packages}"></s:param>
		
</s:url>
<s:url id="editCorporateDataGrid" action="editCorporateDataGrid" />
<sjg:grid 
		  id="gridCorporate"
          href="%{viewCorporateData}"
          gridModel="viewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,200,500"
          rownumbers="-1"
          rownumbers="false"
          multiselect="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="50"
          autowidth="true"
          editurl="%{editCorporateDataGrid}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="masterViewProp" id="masterViewProp" > 
				<s:if test="%{colomnName=='status'}">
					<sjg:gridColumn 
						name="%{colomnName}"
						index="%{colomnName}"
						title="%{headingName}"
						width="%{width}"
						align="%{align}"
						editable="true"
						search="%{search}"
						hidden="%{hideOrShow}"
						formatter="%{formatter}"
						edittype="select"
						editoptions="{value:'active:Active;Inactive:Inactive'}"
						/>
				
				</s:if>
				<s:else>
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					formatter="%{formatter}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
		</s:iterator>  
</sjg:grid>
</div>

</body>


</html>