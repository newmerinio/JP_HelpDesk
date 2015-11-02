<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">
function showhideCapaDiv()
{
	if(document.getElementById("capaDetails").checked)
	{
		document.getElementById("capaDiv").style.display="block";
	}
	else
	{
		document.getElementById("capaDiv").style.display="none";
	}
}


function viewTicketsPage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus=Pending",
	    success : function(subdeptdata) {
$("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
   alert("error");
}
	 });
}

function sendMailSMS()
{
	var smsFormat=$("#smsDraft").val();
	var mailFormat=$("#mailDraft").val();
	alert("SMS Draft is as >>>>>"+smsFormat);
	alert("Mail Draft is as >>>"+mailFormat);

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/report/sendPatAck.action?smsFormat="+smsFormat.split(" ").join("%20")+"&mailFormat="+mailFormat.split(" ").join("%20"),
	    success : function(subdeptdata) {
$("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
   alert("error");
}
	 });
}


</SCRIPT>
</head>
<body>
<div class="list-icon">
	 <div class="head">Feedback</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head"> Communicate to <s:property value="%{#parameters.feedbackBy}"  /></div>
</div>
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
	<table width="80%" height="100%" align="center">
		<thead>
			<tr align="center">
				<td valign="top" align="center" >
					<b>SMS&nbsp;Format</b>
				</td>
			</tr>
			<tr>
				<td align="center">
					<!-- Dear&nbsp;<s:property value="%{#parameters.feedbackBy}" />,&nbsp;we&nbsp;had&nbsp;taken&nbsp;action&nbsp;<b><s:property value="%{#parameters.capa}" /></b>&nbsp;for&nbsp;feedback&nbsp;<b><s:property value="%{#parameters.feedbrief}" /></b>&nbsp;registered&nbsp;on&nbsp;<s:property value="%{#parameters.opendate}" />.&nbsp;We&nbsp;thank&nbsp;you&nbsp;to&nbsp;help&nbsp;us&nbsp;improvise&nbsp;our&nbsp;service&nbsp;quality. -->
					<%=request.getAttribute("smsFormat")%>
					<s:hidden id="smsDraft" name="smsDraft" value="%{smsFormat}"></s:hidden>
				</td>
			</tr>
		</thead>
	</table>
	<br/>
	<br/>
	<table width="90%" height="100%" align="center" border="0">
		<thead>
			<tr>
				<td align="center" colspan="2">
					<b>Mail Format</b>
				</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<b>Subject:&nbsp;</b>
				</td>
				<td>
					 <b>Thanks for your Feedback!</b>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<b>Mail&nbsp;Body:&nbsp;</b>
				</td>
				<td valign="top">
				<s:hidden id="mailDraft" name="mailDraft" value="%{mailFormat}"></s:hidden>
					 <%=request.getAttribute("mailFormat")%>
				</td>
			</tr>
		</tbody>
	</table>
	<sj:a
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 563px;"
		                     onclick="sendMailSMS();"
			            >Send</sj:a>
			            &nbsp;&nbsp;
						<sj:a 
		                     value="Back" 
		                     button="true"
		                     onclick="viewTicketsPage();"
			            >Back</sj:a>
			            <br/><br/><br/>
</div>
</div>
</body>
</html>