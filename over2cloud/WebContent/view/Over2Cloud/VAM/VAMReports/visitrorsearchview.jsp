<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>
	    
	    <%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
	    
	    
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	<title>Insert title here</title>
	<script type="text/javascript">
	function pageisNotAvailable(){
	$("#facilityisnotavilable").dialog('open');
	}
	</script>
	<script type="text/javascript">
	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});	
	function editRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		jQuery("#vistorReportViewid").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		$("#vistorReportViewid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#vistorReportViewid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) 
	{
		  $("#vistorReportViewid").trigger("reloadGrid"); 
	}
</script>
<SCRIPT type="text/javascript">
	 /* * Format a Column as Image */
	 function formatImage(cellvalue, options, row) {
		 var context_path = '<%=request.getContextPath()%>';
		 cellvalue="Search";
	 	return "<img title='Add Automated Kind Reminder' src='"+ context_path +"/images/add.png' onClick='pageisNotAvailable();' />"+
	 	"&nbsp&nbsp<img title='Excel Downloade Visitor Details' src='"+ context_path +"/images/page_white_excel.png' onClick='pageisNotAvailable();' />"+
		"&nbsp&nbsp<img title='DOC Download Visitor Details ' src='"+ context_path +"/images/page_white_word.png' onClick='pageisNotAvailable();' />"+
		"&nbsp&nbsp<img title='Print Visitor Details' src='"+ context_path +"/images/printer.png' onClick='pageisNotAvailable();' />"+
		"&nbsp&nbsp<img title='Mail Visitor Entry'  src='"+ context_path +"/images/mail-message-new.png' onClick='pageisNotAvailable();' />"+
		"&nbsp&nbsp<img title='Exit Visitor'  src='"+ context_path +"/images/exit1.png' onClick='exitVisitor("+row.id+");' />"
	 	;}

	 function formatLink(cellvalue, options, rowObject) {
		  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }

	 
function addreminder(id){
    var  app_id = $("#id").val();
    var app_name = $("#app_name").val();
    var conP = '<%=request.getContextPath()%>';
    var grandtotal = jQuery("#gridedittable").jqGrid('getCell',id, 'grandtotal');
	var customer_name = jQuery("#gridedittable").jqGrid('getCell',id, 'customer_name');
	var customer_type = jQuery("#gridedittable").jqGrid('getCell',id, 'customer_type');
	var invoice_duedate = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_duedate');
	var invoice_no = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_no');
	var invoice_date = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_date');
	$("#data_part").load(conP+"/view/invoiceOver2Cloud/getinvoicekindremainder.action?id="+id+"&customer_name="+customer_name.split(" ").join("%20")+"&totalamount="+grandtotal+"&customer_type="+customer_type.split(" ").join("%20")+"&invoice_duedate="+invoice_duedate+"&invoice_date="+invoice_date
	+"&invoice_no="+invoice_no+"&app_name="+app_name+"&app_id="+app_id);
}
function downloadPDF(xxx){
	var conP = '<%=request.getContextPath()%>';
    var downloadType="downloadPDF";
    var app_name = $("#app_name").val();
    var downloadurlaction="getinvoicepdfdownload";
	$("#downloadpdfid").load(conP+"/view/invoiceOver2Cloud/getinvoicepdfconfirmation.action?id="+xxx+"&downloadType="+downloadType+"&downloadurlaction="+downloadurlaction+"&app_name="+app_name);
    $("#downloadpdfid").dialog('open');
}
function downloadDOC(xxx){
	var conP = '<%=request.getContextPath()%>';
    var downloadType="downloadDOC";
     var app_name = $("#app_name").val();
    var downloadurlaction="getinvoicedocdownload";
	$("#downloadpdfid").load(conP+"/view/invoiceOver2Cloud/getinvoicepdfconfirmation.action?id="+xxx+"&downloadType="+downloadType+"&downloadurlaction="+downloadurlaction+"&app_name="+app_name);
    $("#downloadpdfid").dialog('open');
}
function downloadEXECL(xxx){
	var conP = '<%=request.getContextPath()%>';
    var downloadType="downloadEXCEL";
     var app_name = $("#app_name").val();
    var downloadurlaction="getinvoiceexceldownload";
	$("#downloadpdfid").load(conP+"/view/invoiceOver2Cloud/getinvoicepdfconfirmation.action?id="+xxx+"&downloadType="+downloadType+"&downloadurlaction="+downloadurlaction+"&app_name="+app_name);
    $("#downloadpdfid").dialog('open');
}
function dowloadinvoice(){
 $("#download").submit();
}
function sendmaildownloadpdf(xxxx){
    var  app_id = $("#id").val();
    var app_name = $("#app_name").val();
    var conP = '<%=request.getContextPath()%>';
    var emailidtwo= "pankajk@dreamsol.biz";
	var customer_name = jQuery("#gridedittable").jqGrid('getCell',xxxx, 'customer_name');
	var emailid = jQuery("#gridedittable").jqGrid('getCell',xxxx, 'emailid');
	var subject= "Invoice "+xxxx+"  from "+customer_name+" is attached with PDF";
	$("#data_part").load(conP+"/view/invoiceOver2Cloud/mailinvoiceattchedpdf.action?id="+xxxx+"&subject="+subject.split(" ").join("%20")+"&emailidone="+emailid+"&emailidtwo="+emailidtwo+"&app_name="+app_name+"&app_id="+app_id);
	 }
function exitVisitor(rowid){
	alert("Are You Sure To Exit Visitor?");
	var conP = '<%=request.getContextPath()%>';
	var srnumber = jQuery("#vistorReportViewid").jqGrid('getCell',rowid, 'sr_number');
	$("#visitorexitID").load(conP+"/view/Over2Cloud/VAM/master/exitVisitor.action?sr_number="+srnumber);
	 setTimeout(function(){ $("#visitorexitID").fadeIn(); }, 10);
     setTimeout(function(){ $("#visitorexitID").fadeOut(); }, 4000);
}
</SCRIPT>
<script type="text/javascript">
	$.subscribe('oncompleteresult', function(event,data)
		       {
		         setTimeout(function(){ $("#resultexit").fadeIn(); }, 10);
		         setTimeout(function(){ $("#resultexit").fadeOut(); }, 4000);
		       });
	
	</script>
	</head>
	<body>
	<div class="list-icon">
		<div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Report</div>
	</div>
	<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">
	<div class="rightHeaderButton">
	<span class="normalDropDown"> 
	 </span>
	</div>
	<div class="listviewBorder">
			<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie">
			 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow();"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow();"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow();"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow();"></sj:a>
			<span class="normalDropDown">
			</span> 
			</td></tr></tbody></table>
			</td><td class="alignright printTitle">
			 			 <%-- <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Excel" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadExcel');" />
						 <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Pdf" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadPDF');" /> --%>
					     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Back" buttonIcon="" id="cancelVisitorReportId">Back</sj:a>
			</td>   
			</tr></tbody></table></div>	</div>
			<div class="clear"></div>
		<s:url id="visitorRecordid" action="visitorSearchReport" escapeAmp="false">
		<s:param name="deptname" value="%{#parameters.deptname}" ></s:param>
		<s:param name="location" value="%{#parameters.location}" ></s:param>
		<s:param name="srnumber" value="%{#parameters.srnumber}" ></s:param>
		<s:param name="visitorname" value="%{#parameters.visitorname}" ></s:param>
		<s:param name="visitormobile" value="%{#parameters.visitormobile}" ></s:param>
		<s:param name="visitorcompany" value="%{#parameters.visitorcompany}" ></s:param>
		<s:param name="visitedperson" value="%{#parameters.visitedperson}"></s:param>
		<s:param name="visitedmobile" value="%{#parameters.visitedmobile}"></s:param>
		<s:param name="fromdate" value="%{#parameters.fromdate}" ></s:param>
		<s:param name="todate" value="%{#parameters.todate}" ></s:param>
		</s:url>
		<s:url id="modifyVisitorGrid" action="modifyVisitorGrid" />
		<div style="overflow: scroll; height: 480px;">
		<sjg:grid 
			  id="vistorReportViewid"
	          href="%{visitorRecordid}"
	          gridModel="visitorData"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="true"
	          navigatorEdit="true"
	          navigatorSearch="true"
	          rowList="15,20,25"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="false"  
	          loadingText="Requesting Data..."  
	          rowNum="15"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          editurl="%{modifyVisitorGrid}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          onSelectRowTopics="rowselect"
	          >
		<s:iterator value="viewVisitorReportDetail" id="viewVisitorReportDetail" >  
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
			searchoptions="{sopt:['eq','cn']}"
		/>
		</s:iterator>  
		</sjg:grid>
		</div>
		<center>
			<sj:div id="visitorexitID"  >
			</sj:div>
		</center>
		</div>
		</div>
		<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module" openTopics="openEffectDialog" closeTopics="closeEffectDialog" modal="true" width="390" height="100" />
		<sj:dialog id="downloaddaildetails" buttons="{ 'Download':function() { okdownload(); }, 'Cancel':function() {},}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="Select Fields" openTopics="openEffectDialog"closeTopics="closeEffectDialog"  width="390"height="300" />	
	</body>
	</html>