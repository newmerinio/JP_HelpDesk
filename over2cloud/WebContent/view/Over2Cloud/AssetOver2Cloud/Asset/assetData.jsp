<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:if test="dataFor=='1stDataBlock'">
<s:if test="type=='show'">
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stDataBlock')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('1stDataBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div id="barGraph" style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsBar('1stBlock')"><img src="images/barChart.png" width="15" height="15" alt="Get Data" title="Get Bar Chart" /></a></div>
</s:if>
<div style="height:auto; margin-bottom:10px;" ><B>Asset Score Sheet</B></div>
<div class="clear"></div>
<table  align="center" border="1" width="100%" >
<tbody>
 <tr bgcolor="#CC0099" bordercolor="black" >
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Type</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Total</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Alloted</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Free</b></font></td>
 </tr>
 <s:iterator id="assetList1"  status="status" value="%{assetForDashboard.assetList}" >

<tr style="background-color:#F0B2D1;">
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;"  href="#"><s:property value="#assetList1.assetName" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Total','<s:property value="#assetList1.assetType"/>');" href="#"><s:property value="#assetList1.totalAsset" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Allot','<s:property value="#assetList1.assetType"/>');" href="#"><s:property value="#assetList1.totalAllot" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Free','<s:property value="#assetList1.assetType"/>');" href="#"><s:property value="#assetList1.totalFree" /></a></font></td>
</tr>
</s:iterator>
</tbody>
</table>
</s:if>
<s:if test="dataFor=='Ticket'">
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

</s:if>
</body>
</html>