<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.StyleScheme1{
background: #E28F8F url(template/theme/mytheme/images/ui-bg_glass_55_fbf9ee_1x400.png) 50% 50% repeat-x ;
padding: 0.6%;

}

</style>

<script type="text/javascript">

var pendingColor=[];
var closedColor=[];
var escColor=[];

$.subscribe('escalationColor',function(event,data)
{
	for(var i=0;i<pendingColor.length;i++){
		$("#"+pendingColor[i]).css('background','rgb(255, 216, 242)');
	}
	
	for(var i=0;i<closedColor.length;i++){
		$("#"+closedColor[i]).css('background','rgb(191, 255, 169)');
	}
	
	for(var i=0;i<escColor.length;i++){
		$("#"+escColor[i]).css('background','rgb(255, 255, 128)');
	}
	pendingColor=[];
	escColor=[];
	closedColor=[];
}); 

function viewStatus(cellvalue, options, row)
{
	  	if(row.status=='Pending')
		{
	 		pendingColor.push(row.id);
		}
		else if(row.status=='Resolved' && row.level=='Level1')
		{
			closedColor.push(row.id);
		}
		if(row.level=='Level1')
		{
		}
		else 
		{
			escColor.push(row.id);
		}	 
    	return ''+cellvalue+'';
}




</script>
</head>
<body>
<div class="counertDiv">
<table width="100%" border="1" style="border-radius:3px;float:left;margin-top:10px">
<tr >
<td class="StyleScheme1"></td>
<td class="StyleScheme1"></td>
<td class="StyleScheme1"></td>
<td class="StyleScheme1"></td>
<td align="center" width="15%" height="70%" colspan="2" style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Remarks</b></font></td>
<td class="StyleScheme1"></td>
<td class="StyleScheme1"></td>
<td class="StyleScheme1"></td>
<td class="StyleScheme1"></td>
</tr>
		 <tr style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<td  align="center" width="12%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Mode</b></font></td>
			<td  align="center" width="7%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Total</b></font></td>
			<td  align="center" width="15%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>3 & Above/Positive</b></font></td>
			<td  align="center" width="14%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>1 & 2/Negative</b></font></td>
			<td  align="center" width="11%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Positive</b></font></td>
			<td  align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Negative</b></font></td>
			<td  align="center" width="8%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>No to BLK</b></font></td>
			<td  align="center" width="9%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Ticket Opened</b></font></td>
     	    <td  align="center" width="7%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Closed</b></font></td>
     	   <td  align="center" width="7%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Pending</b></font></td>
       </tr>
	
		<s:iterator  id="first" status="status" value="%{negativeFeedList}">
		<tr>
		<td align="center" style="background: border-radius:3px;" class="StyleScheme1">
				<font color="black"><s:property value="%{Mode}"/></font>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{Mode}"/>','','')" style="border-radius:3px;" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{TotalSeek}"/></b></font></a>
			</td>
			 <td align="center" onclick="getTotalFeedData('<s:property value="%{Mode}"/>','Type','Y')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{posTicket}"/></b></font></a>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{Mode}"/>','Type','N')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{negTicket}"/></b></font></a>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{Mode}"/>','overAll','Yes')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalPosRemarks}"/></b></font></a>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{Mode}"/>','overAll','No')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalNegRemarks}"/></b></font></a>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{Mode}"/>','recommend','No')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalNoToBLK}"/></b></font></a>
			</td>
			<td align="center" onclick="getNegativeFeedData('Stage2TicketOpened','<s:property value="%{Mode}"/>','0')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{Stage2TicketOpened}"/></b></font></a>
			</td>
					<td align="center" onclick="getNegativeFeedData('Stage2ActionTaken','<s:property value="%{Mode}"/>','0')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{Stage2ActionTaken}"/></b></font></a>
			</td>
			<td align="center" onclick="getNegativeFeedData('Stage2Pending','<s:property value="%{Mode}"/>','0')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{Stage2Pending}"/></b></font></a>
			</td>
		</tr>
		</s:iterator>
	<!-- <tr class="tableData" bgcolor="#C4DCFB">
	
 </tr> -->
	</table>
	</div>
	<div class="chooseDiv">
	
		<table width="100%" border="1" style="border-radius:3px;float:left;margin-top:10px">
			<tr>
			<td></td>
			<td></td>
			<td></td>
			<td align="center" width="20%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Chosen&nbsp;BLK&nbsp;Because&nbsp;Of</b></font></td>
			<td></td>
			<td></td>
			<td></td>
			</tr>
			<tr style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
				<td  align="center" width="10%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Mode</b></font></td>
				<td  align="center" width="15%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Physician</b></font></td>
				<td  align="center" width="15%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>TPA</b></font></td>
				<td  align="center" width="15%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Friends & Relative</b></font></td>
				<td  align="center" width="14%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Corp-Tie Up</b></font></td>
				<td  align="center" width="14%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Advt./ Website</b></font></td>
				<td  align="center" width="15%" ><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><b>Others</b></font></td>
			</tr>
			<s:iterator  id="second" status="status" value="%{chooseList}">
			<tr>
		<td align="center"  style="background: border-radius:3px;" class="StyleScheme1">
				<font color="black"><s:property value="%{compType}"/></font>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{compType}"/>','choseHospital','Physician')" style="border-radius:3px;" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalPhysian}"/></b></font></a>
			</td>
			 <td align="center" onclick="getTotalFeedData('<s:property value="%{compType}"/>','choseHospital','TPA')" class="StyleScheme1">
			 	<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalTPA}"/></b></font></a>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{compType}"/>','choseHospital','Friends & Relatives')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalFriends}"/></b></font></a>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{compType}"/>','choseHospital','Corp-Tie Ups')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalCorp}"/></b></font></a>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{compType}"/>','choseHospital','Adv./Website')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalAdvt}"/></b></font></a>
			</td>
			<td align="center" onclick="getTotalFeedData('<s:property value="%{compType}"/>','choseHospital','Others')" class="StyleScheme1">
				<a style="cursor: pointer;text-decoration: none;"  href="#"><font color="#275985"><b><s:property value="%{totalOthers}"/></b></font></a>
			</td>
		</tr>
		</s:iterator>
		</table>
	</div>	
</body>
</html>