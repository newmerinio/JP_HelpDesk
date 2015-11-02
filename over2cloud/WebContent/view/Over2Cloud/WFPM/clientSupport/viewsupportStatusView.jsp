<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Support Status Details</title>
</head>
<body>
<% 
String extpo=null,downloadPO=null,downloadAgree=null,extagree=null;
downloadPO=request.getAttribute("poAttach").toString();
downloadAgree=request.getAttribute("agreeAttach").toString();
String ext=request.getAttribute("Ext").toString();
if(ext!="NA")
{
extpo=request.getAttribute("extpoAttach").toString();
extagree=request.getAttribute("extagreeAttach").toString();
}
%>
		<table border="1" width="100%" style="border-collapse: collapse;">
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Suppot Type:</td>
							<td width="10%"><s:property  value="%{support_type}"/></td>
							<td width="10%" style="font-weight: bolder;">Comments:</td>
							<td width="10%"><s:property  value="%{comments}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%" style="font-weight: bolder;">From Support:</td>
							<td width="10%"><s:property  value="%{support_from}"/></td>
							<td width="10%" style="font-weight: bolder;">To Support:</td>
							<td width="10%"><s:property  value="%{supportTill}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Support PO:</td>
							<td width="10%">
							<% 
							if(downloadPO.equalsIgnoreCase("NA"))
							{
							%>
							<s:a href="#" onclick="docCheck();" cssStyle="color:#4876FF;">NA</s:a>
							<%
							}else{ 
							%>
							<s:a href="view/Over2Cloud/wfpm/clientsupport/downloadPo.action?id='%{id}'&docType=poattach2" cssStyle="color:#4876FF;">
							<img title="Attachment" src='images/docDownlaod.jpg'/>
							</s:a> 
							<%}%>
							</td>
							<td width="10%" style="font-weight: bolder;">Support Agreement:</td>
							<td width="10%">
							<%
							if(downloadAgree.equalsIgnoreCase("NA"))
							{
							%>
							<s:a href="#" onclick="docCheck();" cssStyle="color:#4876FF;">NA</s:a>
							<%
							}else{ 
							%>
							<s:a href="view/Over2Cloud/wfpm/clientsupport/downloadPo.action?id='%{id}'&docType=agree" cssStyle="color:#4876FF;">
							<img title="Attachment" src='images/docDownlaod.jpg'/>
							</s:a> 
							<%}%>
							</td>
				</tr>
				<tr style="height: 25px" colspan="2"> <td width="10%" ><b>Extended Support Details</b>  </td></tr>
				<s:if test="%{extendedSupportType  != null}">
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Support Type:</td>
							<td width="10%"><s:property  value="%{extendedSupportType}"/> </td>
							<td width="10%" style="font-weight: bolder;">Comments:</td>
							<td width="10%"><s:property  value="%{extendComments}"/> </td>
				</tr>
				<tr style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Extended Support From:</td>
							<td width="10%"><s:property  value="%{extendDateFrom}"/> </td>
							<td width="10%" style="font-weight: bolder;">Extended Support To:</td>
							<td width="10%"><s:property  value="%{extendDateTo}"/> </td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Amount:</td>
							<td width="10%"><s:property  value="%{extendAmount}"/></td>
							<td width="10%" style="font-weight: bolder;">Extended Support PO:</td>
							<td width="10%">
							<%
							if(extpo.equalsIgnoreCase("NA") && extpo!=null)
							{
							%>
							<s:a href="#" onclick="docCheck();" cssStyle="color:#4876FF;">NA</s:a>
							<%
							}else{ 
							%>
							<s:a href="view/Over2Cloud/wfpm/clientsupport/downloadPo.action?id='%{id}'&docType=extpoattach1" cssStyle="color:#4876FF;">
							<img title="Attachment" src='images/docDownlaod.jpg'/>
							</s:a> 
							<%}%>
							</td>
				</tr>
				<tr style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Extended Support Agreement:</td>
							<td width="10%">
							<% 
							if(extagree.equalsIgnoreCase("NA") && extagree!=null)
							{
							%>
							<s:a href="#" onclick="docCheck();" cssStyle="color:#4876FF;">NA</s:a>
							<%
							}else{ 
							%>
							<s:a href="view/Over2Cloud/wfpm/clientsupport/downloadPo.action?id='%{id}'&docType=extpoattach2" cssStyle="color:#4876FF;">
							<img title="Attachment" src='images/docDownlaod.jpg'/>
							</s:a> 
							<%}%>
							</td>
							<td width="10%" style="font-weight: bolder;"></td>
							<td width="10%"> </td>
							
				</tr>

				</s:if>
				<s:else>
					<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%" style="font-weight: bolder;">NA</td>
							<td width="10%"> </td>
							<td width="10%" style="font-weight: bolder;"></td>
							<td width="10%"></td>
					</tr>
				</s:else>
		</table>
		
</body>
</html>