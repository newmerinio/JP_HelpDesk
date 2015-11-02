<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<% String userRights=session.getAttribute("userRights")== null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript">
$.subscribe('getStatusForm', function(event,data)
  {
	  var connection=document.getElementById("conString").value;
	  var feedStatus = document.getElementById("feedStatusValue").value;
	  var feedid    = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	  var ticketNo  = jQuery("#gridedittable").jqGrid('getCell',feedid,'ticket_no');
	  var feedby    = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_by');
	  var feedmob   = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_by_mobno');
	  var feedemail = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_by_emailid');
	  var subcatg   = jQuery("#gridedittable").jqGrid('getCell',feedid,'subCatg');
	  var feedbrief = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_brief');
	  var opendate  = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_date');
	  var opentime  = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_time');
	  var allotto   = jQuery("#gridedittable").jqGrid('getCell',feedid,'allot_to');
	  
	  if (feedStatus=='Pending') {
		  $("#data_part").load(connection+"/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/feedAction.action?status="+feedStatus.split(" ").join("%20")+"&feedId="+feedid.split(" ").join("%20")+"&ticketNo="+ticketNo.split(" ").join("%20")+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&emailId="+feedemail.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&openTime="+opentime.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20"));
	  }
	  else if(feedStatus=='Snooze') {
		  $("#data_part").load(connection+"/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/feedAction.action?status="+feedStatus.split(" ").join("%20")+"&feedId="+feedid.split(" ").join("%20")+"&ticketNo="+ticketNo.split(" ").join("%20")+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&emailId="+feedemail.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&openTime="+opentime.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20"));
	  }
	  else if(feedStatus=='High Priority') {
		  $("#data_part").load(connection+"/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/feedAction.action?status="+feedStatus.split(" ").join("%20")+"&feedId="+feedid.split(" ").join("%20")+"&ticketNo="+ticketNo.split(" ").join("%20")+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&emailId="+feedemail.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&openTime="+opentime.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20"));
	  }
	 // $(".ui-dialog-titlebar").show();
	 // $("#feed_status").dialog('open');
  });

$.subscribe('getFormData', function(event,data)
  {
		  var connection=document.getElementById("conString").value;
		  var feedStatus = document.getElementById("feedStatusValue").value;
		  var feedid     = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
		  var ticketNo   = jQuery("#gridedittable").jqGrid('getCell',feedid,'ticket_no');
		  var opendate   = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_date');
		  var opentime   = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_time');
		  var todept     = jQuery("#gridedittable").jqGrid('getCell',feedid,'by_dept');
		  var bydept     = jQuery("#gridedittable").jqGrid('getCell',feedid,'to_dept');
		  var serialno     = jQuery("#gridedittable").jqGrid('getCell',feedid,'serialno');
		  var catg       = jQuery("#gridedittable").jqGrid('getCell',feedid,'category');
		  var subcatg    = jQuery("#gridedittable").jqGrid('getCell',feedid,'subCatg');
		  var feedbrief  = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_brief');
		  var feedby     = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_by');
		  var feedmob    = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_by_mobno');
	  if (feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority' || feedStatus=='Ignore') {
		  var allotto = jQuery("#gridedittable").jqGrid('getCell',feedid,'allot_to');
		  $("#printSelect").load(connection+"/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/printTicketInfo.action?feedStatus="+feedStatus+"&ticketno="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&open_date="+opendate.split(" ").join("%20")+"&open_time="+opentime.split(" ").join("%20")+"&catg="+catg.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&feed_brief="+feedbrief.split(" ").join("%20")+"&allotto_name="+allotto.split(" ").join("%20")+"&todept="+todept.split(" ").join("%20")+"&bydept="+bydept.split(" ").join("%20")+"&serialno="+serialno.split(" ").join("%20"));
	  }
	  else if(feedStatus=='Resolved') {
		  var allotto1  = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_by');
		  var resolveon = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_date');
		  var resolveat = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_time');
		  var actiontaken = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_remark');
		  var spareused = jQuery("#gridedittable").jqGrid('getCell',feedid,'spare_used');
		  $("#printSelect").load(connection+"/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/printTicketInfo.action?feedStatus="+feedStatus+"&ticket_no="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&open_date="+opendate.split(" ").join("%20")+"&open_time="+opentime.split(" ").join("%20")+"&catg="+catg.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&feed_brief="+feedbrief.split(" ").join("%20")+"&allotto="+allotto1.split(" ").join("%20")+"&todept="+todept.split(" ").join("%20")+"&resolveon="+resolveon+"&resolveat="+resolveat+"&spareused="+spareused.split(" ").join("%20")+"&actiontaken="+actiontaken.split(" ").join("%20"));
		  }
	  $("#printSelect").dialog('open');
  });

$.subscribe('getMoreData', function(event,data)
	{

	    var feedStatus = document.getElementById("feedStatusValue").value;
	    if (feedStatus!='Resolved') {
	   	 var changeStatus = document.getElementById("changedStatusId").value;
	    }
		 if (feedStatus!='Resolved' && changeStatus=='Resolved') {
			 var forDeptId = document.getElementById("forDeptId").value;
			 var fromDate = document.getElementById("hiddenFromDate").value;
			 var toDate = document.getElementById("hiddenToDate").value;
		 }
		 else if (feedStatus!='Resolved' && changeStatus!='Resolved') {
			 var forDeptId = document.getElementById("forDeptId").value;
			 var fromDate = document.getElementById("hiddenFromDate").value;
			 var toDate = document.getElementById("hiddenToDate").value;
		 }
		 else if (feedStatus=='Resolved') {
			 var fromDate = document.getElementById("fromDate").value;
			 var toDate = document.getElementById("toDate").value;
		 }

		 if (feedStatus!='Resolved') {
			 $("#gridedittable").jqGrid('setGridParam', {
				  url:"view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/viewMoreFeedbackDetail.action?feedStatus="+changeStatus+"&newDept_id="+forDeptId+"&fromDate="+fromDate+"&toDate="+toDate+"&dataFlag=M"
					,datatype:'JSON'
					}).trigger("reloadGrid");
		}
			else {
				$("#gridedittable").jqGrid('setGridParam', {
					  url:"view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/viewMoreFeedbackDetail.action?feedStatus="+feedStatus+"&fromDate="+fromDate+"&toDate="+toDate+"&dataFlag=M"
					  ,datatype:'JSON'
						}).trigger("reloadGrid");
			}
  });

function openDiv(status)
{
	if (status=='Resolved') {
		document.getElementById('datediv').style.display="block";
	}
	else {
		document.getElementById('datediv').style.display="none";
	}
}

</script>
</head>
<body>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop">
<sj:dialog id="feed_status" modal="true" effect="slide" autoOpen="false"  width="1100" hideEffect="explode" position="['center','top']"></sj:dialog>
<div id="ticketsInfo"></div>
</sj:dialog>
<s:hidden id="feedStatusValue" value="%{feedStatus}"></s:hidden>
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head"><s:property value="%{feedStatus}"/> Feedback</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody>
	   <tr>
		   <!-- Block for insert Left Hand Side Button -->
		   <td>
			  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
			    <tbody>
				    <tr><td class="pL10">
				    <s:if test="%{feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority'}">
					  <sj:submit value="Take Action" button="true" onClickTopics="getStatusForm"/>
				    </s:if>
			        <s:if test="%{feedStatus!='Resolved' && dataFlag=='Y'.toString()}" >
				             <s:select  
						    		id					=		"changedStatusId"
						    		name				=		"changedStatus"
						    		list				=		"statusList"
						    		headerKey           =       "%{feedStatus}"
						    		headerValue         =       "%{feedStatus}"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 120px;"	
						      		onchange            =       "openDiv(this.value);">
					      	 </s:select> 
							 <s:select  
						    		id					=		"forDeptId"
						    		name				=		"formDept"
						    		list				=		"deptList"
						    		headerKey           =       "-1"
						    		headerValue         =       "All Department"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 130px;">
					      	 </s:select> 
					  </s:if>
			       
			          <s:if test="%{feedStatus=='Resolved'}">
				          &nbsp;&nbsp;&nbsp;&nbsp;<b>From :</b>&nbsp;&nbsp;&nbsp;<sj:datepicker name="fromDate" id="fromDate" value="%{fromDate}"  changeMonth="true" changeYear="true" readonly="true" yearRange="2010:2015" cssClass="button" cssStyle="height:16px; width:70px;font-weight: bold;text-align:center;margin-top: 3px;" showOn="focus" displayFormat="dd-mm-yy"  placeholder="Select From Date"/>
				          &nbsp;&nbsp;&nbsp;&nbsp;<b>To :</b>&nbsp;&nbsp;&nbsp;<sj:datepicker name="toDate" id="toDate" value="%{toDate}" changeMonth="true" readonly="true" changeYear="true" yearRange="2010:2015" cssClass="button" cssStyle="height:16px; width:70px;font-weight: bold;text-align:center" showOn="focus" displayFormat="dd-mm-yy"  placeholder="Select To Date"/>
				      </s:if>
				    </td>
				    <td id="datediv" style="display: none; margin-top: 3px;">
							     &nbsp;&nbsp;&nbsp;&nbsp;<b>From :</b>&nbsp;&nbsp;&nbsp;<sj:datepicker name="hiddenfromDate" id="hiddenFromDate" value="%{fromDate}"  changeMonth="true" changeYear="true" readonly="true" yearRange="2010:2015" cssStyle="height:16px; width:70px;font-weight: bold;text-align:center" cssClass="button" showOn="focus" displayFormat="dd-mm-yy"  placeholder="Select From Date"/>
							     &nbsp;&nbsp;&nbsp;&nbsp;<b>To :</b>&nbsp;&nbsp;&nbsp;<sj:datepicker name="hiddentoDate" id="hiddenToDate" value="%{toDate}" changeMonth="true" readonly="true" changeYear="true" yearRange="2010:2015"    cssStyle="height:16px; width:70px;font-weight: bold;text-align:center" cssClass="button" showOn="focus" displayFormat="dd-mm-yy"  placeholder="Select To Date"/>
				    </td>
				    <s:if test="%{remark=='Y'.toString()}" >
				                 &nbsp;&nbsp;<sj:a id="moreDataButton"  button="true"  cssClass="button" cssStyle="height:25px; margin-top:3px;" title="OK"  onClickTopics="getMoreData">OK</sj:a>
				    </s:if>
				    </tr>
				</tbody>
			 </table>
		   </td>
				  
		  <!-- Block for insert Right Hand Side Button -->
		  <td class="alignright printTitle">
		      <sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" onClickTopics="getFormData">Print</sj:a>
		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->


<div style="overflow: scroll; height: 430px;">
<s:url id="href_URL" action="viewFeedbackDetail" escapeAmp="false">
<s:param name="feedStatus" value="%{feedStatus}"></s:param>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{href_URL}"
          gridModel="feedbackList"
          groupField="['via_from']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>{0} - {1} Tickets</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="250,350,500"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="250"
          autowidth="true"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['cn','eq']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		  <s:iterator value="feedbackColumnNames" id="feedbackColumnNames" >  
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
</sjg:grid>
</div>
</div>
</div>
<div id="ab"></div>
</body>
</html>