<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">
.user_form_input{
    margin-bottom:10px;
}
</style>
<html>

<head>
<script src="<s:url value="/js/WFPM/offering.js"/>"></script>
<script src="<s:url value="/js/associate/associate.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut();cancelButton();}, 4000);
	         $('select').find('option:first').attr('selected', 'selected');
	         fillSubType('associateType1');
	         fetchAssoCategory('associateCategoryId');
	       });

$.subscribe('level2', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel5").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel5").fadeOut(); }, 4000);
	         $('select').find('option:first').attr('selected', 'selected');
	       });

$.subscribe('dd', function(event,data)
        {
          setTimeout(function(){ $("#pAssociate").fadeIn(); }, 10);
          setTimeout(function(){ $("#pAssociate").fadeOut();cancelButton();}, 4000);
          $('select').find('option:first').attr('selected', 'selected');
         
        });
$.subscribe('form2', function(event,data)
        {
          setTimeout(function(){ $("#assOffering").fadeIn(); }, 10);
          setTimeout(function(){ $("#assOffering").fadeOut();cancelButton();}, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
        });

function clearAllErroDiv()
{
	$("div[id^='error']").each(function(){
  	  // `this` now refers to the current element
	     this.innerHTML="";
  	});
}

function cancelButton()
{
	 $('#completionResult').dialog('close');
	 viewAssociate();
}
$.subscribe('validate', function(event,data)
	   {	
			var mystring=$(".pIds").text();
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
		            else if(validationType=="ans"){
		             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
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
			            //alert("sdhvjsdhbj"+$("#"+fieldsvalues).val());
			            
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
		        else
		        {
		        	$('#completionResult').dialog('open');
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
		            else if(validationType=="ans"){
			             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
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
		        else
		        {
		        	$('#completionResult').dialog('open');
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
		            else if(validationType=="ans"){
			             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
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
		        else
		        {
		        	$('#completionResult').dialog('open');
		        }
		    }       
		});

function fillSubType(selectId)
{
	$.ajax({
	       type : "post",
	       url : "view/Over2Cloud/wfpm/masters/fetchAssociateType.action",
	       success : function(data) {
		           //alert(data);
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

	function viewAssociate()
	{

		var table = $("#dataTable").val();
		var urls;
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		if(table == "client_status")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_client_status_master&mapSubTable=client_status_master&moduleName=${mainHeaderName}&dataTable="+table;
		}
		else if(table == "weightagemaster")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_weightage_master&mapSubTable=weightage_master&moduleName=${mainHeaderName}&dataTable="+table;
		
		}
		else if(table == "forcastcategarymaster")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_forcastcategary_master&mapSubTable=forcastcategary_master_configuration&moduleName=${mainHeaderName}&dataTable="+table;
		
		}
		else if(table == "salesstagemaster")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_salesstage_master&mapSubTable=salesstage_master_configuration&moduleName=${mainHeaderName}&dataTable="+table;
		
		}
		else if(table == "associatestatus")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?modifyFlag=0&deleteFlag=0&mappingTable=mapped_associate_status_master&mapSubTable=associate_status_master&moduleName=${mainHeaderName}&dataTable="+table;
		}
		else if(table == "associatetype")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_associate_type_master&mapSubTable=associate_type_master&moduleName=${mainHeaderName}&dataTable="+table;
		}
		else if(table == "associatecategory")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_associate_category_master&mapSubTable=associate_category_master&moduleName=${mainHeaderName}&dataTable="+table;
		}
		else if(table == "lostoportunity")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_lost_master&mapSubTable=lost_master&moduleName=${mainHeaderName}&dataTable="+table;
		}
		else if(table == "email_configuration_data")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_email_configuration&mapSubTable=email_configuration&moduleName=${mainHeaderName}&dataTable="+table;
		}
		else if(table == "location")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_location_master&mapSubTable=location_master&moduleName=${mainHeaderName}&dataTable="+table;
		}
		else if(table == "sourcemaster")
		{
			urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_source_master&mapSubTable=source_master&moduleName=${mainHeaderName}&dataTable="+table;
		}
		$.ajax({
		         type : "post",
		         url : encodeURI(urls),
		         success : function(subdeptdata) {
	                               $("#"+"data_part").html(subdeptdata);
		                            },
		         error: function() {
	                       alert("error");
	                       }
		      });
  }

