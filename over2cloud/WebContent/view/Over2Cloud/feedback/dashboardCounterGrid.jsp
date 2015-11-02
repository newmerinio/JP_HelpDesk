<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="viewCounter" action="viewCounterDataInGrid" escapeAmp="false">
	<s:param name="headerName" value="%{headerName}"></s:param>
		<s:param name="counterType" value="%{counterType}"></s:param>
		<s:param name="searchFiled" value="%{searchFiled}"></s:param>
		<s:param name="deptName" value="%{deptName}"></s:param>
</s:url>
<div class="border" style="height: 300px; overflow: auto;">

<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<!--<a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a>
<a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a>
--> 
<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="getCurrentColumn('downloadAllData','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" >Download</sj:a>	
<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="importAsset();">Import</sj:a>
</td> 
</tr></tbody></table></div></div></div>

<center>
<sjg:grid 
		  id="gridedittable"
          href="%{viewCounter}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="15"
          editurl="%{viewModifyFeedback}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="false"
          loadonce="true"
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
<br><br>
</center></div>
</body>
</html>