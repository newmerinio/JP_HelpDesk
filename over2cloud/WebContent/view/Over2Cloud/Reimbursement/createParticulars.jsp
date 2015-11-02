<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
String level=request.getParameter("level");
System.out.println("levelDDDDDDDDD>>>>"+level);
%>
<html>
<head>
<SCRIPT type="text/javascript">
$.subscribe('complete1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#partiCularsDiv11").fadeIn(); }, 10);
	         setTimeout(function(){ $("#partiCularsDiv11").fadeOut(); }, 4000);
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
</script>

</head>
<div class="clear"></div>
<div class="middle-content">
    <div class="list-icon">
        <div class="head"><h4>Particulars >>Details</h4><s:property value="%{}"/></div>
    </div>
    <div class="clear"></div>
    <div style="min-height: 10px;  auto; width: 100%;"></div>
   
    <div class="border" style="height: 50%">
      <div class="secHead"><h4>Particulars >> Details</h4><s:property value=""/></div>
        <div style="width: 100%; center; padding-top: 10px;">
		 
		        <s:form id="formone"  action="addParticulars" theme="css_xhtml" method="post" enctype="multipart/form-data">
		        
		        
		        <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                  <div id="errZone" style="float:center; margin-left: 7px"></div>        
                </div></center>
		         
		         <s:iterator value="particularsTextBox" status="counter">
		         <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
                 <s:if test="#counter.count%2 !=0">
				  <s:if test="%{mandatory}">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1"><span class="needed"> </span><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
			      </div>
			      </s:if>
			      <s:else>
			  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
			      </div>
			      </s:else>
			      </s:if>
			      <s:else>
			        <s:if test="%{mandatory}">
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
			      </div>
			      </s:if>
			      <s:else>
			       <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
			      </div>
			      </s:else>
				 </s:else>
		         </s:iterator>
		        <br><br>
                       <center>
                       <br><br><br>
                  <div class="type-button"><br><br>
                <center>
                  <sj:submit 
                     targets="Result123" 
                     clearForm="true"
                     value="  Save  " 
                     button="true"
                     onCompleteTopics="complete1"
                     effect="highlight"
                     effectOptions="{ color : '#222222'}"
                     effectDuration="5000"
                     cssClass="button"
                     />
                 </center>
     </div>
     	
     </center>
     </s:form><br>
     	<div class="clear"></div>
		<sj:div id="partiCularsDiv11"  effect="fold">
		   <div id="Result123" style="max-height: 500px;"></div>
		</sj:div>
	  </div>
</div>
  
</div>
</html>