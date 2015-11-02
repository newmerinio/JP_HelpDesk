<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<% 
int id;
CustomerPojo pojo=new CustomerPojo();

id=Integer.parseInt(request.getParameter("id"));
String feedTicketId=null;
if(request.getParameter("feedTicketId")!=null)
{
	feedTicketId=request.getParameter("feedTicketId");
}
String level=request.getParameter("level");
if(feedTicketId!=null )
{
	pojo=new FeedbackActionControl().getCustomerInfo(feedTicketId);
}

%>

<%@page import="com.Over2Cloud.ctrl.feedback.FeedbackActionControl"%>
<%@page import="com.Over2Cloud.CommonClasses.DateUtil"%>
<%@page import="com.Over2Cloud.BeanUtil.CustomerPojo"%><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<script type="text/javascript">

$.subscribe('checkActionStatus',function(event,data)
		{
			alert("Hello");
		}
		);

$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
        });


function getFeedbackStatus(actionTaken,feedDataId,feedTicketId1)
{
	var feedId=$("#"+feedDataId).val();
	var feedTicketId=$("#"+feedTicketId1).val();
	var statusType=$("#"+actionTaken).val();
	if(statusType=='1')
	{
		var conP = "<%=request.getContextPath()%>";

		$("#mybuttondialog123").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/feedback/beforeFeedViaOnlineTicket.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=online&fbDataId="+feedId+"&feedTicketId="+feedTicketId,
		    success : function(subdeptdata) {
	       $("#"+"mybuttondialog123").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		 $("#mybuttondialog123").dialog('open');
	}
}


$.subscribe('validate', function(event,data)
        {

   
	var mystring = $(".validate1").text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;

  // alert(">>>>>>>>>>>>>>>>>>>>>>>"+mystring);

    
    for(var i=0; i<fieldtype.length; i++)
    {
       
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2];   
        var validationType = fieldtype[i].split("#")[3];
        $("#"+fieldsvalues).css("background-color","");
        errZone.innerHTML="";

       // alert("Column Type>>>"+colType);
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


function viewNegativeFeedback() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/beforeCustomerPostiveView.action?flage=positive",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

</Script>

<STYLE type="text/css">

	td {
	padding: 5px;
	padding-left: 20px;
}

td.poor
{
	color: #FF0000;
}
td.average
{
	color: #FFCC00;
}
td.good
{
	color: #66FF66;
}

td.vgood
{
	color: #00CC00;
}

td.excellent
{
	color: #006600;
}
tr.color
{
	background-color: #E6E6E6;
}





</STYLE>

</head>
<body>
<sj:dialog 
    	id="mybuttondialog123" 
    	autoOpen="false" 
    	modal="true" 
    	width="1100"
		height="400"
    	title=" Open Ticket For Department"
    	resizable="false"
    >
</sj:dialog>
<div class="list-icon">
	 <div class="head">Negative Feedback</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">Open Ticket</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head"><%= pojo.getTicketNo() %></div> 
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;" align="center">
       <div class="border" style="height: 50%" align="center">
	<s:form id="Takeaction_Url" name="Takeaction_Url" action="takeActionOnFeedback"  name="frm" enctype="multipart/form-data" theme="css_xhtml">
	<input type="hidden" name="feedDataId" id="feedDataId" value="<%= id %>">
    <input type="hidden" name="feedTicketId" id="feedTicketId" value="<%=feedTicketId %>" >
	
	       <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px; margin-bottom: 23px;">
                  <div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
	
		<table border='1' bordercolor="lightgray" cellpadding="10px" rules="rows" width="80%">
			<tr class="color">
				<td>
					<b>CR&nbsp;No:</b>
				</td>
				<td>
					<b><%= pojo.getClientId() %></b>
				</td>
				<td >
					<b>Patient&nbsp;Name:</b>
				</td>
				<td>
					<b><%= pojo.getClientName() %></b>
				</td>
			</tr>
			<tr >
				<td>
					<b>Mob&nbsp;No:</b>
				</td>
				<td>
				
					<b><%= pojo.getMobNo() %></b>
				</td>
				<td >
					<b>Email&nbsp;Id:</b>
				</td>
				<td>
					<b><%= pojo.getEmailId() %></b>
					
				</td>
			</tr>
			<tr class="color">
				<td>
					<b>Room&nbsp;No:</b>
				</td>
				<td>
				
					<b><%= pojo.getCentreCode() %></b>
				</td>
				<td >
					<b>Purpose&nbsp;Of&nbsp;Visit:</b>
				</td>
				<td>
					<b><%= pojo.getCentreName() %></b>
					
				</td>
			</tr>
			<tr >
				<td>
					<b>Open&nbsp;Date:</b>
				</td>
				<td>
					<b><%= pojo.getOpenDate() %></b>
				</td>
				<td >
					<b>Open&nbsp;Time:</b>
				</td>
				<td>
					<b><%= pojo.getOpenTime()%></b>
					
				</td>
			</tr>
			<tr>
				<td><span id="mandatoryFieldsActionTaken" class="validate1" style="display: none; ">actionName#Action Taken#D#a,</span>
					<b>Take&nbsp;Action:</b>
				</td>
				<td><span class="needed"> </span>
					<s:select 
                        id="actionName"
                        name="actionName"
                        listKey="%{key}"
                        listValue="%{value}"
                        list="actionNameMap"
                        headerKey="-1"
                        headerValue="--Select Action Name--" 
                        cssClass="select"
					    cssStyle="width:82%"
                        onchange="getFeedbackStatus('actionName','feedDataId','feedTicketId')"
                        >
				</s:select>
				</td>
				<td><span id="mandatoryFieldsActionTaken" class="validate1" style="display: none; ">comments#Comments#T#a,</span>
					<b>Comments:</b>
				</td>
				<td><span class="needed"> </span>
					<s:textarea cssClass="textcell" id="comments" name="comments" cssStyle="width:180px;" theme="simple"></s:textarea>
				</td>
			</tr>
		</table>
	<br>
	<div class="form_menubox">
   <center>	
             <div class="type-button">
                        <sj:submit 
                        targets="Result" 
                        clearForm="true"
                        value="Register" 
                        timeout="5000"
                        href="%{Takeaction}"
                        effect="highlight"
                        effectOptions="{color:'#222222'}"
                        effectDuration="5000"
                        button="true"
                        onCompleteTopics="complete"
                        cssStyle="margin-left: -160px;"
                        onBeforeTopics="validate"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
		                     value="Reset" 
		                     button="true"
		                     onclick="resetForm('Takeaction_Url');"
		                     cssStyle="margin-top: -43px;"
			            />
			            <sj:a 
		                     value="Back" 
		                     button="true"
		                     cssStyle="margin-top: -43px;margin-right: -137px;"
		                     onclick="viewNegativeFeedback();"
			            >Back
			            </sj:a>
              </div> 
           </center>
           </div>
             <sj:div id="Result"  effect="fold">
                    <div id="foldeffect"></div>
           </sj:div>
           
           </s:form>
   </div>
   </div>
</body>