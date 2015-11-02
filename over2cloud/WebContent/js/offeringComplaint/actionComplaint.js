$.subscribe('validateAction', function(event,data)
{
	var feedstatus=document.getElementById('feedStatus').value;
			var mystring=null;
			var status_new=$("#old_status").val();
			if($("#status").val()!='' && $("#status").val()=='Resolved')
			{
				mystring=$(".reSpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='Snooze')
			{
				mystring=$(".sZpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='High Priority')
			{
				mystring=$(".hPpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='Ignore')
			{
				mystring=$(".iGpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='Re-Assign')
			{
				mystring=$(".reAssignPIds").text();
			}
			else
			{
				mystring=$(".pIds").text();
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
				            var allphanumeric = /^[A-Za-z0-9 ]{1,80}$/;
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
				            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val())))
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
					            var allphanumeric = /^[A-Za-z0-9 ]{3,80}$/;
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
		    if(event.originalEvent.options.submit != false)
		    {
				$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/beforeComplaintAction.action?feedStatus="+status_new+"&moduleName=DREAM_HDM",
				    success : function(data) 
				    {
						$("#"+"data_part").html(data);
				    },
				    error: function() 
				    {
			            alert("error");
			        }
				 });
				
		    }
		});
function getFeedbackStatus(statusId,div)
{
	var statusType=$("#"+statusId).val();

	 if(statusType=='High Priority')
	  {
		showHideFeedDiv('highPriorityDiv','snoozeDiv','resolvedDiv','ignoreDiv','reAssign');
	  }

	if(statusType=='Snooze')
	  {
		showHideFeedDiv('snoozeDiv','highPriorityDiv','resolvedDiv','ignoreDiv','reAssign');
	  }

	if(statusType=='Ignore')
	  {
	      showHideFeedDiv('ignoreDiv','highPriorityDiv','resolvedDiv','snoozeDiv','reAssign');
	  }	  
	
	if(statusType=='Resolved')
	  {
	    var	feedId=$("#feedid").val();
        showHideFeedDiv('resolvedDiv','highPriorityDiv','snoozeDiv','ignoreDiv','reAssign');
        $.ajax({
	           type : "post",
	           url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/getResolver.action?feedid="+feedId,
	           success : function(data) 
	           {
	        	$('#'+div+' option').remove();
	   			$('#'+div).append($("<option></option>").attr("value",-1).text('Select Resolver'));
	   			$(data).each(function(index)
	   			{
	   			   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	   			});
	           },
	  	      error: function() 
	  	      {
         			alert("error");
     		  }
	        });
	   }

	if(statusType=='Re-Assign')
    {
	    var feedStatus = 'reAllot';
         showHideFeedDiv('reAssign','highPriorityDiv','resolvedDiv','snoozeDiv','ignoreDiv');
             $.ajax({
             type : "post",
             url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/reAllotTicket.action?feedStatus="+feedStatus+"&dataFor=HDM",
             success : function(feeddata) {
             $("#reAssign").html(feeddata);
            },
            error: function()
             {
               alert("error");
             }
         });
    }
}
function getRCAName(status,module,div)
{
	if (status=='Resolved') 
	{
		 var module=$("#moduleName").val();
		 var feedId=$("#feedid").val();
		 $.ajax({
	          type : "post",
	          url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/getRCAData.action?feedid="+feedId+"&moduleName="+module,
	          success : function(data) 
	          {
	        	$('#'+div+' option').remove();
	  			$('#'+div).append($("<option></option>").attr("value",-1).text('Select RCA'));
	  			$(data).each(function(index)
	  			{
	  			   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	  			});
	          },
	 	      error: function() 
	 	      {
	    			alert("error");
			  }
	       });
	}
}
function showHideFeedDiv(showDiv,hideDiv1,hideDiv2,hideDiv3,hideDiv4)
{
	if($("#"+showDiv).css('display') == 'none')
		$("#"+showDiv).show('slow');
	 
	if($("#"+hideDiv1).css('display') != 'none')
		$("#"+hideDiv1).hide('slow');

	if($("#"+hideDiv2).css('display') != 'none')
	$("#"+hideDiv2).hide('slow');
	
    if($("#"+hideDiv3).css('display') != 'none')
	$("#"+hideDiv3).hide('slow');

    if($("#"+hideDiv4).css('display') != 'none')
        $("#"+hideDiv4).hide('slow');
}
function viewPendingComplaint()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/beforeComplaintAction.action?feedStatus=Pending&moduleName=DREAM_HDM",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}