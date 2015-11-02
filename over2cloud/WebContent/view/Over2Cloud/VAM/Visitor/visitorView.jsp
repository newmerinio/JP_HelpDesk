<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	                                                                 
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
	<title>Visitor View</title>
	<script type="text/javascript">
	/* function pageisNotAvailable(){
	$("#facilityisnotavilable").dialog('open');
	} */
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
		jQuery("#vistorViewid").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		$("#vistorViewid").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#vistorViewid").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) 
	{
		  $("#vistorViewid").trigger("reloadGrid"); 
	}
</script>
<script type="text/javascript">
function refreshRow(rowid, result) 
{
	$("#gridBasicDetails").trigger("reloadGrid"); 
}	 
function downloadEXECL(rowidval){
	alert("rowExcel"+rowidval);
    var downloadType="downloadEXCEL";
    //var app_name = 'VAM';
     var jsVar = '<s:property value="#app_name"/>'; 
     alert("app_name"+jsVar);
    var downloadurlaction="getvisitorexceldownload";
	$("#downloadpdfid").load("view/Over2Cloud/VAM/download/getvisitorexcelpdfpdfconfirmation.action?id="+rowidval+"&downloadType="+downloadType+"&downloadurlaction="+downloadurlaction+"&app_name="+app_name);
    $("#downloadpdfid").dialog('open');
}
function dowloadinvoice(){
 $("#download").submit();
}
function sendmaildownloadpdf(xxxx){
    var  app_id = $("#id").val();
    var app_name = $("#app_name").val();
    var emailidtwo= "sanjivs@dreamsol.biz";
	var customer_name = jQuery("#gridedittable").jqGrid('getCell',xxxx, 'customer_name');
	var emailid = jQuery("#gridedittable").jqGrid('getCell',xxxx, 'emailid');
	var subject= "Invoice "+xxxx+"  from "+customer_name+" is attached with PDF";
	$("#data_part").load("view/invoiceOver2Cloud/mailinvoiceattchedpdf.action?id="+xxxx+"&subject="+subject.split(" ").join("%20")+"&emailidone="+emailid+"&emailidtwo="+emailidtwo+"&app_name="+app_name+"&app_id="+app_id);
}
function exitVisitor1(){
  var rowid = $("#vistorViewid").jqGrid('getGridParam','selarrrow');
  if(rowid.length==0)
      {  
       alert("please select atleast one row....");
       return false;
      }
      else if(rowid.length > 1)
      {
         alert("please select only one row....");
         return false;
      }
	  alert("Are You Sure To Exit Visitor?");
	  var srnumber = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'sr_number');
	  $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?sr_number="+srnumber,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
 }

