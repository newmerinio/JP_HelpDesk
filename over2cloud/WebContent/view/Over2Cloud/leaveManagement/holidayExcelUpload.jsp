<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
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
	<div class="list-icon">
	 <div class="head">
	 Holiday</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Excel Upload</div> 
</div>
	<div class="clear"></div>
	<div class="border">
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
    <div class="container_block">
    <div style=" float:left; padding:20px 5%; width:90%;">

<s:form id="formtwo" action="uploadExcel" theme="css_xhtml" method="post" enctype="multipart/form-data">
			<s:hidden name="upload4" value="holidayList" />
			
       		 <div class="newColumn">
       <div class="leftColumn1">Select Excel:</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       		<s:file name="excel"/><div id="errAssetExcel" style="display: inline; width: 20px;">
       		</div></div></div>
       		<div class="newColumn">
       <div class="leftColumn1">Excel Format:</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       		<a href="<%=request.getContextPath()%>/view/Over2Cloud/leaveManagement/Holiday List.xls" ><font color="#194d65">Download </font></a>
       		</div>
    		</div>
    		<br>
    		<div class="clear"></div>
    		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
    		<br><br>
    		<div class="clear"></div>
    		
    		
       		<div class="type-button" >
       		<center>
        	<sj:submit
                  openDialog="HolidayBasicDialog"
                  value=" Upload "
                  targets="createHolidayBasic"
                  clearForm="true"
                  button="true"
                  cssStyle="margin-left: -77px;"
                  effect="highlight"
	              effectOptions="{ color : '#222222'}"
	              effectDuration="5000"
                  >
        	</sj:submit>
        	<sj:a cssStyle="margin-left: -21px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
        	<sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewHoliday();"
						cssStyle="margin-right: -170px;margin-top: -44px;"
						>
					  	Back
		   </sj:a>
        	<sj:dialog
                  id="HolidayBasicDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Holiday List Upload Via Excel"
                  width="600"
                  height="270"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="createHolidayBasic"></div>
        	</sj:dialog>
       		</center>
       		</div>
    		
    		
</s:form>

</div>
</div></div>

<sj:dialog 
id="uploadColumnAstDialog" 
modal="true" 
effect="slide" 
autoOpen="false" 
width="300" 
height="500" 
title="Holiday List Column Name" 
loadingText="Please Wait" 
overlayColor="#fff" 
overlayOpacity="1.0" 
position="['left','top']" >
<div id="uploadAstColumnDiv"></div>
</sj:dialog>
</body>
</html>
