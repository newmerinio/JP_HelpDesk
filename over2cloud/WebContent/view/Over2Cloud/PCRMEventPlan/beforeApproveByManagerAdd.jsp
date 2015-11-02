<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<script type="text/javascript"
	src="<s:url value="/js/pcrm/showhide.js"/>"></script>

<script type="text/javascript">
	$.subscribe('level1', function(event, data) {
		$("#addDialog").dialog('open');
		setTimeout( function() {
			closeAdd();
		}, 4000);
		reset("formone");
	});
	function closeAdd() {
		$("#addDialog").dialog('close');
		///backToView();
	}

	function backToView33() {
		$("#data_part")
				.html(
						"<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$
				.ajax( {
					type : "post",
					url : "view/Over2Cloud/patientCRMMaster/viewConfigureLocationHeader.action",
					success : function(subdeptdata) {
						$("#" + "data_part").html(subdeptdata);
					},
					error : function() {
						alert("error");
					}
				});
	}

	function reset(formId) {
		$("#" + formId).trigger("reset");
	}
</script>

<style type="text/css">
table {
	border: blue;
	border-width: 12px;
}

.tdd {
	padding: 2px;
	margin: 1px;
	border-right: 1px solid #e7e9e9;
	background-color: #E6E6E6;
	border-bottom: 1px solid #e7e9e9;
	text-align: center;
}

.phead {
	background-color: grey;
	color: white;
	font-weight: bold;
}
.MytabHead{
			background-color: rgba(1, 34, 25, 0.13);
}
</style>
</head>
<body>
<center><sj:dialog id="addDialog"
	buttons="{'Close':function() { closeAdd(); },}" showEffect="slide"
	hideEffect="explode" autoOpen="false" modal="true"
	title="Status Message" openTopics="openEffectDialog" position="center"
	buttons="{ 
    		'Close':function() { closeAdd() } 
    		}"
	closeTopics="closeEffectDialog" width="350" height="250">
	<sj:div id="level123"></sj:div>
</sj:dialog>
<div class="clear"></div>
<div class="border">
<s:form
	id="DCRform" name="DCRform"
	namespace="/view/Over2Cloud/patientCRMMaster" action="approveByManagerAdd"
	theme="css_xhtml" method="post" enctype="multipart/form-data">
	<s:hidden name="id" value="%{id}"></s:hidden>
	<s:hidden name="date"  value="%{date}"></s:hidden>
	<center>
	<div
		style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	<div id="errZone1" style="float: center; margin-left: 7px"></div>
	</div>
	</center>
	
	
<table width="100%" align="center">
	<tr bgcolor="#D8D8D8"  style="height: 25px;">
		<td><b>Event Type:</b></td>
		<td><s:property value="%{epp.type}" /></td>
		<td><b>Event Title:</b></td>
		<td><s:property value="%{epp.title}" /></td>
	</tr>
	<tr style="height: 25px;">
		<td><b>Event Address:</b></td>
		<td><s:property value="%{epp.address}" /></td>
		<td><b>From Buddy:</b></td>
		<td><s:property value="%{epp.from_buddy}" /></td>
	</tr>
	<tr bgcolor="#D8D8D8"  style="height: 25px;">
		<td ><b>Mapped Team:</b></td>
		<td ><s:property value="%{epp.team}" /></td>
		<td><b>Mapped Offering:</b></td>
		<td ><s:property value="%{epp.from_offering}" /></td>
	</tr>
</table>
	
	<span id="complSpan" class="pIds" style="display: none; ">status#Status#T#a,</span>
          <div class="newColumn">
	        <div class="leftColumn1">Status:</div> 
	       	<span class="needed"></span>
	        <div class="rightColumn1">
            <s:select 
                                      id="status"
                                      name="status" 
                                      list="#{'Approved':'Approved','Disapproved':'Disapproved'}"
                                      headerKey="-1"
                                      headerValue="Select Status"
                                      cssClass="select"
                                      cssStyle="width:80%"
                                      theme="simple"
                                      >
                          </s:select>       
                   
            </div>
         </div>
        
        <span id="complSpan" class="pIds" style="display: none; ">status_comments#Comments#T#a,</span>
          <div class="newColumn">
	        <div class="leftColumn1">Comments:</div> 
	       	<span class="needed"></span>
	        <div class="rightColumn1">
                   <s:textfield name="status_comments"  id="status_comments"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
            </div>
         </div>

	<div class="clear"></div>
	<br>
	<br>
	<!-- Buttons -->
	<center><img id="indicator2"
		src="<s:url value="/images/indicator.gif"/>" alt="Loading..."
		style="display: none" /></center>

	<div class="clear">
	<div style="padding-bottom: 10px; margin-left: -76px" align="center">
	<sj:submit targets="level123" clearForm="true" value="Save"
		effect="highlight" effectOptions="{ color : '#FFFFFF'}"
		effectDuration="5000" button="true" onBeforeTopics="validate3"
		onCompleteTopics="level1" /> &nbsp;&nbsp; <sj:submit value="Reset"
		button="true" cssStyle="margin-left: 139px;margin-top: -43px;"
		onclick="reset('formone3');" /> &nbsp;&nbsp; </div>
	</div>

</s:form></div>
</center>
</body>
</html>
