<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
</head>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

function backPage()
{

$.ajax({
    type : "post",
    url : "view/Over2Cloud/leaveManagement/FetchviewAttendancetHeader.action?modifyFlag=show",
    success : function(subdeptdata) {
   $("#"+"data_part").html(subdeptdata);
},
   error: function() {
        alert("error");
    }
 });
}



</script>




<body>
<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">
	
	
<br>
  <br>
  <br>
 
  
  
<s:hidden id="record" value="%{record}"/>

	<s:if test="%{holidayFullViewMap != null && holidayFullViewMap.size > 0}">
	<table width="100%" >
				<tr>
		<th width="20%" bgcolor="#D8D8D8" >
			<font color="black">From date</font>
		</th>
		<th width="20%" bgcolor="#D8D8D8" >
			<font color="black">Day</font>
		</th>
		<th width="20%" bgcolor="#D8D8D8" >
			<font color="black">Holiday</font>
		</th>
		
	</tr>
		<s:iterator value="holidayFullViewMap" status="status">
	<s:if test="#status.odd">
			
			<td align="center"  class="sortable" width="20%">
			<font color="black"><b><s:property value="%{fdate}"/></b></font>
		</td>
		<td align="center"  class="sortable" width="20%" >
			<a href="#"><font color="black"><b><s:property value="%{day}"/></b></font></a>
		</td>
				<td align="center"  class="sortable" width="20%" >
			<a href="#"><font color="black"><b><s:property value="%{holidayname}"/></b></font></a>
		</td>
				
			
		</s:if>
		
	<s:if test="#status.even">
	<tr  style="background-color: #D8D8D8">
	
	    <td align="center"  class="sortable" width="20%">
			<font color="black"><b><s:property value="%{fdate}"/></b></font>
		</td>
		<td align="center"  class="sortable" width="20%" >
			<font color="black"><b><s:property value="%{day}"/></b></font>
		</td>
				<td align="center"  class="sortable" width="20%" >
			<font color="black"><b><s:property value="%{holidayname}"/></b></font>
		</td>
				
			</tr>
		</s:if>
	</s:iterator>
		
	
	</table>	
	</s:if>
	
		
  <br>
  <br>
  <br>
	<table width="100%" >
	
	<h3>Note:<i> The above listed Holidays are subject to change by the HR Department</i>.</h3>
	
	</table>
	<br>
  <br>
  <br>
<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				 <div class="clear"></div>
				 <center> 
					<div class="type-button">
	                <sj:a cssStyle="margin-left: -1px;margin-top: 0px;" button="true" href="#" onclick="backPage();">Back</sj:a>
	               </div>
	               </center>	

	
	
	<s:else>
		<b>No Record Found</b>
	</s:else>

	
	
	
	
	
	
	
</div>
</div>
</body>
</html>