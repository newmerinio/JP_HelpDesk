function resetForm(formId)
{
    $('#'+formId).trigger("reset");
     $("#formDiv").html("");
}

function checkFormValue(val1){
	$("#clientId").attr("onblur","");
	$("#compType").attr("onchange","");
	if(val1=='Patient')
	{
		$("#clientId").css('display','block')
		.css('margin-top','-28px')
		.css('margin-left','231px');
		$("#clientId").attr("onblur","getFullDetail('clientId')");
	}
	else
	{
		document.getElementById("clientId").style.display="none";
		$("#compType").attr("onchange","getFullDetail('clientId')");
	}
	
}

function getFullDetail(patId)
{
      var patientId = document.getElementById(patId).value;
      var patType= document.getElementById("compType").value;
      var visitType= document.getElementById("visitType").value;
      if(visitType=='-1')
      {
    	  $("#errZone").html("Please Select Visit Type.");
		  setTimeout(function(){ $("#err").fadeIn(); }, 10);
          setTimeout(function(){ $("#err").fadeOut(); }, 4000);
      }
      else if(patType=='-1')
      {
    	  $("#errZone").html("Please Select Patient Type.");
		  setTimeout(function(){ $("#err").fadeIn(); }, 10);
          setTimeout(function(){ $("#err").fadeOut(); }, 4000);
      }
      if (document.getElementById(patId).value=='' && visitType=='Patient')
      {
    	  $("#errZone").html("Please Enter MRD No.");
		  setTimeout(function(){ $("#err").fadeIn(); }, 10);
          setTimeout(function(){ $("#err").fadeOut(); }, 4000);
      }  
      else
      {
    	  $("#patientDetialsViewDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	          type : "post",
	          url : "view/Over2Cloud/feedback/getDataForIdInJson.action?patientId="+patientId+"&patType="+patType+"&visitType="+visitType,
	          success : function(subdeptdata) 
	          {
	              $("#"+"patientDetialsViewDiv").html(subdeptdata);
	          },
	         error: function() 
	         {
	             alert("error");
	         }
	       });
    	} 
}

$.subscribe('validateForm', function(event,data)
{
     $("#choseHospital").css("background-color","#ffffff");
    if($("#formcheck").val()=='checkedform')
    {
        var mystring = $(".pIds").text();
       // alert(mystring);
        var fieldtype = mystring.split(",");
        
        var pattern = /^\d{10}$/;
       
        for(var i=0; i<fieldtype.length; i++)
        {
            var fieldsvalues = fieldtype[i].split("#")[0];
            var fieldsnames = fieldtype[i].split("#")[1];
            var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
            var validationType = fieldtype[i].split("#")[3];
           
            var value1=document.getElementsByName("targetOn");
            var value2=document.getElementsByName("overAll");
            
            // chk date with time 
           // alert("discharge");	
            //alert("time "+$("#discharge_datetime").val().length);
            var taille = $("#discharge_datetime").val();
            if (taille!='NA' && typeof taille!='undefined')
            {
            	//alert(taille.indexOf(":"));
            if (taille.indexOf(":")<0)
            {
                //alert("time chk");
                  errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Discharge Date with Time </div>";
                  setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                  setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                  $("#discharge_datetime").focus();
                  $("#discharge_datetime").css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;
            }
            }
            if ($("#choseHospital").val()==0||$("#choseHospital").val()==null)
            {
                //alert("choose hospital");
                  errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Choose Us Because Of Field </div>";
                  setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                  setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                  $("#choseHospital").focus();
                  $("#choseHospital").css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;
            }
            alert(value1[0]);
            if (value1[0].checked==false&&value1[1].checked==false) {
            	 // alert("2222222222222222222");
                  errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Recommend Us To Other Field </div>";
                  setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                  setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                 $("#targetOn").focus();
                  $("#targetOn").css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;
            }
            /*if (value2[0].checked==false&&value2[1].checked==false) {
            	//alert("33333333333333333333");
                  errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Remarks Field </div>";
                    setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                 $("#overAll").focus();
                  $("#overAll").css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;
            }*/
            if(fieldsvalues!= "" )
            {
            	
            }
            else
            {
                $("#confirmEscalationDialog").dialog('open');
                $("#confirmEscalationDialog").dialog({title:'Feedback Form Opening Status'});
            }
        }
        //alert("idlhgdogh;ouih");
    }else
    {
    	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Data Before Submitting  </div>";
        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        $("#patientDetialsViewDiv").focus();
        event.originalEvent.options.submit = false;
        
    }
            
});