<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<SCRIPT type="text/javascript">
	function addIncentive()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/incentive/beforeIncentiveAdd.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</SCRIPT>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function deleteRow(){
	$("#gridedittable1").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:-50,left:350,modal:true});
}
function editRow(){
	jQuery("#gridedittable1").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:-150,left:350,modal:true});
}
function searchRow()
{
	jQuery("#gridedittable1").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}
function refreshRow(rowid, result) {
	  $("#gridedittable1").trigger("reloadGrid"); 
	}

datePick=function(element) { 
$(element).datepicker(
        {
            dateFormat: 'dd-M-yy',
            buttonImage: "calendar.gif",
            buttonImageOnly: true
        }
        );
};

function configureOBD()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
			type  : "post",
			url : "view/Over2Cloud/wfpm/target/beforeConfigOBDTarget.action?headerName=Configure OBD Target",
			success : function(data){
			$("#"+"data_part").html(data);
			},
			error:function()
			{
				alert("ERROR");
			}
		});
}

function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/target/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function excelUpload()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/target/excelUpload.action",
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
		<div class="list-icon"><div class="clear"></div>
		<%-- <div class="head"><s:property value="%{headerName}"/></div></div> --%>
		<div class="head">OBD Target</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
		<div style=" float:left; padding:20px 1%; width:98%; height: 350px;">
		<div class="rightHeaderButton">
		<!-- <input class="btn createNewBtn" value="OBD Target" type="button" onclick="configureOBD();"> -->
		<span class="normalDropDown"> 
		
		</span>
		</div>
	
		<div class="clear"></div>
		<div class="listviewBorder">
		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
			<%-- <sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow()"> Edit </sj:a>
			<sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a> --%>
			<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Delete" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
			</td></tr></tbody></table>
			</td><td class="alignright printTitle">
				<!-- <input class="btn importNewBtn" id="downloadExceProc" value="Export Excel" type="button"  onclick="getCurrentColumn('downloadExcel','obdTarget','downloadColumnAllModDialog','downloadAllModColumnDiv')">
				<input class="btn importNewBtn" value="Import Excel" type="button"  onclick="excelUpload();"> -->
			<sj:a  button="true" 
				           cssClass="button" 
				           cssStyle="height:25px; width:32px"
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				           onclick="excelDownload();" />
				    <sj:a  button="true" 
				           cssClass="button" 
			               cssStyle="height:25px; width:32px"
			               title="Upload"
				           buttonIcon="ui-icon-arrowstop-1-n" 
				           onclick="excelUpload();" />
			       <sj:a
							button="true"
							cssClass="button" 
							cssStyle="height:25px;"
			                title="Add"
				            buttonIcon="ui-icon-plus"
							onclick="configureOBD();"
							>Add</sj:a>   
			
			</td>   
			</tr></tbody></table></div>
		</div>
		<s:url id="viewOBDTargetURL" action="viewOBDTarget"></s:url>
		<s:url id="editOBDTargetURL" action="editOBDTarget"></s:url>
		<div style="overflow: scroll; height: 480px;">
		<sjg:grid 
				  id="gridedittable1"
		          href="%{viewOBDTargetURL}"
		          gridModel="gridModel"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorEdit="true"
		          navigatorDelete="true"
		          navigatorSearch="true"
		          navigatorRefresh="true"
		          navigatorSearch="true"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          rowList="15,20,25"
		          loadingText="Requesting Data..."  
		          rowNum="10"
		          shrinkToFit="true"
		          autowidth="true"
		          rownumbers="-1"
		          viewrecords="true"
		          pager="true"
          		  pagerButtons="true"
		          onSelectRowTopics="rowselect"
		          editurl="%{editOBDTargetURL}"
		          >
		        
		        <sjg:gridColumn
		          name="empName"
		          index="empName"
		          title="Emp Name"
	           	  align="left">
		           </sjg:gridColumn>
				<s:iterator value="obdTextBox" id="level1ColmnNames" >
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					searchoptions="{sopt:['eq','cn']}"
				/>
				</s:iterator>
				<sjg:gridColumn name="total" index="total" title="Total" align="center"></sjg:gridColumn>
			</sjg:grid>
		</div>
		</div>
</body>
<sj:dialog id="downloadColumnAllModDialog"  buttons="{'Cancel':function() { },}" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="OBD Target Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
<div id="downloadAllModColumnDiv"></div>
</sj:dialog>
</html>