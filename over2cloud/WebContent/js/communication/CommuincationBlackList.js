function addBlackList() {
	
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeBlackListAdd.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
}
function changeDateRange(value,div)
{
	if(value=='Period')
	{
		$("#"+div).show();
	}
	else
	{
		$("#"+div).hide();
	}
}
function excelUpload()
{
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#data_part").load("view/Over2Cloud/CommunicationOver2Cloud/blacklist/BlackListExcelUpload.jsp");	
}

function downloadGridData()
{
	alert("bkgh");
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeBlackListGridDataDownload.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function viewBlackList() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeBlackListView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}
function fetchContact(value,div)
{
	//alert(country);
	//alert(div);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getContforBlack.action?subGroupId="+value,
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select Contact Name'));
	$(data).each(function(index)
	{
	   $('#'+div).append($("<option></option>").attr("value",this.id).text(this.name));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function showGrid() {
	
	$("#BlacklistBasicDialog").dialog('open'); 
	$("#BlacklistBasicDialog").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/showExcel.action",
	    success : function(subdeptdata) {
		
       $("#BlacklistBasicDialog").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}


$.subscribe('varifyFile', function(event,data)
		{
		 var fileName = upload_BL_text.text.value;
		 if(fileName.length >= 1)
		 {
		 parts = fileName.substring(fileName.lastIndexOf("."));
		 if (parts!= ".txt")
		  {
		   alert ("Input File Must Be a Text File (.txt)");
		   return false;
		  }
		  }
		 if(upload_BL_text.text.value=="")
		 {
			 txtError.innerHTML="<div class='user_form_inputError2'>Select A Text File</div>";
			  document.upload_BL_text.text.focus();
		     event.originalEvent.options.submit = false;
		 }
		 else
		 {
		      txtError.innerHTML="";
			  $('#bLTextDialog').dialog('open');
		     event.originalEvent.options.submit = true;

		 }
		  return true;
		});


$.subscribe('validateExcel', function(event,data)
        {
       var status=false;
		if(document.upload_BL_excel.excel.value!="")
        {
      	  temp=document.upload_BL_excel.excel.value.split(".");
      	  if(temp[1]!="xls" && temp[1]!="xlsx")
      	  {
      	  excelError.innerHTML="<div class='user_form_inputError2'>Select only xls file</div>";
      	  document.upload_BL_excel.excel.focus();
      	  status = false;
      	  }
      	  else
      	  {
      		  excelError.innerHTML="";
      		  status = true;
      	  }
        }
          if(document.upload_BL_excel.excel.value=="")
          {
        	  excelError.innerHTML="<div class='user_form_inputError2'>Select An Excel</div>";
        	  document.upload_BL_excel.excel.focus();
    	      event.originalEvent.options.submit = false;
          }
          else
          {
        	  if
        	  (status)
        	  {
        	  excelError.innerHTML="";
        	  $('#ASExcelDialog').dialog('open');
    	      event.originalEvent.options.submit = true;
        	  }
          }
        });  

