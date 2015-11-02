
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<head>
   <s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>::: Over2Cloud :::</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/theme1.css" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=Droid+Sans" rel="stylesheet" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,600" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/select_custom.css" />
<script src="assets/scripts/head.min.js" type="text/javascript" charset="utf-8"></script>
<script src="assets/scripts/jquery.tools-1.2.7.min.js" type="text/javascript" charset="utf-8"></script>
<script src="assets/scripts/jquery.easing.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.custom_radio_checkbox.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	$(".radio").dgStyle();
	$(".checkbox").dgStyle();
});
</script>
<script type="text/javascript">
function OnlickFunction(valuepassed){
	var conP = "<%=request.getContextPath()%>";
	  $("#myeffectdialogdfgdfgdf").load(conP+"/view/CloudApps/GetConfigurationdetails?id="+valuepassed);
	  $("#myeffectdialogdfgdfgdf").dialog('open');
	}
</script>
<script type="text/javascript">
function SelectConfigurtion(valuepassed){


var valuepasseds=valuepassed.value;
var conf_table=valuepassed.title;
var conP = "<%=request.getContextPath()%>";
document.getElementById("levelIDName").value=valuepasseds;
$("#dividsss").load(conP+"/view/CloudApps/GetLevelConfiguration?id="+valuepasseds+"&mapedtable="+conf_table.split(" ").join("%20"));
$("#dividsss").dialog('open');
}
</script>
<script type="text/javascript">
function oksaveLevelConfiguration() {
 var level=document.getElementById("levelIDName").value;
 //alert(level);
	var conftable = $("#conftable").text();
	var input = new Array();
	var a,c = $(".form_menu_inputtext");
	for(a=0; a<c.length; a++)
	{input.push(c[a].value);
	}
	var conP = "<%=request.getContextPath()%>";
    $.ajax({
        type : "post",
         url : conP+"/view/CloudApps/addLevelConfiguration.action?id="+input+"&level="+level+"&mapedtable="+conftable,
        success : function(x) {
    	for(a=0; a<c.length; a++)
    	{
        	c[a].value="";
    	}
    	document.getElementById('levelIdMsg').innerHTML=x;
    	
    },
       error: function() {
            alert("error");
        }
     });
} 
</script>
<script type="text/javascript">
var a,y,s;
var b = -1;
function showdata1(){
		 b--;
}
 function showdata(){
	 if(b==-1)
	 {
		 a = $(":checkbox");
		 y = $("div.abc");
		 s=  $(".sss").text();
		 s = s.split("#");
		 b = 0;
	 }	 
	 var id=a[b].value;
	 
	 var fieldname =s[b];

		var conP = "<%=request.getContextPath()%>";
	    $.ajax({
	        type : "post",
	         url : conP+"/view/CloudApps/GetConfigurationdetailssss.action?id="+id+"&fieldname="+fieldname,
	        success : function(x) {
	        y[b-1].innerHTML=x;
	    },
	       error: function() {
	            alert("error");
	        }
	     });
	 b++;
} 
</script>
<script type="text/javascript">
function tom(tabId){
	var divs = $(".ui-accordion-content");
	var i;
	for(i=0; i<divs.length; i++)
	{
		divs[i].style.display	=	"none";
	}
	tabId.style.display	=	"block";
}
</script>

<script type="text/javascript">
 function getFormValue(id,ref){
	 var selectid = $('input[name='+id+']:checked').map(function() {
	        return $(this).attr("title");
	    }).get();
	    //alert("slec idhdf :: "+selectid);
	    var maped = ref.title;
        var conP = '<%=request.getContextPath()%>';
       $.ajax({
			    type : "post",
			    url : conP +"/view/CloudApps/addconfigurationsliding.action?titles="+selectid+"&mapedtable="+maped,
			    success : function(dividdd) {
		        //alert(dividdd);
		        //$("#abccc").append(dividdd);
				$("#divid").html(dividdd);
				  $("#divid").dialog('open');
			},
			   error: function() {
			        alert("error");
			    }
			 });
	   
  };
