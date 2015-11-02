<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript">
function requestTakeAction(cellvalue, options, rowObject) 
{
	
	
       return "<a href='#' onClick='responseTakeAction("+rowObject.id+")'>Take Action</a>";
}
function responseTakeAction(valuepassed)
{
	
	var validate = jQuery("#gridedittable").jqGrid('getCell',valuepassed,'empname');
 	$("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/leaveManagement/addResponse.action?id="+valuepassed+"&validate="+validate.split(" ").join("%20")+"");
	$("#takeActionGrid").dialog('open');
		//return false;
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

	if(actionStatus == 'Approve')
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
	
	if(actionStatus=='Pending')
	{	
           
		document.getElementById("action").style.display="block";
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
function TakeActionsss() 
{
	 var valuePassed   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	 var validate = jQuery("#gridedittable").jqGrid('getCell',valuePassed,'empname');
	 if (validate==null || validate=="") {
			alert("Please Select a Row !!!");
		}
	$("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/leaveManagement/addResponse.action?id="+valuePassed+"&validate="+validate.split(" ").join("%20")+"");
$("#takeActionGrid").dialog('open');
	//return false;
}



function cancelButton2() {
	$("#takeActionGrid").dialog('close');
}
</SCRIPT>
</head>

<body>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Request"
          modal="true"
          closeTopics="closeEffectDialog"
          width="900"
          height="380"
          draggable="true"
    	  resizable="true"
    	 
          >
</sj:dialog>

<div class="list-icon">
	 <div class="head">
	Pending Request </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="clear"></div>
 <div style=" float:left; padding:10px 1%; width:98%;">
 
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
    
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr>
	<tr><td></td></tr><tr><td>
	 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr>
	 <td class="pL10"> 
	<!--<sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a>	
	-->
	<s:select id="actionStatus" name="actionStatus"  list="#{'All':'All Status','Approve':'Approve','Pending':'Pending'}"  
					cssClass="button" onchange="searchshowleaddata(this.value);" cssStyle="margin-top: 0px;margin-left: 2px;height: 26px;"/>	
				
	 <sj:a id="action" cssClass="button" button="true" title="Action" onClick="TakeActionsss();">Action</sj:a>
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<!--<sj:a  button="true" 
				           cssClass="button" 
				           cssStyle="height:25px; width:32px"
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				           onclick="getCurrentColumn('downloadAllData','holidayList','downloadColumnAllModDialog','downloadAllModColumnDiv');" />
	
	--></td>   
	</tr></tbody></table></div></div>
    </div>
    <div id="data_part">
<s:url id="responseGridDataView" action="responseGridDataView" escapeAmp="false">
<s:param name="moduleFlag" value="%{moduleFlag}" />
<s:param name="id" value="%{id}" />
<s:param name="actionStatus"  value="%{actionStatus}" />

</s:url>

<div style="overflow: scroll; height: 430px;">
<sjg:grid 
			  id="gridedittable"
	          href="%{responseGridDataView}"
	          loadonce="true"
	          pagerInput="true"
	          gridModel="viewResponse1"
	          navigator="true"
	          groupField="['userName']"
	          groupColumnShow="[true]"
	          groupCollapse="true"
	          groupText="['<b>{0}: {1}</b>']"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="false"
	          navigatorEdit="false"
	          navigatorSearch="true"
	          rowList="10,15,20,25"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          loadingText="Requesting Data..."  
	          rowNum="50"
	          autowidth="true"
	          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
	          shrinkToFit="false"
	          navigatorViewOptions="{width:500}"
	          onSelectRowTopics="rowselect"
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
</div>
</div>
</body>
</html>