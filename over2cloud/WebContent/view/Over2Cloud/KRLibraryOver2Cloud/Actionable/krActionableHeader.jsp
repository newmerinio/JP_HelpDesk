<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<SCRIPT type="text/javascript">
function formatImage(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	return "<img title='"+ cellvalue +" ' src='"+ context_path +"/images/docDownlaod.jpg' onClick='javascript:downloadKR("+row.id+");' /></a>";

}
function downloadKR(value)
{

	var accessType = jQuery("#gridedittable11").jqGrid('getCell',value,'access_type');
	$('#filePath').val(value);
	$('#accessType').val(accessType);
	$("#downloadFile").submit();

}
function actionFormat(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	 var retValue =  "<img title='Action' src='"+ context_path +"/images/tasks.png' onClick='javascript:actionTaken("+row.id+");' /></a>";
	 return retValue;
}
function openDialog(id) 
{		
	 $("#leadActionDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/KRLibraryOver2Cloud/modifyPrvlg?id="+id);
	 $('#leadActionDialog').dialog('open');
}
function contactFormat(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	return "<img title='"+ cellvalue +" ' src='"+ context_path +"/images/group.jpg' height='25' width='25' onClick='javascript:showContact("+row.id+");' /></a>";

}
function fullKrDetails(cellvalue, options, row) 
{
	 return "<a href='#' onClick='showFullView("+row.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}
function showFullView(valuepassed)
{
	$('#takeActionDialog').dialog('open');
	var krName= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'kr_name');
	$('#takeActionDialog').dialog({title: krName,width:900,height:200});
	$("#resultDiv").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/fullViewKr.action?shareId="+valuepassed+"&dataFrom=share",
	    success : function(data) 
	    {
			$("#"+"resultDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function showContact(id)
{
	$('#takeActionDialog').dialog('open');
	var krName= jQuery("#gridedittable11").jqGrid('getCell',id,'kr_name');
	$('#takeActionDialog').dialog({title: krName,width:900,height:400});
	$("#resultDiv").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/showContactDetail.action?shareId="+id,
	    success : function(data) 
	    {
			$("#"+"resultDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function actionTaken(valuepassed)
{
	var actionRe = jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'action_req');
	var fromDept= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'sub_type_id');
	var group= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'group_name');
	var subgroup= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'sub_group_name');
	var todept= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'empSubType');
	var emp= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'emp_name');
	var tags=jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'tag_search');
	var accesType=jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'access_type');
	var frequency=jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'frequency');
	var krName= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'kr_name');
	var shareDate= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'due_share_date');
	var actionDate= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'due_date');
	if (actionRe=='No') 
	{
		alert("No Action Required for this KR !!");
	} 
	else 
	{
		$('#takeActionDialog').dialog('open');
		$('#takeActionDialog').dialog({title: krName});
		$("#resultDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/takeAction.action?id="+valuepassed+"&fromDept="+fromDept+"&group="+group+"&subGroup="+subgroup+"&todept="+todept+"&empName="+emp+"&tags="+tags+"&accesType="+accesType+"&frequency="+frequency+"&shareDate="+shareDate+"&actionDate="+actionDate,
		    success : function(data) 
		    {
				$("#"+"resultDiv").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	}

}
function krUpload(status)
{
	var shareStatus=$("#"+status).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRLibraryAdd.action?shareStatus="+shareStatus,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function onchangeKRView(kr)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewActionable.action?shareStatus="+kr,
	    success : function(data) 
	    {
			$("#"+"viewKRinGrid").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function actionView()
{
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewActionable.action?shareStatus=2",
 	    success : function(data) 
 	    {
 			$("#"+"viewKRinGrid").html(data);
 	    },
 	    error: function() 
 	    {
             alert("error");
        }
 	 });
}

actionView();

function getSearchData(searchValue)
{
	var shareStatus=$('#shareStatusValue').val();
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewActionable.action?shareStatus="+shareStatus+"&searchTags="+searchValue,
 	    success : function(data) 
 	    {
 			$("#"+"viewKRinGrid").html(data);
 	    },
 	    error: function() 
 	    {
             alert("error");
        }
 	 });
}
function getDateSearchData(searchValue)
{
	var shareStatus=$('#shareStatusValue').val();
	var fDate=$('#fromDate').val();
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewActionable.action?shareStatus="+shareStatus+"&fromDate="+fDate+"&toDate="+searchValue,
 	    success : function(data) 
 	    {
 			$("#"+"viewKRinGrid").html(data);
 	    },
 	    error: function() 
 	    {
             alert("error");
        }
 	 });
	
}
function onChangeSubGroup(searchValue,searchField)
{
	var shareStatus=$('#shareStatusValue').val();

$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewActionable.action?shareStatus="+shareStatus+"&searchField="+searchField+"&searchValue="+searchValue,
	    success : function(data) 
	    {
			$("#"+"viewKRinGrid").html(data);
	    },
	    error: function() 
	    {
         alert("error");
    }
	 });
}
function reloadRow1()
{
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewActionable.action?shareStatus=2",
 	    success : function(data) 
 	    {
 			$("#"+"viewKRinGrid").html(data);
 	    },
 	    error: function() 
 	    {
             alert("error");
        }
 	 });
}
</script>
</head>
<body>
<s:form action="download" id="downloadFile" theme="css_xhtml" name="forms">
<s:hidden name="filePath" id="filePath"/>
<s:hidden name="accessType" id="accessType"/>
</s:form>
<sj:dialog
          id="takeActionDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Action"
          modal="true"
          width="1000"
          height="370"
          draggable="true"
    	  resizable="true"
          >
          <div id="resultDiv"></div>
