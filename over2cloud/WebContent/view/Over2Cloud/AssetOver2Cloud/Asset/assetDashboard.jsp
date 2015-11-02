<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="jqwidgets/jqxchart.js"></script>
<script type="text/javascript" src="<s:url value="/js/asset/assetDashboard.js"/>"></script>
<title>Insert title here</title>
</head>
<body>
<sj:dialog
          id="dataDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="450"
          >
          <div id="dataDiv"></div>
</sj:dialog>
<div class="middle-content">
<div class="contentdiv-small" style="overflow: hidden; width: 96%">
	<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Outletwise Ticket Status</b></font></center>
	</div>

<div class="clear"></div>
<table width="100%" border="1">
		 <tr bgcolor="#BF8AEF">
			<td  align="center" width="15%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Outlet Name</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Pending</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 1</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 2</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 3</b></font></td>
		    <td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 4</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Level 5</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Snooze</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>High&nbsp;Priority</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Ignore</b></font></td>
			<td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Resolved</b></font></td>
     	    <td  align="center" width="5%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Total</b></font></td>
       </tr>
	
		<s:iterator  id="first" status="status" value="%{allOutLetStatus}">
		<tr align="center" class="tableData" bgcolor="#EDDFFB">
			<td align="left" width="">
				<font color="black"><b><s:property value="%{outletName}"/></b></font>
			</td>
			<s:if test="%{pending==0}">
			<td>
			<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{pending}"/></b></font></a>
			</td>
			</s:if>
			<s:else>
			<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Pending');">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{pending}"/></b></font></a>
			</td>
			</s:else>
			
			<s:if test="%{level1==0}">
			<td >
			<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level1}"/></b></font></a>
			</td>
			</s:if>
			<s:else>
			<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Level1');">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level1}"/></b></font></a>
			</td>
			</s:else>
			
			<s:if test="%{level2==0}">
				<td>
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level2}"/></b></font></a>
				</td>
			</s:if>
			<s:else>
				<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Level2');">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level2}"/></b></font></a>
				</td>
			</s:else>
			
			<s:if test="%{level3==0}">
			<td>
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level3}"/></b></font></a>
			</td>
			</s:if>
			<s:else>
			<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Level3');">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level3}"/></b></font></a>
				
			</td>
			</s:else>
			
			<s:if test="%{level4==0}">
			<td>
			<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level4}"/></b></font></a>
			</td>
			</s:if>
			<s:else>
			<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Level4');">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level4}"/></b></font></a>
			</td>
			</s:else>
			
				<s:if test="%{level5==0}">
				<td>
					<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level5}"/></b></font></a>
				</td>
				</s:if>
				<s:else>
				<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Level5');">
					<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{level5}"/></b></font></a>
				</td>
				</s:else>
				
				<s:if test="%{snooze==0}">
			    <td >
				  <a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{snooze}"/></b></font></a>
			   </td>
				</s:if>
				<s:else>
				<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Snooze');">
					<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{snooze}"/></b></font></a>
				</td>
				</s:else>
				
				<s:if test="%{highpriority==0}">
				<td >
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{highpriority}"/></b></font></a>
				</td>
				</s:if>
				<s:else>
					<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','High Priority');">
				    <a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{highpriority}"/></b></font></a>
			     </td>
				</s:else>
			
			<s:if test="%{ignore==0}">
				<td >
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{ignore}"/></b></font></a>
			</td>
				</s:if>
				<s:else>
					<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Ignore');">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{ignore}"/></b></font></a>
			</td>
				</s:else>
			
			<s:if test="%{resolve==0}">
				<td>
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{resolve}"/></b></font></a>
			</td>
				</s:if>
				<s:else>
					<td onclick="getOnClickDataForTicket('<s:property value="%{id}"/>','Resolved');">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{resolve}"/></b></font></a>
			</td>
				</s:else>
				
				<s:if test="%{counter==0}">
				<td>
					<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{counter}"/></b></font></a>
				</td>
				</s:if>
				<s:else>
					<td onclick="getOnClickDataForTicket1('<s:property value="%{id}"/>','All');">
					<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{counter}"/></b></font></a>
					</td>
				</s:else>
		</tr>
		</s:iterator>
	<tr class="tableData" bgcolor="#EDDFFB">
	<td align="left" width="15%" height="40%"><font color="black"><b>Total</b></font> </td>
		<s:if test="totalPending==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"href="#"><font color="black"><b><s:property value="totalPending"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Pending');"><a style="cursor: pointer;text-decoration: none;"href="#"><font color="black"><b><s:property value="totalPending"/></b></font></a></td>
		</s:else>
		
		<s:if test="totalLevel1==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel1"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Level1');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel1"/></b></font></a></td>
		</s:else>
		
		<s:if test="totalLevel2==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel2"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Level2');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel2"/></b></font></a></td>
		</s:else>
		
		<s:if test="totalLevel3==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel3"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Level3');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel3"/></b></font></a></td>
		</s:else>
		<s:if test="totalLevel4==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel4"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Level4');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel4"/></b></font></a></td>
		</s:else>
		<s:if test="totalLevel5==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel5"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Level5');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalLevel5"/></b></font></a></td>
		</s:else>
		<s:if test="totalSnooze==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalSnooze"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Snooze');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalSnooze"/></b></font></a></td>
		</s:else>
		<s:if test="totalHighPriority==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalHighPriority"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','High Priority');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalHighPriority"/></b></font></a></td>
		</s:else>
		<s:if test="totalIgnore==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalIgnore"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Ignore');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalIgnore"/></b></font></a></td>
		</s:else>
		<s:if test="totalResolve==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalResolve"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket('All','Resolved');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalResolve"/></b></font></a></td>
		</s:else>
		<s:if test="totalCounter==0">
			<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalCounter"/></b></font></a></td>
		</s:if>
		<s:else>
			<td align="center" width="5%" height="40%" onclick="getOnClickDataForTicket1('All','All');"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="totalCounter"/></b></font></a></td>
		</s:else>
 </tr>
	</table>

