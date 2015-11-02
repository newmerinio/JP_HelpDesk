<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">

</script>

</head>
<body class="style-3">
<s:url id="viewDeptData" action="upcomingActivitiesView" escapeAmp="false">
<s:param name="duration" value="%{duration}"></s:param>
<s:param name="stage" value="%{stage}"></s:param>

</s:url>
<div class="clear"></div>
<div class="rightHeaderButton">
<span class="normalDropDown"> 

 </span>
 </div>
 
 <div class="clear"></div>
 <div class="" style="overflow:auto;">

<sjg:grid 
		  id="gridedittable"
          href="%{viewDeptData}"
          gridModel="masterViewProps"
          groupField="['activity']"
	      groupColumnShow="[true]"
	      groupCollapse="false"
	      groupText="['<b>{0}: {1}</b>']"
	      groupOrder="['desc']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="20,30,40"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          rowNum="20"
          autowidth="false"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{editDeptDataGrid}"
          shrinkToFit="true"
         
          >
          
          
		<s:iterator value="masterViewProps" id="masterViewProps" > 
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator>  
</sjg:grid>


</div>

</body>
</html>