<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>

<script type="text/javascript">
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#resultTarget").fadeOut(); }, 400);
		  });


	function viewRatingDiv(openType)
	{
		if(openType=="Rating")
		{
			document.getElementById("ratingLessDiv").style.display="block";
		}	
		else
		{
			document.getElementById("ratingLessDiv").style.display="none";			
		}	
	}
function viewTicketConfig()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/feedback/Escalation_Conf/beforeTicketViewConf.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Open Type</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">Configure</div>  
	  
</div>
<div class="clear"></div>
<div style="overflow:auto; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="addTicketConfig" theme="simple"  method="post" enctype="multipart/form-data" namespace="/view/Over2Cloud/feedback/Escalation_Conf" >

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  

				<div class="newColumn">
        			   <div class="leftColumn" >Open&nbsp;Type:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  
				                  		id="openType"
				                        name="openType"
				                        list="#{'Manual':'Manual','Rating':'Rating'}"
				                        headerKey="-1"
				                        headerValue="Open Type" 
				                        cssClass="select"
					                    cssStyle="margin-left: 3px;width: 143px;height: 30px;"
					                    onchange="viewRatingDiv(this.value)"
				                      >
				                  </s:select>
                           </div>
                   </div>
                   <div id="ratingLessDiv" style="display: none">
                   <div class="newColumn">
        			   <div class="leftColumn" >Rating&nbsp;<=: </div>
					        <div class="rightColumn">
				                  <s:select  
				                  		id="minRating"
				                        name="minRating"
				                        list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
				                        headerKey="-1"
				                        headerValue="Select Rating" 
				                        cssClass="select"
					                    cssStyle="margin-left: 3px;width: 143px;height: 30px;"
				                      >
				                  </s:select>
                           </div>
                   </div>
                   </div>
                   
                   <div class="newColumn">
						<div class="leftColumn">Rating:&nbsp;</div>
							<div class="rightColumn">
								<input type="radio" id="yes" name="negRating" value="Yes" checked>Yes
          			            &nbsp; &nbsp; &nbsp;
          			            <input type="radio" id="no"  name="negRating" value="No" >No
    			 			</div>
    			 	</div>
                   
                   <div class="newColumn">
						<div class="leftColumn">Recommend:&nbsp;</div>
							<div class="rightColumn">
								<input type="radio" id="yes" name="recommend" value="Yes" checked>Yes
          			            &nbsp; &nbsp; &nbsp;
          			            <input type="radio" id="no"  name="recommend" value="No" >No
    			 			</div>
    			 	</div>
			
          <div class="clear"> </div> 
          <center>  
		 <ul>
			<li class="submit" style="background: none;">
				<div class="type-button">
	        	<sj:submit 
         				targets			=	"resultTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
	                    effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                        indicator		=	"indicator2"
                        onBeforeTopics  =   "validate"
                        
     		  	  />
     		  	  <sj:a 
						button="true" href="#"
						onclick="resetForm('formone');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewTicketConfig();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <sj:div id="resultTarget"  effect="fold">
   	     </sj:div>
</s:form>
</div>
</div>
</body>
</html>