</div>
<div class="contentdiv-small" style="overflow: hidden;width: 96% ;margin-top: -5px;">
<div class="clear"></div>
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Outletwise Pending Ticket Status</b></font></center>
</div>
	<table align="center" border="0" width="100%" height="30%">
               <tr bordercolor="black">
                   <th valign="top" bgcolor="#7BA726" >
                       <table align="center" border="0" width="100%" >
                        	  <tr>
                        			<th align="center" class="headings1" width="12%"><font color="white">Ticket&nbsp;Id</font></th>
                        			<th align="center" class="headings1" width="12%"><font color="white">Opened&nbsp;At</font></th>
                        			<th align="center" class="headings1" width="12%"><font color="white">Outlet</font></th>
                        			<th align="center" class="headings1" width="12%"><font color="white">Asset</font></th>
                        			<th align="center" class="headings1" width="12%"><font color="white">Category</font></th>
                        			<th align="center" class="headings1" width="12%"><font color="white">Brief</font></th>
                        			<th align="center" class="headings1" width="12%"><font color="white">AMC</font></th>
                        			<th align="center" class="headings1" width="12%"><font color="white">Alloted&nbsp;To</font></th>
                        			<th align="center" class="headings1" width="12%"><font color="white">Status</font></th>
                        	  </tr>
                       </table>
                    </th>
               </tr>
               <tr>
                   <td valign="top" height="10%">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" >
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                              <s:iterator id="fList"  status="status" value="%{allOutLetPendingStatus}" >
								<tr bordercolor="#ffffff">
                        			<td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{ticket_no}"/></b></font></a></td>
                        			<td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{open_date}"/></b></font></a></td>
                        			<td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{outletName}"/></b></font></a></td>
                        			<td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{assetName}"/></b></font></a></td>
                        		    <td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{feedback_catg}"/></b></font></a></td>
                        			<td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{brand}"/></b></font></a></td>
                        			<td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{amc}"/></b></font></a></td>
                        			<td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{empName}"/></b></font></a></td>
                        			<td align="center" width="12%" onclick="getPendingTicketData('Pending','<s:property value="%{id}"/>');"><a href="#"><font color="black"><b><s:property value="%{status}"/></b></font></a></td>
                        		</tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
              </table>
</div>
<%-- 
<div class="contentdiv-small" style="overflow: hidden;" >
<div class="clear"></div>
<!--<div id="graphBar" style="display: block;">
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('refresh2stGraph')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('2stBlockGraph')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('2stBlock')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data" /></a></div>
<div id="refresh2stGraph">
<div id='jqxChartAstPermance' style="width: 90%; height: 200px" align="center"></div>
</div>
</div>

