<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="page_title"><h1><s:property value="%{mainHeaderName}"/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">

<!-- Level 1 to 5 Data VIEW URL CALLING Part Starts HERE -->
<s:url id="viewFeedbackInGrid" action="beforeDashboardFeedViewGrid" escapeAmp="false">
<s:param name="subDept" value="%{subDept}"></s:param>
<s:param name="searchFlag" value="%{searchFlag}"></s:param>
</s:url>
<center>
<sjg:grid 
		  id="gridedittable"
          caption="%{mainHeaderName}"
          href="%{viewFeedbackInGrid}"
          gridModel="gridModel"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyFeedback}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          >
		<sjg:gridColumn 
		name="id"
		index="id"
		title="Id"
		align="center"
		editable="false"
		hidden="true"
		/> 
		<sjg:gridColumn 
		name="name"
		index="name"
		title="Patient Name"
		width="100"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="mobNo"
		index="mobNo"
		title="Mobile No"
		width="80"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="emailId"
		index="emailId"
		title="Email Id"
		width="120"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="doctName"
		index="doctName"
		title="Doctor Name"
		width="100"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="feedDate"
		index="feedDate"
		title="Date"
		width="100"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="feedTime"
		index="feedTime"
		title="Time"
		width="50"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="purpose"
		index="purpose"
		title="Purpose Of Visit"
		width="140"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="commnts"
		index="commnts"
		title="Comments"
		width="140"
		align="center"
		editable="false"
		/> 
</sjg:grid>

</center>
</div>
</div>

</center>
</div>
</div>
</body>
</html>