function adddiv1(divID)
{
  document.getElementById(divID).style.display="block";
	$("#extraDiv1").show();
}

function deletediv1(divID)
{
  document.getElementById(divID).style.display="none";
}

function adddiv2(divID)
{
  document.getElementById(divID).style.display="block";
	$("#extraDiv1").show();
}

function deletediv2(divID)
{
  document.getElementById(divID).style.display="none";
}


function adddiv3(divID)
{
  document.getElementById(divID).style.display="block";
	$("#extraDiv1").show();
}

function deletediv3(divID)
{
  document.getElementById(divID).style.display="none";
}

$.subscribe('validate1', function(event,data)
		{	
			validate1(event,data,"pIds1");
		});

function validate1(event,data, spanClass)
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
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="ans"){
            var allphanumeric = /^[ A-Za-z0-9_@.#&+-]*$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="a"){
            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
             }
            else if(validationType=="m"){
           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
            {
                errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
            }
            else if (!pattern.test($("#"+fieldsvalues).val())) {
                errZone1.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
             }
                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
             {
                errZone1.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
               }
             }
             else if(validationType =="e"){
             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
             }else{
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            $("#"+fieldsvalues).focus();
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
            event.originalEvent.options.submit = false;
            break;
               }
             }
             else if(validationType =="td"){
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
             } else if(validationType =="num"){

                 if($("#"+fieldsvalues).val().length != 0 && $("#"+fieldsvalues).val().length > 11)
                  {
                      errZone1.innerHTML="<div class='user_form_inputError2'>Enter atmost 10 digit number ! </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                  }
                  else if (!pattern2.test($("#"+fieldsvalues).val())) {
                      errZone1.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                   }
             }
             else if(validationType =="num2"){

                 if ($("#"+fieldsvalues).val().length > 0 && !pattern2.test($("#"+fieldsvalues).val())) {
                    errZone1.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                    $("#"+fieldsvalues).focus();
                    setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                    event.originalEvent.options.submit = false;
                    break;
                 }
           }
           }
          
            else if(colType=="TextArea"){
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }   
            }
            else if(colType=="Date"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }
             } 
        }
    }      
}

$.subscribe('validate2', function(event,data)
		{	
			validate22(event,data,"pIds2");
		});

function validate22(event,data, spanClass)
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
        errZone2.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D"){
            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
            {
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
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
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }   
             }
            else if(validationType=="an"){
             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="ans"){
            var allphanumeric = /^[ A-Za-z0-9_@.#&+-]*$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="a"){
            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
             }
            else if(validationType=="m"){
           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
            {
                errZone2.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
            }
            else if (!pattern.test($("#"+fieldsvalues).val())) {
                errZone2.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
             }
                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
             {
                errZone2.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
               }
             }
             else if(validationType =="e"){
             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
             }else{
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            $("#"+fieldsvalues).focus();
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
            event.originalEvent.options.submit = false;
            break;
               }
             }
             else if(validationType =="td"){
            	 if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
                 {
                 errZone2.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
                 setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
                 setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
                 $("#"+fieldsvalues).focus();
                 $("#"+fieldsvalues).css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;   
                   }
             }
             else if(validationType =="num"){
                 if($("#"+fieldsvalues).val().length != 0 && $("#"+fieldsvalues).val().length > 11)
                  {
                      errZone2.innerHTML="<div class='user_form_inputError2'>Enter atmost 10 digit number ! </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                  }
                  else if (!pattern2.test($("#"+fieldsvalues).val())) {
                      errZone2.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                   }
             }
             else if(validationType =="num2"){
                 if ($("#"+fieldsvalues).val().trim().length <= 0 || !pattern2.test($("#"+fieldsvalues).val())) {
                    errZone2.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                    $("#"+fieldsvalues).focus();
                    setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
                    setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
                    event.originalEvent.options.submit = false;
                    break;
                 }
           }
           }
          
            else if(colType=="TextArea"){
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
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
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }   
            }
            else if(colType=="Date"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone2.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
            setTimeout(function(){ $("#errZone2").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone2").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }
             } 
        }
    }      
}


$.subscribe('validate3', function(event,data)
		{	
			validate3(event,data,"pIds3");
		});

function validate3(event,data, spanClass)
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
        
       // alert(fieldsvalues+fieldsnames+colType+validationType+spanClass);
        errZone3.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D"){
            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
            {
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
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
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }   
             }
            else if(validationType=="an"){
             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="ans"){
            var allphanumeric = /^[ A-Za-z0-9_@.#&+-]*$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="a"){
            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
             }
            else if(validationType=="m"){
           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
            {
                errZone3.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
            }
            else if (!pattern.test($("#"+fieldsvalues).val())) {
                errZone3.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
             }
                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
             {
                errZone3.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
               }
             }
             else if(validationType =="e"){
             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
             }else{
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            $("#"+fieldsvalues).focus();
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
            event.originalEvent.options.submit = false;
            break;
               }
             }
             else if(validationType =="td"){
            	 if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
                 {
                 errZone3.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
                 setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
                 setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
                 $("#"+fieldsvalues).focus();
                 $("#"+fieldsvalues).css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;   
                   }
             }
             else if(validationType =="num"){

                 if($("#"+fieldsvalues).val().length == 0 || $("#"+fieldsvalues).val().length > 11)
                  {
                      errZone3.innerHTML="<div class='user_form_inputError2'>Enter atmost 10 digit number ! </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                  }
                  else if (!pattern2.test($("#"+fieldsvalues).val())) {
                      errZone3.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                      $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                      $("#"+fieldsvalues).focus();
                      setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
                      setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
                      event.originalEvent.options.submit = false;
                      break;
                   }
             }
             else if(validationType =="num2"){

                 if ($("#"+fieldsvalues).val().length > 0 && !pattern2.test($("#"+fieldsvalues).val())) {
                    errZone3.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
                    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                    $("#"+fieldsvalues).focus();
                    setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
                    setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
                    event.originalEvent.options.submit = false;
                    break;
                 }
           }
           }
          
            else if(colType=="TextArea"){
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
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
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }   
            }
            else if(colType=="Date"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone3.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
            setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;   
              }
             } 
        }
    }      
}
