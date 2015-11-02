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
function fullKrDetails(cellvalue, options, row) 
{
	 return "<a href='#' onClick='showFullView("+row.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}
function contactFormat(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	return "<img title='"+ cellvalue +" ' src='"+ context_path +"/images/group.jpg' height='25' width='25' onClick='javascript:showContact("+row.id+");' /></a>";

}
function downloadKR(value)
{
	var accessType = jQuery("#gridedittable").jqGrid('getCell',value,'access_type');
	$('#filePath').val(value);
	$('#accessType').val(accessType);
	$("#downloadFile").submit();

}
function showContact(id)
{
	$('#takeActionDialog').dialog('open');
	var krName= jQuery("#gridedittable").jqGrid('getCell',id,'kr_name');
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
function actionFormat(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	 var retValue ="<img title='Full View' src='"+ context_path +"/images/WFPM/fullView.jpg'  onClick='javascript:showFullView("+row.id+");' /></a>";
	 retValue +=  "<img title='Share' src='"+ context_path +"/images/share.jpg' height='15' width='40' onClick='javascript:shareKr("+row.id+");' /></a>";
	 return retValue;
}
function showFullView(valuepassed)
{
	$('#takeActionDialog').dialog('open');
	var krName= jQuery("#gridedittable").jqGrid('getCell',valuepassed,'kr_name');
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
function shareKr(id)
{
	var value= jQuery("#gridedittable").jqGrid('getCell',id,'document');
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/shareFromLibrary.action?docName="+value+"&flag=show",
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


var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:150,modal:true});
}
function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:100,left:150,modal:true});
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
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
	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?shareStatus="+kr,
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
function shareView()
{
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?shareStatus=byMe",
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


shareView();
function getSearchData(searchValue)
{
	var shareStatus=$('#shareStatusValue').val();
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?shareStatus="+shareStatus+"&searchTags="+searchValue,
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
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?shareStatus="+shareStatus+"&fromDate="+fDate+"&toDate="+searchValue,
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
	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?shareStatus="+shareStatus+"&searchField="+searchField+"&searchValue="+searchValue,
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
          width="950"
          height="390"
          draggable="true"
    	  resizable="true"
          >
          <div id="resultDiv"></div>
</sj:dialog>
 <div class="clear"></div>
 <div class="list-icon">
	 <div class="head">KR Share By Me</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
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
					<%-- 	  <%if(userRights.contains("share_MODIFY")){ %>
					   <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>	
					 <%} %>
					   <%if(userRights.contains("share_DELETE")){ %>
					   <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
					  <%} %> --%>
					   <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					   <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
					   <sj:datepicker id="fromDate" name="fromDate" cssClass="button" cssStyle="height: 14px;width: 59px;margin-left: 4px;" value="%{fromDate}"  readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
					   <sj:datepicker id="toDate" name="toDate" cssClass="button" cssStyle="height: 14px;width: 59px;margin-left: 2px;" value="today" readonly="true"  size="20" changeMonth="true" onchange="getDateSearchData(this.value)" changeYear="true" maxDate="-0y"    showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
 
	
		<s:select
			id="deptName" 
			list="deptList"
			headerKey="-1"
			headerValue="Department"
			onchange="fetchGroup(this.value,'groupName');onChangeSubGroup(this.value,'dept.id');"
            theme="simple"
            cssStyle="height: 26px;width:108px;"
			cssClass="button"
            />
		<s:select
			id="groupName" 
			list="{'No Data'}"
			headerKey="-1"
			headerValue="Group"
		    onchange="getSubGrp(this.value,'subGroupName');onChangeSubGroup(this.value,'krGroup.id');"
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
				  <%if(userRights.contains("share_ADD")){ %>
						<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button"  onclick="krUpload('shareStatus');">Share</sj:a>
				 <%} %>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>	
<s:hidden id="shareStatus" value="%{shareStatus}"/>
<div style="overflow: scroll; height: 430px;">
<div id="viewKRinGrid">		
</div>
</div>
</div>
</body>
</html>