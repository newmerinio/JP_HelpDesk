<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<link rel="stylesheet" media="screen,projection" type="text/css" href="<s:url value="/css/main.css"/>" />
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<SCRIPT type="text/javascript">

function fileload(fp)
{
	alert("hello alert>>>>>>>>>>>"+fp);
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><center><img src=images/ajax-loaderNew.gif></center>");
	var conP = "<%=request.getContextPath()%>";
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Reimbursement/download.action?id=+'fp'",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</SCRIPT>
</head>
<body>
<div id="main">
<ol class="results">
<s:iterator value="attachFileMap" status="status">
     <s:set name="id" value="%{attachFileMap['id']}"> </s:set>
     <s:set name="filePath" value="%{attachFileMap['FilePath']}"> </s:set>
   
</s:iterator>
     >>>>>>filePath:<s:property  value="%{filePath}"/>
      >>>>>>fileId:<s:property  value="%{id}"/>
            <li>            
                <center><a href="<%=request.getContextPath()%>/view/Over2Cloud/Reimbursement/downloadattachedment.action?id=<s:property value="filePath"/>"><B>Download File</B></a></center>
            </li>
    
</ol>
</div>
</body>
</html>