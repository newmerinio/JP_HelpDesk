<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div style="margin-top: 0px" >
<s:if test="%{block=='block2'}">
<table align="center" width="100%" height="100%"
						style="margin-top: 5px; border-radius: 3px;" border="1">
						<tr
							style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
							<td valign="top">
								<table align="center" width="100%" height="100%">
									<tr>
										<th align="center" valign="top" width=""><font
											color="black">Mode</font></th>
									</tr>
								</table>
							</td>
							<td>
								<table align="center" width="100%">
									<tr>
										<th align="center" colspan="2" title="Total Tickets">
											<font color="black">Total&nbsp;Tickets</font>
										</th>
									</tr>
								</table>
							</td>
							<td>
								<table align="center" width="100%" border="0">
									<tr>
										<th align="center" colspan="2"><font color="black">Positive</font>
										</th>
									</tr>
								</table>
							</td>
							<td>
								<table align="center" width="100%">
									<tr>
										<th align="center" colspan="2"><font color="black">Negative</font>
										</th>
									</tr>
								</table>
							</td>
							<td>
								<table align="center" width="100%">
									<tr>
										<th align="center" colspan="2"><font color="black">Pending</font>
										</th>
									</tr>
								</table>
							</td>
							
							<td>
								<table align="center" width="100%">
									<tr>
										<th align="center" colspan="2" title="Ticket Opened"><font
											color="black">T O</font></th>
									</tr>
								</table>
							</td>
							<td>
								<table align="center" width="100%">
									<tr>
										<th align="center" colspan="2" title="Ticket Opened"><font
											color="black">Stage&nbsp;1</font></th>
									</tr>
								</table>
							</td>
							<td>
								<table align="center" width="100%">
									<tr>
										<th align="center" colspan="2" title="Ticket Opened"><font
											color="black">Stage&nbsp;2</font></th>
									</tr>
								</table>
							</td>
						</tr>
						<s:iterator value="summaryList">
							<tr bgcolor="" bordercolor="#000000">
								<td align="left">
									<table align="center" width="100%" height="100%">
										<tr>
											<th
												style="font-weight: normal; border-radius: 3px; background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
												<font color="#000000"><s:property value="%{mode}" /></font>
											</th>
										</tr>
									</table>
								</td>
								<td>
									<table align="center" width="100%">
										<tr>
											<s:if test="%{totalTickets!=0}">
												<th align="center" width="50%"
													onclick="getFeedbackModeOnClick('<s:property value="%{mode}"/>','Total Tickets','1');">
													<a href="#"><font color="#275985"><s:property
																value="%{totalTickets}" /></font></a>
												</th>
											</s:if>
											<s:else>
												<th align="center"><font color="#275985"><s:property
															value="%{totalTickets}" /></font></th>
											</s:else>
										</tr>
									</table>
								</td>
								
								<td>
									<table align="center" width="100%">
										<tr>
											<s:if test="%{positive!=0}">
												<th align="center" width="50%"
													onclick="getFeedbackModeOnClick('<s:property value="%{mode}"/>','Positive','0');">
													<a href="#"><font color="#275985"><s:property
																value="%{positive}" /></font></a>
												</th>
											</s:if>
											<s:else>
												<th align="center"><font color="#275985"><s:property
															value="%{positive}" /></font></th>
											</s:else>
										</tr>
									</table>
								</td>
								<td>
									<table align="center" width="100%">
										<tr>
											<s:if test="%{negative!=0}">
												<th align="center" width="50%"
													onclick="getFeedbackModeOnClick('<s:property value="%{mode}"/>','Negative','1');">
													<a href="#"><font color="#275985"><s:property
																value="%{negative}" /></font></a>
												</th>
											</s:if>
											<s:else>
												<th align="center"><font color="#275985"><s:property
															value="%{negative}" /></font></th>
											</s:else>
										</tr>
									</table>
								</td>
								<td>
									<table align="center" width="100%">
										<tr>
											<s:if test="%{pending!=0}">
												<th align="center" width="50%"
													onclick="getFeedbackModeOnClick('<s:property value="%{mode}"/>','Pending','1');">
													<a href="#"><font color="#275985"><s:property
																value="%{pending}" /></font></a>
												</th>
											</s:if>
											<s:else>
												<th align="center"><font color="#275985"><s:property
															value="%{pending}" /></font></th>
											</s:else>
										</tr>
									</table>
								</td>
																<td>
									<table align="center" width="100%">
										<tr>
											<s:if test="%{ticketOpened!=0}">
												<th align="center" width="50%"
													onclick="getFeedbackModeOnClick('<s:property value="%{mode}"/>','Ticket Opened','1');">
													<a href="#"><font color="#275985"><s:property
																value="%{ticketOpened}" /></font></a>
												</th>
											</s:if>
											<s:else>
												<th align="center"><font color="#275985"><s:property
															value="%{ticketOpened}" /></font></th>
											</s:else>

										</tr>
									</table>
								</td>
							</tr>
						</s:iterator>
					</table>
</s:if>
<s:elseif test="%{block=='block5'}">
<table height="40%" width="100%" align="center" border="1">
	<tr>
		<th width="70%" bgcolor="#FF9933" >
			<font color="white">Category&nbsp;Name</font>
		</th>
		<th width="30%" bgcolor="#FF9933" >
			<font color="white">Counter</font>
		</th>
	</tr>
	<s:iterator value="feedDataDashboardList">
	<tr>
		<td align="center" bgcolor="#FFDCBB" class="sortable" width="70%" >
			<font color="black"><b><s:property value="%{actionName}"/></b></font>
		</td>
		<td align="center" bgcolor="#FFDCBB" class="sortable" width="30%" onclick="getOnClickDataForFeedbackType('<s:property value="%{id}"/>','Category');">
			<a href="#"><font color="black"><b><s:property value="%{actionCounter}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
	<tr bgcolor="#FFDCBB">
	<td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalPending"/></b></font></td>
    </tr>
</table>

</s:elseif>
<s:elseif test="%{block=='block6'}">
<table height="40%" width="100%" align="center" border="1">
	<tr>
		<th width="20%" bgcolor="#666699" >
			<font color="white">Status</font>
		</th>
		<th width="15%" bgcolor="#666699" >
			<font color="white">Level&nbsp;1</font>
		</th>
		<th width="15%" bgcolor="#666699" >
			<font color="white">Level&nbsp;2</font>
		</th>
		<th width="15%" bgcolor="#666699" >
			<font color="white">Level&nbsp;3</font>
		</th>
		<th width="15%" bgcolor="#666699" >
			<font color="white">Level&nbsp;4</font>
		</th>
		<th width="15%" bgcolor="#666699" >
			<font color="white">Level&nbsp;5</font>
		</th>
	</tr>
	<s:iterator value="dashPojoList">
	<tr>
		<td align="center" bgcolor="#D1D1E0" class="sortable" width="20%">
			<font color="black"><b><s:property value="%{pending}"/></b></font>
		</td>
		<td align="center" bgcolor="#D1D1E0" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level1','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL1}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#D1D1E0" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level2','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL2}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#D1D1E0" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level3','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL3}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#D1D1E0" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level4','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL4}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#D1D1E0" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level5','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL5}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
	<tr bgcolor="#D1D1E0">
		<td align="center" width="20%" height="20%"><font color="black"><b>Total</b> </font></td>
		<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel1"/></b></font></td>
		<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel2"/></b></font></td>
		<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel3"/></b></font></td>
		<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel4"/></b></font></td>
		<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel5"/></b></font></td>
    </tr>
</table>

</s:elseif>
</div>
</body>
</html>