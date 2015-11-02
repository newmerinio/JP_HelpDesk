<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/communication/CommunicationInstantMessage.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/CommunicationScheduleMessage.js"/>"></script>
<script src="<s:url value="/js/common/instantacommanvalidation.js"/>"></script>

<SCRIPT type="text/javascript">
function backtoCRMActivityView(){
$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
$.ajax({
	type : "post",
	url : "view/Over2Cloud/WFPM/CRM/beforeCommActivityPage.action",
	success : function(subdeptdata) {
	$("#data_part").html(subdeptdata);
},
error: function() {
	alert("error");
}
 });
}

function changeFrequency(val,div1,div2,div3,div4)
{
if(val=="One-Time")
{
	$("#"+div1).show();
	$("#"+div2).hide();
	$("#"+div3).show();
}
else if(val=="Daily")
{
	$("#"+div1).hide();
	$("#"+div2).hide();
	$("#"+div3).show();
}
else if(val=="Weekly")
{
	$("#"+div1).hide();
	$("#"+div2).show();
	$("#"+div3).show();
}
else if(val=="Monthly")
{
	$("#"+div1).show();
	$("#"+div2).hide();
	$("#"+div3).show();
}
else if(val=="Yearly")
{
	$("#"+div1).show();
	$("#"+div2).hide();
	$("#"+div3).show();
}
else if(val=="-1")
{
	$("#"+div1).hide();
}
}


$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut();cancelButton();}, 4000);
	       });
function cancelButton()
{
	 $('#completionResult').dialog('close');
	 backToMainView();
}

function getSubDept(deptId,destAjaxDiv,deptLevel,subdeptType) 
{
	var dept=$("#"+deptId).val();
	if (deptLevel=='2') {
    if (subdeptType=='1') {
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/CommunicationOver2Cloud/Comm/subDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
		success : function(subDeptData){
		$("#" + destAjaxDiv).html(subDeptData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Sub Department");
		}
	});
    }
  }
}

function changecontact()
{
	
	if(formtwo.mappedid.value != -1)
	{
		if(formtwo.mappedid.value == -2)
		{
			alert('This is a Category, Instead Please Select Group Name.');
			formtwo.mappedid.selectedIndex = 0;
		}
		else
		{
			formtwo.groupName.disabled = true;
		}	
	}
	else
	{
		formtwo.groupName.disabled = false;
	}
}

//disable Contact name.
function changeGroup11()
{
	//alert('changeEmp:frm.grp_name.value'+frm.grp_name.value);
	if( formtwo.groupName.value != -1){
		if(formtwo.groupName.value == -2){
			alert('This is a Category, Instead Please Select Group Name.');
			formtwo.groupName.selectedIndex = 0;
		}else{
			formtwo.mappedid.disabled = true;
		}	
	}else{
		formtwo.mappedid.disabled = false;
	}
}

function gettemplatemsg(message){
	$.getJSON("view/Over2Cloud/CommunicationOver2Cloud/mail/tamplemail.action?message="+message,
		      function(data){
		            $("#writemail").val(data);
		            var i=document.getElementById("writemail").value.length;
		             $("#remLen").val(i);
		    	  });
}

function checkNumber(mobileNum) 
{ 
	var IndNum = /^\d{10}$/;
	var regex = new RegExp(IndNum); 
	
	if (mobileNum.match(regex)) 
		return true; 
	else 
		return false; 
} 
function checkMobNo()
{
	var csvNum = document.formtwo.mobileNo.value;
	var newtext = csvNum.split(","); 
	for(var i=0 ;i < newtext.length; i++) 
	{ 
		var mobileNum = checkNumber(newtext[i]);
		if (!mobileNum) 
		{ 
			errnumber.innerHTML="<div class='user_form_inputError2'>Please enter comma seperated mobile num.</div>";
			document.formtwo.mobileNo.value="";
			//document.formone.csvNumber.focus();
			return false;
		}
	} 
	errnumber.innerHTML="";
	return true;
}

//validation for text counter.
function textCounter(field, countfield, maxlimit) {
	if (field.value.length < maxlimit) 
	field.value = field.value.substring(0, maxlimit);
	
	else 
	countfield.value = maxlimit + field.value.length;
	}

</script>
<script type="text/javascript">
function setdyanmaictemplatemsg(val,mobileNo,frequencys,divId){
 $('#rootname').val($('#root').val());
var groupName= $('#groupName').val();
if(val=="Transssss" && $("#ASExcel").val()=="" && $("#ASExcels").val()==""){
if(frequencys.value!=null && frequencys.value!=""){
 var frequencys=frequencys.value;
 var hours = $("#hours").val();
 var date = $("#date").val();
 var dates = $("#dates").val();
 var day = $("#day").val();
 $("#settemplateGriddata").load("<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/template/viewtemplatedata.action?frequencys="+frequencys+"&hours="+
 hours+"&date="+date+"&dates="+dates+"&day="+day+"&mobileNo="+mobileNo.value+"&root="+val.split(" ").join("%20"));
 $("#settemplateGriddata").dialog('open');
 }else{
 $("#settemplateGriddata").load("<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/template/viewtemplatedata.action?mobileNo="+mobileNo.value+"&root="+val.split(" ").join("%20"));
	$("#settemplateGriddata").dialog('open');
  }
 }
else{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxtemplate.action?template_type="+val,
	    success : function(Data){
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("--Select Template--"));
	    	$(Data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		    },
	   error: function() {
	        alert("error");
	    }
	 });
	
   }
}
</script>
<script type="text/javascript">
	$.subscribe('mailpageId', function(event,data)
	{
		$('#completionResult').dialog('open');
	  });
