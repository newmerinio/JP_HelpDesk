<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
<title>Id Series Add</title>
	<script type="text/javascript">
	$.subscribe('presult', function(event,data)
		       {
		         setTimeout(function(){ $("#idseriesaddition").fadeIn(); }, 10);
		         setTimeout(function(){ $("#idseriesaddition").fadeOut(); }, 4000);
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
		function loadGateName(val,divId,param){
			var id = val;
			if(id == null || id == '-1')
			return;
			$.ajax({
			type : "post",
			url : "view/Over2Cloud/VAM/master/loadgatename.action",
			data: "id="+id+"&param="+param, 
			success : function(data) {
				$('#gate option').remove();
				$('#gate').append($("<option></option>").attr("value",-1).text('Select Gate'));
				$(data).each(function(index)
				{
				   $('#gate').append($("<option></option>").attr("value",this.ID).text(this.NAME));
				});
			},
			error: function() {
				alert("error");
			}
			});
		}
		 function loadSubPrefix()
		{
			 var locationName = $("#location option:selected").val();
				if(locationName){
				$.ajax({
				    type : "post",
				    url : "view/Over2Cloud/VAM/master/loadprefix.action?locationName="+locationName,
				    success : function(subdeptdata) {
				    var visitorinfo = subdeptdata;
			       $('#sub_prefix').val(visitorinfo);
				},
				   error: function() {
			            alert("error");
			        }
				 });
				 }
		} 
		 function loadPrefix()
		 {
			 var seriesforwords = null;
			 var seriesForName = $("#series_for option:selected").text();
			 if(seriesForName == "Vehicle Series")
			 {
				seriesforwords = "VH"; 
			 }else
			 {
				 seriesforwords = seriesForName.split(" ",2);
			 }
			 
			 //alert("seriesforwords"+seriesforwords+">"+seriesforwords[0]+">>"+seriesforwords[1]);
			 var preFix = seriesforwords[0].substring(0,1)+seriesforwords[1].substring(0,1);
			 $('#prefix').val(preFix);
			 }
		</script>
</head>
<body>
	<div class="clear"></div>
	<div class="middle-content">
	<div class="list-icon">
	<div class="head">ID Series</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div class="border">
	<div class="container_block" align="center">
	<div style=" float:left; padding:20px 1%; width:98%;">
	<s:form id="formone" name="formone" cssClass="cssn_form" namespace="/view/Over2Cloud/VAM/master" action="idseriesSubmit" theme="simple"  method="post" enctype="multipart/form-data" >
	<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	  <div id="errZone" style="float:left; margin-left: 7px"></div>        
    </div>
	<div class="clear"></div>
	<s:iterator value="idseriesDropDown" status="counter">
		<s:if test="%{mandatory}">
		<span id="mandatoryFields" class="dIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
		</s:if>
	</s:iterator>
					<s:if test="%{seriesForExist != null}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{seriesfor}"/>:</div>
						<div class="rightColumn1">
						<s:if test="mandatory">
							<span class="needed"></span>
						</s:if>
						<s:if test="%{seriesForExist == 'true'}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="series_for"
						                     name="series_for" 
						                     list="#{'Visitor Series':'Visitor Series', 'Visitor Card':'Visitor Card', 'Vehicle Series':'Vehicle Series', 'Vehicle Token Series':'Vehicle Token Series'}"
						                     headerKey="-1"
						                     headerValue="Select Series For" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     onchange="loadPrefix();"
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
						                     list="locationList"
						                     headerKey="-1"
						                     headerValue="Select Location" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     onchange="loadGateName(this.value,'gate',0);"
						                     >
						         </s:select>
						</div>
						</div>
         			</s:if>	
					<s:if test="%{gateNameExist != null}">
						<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{gate}"/>:</div>
						<div class="rightColumn1">
						<s:if test="%{gateNameExist == 'true'}"><span class="needed"></span></s:if>
						         <s:select 
						                     id="gate"
						                     name="gate" 
						                     list="#{'-1':''}"
						                     headerKey="-1"
						                     headerValue="Select Gate" 
						                     cssClass="select"
						                     cssStyle="width:82%"
						                     onchange="loadSubPrefix();"
						                     >
						         </s:select>
						</div>
						</div>
         			</s:if>	
					<!-- Text box -->
					<s:iterator value="idseriesfieldslist" status="counter">
					<s:if test="%{mandatory}">
						<span id="mandatoryFields" class="dIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
					</s:if>
						<s:if test="#counter.odd">
							<div class="clear"></div>
						</s:if>
						<div class="newColumn">
							<div class="leftColumn1"><s:property value="%{value}"/>:</div>
							<div class="rightColumn1">
								<s:if test="mandatory">
									<span class="needed"></span>
								</s:if>
								<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
							</div>
						</div>
					</s:iterator>
					<div class="clear"></div>
			<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<center>	
			<div class="buttonStyle">
			<div class="type-button">
	        <sj:submit 
	           			   targets="idseriesresult" 
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
						id="idSeriesCancel"
						>
						Back
			</sj:a>
			</div>
			</div>
			   		<sj:div id="idseriesaddition"  effect="fold">
					 <div id="idseriesresult"></div>
					</sj:div>
	   		</center>
	</s:form>
	</div>
	</div>
	</div>
	</div>
</body>
</html>