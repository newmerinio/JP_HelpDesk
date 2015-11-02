	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
	<style type="text/css">
	
	.user_form_input{
	    margin-bottom:10px;
	}
	</style>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	<script type="text/javascript">
	$.subscribe('presult', function(event,data)
		       {
		         setTimeout(function(){ $("#purposeaddition").fadeIn(); }, 10);
		         setTimeout(function(){ $("#purposeaddition").fadeOut(); }, 4000);
		       });
	
	</script>
	<script type="text/javascript">
	$.subscribe('validate', function(event,data)
		{	
		var mystring=$(".dIds").text();
		
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
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
		             else if(validationType =="w"){
		            
		            
		            
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
		            else if(validationType=="mg"){
		             
		             
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
		});
	$.subscribe('validate1', function(event,data)
		{	
		var mystring=$(".aIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		       
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
		        $("#"+fieldsvalues).css("background-color","");
		        assErrZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D"){
		            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		            {
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
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
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }   
		             }
		            else if(validationType=="an"){
		             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="a"){
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;    
		              }
		             }
		            else if(validationType=="m"){
		           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		            {
		                assErrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if (!pattern.test($("#"+fieldsvalues).val())) {
		                assErrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		             }
		                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		             {
		                assErrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		               }
		             }
		             else if(validationType =="e"){
		             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
		             }else{
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		            $("#"+fieldsvalues).focus();
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		             else if(validationType =="w"){
		            
		            
		            
		             }
		           }
		          
		            else if(colType=="TextArea"){
		            if(validationType=="an"){
		            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		            else if(validationType=="mg"){
		             
		             
		             }   
		            }
		            else if(colType=="Time"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }   
		            }
		            else if(colType=="Date"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            assErrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
		            setTimeout(function(){ $("#assErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#assErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		             } 
		        }
		    }       
		});
	$.subscribe('validate2', function(event,data)
		{	
		var mystring=$(".dIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		       
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
		        $("#"+fieldsvalues).css("background-color","");
		        form2ErrZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D"){
		            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		            {
		            form2ErrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
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
		            form2ErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }   
		             }
		            else if(validationType=="an"){
		             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            form2ErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="a"){
		            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;    
		              }
		             }
		            else if(validationType=="m"){
		           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		            {
		                form2ErrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if (!pattern.test($("#"+fieldsvalues).val())) {
		                form2ErrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		             }
		                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		             {
		                form2ErrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		               }
		             }
		             else if(validationType =="e"){
		             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
		             }else{
		            form2ErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		            $("#"+fieldsvalues).focus();
		            setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		             else if(validationType =="w"){
		             }
		           }
		          
		            else if(colType=="TextArea"){
		            if(validationType=="an"){
		            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            form2ErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		            else if(validationType=="mg"){
		             
		             
		             }   
		            }
		            else if(colType=="Time"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            form2ErrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
		            setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }   
		            }
		            else if(colType=="Date"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            form2ErrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
		            setTimeout(function(){ $("#form2ErrZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#form2ErrZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		             } 
		        }
		    }       
		});
	</script>
	<script type="text/javascript">
	function getPurposeFields()
	{
		var alertval = $("[name='alertval']:checked").val();
		var purpose_for = $("#purpose_for option:selected").text();
	 $.ajax({
			type : "post",
		    url : "view/Over2Cloud/VAM/master/addPurpose.action?modifyFlag=0&deleteFlag=0"+"&alertval="+alertval+"&purpose_for="+purpose_for,
		    success : function(data) {
	     	  $("#"+"alertdivid").html(data);
			},
			error: function() {
				alert("error");
			}
			}); 
	}
	
	function loadEmp(val,divId,param){
		var purposeaddition = "purposeaddition";
		var id = val;
		if(id == null || id == '-1')
		return;
		$.ajax({
		type : "post",
		url : "view/Over2Cloud/VAM/master/loadEmployeee.action?purposeaddition="+purposeaddition,
		data: "id="+id+"&param="+param, 
		success : function(data) {
			$('#empName option').remove();
			$('#empName').append($("<option></option>").attr("value",-1).text('Select Employee'));
			$(data).each(function(index)
			{
			   $('#empName').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		},
		error: function() {
			alert("error");
		}
		});
	}
	</script>
	<script type="text/javascript">
	function resetForm(formId)
	{
		$('#'+formId).trigger("reset");
	}
	</script>
	</head>
	
	<body>
	<div class="list-icon">
	<div class="head">Purpose</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 0%; width:100%;">
	<div class="clear"></div>
	<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/VAM/master" action="purposeSubmit" theme="css_xhtml"  method="post" >
	<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	  <div id="errZone" style="float:left; margin-left: 7px"></div>        
	</div>
	<div class="clear"></div>
					<!-- Drop Down -->
					<s:iterator value="purposeDropDown" status="counter">
					<s:if test="%{mandatory}">
						<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="purpose_for"/>#<s:property value="Purpose For"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					</s:if>
					</s:iterator>
					<s:if test="%{dropdownforpurpose == true}">
						<div class="newColumn">
						<div class="leftColumn1">Purpose For:</div>
						<div class="rightColumn1">
						<s:if test="%{dropdownforpurpose == true}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="purpose_for"
						                     name="purpose_for" 
						                     list="#{'Visitor':'Visitor', 'Vehicle':'Vehicle'}"
						                     headerKey="-1"
						                     headerValue="Select Purpose For" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
						<div class="newColumn">
			                 				<div class="leftColumn1">Purpose:</div>
											<div class="rightColumn1">
											<span id="mandatoryFields" class="dIds" style="display: none;"><s:property value="purpose" />#<s:property value="Purpose" />#T#a,</span>
											<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
		                 					<s:textfield name="purpose" id="purpose" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
											</div>
										</div>
         			 	<div class="newColumn">
			              <div class="leftColumn1">Alert:</div>
						  <div class="rightColumn1">
						     <s:radio  name="alertval" id="alertvalid" list="#{'1':'Yes','2':'No'}" value="2" onclick="getPurposeFields();"/>
						</div>
					 	</div>
         			</s:if>	
         			<div id="alertdivid">
         			</div>
					<!-- Text box -->
					<s:iterator value="purposedata" status="counter">
									<s:if test="%{mandatory}">
		                      			<span id="mandatoryFields" class="dIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
		                 			</s:if>
		                 			<s:if test="#counter.odd">
		                 			<div class="clear"></div>
		                 			</s:if>
		                 				<s:if test="%{key == 'alert_after'}">
		                 				<div class="newColumn">
			                 				<div class="leftColumn1"><s:property value="%{value}" />:</div>
											<div class="rightColumn1">
											<s:if test="%{mandatory}">
		                 						<span class="needed"></span>
		                 					</s:if>
		                 						<sj:datepicker id="alertafter" name="%{key}" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
											</div>
										</div>
		                 				</s:if>
		                 				<s:else>
		                 				<div class="newColumn">
			                 				<div class="leftColumn1"><s:property value="%{value}" />:</div>
											<div class="rightColumn1">
											<s:if test="%{mandatory}">
		                 						<span class="needed"></span>
		                 					</s:if>
												<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
											</div>
										</div>
		                 				</s:else>
								</s:iterator>
								<%--       <div class="newColumn">
			                 				<div class="leftColumn1">Alert After:</div>
											<div class="rightColumn1">
											<sj:datepicker id="alertafter" name="alert_after" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
											</div>
								</div> --%>
									<s:iterator value="purposeDropDown" status="counter">
										<s:if test="%{mandatory}">
											<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
										</s:if>
									</s:iterator>
									<s:if test="%{deptNameExist != null}">
										<div class="newColumn">
										<div class="leftColumn1"><s:property value="%{alert_to}"/>:</div>
										<div class="rightColumn1">
										<s:if test="%{deptNameExist == 'true'}"><span class="needed"></span></s:if>
										         <s:select 
										                     id="alert_to"
										                     name="alert_to" 
										                     list="departmentlist"
										                     headerKey="-1"
										                     headerValue="Select Department" 
										                     cssClass="select"
										                     cssStyle="width:82%"
									                         onchange="loadEmp(this.value,'empName',0);"
										                     >
										         </s:select>
										</div>
										</div>
				         			</s:if>	
				         			<s:if test="%{empNameExist != null}">
										<div class="newColumn">
										<div class="leftColumn1"><s:property value="%{empName}"/>:</div>
										<div class="rightColumn1">
										<s:if test="%{empNameExist == 'true'}"><span class="needed"></span></s:if>
										         <s:select 
										                     id="empName"
										                     name="empName" 
										                     list="#{'-1':''}"
										                     headerKey="-1"
										                     headerValue="Select Employee" 
										                     cssClass="select"
										                     cssStyle="width:82%"
										                     multiple="true"
										                     >
										         </s:select>
										</div>
										</div>
				         			</s:if>	
									<s:hidden name="purpose_for" id="purpose_forid" value="%{purpose_for}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
									<div class="clear"></div>
								
								
	<!-- Buttons -->
			<div class="clear"></div>
	 		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<center>	
			<div class="fields">
				<div class="buttonStyle" style="width: 212px;">
			<div class="type-button">
	        <sj:submit 
	           			   targets="purposeresult" 
	                       clearForm="true"
	                       value="Save" 
	                       effect="highlight"
	                       effectOptions="{ color : '#222222'}"
	                       effectDuration="5000"
	                       button="true"
	                       onCompleteTopics="presult"
	                       cssClass="submit"
	                       indicator="indicator2"
	                       onBeforeTopics="validate"
	                       cssStyle="float: left;"
	                       
	         />
	          <sj:a 
						name="Reset"  
						cssClass="button" 
						button="true" 
						onclick="resetForm('formone');"
						>
			  			Reset
						</sj:a>
	          <sj:a 
						name="Cancel"  
						href="#" 
						targets="result" 
						cssClass="button" 
						indicator="indicator" 
						button="true" 
						id="purposeAddCancel"
						
						>
						Back
			</sj:a>
			</div>
			</div>
			   		<sj:div id="purposeaddition"  effect="fold">
					 <div id="purposeresult"></div>
					</sj:div>
	   		</div>
	   		</center>
	   						
		           
	</s:form> 
	</div> 
	</div>
	</div>
	</div>
	</body>
	</html>