</script>
<script type="text/javascript">
		/* 
		 Full window width content slider by Karl Oskar Engdahl
		 for more information visit:
		 http://www.musca.se/full-window-width-jquery-tools-content-slider-demo/

		 This code is licensed under GPL and free to use. 
		 http://www.gnu.org/licenses/gpl.html
		*/
		$(function() {
			// Sets the slides width on page load
			var i = $(window).width();
			if (i > 319){ $('#items > div').css({ width: i }); }
			// Scrollable and navigator plugin settings.
			$("#slider").scrollable({ touch: false, easing: 'easeInOutBack', speed: 900}).navigator({ navi: '#navigation' });
			// Window resize code
			window.api = $("#slider").data("scrollable");
			$(window).resize(function() {
				var a = window.api.getIndex();
				var w = $(window).width();
				if (w > 319) {
					var l = a * w
					$('#items').css({ left: + - +l });
					$('#items > div').css({ width: 800 });
				} else {
					$('#items > div').css({ width: 300 });
				}
			});
		});
		</script>
<!-- First Step script ends here -->

<!-- Completion of profile CSS starts here -->
<style type="text/css">

#slider{
	width: 100%;
	margin: 0 auto;
	overflow: hidden;
	position:relative;
	min-height:431px;
}
#items{
	width: 3000em;
	position: absolute;
}
#items > li, #items > div{
	float: left;
	overflow: visible;
}
#items > div > div{
	margin: 0 auto;
}
#slide-1{
	background-color:black;
}
#items > div > div{
	width: 1580px;
}
	/*	.lt-1680 .content{
			width: 1580px;
		}*/
.lt-1440 #items > div > div, .lt-1440 #navigation-wrapper{
	width: 1180px;
}
.lt-1280 #items > div > div, .lt-1280 #navigation-wrapper{
	width: 770px;
}
.lt-1024 #items > div > div, .lt-1024 #navigation-wrapper{
	width: 768px;
}
.lt-768 #items > div > div, .lt-768 #navigation-wrapper{
	width: 640px;
}
.lt-640 #items > div > div, .lt-640 #navigation-wrapper{
	width: 480px;
}
.lt-480 #items > div > div, .lt-480 #navigation-wrapper{
	width: 460px;
}
.lt-320 #items > div > div, .lt-320 #navigation-wrapper{
	width: 300px;
}
.left, .right{
}
.left, #navigation li{
	float:left;
}
.right{
	float:right;
}
#navigation-wrapper,#navigation{
	margin:0 auto;
}
#navigation{
	clear:both; width:81px; display:none;
}
#navigation li{
	width:15px; height:15px; list-style:none; border-radius:8px; border:1px solid #ccc; margin: 5px;
}
#navigation li:hover, .left:hover, .right:hover{
	cursor:pointer;
}
.active{
	background-color:#ccc;
	cursor:default;
}
.disabled{
	display:none;
}
a.prev{
	background: url("images/buttons.png") no-repeat scroll 0 0 transparent;
    float: left;
    height: 34px;
    width: 34px;
	position:absolute;
	left:-15px;
	top:45%;
}
a.prev:hover{
	background-position: -34px -34px;
}
a.next{
	background: url("images/buttons.png") no-repeat scroll 0 -34px transparent;
    float: right;
    height: 34px;
    width: 34px;
	position:absolute;
	right:-15px;
	top:45%;
}
a.next:hover{
	background-position: -34px 0;
}

</style>
<link rel="stylesheet" type="text/css" href="css/profile.css" />
<!-- Completion of profile CSS ends here -->

     <sj:dialog 
      	id="divid"  
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Msg Effect"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="450"
		height="200"
    >

    </sj:dialog>
     <sj:dialog 
      	id="dividsss" 
      	 		buttons="{ 
    		'Save Button':function() { oksaveLevelConfiguration(); },
    		}"  
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Level Configuration"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="900"
		height="600"
    >
    </sj:dialog>
