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
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript">
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#resultTarget").fadeOut(); }, 400);
		  });
		  
$(document).ready(function()
		 {
		 	$("#escDept").multiselect({
		 		   show: ["", 200],
		 		   hide: ["fold", 1000]
		 		});
		 });
$(document).ready(function()
		 {
		 	$("#escFor").multiselect({
		 		   show: ["", 200],
		 		   hide: ["fold", 1000]
		 		});
		 });
		 
function getEscalationTime(addressingTime,resolutionTime,id)
{
  //alert("Inside Alert  ");
  var addressId = document.getElementById(addressingTime).value;
  var resId = document.getElementById(resolutionTime).value;
  $.getJSON("/over2cloud/view/Over2Cloud/feedback/Escalation_Conf/escalationTime.action?addressTime="+addressId+"&resTime="+resId,
  function(data){
    		       if(data.resolution_time == null){
        		  //    alert("Inside If");
    		       $("#esctime").val("");
    		    }
    		       else {
    		    	  //alert("Inside Else "+data.resolution_time);
    		          	   $("#esctime").val(data.resolution_time);
    					}
    		   });
}
function getOnlinePage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaOnline.action?feedStatus=online&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function viewActivityBoard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function hideBlock(value,divId)
{
	if(value=="Customised")
	{
		$("#"+divId).show();
	}
	else
	{
		$("#"+divId).hide();
	}
}

function viewEscConfig()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/feedback/Escalation_Conf/ViewEscDept.jsp",
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
	<div class="head">Configure Escalation</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Add</div>  
	  
</div>
<div class="clear"></div>
<div style="overflow:auto; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="addEscConfig" theme="simple"  method="post" enctype="multipart/form-data" >

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  

				
				
				<div class="newColumn">
        			   <div class="leftColumn" >Escalation For:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escFor"
				                              name="escFor"
				                              list="#{'Admin Round':'Admin Round','By Hand':'By Hand','Email':'Email','Paper':'Paper','SMS':'SMS','Ward Rounds':'Ward Rounds','Verbal':'Verbal','Mob Tab':'Mob Tab'}"
				                              headerKey="All"
				                              headerValue="All Feedback Type" 
				                            
				                              cssClass="select"
					                          cssStyle="margin-left: 3px;width: 143px;height: 30px;"
					                          multiple="true"
				                              >
				                  </s:select>
                           </div>
                   </div>
                   
				
				<div class="newColumn">
        			   <div class="leftColumn" >Mapped Department:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escDept"
				                              name="escDept"
				                              list="serviceDeptMap"
				                              headerKey="All"
				                              headerValue="All Department" 
				                              cssClass="select"
					                          cssStyle="margin-left: 3px;width: 143px;height: 30px;"
					                          multiple="true"
				                              >
				                  </s:select>
                           </div>
                   </div>
                   
                  <%--  <div class="newColumn">
        			   <div class="leftColumn" >Escalation Level:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevel"
				                              name="escLevel"
				                              list="#{'subdept':'General','floor':'Floor Wise','wings':'Wings Wise'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
				                              >
				                  </s:select>
                           </div>
                   </div> --%>
                   
                   <div class="newColumn">
								<div class="leftColumn">Consider Roaster:&nbsp;</div>
								<div class="rightColumn">
								<input type="radio" id="yes" name="considerRoaster" value="Yes" checked>Yes
          			            &nbsp; &nbsp; &nbsp;
          			            <input type="radio" id="no"  name="considerRoaster" value="No" >No
    			  </div>
    			  </div>
    			  
    			   <div class="clear"> </div>
    			  <%-- <div class="newColumn">
        			   <div class="leftColumn" >L2 To L3 Time:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevelL2L3"
				                              name="escLevelL2L3"
				                              list="#{'Multiplicative':'Multiplicative','Customised':'Customised'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                            onchange="hideBlock(this.value,'block23')"
				                              >
				         			</s:select>
                           </div>
                   </div> --%>
                   
                  
                   <!--  <div id="block23" style="display: none">   -->
                   <div class="newColumn">
        			   <div class="leftColumn" >Addressing Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="Enter Addressing Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="addrtime" name="addrtime" size="20"   readonly="true"   showOn="focus" onchange="getEscalationTime('addrtime','restime','')"/>
                           </div>
                   </div>
                  <!--  </div> -->
                     <%--  <div class="newColumn">
        			   <div class="leftColumn" >L3 To L4 Time:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevelL3L4"
				                              name="escLevelL3L4"
				                              list="#{'Multiplicative':'Multiplicative','Customised':'Customised'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                          onchange="hideBlock(this.value,'block34')"
				                              >
				         			</s:select>
                           </div>
                   </div> --%>
                   <!-- <div id="block34" style="display: none"> -->
                   <div class="newColumn">
        			   <div class="leftColumn" >Resolution Time:&nbsp;</div>
					        <div class="rightColumn">
				                    <sj:datepicker placeholder="Enter Resolution Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="restime" name="restime" size="20"   readonly="true" onchange="getEscalationTime('addrtime','restime','')"  showOn="focus"/>
                       
				             </div>
                   </div>
                   <!-- </div> -->
                    <div class="clear"> </div>
                  <%--  <div class="newColumn">
        			   <div class="leftColumn" >L4 To L5 Time:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevelL4L5"
				                              name="escLevelL4L5"
				                              list="#{'Multiplicative':'Multiplicative','Customised':'Customised'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                          onchange="hideBlock(this.value,'block45')"
				                              >
				         			</s:select>
                           </div>
                   </div> --%>
                    <div class="newColumn">
        			   <div class="leftColumn" >Escalation Time:&nbsp;</div>
					        <div class="rightColumn">
				              <%--      <sj:datepicker placeholder="Enter L4 To L5 Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime2" name="l4Tol5" size="20"   readonly="true"   showOn="focus"/>
                        --%>   
                              		<s:textfield name="esctime" id="esctime" name="esctime" maxlength="30" placeholder="Enter Data"  readonly="true"  cssClass="textField"/>
				
                         </div>
                   </div>
                   
          <div class="clear"> </div>   
		<!--  <div class="fields" align="center"> -->
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
						onclick="viewEscConfig();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <sj:div id="resultTarget"  effect="fold">
   	     </sj:div>
		 <!-- </div> -->
</s:form>
</div>
</div>
</body>
</html>