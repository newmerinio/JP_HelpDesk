<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@ taglib uri="/struts-jquery-grid-tags" prefix="sjg" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">

function setDownloadType(type){
	var fromDate=$("#"+'fromDate').val();
	var toDate=$("#"+'toDate').val();
	var level=$("#"+'level').val();
	var conP = "<%=request.getContextPath()%>";
 var downloadactionurl="downloadfeedbackreport";
$("#downloaddailcontactdetails").load(conP+"/view/Over2Cloud/feedback/feedbackprogress.action?downloadType="+type+"&downloadactionurl="+downloadactionurl+"&fromDate="+fromDate+"&toDate="+toDate+"&tableName=feedbackdata"+"&Level="+level);
$("#downloaddailcontactdetails").dialog('open');

}
	 
</script>
</head>
<body>
	<s:url id="remoteurl" action="viewActionReportList" escapeAmp="false">
		<s:param name="fromDate" value="%{fromDate}"></s:param>
		<s:param name="toDate" value="%{toDate}"></s:param>
		<s:param name="Level" value="%{Level}"></s:param>
		<s:param name="dataQuery" value="%{dataQry}"></s:param>
	</s:url>
	<br><div style=" float:left; padding:1px 1%; width:98%; height: 350px;">

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
        <sj:a id="downloadExcel" 
             onclick="setDownloadType('downloadExcel','dataSQuery')" 
             buttonIcon="ui-icon-arrowthickstop-1-s"
             cssClass="button"
             button="true"
        >
                Excel Report
        </sj:a>
</td> 
</tr></tbody></table></div></div></div>

	<input type="hidden" name="fromDate" id="fromDate" value="<s:property value="fromDate"/>" >
	<input type="hidden" name="toDate" id="toDate" value="<s:property value="toDate"/>" >
	<input type="hidden" name="level" id="level" value="<s:property value="Level"/>" >
  <center>
   <sjg:grid
    	id="gridedittable"
    	dataType="json"
    	href="%{remoteurl}"
    	pager="true"
    	gridModel="masterViewList"
    	rowList="10,20,30"
    	rowNum="10"
    	rownumbers="true"
    	viewrecords="true"
    	shrinkToFit="false"
    	loadingText="Loading..."
    	loadonce="true"
    	autowidth="true"
    	navigatorDelete="false"
    	navigatorEdit="false"
    	navigatorView="false"
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
		/>
		</s:iterator>  
    	                
    	       </sjg:grid><br><br><br><br><br>
    	                </center></div><br><br>
    	               
  
          
         <sj:dialog 
      	id="downloaddailcontactdetails" 
      	 	  		buttons="{ 
    		'Ok Download':function() {okdownload();},
    		}"  
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Select Fields"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="390"
		height="300"
    />
    </body>
</html>