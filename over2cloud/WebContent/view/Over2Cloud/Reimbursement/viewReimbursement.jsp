<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript">
	function printReimb()
	{
		alert("Alert >>>>getPrintData>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	   
		$("#printSelectBox").load("view/Over2Cloud/Reimbursement/printReimbInfo12345678.action");
		$("#printSelectBox").dialog('open');
	}

function addReimbursement()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><center><img src=images/ajax-loaderNew.gif></center>");
	var conP = "<%=request.getContextPath()%>";
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Reimbursement/beforeAddReimbursement.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function openDialog(id)
{
	alert("id>>>>>>>>>>>>>>"+id);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Reimbursement/getAttachmentTask.action?id="+id,
	    success : function(clientdata) {
		       $("#"+"download").html(clientdata);
	    },
	   error: function() {
	        alert("error");
	    }
	 }); 
	
}
--></SCRIPT>
<SCRIPT type="text/javascript">
	 /* * Format a Column as Image */
	   function formatImage(cellvalue, options, row) 
	    {
		 var context_path = '<%=request.getContextPath()%>';
		 cellvalue="Search";
	 	return "&nbsp&nbsp<img title='Download Document' src='"+ context_path +"/images/download.png' onClick='openDialog("+row.id+");' />"
	 	;}

	  function formatLink(cellvalue, options, rowObject) {
		  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }
</SCRIPT>
</head>
<body>
<sj:dialog 
       id="printSelectBox" 
       title="Print Conveyance & Other Expenses Claim Form" 
       autoOpen="false"  
       modal="true" 
       height="420" 
       width="900" 
       showEffect="drop">
     </sj:dialog>
<div class="list-icon"><div class="clear"></div><div class="head"><h4>Reimbursement >>View</h4><s:property value=""/></div></div>
<div class="clear"></div>
<div class="rightHeaderButton">
<sj:a cssClass="btn createNewBtn"  onclick="addReimbursement()" button="true" buttonIcon="ui-icon-plus"> Create New Reimbursement </sj:a>
 </div><br>
<div id="download" align="left"></div>
<div style=" float:left; padding:10px 1%; width:98%;">
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
<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="getCurrentColumn('downloadAllData','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" >Download</sj:a>	
<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="importAsset();">Import</sj:a>
<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" onclick="printReimb();">Print</sj:a>
</td> 
</tr></tbody></table></div></div></div>
<s:url id="viewReimbursement_URL" action="reimbursementDataInGrid"/>
<sjg:grid 
		  id="gridtable"
          href="%{viewReimbursement_URL}"
          gridModel="gridModel"
          navigator="true"
          dataType="json"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          viewrecords="true"       
          pager="true"
          loadingText="Requesting Data..."  
          rowNum="15"
          rownumbers="true"          
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          footerrow="true"
    	  userDataOnFooter="true"
          >
		<sjg:gridColumn 
            name="imagename"
            name="imagename"
     		title="Download"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="50"
    		formatter="formatImage"
    		/>
		
<s:iterator value="masterViewProp" id="masterView" >  
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
</sjg:grid>
<table border="1"width="100%">
<thead>
 <tr>  
  <td><center><strong><b>Total Claim Balance {A-B} =<s:property value="%{detected_amount}"/></b></strong></center></td> 
 </tr>
 </thead>
</table>

<!--<table border="1" width="100%">  
  <thead>  
  <tr>  
 <td><b>Signature</b></td> 
  <td><b>(A) Total Amount</b></td>  
   <s:if test="%{arrayfieldName.size()!=0}">
   <s:iterator value="tempMap">
    <tr>  
     <td><s:property value="%{amount}"/></td>
  </tr>  
   </s:iterator>
  </s:if>
  </tr>
  
  <tr>  
  <td><b>Date</b></td> 
  <td><b>(B) Advance Amount Received(If Any)</b></td>
   <s:if test="%{arrayfieldName.size()!=0}">
   <s:iterator value="tempMap">
    <tr>  
     <td><s:property value="%{totalamont}"/></td>  
  </tr>  
   </s:iterator>
  </s:if>
   <td><b> </b></td>  
  </tr>
  
  <tr>  
   <td><b>Balance</b></td> 
  <td><b>(C) Total Claim Balance{A-B}</b></td>
  <td><s:property value="%{sum_totalaoumt}"/></td>   
  </tr>
  
</thead>
</table>
--><!--<table border="1" width="100%">
 <tfoot>
  <tr>
   <td><h3><b><I>Kindly support Service Call Report, Other Bill, Bonuse Clause Number etc. in References Doc. Column for Approved of respective claim</I></b></h3></td>  
  </tr>
  </tfoot>
</table>
	--></div>
	<div>
	<br>
	<br>
</div>
</body>
</html>