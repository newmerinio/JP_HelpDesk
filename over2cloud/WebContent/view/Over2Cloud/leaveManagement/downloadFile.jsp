<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" media="screen,projection" type="text/css" href="<s:url value="/css/main.css"/>" />
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
<div id="main">
<ul class="results">
<s:iterator value="attachFileMap" status="status">
     <s:set name="id" value="%{attachFileMap['id']}"> </s:set>
     <s:set name="filePath" value="%{attachFileMap['File Path']}"> </s:set>
</s:iterator>

<!-- Changes by Abhay -->
        <s:if test="%{attachFileMap.size > 0}">
            <li>            
                <a href="<%=request.getContextPath()%>/view/Over2Cloud/leaveManagement/download.action?id=<s:property value="filePath"/>"><font color="#194d65" style="display: inline; width: 20px;">Download File</font></a>
            </li>
            </s:if>
         <s:else>
            <li>No File Attached</li>
        </s:else>
            
    
</ul>
</div>
</body>
</html>