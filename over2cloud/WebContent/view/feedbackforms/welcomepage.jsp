<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<SCRIPT type="text/javascript">
$.subscribe('validate', function(event,data)
		{		
					if( $('#agree:checked').length<=0)
					 {
						errZone.innerHTML="<div class='user_form_inputError2'>Please fill the value of field.</div>";
					  	setTimeout(function(){ $("#errZone").fadeIn(); }, 2000);
			     		setTimeout(function(){ $("#errZone").fadeOut(); }, 4000);     
			     		$('#agree').css("box-shadow","0 0 13px 0px red inset");
			     		$('#agree').focus();
					  	event.originalEvent.options.submit = false;
					  	return false;
					 }
		});
				
function resend()
{

//	$("#result").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#openDialog").dialog({title: 'Terms And Conditions',width: 800,height: 200});
	$("#openDialog").dialog('open');
	$("#openDialog").html("I, the undersigned, hereby declare that I have read, understood and answered the above medical questionnaire to the best of my knowledge. I also hereby promise to inform you of any change to my health. I authorize the setting up of my Medical file, its follow-up, as well as my registration on the recall list(s) of the attending Physicians. I have been informed that my file will be kept in the office at all times and that only the Physician(s) and his/her (their) auxiliary personnel will have access to it. ");
//	 setTimeout(function(){ window.location.reload(true); }, 5000);
}
	
	
</SCRIPT>
<!-- color: #8B8B70; -->
<style>
  .carousel-inner > .item > img,
  .carousel-inner > .item > a > img {
      width: 70%;
      margin: auto;
  }
  </style>

<style type="text/css">

 body{
  background-size: cover;
  background-color: #F8F8F8;
  }	


#logoDiv{
background: url("/over2cloud/images/medantalogo.jpg");
  border: rgb(11, 11, 11);
  height: 59px;
  width: 235px;
 height: 88px;
  width: 353px;
  background-size: 354px;
  }

  


div#compDetail{
 border: rgb(11, 11, 11);
  height: 23px;
  width: 181px;
  background-size: contain;
  float: right;
  margin-right:56px;
  
  margin-top: -49px;
  text-indent: -56px;
  text-decoration: blink;
  animation-fill-mode: both;
  font-style: italic;
  text-transform: capitalize;
  font-size: 18px;
    animation-direction: reverse;	
}

#mainDiv{

	
  align-content: center;
  align-self: center;
  background-color: #FFFFFF;
    margin: auto;
  height: 900px;
    width: 937px;
 }
 
 #innerDiv1{
      height: 99px;
  border-bottom-style: double;
  border-bottom-color: rgb(229, 80, 80);
  }

	.belowheader
	{
		width: 100%;
  height: 221px;
  margin-top: 10px;
  background-color: #ff6633;
  border-radius: 22px;
  padding: 10px;
	}

.bleft{
	
	width: 250px;
	height: 200px;
	margin-left: 20px;
	  float: left;
}
.bleft img
{
	width:216px;
	height: 331px;
	border-radius: 100px/50px;
}
.bright
{
	width: 18px;
	height: 200px;
	float: left;
	margin-top: 28px;
}

.slid
{
	width: 100%;
	height: 323px;
	margin-top: 10px;
}

input[type="checkbox"]{
  width: 15px; /*Desired width*/
  height: 15px; /*Desired height*/
}

.carousel-inner > .item > img, .carousel-inner > .item > a > img
{
	  width: 100%;
  margin: auto;
  height: 322px;
}
.proceed
{	
	float: left;
	margin-top: 26px;
	margin-left: 14px;
}
.agreement
{
float: left;
	margin-top: 20px;
}

</style>
</head>
<body  style=" #ECECEC width: 1295px;">
<sj:dialog
          id="openDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Upload"
          modal="true"
          width="1200"
          height="450"
          draggable="true"
    	  resizable="true"
          >
          <div id="view"></div>
