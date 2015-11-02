<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />

<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>	
<script type="text/javascript">
$.subscribe('validate', function(event,data)
		{	
			validate(event,data,"pIds");
		});

$.subscribe('validateAllocateBudget', function(event,data)
		{	
			validateAllocate(event,data,"pIds1");
		});


function getEmployeeName(deptid,div){
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/viewEmployeeName.action?deptid='"+deptid,
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select'));
	$(data).each(function(index)
	{
		
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
	 
}



function validateAllocate(event,data, spanClass)
{
	var mystring=$("."+spanClass).text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    var pattern2 = /^\d+$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
        var validationType = fieldtype[i].split("#")[3];
        $("#"+fieldsvalues).css("background-color","");
        errZone1.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D"){
            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
            {
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }
            }
            else if(colType =="T"){
            if(validationType=="n"){
            var numeric= /^[0-9]+$/;
            if(!(numeric.test($("#"+fieldsvalues).val()))){
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }   
             }
            else if(validationType=="an"){
             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="ans"){
            var allphanumeric = /^[ A-Za-z0-9_@.#&+-]*$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="a"){
            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
             }
            else if(validationType=="m"){
           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
            {
                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
            }
            else if (!pattern.test($("#"+fieldsvalues).val())) {
                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
             }
                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
             {
                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
               }
             }
             else if(validationType =="e"){
             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
             }else{
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            $("#"+fieldsvalues).focus();
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            event.originalEvent.options.submit = false;
            break;
               }
             }
             else if(validationType =="num"){

                 if($("#"+fieldsvalues).val().length == 0 || $("#"+fieldsvalues).val().length > 11)
                  {
                      errZone.innerHTML="<div class='user_form_inputError2'>Enter atmost 10 digit number ! </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                  }
                  else if (!pattern2.test($("#"+fieldsvalues).val())) {
                      errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                   }
             }
           }
          
            else if(colType=="TextArea"){
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
               }
             }
            else if(validationType =="td"){
           	 if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
                {
                errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                $("#"+fieldsvalues).focus();
                $("#"+fieldsvalues).css("background-color","#ff701a");
                event.originalEvent.options.submit = false;
                break;   
                  }
            }   
            }
            else if(colType=="Time"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }   
            }
            else if(colType=="Date"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }
             } 
        }
    }      
}



function validate(event,data, spanClass)
{
	var mystring=$("."+spanClass).text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    var pattern2 = /^\d+$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
        var validationType = fieldtype[i].split("#")[3];
        $("#"+fieldsvalues).css("background-color","");
        errZone.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D"){
            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }
            }
            else if(colType =="T"){
            if(validationType=="n"){
            var numeric= /^[0-9]+$/;
            if(!(numeric.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }   
             }
            else if(validationType=="an"){
             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="ans"){
            var allphanumeric = /^[ A-Za-z0-9_@.#&+-]*$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="a"){
            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
             }
            else if(validationType=="m"){
           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
            {
                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
            }
            else if (!pattern.test($("#"+fieldsvalues).val())) {
                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
             }
                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
             {
                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
               }
             }
             else if(validationType =="e"){
             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
             }else{
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            $("#"+fieldsvalues).focus();
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            event.originalEvent.options.submit = false;
            break;
               }
             }
             else if(validationType =="num"){

                 if($("#"+fieldsvalues).val().length == 0 || $("#"+fieldsvalues).val().length > 11)
                  {
                      errZone.innerHTML="<div class='user_form_inputError2'>Enter atmost 10 digit number ! </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                  }
                  else if (!pattern2.test($("#"+fieldsvalues).val())) {
                      errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                   }
             }
           }
          
            else if(colType=="TextArea"){
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
               }
             }
            else if(validationType =="td"){
           	 if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
                {
                errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                $("#"+fieldsvalues).focus();
                $("#"+fieldsvalues).css("background-color","#ff701a");
                event.originalEvent.options.submit = false;
                break;   
                  }
            }   
            }
            else if(colType=="Time"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }   
            }
            else if(colType=="Date"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }
             } 
        }
    }      
}


</script>

<SCRIPT type="text/javascript">
	$.subscribe('level1', function(event, data) {
		$("#addDialog").dialog('open');
		setTimeout(function() {closeAdd() ;}, 4000);
		//reset("formone");
		//getSelectedMonths('for_month');
		
	});
