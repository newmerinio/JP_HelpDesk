<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<script type="text/javascript">

function fullKrDetails(cellvalue, options, row) 
{
	 return "<a href='#' onClick='krDetails("+row.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}
function downLoadLink(cellvalue, options, row) 
{
	 return "<a href='#' onClick='downloadReport("+row.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}
function shareLink(cellvalue, options, row) 
{
	 return "<a href='#' onClick='shareReport("+row.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}
function editLink(cellvalue, options, row) 
{
	 return "<a href='#' onClick='editReport("+row.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}
function actionFormat(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	 return "<img title='Share' src='"+ context_path +"/images/share.jpg' onClick='javascript:shareLibrary("+row.id+");' height='20' width='40' /></a>";
}
function fileFormatter(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	 return "<a href='#' onClick='fileDownload("+row.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}
function fileDownload(value)
{
	$('#filePath').val(value);
	$("#downloadFile").submit();
}
function shareLibrary(value)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/shareFromLibrary.action?docName="+value+"&status=Active",
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
function krDetails(valuepassed)
{
	$('#takeActionDialog').dialog('open');
	var krName= jQuery("#gridedittable").jqGrid('getCell',valuepassed,'krName');
	$('#takeActionDialog').dialog({title: krName,width:900,height:200});
	$("#resultDiv").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/fullViewKr.action?shareId="+valuepassed+"&dataFrom=upload",
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
var row=0;
$.subscribe('rowselect', function(event, data) 
{
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}
function deleteRow()
{
	var status = jQuery("#gridedittable").jqGrid('getCell',row,'active');
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
 	else if(status=='Inactive')
	{
		alert("The selected data is already Inactive.");
	} 
	else
	{
		$("#gridedittable").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUploadViewHeader.action",
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
function uploadKRViewData()
{	
	$.ajax({
    type : "post",
    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUploadView.action",
    success : function(data) 
    {
		$("#"+"resultDivData").html(data);
    },
    error: function() 
    {
        alert("error");
    }
 });
}
uploadKRViewData();
function onChangeSubGroup(searchString,searchField)
{	
	var fromDate=$("#fromDate").val();
	var tDate=$("#toDate").val();
	$.ajax({
    type : "post",
    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUploadView.action?searchString="+searchString+"&searchField="+searchField+"&fromDate="+fromDate+"&toDate="+tDate,
    success : function(data) 
    {
		$("#"+"resultDivData").html(data);
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

<sj:dialog
          id="takeActionDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Action"
          modal="true"
          width="950"
          height="390"
          draggable="true"
    	  resizable="true"
          >
          <div id="resultDiv"></div>
</sj:dialog>
<s:form action="downloadLiabrary" id="downloadFile" theme="css_xhtml" name="forms">
<s:hidden name="filePath" id="filePath"/>
</s:form>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 KR Library</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
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
					    <tbody>
					       <tr><td class="pL10"> 
					           <%if(userRights.contains("library_MODIFY")){ %>
					         <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>	
						    	<%} %>
						        <%if(userRights.contains("library_DELETE")){ %>
						     <sj:a id="delButton" title="Inactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
								<%} %>
							 <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
						 	 <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
					      	 <sj:datepicker id="fromDate" name="fromDate" cssClass="button" cssStyle="height: 14px;width: 57px;margin-left: 4px;" value="%{fromDate}"  readonly="true"  size="20" onchange="onChangeSubGroup(this.value,'fromDate')" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
					         <sj:datepicker id="toDate" name="toDate" cssClass="button" cssStyle="height: 14px;width: 57px;margin-left: 2px;" value="today" readonly="true"  size="20" changeMonth="true" onchange="onChangeSubGroup(this.value,'toDate')" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
							 <s:select
							id="deptName" 
							list="deptName"
							headerKey="-1"
							headerValue="Department"
							onchange="fetchGroup(this.value,'groupName');onChangeSubGroup(this.value,'dept.id');"
				            theme="simple"
				            cssStyle="height: 26px;width:117px;"
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
							id="accessType" 
							list="#{'Public':'Public','Private':'Private'}"
							headerKey="-1"
							headerValue="Access Type"
							cssStyle="height: 26px;"
				            theme="simple" 
							cssClass="button"
							 onchange="onChangeSubGroup(this.value,'krUpload.access_type');"
							/>
							<s:select 
							id="createdBy" 
							list="createdBy"
							headerKey="-1"
							headerValue="Created By"
							cssStyle="height: 26px;"
				            theme="simple" 
							cssClass="button"
							 onchange="onChangeSubGroup(this.value,'krUpload.user_name');"
							/>
							 <s:textfield id="searching" name="searching" cssClass="button" cssStyle="margin-top: -28px;height: 14px;width:80px;" Placeholder="Enter Tag" onchange="onChangeSubGroup(this.value,'wildSearch')"/>
					      </td></tr></tbody>
					  </table>
				  </td>
				     <%if(userRights.contains("library_ADD")){ %>
						  <td class="alignright printTitle">
								<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewDataUpload();">Add</sj:a>
						  </td>
				     <%} %>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="resultDivData"></div>
</div>
</div>
</div>
</body>
</html>