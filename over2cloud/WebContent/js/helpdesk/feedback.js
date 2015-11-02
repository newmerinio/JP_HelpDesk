
function Reset(id)
{
	var mystring=$(id).text();
    var fieldtype = mystring.split(",");
      for(var i=0; i<fieldtype.length; i++)
    {
       
        var fieldsvalues = fieldtype[i].split("#")[0];
        
        $("#"+fieldsvalues).css("background-color","");
    }

}
      function getDetail(uniqueid,columnName)
      {
          
      	var uniqueid = document.getElementById(uniqueid).value;
      	if(uniqueid!=null && uniqueid!="")
      	   

      	     $.getJSON("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/ContactDetailViaAjax.action?uid="+uniqueid+"&pageFor=SD&columnName="+columnName,
          	 
          		   function(data){
                     if(data.empName == null){$("#feed_by").val("NA");}
                     else {$("#feed_by").val(data.empName);}
                     
                     if(data.mobOne == null){$("#feed_by_mobno").val("NA");}
                     else {$("#feed_by_mobno").val(data.mobOne); }
                     
                     if(data.emailIdOne == null){$("#feed_by_emailid").val("NA");}
                     else {$("#feed_by_emailid").val(data.emailIdOne);}
                     
                     if(data.empId == null){$("#uniqueid").val("NA");}
                     else {$("#uniqueid").val(data.empId);}
                       
                     if (data.deptName ==null || data.deptName =='' || data.deptName =='NA') {
                  	   
                  	  //  document.getElementById('fielddiv').style.display="none";
              		 //  document.getElementById('selectdiv').style.display="block";
              		   $("#deptname").val("NA");
                  	   $("#bydept_subdept").val("NA");
      			   }
                     else {
                  	//  document.getElementById('fielddiv').style.display="block";
              		  // document.getElementById('selectdiv').style.display="none"; 
              		    $("#deptname").val(data.deptName);
                  	   $("#bydept_subdept").val(data.other2);
      			   }
             });
      }
$.subscribe('validateCompletionTip', function(event,data)
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
						             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
						             }
						             else{
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

function getUserByDept(conditionVar_Value,targetid,frontVal) {
	//alert("module  "+conditionVar_Value2);
	 	$.ajax
	 (
	   {
		type :"post",
		url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/getUserByDept.action?conditionVar_Value="+conditionVar_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	   }
	 ); 
  }




function getFeedBreifViaAjax(subCatg)
{
     // var subCatg = document.getElementById(subCatgId).value;
      $("#scatgid").val(subCatg);
      $("#test").val(subCatg);
       $.getJSON("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/SubCatgDetail.action?subcatg="+subCatg,
    	 function(data){
        	   $("#subCatgId").val(data.id);
    	   
               if(data.feed_msg == null){
            	   $("#feed_brief").val("NA");
               }
               else {
            	   $("#feed_brief").val(data.feed_msg);
			   }
               
               if(data.addressing_time == null){
            	   $("#resolutionTime").val("00:30");
               }
               else {
            	   $("#resolutionTime").val(data.resolution_time);
			   }
               
               if(data.escalation_time == null){
            	   $("#escalationTime").val("02:00");
               }
               else {
            	   $("#escalationTime").val(data.resolution_time);
			   }
       });
}


function viewForm()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus=Pending&moduleName=HDM",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function backOnViewActivityBoard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function callForm()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaCall.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=call&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function onlineForm()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaOnline.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=online&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}


$.subscribe('validateOnline', function(event,data)
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
						             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
						             }
						             else{
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
		    if(event.originalEvent.options.submit != false)
		    {
		    	$("#ButtonDiv").hide();
		    	$("#confirmEscalationDialog").dialog('open');
		    	$("#confirmEscalationDialog").dialog({title:'Action Status'});
		    	setTimeout(function(){ $("#confirmEscalationDialog").dialog('close'); }, 10000);
		    }
		});


$.subscribe('validateCall', function(event,data)
		{
			var mystring=null;
			if($("#deptname1").val()=='' || $("#deptname1").val()=='NA')
			{
				mystring=$(".ddPids").text();
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
		             var allphanumeric = /^[A-Za-z0-9 ]{1,80}$/;
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
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA'){
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
		             else if(validationType =="sc"){
		            	  if($("#"+fieldsvalues).val().length < 1)
				             {
				                errZone.innerHTML="<div class='user_form_inputError2'>Please Feel Form Using Search Option !!! </div>";
				                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				                event.originalEvent.options.submit = false;
				                break;
				             }
		             }
		           }
		          
		            else if(colType=="TextArea"){
		            if(validationType=="an"){
		            var allphanumeric = /^[A-Za-z0-9 ]{3,80}$/;
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
		 		                errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Mobile Number Start with 9,8,7,6.  </div>";
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
		            		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val())){
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
		            }
		        }
		    }
		    if(event.originalEvent.options.submit != false)
		    {
		    	$("#ButtonDiv").hide();
		    	$("#printSelect").dialog('open');
		    	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		    }
		});