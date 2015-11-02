<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<SCRIPT type="text/javascript">
	 /* * Format a Column as Image */
	 function formatImage(cellvalue, options, row) {
		 var context_path = '<%=request.getContextPath()%>';
		 cellvalue="Search";
	 	return "<img title='Full View Invoice' src='"+ context_path +"/images/view.png' onClick='fullviewMe("+row.id+");' />"+
	 	"&nbsp&nbsp<img title='PDF Downloade Invoice' src='"+ context_path +"/images/page_white_acrobat.png' onClick='downloadPDF("+row.id+");' />"+
	 	"&nbsp&nbsp<img title='Add Automated Kind Reminder' src='"+ context_path +"/images/add.png' onClick='addreminder("+row.id+");' />"+
	 	"&nbsp&nbsp<img title='Excel Downloade Invoice' src='"+ context_path +"/images/page_white_excel.png' onClick='downloadEXECL("+row.id+");' />"+
		"&nbsp&nbsp<img title='DOC Downloade Invoice' src='"+ context_path +"/images/page_white_word.png' onClick='downloadDOC("+row.id+");' />"+
		"&nbsp&nbsp<img title='Print Invoice' src='"+ context_path +"/images/printer.png' onClick='downloadDOC("+row.id+");' />"+
		"&nbsp&nbsp<img title='Mail Invoice'  src='"+ context_path +"/images/mail-message-new.png' onClick='sendmaildownloadpdf("+row.id+");' />"+
		"&nbsp&nbsp<img title='Payment Invoice' src='"+ context_path +"/images/money_dollar.png' onClick='invoicepayment("+row.id+");' />"
	 	;}

	 function formatLink(cellvalue, options, rowObject) {
		  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }

	 
function fullviewMe(id){
	 var conP = '<%=request.getContextPath()%>';
     var grandtotal = jQuery("#gridedittable").jqGrid('getCell',id, 'grandtotal');
	var customer_name = jQuery("#gridedittable").jqGrid('getCell',id, 'customer_name');
	var customer_type = jQuery("#gridedittable").jqGrid('getCell',id, 'customer_type');
	var invoice_duedate = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_duedate');
	var invoice_date = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_date');
	var invoice_no = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_no');
	var po_no = jQuery("#gridedittable").jqGrid('getCell',id, 'purchase_order');
	var contact_name = jQuery("#gridedittable").jqGrid('getCell',id, 'contact_name');
	var invoice_status = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_status');
	var subject = jQuery("#gridedittable").jqGrid('getCell',id, 'subject');
	$("#invoicemail").load(conP+"/view/invoiceOver2Cloud/getinvoicefullview.action?id="+id+"&customer_name="+customer_name.split(" ").join("%20")+"&grandtotal="+grandtotal+"&customer_type="+customer_type.split(" ").join("%20")+"&invoice_duedate="+invoice_duedate+"&invoice_date="+invoice_date
    +"&invoice_no="+invoice_no+"&po_no="+po_no+"&contact_name="+contact_name.split(" ").join("%20")+"&invoice_status="+invoice_status.split(" ").join("%20")+"&subject="+subject.split(" ").join("%20"));
	}

function invoicepayment(id){
    var conP = '<%=request.getContextPath()%>';
    var grandtotal = jQuery("#gridedittable").jqGrid('getCell',id, 'grandtotal');
	var customer_name = jQuery("#gridedittable").jqGrid('getCell',id, 'customer_name');
	var customer_type = jQuery("#gridedittable").jqGrid('getCell',id, 'customer_type');
	var invoice_duedate = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_duedate');
	var invoice_date = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_date');
	var invoice_no = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_no');
	var po_no = jQuery("#gridedittable").jqGrid('getCell',id, 'purchase_order');
	$("#invoicemail").load(conP+"/view/invoiceOver2Cloud/getinvoicepayment.action?id="+id+"&customer_name="+customer_name.split(" ").join("%20")+"&totalamount="+grandtotal+"&customer_type="+customer_type.split(" ").join("%20")+"&invoice_duedate="+invoice_duedate+"&invoice_date="+invoice_date
    +"&invoice_no="+invoice_no+"&po_no="+po_no);
}

