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
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridBasicDetails3").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:70,modal:true});
}

function deleteRow()
{
	row = $("#gridBasicDetails3").jqGrid('getGridParam','selarrrow');
	$("#gridBasicDetails3").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,modal:true});
}
function searchRow()
{
	jQuery("#gridedittable1").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}
function refreshRow(rowid, result) {
	  $("#gridedittable1").trigger("reloadGrid"); 
	}

</script>
<SCRIPT type="text/javascript">
var datePicker = function(elem) {
	//alert(">>>>>>>>>>>>>>calender");
    $(elem).datepicker({dateFormat:'mm-yy'});
    $('#date_picker_dob').css("z-index", 2000);
    
};
</SCRIPT>
</head>
<body>
<div id="date_picker_dob" style="display:none">
  <sj:datepicker name="dob" id="doba" value="dob" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
</div>
	<div class="clear"></div>
	<div class="list-icon">
		<%-- <div class="head"><s:property value="%{mainHeader}"/></div> --%>
		<div class="head">IVRS</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Report</div>
	</div>
	<div style=" float:left; padding:10px 1%; width:98%;">
	<div class="clear"></div>	 	
 	<s:url id="ivrsRecord" action="ivrsRecords">
 		<s:param name="userN" value="%{userN}" />
 	</s:url>
 	<s:url id="ivrsRecordDetails" action="ivrsRecordDetailsData"></s:url>
 	
 	<div class="listviewBorder">
 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
				<%-- <sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a> --%>
				<%-- <sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Search</sj:a> --%>
			    <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
			</td></tr></tbody></table>
		</td><td class="alignright printTitle">
				<s:if test="%{isExistingClient==0 && lostFlag==0}">
					<sj:a  button="true" cssClass="button" 
				    buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="excelUpload();">Import</sj:a>
                </s:if>
			</td>   
			</tr></tbody></table></div>
		</div>
 	</div>
	<sjg:grid 
			  id="gridedittable1"
	          href="%{ivrsRecord}"
	          gridModel="ivrsData"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorEdit="false"
	          navigatorDelete="false"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          navigatorSearch="true"
	          navigatorRefresh="true"
	          navigatorView="true"
	          navigatorSearch="true"
	          rowList="15,20,25"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          loadingText="Requesting Data..."  
	          rowNum="10"
	          shrinkToFit="true"
	          autowidth="true"
	          onSelectRowTopics="rowselect" 
	          >
	    		<sjg:grid 
					  id="gridIVRSRecord"
			          href="%{ivrsRecordDetails}"
			          gridModel="ivrsDataDetails"
			          navigator="false"
			          navigatorSearchOptions="{sopt:['eq','cn']}"
			          rowList="15,20,25"
			          rownumbers="-1"
			          viewrecords="true"       
			          pager="true"
			          pagerButtons="true"
			          pagerInput="false"   
			          loadingText="Requesting Data..."  
			          rowNum="10"
			          onSelectRowTopics="rowselect"
			          >
			          
					<sjg:gridColumn name="availability" index="availability" title="Availability" align="center" hidden="true" width="50" search="false"/>
					<sjg:gridColumn name="totalCall" index="totalCall" title="Total Call" align="center" width="50" search="false"/>
					<sjg:gridColumn name="productiveCall" index="productiveCall" title="Productive Call" align="center" width="50" search="false"/>
					<sjg:gridColumn name="totalSale" index="totalSale" title="Total Sale" align="center" width="50" search="false"/>
					<sjg:gridColumn name="newOutlet" index="newOutlet" title="New Outlet" align="center" width="50" search="false"/>
					<sjg:gridColumn name="talkTime" index="talkTime" title="Talk Time" align="center" width="50" search="false"/>
					<sjg:gridColumn name="pulse" index="pulse" title="Pulse" align="center" width="50" search="false"/>
					<sjg:gridColumn name="date" index="date" title="Date" align="center" width="150" search="true"/>
					<sjg:gridColumn 
					name="date"
					index="date"
					searchoptions="{sopt:['eq','cn'], dataInit:datePicker}"
					title="Date"
					align="center"
					search="true"
					hidden="false"
					width="150"
				/>
					<sjg:gridColumn name="time" index="time" title="Time" align="center" width="150" search="false"/>
				</sjg:grid>
				      
	        <sjg:gridColumn name="id" index="id" title="ID" align="center" hidden="true" search="false"/>
	        <sjg:gridColumn name="name" index="name" title="Name" align="center"search="false"/>
			<sjg:gridColumn name="mobileNo" index="mobileNo" title="Mobile No" align="center" search="false"/>
			<sjg:gridColumn name="availability" index="availability" title="Availability" align="center" hidden="true" search="false"/>
			<sjg:gridColumn name="target" index="target" title="Total" align="center" search="false"/>
			<sjg:gridColumn name="achive" index="achive" title="Achive" align="center" search="false"/>
			<sjg:gridColumn name="achivePerc" index="achivePerc" title="Achive(%)" align="center" search="false"/>
			<sjg:gridColumn name="totalCall" index="totalCall" title="Total Call" align="center" search="false"/>
			<sjg:gridColumn name="productiveCall" index="productiveCall" title="Productive Call" align="center" search="false"/>
			<sjg:gridColumn name="totalSale" index="totalSale" title="Total Sale" align="center" search="false"/>
			<sjg:gridColumn name="newOutlet" index="newOutlet" title="New Outlet" align="center" search="false"/>
			<sjg:gridColumn name="talkTime" index="talkTime" title="Talk Time" align="center" search="false"/>
			<sjg:gridColumn name="pulse" index="pulse" title="Pulse" align="center" search="false"/>
			<sjg:gridColumn name="date" index="date" title="Date" align="center" search="true" searchoptions="{sopt:['eq','cn'], dataInit:datePicker}"/>
			<sjg:gridColumn name="time" index="time" title="Time" align="center" search="false"/>
		</sjg:grid>
		
		</div>
</body>
</html>