</script>
<script type="text/javascript">
function okdownload(){
  $("#download").submit();
}
function okdownloadexcel(){
	  $("#downloadexcel").submit();
	}
function changeGroupname(divId,param){

	if(param!='all_contactType'){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxcontactsubType.action?select_param="+param,
	    success : function(Data){
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Sub Contact Type"));
	    	$(Data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		    },
	   error: function() {
	        alert("error");
	    }
	 });
	}
}

function changsubcontactType(divId,param){
	if(param!='all_contactSubType'){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxmailcontactType.action?select_param="+param,
	    success : function(saveData){
	    	$("#clienttddddd").html(saveData);
		    },
	   error: function() {
	        alert("error");
	    }
	 });
	}
}
</script>
<script type="text/javascript">

function checkMail() {
	 var pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
	    if(!pattern.test(document.formone.email_id.value))
	    {         
	    	errZone.innerHTML = "<div class='user_form_inputError2'>Please Enter Correct Email Id.</div>";
	    	//document.formone.emailId.value = "";
	    	//document.formone.submit = false; 
	    	return false;
	    }
	    errZone.innerHTML = "<div class='user_form_inputError2'></div>";
	    return true;
}
</script>
<script type="text/javascript">
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}
function cancelButtonsendsms(){
	$('#cancelmailButton').dialog('close');
	};
</script>
</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="resultComes"></div>
</sj:dialog>

<sj:dialog
          id="tagViewId"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Tags For Use"
          modal="false"
          width="1000"
   	      height="170"
          draggable="true"
    	  resizable="true"
    	  >
 
</sj:dialog>
<div class="list-icon">
	<div class="head">Mail </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Send</div>
</div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="border">
<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>
   <div id="excelError"></div>   
    <div id="txtError"></div>   
        </div>
 
    <s:form id="formone" name="formone"  namespace="/view/Over2Cloud/WFPM/CRM" action="insertMailData" theme="simple"  method="post" enctype="multipart/form-data">
        <s:hidden name="data" value="%{data}"></s:hidden>
  	    <s:hidden name="entityContacts" value="%{entityContacts}" />
        <s:hidden name="entityType" value="%{entityType}" />
			    <div class="clear"></div>	
			<!--  <div class="newColumn">
								<div class="leftColumn1">Contact Type:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="contacttype"
				                              name="contacttype"
				                              list='groupcontactNameListData'
				                              headerKey="-1"
				                              headerValue="-Select Contact Type-" 
				                              cssClass="select"
				                              onchange="changeGroupname('subcontacttype',this.value);"
				                             cssStyle="width:96%"
											>
						                 </s:select>
						           
								</div>
							</div>
							<div class="newColumn">
								<div class="leftColumn1">Contact Sub Type:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="subcontacttype"
				                              name="subcontacttype"
				                              list="#{'-1':'-Select Contact Sub-Type-'}"
				                              headerKey="-1"
				                              cssClass="select"
				                              onchange="changsubcontactType('check_list',this.value);"
				                             cssStyle="width:96%"
											>
						                 </s:select>
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
								</div>
							</div>
							<div id="clienttddddd">
				<div class="newColumn">
								<div class="leftColumn1">Contact Person:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="contactid"
				                              name="contactid"
				                              list="#{'-1':'-Select Contact Person-'}"
				                              headerKey="-1"
				                              cssClass="select"
				                              cssStyle="width:96%"
											>
						                 </s:select>
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
															</div>
							</div>
							</div>
							
							<div class="newColumn">
								<div class="leftColumn1">Select Group:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="groupName"
				                              name="groupName"
				                              list='groupNameListData'
				                              headerKey="-1"
				                              headerValue="-Select Group Name-" 
				                              cssClass="select"
				                              cssStyle="width:96%"
				                             
											>
						                 </s:select>
								</div>
							</div>
			
			<div class="newColumn">
								<div class="leftColumn1">Select Template:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="template_name"
				                              name="template_name"
				                              list='templateNameList'
				                              headerKey="-1"
				                              headerValue="-Select Template Name-" 
				                              cssClass="select"
				                               cssStyle="width:96%"
				                              onchange="gettemplatemsg(this.value);"
											>
						                 </s:select>
								</div>
			</div>
	-->
	
			<s:iterator value="mobileTextBox" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
           
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
	                   <div class="newColumn">
						<div class="leftColumn1">From <s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					     <span class="needed"></span><s:textfield name="from_email"  id="from_email"   value="services@dreamsol.biz"  onchange="validateCsv()"  cssClass="textField" placeholder="Enter Data" cssStyle="margin: 0px 0px 10px; width: 302px; height: 30px;" />
					     </div></div>
						  </s:if>
					     
			  </s:if>
		</s:iterator>
		<!--<div class="newColumn">
						<div class="leftColumn1">To Email Id:</div>
						<div class="rightColumn">
					     <span class="needed"></span>
					    <s:textarea name="email_id" id="email_id" cols="95" rows="20" class="form_menu_inputtext"  style="margin: 0px 0px 10px; width: 302px; height: 30px;" />
						</div>
		</div>
	
   --><div class="newColumn">
     <span id="mandatoryFields" class="pIds" style="display: none; ">subject#Subject:#T#a,</span>
						<div class="leftColumn1">Subject:</div>
						<div class="rightColumn">
					     <span class="needed"></span>
					    <s:textarea name="subject" id="subject" cols="95" rows="20" class="form_menu_inputtext" onkeydown="textCounter(this.form.writeMessage,this.form.remLen,0)" style="margin: 0px 0px 10px; width: 302px; height: 30px;"/>
						</div>
					     </div>
					     <div class="clear"></div>	
       <div class="newColumn">
       <div class="leftColumn1">Select Attachment:</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		<s:file id="crmImage" type="crmImage" name="crmImage" cssClass="textField" cssStyle="width: 94%;"/>
      </div>
       </div>
    <s:iterator value="frequency" begin="0" end="0">
