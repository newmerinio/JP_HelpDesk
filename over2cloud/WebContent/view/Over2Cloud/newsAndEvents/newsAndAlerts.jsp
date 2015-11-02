<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<title>Insert title here</title>
<style>
	.headHeading
	{
		font-size: 9px;
		font-size: 2.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #CCCCCC;
	}
</style>
</head>
<body>
<div class="clear"></div>
	<div class="list-icon">
		<div class="head">News And Alerts For <s:property value="%{mainHeaderName}"/></div>
	</div>
<div class="clear"></div>
<table width="100%" cellspacing="0" cellpadding="3" align="center" border="0">
	
	<tr>
		<td height="10px">
		</td>
	</tr>
	<tr>
		<td width="100%" height="100%">
			<div>
				<table height="100%" align="left" border="0">
					<s:iterator value="newsAlertsList" id="parentTakeaction" status="counter">
						<tr>
							<td valign="top" height="100%">
								<s:if test="%{severity=='High-Red'}">
									<font color="red">
										<s:property value="%{desc}" />
									</font>
								</s:if>
								<s:elseif test="%{severity=='Normal-Blue'}" >
									<font color="blue">
										<s:property value="%{desc}" />
									</font>
								</s:elseif>
								<s:elseif test="%{severity=='Low-Green'}">
									<font color="green">
										<s:property value="%{desc}" />
									</font>
								</s:elseif>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</td>
	</tr>
</table>
</body>
</html>