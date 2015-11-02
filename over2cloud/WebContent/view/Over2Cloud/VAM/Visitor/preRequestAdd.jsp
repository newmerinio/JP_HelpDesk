<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pre Request Add</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	<script type="text/javascript" src="<s:url value="/js/VAM/prerequestvisit.js"/>"></script>
	<script type="text/javascript">
	$.subscribe('presult', function(event,data)
		       {
		         setTimeout(function(){ $("#prerequestaddition").fadeIn(); }, 10);
		         setTimeout(function(){ $("#prerequestaddition").fadeOut(); }, 6000);
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
		        //alert("fieldsvalues>"+fieldsvalues+"fieldsnames>"+fieldsnames+"colType>"+colType+"validationType>"+validationType);
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
	function resetForm(formId)
	{
		$('#'+formId).trigger("reset");
	}
	</script>
	<script type="text/javascript">
	function loadVisitor(divId,param){
		var organizationName = $("#visitor_company option:selected").text();
		if(organizationName=="New")
		{
			document.getElementById("hideThis").style.display="none";
			document.getElementById("showThis").style.display="block";
		}	
		//var id = val;
		$.ajax({
		type : "post",
		url : "view/Over2Cloud/VAM/master/loadvisitorname.action",
		data: "organizationName="+organizationName+"&param="+param, 
		success : function(data) {
			$('#visitor_name option').remove();
			$('#visitor_name').append($("<option></option>").attr("value",-1).text('Select Visitor'));
			$(data).each(function(index)
			{
			   $('#visitor_name').append($("<option></option>").attr("value",this.NAME).text(this.NAME));
			});
		},
		error: function() {
			alert("error");
		}
		});
	}
	
	function loadVisitorMobile(divId,param){
		var visitorName = $("#visitor_name option:selected").text();
		if(visitorName=="New")
		{
			document.getElementById("hide").style.display="none";
			document.getElementById("show").style.display="block";
		}
		$.ajax({
		type : "post",
		url : "view/Over2Cloud/VAM/master/loadmobileno.action",
		data: "requestedvisitorName="+visitorName+"&param="+param, 
		success : function(data) {
			$('#visitor_mobile option').remove();
			$('#visitor_mobile').append($("<option></option>").attr("value",-1).text('Select Mobile No.'));
			$(data).each(function(index)
			{
			   $('#visitor_mobile').append($("<option></option>").attr("value",this.NAME).text(this.NAME));
			});
		},
		error: function() {
			alert("error");
		}
		});
	}
	function VisitorMobile()
	{
		var visitormob = $("#visitor_mobile option:selected").text();
		if(visitormob=="New")
		{
			document.getElementById("hideid").style.display="none";
			document.getElementById("showid").style.display="block";
		}
	}
	</script>
</head>
<body>
	<div class="list-icon">
	<div class="head">Prerequest</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 0%; width:100%;">
	<div class="clear"></div>
	<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/VAM/master" action="prerequestSubmit" theme="css_xhtml"  method="post" >
	<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	  <div id="errZone" style="float:left; margin-left: 7px"></div>        
	 </div>

	<!-- Drop down -->
						<s:iterator value="dropDownList" status="counter">
							<s:if test="%{mandatory}">
								<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
							</s:if>
						</s:iterator>
					<s:if test="%{visitorCompanyExist != null}">
						<div class="newColumn" id="hideThis">
						<div class="leftColumn1"><s:property value="%{orgname}"/>:</div>
						<div class="rightColumn1">
						<s:if test="mandatory">
							<span class="needed"></span>
						</s:if>
						<s:if test="%{visitorCompanyExist == 'true'}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="visitor_company"
						                     name="visitor_company" 
						                     list="ornizationList.values"
						                     headerKey="-1"
						                     headerValue="Select Organization" 
						                     cssClass="select"
						                     onchange="loadVisitor('visitor_name',0);"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
         			</s:if>	
         			<div id="showThis" style="display: none;">
         			<div class="newColumn" >
								<div class="leftColumn1">Visitor Organization:</div>
								<div class="rightColumn1">
									<s:if test="mandatory">
										<span class="needed"></span>
									</s:if>
									<s:textfield name="visitorcompany"  id="visitorcompanyid" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								</div>
					</div>
         			</div>
         			
         			
         			<s:if test="%{visitorNameExist != null}">
						<div class="newColumn" id="hide">
						<div class="leftColumn1"><s:property value="%{visitorName}"/>:</div>
						<div class="rightColumn1">
						<s:if test="%{visitorNameExist == 'true'}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="visitor_name"
						                     name="visitor_name" 
						                     list="reqvisitorList"
						                     headerKey="-1"
						                     headerValue="Select Visitor" 
						                     cssClass="select"
						                     onchange="loadVisitorMobile('visitor_mobile',0);"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
         			</s:if>
         			<div id="show" style="display: none;">
         			<div class="newColumn">
								<div class="leftColumn1">Visitor Name:</div>
								<div class="rightColumn1">
									<s:if test="mandatory">
										<span class="needed"></span>
									</s:if>
									<s:textfield name="visitorname"  id="visitornameid" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								</div>
					</div>
         			</div>
         			
         			<s:if test="%{visitorMobileExist != null}">
						<div class="newColumn" id="hideid">
						<div class="leftColumn1"><s:property value="%{visitorMob}"/>:</div>
						<div class="rightColumn1">
						<s:if test="%{visitorMobileExist == 'true'}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="visitor_mobile"
						                     name="visitor_mobile" 
						                     list="reqvisitormobList"
						                     headerKey="-1"
						                     headerValue="Select Mobile No." 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     onchange="VisitorMobile();"
						                     >
						         </s:select>
						</div>
						</div>
         			</s:if>
         			<div id="showid" style="display: none;">
         			<div class="newColumn">
								<div class="leftColumn1">Visitor Mobile:</div>
								<div class="rightColumn1">
									<s:if test="mandatory">
										<span class="needed"></span>
									</s:if>
									<s:textfield name="visitormobile"  id="visitormobileid" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								</div>
					</div>
         			</div>
         			
				<s:if test="%{purposeNameExist != null}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{purpose}"/>:</div>
						<div class="rightColumn1">
						<s:if test="%{purposeNameExist == 'true'}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="purpose"
						                     name="purpose" 
						                     list="purposeListPreReqest"
						                     headerKey="-1"
						                     headerValue="Select Purpose" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
         			</s:if>	
				<s:if test="%{locationNameExit != null}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{location}"/>:</div>
						<div class="rightColumn1">
						<s:if test="%{locationNameExit == 'true'}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="location"
						                     name="location" 
						                     list="locationListPreReqest"
						                     headerKey="-1"
						                     headerValue="Select Location" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
         			</s:if>	
	<!-- Text box -->
				<s:iterator value="prerequestdatetimelist" status="counter">
					<s:if test="%{mandatory}">
						<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
					</s:iterator>
						<s:if test="%{previsitdateExit != null}">
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{visit_date}"/>:</div>
							<div class="rightColumn1">
								<s:if test="%{previsitdateExit == 'true'}"><span class="needed"></span></s:if>
								<sj:datepicker id="visit_date" displayFormat="dd-mm-yy" name="visit_date" value="%{new java.util.Date()}" readonly="true" showOn="focus" cssClass="textField" />
							</div>
						</div>
					 	</s:if>
					 	<s:if test="%{previsittimeExit != null}">
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{time_in}"/>:</div>
							<div class="rightColumn1">
								<s:if test="%{previsittimeExit == 'true'}"><span class="needed"></span></s:if>
								<sj:datepicker id="time_in" name="time_in" value="%{new java.util.Date()}" readonly="true" showOn="focus" cssClass="textField" timepicker="true" timepickerOnly="true"/>
							</div>
						</div>
					 	</s:if>
					 	<s:iterator value="prerequestdatetimelist" status="counter">
					<s:if test="%{mandatory}">
						<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
					</s:iterator>
						<s:iterator value="prerequestfields" status="counter">
						<s:if test="%{mandatory}">
							<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
						</s:if>
							<s:if test="%{key!='visited_mobile' && key!='one_time_pwd' && key != 'visited_person' && key != 'deptName' && key != 'sr_number' && key != 'gate'}">
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
									<s:if test="mandatory">
										<span class="needed"></span>
									</s:if>
									<s:textfield name="%{key}"  id="%{key}" value="%{default_value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
								</div>
							</div>
							</s:if>
							<s:elseif test="%{key == 'sr_number'}">
							<s:set name="serialno"  value="%{sr_number}">
							</s:set>
							</s:elseif>
							<s:elseif test="%{key == 'deptName'}">
							<s:set name="departmentn"  value="%{default_value}">
							</s:set>
							</s:elseif>
							<s:elseif test="%{key == 'gate'}">
							<s:set name="gatename"  value="%{default_value}">
							</s:set>
							</s:elseif>
							<s:elseif test="%{key == 'visited_person'}">
							<s:set name="visitedpersn"  value="%{default_value}">
							</s:set>
							</s:elseif>
							
							<s:elseif test="%{key == 'one_time_pwd'}">
							<s:set name="onetimepwd"  value="%{one_time_pwd}">
							</s:set>
							</s:elseif>
							<s:elseif test="%{key == 'visited_mobile'}">
							<s:set name="visitedmobile"  value="%{default_value}">
							</s:set>
							</s:elseif>
								<s:else>
								</s:else>
					</s:iterator>
         				<div class="newColumn">
							<div class="leftColumn1">Visitor Notify By :</div>
							<div class="rightColumn1">
							SMS: <s:checkbox name="sms" id="sms" value="true"  theme="simple" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							Mail: <s:checkbox name="mail" id="mail" value="true" theme="simple" />
							</div>
						</div>
						<s:hidden name="sr_number"  id="sr_number" value="%{serialno}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
						<s:hidden name="deptName"  id="deptName" value="%{departmentn}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
						<s:hidden name="gate"  id="gate" value="%{gatename}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
						<s:hidden name="visited_person"  id="visited_person" value="%{visitedpersn}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
						<s:hidden name="one_time_pwd"  id="one_time_pwd" value="%{onetimepwd}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
						<s:hidden name="visited_mobile"  id="visited_mobile" value="%{visitedmobile}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
						
						
         <!-- Buttons -->
			
			<div class="clear"></div>
	 		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<center>	
			<div class="fields">
			<div class="buttonStyle" style="width: 212px;">
			<div class="type-button">
	        <sj:submit 
	           			   targets="prerequestresult" 
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
						name="Back"  
						href="#" 
						targets="result" 
						cssClass="button" 
						indicator="indicator" 
						button="true" 
						id="preRequestAddCancel"
						>
						Back
			</sj:a>
			</div>
			</div></div>
			   		<sj:div id="prerequestaddition"  effect="fold">
					 <div id="prerequestresult"></div>
					</sj:div>
	   		</center>
	
	</s:form>
	</div>
	</div>
	</div>
	</div>
</body>
</html>