function searchDeptWiseVisitor(){
	 var deptname = $("#deptNameid option:selected").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?deptName="+deptname+"&column="+"deptName",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function searchPurposeWiseVisitor(){
	 var purpose = $("#purpose option:selected").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?purpose="+purpose+"&column="+"purpose",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function searchLocationWisePreReq(){
	 var location = $("#location option:selected").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?location="+location+"&column="+"location",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function searchDateWiseVisitorData(){
	var from_date = $("#from_date").val();
	var to_date = $("#to_date").val();
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?from_date="+from_date+"&to_date="+to_date+"&column="+"visit_date",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
function searchData()
{     var selectedId = $("#visitorStatus").val();
     //var visitor_name = $("#visitor_name").val();
     //var visitor_mobile = $("#visitor_mobile").val();
      var searchdata = $("#searchdata").val();
      var search_parameter = $("#search_parameter").val();
      $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?selectedId="+selectedId+"&search_parameter="+search_parameter,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });

}
</script>
<script type="text/javascript">
	function reloadPage()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action?modifyFlag=0&deleteFlag=0",
			success : function(subdeptdata) {
			$("#"+"data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
	}
</script>
<script type="text/javascript">
  function reprintVisitorPass()
  {
    var rowid = $("#vistorViewid").jqGrid('getGridParam','selarrrow');
      if(rowid.length==0)
      {  
       alert("please select atleast one row....");
       return false;
      }
      else if(rowid.length > 1)
      {
         alert("please select only one row....");
         return false;
     }
    
   	var sr_number = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'sr_number');
	var visitor_name = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'visitor_name');
	var visitor_mobile = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'visitor_mobile');
	var visitor_company = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'visitor_company');
	var visited_person = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'visited_person');
	var address = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'address');
	var possession = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'possession');
	var deptName = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'deptName');
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
    if(dd<10) {
    dd='0'+dd;
	} 
    if(mm<10) {
    mm='0'+mm;
	} 
	var visit_date = dd+'-'+mm+'-'+yyyy;
	var hour = today.getHours();
	var min = today.getMinutes();
	if(hour<10) {
    hour='0'+hour;
	} 
    if(min<10) {
    min='0'+min;
	} 
	var time_in = hour+":"+min;
	// var visit_date = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'visit_date');
	// var time_in = jQuery("#vistorViewid").jqGrid('getCell',rowid, 'time_in');
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/VAM/master/rePrintVisitorPass.action?sr_number="+sr_number+"&visitor_name="+visitor_name+"&visitor_mobile="+visitor_mobile+"&visitor_company="+visitor_company+"&visited_person="+visited_person+"&address="+address+"&possession="+possession+"&deptName="+deptName+"&visit_date="+visit_date+"&time_in="+time_in,
	    success : function(subdeptdata) {
	    	$("#"+"reprintvisitorpass").html(subdeptdata);
	    	$("#"+"reprintvisitorpass").dialog('open');
	        
	       },
	    error: function() {
            alert("error");
        }
	 });
}
</script>
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
	       <div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">&nbsp&nbsp&nbsp Total:&nbsp<s:property value="%{countTotal}"/> &nbsp&nbsp Entry:&nbsp<s:property value="%{countIn}"/>&nbsp&nbsp&nbsp Exit:&nbsp<s:property value="%{countOut}"/>&nbsp&nbsp&nbsp &nbsp<sj:a id="refButtonPage" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px;margin-left: 571px;" cssClass="button" button="true" onclick="reloadPage();"></sj:a></div>
	       <div class="head" style="margin-top:4px; float: right;"><sj:a button="true" cssClass="button" cssStyle="margin-top: -2px;" style="width:20px" title="Add" buttonIcon="ui-icon-plus" id="addVisitorId">Entry</sj:a></div>
	</div>
	<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">
	<div class="rightHeaderButton">
	<span class="normalDropDown"> 
	</span>
	</div>
	<div class="listviewBorder">
			<div class="listviewButtonLayer" id="listviewButtonLayerDiv" style="height: 36px;">
			<div class="pwie">
			    <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> 
			    <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10" style="width: 75%;"> 
					   <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow();"></sj:a>
					   <!--<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow();"></sj:a>
					   --><sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow();"></sj:a>
				   	   <!--<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reloadPage();"></sj:a>
				       -->	
				       From:<sj:datepicker name="from_date" id="from_date" showOn="focus" 
						                                                                 displayFormat="dd-mm-yy" yearRange="2014:2020" 
						                                                                 readonly="true" cssClass="textField" 
						                                                                 style="margin: 0px 6px 10px; width: 8%;"></sj:datepicker>
					   To:<sj:datepicker name="to_date" id="to_date" showOn="focus" displayFormat="dd-mm-yy" 
					                                                                     yearRange="2014:2020" readonly="true"   cssClass="textField" 
					                                                                     onchange="searchDateWiseVisitorData();" style="margin: 0px 6px 10px; width: 8%;"></sj:datepicker>
					      <s:select 
			                       id="visitorStatus" 
			                       name="visitorStatus"
			                       list="#{'0':'IN','1':'OUT','2':'ALL'}"
			                       headerKey="-1"
			                       headerValue="Status"
			                       cssStyle="height: 26px; width: 9%;"
                                   theme="simple" 
			                       cssClass="select"
			                       onchange="searchData();"
			              />
			       	     <s:select  id="deptNameid"  name="deptName" list="departmentlist" headerKey="-1" headerValue="Department" theme="simple" cssClass="select" onchange="searchDeptWiseVisitor();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 10%;" /> 
				         <s:select id="purpose" name="purpose" list="purposeListPreReqest" headerKey="-1" headerValue="Purpose" theme="simple" cssClass="select" onchange="searchPurposeWiseVisitor();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 10%;" /> 
					     <s:select  id="location"  name="location" list="locationlist" headerKey="-1" headerValue="Location" theme="simple" cssClass="select" onchange="searchLocationWisePreReq();" style="margin-top: -10px; height: 28px; margin-left: 6px; width: 10%;" />
			             <s:textfield id="search_parameter" name="search_parameter" theme="simple" cssClass="textField" onchange="searchData();" style="margin-top: -10px; height: 23px; margin-left: 6px; width:9%;" placeholder="Enter Data"></s:textfield>         
			             <!--<s:textfield id="visitor_name" name="visitor_name" theme="simple" cssClass="textField" onchange="searchData();" style="margin-top: -10px; height: 23px; margin-left: 6px; width: 8%;" placeholder="Visitor Name"></s:textfield>         
			             <s:textfield id="visitor_mobile" name="visitor_mobile" theme="simple" cssClass="textField" onchange="searchData();" style="margin-top: -10px; height: 23px; margin-left: 6px; width:9%;" placeholder="Mobile No"></s:textfield>         
			             -->
			             </td></tr></tbody></table>
			             </td><td class="alignright printTitle" style="width: 20%;">
						    <!--<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Excel" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadExcel');" />
						    <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download Pdf" buttonIcon="ui-icon-arrowstop-1-s" onclick="setVisitorDownloadType('downloadPDF');" />
					        -->
					     <a><img title="Print" src="images/printer.png" style="height:23px; width:28px;" onclick="reprintVisitorPass();"/></a>&nbsp&nbsp
					     <a><img title="Download Excel" src="images/page_white_excel.png" style="height:23px; width:28px;"  cssStyle="height:25px;" onclick="setVisitorDownloadType('downloadExcel');"></a>&nbsp&nbsp
					     <a><img title="Download Pdf" src="images/page_white_acrobat.png" style="height:23px; width:28px;" cssStyle="height:25px;" onClick="setVisitorDownloadType('downloadPDF');"/></a>&nbsp&nbsp
					     <a><img title="Exit" src="images/exit1.png" style="height:23px; width:28px;" onclick="exitVisitor1();"/></a>
					     </td>   
			             </tr></tbody></table></div></div>
			             <div class="clear"></div>
 		<s:url var="app_name"  id="visitorDetails" action="visitorDetails" escapeAmp="false">
		<s:param name="app_name" value="%{#parameters.app_name}"></s:param>
		<s:param name="deptName" value="%{#parameters.deptName}"></s:param>
		<s:param name="from_date" value="%{#parameters.from_date}"></s:param>
		<s:param name="to_date" value="%{#parameters.to_date}"></s:param>
		<s:param name="purpose" value="%{#parameters.purpose}"></s:param>
		<s:param name="location" value="%{#parameters.location}"></s:param>
		<s:param name="selectedId" value="%{selectedId}"></s:param>
		<!--<s:param name="visitor_name" value="%{visitor_name}"></s:param>
		<s:param name="visitor_mobile" value="%{visitor_mobile}"></s:param>
		--><s:param name="searchdata" value="%{searchdata}"></s:param>
		<s:param name="search_parameter" value="%{search_parameter}"></s:param>
		</s:url>
		<s:url id="modifyVisitorGrid" action="modifyVisitorGrid" />
		<div style="overflow: scroll; height: 480px;">
		<sjg:grid 
	          id="vistorViewid"
	          href="%{visitorDetails}"
	          gridModel="visitorData"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="false"
	          navigatorEdit="true"
	          navigatorSearch="true"
	          editinline="false"
	          rowList="14,100,200"
	          rowNum="14"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          rownumbers="-1"
	          pagerInput="false"   
	          multiselect="true"
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          loadingText="Requesting Data..."  
	          navigatorEditOptions="{height:230,width:400}"
	          editurl="%{modifyVisitorGrid}"
	          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          onEditInlineSuccessTopics="oneditsuccess"
	          
	          >
	    <s:iterator value="viewVisitorDetail" id="viewVisitorDetail" >  
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
		<sj:dialog 
    	id="reprintvisitorpass" 
    	autoOpen="false" 
    	modal="true" 
    	title="Reprint Pass"
    	openTopics="openRemoteDialog"
    	width="652" height="350"
    	/> 
		 <sj:dialog id="downloaddaildetails" buttons="{'Download':function() { okdownload(); }, 'Cancel':function() {},}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="Select Fields" openTopics="openEffectDialog" closeTopics="closeEffectDialog"  width="250" height="400" />	
		
</body>
	<SCRIPT type="text/javascript">
	/*
	var oneWeekAgo = new Date();
	oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
    var dd = oneWeekAgo.getDate()+"-"+oneWeekAgo.getMonth()+"-"+oneWeekAgo.getFullYear();
    $("#from_date").val(dd);
	 searchDateWiseVisitorData();
	*/
	</SCRIPT>
	</html>