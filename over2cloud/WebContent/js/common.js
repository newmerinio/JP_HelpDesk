// Method for getting the location detail on office type basis
function getLocationViaAjax(destLocationAjaxDiv,destBranchAjaxDiv,officeTypeId,comId)
{
	var officeyType=$("#"+officeTypeId).val();
    var comVal=$("#"+comId).val();
    
	if(officeyType=='1' || officeyType=='2')
	 {
	   if($("#"+destLocationAjaxDiv).css('display') == 'none')
		  {
			 $("#"+destLocationAjaxDiv).show('slow');
		  }
		
		 if(officeyType=='1')
		  {
			 if($("#"+destBranchAjaxDiv).css('display') == 'none')
			 {
				 $("#"+destBranchAjaxDiv).show('slow');
			 }
		  }
		
		 if(officeyType=='2')
		 {
			 if($("#"+destBranchAjaxDiv).css('display') != 'none')
			 {
				 $("#"+destBranchAjaxDiv).hide('slow');
			 }
		 }
		 
		 if($("#"+destLocationAjaxDiv).css('display') != 'none' ||$("#"+destBranchAjaxDiv).css('display') != 'none')
		   {
			 $.ajax({
				    type : "post",
				    url : "/sfa/view/admin_pages/common_admin/officeLocationAjax.action?companyId="+comVal+"&officeType="+officeyType+"&branchAjaxDiv="+destBranchAjaxDiv,
				    success : function(officeLocationData) {
			        $("#"+destLocationAjaxDiv).html(officeLocationData);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			 
		 }
		 
		 
	 }
	 
	else if(officeyType=='3')
	 {
		 if($("#"+destLocationAjaxDiv).css('display') != 'none')
		  { 
			 $("#"+destLocationAjaxDiv).hide('slow');
		  }
		 if($("#"+destBranchAjaxDiv).css('display') != 'none')
		  {
			 $("#"+destBranchAjaxDiv).hide('slow');
		  }
	 }
	else if(officeyType=='-1')
	 {
		 if($("#"+destLocationAjaxDiv).css('display') != 'none')
		  {
			 $("#"+destLocationAjaxDiv).hide('slow');
		  }
		
		 if($("#"+destBranchAjaxDiv).css('display') != 'none')
		  {
				 $("#"+destBranchAjaxDiv).hide('slow');
		  }
	 }
}

// Get Branch detail on the basis Location ID	
function getBranchViaAjax(destBranchAjaxDiv,locationId,officeyTypeId)
	 {
		if(officeyTypeId=='1')
		{
			var location=$("#"+locationId).val();
			$.ajax({
					    type : "post",
					    url : "/sfa/view/admin_pages/common_admin/officeBranchAjax.action?locationId="+location+"&officeType="+officeyTypeId+"&destBranchAjaxDiv="+destBranchAjaxDiv,
					    success : function(officeBranchData) {
				        $("#"+destBranchAjaxDiv).html(officeBranchData);
					},
					   error: function() {
				            alert("error");
				        }
					 });
		  }		 
				 
	   }
			 	 
	
// Method for getting the department detail on the basis of Head Office Level
function getDeptAtHeadViaAjax(destDeptAjaxDiv,officeTypeId,comId)
	 {
	 	var officeyType=$("#"+officeTypeId).val();
	 	var comVal=$("#"+comId).val();
	 	if(officeyType=='3')
	 	 {
	 		   $.ajax({
	 				    type : "post",
	 				    url : "/sfa/view/admin_pages/common_admin/deptAjax.action?companyId="+comVal+"&officeType="+officeyType+"&deptAjaxDiv="+destDeptAjaxDiv,
	 				    success : function(officeDeptData) {
	 			        $("#"+destDeptAjaxDiv).html(officeDeptData);
	 				},
	 				   error: function() {
	 			            alert("error");
	 			        }
	 				 });
	 	 }
	 }


// Method  for getting Departments detail normally
function getDeptViaAjax(deptAjaxDiv)
	{
		$.ajax({
		    type : "post",
		    url : "/sfa/view/admin_pages/common_admin/DeptAjax.action?",
		    success : function(deptdata) {
	        $("#"+deptAjaxDiv).html(deptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		
	}


// Method for getting Sub Department detail on the basis of Department ID 
function getSubDeptViaAjax(sourceAjaxDiv,destAjaxDiv,deptId)
{
	var dept=document.getElementById(deptId).value;
	$.ajax({
	    type : "post",
	    url : "/sfa/view/admin_pages/common_admin/SubDeptAjax.action?dept="+dept+"&destination="+destAjaxDiv+"&source="+sourceAjaxDiv,
	    success : function(subdeptdata) {
        $("#"+sourceAjaxDiv).html(subdeptdata);
       
	},
	   error: function() {
            alert("error");
        }
	 });
	
}

// Method for getting the availability for particular data on the basis of particular field
function getAvailableViaAjax(fieldId,destAjaxDiv,destStatus)
 {
	var fieldValue=document.getElementById(fieldId).value;
	document.getElementById("indicator2").style.display="block";
     $.getJSON("/sfa/view/admin_pages/common_admin/checkAvailabilty.action?searchField="+fieldValue,
		function(checkdata){
    	     $("#"+destAjaxDiv).html(checkdata.msg);
    	     document.getElementById("indicator2").style.display="none";
        	document.getElementById(destStatus).value=checkdata.status;
    });
}


// Method for getting the designation detail on the basis of different parameters
function getDesgnationViaAjax(destDesgAjaxDiv,officeTypeId,comId)
	 {
	 	var officeType=$("#"+officeTypeId).val();
	 		   $.ajax({
	 				    type : "post",
	 				    url : "/sfa/view/admin_pages/common_admin/desgnationViaAjax.action?office="+officeType,
	 				    success : function(desgnationData) {
	 			        $("#"+destDesgAjaxDiv).html(desgnationData);
	 				},
	 				   error: function() {
	 			            alert("error");
	 			        }
	 				 });
	 }


// Method for getting the Dept Detail on the basis of location
function getDeptAtLocationViaAjax(destDeptAjaxDiv,locationId,officeTypeId)
   {
	 if(officeTypeId=='2')
		{
		  var locationType=$("#"+locationId).val();
	 		   $.ajax({
	 				    type : "post",
	 				    url : "/sfa/view/admin_pages/common_admin/deptAjax.action?locationId="+locationType+"&officeType="+officeTypeId+"&deptAjaxDiv="+destDeptAjaxDiv,
	 				    success : function(locationDeptData) {
	 			        $("#"+destDeptAjaxDiv).html(locationDeptData);
	 				},
	 				   error: function() {
	 			            alert("error");
	 			        }
	 				 });
		}    
	}


// Method for getting the department detail on the branch and office ID
function getDeptViaAjax(destDeptAjaxDiv,branchId,officeTypeId)
  {
	if(officeTypeId=='1')
	  {
	 	var branch=$("#"+branchId).val();
	 		$.ajax({
	 				 type : "post",
	 				 url : "/sfa/view/admin_pages/common_admin/deptAjax.action?branchId="+branch+"&officeType="+officeTypeId+"&deptAjaxDiv="+destDeptAjaxDiv,
	 				 success : function(officeDeptData) {
	 				 $("#"+destDeptAjaxDiv).html(officeDeptData);
	 				},
	 				error: function() {
	 				            alert("error");
	 				}
	 				});
	 		}		 
	 	}



// Method for getting Sub Department detail on the basis of department ID
function getSubDeptViaAjax(destSubDeptAjaxDiv,deptId)
	 {
	 	var deptKeyId =$("#"+deptId).val();
	 	$.ajax({
	 			 type : "post",
	 			 url : "/sfa/view/admin_pages/common_admin/subDeptAjax.action?dept="+deptKeyId+"&subDeptAjaxDiv="+destSubDeptAjaxDiv,
	 			 success : function(officeSubDeptData) {
	 				 $("#"+destSubDeptAjaxDiv).html(officeSubDeptData);
	 				},
	 				error: function() {
	 				            alert("error");
	 				}
	 				});
	 	}

	 
// Get Designation detail on the basis of Office ID
function getUnderDesgnationViaAjax(destDesgAjaxDiv,officeTypeId,comId)
	 {
	 	var officeType=$("#"+officeTypeId).val();
	 		   $.ajax({
	 				    type : "post",
	 				    url : "/sfa/view/admin_pages/common_admin/underDesgnationViaAjax.action?office="+officeType,
	 				    success : function(desgnationData) {
	 			        $("#"+destDesgAjaxDiv).html(desgnationData);
	 				},
	 				   error: function() {
	 			            alert("error");
	 			        }
	 				 });
	 }

	 
function getUnderEmpNameViaAjax(destination,desgId,sdeptid)
	{
	
	  var designation=$("#"+desgId).val();
	  var subdept =$("#"+sdeptid).val();
	  $.ajax({
		       type:"post",
		       url : "/sfa/view/admin_pages/common_admin/employeeViaAjax.action?destination="+designation+"&subdeptID="+subdept,
		       success : function(desgData){
		       $("#"+destination).html(desgData);
	        },
		       error: function(){alert("error");}
	  });
	}










//Method for hide an error div
  function hideErrorDiv(divID1)
   {
     document.getElementById(divID1).style.display="none";
   }

//Method for validate a text field
  function validadeTextField(userForm,fieldName,errorField) {
	  var nameValue = document.getElementById(fieldName).value;
	  var iChars = "!@#$%^&*()+=-[]';,/{}|\":<>?0123456789";
	    for (var i = 0; i < nameValue.length; i++) {
	        if (iChars.indexOf(nameValue.charAt(i)) != -1) {
	        	document.getElementById(errorField).innerHTML = "<div class='user_form_inputError2'>Enter Alphabet Only.</div>";
	            userForm.fieldName.focus();
	           
	        }
	    }
	    return false;
	}


//Method for validate a mobile no
  function validadeMobileNO(userForm,fieldName,errorField)
   {
	var msgName = document.getElementById(fieldName).value;
	var iChars = "0123456789";
	for (var i = 0; i < msgName.length; i++)
	  {
	     if (iChars.indexOf(msgName.charAt(i)) == -1) 
	      {
	    	 document.getElementById(errorField).innerHTML = "<div class='user_form_inputError2'>Enter Numeric Values Only.</div>";
	          userForm.fieldName.focus();
	          return false;
	      }
	     if (msgName.charAt(0) != "9" && msgName.charAt(0) != "8" && msgName.charAt(0) != "7")
	      {
	    	 document.getElementById(errorField).innerHTML = "<div class='user_form_inputError2'>Mob No Should Be Start with 9,8 or 7.</div>";
	          userForm.fieldName.focus();
	          
	      }
	    }
	return false;
  }
  

//Method for validate a number field
  function validadeNumberField(userForm,fieldName,errorField)
   {
	var msgName = document.getElementById(fieldName).value;
	var iChars = "0123456789";
	for (var i = 0; i < msgName.length; i++)
	  {
	     if (iChars.indexOf(msgName.charAt(i)) == -1) 
	      {
	    	  document.getElementById(errorField).innerHTML = "<div class='user_form_inputError2'>Enter Numeric Values Only.</div>";
	          userForm.fieldName.focus();
	          
	      }
	    }
	return false;
  }

 
//Method for validate a alpha numeric field
  function validadeAlphaNumericField(userForm,fieldName,errorField) {
	  var nameValue = document.getElementById(fieldName).value;
	    var iChars = "!@#$%^&*()+=-[]';,/{}|\":<>?";
	    for (var i = 0; i < nameValue.length; i++) {
	        if (iChars.indexOf(nameValue.charAt(i)) != -1) {
	        	document.getElementById(errorField).innerHTML = "<div class='user_form_inputError2'>Special Charecters Not Allowed.</div>";
	            userForm.fieldName.focus();
	            
	        }
	    }
	    return false;
	}

  
  
  function verifyEmail1(){
	  var status = false;     
	  var emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
	  if (document.empBasic.emailIdOne.value.search(emailRegEx) == -1) {
	  
	 // if (document.empBasic.emailIdOne.value.search(emailRegEx) == -1) {
	    	   empBasic.emailIdOne.focus();
	            alert("Please enter a valid email address.");
	       }
	       return status;
	  }
  
  

// Method for verify a email ID  
  function verifyEmail(userForm,fieldName,errorField) {
	  var emailid = document.getElementById(fieldName).value;
	    if (emailid.length > 1) {
	    	alert("after checking the length");
	        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(userForm.emailid)) {
	        	alert("Inside if when everything is fine");
	            return (true);
	        }
	        document.getElementById(errorField).innerHTML = "<div class='user_form_inputError2'>Please Fill valid E-Mail Id.</div>";
	    }
	    return (false);
	}
  
//Method for getting Sub Department detail on the basis of sub department ID
  function getDesignation(DesignationAjaxDiv,deptId)
         {
                 var deptKeyId =$("#"+deptId).val();
                 $.ajax({
                                  type : "post",
                                  url : "/sfa/view/admin_pages/common_admin/DesignationAjax.action?dept="+deptKeyId,
                                  success : function(officeDesignation) {
                                          $("#"+DesignationAjaxDiv).html(officeDesignation);
                                         },
                                         error: function() {
                                                     alert("error");
                                         }
                                         });
                 }



  //Method for getting Sub Department detail on the basis of sub department ID
  function getUnderDesignation(DesignationAjaxDiv,subdeptId)
         {
                 var subdeptId =$("#"+subdeptId).val();
                 $.ajax({
                                  type : "post",
                                 // url : "/sfa/view/admin_pages/common_admin/DesignationAjax.action?dept="+subdeptId,
                                  url : "/sfa/view/admin_pages/common_admin/underDesgnationViaAjax.action?dept="+subdeptId+"&desgAjaxDiv="+DesignationAjaxDiv,
                                  success : function(officeDesignation) {
                                          $("#"+DesignationAjaxDiv).html(officeDesignation);
                                         },
                                         error: function() {
                                                     alert("error");
                                         }
                                         });
                 }
   

  
  
  
  
  
  
  
  

