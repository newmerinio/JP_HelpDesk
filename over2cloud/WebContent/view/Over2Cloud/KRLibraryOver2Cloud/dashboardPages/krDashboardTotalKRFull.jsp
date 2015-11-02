<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<SCRIPT type="text/javascript">
function actionFormat(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	 return "<img title='Share' src='"+ context_path +"/images/share.jpg' onClick='javascript:shareLibrary("+row.id+");' height='20' width='40' /></a>";
}
function fileFormatter(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	 return "<img title='"+ cellvalue +" ' src='"+ context_path +"/images/docDownlaod.jpg' onClick='javascript:fileDownload("+row.id+");' /></a>";
}

function fileDownload(value)
{
	$('#filePath').val(value);
	$("#downloadFile").submit();
}
function shareLibrary(value)
{
	var fromDept= jQuery("#gridedittable").jqGrid('getCell',value,'deptName');
	var group= jQuery("#gridedittable").jqGrid('getCell',value,'groupName');
	var subgroup= jQuery("#gridedittable").jqGrid('getCell',value,'subGroupName');
	var tags=jQuery("#gridedittable").jqGrid('getCell',value,'tag_search');
	var accesType=jQuery("#gridedittable").jqGrid('getCell',value,'accessType');
	var krName= jQuery("#gridedittable").jqGrid('getCell',value,'kr_name');
	var krID= jQuery("#gridedittable").jqGrid('getCell',value,'kr_starting_id');
	var krBrif= jQuery("#gridedittable").jqGrid('getCell',value,'kr_brief');
	var docValue= jQuery("#gridedittable").jqGrid('getCell',value,'file');
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/shareFromLibrary.action?docName="+value+"&flag=0&dept="+fromDept+"&group="+group+"&subGroup="+subgroup+"&tags="+tags+"&accesType="+accesType+"&krName="+krName+"&krBrief="+krBrif+"&krId="+krID+"&doc="+docValue,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });	
}
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUploadView.action",
	    success : function(data) 
	    {
			$("#"+"resultDivData").html(data);
	    },
	    error: function() 
	    {
	        alert("error");
	    }
	 });
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}

</script>
</head>
<body>
<sj:dialog
          id="takeActionDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Action"
          modal="true"
          width="950"
          height="390"
          draggable="true"
    	  resizable="true"
          >
          <div id="resultDiv"></div>
</sj:dialog>
<s:form action="downloadLiabrary" id="downloadFile" theme="css_xhtml" name="forms">
<s:hidden name="filePath" id="filePath"/>
</s:form>
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody>
					       <tr><td class="pL10"> 
							 <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
						 	 <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
					      
					      </td></tr></tbody>
					  </table>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
</div>	
<s:url id="viewKrUpload" action="viewKrUploadDash" escapeAmp="false">
</s:url>
<s:url id="viewModifyKrUpload" action="viewModifyKrUpload" />
<sjg:grid 
		  id="gridedittable"
          href="%{viewKrUpload}"
          gridModel="masterViewList"
          groupField="['groupName']"
          groupColumnShow="[false]"
          groupCollapse="true"
          groupText="['<b>{0}-Total {1}</b>']"
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
          navigatorViewOptions="{height:300,width:400}"
          navigatorEditOptions="{height:300,width:400,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{viewModifyKrUpload}"
          shrinkToFit="false"
          loadonce="true"
          autowidth="true"
          pager="true"
          rownumbers="true"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="masterViewProp" id="masterViewProp" >  
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
		</s:iterator>  
		
		
		
</sjg:grid>
</div>
</body>
</html>