<div class="wrapper">

	<div class="main_wrapper" style="height: 20%;">
		<div class="sidebar">
			<div class="sidebar_block">
				<div class="logo"><a href="javascript:void();">
					<img src="images/logo.png" width="184" height="40" alt="Over2Cloud" title="Over2Cloud" /></a>
				</div>
				<div class="page_text">Organization Dashboard</div>
			</div>
		</div>
		<div class="content" style=" min-height:auto;">
			<div class="content_block" style=" padding:0px;">
				<div class="content_block_top">
					<div class="heading"><h1><s:property value="#session['orgname']" /></h1></div>
					<div class="profile">
						<div class="profile_pic"><a href="javascript:void();" onmouseout="var change_pic = document.getElementById('mychange_pic'); change_pic.style.display='none';" onmouseover="var change_pic = document.getElementById('mychange_pic'); change_pic.style.display='block';">
							<img src="images/profile.png" width="51" height="65" alt="" title="" /></a>
							<div class="change_pic" id="mychange_pic"><a href="javascript:void();">Update</a></div>
						</div>							
						<div class="profile_desc">
							<div class="date"><s:property value="#session['currentdate']" /></div>
							<div class="profile_details">
								<ul>
									<li><a href="javascript:void();" class="profile_name"><s:property value="#session['orgname']" /></a>
										<ul class="sub_menu">
											<li><a href="javascript:void();">My Profile</a></li>
											<li><a href="javascript:void();">Logout</a></li>
										</ul>
									</li>
								</ul>
							</div>
						</div>							
					</div>
				</div>
				<div class="page_title"><h1>Complete your profile</h1></div>
			</div>
		</div>
	</div>
	
	  <s:form id="config" name="config" method="get"  theme="simple">
	<div class="main_wrapper">
		<div class="steps">
			<div class="steps_block">
				<div class="steps_sub_block" style=" position:relative; width:860px; padding:1px; border:4px solid #999999;">
					<div class="text_heading"><span>Please configure for your custom configuration, Re login after configuration for change effects</span></div>
					<div id="slider">
						<div id="items">
						<div>
								<div class="steps_content">
									<div class="form_content">
										<ul>
									<s:iterator id="ConfigurationUtilBean" value="configBean" >
									<li><div id="z" class="checkbox"><s:checkbox name="titles" id="titles" fieldValue="%{id}"/></div>&nbsp;&nbsp;<a class="sss" href="javascript:void();" ><s:property value="field_value"/><div style="display:none;">#</div></a>
									</li>
									</s:iterator>
										</ul>
									</div>
								
								</div>
							</div>
						  <s:iterator id="ConfigurationUtilBean" value="configBean" >
							<div id="<s:property value="%{#id}"/>" class="abc">
							<div id="dividdd">
							</div>
							<s:property value="%{id}"/>
							</div>
							</s:iterator>
							
						</div>
					</div>
					<div id="navigation-wrapper">
							<a class="prev left" href="javascript:void();" onclick="javascript:showdata1();">&nbsp;</a>
						<a class="next right" href="javascript:void();" onclick="javascript:showdata();">&nbsp;</a>
						<ul id="navigation">
							<li class="active"></li>
							<li></li>
							<li></li>
						</ul>
					</div>
					<div class="cancel_icon"><a href="${pageContext.request.contextPath}/mainFrame?param=1">
						<img src="images/cancel.png" width="20" height="20" alt="Cancel" title="Close Configuration" /></a>
					</div>
			</div>
		</div>
	</div>
	</div>
	</s:form>
	<div class="main_wrapper">
		<div class="footer">
			<div class="copyrights">
				<ul>
					<li><a href="javascript:void();">Dashboard</a></li>
					<li><a href="javascript:void();">Report a Bug</a></li>
					<li class="sub_footer"><a href="javascript:void();">Seek Assistance</a></li>
				</ul>
				<p>Over2Cloud (Enterprise Version 4.0)<br>All rights reserved</p>
			</div>
			<div class="clients"><a href="javascript:void();">
				<img width="117" height="69" title="Max Healthcare" alt="Max Healthcare" src="images/clients1.png"></a></div>
		</div>
	</div>
</div>
