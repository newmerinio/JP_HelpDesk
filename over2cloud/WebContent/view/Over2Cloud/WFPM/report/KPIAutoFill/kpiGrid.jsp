<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

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

function deleteRow(){
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:-50,left:350,modal:true});
}
function editRow(){
	//alert(row);
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:400 , width:425,reloadAfterSubmit:true,closeAfterEdit:true,modal:true});
}
function refreshRow()
{
	$("#gridedittable").jqGrid('refGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function searchRow()
{
	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}
function refreshRow(rowid, result) {
	  $("#gridedittable").trigger("reloadGrid"); 
	}
function showBarData()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/report/DSR/showGraph.action",
	    success : function(subdeptdata) {
		$("#data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getDataAccDate(date)
{
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/WFPM/report/DSR/beforeKPIAutofill.action?currentMonthValue="+date,
		success : function(data){
			$("#data_part").html(data);
		},
		error   : function(){
			alert("error");
		}
	});
}
function gridData1(cellvalue, options, rowObject) 
{ 
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",01,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData2(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",02,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData3(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",03,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData4(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",04,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData5(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",05,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData6(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",06,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData7(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",07,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData8(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",08,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData9(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",09,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData10(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",10,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData11(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",11,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData12(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",12,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData13(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",13,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData14(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",14,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData15(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",15,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData16(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",16,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData17(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",17,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData18(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",18,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData19(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",19,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData20(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",20,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData21(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",21,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData22(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",22,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData23(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",23,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData24(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",24,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData25(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",25,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData26(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",26,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData27(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",27,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData28(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",28,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData29(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",29,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData30(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",30,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridData31(cellvalue, options, rowObject) 
{
	if(cellvalue == '0') return cellvalue;
   return "<a href='#' onClick='KpiGridData("+rowObject.id+",31,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function gridComplete(cellvalue, options, rowObject) 
{
	if(rowObject.kpiname.indexOf('Total :') > -1 || cellvalue == '0') return cellvalue;
   return "<u><a href='#' onClick='KpiGridData("+rowObject.id+",0,\""+rowObject.kpiname+"\")'>"+cellvalue+"</a></u>";
}
function KpiGridData(valuepassed, colName, cellvalue) 
{
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	var date = $("#fromDate").val();
	$.ajax
	({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/report/DSR/beforeKPIDataView.action",
	    data: "kpiId="+valuepassed+"&dateValue="+colName+"&currentMonthValue="+date,
	    success : function(data) 
	    {
 			$("#takeActionGrid").dialog({title: cellvalue});
 			$("#takeActionGrid").html(data);
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
          id="takeActionGrid"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title=" KPI Complete Details"
          modal="true"
          width="980"
          height="510"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
	
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head">KPI Report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">View</div>
	</div>
	<div class="clear"></div>
	<div class="listviewBorder" style="margin: 10px;width: 98%;" align="center">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:datepicker cssStyle="height: 16px; width: 79px;" onchange="getDataAccDate(this.value);" cssClass="button" id="fromDate" 
							name="fromDate" size="20" maxDate="0" value="%{currentMonthValue}" readonly="true"   yearRange="2013:2050" showOn="focus" 
							displayFormat="M-yy" Placeholder="Select Date" changeMonth="true" changeYear="true" />
							
					<!--<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="refreshRow()"></sj:a>
					-->
					</td></tr></tbody></table>
				</td>
				<td class="alignright printTitle">
				  <div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showBarData()"><img src="images/WFPM/commonDashboard/barGraph.jpg" width="31px" height="24px" alt="Graph" title="Graph" /></a></div>		
				</td>   
					</tr></tbody></table>
			  </div>
			</div>
	<div style="overflow: scroll; height: 450px; padding: 1px;">
	<s:url id="kpiRecord" action="kpiRecords" escapeAmp="false">
	<s:param name="currentMonthValue"  value="%{currentMonthValue}" />
	</s:url>
	<sjg:grid
			id="gridedittable"
			dataType="json"
			href="%{kpiRecord}" 
			pager="true" 
			pagerButtons="true"
            pagerInput="true"
			navigator="true" 
			navigatorView="true" 
			navigatorAdd="false"
			navigatorDelete="false"
			navigatorEdit="false"
			navigatorSearch="false"
			editinline="false"
			gridModel="kpiGridModelList"
			rowList="15,20,25,30" 
			rowNum="15" 
			multiselect="false" 
			draggable="true" 
			loadingText="Requesting Data..." 
			shrinkToFit="false"
			footerrow="true"
			autowidth="true"
			userDataOnFooter="true"
			onSelectRowTopics="rowselect"
			
			>

			<sjg:gridColumn
					name="id" index="id" title="ID" formatter="integer" editable="true" edittype="text" sortable="true" 
					search="false" hidden="true" />
    		<sjg:gridColumn 
					name="kpiname" index="kpiname" title="KPI" frozen="true" editable="true" edittype="text" sortable="true" search="false" width="120"
					 align="center"/>
    		<sjg:gridColumn 
					name="targetvalue" index="targetvalue" frozen="true" title="Target" editable="true" edittype="text" sortable="true" search="false" align="center" width="90"/>
    		
    		<sjg:gridColumn 
					name="weightage" index="weightage" frozen="true" title="Weightage" editable="true" edittype="text" sortable="true" search="false" align="center" width="90"/>
    		
    		<sjg:gridColumn 
    				name="totalsale" index="totalsale" frozen="true" title="Achievement" formatter="gridComplete" editable="true" edittype="text" sortable="true" align="center" search="false"  width="90"/>
    	   
    	    <sjg:gridColumn 
    				name="remainng_target" frozen="true"  index="remainng_target" title="Remaining" align="center" width="90"
    				editable="true" edittype="text" sortable="true" search="false" />
    	   
    	    <sjg:gridColumn 
    				name="perAcheive"  index="perAcheive" frozen="true" title="Achi. (%)" align="center" width="90"
    				editable="true" edittype="text" sortable="true" search="false" />
    	
    		<s:iterator value="listfistweekdays">
				<sjg:gridColumn name="%{fistweekdatename}" index="%{fistweekdatename}" title="%{fistweekdays}" sortable="true" align="center"
					editable="true" search="false" width="40" formatter="%{formatterName}"/>
			</s:iterator>
	
			<s:iterator value="listSecondweekdays">
				<sjg:gridColumn name="%{secondweekdatename}" index="%{secondweekdatename}" title="%{secondweekdays}" align="center" width="40"
					editable="true"  search="false" formatter="%{formatterName}" />
			</s:iterator>
	
			<s:iterator value="listThirdweekdays">
				<sjg:gridColumn name="%{thirdweekdatename}" index="%{thirdweekdatename}" title="%{thirdtweekdays}" align="center" width="40"
					editable="true" edittype="text" search="false"  formatter="%{formatterName}"/>
			</s:iterator>
	
			<s:iterator value="listFourthweekdays">
				<sjg:gridColumn name="%{fourthweekdatename}" index="%{fourthweekdatename}" title="%{fourthweekdays}" align="center" width="40"
					editable="true" edittype="text" search="false" formatter="%{formatterName}" />
			</s:iterator>
		
		<sjg:gridColumn 
			name="currentmonth"
			index="currentmonth"
			title="Month"
			align="center"
			editable="true"
			search="true"
			hidden="false"
			width="70"
		/>
	</sjg:grid>
	</div>
</div>
</body>
<script type="text/javascript">
	$(document).ready( function() {
		jQuery("#gridedittable").jqGrid('setGroupHeaders', {
			useColSpanStyle :false,
			groupHeaders : [
			                {
				startColumnName :'targetvalue',
				numberOfColumns :4,
				titleText :'Target Details '
			},{
				startColumnName :'one',
				numberOfColumns :7,
				titleText :'First - Week'
			}, {
				startColumnName :'eight',
				numberOfColumns :7,
				titleText :'Second - Week'
			}, {
				startColumnName :'fifteen',
				numberOfColumns :7,
				titleText :'Third - Week'
			}, {
				startColumnName :'twentytwo',
				numberOfColumns :10,
				titleText :'Fourth - Week'
			}, ]
		});
	});

</script>
</html>