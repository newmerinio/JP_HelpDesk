<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<table width="100%" cellspacing="0" cellpadding="9">
	
	<!-- for heading -->
	<tr>
			<th bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
				History ID
			</th>
			
			<th bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
				Action By
			</th>
			
			<th bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
				Action Date
			</th>
			
			<th bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff; padding:4px; font-weight:600;" valign="top" class="tabular_cont">
				Action Taken
			</th>
	</tr>
	
	
	<!-- for data -->
	<s:iterator value="fullAchievmentList" id="parantHistory" status="status">
		<s:if test="#status.odd">
			<tr>
				<td style=" border-bottom:1px solid #e7e9e9; padding:4px; text-align:center;">
					<s:property value="historyId"/>
				</td>
				<td style=" border-bottom:1px solid #e7e9e9; padding:4px; text-align:center;">
					<s:property value="actionBy"/>
				</td>
				<td style=" border-bottom:1px solid #e7e9e9; padding:4px; text-align:center;">
					<s:property value="actionDate"/>
				</td>
				<td style=" border-bottom:1px solid #e7e9e9; padding:4px; text-align:center;">
					<s:property value="actionTaken"/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<tr>
				<td style=" border-bottom:1px solid #e7e9e9; padding:4px; text-align:center;">
					<s:property value="historyId"/>
				</td>
				<td style=" border-bottom:1px solid #e7e9e9; padding:4px; text-align:center;">
					<s:property value="actionBy"/>
				</td>
				<td style=" border-bottom:1px solid #e7e9e9; padding:4px; text-align:center;">
					<s:property value="actionDate"/>
				</td>
				<td style=" border-bottom:1px solid #e7e9e9; padding:4px; text-align:center;">
					<s:property value="actionTaken"/>
				</td>
			</tr>
		</s:else>
	</s:iterator>
</table>
</body>
</html>