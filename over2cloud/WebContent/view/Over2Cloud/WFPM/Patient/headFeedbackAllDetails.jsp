<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
	
function getOnChangePrimaryData()
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/patientActivity/viewSubmittedData.action",
	    data:"date="+$('#tdate').val()+"&pdate="+$('#sdate').val()+"&pid="+$('#spatien_id').val(),
	    success : function(subdeptdata) {
       $("#result_part").html(subdeptdata);
              
	    },
	   error: function() {
            alert("error");
        }
	 });
}
	
	function onload(){
		//alert("onload()");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/patientActivity/viewSubmittedData.action",
		    success : function(subdeptdata) {
		    $("#result_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	       $("#result_part").html(subdeptdata);
				
		    },
		   error: function() {
	            alert("error");
	        }
		 });
	}
	onload();
</script>
</head>

<body>
<div class="clear"></div>
<div class="middle-content">

<div class="list-icon">
	 <div class="head">Feedback Submitted History </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View
</div> 
</div> 

<!-- Code For Header Strip -->
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 96%;margin-left: 22px;" align="center">

<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					 <td class="pL10"><b>Date : </b>
					<sj:datepicker id="sdate" 
						title="Date of Feedback Submitted"
						cssClass             =      "button"
							       cssStyle             =      "margin-top: -32px;margin-left:3px; width :15%; "
							      theme                 =       "simple"  
					readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date" 
					onchange="getOnChangePrimaryData()" value="today"/>
					&nbsp; &nbsp;
					<b>To Date : </b>
					<sj:datepicker id="tdate" title="To Feedback Submitted" cssClass =  "button"
							       cssStyle             =      "margin-top: -32px;margin-left:3px; width :15%;"  
							      theme                 =       "simple"  
					readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date" 
					onchange="getOnChangePrimaryData()" value="today"/>
					&nbsp; &nbsp;  <b>Patient Id : </b>						
					<s:textfield name="spatien_id"  title="Patient Id" id="spatien_id" theme="simple" onblur="getOnChangePrimaryData();" cssClass="textField" placeholder="Patient ID" style="margin: 0px 0px 9px; width: 20%;"/>
					
					
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

 	<div id="result_part"></div>
 </div>
 </div>
</body>

</html>