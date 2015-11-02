<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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

	return "<a href='#' onClick='javascript:openDialog10(" + row.id
			+ ")'>" + cellvalue + "</a>";
}
function openDialog10(userId) {
    id=userId;
    //$("#actionHistoryDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/WFPM/Lead/leadHistoryAction.action?id=" + userId);
   // $("#actionHistoryDialog").dialog("open");
    $("#takeActionOnLead8").show();
    
    $("#content9").load("<%=request.getContextPath()%>/view/Over2Cloud/feedback/viewAction7.action?id=" + userId);
	
}


$.subscribe('getFormData', function(event,data)
		  {
			  var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
			  var mobileNo = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedByMob');
				var conP = "<%=request.getContextPath()%>";
				$("#printSelect").load(conP+"/view/Over2Cloud/feedback/checkFeedbackForAction.action?mobNo="+mobileNo);
				 $("#printSelect").dialog('open');
		  });
	</SCRIPT>
	</head>
	<body>
<div class="list-icon"><div class="clear"></div><div class="head"><s:property value="%{mainHeaderName}"/></div></div>
<sj:dialog id="printSelect" title="Feedback Action Details" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop">
<div id="ticketsInfo"></div>
</sj:dialog>
<div class="page_title"><h1><s:property value=""/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">

<!-- Level 1 to 5 Data VIEW URL CALLING Part Starts HERE -->
<s:url id="viewFeedbackInGrid" action="viewLevelCustomerInGridTodays" escapeAmp="false">
<s:param name="searchFlag" value="%{searchFlag}"></s:param>
</s:url>
<!-- Level 1 to 5 Data VIEW URL CALLING Part ENDS HERE -->

<s:url id="viewModifyFeedback" action="viewModifyCustomer" />


<center>
<sjg:grid 
		  id="gridedittable"
          href="%{viewFeedbackInGrid}"
          gridModel="ticketDataList"
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
          rowNum="10"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyFeedback}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          >
		<sjg:gridColumn 
		name="id"
		index="id"
		title="Id"
		align="center"
		editable="false"
		hidden="true"
		/> 
		<sjg:gridColumn 
		name="feedBy"
		index="feedBy"
		title="Patient Name"
		width="100"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="feedByMob"
		index="feedByMob"
		title="Mobile No"
		width="80"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="feedByMailId"
		index="feedByMailId"
		title="Email Id"
		width="120"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="doctorName"
		index="doctorName"
		title="Doctor Name"
		width="100"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="feedDate"
		index="feedDate"
		title="Date"
		width="100"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="feedTime"
		index="feedTime"
		title="Time"
		width="50"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="feedLevel"
		index="feedLevel"
		title="Level"
		width="50"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="purposeVisit"
		index="purposeVisit"
		title="Purpose Of Visit"
		width="140"
		align="center"
		editable="false"
		/> 
		<sjg:gridColumn 
		name="comments"
		index="comments"
		title="Comments"
		width="140"
		align="center"
		editable="false"
		/> 
</sjg:grid>
<div id="takeActionOnLead8" style="display: none; width:850px;">
<div class="wrapper">
	<div class="sub_wrapper">
		<div class="block_main repeating_block_head"><a href="javascript:void();" id="tab4" class="inactive" onclick="javascript:toggle_visibility('content9','tab4');"><h1><b>Take Action</b></h1></a></div>
		<div class="content_main" id="content9" style="border-top:3px solid #aaa9ab; min-width:98%; overflow-x:scroll;">
			
		</div>
	</div>
</div>
</div>

</center>
</div>
</div>

</center>
</div>
</div>
</body>
</html>