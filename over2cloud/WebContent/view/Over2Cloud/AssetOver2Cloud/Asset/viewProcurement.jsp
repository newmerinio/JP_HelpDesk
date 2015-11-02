<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
var datePick = function(elem) {
    $(elem).datepicker({dateFormat: 'dd-mm-yy'});
    $('#ui-datepicker-div').css("z-index", 2000);
}

</script>
</head>
<body>
<div class="clear"></div>
<div class="clear"></div>
<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="dd.mm.yy" />
</div>
<s:url id="modifyProcurement" action="modifyProcurement" />
<s:url id="viewProcurement" action="viewProcurementGridData">
<s:param name="id" value="%{id}" />
</s:url>
<div class="clear"></div>
<div class="listviewBorder" style="width: 1000px;">
	<div style="height: 25px; width: 1000px; overflow: auto;" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr><tr><td></td></tr><tr><td> 
	<table class="floatL" border="0" cellpadding="0" cellspacing="0" height="3px">
	<tbody><tr>
	<td class="pL10"  align="center"> 
	<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow();">Edit</sj:a>	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	 </td> 
	</tr></tbody></table></div></div></div>
<sjg:grid 
		  id="procumntGrid"
          href="%{viewProcurement}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{modifyProcurement}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true,closeAfterEdit:true}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          width="1000"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" status="test">
		<s:if test="colomnName=='expectedlife' || colomnName=='challandate' || colomnName=='receivedon' || colomnName=='installon' || colomnName=='commssioningon' || colomnName=='paidon'">  
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
			editoptions="{dataInit:datePick}"
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
</sjg:grid>
		<center>
							<!--<sj:a 
					            id="downloadExceProc"
					            buttonIcon="ui-icon-arrowthickstop-1-s"
					            button="true"
					            onclick="getCurrentColumn('downloadExcel','procurementDetail','downloadColumnProcDialog','downloadProcColumnDiv')" 
					        >Download
            				</sj:a>
	               			--></center>
	              			<sj:dialog id="downloadColumnProcDialog" modal="true" effect="slide" autoOpen="false" width="500" height="500" title="Procurement Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
								<div id="downloadProcColumnDiv"></div>
							</sj:dialog>
</body>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#procumntGrid").jqGrid('editGridRow', row ,{height:400, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}

function deleteRow()
{
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
</script>
</html>