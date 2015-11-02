<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lost Action</title>
<script type="text/javascript">
	$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#resultIdOuter").fadeIn(); }, 10);
          setTimeout(function(){ $("#resultIdOuter").fadeOut(); }, 6000);
          
});
</script>
<script type="text/javascript">
 function showDiv()
 	{
	 	//var data=$("#lostId").val();
	 	var data=$("#lostId option:selected").text();
	       if(data=="Other")
	       {
	        document.getElementById("showotherreason").style.display="block";
	       }
	       else
	       {
	    	   document.getElementById("showotherreason").style.display="none";
	       }
 	}
</script>

<script type="text/javascript">
		$.subscribe('validate1', function(event,data)
	{
		validate(event,data,"pIds");
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
</script>
</head>
<body>
			<s:form id="actionFormid" namespace="/view/Over2Cloud/wfpm/client" action="convertToLost" 
	    		theme="simple" method="post" enctype="multipart/form-data">
			<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
         </div>
         <br>
			<s:hidden id="id" name="id" value="%{id}"></s:hidden>
			<s:hidden id="clientId" name="clientId" value="%{clientId}"></s:hidden>
			<div class="newColumn">
			<span id="mandatoryFields" class="pIds" style="display: none; ">lostId#Lost Reason#D#,</span>
			<div class="leftColumn1">Lost Reason:</div>
			<div class="rightColumn1"><span class="needed"></span>
				<s:select
					id="lostId"
					name="lostId" 
					list="lostStatusMAP"
					headerKey="-1"
					headerValue=" Select Reason"
					cssClass="textField"
					onchange="showDiv();"
					></s:select>
			</div>
			</div>   
			<div id="showotherreason" style="display: none; ">
				<div class="newColumn">
				<div class="leftColumn1">Lost Reason:</div>
					<div class="rightColumn1">
						<s:textfield name="otherlostreason"  id="otherlostreason" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					</div>
				</div>
			</div>
			<div class="newColumn">
			
				<div class="leftColumn1">RCA:</div>
				<div class="rightColumn1">
				     <s:textfield name="RCA"  id="RCA" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>
			
			<div class="newColumn">
			
				<div class="leftColumn1">CAPA:</div>
				<div class="rightColumn1">
				     <s:textfield name="CAPA"  id="CAPA" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>
			</s:form>
			<div class="buttonStyle" style="float:left;">
					<sj:submit 
	           	   		formIds="actionFormid"
	       				targets="resultIdD"
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onSuccessTopics="completeAction"
                       onCompleteTopics="level1"
                       cssClass="submit"
                       indicator="indicator4"
                       onBeforeTopics="validate1"
	         	 />
	       
		<sj:div id="resultIdOuter"  effect="fold">
	   		<div id="resultIdD"></div>
	   	</sj:div>
	</div>
</body>
</html>