<div style="height:auto; margin-bottom:10px; display: none;" id='2stBlock'>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('2stBlock')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('2stBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsBar('graphBar')"><img src="images/barChart.png" width="15" height="15" alt="Get Data" title="Get Bar Chart" /></a></div>
<div id="refreshDiv2nd">
<B>Asset Type Performance Rank</B>
<%int i=1; %>
<br>
<br>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
    <tr>
        <td valign="bottom">
            <table  align="center" border="1" style="border-style:dotted solid;" width="100%">
             	  <tr bgcolor="#DAA520">
             			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rank</b></font></td>
                        <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Asset&nbsp;Type</b></font></td>
                        <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Score</b></font></td>
             	  </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top" style="padding-top: 5px" align="center">
        <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="5" direction="up" >
	          <table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                  <s:iterator id="assetList2"  status="status" value="%{assetRankForDashboard.assetList}" > 
					 <tr bordercolor="#ffffff">
                		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','performanceRank','<s:property value="#assetList2.assetid"/>');" href="#"><%=i%><b></b></a></font></td>
                   	    <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','performanceRank','<s:property value="#assetList2.assetid"/>');" href="#"><s:property value="#assetList2.assetType" /></a></font></td>
                        <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','performanceRank','<s:property value="#assetList2.assetid"/>');"href="#"><s:property value="#assetList2.assetScore" /></a></font></td>
                     </tr>
                     <%i++;%>
             	   </s:iterator>
             </table>
        </marquee>
        </td>
    </tr>
</table>
</div>
</div>
-->
</div>

<div class="contentdiv-small" style="overflow: hidden;" >
<div class="clear"></div>

<!--<div id="graphBar1" style="display: block;"> 
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('refresh3stGraphBlock')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('3stBlockGraph1')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('3stBlock')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data" /></a></div>
<div id="refresh3stGraphBlock">
<div id='jqxChartUserPermance' style="width: 90%; height: 200px" align="center"></div>
</div>
</div>

<div style="height:auto; margin-bottom:10px; display: none;" id='3stBlock'>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3stBlock')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('3stBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsBar('graphBar1')"><img src="images/barChart.png" width="15" height="15" alt="Get Data" title="Get Bar Chart" /></a></div>
<div id="refreshDiv3rd">
<B>User Asset Breakdown Rank</B>
<% int j=1; %>
<br>
<br>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
        <tr>
            <td valign="bottom">
                <table align="center" border="1" style="border-style:dotted solid;" width="100%">
                 	  <tr bgcolor="#008B8B">
                 			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rank</b></font></td>
                          <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>User Name</b></font></td>
                          <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Score</b></font></td>
                 	  </tr>
                </table>
             </td>
        </tr>
        <tr>
        <td valign="top" style="padding-top: 5px" align="center">
        <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="2" scrolldelay="0" direction="up" >
			<table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                  <s:iterator id="assetList2"  status="status" value="%{userRankForDashboard.assetList}" > 
						<tr bordercolor="#ffffff">
             				<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','User','<s:property value="#assetList2.assetid"/>');" href="#"><%=j%></b></a></font></td>
                    	    <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','User','<s:property value="#assetList2.assetid"/>');" href="#"><s:property value="#assetList2.empName" /></a></font></td>
                            <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','User','<s:property value="#assetList2.assetid"/>');" href="#"><s:property value="#assetList2.assetScore" /></a></font></td>
                     	</tr>
                     <%j++;%>
             	   </s:iterator>
            </table>
        </marquee>
        </td>
        </tr>
</table>
</div>
</div>
--></div> --%>
<!-- 
<div class="contentdiv-small" style="overflow: hidden;" >
<div style="height:auto; margin-bottom:10px; display: block;" id='4stBlock'>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('4stBlockSupportPreventiveRefresh')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('4stBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('4stBlock')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('4stBlockSupportPie')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart of support" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('4stBlockPreventivePie')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart of Preventive" /></a></div>
<div id="4stBlockSupportPreventiveRefresh">
<B>Asset Due Action Matrix</B>
<br>
<br>
<table border="1" width="100%" align="center" >
<tbody>
<tr bgcolor="FF6699">
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Type</b></font></td>
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>7&nbsp;Days</b></font></td>
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>30&nbsp;Days</b></font></td>
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>90&nbsp;Days</b></font></td>
<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>180&nbsp;Days</b></font></td>
</tr>
 
 <s:iterator id="assetList2"  status="status" value="%{supportForDashboard.assetList}" > 
<tr bgcolor="#FFB6C1">
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#assetList2.assetName" /></b>   </a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','7Days','Support');" href="#"><s:property value="#assetList2.next7Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','30Days','Support');" href="#"><s:property value="#assetList2.next30Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','90Days','Support');" href="#"><s:property value="#assetList2.next90Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','180Days','Support');" href="#"><s:property value="#assetList2.next180Days" /></a></font></td>
</tr>
</s:iterator>
 <s:iterator id="assetList2"  status="status" value="%{expireForDashboard.assetList}" > 
<tr bgcolor="#FFB6C1">
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="#assetList2.assetName" /></b></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','7Days','Preventive');" href="#"><s:property value="#assetList2.next7Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','30Days','Preventive');" href="#"><s:property value="#assetList2.next30Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','90Days','Preventive');" href="#"><s:property value="#assetList2.next90Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','180Days','Preventive');" href="#"><s:property value="#assetList2.next180Days" /></a></font></td>
</tr>
</s:iterator>
</tbody>
</table>
</div>
</div>
<div style="height:auto; margin-bottom:10px; display: none;" id='4stBlockSupportPie'>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('4stBlockSupportGraphRefresh')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('4stBlockSupport')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('4stBlock')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('4stBlockSupportPie')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart of support" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('4stBlockPreventivePie')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart of Preventive" /></a></div>
<div id="4stBlockSupportGraphRefresh">
<B>Asset Support Action Matrix</B> 
<center> 
    <sjc:chart
        id="chartPie1"
        cssStyle="width: 200px; height: 300px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        onHoverTopics="true"
    >
    <s:iterator value="%{supportData}">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		
		    		/>	
		    		
    	</s:iterator>
    </sjc:chart>
    </center>
    </div>
</div>

<div style="height:auto; margin-bottom:10px; display: none;" id='4stBlockPreventivePie'>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('4stBlockPreventiveGraphRefresh')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('4stBlockPreventiveData')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('4stBlock')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('4stBlockSupportPie')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart of support" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('4stBlockPreventivePie')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart of Preventive" /></a></div>
<div id="4stBlockPreventiveGraphRefresh">
<B>Asset Preventive Action Matrix</B>
<center>
    <sjc:chart
        id="chartPiePreventive"
        cssStyle="width: 200px; height: 300px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        onHoverTopics="true"
    >
    <s:iterator value="%{preventiveData}">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		
		    		/>	
		    		
    	</s:iterator>
    </sjc:chart>
    </center>
    </div>
</div>
</div> -->

<!--<div class="contentdiv-small" style="overflow: hidden;">
<div class="clear"></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('5stBlockRefresh')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('5stBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div id="5stBlockRefresh">
<div style="height:auto; margin-bottom:10px;" ><B>Asset Ticket Status</B></div>
	<table border="1" width="100%" align="center">
	    <tr>
			<td align="center" width="16%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Ticket&nbsp;No</b></td>
			<td align="center" width="30%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Feedback&nbsp;By</b></td>
			<td align="center" width="25%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Date</b></td>
		    <td align="center" width="15%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Time</b></td>
		    <td align="center" width="20%" bgcolor="#2799CE" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Status</b></td>
		</tr>
	</table>
    <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="5" direction="up" height="310" width="100%" style="color: #000000; text-align:left; font-family:Verdana; font-size:8pt">
    <s:iterator id="ticketData"  status="status" value="%{ticketsList}" >
	<table  width="100%" align="center">
 	<tr>
 	    <td align="center" width="16%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><a style="cursor: pointer;text-decoration: none;" onclick="ShowTicketData('<s:property value="%{id}"/>','T')" href="#"><b><s:property value="%{ticket_no}"/></b></a></td>
		<td align="center" width="30%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><a style="cursor: pointer;text-decoration: none;" onclick="ShowTicketData('<s:property value="%{id}"/>','FB')" href="#"><b><s:property value="%{feed_by}"/></b></a></td>
		<td align="center" width="25%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{open_date}"/></b></td>
 		<td align="center" width="15%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{open_time}"/></b></td>
 		<td align="center" width="20%"  style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><b><s:property value="%{status}"/></b></td>
 	</tr>
 	</table>
	</s:iterator>
	</marquee>
</div>
</div>


<div class="contentdiv-small" style="overflow: hidden;">
<div class="clear"></div>
<div style="height:auto; margin-bottom:10px;" ><B>News And Update</B></div>

</div>-->
</div>
</body>
</html>