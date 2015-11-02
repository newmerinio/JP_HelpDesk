<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Conversion Details</title>
</head>
<body>
<%
String downloadPO=request.getAttribute("download").toString();
%>
		<table border="1" width="100%" style="border-collapse: collapse;">
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Organization Name :</td>
							<td width="10%"><s:property  value="%{organization}"/></td>
							<td width="10%" style="font-weight: bolder;">Location :</td>
							<td width="10%"><s:property  value="%{location}"/></td>
				</tr>
				<tr style="height: 23px">
							<td width="10%" style="font-weight: bolder;">Target Segment :</td>
							<td width="10%"><s:property  value="%{targetSegment}"/></td>
							<td width="10%" style="font-weight: bolder;">Website :</td>
							<td width="10%"><s:property  value="%{website}"/></td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="20%"><b>Vertical :</b><b><s:property  value="%{verticalname}"/></b>  </td>
							<td width="10%"><b>Offering :</b><b> <s:property  value="%{offeringname}"/></b></td>
							<td width="10%" colspan="2"><b>Sub Offering :</b><b> <s:property  value="%{subofferingname}"/> </b></td>
				</tr>
				<s:iterator value="contactDetails">
					<tr style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Name :</td>
							<td width="10%"><s:property  value="%{contactName}"/> </td>
							<td width="10%" style="font-weight: bolder;">Designation :</td>
							<td width="10%"><s:property  value="%{designation}"/> </td>
				</tr>
				<tr  bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Mobile No. :</td>
							<td width="10%"><s:property  value="%{contactNumber}"/> </td>
							<td width="10%" style="font-weight: bolder;">Email ID :</td>
							<td width="10%"><s:property  value="%{email}"/> </td>
				</tr>
				</s:iterator>
				
				
				<tr style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Converted On.:</td>
							<td width="10%"><s:property  value="%{convertedOn}"/> </td>
							<td width="10%" style="font-weight: bolder;">Comments:</td>
							<td width="10%"><s:property  value="%{comments}"/> </td>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%" style="font-weight: bolder;">Download PO:</td>
							<td width="10%">
							<% 
							if(downloadPO.equalsIgnoreCase("NA"))
							{
							%>
							<s:a href="#" onclick="docCheck();" cssStyle="color:#4876FF;">NA</s:a>
							<%
							}else{ 
							%>
							<s:a href="view/Over2Cloud/wfpm/clientsupport/downloadPo.action?offeringId='%{offeringId}'&clientName='%{clientName}'&docType=poattach1" cssStyle="color:#4876FF;">
							<img title="Attachment" src='images/docDownlaod.jpg'/>
							</s:a> 
							<%}%>
							</td>
							<td width="10%" style="font-weight: bolder;" >Converted By:</td>
							<td width="10%"><s:a href="#" onclick="viewAccMgrDetails('%{offeringId}','%{clientName}');" cssStyle="color:#4876FF;"><s:property  value="%{acManager}"/></s:a></td>
				</tr>
				
		</table>
		
</body>
</html>