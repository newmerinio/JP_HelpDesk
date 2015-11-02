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
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">

function toggle_visibility(id1,id2) {
	if(document.getElementById(id1).style.display == 'block'){
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"inactive";
		sub1.style.display	=	"none";
	}else{
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"active";
		sub1.style.display	=	"block";
	}
}

function ticketNo2(cellvalue, options, row) {

	return "<a href='#' onClick='javascript:openDialog10("+row.id+ ","+row.feedTicketId+ ")'>" + cellvalue + "</a>";
}


function openDialog10(userId,feedTicketId) {
    id=userId;
    //$("#actionHistoryDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/WFPM/Lead/leadHistoryAction.action?id=" + userId+"&feedTicketId="+feedTicketId);
   // $("#actionHistoryDialog").dialog("open");
    $("#takeActionOnLead8").show();
    
    $("#content9").load("<%=request.getContextPath()%>/view/Over2Cloud/feedback/viewAction7.action?id=" + userId+"&feedTicketId="+feedTicketId);
	
}


	
	</SCRIPT>
	</head>
<body>
<sj:dialog 
           id="facilityisnotavilable" 
           buttons="{ 'OK ':function() { },'Cancel':function() { },}"  
           showEffect="slide" 
           hideEffect="explode" 
           autoOpen="false" 
           modal="true" 
           title="This facility is not available in Existing Module"
           openTopics="openEffectDialog"closeTopics="closeEffectDialog"
           modal="true" 
           width="390" 
           height="100"/>

<div class="list-icon"><div class="clear"></div><div class="head"><s:property value="%{mainHeaderName}"/></div></div>
<div style=" float:left; padding:20px 1%; width:98%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">

<!-- Level 1 to 5 Data VIEW URL CALLING Part Starts HERE -->
<!--<s:url id="viewFeedbackInGrid" action="viewCustomerInGrid" />
--><!-- Level 1 to 5 Data VIEW URL CALLING Part ENDS HERE -->
<s:url id="viewFeedbackInGrid" action="viewFeedbackModesInGrid" escapeAmp="false">
<s:param name="mode" value="%{mode}"></s:param>
</s:url>
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<!--<a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a>
<a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a>
--> 
<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="getCurrentColumn('downloadAllData','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" >Download</sj:a>	
<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="pageisNotAvibale();">Import</sj:a>
<sj:a  button="true" cssClass="button" onclick="pageisNotAvibale();" buttonIcon="ui-icon-print" >Print</sj:a>
</td> 
</tr></tbody></table></div></div></div>
<center>
<sjg:grid 
		  id="gridedittable"
          href="%{viewFeedbackInGrid}"
          gridModel="custDataList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyFeedback}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
         autowidth="true"
         loadonce="true"
          >
		<s:iterator value="CRMColumnNames" id="CRMColumnNames" >  
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
		      			   frozen="%{frozenValue}"
		      			   
		 				   />
		   </s:iterator>  		
</sjg:grid></div><br><br>

<div id="takeActionOnLead8" style="display: none; width:850px;">
<div class="wrapper">
	<div class="sub_wrapper">
		<div class="block_main repeating_block_head"><a href="javascript:void();" id="tab4" class="inactive" onclick="javascript:toggle_visibility('content9','tab4');"><h1><b>Take Action</b></h1></a></div><br>
		<div class="content_main" id="content9" style="border-top:3px solid #aaa9ab; min-width:98%; overflow-x:scroll;">
			
		</div>
	</div>
</div>
</div>



</div>
</div>
</body>
</html>