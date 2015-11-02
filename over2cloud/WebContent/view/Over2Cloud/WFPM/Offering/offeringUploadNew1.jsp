<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">
</script>
</head>
<body>
<div class="clear"></div>
<div class="list-icon">
	<div class="head">Offering</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:20%; float: left;"></div><div class="head">Excel Upload</div>
</div>
<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">

<div class="clear"></div>
<div class="border">
<s:form name="offeringForm" namespace="/view/Over2Cloud/wfpm/offering" action="uploadOfferingExcelNew" theme="simple"  method="post"
	enctype="multipart/form-data" >
<s:file  name="offering"  />	

<sj:a 
						     	name="Reset"  
								href="#" 
								cssClass="submit" 
								indicator="indicator" 
								button="true" 
								targets="orglevel1Div"
								onCompleteTopics="complete"
							>
			  				Upload
							</sj:a>
</s:form>
</div>
</div>
</div>
</body>
</html>