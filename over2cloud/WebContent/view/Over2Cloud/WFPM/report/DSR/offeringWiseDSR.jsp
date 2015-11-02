<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="sj"  uri="/struts-jquery-tags"%>
    <%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">
var datePicker = function(elem) {
	//alert(">>>>>>>>>>>>>>calender");
    $(elem).datepicker({dateFormat:'mm-yy'});
    $('#date_picker_dob').css("z-index", 2000);
    
};
</SCRIPT>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function deleteRow(){
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:-50,left:350,modal:true});
}
function editRow(){
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:400, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:-50,modal:true});
}
function searchRow()
{
	$("#gridedittable").jqGrid('searchGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
/* function refreshRow()
{
	$("#gridedittable").jqGrid('refGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
} */
function refreshRow(rowid, result) {
	  $("#gridedittable").trigger("reloadGrid"); 
	}
</script>
</head>
<body>
<div id="date_picker_dob" style="display:none">
  <sj:datepicker name="dob" id="doba" value="dob" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
</div>

	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Offering Wise DSR</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
	</div>
<div class="container_block"><div style=" float:left; padding:10px 1%; width:98%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="currentmonth_url" action="currentmonth"></s:url>
<s:url id="offeringDsrRecord" action="offeringDsrRecords" escapeAmp="false">
	<s:param name="userID" value="%{userID}"></s:param>
	<s:param name="mode" value="%{mode}"></s:param>
</s:url>
<s:url id="offeringDSR_URL"  action="offeringDSR">
	<s:param name="mode" value="%{mode}"></s:param>
</s:url>
	<div class="clear"></div>
		<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<%-- <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a> --%>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
				<s:property value="%{month}"/>	
				</td></tr></tbody></table>
				</td><td class="alignright printTitle"></td>   
				</tr></tbody></table></div>
			</div>
	 	</div>
	<sjg:grid
			id="gridedittable"
			dataType="json"
			href="%{offeringDsrRecord}" 
			pager="true" 
			navigator="true" 
			navigatorSearch="true"
			navigatorSearchOptions="{multipleSearch:true,sopt:['cn','eq'],reloadAfterSubmit:true,closeAfterSearch:true,closeOnEscape:true,jqModal:true,caption:'Search DSR data'}"
			navigatorView="true" 
			navigatorAdd="false"
			navigatorDelete="false"
			navigatorEdit="true"
			navigatorEditOptions="{height:280,reloadAfterSubmit:true}"
			navigatorInlineEditButtons="false"
			gridModel="offeringGridModelList"
			rowList="15,20,25,30" 
			rowNum="15" 
			editurl="%{offeringDSR_URL}"
			draggable="true" 
			loadingText="Requesting Data..." 
			shrinkToFit="false"
			width="850"
			footerrow="true"
			userDataOnFooter="true"
			editinline="false"
			multiselect="true"
			autowidth="true"
			onSelectRowTopics="rowselect"
			>

			<sjg:gridColumn
					name="id" index="id" title="ID" formatter="integer" editable="false" edittype="text" sortable="true" 
					search="false" hidden="true" />
    		<sjg:gridColumn 
					name="kpiname" index="kpiname" title="Offering" editable="false" edittype="text" sortable="true" search="false" width="120"
					 align="left"/>
    		<sjg:gridColumn 
					name="targetvalue" index="targetvalue" title="Total Target"  edittype="text" sortable="true" search="false" align="center" width="120"  />
    		<sjg:gridColumn 
    				name="totalsale" index="totalsale" title="Achievement Target" editable="false" edittype="text" sortable="true" align="center" search="false"  width="120"/>
    		<sjg:gridColumn 
    				name="remainng_target" frozen="true" index="remainng_target" title="Remaining Target" align="center" width="120"
    				editable="false" edittype="text" sortable="true" search="false" />
    	
    		<s:iterator value="listfistweekdays">
				<sjg:gridColumn name="%{fistweekdatename}" index="%{fistweekdatename}" title="%{fistweekdays}" sortable="true" align="center"
					editable="true" sortable="true" search="false" width="120"/>
			</s:iterator>
	
			<s:iterator value="listSecondweekdays">
				<sjg:gridColumn name="%{secondweekdatename}" index="%{secondweekdatename}" title="%{secondweekdays}" align="center" width="120"
					editable="true"  search="false" />
			</s:iterator>
	
			<s:iterator value="listThirdweekdays">
				<sjg:gridColumn name="%{thirdweekdatename}" index="%{thirdweekdatename}" title="%{thirdtweekdays}" align="center" width="120"
					editable="true" edittype="text" search="false" />
			</s:iterator>
	
			<s:iterator value="listFourthweekdays">
				<sjg:gridColumn name="%{fourthweekdatename}" index="%{fourthweekdatename}" title="%{fourthweekdays}" align="center" width="120"
					editable="true" edittype="text" search="false" />
			</s:iterator>
		
			<sjg:gridColumn 
			name="currentmonth"
			index="currentmonth"
			searchoptions="{sopt:['eq'], dataInit:datePicker}"
			title="Month"
			align="center"
			editable="true"
			search="true"
			hidden="false"
			width="100"
		/>
		
			<%-- <sjg:gridColumn name="currentmonth" index="currentmonth" title="Current Month" editable="false" 
			searchtype="select" searchoptions="{sopt:['eq','cn'],dataUrl: '%{currentmonth_url}'}" edittype="text" sortable="true" 
			searchtype="select" edittype="text"/> --%>
			
			
	</sjg:grid>
</div></div>
</center>
</div>
</div>
</body>
<script type="text/javascript">
	$(document).ready( function() {
		jQuery("#gridedittable").jqGrid('setGroupHeaders', {
			useColSpanStyle :false,
			groupHeaders : [ {
				startColumnName :'targetvalue',
				numberOfColumns :3,
				titleText :'<em>Target Detail</em>'
			}, {
				startColumnName :'one',
				numberOfColumns :7,
				titleText :'First - Week'
			}, {
				startColumnName :'eight',
				numberOfColumns :7,
				titleText :'<em>Second - Week</em>'
			}, {
				startColumnName :'fifteen',
				numberOfColumns :7,
				titleText :'<em>Third - Week</em>'
			}, {
				startColumnName :'twentytwo',
				numberOfColumns :10,
				titleText :'<em>Fourth - Week</em>'
			}, ]
		});
	});

</script>
</html>