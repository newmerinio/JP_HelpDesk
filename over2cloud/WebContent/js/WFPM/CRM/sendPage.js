
function fetchTemplate(val,divId)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/CRM/fetchTemplate.action?templateType="+val,
	    success : function(Data){
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("--Select Template--"));
	    	$(Data.templateJSONArray).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		    },
	   error: function() {
	        alert("error");
	    }
	 });
}

function getMessageText(message)
{
	  var rootvalue= $("#root").val();
	  if(rootvalue=='Trans'){
		  $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/WFPM/CRM/templateSubmission.action?id="+message,
			    success : function(subdeptdata) {
			    $("#divid").hide();
			    $("#destAjaxDiv").show();
		        $("#"+"destAjaxDiv").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
    	  }
	  else{
    	$.getJSON("view/Over2Cloud/WFPM/CRM/fetchMessage.action?message="+message,
      function(data){
    		  $("#divid").show();
    		  $("#destAjaxDiv").hide();
            $("#writeMessage").val(data.messageName);
            var i=document.getElementById("writeMessage").value.length;
            $("#remLen").val(i);
    	  });
	  }
}

function textCounter(field, countfield, maxlimit) {    //162    160
	
	if (document.formtwo.writeMessage.value.length > 0) {
		var a = document.formtwo.writeMessage.value;
		var iChars = "!@$%^&*()+=-[]';,{}|\":?";
		for ( var i = 0; i < a.length; i++) {
			if (iChars.indexOf(a.charAt(i)) != -1) {
				errZone.innerHTML = "<div class='user_form_inputError2'>Only alphabets are allowed. </div>";
				document.formtwo.writeMessage.value = "";
			} else 
			{
				errZone.innerHTML="";
		    	}
		  }
	   }
	
	if(field.value.length <= maxlimit)
	{
		countfield.value = field.value.length;
	}
	else
	{
		var a = Math.floor(field.value.length / maxlimit);
		var b = maxlimit * a;
		var c = field.value.length - b;
		countfield.value = c;
		countfield.value += "/";
		countfield.value += Math.floor(field.value.length / maxlimit);
	}
}

function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}

function checkMobNo()
{
	var csvNum = document.formtwo.mobileNo.value;
	var newtext = csvNum.split(","); 
	for(var i=0 ;i < newtext.length; i++) 
	{ 
		var mobileNum = checkNumber(newtext[i]);
		if (!mobileNum) 
		{ 
			errZone.innerHTML="<div class='user_form_inputError2'>Please enter comma seperated mobile Number.</div>";
			document.formtwo.mobileNo.value="";
			//document.formtwo.mobileNo.focus();
			return false;
		}
	} 
	errZone.innerHTML="";
	return true;
}

function cancelMe()
{
	$("#data_part").load("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load("view/Over2Cloud/WFPM/CRM/beforeCRMPage.action");
}

function putValues(ref)
{
	$("#DIV"+ref.name).html(" "+ref.value+" ");
	$("#writeMessage").val($("#xxxxxxxxx").text().replace(/^(\s*)|(\s*)$/g, '').replace(/\s+/g, ' '));
}

