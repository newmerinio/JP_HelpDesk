<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	var rows= $("#gridedittable").jqGrid('getGridParam','selarrrow'); 
	if(rows==0)
	{
		alert("Please select atleast one row.");
	}
	if(rows.length>1)
	{
		alert("Please select only one row.");
	}	
	else
	{	
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:150, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
	}
}
function deleteRow()
{
	var ids = $("#gridedittable").jqGrid('getGridParam','selarrrow'); 
	if(ids==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{	
		$("#gridedittable").jqGrid('delGridRow',ids, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}	
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

function expenseFormat(cellvalue, options, rowObject) 
{
     var context_path = '<%=request.getContextPath()%>';
     return "<img title='CheckList' src='"+ context_path +"/images/OTM/check.jpg' height='20' width='20' onClick='javascript:expenseView("+rowObject.id+");' /></a>";

}
function expenseView(val)
{
	//alert("val ::: "+val);
	$('#abcd').dialog('open');
	$('#abcd').dialog({title: 'View Expense',height: '350',width:'1000'});
    $("#activity1").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeViewExpenseEdit.action?activityId="+val,
	    success : function(subdeptdata) 
	   {
			$("#"+"activity1").html(subdeptdata);
	   },
	   error: function() 
	   {
           alert("error");
      }
	 });
}
function disapproveDSR()
{
	var id= $("#gridedittable").jqGrid('getGridParam','selarrrow'); 
	var date =  jQuery("#gridedittable").jqGrid('getCell',id,'for_month');
	var id1 =  jQuery("#gridedittable").jqGrid('getCell',id,'activity_id');
	var datev= date.split("-")[2]+"-"+date.split("-")[1]+"-"+date.split("-")[0];
	//alert(datev);
	$('#abcd').dialog('open');
	$('#abcd').dialog({title: 'Validate DCR',height: '450',width:'1150'});
    $("#activity1").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeDCRDisapprove.action?date="+datev+"&dsrId="+id+"&activityId="+id1,
	    success : function(subdeptdata) 
	   {
			$("#"+"activity1").html(subdeptdata);
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
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" >
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
							    <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
								<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  
				  
				   <sj:a id="drc_approve"  button="true"  buttonIcon="ui-icon-plus" cssStyle="width: 126px;" cssClass="button" onclick="disapproveDSR();">Validate DCR</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 440px;">
<s:url id="veiwTaskType" action="dsrViewGridFull" escapeAmp="false">
<s:param  name="month" value="%{month}"></s:param>
<s:param  name="emp" value="%{emp}"></s:param>
</s:url>
<sjg:grid 
		 
          id="gridedittable"
          href="%{veiwTaskType}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          editinline="false"
          rowList="100,200,500"
          rowNum="100"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="true"
          pagerInput="false"   
          multiselect="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{viewModifyTaskType}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          filter="true"
          filterOptions="{searchOnEnter: false, defaultSearch: 'cn'}"
          
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
					/>
		</s:if>
		<s:else>
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
			/>
		</s:else>
		</s:iterator> 
		<sjg:gridColumn 
				name="expense"
				index="expense"
				title="Reimbursement"
				width="70"
				align="center"
				formatter="expenseFormat"
				hidden="false"
			/> 
</sjg:grid>
</div>
<sj:dialog
          id="abcd"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Create Activity"
          modal="true"
          width="900"
          height="450"
          draggable="true"
    	  resizable="true"
          >
          <div id="activity1"></div>
</sj:dialog>
</div>
</div>
</body>

</html>