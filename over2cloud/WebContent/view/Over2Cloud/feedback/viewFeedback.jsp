<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">

function takeAction(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:showActionPage("+row.id+ ","+row.feedTicketId+ ")'>" + cellvalue + "</a>";
}

function showActionPage(rowId,feedTicketId)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/beforeActionOnPaperFeedback.action?id="+rowId+"&feedTicketId="+feedTicketId,
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function feedbackDetails(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:showFeedDetails("+row.id+ ")'>" + cellvalue + "</a>";
}

function showFeedDetails(feedId)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/getAllFeedDetails.action?id="+feedId,
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}
</SCRIPT>
<script type="text/javascript">
/* * Format a Column as Image */
	function formatImage(cellvalue, options, row) {
	 var context_path = '<%=request.getContextPath()%>';
	 cellvalue="Search";
	return "<img title='Rating View' src='"+ context_path +"/images/paperimage.png' onClick='viewRating("+row.id+");' />" ;}

function formatLink(cellvalue, options, rowObject) {
	  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }
</script>
<script type="text/javascript">
function viewRating(rowid){
	var clientId = jQuery("#gridedittable").jqGrid('getCell',rowid, 'clientId');
	var clientName = jQuery("#gridedittable").jqGrid('getCell',rowid, 'clientName');
	var compType = jQuery("#gridedittable").jqGrid('getCell',rowid, 'compType');
	var centerCode = jQuery("#gridedittable").jqGrid('getCell',rowid, 'centerCode');
	var mobNo = jQuery("#gridedittable").jqGrid('getCell',rowid, 'mobNo');
	var centreName = jQuery("#gridedittable").jqGrid('getCell',rowid, 'centreName');
	var serviceBy = jQuery("#gridedittable").jqGrid('getCell',rowid, 'serviceBy');
	//alert("alert>>>"+clientId+"rowid>>>>"+rowid);
	//alert(compType);
	if(compType=="IPD")
	{
	//	alert("For IPD");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/getPaperRating.action?id="+rowid+"&clientId="+clientId+"&clientName="+clientName+"&compType="+compType+"&centerCode="+centerCode+"&mobNo="+mobNo+"&centreName="+centreName+"&serviceBy="+serviceBy,
		    success : function(subdeptdata) {
		    	$("#"+"viewratingId").html(subdeptdata);
		    	$("#"+"viewratingId").dialog('open');
		        
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(compType=="OPD")
	{
	//	alert("For OPD");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/getPaperRating.action?id="+rowid+"&clientId="+clientId+"&clientName="+clientName+"&compType="+compType+"&centerCode="+centerCode+"&mobNo="+mobNo+"&centreName="+centreName+"&serviceBy="+serviceBy,
		    success : function(subdeptdata) {
		    	$("#"+"viewratingId").html(subdeptdata);
		    	$("#"+"viewratingId").dialog('open');
		        
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
</script>


</head>
<body>
<s:url id="viewFeedbackInGrid1" action="viewFeedbackInGridPaper" escapeAmp="false">
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="patType" value="%{patType}"></s:param>
<s:param name="docName" value="%{docName}"></s:param>
<s:param name="spec" value="%{spec}"></s:param>
<s:param name="feedBack" value="%{feedBack}"></s:param>
</s:url>
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{viewFeedbackInGrid1}"
		          gridModel="masterViewList"
		          groupField="['targetOn']"
		          groupText="['<b> {0}: {1}</b>']"
		          groupCollapse="true"
		          groupColumnShow="false"
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
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          editurl="%{viewModifyFeedback}"
		          navigatorEditOptions="{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true}"
				  navigatorSearchOptions="{sopt:['cn','eq'],height:230, width:425,top:0,left:350,modal:true}"
				  navigatorDeleteOptions="{height:230, width:425,top:0,left:350,modal:true}"
				  navigatorViewOptions="{height:230, width:425,top:0,left:350,modal:true}"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          pager="true"
		          rownumbers="true"
		          >
				<s:iterator value="masterViewProp" id="masterViewProp" >  
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
				</s:iterator>  
				
				 <sjg:gridColumn 
		            name="imagename"
		            index="imagename"
		     		title="View Rating"
		     		editable="true"
		     		sortable="true"
		    		align="center"
		    		width="70"
		    		formatter="formatImage"
    		/> 
		</sjg:grid>
</body>
</html>