<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});

function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function reloadGrid()
{
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}


function serachAgain()
{
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/report/beforeFullSearch.action",
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
	 <div class="head">Searched Data</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div>
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					        <sj:a cssClass="btn createNewBtnFeedbck" button="true" onclick="serachAgain();" cssClass="button" title="Back" value="Back" label="Back">Back</sj:a> 
					         </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>

<s:url id="viewPatients" action="searchedDataGrid" escapeAmp="false">
<s:param name="startDate" value="%{startDate}"></s:param>
<s:param name="endDate" value="%{endDate}"></s:param>
<s:param name="patType" value="%{patType}"></s:param>
<s:param name="smsStat" value="%{smsStat}"></s:param>
</s:url>
<div style="overflow: scroll; height: 430px;">
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{viewPatients}"
		          gridModel="masterViewList"
		          groupField="['revertStatus']"
		          groupText="['<b>{0} - {1} Records</b>']"
                  groupColumnShow="[false]"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="false"
		          navigatorEdit="false"
		          navigatorSearch="true"
		          rowList="15,30,45,60"
		          rownumbers="-1"
		          viewrecords="true"       
		          multiselect="false"  
		          loadingText="Requesting Data..."  
		          rowNum="15"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          pager="true"
		          >
				<sjg:gridColumn 
				name="patType"
				index="patType"
				title="Patient Type"
				width="50"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="patientId"
				index="patientId"
				title="Patient Id"
				width="80"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="patientName"
				index="patientName"
				title="Patient Name"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="purpVisit"
				index="purpVisit"
				title="Purpose of Visit"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="visitDateTime"
				index="visitDateTime"
				title="Visited Date Time"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="dischargeDateTime"
				index="dischargeDateTime"
				title="Discharge Date Time"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="roomNo"
				index="roomNo"
				title="Room No"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="mobNo"
				index="mobNo"
				title="Mob No"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="emailId"
				index="emailId"
				title="Email Id"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="smsStatus"
				index="smsStatus"
				title="SMS Status"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="revertStatus"
				index="revertStatus"
				title="Reverted"
				width="80"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="revertDate"
				index="revertDate"
				title="Reverted On"
				width="80"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="revertTime"
				index="revertTime"
				title="Reverted At"
				width="80"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="satisfaction"
				index="satisfaction"
				title="Feedback"
				width="80"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="status"
				index="status"
				title="Status"
				width="150"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="feedback_to_dept"
				index="feedback_to_dept"
				title="Tickets Opened For Depts"
				width="200"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="subCategoryName"
				index="subCategoryName"
				title="Sub Category"
				width="200"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="problem"
				index="problem"
				title="Problem"
				width="200"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="level"
				index="level"
				title="Level"
				width="150"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="allotTo"
				index="allotTo"
				title="Alloted To"
				width="200"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="resolve_date"
				index="resolve_date"
				title="Res Date"
				width="200"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="rca"
				index="rca"
				title="RCA"
				width="200"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				<sjg:gridColumn 
				name="capa"
				index="capa"
				title="CAPA"
				width="200"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
		</sjg:grid>
</div>		
		</div></div>
</body>
</html>