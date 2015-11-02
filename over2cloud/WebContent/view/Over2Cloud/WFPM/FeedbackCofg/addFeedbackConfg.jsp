<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<style type="text/css">

.user_form_input{
    margin-bottom:10px;
}

</style>
<html>
<head>
<SCRIPT type="text/javascript">
	$.subscribe('level1', function(event,data)
     {
       setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
       setTimeout(function(){ $("#orglevel1").fadeOut(); }, 10000);
     });

	$.subscribe('sliderStop', function(event, data) {
		alert('Slider stoped with value : ' + event.originalEvent.ui.value);
	});
	$.subscribe('sliderRangeStop', function(event, data) {
		alert('Slider stoped with values : ' + event.originalEvent.ui.values[0] + ' - ' + event.originalEvent.ui.values[1]);
	});
	
	function viewFeedbackConfig()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/masters/beforeFeedbackConfg.action?mainHeaderName=View Feedback Configuration",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	function checkKeywordExist(keyword)
	{

		if(keyword != "")
		{
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/wfpm/masters/keywordExist.action?keyword="+keyword,
			    success : function(data) {
			    if(data.exist == true)
			    {
				    $('#status').text("Keyword already exist !!!");
				    $('#status').css("color","red");
				}
			    else
			    {
			    	$('#status').text("Keyword available !!!");
			    	$('#status').css("color","black");
				}
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
</SCRIPT>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head"><s:property value="mainHeaderName"/> </div>
	</div>
	
	<div style=" float:left;  width:98%;">
		<div class="border">
			<s:form id="configFeedback" name="configFeedback" namespace="/view/Over2Cloud/wfpm/masters" action="feedbackConfig" theme="simple"  method="post" enctype="multipart/form-data">
				<div class="menubox">
					<div class="clear"></div>
					<div class="newColumn">
						<div class="leftColumn1">After</div>
						<div class="rightColumn1">
						 <sj:spinner name="after" id="after" min="0" max="50" cssStyle="width: 85%;" step="1" value="1" cssClass="textField" mouseWheel="true" placeholder="Enter Data"/>
						</div>
					</div>
					<div class="newColumn">
						<div class="leftColumn1">Time</div>
						<div class="rightColumn1">
							<sj:datepicker id="time" name="time" value="%{new java.util.Date()}"  readonly="true"  showOn="focus" cssClass="textField"  timepicker="true" timepickerOnly="true"/>
						</div>
					</div>
					<div class="clear"></div>
					<div class="newColumn">
						<div class="leftColumn1">Keyword</div>
						<div class="rightColumn1"><s:textfield name="keyword"  id="keyword"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" onblur="checkKeywordExist(this.value)"></s:textfield> </div>
						<div id="status" style="font-size: 14px;margin-left: -50px;"></div>
					</div>
					<div class="newColumn">
						<div class="leftColumn1">Length</div>
						<div class="rightColumn1">
							<sj:spinner name="length" id="length" min="1" max="200" step="1" value="1" cssClass="textField" cssStyle="width: 85%;" mouseWheel="true" placeholder="Enter Data"/>
						</div>
					</div>
					<div class="clear"></div>
					<div class="newColumn">
						<div class="leftColumn1">Keyword Type</div>
						<div class="rightColumn1">
							<s:select name="type" id="type" list="#{'0':'Positive', '1':'Negative'}" headerKey="-1" value="-- Select Keyword Type --" cssStyle="width:82%;"></s:select>
						</div>
					</div>
						
					<div class="clear"></div>
					<div style="width: 100%; text-align: center; padding-bottom: 10px; margin-left: 70px;">
						<sj:submit 
				             targets="result" 
		                     clearForm="true"
		                     value="  Add  " 
		                     effect="highlight"
		                     effectOptions="{ color : '#222222'}"
		                     effectDuration="5000"
		                     button="true"
		                     onCompleteTopics="level1"
		                     cssClass="button"
		                     indicator="indicator2"
		                     onclick="checkKeywordExist();"
			            />
						<sj:a 
					     	name="Cancel"  
							href="#" 
							cssClass="button" 
							indicator="indicator" 
							button="true" 
							onclick="viewFeedbackConfig();"
						>
						  	Cancel
						</sj:a>
						
	                    
					</div>
				</div>	
			</s:form>
			<div class="clear"></div>
			<sj:div id="orglevel1"  effect="fold">
                <div id="result" align="center"></div>
            </sj:div>
			
		</div>
	</div>
</body>
</html>