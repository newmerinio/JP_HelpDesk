<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/communication/CommuincationBlackList.js"/>"></script>
<script type="text/javascript">
function editRow()
{
     var row = $("#gridblacklistid").jqGrid('getGridParam','selarrrow');
	jQuery("#gridblacklistid").jqGrid('editGridRow', row ,{height:240, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}
	function deleteRow(){
		 var row = $("#gridblacklistid").jqGrid('getGridParam','selarrrow');
		$("#gridblacklistid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:50,modal:true});}
	
	function searchRow(){
		jQuery("#gridblacklistid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) {
		  $("#gridblacklistid").trigger("reloadGrid"); 
	}
	
function getmobilenumberdata(mobile){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getsearchmobile.action?mobileNo="+mobile,
	    success : function(subdeptdata) {
       $("#"+"ajaxresponcediv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getselecteddate(){

	var from_date= $('#from_date').val();
	var  to_date= $("#to_date").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getblackdataselecteddate.action?from_date="+from_date+"&to_date="+to_date,
	    success : function(subdeptdata) {
       $("#"+"ajaxresponcediv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}



</script>
<SCRIPT type="text/javascript">
var datePicker = function(elem) {
    $(elem).datepicker({dateFormat:'dd-mm-yy'});
    $('#date_picker_fromdate').css("z-index", 2000);
    $('#date_picker_todate').css("z-index", 2000);
    
}
</SCRIPT>

</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Exclusion</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>

</div>

<div class="clear"></div>
<div id="date_picker_fromdate" style="display:none">
 <sj:datepicker name="fromdate"  id="fromdates" value="fromdate"  cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>
 <div id="date_picker_todate" style="display:none">
 <sj:datepicker name="todate"  id="todates"  value="todate" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>

<div style="overflow:auto; height:465px; width: 96%; margin: 1%; padding: 1%;">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"   cssClass="button" button="true"      onclick="editRow();"  ></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>	
					&nbsp;Mobile No:<s:textfield name="mobileNo" id="mobileNo"  cssClass="button"    onkeyup="getmobilenumberdata(this.value);"  cssStyle="margin-top: 1px;margin-left: 2px;height: 19px; width: 10%;"   theme="simple"/>
				    From:<sj:datepicker name="from_date"  id="from_date"  showOn="focus"  displayFormat="dd-mm-yy"  value="today"   onchange ="getselecteddate();"     yearRange="2014:2020" readonly="true" cssClass="button" style="margin: 0px 6px 10px; width: 12%;"/>
					To:<sj:datepicker name="to_date"  id="to_date"  showOn="focus"  displayFormat="dd-mm-yy"   value="today"   onchange="getselecteddate();"   yearRange="2014:2020" readonly="true"   cssClass="button"   style="margin: 0px 6px 10px; width: 12%;"/>
				    
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
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
<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addBlackList();">Add</sj:a>
</td> 
</tr></tbody></table></div></div>
<s:url id="blackListGrid" action="viewBlacKListDataGrid" ></s:url>
<s:url id="viewModifyblacklistGrid" action="modifyBlackList" ></s:url>
<div id ="ajaxresponcediv">
<sjg:grid 
		  id="gridblacklistid"
          href="%{blackListGrid}"
          gridModel="viewblacklList"
          groupField="['module_name']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0}: {1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,20,25,30"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyblacklistGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          >
          
          <s:iterator value="ViewBlackListedNumGrid" id="ViewBlackListedNumGrid" >  
		<s:if test="colomnName=='from_date'">
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
	    editoptions="{dataInit:datePicker}"
		/>
		</s:if>
		
		<s:elseif test="colomnName=='to_date'" >
        <sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		editoptions="{dataInit:datePicker}"
		/>
		</s:elseif>
		<s:elseif test="colomnName=='user_name'" >
        <sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="false"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:elseif>
		<s:else>
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:else>
		
		</s:iterator> 
	          
          
		
</sjg:grid>
</div>
</div>
</div>

</body>
</html>