<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="qualityDataURL" action="viewQualityDetail" escapeAmp="false">
<s:param name="deptId" value="%{deptId}"/>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="moduleName" value="%{moduleName}"></s:param>
</s:url>
<sjg:grid 
		  id="qualityProductivity"
          href="%{qualityDataURL}"
          gridModel="feedbackList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,25,50"
          viewrecords="true"       
          pager="true"
          rownumbers="-1"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="20"
          autowidth="true"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          userDataOnFooter="true"
          footerrow="true"
          >
         	 <sjg:gridColumn 
		    	name="id"
		      	index="id"
		      	title="Id"
		      	width="80"
		      	align="center"
		      	hidden="true"
		      	key="true"
		 	/>
		  	<sjg:gridColumn 
		    	name="catName"
		      	index="catName"
		      	title="Category"
		      	width="300"
		      	align="center"
		      	hidden="false"
		 	/>
		 	
		 	<sjg:gridColumn 
		    	name="rat1"
		      	index="rat1"
		      	title="Poor"
		      	width="100"
		      	align="center"
		      	hidden="false"
		 	/>
		 	<sjg:gridColumn 
		    	name="rat2"
		      	index="rat2"
		      	title="Average"
		      	width="100"
		      	align="center"
		      	hidden="false"
		 	/>
		 	<sjg:gridColumn 
		    	name="rat3"
		      	index="rat3"
		      	title="Good"
		      	width="100"
		      	align="center"
		      	hidden="false"
		 	/>
		 	<sjg:gridColumn 
		    	name="rat4"
		      	index="rat4"
		      	title="Very Good"
		      	width="100"
		      	align="center"
		      	hidden="false"
		 	/>
		 	<sjg:gridColumn 
		    	name="rat5"
		      	index="rat5"
		      	title="Excellent"
		      	width="100"
		      	align="center"
		      	hidden="false"
		 	/>
  </sjg:grid>

</body>
</html>