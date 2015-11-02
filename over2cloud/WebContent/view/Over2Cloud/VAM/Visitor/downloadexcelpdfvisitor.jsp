	
	<%@ taglib prefix="s"  uri="/struts-tags"%>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<style type="text/css">
	.sortable {
	margin:0px;
	padding:1% 2%;
	text-align: left;
	float:left; width:94%;
	margin-bottom:5px;
	}
	span.ui-button-text{
		font-family:'Open Sans', sans-serif; font-weight:600; font-size:11px;
	}
	</style>
	
	<script type="text/javascript">
	$.subscribe('complete_close',function(event,data){
		$("#downloaddailcontactdetails").dialog('close');
	});
	</script>
	</head>
	<body topmargin="0" leftmargin="0">
	<s:form action="%{#parameters.downloadurlaction}" id="download" theme="css_xhtml" name="forms">
	<s:hidden name="downloadType" value="%{#parameters.downloadType}"/>
	<s:hidden name="id" value="%{#parameters.id}"/>
	<s:hidden name="app_name" value="%{#parameters.app_name}"/>
	</s:form>
	     
	</body>
	</html>