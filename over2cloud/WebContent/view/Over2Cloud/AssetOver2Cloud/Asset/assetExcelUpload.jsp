<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<script src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script src="<s:url value="/js/asset/AssetCommonValidation.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

</script>
</head>
<body>
<div class="clear"></div>

<div class="middle-content">
<s:form id="formtwo" action="uploadExcel" theme="css_xhtml" method="post" enctype="multipart/form-data">
			<s:hidden name="upload4" value="assetDetail" />
			<div class="form_menubox">
       		<div class="user_form_text">Select Excel</div>
       		<div class="user_form_input">
       		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       		<s:file name="excel"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div></div>
       		<div class="user_form_text1">Excel Format</div>
       		<div class="user_form_input">
       		<a id='astUpload' style="cursor: pointer;text-decoration: none;"  onclick="getCurrentColumn('uploadExcel','assetDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')">
       			<font color="#194d65">Download</font>
       		</a>
       		</div>
    		</div>
    		
    		<div class="form_menubox">
    		<center>
       		<div class="type-button">
        	<sj:submit
                  value=" Upload "
                  targets="dataDiv"
                  clearForm="true"
                  button="true"
                  onBeforeTopics="validExcelAsset"
                  >
        	</sj:submit>
       		</div>
    		</center>
    		</div>
</s:form>
</div>
</body>
</html>
