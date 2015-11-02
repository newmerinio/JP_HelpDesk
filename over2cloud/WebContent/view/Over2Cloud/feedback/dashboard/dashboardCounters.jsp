<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.counertDiv table td:HOVER{
 background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;
}

.StyleScheme1{
background: #fbf9ee url(template/theme/mytheme/images/ui-bg_glass_55_fbf9ee_1x400.png) 50% 50% repeat-x;


}

</style>
</head>
<body >
<div class="counertDiv">
<center><font color="#000000"><b><s:property value="%{graphHeader}"/></b></font></center>
<table width="100%" border="1" style="border-radius:3px;float:left;margin-top:10px">
		 <tr style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<td  align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Department</b></font></td>
			 <td style="cursor: pointer;text-decoration: none;border-radius:3px;background:#fbf9ee url(template/theme/mytheme/images/ui-bg_glass_55_fbf9ee_1x400.png) 50% 50% repeat-x;border-radius:3px;"  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Total</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Pending</b></font></td>
		<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Snooze</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>High&nbsp;Priority</b></font></td> 
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Ignore</b></font></td>
      		<td  align="center" width="8%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Re-Assign</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Resolved</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Noted</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2" title="Ticket Opened"><b>T&nbsp;O</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2" title="No Further Action Required"><b>N&nbsp;F&nbsp;A</b></font></td>
     	   
     	    <td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Action</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>CAPA</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>%</b></font></td>
     	    <td  align="center" width="8%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2" ><b>Productivity</b></font></td>
     	    
       </tr>
	
		<s:iterator  id="first" status="status" value="%{dashPojoList}">
		<tr align="center" class="tableData" bgcolor="">
			<td align="left" style="cursor: pointer;text-decoration: none;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;"   onclick="getAllotToDetails('<s:property value="%{deptId}"/>','<s:property value="%{deptName}"/>');">
				<span style="color:black;"><s:property value="%{deptName}"/></span>
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','','<s:property value="%{deptName}"/>');"  class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{totalCounter}"/></b></font></a>
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','Pending','<s:property value="%{deptName}"/>');"  class="StyleScheme1">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{pending}"/></b></font></a>
				
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','Snooze','<s:property value="%{deptName}"/>');"  class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{sz}"/></b></font></a>
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','High Priority','<s:property value="%{deptName}"/>');"  class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{hp}"/></b></font></a>
			</td> 
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','Ignore','<s:property value="%{deptName}"/>');"  class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{ig}"/></b></font></a>
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','Re-Assign','<s:property value="%{deptName}"/>');"  class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{rAs}"/></b></font></a>
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','Resolved','<s:property value="%{deptName}"/>');" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{resolved}"/></b></font></a>
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','Noted','<s:property value="%{deptName}"/>');" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{noted}"/></b></font></a>
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','Ticket Opened','<s:property value="%{deptName}"/>');" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{ticketOpened}"/></b></font></a>
			</td>
			<td onclick="getOnClickDataForTicket('<s:property value="%{deptId}"/>','No Further Action Required','<s:property value="%{deptName}"/>');" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{nfa}"/></b></font></a>
			</td>
			
				
			<td onclick="getFeedbackActionOnClick('<s:property value="%{deptId}"/>','Action','<s:property value="%{deptName}"/>');" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalAction}"/></b></font></a>
			</td>
			<td onclick="getFeedbackActionOnClick('<s:property value="%{deptId}"/>','Capa','<s:property value="%{deptName}"/>');" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalCapa}"/></b></font></a>
			</td>
			<td  class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{percent}"/></b></font></a>
			</td>
			<td align="center" style="cursor: pointer;text-decoration: none;" >
				<b style="color: #275985;"><a href="#"  onclick="getProductivity('<s:property value="%{deptId}"/>','Category');"><img src="images/group.png" width="20" height="20" alt="Show Counters" title="Category Productivity" /></a></b>
			
			&nbsp;&nbsp;
				<b style="color: #275985;"><a href="#" onclick="getProductivity('<s:property value="%{deptId}"/>','Employee');"><img src="images/employee.png" width="20" height="20" alt="Show Counters" title="Employee Productivity" /></a></b>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<b style="color: #275985;"><a href="#" onclick="getQualityScore('<s:property value="%{deptId}"/>');"><img src="images/qualityScore.png" width="20" height="20" alt="Show Quality Score" title="Quality Score" style="margin-left: -22px;margin-top: -23px;"  /></a></b>
			</td>
		</tr>
		</s:iterator>
	<tr class="tableData" bgcolor="#C4DCFB">
	<td align="left" width="15%" height="40%"><b>Total</b> </td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket1('All','All');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalCounter"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('','Pending','');"><a style="cursor: pointer;text-decoration: none;"href="#"><font color="black"><b><s:property value="totalPending"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('','Snooze','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalSnooze"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('','High Priority','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalHighPriority"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('','Ignore','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalIgnore"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('','Re-Assign','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalReAssign"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('','Resolved','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalResolve"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket(''','Noted','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalNoted"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket(''','Ticket Opened','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalTO"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket(''','No Further Action Required','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalNFA"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getFeedbackActionOnClick('','Action','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="SubTotalAction"/></b></font></a></td>
		<td align="center" width="5%" height="40%" onclick="getFeedbackActionOnClick('','Capa','');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="SubTotalCapa"/></b></font></a></td>
		<td align="center" width="5%" height="40%" ><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b>100</b></font></a></td>
 </tr>
	</table>
	<br>
	</div>
</body>
</html>