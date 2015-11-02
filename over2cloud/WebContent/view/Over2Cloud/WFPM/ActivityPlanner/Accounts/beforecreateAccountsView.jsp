<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
function getMonthName(month) 
{
	var ar = new Array(12);
	ar[0] = "January";
	ar[1] = "February";
	ar[2] = "March";
	ar[3] = "April";
	ar[4] = "May";
	ar[5] = "June";
	ar[6] = "July";
	ar[7] = "August";
	ar[8] = "September";
	ar[9] = "October";
	ar[10] = "November";
	ar[11] = "December";

	return ar[month];
}
function addPayment()
{
	var id   = jQuery("#accounts").jqGrid('getGridParam', 'selrow');

    var name =jQuery("#accounts").jqGrid('getCell',id,'mappedEmpName');
	var for_month = jQuery("#accounts").jqGrid('getCell',id,'month');
	var bal = jQuery("#accounts").jqGrid('getCell',id,'balance');
	var by = jQuery("#accounts").jqGrid('getCell',id,'autoby');
	var date = jQuery("#accounts").jqGrid('getCell',id,'autodate');
	
	
	//alert(id);
	if(id!=null)
	{
		if(id.split(",").length==1)
		{
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeAccountsPage.action?emp_id="+id+"&name="+name+"&for_month="+ getMonthName(for_month.split("-")[1])+"-"+for_month.split("-")[2]+"&bal="+bal+"&by="+by+"&date="+date,
			    success : function(subdeptdata) 
			   {
					$("#"+"data_part").html(subdeptdata);
			   },
			   error: function() 
			   {
		           alert("error");
		      }
			 });
		}
		else
		{
			alert('Please select one checkbox.');
		}
	}
	else
	{
		alert('Please select one checkbox.');
	}
}


function breakUpTotal(cellvalue, options, row) 
{
    return "&nbsp;<a href='#' style='cursor: pointer; color:blue;' onClick='breakTotal("+row.id+");'><font color='blue'>"+cellvalue+"</font></a>&nbsp;";
}
function breakTotal(id)
{
    var for_month = jQuery("#accounts").jqGrid('getCell',id,'month');
    var name =jQuery("#accounts").jqGrid('getCell',id,'mappedEmpName');
    $('#abc').dialog('open');
	$('#abc').dialog({title: 'Break Total for '+name,height: '150',width:'350',position:'center'});
    $("#activity").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/breaktotalReimbursement.action?emp_id="+id+"&month="+for_month,
	    async:false,
	    success : function(subdeptdata) 
	    {
		    $("#"+"activity").html(subdeptdata);
	    },
	   error: function() {
	            alert("error");
	        }
	 });
}
loadingFunction();
function loadingFunction()
{
	//alert(" KARNIKA ");
	var fdate=$("#from_date").val();
	var tdate=$("#to_date").val();
	var emp=$("#emp_id").val();
	$.ajax
	({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeAccountsViewMain.action?fdate="+fdate+"&tdate="+tdate+"&emp_id="+emp,
	    async:false,
	    success : function(subdeptdata) 
	    {
		    $("#"+"result").html(subdeptdata);
	    },
	    error: function()
	    {
	        alert("error");
	    }
	 });
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Reimbursement</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div>  
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr>
	<tr><td></td></tr>
	<tr>
	    <td>
		    <table class="floatL" border="0" cellpadding="0" cellspacing="0">
		    <tbody><tr><td class="pL10">
		    From:  <sj:datepicker id="from_date" cssStyle="width: 20%;"  value="%{fdate}"  readonly="true" onchange="loadingFunction();" cssClass="button" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Data"/>
		    To:  <sj:datepicker id="to_date"  cssStyle="width: 20%;" value="today" onchange="loadingFunction();" readonly="true" cssClass="button" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Data"/>
		     <s:select 
                     id="emp_id"
                     name="emp_id" 
                     list="commonMap"
                     headerKey="-1"
                     headerValue="Select Employee"
                     onchange="loadingFunction();"
                     cssClass="button"
                     cssStyle="margin-left: -114px;float: left;width: 230%;margin-top: -25px;height: 60%;"
                    >
            </s:select>  
		    </td></tr></tbody>
		    </table>
	    </td>
	        <td class="alignright printTitle">
	        	<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addPayment();">Payment</sj:a>
	    </td>
	</tr>
	</tbody>
	</table>
	</div>
	</div>
<div class="clear"></div>
<div style="overflow: scroll; height: 430px;">
<div id="result"></div>
</div></div>
<sj:dialog
          id="abc"
          showEffect="slide" 
          autoOpen="false"
          title="Create Activity"
          modal="true"
          width="1200"
          position="center"
          height="450"
          draggable="true"
    	  resizable="true"
          >
        <center>  <div id="activity"></div> </center>
</sj:dialog>
</div>
</body>
</html>