<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>

<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}


var status=false;
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	         $('select').find('option:first').attr('selected', 'selected');
	       });


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
		            else if(validationType=="a"){
		            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val()))){
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

	function viewKRAKPI()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/hr/beforekra_kpiView.action?modifyFlag=0&deleteFlag=0&moduleName=${mainHeaderName}",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

function fetchDepartment(regLevel,divId)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/fetchDepartment.action",
	    data : "regLevel="+regLevel,
	    success : function(data) {
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Department'));
			$(data.deptJSONArray).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
            alert("error");
        }
	 });
}
	
</SCRIPT>
<STYLE type="text/css">
	.rightColumn1
	{
		width: 49%;
	}
</STYLE>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<%-- <div class="head"><s:property value="%{mainHeaderName}"/> >> Add</div> --%>
		<div class="head">KRA-KPI</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:20%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="createKRAKPI" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
			 <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
         </div>
         <br>
			<div class="menubox">
			<span id="mandatoryFields" class="pIds" style="display: none; ">regLevel#Organization Level#D#<s:property value="%{validationType}"/>,</span>
				 <div class="newColumn">
				 	<div class="leftColumn1">Organization Level:</div>
				 	<div class="rightColumn1">	
				 		<span class="needed"></span>
		                 	<s:select 
		                              id="regLevel"
		                              name="regLevel" 
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Level" 
		                              cssClass="textField"
		                              onchange="fetchDepartment(this.value,'dept');"
		                              >
		                  </s:select>
				 	</div>				 
				 </div>
				<span id="mandatoryFields" class="pIds" style="display: none; ">dept#Department#D#<s:property value="%{validationType}"/>,</span>
				 <div class="newColumn">
				 	<div class="leftColumn1">Department:</div>
				 	<div class="rightColumn1">	
				 		<span class="needed"></span>
		                 	<s:select 
		                              id="dept"
		                              name="mappeddeptid" 
		                              list="#{'-1':'Select Department'}"
		                              cssClass="textField"
		                              >
		                  </s:select>
				 	</div>				 
				 </div>
				 	
				 <s:iterator value="kraKPIParam" status="counter">
				 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		 				<div class="newColumn">
		 					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
		 					<div class="rightColumn1">
	 							<span class="needed"></span>
	 							<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
		 					</div>
		 				</div>
			 			<div class="clear"></div>
				 	</s:iterator>
				 	
				 	<div id="newDiv" style="float: right;width: 37%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true">+</sj:a></div>
				 	<s:iterator begin="100" end="109" var="deptIndx">
				 		<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
				 			<s:iterator value="kraKPIParam" status="counter">
					 					<s:if test="%{key == 'kpi'}">
				 				<div class="newColumn">
				 					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
					 				<div class="rightColumn1">
					 					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
					 				</div>
				 				</div>
					 					</s:if>
			 					<div class="clear"></div>
				 			</s:iterator>
				 			
				 			<div style="float: right;width: 24.5%;margin-top: -46px;">
								<s:if test="#deptIndx==109">
									<div class="user_form_button2" style="margin-left: 54px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
								</s:if>
	           					<s:else>
	            	 				<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
	            					<div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
	       						</s:else>
        					</div>
				 		</div>
				 	</s:iterator>
				 	<div class="clear"></div>
					<br>				 	
				 	
					<!-- Buttons -->
					<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
						<div class="buttonStyle" style="width: 100%; text-align: center; padding-bottom: 10px;">
				         <sj:submit 
				           	targets="orglevel1Div" 
	                        clearForm="true"
	                        value="Save" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="button"
	                        indicator="indicator2"
	                        onBeforeTopics="validate1"
				          />
				          <sj:a 
							     	name="Reset"  
									href="#" 
									cssClass="button" 
									button="true" 
									onclick="resetForm('formone');"
									cssStyle="margin-left: 205px;margin-top: -45px;"
								>
								  	Reset
								</sj:a>
				          <sj:a 
					     	name="Cancel"  
							href="#" 
							cssClass="button" 
							indicator="indicator" 
							button="true" 
							cssStyle="margin-left: 164px;margin-top: -27px;"
							onclick="viewKRAKPI()"
							cssStyle="margin-top: -45px;"
							
						>
						  	Back
						</sj:a>
					    </div>
						<sj:div id="orglevel1"  effect="fold">
		                    <div id="orglevel1Div"></div>
		                </sj:div>
				 </div>
		</s:form>
	</div>	
</body>
</html>