function addreminder(id){
   var conP = '<%=request.getContextPath()%>';
    var grandtotal = jQuery("#gridedittable").jqGrid('getCell',id, 'grandtotal');
	var customer_name = jQuery("#gridedittable").jqGrid('getCell',id, 'customer_name');
	var customer_type = jQuery("#gridedittable").jqGrid('getCell',id, 'customer_type');
	var invoice_duedate = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_duedate');
	var invoice_no = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_no');
	var invoice_date = jQuery("#gridedittable").jqGrid('getCell',id, 'invoice_date');
	$("#invoicemail").load(conP+"/view/invoiceOver2Cloud/getinvoicekindremainder.action?id="+id+"&customer_name="+customer_name.split(" ").join("%20")+"&totalamount="+grandtotal+"&customer_type="+customer_type.split(" ").join("%20")+"&invoice_duedate="+invoice_duedate+"&invoice_date="+invoice_date
	+"&invoice_no="+invoice_no);
}
function downloadPDF(xxx){
	var conP = '<%=request.getContextPath()%>';
    var downloadType="downloadPDF";
    var downloadurlaction="getinvoicepdfdownload";
	$("#downloadpdfid").load(conP+"/view/invoiceOver2Cloud/getinvoicepdfconfirmation.action?id="+xxx+"&downloadType="+downloadType+"&downloadurlaction="+downloadurlaction);
    $("#downloadpdfid").dialog('open');
}
function downloadDOC(xxx){
	var conP = '<%=request.getContextPath()%>';
    var downloadType="downloadDOC";
    var downloadurlaction="getinvoicedocdownload";
	$("#downloadpdfid").load(conP+"/view/invoiceOver2Cloud/getinvoicepdfconfirmation.action?id="+xxx+"&downloadType="+downloadType+"&downloadurlaction="+downloadurlaction);
    $("#downloadpdfid").dialog('open');
}
function downloadEXECL(xxx){
	var conP = '<%=request.getContextPath()%>';
    var downloadType="downloadEXCEL";
    var downloadurlaction="getinvoiceexceldownload";
	$("#downloadpdfid").load(conP+"/view/invoiceOver2Cloud/getinvoicepdfconfirmation.action?id="+xxx+"&downloadType="+downloadType+"&downloadurlaction="+downloadurlaction);
    $("#downloadpdfid").dialog('open');
}
function dowloadinvoice(){
 $("#download").submit();
}
function sendmaildownloadpdf(xxxx){
var conP = '<%=request.getContextPath()%>';
    var emailidtwo= "pankajk@dreamsol.biz";
	var customer_name = jQuery("#gridedittable").jqGrid('getCell',xxxx, 'customer_name');
	var emailid = jQuery("#gridedittable").jqGrid('getCell',xxxx, 'emailid');
	var subject= "Invoice "+xxxx+"  from "+customer_name+" is attached with PDF";
	$("#invoicemail").load(conP+"/view/invoiceOver2Cloud/mailinvoiceattchedpdf.action?id="+xxxx+"&subject="+subject.split(" ").join("%20")+"&emailidone="+emailid+"&emailidtwo="+emailidtwo);
	 }
</SCRIPT>
<script type="text/javascript">
function setDownloadType(type){
        var mappedtablele = $("#mappedtablele").text();
	    var basicdetails_table = $("#basicdetails_table").text();
		var addressdetails_table = $("#addressdetails_table").text();
		var customdetails_table= $("#customdetails_table").text();
		var descriptive_table= $("#descriptive_table").text();
		var createtbasicdetails_table= $("#createtbasicdetails_table").text();
		var createaddressdetails_table= $("#createaddressdetails_table").text();
$("#downloaddailcontactdetails").load("view/contactOver2Cloud/progressbar.action?downloadType="+type+"&basicdetails_table="+basicdetails_table+"&addressdetails_table="+addressdetails_table
+"&customdetails_table="+customdetails_table+"&descriptive_table="+descriptive_table+"&createtbasicdetails_table="+createtbasicdetails_table+"&createaddressdetails_table="+createaddressdetails_table+
"&mappedtablele="+mappedtablele);
$("#downloaddailcontactdetails").dialog('open');
}
</script>

