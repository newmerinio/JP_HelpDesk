<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function viewLead()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/beforeleadView.action?modifyFlag=0&deleteFlag=0&formater=1&lostLead=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</script>
</head>


<body>
<div class="clear"></div>
<div class="list-icon">
		<!-- <div class="head">Lead History</div><img alt="" src="images/forward2.png" style="width: 2%; float: left;margin: 7px;"><div class="head">View</div> -->
	<div class="head">Lead History</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	<div class="head">View</div>
	</div>
	<div class="clear"></div>
<div class="border">

<table border="0" align="center" style="width: 100%">
	<tr style="background-color: #e3e3e3;">
		<th style="width: 40px">S.N</th>
		<th>Name</th>
		<th>Converted To</th>
		<th>Comment</th>
		<th>Date</th>
		<th>By</th>
	</tr>
	
<s:iterator value="parentTakeaction" id="parentTakeaction" status="counter">  
<s:if test="#counter.count%2 != 0">
	<tr>
</s:if>
<s:else>
	<tr style="background-color: #e9e9e9;">
</s:else>

		<td style="width: 30px" align="center"><s:property value="#counter.count"/></td>
		<s:iterator value="childList" status="status">
			<s:if test="#status.index == 3" >
				<td style="width: 80px;" align="center">&nbsp;<s:property value="name"/></td> 
			</s:if>
			<s:elseif test="#status.index != 4" >
				<td>&nbsp;<s:property value="name"/></td> 
			</s:elseif>
			
		</s:iterator>
</s:iterator>
</table>

<br>
<center><sj:submit 
	                        value="Back" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        cssClass="submit"
	                        onclick="viewLead();"
	                        /></center>
</div>
</body>
</html>