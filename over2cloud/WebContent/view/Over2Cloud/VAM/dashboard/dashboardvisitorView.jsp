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
	<title>Visitor MIS View</title>
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
		jQuery("#dashboardVistorId").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		if(row==0)
		{
			alert("Please, Select At Least One Row.");
			return;
		}
		$("#dashboardVistorId").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:false,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		jQuery("#dashboardVistorId").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) 
	{
		  $("#dashboardVistorId").trigger("reloadGrid"); 
	}
</script>
	<script type="text/javascript">
		function exitDashboardVisitor()
		 {
		 	//var srnumber = $("#srnumberid").text();
		 	var srnumber = document.getElementById("srnumberid").value;
		 	alert("srnumber"+srnumber);
		 	$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/VAM/master/visitorexitdashboard.action?modifyFlag=0&deleteFlag=0&srnumber="+srnumber,
					    success : function(subdeptdata) {alert("success");
				        $("#visitorshowid").html(subdeptdata);
		    			 $("#visitorshowid").dialog('open');
					},
					   error: function() {
				            alert("error<>");
				        }
					 });
		 }
			   
	</script>
	</head>
	<body>
	<sj:dialog 
    	id="visitorshowidaaaaaa" 
    	autoOpen="false" 
    	modal="true" 
    	width="750"
		height="180"
    	resizable="false"
    	title="View"
    >
	</sj:dialog>
		<div style="height:auto; margin-bottom:10px;" ><B>Current Visitor In Status</B></div>
		<table border="" width="100%" align="center" style="border-style:dotted solid;" id="exitvisitorid">

				  <tr>
	                   <td valign="bottom">
	                       <table align="center" border="1" style="border-style:dotted solid;" width="100%">
	                        	  <tr bgcolor="#808000">
	                        	  		<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Sr No</b></font></td>
	                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Visitor Name</b></font></td>
		                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Visitor Mobile</b></font></td>
		                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Visitor Organization</b></font></td>
		                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Purpose Of Meeting</b></font></td>
		                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Visited Person</b></font></td>
		                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Visited Mobile</b></font></td>
		                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>For Department</b></font></td>
	                        	  </tr>
	                       </table>
	                    </td>
                 </tr>
				 <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                       <marquee scrollamount="1" scrolldelay="0" direction="up" onmouseover="this.setAttribute('scrollamount', 0, 0);;" onmouseout="this.setAttribute('scrollamount', 2, 0);">
                       <table align="center" border="0" style="border-style:dotted solid;" width="100%">
                       
                        	  <s:iterator id="dashboardvisitorData"  value="dashboardvisitorData" var = "varvisitorlist"> 
                        	  
                        	  	  
								<tr bordercolor="#ffffff">
                        	    <s:iterator value = "%{#varvisitorlist}">
                        	    <s:if test="%{key == 'sr_number'}">
                        	    <td align="center" id="testid" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" id="srnumberid" href="#" onclick="exitDashboardVisitor();"><b><s:property value="%{value}"/> </b></a></font></td>
                        	    </s:if>
                        	    <s:else>
                        	     <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{value}"/> </b></a></font></td>
                        	    </s:else>
                        		
                                </s:iterator>
                                </tr>
                                
                        	   </s:iterator>
                       </table>
                       </marquee>
                   </td>
               </tr>
</table>
	</body>
	</html>