
$.subscribe('validateBasics', function(event,data)
        {
          if(document.formone.office.value=="-1")
          {
        	errorOffic.innerHTML="<div class='user_form_inputError2'>Select Office Type</div>";
            document.formone.office.focus();
            event.originalEvent.options.submit = false;
          }
          else if(document.formone.locationId.value=="-1")
          {
        	  errorOffic.innerHTML="";
          	errLoc.innerHTML="<div class='user_form_inputError2'>Select Location</div>";
			document.formone.locationId.focus();
			event.originalEvent.options.submit = false;
          }
          else if(document.formone.branchId.value=="-1")
          {

        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";

        	  
        	  errBranch.innerHTML="<div class='user_form_inputError2'>Select Branch</div>";
        	  document.formone.branchId.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.dept.value=="-1")
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  
        	  errorOfficDept.innerHTML="<div class='user_form_inputError2'>Select Department</div>";
        	  document.formone.dept.focus();
  			  event.originalEvent.options.submit = false;
          }
          else if(document.formone.subdept.value=="-1")
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
             
        	  errorOfficSubDept.innerHTML="<div class='user_form_inputError2'>Select Sub-Department</div>";
        	  document.formone.subdept.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.desg.value=="-1")
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";

        	  
        	  errorOfficDesg.innerHTML="<div class='user_form_inputError2'>Select Designation</div>";
        	  document.formone.desg.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.doj_date.value=="")
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  
        	  errorOfficDOJ.innerHTML="<div class='user_form_inputError2'>Enter Joining Date</div>";
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.empName.value=="")
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  errorOfficDOJ.innerHTML="";

        	  
        	  errorEMPName.innerHTML="<div class='user_form_inputError2'>Enter Employee Name</div>";
        	  document.formone.empName.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.mobOne.value=="")
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  errorOfficDOJ.innerHTML="";
        	  errorEMPName.innerHTML="";
        	  errorComName.innerHTML="";

        	  
        	  errorMobBasc1.innerHTML="<div class='user_form_inputError2'>Enter Mobile No 1</div>";
        	  document.formone.mobOne.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.mobOne.value.length<10)
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  errorOfficDOJ.innerHTML="";
        	  errorEMPName.innerHTML="";
        	  errorComName.innerHTML="";
        	  
        	  errorMobBasc1.innerHTML="<div class='user_form_inputError2'>Invalid Mobile No 1</div>";
        	  document.formone.mobOne.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.emailIdOne.value.length<10)
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  errorOfficDOJ.innerHTML="";
        	  errorEMPName.innerHTML="";
        	  errorComName.innerHTML="";
        	  errorMobBasc1.innerHTML="";

        	  
        	  errorEmlBasc1.innerHTML="<div class='user_form_inputError2'>Invalid Email Id</div>";
        	  document.formone.emailIdOne.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.address.value=="")
          {

        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  errorOfficDOJ.innerHTML="";
        	  errorEMPName.innerHTML="";
        	  errorComName.innerHTML="";
        	  errorMobBasc1.innerHTML="";
        	  errorMobBasc2.innerHTML="";
        	  errorEmlBasc1.innerHTML="";
        	  errorEmlBasc2.innerHTML="";       	  
        	  errorSal.innerHTML="";
        	  errorContct.innerHTML="";
              
        	  errorAddrs.innerHTML="<div class='user_form_inputError2'>Enter Address</div>";
        	  document.formone.address.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.city.value=="")
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  errorOfficDOJ.innerHTML="";
        	  errorEMPName.innerHTML="";
        	  errorComName.innerHTML="";
        	  errorMobBasc1.innerHTML="";
        	  errorMobBasc2.innerHTML="";
        	  errorEmlBasc1.innerHTML="";
        	  errorEmlBasc2.innerHTML="";       	  
        	  errorSal.innerHTML="";
        	  errorContct.innerHTML="";
        	  errorAddrs.innerHTML="";
        	  errorLMark.innerHTML="";

              
        	  errorCity.innerHTML="<div class='user_form_inputError2'>Enter City</div>";
        	  document.formone.city.focus();
        	  event.originalEvent.options.submit = false;
          }
          else if(document.formone.empid.value=="")
          {
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  errorOfficDOJ.innerHTML="";
        	  errorEMPName.innerHTML="";
        	  errorComName.innerHTML="";
        	  errorMobBasc1.innerHTML="";
        	  errorMobBasc2.innerHTML="";
        	  errorEmlBasc1.innerHTML="";
        	  errorEmlBasc2.innerHTML="";       	  
        	  errorSal.innerHTML="";
        	  errorContct.innerHTML="";
        	  errorAddrs.innerHTML="";
        	  errorLMark.innerHTML="";
        	  errorCity.innerHTML="";
        	  errorPIN.innerHTML="";
              
        	  errorEmpId.innerHTML="<div class='user_form_inputError2'>Enter Emp Id</div>";
        	  document.formone.empid.focus();
        	  event.originalEvent.options.submit = false;
          }
          else
          {
        	 
        	  errorOffic.innerHTML="";
        	  errLoc.innerHTML = "";
        	  errBranch.innerHTML = "";
        	  errorOfficDept.innerHTML="";
        	  errorOfficSubDept.innerHTML="";
        	  errorOfficDesg.innerHTML="";
        	  errorOfficDOJ.innerHTML="";
        	  errorEMPName.innerHTML="";
        	  errorComName.innerHTML="";
        	  errorMobBasc1.innerHTML="";
        	  errorMobBasc2.innerHTML="";
        	  errorEmlBasc1.innerHTML="";
        	  errorEmlBasc2.innerHTML="";       	  
        	  errorSal.innerHTML="";
        	  errorContct.innerHTML="";
        	  errorAddrs.innerHTML="";
        	  errorLMark.innerHTML="";
        	  errorCity.innerHTML="";
        	  errorPIN.innerHTML="";
        	  errorEmpId.innerHTML="";

        	 
        	  event.originalEvent.options.submit = true;
          }
        });