</head>
<body>
   <sj:dialog 
        		buttons="{ 
    		'Cancel Button':function() { oksaveLevelConfiguration(); },
    		}"  
      	id="fullviewid" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Full View of Invoice"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="800"
		height="500"
    >
    </sj:dialog>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
		<div class="tabs" align="left">
								<ul>
									<li style="margin-left: -12px;"><a href="javascript:void();" onclick="invoicedetailsAddd();">Create New Invoice</a></li>
									<li><a href="javascript:void();" onclick="pageisNotAvibale();">Mail Invoice</a></li>
									<li><a href="javascript:void();" onclick="pageisNotAvibale();">Download Invoice</a></li>
									<li><a href="javascript:void();" class="delete" onclick="pageisNotAvibale();">Delete Invoice</a></li>
								</ul>
							</div>
<center>
<div class="form_inner" id="form_reg" style="margin-left: -51px;">
<div class="page_form">
<s:url id="viewcontactData" action="viewcontactData" escapeAmp="false">
<s:param name="mappedtablele" value="%{mappedtablele}"></s:param>
<s:param name="basicdetails_table" value="%{basicdetails_table}"></s:param>
<s:param name="addressdetails_table" value="%{addressdetails_table}"></s:param>
<s:param name="customdetails_table" value="%{customdetails_table}"></s:param>
<s:param name="descriptive_table" value="%{descriptive_table}"></s:param>
<s:param name="createtbasicdetails_table" value="%{createtbasicdetails_table}"></s:param>
<s:param name="createaddressdetails_table" value="%{createaddressdetails_table}"></s:param>
</s:url>
<s:url id="editContactDataGrid" action="editContactDataGrid" escapeAmp="false">
<s:url id="statusurl" action="statusurl" escapeAmp="false"></s:url>
</s:url>
<center>
<sjg:grid 
		  id="gridedittable"
		  loadonce="false"
          caption="%{headerName}"
          href="%{viewcontactData}"
          gridModel="contactDetail"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="%{editDeptData}"
          navigatorSearch="true"
          rowList="10,15,20,25,50,75,100,150,200,300,400,500,600,700,800,900,1000"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          rowNum="10"
          width="1000"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{editContactDataGrid}"
          shrinkToFit="false"
          >
         <sjg:gridColumn 
            name="imagename"
            name="imagename"
     		title="Actions"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="200"
    		formatter="formatImage"
    		/>
		<s:iterator value="contactGridColomns" id="contactGridColomns" > 
		
		<s:if test="search">
			<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		surl="%{statusurl}" searchtype="select" searchoptions="{sopt:['eq','ne'], dataUrl: '%{statusurl}'}"
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
			<sjg:gridColumn 
		name="grandtotal"
		index="grandtotal"
		title="Total Ammount"
		width="100"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/> 
		<sjg:gridColumn 
		name="emailid"
		index="emailid"
		title="Email Id"
		width="100"
		align="center"
		editable="false"
		search="false"
		hidden="true"
		/>
</sjg:grid>
</center>
</div>
</div>
</center>
</div>
</div>
<br/> 
  <div id="invoicemail">
<center><ul>

  <sj:a id="downloadPDF" 
        
                button="true" 
                onclick="setDownloadTypestillnot('downloadPDF')"
                buttonIcon="ui-icon-arrowthickstop-1-s"
        >
                PDF Report
        </sj:a>
          <sj:a id="downloadExcel" 
             onclick="setDownloadTypestillnot('downloadExcel')" 
             buttonIcon="ui-icon-arrowthickstop-1-s"
             button="true"
        >
                Excel Report
        </sj:a>
          <sj:a id="downloadCSV" 
                onclick="setDownloadTypestillnot('downloadCSV')"
                button="true" 
                buttonIcon="ui-icon-arrowthickstop-1-s"
        >
                CSV Report
        </sj:a>
        
          <sj:a id="downloadHTML" 
                button="true" 
                onclick="setDownloadTypestillnot('downloadHTML')"
                buttonIcon="ui-icon-arrowthickstop-1-s"
        >
                HTML Report
        </sj:a>
        </ul>
        </center>
         </div>
        <center>
      
  </center>

 

 
   <sj:dialog 
      	id="downloadpdfid" 
      	 	 		buttons="{ 
    		'OK Download':function() { dowloadinvoice(); },
    		}"  
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Are You Sure, You want to Download"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="390"
		height="100"
    />

  
</body>
</html>
