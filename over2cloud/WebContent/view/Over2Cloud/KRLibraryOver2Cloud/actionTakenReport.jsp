<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript">
function formater(cellvalue, options, row)
{ 
return "<a href='#' onClick='javascript:openDialog("+row.id+")'>" + cellvalue + "</a>";

}
function takeAction(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:takeActionDialog("+row.id+")'>" + cellvalue + "</a>";
}
function takeActionDialog(id)
{
	
	$("#takeActionDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/KRLibraryOver2Cloud/takeAction?id="+id);
	$('#takeActionDialog').dialog('open');
}
function openDialog(id) 
{		
	 $("#leadActionDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/KRLibraryOver2Cloud/modifyPrvlg?id="+id);
	 $('#leadActionDialog').dialog('open');

}

function krUpload()
{$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRLibraryAdd.action",
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
function onchangeKRView(val)
{
var kr=val;

if(kr==2){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?KRSharedbyWithMe=0&modifyFlag=0&deleteFlag=0&formater=1",
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
else if(kr==1)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?KRSharedbyWithMe=1&modifyFlag=0&deleteFlag=0&formater=1",
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

else if(kr==3)
 {
 	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?KRSharedbyWithMe=3&modifyFlag=0&deleteFlag=0&formater=1",
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

}


</script>
<SCRIPT type="text/javascript">

function getCurrentColumn()
{
	 $('#notAvailable').dialog('open');
}

</SCRIPT>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:545, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:0,modal:true});
}

function deleteRow()
{
	$("#gridedittable").jqGrid('delGridRow',row, {height:200,reloadAfterSubmit:true});
}

function updateRow()
{
	$.ajax({
 	    type : "post",
 	    url : "view/Over2Cloud/KRLibraryOver2Cloud/beforekrModify.action?id="+row,
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
</script>
</head>
<body>
  <sj:dialog 
    	id="leadActionDialog" 
    	autoOpen="false" 
    	modal="true" 
    	title="Shared Details"
    	
    >
    </sj:dialog>
    
    <sj:dialog 
    	id="notAvailable" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	title="This Functionality Is Not Available"
    	width="300"
    	height="50"
    	
    >
    </sj:dialog>
    
    <sj:dialog 
    	id="takeActionDialog" 
    	autoOpen="false" 
    	modal="true" 
    	title="Take Action"
    	width="500"
    	height="500"
    >
    </sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 KR</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Report</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
								<sj:a id="editButton" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onclick="editRow()"></sj:a>
								<sj:a id="delButton"  title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					      </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
						<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNewTaskName();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div id="viewKRinGrid">		
<s:url id="viewKR_URL" action="viewKRReportInGrid" ></s:url>
<s:url id="modifyKR_URL" action="modifyReportInGrid" />
<center>
<sjg:grid 
		  id="gridedittable"
          href="%{viewKR_URL}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="false"
          rowList="20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="5"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyKR_URL}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          autowidth="true"
          shrinkToFit="true"
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
</center></div>
<div id="divFullHistory" style="float:left; width:900px;">
</div>
</div>
</div>
</body>
</html>