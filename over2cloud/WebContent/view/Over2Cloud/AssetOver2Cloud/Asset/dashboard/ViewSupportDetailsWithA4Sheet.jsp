<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<center>
	<div id="overlapAll">
	<s:if test="forwardStatus">
	<a href="#" onclick="getNextPreviousData('BackwardData')">
			<img src="images/WFPM/commonDashboard/backward.jpg" alt="Backward" title="Backward" />
		</a>
	</s:if>
	<s:if test="backwardStatus">
	<a href="#" onclick="getNextPreviousData('ForwardData')" style="margin-right: -92%;">
			<img src="images/WFPM/commonDashboard/forward.jpg" alt="Forward" title="Forward" />
		</a>
		<br>
	</s:if>
	<font face="Arial, Helvetica, sans-serif" size="2"><B>Asset Life Cycle History for <s:property value="%{assetName}"/>, Code: <s:property value="%{assetCode}"/>, Sr. No. <s:property value="%{slNo}"/></B></font>
	 	<s:if test="%{assetSupportDetailMap.size()>0}">
		<br><br><b>Support: <s:property value="%{supportStatus}"/></b>
		<br>
	
		<table border="1" width="100%" style="border-collapse: collapse;">
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<s:iterator value="assetSupportDetailMap">
						<s:if test="key=='Support Type' || key=='From Vendor' || key=='Contact No.'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 23px">
					<s:iterator value="assetSupportDetailMap">
						<s:if test="key=='Support Detail' || key=='Support From' || key=='Support To'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<s:iterator value="assetSupportDetailMap">
						<s:if test="key=='Frequency' || key=='Mapped To' || key=='Contact No. '">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 23px">
					<s:iterator value="assetSupportDetailMap">
						<s:if test="key=='Reminder-1' || key=='Reminder-2' || key=='Reminder-3'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#BDBDBD" style="height: 23px">
					<s:iterator value="assetSupportDetailMap">
					<s:if test="key=='Document 1'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%">
								<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{doc1}"/>">
									<s:property value="value"/>
								</a>
							</td>
						</s:if>
						<s:elseif test="key=='Document 2'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%">
								<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{doc2}"/>">
									<s:property value="value"/>
								</a>
							</td>
						</s:elseif>
						<s:elseif test="key=='Last Action Doc'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%">
								<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{lastActionDoc}"/>">
									<s:property value="value"/>
								</a>
							</td>
						</s:elseif>
					</s:iterator>
				</tr>
				
		</table>
		</s:if>
		<s:elseif test="assetPreventiveDetailMap.size()>0">
		<br><br>
		<b>Preventive Detail: <s:property value="%{frequency}"/></b>
		<br>
		<table border="1" width="100%" style="border-collapse: collapse;">
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<s:iterator value="assetPreventiveDetailMap">
						<s:if test="key=='Detail' || key=='Due Date' || key=='Next Due'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 23px">
					<s:iterator value="assetPreventiveDetailMap">
						<s:if test="key=='Ownership' || key=='Mapped To' || key=='Contact No.'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<s:iterator value="assetPreventiveDetailMap">
						<s:if test="key=='Reminder-1' || key=='Reminder-2' || key=='Reminder-3'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 23px">
					<s:iterator value="assetPreventiveDetailMap">
					<s:if test="key=='Document 1'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%">
								<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{pmDoc1}"/>">
									<s:property value="value"/>
								</a>
							</td>
						</s:if>
						<s:elseif test="key=='Document 2'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%">
								<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{pmDoc2}"/>">
									<s:property value="value"/>
								</a>
							</td>
						</s:elseif>
						<s:elseif test="key=='Last Action Doc'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%">
								<a href="<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/confirmDownload.action?fileName=<s:property value="%{pmLastActionDoc}"/>">
									<s:property value="value"/>
								</a>
							</td>
						</s:elseif>
					</s:iterator>
				</tr>
				
		</table>
		</s:elseif>
		<br>
		<sj:a id="action" cssClass="button" button="true" title="Action" onclick="reminderAction();">Action</sj:a>
		<div id="actionData">
		</div>
		</div>
	</center>
</body>
</html>