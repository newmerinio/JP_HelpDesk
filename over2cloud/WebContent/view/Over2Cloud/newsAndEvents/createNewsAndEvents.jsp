<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>

<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="/js/tabConfig/tabAdd.js"/>"></script>
<script src="<s:url value="/js/organization.js"/>"></script>

<SCRIPT type="text/javascript">
$.subscribe('validate', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	
	        var mystring = $(".pIds").text();
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
	                else if(validationType=="desc"){
		                if(!(/^[a-zA-Z !@#$%^&*]+$/.test($("#"+fieldsvalues).val()))){
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
	    	        
	       });
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });


function newsView() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/newsAlertsConfig/beforeNewsAlertsView.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function reset(formId) {
	  $("#formId").trigger("reset"); 
	}

</script>

</head>
<body>
	<div class="list-icon">
	<div class="head">News & Alerts</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	
	<div class="container_block" style="width: 100%">
	<div style="min-height: 10px; height: auto; width: 96%;"></div>
			<div class="border">
					<div class="form_inner" id="form_reg" style="margin-top: 10px;">
						<s:form id="formone" name="formone" namespace="/view/Over2Cloud/newsAlertsConfig" action="addNewsAlerts" theme="css_xhtml" method="post" enctype="multipart/form-data">
							<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: left; margin-left: 7px"></div>
							</div>
							<br/>
<div class="form_inner" id="form_reg" >
<div class="page_form">
				 <s:iterator value="columnDate" status="counter" >
		     		   <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					<s:if test="#counter.count%2 != 0">
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <sj:datepicker name="%{key}" id="%{key}" changeMonth="true" changeYear="true" yearRange="2013:2020" readonly="true" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select From date"/>
                      </div>
                      </div>
                   </s:if>
                   <s:else>
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <sj:datepicker name="%{key}" id="%{key}" changeMonth="true" changeYear="true" readonly="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select To date"/>
                                </div>
                   </div>	
                 </s:else>
                  </s:iterator>
                  
                    <s:iterator value="columnText" status="counter">
                      <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
                    <s:if test="#counter.count%2 != 0">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="%{Key}" name="%{Key}"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data"  ></s:textfield>
                                </div>
                  </div>             
                  </s:if>
                  <s:else>
                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="%{Key}" name="%{Key}"  cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data"  ></s:textfield>
                                </div>
                  </div>                 
                 </s:else>
                 </s:iterator>
                 
                 
                  <div class="newColumn">
                  <div class="leftColumn1">For Department:</div>
                  <div class="rightColumn1">
                  	<s:select 
                    cssClass="textField"
                    id="department"
                    name="department" 
                    list="deptList" 
                    headerKey="-1"
                    headerValue="Select Departement"
                    cssStyle="width:310px;"
                    >
          </s:select>
                  </div>
                   </div>
                   <div class="newColumn">
                  <div class="leftColumn1">Visible To:</div>
                  <div class="rightColumn1">
                  	<s:select 
                    cssClass="textField"
                    id="visible_department"
                    name="visible_department" 
                    list="{'All','Self-Departments'}" 
                    headerKey="-1"
                    headerValue="Select Department"
                    cssStyle="width:310px;"
                    >
          			</s:select>
                  	
              		</div>
            </div>
             <div class="newColumn">
                  <div class="leftColumn1">Severity:</div>
                  <div class="rightColumn1">
	                  	<s:select 
                    cssClass="textField"
                    id="severity"
                    name="severity" 
                    list="{'High-Red','Normal-Blue','Low-Green'}" 
                    headerKey="-1"
                    headerValue="Select Severity"
                    cssStyle="width:310px;"
                    >
          </s:select>
                  </div>
             </div>
			    <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="clear"></div>
				<div class="fields">
				<div class="fields" align="center">
				<sj:submit 
		                        targets="orglevel1" 
		                        clearForm="true"
		                        value="  Save  " 
		                        effect="highlight"
		                        effectOptions="{ color : '#222222'}"
		                        effectDuration="5000"
		                        button="true"
		                        onCompleteTopics="level1"
		                        cssClass="submit"
		                        indicator="indicator2"
		                        onBeforeTopics="validate"
				/>
					<div id="reset" style="margin-top: -27px;margin-left: 220px;">
					<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
					<sj:a 
							button="true" href="#"
							onclick="newsView();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
					</div>
				 <sj:div id="foldeffect"  effect="fold">
                    <div id="Result"></div>
               </sj:div>
				</div>
			</div>
				<sj:div id="orglevel1Div"  effect="fold">
                    <div id="orglevel1"></div>
               </sj:div>
               </div>
             
</div></s:form></div>
</div>
</div>
</body>
</html>