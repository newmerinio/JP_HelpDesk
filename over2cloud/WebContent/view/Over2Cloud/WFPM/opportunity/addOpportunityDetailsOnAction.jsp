<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Opportunity On Action</title>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">

		$.subscribe('addactionform', function(event,data)
	        {
	          setTimeout(function(){ $("#addactionsave").fadeIn(); }, 10);
	          setTimeout(function(){ $("#addactionsave").fadeOut(); }, 4000);
	         
        });
</script>
	<script type="text/javascript">
		function resetForm(formtwo)
		{
			$('#'+formtwo).trigger("reset");
		}
	</script>
	<script type="text/javascript">
	function viewClient()
	{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeClientView.action",
	    data : "isExistingClient=${isExistingClient}",
	    success : function(subdeptdata) {
	   $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	}
</script>

</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Add Opportunity Data</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">For Converted Lead</div>
	</div>
	
	<div class="clear"></div>
	<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:10px 1%; width:98%;">

	<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/wfpm/client" action="addOpportintyDetails" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	
		<s:hidden name="id" value="%{id}" id="id" /> 
		<s:hidden name="clientName" value="%{clientName}" id="clientName"/> 
		<s:hidden name="offeringId" value="%{offeringId}" id="offeringId" /> 
	
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Brief:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="opportunity_name"  id="opportunity_name"  cssClass="textField" placeholder="Enter Data" theme="simple"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Expected Value:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="opportunity_value"  id="opportunity_value"  cssClass="textField" placeholder="Enter Data" theme="simple"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Expected Date:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<sj:datepicker id="closure_date" name="closure_date" cssClass="textField" size="20" value="today" readonly="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Expected Date"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Remarks:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<s:textfield name="comments"  id="comments" cssClass="textField" placeholder="Enter Data" theme="simple"/>
	</div>
	</div>
		
		<div class="clear"></div>
	<!-- Buttons -->
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;margin-top: 3%;">
         
          <sj:submit 
	           		   targets="opportunityAdd" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="addactionform"
                       cssClass="submit"
                       indicator="indicator3"
                       onBeforeTopics="validate2"
	          />
           	<sj:a 
	     	    name="Reset"  
				href="#" 
				cssClass="submit" 
				indicator="indicator" 
				button="true" 
				onclick="resetForm('formtwo');"
				cssStyle="margin-left: 193px;margin-top: -43px;"
				>
				  	Reset
				</sj:a>
          <sj:a 
	     	name="Cancel"  
			href="#" 
			cssClass="submit" 
			indicator="indicator" 
			button="true" 
			cssStyle="margin-left: 145px; margin-top: -25px;"
			onclick="viewClient();"
			cssStyle="margin-top: -43px;"
			
			>
	  		Back
		</sj:a>
		
				<sj:div id="addactionsave"  effect="fold">
                    	<div id="opportunityAdd"></div>
               	</sj:div>
	    </div>
		</s:form>
		</div>
		</div>
		</div>
</body>
</html>
