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
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/associate/associate.js"/>"></script>

<SCRIPT type="text/javascript">
function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}
function resetForm2(formtwo)
{
	$('#'+formtwo).trigger("reset");
}


function resetForm3(formThree)
{
	$('#'+formThree).trigger("reset");
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


	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/fetchReferredName.action",
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


$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#associatebasicid").fadeIn(); }, 10);
          setTimeout(function(){ $("#associatebasicid").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
});
$.subscribe('level2', function(event,data)
        {
          setTimeout(function(){ $("#associateactionid").fadeIn(); }, 10);
          setTimeout(function(){ $("#associateactionid").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
});
$.subscribe('dd', function(event,data)
        {
          setTimeout(function(){ $("#pAssociate").fadeIn(); }, 10);
          setTimeout(function(){ $("#pAssociate").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });
$.subscribe('form2', function(event,data)
        {
          setTimeout(function(){ $("#orglevel3").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel3").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
         
        });

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
</SCRIPT>
<script type="text/javascript">
function viewAssociate()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
$.ajax({
    type : "post",
    url : "view/Over2Cloud/WFPM/Associate/beforeAssociateView.action",
    data: "isExistingAssociate=${isExistingAssociate}",
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
	<!-- <div class="head">Add Prospective Client</div> -->
		<div class="head">Prospective Associate</div>
		<div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Edit</div>
	</div>

<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">

<!-- Associate basic data ---------------------------------------------------------------------------------------------------->
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Basic Details" id="one">
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Associate" action="editAssociate" theme="simple" method="post" 
	enctype="multipart/form-data">
	<s:hidden name="id" value="%{id}" />
	
	<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float: left; margin-left: 7px"></div>
	</div>
	<br/>
	
	<div class="menubox">		
	<s:iterator value="masterViewProp" status="counter">
		<s:if test='%{colType == "T"}'>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:textfield name="%{colomnName}"  id="%{colomnName}" value="%{value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
			</div>
		</s:if>
    </s:iterator>
	
	<s:iterator value="masterViewProp" status="counter">
	<s:if test='%{colType == "D"}'>
		<s:if test="%{colomnName == 'associateType'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="associateTypeMap"
						headerKey="-1" 
						headerValue="Select Associate Type"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{colomnName == 'associateSubType'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="associateSubTypeMap"
						headerKey="-1" 
						headerValue="Select Associate Sub Type"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'associateCategory'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="associateCategoryMap"
						headerKey="-1" 
						headerValue="Select Associate Category"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'sourceName'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="sourceMap"
						headerKey="-1" 
						headerValue="Select Source"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'associateRating'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="#{'-1':'Select Rating'}" 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            headerKey="-1"
                            headerValue="Select Rating"
                            value="%{value}" 
                            cssClass="select"
                            cssStyle="width:82%"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'state'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="stateDataMap"
							headerKey="-1"
							headerValue="Select Location"
							value="%{selectedStateId}" 
							cssClass="select"
                            cssStyle="width:82%">
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'location'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="location_master_dataMap"
							headerKey="-1"
							headerValue="Select Location"
							value="%{value}" 
							cssClass="select"
                            cssStyle="width:82%">
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'acManager'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="%{colomnName}" 
						name="%{colomnName}"
			            list="employee_basicMap"
			            headerKey="-1"
			            headerValue="Select Name" 
			            value="%{value}"
		                cssClass="select"
	                    cssStyle="width:82%"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'industry'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="industry" 
						name="%{colomnName}"
						list="industryMap"
			            headerValue="Select Industry" 
			            value="%{value}"
	                    cssClass="select"
                       	cssStyle="width:82%"
                       	onchange="fillName(this.value,'subIndustry',0)"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
		<s:elseif test="%{colomnName == 'subIndustry'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
			            id="subIndustry" 
						name="%{colomnName}"
						list="subIndustryMap"
	                    headerKey="-1"
	                    headerValue="Select Sub-Industry" 
			            value="%{value}"
	                    cssClass="select"
                       	cssStyle="width:82%"
			            >
				</s:select>
			</div>
			</div>	
		</s:elseif>
	
	<s:if test="%{#status.even}">
		<div class="clear"></div>
	</s:if>	
</s:if>
	</s:iterator>	
	</div>	

	<br>		
	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="associateresult" 
		     clearForm="true"
		     value="Edit" 
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
			button="true"
			onclick="resetForm('formone');"
		>
		  	Reset
		</sj:a> 
	     <sj:a 
	     	name="Cancel"  
			href="#" 
			targets="result" 
			cssClass="submit" 
			indicator="indicator"
			button="true" 
			onclick="viewAssociate()" 
			style="margin-left:4px;"
		>
		  	Back
		</sj:a>
	</div>
	</div>
	<div class="clear"></div>
	<center>
		<sj:div id="associatebasicid"  effect="fold">
                  <div id="associateresult"></div>
              </sj:div>
             </center>
	<br>
</s:form>

</sj:accordionItem>

<!-- Associate contact data --------------------------------------------------------------------------------------------------------->
<sj:accordionItem title="Contact Details" id="two">
<s:form id="formTwo" name="formone" namespace="/view/Over2Cloud/WFPM/Associate" action="editAssociateContact" theme="simple" method="post" 
	enctype="multipart/form-data">
<s:iterator value="associateContactEditList" var="varAssociateContactEditList">	
	<div class="menubox">
	<!-- textfield -->
	<s:iterator value="%{#varAssociateContactEditList}" status="counter">
		<s:if test='%{colType == "T" && colomnName == "id"}'>
			<s:hidden name="%{colomnName}" value="%{value}" />
		</s:if>
		<s:elseif test='%{colType == "T" && colomnName != "id"}'>
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:textfield name="%{colomnName}"  id="%{colomnName}" value="%{value}"  cssClass="textField" 
			     			placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
			</div>
			</div>
		</s:elseif>
			
   </s:iterator>
		<!-- dropdown -->
	<s:iterator value="%{#varAssociateContactEditList}" status="counter">
	<s:if test='%{colType == "D"}'>
		<s:if test="%{colomnName == 'department'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select 
						id="%{colomnName}" 
						name="%{colomnName}"
						list="deptlist"
						headerKey="-1" 
						headerValue="Select Source"
						value="%{value}"
						cssClass="select"
                        cssStyle="width:82%"
						>
					</s:select>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{colomnName == 'degreeOfInfluence'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <s:select
							id="%{colomnName}" 
							name="%{colomnName}" 
							list="#{'-1':'Select Rating'}" 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
                            headerKey="-1"
                            headerValue="Influence" 
                            cssClass="select"
                            cssStyle="width:82%"
                            >
				</s:select>
			</div>
			</div>	
		</s:elseif>	
	</s:if>
	
	</s:iterator>
		<!-- datepicker -->	
	<s:iterator value="%{#varAssociateContactEditList}" status="counter">
	<s:if test='%{colType == "Time"}'>
		<s:if test="%{colomnName == 'anniversary'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <sj:datepicker 
		     				name="%{colomnName}" 
		     				id="%{anniversaryId}" 
							value ="%{value}"
		     				changeMonth="true" 
		     				changeYear="true" 
		     				cssClass="textField"
                            cssStyle="width:82%"   
		     				yearRange="1890:2020" 
		     				showOn="focus" displayFormat="dd-mm-yy" placeholder="Select Date"/>
			</div>
			</div>	
		</s:if>
		<s:elseif test="%{colomnName == 'birthday'}">
			<div class="newColumn">
			<div class="leftColumn1"><s:property value="%{headingName}"/>:</div>
			<div class="rightColumn1"><s:if test="%{mandatroy == true}"><span class="needed"></span></s:if>
			     <sj:datepicker 
		     				name="%{colomnName}" 
		     				id="%{birthdayId}" 
							value ="%{value}"
		     				changeMonth="true" 
		     				changeYear="true" 
		     				cssClass="textField"
                            cssStyle="width:82%"   
		     				yearRange="1890:2020" 
		     				showOn="focus" displayFormat="dd-mm-yy" placeholder="Select Date"/>
			</div>
			</div>	
		</s:elseif>	
	</s:if>
	
	</s:iterator>
		
	</div>
</s:iterator>
	<br>		
	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="contactresult" 
		     clearForm="true"
		     value="Edit" 
		     effect="highlight"
		     effectOptions="{ color : '#222222'}"
		     effectDuration="5000"
		     button="true"
		     onCompleteTopics="level2"
		     cssClass="submit" 
		     indicator="indicator2"
		     onBeforeTopics="validate"
	     />
	     <sj:a 
	     	name="Reset"  
			href="#" 
			cssClass="submit" 
			button="true"
			onclick="resetForm('formone');"
		>
		  	Reset
		</sj:a> 
	     <sj:a 
	     	name="Cancel"  
			href="#" 
			targets="result" 
			cssClass="submit" 
			indicator="indicator"
			button="true" 
			onclick="viewAssociate()" 
			style="margin-left:4px;"
		>
		  	Back
		</sj:a>
	</div>
	</div>
	<div class="clear"></div>
	<center>
		<sj:div id="associateactionid"  effect="fold">
                  <div id="contactresult"></div>
              </sj:div>
             </center>
	<br>

</s:form>
</sj:accordionItem>
</sj:accordion>
</div>
</div>
<SCRIPT type="text/javascript">
/*fillName('Client','clientNa',0);
fillName('Client','clientN',0);*/
</SCRIPT>
</body>
</html>