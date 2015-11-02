<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
  });
 
 function getUnmappedContact()
	{
        var outletId=$("#outletId").val();
		$("#viewAssetDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getUnmappedContact.action?outletId="+outletId,
		success : function(data)
		{
			$("#viewAssetDiv").html(data);
		    },
		    error : function () 
		    {
			alert("Somthing is wrong to get Asset");
		}
		});
	}

 function mapContact2Outlet()
 {
	 	var contactId;
	 	contactId = $("#viewUnMappedContactId").jqGrid('getGridParam','selarrrow');
		var outletId=$("#outletId").val();
		var sendFlag=$("#sendFlag").val();
		if(contactId=="")
		{
		     alert("Please select atleast one check box...");        
		     return false;
		}
		else
		{
			$.ajax({
			    type : "post",
			    url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/mapContact2Outlet.action",
			    data:"outletId="+outletId+"&contactId="+contactId+"&sendFlag="+sendFlag,
			    success : function(data) 
			    {
					setTimeout(function(){ $("#resultDiv").fadeIn(); }, 10);
				 	setTimeout(function(){ $("#resultDiv").fadeOut(); }, 900);
				 	getUnmappedContact();
				},
			   error: function() {
			        alert("error");
			    }
			 });
		}	
 }

 function viewMapContact2Outlet()
 {
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/BeforeViewCont2OutletMapping.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
 }


 
getUnmappedContact();
</SCRIPT>
</head>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	  Map Contact In Outlet</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head"> Add </div> 
</div>
<div class="clear"></div>
<center><div id="resultDiv"></div></center>
	   <div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
	    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie">
				<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
					<tbody>
						<tr>
							  <td>
								  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
								    <tbody><tr>
								    <td> 
								    
								    <s:select  
		                              	id					=		"outletId"
		                              	list				=		"outletName"
		                              	name				=		"outletId"
		                              	headerKey			=		"-1"
		                              	headerValue			=		"Outlet Name" 
		                              	cssClass			=		"button"
		                              	cssStyle			=		"margin-left: 3px;width: 143px;height: 30px;"
		                              	theme 				=		"simple"
		                              	onchange			=		"getUnmappedContact();"
									>
									</s:select>
									
									<s:select  
		                              	id					=		"sendFlag"
		                              	list				=		"#{'NC':'No Copy','CC':'Carbon Copy'}"
		                              	cssClass			=		"button"
		                              	cssStyle			=		"margin-left: 3px;width: 143px;height: 30px;"
		                              	theme 				=		"simple"
		                              	onchange			=		"assetDetails();"
										                              >
									</s:select>
								    
								     
							     <sj:a id="add" cssStyle="margin-left: 1px;height: 29px;" buttonIcon="" cssClass="button" button="true" onclick="mapContact2Outlet()"> Allot </sj:a>
								 <sj:a id="cancel" cssStyle="margin-left: 1px;height: 29px;" buttonIcon="" cssClass="button" button="true"  onclick="viewMapContact2Outlet()">Back</sj:a>
								      </td>
								      
								      </tr></tbody>
								  </table>
							  </td>
							  <td class="alignright printTitle">
							  </td>
						</tr>
					</tbody>
				</table>
			</div>
</div>
 <div id="viewAssetDiv"></div>
</div>
  
</div>
</html>