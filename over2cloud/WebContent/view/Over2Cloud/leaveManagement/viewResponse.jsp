<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/leave.js"/>"></script>
<script type="text/javascript">
function reloadRow()
{
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}


function searchshowleaddata()
{
	
	var actionStatus;
	actionStatus=$("#actionStatus").val();

	if(actionStatus == 'All')
	{	
		$.ajax({
	 		type :"post",
	 		url :"/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveResponseView.action?&actionStatus="+actionStatus,
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
	 		url :"/over2cloud/view/Over2Cloud/leaveManagement/beforeleaveResponseView.action?&actionStatus="+actionStatus,
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
		    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeRequestInResponseView.action?actionStatus="+actionStatus,
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
		    url : "/over2cloud/view/Over2Cloud/leaveManagement/beforeRequestInResponseView.action?actionStatus="+actionStatus,
		    success : function(subdeptdata) {
	       $("#data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
</script>


</head>
<body>
<div class="list-icon">
	 <div class="head">
	Leave Response </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div style=" float:left; padding:10px 1%; width:98%;">
    <div class="listviewBorder">
	    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody>
			<tr>
				<td class="pL10"> 
					<s:select id="actionStatus" name="actionStatus"  list="#{'All':'All Status','Approve':'Approve','Pending':'Pending'}"  
					cssClass="button" onchange="searchshowleaddata(this.value);" cssStyle="margin-top: 0px;margin-left: 2px;height: 26px;"/>	
				</td>
				
			</tr></tbody></table>
			</td>
			<td class="alignright printTitle">
			<!--<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="pendingRequest();">View Pending Request</sj:a>
			--></td>   
			</tr></tbody></table></div>
		</div>
    </div>
    <s:hidden id="actionStatus" value="%{actionStatus}" name="actionStatus"/>
<s:url id="requestGridDataView" action="requestGridDataViewLeave" escapeAmp="false">
	<s:param name="moduleFlag" value="%{moduleFlag}" />
	<s:param name="id" value="%{id}" />
	<s:param name="fromDate"  value="%{fromDate}" />
	<s:param name="toDate"  value="%{toDate}" />
	<s:param name="actionStatus"  value="%{actionStatus}" />	
</s:url>
<div id="data_part">
<div style="overflow: scroll; height: 430px;">
<sjg:grid 
		  id="gridedittable"
          href="%{requestGridDataView}"
          gridModel="viewResponse"
          loadonce="true"
           groupField="['userName']"
          groupColumnShow="[true]"
          groupCollapse="true"
          groupText="['<b>{0}: {1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="15"
          rowTotal="10"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
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
</div>


</div></div>
</body>
</html>