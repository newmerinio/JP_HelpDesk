function fetchLevelData(val,selectId,Offeringlevel)
{
	//alert(val.value);
	//alert(Offeringlevel);
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/fetchOfferingLevelData.action?offeringId="+val.value+"&Offeringlevel="+Offeringlevel,
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
	 var refdata=$("#referedBy").val(); 
	
     if(refdata != null && refdata != '-1' )
     {
         document.getElementById("refNameId").style.display="block";
     }
	
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
          setTimeout(function(){ $("#leadgntion").fadeIn(); }, 10);
          setTimeout(function(){ $("#leadgntion").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
          fillName('Client','clientNa',0);
          fillName('Client','clientN',0);
});
$.subscribe('dd', function(event,data)
        {
          setTimeout(function(){ $("#pAssociate").fadeIn(); }, 10);
          setTimeout(function(){ $("#pAssociate").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });
$.subscribe('form2', function(event,data)
        {
          setTimeout(function(){ $("#orglevel33").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel33").fadeOut(); cancelButton(); }, 6000);
          $('select').find('option:first').attr('selected', 'selected');
        });

$.subscribe('validate1', function(event,data)
{
	validate(event,data,"pIds");
});
$.subscribe('validate2', function(event,data)
{
	validate(event,data,"qIds");
	$("#completionResult").dialog('open');
});
$.subscribe('validate3', function(event,data)
{
	validate(event,data,"rIds");
});
function validate(event,data, spanClass)
	{	
		var mystring=$("."+spanClass).text();
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
	}

function viewClient()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/client/beforeClientView.action",
	    data : "isExistingClient=${isExistingClient}",
	    success : function(subdeptdata) {
	   $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	}