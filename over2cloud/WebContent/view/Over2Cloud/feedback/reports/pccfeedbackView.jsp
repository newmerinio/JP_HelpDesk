<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />

<script src="<s:url value="/js/feedback/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});

function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}


function pccSearchData()
{
	var mrdNo = $("#mrdNo").val();
	var compType = $("#compTypeid").val();
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/feedback/beforePccSearchedPaperView.action?modifyFlag=0&deleteFlag=0&mrdNo="+mrdNo+"&compType="+compType,
 		success : function(data) 
	    {
			$("#paperFeedbackid").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
	
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
           height="100">
 </sj:dialog>






<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<!-- Code For Header Strip -->
<s:hidden name = "mrdNo" id="mrdNo" value="%{#parameters.mrdNo}" />
<s:hidden name = "compType" id="compTypeid" value="%{#parameters.compType}" />

<div style="overflow: scroll; height: 185px;">
		<div id ="paperFeedbackid">

		</div>
		</div>
		<center>
         <sj:dialog 
      	id="downloaddailcontactdetails" 
      	 	  		buttons="{ 
    		'Download':function() {okdownload();},
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
  </center>
		</div></div>
</body>
<script type="text/javascript">
pccSearchData();
</script>

</html>