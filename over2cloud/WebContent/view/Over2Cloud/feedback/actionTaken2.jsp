<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<% 
System.out.println("Hello");
int id;
Object name=null;
Object mobileNo=null;
Object emailId=null;
Object docterName=null;
Object cleanliness=null;
Object staffBehavour=null;
Object costingFactor=null;
Object treatment=null;
Object ambience=null;
Object billing=null;
Object resultSatis=null;
Object overAll=null;
id=Integer.parseInt(request.getParameter("id"));
String level=request.getParameter("level");
System.out.println("level>>>>>>>"+id);
int uid=id+1;
FeedbackActionControl feedbackActionControl=new FeedbackActionControl();
List<Object> getValue=feedbackActionControl.getValue(uid);
for (Iterator iterator = getValue.iterator(); iterator.hasNext();)
{
	Object object[] = (Object[]) iterator.next();
	name=object[1];
	mobileNo=object[3];
	emailId=object[4];
	docterName=object[2];
	cleanliness=object[6];
	staffBehavour=object[10];
	costingFactor=object[13];
	treatment=object[16];
	ambience=object[19];
	billing=object[22];
	resultSatis=object[28];
	overAll=object[10];
	}

System.out.println("value of name"+ name);



%>



<%@page import="com.Over2Cloud.ctrl.feedback.FeedbackActionControl"%><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<script type="text/javascript">

$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
        });



