<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/buddy/buddy.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });


</script>
</head>
<div class="clear"></div>

<div class="middle-content">
	<div class="list-icon">
	<div class="head">Buddy >> Excel Upload</div>
</div>
<div style=" float:left; width:100%; height: 350px;">
<div class="border">
<s:form id="formtwo" action="uploadExcel" theme="css_xhtml" method="post" enctype="multipart/form-data">
			<s:hidden name="upload4" value="buddyDetail" />
			   <div class="newColumn">
					<div class="leftColumn1">Select Excel:</div>
					<div class="rightColumn1">
						<s:file name="excel"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div>
                  	</div>
                  </div>
                    <div class="newColumn">
					<div class="leftColumn1">Excel Format:</div>
					<div class="rightColumn1">
						<a id='astUpload' href="#" onclick="getCurrent('uploadExcel','buddyDetail','uploadColumnBuddyDialog','uploadBuddyColumnDiv');">
       			              <font color="#194d65">Download</font>
       		            </a>
                  	</div>
                  </div>
    		
    		<center>
       		<div class="buttonStyle" style="text-align: center;">
        	<sj:submit
                  openDialog="buddyDialog"
                  value=" Upload "
                  targets="createBuddyBasic"
                  clearForm="true"
                  button="true"
                  onBeforeTopics="validExcelAsset"
                  >
        	</sj:submit>
        	  <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewPage();"
						cssStyle="margin-left: 180px;margin-top: -28px;"
						>
					  	Cancel
						</sj:a>
        	<sj:dialog
                  id="buddyDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Buddy Detail Upload Via Excel"
                  width="705"
                  height="300"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="createBuddyBasic"></div>
        	</sj:dialog>
       		</div>
    		</center>
</s:form>

</div>
</div>
<sj:dialog id="uploadColumnBuddyDialog" 
modal="true" 
effect="slide" 
autoOpen="false" 
width="300" 
height="500" 
title="Buddy Detail Column Name" 
loadingText="Please Wait" 
overlayColor="#fff" 
overlayOpacity="1.0" 
position="['left','top']" >
<div id="uploadBuddyColumnDiv"></div>
</sj:dialog>
</div>

</html>