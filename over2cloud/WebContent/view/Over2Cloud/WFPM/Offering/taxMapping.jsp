<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1',function(event,data)
		{
			$('select').find('option:first').attr('selected', 'selected');
		});

/*function level1()
{
	alert("aaaaaaaaaaaaaaaaaaa");
	$('select').find('option:first').attr('selected', 'selected');

	
}*/
$.subscribe('validate', function(event,data)
        {

			 //alert("validatebasic");
    		 if(document.formone.clientName.value!=null && document.formone.clientName.value=="" || document.formone.clientName.value=="-1")
             {
             	clearAllErroDiv();
             	errorclientName.innerHTML="Error: Fill data in field";
             	event.originalEvent.options.submit = false;
             	return false;
             }
             else if(document.formone.address.value!=null && document.formone.address.value=="" || document.formone.address.value=="-1")
             {
             	clearAllErroDiv();
             	erroraddress.innerHTML="Error: Fill data in field";
             	event.originalEvent.options.submit = false;
             	return false;
             }
             else if(document.formone.phoneNo.value!=null && document.formone.phoneNo.value=="" || document.formone.phoneNo.value=="-1")
             {
             	clearAllErroDiv();
             	errorphoneNo.innerHTML="Error: Fill data in field";
             	event.originalEvent.options.submit = false;
             	return false;
             }
             else if(document.formone.companyEmail.value!=null && document.formone.companyEmail.value=="" || document.formone.companyEmail.value=="-1")
             {
             	clearAllErroDiv();
             	errorcompanyEmail.innerHTML="Error: Fill data in field";
             	event.originalEvent.options.submit = false;
             	return false;
             }
             else if(document.formone.status.value!=null && document.formone.status.value=="" || document.formone.status.value=="-1")
            {
            	clearAllErroDiv();
            	errorstatus.innerHTML="Error: Select data in field";
            	event.originalEvent.options.submit = false;
            	return false;
            }
            else if(document.formone.acManager.value!=null && document.formone.acManager.value=="" || document.formone.acManager.value=="-1")
            {
            	clearAllErroDiv();
            	erroracManager.innerHTML="Error: Select data in field";
            	event.originalEvent.options.submit = false;
            	return false;
            }
           
    		clearAllErroDiv();

        });

function clearAllErroDiv()
{
	$("div[id^='error']").each(function() {
  	  // `this` now refers to the current element
	     this.innerHTML="";
  	});
}

function fetchLevelData(val,selectId,Offeringlevel)
{
	//alert(val.value);
	//alert(Offeringlevel);
	
	$.ajax({
	    type : "post",
	    url : "<%=request.getContextPath()%>/view/Over2Cloud/wfpm/client/fetchOfferingLevelData.action?offeringId="+val.value+"&Offeringlevel="+Offeringlevel,
	    success : function(data) {
		    
		$('#'+selectId+' option').remove();
		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
    	$(data).each(function(index)
		{
    		//alert(this.ID +" "+ this.NAME);
		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		});
		
	},
	   error: function() {
            alert("error");
        }
	 });
}


function fillName(val,divId,param)
{
	/*
		param is either 1 or 0
		1: for fetching name on both key and value place
		0: for fetching id on key and name on value place
	*/
	//in case of param 1 fetch dropdown both parameter as name only otherwise set it as id and name  
	var id = val;
	if(id == null || id == '-1')
		return;
	//alert(id);

	$.ajax({
	    type : "post",
	    url : "<%=request.getContextPath()%>/view/Over2Cloud/wfpm/client/fetchReferredName.action",
		data: "id="+id+"&param="+param, 
		success : function(data) {   
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text('Select'));
		$(data).each(function(index)
		{
		   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		});
	},
	   error: function() {
            alert("error");
        }
	 });
}

function fetchTax(offeringId)
{
	var id = offeringId.value;
	if(id != -1 && id != "")
	{
		//alert(id);
		$("#errorofferingId").html("");
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/wfpm/offering/fetchTaxForOffering.action",
		    data: "id="+id,
		    success : function(data) {
	       $("#"+"divTaxContent").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		
	} 
}
$.subscribe('checkDDN', function(event,data)
		{
			var value = $("#offeringId").val();
			var b = $(":checked");
			alert(b.length);
			if(value == null || value == '' || value == '-1')
			{
				$("#errorofferingId").html("Error: Select data in field");
				event.originalEvent.options.submit = false;
				return;
			}
			
			if(b.length < 2)
			{
				if(!confirm("If mapped earlier, all mapping will be deleted !\nAre you sure you want to delete !"))
					event.originalEvent.options.submit = false;
			}
			
		});
</SCRIPT>

</head>
<body>
<div class="page_title"><h1>Tax Mapping With Offering</h1>
<span class="needed_block">= indicates mandatory fields</span>
</div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">

<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Tax Mapping" id="oneId">  
 <s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/offering" action="addTaxMapping" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
 <div class="form_menubox">
 <div class="inputmain">
		<div class="user_form_text">Offering:</div>
		<div class="user_form_input inputarea"><span class="needed"/>
			<s:select 
			id="offeringId"
			name="offeringId" 
			list="offeringList"
			headerKey="-1"
			headerValue="Select" 
			cssClass="form_menu_inputselect"
			onchange="fetchTax(this)"
			>
			</s:select>
			<div id="errorofferingId" class="errordiv"></div>
		</div>
		</div>
 </div>
 
<br>

<div id="divTaxContent">
               
</div>             
               
  </s:form>             
</sj:accordionItem>
</sj:accordion>
</div></div>

<script type="text/javascript">
function checkMe(val)
{
	alert(val.id+"  "+val.name+"  "+val.value);	
}
</script>
</body>
</html>
