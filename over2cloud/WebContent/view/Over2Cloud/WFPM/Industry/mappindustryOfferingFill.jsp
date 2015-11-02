<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/WFPM/industry/targetMap/targetMap.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />

<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function(){
$("#offeringname").multiselect();
});
</script>

</head>
<body>

						<s:select 
	                              id="offeringname"
	                              name="offeringname" 
	                              list="#{'-1':'Select'}"
	                              cssClass="select"
	                              multiple="true"
	                              onchange="fetchLevelData(this,'subofferingname','2')"
	                              >
	                  	</s:select>
</body>
</html>