</sj:dialog>

	<div class="wrapper">
		
		<div id="mainDiv" style="background-color: #FFFFFF;margin-left: 90px;width: 1095px;height: 637px;">
			<div id="innerDiv1" style="height: 120px;">
				<div id="logoDiv" style=" margin-top: 6px;"></div>
				<div id="compDetail">hotline&nbsp;no.&nbsp;+91&nbsp;124&nbsp;<span id="contactNo" style="  color: red;">4141414</span></div>
				
			</div>
			
			
<div id="maincontent" style=" margin-top: 30px; margin-left: 38px; font: normal 14px arial; justify-content: space-around; text-align: justify; width: 690px;">
Dear <b id="pnameId"><s:property value="%{pname}" /></b>,<br><br>
We would like to welcome you to the Medanta Wellness Program. We are committed to provide you with the best possible Healthcare. This is your Wellness-medical history form, to be completed prior to your first Wellness Consultation. All information will be kept confidential. This information will be used for the evaluation of your Health. The form is extensive, but please try to make it as accurate and complete as possible. Please take your time and complete it carefully and thoroughly, and then review it to be certain you have not left anything out. Your answers will help us design a suitable Wellness program that meets your individual needs, also our comprehensive questionnaires really help us to determine the best diagnosis and treatment plan.<br><br> If you have questions or concerns, we will help you with those during the course of your Wellness program. We realize that some parts of the form will be unclear to you. Do your best to complete the form. Your questions will be thoroughly reviewed and addressed afterwards. It might be helpful for you to keep a written list of questions or concerns as you complete the medical history form.
</div>			
			
			
			<!--
 <div class="slid">
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
     Indicators 
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

     Wrapper for slides 
    <div class="carousel-inner" role="listbox">

      <div class="item active">
        <img src="/over2cloud/images/medanta1.jpg" alt="Chania" width="460" height="345">
       </div>

      <div class="item">
        <img src="/over2cloud/images/medanta2.jpeg" alt="Chania" width="460" height="345">
        
      </div>
    
      <div class="item">
        <img src="/over2cloud/images/medanta3.jpg" alt="Flower" width="460" height="345">
      </div>

        
    </div>

     Left and right controls 
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>

	</div>		
			
			
			--><!--<div class="belowheader" style=" margin-top: 40px; height: 210px;">
				--><!----><div class="bleft"style="
    margin-top: -215px;
    margin-left: 791px;
">
					<img alt="" src="/over2cloud/images/ImageResize.jpg">
					
				</div>
				
								<s:form id="form1" action="/view/Over2Cloud/patientActivity/viewQuestionnaireForms.action" method="post" enctype="multipart/form-data">
				
				<div class="bright" style="
    margin-top: 25px;
    margin-left: 35px;
">
				<input type="checkbox" id="agree" value="aa">
				</div>
				<div class="agreement"style="
    margin-left: 20px;
    margin-top: 18px;">
    <h4>I accept all <a onclick="resend();" href="#"><font color="blue">terms and conditions</font></a>.</h4></div>
				<s:hidden id="traceid" name="traceid" value="%{traceid}"></s:hidden>
				<s:hidden id="type" name="type" value="%{type}"></s:hidden>
				<s:hidden name="pageNo" value="1"></s:hidden>
				<s:hidden name="pname" value="%{pname}"></s:hidden>
				<s:hidden id="positionPage" name="positionPage" value="%{positionPage}" />
				<s:hidden id="done" name="done" value="%{done}" />
				<div class="type-button">
   			<div id="bt" style="display: block;">
         				<sj:submit 
	           				targets="datapart" 
	           				clearForm="true"
	           				value="  Proceed  " 
	           				effect="highlight"
	           				effectOptions="{ color : '#E00000'}"
	           				effectDuration="5000"
	           				button="true"
	           				cssStyle="margin-left: -191px; margin-top: 70px; color: white; font-size: 95%; background: #E00000; width: 96px; height: 33px;"
	           				onBeforeTopics="validate"
	           				
			   			/>
	</div>
	
   </div>
   
				</s:form>
				<center>
						<div
								style="float: center; font-family:Arial; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: center; margin-left: 7px "></div>
							</div>
						</center>
			</div>
			
			
	<!--</div>
	</div>-->
	
	</body>

</html>