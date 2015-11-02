<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
<SCRIPT type="text/javascript">
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


function searchshowdata()
{
	
	var actionStatus;
	actionStatus=$("#actionStatus").val();
	alert("ActionStatus ::::  "+actionStatus);
	if(actionStatus == 'All')
	{	
		$.ajax({
	 		type :"post",
	 		url :"/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?&actionStatus="+actionStatus,
	 		success : function(subdeptdata) 
		    {
				$("#data_part").html(subdeptdata);
			},
		    error: function()
		    {
		        alert("error");
		    }
	 	});
	}
	else if(actionStatus == 'Approve')
	{	
		
		$.ajax({
	 		type :"post",
	 		url :"/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?&actionStatus="+actionStatus,
	 		success : function(subdeptdata) 
		    {
				$("#data_part").html(subdeptdata);
			},
		    error: function()
		    {
		        alert("error");
		    }
	 	});
	}
	
	else if(actionStatus=='Pending')
	{	
		
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?actionStatus="+actionStatus,
		    success : function(subdeptdata) {
		    	$("#data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	
	else if(actionStatus=='Disapprove')
	{	
		
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?actionStatus="+actionStatus,
		    success : function(subdeptdata) {
		    	$("#data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

function searchModedata()
{
	mode=$("#mode").val();
	alert(mode);
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?mode="+mode,
	    success : function(subdeptdata) {
	    	$("#data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function onChangeDate()
{
	var fdate=$("#fdate").val();
	var tdate=$("#tdate").val();
	
	//alert("fdate" +fdate+ "tdate" +tdate+ "modifyFlag"+modifyFlag);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeleaveRequestView.action?fdate="+fdate+"&tdate="+tdate,
	    success : function(feeddraft) {
       $("#data_part").html(feeddraft);
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
	 <div class="head">
	 Leave </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Leaves Availed: <s:property value="%{totalLeaveAvailed}"/> Leave Balance:  <s:property value="%{empLeaveBalance}"/>  </div>
</div>
<!--<div class="rightHeaderButton">
<%if(userRights.contains("lreq_ADD")){ %>
<sj:a  button="true" onclick="createRequest();" buttonIcon="ui-icon-plus">Add Request</sj:a>
<%} %>
</div>

-->
	 <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	<!--<sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a>	
	-->
	<s:if test="%{modifyFlag==1}">
	<sj:a id="backButton"  button="true"   cssClass="button" onclick="backPage();">Back</sj:a>
	</s:if>
From:	<sj:datepicker name="fdate" id="fdate" changeMonth="true" readonly="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="height: 16px; width: 70px;" cssClass="button" placeholder="Select From Time"/>
To:	<sj:datepicker name="tdate" id="tdate" onchange="onChangeDate()" changeMonth="true" readonly="true" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="height: 16px; width: 70px;" cssClass="button" placeholder="Select To Time"/>
		     		
<s:select id="actionStatus" name="actionStatus" list="#{'All':'All Status','Approve':'Approve','Pending':'Pending','Disapprove':'Disapprove'}"  
			theme="simple"	cssClass="button" onchange="searchshowdata(this.value);" cssStyle="margin-top: 0px;margin-left: 2px;height: 26px;"/>
			<s:select id="mode" name="mode" list="#{'All':'All mode','Online':'Online','sms':'SMS'}"  
			theme="simple"	cssClass="button" onchange="searchModedata(this.value);" cssStyle="margin-top: 0px;margin-left: 8px;height: 26px;"/>
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	
<sj:a id="addButton"  button="true"   cssClass="button" onclick="createRequest();">Leave Request</sj:a>


	
	</td>   
	</tr></tbody></table></div></div>
    </div>
    <s:hidden id="modifyFlag" value="%{modifyFlag}"/>
<s:url id="requestGridDataView" action="requestGridData" escapeAmp="false">
<s:param name="moduleFlag" value="%{moduleFlag}" />
<s:param name="id" value="%{id}" />
<s:param name="empname" value="%{empname}" />
<s:param name="fdate" value="%{fdate}" />
<s:param name="tdate" value="%{tdate}" />
<s:param name="actionStatus"  value="%{actionStatus}" />
 <s:param name="mode"  value="%{mode}" />          
</s:url>
<div id="data_part">
<div style="overflow: scroll; height: 430px;">
<sjg:grid 
		  id="gridedittable"
          href="%{requestGridDataView}"
          loadonce="true"
          gridModel="viewRequest"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          groupField="['status']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0}: {1}</b>']"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="15"
          autowidth="true"
          shrinkToFit="false"
         
          >
          
		<s:iterator value="requestGridColomns" id="requestGridColomns" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		formatter="%{formatter}"
		frozen="%{frozenValue}"
		/>
		</s:iterator>  
</sjg:grid>
</div></div>

</body>
</html>