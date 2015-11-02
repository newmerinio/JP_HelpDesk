<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function viewStatus(cellvalue, options, row)
{
    return '<a href="#" onClick="viewStatusDetails('+row.feedStatId+','+row.id+');" ><b><font color="blue">'+row.ticket_no+'</font></b></a>';
}
function viewStatusDetails(feedStatId,id)
{
	 var clientId  = jQuery("#clId").text();
	 var ticketNo  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'ticket_no');
	 ticketNo=$(ticketNo).text();
	 var patientId  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'empId');
	 var patientName  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'clientName');
	// patientName=$(patientName).text();
	 var dept  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'feedback_to_dept');
	 var cat  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'feedback_catg');
	 var subCat  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'feedback_subcatg');
	 var brief  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'feed_brief');
	 var date  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'open_date');
	 var centerCode  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'location');
	 var status  = jQuery("#gridedittableHistory").jqGrid('getCell',id,'status');
    var conP = "<%=request.getContextPath()%>";
	$("#feedbackHistoryView12").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/feedback_Activity/getActivityStatusDetails.action?dataFor=status&feedStatId="+id+"&ticketNo="+ticketNo+"&activityFlag=0&clientId="+clientId+"&patientId="+patientId+"&patientName="+patientName+"&dept="+dept+"&cat="+cat+"&subCat="+subCat+"&brief="+brief+"&date="+date+"&centerCode="+centerCode+"&status="+status,
        success : function(subdeptdata) {
	    	 $("#feedbackHistoryView12").html(subdeptdata);
    },
       error: function() {
           alert("error");
        }
     }); 
     $("#mybuttondialog1").dialog({title: 'Open Status For UHID '+clientId});
     $("#mybuttondialog1").dialog('open'); 
}
</script>
</head>
<body>
 <sj:dialog 
    	id="mybuttondialog1" 
    	autoOpen="false" 
    	modal="true" 
    	width="1000"
		height="550"
    	resizable="false"
    >
    <div id="feedbackHistoryView12"></div>
</sj:dialog> 
<div hidden="true" id="clId"><s:property value="clientId"/></div>
<s:url id="href_URL" action="viewPatAllTicketDetails" namespace="/view/Over2Cloud/feedback/report" escapeAmp="false">
<s:param name="clientId" value="%{clientId}"></s:param>
<s:param name="mobNo" value="%{mobNo}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittableHistory"
          href="%{href_URL}"
          gridModel="feedbackList"
          groupField="['feedtype']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>{0} : {1} Tickets</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="15,30,45"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          rownumbers="true"
          >
		 <s:property value="%{feedbackColumnNames.size}"/>
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
</body>
</html>