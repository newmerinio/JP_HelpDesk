<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<style>
.radioClass
{
    color: #ffffff;
}
</style>
<SCRIPT type="text/javascript">
function viewFeedback() {
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/feedback_Activity/viewActivityHeader.action",
        success : function(subdeptdata) 
        {
            $("#"+"data_part").html(subdeptdata);
        },
       error: function() 
       {
           alert("error");
       }
     });
}


$.subscribe('level1', function(event,data)
           {
             setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
             setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
           });

function feedbackReport(val,data)
{
    $("#"+data).val(val);
}
</script>
</head>
<body>
<div class="list-icon">
     <div class="head">Paper Feedback</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
     <div align="right"><sj:a  buttonIcon="ui-icon-person" button="true" onclick="viewFeedback();" title="PCC Action Page" cssClass="button" cssStyle = "width: 140px;margin-right: 3px;margin-top:3px;height:25px">Activity Board</sj:a></div>
</div>
<div class="clear"></div>
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/feedback" action="addFeedbackDataViaPaper" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
                           <center>
                             <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;" id="err">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
                           
<div style="height: 30px;" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
    <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
        <tbody>
            <tr>
                      <table class="floatL" border="0" cellpadding="0" cellspacing="0">
                        <tbody><tr>
                        
                        <s:select 
	                      id="visitType" 
	                      name="visitType" 
	                      list="{'Patient','Visitor'}"
	                      headerKey="-1"
	                      headerValue="Visit Type"
	                      cssClass="button"
	                      theme="simple"
	                      cssStyle = "height:28px;margin-top:-2px;;margin-left: 3px"
	                      onchange="checkFormValue(this.value)"
                      />
                      
                        <s:select 
	                      id="compType" 
	                      name="compType" 
	                      list="{'IPD','OPD'}"
	                      headerKey="-1"
	                      headerValue="Patient Type"
	                      cssClass="button"
	                      theme="simple"
	                      cssStyle = "height:28px;margin-top:-2px;;margin-left: 3px"
	                      onchange=""
                      />
                     <s:textfield  theme="simple" id="clientId" name="clientId" placeholder="Enter MRD No" cssClass="button" cssStyle = "height:15px;margin-top:-2px;margin-left: 3px" /></tr></tbody>
                      </table>
                  <!-- Block for insert Right Hand Side Button -->
                  <td class="alignright printTitle">
                    </td>
            </tr>
        </tbody>
    </table>
</div>
</div>
<div id="patientDetialsViewDiv"> </div>
<br />
                              <center>
                             <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone1" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div style="padding-bottom: 10px;" align="center">
                        <sj:submit 
                             targets="confirmingEscalation" 
                             clearForm="true"
                             value="Save" 
                             button="true"
                             onBeforeTopics="validateForm"
                             cssStyle="margin-left: -130px;margin-top: 20px;"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             onclick="resetForm('formone');"
                             cssStyle="margin-top: -42px;"
                        />&nbsp;&nbsp;
                        <sj:a
                        button="true" href="#" value="Back" onclick="viewFeedback();" cssStyle="margin-top: -71px;margin-left: 119px;"
                        >Back
                    </sj:a>
                    </div>
               </div>
               <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
                <center>
                     <sj:dialog loadingText="Please wait..."  id="confirmEscalationDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Online" width="450" cssStyle="overflow:hidden;" height="200" showEffect="slide" hideEffect="explode" >
                           <sj:div id="foldeffectExcel"  effect="fold"><div id="saveExcel"></div></sj:div><div id="confirmingEscalation"></div>
                     </sj:dialog>
                </center>
</div>
</div>
<br/><br/></s:form>
</body>
</html>