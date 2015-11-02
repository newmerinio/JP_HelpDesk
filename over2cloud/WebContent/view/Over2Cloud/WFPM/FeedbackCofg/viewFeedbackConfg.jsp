<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">
	function addFeedbackConfig()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/beforeFeedbackConfigAdd.action?mainHeaderName=Feedback Configuration",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}	

	function searchKeyword(param)
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/searchKeyword.action?searchType="+param+"&mainHeaderName=Feedback Configuration",
		    success : function(subdeptdata) {
	       $("#"+"datapart").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</SCRIPT>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head"><s:property value="mainHeaderName"/> </div>	
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:20px 1%; width:98%;">
	<div class="rightHeaderButton">
		<input class="btn createNewBtn" value="Feedback Config." type="button" onClick="addFeedbackConfig();">
		<span class="normalDropDown"> </span>
	</div>
	
	<div class="leftHeaderButton">
		<s:select id="selectstatus" list="#{'3':'All Keyword','0':'Positive','1':'Negative'}"  cssClass="button" onchange="searchKeyword(this.value);"/>
	</div>
	<center>
	<div class="clear"></div>
	<div class="listviewBorder">
 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
			<!--<input id="sendMailButton" class="button"  value="Edit" type="button" onclick="editRow()">
			--><input name="cvDelete" id="cvDelete" class="button" value="Delete" type="button" onclick="deleteRow()">		
			</td></tr></tbody></table>
			</td>
			</tr></tbody></table></div>
		</div>
 	</div>
	<s:url id="viewFeedConfig" action="viewFeedbackConfig"></s:url>
	<s:url id="viewEditGrid" action="editFeedbackConfig"></s:url>
	<div id="datapart">
	<sjg:grid  
		  id="viewFeedbackConfg"
          href="%{viewFeedConfig}"
          gridModel="feedbackDetail"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          navigatorRefresh="false"
          rowList="10"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewEditGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          autowidth="true"
          shrinkToFit="true"
          onSelectRowTopics="rowselect"
          cssClass="textField"
          
          >
          <sjg:gridColumn name="after" index="after" title="After" editable="false" align="center"></sjg:gridColumn>
          <sjg:gridColumn name="time" index="time" title="Time" editable="false" align="center"></sjg:gridColumn>
          <sjg:gridColumn name="keyword" index="keyword" title="Keyword" editable="false" align="center"></sjg:gridColumn>
          <sjg:gridColumn name="length" index="length" title="Length" editable="false" align="center"></sjg:gridColumn>
          <sjg:gridColumn name="type" index="type" title="Type" editable="false" align="center"></sjg:gridColumn>
   	 </sjg:grid></div>
   	 </center>
   	 </div>
</body>
<script type="text/javascript">
	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});
	
	function editRow()
	{
		jQuery("#viewFeedbackConfg").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		$("#viewFeedbackConfg").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	function searchData()
	{
		jQuery("#viewFeedbackConfg").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	
</script>
</html>