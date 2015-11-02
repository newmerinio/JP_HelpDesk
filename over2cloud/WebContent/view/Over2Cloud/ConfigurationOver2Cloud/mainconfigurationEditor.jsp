<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<head>
</head>
<title>::: Over2Cloud :::</title>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=Droid+Sans" rel="stylesheet" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,600" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/select_custom.css" />
<script src="assets/scripts/jquery.tools-1.2.7.min.js" type="text/javascript" charset="utf-8"></script>
<script src="assets/scripts/jquery.easing.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.custom_radio_checkbox.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/profile.css" />
<script type="text/javascript">
var a,y,s;
var b = -1;
function showdata1Editor()
{
		 b--;
}
 function showdataEditor()
 {
	 if(b==-1)
	 {
		 a = $(":checkbox");
		 y = $("div.abc");
		 s=  $(".sss").text();
		 s = s.split("#");
		 b = 0;
	 }	 
	 var id = a[b].value;
	 var fieldname = s[b];
	 var conP = "<%=request.getContextPath()%>";
//	$("#"+ y[b-1]).html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
     $.ajax({
        type : "post",
    	url : conP+"/view/CloudApps/editeOrganizationconfiguration.action?id="+id+"&filed_name="+fieldname,
        success : function(x) 
        {
        	y[b-1].innerHTML=x;
        },
        error: function() 
        {
            alert("error");
        }
      });
	  b++;
} 

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
			if (i > 319)
			{ 
				$('#itemseditor > div').css({ width: i }); 
			}
			// Scrollable and navigator plugin settings.
			$("#slidereditor").scrollable({ touch: false, easing: 'easeInOutBack', speed: 900}).navigator({ navi: '#navigation' });
			// Window resize code
			window.api = $("#slidereditor").data("scrollable");
			$(window).resize(function() {
				var a = window.api.getIndex();
				var w = $(window).width();
				if (w > 319) 
				{
					var l = a * w
					$('#itemseditor').css({ left: + - +l });
					$('#itemseditor > div').css({ width: 800 });
				} else {
					$('#itemseditor > div').css({ width: 300 });
				}
			});
		});
		</script>
		<script type="text/javascript">
function tom(tabId)
{
	
	var divs = $(".ui-accordion-content");
	//alert("Tab Idv :::: "+divs);
	var i;
	for(i=0; i<divs.length; i++)
	{
		divs[i].style.display	=	"none";
	}
	tabId.style.display	=	"block";
}
function OnlickEdite(columnid,mandatoryFalg,levelname,tablemapped,datafor,mappedTab,col,len)
{
//	alert("col:::::"+col);
	//alert("len:::::"+len);
	//var abc = value.title;
	//alert("abc::: "+abc);
	//
	//var tablemapped=valuepass.value;
	//var columnid=valuepass.id;
	//var levelname=valuepass.name;
	//alert("tablemapped::::"+tablemapped);
	//alert("columnid::::"+columnid);
//	alert("levelname::::"+levelname);
	//var mappedTab= $("#mappedTableConf").val();
	var mandatoryFalgTemp=mandatoryFalg;
    var conP = "<%=request.getContextPath()%>";
       $("#mybuttondialog").load(conP+"/view/CloudApps/operationwithfieldAction.action?mapedtable="+tablemapped.split(" ").join("%20")+"&id="+columnid.split(" ").join("%20")+"&levelname="+levelname.split(" ").join("%20")+"&dataFor="+datafor.split(" ").join("%20")+"&comonmaped="+mappedTab+"&colType="+col+"&length="+len);
	   $("#mybuttondialog").dialog('open');
	} 
 function cancelButton()
 {
	 $("#mybuttondialog").dialog('close');
 }
