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
<script type="text/javascript" src="<s:url value="/js/dashboard/feedbackDash.js"/>"></script>
<style type="text/css">
.myDiv{

width: 100%;
height: 32px;
margin-top: 4px;
font-family: sans-serif;
font-weight: bold;
color: black;
background: rgb(244, 244, 244);

}
#tdhover table td:HOVER{

background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;


}
</style>
<SCRIPT type="text/javascript">
function takeAction(id,ticketNo,feedStatus,feedby,feedbrief,subcatg,opendate,allotTo)
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	    $.ajax({
	        type : "post",
	        url : "view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/feedActionDash.action?feedId="+id.split(" ").join("%20")+"&ticketNo="+ticketNo.split(" ").join("%20")+"&feedStatus="+feedStatus.split(" ").join("%20")+"&feedbackBy="+feedby.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&allotto="+allotTo.split(" ").join("%20"),
	        success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	    },
	       error: function() {
	            alert("error");
	        }
	     });
}

$( document ).ready(function() {
    document.getElementById('sdate').value=$("#hfromDate").val();
    document.getElementById('edate').value=$("#htoDate").val();
 
});

function getDataForAllBoard(){
	getcategrating('categratingBlock');
	getPendingBlockData('PendingBlock');
	getFeedbackType('4rdBlock');
	getCategType('block5');
	getLevelInfo('block6');
	
}
</SCRIPT>
</head>
<body>
<sj:dialog 
	 			id				=	"feedDialog" 
	 			modal			=	"true" 
	 			effect			=	"slide" 
	 			autoOpen		=	"false" 
	 			width			=	"1200" 
	 			height			=	"580"
	 			title			=	"Complete Details" 
	 			overlayColor	=	"#fff" 
	 			overlayOpacity	=	"1.0" 
	 			position="['center','center']"
	 			>
</sj:dialog>

<div class="middle-content">

<s:hidden id="hfromDate" value="%{fromDate}"></s:hidden>
<s:hidden id="htoDate" value="%{toDate}"></s:hidden>
<center><div class="myDiv"><div style="line-height: inherit;padding: 2px;">Data From 
<sj:datepicker id="sdate"  cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date" onchange="getDataForAllBoard()" value=" "/>
To
<sj:datepicker id="edate"  cssClass="button" cssStyle="width: 70px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 18px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select TO Date" onchange="getDataForAllBoard()" value=" " />


</div></div></center>
<div class="contentdiv-small" style="y-overflow: scroll; x-overflow:hidden  ;width:62%;height:271px;margin-top: 0px">
<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Pending Feedback Details</b></font></center>
</div>
<div id="PendingBlock">
	<table align="center" border="0" width="100%" height="30%">
          <tr bordercolor="black">
              <th valign="top" bgcolor="" >
                  <table align="center" border="0" width="100%" >
                   	  <tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
                   	 		<th align="center" class="headings1" width="10%"><font color="black">CR&nbsp;No</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Ticket&nbsp;No</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Patient&nbsp;Name</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Mob&nbsp;No</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Room&nbsp;No</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Date</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Status</font></th>
                   			<th align="center" class="headings1" width="10%"><font color="black">Category</font></th>
                   			<th align="center" class="headings1" width="40%"><font color="black">Feedback Brief</font></th>
                   	  </tr>
                  </table>
               </th>
          </tr>
          <tr>
              <td valign="top" height="10%">
              <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="2" scrolldelay="0" direction="up" height="200px">
  				 <table align="center" border="0" width="100%" style="border-style:dotted solid;">                        		
                         <s:iterator id="fList"  status="status" value="%{feedTicketList}" >
						 <tr bordercolor="#ffffff">
						    <td align="center" width="10%"><b><s:property value="%{cr_No}"/></b></td>
                   			<td align="center" width="10%" style="cursor: pointer;text-decoration: none;" onclick="takeAction('<s:property value="%{id}"/>','<s:property value="%{ticketNo}"/>','<s:property value="%{status}"/>','<s:property value="%{feedBy}"/>','<s:property value="%{feedBrief}"/>','<s:property value="%{categoryName}"/>','<s:property value="%{feedDate}"/>','<s:property value="%{allotTo}"/>');"><b><s:property value="%{ticketNo}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{feedBy}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{feedByMob}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{location}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{feedDate}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{status}"/></b></td>
                   			<td align="center" width="10%"><b><s:property value="%{categoryName}"/></b></td>
                   			<td align="center" width="40%"><b><s:property value="%{feedBrief}"/></b></td>
                   		</tr>
                   	   </s:iterator>
                   </table>
              </marquee>
              </td>
          </tr>
   </table>
   </div>
