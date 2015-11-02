<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

		<table border="1" width="100%" style="border-collapse: collapse;">
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Group Name:</td>
							<td width="10%"><s:property value="%{groupName}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%">Brief:</td>
							<td width="10%"><s:property value="%{comment}"/></td>
				</tr>
				<!--<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%">Create Type:</td>
							<td width="10%"><s:property value="%{createType}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%">View Type:</td>
							<td width="10%"><s:property value="%{viewType}"/></td>
				</tr>
		--></table>
<div style=" float:left; padding:10px 1%; width:97%;">
		<div class="clear"></div>	 	
<s:url id="viewMailGroupDetails1111" action="viewMailGroupDataGrid" escapeAmp="false">
	<s:param name="groupId" value="%{id}"></s:param>
</s:url>
<s:hidden id="records" value="%{records}"></s:hidden>
<div style="overflow: scroll; height: 400px;">
<sjg:grid 
		  id="gridBasicDetail"
          href="%{viewMailGroupDetails1111}"
          gridModel="groupdataViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorEdit="true"
          navigatorDelete="true"
          navigatorSearch="true"
          navigatorRefresh="true"
          navigatorSearch="true"
          rowList="15,20,250"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyClient}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false" 
          autowidth="true"    
          onSelectRowTopics="rowselect"
          >
       <s:iterator value="%{crmgroupViewProp}" id="crmgroupViewProp1" >  
		<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}" 
			title="%{headingName}"
			width="%{80}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
		/>
		</s:iterator>
	</sjg:grid>
	</div>
</div>		
		
</body>
</html>