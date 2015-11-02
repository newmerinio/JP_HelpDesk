<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="s" uri="/struts-tags" %>
	 <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	 <% 
	/* Runtime rt = Runtime.getRuntime();
			rt.exec("java -jar C:/snapcapturer.jar");//for snap capture */
			String path = getServletContext().getRealPath("/");
			Runtime rt = Runtime.getRuntime();
					rt.exec("java -jar C:/snapcapture.jar "+ path);//for snap capture
					System.out.println("************camera execute***************");
	%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
<title>Vehicle Entry Add</title>
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
	$.subscribe('presult', function(event,data)
		       {
		         $("#vehicleaddresult").dialog('open');
		       });
	
	</script>
	 <%-- <script type="text/javascript">
				$.subscribe('presult', function(event,data)
		       {
		         setTimeout(function(){ $("#vehicleentryaddition").fadeIn(); }, 10);
		         setTimeout(function(){ $("#vehicleentryaddition").fadeOut(); }, 4000);
		       });
	</script> --%>
	<script type="text/javascript">
	function loadAlertTime(val,divId,param){
			var id=val;
			if(id == null || id == '-1')
			return;
			$.ajax({
			type : "post",
			url : "view/Over2Cloud/VAM/master/loadalerttime.action",
			data: "id="+id+"&param="+param, 
			success : function(data) {
				$('#alert_after').val(data);
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
	  var vehiclestatus = $("#vehiclestatus").val();
	    $.ajax({
			type : "post",
		    url : "view/Over2Cloud/VAM/master/vehicleDetailadd.action?modifyFlag=0&deleteFlag=0"+"&vehiclestatus="+vehiclestatus+"&vehicleExitStatus=0",
		    success : function(data) {
	     	  $("#"+"data_part").html(data);
	     	  document.getElementById("showbuttons").style.display="block";
			},
			error: function() {
				alert("error");
			}
			});
		
	}
	</script>
	<script type="text/javascript">
	function getEntryFields(vehiclestatus) {
		$.ajax({
			type : "post",
		    url : "view/Over2Cloud/VAM/master/vehicleDetailadd.action?modifyFlag=0&deleteFlag=0"+"&vehiclestatus="+vehiclestatus+"&vehicleExitStatus=0",
		    success : function(data) {
	     	  $("#"+"data_part").html(data);
	     	  document.getElementById("showbuttons").style.display="block";
			},
			error: function() {
				alert("error");
			}
			});
	}
	</script>
	<script type="text/javascript">
	function searchPreVehicleEntryData(drivermobile)
	{
		var drivermob = drivermobile;
		if(drivermob != ""){
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/VAM/master/preVehicleEntryData.action?drivermob="+drivermob,
		    success : function(subdeptdata) {
		       var vehicleinfo = subdeptdata.split("#");
		       $('#driver_name').val(vehicleinfo[0]);
		       $('#vehicle_model').val(vehicleinfo[1]);
		       $('#vehicle_number').val(vehicleinfo[2]);
	       	   $('#trip_no').val(vehicleinfo[3]);
	       	   $('#vehicle_owner').val(vehicleinfo[4]);
	       	   $('#vh_owner_mob').val(vehicleinfo[5]); 
	       	   $('#location').val(vehicleinfo[6]);
	    	   $('#invoce_no').val(vehicleinfo[7]);
	    	   $('#destination').val(vehicleinfo[8]);
	    	   
		},
		   error: function() {
	            alert("error");
	        }
		 });
		 }
	}
	</script>
</head>
<body>
	<sj:dialog id="vehicleaddresult"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="Vehicle Pass" openTopics="openEffectDialog" closeTopics="closeEffectDialog" modal="true" width="590" height="420"/>
	<div class="list-icon">
	<div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 0%; width:100%;">
	<div class="clear"></div>
	<div class="border">
	<div class="container_block">
	<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/VAM/master" action="vehicleEntrySubmit" theme="css_xhtml"  method="post" >
			                   <div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	 		                                        <div id="errZone" style="float:left; margin-left: 7px"></div>        
	                           </div>
	        <div class="clear"></div>
	        <s:hidden name="vehicleExitStatus" id="vehicleExitStatus" value="%{vehicleExitStatus}"></s:hidden> 
	        <s:hidden id="vehiclestatus" value="%{vehiclestatus}"></s:hidden>
					<!-- Text box -->
	  <s:iterator value="vehiclefields" status="counter">
					<s:if test="%{mandatory}">
						<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
						<s:if test="%{key=='driver_mobile'}">
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}" cssClass="textField" placeholder="Enter Data" onblur="searchPreVehicleEntryData(this.value);" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
						</s:if>
						<s:elseif test="%{key=='sr_number'}">
						<s:if test="%{hidesrno=='true'}">
							<s:set name="sr_no"  value="%{sr_number}">
							</s:set>
						</s:if>
						<s:else>
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}" value="%{sr_number}"  cssClass="textField" readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
						</s:else>
						
						</s:elseif>
						
						<s:elseif test="%{key=='trip_no'}">
						<s:if test="%{hidetripno=='true'}">
							<s:set name="trip"  value="%{trip_no}">
							</s:set>
						</s:if>
						<s:else>
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}" value="%{trip_no}"  cssClass="textField" readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
						</s:else>
						</s:elseif>
						<s:else>
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
						</s:else>
	 </s:iterator><!--
	 vehicle drop down field
	 --><s:iterator value="deptDropDown" status="counter">
					<s:if test="%{mandatory}">
						<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					</s:if>
	 </s:iterator><!--
	location name			
     --><s:if test="%{locationNameExit != null}">
						<div class="newColumn">
						<div class="leftColumn1">Location From:</div>
						<div class="rightColumn1">
						<s:if test="mandatory">
							<span class="needed"></span>
						</s:if>
						<s:if test="%{locationNameExit == 'true'}"><span class="needed"></span></s:if>
						        <%--  <s:select 
						                     id="location"
						                     name="location" 
						                     list="locationListPreReqest"
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     >
						         </s:select> --%>
						         
                      <s:textfield name="location"  id="location"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
													         
						</div>
						</div>
      </s:if>	
         		
         			<s:if test="%{deptNameExist != null}">
						<div class="newColumn">
						<div class="leftColumn1">Plant To:</div>
						<div class="rightColumn1">
						<s:if test="mandatory">
							<span class="needed"></span>
						</s:if>
						<s:if test="%{deptNameExist == 'true'}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="deptName"
						                     name="deptName" 
						                     list="jlocationlist"
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     >
						         </s:select>
						</div>
						</div>
         			</s:if>
         			<s:if test="%{purposeExist != null}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{purpose}"/>:</div>
						<div class="rightColumn1">
						<s:if test="mandatory">
							<span class="needed"></span>
						</s:if>
						<s:if test="%{purposeExist == 'true'}"><span class="needed"></span></s:if>
						        	 <s:select 
						                     id="purpose"
						                     name="purpose" 
						                     list="purposelist"
						                     headerKey="-1"
						                     headerValue="Select Purpose" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     onchange="loadAlertTime(this.value,'alert_after',0);"
						                     >
						        	 </s:select>
						</div>
						</div>
         			</s:if>	
         			<s:if test="destinationto == 'Destination To'">
         			<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{destinationto}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="destination"  id="destination"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
         			</s:if>
         			
         			
         			<!--<s:if test="%{dropdownformaterial == true}">
						<div class="newColumn">
						<div class="leftColumn1">Vehicle Status:</div>
						<div class="rightColumn1">
						<s:if test="%{dropdownformaterial == true}"><span class="needed"></span></s:if>
						         <s:radio  name="vehiclestatus" id="vehiclestatus" list="#{'1':'With Material','2':'Without Material'}"  onclick="getEntryFields(this.value);"/>
						</div>
						</div>
         			</s:if>	
         			--><s:iterator value="vehicledatetimelist" status="counter">
					<s:if test="%{mandatory}">
						<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
					</s:iterator>
						<s:if test="%{vehicleEntrydateExist != null}">
						 <s:hidden name="entry_date"  id="entry_date"  value="%{datevalue}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" readonly="true"/>
					 	</s:if>
					 	<s:if test="%{vehicleEntrytimeExist != null}">
						  <s:hidden name="entry_time"  id="entry_time"  value="%{timevalue}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" readonly="true"/>
					 	</s:if>
         				<s:hidden name="alert_after"  id="alert_after"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
         				<s:hidden name="sr_number"  id="sr_number" value="%{sr_no}"  cssClass="textField" readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
         				<s:hidden name="trip_no"  id="trip_no" value="%{trip}"  cssClass="textField" readonly="true" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
         	<div class="clear"></div>
			<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<center>	
			<div class="fields" id="showbuttons" style="display: none;">
			<div class="buttonStyle" style="width: 212px;">
			<div class="type-button">
	        <sj:submit 
	           			   targets="vehicleaddresult" 
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
						cssClass="submit" 
						button="true" 
						onclick="resetForm('formone');"
						>
			  			Reset
						</sj:a>
	          <sj:a 
						name="Back"  
						href="#" 
						targets="result" 
						cssClass="submit" 
						indicator="indicator" 
						button="true" 
						id="vehicleAddCancel"
						>
						Back
			</sj:a>
			</div>
			</div>
					<%-- <sj:div id="vehicleentryaddition"  effect="fold">
					 <div id="vehicleaddresult"></div>
					</sj:div> --%>
			</div>
	   		</center>
	
	</s:form>
	</div>
	</div>
	</div>
	</div>
</body>
</html>