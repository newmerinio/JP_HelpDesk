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
var datePick = function(elem) {
    $(elem).datepicker({dateFormat: 'dd-mm-yy'});
    $('#ui-datepicker-div').css("z-index", 2000);
}
function createIssurance() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSpareIssuePage.action?issueSpare=1",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
</script>
</head>
<body>
<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="dd.mm.yy" />
</div>
<div class="list-icon">
<div class="clear"></div>
<div class="head"><s:property value="%{MainHeaderName}"/></div></div>

<div style=" float:left; width:100%;">
<div class="rightHeaderButton">
<sj:a  button="true" onclick="createIssurance();" buttonIcon="ui-icon-plus">Add Issurance Spare</sj:a>
</div>
<s:url id="modifyIssueSpare" action="modifyIssueSpare" escapeAmp="false" />
<s:url id="viewIssueSpare" action="viewIssueSpareGridData">
<s:param name="id" value="%{id}"/>
</s:url>
<div class="border">
<div style=" float:left; width:100%; height: 340px;">
	<div class="listviewBorder"><div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow()">Edit</sj:a>	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
	</td>   
	</tr></tbody></table></div></div>
</div>
<sjg:grid 
		  id="spareIssueGrid123"
          href="%{viewIssueSpare}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          rowNum="5"
          editinline="false"
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{modifyIssueSpare}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          loadonce="true"
          autowidth="true"
          onSelectRowTopics="rowselect"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" status="test">
		<s:if test="colomnName=='from_date' || colomnName=='to_date'">  
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
			formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d'}"
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
				search="%{search}"
				hidden="%{hideOrShow}"
				/>
			</s:else>
		</s:iterator>  
</sjg:grid>

                        <!--<center>
							<sj:a 
					            id="downloadExcelIssueSpare"
					            buttonIcon="ui-icon-arrowthickstop-1-s"
					            button="true"
					            onclick="getCurrentColumn('downloadExcel','totalIssueSpareDetail','downloadColumnIssueSpareDialog','downloadIssueSpareColumnDiv')" 
					        >Download
            				</sj:a>
	               			</center>
	               			-->
	               			
	               			<sj:dialog id="downloadColumnIssueSpareDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Issue Spare Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
								<div id="downloadIssueSpareColumnDiv"></div>
							</sj:dialog>


</div>
</div>
</div>
</body>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#spareIssueGrid123").jqGrid('editGridRow', row ,{height:400, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});

}

function deleteRow()
{
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
</script>

</html>