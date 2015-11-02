<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">

.user_form_input{
	margin-bottom:10px;
}

</style>

<html>
<head>
<link rel="stylesheet" media="screen,projection" type="text/css" href="<s:url value="/css/main.css"/>" />
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />

<script type="text/javascript">
function documentModify(id){
	
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeModifyUpload.action?id="+id,
 	    success : function(data) 
 	    {
 			$("#"+"modifyKR").html(data);
 	    },
 	    error: function() 
 	    {
             alert("error");
         }
 	 });
}


</script>
</head>
<body>
<br>
<br>

<s:url id="fileDownload" action="download" namespace="/view/Over2Cloud/KRLibraryOver2Cloud">
<s:param name="filePathhhhhhhhhhhh" value="%{id}"/>
</s:url>
 
 <div class="form_menubox">
	<s:iterator value="modifyTagMap" status="status">
		<s:set name="krId" value="%{modifyTagMap['KR ID']}"> </s:set>
		<s:set name="kr_Name" value="%{modifyTagMap['Document']}"> </s:set>
		<s:set name="filePath" value="%{modifyTagMap['Created On']}"> </s:set>
		<s:set name="date" value="%{modifyTagMap['Created At']}"> </s:set>
		<s:set name="time" value="%{modifyTagMap['Last Modified']}"> </s:set>
	</s:iterator>
	<table width="696px" height="100px" cellspacing="0" cellpadding="3">
<tr><td bgcolor="#A0A0A0 " class="tabular_head" valign="middle" colspan="50"><h3>Search Result</h3></td></tr>
	<tr><td bgcolor="#D8D8D8 " style=" border-bottom:1px solid #e7e9e9; color:#000000;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
				<td valign="middle" width="25%"><h4>KR ID</h4></td>
				<td valign="middle" width="25%"><h4>KR Name</h4></td>
				<td valign="middle" width="25%"><h4>Created On</h4></td>
				<td valign="middle" width="25%"><h4>Created At</h4></td>
				<td valign="middle" width="25%"><h4>Modify</h4></td>
			</tr>
			<tr>
				<td><s:property  value="%{krId}"/></td>
				<td><s:property  value="%{kr_Name}"/></td>
				<td><s:property  value="%{date}"/></td>
				<td><s:property  value="%{time}"/></td>
				<td><a href="#" onclick="documentModify('${krId}');">Modify</a></td>
			</tr>
		</tbody></table>
	</td>
	</tr>

</table><br><br><br>
<div id="modifyKR"></div>
	
	
	
</div>
</body>
</html>
