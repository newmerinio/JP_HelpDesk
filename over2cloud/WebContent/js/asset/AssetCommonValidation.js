$.subscribe('configUtilityValidate', function(event,data)
{
	var mystring;
	if($('#threshold_Level_Min_alertYes').is(':checked'))
	{
		mystring = $(".minAlertpIds").text();
	}
	else if($('#threshold_Level_Max_alertYes').is(':checked'))
	{
		 mystring = $(".maxAlertpIds").text();
	}
	else
	{
		mystring = $(".pIds").text();
	}
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2];   
        var validationType = fieldtype[i].split("#")[3];
        $("#"+fieldsvalues).css("background-color","");
        errZone.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D")
            {
            	if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1" || $("#"+fieldsvalues).val()==null)
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
            else if(colType =="T" || colType =="Text")
            { 
            	if(validationType=="n")
            	{
            		// alert("validationType ::" +validationType);
		            var numeric= /^[0-9]+$/;
		            if(!(numeric.test($("#"+fieldsvalues).val())))
		            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;
		            }   
            	}
            	else if(validationType=="an")
            	{
		            var allphanumeric = /^[A-Za-z0-9 ]{3,50}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
		            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;
		            }
		           /* else if($("#status").val()==false);
		            {
		            	errZone.innerHTML="<div class='user_form_inputError2'>Enter Serial No Allready Exist</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#serialno").focus();
			            $("#serialno").css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
		            }*/
            	}
            	else if(validationType=="a")
            	{
            		
            	
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())))
		            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;    
		            }
            	}
            	else if(validationType=="m")
            	{
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
		            else if (!pattern.test($("#"+fieldsvalues).val()))
		            {
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
            	else if(validationType =="e")
            	{
            		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
            		{
            		}
            		else
            		{
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
			            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			            $("#"+fieldsvalues).focus();
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            event.originalEvent.options.submit = false;
			            break;
            		}
            	}
            	else if(validationType =="w")
            	{
                }
            	 else if(validationType =="sc"){
	            	  if($("#"+fieldsvalues).val().length < 1)
			             {
			                errZone.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			             }
	             }
            }
            else if(colType=="TextArea")
            {
	            if(validationType=="an")
	            {
		            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
		            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;
		            }
	            }
	            else if(validationType=="mg")
	            {
	            }   
            }
            else if(colType=="Time")
            {
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
            else if(colType=="Date")
            {
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


$.subscribe('validate', function(event,data)
		{
		    var mystring = $(".pIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		       
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   
		        var validationType = fieldtype[i].split("#")[3];
		        $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D")
		            {
		            	if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
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
		            else if(colType =="T" || colType =="Text")
		            { 
		            	if(validationType=="n")
		            	{
		            		// alert("validationType ::" +validationType);
				            var numeric= /^[0-9]+$/;
				            if(!(numeric.test($("#"+fieldsvalues).val())))
				            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				            }   
		            	}
		            	else if(validationType=="an")
		            	{
				            var allphanumeric = /^[A-Za-z0-9 ]{3,50}$/;
				            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
				            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				            }
				           /* else if($("#status").val()==false);
				            {
				            	errZone.innerHTML="<div class='user_form_inputError2'>Enter Serial No Allready Exist</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#serialno").focus();
					            $("#serialno").css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;   
				            }*/
		            	}
		            	else if(validationType=="a")
		            	{
		            		
		            	
				            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())))
				            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;    
				            }
		            	}
		            	else if(validationType=="m")
		            	{
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
				            else if (!pattern.test($("#"+fieldsvalues).val()))
				            {
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
		            	else if(validationType =="e")
		            	{
		            		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
		            		{
		            		}
		            		else
		            		{
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
					            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					            $("#"+fieldsvalues).focus();
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            event.originalEvent.options.submit = false;
					            break;
		            		}
		            	}
		            	else if(validationType =="w")
		            	{
		                }
		            	 else if(validationType =="sc"){
			            	  if($("#"+fieldsvalues).val().length < 1)
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
			             }
		            }
		            else if(colType=="TextArea")
		            {
			            if(validationType=="an")
			            {
				            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
				            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
				            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				            }
			            }
			            else if(validationType=="mg")
			            {
			            }   
		            }
		            else if(colType=="Time")
		            {
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
		            else if(colType=="Date")
		            {
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



$.subscribe('validateSupport', function(event,data)
{
	var mystring=null;
	if($('#reminder_forremToOther').is(':checked'))
	{
		mystring=$(".rem2otherpIds").text();
	}
	else if($('#reminder_forremToBoth').is(':checked'))
	{
		mystring=$(".rem2bothpIds").text();
	}
	else if($('#reminder_forremToSelf').is(':checked'))
	{
		mystring=$(".pIds").text();
	}
	
    var fieldtype = mystring.split(",");
    for(var i=0; i<fieldtype.length; i++)
    {
        var fieldsvalues   = fieldtype[i].split("#")[0];
        var fieldsnames    = fieldtype[i].split("#")[1];
        var colType        = fieldtype[i].split("#")[2];   
        var validationType = fieldtype[i].split("#")[3];
        $("#"+fieldsvalues).css("background-color","");
        errZone.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D")
            {
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
            else if(colType=="date")
            {
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
            else if(colType=="R")
            {
            	if($('#reminder_forremToSelf').is(':checked') || $('#reminder_forremToOther').is(':checked') || $('#reminder_forremToBoth').is(':checked'))
        	    { 
            	    
        	    }
        	 	else
        	 	{
        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Remind To"+" Value  </div>";
         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
         	$("#"+fieldsvalues).focus();
         	$("#"+fieldsvalues).css("background-color","#ff701a");
         	event.originalEvent.options.submit = false;
         	break;   
        	 	}
            	if($('#remindModeSMS').is(':checked') || $('#remindModeMail').is(':checked') || $('#remindModeBoth').is(':checked') || $('#remindModeNone').is(':checked'))
        	    { 
            	    
        	    }
        	 	else
        	 	{
        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Mode"+" Value  </div>";
         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
         	$("#"+fieldsvalues).focus();
         	$("#"+fieldsvalues).css("background-color","#ff701a");
         	event.originalEvent.options.submit = false;
         	break;   
        	 	}
             }
            else if(colType =="T")
            {
            	if(validationType=="n")
            	{
            	var numeric= /^[0-9]+$/;
            	if(!(numeric.test($("#"+fieldsvalues).val())))
            	{
            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            	$("#"+fieldsvalues).focus();
            	$("#"+fieldsvalues).css("background-color","#ff701a");
            	event.originalEvent.options.submit = false;
            	break;
            	}   
            	}
            	else if(validationType=="an")
            	{
            	var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
            	if(!(allphanumeric.test($("#"+fieldsvalues).val())))
            	{
            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            	$("#"+fieldsvalues).focus();
            	$("#"+fieldsvalues).css("background-color","#ff701a");
            	event.originalEvent.options.submit = false;
            	break;
            	}
            	}
            	else if(validationType=="a")
            	{
            	if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
            	{
            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            	$("#"+fieldsvalues).focus();
            	$("#"+fieldsvalues).css("background-color","#ff701a");
            	event.originalEvent.options.submit = false;
            	break;    
            	}
            	}
            	else if(validationType=="m")
            	{
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
            	else if (!pattern.test($("#"+fieldsvalues).val())) 
            	{
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
            	else if(validationType =="e")
            	{
            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
            	{
                	
            	}
            	else
            	{
            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            	$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            	$("#"+fieldsvalues).focus();
            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            	event.originalEvent.options.submit = false;
            	break;
            	}
            	}
            	else if(validationType =="w")
            	{
            
            	}
            	else if(validationType =="sc"){
	            	  if($("#"+fieldsvalues).val().length < 1)
			             {
			                errZone.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			             }
	             }
            }
            else if(colType=="TA")
            {
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{1,100}$/;
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
            else if(colType=="time"){
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
            else if(colType=="date")
            {
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
            else if(colType=="C")
            {
            	if(document.formone.recvSMS.checked==true && document.formone.recvEmail.checked==true)
            	{
 	            if($("#feed_by_mobno").val().length > 10 || $("#feed_by_mobno").val().length < 10)
 	            {
 	                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
 	                $("#feed_by_mobno").focus();
            $("#feed_by_mobno").css("background-color","#ff701a");
 	                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 	                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 	                event.originalEvent.options.submit = false;
 	                break;
 	            }
 	            else if (!pattern.test($("#feed_by_mobno").val())) {
 	                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
 	                $("#feed_by_mobno").focus();
            $("#feed_by_mobno").css("background-color","#ff701a");
 	                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 	                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 	                event.originalEvent.options.submit = false;
 	                break;
 	             }
 	             else if(($("#feed_by_mobno").val().charAt(0)!="9") && ($("#feed_by_mobno").val().charAt(0)!="8") && ($("#feed_by_mobno").val().charAt(0)!="7") && ($("#feed_by_mobno").val().charAt(0)!="6"))
 	             {
 	                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
 	                $("#feed_by_mobno").focus();
            $("#feed_by_mobno").css("background-color","#ff701a");
 	                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 	                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 	                event.originalEvent.options.submit = false;
 	                break;
 	             }
 	             else if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val())){
             }else{
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#feed_by_emailid").css("background-color","#ff701a");  //255;165;79
            $("#feed_by_emailid").focus();
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            event.originalEvent.options.submit = false;
            break;
     }
            	}
            	else if(document.formone.recvSMS.checked==true)
            	{
            	if($("#feed_by_mobno").val().length > 10 || $("#feed_by_mobno").val().length < 10)
 	            {
 	                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
 	                $("#feed_by_mobno").focus();
            $("#feed_by_mobno").css("background-color","#ff701a");
 	                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 	                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 	                event.originalEvent.options.submit = false;
 	                break;
 	            }
 	            else if (!pattern.test($("#feed_by_mobno").val())) {
 	                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
 	                $("#feed_by_mobno").focus();
            $("#feed_by_mobno").css("background-color","#ff701a");
 	                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 	                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 	                event.originalEvent.options.submit = false;
 	                break;
 	             }
 	             else if(($("#feed_by_mobno").val().charAt(0)!="9") && ($("#feed_by_mobno").val().charAt(0)!="8") && ($("#feed_by_mobno").val().charAt(0)!="7") && ($("#feed_by_mobno").val().charAt(0)!="6"))
 	             {
 	                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
 	                $("#feed_by_mobno").focus();
            $("#feed_by_mobno").css("background-color","#ff701a");
 	                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 	                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 	                event.originalEvent.options.submit = false;
 	                break;
 	             }
            	}
            	else if(document.formone.recvEmail.checked==true)
            	{
            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val()))
                	{
            }
            else
        {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#feed_by_emailid").css("background-color","#ff701a");  //255;165;79
            $("#feed_by_emailid").focus();
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            event.originalEvent.options.submit = false;
            break;
    }
            	}
            }
        }
    } 
    
    if(event.originalEvent.options.submit != false)
    { 
    	if($("#escalation").val()!='' && $("#escalation").val()=='Y')
         {
    	if($("#escalation_level_1").val()==null || $("#escalation_level_1").val()=="" || $("#escalation_level_1").val()=="-1")
    	{
    	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Escalation L1 Value from Drop Down </div>";
            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            	$("#escalation_level_1").focus();
            	$("#escalation_level_1").css("background-color","#ff701a");
            	event.originalEvent.options.submit = false;
    	}
    	else if($("#l1EscDuration").val()==null || $("#l1EscDuration").val()=="")
    	{
    	errZone.innerHTML="<div class='user_form_inputError2'>Please L1 Esc Duration </div>";
            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            	$("#l1EscDuration").focus();
            	$("#l1EscDuration").css("background-color","#ff701a");
            	event.originalEvent.options.submit = false;
    	}
      }
    }
});