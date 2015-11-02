<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
function showParametersTotal(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewParamDetails("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}

function showROITotal(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='viewRoiDetails("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}

function viewParamDetails(id){
	//alert(empId+" ale  "+login +"   rt !!" +eid);
	  var name  = jQuery("#gridEventPlan").jqGrid('getCell',id,'eplan_title');
	  var dat=jQuery("#gridEventPlan").jqGrid('getCell',id,'for_month');
	  $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/patientCRMMaster/viewEventParamRoi.action",
		    data:"id="+id+"&oper=param",
		    success : function(subdeptdata) {
		    	$("#showData").dialog('open');
		  	  $("#showData").dialog({title:'Event Title: '+name+" on "+dat+" (Event Cost Parameter)",width: 800,height: 450});
	       $("#showDetails").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	
}

function viewRoiDetails(id){
	 var name  = jQuery("#gridEventPlan").jqGrid('getCell',id,'eplan_title');
	  var dat=jQuery("#gridEventPlan").jqGrid('getCell',id,'for_month');
	  $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/patientCRMMaster/viewEventParamRoi.action",
		    data:"id="+id+"&oper=roi",
		    success : function(subdeptdata) {
		    	$("#showData").dialog('open');
		  	  $("#showData").dialog({title:'Event Title: '+name+" on "+dat+" (Event RIO Parameter)",width: 800,height: 450});
	       $("#showDetails").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	
}

function showDCRForEvent(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='fillDCRForEvent("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}


function  showSubmittedDCR(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='filledDCRValues("+row.id+");'>"+cellvalue+"</a>&nbsp;";
}

function filledDCRValues(id){
	//alert(id);
	var name  = jQuery("#gridEventPlan").jqGrid('getCell',id,'eplan_title');
	  var dat=jQuery("#gridEventPlan").jqGrid('getCell',id,'for_month');
	  $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/patientCRMMaster/filledDCRValues.action",
		    data:"id="+id,
		    success : function(subdeptdata) {
		    	$("#showData").dialog('open');
		  	  $("#showData").dialog({title:'Submitted DCR for Event: '+name+" on "+dat,width: 800,height: 450});
	       $("#showDetails").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function fillDCRForEvent(id){
	var name  = jQuery("#gridEventPlan").jqGrid('getCell',id,'eplan_title');
	  var dat=jQuery("#gridEventPlan").jqGrid('getCell',id,'for_month');
	  $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/patientCRMMaster/fillDCRForEvent.action",
		    data:"id="+id,
		    success : function(subdeptdata) {
		    	$("#showData").dialog('open');
		  	  $("#showData").dialog({title:'DCR for Event: '+name+" on "+dat,width: 800,height: 450});
	       $("#showDetails").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}


function closedownload(){
	$("#showData").dialog('close');
}


</SCRIPT>
</head>
<body>

 <sj:dialog 
	id="showData"
	buttons="{'Cancel':function() { closedownload(); }}"
	showEffect="slide" 
	hideEffect="explode" 
	autoOpen="false" 
	modal="true"
	title="View History" 
	openTopics="openEffectDialog"
	closeTopics="closeEffectDialog"
	width="800"
	height="900"
	>
	<center><div id="showDetails"></div></center>
	</sj:dialog>

 <div class="clear"></div>
 <div style="overflow: scroll; height: 430px;">
<s:url id="viewEventPlanData" action="viewEventPlanData" escapeAmp="false">
	<s:param name="date" value="%{date}" ></s:param>
</s:url>
<s:url id="editEventPlanDataGrid" action="editEventPlanDataGrid" />
<sjg:grid 
		  id="gridEventPlan"
          href="%{viewEventPlanData}"
          gridModel="viewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,200,500"
          rownumbers="-1"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="50"
          autowidth="true"
          editurl="%{editEventPlanDataGrid}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
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
						editoptions="{value:'active:Active;Inactive:Inactive'}"
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
					search="%{search}"
					hidden="%{hideOrShow}"
					formatter="%{formatter}"
					/>
				</s:else>
		</s:iterator>  
</sjg:grid>
</div>

</body>


</html>