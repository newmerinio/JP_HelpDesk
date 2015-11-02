<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="list-icon">
		<div class="head"><s:property value="%{mainHeaderName}"/></div>
	</div>
	<div style="padding:20px 1%; width:98%;">
		 	<div class="clear"></div>
		 	<s:url id="viewFeedbackInGrid1" action="viewFeedbackInGridDashboard" namespace="/view/Over2Cloud/feedback" escapeAmp="false">
		 		<s:param name="mode" value="%{mode}"></s:param>
				<s:param name="searchFor" value="%{searchFor}"></s:param>
		 	</s:url>
<div class="listviewBorder">
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{viewFeedbackInGrid1}"
		          gridModel="masterViewList"
		          groupField="['targetOn']"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="false"
		          navigatorDelete="%{deleteFlag}"
		          navigatorEdit="%{modifyFlag}"
		          navigatorSearch="false"
		          rowList="10,15,20,25"
		          rownumbers="-1"
		          viewrecords="true"       
		          multiselect="false"  
		          loadingText="Requesting Data..."  
		          rowNum="15"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
		          editurl="%{viewModifyFeedback}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          pager="true"
		          >
				<s:iterator value="masterViewProp" id="masterViewProp" >  
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
		
		<center>
         <sj:dialog 
      	id="downloaddailcontactdetails" 
      	 	  		buttons="{ 
    		'Ok Download':function() {okdownload();},
    		}"  
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Select Fields"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="390"
		height="300"
    />
  </center>
		</div></div>
</body>
</html>