<div class="clear"></div>
		<div style="width:100%; line-height:40px;">
       <div style="text-align:right; padding:6px; line-height:25px;	float:left;	width:12%;">
       <s:property value="%{value}"/>:</div>
       <div style="width: 100%">
             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      	 	 <s:radio list="#{'One-Time':'One-Time','Daily':'Daily','Weekly':'Weekly','Monthly':'Monthly','Yearly':'Yearly'}" name="%{key}" id="%{key}" onclick="changeFrequency(this.value,'dateDiv','dayDiv','timeDiv','dateDiv1')"/>
       </div>
       </div>
					<div class="clear"></div>
							<div id="dateDiv" style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Date</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       								<sj:datepicker id="date" name="date" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="yy-mm-dd"  cssClass="textField" />
      						 </div>
				             	<div id="errorlevel2org" class="errordiv"></div>
							</div>
							</div>
							<div id="dateDiv1" style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Date</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      							 <sj:datepicker id="dates" name="date" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="yy-mm-dd" cssClass="textField" />
     						  </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div>
							</div>
					
							<div id="dayDiv" style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Day:</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
						 <s:select 
                              id="day"
                              name="day" 
                              list="#{'Monday':'Monday','Tuesday':'Tuesday','Wednesday':'Wednesday','Thursday':'Thursday','Friday':'Friday','Saturday':'Saturday'}"
                              headerKey="Sunday"
                              headerValue="Sunday" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  		 </s:select>
      					 </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div></div>
							<div id="timeDiv" style="display: none">	
					     <div class="newColumn">
							<div class="leftColumn1">Time:</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      					 <sj:datepicker id="hours" name="hours" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" timepickerFormat="hh:mm" cssClass="textField"/>
      						 </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div></div>

</s:iterator>
			<div class="newColumn" style="width: -1%; margin-top: -6%; margin-left: 60%;" id="mailtagdatapart">
					
					<a href="#" style='cursor: pointer; color:blue;' onClick="loadTagData();">Tags For Mail</a>
				</div>
<div class="clear"></div>	
 <s:iterator value="writemessageTextBox" >
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
				<s:if test="%{key == 'writemail'}">
			<div class="list-icon" style="height: 25px;">
                  <div class="head" style="margin-top: -4px;">Message</div>
                  </div>
		      		   <table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				    <tbody width="100%">
		      		   <tr>
			   <sjr:ckeditor 
				    name="%{key}" 
					id="writemail" 
					rows="10" 
					cols="100" 
					width="1000"
				    height="120"
				    cssClass="margin-left: 41px;"
					onFocusTopics="focusRichtext"
					onBlurTopics="blurRichtext"
					onChangeTopics="highlightRichtext"
				/>
		      		   </tr>
		      		   </tbody>
		      		   </table>
		      		   </s:if>
		      		   </s:iterator>
				<div class="clear"></div>	
 
 					   <div class="clear"></div>	
				<div class="clear"></div>	
				
                <br>
		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			<center>

			<sj:submit 
          			   targets="resultComes"
                       clearForm="true"
                       value="Send" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       cssClass="submit"
                       cssStyle="margin-right: 2px;margin-bottom: 29px;"
                       indicator="indicator2"
                       onBeforeTopics="mailpageId"
                       onCompleteTopics="level1"
         />
         
         	<sj:a 
				     	href="#" 
		               	button="true" 
		                onclick="resetForm('formone');"
						cssStyle="margin-top: -28px;"
						>
					  	Reset
		   </sj:a>
         	<sj:a 
				     	href="#" 
		               	button="true" 
		                onclick="backToMainView();"
						cssStyle="margin-top: -28px;"						
						>
					  	Back
		   </sj:a>
        
		  </center> 
   </div>

</s:form>  
</div>
</div>
</div>
</div>
</body>

</html>