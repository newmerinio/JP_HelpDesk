<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript"><!--
function addCorporate(){
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/WFPM/Patient/beforeAddCorporate.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getGridView(param, searchField, searchString, searchOper)
{
	var from_date=$("#from_date").val();
	var to_date=$("#to_date").val();
	var data_status=$("#data_status").val();
	var corp_type=$("#corp_type").val();
	var packages=$("#packages").val();
	var searchParameter=$("#searchParameter").val();
	var action_status=$("#action_status").val();
	$.ajax({
 		type :"post",
 		url :"/over2cloud/view/Over2Cloud/WFPM/Patient/viewCorporateDetails.action?searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper+"&from_date="+from_date+"&to_date="+to_date+"&searchParameter="+searchParameter+"&data_status="+data_status+"&corp_type="+corp_type+"&packages="+packages+"&action_status="+action_status,
 		success : function(data) 
	    {
			$("#middleDiv").html(data);
		},
	    error: function()
	    {
	        alert("error viewLinsence");
	    }
 	}); 	
}

function getCorp(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='CorporateProfile("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>&nbsp;";
}

function CorporateProfile(id) 
{
	var org_name = jQuery("#gridCorporate").jqGrid('getCell',id, 'corp_name');
	   var rating = jQuery("#gridCorporate").jqGrid('getCell',id, 'packages');
	if(rating='Gold')
	{
		rating=3;
	}
	else if(rating='Silver')
	{
		rating=2;
	}
	else
	{
		rating=1;
	}
	
	var path="";
	for(var i=1;i<=rating;i++)
	{
		path = path+"<img alt='star' src='images/WFPM/commonDashboard/star.jpg'/>";
	}
	
	$("#contactPersonDetailsDialog").dialog('open');
	$("#contactPersonDetailsDialog").dialog();
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Patient/corpBasicDetails.action",
		data: "id="+id, 
		success : function(data) {  
		     $("#contactPersonDetailsDialog").dialog({title: 'Basic Details For '+org_name+', '+id+' '+path, width:'600', height:'180'});
			$("#contactPersonDetailsDialog").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function getStatus(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='InstiStatus("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>&nbsp;";
}

function InstiStatus(id) 
{
	var org_name = jQuery("#gridCorporate").jqGrid('getCell',id, 'corp_name');
    var rating = jQuery("#gridCorporate").jqGrid('getCell',id, 'packages');
	if(rating='Gold')
	{
		rating=3;
	}
	else if(rating='Silver')
	{
		rating=2;
	}
	else
	{
		rating=1;
	}
	
	var path="";
	for(var i=1;i<=rating;i++)
	{
		path = path+"<img alt='star' src='images/WFPM/commonDashboard/star.jpg'/>";
	}
	
	$("#contactPersonDetailsDialog").dialog('open');
	$("#contactPersonDetailsDialog").dialog();
	$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/wfpmReferral/commstatus.action?tabel=corporate_master",
		data: "id="+id, 
		success : function(data) {  
		     $("#contactPersonDetailsDialog").dialog({title: 'Status For '+org_name+', '+id+' '+path, width:'700', height:'250'});
			$("#contactPersonDetailsDialog").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}

function action()
{
	var action_on = jQuery("#gridCorporate").jqGrid('getCell',row, 'action_on');

	//alert("Hello");
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else if(row.length>1)
	{
		alert("Please select only one row to edit.");
	}
	else
	{
		if(action_on=='NA')
		{
		var org_name = jQuery("#gridCorporate").jqGrid('getCell',row, 'corp_name');

		$("#contactPersonDetailsDialog").dialog('open');
		$("#contactPersonDetailsDialog").dialog();
		$("#contactPersonDetailsDialog").html("<center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeStatusAction.action?tabel=corporate_master",
			data: "id="+row, 
			success : function(data) {  
     		    $("#contactPersonDetailsDialog").dialog({title: 'Take Action For '+org_name+', '+row , width:'700', height:'250'});
				$("#contactPersonDetailsDialog").html(data);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else{
			alert("Action Has Been Already Taken !!!");
		}
	}
}



function fillAlphabeticalLinks()
{
	param = $("#selectstatus").val();
	var val = "";
	val += '&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'\',\'\',\'\')">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	for(var i=65; i<=90; i++)
	{
		val += '&nbsp;&nbsp;&nbsp;<a href="#" onclick="getGridView(\''+param+'\',\'corp_name\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
	//alert("val:"+val);
}

fillAlphabeticalLinks();
getGridView('','','','');
</script>
</head>

<body >
<div class="list-icon">
	 <div class="head">Corporate</div>
	 <div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View For</div>
	 <div class="head"><img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div>
	<div class="head"><s:select
			id="action_status" 
			name="action_status" 
			list="#{'Approved':'Approved Corporate','Unapproved':'Unapproved Corporate'}" 
            theme="simple"
            cssStyle="height: 26px;margin-top: 4px;"
          onchange="getGridView('','','','')"
			cssClass="button"
            />
	</div> 
</div>
	<div id="alphabeticalLinks" style="margin-left: 18px;margin-bottom: -5px;margin-top: 45px;"></div>
 <div class="clear"></div>
  
    <div class="listviewBorder"  style="margin-top: 8px;width: 98%;margin-left: 11px;" align="center">
   
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr><tr><td></td></tr><tr><td>
	 <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	  <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
   	  <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
 	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
	<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadPage()"></sj:a>
	From Date:<sj:datepicker cssStyle="height: 16px; width: 70px; margin-left: 4px;" cssClass="button"  id="from_date"  name="from_date" value="%{from_date}" size="20" maxDate="0"  readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" onchange="getGridView('','','','');" />
  	To Date:<sj:datepicker cssStyle="height: 16px; width: 70px;margin-left: 4px;" cssClass="button" value="today" id="to_date" name="to_date" size="20"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
			<s:select 
		                              id="corp_type"
		                              name="corp_type" 
		                              list="corpTypeMap" 
		                              headerKey="-1"
    								  headerValue="Select Corporate Type" 
    		                          theme="simple"
		                              cssClass="button"
		                              cssStyle="height: 28px; width: 13%;"
		                              onchange="getGridView('','','','');"
		                              >
		             		   </s:select>
		             		 
			<s:select 
		                              id="packages"
		                              name="packages" 
		                              list="{'Gold','Silver','Platinium'}"
		                              headerKey="-1"
    								  headerValue="Select Package Type" 
    		                          theme="simple"
		                              cssClass="button"
		                              cssStyle="height: 28px; width: 15%;"
		                              onchange="getGridView('','','','');"
		                              >
		             		   </s:select>
			<s:select
									id="data_status" 
									name="data_status" 
							   	    list="#{'All Status':'All Status','active':'Active','inactive':'Inactive'}"
									cssStyle="height: 28px; width: 13%;"
									theme="simple"
									cssClass="button"
									onchange="getGridView('','','','');"
							    />
				<s:textfield 
	    	   					 id="searchParameter" 
	            				 name="searchParameter" 
	           					 onkeyup="getGridView('','','','');" 
	           					 theme="simple" cssClass="button" 
	           				     cssStyle="margin-top: -3px;height: 15px;width:8%;margin-left: 4px;"
	          					 Placeholder="Search Any Value" 
	          	                />
	
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<sj:a
				button="true"
				cssClass="button" 
				cssStyle="height:25px;"
                title="Action"
	          onclick="action()"
				>Action</sj:a>   
	<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addCorporate();">Add</sj:a>
    </td>   
	</tr></tbody></table>
	</div>
	</div>
   <%-- "#{'All':'All','Active':'Active','DActive':'InActive'}" --%>
  
<div style="overflow: scroll; height: 430px;">
				<div id="middleDiv"></div>
			</div>
		</div>
<sj:dialog
	id="contactPersonDetailsDialog"
	autoOpen="false"
	modal="true"
></sj:dialog>
</body>
</body>

<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

var selRowIds = $('#gridCorporate').jqGrid('rowselect', 'selarrrow');

function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row to edit.");
	}
	else
	{
		$("#gridCorporate").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
}

function deleteRow()
{
	  var status = $("#gridCorporate").jqGrid('getCell',row,'status');
//	  alert(status);
	if(row==0)
	{
		alert("Please select atleast one row to delete.");
	}
	else if(status=='Inactive')
	{
		alert("The selected data is already delete.");
	}
	else
	{
		$("#gridCorporate").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Delete',msg: "Delete selected record(s)?",bSubmit: "Delete" , afterSubmit: function () {
			reloadPage();
	    }});
		
	}	
}

function searchRow()
{
	 $("#gridCorporate").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function reloadPage()
{
	var grid = $("#gridCorporate");
	grid.trigger("reloadGrid",[{current:true}]);
}


</script>


<%-- 
<script type="text/javascript">
 var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:150, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}

function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
 </script> --%>
</html>