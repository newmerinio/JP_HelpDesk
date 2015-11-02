<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<script type="text/javascript">
</script>
</head>
<body>

<div style="width: 100%"></div>
<s:url id="viewAudit" action="viewAuditReport" namespace="/view/Over2Cloud/feedback/feedback_Activity" escapeAmp="false">
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
</s:url>
<div style="overflow: scroll;">
<sjg:grid 
		 
          id="auditGrid"
          href="%{viewAudit}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="true"
          editinline="false"
          rowList="100,150,500"
          rowNum="100"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="true"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          
          >
        <sjg:gridColumn 
			name="id"
			index="id"
			title="Id"
			width="65"
			align="left"
			editable="false"
			hidden="true"
		/>
		
		<sjg:gridColumn 
			name="clientId"
			index="clientId"
			title="MRD No"
			width="70"
			align="center"
			editable="false"
		/>
		<sjg:gridColumn 
			name="patientId"
			index="patientId"
			title="Patient Id"
			width="70"
			align="center"
		/>
		 
		<sjg:gridColumn 
			name="clientName"
			index="clientName"
			title="Patient Name"
			width="140"
			align="center"
		/>
		
		<sjg:gridColumn 
			name="action_type"
			index="action_type"
			title="Action Type"
			width="80"
			align="center"
		/>
		
		<sjg:gridColumn 
			name="comments"
			index="comments"
			title="Comments"
			width="250"
			align="center"
		/>
		
		<sjg:gridColumn 
			name="action_by"
			index="action_by"
			title="Action By"
			width="100"
			align="center"
		/>
		
		<sjg:gridColumn 
			name="feedDataId"
			index="feedDataId"
			title="feedDataId"
			width="80"
			align="center"
			hidden="true"
		/>
		
</sjg:grid>
</div>
</body>
</html>