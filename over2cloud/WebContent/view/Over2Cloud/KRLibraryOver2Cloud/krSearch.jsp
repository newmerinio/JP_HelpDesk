<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script src="<s:url value="/js/krLibrary/demo/js/jquery-ui.1.8.20.min.js"/>"></script>
<script src="<s:url value="/js/krLibrary/tagJs/tagit.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<s:url value="/js/krLibrary/demo/css/jquery-ui-base-1.8.20.css"/>">
<link rel="stylesheet" type="text/css" href="<s:url value="/js/krLibrary/css1/tagit-stylish-yellow.css"/>">
 <SCRIPT type="text/javascript">
 $.subscribe('level1', function(event,data)
  { 
	 setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	 setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
  });
 </script>
 
<SCRIPT type="text/javascript">
function krSearchViaAjax(krLibrarySearchedDiv)
{
	var tagValue=krSearchForm.tags.value;
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/searchKRViaAjax.action?tags="+tagValue.split(" ").join("%20"),
	        success : function(theDivData) 
	        {
	    		$("#"+krLibrarySearchedDiv).html(theDivData);},
	        	error: function()
	        	{alert("error");}
	    	}
	  );
}

</SCRIPT>
</head>
	
<div class="list-icon">
	 <div class="head">KR</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Search</div> 
</div> 
<div class="clear"></div>
 <div style=" float:left; padding:5px 0%; width:100%;">
	<div class="border" style="overflow-x:hidden;">
	<%-- <s:param name="krSearch" value="%{krSearch}" />	 --%>
   <s:hidden id="123" value="%{krSearch}" />
  <s:form id="krSearchForm" name="krSearchForm" namespace="/view/Over2Cloud/KRLibraryOver2Cloud" action="searchKRByTag" theme="simple"  method="post"enctype="multipart/form-data" >
  	<div id="offering2HideShow">
   
					<div class="newColumn">
		              	<div class="leftColumn1">Tag / KR ID :</div>
		              		<div class="rightColumn1">
		                   		<s:textfield name="tags" id="tags" maxlength="50" cssClass="textField"  placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				            </div>
				    
					</div>
						<div class="clear"></div>
							 <div class="type-button" style="text-align: center;">
	       						 <sj:submit 
								     targets="orglevel1" 
								     clearForm="true"
								     value="  Add  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="5000"
								     button="true"
								     onCompleteTopics="level1"
								     cssClass="button"
								     indicator="indicator2"
								      onclick="krSearchViaAjax('krLibrarySearchedDiv');"
							     />
	   
			        
	       				 </div>
						</div>
					
 </s:form>
 </div>
</div>
   <div id="krLibrarySearchedDiv" >
  </div>
</html>