</div>
<div class="contentdiv-small" style="y-overflow: hidden; x-overflow:hidden  ; margin-top:0px;margin-left: 1 px;height:271px; " id="">
      <!--  <div style="float:right; width:auto; padding:0px 4px 0 0;">
		<a href="#" onclick="showPieChart('4rdBlock')">
			<img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Show Counters" title="Show Pie Chart" />
		</a>
	</div>
	 -->
     <div style="float:right; width:auto; padding:0px 4px 0 0;">
		<a href="#" onclick="getcategrating('categratingBlock')">
			<img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" />
		</a>
	 </div>
<center style="margin-left: -37px;"><font color="#000000"><b>Feedback Rating Details</b></font>	
<br>
<b>Feedback For:</b>
		<s:select id="patType"
				name="patType"
				list="{'IPD','OPD'}"
				headerKey="-1"
				headerValue="Patient Type"
				theme="simple" 
				cssStyle="width:120px;height:25px;margin-bottom:5px;"
				cssClass="button"
				onchange="getRatingDataForDept('','patType','categratingBlock');">
		</s:select> 
		</center>
<div id="categratingBlock">


<table width="100%" align="center" border="1">
<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		
		<th width="" bgcolor="" >
			<font color="black">Mode</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Category</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;1</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;2</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;3</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;4</font>
		</th>
		<th width="" bgcolor="" >
			<font color="black">Rating&nbsp;5</font>
		</th>
		
	</tr>
	<s:iterator id="first" status="status" value="%{Ratinginfo}">
	<tr>
	<s:if test="%{mode=='IPD'}">
	<th><b>IPD</b></th>
	<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{catName}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat1}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat2}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat3}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat4}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat5}"/></b></font>
		</td>
	</s:if>
	<s:else >
	<th><b>OPD</b></th>
	<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{catName}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat1}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat2}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat3}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat4}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><b><s:property value="%{rat5}"/></b></font>
		</td>
	</s:else>
	</tr>
	</s:iterator>

</table>

</div>
   
</div>
<div class="contentdiv-small" style="y-overflow: hidden; x-overflow:hidden  ; margin-top:-4px;margin-left: 1 px " id="tdhover">
       <div style="float:right; width:auto; padding:0px 4px 0 0;">
		<a href="#" onclick="showPieChart('4rdBlock')">
			<img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Show Counters" title="Show Pie Chart" />
		</a>
	</div>
	
     <div style="float:right; width:auto; padding:0px 4px 0 0;">
		<a href="#" onclick="getFeedbackType('4rdBlock');">
			<img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" />
		</a>
	 </div>
	
	<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Feedback Type</b></font></center>
	</div>
<div id="4rdBlock" style="y-overflow: hidden; x-overflow:hidden;">
<table height="40%" width="100%" align="center" border="1">
	<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		<th width="60%" bgcolor="" >
			<font color="black">Type&nbsp;Name</font>
		</th>
		<th width="20%" bgcolor="" >
			<font color="black">Total</font>
		</th>
		<th width="20%" bgcolor="" >
			<font color="black">Today</font>
		</th>
	</tr>
<s:iterator value="feedDataDashboardType">
	<tr>
		<td align="center" bgcolor="" class="sortable" width="60%" style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font color="black"><b><s:property value="%{feedType}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="20%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','TotalDept');">
			<a href="#" ><font color="black"><b><s:property value="%{feedTypeTotal}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="20%" onclick="getOnClickDataForFeedbackType('<s:property value="%{feedType}"/>','TodayDept');">
			<a href="#"><font color="black"><b><s:property value="%{feedTypeToday}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
	<tr bgcolor="C0E0DA">
	<td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalData"/></b></font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="today"/></b></font></td>
 </tr>
