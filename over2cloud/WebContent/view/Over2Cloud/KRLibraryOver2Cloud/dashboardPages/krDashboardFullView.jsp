<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<SCRIPT type="text/javascript">
function formatImage(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	return "<img title='"+ cellvalue +" ' src='"+ context_path +"/images/docDownlaod.jpg' onClick='javascript:downloadKR("+row.id+");' /></a>";

}
function downloadKR(value)
{

	var accessType = jQuery("#gridedittable11").jqGrid('getCell',value,'accessType');
	$('#filePath').val(value);
	$('#accessType').val(accessType);
	$("#downloadFile").submit();

}
function actionFormat(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	 var retValue =  "<img title='Action' src='"+ context_path +"/images/tasks.png' onClick='javascript:actionTaken("+row.id+");' /></a>";
	 return retValue;
}
function openDialog(id) 
{		
	 $("#leadActionDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/KRLibraryOver2Cloud/modifyPrvlg?id="+id);
	 $('#leadActionDialog').dialog('open');
}
function actionTaken(valuepassed)
{
	var actionRe = jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'actionReq');
	var fromDept= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'dept');
	var group= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'groupName');
	var subgroup= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'subGroupName');
	var todept= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'deptName');
	var emp= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'empName');
	var tags=jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'tag_search');
	var accesType=jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'accessType');
	var frequency=jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'frequency');
	var krName= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'krName');
	var shareDate= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'dueShareDate');
	var actionDate= jQuery("#gridedittable11").jqGrid('getCell',valuepassed,'dueDate');
	if (actionRe=='No') 
	{
		alert("No Action Required for this KR !!");
	} 
	else 
	{
		$('#takeActionDialog').dialog('open');
		$('#takeActionDialog').dialog({title: krName});
		$("#resultDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/takeAction.action?id="+valuepassed+"&fromDept="+fromDept+"&group="+group+"&subGroup="+subgroup+"&todept="+todept+"&empName="+emp+"&tags="+tags+"&accesType="+accesType+"&frequency="+frequency+"&shareDate="+shareDate+"&actionDate="+actionDate,
		    success : function(data) 
		    {
				$("#"+"resultDiv").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	}

}

function reloadRow1()
{
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrViewActionable.action?shareStatus=2",
 	    success : function(data) 
 	    {
 			$("#"+"viewKRinGrid").html(data);
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
<s:form action="download" id="downloadFile" theme="css_xhtml" name="forms">
<s:hidden name="filePath" id="filePath"/>
<s:hidden name="accessType" id="accessType"/>
</s:form>
 <div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
						  <td class="pL10"> 
						   		<sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow1()"></sj:a> 
						 </td>
					      </tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
						
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>	
<s:url id="viewKR_URL" action="viewKrInGridDashboard" escapeAmp="false"  >
<s:param name="status" value="%{status}"/>
<s:param name="dataFor" value="%{dataFor}"/>
</s:url>
<s:url id="modifyKR_URL" action="modifyKrInGrid" />
<sjg:grid 
		  id="gridedittable11"
          href="%{viewKR_URL}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyKR_URL}"
          navigatorViewOptions="{reloadAfterSubmit:true}"
          autowidth="true"
          shrinkToFit="true"
          onSelectRowTopics="rowselect"
          loadonce="true"
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