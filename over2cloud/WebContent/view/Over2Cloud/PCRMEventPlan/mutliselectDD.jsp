<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<SCRIPT type="text/javascript">

$("#eplan_team").multiselect({
	   show: ["", 200],
	   hide: ["explode", 1000]
	});
</SCRIPT>
<title>Insert title here</title>
</head>
<body>
<s:select 
                                      id="eplan_team"
                                      name="eplan_team" 
                                      list="teamMap"
                                      headerKey="-1"
                                      headerValue="Select Mapped Team"
                                      cssClass="select"
                                      cssStyle="width:20%"
                                      theme="simple"
                                      multiple="true"
                                      >
                          </s:select>
</body>
</html>