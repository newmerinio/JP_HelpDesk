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
		<center><font color="#000000"><b>KR&nbsp;Score</b></font></center>
</div>
<table width="100%" align="center" border="1">
	<tr >
		<th width="20%" bgcolor="#EA728A" rowspan="2" >
			<font color="white">Status</font>
		</th>
		<th width="20%" bgcolor="#EA728A" colspan="3" >
			<font color="white">Total</font>
		</th>
		<th width="20%" bgcolor="#EA728A" colspan="3" >
			<font color="white">Last&nbsp;Month</font>
		</th>
		<th width="20%" bgcolor="#EA728A"  colspan="3">
			<font color="white">Last&nbsp;Week</font>
		</th>
		<th width="20%" bgcolor="#EA728A" colspan="3" >
			<font color="white">Yesterday</font>
		</th>
		<th width="20%" bgcolor="#EA728A" colspan="3" >
			<font color="white">Today</font>
		</th>
	</tr>
	<tr >
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Actionable</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Action&nbsp;Taken</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Actionable</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Action&nbsp;Taken</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Actionable</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Action&nbsp;Taken</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Actionable</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Action&nbsp;Taken</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Actionable</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="7%" bgcolor="#EA728A" >
			<font color="white">Action&nbsp;Taken</font>
		</th>
	</tr>
	<s:iterator value="scoreKRList">
	<tr>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" >
			<a href="#"><font color="black"><b><s:property value="%{shareStatus}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataOfOGTask('<s:property value="%{status}"/>','Shared')">
			<a href="#"><font color="black"><b><s:property value="%{actionable}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{pending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{actionTaken}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%">
			<font color="black"><b><s:property value="%{lastMonthActionable}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{lastMonthPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%">
			<font color="black"><b><s:property value="%{lastMonthActionTaken}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekActionable}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekActionTaken}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%">
			<font color="black"><b><s:property value="%{yesterActionable}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{yesterPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%">
			<font color="black"><b><s:property value="%{yesterActionTaken}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{todayActionable}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{todayPending}"/></b></font></a>
		</td>
			<td align="center" bgcolor="#FADCE2" class="sortable" width="7%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{todayActionTaken}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
</table>
</div>

<div class="contentdiv-small" style="y-overflow: hidden; x-overflow:hidden  ;width:95%;height:230px;">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>KR Status From <s:property value="%{fromDate}"/> To <s:property value="%{toDate}"/>    </b></font></center>
</div>
	<table align="center" border="0" width="100%" >
               <tr>
                   <th valign="top" >
                       <table align="center" border="0" width="100%" >
                        	 	<tr>
									<th width="30%" bgcolor="AD5CFF" colspan="3">
										<font color="white">KR Details</font>
									</th>
									<th width="70%" bgcolor="AD5CFF" colspan="5">
										<font color="white">Shared By</font>
									</th>
								</tr>
								<tr>
									<th width="10%" bgcolor="AD5CFF" >
										<font color="white">Name</font>
									</th>
									<th width="10%" bgcolor="AD5CFF" >
										<font color="white">Group</font>
									</th>
									<th width="10%" bgcolor="AD5CFF" >
										<font color="white">Sub Group</font>
									</th>
									<th width="10%" bgcolor="AD5CFF" >
										<font color="white">Name</font>
									</th>
									<th width="10%" bgcolor="AD5CFF" >
										<font color="white">Dept</font>
									</th>
									<th width="10%" bgcolor="AD5CFF" >
										<font color="white">Shared Date</font>
									</th>
									<th width="10%" bgcolor="AD5CFF" >
										<font color="white">Access Rights</font>
									</th>
									<th width="10%" bgcolor="AD5CFF" >
										<font color="white">Actionable</font>
									</th>
								</tr>
                       </table>
                    </th>
               </tr>
               <tr >
                   <td>
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" >
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                         		<s:iterator value="ksStatusList">
									<tr>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="10%">
											<font color="black"><b><s:property value="%{krName}"/></b></font>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="10%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
											<a href="#"><font color="black"><b><s:property value="%{group}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="10%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
											<a href="#"><font color="black"><b><s:property value="%{subGroup}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="10%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
											<a href="#"><font color="black"><b><s:property value="%{shareBy}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="10%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
											<a href="#"><font color="black"><b><s:property value="%{shareDept}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="10%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
											<a href="#"><font color="black"><b><s:property value="%{shareDate}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="10%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
											<a href="#"><font color="black"><b><s:property value="%{accessRights}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="10%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
											<a href="#"><font color="black"><b><s:property value="%{actionable}"/></b></font></a>
										</td>
									</tr>
									</s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
     </table>
</div>
</div>
</body>
</html>