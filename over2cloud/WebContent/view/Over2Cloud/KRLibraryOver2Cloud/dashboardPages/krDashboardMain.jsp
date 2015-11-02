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
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/KRDashboard.js"/>"></script>
</head>
<body>
<sj:dialog
          id="KrDashboardDialog"
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
		<th width="20%" bgcolor="#EA728A" rowspan="3" >
			<font color="white">Status</font>
		</th>
		<th width="10%" bgcolor="#EA728A" colspan="3" >
			<font color="white">Total</font>
		</th>
		<th width="22%" bgcolor="#EA728A" colspan="3" >
			<font color="white">Last&nbsp;Month</font>
		</th>
		<th width="22%" bgcolor="#EA728A"  colspan="3">
			<font color="white">Last&nbsp;Week</font>
		</th>
		<th width="22%" bgcolor="#EA728A" colspan="3" >
			<font color="white">Yesterday</font>
		</th>
		<th width="22%" bgcolor="#EA728A" colspan="3" >
			<font color="white">Today</font>
		</th>
	</tr>
	<tr >
		<th width="5%" bgcolor="#EA728A" colspan="2">
			<font color="white">Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" rowspan="2">
			<font color="white">Non Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" colspan="2">
			<font color="white">Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" rowspan="2">
			<font color="white">Non Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" colspan="2">
			<font color="white">Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" rowspan="2">
			<font color="white">Non Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" colspan="2">
			<font color="white">Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" rowspan="2">
			<font color="white">Non Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" colspan="2">
			<font color="white">Actionable</font>
		</th>
		<th width="5%" bgcolor="#EA728A" rowspan="2">
			<font color="white">Non Actionable</font>
		</th>
	</tr>
	<tr >
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Done</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Done</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Done</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Done</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Pending</font>
		</th>
		<th width="5%" bgcolor="#EA728A" >
			<font color="white">Done</font>
		</th>
	</tr>
	<s:iterator value="scoreKRList">
	<tr>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" >
			<a href="#"><font color="black"><b><s:property value="%{shareStatus}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','TotalP');">
			<a href="#"><font color="black"><b><s:property value="%{pending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','TotalD');">
			<a href="#"><font color="black"><b><s:property value="%{actionTaken}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','TotalN')">
			<a href="#"><font color="black"><b><s:property value="%{actionable}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','LastMP');">
			<a href="#"><font color="black"><b><s:property value="%{lastMonthPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','LastMD');">
			<a href="#"><font color="black"><b><s:property value="%{lastMonthActionTaken}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','LastMN');">
			<a href="#"><font color="black"><b><s:property value="%{lastMonthActionable}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','LastWP');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','LastWD');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekActionTaken}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','LastWN');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekActionable}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','YesterdayP');">
			<a href="#"><font color="black"><b><s:property value="%{yesterPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','YesterdayD');">
			<a href="#"><font color="black"><b><s:property value="%{yesterActionTaken}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','YesterdayN');">
			<a href="#"><font color="black"><b><s:property value="%{yesterActionable}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','TodayP');">
			<a href="#"><font color="black"><b><s:property value="%{todayPending}"/></b></font></a>
		</td>
			<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','TodayD');">
			<a href="#"><font color="black"><b><s:property value="%{todayActionTaken}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForKRScore('<s:property value="%{shareStatus}"/>','TotalN');">
			<a href="#"><font color="black"><b><s:property value="%{todayActionable}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
		<tr>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" >
			<a href="#"><font color="black"><b>Total</b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{pending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{done}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataOfOGTask('<s:property value="%{status}"/>','Shared')">
			<a href="#"><font color="black"><b><s:property value="%{nonaction}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{lastMonthPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%">
			<font color="black"><b><s:property value="%{lastMonthDone}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%">
			<font color="black"><b><s:property value="%{lastMonthNonAction}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekDone}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{lastWeekNonAction}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{yesterdayPending}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%">
			<font color="black"><b><s:property value="%{yesterdayDone}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%">
			<font color="black"><b><s:property value="%{yesterdayNonAction}"/></b></font>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{todayPending}"/></b></font></a>
		</td>
			<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Today');">
			<a href="#"><font color="black"><b><s:property value="%{todayDone}"/></b></font></a>
		</td>
		<td align="center" bgcolor="#FADCE2" class="sortable" width="5%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','Total');">
			<a href="#"><font color="black"><b><s:property value="%{todayNonAction}"/></b></font></a>
		</td>
	</tr>
</table>
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>KR Status From <s:property value="%{fromDate}"/> To <s:property value="%{toDate}"/>    </b></font></center>
</div>
<div id="ergghhjjh" style="height: 10px;" > 
<table align="center" border="1" width="100%" height="10px;">
               <tr>
                   <th valign="top" >
                       <table align="center" border="0" width="100%" >
                        	 	<tr>
									<th width="30%" bgcolor="AD5CFF" colspan="3">
										<font color="white">KR&nbsp;Details</font>
									</th>
									<th width="70%" bgcolor="AD5CFF" colspan="5">
										<font color="white">Shared&nbsp;By</font>
									</th>
								</tr>
								<tr>
									<th width="12%" bgcolor="AD5CFF" >
										<font color="white">Name</font>
									</th>
									<th width="12%" bgcolor="AD5CFF" >
										<font color="white">Group</font>
									</th>
									<th width="12%" bgcolor="AD5CFF" >
										<font color="white">Sub&nbsp;Group</font>
									</th>
									<th width="12%" bgcolor="AD5CFF" >
										<font color="white">Name</font>
									</th>
									<th width="12%" bgcolor="AD5CFF" >
										<font color="white">Dept</font>
									</th>
									<th width="12%" bgcolor="AD5CFF" >
										<font color="white">Shared&nbsp;Date</font>
									</th>
									<th width="12%" bgcolor="AD5CFF" >
										<font color="white">Access&nbsp;Rights</font>
									</th>
									<th width="12%" bgcolor="AD5CFF" >
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
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="12%" onclick="getOnClickDataForKRScore('KRStatus','<s:property value="%{shareId}"/>');">
											<a href="#"><font color="black"><b><s:property value="%{krName}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="12%" onclick="getOnClickDataForKRScore('KRStatus','<s:property value="%{shareId}"/>');">
											<a href="#"><font color="black"><b><s:property value="%{group}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="12%" onclick="getOnClickDataForKRScore('KRStatus','<s:property value="%{shareId}"/>');">
											<a href="#"><font color="black"><b><s:property value="%{subGroup}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="12%" onclick="getOnClickDataForKRScore('KRStatus','<s:property value="%{shareId}"/>');">
											<a href="#"><font color="black"><b><s:property value="%{shareBy}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="12%" onclick="getOnClickDataForKRScore('KRStatus','<s:property value="%{shareId}"/>');">
											<a href="#"><font color="black"><b><s:property value="%{shareDept}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="12%" onclick="getOnClickDataForKRScore('KRStatus','<s:property value="%{shareId}"/>');">
											<a href="#"><font color="black"><b><s:property value="%{shareDate}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="12%" onclick="getOnClickDataForKRScore('KRStatus','<s:property value="%{shareId}"/>');">
											<a href="#"><font color="black"><b><s:property value="%{accessRights}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="12%" onclick="getOnClickDataForKRScore('KRStatus','<s:property value="%{shareId}"/>');">
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

<div class="contentdiv-small" style="overflow: hidden;">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>KR Ageing</b></font></center>
</div>
	<table align="center" border="0" width="100%" >
               <tr>
                   <th valign="top" >
                       <table align="center" border="0" width="100%" >
                        	 	<tr>
									<th width="30%" bgcolor="#FF1493" >
										<font color="white">This Week</font>
									</th>
									<th width="30%" bgcolor="#FF1493" >
										<font color="white">This Month</font>
									</th>
									<th width="30%" bgcolor="#FF1493">
										<font color="white">This Year</font>
									</th>
								</tr>
								
                       </table>
                    </th>
               </tr>
               <tr >
                   <td>
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                         		<s:iterator value="ageingKRList">
									<tr>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="30%" onclick="getOnClickDataForKRScore('Share With Me','week');">
											<a href="#"><font color="black"><b><s:property value="%{thisWeek}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="30%" onclick="getOnClickDataForKRScore('Share With Me','month');">
											<a href="#"><font color="black"><b><s:property value="%{thisMonth}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="30%" onclick="getOnClickDataForKRScore('Share With Me','year');">
											<a href="#"><font color="black"><b><s:property value="%{thisYear}"/></b></font></a>
										</td>
										
									</tr>
									</s:iterator>
                        </table>
                   </td>
               </tr>
     </table>
</div>

<div class="contentdiv-small" style="overflow: hidden;">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Total Score</b></font></center>
</div>
	<table align="center" border="0" width="100%" >
               <tr>
                   <th valign="top" >
                       <table align="center" border="0" width="100%" >
                        	 	<tr>
									<th width="25%" bgcolor="856085" >
										<font color="white">Total KR</font>
									</th>
									<th width="25%" bgcolor="856085" >
										<font color="white">Shared</font>
									</th>
									<th width="25%" bgcolor="856085">
										<font color="white">Download</font>
									</th>
									<th width="25%" bgcolor="856085">
										<font color="white">Edit</font>
									</th>
								</tr>
								
                       </table>
                    </th>
               </tr>
               <tr >
                   <td>
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                         		<s:iterator value="totalScoreKRList">
									<tr>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="25%" onclick="getOnClickDataForKRTScore();">
											<a href="#"><font color="black"><b><s:property value="%{totalKR}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="25%" onclick="getOnClickDataForKRScore('Share With Me','share');">
											<a href="#"><font color="black"><b><s:property value="%{shareKR}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="25%" onclick="getOnClickDataForKRScore('Share With Me','download');">
											<a href="#"><font color="black"><b><s:property value="%{downloadKR}"/></b></font></a>
										</td>
										<td align="center" bgcolor="#F5EBFF" class="sortable" width="25%" onclick="getOnClickDataForKRScore('Share With Me','edit');">
											<a href="#"><font color="black"><b><s:property value="%{editKR}"/></b></font></a>
										</td>
									</tr>
									</s:iterator>
                        </table>
                   </td>
               </tr>
     </table>
</div>
<div class="contentdiv-small" style="overflow: hidden;">

</div>
</div>
</body>
</html>