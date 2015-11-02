<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script src="<s:url value="/js/asset/ProcValidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
         
        });
</script>
<%-- <script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script> --%>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
</head>
<body>
<div class="middle-content">
	<div class="list-icon">
	<div class="head">Asset Support >> Excel Upload</div>
</div>
<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">
<div class="clear"></div>
<div class="clear"></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">

<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Asset Support >> Excel Upload" id="twoId">
<s:form id="formtwo" action="uploadExcel" theme="css_xhtml" method="post" enctype="multipart/form-data">
			<s:hidden name="upload4" value="assetSupport" />
			<div class="form_menubox">
       		<div class="user_form_text">Select Excel</div>
       		<div class="user_form_input"><s:file name="excel"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div></div>
       		<div class="user_form_text1">Excel Format</div>
       		<div class="user_form_input">
       		<a id='astProcUpload' onclick="getCurrentColumn('uploadExcel','assetSupport','uploadColumnSupportDialog','uploadSupportColumnDiv')">
       			<font color="#194d65">Download</font>
       		</a>
       		</div>
    		</div>
    		
    		<div class="form_menubox">
    		<center>
       		<div class="type-button">
        	<sj:submit
                  openDialog="AssetSupportDialog"
                  value=" Upload "
                  targets="createSupportBasic"
                  clearForm="true"
                  button="true"
                  onBeforeTopics="validExcelAsset"
                  >
        	</sj:submit>
        	<sj:dialog
                  id="AssetSupportDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Assets Support Detail Upload Via Excel"
                  width="600"
                  height="350"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="createSupportBasic"></div>
        	</sj:dialog>
       		</div>
    		</center>
    		</div>
</s:form>
</sj:accordionItem>
</sj:accordion>
</div>
</div>
<sj:dialog id="uploadColumnSupportDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Asset Detail Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
	<div id="uploadSupportColumnDiv"></div>
</sj:dialog>
</div>
</div>
</div>
</div>
</body>
</html>
