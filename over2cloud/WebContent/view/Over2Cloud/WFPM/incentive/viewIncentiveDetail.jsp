<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
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
	$("#krakpigrid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:-50,left:350,modal:true});
}
function editRow(){
	jQuery("#krakpigrid").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:-150,left:350,modal:true});
}
function searchRow()
{
	jQuery("#krakpigrid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
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
function refreshRow(rowid, result) {
	  $("#krakpigrid").trigger("reloadGrid"); 
	}
</script>
</head>
<body>

	<div class="clear"></div>
		<s:url id="incentiveRecords" action="viewIncentiveRecords?targetOn=%{targetOn}" />
		<s:url id="viewModifyIncentive" action="modifyIncentive?tableName=%{tableName}" ></s:url>
		
		<s:if test="incentiveConfig">
		<div style=" float:left; padding:10px 1%; width:98%;">
		<%-- <div class="rightHeaderButton">
			<input class="btn createNewBtn" value="Create Incentive" type="button" onClick="addIncentive();">
			<span class="normalDropDown"> </span>
		</div> --%>
		<div class="leftHeaderButton" style="display: none;">
	 	
	 	</div>
		<center>
		<div class="clear"></div>
		<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
				<!-- <input id="sendMailButton" class="button"  value="Edit" type="button" onclick="editRow()">
				<input name="cvDelete" id="cvDelete" class="button" value="Delete" type="button" onclick="deleteRow()"> -->		
				<!--<span class="normalDropDown"><input class="dbtn" name="moreAct" id="moreAct" value="More Actions" type="button"></span> 
				-->
				<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Delete" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
				</td></tr></tbody></table>
				<!-- <img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"><a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a><a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a></td> -->   
				 <td class="alignright printTitle">
				<sj:a
							button="true"
							cssClass="button" 
							cssStyle="height:25px;"
			                title="Add"
				            buttonIcon="ui-icon-plus"
							onclick="addIncentive();"
							>Add</sj:a>  
				</td></tr></tbody></table></div>
			</div>
	 	</div>
	 	<div style="overflow: scroll; height: 420px;">
		<sjg:grid 
				  id="krakpigrid"
		          href="%{incentiveRecords}"
		          gridModel="incentiveData"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorEdit="true"
		          navigatorDelete="true"
		          navigatorSearch="true"
		          navigatorRefresh="true"
		          navigatorAdd="false"
		          navigatorSearch="true"
		          rowList="15,20,25"
		          rownumbers="-1"
		          viewrecords="true"       
		          pager="true"
		          pagerButtons="true"
		          pagerInput="false"   
		          multiselect="true"  
		          loadingText="Requesting Data..."  
		          rowNum="10"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          editurl="%{viewModifyIncentive}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="false"
		          autowidth="true"
		          onSelectRowTopics="rowselect"
		          >
		        
				<s:iterator value="viewIncentive" id="viewIncentive" >  
				<sjg:gridColumn 
				name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="%{width}"
				align="%{align}"
				editable="%{editable}"
				formatter="%{formatter}"
				search="%{search}"
				hidden="%{hideOrShow}"
				searchoptions="{sopt:['eq','cn']}"
				/>
				</s:iterator>  
		</sjg:grid>
		</div>
		</center>
		</div>
		</s:if>
</body>
</html>