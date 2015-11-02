<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/productivityEvaluation/dashboardProductivity.js"/>"></script>
</head>
<body>
<sj:dialog
          id="productivityDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="View Details"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1100"
          height="350"
          >
          <div id="result_part"></div>
</sj:dialog>
<div class="middle-content">
<div class="contentdiv-small" style="y-overflow: scroll; x-overflow:hidden  ;width:95%;height:271px;margin-top: 10px">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>OG&nbsp;Task</b></font></center>
</div>
<table height="40%" width="100%" align="center" border="1">
	<tr >
		<th width="20%" bgcolor="#EA728A" rowspan="2">
			<font color="white">Status</font>
		</th>
		<th width="40%" bgcolor="#EA728A" colspan="4" >
			<font color="white">Total</font>
		</th>
		<th width="40%" bgcolor="#EA728A"  colspan="4">
			<font color="white">Current&nbsp;Month</font>
		</th>
	</tr>
	<tr >
		<th width="10%" bgcolor="#EA728A" >
			<font color="white">Shared</font>
		</th>
		<th width="10%" bgcolor="#EA728A" >
			<font color="white">Implemented</font>
		</th>
		<th width="10%" bgcolor="#EA728A" >
			<font color="white">Can not be implemented</font>
		</th>
		<th width="10%" bgcolor="#EA728A" >
			<font color="white">Already Running</font>
		</th>
		
	    <th width="10%" bgcolor="#EA728A" >
			<font color="white">Shared</font>
		</th>
		<th width="10%" bgcolor="#EA728A" >
			<font color="white">Implemented</font>
		</th>
		<th width="10%" bgcolor="#EA728A" >
			<font color="white">Can not be implemented</font>
		</th>
		<th width="10%" bgcolor="#EA728A" >
			<font color="white">Already Running</font>
		</th>
	</tr>
	
	<s:iterator value="OGTaskList">
	<tr>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%" >
			<a href="#"><font color="black"><b><s:property value="%{status}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%" onclick="getOnClickDataOfOGTask('<s:property value="%{status}"/>','Shared')">
			<a href="#"><font color="black"><b><s:property value="%{totalCountToday}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{implementToday}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{notImplementToday}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%">
			<font color="black"><b><s:property value="%{runningToday}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{totalCountCurrent}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%">
			<font color="black"><b><s:property value="%{implementCurrent}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{notImplementCurrent}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="9%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{runningCurrent}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
<%-- 	<tr bgcolor="C0E0DA">
	<td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalData"/></b></font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="today"/></b></font></td>
    <td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalData"/></b></font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="today"/></b></font></td>
 </tr> --%>
</table>
</div>
<div class="contentdiv-small" style="width:46%;height:250px;margin-top: 10px">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>CMO&nbsp;Comm</b></font></center>
</div>
<table height="10%" width="100%" align="center" border="1">
	<tr>
		<th width="15%" bgcolor="40B240" >
			<font color="white">Status</font>
		</th>
		<th width="15%" bgcolor="40B240" >
			<font color="white">Total</font>
		</th>
		<th width="15%" bgcolor="40B240" >
			<font color="white">This&nbsp;Month</font>
		</th>
	</tr>
	<s:iterator value="cmoCommList">
	<tr>
		<td align="center" bgcolor="#E6F5E6" class="sortable" width="60%">
			<font color="black"><b><s:property value="%{status}"/></b></font>
		</td>
		<td align="center" bgcolor="#E6F5E6" class="sortable" width="20%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{totalCountCurrent}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#E6F5E6" class="sortable" width="20%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{totalCountToday}"/></b></font></a>
		</td>
	
	</tr>
	</s:iterator>
<%-- 	<tr bgcolor="C0E0DA">
	<td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalData"/></b></font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="today"/></b></font></td>
 </tr> --%>
</table>
</div>
<div class="contentdiv-small" style="width:45%;height:250px;">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Projects</b></font></center>
</div>
<table height="10%" width="100%" align="center" border="1">
	<tr>
		<th width="30%" bgcolor="AD5CFF" >
			<font color="white">Status</font>
		</th>
		<th width="15%" bgcolor="AD5CFF" >
			<font color="white">Total</font>
		</th>
		<th width="15%" bgcolor="AD5CFF" >
			<font color="white">Pending</font>
		</th>
		<th width="15%" bgcolor="AD5CFF" >
			<font color="white">Missed</font>
		</th>
		<th width="15%" bgcolor="AD5CFF" >
			<font color="white">Done</font>
		</th>
		
	</tr>
	<s:iterator value="projectsList">
	<tr>
		<td align="center" bgcolor="#F5EBFF" class="sortable" width="30%">
			<font color="black"><b><s:property value="%{status}"/></b></font>
		</td>
		<td align="center" bgcolor="#F5EBFF" class="sortable" width="15%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{totalCountToday}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#F5EBFF" class="sortable" width="15%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{missed}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#F5EBFF" class="sortable" width="15%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{pending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#F5EBFF" class="sortable" width="15%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{done}"/></b></font></a>
		</td>
	
	</tr>
	</s:iterator>
<%-- 	<tr bgcolor="C0E0DA">
	<td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalData"/></b></font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="today"/></b></font></td>
 </tr> --%>
</table>
</div>
</div>
</body>
</html>