function fetchAssoCategory(divId)
{
   $.ajax({
	       type : "post",
	       url : "view/Over2Cloud/WFPM/Associate/fetchAssoCategory.action",
		   success : function(data) {
		            $('#'+divId+' option').remove();
		            $('#'+divId).append($("<option></option>").attr("value",-1).text('Select'));
		            $(data).each(function(index)
		  {
		            $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		 });
	     },
	       error: function(){
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
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="orglevel1Div" ></div>
</sj:dialog>
 <div class="clear"></div>
	     <div class="middle-content">
		         <div class="list-icon">
		                 <div class="head"><s:property value="%{mainHeaderName}"/> </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
		         </div>
			     <div class="clear"></div>
                 <div class="container_block"><div style=" float:left; padding:20px 1%; width:98%;">
                           <sj:accordion id="accordionAssoType" autoHeight="false">
                                  <sj:accordionItem title="Add" id="oneId"> 
                                            <s:form id="formone" title="%{moduleName}" name="formone" namespace="/view/Over2Cloud/wfpm/masters" 
		                                                                   action="associateStatusAddMaster" theme="simple"  method="post"enctype="multipart/form-data" >
	
        			                                <s:hidden name="mappingTable" value="%{mappingTable}"></s:hidden>   
                                                    <s:hidden name="mapSubTable" value="%{mapSubTable}"></s:hidden>   
                                                    <s:hidden name="moduleName" value="%{moduleName}"></s:hidden>   
    				                                <s:hidden id="dataTable" name="dataTable" value="%{dataTable}"></s:hidden>
    				
    				                             <div class="menubox">
			                                            <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                                                           <div id="errZone" style="float:left; margin-left: 7px"></div>        
                                                        </div>
    					                               <s:iterator value="associateStatusMasterTextBox" status="counter">
     					                                     <s:if test="%{mandatory}">
     						                                 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
     					                                     </s:if>
     					
     					
     					                                           <s:if test="#counter.odd">
     					                                                 <div class="clear"></div>
     						                                                  <div class="newColumn">
      						                                                  <div class="leftColumn1"><s:property value="%{value}"/>:</div>
      						                                                  <div class="rightColumn1">
      							                                              <s:if test="%{mandatory}">
      								                                          <span class="needed"></span>
      							                                              </s:if>
					                                                          <s:textfield name="%{key}"  id="%{key}3"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					                                                          </div>
				                                                         </div>   
     					                                           </s:if>
     					                                           <s:else>
      					                                                  <div class="newColumn">
      						                                              <div class="leftColumn1"><s:property value="%{value}"/>:</div>
      						                                              <div class="rightColumn1">
      								                                      <s:if test="%{mandatory}">
      							                                          <span class="needed"></span>
      						                                              </s:if>
					                                                      <s:textfield name="%{key}"  id="%{key}3"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					                                                      </div>
				                                                          </div>
				
     					                                           </s:else>
    					                              </s:iterator>
    					                                <s:if test="stateNameExists">
    						                                 <s:if test="%{mandatory}">
                 				                                <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
            				                                 </s:if>
            				                                  
            				                                 <div class="newColumn">
            					                             <div class="leftColumn1">State:</div>
            					                             <div class="rightColumn1">
					                                             <s:select 
                                                                     id="stateName"
                                                                     name="stateName" 
                                                                     list="stateNameList"
                                                                     headerKey="-1"
                                                                     headerValue="Select State" 
                                                                     cssClass="select"
                                                                     onchange="fetchLocation(this.value,'location123')"
                                                                     >
					                                              </s:select>
				                                             </div>
            				                                 </div>
    					                                </s:if>

				                                    <!-- lost opportunity -->
  					                                    <s:if test="%{lostOpportunityTypeName != null && !lostOpportunityTypeName.equals('')}">
  						                                      <s:if test="%{lostOpportunityMandatory}">
               				                                              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{lostOpportunityTypeName}"/>#<s:property value="%{lostOpportunityTypeHeading}"/>#D#D,</span>
          				                                      </s:if> 
          				                                      <div class="newColumn">
          					                                  <div class="leftColumn1"><s:property value="%{lostOpportunityTypeHeading}"/>:</div>
          					                                  <div class="rightColumn1">
	          					                                  <s:if test="%{lostOpportunityMandatory}">
	      							                                    <span class="needed"></span>
	      						                                  </s:if>
								                                  <s:select 
			                                                              id="%{lostOpportunityTypeName}"
			                                                              name="%{lostOpportunityTypeName}" 
			                                                              list="#{'0':'Lead','1':'Client','2':'Associate'}"
			                                                              headerKey="-1"
			                                                              headerValue="Select For Whome" 
			                                                              cssClass="textField"
			                                                              >
									                              </s:select>
						                                      </div>
          				                                      </div>
  					                                   </s:if>
				                                  <!-- lost opportunity -->
    					
    					                          <div class="clear"></div>
		                                          <div class="buttonStyle">
		                                                 <sj:submit 
                                                             targets="orglevel1Div" 
                                                             clearForm="true"
                                                             value="  Save  " 
                                                             effect="highlight"
                                                             effectOptions="{ color : '#222222'}"
                                                             effectDuration="5000"
                                                             button="true"
                                                             onCompleteTopics="level1"
                                                             cssClass="submit"
                                                             indicator="indicator2"
                                                             onBeforeTopics="validate"
                                                          />
		                                                  <sj:a 
		     	                                              name="Reset"  
				                                              href="#" 
				                                              cssClass="submit" 
				                                              indicator="indicator" 
				                                              button="true" 
				                                              onclick="resetForm('formone');"
			                                                  >
			  	                                             Reset
			                                              </sj:a>
		                                                  <sj:a 
		     	                                              name="Cancel"  
				                                              href="#" 
				                                              cssClass="submit" 
				                                              indicator="indicator" 
				                                              button="true" 
				                                              onclick="viewAssociate();"
			                                                  >
			  	                                             Back
			                                              </sj:a>
		                                             <br><br>
		                          						<%-- <sj:div id="orglevel1"  effect="fold">
               			                                     <div id="orglevel1Div"></div>
          				                             </sj:div> --%>
                     				              </div>
                     	   </div>
                           <!-- End Menu Box -->  
                   </s:form>
             </sj:accordionItem>
<!-- Associate sub type ------------------------------------------------------------------------------------------>
 <s:if test="%{subTypeExists == 2}">
         <sj:accordionItem title="%{moduleName1}" id="oneId1">
		     <s:form id="formThree" name="formThree" namespace="/view/Over2Cloud/wfpm/masters" action="addAssociateSubType" theme="simple"  method="post" enctype="multipart/form-data" >
                   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                         <div id="assErrZone" style="float:left; margin-left: 7px">
                         </div> 
                   </div> 
                      <div class="menubox">
                            <span id="assoMandatoryFields" class="aIds" style="display: none; ">associateType1#Associate#D#,</span>
                            <div class="newColumn">
                            <div class="leftColumn1">Associate Type:</div>
                            <div class="rightColumn1"><span class="needed"></span>
                            <s:select 
                                  id="associateType1"
                                  name="associateType" 
                                  list="#{'-1':'No Data'}"
                                  headerKey="-1"
                                  headerValue="Select" 
                                  cssClass="textField"
                                  >
                            </s:select>
                            </div>
                            </div>
                      
                            <div class="clear"></div>    
                                      <span id="assoMandatoryFields" class="aIds" style="display: none; ">associateSubType#<s:property value="%{moduleName1}"/>#T#ans,</span>
	                                  <div class="newColumn">
	                                  <div class="leftColumn1"><s:property value="%{moduleName1}"/>:</div>
					                  <div class="rightColumn1"><span class="needed"></span>
					                         <s:textfield name="associateSubType"  id="associateSubType"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				                      </div> 
					                  </div>
             	     </div>
             	     <div class="clear"></div> 
					  <div class="buttonStyle">
	                    <sj:submit 
	                        targets="orglevel1Div"
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="dd"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate1"
	                        />
				        <sj:a 
		     	            name="Reset"  
				            href="#" 
				            cssClass="submit" 
				            indicator="indicator" 
				            button="true" 
				            onclick="resetForm('formThree');"
			                >
			  	            Reset
			           </sj:a>
               
		               <sj:a 
		     	           name="Cancel"  
				           href="#" 
				           cssClass="submit" 
				           indicator="indicator" 
				           button="true" 
				           onclick="viewAssociate();"
			               >
			  	           Back
			           </sj:a>
			        <br><br>
                    <%-- <sj:div id="pAssociate"  effect="fold">
                          <div id="assoresult1"></div>
                    </sj:div> --%>
	              </div>
               
               
		</s:form>
	</sj:accordionItem>
</s:if>
<s:if test="categoryBlock">
    <sj:accordionItem title="Associate Category Cost Mapping" id="twoId">  
	    <s:form id="formTwo" name="formTwo" namespace="/view/Over2Cloud/wfpm/masters" action="associateCategoryCostMapping" theme="simple"  method="post" >
			   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                        <div id="form2ErrZone" style="float:left; margin-left: 7px">
                        </div> 
               </div>
		       <s:hidden id="offeringLevelId" name="offeringLevelId" value="%{offeringLevelId}"></s:hidden>
               <div id="offeringAllHideShow">    
                     <div id="levelAllOffering"></div>
                  
                       <div class="menubox">
		                      <span id="form2MandatoryFields" class="dIds" style="display: none; ">associateCategoryId#Associate Category#D#,</span>
		                      <span id="form2MandatoryFields" class="dIds" style="display: none; ">price#Price#D#,</span>
						  <div class="newColumn">
							  <div class="leftColumn1">Associate Category:</div>
							  <div class="rightColumn1">
							  <span class="needed"></span>
							  <s:select 
		                              id="associateCategoryId"
		                              name="associateCategoryId" 
		                              list="associateCatList"
		                              headerKey="-1"
		                              headerValue="--Select Data--"  
		                              cssClass="textField"
		                              >
		                      </s:select>
							  </div> 
				          </div>
							  <div class="newColumn">
							  <div class="leftColumn1">Value(%):</div>
							  <div class="rightColumn1">
							  <span class="needed"></span>
							     <s:textfield name="price"  id="price"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							  </div>
						      </div>
						<!-- End Menu Box -->      
				     </div>
               
				      <!-- Buttons -->
				      <div class="clear"></div> 
					  <div class="buttonStyle">
	                     <sj:submit 
	                           targets="orglevel1Div" 
	                           clearForm="true"
	                           value="  Save  " 
	                           effect="highlight"
	                           effectOptions="{ color : '#222222'}"
	                           effectDuration="5000"
	                           button="true"
	                           cssClass="submit"
	                           onCompleteTopics="form2"
	                           indicator="indicator6"
	                           onBeforeTopics="validate2"
	                        />
	                     <sj:a 
		     	               name="Reset"  
				               href="#" 
				               cssClass="submit" 
				               indicator="indicator" 
				               button="true" 
				               onclick="resetForm('formTwo');"
			                   >
			  	              Reset
			             </sj:a>
	                     <sj:a 
		     	              name="Cancel"  
				              href="#" 
				              cssClass="submit" 
				              indicator="indicator" 
				              button="true" 
				              onclick="viewAssociate();"
			                  >
			  	              Back
			            </sj:a>
			            <br><br>   
	                        
			           <%-- <sj:div id="assOffering"  effect="fold">
                              <div id="orglevel3"></div>
                       </sj:div> --%>
                 </div>
               
            </div>                
	    </s:form>
	</sj:accordionItem>
</s:if>
</sj:accordion>
	</div></div></div>
</body>

<SCRIPT type="text/javascript">
fillSubType('associateType1');
allLevelOffering('levelAllOffering','offeringAllHideShow',$("#offeringLevelId").val());
</SCRIPT>

</html>
