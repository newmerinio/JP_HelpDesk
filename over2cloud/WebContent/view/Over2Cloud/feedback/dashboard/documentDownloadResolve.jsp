<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div id="main">

<s:if test="%{docMap.size()==0}">
<br>
     <b>No document available</b>
</s:if>
<s:else>
<ol>
		  <s:iterator value="docMap"  status="status">
			 <li>
			   <b>Do you want to download ? </b>
			 </li>
			 <br>
		  	<li>
                
                 <a href="<%=request.getContextPath()%>/view/Over2Cloud/feedback/confirmDownloadResolve.action?fileName=<s:property value="key"/>"><font color="blue"><h4><B><s:property value="value"/></B></h4></font></a>
		  	</li>
		  </s:iterator>
		  </ol>
</s:else>
		  
</div>
</body>
</html>