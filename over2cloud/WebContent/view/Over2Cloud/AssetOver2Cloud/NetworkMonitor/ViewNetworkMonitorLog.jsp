<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>

</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Network Monitor</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Log</div> 
</div>
<div class="clear"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				     <!--<%if(userRights.contains("taty_ADD")) {%>-->
							   <sj:a id="viewButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="viewNetMonitorLog();">Download</sj:a>
	      			<!--<%}%>
				  --></td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:url id="viewNetWorkLog" action="ViewNetworkMonitorLog" />
<sjg:grid 
		 
          id="gridedittable"
          href="%{viewNetWorkLog}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          editinline="false"
          rowList="14,50,100"
          rowNum="14"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="false"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="165"
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
</div>
</body>

</html>