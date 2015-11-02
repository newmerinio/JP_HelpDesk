<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {
	var sel_id;
	sel_id = $("#gridedittable").jqGrid('getGridParam','selrow');
	if (sel_id==null || sel_id=='null' || sel_id=="") {
		alert("Please select one Employee !!!");
	}
	else {
    	$.ajax({
			 type : "post",
			 url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/registerFeedbackViaCall.action?selectedId="+sel_id+
			 "&feedbackBy=<s:property value="%{#parameters.feed_by}"/>&mobileno=<s:property value="%{#parameters.feed_by_mobno}"/>&emailId=<s:property value="%{#parameters.feed_by_emailid}"/>&bydept=<s:property value="%{#parameters.bydept}"/>&todept=<s:property value="%{#parameters.to_dept}"/>&subCatg=<s:property value="%{#parameters.subCatg}"/>&feed_brief=<s:property  value="%{#parameters.feed_brief}"/>&asset_id=<s:property  value="%{#parameters.assetid}"/>&viaFrom=<s:property  value="%{#parameters.viaFrom}"/>",
			 success : function(saveData) {
    				 $("#escalationMapResult").html(saveData);
				},
				error: function() {
				       alert("error");
				}
				});
	}
	});
</script>
</head>
<body>
<s:url id="emp4escalation_URL" action="getEmp4Escalation" escapeAmp="false">
<s:param name="todept" value="%{#parameters.to_dept}"></s:param>
<s:param name="subCatg" value="%{#parameters.subCatg}"></s:param>
<s:param name="dataFor" value="%{#parameters.dataFor}"></s:param>
</s:url>
<div id="escalationMapResult" align="center">
<center>
<div align="center">
<sjg:grid 
		  id="gridedittable"
          caption="Employee Detail"
          href="%{emp4escalation_URL}"
          gridModel="empData4Escalation"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		 		   <sjg:gridColumn 
		      						name="id"
		      						index="id"
		      						title="ID"
		      						align="center"
		      						width="75"
		      						hidden="true"
		 							/>
		       
		 		 <sjg:gridColumn 
		      						name="empName"
		      						index="empName"
		      						title="Employee Name"
		      						width="190"
		      						align="left"
		 							/>	
		 		 <sjg:gridColumn 
		      						name="mobOne"
		      						index="mobOne"
		      						title="Mobile No"
		      						width="180"
		      						align="center"
		 							/>	
		 		 <sjg:gridColumn 
		      						name="emailIdOne"
		      						index="emailIdOne"
		      						title="Email ID"
		      						width="300"
		      						align="left"
		 							/>				
		  </sjg:grid>
		  </div>
		  <br>
		  <div id="bt" style="display: block">
  		<sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelected4Save" button="true"  />
  	</div>
  	</center>
   </div>
</body>
</html>