$.subscribe('validate', function(event,data)
        {

   
	var mystring = $(".pIds").text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;

    alert(">>>>>>>>>>>>>>>>>>>>>>>"+mystring);

    
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
</Script>



</head>
<body style="margin-left: 200px; margin-top: 50px;">





<div style="width: 800px">
	<sj:accordion id="accordion"  autoHeight="false" >
	<sj:accordionItem title="Feedback Action Taken">
    <s:form id="Takeaction_Url" action="takeActionOnFeedback"  name="frm" enctype="multipart/form-data" theme="css_xhtml">
	<input type="hidden" name="id" value="<%= uid %>">
	<input type="hidden" name="level" value="<%= level %>">

	<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                  <div id="errZone" style="float:center; margin-left: 7px"></div>        
                </div></center>



	<div class="form_menubox">
			  <div class="user_form_text">Patient Name:</div>
			  <div class="user_form_input">
				<label class="lablecell"><%= name%><input type="hidden" name="actionName" value="<%= name%>"> </label>
        	</div>
 			  <div class="user_form_text1"> Docter Name:</div>
		      <div class="user_form_input">
			    <label class="lablecell"><%= docterName %><input type="hidden" name="actionDesc" value="<%= docterName %>">  </label>
			  </div>
     </div>
	 <div class="form_menubox">
			 
        <div class="user_form_text">Mob No:</div>
		<div class="user_form_input">
		<label class="lablecell" id="epcm_id"><%= mobileNo %><input type="hidden" name="actionMob" value="<%= mobileNo %>"> </label>
        </div>
        
        <div class="user_form_text1">Email Id:</div>
		<div class="user_form_input">
		<label class="lablecell" id="epcm_id"><%= emailId %><input type="hidden" name="actionEmail" value="<%= emailId %>"> </label>
        </div>
 	</div>
 	
 	
 	
 	
 	<div class="form_menubox">
			 
        <div class="user_form_text">Front Office:</div>
		<div class="user_form_input">
		<label class="lablecell" id="hyg_id"><%= cleanliness %><input type="hidden" name="actionHyg" value="<%=cleanliness %>"> </label>
        </div>
        
        <div class="user_form_text1">Comments:</div>
		<div class="user_form_input">
		<div class="user_form_input"><s:textarea cssClass="textcell" id="commentsHyg" name="commentsHyg" cssStyle="width:180px;" theme="simple"></s:textarea></div>
        </div>
 	</div>
 	<div class="form_menubox">
			 
        <div class="user_form_text">Medical Care:</div>
		<div class="user_form_input">
		<label class="lablecell" id="staff_id"><%=staffBehavour %><input type="hidden" name="actionStaff" value="<%= staffBehavour %>"> </label>
        </div>
        
        <div class="user_form_text1">Comments:</div>
		<div class="user_form_input">
		<div class="user_form_input"><s:textarea cssClass="textcell" id="commentsStaff" name="commentsStaff" cssStyle="width:180px;" theme="simple"></s:textarea></div>
        </div>
 	</div>
 	<div class="form_menubox">
			 
        <div class="user_form_text">Nursing Care:</div>
		<div class="user_form_input">
		<label class="lablecell" id="costing_id"><%= costingFactor %><input type="hidden" name="actionCost" value="<%=costingFactor %> "> </label>
        </div>
        
        <div class="user_form_text1">Comments:</div>
		<div class="user_form_input">
		<div class="user_form_input"><s:textarea cssClass="textcell" id="commentsCosting" name="commentsCosting" cssStyle="width:180px;" theme="simple"></s:textarea></div>
        </div>
 	</div>
 	<div class="form_menubox">
			 
        <div class="user_form_text">Diagnostics:</div>
		<div class="user_form_input">
		<label class="lablecell" id="hyg_id"><%=treatment %><input type="hidden" name="actionTreatment" value="<%=treatment %>"> </label>
        </div>
        
        <div class="user_form_text1">Comments:</div>
		<div class="user_form_input">
		<div class="user_form_input"><s:textarea cssClass="textcell" id="commentsTreatment" name="commentsTreatment" cssStyle="width:180px;" theme="simple"></s:textarea></div>
        </div>
 	</div>
 	<div class="form_menubox">
			 
        <div class="user_form_text">Pharmacy:</div>
		<div class="user_form_input">
		<label class="lablecell" id="ambience_id"><%= ambience %><input type="hidden" name="actionAmbience" value="<%=ambience %>"> </label>
        </div>
        
        <div class="user_form_text1">Comments:</div>
		<div class="user_form_input">
		<div class="user_form_input"><s:textarea cssClass="textcell" id="commentsAmbience" name="commentsAmbience" cssStyle="width:180px;" theme="simple"></s:textarea></div>
        </div>
 	</div>
 	<div class="form_menubox">
			 
        <div class="user_form_text">OverAll Services:</div>
		<div class="user_form_input">
		<label class="lablecell" id="billing_id"><%= billing %><input type="hidden" name="actionBilling" value="<%= billing %>"> </label>
        </div>
        
        <div class="user_form_text1">Comments:</div>
		<div class="user_form_input">
		<div class="user_form_input"><s:textarea cssClass="textcell" id="commentsBilling" name="commentsBilling" cssStyle="width:180px;" theme="simple"></s:textarea></div>
        </div>
 	</div>
 	<div class="form_menubox">
			 
        <div class="user_form_text">Other Facilities:</div>
		<div class="user_form_input">
		<label class="lablecell" id="result_id"><%= resultSatis %><input type="hidden" name="actionResult" value="<%=resultSatis %>"> </label>
        </div>
        
        <div class="user_form_text1">Comments:</div>
		<div class="user_form_input">
		<div class="user_form_input"><s:textarea cssClass="textcell" id="commentsResult" name="commentsResult" cssStyle="width:180px;" theme="simple"></s:textarea></div>
        </div>
 	</div>
 	<!--<div class="form_menubox">
			 
        <div class="user_form_text">Over All:</div>
		<div class="user_form_input">
		<label class="lablecell" id="All_id"><%=overAll %><input type="hidden" name="actionAll" value="<%= overAll %>"> </label>
        </div>
        
        <div class="user_form_text1">Comments:</div>
		<div class="user_form_input">
		<div class="user_form_input"><s:textarea cssClass="textcell" id="commentsAll" name="commentsAll" cssStyle="width:180px;" theme="simple"></s:textarea></div>
        </div>
 	</div>
 	
 	
 	
 	
	 	-->
	 <div class="form_menubox">
	 <span id="mandatoryFields" class="pIds" style="display: none; ">actionTaken#Action Taken#D#,</span>
             <div class="user_form_text">Action Taken:</div>
			<div class="user_form_input"><s:textarea  id="actionTaken" name="actionTaken" cssStyle="width:180px;"></s:textarea></div>
			<div class="user_form_text1">Comments:</div>
                  <div class="user_form_input"><s:textarea cssClass="textcell" id="comments" name="comments" cssStyle="width:180px;" theme="simple"></s:textarea></div>
	</div>
	<br>
	<div class="form_menubox">
   <center>	
             <div class="type-button">
            
             <sj:submit 
                        targets="Result" 
                        clearForm="true"
                        value="Take Action" 
                        timeout="5000"
                        href="%{Takeaction}"
                        effect="highlight"
                        effectOptions="{color:'#222222'}"
                        effectDuration="5000"
                        button="true"
                        onCompleteTopics="complete"
                         onBeforeTopics="validate"
                        />
              </div> 
           </center>
           </div>
             <sj:div id="Result"  effect="fold">
                    <div id="foldeffect"></div>
           </sj:div>
           </s:form>
           </sj:accordionItem>
           </sj:accordion>
           
   </div>
</body>

