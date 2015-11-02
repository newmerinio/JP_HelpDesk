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
	<div id="printDiv">
	<s:if test="forwardStatus">
		<a href="#" onclick="getNextOrPreviousData('BackwardData')">
			<img src="images/WFPM/commonDashboard/backward.jpg" alt="Backward" title="Backward" />
		</a>
	</s:if>
	<s:if test="backwardStatus">
		<a href="#" onclick="getNextOrPreviousData('ForwardData')" style="margin-right: -92%;">
			<img src="images/WFPM/commonDashboard/forward.jpg" alt="Forward" title="Forward" />
		</a>
	</s:if>
	<div class="type-button" style="
    margin-left: -91%;
    margin-top: -2%;
	">
       <sj:submit 
                 clearForm="true"
                 value="  Print  " 
                 button="true"
                 onclick="printDiv('printDiv')"
                 
                 />
    </div> 

	<font face="Arial, Helvetica, sans-serif" size="2"><B>Asset Life Cycle History for <s:property value="%{assetName}"/>, Code: <s:property value="%{assetCode}"/>, Sr. No. <s:property value="%{slNo}"/></B></font>
	 
	<br><br><b>Procurement & Allotment</b>
	<br>
		<table border="1" width="100%" style="border-collapse: collapse;">
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<s:iterator value="assetDetailMap">
						<s:if test="key=='Specification' || key=='Brand' || key=='Model'">
							<td width="10%"><s:property value="%{key}"/> :</td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 23px">
					<s:iterator value="assetDetailMap">
						<s:if test="key=='P.O. No.' || key=='Vendor' || key=='At'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<s:iterator value="assetDetailMap">
						<s:if test="key=='Purchased On' || key=='Install On' || key=='Commissioned On'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 23px">
					<s:iterator value="assetDetailMap">
						<s:if test="key=='Asset Category' || key=='Expected Life' || key=='Current Value'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<s:iterator value="assetDetailMap">
						<s:if test="key=='Asset Type' || key=='Allotted To' || key=='Location'">
							<td width="10%"><s:property value="%{key}"/> : </td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
		</table>
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
		<br><br>
		<s:if test="supportList.size>0">
		<table border="1" width="100%" style="border-collapse: collapse;">
			<tr bgcolor="#BDBDBD" style="height: 25px">
				<td align="center" width="10%">Renewed On:</td><td align="center" width="10%">Renewed Till:</td><td align="center" width="10%">Renewed By:</td>
			</tr>
			<s:iterator id="assetSupport"  status="status" value="%{supportList}" >
				<s:if test="#status.odd">
				<tr style="height: 23px">
					<td align="center"><s:property value="#assetSupport.actionTakenDate" /></td>
					<td align="center"><s:property value="#assetSupport.dueDate" /></td>
					<td align="center"><s:property value="#assetSupport.empName" /></td>
				</tr>
				</s:if>
				<s:else>
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<td align="center"><s:property value="#assetSupport.actionTakenDate" /></td>
					<td align="center"><s:property value="#assetSupport.dueDate" /></td>
					<td align="center"><s:property value="#assetSupport.empName" /></td>
				</tr>	
				</s:else>
			</s:iterator>
		</table>
		<br><br>
		</s:if>
		<b>Preventive Detail: <s:property value="%{frequency}"/></b>
		<table border="1" width="100%" style="border-collapse: collapse;">
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<s:iterator value="assetPreventiveDetailMap">
						<s:if test="key=='Detail' || key=='Due' || key=='Next Due'">
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
		<br><br>
		<s:if test="preventiveList.size>0">
		<table border="1" width="100%" style="border-collapse: collapse;">
			<tr bgcolor="#BDBDBD" style="height: 25px">
				<td align="center" width="10%">Last Due:</td><td align="center" width="10%">Status:</td><td align="center" width="10%">Achieved On:</td><td align="center" width="10%">Brief:</td><td align="center" width="10%">Activity By:</td>
			</tr>
			<s:iterator id="assetPreventive"  status="status" value="%{preventiveList}" >
				<s:if test="#status.odd">
				<tr style="height: 23px">
					<td align="center"><s:property value="#assetPreventive.dueDate" /></td>
					<td align="center"><s:property value="#assetPreventive.actionStatus" /></td>
					<td align="center"><s:property value="#assetPreventive.actionTakenDate" /></td>
					<td align="center"><s:property value="#assetPreventive.comments" /></td>
					<td align="center"><s:property value="#assetPreventive.empName" /></td>
				</tr>
				</s:if>
				
				<s:else>
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<td align="center"><s:property value="#assetPreventive.dueDate" /></td>
					<td align="center"><s:property value="#assetPreventive.actionStatus" /></td>
					<td align="center"><s:property value="#assetPreventive.actionTakenDate" /></td>
					<td align="center"><s:property value="#assetPreventive.comments" /></td>
					<td align="center"><s:property value="#assetPreventive.empName" /></td>
				</tr>
				</s:else>
			</s:iterator>
		</table>
		<br><br>
		</s:if>
		<b>Breakdown History: <s:property value="totalBreakDown"/>, Current Status: <s:property value="currentStatus"/></b>
		<table border="1" width="100%" style="border-collapse: collapse;">
			<tr bgcolor="#BDBDBD" style="height: 25px">
				<td align="center" width="10%">Ticket ID</td>
				<td align="center" width="10%">Issue</td>
				<td align="center" width="10%">Opened On</td>
				<td align="center" width="10%">Total Breakdown</td>
				<td align="center" width="10%">Spare</td>
				<td align="center" width="10%">RCA</td>
			</tr>
			
			<s:iterator id="assetBreakdown"  status="status" value="%{breakDownList}" >
				<s:if test="#status.odd">
				<tr style="height: 23px">
					<td align="center">
					<a href="#" style="cursor: pointer;text-decoration: none;" onclick="getTicketInfo('<s:property value="#assetBreakdown.id" />')">
						<s:property value="#assetBreakdown.ticket_no" />
					</a>
					</td>
					<td align="center"><s:property value="#assetBreakdown.feedback_catg" /></td>
					<td align="center"><s:property value="#assetBreakdown.open_date" /></td>
					<td align="center"><s:property value="#assetBreakdown.offTime" /></td>
					<td align="center"><s:property value="#assetBreakdown.other" /></td>
					<td align="center"><s:property value="#assetBreakdown.comments" /></td>
				</tr>
				</s:if>
				
				<s:else>
				<tr bgcolor="#BDBDBD" style="height: 25px">
					<td align="center">
					<a href="#" style="cursor: pointer;text-decoration: none;" onclick="getTicketInfo('<s:property value="#assetBreakdown.id" />')">
						<s:property value="#assetBreakdown.ticket_no" />
					</a>
					</td>
					<td align="center"><s:property value="#assetBreakdown.feedback_catg" /></td>
					<td align="center"><s:property value="#assetBreakdown.open_date" /></td>
					<td align="center"><s:property value="#assetBreakdown.offTime" /></td>
					<td align="center"><s:property value="#assetBreakdown.other" /></td>
					<td align="center"><s:property value="#assetBreakdown.comments" /></td>
				</tr>
				</s:else>
			</s:iterator>
		</table>
		</div>
		</div>
	</center>
</body>
</html>