</sj:dialog>
 <div class="clear"></div>
 <div class="list-icon">
	 <div class="head">KR Share With Me </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
   </div>  
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					    
					  <td class="pL10"> 
					   <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow1()"></sj:a> 
					  <sj:datepicker id="fromDate" name="fromDate" cssClass="button" cssStyle="height: 14px;width: 82px;margin-left: 4px;" value="%{fromDate}"  readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
					  <sj:datepicker id="toDate" name="toDate" cssClass="button" cssStyle="height: 14px;width: 82px;margin-left: 2px;" value="today" readonly="true"  size="20" changeMonth="true" onchange="getDateSearchData(this.value)" changeYear="true" maxDate="-0y" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
 
		<%-- <s:select 
			id="shareStatusValue" 
			list="#{'2':'Both','1':'With Me','0':'By Me'}"
		    onchange="onchangeKRView(this.value);"
			theme="simple"
			cssStyle="height: 26px;width:8%"
			cssClass="button"
			/> --%>
		<s:select
			id="deptName" 
			list="deptList"
			headerKey="-1"
			headerValue="Department"
			onchange="fetchGroup(this.value,'groupName');onChangeSubGroup(this.value,'dept.id');"
            theme="simple"
            cssStyle="height: 26px;width:121px;"
			cssClass="button"
            />
		<s:select
			id="groupName" 
			list="{'No Data'}"
			headerKey="-1"
			headerValue="Group"
		    onchange="getSubGrp(this.value,'subGroupName');onChangeSubGroup(this.value,'grp.id');"
            cssStyle="height: 26px;"
            theme="simple" 
			cssClass="button"
            />
         <s:select 
			id="subGroupName" 
			list="{'No Data'}"
			headerKey="-1"
			headerValue="Sub Group"
		    onchange="onChangeSubGroup(this.value,'subGroup.id');"
			cssStyle="height: 26px;"
            theme="simple" 
			cssClass="button"
			/>
		 <s:select
			id="actionalble" 
		    list="#{'Pending':'Pending','Done':'Done'}"
		   	headerKey="-1"
		   	headerValue="Action"
		  	onchange="onChangeSubGroup(this.value,'share.action_status');"
			cssStyle="height: 26px;"
			theme="simple"
			cssClass="button"
			/>
			<s:select
			id="actionalble2" 
		    list="#{'Yes':'Yes','No':'No'}"
		   	headerKey="-1"
		   	headerValue="Actionable"
		  	onchange="onChangeSubGroup(this.value,'share.action_req');"
			cssStyle="height: 26px;"
			theme="simple"
			cssClass="button"
			/>
		    <s:textfield id="searching" name="searching" cssClass="button" cssStyle="margin-top: -28px;height: 14px;width:80px;" Placeholder="Enter Search" onchange="getSearchData(this.value)"/>
	
				</td>
					      </tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
						
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>	
<div id="viewKRinGrid">		
</div>
</div>
</body>
</html>