</table>





</div>
   
</div>
<div class="contentdiv-small" style="overflow: auto; margin-top:-4px;margin-left: 1 px " id="tdhover">
  <div style="float:right; width:auto; padding:0px 4px 0 0;">
		<a href="#" onclick="showPieChart('block5');">
			<img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Show Counters" title="Show Pie Chart" />
		</a>
	</div>
	<div style="float:right; width:auto; padding:0px 4px 0 0;">
		<a href="#" onclick="getCategType('block5');">
			<img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" />
		</a>
	</div>
	
	<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Category Status</b></font></center>
	</div>
<div style="margin-top: 0px;margin-bottom: 10px;" id='block5'>
<div style="margin-top: 0px" >
<table height="40%" width="100%" align="center" border="1">
	<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		<th width="70%" bgcolor="" >
			<font color="black">Category&nbsp;Name</font>
		</th>
		<th width="30%" bgcolor="" >
			<font color="black">Counter</font>
		</th>
	</tr>
	<s:iterator value="feedDataDashboardList">
	<tr>
		<td align="center" bgcolor="" class="sortable" width="70%" style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font color="black"><b><s:property value="%{actionName}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="30%" onclick="getOnClickDataForFeedbackType('<s:property value="%{id}"/>','Category');">
			<a href="#"><font color="black"><b><s:property value="%{actionCounter}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
	<tr bgcolor="C0E0DA">
	<td align="center" width="25%" height="40%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="25%" height="40%"><font color="black"><b><s:property value="totalPending"/></b></font></td>
    </tr>
</table>
</div>

</div>

</div>

<div class="contentdiv-small" style="overflow: hidden; margin-top: -4px " id="tdhover">
  <div style="float:right; width:auto; padding:0px 4px 0 0;">
		<a href="#" onclick="showPieChart('block6');">
			<img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Show Counters" title="Show Pie Chart" />
		</a>
	</div>
	<div style="float:right; width:auto; padding:0px 4px 0 0;">
		<a href="#" onclick="getLevelInfo('block6');">
			<img src="images/table.jpg" width="18" height="18" alt="Show Counters" title="Show Counters" />
		</a>
	</div>
	
	<div style="float:center; width:auto; padding:0px 4px 0 0;">
		<center><font color="#000000"><b>Level Status</b></font></center>
	</div>
<div style="margin-top: 0px" id='block6'>
<div style="margin-top: 0px" >
<table height="40%" width="100%" align="center" border="1">
	<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		<th width="20%" bgcolor="" >
			<font color="black">Status</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;1</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;2</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;3</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;4</font>
		</th>
		<th width="15%" bgcolor="" >
			<font color="black">Level&nbsp;5</font>
		</th>
	</tr>
	<s:iterator value="dashPojoList">
	<tr>
		<td align="center" bgcolor="" class="sortable" width="20%" style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font color="black"><b><s:property value="%{pending}"/></b></font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level1','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL1}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level2','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL2}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level3','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL3}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level4','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL4}"/></b></font></a>
		</td>
		<td align="center" bgcolor="" class="sortable" width="15%" onclick="getLevelDataOnClick('<s:property value="%{pending}"/>','Level5','<s:property value="%{deptId}"/>');">
			<a href="#"><font color="black"><b><s:property value="%{pendingL5}"/></b></font></a>
		</td>
	</tr>
	</s:iterator>
	<tr bgcolor="C0E0DA">
	<td align="center" width="20%" height="20%"><font color="black"><b>Total</b> </font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel1"/></b></font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel2"/></b></font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel3"/></b></font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel4"/></b></font></td>
	<td align="center" width="15%" height="15%"><font color="black"><b><s:property value="totalLevel5"/></b></font></td>
	
    </tr>
</table>
</div>
</div>
</div>
</div>
</body>
</html>