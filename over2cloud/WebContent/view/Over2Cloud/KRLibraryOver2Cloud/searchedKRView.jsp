<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

</script>
</head>
<body>
<br>
<br>

<s:url id="fileDownload" action="download" namespace="/view/Over2Cloud/KRLibraryOver2Cloud">
<s:param name="filePathhhhhhhhhhhh" value="%{id}"/>
</s:url>
	
	<div class="form_menubox">
        <div class="user_form_text" style="margin-top: -10px;">Searching Tag:</div>
           <div class="user_form_input">
          		 <s:label value="%{tags}"></s:label>
          </div>
          <div class="user_form_text1" style="margin-top: -5px;">Total Result Found:</div>
          		<div class="user_form_input">
          				<s:label value="%{total}"></s:label>
          		</div>
    </div>

 <div class="form_menubox">
	<s:iterator value="serchedTagMap" status="status">
		<s:set name="krId" value="%{serchedTagMap['KR id']}"> </s:set>
		<s:set name="kr_Name" value="%{serchedTagMap['kr Name']}"> </s:set>
		<s:set name="filePath" value="%{serchedTagMap['File Path']}"> </s:set>
		<s:set name="date" value="%{serchedTagMap['date']}"> </s:set>
		<s:set name="time" value="%{serchedTagMap['time']}"> </s:set>
	</s:iterator>
<table align="center" width="98%" style=" border:solid black 1px;">
	<tr style="background-color: #D8D8D8;-moz-border-radius: 6px 0 0 0;-webkit-border-radius: 6px 0 0 0;border-radius: 6px 0 0 0;">
		<td align="center">KR ID</td>
		<td align="center">KR Name</td>
		<td align="center">Date</td>
		<td align="center">Time</td>
		<td align="center">Download</td>
	</tr>
<s:iterator value="parentTakeaction" id="parentTakeaction" status="counter">  
<s:if test="%{#counter.odd}">
		<tr>
		<s:form id="fileDownload" theme="simple" action="download" namespace="/view/Over2Cloud/KRLibraryOver2Cloud">
			<s:iterator value="childList" status="tmp">
			
				<s:if test="%{#tmp.last}">
					<s:hidden name="filePathhhhhhhhhhhh" value="%{name}"></s:hidden>
					<td align="center"><sj:submit name="button" value="Download"  /></td>
				</s:if>
				<s:else>
					<td align="center"><s:property value="name"/></td>
				</s:else>
			
				
			</s:iterator>
			</s:form>
		</tr>
</s:if>
<s:else>
		<tr style="background-color: #F2F2F2;">
		<s:form id="fileDownload" theme="simple" action="download" namespace="/view/Over2Cloud/KRLibraryOver2Cloud">
			<s:iterator value="childList" status="tmp">
				
				<s:if test="%{#tmp.last}">
				<s:hidden name="filePathhhhhhhhhhhh" value="%{name}"></s:hidden>
					<td align="center"><sj:submit name="button"  value="Download"  /></td>
				</s:if>
				<s:else>
					<td align="center"><s:property value="name"/></td>
				</s:else>
			
				
			</s:iterator>
			</s:form>
		</tr>
</s:else>
</s:iterator> 
</table>
				
		


	<!--<td><s:a  href="%{fileDownload}" ><h3>Download</h3></s:a></td>
	
	--></div>
</body>
</html>
