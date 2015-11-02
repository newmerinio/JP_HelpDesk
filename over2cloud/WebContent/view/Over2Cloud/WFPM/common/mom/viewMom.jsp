<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="container_block">
<div style="float: left; padding: 20px 1%; width: 98%;">
<div class="form_inner" id="form_reg" style="margin-top: 10px;">
	<s:if test="%{showMomDetails == true}">
	<table width="100%">
	<tr style="background-color: #E0E0E0">
	<td colspan="4" align="center"><H3><STRONG>MOM Details</STRONG></H3></tr>
	<tr style="background-color: #E0E0E0"><td colspan="4" align="left"></tr>
	
	<s:iterator value="momFullViewMap" status="status" var="var">
		<tr  style="background-color: #F8F8F8">
			<td align="left" width="20%"><strong><s:property value="%{key}"/>:</strong></td>
			<s:if test="key == 'Client Contact'">
				<td align="left" width="80%">
					<s:iterator value="#var.value" status="stt">
						<b><s:property value="#stt.count"/>:</b> <s:property/><br>
					</s:iterator>
				</td>
			</s:if>
			
			<s:elseif test="key == 'Employee'">
				<td align="left" width="80%">
				<s:iterator value="#var.value" status="stt">
				<b><s:property value="#stt.count"/>:</b> <s:property/><br><br>
				</s:iterator>
				</td>
			</s:elseif>
			
			<s:elseif test="key == 'FutureAction'">
				<td align="left" width="80%">
				<s:iterator value="#var.value" status="stt">
				<b><s:property value="#stt.count"/>:</b> <s:property /><br><br>
				</s:iterator>
				</td>
			</s:elseif>
			
			<s:elseif test="key == 'Discussion'">
				<td align="left" width="80%">
				<s:iterator value="#var.value" status="stt">
				<b><s:property value="#stt.count"/>:</b> <s:property /><br><br>
				</s:iterator>
				</td> 
			</s:elseif>
			
			<s:elseif test="key == 'Attached Doc'">
				<td align="left" width="80%" >
				<s:iterator value="#var.value" status="stt">
				<b><s:property value="#stt.count"/>:</b> 
				<a href="view/Over2Cloud/wfpm/client/downloadattachedment.action?fileName=<s:property />">
				<B style="color: green">
				 <s:property />
				</B>
				</a><br><br>
				</s:iterator>
				</td> 
			</s:elseif>
		
			<s:else>
			<td align="left" width="80%"><s:property value="%{value}"/></td>
			</s:else>
		</tr>
	</s:iterator>
	</table>
	</s:if>
	<s:else>
		<center><H3>No MOM had been filled !</H3></center>
	</s:else>
</div>
</div>
</div>
</body>
</html>