</script>

<SCRIPT type="text/javascript">
	$.subscribe('level2', function(event, data) {
		$("#addDialog").dialog('open');
		setTimeout(function() {closeAdd1() ;}, 4000);
	});
</script>

<script type="text/javascript">
	function closeAdd() {
		$("#addDialog").dialog('close');
		budget_planning();
	}
	function closeAdd1() {
		$("#addDialog").dialog('close');
		budget_planning();
	}
	
</script>
<script type="text/javascript">


function getSelectedMonths(div)
{

	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/viewForMonth.action",
	    success : function(data) {
	    	$('#'+div+' option').remove();
	    	
	    	
	    	
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select'));
	
	$(data).each(function(index)
	{	
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	   
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}


function checkavailbudget()
{
	
	var ac=$("#budget_value").val();
	var avilbudget=parseInt(ac);
	var b;
	var value=$("#month_value").val();
	var sum=value;
	for(i=100; i<110;i++)
	{
		b=$("#month_value"+i).val();
		if(b!='' && b!='undefined')
		{
			sum=parseInt(sum)+parseInt(b);
		}
		
	}
	
	if(avilbudget<sum)
	{
		$("#UploadDialog").dialog('open');
		
		$("#month_value").val('');
		
	}
	
}
function  totalForMonth(value,div)
{	
	var ac=$("#for_month").val();
	var total=ac;
	for(i=200; i<210;i++)
	{
		b=$("#for_month"+i).val();
		if(b!='-1' && b!='undefined')
		{
			total=total+"-"+b;
		}
		
	}
	
		$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/removeForMonth.action?removeMonth="+total,
	    success : function(data) {
	    	//$('#for_month200'+'  option').remove();
	    	$('#'+div+' option').remove();
	    	
	//$('#for_month200').append($("<option></option>").attr("value",-1).text('Select'));
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select'));
	
	$(data).each(function(index)
	{	
	   //$('#for_month200').append($("<option></option>").attr("value",this.ID).text(this.NAME));
		$('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function checkTotalAmount()
{
	var value=$("#for_month").val();
	var empId=$("#emp").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/gettingMonthSum.action?forMonth='"+value+"&empId="+empId,
	    success : function(data) {
	
	$(data).each(function(index)
	{	
		$('#forMonthSumm').val(this.ID);
		//alert("monthsum11  "+this.ID);
		var monthsum=$("#forMonthSumm").val();
		//alert("monthsum  "+monthsum);
		var avilbudget=parseInt(monthsum);
		var amount=$("#amount").val();
		//alert("amount  "+amount);
		var avilamount=parseInt(amount);
		
		if(avilbudget<avilamount)
		{
			$("#UploadDialog").dialog('open');
			
			$("#amount").val('');
			
		}
		
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
 
}


function checkTotalAmount1(count)
{
	//alert(count);
	var value=$('#for_month'+count).val();
	var empId=$("#emp").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/patientCRMMaster/gettingMonthSum.action?forMonth='"+value+"&empId="+empId,
	    success : function(data) {
	
	$(data).each(function(index)
	{	
		$('#forMonthSumm').val(this.ID);
		//alert("monthsum11  "+this.ID);
		var monthsum=$("#forMonthSumm").val();
		//alert("monthsum  "+monthsum);
		var avilbudget=parseInt(monthsum);
		var amount=$('#amount'+count).val();
		//alert("amount  "+amount);
		var avilamount=parseInt(amount);
		
		if(avilbudget<avilamount)
		{
			$("#UploadDialog").dialog('open');
			
			$('#amount'+count).val('');
			
		}
		
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
 
}







	function reset(formId) {
		$("#" + formId).trigger("reset");
	}
</script>
</head>
<body>
	<div class="clear"></div>
	<sj:dialog id="addDialog"
		buttons="{'Close':function() { closeAdd(); },}" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Status Message" openTopics="openEffectDialog"
		closeTopics="closeEffectDialog" width="350" height="150">
		<sj:div id="level123"></sj:div>
	</sj:dialog>
	
	
	
	<sj:dialog
                  id="UploadDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Message"
                  width="300"
                  height="100"
                  showEffect="slide"
                  hideEffect="explode">
                  
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveData"></div>
                  </sj:div>
                  <div id="dataDiv">Your Month value is greater than your budget value.</div>
        	</sj:dialog>
	
	

	<div class="middle-content">

		<div class="list-icon">
			<div class="head">Budget Planning</div>
			<div class="head">
				<img alt="" src="images/forward.png"
					style="margin-top: 50%; float: left;">
			</div>
			<div class="head">Add</div>
		</div>
		<div class="clear"></div>
		
		
		<sj:accordion id="accordion" autoHeight="false"> 
			<sj:accordionItem title="Budget Planning" id="oneId"> 
			
				
					<div class="border">
			<div class="container_block">
				<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone" name="formone"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addBudgetPlanning" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: center; margin-left: 7px"></div>
							</div>
						</center>

				<s:iterator value="dataDropDown" status="status">
				 <s:if test="%{key == 'plan_budget_for'}">
						  <s:if test="%{mandatory}">
		     				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             			</s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       
				    
				            
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="%{financialYear}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				        
				  
				    </div>
			        </div>
			      </s:if>
			        
			</s:iterator>
			
			
			<s:iterator value="dataTextBox" status="status">
				<s:if test="%{key == 'budget_value'}">
				<s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:if>
             	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				           
				         	<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				        
				    </div>
			        </div>
			        
			     </s:if>            	
             	
			</s:iterator>
			
			
			
			
			<s:iterator value="dataDropDown" status="status">
				 <s:if test="%{key == 'month_wise_breakup'}">
						  <s:if test="%{mandatory}">
		     				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             			</s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       
				    
				            
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="#{'April':'April','May':'May','June':'June','July':'July','August':'August','September':'September','October':'October','November':'November','December':'December','January':'January','February':'February','March':'March'}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				        
				  
				    </div>
			        </div>
			      </s:if>
			        
			</s:iterator>
			
			
			<s:iterator value="dataTextBox" status="status">
				<s:if test="%{key == 'month_value'}">
				<s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:if>
             	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				           
				         	<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" onchange="checkavailbudget();" placeholder="Enter Data"/>
				         	<sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle=" margin-left: 352px; margin-top: -30px;">+</sj:a>
				        
				    </div>
			        </div>
			        
			     </s:if>            	
             	
			</s:iterator>
			
			
			
			
						
						
	<div id="extraDiv">
         <s:iterator begin="100" end="109" var="typeIndx" >
            <div class="clear"></div>
              <div id="<s:property value="%{#typeIndx}"/>" style="display: none ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto; "  class="container_block" >
             <s:iterator value="dataDropDown" status="counter">
             		
             		
				 <s:if test="%{key == 'month_wise_breakup'}">
						  <s:if test="%{mandatory}">
		     				<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             			</s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       
				    
				            
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="#{'April':'April','May':'May','June':'June','July':'July','August':'August','September':'September','October':'October','November':'November','December':'December','January':'January','February':'February','March':'March'}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				        
				  
				    </div>
			        </div>
			      </s:if>
			        
			                
                            
             </s:iterator>
             
             <s:iterator value="dataTextBox" status="counter" begin="1">
				<s:if test="%{key == 'month_value'}">
				<s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:if>
             	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				           
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" onchange="checkavailbudget();"  placeholder="Enter Data"/>
				         	<div style="">
                            <s:if test="#typeIndx==110">
                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#typeIndx}')" button="true"> - </sj:a></div>
                            </s:if>
                               <s:else>
                                   <div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#typeIndx+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#typeIndx}')" button="true"> - </sj:a></div>
                               </s:else>
                          </div>
				        
				    </div>
			        </div>
			        
			     </s:if>    
			    
			</s:iterator>
             
             
         </div>
         </s:iterator>
 </div>		
						
						
						
						
							
					<!-- Buttons -->
						<center>
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
						</center>

						<div class="clear">
							<div style="padding-bottom: 10px; margin-left: -76px"
								align="center">
								<sj:submit targets="level123" clearForm="true" value="Save"
									effect="highlight" effectOptions="{ color : '#222222'}"
									effectDuration="5000" button="true" onBeforeTopics="validate"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="budget_planning();"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
			
				
			</sj:accordionItem>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
<sj:accordionItem title="Allocate Budget" id="twoId">

			<div class="newColumn" >
				<div class="leftColumn1">Available Sum:</div>
				<div class="rightColumn1">
					<s:textfield id="forMonthSumm"></s:textfield>
				</div>
					
			</div>
			
					<div class="border">
			<div class="container_block">
				<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formtwo" name="formtwo"
						namespace="/view/Over2Cloud/patientCRMMaster"
						action="addAllocateBudgetData" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone1" style="float: center; margin-left: 7px"></div>
							</div>
						</center>

				<s:iterator value="dataDropDown1" status="status">
				 <s:if test="%{key == 'dept'}">
						  <s:if test="%{mandatory}">
		     				<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             			</s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       
				    
				            
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="%{departmentData}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="getEmployeeName(this.value,'emp');"
                                      >
                          </s:select>
				        
				  
				    </div>
			        </div>
			      </s:if>
			      
			      
			      <s:if test="%{key == 'emp'}">
						  <s:if test="%{mandatory}">
		     				<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             			</s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="{}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      
                                      >
                          </s:select>
				        
				  
				    </div>
			        </div>
			      </s:if>
			      
			      
			      <s:if test="%{key == 'for_month'}">
						  <s:if test="%{mandatory}">
		     				<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             			</s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       
				    
				            
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="%{monthList}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="totalForMonth(this.value,'for_month200');"
                                      >
                          </s:select>
				        
				  
				    </div>
			        </div>
			      </s:if>
			      
			      
			        
			</s:iterator>
			
			
						
			
			
			<s:iterator value="dataTextBox1" status="status">
				<s:if test="%{key == 'amount'}">
				<s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:if>
             	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				           
				         	<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" onchange="checkTotalAmount();" placeholder="Enter Data"/>
				         	<sj:a value="+" onclick="adddiv1('200')" button="true" cssClass="submit" cssStyle=" margin-left: 352px; margin-top: -30px;">+</sj:a>
				        
				    </div>
			        </div>
			        
			     </s:if>            	
             	
			</s:iterator>
			
			
			
			
						
						
	<div id="extraDiv1">
         <s:iterator begin="200" end="209" var="typeIndx1" >
            <div class="clear"></div>
              <div id="<s:property value="%{#typeIndx1}"/>" style="display: none ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto; "  class="container_block" >
             <s:iterator value="dataDropDown1" status="counter">
             		
             		
				 <s:if test="%{key == 'for_month'}">
						  <s:if test="%{mandatory}">
		     				<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             			</s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       
				    
				            
				         		<s:select 
                                      id="%{key}%{#typeIndx1}"
                                      name="%{key}" 
                                      list="%{monthList}"
                                      headerKey="-1"
                                      headerValue="--Select--"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="totalForMonth(this.value,'%{key}%{#typeIndx1+1}');"
                                      >
                          </s:select>
				        
				  
				    </div>
			        </div>
			      </s:if>
			        
			                
                            
             </s:iterator>
             
             
             <s:iterator value="dataTextBox1" status="status">
				<s:if test="%{key == 'amount'}">
				<s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds1" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:if>
             	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				           
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx1}"  maxlength="50" onchange="checkTotalAmount1('%{#typeIndx1}');" cssClass="textField" placeholder="Enter Data"/>
				         	<div style="">
                            <s:if test="#typeIndx1==210">
                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv1('%{#typeIndx1}')" button="true"> - </sj:a></div>
                            </s:if>
                               <s:else>
                                   <div class="user_form_button2"><sj:a value="+"  onclick="adddiv1('%{#typeIndx1+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv1('%{#typeIndx1}')" button="true"> - </sj:a></div>
                               </s:else>
                          </div>
				        
				    </div>
			        </div>
			        
			     </s:if>            	
             	
			</s:iterator>
             
             
             
             
             
         </div>
         </s:iterator>
 </div>		
						
						
						
						
							
					<!-- Buttons -->
						<center>
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
						</center>

						<div class="clear">
							<div style="padding-bottom: 10px; margin-left: -76px"
								align="center">
								<sj:submit targets="level123" clearForm="true" value="Save"
									effect="highlight" effectOptions="{ color : '#222222'}"
									effectDuration="5000" button="true" onBeforeTopics="validateAllocateBudget"
									onCompleteTopics="level2" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="budget_planning();"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
			 
				
			</sj:accordionItem>
			
			
		</sj:accordion>
		
		
		
		
	</div>

</body>
</html>