function previewPage(value,fieldvalue)
 {
 	 //var valuepassed = $("#idVal").val();
 	// var fieldvalue= $("#filed_name").val();
	//alert("value id ::: "+value);
	
 	//alert("Field Value ::: "+fieldvalue);
 
 //	alert("value::::: ::: "+value);
 	 $("#data_result"+value).html("<br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 		// alert("value of id"+valuepassed);
 		$.ajax({
 		    type : "post",
 		    url : "view/CloudApps/editeOrganizationconfigurationPreview.action?id="+value+"&filed_name="+fieldvalue+"&data=preview",
 		    async:true,
 		    success : function(a) {
 	      // $("#"+"data_result"+value).html(a);
 	      $("#"+"data_result"+value).html(a);
 		},
 		   error: function() {
 	            alert("error");
 	        }
 		 });
 }
function onLoadFn()
{
    document.getElementById('data_result').innerHTML = document.getElementById('iframeid').innerHTML;
}
function submitFn(formId,valuepassed,fieldvalue)
{
    // validate form here
    document.getElementById(formId).submit();
    previewPage(valuepassed,fieldvalue);
}
function changeValue(id)
{
	//alert("ID::::"+id);
	//alert("Value::::"+$('#'+id).val());
	//$('#'+id).is(':checked');
	if($('#'+id).val()==true)
	{
        $('#'+id).val(true);
    }
    else 
    {
        $('#'+id).val(false);
    }
	//alert("After Value::::"+ $('#'+id).val());
}
</script>
<!-- First Step script ends here -->

<!-- Completion of profile CSS starts here -->
<style type="text/css">

#slidereditor{
	width: 100%;
	margin: 0 auto;
	overflow: hidden;
	position:relative;
	min-height:431px;
}
#itemseditor{
	width: 3000em;
	position: absolute;
}
#itemseditor > li, #itemseditor > div{
	float: left;
	overflow: visible;
}
#itemseditor > div > div{
	margin: 0 auto;
}
#slide-1{
	background-color:black;
}
#itemseditor > div > div{
	width: 1580px;
}
	/*	.lt-1680 .content{
			width: 1580px;
		}*/
.lt-1440 #itemseditor > div > div, .lt-1440 #navigation-wrapper{
	width: 1180px;
}
.lt-1280 #itemseditor > div > div, .lt-1280 #navigation-wrapper{
	width: 770px;
}
.lt-1024 #itemseditor > div > div, .lt-1024 #navigation-wrapper{
	width: 768px;
}
.lt-768 #itemseditor > div > div, .lt-768 #navigation-wrapper{
	width: 640px;
}
.lt-640 #itemseditor > div > div, .lt-640 #navigation-wrapper{
	width: 480px;
}
.lt-480 #itemseditor > div > div, .lt-480 #navigation-wrapper{
	width: 460px;
}
.lt-320 #itemseditor > div > div, .lt-320 #navigation-wrapper{
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
    height: 5px;
    width: 5px;
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
    height: 5px;
    width: 5px;
	position:absolute;
	right:-15px;
	top:45%;
}
a.next:hover{
	background-position: -34px 0;
}

</style>

<!-- Completion of profile CSS ends here -->

    <sj:dialog 
    	id="mybuttondialog" 
    	buttons="{ 
    		'Cancel':function() { cancelButton(); } 
    		}" 
    	autoOpen="false" 
    	modal="true" 
    	width="310"
		height="500"
    	title=" Edit Box"
    	resizable="false"
    >
</sj:dialog>
	<div class="main_wrapper">
		<div class="steps">
			<div class="steps_block">
				<div class="steps_sub_block" style=" position:relative;margin-left: -266px; width: 1420px;    height: 570px; padding:1px; border:4px solid #999999;">
					<div id="slidereditor" style=" height: 100%;">
						<div id="itemseditor">
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
									<s:property value="%{id}"/>
								</div>
							</s:iterator>
						</div>
					</div>
					<div id="navigation-wrapper">
							<a class="prev left" href="#" onclick="showdata1Editor();">&nbsp;</a>
							<a class="next right" href="#" onclick="showdataEditor();">&nbsp;</a>
						<ul id="navigation">
							<li class="active"></li>
							<li></li>
							<li></li>
						</ul>
					</div>
			</div>
		</div>
	</div>
	</div>
