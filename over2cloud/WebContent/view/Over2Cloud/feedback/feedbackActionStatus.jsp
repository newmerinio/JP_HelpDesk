<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo,java.util.Iterator,com.Over2Cloud.Rnd.createTable,java.util.List,org.hibernate.SessionFactory,java.util.Map,com.opensymphony.xwork2.ActionContext,com.Over2Cloud.CommonClasses.DateUtil" %>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:property value="%{actionStatusFeedback}"/>
<br /><br />
<center>
<table align="center" width="100%" border="1">
<tr>
	<th bgcolor="grey">
		<b>Name</b>
	</th>
	<th bgcolor="grey">
		<b>Mobile No</b>
	</th>
	<th bgcolor="grey">
		<b>Action Taken</b>
	</th>
	<th bgcolor="grey">
		<b>Action Date</b>
	</th>
	<th bgcolor="grey">
		<b>Action Time</b>
	</th>
</tr>
<s:iterator id="fList"  status="status" value="%{actionList}" >
								<tr bordercolor="#ffffff">
                        			<td align="center" width="20%"><b><s:property value="%{name}"/></b></td>
                        			<td align="center" width="35%"><b><s:property value="%{mobNo}"/></b></td>
                        			<td align="center" width="20%"><b><s:property value="%{actionName}"/></b></td>
                        			<td align="center" width="25%"><b><s:property value="%{actionDate}"/></b></td>
                        			<td align="center" width="25%"><b><s:property value="%{actionTime}"/></b></td>
                        		</tr>
                        	   </s:iterator>
</table>
</center>
</body>
</html>