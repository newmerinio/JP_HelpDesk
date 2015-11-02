<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%

String deptFlag=request.getParameter("patientCareFlag");
System.out.println("patientCareFlag is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>."+deptFlag);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
function showData(searchDept,searchField)
{

	if(searchField=='Pending')
	{
		var conP = "<%=request.getContextPath()%>";
		$("#mybuttondialog").load(conP+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedActionDash.action?feedStatus=Pending&toSubDeptName='"+searchDept.split(" ").join("%20")+"'&searchFlagName='"+searchField+"'");
		 $("#mybuttondialog").dialog('open');
	}
	else if(searchField=='Resolved')
	{
		var conP = "<%=request.getContextPath()%>";
		$("#mybuttondialog").load(conP+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedActionDash.action?feedStatus=Resolved&toSubDeptName='"+searchDept.split(" ").join("%20")+"'&searchFlagName='"+searchField+"'");
		 $("#mybuttondialog").dialog('open');
	}
	else if(searchField=='level1' || searchField=='level2' || searchField=='level3'|| searchField=='level4'|| searchField=='level5')
	{
		var conP = "<%=request.getContextPath()%>";
		$("#mybuttondialog").load(conP+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedActionDash.action?feedStatus=Resolved&toSubDeptName='"+searchDept.split(" ").join("%20")+"'&searchFlagName='"+searchField+"'");
		 $("#mybuttondialog").dialog('open');
	}
	
}
</script>
<style type="text/css">
	 td.sortable:hover {
    cursor: pointer;
    
    	td {
	padding: 5px;
	padding-left: 20px;
}

.pending
{
	background-color: red;
}
    
	}	
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
</head>
<body>
<div class="list-icon"><div class="clear"></div><div class="head"><s:property value="%{mainHeaderName}"/></div></div>
<sj:dialog 
    	id="mybuttondialog" 
    	autoOpen="false" 
    	modal="true" 
    	width="1050"
		height="500"
    	title=" Tickets Status Data"
    	resizable="false"
    >
</sj:dialog>
<center><br><br><br>
<h1>Department Wise Dashboard </h1>
<table height="100%" width="80%" align="center" border="1">
	<tr>
		<th width="30%" bgcolor="grey" >
			<font color="white">Department</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Open</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Closed</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Level 1</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Level 2</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Level 3</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Level 4</font>
		</th>
	</tr>
	<s:iterator value="feedDashboardList">
	<tr>
		<td align="center" bgcolor="lightgrey" class="sortable" width="30%">
			<s:property value="%{subDeptName}"/>
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" class="class="color" onclick="showData('<s:property value="%{subDeptName}"/>','Pending')">
		<font color="red"><s:property value="%{openTickets}"/></font>
		</td>
		<td align="center"  bgcolor="lightgrey" class="sortable" width="10%" onclick="showData('<s:property value="%{subDeptName}"/>','Resolved')">
		<font color="green"><s:property value="%{resolvedTickets}"/>
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" onclick="showData('<s:property value="%{subDeptName}"/>','level1')">
		<s:property value="%{level1}"/>
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" onclick="showData('<s:property value="%{subDeptName}"/>','level2')">
		<s:property value="%{level2}"/>
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" onclick="showData('<s:property value="%{subDeptName}"/>','level3')">
		<s:property value="%{level3}"/>
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" onclick="showData('<s:property value="%{subDeptName}"/>','level4')">
		<s:property value="%{level4}"/>
		</td>
	</tr>
	</s:iterator>
</table>
<h1>Category Wise Data </h1>
<table height="100%" width="80%" align="center" border="1">
	<tr>
		<th width="30%" bgcolor="grey" >
			<font color="white">Department</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Open</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Closed</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Level 1</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Level 2</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Level 3</font>
		</th>
		<th width="10%" bgcolor="grey">
			<font color="white">Level 4</font>
		</th>
	</tr>
	<tr>
		<td align="center" bgcolor="lightgrey" class="sortable" width="30%">
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" >
		<font color="red"></font>
		</td>
		<td align="center"  bgcolor="lightgrey" class="sortable" width="10%">
		
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%" >
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%">
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%">
		</td>
		<td align="center" bgcolor="lightgrey" class="sortable" width="10%">
		</td>
	</tr>
</table>
<center>
</body>
</html>